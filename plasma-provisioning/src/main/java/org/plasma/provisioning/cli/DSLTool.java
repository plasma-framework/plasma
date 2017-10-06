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
import java.io.IOException;

import javax.xml.bind.JAXBException;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import joptsimple.OptionSpecBuilder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.common.exception.ProvisioningException;
import org.plasma.metamodel.adapter.ModelAdapter;
import org.plasma.metamodel.adapter.ProvisioningModel;
import org.plasma.provisioning.AnnotationMetamodelAssembler;
import org.plasma.provisioning.MetamodelAssembler;
import org.plasma.runtime.PlasmaRuntime;
import org.plasma.text.lang3gl.DefaultLang3GLContext;
import org.plasma.text.lang3gl.DefaultStreamAssembler;
import org.plasma.text.lang3gl.Lang3GLContext;
import org.plasma.text.lang3gl.Lang3GLDialect;
import org.plasma.text.lang3gl.Lang3GLFactory;
import org.plasma.text.lang3gl.Lang3GLOperation;
import org.plasma.text.lang3gl.java.DSLAssembler;
import org.plasma.text.lang3gl.java.DSLFactory;
import org.xml.sax.SAXException;

/**
 * The DSL Tool is used to provision Query DSL code artifacts.
 * <p>
 * </p>
 * <b>Usage:</b> java <@link org.plasma.provisioning.cli.DSLTool> Option
 * Description ------ ----------- --command <DSLToolAction> the primary action
 * or command performed by this tool - one of [create] is expected --dest [File]
 * the fully qualified tool output destination file or directory name --help
 * prints help on this tool --lastExecution <Long> a long integer representing
 * the last time the tool was executed --silent whether to log or print no
 * messages at all (typically for testing) --verbose whether to log or print
 * detailed messages *
 */
public class DSLTool extends ProvisioningTool {

  private static Log log = LogFactory.getLog(DSLTool.class);

  /**
   * Command line entry point. Option Description ------ ----------- --command
   * <DSLToolAction> the primary action or command performed by this tool - one
   * of [create] is expected --dest [File] the fully qualified tool output
   * destination file or directory name --help prints help on this tool
   * --lastExecution <Long> a long integer representing the last time the tool
   * was executed --silent whether to log or print no messages at all (typically
   * for testing) --verbose whether to log or print detailed messages *
   * 
   * @param args
   * @throws JAXBException
   * @throws SAXException
   * @throws IOException
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
        ProvisioningToolOption.command.getDescription() + " - one of [" + DSLToolAction.asString()
            + "] is expected");
    commandOpt.withRequiredArg().ofType(DSLToolAction.class);

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
    DSLToolAction command = (DSLToolAction) options.valueOf(ProvisioningToolOption.command.name());

    Lang3GLDialect dialect = Lang3GLDialect.java;

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
    case create:
      Lang3GLOperation operation = Lang3GLOperation.valueOf(command.name());

      long lastExecution = 0L;

      if (options.has(lastExecutionOpt)) {
        lastExecution = lastExecutionOpt.value(options).longValue();
      }

      if (!regenerate(lastExecution)) {
        if (!options.has(silentOpt))
          log.info("skipping DSL creation - no stale artifacts detected");
        return;
      }

      org.plasma.metamodel.Model model = null;
      if (PlasmaRuntime.getInstance().isDerived()) {
        model = PlasmaRuntime.getInstance().getDerivedModel();
      } else {
        MetamodelAssembler modelAssembler = new MetamodelAssembler();
        model = modelAssembler.getModel();
      }
      ProvisioningModel validator = new ModelAdapter(model);

      Lang3GLContext factoryContext = new DefaultLang3GLContext(validator);

      Lang3GLFactory factory = null;
      switch (dialect) {
      case java:
        factory = new DSLFactory(factoryContext);
        break;
      default:
        throw new ProvisioningException("unknown 3GL language dialect, '" + dialect.name() + "'");
      }

      DefaultStreamAssembler assembler = new DSLAssembler(validator, factory, operation, destDir);
      assembler.start();
      if (!options.has(silentOpt))
        log.info("generated " + assembler.getResultClassesCount()
            + " classes to output to directory: " + destDir.getAbsolutePath());

      break;
    default:
      throw new ProvisioningException("unknown command '" + command.toString() + "'");
    }

  }
}
