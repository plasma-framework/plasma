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

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.plasma.query.visitor.QueryVisitor;
import org.plasma.query.visitor.Traversal;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Clause", propOrder = {
	"select",
	"update",
	"delete",
    "from",
    "join",
    "where",
    "orderBy",
    "groupBy"
})
@XmlRootElement(name = "Clause")
public class Clause {

    @XmlElement(name = "Join")
    protected Join join;
    @XmlElement(name = "GroupBy")
    protected GroupBy groupBy;
    @XmlElement(name = "OrderBy")
    protected OrderBy orderBy;
    @XmlElement(name = "Where")
    protected Where where;
    @XmlElement(name = "From")
    protected From from;
    @XmlElement(name = "Select")
    protected Select select;
    @XmlElement(name = "Update")
    protected Update update;
    @XmlElement(name = "Delete")
    protected Delete delete;


      //----------------/
     //- Constructors -/
    //----------------/

    public Clause() {
        super();
    } //-- org.plasma.mda.query.Clause()

    public Clause(Select select) {
        this();
        this.select = select;
    } 
    
    public Clause(Update update) {
        this();
        this.update = update;
    } 
    
    public Clause(Delete delete) {
        this();
        this.delete = delete;
    } 

    public Clause(From from) {
        this();
        this.from = from;
    } 

    public Clause(Where where) {
        this();
        this.where = where;
    } 

    public Clause(OrderBy orderBy) {
        this();
        this.orderBy = orderBy;
    } 

    public Clause(GroupBy groupBy) {
        this();
        this.groupBy = groupBy;
    } 
    
    public Clause(Join join) {
        this();
        this.join = join;
    } 

    /**
     * Gets the value of the join property.
     * 
     * @return
     *     possible object is
     *     {@link Join }
     *     
     */
    public Join getJoin() {
        return join;
    }

    /**
     * Sets the value of the join property.
     * 
     * @param value
     *     allowed object is
     *     {@link Join }
     *     
     */
    public void setJoin(Join value) {
        this.join = value;
    }


    /**
     * Gets the value of the orderBy property.
     * 
     * @return
     *     possible object is
     *     {@link OrderBy }
     *     
     */
    public OrderBy getOrderBy() {
        return orderBy;
    }

    /**
     * Sets the value of the orderBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderBy }
     *     
     */
    public void setOrderBy(OrderBy value) {
        this.orderBy = value;
    }
    
    /**
     * Gets the value of the GroupBy property.
     * 
     * @return
     *     possible object is
     *     {@link GroupBy }
     *     
     */
    public GroupBy getGroupBy() {
        return groupBy;
    }

    /**
     * Sets the value of the GroupBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link GroupBy }
     *     
     */
    public void setGroupBy(GroupBy value) {
        this.groupBy = value;
    }

    /**
     * Gets the value of the where property.
     * 
     * @return
     *     possible object is
     *     {@link Where }
     *     
     */
    public Where getWhere() {
        return where;
    }

    /**
     * Sets the value of the where property.
     * 
     * @param value
     *     allowed object is
     *     {@link Where }
     *     
     */
    public void setWhere(Where value) {
        this.where = value;
    }

    /**
     * Gets the value of the from property.
     * 
     * @return
     *     possible object is
     *     {@link From }
     *     
     */
    public From getFrom() {
        return from;
    }

    /**
     * Sets the value of the from property.
     * 
     * @param value
     *     allowed object is
     *     {@link From }
     *     
     */
    public void setFrom(From value) {
        this.from = value;
    }

    /**
     * Gets the value of the select property.
     * 
     * @return
     *     possible object is
     *     {@link Select }
     *     
     */
    public Select getSelect() {
        return select;
    }

    /**
     * Sets the value of the select property.
     * 
     * @param value
     *     allowed object is
     *     {@link Select }
     *     
     */
    public void setSelect(Select value) {
        this.select = value;
    }

    public Update getUpdate() {
		return update;
	}

	public void setUpdate(Update update) {
		this.update = update;
	}

	public Delete getDelete() {
		return delete;
	}

	public void setDelete(Delete delete) {
		this.delete = delete;
	}

	public void accept(QueryVisitor visitor)
    {
        visitor.start(this);
        if (visitor.getContext().getTraversal().ordinal() == Traversal.CONTINUE.ordinal())
        {
            if (this.select != null)                        
            	this.select.accept(visitor);                
            if (this.update != null)                        
            	this.update.accept(visitor);                
            if (this.delete != null)                        
            	this.delete.accept(visitor);                
            if (this.from != null)                          
            	this.from.accept(visitor);                  
            if (this.where != null)                         
            	this.where.accept(visitor);                 
            if (this.orderBy != null)                       
            	this.orderBy.accept(visitor);               
            if (this.groupBy != null)                       
            	this.groupBy.accept(visitor);               
            if (this.join != null)                       
            	this.join.accept(visitor);               
        }
    	visitor.end(this);
    }

}
