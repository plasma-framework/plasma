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
package org.plasma.xml.xslt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;


/**
 * <p>Java class for attribute-type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="attribute-type">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.w3.org/1999/XSL/Transform}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element ref="{http://www.w3.org/1999/XSL/Transform}char-instruction"/>
 *         &lt;element ref="{http://www.w3.org/1999/XSL/Transform}top-level-element-and-char-instruction"/>
 *       &lt;/choice>
 *       &lt;attGroup ref="{http://www.w3.org/1999/XSL/Transform}name-avt"/>
 *       &lt;attribute name="namespace" type="{http://www.w3.org/1999/XSL/Transform}expr-avt" />
 *       &lt;attribute ref="{http://www.w3.org/XML/1998/namespace}space"/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "attribute-type", propOrder = {
    "content"
})
public class AttributeType {

    @XmlElementRefs({
        @XmlElementRef(name = "char-instruction", namespace = "http://www.w3.org/1999/XSL/Transform", type = JAXBElement.class),
        @XmlElementRef(name = "top-level-element-and-char-instruction", namespace = "http://www.w3.org/1999/XSL/Transform", type = JAXBElement.class)
    })
    @XmlMixed
    protected List<Serializable> content;
    @XmlAttribute
    protected String namespace;
    @XmlAttribute(namespace = "http://www.w3.org/XML/1998/namespace")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String space;
    @XmlAttribute(required = true)
    protected String name;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Gets the value of the content property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the content property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link If }{@code >}
     * {@link JAXBElement }{@code <}{@link ValueOf }{@code >}
     * {@link JAXBElement }{@code <}{@link ForEach }{@code >}
     * {@link JAXBElement }{@code <}{@link Variable }{@code >}
     * {@link JAXBElement }{@code <}{@link CopyOf }{@code >}
     * {@link JAXBElement }{@code <}{@link Number }{@code >}
     * {@link JAXBElement }{@code <}{@link CallTemplate }{@code >}
     * {@link JAXBElement }{@code <}{@link ApplyTemplates }{@code >}
     * {@link JAXBElement }{@code <}{@link Choose }{@code >}
     * {@link JAXBElement }{@code <}{@link Copy }{@code >}
     * {@link JAXBElement }{@code <}{@link Fallback }{@code >}
     * {@link String }
     * {@link JAXBElement }{@code <}{@link Message }{@code >}
     * {@link JAXBElement }{@code <}{@link Text }{@code >}
     * {@link JAXBElement }{@code <}{@link AnyType }{@code >}
     * {@link JAXBElement }{@code <}{@link AnyType }{@code >}
     * {@link JAXBElement }{@code <}{@link AnyType }{@code >}
     * 
     * 
     */
    public List<Serializable> getContent() {
        if (content == null) {
            content = new ArrayList<Serializable>();
        }
        return this.content;
    }

    /**
     * Gets the value of the namespace property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * Sets the value of the namespace property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNamespace(String value) {
        this.namespace = value;
    }

    /**
     * Gets the value of the space property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpace() {
        return space;
    }

    /**
     * Sets the value of the space property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpace(String value) {
        this.space = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets a map that contains attributes that aren't bound to any typed property on this class.
     * 
     * <p>
     * the map is keyed by the name of the attribute and 
     * the value is the string value of the attribute.
     * 
     * the map returned by this method is live, and you can add new attribute
     * by updating the map directly. Because of this design, there's no setter.
     * 
     * 
     * @return
     *     always non-null
     */
    public Map<QName, String> getOtherAttributes() {
        return otherAttributes;
    }

}
