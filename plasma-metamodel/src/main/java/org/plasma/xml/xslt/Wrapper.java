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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.w3c.dom.Element;


/**
 * <p>Java class for wrapper complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="wrapper">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.w3.org/1999/XSL/Transform}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.w3.org/1999/XSL/Transform}import" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element ref="{http://www.w3.org/1999/XSL/Transform}top-level-element"/>
 *           &lt;element ref="{http://www.w3.org/1999/XSL/Transform}variable"/>
 *           &lt;any/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *       &lt;attribute name="extension-element-prefixes" type="{http://www.w3.org/1999/XSL/Transform}tokens" />
 *       &lt;attribute name="exclude-result-prefixes" type="{http://www.w3.org/1999/XSL/Transform}tokens" />
 *       &lt;attribute name="version" use="required" type="{http://www.w3.org/2001/XMLSchema}decimal" />
 *       &lt;attribute ref="{http://www.w3.org/XML/1998/namespace}space"/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wrapper", propOrder = {
    "imports",
    "topLevelElementsAndVariablesAndAnies"
})
public class Wrapper
    extends AnyType
{

    @XmlElement(name = "import")
    protected List<CombineStylesheets> imports;
    @XmlElementRefs({
        @XmlElementRef(name = "top-level-element", namespace = "http://www.w3.org/1999/XSL/Transform", type = JAXBElement.class),
        @XmlElementRef(name = "variable", namespace = "http://www.w3.org/1999/XSL/Transform", type = JAXBElement.class)
    })
    @XmlAnyElement
    protected List<Object> topLevelElementsAndVariablesAndAnies;
    @XmlAttribute
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;
    @XmlAttribute(name = "extension-element-prefixes")
    protected List<String> extensionElementPrefixes;
    @XmlAttribute(name = "exclude-result-prefixes")
    protected List<String> excludeResultPrefixes;
    @XmlAttribute(required = true)
    protected BigDecimal version;
    @XmlAttribute(namespace = "http://www.w3.org/XML/1998/namespace")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String space;

    /**
     * Gets the value of the imports property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the imports property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getImports().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CombineStylesheets }
     * 
     * 
     */
    public List<CombineStylesheets> getImports() {
        if (imports == null) {
            imports = new ArrayList<CombineStylesheets>();
        }
        return this.imports;
    }

    /**
     * Gets the value of the topLevelElementsAndVariablesAndAnies property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the topLevelElementsAndVariablesAndAnies property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTopLevelElementsAndVariablesAndAnies().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link Output }{@code >}
     * {@link JAXBElement }{@code <}{@link AttributeSet }{@code >}
     * {@link JAXBElement }{@code <}{@link Variable }{@code >}
     * {@link JAXBElement }{@code <}{@link DecimalFormat }{@code >}
     * {@link JAXBElement }{@code <}{@link Key }{@code >}
     * {@link JAXBElement }{@code <}{@link PreserveSpace }{@code >}
     * {@link JAXBElement }{@code <}{@link Template }{@code >}
     * {@link JAXBElement }{@code <}{@link PreserveSpace }{@code >}
     * {@link JAXBElement }{@code <}{@link AnyType }{@code >}
     * {@link Element }
     * {@link JAXBElement }{@code <}{@link NamespaceAlias }{@code >}
     * {@link JAXBElement }{@code <}{@link Variable }{@code >}
     * {@link JAXBElement }{@code <}{@link CombineStylesheets }{@code >}
     * 
     * 
     */
    public List<Object> getTopLevelElementsAndVariablesAndAnies() {
        if (topLevelElementsAndVariablesAndAnies == null) {
            topLevelElementsAndVariablesAndAnies = new ArrayList<Object>();
        }
        return this.topLevelElementsAndVariablesAndAnies;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the extensionElementPrefixes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the extensionElementPrefixes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExtensionElementPrefixes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getExtensionElementPrefixes() {
        if (extensionElementPrefixes == null) {
            extensionElementPrefixes = new ArrayList<String>();
        }
        return this.extensionElementPrefixes;
    }

    /**
     * Gets the value of the excludeResultPrefixes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the excludeResultPrefixes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExcludeResultPrefixes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getExcludeResultPrefixes() {
        if (excludeResultPrefixes == null) {
            excludeResultPrefixes = new ArrayList<String>();
        }
        return this.excludeResultPrefixes;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVersion(BigDecimal value) {
        this.version = value;
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

}
