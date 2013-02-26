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


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.w3.org/1999/XSL/Transform}anyType">
 *       &lt;attribute name="name" type="{http://www.w3.org/1999/XSL/Transform}QName" />
 *       &lt;attribute name="decimal-separator" type="{http://www.w3.org/2001/XMLSchema}string" default="." />
 *       &lt;attribute name="grouping-separator" type="{http://www.w3.org/2001/XMLSchema}string" default="," />
 *       &lt;attribute name="infinity" type="{http://www.w3.org/2001/XMLSchema}string" default="Infinity" />
 *       &lt;attribute name="minus-sign" type="{http://www.w3.org/2001/XMLSchema}string" default="-" />
 *       &lt;attribute name="NaN" type="{http://www.w3.org/2001/XMLSchema}string" default="NaN" />
 *       &lt;attribute name="percent" type="{http://www.w3.org/2001/XMLSchema}string" default="%" />
 *       &lt;attribute name="per-mille" type="{http://www.w3.org/2001/XMLSchema}string" default />
 *       &lt;attribute name="zero-digit" type="{http://www.w3.org/2001/XMLSchema}string" default="0" />
 *       &lt;attribute name="digit" type="{http://www.w3.org/2001/XMLSchema}string" default="#" />
 *       &lt;attribute name="pattern-separator" type="{http://www.w3.org/2001/XMLSchema}string" default=";" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class DecimalFormat
    extends AnyType
{

    @XmlAttribute
    protected String name;
    @XmlAttribute(name = "decimal-separator")
    protected String decimalSeparator;
    @XmlAttribute(name = "grouping-separator")
    protected String groupingSeparator;
    @XmlAttribute
    protected String infinity;
    @XmlAttribute(name = "minus-sign")
    protected String minusSign;
    @XmlAttribute(name = "NaN")
    protected String naN;
    @XmlAttribute
    protected String percent;
    @XmlAttribute(name = "per-mille")
    protected String perMille;
    @XmlAttribute(name = "zero-digit")
    protected String zeroDigit;
    @XmlAttribute
    protected String digit;
    @XmlAttribute(name = "pattern-separator")
    protected String patternSeparator;

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
     * Gets the value of the decimalSeparator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDecimalSeparator() {
        if (decimalSeparator == null) {
            return ".";
        } else {
            return decimalSeparator;
        }
    }

    /**
     * Sets the value of the decimalSeparator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDecimalSeparator(String value) {
        this.decimalSeparator = value;
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
        if (groupingSeparator == null) {
            return ",";
        } else {
            return groupingSeparator;
        }
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
     * Gets the value of the infinity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInfinity() {
        if (infinity == null) {
            return "Infinity";
        } else {
            return infinity;
        }
    }

    /**
     * Sets the value of the infinity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInfinity(String value) {
        this.infinity = value;
    }

    /**
     * Gets the value of the minusSign property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMinusSign() {
        if (minusSign == null) {
            return "-";
        } else {
            return minusSign;
        }
    }

    /**
     * Sets the value of the minusSign property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMinusSign(String value) {
        this.minusSign = value;
    }

    /**
     * Gets the value of the naN property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNaN() {
        if (naN == null) {
            return "NaN";
        } else {
            return naN;
        }
    }

    /**
     * Sets the value of the naN property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNaN(String value) {
        this.naN = value;
    }

    /**
     * Gets the value of the percent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPercent() {
        if (percent == null) {
            return "%";
        } else {
            return percent;
        }
    }

    /**
     * Sets the value of the percent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPercent(String value) {
        this.percent = value;
    }

    /**
     * Gets the value of the perMille property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPerMille() {
        if (perMille == null) {
            return "\u2030";
        } else {
            return perMille;
        }
    }

    /**
     * Sets the value of the perMille property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerMille(String value) {
        this.perMille = value;
    }

    /**
     * Gets the value of the zeroDigit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZeroDigit() {
        if (zeroDigit == null) {
            return "0";
        } else {
            return zeroDigit;
        }
    }

    /**
     * Sets the value of the zeroDigit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZeroDigit(String value) {
        this.zeroDigit = value;
    }

    /**
     * Gets the value of the digit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDigit() {
        if (digit == null) {
            return "#";
        } else {
            return digit;
        }
    }

    /**
     * Sets the value of the digit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDigit(String value) {
        this.digit = value;
    }

    /**
     * Gets the value of the patternSeparator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPatternSeparator() {
        if (patternSeparator == null) {
            return ";";
        } else {
            return patternSeparator;
        }
    }

    /**
     * Sets the value of the patternSeparator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPatternSeparator(String value) {
        this.patternSeparator = value;
    }

}
