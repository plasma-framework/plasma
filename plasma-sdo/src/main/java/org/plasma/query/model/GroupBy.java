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

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.plasma.query.visitor.QueryVisitor;
import org.plasma.query.visitor.Traversal;



/**
 * <p>Java class for GroupBy complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GroupBy">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.servicelabs.org/plasma/query}Property" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.servicelabs.org/plasma/query}TextContent" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GroupBy", propOrder = {
    "properties",
    "textContent"
})
@XmlRootElement(name = "GroupBy")
public class GroupBy implements org.plasma.query.GroupBy {

    @XmlElement(name = "Property")
    protected List<Property> properties;
    @XmlElement(name = "TextContent")
    protected TextContent textContent;

    public GroupBy() {
        super();
    } //-- org.plasma.mda.query.GroupBy()

    public GroupBy(Property p1) {
        this();
        getProperties().add(p1);
    }

    public GroupBy(Property p1, Property p2) {
        this();
        getProperties().add(p1);
        getProperties().add(p2);
    }

    public GroupBy(Property p1, Property p2, Property p3) {
        this();
        getProperties().add(p1);
        getProperties().add(p2);
        getProperties().add(p3);
    }

    public GroupBy(Property p1, Property p2, Property p3, Property p4) {
        this();
        getProperties().add(p1);
        getProperties().add(p2);
        getProperties().add(p3);
        getProperties().add(p4);
    }

    public GroupBy(Property[] properties) {
        this();
        for (int i = 0; i < properties.length; i++)
            getProperties().add(properties[i]);
    }

    /**
     * Gets the value of the properties property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the properties property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProperties().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Property }
     * 
     * 
     */
    public List<Property> getProperties() {
        if (properties == null) {
            properties = new ArrayList<Property>();
        }
        return this.properties;
    }
    
    public void addProperty(Property property) {
    	this.getProperties().add(property);
    }

    /**
     * Gets the value of the textContent property.
     * 
     * @return
     *     possible object is
     *     {@link TextContent }
     *     
     */
    public TextContent getTextContent() {
        return textContent;
    }

    /**
     * Sets the value of the textContent property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextContent }
     *     
     */
    public void setTextContent(TextContent value) {
        this.textContent = value;
    }

    public void accept(QueryVisitor visitor)
    {
        visitor.start(this);
        if (visitor.getContext().getTraversal().ordinal() == Traversal.CONTINUE.ordinal())
        {
            for (int i = 0; i < this.getProperties().size(); i++)
               this.getProperties().get(i).accept(visitor);  
        }
    	visitor.end(this);
    }

}
