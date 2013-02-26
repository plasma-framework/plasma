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
 *       &lt;attribute name="stylesheet-prefix" use="required" type="{http://www.w3.org/1999/XSL/Transform}prefix" />
 *       &lt;attribute name="result-prefix" use="required" type="{http://www.w3.org/1999/XSL/Transform}prefix" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class NamespaceAlias
    extends AnyType
{

    @XmlAttribute(name = "stylesheet-prefix", required = true)
    protected String stylesheetPrefix;
    @XmlAttribute(name = "result-prefix", required = true)
    protected String resultPrefix;

    /**
     * Gets the value of the stylesheetPrefix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStylesheetPrefix() {
        return stylesheetPrefix;
    }

    /**
     * Sets the value of the stylesheetPrefix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStylesheetPrefix(String value) {
        this.stylesheetPrefix = value;
    }

    /**
     * Gets the value of the resultPrefix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResultPrefix() {
        return resultPrefix;
    }

    /**
     * Sets the value of the resultPrefix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResultPrefix(String value) {
        this.resultPrefix = value;
    }

}
