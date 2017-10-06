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

package org.plasma.provisioning.cli;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

import javax.xml.bind.JAXBException;

import joptsimple.BuiltinHelpFormatter;
import joptsimple.HelpFormatter;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpecBuilder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom2.output.Format;
import org.modeldriven.fuml.Fuml;
import org.modeldriven.fuml.io.ResourceArtifact;
import org.plasma.common.bind.DefaultValidationEventHandler;
import org.plasma.common.provisioning.NamespaceUtils;
import org.plasma.metamodel.Model;
import org.plasma.metamodel.Package;
import org.plasma.metamodel.adapter.ModelAdapter;
import org.plasma.metamodel.adapter.ProvisioningModel;
import org.plasma.provisioning.AnnotationMetamodelAssembler;
import org.plasma.provisioning.MetamodelDataBinding;
import org.plasma.runtime.Artifact;
import org.plasma.runtime.EnumSource;
import org.plasma.runtime.ImplementationProvisioning;
import org.plasma.runtime.InterfaceProvisioning;
import org.plasma.runtime.NamespaceProvisioning;
import org.plasma.runtime.PlasmaRuntime;
import org.plasma.runtime.PropertyNameStyle;
import org.plasma.runtime.QueryDSLProvisioning;
import org.plasma.xml.uml.MDModelAssembler;
import org.plasma.xml.uml.PapyrusModelAssembler;
import org.plasma.xml.uml.UMLModelAssembler;
import org.xml.sax.SAXException;

public abstract class ProvisioningTool {
  private static Log log = LogFactory.getLog(ProvisioningTool.class);

  protected static void printUsage(OptionParser parser, Log theLog) throws IOException {
    HelpFormatter helpFormat = new BuiltinHelpFormatter(640, 1);
    parser.formatHelpWith(helpFormat);
    ByteArrayOutputStream helpOs = new ByteArrayOutputStream();
    parser.printHelpOn(helpOs);
    theLog.info("\n" + new String(helpOs.toByteArray()));
  }

  protected static Model validateStagingModel(File source) throws JAXBException, SAXException,
      IOException {
    FileInputStream is = new FileInputStream(source);
    MetamodelDataBinding binding = new MetamodelDataBinding(new DefaultValidationEventHandler());
    Model model = null;
    try {
      model = (Model) binding.validate(is);
    } finally {
      is.close();
    }

    ProvisioningModel validator = new ModelAdapter(model);

    return model;
  }

  protected static boolean regenerate(long lastExecution) {
    boolean stale = false;

    // check config file
    if (PlasmaRuntime.getInstance().getConfigFileLastModifiedDate() > lastExecution) {
      stale = true;
      log.debug("detected stale configuration file '"
          + PlasmaRuntime.getInstance().getConfigFileName() + "' against time: "
          + String.valueOf(lastExecution));
    }

    if (!stale) {
      // check repo artifacts
      for (Artifact artifact : PlasmaRuntime.getInstance().getRepository().getArtifacts()) {
        URL url = PlasmaRuntime.class.getResource(artifact.getUrn());
        if (url == null)
          url = PlasmaRuntime.class.getClassLoader().getResource(artifact.getUrn());
        log.debug("checking modified state of repository artifact '" + url.getFile()
            + "' against time: " + String.valueOf(lastExecution));
        File urlFile = new File(url.getFile());
        if (urlFile.exists()) {
          if (urlFile.lastModified() > lastExecution) {
            stale = true;
            log.debug("detected stale repository artifact '" + url.getFile() + "' against time: "
                + String.valueOf(lastExecution));
            break;
          }
        }
      }
    }

    // FIXME - how to determine if annotated classes have changed
    if (!stale) {
    }

    return stale;
  }

  /**
   * Assembles provisioning meta model from annotated classes found using a
   * class indexing utility, then loads convert the assembled model to UML,
   * loading it into the FUML runtime.
   * 
   * @param annotationAssembler
   *          the meta model assembler
   * @param destDir
   *          the destination dir
   * @param options
   *          the provisioning options
   * @param silentOpt
   *          the silent option
   */
  protected static void loadAnnotationModel(AnnotationMetamodelAssembler annotationAssembler,
      File destDir, OptionSet options, OptionSpecBuilder silentOpt) throws JAXBException,
      SAXException, IOException {

    if (log.isDebugEnabled()) {
      MetamodelDataBinding binding = new MetamodelDataBinding(new DefaultValidationEventHandler());
      String xml = binding.marshal(annotationAssembler.getModel());
      File outFile = new File(destDir, "derived-technical-model.xml");
      FileOutputStream stream = new FileOutputStream(outFile);
      stream.write(xml.getBytes());
      stream.flush();
      stream.close();
      if (!options.has(silentOpt))
        log.debug("wrote derived model file to: " + outFile.getAbsoluteFile());
      // log.debug(xml);
    }
    ProvisioningModel annotationModelAdapter = new ModelAdapter(annotationAssembler.getModel());

    // below namespace config and UML load are both once per JVM
    // so return if already loaded
    if (configHasAllNamespaces(annotationModelAdapter.getLeafPackages())) {
      if (!options.has(silentOpt))
        if (log.isDebugEnabled())
          log.debug("found all annotation namespaces in plasma configuration - returning");
      return;
    }

    // configure namespaces
    String targetNamespaceUri = annotationModelAdapter.getModel().getUri();
    for (Package pkg : annotationModelAdapter.getLeafPackages()) {
      configureNamespace(pkg, targetNamespaceUri);
    }

    // generate and load UML
    UMLModelAssembler umlAssembler = null;
    UMLPlatform platform = UMLPlatform.magicdraw;
    switch (platform) {
    case papyrus:
      umlAssembler = new PapyrusModelAssembler(annotationModelAdapter.getModel(),
          targetNamespaceUri, "tns");
      break;
    case magicdraw:
      umlAssembler = new MDModelAssembler(annotationModelAdapter.getModel(), targetNamespaceUri,
          "tns");
      break;
    }
    umlAssembler.setDerivePackageNamesFromURIs(false);
    String xmi = umlAssembler.getContent(Format.getPrettyFormat());
    if (log.isDebugEnabled()) {
      File xmiDebugFile = new File(destDir, annotationModelAdapter.getModel().getId() + ".uml");
      if (!options.has(silentOpt))
        log.debug("Writing UML/XMI to: " + xmiDebugFile.getAbsolutePath());
      try {
        FileOutputStream os = new FileOutputStream(xmiDebugFile);
        umlAssembler.getContent(os, Format.getPrettyFormat());
      } catch (FileNotFoundException e) {
      }
    }
    ByteArrayInputStream fumlStream = new ByteArrayInputStream(xmi.getBytes());

    if (log.isDebugEnabled())
      if (!options.has(silentOpt))
        log.debug("loading UML/XMI model");
    Fuml.load(new ResourceArtifact(targetNamespaceUri, targetNamespaceUri, fumlStream));

  }

  private static boolean configHasAllNamespaces(List<Package> packages) {
    boolean result = true;
    for (Package pkg : packages)
      if (!PlasmaRuntime.getInstance().hasSDONamespace(pkg.getUri())) {
        result = false;
        break;
      }
    return result;
  }

  private static void configureNamespace(Package pkg, String targetNamespaceUri) {
    if (PlasmaRuntime.getInstance().hasSDONamespace(pkg.getUri()))
      return; // skip if URI manually configured

    String pkgName = null;
    if (pkg.getNamespaceProvisioning() != null
        && pkg.getNamespaceProvisioning().getPackageName() != null
        && pkg.getNamespaceProvisioning().getPackageName().trim().length() > 0) {
      pkgName = pkg.getNamespaceProvisioning().getPackageName().trim();
    } else {
      // derive a java package name from the namespace URI
      pkgName = NamespaceUtils.toPackageName(pkg.getUri());
    }

    // FIXME: while we may have class or even property level provisioning
    // info to store
    // based on annotated classes, there is nowhere to store it
    // in the config.
    NamespaceProvisioning nsProvConfig = new NamespaceProvisioning();
    nsProvConfig.setPackageName(pkgName);

    InterfaceProvisioning itfProv = new InterfaceProvisioning();
    nsProvConfig.setInterface(itfProv);
    // FIXME: collapse these elements into property name/value pairs
    itfProv.setPropertyNameStyle(PropertyNameStyle.ENUMS);
    itfProv.setEnumSource(EnumSource.EXTERNAL);

    ImplementationProvisioning implProv = new ImplementationProvisioning();
    nsProvConfig.setImplementation(implProv);

    QueryDSLProvisioning queryDSL = new QueryDSLProvisioning();
    nsProvConfig.setQueryDSL(queryDSL);
    queryDSL.setGenerate(true);

    PlasmaRuntime.getInstance().addDynamicSDONamespace(pkg.getUri(), targetNamespaceUri,
        nsProvConfig);
  }

  protected static void writeContent(InputStream is, OutputStream os) throws IOException {
    byte[] buf = new byte[4000];
    int len = -1;
    try {
      while ((len = is.read(buf)) != -1)
        os.write(buf, 0, len);
    } finally {
      is.close();
      os.flush();
    }
  }

  public static byte[] readContent(InputStream is) throws IOException {
    byte[] result;
    byte[] buf = new byte[4000];
    ByteArrayOutputStream os = new ByteArrayOutputStream(buf.length);
    int len = -1;
    try {
      while ((len = is.read(buf, 0, buf.length)) != -1)
        os.write(buf, 0, len);
      result = os.toByteArray();
    } finally {
      is.close();
      os.flush();
      os.close();
    }
    return result;
  }
}
