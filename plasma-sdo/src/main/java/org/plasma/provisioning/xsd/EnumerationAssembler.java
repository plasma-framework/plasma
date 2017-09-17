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

package org.plasma.provisioning.xsd;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xerces.dom.ElementNSImpl;
import org.plasma.metamodel.Alias;
import org.plasma.metamodel.Documentation;
import org.plasma.metamodel.DocumentationType;
import org.plasma.metamodel.Enumeration;
import org.plasma.metamodel.EnumerationLiteral;
import org.plasma.xml.schema.AbstractSimpleType;
import org.plasma.xml.schema.Restriction;
import org.plasma.xml.schema.SimpleType;

public class EnumerationAssembler extends AbstractAssembler {

  private static Log log = LogFactory.getLog(EnumerationAssembler.class);

  public EnumerationAssembler(ConverterSupport converterSupport, String destNamespaceURI,
      String destNamespacePrefix) {
    super(destNamespaceURI, destNamespacePrefix, converterSupport);
  }

  public Enumeration buildEnumeration(AbstractSimpleType simpleType, AbstractSimpleType source) {
    String name = simpleType.getName();
    if (name == null)
      name = source.getName();
    return buildEnumeration(simpleType, name);
  }

  public Enumeration buildEnumeration(SimpleType simpleType) {
    return buildEnumeration(simpleType, simpleType.getName());
  }

  public Enumeration buildEnumeration(AbstractSimpleType simpleType, String name) {
    Enumeration enm = new Enumeration();
    enm.setId(UUID.randomUUID().toString());
    Alias alias = new Alias();
    enm.setAlias(alias);
    alias.setLocalName(name);
    String logicalName = this.formatLocalClassName(name);
    enm.setName(logicalName);
    enm.setUri(this.destNamespaceURI);

    Documentation documentation = createDocumentation(DocumentationType.DEFINITION,
        getDocumentationContent(simpleType));
    enm.getDocumentations().add(documentation);
    Map<String, EnumerationLiteral> literalMap = new HashMap<String, EnumerationLiteral>();

    Restriction restriction = simpleType.getRestriction();
    if (restriction.getMinExclusivesAndMinInclusivesAndMaxExclusives().size() == 0)
      throw new IllegalStateException("expected collection values");
    for (Object obj : restriction.getMinExclusivesAndMinInclusivesAndMaxExclusives()) {
      if (obj instanceof org.plasma.xml.schema.Enumeration) {
        org.plasma.xml.schema.Enumeration schemaEnum = (org.plasma.xml.schema.Enumeration) obj;

        EnumerationLiteral literal = new EnumerationLiteral();
        enm.getEnumerationLiterals().add(literal);
        String literalName = schemaEnum.getValue();
        literalName = support.buildLogicalEnumerationLiteralName(enm, literalName, literalMap);
        literalMap.put(literalName, literal);
        literal.setName(literalName);
        literal.setId(UUID.randomUUID().toString());
        alias = new Alias();
        literal.setAlias(alias);
        alias.setPhysicalName(literalName);

        String value = findAppInfoValue(schemaEnum);
        if (value == null)
          value = literalName;
        literal.setValue(value);

        buildEnumerationLiteralDocumentation(schemaEnum, literal);
      } else
        log.warn("unexpected Restriction child class, " + obj.getClass().getName());
    }
    return enm;
  }

  public void buildEnumerationLiteralDocumentation(org.plasma.xml.schema.Enumeration schemaEnum,
      EnumerationLiteral literal) {
    if (schemaEnum.getAnnotation() != null)
      for (Object o2 : schemaEnum.getAnnotation().getAppinfosAndDocumentations()) {
        if (o2 instanceof org.plasma.xml.schema.Documentation) {
          org.plasma.xml.schema.Documentation doc = (org.plasma.xml.schema.Documentation) o2;
          if (doc.getContent() != null && doc.getContent().size() > 0) {
            for (Object content : doc.getContent()) {
              if (content instanceof String) {
                Documentation documentation = createDocumentation(DocumentationType.DEFINITION,
                    (String) content);
                literal.getDocumentations().add(documentation);
              } else if (content instanceof ElementNSImpl) {
                ElementNSImpl nsElem = (ElementNSImpl) content;
                Documentation documentation = createDocumentation(DocumentationType.DEFINITION,
                    serializeElement(nsElem));
                literal.getDocumentations().add(documentation);
              } else {
                Documentation documentation = createDocumentation(DocumentationType.DEFINITION,
                    content.toString());
                literal.getDocumentations().add(documentation);
              }
            }
          }
        } else
          log.warn("unexpected Enumeration child class, " + o2.getClass().getName());
      }
  }
}
