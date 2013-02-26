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
package org.plasma.query.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PathElement")
@XmlRootElement(name = "PathElement")
public class PathElement
    extends AbstractPathElement
{
    @XmlAttribute
    protected Integer index;

    /** 
     * Stores the physical name associated with this 
     * property. Can be used by service providers
     * for query post processing. This field is not
     * processed during XML or other serialization
     * operations.  
     */
    protected transient String physicalName;
    /** 
     * Stores the physical name bytes associated with this 
     * property. Can be used by service providers
     * for query post processing. This field is not
     * processed during XML or other serialization
     * operations.  
     */
    protected transient byte[] physicalNameBytes;
    
    
    public PathElement() {
    } 

    public PathElement(String content) {
        super(content);
    } 
    /**
     * Gets the value of the index property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIndex() {
        return index;
    }


    /**
     * Sets the value of the index property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIndex(Integer value) {
        this.index = value;
    }
    
    public String getPhysicalName() {
        return physicalName;
    }

    public void setPhysicalName(String value) {
        this.physicalName = value;
    }
    
    public byte[] getPhysicalNameBytes() {
        return physicalNameBytes;
    }

    public void setPhysicalNameBytes(byte[] value) {
        this.physicalNameBytes = value;
    }
}
