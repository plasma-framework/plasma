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
 *       &lt;attGroup ref="{http://www.w3.org/1999/XSL/Transform}select-required"/>
 *       &lt;attGroup ref="{http://www.w3.org/1999/XSL/Transform}disable-output-escaping"/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class ValueOf
    extends AnyType
{

    @XmlAttribute(required = true)
    protected String select;
    @XmlAttribute(name = "disable-output-escaping")
    protected YesOrNo disableOutputEscaping;

    /**
     * Gets the value of the select property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSelect() {
        return select;
    }

    /**
     * Sets the value of the select property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSelect(String value) {
        this.select = value;
    }

    /**
     * Gets the value of the disableOutputEscaping property.
     * 
     * @return
     *     possible object is
     *     {@link YesOrNo }
     *     
     */
    public YesOrNo getDisableOutputEscaping() {
        if (disableOutputEscaping == null) {
            return YesOrNo.NO;
        } else {
            return disableOutputEscaping;
        }
    }

    /**
     * Sets the value of the disableOutputEscaping property.
     * 
     * @param value
     *     allowed object is
     *     {@link YesOrNo }
     *     
     */
    public void setDisableOutputEscaping(YesOrNo value) {
        this.disableOutputEscaping = value;
    }

}
