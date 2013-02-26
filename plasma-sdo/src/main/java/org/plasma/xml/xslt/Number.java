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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *       &lt;attribute name="level" default="single">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="single"/>
 *             &lt;enumeration value="multiple"/>
 *             &lt;enumeration value="any"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="count" type="{http://www.w3.org/1999/XSL/Transform}pattern" />
 *       &lt;attribute name="from" type="{http://www.w3.org/1999/XSL/Transform}pattern" />
 *       &lt;attribute name="value" type="{http://www.w3.org/1999/XSL/Transform}expression" />
 *       &lt;attribute name="format" type="{http://www.w3.org/1999/XSL/Transform}expr-avt" default="1" />
 *       &lt;attribute name="lang" type="{http://www.w3.org/1999/XSL/Transform}expr-avt" />
 *       &lt;attribute name="letter-value" type="{http://www.w3.org/1999/XSL/Transform}expr-avt" />
 *       &lt;attribute name="grouping-separator" type="{http://www.w3.org/1999/XSL/Transform}expr-avt" />
 *       &lt;attribute name="grouping-size" type="{http://www.w3.org/1999/XSL/Transform}expr-avt" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class Number
    extends AnyType
{

    @XmlAttribute
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String level;
    @XmlAttribute
    protected String count;
    @XmlAttribute
    protected String from;
    @XmlAttribute
    protected String value;
    @XmlAttribute
    protected String format;
    @XmlAttribute
    protected String lang;
    @XmlAttribute(name = "letter-value")
    protected String letterValue;
    @XmlAttribute(name = "grouping-separator")
    protected String groupingSeparator;
    @XmlAttribute(name = "grouping-size")
    protected String groupingSize;

    /**
     * Gets the value of the level property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLevel() {
        if (level == null) {
            return "single";
        } else {
            return level;
        }
    }

    /**
     * Sets the value of the level property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLevel(String value) {
        this.level = value;
    }

    /**
     * Gets the value of the count property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCount() {
        return count;
    }

    /**
     * Sets the value of the count property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCount(String value) {
        this.count = value;
    }

    /**
     * Gets the value of the from property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFrom() {
        return from;
    }

    /**
     * Sets the value of the from property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFrom(String value) {
        this.from = value;
    }

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the format property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFormat() {
        if (format == null) {
            return "1";
        } else {
            return format;
        }
    }

    /**
     * Sets the value of the format property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFormat(String value) {
        this.format = value;
    }

    /**
     * Gets the value of the lang property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLang() {
        return lang;
    }

    /**
     * Sets the value of the lang property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLang(String value) {
        this.lang = value;
    }

    /**
     * Gets the value of the letterValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLetterValue() {
        return letterValue;
    }

    /**
     * Sets the value of the letterValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLetterValue(String value) {
        this.letterValue = value;
    }

    /**
     * Gets the value of the groupingSeparator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGroupingSeparator() {
        return groupingSeparator;
    }

    /**
     * Sets the value of the groupingSeparator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGroupingSeparator(String value) {
        this.groupingSeparator = value;
    }

    /**
     * Gets the value of the groupingSize property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGroupingSize() {
        return groupingSize;
    }

    /**
     * Sets the value of the groupingSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGroupingSize(String value) {
        this.groupingSize = value;
    }

}
