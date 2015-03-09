/**
 *         PlasmaSDO™ License
 * 
 * This is a community release of PlasmaSDO™, a dual-license 
 * Service Data Object (SDO) 2.1 implementation. 
 * This particular copy of the software is released under the 
 * version 2 of the GNU General Public License. PlasmaSDO™ was developed by 
 * TerraMeta Software, Inc.
 * 
 * Copyright (c) 2013, TerraMeta Software, Inc. All rights reserved.
 * 
 * General License information can be found below.
 * 
 * This distribution may include materials developed by third
 * parties. For license and attribution notices for these
 * materials, please refer to the documentation that accompanies
 * this distribution (see the "Licenses for Third-Party Components"
 * appendix) or view the online documentation at 
 * <http://plasma-sdo.org/licenses/>.
 *  
 */
package org.plasma.xml.schema;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;


/**
 * <p>Java class for restrictionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="restrictionType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.w3.org/2001/XMLSchema}annotated">
 *       &lt;sequence>
 *         &lt;choice minOccurs="0">
 *           &lt;group ref="{http://www.w3.org/2001/XMLSchema}typeDefParticle"/>
 *           &lt;group ref="{http://www.w3.org/2001/XMLSchema}simpleRestrictionModel"/>
 *         &lt;/choice>
 *         &lt;group ref="{http://www.w3.org/2001/XMLSchema}attrDecls"/>
 *       &lt;/sequence>
 *       &lt;attribute name="base" use="required" type="{http://www.w3.org/2001/XMLSchema}QName" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "restrictionType", propOrder = {
    "simpleType",
    "minExclusivesAndMinInclusivesAndMaxExclusives",
    "sequence",
    "choice",
    "all",
    "group",
    "attributesAndAttributeGroups",
    "anyAttribute"
})
public class RestrictionType
    extends Annotated
{

    protected LocalSimpleType simpleType;
    @XmlElementRefs({
        @XmlElementRef(name = "enumeration", namespace = "http://www.w3.org/2001/XMLSchema", type = Enumeration.class),
        @XmlElementRef(name = "maxExclusive", namespace = "http://www.w3.org/2001/XMLSchema", type = JAXBElement.class),
        @XmlElementRef(name = "minExclusive", namespace = "http://www.w3.org/2001/XMLSchema", type = JAXBElement.class),
        @XmlElementRef(name = "whiteSpace", namespace = "http://www.w3.org/2001/XMLSchema", type = WhiteSpace.class),
        @XmlElementRef(name = "minInclusive", namespace = "http://www.w3.org/2001/XMLSchema", type = JAXBElement.class),
        @XmlElementRef(name = "totalDigits", namespace = "http://www.w3.org/2001/XMLSchema", type = TotalDigits.class),
        @XmlElementRef(name = "fractionDigits", namespace = "http://www.w3.org/2001/XMLSchema", type = JAXBElement.class),
        @XmlElementRef(name = "minLength", namespace = "http://www.w3.org/2001/XMLSchema", type = JAXBElement.class),
        @XmlElementRef(name = "length", namespace = "http://www.w3.org/2001/XMLSchema", type = JAXBElement.class),
        @XmlElementRef(name = "pattern", namespace = "http://www.w3.org/2001/XMLSchema", type = Pattern.class),
        @XmlElementRef(name = "maxInclusive", namespace = "http://www.w3.org/2001/XMLSchema", type = JAXBElement.class),
        @XmlElementRef(name = "maxLength", namespace = "http://www.w3.org/2001/XMLSchema", type = JAXBElement.class)
    })
    protected List<Object> minExclusivesAndMinInclusivesAndMaxExclusives;
    protected ExplicitGroup sequence;
    protected ExplicitGroup choice;
    protected All all;
    protected GroupRef group;
    @XmlElements({
        @XmlElement(name = "attribute", type = Attribute.class),
        @XmlElement(name = "attributeGroup", type = AttributeGroupRef.class)
    })
    protected List<Annotated> attributesAndAttributeGroups;
    protected Wildcard anyAttribute;
    @XmlAttribute(required = true)
    protected QName base;

    /**
     * Gets the value of the simpleType property.
     * 
     * @return
     *     possible object is
     *     {@link LocalSimpleType }
     *     
     */
    public LocalSimpleType getSimpleType() {
        return simpleType;
    }

    /**
     * Sets the value of the simpleType property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocalSimpleType }
     *     
     */
    public void setSimpleType(LocalSimpleType value) {
        this.simpleType = value;
    }

    /**
     * Gets the value of the minExclusivesAndMinInclusivesAndMaxExclusives property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the minExclusivesAndMinInclusivesAndMaxExclusives property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMinExclusivesAndMinInclusivesAndMaxExclusives().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Enumeration }
     * {@link JAXBElement }{@code <}{@link Facet }{@code >}
     * {@link JAXBElement }{@code <}{@link Facet }{@code >}
     * {@link WhiteSpace }
     * {@link TotalDigits }
     * {@link JAXBElement }{@code <}{@link Facet }{@code >}
     * {@link JAXBElement }{@code <}{@link NumFacet }{@code >}
     * {@link Pattern }
     * {@link JAXBElement }{@code <}{@link NumFacet }{@code >}
     * {@link JAXBElement }{@code <}{@link NumFacet }{@code >}
     * {@link JAXBElement }{@code <}{@link Facet }{@code >}
     * {@link JAXBElement }{@code <}{@link NumFacet }{@code >}
     * 
     * 
     */
    public List<Object> getMinExclusivesAndMinInclusivesAndMaxExclusives() {
        if (minExclusivesAndMinInclusivesAndMaxExclusives == null) {
            minExclusivesAndMinInclusivesAndMaxExclusives = new ArrayList<Object>();
        }
        return this.minExclusivesAndMinInclusivesAndMaxExclusives;
    }

    /**
     * Gets the value of the sequence property.
     * 
     * @return
     *     possible object is
     *     {@link ExplicitGroup }
     *     
     */
    public ExplicitGroup getSequence() {
        return sequence;
    }

    /**
     * Sets the value of the sequence property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExplicitGroup }
     *     
     */
    public void setSequence(ExplicitGroup value) {
        this.sequence = value;
    }

    /**
     * Gets the value of the choice property.
     * 
     * @return
     *     possible object is
     *     {@link ExplicitGroup }
     *     
     */
    public ExplicitGroup getChoice() {
        return choice;
    }

    /**
     * Sets the value of the choice property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExplicitGroup }
     *     
     */
    public void setChoice(ExplicitGroup value) {
        this.choice = value;
    }

    /**
     * Gets the value of the all property.
     * 
     * @return
     *     possible object is
     *     {@link All }
     *     
     */
    public All getAll() {
        return all;
    }

    /**
     * Sets the value of the all property.
     * 
     * @param value
     *     allowed object is
     *     {@link All }
     *     
     */
    public void setAll(All value) {
        this.all = value;
    }

    /**
     * Gets the value of the group property.
     * 
     * @return
     *     possible object is
     *     {@link GroupRef }
     *     
     */
    public GroupRef getGroup() {
        return group;
    }

    /**
     * Sets the value of the group property.
     * 
     * @param value
     *     allowed object is
     *     {@link GroupRef }
     *     
     */
    public void setGroup(GroupRef value) {
        this.group = value;
    }

    /**
     * Gets the value of the attributesAndAttributeGroups property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attributesAndAttributeGroups property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttributesAndAttributeGroups().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Attribute }
     * {@link AttributeGroupRef }
     * 
     * 
     */
    public List<Annotated> getAttributesAndAttributeGroups() {
        if (attributesAndAttributeGroups == null) {
            attributesAndAttributeGroups = new ArrayList<Annotated>();
        }
        return this.attributesAndAttributeGroups;
    }

    /**
     * Gets the value of the anyAttribute property.
     * 
     * @return
     *     possible object is
     *     {@link Wildcard }
     *     
     */
    public Wildcard getAnyAttribute() {
        return anyAttribute;
    }

    /**
     * Sets the value of the anyAttribute property.
     * 
     * @param value
     *     allowed object is
     *     {@link Wildcard }
     *     
     */
    public void setAnyAttribute(Wildcard value) {
        this.anyAttribute = value;
    }

    /**
     * Gets the value of the base property.
     * 
     * @return
     *     possible object is
     *     {@link QName }
     *     
     */
    public QName getBase() {
        return base;
    }

    /**
     * Sets the value of the base property.
     * 
     * @param value
     *     allowed object is
     *     {@link QName }
     *     
     */
    public void setBase(QName value) {
        this.base = value;
    }

}
