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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import jakarta.xml.bind.JAXBException;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import joptsimple.OptionSpecBuilder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom2.Document;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.plasma.common.bind.DefaultValidationEventHandler;
import org.plasma.common.exception.ProvisioningException;
import org.plasma.metamodel.Model;
import org.plasma.metamodel.adapter.ModelAdapter;
import org.plasma.metamodel.adapter.ProvisioningModel;
import org.plasma.provisioning.MetamodelAssembler;
import org.plasma.provisioning.MetamodelDataBinding;
import org.plasma.provisioning.rdb.RDBConstants;
import org.plasma.xml.uml.MDModelAssembler;
import org.plasma.xml.uml.PapyrusModelAssembler;
import org.plasma.xml.uml.UMLModelAssembler;
import org.xml.sax.SAXException;

/**
 * The Unified Modeling Language (UML) Tool is used to provision UML modeling
 * artifacts from various sources.
 * <p>
 * </p>
 * <b>Usage:</b> java <@link org.plasma.provisioning.cli.UMLTool> Option
 * Description ------ ---------- --dest [File] the fully qualified tool output
 * destination file or directory name --dialect <RDBDialect> the vocabulary or
 * usage which is characteristic for this context - used when sourceType is rdb
 * - one of [oracle, mysql] is expected --help prints help on this tool
 * --namespaces a comma separated list of namespace URIs --platform
 * <UMLPlatform> the UML modeling or other platform for this context - one of
 * [papyrus, magicdraw] is expected --schemas a comma separated list of schema
 * names --silent whether to log or print no messages at all (typically for
 * testing) --sourceType <UMLToolSource> a qualifier describing the input source
 * - one of [xsd, rdb] is expected --verbose whether to log or print detailed
 * messages
 */
public class UMLTool extends ProvisioningTool implements RDBConstants {

  private static Log log = LogFactory.getLog(UMLTool.class);

  /**
   * Command line entry point.
   * <p>
   * </p>
   * <b>Usage:</b> java <@link org.plasma.provisioning.cli.UMLTool> Option
   * Description ------ ---------- --dest [File] the fully qualified tool output
   * destination file or directory name --dialect <RDBDialect> the vocabulary or
   * usage which is characteristic for this context - used when sourceType is
   * rdb - one of [oracle, mysql] is expected --help prints help on this tool
   * --namespaces a comma separated list of namespace URIs --platform
   * <UMLPlatform> the UML modeling or other platform for this context - one of
   * [papyrus, magicdraw] is expected --schemas a comma separated list of schema
   * names --silent whether to log or print no messages at all (typically for
   * testing) --sourceType <UMLToolSource> a qualifier describing the input
   * source - one of [xsd, rdb] is expected --verbose whether to log or print
   * detailed messages
   */
  public static void main(String[] args) throws JAXBException, SAXException, IOException {
    log.info(args.toString());

    OptionParser parser = new OptionParser();
    OptionSpecBuilder verboseOpt = parser.accepts(ProvisioningToolOption.verbose.name(),
        ProvisioningToolOption.verbose.getDescription());
    OptionSpecBuilder silentOpt = parser.accepts(ProvisioningToolOption.silent.name(),
        ProvisioningToolOption.silent.getDescription());

    OptionSpecBuilder helpOpt = parser.accepts(ProvisioningToolOption.help.name(),
        ProvisioningToolOption.help.getDescription());
    OptionSpecBuilder sourceTypeOpt = parser.accepts(
        ProvisioningToolOption.sourceType.name(),
        ProvisioningToolOption.sourceType.getDescription() + " - one of ["
            + UMLToolSource.asString() + "] is expected");
    OptionSpecBuilder platformOpt = parser.accepts(ProvisioningToolOption.platform.name(),
        ProvisioningToolOption.platform.getDescription() + " - one of [" + UMLPlatform.asString()
            + "] is expected");
    OptionSpecBuilder dialectOpt = parser.accepts(ProvisioningToolOption.dialect.name(),
        ProvisioningToolOption.dialect.getDescription() + " - used when "
            + ProvisioningToolOption.sourceType.name() + " is " + UMLToolSource.rdb.name()
            + " - one of [" + RDBDialect.asString() + "] is expected");
    OptionSpec<String> namespacesOpt = parser
        .accepts(ProvisioningToolOption.namespaces.name(),
            ProvisioningToolOption.namespaces.getDescription()).withOptionalArg()
        .ofType(String.class);
    OptionSpec<String> schemasOpt = parser
        .accepts(ProvisioningToolOption.schemas.name(),
            ProvisioningToolOption.schemas.getDescription()).withOptionalArg().ofType(String.class);
    OptionSpec<File> destOpt = parser
        .accepts(ProvisioningToolOption.dest.name(), ProvisioningToolOption.dest.getDescription())
        .withOptionalArg().ofType(File.class);

    sourceTypeOpt.withRequiredArg().ofType(UMLToolSource.class);
    platformOpt.withRequiredArg().ofType(UMLPlatform.class);
    dialectOpt.withRequiredArg().ofType(RDBDialect.class);

    OptionSet options = parser.parse(args);

    if (options.has(helpOpt)) {
      printUsage(parser, log);
      return;
    }

    if (!options.has(ProvisioningToolOption.sourceType.name())) {
      if (!options.has(silentOpt))
        printUsage(parser, log);
      throw new IllegalArgumentException("expected option '"
          + ProvisioningToolOption.sourceType.name() + "'");
    }
    UMLToolSource source = (UMLToolSource) options
        .valueOf(ProvisioningToolOption.sourceType.name());

    String[] schemaNames = null;
    String[] namespaces = null;
    if (options.has(ProvisioningToolOption.namespaces.name())) {
      namespaces = namespacesOpt.value(options).split(",");
      schemaNames = schemasOpt.value(options).split(",");
      if (namespaces.length != 1)
        throw new ProvisioningException("expected single value for '"
            + ProvisioningToolOption.namespaces.name()
            + " option - only single value currently supported");
      if (schemaNames.length != 1)
        throw new ProvisioningException("expected single value for '"
            + ProvisioningToolOption.schemas.name()
            + " option - only single value currently supported");
      if (namespaces.length != schemaNames.length)
        throw new ProvisioningException("expected '" + ProvisioningToolOption.schemas.name()
            + "' and '" + ProvisioningToolOption.namespaces.name()
            + "' arguments with equal number of comma seperated  values");
    } else if (options.has(ProvisioningToolOption.schemas.name())) {
      schemaNames = schemasOpt.value(options).split(",");
      if (schemaNames.length != 1)
        throw new ProvisioningException("expected single value for '"
            + ProvisioningToolOption.schemas.name()
            + " option - only single value currently supported");
      namespaces = new String[schemaNames.length];
      for (int i = 0; i < schemaNames.length; i++)
        namespaces[i] = "http://" + schemaNames[i];
    }
    if (namespaces == null) {
      namespaces = new String[1];
      namespaces[0] = "http://" + destOpt.value(options).getName();
    }

    RDBDialect dialect = null;
    Model model = null;
    switch (source) {
    case rdb:
      if (!options.has(ProvisioningToolOption.dialect.name())) {
        if (!options.has(silentOpt))
          printUsage(parser, log);
        throw new IllegalArgumentException("expected option '"
            + ProvisioningToolOption.dialect.name() + "'");
      }
      dialect = (RDBDialect) options.valueOf(ProvisioningToolOption.dialect.name());
      model = (new RDBReader()).read(dialect, schemaNames, namespaces);
      break;
    case uml:
      MetamodelAssembler modelAssembler = new MetamodelAssembler();
      model = modelAssembler.getModel();
      break;
    case xsd:
    default:
      printUsage(parser, log);
      throw new IllegalArgumentException("unsupported " + ProvisioningToolOption.sourceType.name()
          + " value '" + source + "'");
    }

    UMLPlatform platform = UMLPlatform.papyrus;
    if (options.has(ProvisioningToolOption.platform.name())) {
      platform = (UMLPlatform) options.valueOf(ProvisioningToolOption.platform.name());
    }

    File dest = new File("./target/" + UMLTool.class.getSimpleName() + ".out");
    if (options.has(destOpt)) {
      dest = destOpt.value(options);
    }
    if (!dest.getParentFile().exists())
      dest.getParentFile().mkdirs();

    if (log.isDebugEnabled()) {
      MetamodelDataBinding provBinding = new MetamodelDataBinding(
          new DefaultValidationEventHandler());
      String xml = provBinding.marshal(model);
      File outFile = new File(dest.getParentFile(), "technical-model.xml");
      OutputStream stream = new FileOutputStream(outFile);
      stream.write(xml.getBytes());
      stream.flush();
      stream.close();
      log.debug("wrote merged model file to: " + outFile.getAbsoluteFile());
      log.debug("reading merged model file: " + outFile.getAbsoluteFile());
      model = (Model) provBinding.unmarshal(new FileInputStream(outFile));
    }

    ProvisioningModel validator = new ModelAdapter(model);
    UMLModelAssembler umlAssembler = null;
    switch (platform) {
    case papyrus:
      umlAssembler = new PapyrusModelAssembler(model, namespaces[0], "tns");
      break;
    case magicdraw:
      umlAssembler = new MDModelAssembler(model, namespaces[0], "tns");
      break;
    }
    umlAssembler.setDerivePackageNamesFromURIs(false);
    Document document = umlAssembler.getDocument();
    if (!options.has(silentOpt))
      log.info("marshaling XMI model to " + dest.getAbsolutePath());
    try {
      FileOutputStream os = new FileOutputStream(dest);
      XMLOutputter outputter = new XMLOutputter();
      outputter.setFormat(Format.getPrettyFormat());
      outputter.output(document, os);
    } catch (FileNotFoundException e) {
      throw new ProvisioningException(e);
    } catch (IOException e) {
      throw new ProvisioningException(e);
    }

  }

}
