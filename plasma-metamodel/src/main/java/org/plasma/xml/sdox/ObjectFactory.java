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

package org.plasma.xml.sdox;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each Java content interface and Java
 * element interface generated in the org.plasma.xml.sdox package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the
 * Java representation for XML content. The Java representation of XML content
 * can consist of schema derived interfaces and classes representing the binding
 * of schema type definitions, element declarations and model groups. Factory
 * methods for each of these are provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

  private final static QName _DataObject_QNAME = new QName("commonj.sdo", "dataObject");

  /**
   * Create a new ObjectFactory that can be used to create new instances of
   * schema derived classes for package: org.plasma.xml.sdox
   * 
   */
  public ObjectFactory() {
  }

  /**
   * Create an instance of {@link ModelsType }
   * 
   */
  public ModelsType createModelsType() {
    return new ModelsType();
  }

  /**
   * Create an instance of {@link ChangeSummaryType }
   * 
   */
  public ChangeSummaryType createChangeSummaryType() {
    return new ChangeSummaryType();
  }

  /**
   * Create an instance of {@link XSDType }
   * 
   */
  public XSDType createXSDType() {
    return new XSDType();
  }

  /**
   * Create an instance of {@link Types }
   * 
   */
  public Types createTypes() {
    return new Types();
  }

  /**
   * Create an instance of {@link Datagraph }
   * 
   */
  public Datagraph createDatagraph() {
    return new Datagraph();
  }

  /**
   * Create an instance of {@link Property }
   * 
   */
  public Property createProperty() {
    return new Property();
  }

  /**
   * Create an instance of {@link Type }
   * 
   */
  public Type createType() {
    return new Type();
  }

  /**
   * Create an instance of {@link XMLInfo }
   * 
   */
  public XMLInfo createXMLInfo() {
    return new XMLInfo();
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}
   * 
   */
  @XmlElementDecl(namespace = "commonj.sdo", name = "dataObject")
  public JAXBElement<Object> createDataObject(Object value) {
    return new JAXBElement<Object>(_DataObject_QNAME, Object.class, null, value);
  }

}
