package org.plasma.query.model;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.plasma.query.visitor.QueryVisitor;


/**
 * <p>Java class for Entity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Entity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Entity")
@XmlRootElement(name = "Entity")
public class Entity {

    @XmlAttribute(required = true)
    protected String name;
    @XmlAttribute
    protected String namespaceURI;
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

    public Entity() {
        super();
    } 
    
    public Entity(String name, String namespaceURI) {
        this();
        this.setName(name);
        this.setNamespaceURI(namespaceURI);
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
     * Gets the value of the namespaceURI property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNamespaceURI() {
        return namespaceURI;
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

    /**
     * Sets the value of the namespaceURI property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNamespaceURI(String value) {
        this.namespaceURI = value;
    }

    public void accept(QueryVisitor visitor)
    {
        visitor.start(this);
    	visitor.end(this);
    }
}
