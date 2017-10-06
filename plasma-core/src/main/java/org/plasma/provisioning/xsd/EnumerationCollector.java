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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.plasma.metamodel.Enumeration;
import org.plasma.xml.schema.AbstractSimpleType;
import org.plasma.xml.schema.Schema;
import org.plasma.xml.schema.SimpleType;
import org.plasma.xml.schema.SimpleTypeVisitor;

/**
 * Provisioning enumeration collector (simple type visitor) which receives
 * visitor events and and calls a delegate assembler/builder for enumerations,
 * storing the enumeration for clients.
 * 
 * @see SimpleTypeVisitor
 */
public class EnumerationCollector extends AbstractCollector implements SimpleTypeVisitor {

  private Schema schema;
  private Map<String, SimpleType> simpleTypeMap;
  private EnumerationAssembler assembler;
  private List<Enumeration> result = new ArrayList<Enumeration>();

  public EnumerationCollector(Schema schema, Map<String, SimpleType> simpleTypeMap,
      EnumerationAssembler enumerationAssembler) {
    super();
    this.schema = schema;
    this.simpleTypeMap = simpleTypeMap;
    this.assembler = enumerationAssembler;
  }

  public List<Enumeration> getResult() {
    return result;
  }

  @Override
  public void visit(AbstractSimpleType target, AbstractSimpleType source, int level) {
    if (target.getRestriction() != null && SimpleTypeUtils.isEnumeration(target)) {
      Enumeration enumeration = this.assembler.buildEnumeration(target, source);
      result.add(enumeration);
    }
  }

  @Override
  public SimpleType getTopLevelSimpleType(QName name) {
    return simpleTypeMap.get(name.getLocalPart());
  }

  @Override
  public String getTargetNamespace() {
    return schema.getTargetNamespace();
  }

}
