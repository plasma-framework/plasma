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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.plasma.query.visitor.QueryVisitor;
import org.plasma.query.visitor.Traversal;

import commonj.sdo.Type;
import commonj.sdo.impl.HelperProvider;


/**
 * <p>Java class for From complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="From">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.terrameta.org/plasma/query}Entity"/>
 *       &lt;attribute name="randomSample" type="{http://www.w3.org/2001/XMLSchema}float" />
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "From", propOrder = {
    "entities"
})
@XmlRootElement(name = "From")
public class From implements org.plasma.query.From {

    @XmlElement(name = "Entity", required = true)
    protected List<Entity> entities;
    @XmlAttribute(name = "randomSample")
    protected Float randomSample;

    public From() {
        super();
    } 

    public From(Entity entity) {
        this();
        this.getEntities().add(entity);
    }  
    
    public From(String name, String namespaceURI) {
        this();
        this.getEntities().add(new Entity(name, namespaceURI));
    } 

    public From(Class c) {
        this();
        Type type = HelperProvider.getTypeHelper().getType(c);        
        this.getEntities().add(new Entity(type.getName(), type.getURI()));
    } 

    public List<Entity> getEntities() {
        if (entities == null) {
            entities = new ArrayList<Entity>();
        }
        return this.entities;
    }
    
    public Entity getEntity() {
		validateSingleEntity();
        return this.getEntities().get(0);
    }
    
    public void setEntity(Entity entity) {
        this.getEntities().add(entity);
		validateSingleEntity();
    }

	@Override
	public String getName() {
		validateSingleEntity();
        return this.getEntities().get(0).getName();
	}

	@Override
	public String getUri() {
		validateSingleEntity();
        return this.getEntities().get(0).getNamespaceURI();
	}
    
    private void validateSingleEntity() {
		int size = this.getEntities().size();
		if (size > 0) {
			if (size > 1)
				throw new IllegalStateException("clause containes multiple entities");
		}
		else
			throw new IllegalStateException("clause containes no entities");
    }

    /**
     * Gets the value of the randomSample property. A floating point value
	 * between zero (result entity is never included) and 1 (result entity is
	 * always included) used to sample or filter results. Cannot be used with
	 * with (potentially) many types of predicates depending on the restrictions
	 * within the underlying data store.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getRandomSample() {
        return this.randomSample;
    }

    /**
     * Sets the value of the randomSample property. A floating point value
	 * between zero (result entity is never included) and 1 (result entity is
	 * always included) used to sample or filter results. Cannot be used with
	 * with (potentially) many types of predicates depending on the restrictions
	 * within the underlying data store.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setRandomSample(Float value) {
        this.randomSample = value;
    }
    
    public void accept(QueryVisitor visitor)
    {
        visitor.start(this);
        if (visitor.getContext().getTraversal().ordinal() == Traversal.CONTINUE.ordinal())
        	for (Entity entity : this.getEntities())
        		entity.accept(visitor);
    	visitor.end(this);
    }

}
