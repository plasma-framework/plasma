/**
 *         PlasmaSDO™ License
 * 
 * This is a community release of PlasmaSDO™, a dual-license 
 * Service Data Object (SDO) 2.1 implementation. 
 * This particular copy of the software is released under the 
 * version 2 of the GNU General Public License. PlasmaSDO™ was developed by 
 * TerraMeta Software, Inc.
 * 
 * Copyright (c) 2013, TerraMeta Software, Inc. All rights reserved.
 * 
 * General License information can be found below.
 * 
 * This distribution may include materials developed by third
 * parties. For license and attribution notices for these
 * materials, please refer to the documentation that accompanies
 * this distribution (see the "Licenses for Third-Party Components"
 * appendix) or view the online documentation at 
 * <http://plasma-sdo.org/licenses/>.
 *  
 */
package org.plasma.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.Fuml;
import org.modeldriven.fuml.io.ResourceArtifact;
import org.plasma.common.bind.DefaultValidationEventHandler;
import org.plasma.common.env.EnvConstants;
import org.plasma.common.env.EnvProperties;
import org.plasma.common.exception.PlasmaRuntimeException;
import org.plasma.config.adapter.NamespaceAdapter;
import org.plasma.config.adapter.PropertyBindingAdapter;
import org.plasma.config.adapter.TypeBindingAdapter;
import org.plasma.profile.ProfileArtifact;
import org.plasma.profile.ProfileConfig;
import org.plasma.profile.ProfileURN;
import org.plasma.profile.adapter.ProfileArtifactAdapter;
import org.xml.sax.SAXException;


public class PlasmaConfig {

    private static Log log = LogFactory.getLog(PlasmaConfig.class);
    private static volatile PlasmaConfig instance = null;
	private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
   
    private static final String defaultConfigFileName = "plasma-config.xml";  
    private String configFileNameOrURIString;
    private URI configURI;
    private long configFileLastModifiedDate = System.currentTimeMillis();
    private PlasmaConfiguration config;
    private Map<String, Artifact> artifactMap = new HashMap<String, Artifact>();
    private Map<String, NamespaceAdapter> sdoNamespaceMap = new HashMap<String, NamespaceAdapter>();
    private DataAccessProviderName defaultProviderName;
    
    /** maps data store types to maps of namespace links */
    private Map<DataStoreType, Map<String, NamespaceLink>> dataStoreNamespaceLinkMap 
        = new HashMap<DataStoreType, Map<String, NamespaceLink>>();

    /** maps provider names to provider instances */
    private Map<DataAccessProviderName, DataAccessProvider> dataAccessProviderMap 
        = new HashMap<DataAccessProviderName, DataAccessProvider>();

    /** maps provider names to maps of namespace URI to provisioning structures */
    private Map<DataAccessProviderName, Map<String, NamespaceProvisioning>> dataAccessProviderProvisioningMap 
        = new HashMap<DataAccessProviderName,  Map<String, NamespaceProvisioning>>();    

    /** maps provider names to maps of provider properties */
    private Map<DataAccessProviderName, Map<String, Property>> dataAccessProviderPropertyMap 
        = new HashMap<DataAccessProviderName,  Map<String, Property>>();    
    
    private PlasmaConfig()
    {
        log.debug("initializing...");
        try {
            
            this.configFileNameOrURIString = EnvProperties.instance().getProperty(
                    EnvConstants.PROPERTY_NAME_ENV_CONFIG);
            
            if (this.configFileNameOrURIString == null)
            	this.configFileNameOrURIString = defaultConfigFileName;
            
            PlasmaConfigDataBinding configBinding = new PlasmaConfigDataBinding(
	        		new PlasmaConfigValidationEventHandler());
	        
            config = unmarshalConfig(this.configFileNameOrURIString, configBinding);
                        
            constructArtifactAndNamespaceMappings();
            constructKnownArtifacts();
            constructDataAccessServiceMappings();
            validateNamespaceToDataStoreMappings();
        }
        catch (SAXException e) {
            throw new ConfigurationException(e);
        }
        catch (JAXBException e) {
            throw new ConfigurationException(e);
        }
    }
    
    private void constructArtifactAndNamespaceMappings() throws ConfigurationException {
        // map declared artifacts
        for (Artifact artifact : config.getRepository().getArtifacts()) {
            this.artifactMap.put(artifact.getNamespaceUri(), artifact);
        }
        
        // Default namespace now optional, load dynamically below if not found
        if (config.getSDO().getDefaultNamespace() != null) {
            if (artifactMap.get(config.getSDO().getDefaultNamespace().getArtifact()) == null)
                throw new ConfigurationException("Invalid SDO Namespace - could not find repository artifact based on URI '"
                    + config.getSDO().getDefaultNamespace().getArtifact() + "'");
            if (log.isDebugEnabled())
    	    	log.debug("setting default namespace: " + config.getSDO().getDefaultNamespace().getUri());
            NamespaceAdapter defaultNamespace = new NamespaceAdapter(config.getSDO().getDefaultNamespace()); 
            sdoNamespaceMap.put(config.getSDO().getDefaultNamespace().getUri(), 
            		defaultNamespace);
            this.profileNamespace = defaultNamespace;
            this.dataTypesNamespace = defaultNamespace;                        
        }
        
        // check declared SDO namespaces against repository artifacts
        for (Namespace namespace : config.getSDO().getNamespaces()) {  
            if (artifactMap.get(namespace.getArtifact()) == null)
                throw new ConfigurationException("Invalid SDO Namespace - could not find repository artifact based on URI '"
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
            
            //map namespaces by URI and interface package
            NamespaceAdapter namespaceAdapter = new NamespaceAdapter(namespace);
            
            this.sdoNamespaceMap.put(namespace.getUri(),  namespaceAdapter);
            if (this.sdoNamespaceMap.get(namespace.getProvisioning().getPackageName()) != null)
    			throw new ConfigurationException("duplicate SDO namespace configuration - "
    					+ "a namespace with provisioning package name '" + namespace.getProvisioning().getPackageName() + "' already exists "
    					+ "within the configuration");
            this.sdoNamespaceMap.put(namespace.getProvisioning().getPackageName(),  namespaceAdapter);
            
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
            	namespace.setUri(artifact.getNamespaceUri()); // SDO Namespace URI same as artifact URI 
            	NamespaceProvisioning provisioning = createDefaultProvisioning(artifact.getNamespaceUri());
            	namespace.setProvisioning(provisioning);
                NamespaceAdapter namespaceAdapter = new NamespaceAdapter(namespace);                    
                this.sdoNamespaceMap.put(namespace.getUri(),  namespaceAdapter);
            }
        }    	
    }   
    
    /**
     * Dynamically instantiates appropriate profile artifacts based on any 
     * user configured artifacts, which allows the profile artifacts to be omitted from
     * the config. This is done by "peeking" into the artifact stream
     * using a STaX based reader which finds the SDO Profile version. 
     * throws ProfileVersionDetectionException if more than 1 profile version is detected across
     * all artifacts. 
     */
    private void constructKnownArtifacts() throws ProfileVersionDetectionException {
    	
    	// Determine the profile version(s) required by the defined user artifacts 
    	Map<String, ProfileArtifactAdapter> versions = new HashMap<String, ProfileArtifactAdapter>();
        for (Artifact artifact : config.getRepository().getArtifacts()) {
        	
        	if (ProfileConfig.getInstance().findArtifactByUri(artifact.getNamespaceUri()) != null)
        		continue; // validated elsewhere
        	
            InputStream stream = PlasmaConfig.class.getResourceAsStream(artifact.getUrn());
            if (stream == null)
                stream = PlasmaConfig.class.getClassLoader().getResourceAsStream(artifact.getUrn());
            if (stream == null) {
            	if (this.configURI == null) {
                    throw new PlasmaRuntimeException("could not find artifact resource '" 
                        + artifact.getUrn() 
                        + "' on the current classpath");   
            	}
            	else { // look for artifact as relative URI
            		URI artifactURI = null;
            		try {
            		    artifactURI = this.configURI.resolve(artifact.getUrn());
						stream = artifactURI.toURL().openStream();
            		}
            		catch (IllegalArgumentException e) {
            			throw new PlasmaRuntimeException(e);
					} catch (MalformedURLException e) {
						throw new PlasmaRuntimeException(e);
					} catch (IOException e) {
						throw new PlasmaRuntimeException(e);
					}
            		if (stream == null)
                        throw new PlasmaRuntimeException("could not find artifact resource '" 
                            + artifact.getUrn() 
                            + "' on the current classpath or as a relative URI based on the configuration URI, "
                            + this.configURI.toString());   
           	    }
            }            
            
            ProfileVersionFinder finder = new ProfileVersionFinder();
            ProfileArtifactAdapter version = finder.getVersion(artifact.getUrn(), stream);
            if (log.isDebugEnabled())
                log.debug("artifact: (" + artifact.getUrn() + ") " + artifact.getNamespaceUri() 
            	    + " - detected as Plasma SDO Profile version (" + version.getUrn() + ") " + version.getNamespaceUri());
            versions.put(version.getNamespaceUri(), version);
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
			        + buf.toString()+ ") for configured set of UML artifacts '" 
			        + "' - all UML artifacts are required to be annotated with the same version "
			        + "of the PlasmaSDO UML Profile");
        }
        
        // If Plasma SDO Papyrus profile 1.0 is required and not already a defined artifact,
        // load it and the separate data types modules
        if (this.profileNamespace == null) {
        	ProfileArtifactAdapter papyrusProfile = ProfileConfig.getInstance().findArtifactByUrn(ProfileURN.PLASMA_SDO___PROFILE___V_1___1___UML);
	        if (versions.get(papyrusProfile.getNamespaceUri()) != null) {
	        	if (this.artifactMap.get(papyrusProfile.getNamespaceUri()) == null) {
		    		NamespaceAdapter namespaceAdapter = loadKnownArtifact(
		    			papyrusProfile.getUrn().value(),
		    			papyrusProfile.getNamespaceUri());
		    		if (namespaceAdapter != null) {
			    	    this.profileNamespace = namespaceAdapter;  
		    		}
		    		else
		    			log.debug("no resource found for, " + papyrusProfile.getUrn().value());
	            }
	        	ProfileArtifactAdapter papyrusProfileDatatypes = ProfileConfig.getInstance().findArtifactByUrn(ProfileURN.PLASMA_SDO_DATA_TYPES___V_1___1___UML);		    
	        	if (this.artifactMap.get(papyrusProfileDatatypes.getNamespaceUri()) == null) {
	        		NamespaceAdapter namespaceAdapter = loadKnownArtifact(
	        				papyrusProfileDatatypes.getUrn().value(),
	        				papyrusProfileDatatypes.getNamespaceUri());
		    		if (namespaceAdapter != null) {
			    	    this.dataTypesNamespace = namespaceAdapter;      
		    		}
		    		else
		    			log.debug("no resource found for, " + papyrusProfileDatatypes.getUrn().value());
	        	}
	        } 
        }
        
        // finally if the case where no artifacts are configured, load the MD profile by default
        // If Plasma SDO MagicDraw profile 1.0 is required and not already a defined artifact 
        // load it
        if (this.profileNamespace == null) {
        	ProfileArtifactAdapter magicdrawProfile = ProfileConfig.getInstance().findArtifactByUrn(ProfileURN.PLASMA___SDO___PROFILE___V_1___1___MDXML);
    		NamespaceAdapter namespaceAdapter = loadKnownArtifact(
    				magicdrawProfile.getUrn().value(),
    				magicdrawProfile.getNamespaceUri());
    		if (namespaceAdapter != null) {
	    	    this.profileNamespace = namespaceAdapter;  
	    	    this.dataTypesNamespace = namespaceAdapter; 
    		}
    		else
    			log.debug("no resource found for, " + magicdrawProfile.getUrn().value());
        }
        
    } 
    
    /**
     * Construct SDO namespace where the Artifact namespace is the SAME as the
     * SDO namespace. Only the actual artifact itself should know about the actual resource name. The 
     * "artifact" member here is the namespace URI of the artifact, NOT the URN. 
     * @param resource
     * @param uri
     * @return the new namespace or null if the resource does not exist
     */
    private NamespaceAdapter loadKnownArtifact(String resource, String uri) {
    	InputStream stream = PlasmaConfig.class.getClassLoader().getResourceAsStream(resource);
    	if (stream == null)
    		return null;
	    if (log.isDebugEnabled())
	    	log.debug("loading known UML/XMI artifact, " 
	            + resource + " (" +uri + ")");
        Fuml.load(new ResourceArtifact(resource, uri, stream));              	        		    	
    	
    	Namespace namespace = new Namespace();
    	namespace.setArtifact(uri); 
    	namespace.setUri(uri);
    	namespace.setProvisioning(null);
    	NamespaceAdapter namespaceAdapter = new NamespaceAdapter(namespace); 
    	this.sdoNamespaceMap.put(namespace.getUri(),  namespaceAdapter);
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
        	
         	for (DataAccessProvider provider : daconfig.getDataAccessProviders())
         	{
         		if (defaultProviderName == null)
         			defaultProviderName = provider.name;
         		
         		this.dataAccessProviderMap.put(provider.getName(), provider);
            	Map<String, NamespaceProvisioning> provMap = dataAccessProviderProvisioningMap.get(provider.getName());
            	if (provMap == null) {
            		provMap = new HashMap<String, NamespaceProvisioning>();
            		dataAccessProviderProvisioningMap.put(provider.getName(), provMap);
            	}
            	Map<String, NamespaceLink> linkMap = dataStoreNamespaceLinkMap.get(daconfig.getDataStoreType());
            	if (linkMap == null) {
            		linkMap = new HashMap<String, NamespaceLink>();
            		dataStoreNamespaceLinkMap.put(daconfig.getDataStoreType(), linkMap);
            	}
            	for (NamespaceLink namespaceLink : provider.getNamespaceLinks()) {
            		if (namespaceLink.getUri() == null)
                        throw new ConfigurationException("expected namespace URI for Data Access Service configuration '"
                                + provider.getName().toString() + "'");  
            		linkMap.put(namespaceLink.getUri(), namespaceLink);
            		NamespaceAdapter adapter = sdoNamespaceMap.get(namespaceLink.getUri());
            		if (adapter == null)
                        throw new ConfigurationException("Invalid SDO Namespace - could not find SDO namespace based on namespace URI '"
                                + namespaceLink.getUri() + "' for Data Access Service configuration '"
                                + provider.getName().toString() + "'");  
            		if (adapter.getNamespace().getProvisioning() != null && 
            			adapter.getNamespace().getProvisioning().getPackageName() != null &&
            			namespaceLink.getProvisioning() != null) 
            		{
            			if (adapter.getNamespace().getProvisioning().getPackageName().equals(
            					namespaceLink.getProvisioning().getPackageName())) 
            			{
                            throw new ConfigurationException("Duplicate provisioning package name ("
                            		+ namespaceLink.getProvisioning().getPackageName() 
                            		+ ") for SDO Namespace '"
                                    + namespaceLink.getUri() + "' and Data Access Service configuration '"
                                    + provider.getName().toString() + "'");  
            			}				
            		}
            		if (namespaceLink.getProvisioning() != null)
            		    provMap.put(namespaceLink.getUri(), 
            		    		namespaceLink.getProvisioning());		            		
            	}
            	
            	Map<String, Property> propertyMap = this.dataAccessProviderPropertyMap.get(provider.getName());
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
    
    private void validateNamespaceToDataStoreMappings()
    {
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
    			throw new ConfigurationException("SDO namespace '"+namespace.getUri()+"' not mapped to any "
    					+ "data access provider - every namespace should ba mapped/linked to one provider using a " 
    					+ NamespaceLink.class.getSimpleName() + " within the provider configuration");
        	if (count > 1)
    			throw new ConfigurationException("SDO namespace '"+namespace.getUri()+"' mapped to multiple "
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
    
    @SuppressWarnings("unchecked")
    private PlasmaConfiguration unmarshalConfig(String configFileURI, PlasmaConfigDataBinding binding)
    {
    	try {
    		try {
				this.configURI = new URI(configFileURI);
			} catch (URISyntaxException e) {
			}
    		InputStream stream = null;
    		if (this.configURI == null || this.configURI.getScheme() == null) {    		
		        stream = PlasmaConfig.class.getResourceAsStream(configFileURI);
		        if (stream == null)
		            stream = PlasmaConfig.class.getClassLoader().getResourceAsStream(configFileURI);
		        if (stream == null)
		            throw new ConfigurationException("could not find configuration file resource '" 
		                    + configFileURI 
		                    + "' on the current classpath");        
    		}
    		else {
    			try {
					stream = configURI.toURL().openStream();
				} catch (MalformedURLException e) {
		            throw new ConfigurationException(e);
				} catch (IOException e) {
		            throw new ConfigurationException(e);
				}
		        if (stream == null)
		            throw new ConfigurationException("could not open stream for configuration file resource '" 
		                    + configFileURI 
		                    + "'");        
    		}
	        
            PlasmaConfiguration result = (PlasmaConfiguration)binding.validate(stream);

            URL url = PlasmaConfig.class.getResource(configFileURI);
            if (url == null)
            	url = PlasmaConfig.class.getClassLoader().getResource(configFileURI);
            if (url != null) {
                File urlFile = new File(url.getFile());
                if (urlFile.exists()) 
                	this.configFileLastModifiedDate = urlFile.lastModified();
            }
            
            
            return result;
    	}
        catch (UnmarshalException e) {
            throw new ConfigurationException(e);
        }
        catch (JAXBException e) {
            throw new ConfigurationException(e);
        }
    }
    
    public void marshal(OutputStream stream) {
        try {
            PlasmaConfigDataBinding configBinding = new PlasmaConfigDataBinding(
                    new DefaultValidationEventHandler());
            configBinding.marshal(this.config, stream);
        } catch (JAXBException e1) {
            throw new ConfigurationException(e1);
        } catch (SAXException e1) {
            throw new ConfigurationException(e1);
        }
    }
    
    public static PlasmaConfig getInstance()
        throws PlasmaRuntimeException
    {
        if (instance == null)
            initializeInstance();
        return instance;
    }
    
    private static synchronized void initializeInstance()
        throws PlasmaRuntimeException
    {
        if (instance == null)
            instance = new PlasmaConfig();
    }

    public PlasmaConfiguration getConfig() {
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
            throw new ConfigurationException("no configured SDO namespace found for URI '"
                    + uri + "'");
    }
    
    public Namespace getSDONamespaceByInterfacePackage(String packageName) {
    	NamespaceAdapter result = sdoNamespaceMap.get(packageName);
        if (result != null)
            return result.getNamespace();
        else
            throw new ConfigurationException("no configured SDO namespace found for interface package name '"
                    + packageName + "'");
    }

    public TypeBindingAdapter findTypeBinding(String uri, String typeName) {
        NamespaceAdapter namespaceAdapter = sdoNamespaceMap.get(uri);
        if (namespaceAdapter != null) {
        	TypeBindingAdapter resultAdapter = namespaceAdapter.findTypeBinding(typeName);
        	if (resultAdapter != null)
        	    return resultAdapter;
        	else
        		return null;
        }
        else
            throw new ConfigurationException("no configured SDO namespace found for URI '"
                    + uri + "'");
    }
    
    public void remapTypeBinding(String uri, TypeBindingAdapter typeBinding) {
        NamespaceAdapter namespaceAdapter = sdoNamespaceMap.get(uri);
        if (namespaceAdapter != null) {
        	namespaceAdapter.remapTypeBinding(typeBinding);
        }
        else
            throw new ConfigurationException("no configured SDO namespace found for URI '"
                    + uri + "'");
    }

    public PropertyBindingAdapter findPropertyBinding(String uri, String typeName, String propertyName) {
        NamespaceAdapter namespaceAdapter = sdoNamespaceMap.get(uri);
        if (namespaceAdapter != null) {
        	TypeBindingAdapter typeBinding = namespaceAdapter.findTypeBinding(typeName);
        	if (typeBinding != null)
        	{
        		PropertyBindingAdapter resultAdapter = typeBinding.findPropertyBinding(propertyName);
        		if (resultAdapter != null)
        			return resultAdapter;
        		else
        			return null;
        	}
        	else
        		return null;
        }
        else
            throw new ConfigurationException("no configured SDO namespace found for URI '"
                    + uri + "'");
    }
    
    /**
     * Creates a dynamic SDO namespace configuration 
     * @param uri the dynamic namespace URI
     * @param supplierUri the original static URI which supplied the derived dynamic namespace
     */
    public void addDynamicSDONamespace(String uri, String supplierUri) {
    	Namespace namespace = new Namespace();
    	namespace.setArtifact(uri); // assumed artifact is created in-line dynamically
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
     * Returns the property based on the given name for the given data 
     * access provider, or null if not exists. 
     * @param provider the data access provider
     * @param name the property name
     * @return the property based on the given name for the given data 
     * access provider, or null if not exists. 
     */
    public Property findProviderProperty(DataAccessProvider provider, String name) {
	    for (org.plasma.config.Property property : provider.getProperties()) {
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
    		}
    		else {
    			log.debug("no vendor property (" + ConfigurationConstants.JDBC_VENDOR + ") found for provider '"
    		        + providerName.value() + "'- searching for vendor based on JDBC driver name");
        		Property driverProp = findProviderPropertyByName(provider, ConfigurationConstants.JDBC_DRIVER_NAME);
        		if (driverProp != null) {
    		        vendor = matchVendorName(driverProp);
        		}
		        if (vendor == null)
                    throw new ConfigurationException("could not determine RDBMS vendor for provider '"
                        + providerName.value() + "' - no vendor property (" + ConfigurationConstants.JDBC_VENDOR + ")"
                        + " or driver ("+ ConfigurationConstants.JDBC_DRIVER_NAME + ") properties found");
    		}
    		break;
    	default:	
            throw new ConfigurationException("could not determine RDBMS vendor for non-RDBMS provider '"
                    + providerName.value() + "'");
    	}
    	return vendor;
    }
    
    private RDBMSVendorName getVendorName(DataAccessProvider provider, Property prop)    
    {
    	try {
    	    return RDBMSVendorName.valueOf(prop.getValue());
    	}
    	catch (IllegalArgumentException e) {
    		StringBuilder buf = new StringBuilder();
    		for (int i = 0; i < RDBMSVendorName.values().length; i++) {
    			if (i > 0)
    				buf.append(", ");
    			buf.append(RDBMSVendorName.values()[i].name());
    		}
            throw new ConfigurationException("could not determine RDBMS vendor for provider '"
                    + provider.getName().value() + "' based on property value '"
            		+ prop.getValue() + "' - expected one of [" + buf.toString() + "]");
    	}    	
    }    
        
    private RDBMSVendorName matchVendorName(Property prop)    
    {
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

    public NamespaceProvisioning getProvisioningByNamespaceURI(DataAccessProviderName providerName, String uri) {
    	Map<String, NamespaceProvisioning> providerProvisionMap = this.dataAccessProviderProvisioningMap.get(providerName);
    	if (providerProvisionMap != null) {
    	    NamespaceProvisioning result = providerProvisionMap.get(uri);
    	    if (result != null)
                return result;
    	}         
        throw new NonExistantNamespaceException("no configured '"+ providerName.value() + "' provider namespace found for URI '"
                    + uri + "'");
    }
    
    public boolean hasNamespace(DataStoreType dataStore) {
    	return this.dataStoreNamespaceLinkMap.get(dataStore) != null;
    }
    
    public Repository getRepository() {
        
        return config.getRepository();
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
    
    public String getSDOImplementationClassName(String uri, String name) {
    	String result = name;
    	
    	@SuppressWarnings("unused")
		Namespace namespace = getSDONamespaceByURI(uri); // validate URI for now
    	if (namespace != null) { 
    		String prefix = null;
    		String suffix = null;
    		if (this.getSDO() != null && this.getSDO().getGlobalProvisioning() != null &&
    				this.getSDO().getGlobalProvisioning().getImplementation() != null) {
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
		    if (this.getSDO() != null && this.getSDO().getGlobalProvisioning() != null &&
    				this.getSDO().getGlobalProvisioning().getImplementation() != null) {
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
		if (this.getSDO() != null && this.getSDO().getGlobalProvisioning() != null &&
			this.getSDO().getGlobalProvisioning().getQueryDSL() != null) {
			
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
    		if (this.getSDO() != null && this.getSDO().getGlobalProvisioning() != null &&
    				this.getSDO().getGlobalProvisioning().getQueryDSL() != null) {
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
    
    
    public String getServiceImplementationClassName(DataAccessProviderName providerName, 
    		String uri, String name) {
    	String result = name;
    	DataAccessProvider config = this.getDataAccessProvider(providerName);
    	@SuppressWarnings("unused")
		Namespace namespace = getSDONamespaceByURI(uri); // validate URI for now
    	return result;
    } 
    
    public String getServiceImplementationPackageName(DataAccessProviderName serviceName, 
    		String uri) {
    	
    	NamespaceProvisioning provisioning = getProvisioningByNamespaceURI(serviceName, uri);
    	@SuppressWarnings("unused")
		Namespace namespace = getSDONamespaceByURI(uri); // validate URI for now
    	return provisioning.getPackageName();
    }    
    
}
