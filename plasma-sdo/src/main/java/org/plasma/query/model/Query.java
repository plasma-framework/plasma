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

import org.plasma.query.QueryException;
import org.plasma.query.visitor.QueryVisitor;
import org.plasma.query.visitor.Traversal;
import org.plasma.query.visitor.VisitorContext;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Query", propOrder = {
    "clauses",
    "startRange",
    "endRange"
})
@XmlRootElement(name = "Query")
public class Query implements org.plasma.query.Query {

    @XmlElement(name = "Clause", required = true)
    protected List<Clause> clauses;
    @XmlElement(namespace = "", defaultValue = "0")
    protected Integer startRange;
    @XmlElement(namespace = "", defaultValue = "0")
    protected Integer endRange;
    @XmlAttribute
    protected String name;

    public Query() {
        super();
    } 

    public Query(Select select, From from) {
        this();
        getClauses().add(new Clause(select));
        getClauses().add(new Clause(from));
    } 

    public Query(Select select, From from, GroupBy groupBy) {
        this();
        getClauses().add(new Clause(select));
        getClauses().add(new Clause(from));
        getClauses().add(new Clause(groupBy));
    } 

    public Query(Select select, From from, Where where) {
        this();
        getClauses().add(new Clause(select));
        getClauses().add(new Clause(from));
        getClauses().add(new Clause(where));
    } 

    public Query(Select select, From from, Where where, GroupBy groupBy) {
        this();
        getClauses().add(new Clause(select));
        getClauses().add(new Clause(from));
        getClauses().add(new Clause(where));
        getClauses().add(new Clause(groupBy));
    } 

    public Query(Select select, From from, OrderBy orderBy) {
        this();
        getClauses().add(new Clause(select));
        getClauses().add(new Clause(from));
        getClauses().add(new Clause(orderBy));
    } 

    public Query(Select select, From from, OrderBy orderBy, GroupBy groupBy) {
        this();
        getClauses().add(new Clause(select));
        getClauses().add(new Clause(from));
        getClauses().add(new Clause(orderBy));
        getClauses().add(new Clause(groupBy));
    } 

    public Query(Select select, From from, Where where, OrderBy orderBy) {
        this();
        getClauses().add(new Clause(select));
        getClauses().add(new Clause(from));
        getClauses().add(new Clause(where));
        getClauses().add(new Clause(orderBy));
    } 

    public Query(Select select, From from, Where where, OrderBy orderBy, GroupBy groupBy) {
        this();
        getClauses().add(new Clause(select));
        getClauses().add(new Clause(from));
        getClauses().add(new Clause(where));
        getClauses().add(new Clause(orderBy));
        getClauses().add(new Clause(groupBy));
    }   
    
	public Query getModel() {
		return this;
	}

    /* (non-Javadoc)
	 * @see org.plasma.query.model.Query2#getClauses()
	 */
    public List<Clause> getClauses() {
        if (clauses == null) {
            clauses = new ArrayList<Clause>();
        }
        return this.clauses;
    }

    /* (non-Javadoc)
	 * @see org.plasma.query.model.Query2#getStartRange()
	 */
    public Integer getStartRange() {
    	if (startRange != null)
            return startRange;
    	else
    		return null;
    }

    /* (non-Javadoc)
	 * @see org.plasma.query.model.Query2#setStartRange(java.lang.Integer)
	 */
    public void setStartRange(Integer value) {
        this.startRange = value;
    }

    /* (non-Javadoc)
	 * @see org.plasma.query.model.Query2#getEndRange()
	 */
    public Integer getEndRange() {
    	if (endRange != null)
            return endRange;
    	else
    		return null;
    }

    /* (non-Javadoc)
	 * @see org.plasma.query.model.Query2#setEndRange(java.lang.Integer)
	 */
    public void setEndRange(Integer value) {
        this.endRange = value;
    }
    
    /* (non-Javadoc)
	 * @see org.plasma.query.model.Query2#getName()
	 */
    public String getName() {
        return name;
    }

    /* (non-Javadoc)
	 * @see org.plasma.query.model.Query2#setName(java.lang.String)
	 */
    public void setName(String value) {
        this.name = value;
    }

    public void accept(QueryVisitor visitor)
    {
    	visitor.start(this);
        if (visitor.getContext().getTraversal().ordinal() == Traversal.CONTINUE.ordinal())
            for (int i = 0; i < this.getClauses().size(); i++)
               this.getClauses().get(i).accept(visitor);  
    	visitor.end(this);
    }

    /* (non-Javadoc)
	 * @see org.plasma.query.model.Query2#getSelectClause()
	 */
    public Select getSelectClause()
    {
        for (int i = 0; i < this.getClauses().size(); i++)
        {   
            Select select = this.getClauses().get(i).getSelect();
            if (select != null)
                return select;
        }
        throw new QueryException("could not get Select clause");       
    }

    /* (non-Javadoc)
	 * @see org.plasma.query.model.Query2#getFromClause()
	 */
    public From getFromClause()
    {
        for (int i = 0; i < this.getClauses().size(); i++)
        {   
            From from = this.getClauses().get(i).getFrom();
            if (from != null)
                return from;
        }
        throw new QueryException("could not get From clause");       
    }

    /* (non-Javadoc)
	 * @see org.plasma.query.model.Query2#getWhereClause()
	 */
    public Where getWhereClause()
    {
        Where result = findWhereClause();
        if (result == null)
            throw new QueryException("could not get Where clause");   
        return result;    
    }

    /* (non-Javadoc)
	 * @see org.plasma.query.model.Query2#findWhereClause()
	 */
    public Where findWhereClause()
    {
        for (int i = 0; i < this.getClauses().size(); i++)
        {   
            Where where = this.getClauses().get(i).getWhere();
            if (where != null)
                return where;
        }
        return null;       
    }

    /* (non-Javadoc)
	 * @see org.plasma.query.model.Query2#clearOrderByClause()
	 */
    public void clearOrderByClause()
    {
        for (int i = 0; i < this.getClauses().size(); i++)
        {   
            OrderBy orderBy = this.getClauses().get(i).getOrderBy();
            if (orderBy != null)
                this.getClauses().remove(this.getClauses().get(i));
        }
    }

    
    /* (non-Javadoc)
	 * @see org.plasma.query.model.Query2#findOrderByClause()
	 */
    public OrderBy findOrderByClause()
    {
        for (int i = 0; i < this.getClauses().size(); i++)
        {   
            OrderBy orderBy = this.getClauses().get(i).getOrderBy();
            if (orderBy != null)
                return orderBy;
        }
        return null;       
    }

    /* (non-Javadoc)
	 * @see org.plasma.query.model.Query2#findGroupByClause()
	 */
    public GroupBy findGroupByClause()
    {
        for (int i = 0; i < this.getClauses().size(); i++)
        {   
            GroupBy groupBy = this.getClauses().get(i).getGroupBy();
            if (groupBy != null)
                return groupBy;
        }
        return null;       
    }

}
