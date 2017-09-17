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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.common.bind.DefaultValidationEventHandler;
import org.plasma.config.PlasmaConfig;
import org.plasma.provisioning.AnnotationMetamodelAssembler;
import org.plasma.provisioning.MetamodelAssembler;
import org.plasma.provisioning.MetamodelDataBinding;
import org.plasma.provisioning.ProvisioningException;
import org.plasma.provisioning.adapter.ModelAdapter;
import org.plasma.provisioning.adapter.ProvisioningModel;
import org.plasma.text.lang3gl.DefaultLang3GLContext;
import org.plasma.text.lang3gl.DefaultStreamAssembler;
import org.plasma.text.lang3gl.Lang3GLContext;
import org.plasma.text.lang3gl.Lang3GLDialect;
import org.plasma.text.lang3gl.Lang3GLFactory;
import org.plasma.text.lang3gl.Lang3GLOperation;
import org.plasma.text.lang3gl.java.SDOAssembler;
import org.plasma.text.lang3gl.java.SDOFactory;
import org.xml.sax.SAXException;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import joptsimple.OptionSpecBuilder;

/**
 * The SDO Tool is used to provision SDO code artifacts.
 * <p>
 * </p>
 * <b>Usage:</b> java <@link org.plasma.provisioning.cli.SDOTool>
 * <p>
 * </p>
 * Option Description ------ ----------- --command <SDOToolAction> the primary
 * action or command performed by this tool - one of [create, export] is
 * expected --dest [File] the fully qualified tool output destination file or
 * directory name --help prints help on this tool --lastExecution <Long> a long
 * integer representing the last time the tool was executed --silent whether to
 * log or print no messages at all (typically for testing) --verbose whether to
 * log or print detailed messages
 */
public class SDOTool extends ProvisioningTool {

  private static Log log = LogFactory.getLog(SDOTool.class);

  /**
   * Command line entry point.
   * <p>
   * </p>
   * <b>Usage:</b> java <@link org.plasma.provisioning.cli.SDOTool>
   * <p>
   * </p>
   * Option Description ------ ----------- --command <SDOToolAction> the primary
   * action or command performed by this tool - one of [create, export] is
   * expected --dest [File] the fully qualified tool output destination file or
   * directory name --help prints help on this tool --lastExecution <Long> a
   * long integer representing the last time the tool was executed --silent
   * whether to log or print no messages at all (typically for testing)
   * --verbose whether to log or print detailed messages
   */
  public static void main(String[] args) throws JAXBException, SAXException, IOException {

    OptionParser parser = new OptionParser();
    OptionSpecBuilder verboseOpt = parser.accepts(ProvisioningToolOption.verbose.name(),
        ProvisioningToolOption.verbose.getDescription());
    OptionSpecBuilder silentOpt = parser.accepts(ProvisioningToolOption.silent.name(),
        ProvisioningToolOption.silent.getDescription());

    OptionSpecBuilder helpOpt = parser.accepts(ProvisioningToolOption.help.name(),
        ProvisioningToolOption.help.getDescription());
    OptionSpecBuilder commandOpt = parser.accepts(ProvisioningToolOption.command.name(),
        ProvisioningToolOption.command.getDescription() + " - one of [" + SDOToolAction.asString()
            + "] is expected");
    commandOpt.withRequiredArg().ofType(SDOToolAction.class);

    OptionSpec<Long> lastExecutionOpt = parser
        .accepts(ProvisioningToolOption.lastExecution.name(),
            ProvisioningToolOption.lastExecution.getDescription()).withRequiredArg()
        .ofType(Long.class);
    OptionSpec<File> destOpt = parser
        .accepts(ProvisioningToolOption.dest.name(), ProvisioningToolOption.dest.getDescription())
        .withOptionalArg().ofType(File.class);

    OptionSet options = parser.parse(args);

    if (options.has(helpOpt)) {
      printUsage(parser, log);
      return;
    }

    if (!options.has(ProvisioningToolOption.command.name())) {
      if (!options.has(silentOpt))
        printUsage(parser, log);
      throw new IllegalArgumentException("expected option '"
          + ProvisioningToolOption.command.name() + "'");
    }
    SDOToolAction command = (SDOToolAction) options.valueOf(ProvisioningToolOption.command.name());

    Lang3GLDialect dialect = Lang3GLDialect.java;
    MetamodelAssembler modelAssembler = null;
    File destDir = new File("./target/");
    if (options.has(destOpt)) {
      destDir = destOpt.value(options);
    }
    if (!destDir.exists()) {
      if (!options.has(silentOpt))
        log.debug("given destination dir '" + destDir.getName() + "' does not exist");
      if (!destDir.mkdirs())
        throw new IllegalArgumentException("given destination dir '" + destDir.getName()
            + "' could not be created");
    }

    switch (command) {
    case export:
      File destFile = new File(destDir, "technical-model.xml");
      if (options.has(destOpt)) {
        destFile = destOpt.value(options);
      }
      if (destFile.getParentFile() == null || !destFile.getParentFile().exists()) {
        log.info("given destination directory '" + destFile.getParentFile().getAbsolutePath()
            + "' does not exist, creating");
        if (!destFile.getParentFile().mkdirs())
          throw new IllegalArgumentException("given destination directory '"
              + destFile.getParentFile() + "' could not be created");
      }
      if (destFile.isDirectory())
        destFile = new File(destFile, "technical-model.xml");
      if (!options.has(silentOpt))
        log.info("dest: " + destFile.getAbsoluteFile());

      AnnotationMetamodelAssembler annotationAssembler = new AnnotationMetamodelAssembler();
      if (annotationAssembler.hasAnnotatedClasses())
        loadAnnotationModel(annotationAssembler, destDir, options, silentOpt);

      modelAssembler = new MetamodelAssembler();
      MetamodelDataBinding binding = new MetamodelDataBinding(new DefaultValidationEventHandler());
      String xml = binding.marshal(modelAssembler.getModel());
      FileOutputStream stream = new FileOutputStream(destFile);
      stream.write(xml.getBytes());
      stream.flush();
      stream.close();
      validateStagingModel(destFile);
      if (!options.has(silentOpt))
        log.info("wrote merged model file to: " + destFile.getAbsoluteFile());
      break;
    case create:
      Lang3GLOperation operation = Lang3GLOperation.valueOf(command.name());
      if (!options.has(silentOpt))
        log.debug("dest: " + destDir.getName());

      long lastExecution = 0L;

      if (options.has(lastExecutionOpt)) {
        lastExecution = lastExecutionOpt.value(options).longValue();
      }

      if (!regenerate(lastExecution)) {
        if (!options.has(silentOpt))
          log.info("skipping SDO creation - no stale artifacts detected");
        return;
      }

      // check for manually configured UML artifacts/namespaces
      int configuredNamespaceCount = PlasmaConfig.getInstance().getSDONamespaceCount();

      // loads the config and
      annotationAssembler = new AnnotationMetamodelAssembler();
      if (annotationAssembler.hasAnnotatedClasses())
        loadAnnotationModel(annotationAssembler, destDir, options, silentOpt);

      ProvisioningModel validator = null;
      binding = new MetamodelDataBinding(new DefaultValidationEventHandler());

      // Rely entirely on config and UML for the entire provisioning context,
      // load the metamodel from configured SDO namespaces
      modelAssembler = new MetamodelAssembler();
      xml = binding.marshal(modelAssembler.getModel());
      if (log.isDebugEnabled()) {
        File outFile = new File(destDir, "technical-model.xml");
        stream = new FileOutputStream(outFile);
        stream.write(xml.getBytes());
        stream.flush();
        stream.close();
        if (!options.has(silentOpt))
          log.debug("wrote merged model file to: " + outFile.getAbsoluteFile());
      }
      validator = new ModelAdapter(modelAssembler.getModel());

      Lang3GLContext factoryContext = new DefaultLang3GLContext(validator);

      Lang3GLFactory factory = null;
      switch (dialect) {
      case java:
        factory = new SDOFactory(factoryContext);
        break;
      default:
        throw new ProvisioningException("unknown 3GL language dialect, '" + dialect.name() + "'");
      }

      DefaultStreamAssembler assembler = new SDOAssembler(validator, factory, operation, destDir);
      assembler.start();

      if (!options.has(silentOpt)) {
        log.info("generated " + assembler.getResultInterfacesCount()
            + " interfaces to output to directory: " + destDir.getAbsolutePath());
        log.info("generated " + assembler.getResultClassesCount()
            + " classes to output to directory: " + destDir.getAbsolutePath());
        log.info("generated " + assembler.getResultEnumerationsCount()
            + " enumerations to output to directory: " + destDir.getAbsolutePath());
      }
      break;
    default:
      throw new ProvisioningException("unknown command '" + command.toString() + "'");
    }
  }

}
