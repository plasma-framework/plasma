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
