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

package org.plasma.xml.wsdl.v20;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each Java content interface and Java
 * element interface generated in the org.plasma.xml.wsdl.v20 package.
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

  private final static QName _InterfaceOperation_QNAME = new QName("http://www.w3.org/ns/wsdl",
      "operation");
  private final static QName _InterfaceFault_QNAME = new QName("http://www.w3.org/ns/wsdl", "fault");
  private final static QName _BindingOperationTypeOutfault_QNAME = new QName(
      "http://www.w3.org/ns/wsdl", "outfault");
  private final static QName _BindingOperationTypeInput_QNAME = new QName(
      "http://www.w3.org/ns/wsdl", "input");
  private final static QName _BindingOperationTypeOutput_QNAME = new QName(
      "http://www.w3.org/ns/wsdl", "output");
  private final static QName _BindingOperationTypeInfault_QNAME = new QName(
      "http://www.w3.org/ns/wsdl", "infault");

  /**
   * Create a new ObjectFactory that can be used to create new instances of
   * schema derived classes for package: org.plasma.xml.wsdl.v20
   * 
   */
  public ObjectFactory() {
  }

  /**
   * Create an instance of {@link MessageRefFaultType }
   * 
   */
  public MessageRefFaultType createMessageRefFaultType() {
    return new MessageRefFaultType();
  }

  /**
   * Create an instance of {@link BindingOperationMessageType }
   * 
   */
  public BindingOperationMessageType createBindingOperationMessageType() {
    return new BindingOperationMessageType();
  }

  /**
   * Create an instance of {@link Service }
   * 
   */
  public Service createService() {
    return new Service();
  }

  /**
   * Create an instance of {@link Binding }
   * 
   */
  public Binding createBinding() {
    return new Binding();
  }

  /**
   * Create an instance of {@link BindingOperationType }
   * 
   */
  public BindingOperationType createBindingOperationType() {
    return new BindingOperationType();
  }

  /**
   * Create an instance of {@link InterfaceOperationType }
   * 
   */
  public InterfaceOperationType createInterfaceOperationType() {
    return new InterfaceOperationType();
  }

  /**
   * Create an instance of {@link BindingFaultType }
   * 
   */
  public BindingFaultType createBindingFaultType() {
    return new BindingFaultType();
  }

  /**
   * Create an instance of {@link Include }
   * 
   */
  public Include createInclude() {
    return new Include();
  }

  /**
   * Create an instance of {@link Interface }
   * 
   */
  public Interface createInterface() {
    return new Interface();
  }

  /**
   * Create an instance of {@link BindingOperationFaultType }
   * 
   */
  public BindingOperationFaultType createBindingOperationFaultType() {
    return new BindingOperationFaultType();
  }

  /**
   * Create an instance of {@link Types }
   * 
   */
  public Types createTypes() {
    return new Types();
  }

  /**
   * Create an instance of {@link MessageRefType }
   * 
   */
  public MessageRefType createMessageRefType() {
    return new MessageRefType();
  }

  /**
   * Create an instance of {@link Description }
   * 
   */
  public Description createDescription() {
    return new Description();
  }

  /**
   * Create an instance of {@link InterfaceFaultType }
   * 
   */
  public InterfaceFaultType createInterfaceFaultType() {
    return new InterfaceFaultType();
  }

  /**
   * Create an instance of {@link Documentation }
   * 
   */
  public Documentation createDocumentation() {
    return new Documentation();
  }

  /**
   * Create an instance of {@link Endpoint }
   * 
   */
  public Endpoint createEndpoint() {
    return new Endpoint();
  }

  /**
   * Create an instance of {@link DocumentedType }
   * 
   */
  public DocumentedType createDocumentedType() {
    return new DocumentedType();
  }

  /**
   * Create an instance of {@link Import }
   * 
   */
  public Import createImport() {
    return new Import();
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}
   * {@link InterfaceOperationType }{@code >}
   * 
   */
  @XmlElementDecl(namespace = "http://www.w3.org/ns/wsdl", name = "operation", scope = Interface.class)
  public JAXBElement<InterfaceOperationType> createInterfaceOperation(InterfaceOperationType value) {
    return new JAXBElement<InterfaceOperationType>(_InterfaceOperation_QNAME,
        InterfaceOperationType.class, Interface.class, value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}
   * {@link InterfaceFaultType }{@code >}
   * 
   */
  @XmlElementDecl(namespace = "http://www.w3.org/ns/wsdl", name = "fault", scope = Interface.class)
  public JAXBElement<InterfaceFaultType> createInterfaceFault(InterfaceFaultType value) {
    return new JAXBElement<InterfaceFaultType>(_InterfaceFault_QNAME, InterfaceFaultType.class,
        Interface.class, value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}
   * {@link BindingOperationFaultType }{@code >}
   * 
   */
  @XmlElementDecl(namespace = "http://www.w3.org/ns/wsdl", name = "outfault", scope = BindingOperationType.class)
  public JAXBElement<BindingOperationFaultType> createBindingOperationTypeOutfault(
      BindingOperationFaultType value) {
    return new JAXBElement<BindingOperationFaultType>(_BindingOperationTypeOutfault_QNAME,
        BindingOperationFaultType.class, BindingOperationType.class, value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}
   * {@link BindingOperationMessageType }{@code >}
   * 
   */
  @XmlElementDecl(namespace = "http://www.w3.org/ns/wsdl", name = "input", scope = BindingOperationType.class)
  public JAXBElement<BindingOperationMessageType> createBindingOperationTypeInput(
      BindingOperationMessageType value) {
    return new JAXBElement<BindingOperationMessageType>(_BindingOperationTypeInput_QNAME,
        BindingOperationMessageType.class, BindingOperationType.class, value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}
   * {@link BindingOperationMessageType }{@code >}
   * 
   */
  @XmlElementDecl(namespace = "http://www.w3.org/ns/wsdl", name = "output", scope = BindingOperationType.class)
  public JAXBElement<BindingOperationMessageType> createBindingOperationTypeOutput(
      BindingOperationMessageType value) {
    return new JAXBElement<BindingOperationMessageType>(_BindingOperationTypeOutput_QNAME,
        BindingOperationMessageType.class, BindingOperationType.class, value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}
   * {@link BindingOperationFaultType }{@code >}
   * 
   */
  @XmlElementDecl(namespace = "http://www.w3.org/ns/wsdl", name = "infault", scope = BindingOperationType.class)
  public JAXBElement<BindingOperationFaultType> createBindingOperationTypeInfault(
      BindingOperationFaultType value) {
    return new JAXBElement<BindingOperationFaultType>(_BindingOperationTypeInfault_QNAME,
        BindingOperationFaultType.class, BindingOperationType.class, value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}
   * {@link BindingOperationType }{@code >}
   * 
   */
  @XmlElementDecl(namespace = "http://www.w3.org/ns/wsdl", name = "operation", scope = Binding.class)
  public JAXBElement<BindingOperationType> createBindingOperation(BindingOperationType value) {
    return new JAXBElement<BindingOperationType>(_InterfaceOperation_QNAME,
        BindingOperationType.class, Binding.class, value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link BindingFaultType }
   * {@code >}
   * 
   */
  @XmlElementDecl(namespace = "http://www.w3.org/ns/wsdl", name = "fault", scope = Binding.class)
  public JAXBElement<BindingFaultType> createBindingFault(BindingFaultType value) {
    return new JAXBElement<BindingFaultType>(_InterfaceFault_QNAME, BindingFaultType.class,
        Binding.class, value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}
   * {@link MessageRefFaultType }{@code >}
   * 
   */
  @XmlElementDecl(namespace = "http://www.w3.org/ns/wsdl", name = "outfault", scope = InterfaceOperationType.class)
  public JAXBElement<MessageRefFaultType> createInterfaceOperationTypeOutfault(
      MessageRefFaultType value) {
    return new JAXBElement<MessageRefFaultType>(_BindingOperationTypeOutfault_QNAME,
        MessageRefFaultType.class, InterfaceOperationType.class, value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link MessageRefType }
   * {@code >}
   * 
   */
  @XmlElementDecl(namespace = "http://www.w3.org/ns/wsdl", name = "input", scope = InterfaceOperationType.class)
  public JAXBElement<MessageRefType> createInterfaceOperationTypeInput(MessageRefType value) {
    return new JAXBElement<MessageRefType>(_BindingOperationTypeInput_QNAME, MessageRefType.class,
        InterfaceOperationType.class, value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link MessageRefType }
   * {@code >}
   * 
   */
  @XmlElementDecl(namespace = "http://www.w3.org/ns/wsdl", name = "output", scope = InterfaceOperationType.class)
  public JAXBElement<MessageRefType> createInterfaceOperationTypeOutput(MessageRefType value) {
    return new JAXBElement<MessageRefType>(_BindingOperationTypeOutput_QNAME, MessageRefType.class,
        InterfaceOperationType.class, value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}
   * {@link MessageRefFaultType }{@code >}
   * 
   */
  @XmlElementDecl(namespace = "http://www.w3.org/ns/wsdl", name = "infault", scope = InterfaceOperationType.class)
  public JAXBElement<MessageRefFaultType> createInterfaceOperationTypeInfault(
      MessageRefFaultType value) {
    return new JAXBElement<MessageRefFaultType>(_BindingOperationTypeInfault_QNAME,
        MessageRefFaultType.class, InterfaceOperationType.class, value);
  }

}
