/**
 * Copyright 2017 TerraMeta Software, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.plasma.runtime;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.atteo.classindex.ClassIndex;
import org.jdom2.output.Format;
import org.modeldriven.fuml.Fuml;
import org.modeldriven.fuml.io.ResourceArtifact;
import org.plasma.common.bind.DefaultValidationEventHandler;
import org.plasma.common.env.EnvConstants;
import org.plasma.common.env.EnvProperties;
import org.plasma.common.exception.PlasmaRuntimeException;
import org.plasma.metamodel.Model;
import org.plasma.metamodel.adapter.ModelAdapter;
import org.plasma.metamodel.adapter.ProvisioningModel;
import org.plasma.profile.ProfileArtifact;
import org.plasma.profile.ProfileConfig;
import org.plasma.profile.ProfileURN;
import org.plasma.profile.adapter.ProfileArtifactAdapter;
import org.plasma.provisioning.AnnotationMetamodelAssembler;
import org.plasma.runtime.Artifact;
import org.plasma.runtime.DataAccessProvider;
import org.plasma.runtime.DataAccessProviderName;
import org.plasma.runtime.DataAccessService;
import org.plasma.runtime.DataStoreType;
import org.plasma.runtime.EnumSource;
import org.plasma.runtime.GlobalProvisioning;
import org.plasma.runtime.ImplementationProvisioning;
import org.plasma.runtime.InterfaceProvisioning;
import org.plasma.runtime.Namespace;
import org.plasma.runtime.NamespaceLink;
import org.plasma.runtime.NamespaceProvisioning;
import org.plasma.runtime.Property;
import org.plasma.runtime.PropertyNameStyle;
import org.plasma.runtime.QueryDSLProvisioning;
import org.plasma.runtime.RDBMSVendorName;
import org.plasma.runtime.Repository;
import org.plasma.runtime.SDO;
import org.plasma.runtime.TypeBinding;
import org.plasma.runtime.adapter.NamespaceAdapter;
import org.plasma.runtime.adapter.PropertyBindingAdapter;
import org.plasma.runtime.adapter.TypeBindingAdapter;
import org.plasma.runtime.annotation.AmbiguousProviderException;
import org.plasma.runtime.annotation.NamespaceService;
import org.plasma.runtime.annotation.PlasmaServiceProvider;
import org.plasma.runtime.annotation.UnknownProviderException;
import org.plasma.xml.uml.MDModelAssembler;
import org.plasma.xml.uml.PapyrusModelAssembler;
import org.plasma.xml.uml.UMLModelAssembler;
import org.xml.sax.SAXException;

public class PlasmaRuntime {

  private static Log log = LogFactory.getLog(PlasmaRuntime.class);
  private static volatile PlasmaRuntime instance = null;
  private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
  private static final String defaultConfigFileName = "plasma-config.xml";
  private String configFileNameOrURIString;
  private URI configURI;
  private long configFileLastModifiedDate = System.currentTimeMillis();
  private RuntimeConfiguration config;
  private Map<String, Artifact> artifactMap = new HashMap<String, Artifact>();
  private Map<String, NamespaceAdapter> sdoNamespaceMap = new HashMap<String, NamespaceAdapter>();
  private DataAccessProviderName defaultProviderName;
  private List<Class<?>> metadataClasses = new ArrayList<Class<?>>();

  /** maps data store types to maps of namespace links */
  private Map<DataStoreType, Map<String, NamespaceLink>> dataStoreNamespaceLinkMap = new HashMap<DataStoreType, Map<String, NamespaceLink>>();

  /** maps provider names to provider instances */
  private Map<DataAccessProviderName, DataAccessProvider> dataAccessProviderMap = new HashMap<DataAccessProviderName, DataAccessProvider>();

  /** maps provider names to maps of namespace URI to provisioning structures */
  private Map<DataAccessProviderName, Map<String, NamespaceProvisioning>> dataAccessProviderProvisioningMap = new HashMap<DataAccessProviderName, Map<String, NamespaceProvisioning>>();

  /** maps provider names to maps of provider properties */
  private Map<DataAccessProviderName, Map<String, Property>> dataAccessProviderPropertyMap = new HashMap<DataAccessProviderName, Map<String, Property>>();

  /** */
  private Model derivedModel;

  private PlasmaRuntime() {
    log.debug("initializing...");
    try {

      this.configFileNameOrURIString = EnvProperties.instance().getProperty(
          EnvConstants.PROPERTY_NAME_ENV_CONFIG);

      if (this.configFileNameOrURIString == null)
        this.configFileNameOrURIString = defaultConfigFileName;

      for (Class<?> c : ClassIndex.getAnnotated(org.plasma.sdo.annotation.Type.class))
        metadataClasses.add(c);

      PlasmaRuntimeDataBinding configBinding = new PlasmaRuntimeDataBinding(
          new PlasmaRuntimeValidationEventHandler());

      InputStream stream = this.findConfigStream(this.configFileNameOrURIString);
      if (stream != null) {
        config = unmarshalConfig(this.configFileNameOrURIString, stream, configBinding);
        if (metadataClasses.size() > 0) {
          log.warn("found config file '" + this.configFileNameOrURIString + "' - ignoring "
              + metadataClasses.size() + " annotated classes ");
        }
        constructArtifactAndNamespaceMappings();
        constructKnownArtifacts();
        constructDataAccessServiceMappings();
        validateNamespaceToDataStoreMappings();
      } else {
        if (metadataClasses.size() > 0) {
          this.derivedModel = this.deriveModel();
          this.config = this.deriveConfig(derivedModel);
          constructArtifactAndNamespaceMappings(true);
          constructKnownArtifacts();

          // generate and load derived model as a UML stream
          UMLModelAssembler umlAssembler = new MDModelAssembler(derivedModel,
              derivedModel.getUri(), "tns");
          umlAssembler.setDerivePackageNamesFromURIs(false);
          String xmi = umlAssembler.getContent(Format.getPrettyFormat());
          ByteArrayInputStream fumlStream = new ByteArrayInputStream(xmi.getBytes());

          if (log.isDebugEnabled())
            log.debug("loading UML/XMI model");
          Fuml.load(new ResourceArtifact(derivedModel.getUri(), derivedModel.getUri(), fumlStream));

          constructDataAccessServiceMappings();
          validateNamespaceToDataStoreMappings();

        } else {
          if (this.configFileNameOrURIString.equals(defaultConfigFileName))
            throw new ConfigurationException(
                "could not find default configuration file resource '"
                    + this.configFileNameOrURIString
                    + "' on the current classpath and could not derive configuration from annotated classes and packages"
                    + " - please ensure all annotated classes are on the classpath");
          else
            throw new ConfigurationException(
                "could not find default configuration file resource '"
                    + this.configFileNameOrURIString
                    + "' on the current classpath and could not derive configuration from annotated classes and packages on the current classpath");
        }
      }

    } catch (SAXException e) {
      throw new ConfigurationException(e);
    } catch (JAXBException e) {
      throw new ConfigurationException(e);
    }
  }

  public boolean isDerived() {
    return derivedModel != null;
  }

  public Model getDerivedModel() {
    return derivedModel;
  }

  private RuntimeConfiguration deriveConfig(Model derivedModel) {
    RuntimeConfiguration config = new RuntimeConfiguration();
    log.debug("deriving configuration");
    SDO sdo = new SDO();
    config.setSDO(sdo);
    GlobalProvisioning gp = deriveGlobalProvisioning();
    sdo.setGlobalProvisioning(gp);

    List<NamespaceService> serviceAnnotations = deriveNamespaceSerivces();

    // discover data access services from annotated classes
    for (Class<?> c : ClassIndex.getAnnotated(PlasmaServiceProvider.class)) {
      PlasmaServiceProvider service = c.getAnnotation(PlasmaServiceProvider.class);
      log.debug("discovered " + service.storeType() + ":" + service.providerName() + " provider");
      DataAccessService das = new DataAccessService();
      sdo.getDataAccessServices().add(das);

      das.setDataStoreType(service.storeType());

      DataAccessProvider provider = new DataAccessProvider();
      das.getDataAccessProviders().add(provider);
      provider.setName(service.providerName());
      provider.setClassName(c.getName());

      for (NamespaceService serviceAnnotation : serviceAnnotations) {
        if (serviceAnnotation.storeType().ordinal() == das.getDataStoreType().ordinal()
            && serviceAnnotation.providerName().ordinal() == provider.getName().ordinal()) {
          for (String nameValue : serviceAnnotation.properties()) {
            String[] tokens = nameValue.split("=");
            Property propery = new Property();
            provider.getProperties().add(propery);
            propery.setName(tokens[0]);
            propery.setValue(tokens[1]);
            log.debug("added provider property: " + nameValue);
          }
        }
      }

    }
    if (sdo.getDataAccessServices().size() == 0)
      throw new ProviderDiscoveryException(
          "found no data access providers - please ensure the runtime classpath includes all intended data access providers");
    log.debug("discovered " + sdo.getDataAccessServices().size() + " access providers");

    List<Namespace> namespaces = deriveNamespaces(derivedModel.getUri());
    for (Namespace namespace : namespaces)
      sdo.getNamespaces().add(namespace);
    log.debug("discovered " + sdo.getNamespaces().size() + " namespaces");
    // associate every namespace with the only discovered provider
    if (sdo.getDataAccessServices().size() == 1) {
      for (Namespace namespace : namespaces) {
        DataAccessProvider provider = sdo.getDataAccessServices().get(0).getDataAccessProviders()
            .get(0);
        NamespaceLink nsLink = new NamespaceLink();
        provider.getNamespaceLinks().add(nsLink);
        nsLink.setUri(namespace.getUri());
      }
    } else { // must rely on annotation

      for (Namespace namespace : namespaces) {
        for (Class<?> c : ClassIndex.getAnnotated(org.plasma.sdo.annotation.Namespace.class)) {
          NamespaceService serviceAnnot = c.getAnnotation(NamespaceService.class);
          if (serviceAnnot == null)
            throwAmbiguousProvider(sdo, namespace);
          DataAccessProvider provider = findProvider(sdo, serviceAnnot);
          if (provider == null)
            throwUnknownProvider(sdo, serviceAnnot);
          NamespaceLink nsLink = new NamespaceLink();
          provider.getNamespaceLinks().add(nsLink);
          nsLink.setUri(namespace.getUri());
        }
      }
    }

    Repository repo = new Repository();
    config.getRepositories().add(repo);

    return config;
  }

  /**
   * Derives a model from discovered annotations, then converts the model to UML
   * / stream and loads it into the FUML runtime.
   * 
   * @return the derived artifact namespace URI
   */
  private Model deriveModel() {
    AnnotationMetamodelAssembler annotationAssembler = new AnnotationMetamodelAssembler();
    ProvisioningModel annotationModelAdapter = new ModelAdapter(annotationAssembler.getModel());
    return annotationModelAdapter.getModel();
  }

  private DataAccessProvider findProvider(SDO sdo, NamespaceService serviceAnnot) {

    for (DataAccessService das : sdo.getDataAccessServices()) {
      if (das.getDataStoreType().ordinal() == serviceAnnot.storeType().ordinal()) {
        DataAccessProvider provider = das.getDataAccessProviders().get(0);
        if (provider.getName().ordinal() == serviceAnnot.providerName().ordinal()) {
          return provider;
        }
      }
    }
    return null;
  }

  private void throwAmbiguousProvider(SDO sdo, Namespace namespace) {
    String providers = buildProvidersMessage(sdo);
    throw new AmbiguousProviderException("multiple providers discovered " + providers
        + " - could not determine provider for namespace '" + namespace.getUri() + "'"
        + " - please annotate the namespace (package) using " + NamespaceService.class.getName());

  }

  private void throwUnknownProvider(SDO sdo, NamespaceService serviceAnnot) {
    String providers = buildProvidersMessage(sdo);
    String provider = serviceAnnot.storeType().name() + ":" + serviceAnnot.providerName().name();
    throw new UnknownProviderException("no provider '" + provider + "' found - expected one of "
        + providers);
  }

  private String buildProvidersMessage(SDO sdo) {
    StringBuilder buf = new StringBuilder();
    buf.append("[");
    int i = 0;
    for (DataAccessService das : sdo.getDataAccessServices()) {
      if (i > 0)
        buf.append(", ");
      buf.append(das.getDataStoreType());
      buf.append(":");
      buf.append(das.getDataAccessProviders().get(0).getName());
    }
    buf.append("]");
    return buf.toString();
  }

  private List<Namespace> deriveNamespaces(String derivedArtifactNamespace) {

    Set<java.lang.Package> leafPackages = new HashSet<java.lang.Package>();
    for (Class<?> c : metadataClasses) {
      leafPackages.add(c.getPackage());
    }

    List<Namespace> namespaces = new ArrayList<Namespace>();
    for (java.lang.Package pkg : leafPackages) {
      org.plasma.sdo.annotation.Namespace nsAnnot = pkg
          .getAnnotation(org.plasma.sdo.annotation.Namespace.class);
      org.plasma.runtime.annotation.NamespaceProvisioning nsProvAnnot = pkg
          .getAnnotation(org.plasma.runtime.annotation.NamespaceProvisioning.class);

      Namespace namespace = new Namespace();
      namespaces.add(namespace);
      namespace.setArtifact(derivedArtifactNamespace); // FIXME: this is going
                                                       // to break
      namespace.setUri(nsAnnot.uri());
      log.debug("discovered namespace " + namespace.getUri());

      NamespaceProvisioning nsProvConfig = new NamespaceProvisioning();
      namespace.setProvisioning(nsProvConfig);
      nsProvConfig.setPackageName(nsProvAnnot.rootPackageName());

      InterfaceProvisioning itfProv = new InterfaceProvisioning();
      nsProvConfig.setInterface(itfProv);

      // FIXME: collapse these elements into property name/value pairs
      if (nsProvAnnot.propertyNameStyle() != null)
        itfProv.setPropertyNameStyle(nsProvAnnot.propertyNameStyle());
      else
        itfProv.setPropertyNameStyle(PropertyNameStyle.ENUMS);
      if (nsProvAnnot.enumSource() != null)
        itfProv.setEnumSource(nsProvAnnot.enumSource());
      else
        itfProv.setEnumSource(EnumSource.EXTERNAL);
      if (nsProvAnnot.interfaceNamePrefix() != null
          && nsProvAnnot.interfaceNamePrefix().trim().length() != 0)
        itfProv.setClassNamePrefix(nsProvAnnot.interfaceNamePrefix());
      if (nsProvAnnot.interfaceNameSuffix() != null
          && nsProvAnnot.interfaceNameSuffix().trim().length() != 0)
        itfProv.setClassNameSuffix(nsProvAnnot.interfaceNameSuffix());

      ImplementationProvisioning implProv = new ImplementationProvisioning();
      nsProvConfig.setImplementation(implProv);
      if (nsProvAnnot.implementationClassNamePrefix() != null
          && nsProvAnnot.implementationClassNamePrefix().trim().length() != 0)
        implProv.setClassNamePrefix(nsProvAnnot.implementationClassNamePrefix());
      if (nsProvAnnot.implementationClassNameSuffix() != null
          && nsProvAnnot.implementationClassNameSuffix().trim().length() != 0)
        implProv.setClassNameSuffix(nsProvAnnot.implementationClassNameSuffix());
      if (nsProvAnnot.implementationPackageName() != null
          && nsProvAnnot.implementationPackageName().trim().length() != 0)
        implProv.setChildPackageName(nsProvAnnot.implementationPackageName());

      QueryDSLProvisioning queryDSLProv = new QueryDSLProvisioning();
      nsProvConfig.setQueryDSL(queryDSLProv);
      ImplementationProvisioning queryDslimplProv = null;
      if (nsProvAnnot.queryDSLClassNamePrefix() != null
          && nsProvAnnot.queryDSLClassNamePrefix().trim().length() != 0) {
        queryDslimplProv = new ImplementationProvisioning();
        queryDSLProv.setImplementation(queryDslimplProv);
        queryDslimplProv.setClassNamePrefix(nsProvAnnot.queryDSLClassNamePrefix());
      }
      if (nsProvAnnot.queryDSLClassNameSuffix() != null
          && nsProvAnnot.queryDSLClassNameSuffix().trim().length() != 0) {
        queryDslimplProv = new ImplementationProvisioning();
        queryDSLProv.setImplementation(queryDslimplProv);
        queryDslimplProv.setClassNameSuffix(nsProvAnnot.queryDSLClassNameSuffix());
      }

      queryDSLProv.setGenerate(true);
    }
    return namespaces;
  }

  private List<NamespaceService> deriveNamespaceSerivces() {

    Set<java.lang.Package> leafPackages = new HashSet<java.lang.Package>();
    for (Class<?> c : metadataClasses) {
      leafPackages.add(c.getPackage());
    }

    List<NamespaceService> results = new ArrayList<NamespaceService>();
    for (java.lang.Package pkg : leafPackages) {
      NamespaceService nsServiceAnnot = pkg.getAnnotation(NamespaceService.class);
      results.add(nsServiceAnnot);
    }

    return results;
  }

  private GlobalProvisioning deriveGlobalProvisioning() {
    GlobalProvisioning globalProvisioning = new GlobalProvisioning();
    globalProvisioning.setPackageName("org.plasma.platform.sdo");
    InterfaceProvisioning intfs = new InterfaceProvisioning();
    globalProvisioning.setInterface(intfs);
    intfs.setPropertyNameStyle(PropertyNameStyle.ENUMS);
    intfs.setEnumSource(EnumSource.DERIVED);

    ImplementationProvisioning impl = new ImplementationProvisioning();
    globalProvisioning.setImplementation(impl);
    impl.setClassNameSuffix("Impl");
    impl.setChildPackageName("impl");

    QueryDSLProvisioning queryDSL = new QueryDSLProvisioning();
    globalProvisioning.setQueryDSL(queryDSL);
    ImplementationProvisioning impl2 = new ImplementationProvisioning();
    queryDSL.setImplementation(impl2);
    impl2.setClassNamePrefix("Q");
    impl2.setChildPackageName("query");
    return globalProvisioning;
  }

  private void constructArtifactAndNamespaceMappings() throws ConfigurationException {
    constructArtifactAndNamespaceMappings(false);
  }

  /**
   * 
   * @param dynamicArtifacts
   *          whether user model artifacts are created dynamically and therefore
   *          not expected in the repository config.
   * @throws ConfigurationException
   */
  private void constructArtifactAndNamespaceMappings(boolean dynamicArtifacts)
      throws ConfigurationException {
    // map declared artifacts
    for (Repository repo : config.getRepositories()) {
      for (Artifact artifact : repo.getArtifacts()) {
        this.artifactMap.put(artifact.getNamespaceUri(), artifact);
      }
    }

    // Default namespace now optional, load dynamically below if not found
    if (config.getSDO().getDefaultNamespace() != null) {
      if (artifactMap.get(config.getSDO().getDefaultNamespace().getArtifact()) == null)
        throw new ConfigurationException(
            "Invalid SDO Namespace - could not find repository artifact based on URI '"
                + config.getSDO().getDefaultNamespace().getArtifact() + "'");
      if (log.isDebugEnabled())
        log.debug("setting default namespace: " + config.getSDO().getDefaultNamespace().getUri());
      NamespaceAdapter defaultNamespace = new NamespaceAdapter(config.getSDO()
          .getDefaultNamespace());
      sdoNamespaceMap.put(config.getSDO().getDefaultNamespace().getUri(), defaultNamespace);
      this.profileNamespace = defaultNamespace;
      this.dataTypesNamespace = defaultNamespace;
    }

    // check declared SDO namespaces against repository artifacts
    for (Namespace namespace : config.getSDO().getNamespaces()) {
      if (artifactMap.get(namespace.getArtifact()) == null && !dynamicArtifacts)
        throw new ConfigurationException(
            "Invalid SDO Namespace - could not find repository artifact based on URI '"
                + namespace.getArtifact() + "'");

      if (this.sdoNamespaceMap.get(namespace.getUri()) != null)
        throw new ConfigurationException("duplicate SDO namespace configuration - "
            + "a namespace with URI '" + namespace.getUri() + "' already exists "
            + "within the configuration");

      // create deflt provisioning based on namespace URI
      if (namespace.getProvisioning() == null) {
        NamespaceProvisioning provisioning = createDefaultProvisioning(namespace.getUri());
        namespace.setProvisioning(provisioning);
      }

      // map namespaces by URI and interface package
      NamespaceAdapter namespaceAdapter = new NamespaceAdapter(namespace);

      this.sdoNamespaceMap.put(namespace.getUri(), namespaceAdapter);
      if (this.sdoNamespaceMap.get(namespace.getProvisioning().getPackageName()) != null)
        throw new ConfigurationException("duplicate SDO namespace configuration - "
            + "a namespace with provisioning package name '"
            + namespace.getProvisioning().getPackageName() + "' already exists "
            + "within the configuration");
      this.sdoNamespaceMap.put(namespace.getProvisioning().getPackageName(), namespaceAdapter);

      for (TypeBinding typeBinding : namespace.getTypeBindings()) {
        namespaceAdapter.addTypeBinding(typeBinding);
      }
    }

    // create default SDO namespace configs based on artifact where
    // config does not exist
    Iterator<String> iter = artifactMap.keySet().iterator();
    while (iter.hasNext()) {
      String artifactUri = iter.next();
      Artifact artifact = artifactMap.get(artifactUri);
      if (this.sdoNamespaceMap.get(artifact.getNamespaceUri()) == null) {
        Namespace namespace = new Namespace();
        namespace.setArtifact(artifact.getNamespaceUri());
        namespace.setUri(artifact.getNamespaceUri()); // SDO Namespace URI same
                                                      // as artifact URI
        NamespaceProvisioning provisioning = createDefaultProvisioning(artifact.getNamespaceUri());
        namespace.setProvisioning(provisioning);
        NamespaceAdapter namespaceAdapter = new NamespaceAdapter(namespace);
        this.sdoNamespaceMap.put(namespace.getUri(), namespaceAdapter);
      }
    }
  }

  /**
   * Dynamically instantiates appropriate profile artifacts based on any user
   * configured artifacts, which allows the profile artifacts to be omitted from
   * the config. This is done by "peeking" into the artifact stream using a STaX
   * based reader which finds the SDO Profile version. throws
   * ProfileVersionDetectionException if more than 1 profile version is detected
   * across all artifacts.
   */
  private void constructKnownArtifacts() throws ProfileVersionDetectionException {

    // Determine the profile version(s) required by the defined user artifacts
    Map<String, ProfileArtifactAdapter> versions = new HashMap<String, ProfileArtifactAdapter>();

    for (Repository repo : config.getRepositories()) {
      for (Artifact artifact : repo.getArtifacts()) {

        if (ProfileConfig.getInstance().findArtifactByUri(artifact.getNamespaceUri()) != null)
          continue; // validated elsewhere

        InputStream stream = PlasmaRuntime.class.getResourceAsStream(artifact.getUrn());
        if (stream == null)
          stream = PlasmaRuntime.class.getClassLoader().getResourceAsStream(artifact.getUrn());
        if (stream == null) {
          if (this.configURI == null) {
            throw new PlasmaRuntimeException("could not find artifact resource '"
                + artifact.getUrn() + "' on the current classpath");
          } else { // look for artifact as relative URI
            URI artifactURI = null;
            try {
              artifactURI = this.configURI.resolve(artifact.getUrn());
              stream = artifactURI.toURL().openStream();
            } catch (IllegalArgumentException e) {
              throw new PlasmaRuntimeException(e);
            } catch (MalformedURLException e) {
              throw new PlasmaRuntimeException(e);
            } catch (IOException e) {
              throw new PlasmaRuntimeException(e);
            }
            if (stream == null)
              throw new PlasmaRuntimeException(
                  "could not find artifact resource '"
                      + artifact.getUrn()
                      + "' on the current classpath or as a relative URI based on the configuration URI, "
                      + this.configURI.toString());
          }
        }
        ProfileVersionFinder finder = new ProfileVersionFinder();
        ProfileArtifactAdapter version = finder.getVersion(artifact.getUrn(), stream);
        if (log.isDebugEnabled())
          log.debug("artifact: (" + artifact.getUrn() + ") " + artifact.getNamespaceUri()
              + " - detected as Plasma SDO Profile version (" + version.getUrn() + ") "
              + version.getNamespaceUri());
        versions.put(version.getNamespaceUri(), version);
      }
    }

    // throw an error if more than one profile used across artifacts
    if (versions.size() > 1) {
      StringBuilder buf = new StringBuilder();
      ProfileArtifact[] versionArray = new ProfileArtifact[versions.values().size()];
      versions.values().toArray(versionArray);
      for (int i = 0; i < versionArray.length; i++) {
        if (i > 0)
          buf.append(", ");
        buf.append(versionArray[i].getNamespaceUri());
      }
      throw new ProfileVersionDetectionException("multiple profile versions detected("
          + buf.toString() + ") for configured set of UML artifacts '"
          + "' - all UML artifacts are required to be annotated with the same version "
          + "of the PlasmaSDO UML Profile");
    }

    // If Plasma SDO Papyrus profile 1.0 is required and not already a defined
    // artifact,
    // load it and the separate data types modules
    if (this.profileNamespace == null) {
      ProfileArtifactAdapter papyrusProfile = ProfileConfig.getInstance().findArtifactByUrn(
          ProfileURN.PLASMA_SDO_PROFILE_V_1_1_UML);
      if (versions.get(papyrusProfile.getNamespaceUri()) != null) {
        if (this.artifactMap.get(papyrusProfile.getNamespaceUri()) == null) {
          NamespaceAdapter namespaceAdapter = loadSystemArtifact(papyrusProfile.getUrn().value(),
              papyrusProfile.getNamespaceUri());
          if (namespaceAdapter != null) {
            this.profileNamespace = namespaceAdapter;
          } else
            log.debug("no resource found for, " + papyrusProfile.getUrn().value());
        }
        ProfileArtifactAdapter papyrusProfileDatatypes = ProfileConfig.getInstance()
            .findArtifactByUrn(ProfileURN.PLASMA_SDO_DATA_TYPES_V_1_1_UML);
        if (this.artifactMap.get(papyrusProfileDatatypes.getNamespaceUri()) == null) {
          NamespaceAdapter namespaceAdapter = loadSystemArtifact(papyrusProfileDatatypes.getUrn()
              .value(), papyrusProfileDatatypes.getNamespaceUri());
          if (namespaceAdapter != null) {
            this.dataTypesNamespace = namespaceAdapter;
          } else
            log.debug("no resource found for, " + papyrusProfileDatatypes.getUrn().value());
        }
      }
    }

    // finally if the case where no artifacts are configured, load the MD
    // profile by default
    // If Plasma SDO MagicDraw profile 1.0 is required and not already a defined
    // artifact
    // load it
    if (this.profileNamespace == null) {
      ProfileArtifactAdapter magicdrawProfile = ProfileConfig.getInstance().findArtifactByUrn(
          ProfileURN.PLASMA_SDO_PROFILE_V_1_1_MDXML);
      NamespaceAdapter namespaceAdapter = loadSystemArtifact(magicdrawProfile.getUrn().value(),
          magicdrawProfile.getNamespaceUri());
      if (namespaceAdapter != null) {
        this.profileNamespace = namespaceAdapter;
        this.dataTypesNamespace = namespaceAdapter;
      } else
        log.debug("no resource found for, " + magicdrawProfile.getUrn().value());
    }

  }

  /**
   * Construct SDO namespace where the Artifact namespace is the SAME as the SDO
   * namespace. Only the actual artifact itself should know about the actual
   * resource name. The "artifact" member here is the namespace URI of the
   * artifact, NOT the URN.
   * 
   * @param resource
   * @param uri
   * @return the new namespace or null if the resource does not exist
   */
  private NamespaceAdapter loadSystemArtifact(String resource, String uri) {
    InputStream stream = PlasmaRuntime.class.getClassLoader().getResourceAsStream(resource);
    if (stream == null)
      return null;
    if (log.isDebugEnabled())
      log.debug("loading known UML/XMI artifact, " + resource + " (" + uri + ")");
    Fuml.load(new ResourceArtifact(resource, uri, stream));

    Namespace namespace = new Namespace();
    namespace.setArtifact(uri);
    namespace.setUri(uri);
    namespace.setProvisioning(null);
    NamespaceAdapter namespaceAdapter = new NamespaceAdapter(namespace);
    namespaceAdapter.setSystemArtifact(true);
    this.sdoNamespaceMap.put(namespace.getUri(), namespaceAdapter);
    return namespaceAdapter;
  }

  private NamespaceAdapter dataTypesNamespace;

  public NamespaceAdapter getSDODataTypesNamespace() {
    return this.dataTypesNamespace;
  }

  private NamespaceAdapter profileNamespace;

  public NamespaceAdapter getSDOProfileNamespace() {
    return this.profileNamespace;
  }

  private void constructDataAccessServiceMappings() throws ConfigurationException {
    for (DataAccessService daconfig : config.getSDO().getDataAccessServices()) {

      for (DataAccessProvider provider : daconfig.getDataAccessProviders()) {
        if (defaultProviderName == null)
          defaultProviderName = provider.getName();

        this.dataAccessProviderMap.put(provider.getName(), provider);
        Map<String, NamespaceProvisioning> provMap = dataAccessProviderProvisioningMap.get(provider
            .getName());
        if (provMap == null) {
          provMap = new HashMap<String, NamespaceProvisioning>();
          dataAccessProviderProvisioningMap.put(provider.getName(), provMap);
        }
        Map<String, NamespaceLink> linkMap = dataStoreNamespaceLinkMap.get(daconfig
            .getDataStoreType());
        if (linkMap == null) {
          linkMap = new HashMap<String, NamespaceLink>();
          dataStoreNamespaceLinkMap.put(daconfig.getDataStoreType(), linkMap);
        }
        for (NamespaceLink namespaceLink : provider.getNamespaceLinks()) {
          if (namespaceLink.getUri() == null)
            throw new ConfigurationException(
                "expected namespace URI for Data Access Service configuration '"
                    + provider.getName().toString() + "'");
          linkMap.put(namespaceLink.getUri(), namespaceLink);
          NamespaceAdapter adapter = sdoNamespaceMap.get(namespaceLink.getUri());
          if (adapter == null)
            throw new ConfigurationException(
                "Invalid SDO Namespace - could not find SDO namespace based on namespace URI '"
                    + namespaceLink.getUri() + "' for Data Access Service configuration '"
                    + provider.getName().toString() + "'");
          if (adapter.getNamespace().getProvisioning() != null
              && adapter.getNamespace().getProvisioning().getPackageName() != null
              && namespaceLink.getProvisioning() != null) {
            if (adapter.getNamespace().getProvisioning().getPackageName()
                .equals(namespaceLink.getProvisioning().getPackageName())) {
              throw new ConfigurationException("Duplicate provisioning package name ("
                  + namespaceLink.getProvisioning().getPackageName() + ") for SDO Namespace '"
                  + namespaceLink.getUri() + "' and Data Access Service configuration '"
                  + provider.getName().toString() + "'");
            }
          }
          if (namespaceLink.getProvisioning() != null)
            provMap.put(namespaceLink.getUri(), namespaceLink.getProvisioning());
        }

        Map<String, Property> propertyMap = this.dataAccessProviderPropertyMap.get(provider
            .getName());
        if (propertyMap == null) {
          propertyMap = new HashMap<String, Property>();
          this.dataAccessProviderPropertyMap.put(provider.getName(), propertyMap);
        }
        for (Property property : provider.getProperties()) {
          propertyMap.put(property.getName(), property);
        }
      }
    }
  }

  private void validateNamespaceToDataStoreMappings() {
    // check SDO user namespaces against datastore mappings
    // ensure every user namespace is mapped to one data store provider
    for (Namespace namespace : config.getSDO().getNamespaces()) {
      int count = 0;
      for (Map<String, NamespaceLink> map : this.dataStoreNamespaceLinkMap.values()) {
        NamespaceLink link = map.get(namespace.getUri());
        if (link != null)
          count++;
      }
      if (count == 0)
        throw new ConfigurationException(
            "SDO namespace '"
                + namespace.getUri()
                + "' not mapped to any "
                + "data access provider - every namespace should ba mapped/linked to one provider using a "
                + NamespaceLink.class.getSimpleName() + " within the provider configuration");
      if (count > 1)
        throw new ConfigurationException(
            "SDO namespace '"
                + namespace.getUri()
                + "' mapped to multiple "
                + "data access providers - every namespace should ba mapped/linked to a single provider using a "
                + NamespaceLink.class.getSimpleName() + " within the provider configuration");
    }
  }

  public String getConfigFileName() {
    return configFileNameOrURIString;
  }

  public long getConfigFileLastModifiedDate() {
    return configFileLastModifiedDate;
  }

  public URI getConfigURI() {
    return configURI;
  }

  public DataAccessProviderName getDefaultProviderName() {
    return defaultProviderName;
  }

  private NamespaceProvisioning createDefaultProvisioning(String uri) {
    NamespaceProvisioning provisioning = new NamespaceProvisioning();
    String[] tokens = ConfigUtils.toPackageTokens(uri);
    StringBuilder buf = new StringBuilder();
    for (int i = 0; i < tokens.length; i++) {
      if (i > 0)
        buf.append(".");
      buf.append(tokens[i]);
    }
    provisioning.setPackageName(buf.toString());
    return provisioning;
  }

  private InputStream findConfigStream(String configFileURI) {
    if (this.configURI == null)
      try {
        this.configURI = new URI(configFileURI);
      } catch (URISyntaxException e) {
      }
    InputStream stream = null;
    if (this.configURI == null || this.configURI.getScheme() == null) {
      stream = PlasmaRuntime.class.getResourceAsStream(configFileURI);
      if (stream == null)
        stream = PlasmaRuntime.class.getClassLoader().getResourceAsStream(configFileURI);
    } else {
      try {
        stream = this.configURI.toURL().openStream();
      } catch (MalformedURLException e) {
        throw new ConfigurationException(e);
      } catch (IOException e) {
        throw new ConfigurationException(e);
      }
      if (stream == null)
        log.debug("could not open stream for configuration file resource '" + configFileURI + "'");
    }
    return stream;
  }

  @SuppressWarnings("unchecked")
  private RuntimeConfiguration unmarshalConfig(String configFileURI, InputStream stream,
      PlasmaRuntimeDataBinding binding) {
    try {
      RuntimeConfiguration result = (RuntimeConfiguration) binding.validate(stream);
      URL url = PlasmaRuntime.class.getResource(configFileURI);
      if (url == null)
        url = PlasmaRuntime.class.getClassLoader().getResource(configFileURI);
      if (url != null) {
        File urlFile = new File(url.getFile());
        if (urlFile.exists())
          this.configFileLastModifiedDate = urlFile.lastModified();
      }

      return result;
    } catch (UnmarshalException e) {
      throw new ConfigurationException(e);
    } catch (JAXBException e) {
      throw new ConfigurationException(e);
    }
  }

  public void marshal(OutputStream stream) {
    try {
      PlasmaRuntimeDataBinding configBinding = new PlasmaRuntimeDataBinding(
          new DefaultValidationEventHandler());
      configBinding.marshal(this.config, stream);
    } catch (JAXBException e1) {
      throw new ConfigurationException(e1);
    } catch (SAXException e1) {
      throw new ConfigurationException(e1);
    }
  }

  public static PlasmaRuntime getInstance() throws PlasmaRuntimeException {
    if (instance == null)
      initializeInstance();
    return instance;
  }

  private static synchronized void initializeInstance() throws PlasmaRuntimeException {
    if (instance == null)
      instance = new PlasmaRuntime();
  }

  public RuntimeConfiguration getConfig() {
    return config;
  }

  public SDO getSDO() {

    return config.getSDO();
  }

  public Namespace getSDONamespaceByURI(String uri) {
    NamespaceAdapter result = sdoNamespaceMap.get(uri);
    if (result != null)
      return result.getNamespace();
    else
      throw new ConfigurationException("no configured SDO namespace found for URI '" + uri + "'");
  }

  public boolean hasSDONamespace(String uri) {
    NamespaceAdapter result = sdoNamespaceMap.get(uri);
    if (result != null)
      return true;
    return false;
  }

  public Namespace getSDONamespaceByInterfacePackage(String packageName) {
    NamespaceAdapter result = sdoNamespaceMap.get(packageName);
    if (result != null)
      return result.getNamespace();
    else
      throw new ConfigurationException(
          "no configured SDO namespace found for interface package name '" + packageName + "'");
  }

  public int getSDONamespaceCount() {
    int count = 0;
    for (NamespaceAdapter adapter : sdoNamespaceMap.values()) {
      if (!adapter.isSystemArtifact())
        count++;
    }
    return count;
  }

  public TypeBindingAdapter findTypeBinding(String uri, String typeName) {
    NamespaceAdapter namespaceAdapter = sdoNamespaceMap.get(uri);
    if (namespaceAdapter != null) {
      TypeBindingAdapter resultAdapter = namespaceAdapter.findTypeBinding(typeName);
      if (resultAdapter != null)
        return resultAdapter;
      else
        return null;
    } else
      throw new ConfigurationException("no configured SDO namespace found for URI '" + uri + "'");
  }

  public void remapTypeBinding(String uri, TypeBindingAdapter typeBinding) {
    NamespaceAdapter namespaceAdapter = sdoNamespaceMap.get(uri);
    if (namespaceAdapter != null) {
      namespaceAdapter.remapTypeBinding(typeBinding);
    } else
      throw new ConfigurationException("no configured SDO namespace found for URI '" + uri + "'");
  }

  public PropertyBindingAdapter findPropertyBinding(String uri, String typeName, String propertyName) {
    NamespaceAdapter namespaceAdapter = sdoNamespaceMap.get(uri);
    if (namespaceAdapter != null) {
      TypeBindingAdapter typeBinding = namespaceAdapter.findTypeBinding(typeName);
      if (typeBinding != null) {
        PropertyBindingAdapter resultAdapter = typeBinding.findPropertyBinding(propertyName);
        if (resultAdapter != null)
          return resultAdapter;
        else
          return null;
      } else
        return null;
    } else
      throw new ConfigurationException("no configured SDO namespace found for URI '" + uri + "'");
  }

  /**
   * Creates a dynamic SDO namespace configuration
   * 
   * @param uri
   *          the dynamic namespace URI
   * @param supplierUri
   *          the original static URI which supplied the derived dynamic
   *          namespace
   */
  public void addDynamicSDONamespace(String uri, String supplierUri) {
    Namespace namespace = new Namespace();
    namespace.setArtifact(uri); // assumed artifact is created in-line
                                // dynamically
    namespace.setUri(uri);
    if (this.sdoNamespaceMap.get(namespace.getUri()) != null)
      throw new ConfigurationException("duplicate SDO namespace configuration - "
          + "a namespace with URI '" + namespace.getUri() + "' already exists "
          + "within the configuration");

    this.lock.writeLock().lock();
    try {
      this.sdoNamespaceMap.put(uri, new NamespaceAdapter(namespace));

      // find the supplier URI where mapped and add a mapping to the
      // new dynamic URI
      for (Map<String, NamespaceProvisioning> map : dataAccessProviderProvisioningMap.values()) {
        Iterator<String> iter = map.keySet().iterator();
        NamespaceProvisioning provisioning = null;
        while (iter.hasNext()) {
          String key = iter.next();
          if (key.equals(supplierUri)) {
            provisioning = map.get(key);
          }
        }
        if (provisioning != null)
          map.put(uri, provisioning);
      }
    } finally {
      this.lock.writeLock().unlock();
    }
  }

  public Namespace addDynamicSDONamespace(String uri, String artifact,
      NamespaceProvisioning provisioning) {
    Namespace namespace = new Namespace();
    namespace.setArtifact(artifact);
    namespace.setUri(uri);
    namespace.setProvisioning(provisioning);
    if (this.sdoNamespaceMap.get(namespace.getUri()) != null)
      throw new ConfigurationException("duplicate SDO namespace configuration - "
          + "a namespace with URI '" + namespace.getUri() + "' already exists "
          + "within the configuration");
    this.lock.writeLock().lock();
    try {
      this.sdoNamespaceMap.put(uri, new NamespaceAdapter(namespace));
    } finally {
      this.lock.writeLock().unlock();
    }
    return namespace;
  }

  public DataAccessProvider findDataAccessProvider(DataAccessProviderName providerName) {
    DataAccessProvider result = this.dataAccessProviderMap.get(providerName);
    return result;
  }

  public DataAccessProvider getDataAccessProvider(DataAccessProviderName providerName) {
    DataAccessProvider result = this.dataAccessProviderMap.get(providerName);
    if (result != null)
      return result;
    else
      throw new ConfigurationException("no data access provider configuration found for '"
          + providerName.value() + "'");
  }

  /**
   * Returns the property based on the given name for the given data access
   * provider, or null if not exists.
   * 
   * @param provider
   *          the data access provider
   * @param name
   *          the property name
   * @return the property based on the given name for the given data access
   *         provider, or null if not exists.
   */
  public Property findProviderProperty(DataAccessProvider provider, String name) {
    for (org.plasma.runtime.Property property : provider.getProperties()) {
      if (property.getName().equals(name))
        return property;
    }
    return null;
  }

  public RDBMSVendorName getRDBMSProviderVendor(DataAccessProviderName providerName) {
    DataAccessProvider provider = this.dataAccessProviderMap.get(providerName);
    if (provider == null)
      throw new ConfigurationException("no data access provider configuration found for '"
          + providerName.value() + "'");
    RDBMSVendorName vendor = null;
    switch (providerName) {
    case JDBC:
    case JDO:
    case JPA:
      Property vendorProp = findProviderPropertyByName(provider, ConfigurationConstants.JDBC_VENDOR);
      if (vendorProp != null) {
        vendor = getVendorName(provider, vendorProp);
      } else {
        log.debug("no vendor property (" + ConfigurationConstants.JDBC_VENDOR
            + ") found for provider '" + providerName.value()
            + "'- searching for vendor based on JDBC driver name");
        Property driverProp = findProviderPropertyByName(provider,
            ConfigurationConstants.JDBC_DRIVER_NAME);
        if (driverProp != null) {
          vendor = matchVendorName(driverProp);
        }
        if (vendor == null)
          throw new ConfigurationException("could not determine RDBMS vendor for provider '"
              + providerName.value() + "' - no vendor property ("
              + ConfigurationConstants.JDBC_VENDOR + ")" + " or driver ("
              + ConfigurationConstants.JDBC_DRIVER_NAME + ") properties found");
      }
      break;
    default:
      throw new ConfigurationException("could not determine RDBMS vendor for non-RDBMS provider '"
          + providerName.value() + "'");
    }
    return vendor;
  }

  private RDBMSVendorName getVendorName(DataAccessProvider provider, Property prop) {
    try {
      return RDBMSVendorName.valueOf(prop.getValue());
    } catch (IllegalArgumentException e) {
      StringBuilder buf = new StringBuilder();
      for (int i = 0; i < RDBMSVendorName.values().length; i++) {
        if (i > 0)
          buf.append(", ");
        buf.append(RDBMSVendorName.values()[i].name());
      }
      throw new ConfigurationException("could not determine RDBMS vendor for provider '"
          + provider.getName().value() + "' based on property value '" + prop.getValue()
          + "' - expected one of [" + buf.toString() + "]");
    }
  }

  private RDBMSVendorName matchVendorName(Property prop) {
    for (RDBMSVendorName vendor : RDBMSVendorName.values()) {
      if (prop.getValue().toLowerCase().contains(vendor.name().toLowerCase()))
        return vendor;
    }
    return null;
  }

  private Property findProviderPropertyContainingValue(DataAccessProvider provider, String value) {
    for (Property prop : provider.getProperties()) {
      if (prop.getValue() != null && prop.getValue().toLowerCase().contains(value))
        return prop;
    }
    return null;
  }

  private Property findProviderPropertyByName(DataAccessProvider provider, String name) {
    Map<String, Property> propertyMap = this.dataAccessProviderPropertyMap.get(provider.getName());
    if (propertyMap != null)
      return propertyMap.get(name);
    else
      return null;
  }

  public NamespaceProvisioning getProvisioningByNamespaceURI(DataAccessProviderName providerName,
      String uri) {
    Map<String, NamespaceProvisioning> providerProvisionMap = this.dataAccessProviderProvisioningMap
        .get(providerName);
    if (providerProvisionMap != null) {
      NamespaceProvisioning result = providerProvisionMap.get(uri);
      if (result != null)
        return result;
    }
    throw new NonExistantNamespaceException("no configured '" + providerName.value()
        + "' provider namespace found for URI '" + uri + "'");
  }

  public boolean hasNamespace(DataStoreType dataStore) {
    return this.dataStoreNamespaceLinkMap.get(dataStore) != null;
  }

  public Repository getRepository() {
    return config.getRepositories().get(0);
  }

  public List<Repository> getRepositories() {
    return config.getRepositories();
  }

  public String getSDOInterfaceClassName(String uri, String name) {
    String result = name;
    @SuppressWarnings("unused")
    Namespace namespace = getSDONamespaceByURI(uri); // validate URI for now
    return result;
  }

  public String getSDOInterfacePackageName(String uri) {
    Namespace sdoNamespace = this.getSDONamespaceByURI(uri);
    String packageName = sdoNamespace.getProvisioning().getPackageName();
    return packageName;
  }

  public InterfaceProvisioning getSDOInterfaceProvisioning(String uri) {
    Namespace sdoNamespace = this.getSDONamespaceByURI(uri);
    InterfaceProvisioning prov = sdoNamespace.getProvisioning().getInterface();
    return prov;
  }

  public String getSDOImplementationClassName(String uri, String name) {
    String result = name;

    @SuppressWarnings("unused")
    Namespace namespace = getSDONamespaceByURI(uri); // validate URI for now
    if (namespace != null) {
      String prefix = null;
      String suffix = null;
      if (this.getSDO() != null && this.getSDO().getGlobalProvisioning() != null
          && this.getSDO().getGlobalProvisioning().getImplementation() != null) {
        ImplementationProvisioning prov = this.getSDO().getGlobalProvisioning().getImplementation();
        prefix = prov.getClassNamePrefix();
        suffix = prov.getClassNameSuffix();
      }
      if (prefix != null && prefix.trim().length() > 0)
        result = prefix + result;
      if (suffix != null && suffix.trim().length() > 0)
        result = result + suffix;
      return result;
    }
    return null;
  }

  public String getSDOImplementationPackageName(String uri) {
    Namespace sdoNamespace = this.getSDONamespaceByURI(uri);
    if (sdoNamespace != null && sdoNamespace.getProvisioning() != null) {
      String packageName = sdoNamespace.getProvisioning().getPackageName();
      String subpackage = null;
      if (this.getSDO() != null && this.getSDO().getGlobalProvisioning() != null
          && this.getSDO().getGlobalProvisioning().getImplementation() != null) {
        ImplementationProvisioning prov = this.getSDO().getGlobalProvisioning().getImplementation();
        subpackage = prov.getChildPackageName();
      }
      if (subpackage != null && subpackage.trim().length() > 0)
        packageName = packageName + "." + subpackage;
      return packageName;
    }
    return null;
  }

  public boolean generateQueryDSL(String uri, String name) {
    @SuppressWarnings("unused")
    Namespace namespace = getSDONamespaceByURI(uri); // validate URI for now
    if (this.getSDO() != null && this.getSDO().getGlobalProvisioning() != null
        && this.getSDO().getGlobalProvisioning().getQueryDSL() != null) {

      // turned off globally
      QueryDSLProvisioning queryDsl = this.getSDO().getGlobalProvisioning().getQueryDSL();
      if (!queryDsl.isGenerate())
        return false;

      NamespaceProvisioning prov = namespace.getProvisioning();
      if (prov != null && prov.getQueryDSL() != null)
        if (!prov.getQueryDSL().isGenerate())
          return false;
    }
    return true;
  }

  public String getQueryDSLImplementationClassName(String uri, String name) {
    String result = name;

    @SuppressWarnings("unused")
    Namespace namespace = getSDONamespaceByURI(uri); // validate URI for now
    if (namespace != null) {
      String prefix = null;
      String suffix = null;
      if (this.getSDO() != null && this.getSDO().getGlobalProvisioning() != null
          && this.getSDO().getGlobalProvisioning().getQueryDSL() != null) {
        QueryDSLProvisioning queryDsl = this.getSDO().getGlobalProvisioning().getQueryDSL();
        if (queryDsl.getImplementation() != null) {
          ImplementationProvisioning prov = queryDsl.getImplementation();
          prefix = prov.getClassNamePrefix();
          suffix = prov.getClassNameSuffix();
        }
      }
      if (prefix != null && prefix.trim().length() > 0)
        result = prefix + result;
      if (suffix != null && suffix.trim().length() > 0)
        result = result + suffix;
      return result;
    }
    return null;
  }

  public String getServiceImplementationClassName(DataAccessProviderName providerName, String uri,
      String name) {
    String result = name;
    DataAccessProvider config = this.getDataAccessProvider(providerName);
    @SuppressWarnings("unused")
    Namespace namespace = getSDONamespaceByURI(uri); // validate URI for now
    return result;
  }

  public String getServiceImplementationPackageName(DataAccessProviderName serviceName, String uri) {

    NamespaceProvisioning provisioning = getProvisioningByNamespaceURI(serviceName, uri);
    @SuppressWarnings("unused")
    Namespace namespace = getSDONamespaceByURI(uri); // validate URI for now
    return provisioning.getPackageName();
  }

}
