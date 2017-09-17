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

package org.plasma.provisioning;

import java.util.Iterator;

import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.metamodel.Model;
import org.plasma.provisioning.xsd.SDOXSchemaConverter;
import org.plasma.provisioning.xsd.XSDSchemaConverter;
import org.plasma.xml.schema.OpenAttrs;
import org.plasma.xml.schema.Schema;
import org.plasma.xml.sdox.SDOXConstants;

/**
 * Constructs a provisioning model based on the a given XML Schema
 */
public class SchemaMetamodelAssembler {
  private static Log log = LogFactory.getLog(SchemaMetamodelAssembler.class);

  private SchemaConverter processor;

  @SuppressWarnings("unused")
  private SchemaMetamodelAssembler() {
  }

  public SchemaMetamodelAssembler(Schema schema, String destNamespaceURI, String destNamespacePrefix) {
    QName sdoxNamespace = findOpenAttributeQNameByValue(SDOXConstants.SDOX_NAMESPACE_URI, schema);

    if (sdoxNamespace != null) {
      processor = new SDOXSchemaConverter(schema, destNamespaceURI, destNamespacePrefix);
    } else {
      processor = new XSDSchemaConverter(schema, destNamespaceURI, destNamespacePrefix);
    }
  }

  private QName findOpenAttributeQNameByValue(String value, OpenAttrs attrs) {
    Iterator<QName> iter = attrs.getOtherAttributes().keySet().iterator();
    while (iter.hasNext()) {
      QName key = iter.next();
      String s = attrs.getOtherAttributes().get(key);
      if (s != null && s.equals(value))
        return key;
    }
    return null;
  }

  public Model getModel() {
    return processor.buildModel();
  }
}
