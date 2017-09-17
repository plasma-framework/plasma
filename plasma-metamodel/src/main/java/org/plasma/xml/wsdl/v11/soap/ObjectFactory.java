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

package org.plasma.xml.wsdl.v11.soap;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each Java content interface and Java
 * element interface generated in the org.plasma.xml.wsdl.v11.soap package.
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

  private final static QName _Body_QNAME = new QName("http://schemas.xmlsoap.org/wsdl/soap/",
      "body");

  /**
   * Create a new ObjectFactory that can be used to create new instances of
   * schema derived classes for package: org.plasma.xml.wsdl.v11.soap
   * 
   */
  public ObjectFactory() {
  }

  /**
   * Create an instance of {@link Fault }
   * 
   */
  public Fault createFault() {
    return new Fault();
  }

  /**
   * Create an instance of {@link Headerfault }
   * 
   */
  public Headerfault createHeaderfault() {
    return new Headerfault();
  }

  /**
   * Create an instance of {@link TBody }
   * 
   */
  public TBody createTBody() {
    return new TBody();
  }

  /**
   * Create an instance of {@link Header }
   * 
   */
  public Header createHeader() {
    return new Header();
  }

  /**
   * Create an instance of {@link Operation }
   * 
   */
  public Operation createOperation() {
    return new Operation();
  }

  /**
   * Create an instance of {@link Binding }
   * 
   */
  public Binding createBinding() {
    return new Binding();
  }

  /**
   * Create an instance of {@link Address }
   * 
   */
  public Address createAddress() {
    return new Address();
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link TBody }{@code >}
   * 
   */
  @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/wsdl/soap/", name = "body")
  public JAXBElement<TBody> createBody(TBody value) {
    return new JAXBElement<TBody>(_Body_QNAME, TBody.class, null, value);
  }

}
