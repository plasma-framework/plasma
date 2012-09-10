package org.plasma.query.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.plasma.query.visitor.QueryVisitor;
import org.plasma.query.visitor.Traversal;
import org.plasma.sdo.helper.PlasmaTypeHelper;

import commonj.sdo.Type;


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
 *         &lt;element ref="{http://www.servicelabs.org/plasma/query}Entity"/>
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
    "entity"
})
@XmlRootElement(name = "From")
public class From implements org.plasma.query.From {

    @XmlElement(name = "Entity", required = true)
    protected Entity entity;

    public From() {
        super();
    } 

    public From(Entity entity) {
        this();
        setEntity(entity);
    }  
    
    public From(String name, String namespaceURI) {
        this();
        setEntity(new Entity(name, namespaceURI));
    } 

    public From(Class c) {
        this();
        Type type = PlasmaTypeHelper.INSTANCE.getType(c);
        
        setEntity(new Entity(type.getName(), type.getURI()));
    } 

    /* (non-Javadoc)
	 * @see org.plasma.query.model.From2#getEntity()
	 */
    public Entity getEntity() {
        return entity;
    }

    /* (non-Javadoc)
	 * @see org.plasma.query.model.From2#setEntity(org.plasma.query.model.Entity)
	 */
    public void setEntity(Entity value) {
        this.entity = value;
    }

    public void accept(QueryVisitor visitor)
    {
        visitor.start(this);
        if (visitor.getContext().getTraversal().ordinal() == Traversal.CONTINUE.ordinal())
            this.getEntity().accept(visitor);
    	visitor.end(this);
    }

	@Override
	public String getName() {
		return this.entity.getName();
	}

	@Override
	public String getUri() {
		return this.entity.getNamespaceURI();
	}

}
