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

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.w3.org/1999/XSL/Transform}anyType">
 *       &lt;attribute name="method">
 *         &lt;simpleType>
 *           &lt;union>
 *             &lt;simpleType>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                 &lt;enumeration value="xml"/>
 *                 &lt;enumeration value="html"/>
 *                 &lt;enumeration value="text"/>
 *               &lt;/restriction>
 *             &lt;/simpleType>
 *             &lt;simpleType>
 *               &lt;restriction base="{http://www.w3.org/1999/XSL/Transform}QName">
 *                 &lt;pattern value="\c*:\c*"/>
 *               &lt;/restriction>
 *             &lt;/simpleType>
 *           &lt;/union>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="version" type="{http://www.w3.org/2001/XMLSchema}NMTOKEN" />
 *       &lt;attribute name="encoding" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="omit-xml-declaration" type="{http://www.w3.org/1999/XSL/Transform}YesOrNo" />
 *       &lt;attribute name="standalone" type="{http://www.w3.org/1999/XSL/Transform}YesOrNo" />
 *       &lt;attribute name="doctype-public" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="doctype-system" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="cdata-section-elements" type="{http://www.w3.org/1999/XSL/Transform}QNames" />
 *       &lt;attribute name="indent" type="{http://www.w3.org/1999/XSL/Transform}YesOrNo" />
 *       &lt;attribute name="media-type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class Output
    extends AnyType
{

    @XmlAttribute(name = "method")
    protected List<String> methods;
    @XmlAttribute
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NMTOKEN")
    protected String version;
    @XmlAttribute
    protected String encoding;
    @XmlAttribute(name = "omit-xml-declaration")
    protected YesOrNo omitXmlDeclaration;
    @XmlAttribute
    protected YesOrNo standalone;
    @XmlAttribute(name = "doctype-public")
    protected String doctypePublic;
    @XmlAttribute(name = "doctype-system")
    protected String doctypeSystem;
    @XmlAttribute(name = "cdata-section-elements")
    protected List<String> cdataSectionElements;
    @XmlAttribute
    protected YesOrNo indent;
    @XmlAttribute(name = "media-type")
    protected String mediaType;

    /**
     * Gets the value of the methods property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the methods property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMethods().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getMethods() {
        if (methods == null) {
            methods = new ArrayList<String>();
        }
        return this.methods;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Gets the value of the encoding property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEncoding() {
        return encoding;
    }

    /**
     * Sets the value of the encoding property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEncoding(String value) {
        this.encoding = value;
    }

    /**
     * Gets the value of the omitXmlDeclaration property.
     * 
     * @return
     *     possible object is
     *     {@link YesOrNo }
     *     
     */
    public YesOrNo getOmitXmlDeclaration() {
        return omitXmlDeclaration;
    }

    /**
     * Sets the value of the omitXmlDeclaration property.
     * 
     * @param value
     *     allowed object is
     *     {@link YesOrNo }
     *     
     */
    public void setOmitXmlDeclaration(YesOrNo value) {
        this.omitXmlDeclaration = value;
    }

    /**
     * Gets the value of the standalone property.
     * 
     * @return
     *     possible object is
     *     {@link YesOrNo }
     *     
     */
    public YesOrNo getStandalone() {
        return standalone;
    }

    /**
     * Sets the value of the standalone property.
     * 
     * @param value
     *     allowed object is
     *     {@link YesOrNo }
     *     
     */
    public void setStandalone(YesOrNo value) {
        this.standalone = value;
    }

    /**
     * Gets the value of the doctypePublic property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDoctypePublic() {
        return doctypePublic;
    }

    /**
     * Sets the value of the doctypePublic property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDoctypePublic(String value) {
        this.doctypePublic = value;
    }

    /**
     * Gets the value of the doctypeSystem property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDoctypeSystem() {
        return doctypeSystem;
    }

    /**
     * Sets the value of the doctypeSystem property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDoctypeSystem(String value) {
        this.doctypeSystem = value;
    }

    /**
     * Gets the value of the cdataSectionElements property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cdataSectionElements property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCdataSectionElements().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getCdataSectionElements() {
        if (cdataSectionElements == null) {
            cdataSectionElements = new ArrayList<String>();
        }
        return this.cdataSectionElements;
    }

    /**
     * Gets the value of the indent property.
     * 
     * @return
     *     possible object is
     *     {@link YesOrNo }
     *     
     */
    public YesOrNo getIndent() {
        return indent;
    }

    /**
     * Sets the value of the indent property.
     * 
     * @param value
     *     allowed object is
     *     {@link YesOrNo }
     *     
     */
    public void setIndent(YesOrNo value) {
        this.indent = value;
    }

    /**
     * Gets the value of the mediaType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMediaType() {
        return mediaType;
    }

    /**
     * Sets the value of the mediaType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMediaType(String value) {
        this.mediaType = value;
    }

}
