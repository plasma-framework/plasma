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
package org.plasma.query.dsl;

import org.plasma.query.DataProperty;
import org.plasma.query.Expression;
import org.plasma.query.From;
import org.plasma.query.GroupBy;
import org.plasma.query.OrderBy;
import org.plasma.query.Select;
import org.plasma.query.Where;
import org.plasma.query.Wildcard;
import org.plasma.query.model.AbstractProperty;
import org.plasma.query.model.Clause;
import org.plasma.query.model.Entity;
import org.plasma.query.model.Path;

import commonj.sdo.Type;

/**
 * The implementation of a domain query as a root within
 * a query graph.
 */
public class DomainRoot extends PathNode  
    implements DomainQuery 
{		
	private org.plasma.query.model.Query query;
	
	protected DomainRoot(Type type) {
		super(type);
		this.query = new org.plasma.query.model.Query();
		org.plasma.query.model.From from = new org.plasma.query.model.From();
		from.setEntity(new Entity(
			this.type.getName(), 
			this.type.getURI()));
		this.query.getClauses().add(new org.plasma.query.model.Clause(from));
		this.isRoot = true;
	}
	
	protected DomainRoot(PathNode source, String sourceProperty) {
		super(source, sourceProperty);
	}
	
	protected DomainRoot(PathNode source, String sourceProperty, Expression expr) {
		super(source, sourceProperty, expr);
	}	

	public org.plasma.query.model.Query getModel() {
		return this.query;
	}
	
	@Override
	public DomainQuery where(Expression expr) {
		org.plasma.query.model.Where where = this.query.findWhereClause();
		
		// we may have been re-parented
		org.plasma.query.model.Expression root = (org.plasma.query.model.Expression)expr;
		while (root.getParent() != null) {
			root = root.getParent();
		}
		
		if (where == null) {
		    where = new org.plasma.query.model.Where();
		    this.query.getClauses().add(new Clause(where));
		    where.addExpression(root);
		}
		else {
			where.addExpression(org.plasma.query.model.Expression.and());
		    where.addExpression(root);
		}
		return this;
	}
	
	@Override
	public DomainQuery select(DataProperty property) {
		org.plasma.query.model.Select select = null;
        for (int i = 0; i < this.query.getClauses().size(); i++)
        {   
            select = this.query.getClauses().get(i).getSelect();
            if (select != null)
                break;
        }
        
		if (select == null) {
		    select = new org.plasma.query.model.Select();
			this.query.getClauses().add(new Clause(select));
		}
		
		DataNode domainProperty = (DataNode)property;
		AbstractProperty prop = domainProperty.getModel();
		
		select.addProperty(prop);
		return this;
	}
	
	@Override
	public DomainQuery select(Wildcard property) {
		org.plasma.query.model.Select select = null;
        for (int i = 0; i < this.query.getClauses().size(); i++)
        {   
            select = this.query.getClauses().get(i).getSelect();
            if (select != null)
                break;
        }
        
		if (select == null) {
		    select = new org.plasma.query.model.Select();
			this.query.getClauses().add(new Clause(select));
		}
		
		WildcardDataNode domainProperty = (WildcardDataNode)property;
		AbstractProperty prop = domainProperty.getModel();
		
		select.addProperty(prop);
		return this;
	}
	
	@Override
	public DomainQuery orderBy(DataProperty property) {
		org.plasma.query.model.OrderBy orderBy = this.query.findOrderByClause();
		if (orderBy == null) {
			orderBy = new org.plasma.query.model.OrderBy();
			this.query.getClauses().add(new Clause(orderBy));			
		}
		DataNode domainProperty = (DataNode)property;
		String[] path = domainProperty.getPath();
		org.plasma.query.model.Property prop = null;
		if (path != null && path.length > 0)
			prop = org.plasma.query.model.Property.forName(domainProperty.getName(), 
					new Path(path));
		else 
			prop = org.plasma.query.model.Property.forName(domainProperty.getName());
		orderBy.addProperty(prop);
		return this;
	}
	
	@Override
	public DomainQuery groupBy(DataProperty property) {
		org.plasma.query.model.GroupBy groupBy = this.query.findGroupByClause();
		if (groupBy == null) {
			groupBy = new org.plasma.query.model.GroupBy();
			this.query.getClauses().add(new Clause(groupBy));			
		}
		DataNode domainProperty = (DataNode)property;
		String[] path = domainProperty.getPath();
		org.plasma.query.model.Property prop = null;
		if (path != null && path.length > 0)
			prop = org.plasma.query.model.Property.forName(domainProperty.getName(), 
					new Path(path));
		else 
			prop = org.plasma.query.model.Property.forName(domainProperty.getName());
		groupBy.addProperty(prop);
		return this;
	}
	
	public From getFromClause() {
		return this.query.getFromClause();
	}
	
	public void clearOrderByClause() {
		this.query.clearOrderByClause();		
	}

	public GroupBy findGroupByClause() {
		return this.query.findGroupByClause();
	}

	public OrderBy findOrderByClause() {
		return this.query.findOrderByClause();
	}

	public Where findWhereClause() {
		return this.query.findWhereClause();
	}


	public Integer getEndRange() {
		return this.query.getEndRange();
	}


	public String getName() {
		return this.query.getName();
	}

	public Select getSelectClause() {
		return this.query.getSelectClause();
	}

	public Integer getStartRange() {
		return this.query.getStartRange();
	}

	public Where getWhereClause() {
		return this.query.getWhereClause();
	}

	public void setEndRange(Integer value) {
		this.query.setEndRange(value);		
	}

	public void setName(String value) {
		this.query.setName(value);		
	}

	public void setStartRange(Integer value) {
		this.query.setStartRange(value);		
	}



}
