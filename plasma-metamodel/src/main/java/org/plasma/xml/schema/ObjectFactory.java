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

package org.plasma.xml.schema;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each Java content interface and Java
 * element interface generated in the org.plasma.xml.schema package.
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

  private final static QName _MinInclusive_QNAME = new QName("http://www.w3.org/2001/XMLSchema",
      "minInclusive");
  private final static QName _MaxLength_QNAME = new QName("http://www.w3.org/2001/XMLSchema",
      "maxLength");
  private final static QName _Sequence_QNAME = new QName("http://www.w3.org/2001/XMLSchema",
      "sequence");
  private final static QName _MinLength_QNAME = new QName("http://www.w3.org/2001/XMLSchema",
      "minLength");
  private final static QName _Key_QNAME = new QName("http://www.w3.org/2001/XMLSchema", "key");
  private final static QName _All_QNAME = new QName("http://www.w3.org/2001/XMLSchema", "all");
  private final static QName _Length_QNAME = new QName("http://www.w3.org/2001/XMLSchema", "length");
  private final static QName _Choice_QNAME = new QName("http://www.w3.org/2001/XMLSchema", "choice");
  private final static QName _FractionDigits_QNAME = new QName("http://www.w3.org/2001/XMLSchema",
      "fractionDigits");
  private final static QName _MinExclusive_QNAME = new QName("http://www.w3.org/2001/XMLSchema",
      "minExclusive");
  private final static QName _MaxExclusive_QNAME = new QName("http://www.w3.org/2001/XMLSchema",
      "maxExclusive");
  private final static QName _Unique_QNAME = new QName("http://www.w3.org/2001/XMLSchema", "unique");
  private final static QName _MaxInclusive_QNAME = new QName("http://www.w3.org/2001/XMLSchema",
      "maxInclusive");
  private final static QName _AnyAttribute_QNAME = new QName("http://www.w3.org/2001/XMLSchema",
      "anyAttribute");
  private final static QName _AbstractGroupElement_QNAME = new QName(
      "http://www.w3.org/2001/XMLSchema", "element");
  private final static QName _AbstractGroupGroup_QNAME = new QName(
      "http://www.w3.org/2001/XMLSchema", "group");

  /**
   * Create a new ObjectFactory that can be used to create new instances of
   * schema derived classes for package: org.plasma.xml.schema
   * 
   */
  public ObjectFactory() {
  }

  /**
   * Create an instance of {@link All }
   * 
   */
  public All createAll() {
    return new All();
  }

  /**
   * Create an instance of {@link Attribute }
   * 
   */
  public Attribute createAttribute() {
    return new Attribute();
  }

  /**
   * Create an instance of {@link Field }
   * 
   */
  public Field createField() {
    return new Field();
  }

  /**
   * Create an instance of {@link Selector }
   * 
   */
  public Selector createSelector() {
    return new Selector();
  }

  /**
   * Create an instance of {@link Appinfo }
   * 
   */
  public Appinfo createAppinfo() {
    return new Appinfo();
  }

  /**
   * Create an instance of {@link Facet }
   * 
   */
  public Facet createFacet() {
    return new Facet();
  }

  /**
   * Create an instance of {@link List }
   * 
   */
  public List createList() {
    return new List();
  }

  /**
   * Create an instance of {@link SimpleExplicitGroup }
   * 
   */
  public SimpleExplicitGroup createSimpleExplicitGroup() {
    return new SimpleExplicitGroup();
  }

  /**
   * Create an instance of {@link AttributeGroupRef }
   * 
   */
  public AttributeGroupRef createAttributeGroupRef() {
    return new AttributeGroupRef();
  }

  /**
   * Create an instance of {@link LocalElement }
   * 
   */
  public LocalElement createLocalElement() {
    return new LocalElement();
  }

  /**
   * Create an instance of {@link NoFixedFacet }
   * 
   */
  public NoFixedFacet createNoFixedFacet() {
    return new NoFixedFacet();
  }

  /**
   * Create an instance of {@link Restriction }
   * 
   */
  public Restriction createRestriction() {
    return new Restriction();
  }

  /**
   * Create an instance of {@link ComplexContent }
   * 
   */
  public ComplexContent createComplexContent() {
    return new ComplexContent();
  }

  /**
   * Create an instance of {@link NumFacet }
   * 
   */
  public NumFacet createNumFacet() {
    return new NumFacet();
  }

  /**
   * Create an instance of {@link Group }
   * 
   */
  public Group createGroup() {
    return new Group();
  }

  /**
   * Create an instance of {@link LocalSimpleType }
   * 
   */
  public LocalSimpleType createLocalSimpleType() {
    return new LocalSimpleType();
  }

  /**
   * Create an instance of {@link ExplicitGroup }
   * 
   */
  public ExplicitGroup createExplicitGroup() {
    return new ExplicitGroup();
  }

  /**
   * Create an instance of {@link Keybase }
   * 
   */
  public Keybase createKeybase() {
    return new Keybase();
  }

  /**
   * Create an instance of {@link ComplexType }
   * 
   */
  public ComplexType createComplexType() {
    return new ComplexType();
  }

  /**
   * Create an instance of {@link GroupRef }
   * 
   */
  public GroupRef createGroupRef() {
    return new GroupRef();
  }

  /**
   * Create an instance of {@link Keyref }
   * 
   */
  public Keyref createKeyref() {
    return new Keyref();
  }

  /**
   * Create an instance of {@link RealGroup }
   * 
   */
  public RealGroup createRealGroup() {
    return new RealGroup();
  }

  /**
   * Create an instance of {@link ExtensionType }
   * 
   */
  public ExtensionType createExtensionType() {
    return new ExtensionType();
  }

  /**
   * Create an instance of {@link Import }
   * 
   */
  public Import createImport() {
    return new Import();
  }

  /**
   * Create an instance of {@link SimpleRestrictionType }
   * 
   */
  public SimpleRestrictionType createSimpleRestrictionType() {
    return new SimpleRestrictionType();
  }

  /**
   * Create an instance of {@link Any }
   * 
   */
  public Any createAny() {
    return new Any();
  }

  /**
   * Create an instance of {@link Element }
   * 
   */
  public Element createElement() {
    return new Element();
  }

  /**
   * Create an instance of {@link NarrowMaxMin }
   * 
   */
  public NarrowMaxMin createNarrowMaxMin() {
    return new NarrowMaxMin();
  }

  /**
   * Create an instance of {@link Redefine }
   * 
   */
  public Redefine createRedefine() {
    return new Redefine();
  }

  /**
   * Create an instance of {@link SimpleContent }
   * 
   */
  public SimpleContent createSimpleContent() {
    return new SimpleContent();
  }

  /**
   * Create an instance of {@link LocalComplexType }
   * 
   */
  public LocalComplexType createLocalComplexType() {
    return new LocalComplexType();
  }

  /**
   * Create an instance of {@link WhiteSpace }
   * 
   */
  public WhiteSpace createWhiteSpace() {
    return new WhiteSpace();
  }

  /**
   * Create an instance of {@link TotalDigits }
   * 
   */
  public TotalDigits createTotalDigits() {
    return new TotalDigits();
  }

  /**
   * Create an instance of {@link OpenAttrs }
   * 
   */
  public OpenAttrs createOpenAttrs() {
    return new OpenAttrs();
  }

  /**
   * Create an instance of {@link Wildcard }
   * 
   */
  public Wildcard createWildcard() {
    return new Wildcard();
  }

  /**
   * Create an instance of {@link Include }
   * 
   */
  public Include createInclude() {
    return new Include();
  }

  /**
   * Create an instance of {@link Schema }
   * 
   */
  public Schema createSchema() {
    return new Schema();
  }

  /**
   * Create an instance of {@link Enumeration }
   * 
   */
  public Enumeration createEnumeration() {
    return new Enumeration();
  }

  /**
   * Create an instance of {@link AttributeGroup }
   * 
   */
  public AttributeGroup createAttributeGroup() {
    return new AttributeGroup();
  }

  /**
   * Create an instance of {@link SimpleType }
   * 
   */
  public SimpleType createSimpleType() {
    return new SimpleType();
  }

  /**
   * Create an instance of {@link Notation }
   * 
   */
  public Notation createNotation() {
    return new Notation();
  }

  /**
   * Create an instance of {@link RestrictionType }
   * 
   */
  public RestrictionType createRestrictionType() {
    return new RestrictionType();
  }

  /**
   * Create an instance of {@link TopLevelAttributeType }
   * 
   */
  public TopLevelAttributeType createTopLevelAttributeType() {
    return new TopLevelAttributeType();
  }

  /**
   * Create an instance of {@link Documentation }
   * 
   */
  public Documentation createDocumentation() {
    return new Documentation();
  }

  /**
   * Create an instance of {@link SimpleExtensionType }
   * 
   */
  public SimpleExtensionType createSimpleExtensionType() {
    return new SimpleExtensionType();
  }

  /**
   * Create an instance of {@link Union }
   * 
   */
  public Union createUnion() {
    return new Union();
  }

  /**
   * Create an instance of {@link Annotation }
   * 
   */
  public Annotation createAnnotation() {
    return new Annotation();
  }

  /**
   * Create an instance of {@link ComplexRestrictionType }
   * 
   */
  public ComplexRestrictionType createComplexRestrictionType() {
    return new ComplexRestrictionType();
  }

  /**
   * Create an instance of {@link Pattern }
   * 
   */
  public Pattern createPattern() {
    return new Pattern();
  }

  /**
   * Create an instance of {@link Annotated }
   * 
   */
  public Annotated createAnnotated() {
    return new Annotated();
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link Facet }{@code >}
   * 
   */
  @XmlElementDecl(namespace = "http://www.w3.org/2001/XMLSchema", name = "minInclusive")
  public JAXBElement<Facet> createMinInclusive(Facet value) {
    return new JAXBElement<Facet>(_MinInclusive_QNAME, Facet.class, null, value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link NumFacet }{@code >}
   * 
   */
  @XmlElementDecl(namespace = "http://www.w3.org/2001/XMLSchema", name = "maxLength")
  public JAXBElement<NumFacet> createMaxLength(NumFacet value) {
    return new JAXBElement<NumFacet>(_MaxLength_QNAME, NumFacet.class, null, value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link ExplicitGroup }
   * {@code >}
   * 
   */
  @XmlElementDecl(namespace = "http://www.w3.org/2001/XMLSchema", name = "sequence")
  public JAXBElement<ExplicitGroup> createSequence(ExplicitGroup value) {
    return new JAXBElement<ExplicitGroup>(_Sequence_QNAME, ExplicitGroup.class, null, value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link NumFacet }{@code >}
   * 
   */
  @XmlElementDecl(namespace = "http://www.w3.org/2001/XMLSchema", name = "minLength")
  public JAXBElement<NumFacet> createMinLength(NumFacet value) {
    return new JAXBElement<NumFacet>(_MinLength_QNAME, NumFacet.class, null, value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link Keybase }{@code >}
   * 
   */
  @XmlElementDecl(namespace = "http://www.w3.org/2001/XMLSchema", name = "key")
  public JAXBElement<Keybase> createKey(Keybase value) {
    return new JAXBElement<Keybase>(_Key_QNAME, Keybase.class, null, value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link All }{@code >}
   * 
   */
  @XmlElementDecl(namespace = "http://www.w3.org/2001/XMLSchema", name = "all")
  public JAXBElement<All> createAll(All value) {
    return new JAXBElement<All>(_All_QNAME, All.class, null, value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link NumFacet }{@code >}
   * 
   */
  @XmlElementDecl(namespace = "http://www.w3.org/2001/XMLSchema", name = "length")
  public JAXBElement<NumFacet> createLength(NumFacet value) {
    return new JAXBElement<NumFacet>(_Length_QNAME, NumFacet.class, null, value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link ExplicitGroup }
   * {@code >}
   * 
   */
  @XmlElementDecl(namespace = "http://www.w3.org/2001/XMLSchema", name = "choice")
  public JAXBElement<ExplicitGroup> createChoice(ExplicitGroup value) {
    return new JAXBElement<ExplicitGroup>(_Choice_QNAME, ExplicitGroup.class, null, value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link NumFacet }{@code >}
   * 
   */
  @XmlElementDecl(namespace = "http://www.w3.org/2001/XMLSchema", name = "fractionDigits")
  public JAXBElement<NumFacet> createFractionDigits(NumFacet value) {
    return new JAXBElement<NumFacet>(_FractionDigits_QNAME, NumFacet.class, null, value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link Facet }{@code >}
   * 
   */
  @XmlElementDecl(namespace = "http://www.w3.org/2001/XMLSchema", name = "minExclusive")
  public JAXBElement<Facet> createMinExclusive(Facet value) {
    return new JAXBElement<Facet>(_MinExclusive_QNAME, Facet.class, null, value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link Facet }{@code >}
   * 
   */
  @XmlElementDecl(namespace = "http://www.w3.org/2001/XMLSchema", name = "maxExclusive")
  public JAXBElement<Facet> createMaxExclusive(Facet value) {
    return new JAXBElement<Facet>(_MaxExclusive_QNAME, Facet.class, null, value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link Keybase }{@code >}
   * 
   */
  @XmlElementDecl(namespace = "http://www.w3.org/2001/XMLSchema", name = "unique")
  public JAXBElement<Keybase> createUnique(Keybase value) {
    return new JAXBElement<Keybase>(_Unique_QNAME, Keybase.class, null, value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link Facet }{@code >}
   * 
   */
  @XmlElementDecl(namespace = "http://www.w3.org/2001/XMLSchema", name = "maxInclusive")
  public JAXBElement<Facet> createMaxInclusive(Facet value) {
    return new JAXBElement<Facet>(_MaxInclusive_QNAME, Facet.class, null, value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link Wildcard }{@code >}
   * 
   */
  @XmlElementDecl(namespace = "http://www.w3.org/2001/XMLSchema", name = "anyAttribute")
  public JAXBElement<Wildcard> createAnyAttribute(Wildcard value) {
    return new JAXBElement<Wildcard>(_AnyAttribute_QNAME, Wildcard.class, null, value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link LocalElement }
   * {@code >}
   * 
   */
  @XmlElementDecl(namespace = "http://www.w3.org/2001/XMLSchema", name = "element", scope = AbstractGroup.class)
  public JAXBElement<LocalElement> createAbstractGroupElement(LocalElement value) {
    return new JAXBElement<LocalElement>(_AbstractGroupElement_QNAME, LocalElement.class,
        AbstractGroup.class, value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link GroupRef }{@code >}
   * 
   */
  @XmlElementDecl(namespace = "http://www.w3.org/2001/XMLSchema", name = "group", scope = AbstractGroup.class)
  public JAXBElement<GroupRef> createAbstractGroupGroup(GroupRef value) {
    return new JAXBElement<GroupRef>(_AbstractGroupGroup_QNAME, GroupRef.class,
        AbstractGroup.class, value);
  }

}
