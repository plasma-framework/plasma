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

import java.util.UUID;

import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.common.provisioning.NameUtils;
import org.plasma.metamodel.Alias;
import org.plasma.metamodel.Class;
import org.plasma.metamodel.ClassRef;
import org.plasma.metamodel.Documentation;
import org.plasma.metamodel.DocumentationType;
import org.plasma.xml.schema.ComplexRestrictionType;
import org.plasma.xml.schema.ComplexType;
import org.plasma.xml.schema.Element;
import org.plasma.xml.schema.ExtensionType;
import org.plasma.xml.schema.SchemaUtil;

public class ClassAssembler extends AbstractAssembler {
  private static Log log = LogFactory.getLog(ClassAssembler.class);

  public ClassAssembler(ConverterSupport converterSupport, String destNamespaceURI,
      String destNamespacePrefix) {
    super(destNamespaceURI, destNamespacePrefix, converterSupport);
  }

  public Class buildClass(ComplexType complexType) {
    Class clss = new Class();
    clss.setId(UUID.randomUUID().toString());
    clss.setUri(this.destNamespaceURI); // FIXME - what if we collect multiple
                                        // URI's in the query
    Alias alias = new Alias();
    clss.setAlias(alias);
    alias.setLocalName(complexType.getName()); // because XML schema
                                               // "projection" names could
                                               // differ

    String name = formatLocalClassName(complexType.getName());
    clss.setName(name);

    String physicalNameAlias = NameUtils.toAbbreviatedName(name);
    alias.setPhysicalName(physicalNameAlias); // Note: SDOX only allows one
                                              // alias name, hence we expect
                                              // PhysicalName

    Documentation documentation = createDocumentation(DocumentationType.DEFINITION,
        getDocumentationContent(complexType));
    clss.getDocumentations().add(documentation);

    if (complexType.getComplexContent() != null) { // has a base type
      ExtensionType extension = complexType.getComplexContent().getExtension();
      if (extension != null) {
        QName base = extension.getBase();
        if (base != null && !base.getLocalPart().equals(SchemaUtil.getSerializationBaseTypeName())) {
          ClassRef baseRef = new ClassRef();
          String formattedClassName = formatLocalClassName(base.getLocalPart());
          baseRef.setName(formattedClassName);
          baseRef.setUri(this.destNamespaceURI);
          clss.getSuperClasses().add(baseRef);
        }
      }
      ComplexRestrictionType restriction = complexType.getComplexContent().getRestriction();
      if (restriction != null) {
        QName base = restriction.getBase();
        if (base != null && !base.getLocalPart().equals(SchemaUtil.getSerializationBaseTypeName())) {
          ClassRef baseRef = new ClassRef();
          String formattedClassName = formatLocalClassName(base.getLocalPart());
          baseRef.setName(formattedClassName);
          baseRef.setUri(this.destNamespaceURI);
          clss.getSuperClasses().add(baseRef);
        }
      }
    }

    return clss;
  }

  public Class buildClass(Element element) {
    Class clss = new Class();
    clss.setId(UUID.randomUUID().toString());
    clss.setUri(this.destNamespaceURI); // FIXME - what if we collect multiple
                                        // URI's
    Alias alias = new Alias();
    clss.setAlias(alias);
    alias.setLocalName(element.getName()); // because XML schema "projection"
                                           // names could differ

    String name = formatLocalClassName(element.getName());
    clss.setName(name);

    String physicalNameAlias = NameUtils.toAbbreviatedName(name);
    alias.setPhysicalName(physicalNameAlias);

    Documentation documentation = createDocumentation(DocumentationType.DEFINITION,
        getDocumentationContent(element));
    clss.getDocumentations().add(documentation);

    if (element.getComplexType() != null && element.getComplexType().getComplexContent() != null) { // has
                                                                                                    // a
                                                                                                    // base
                                                                                                    // type
      ExtensionType baseType = element.getComplexType().getComplexContent().getExtension();
      QName base = baseType.getBase();
      if (!base.getLocalPart().equals(SchemaUtil.getSerializationBaseTypeName())) {
        ClassRef baseRef = new ClassRef();
        String formattedClassName = formatLocalClassName(base.getLocalPart());
        baseRef.setName(formattedClassName);
        baseRef.setUri(this.destNamespaceURI);
        clss.getSuperClasses().add(baseRef);
      }
    } else if (element.getSubstitutionGroup() != null) {
      QName base = element.getSubstitutionGroup();
      if (!base.getLocalPart().equals(SchemaUtil.getSerializationBaseTypeName())) {
        ClassRef baseRef = new ClassRef();
        String formattedClassName = formatLocalClassName(base.getLocalPart());
        baseRef.setName(formattedClassName);
        baseRef.setUri(this.destNamespaceURI);
        clss.getSuperClasses().add(baseRef);
      }
    }

    if (log.isDebugEnabled())
      log.debug("created class: " + clss.getUri() + "#" + clss.getName());

    return clss;
  }
}
