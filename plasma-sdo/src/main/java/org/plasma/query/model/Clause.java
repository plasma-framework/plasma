/*
 * This class was automatically generated with 
 * <a href="http://castor.exolab.org">Castor 0.9.4</a>, using an
 * XML Schema.
 * $Id: Clause.java,v 1.3 2007/12/15 04:14:19 grays Exp $
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
    "orderBy",
    "groupBy",
    "where",
    "from",
    "select"
})
@XmlRootElement(name = "Clause")
public class Clause {

    @XmlElement(name = "OrderBy")
    protected OrderBy orderBy;
    @XmlElement(name = "GroupBy")
    protected GroupBy groupBy;
    @XmlElement(name = "Where")
    protected Where where;
    @XmlElement(name = "From")
    protected From from;
    @XmlElement(name = "Select")
    protected Select select;


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

    public void accept(QueryVisitor visitor)
    {
        visitor.start(this);
        if (visitor.getContext().getTraversal().ordinal() == Traversal.CONTINUE.ordinal())
        {
            if (select != null)                        
                select.accept(visitor);                
            if (from != null)                          
                from.accept(visitor);                  
            if (where != null)                         
                where.accept(visitor);                 
            if (orderBy != null)                       
                orderBy.accept(visitor);               
            if (groupBy != null)                       
                groupBy.accept(visitor);               
        }
    	visitor.end(this);
    }

}
