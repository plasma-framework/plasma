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
package org.plasma.query.visitor;

import org.plasma.query.From;
import org.plasma.query.GroupBy;
import org.plasma.query.OrderBy;
import org.plasma.query.Query;
import org.plasma.query.Where;
import org.plasma.query.model.AbstractProperty;
import org.plasma.query.model.ArithmeticOperator;
import org.plasma.query.model.Clause;
import org.plasma.query.model.Delete;
import org.plasma.query.model.Entity;
import org.plasma.query.model.Expression;
import org.plasma.query.model.GroupOperator;
import org.plasma.query.model.Join;
import org.plasma.query.model.Literal;
import org.plasma.query.model.LogicalOperator;
import org.plasma.query.model.NullLiteral;
import org.plasma.query.model.Property;
import org.plasma.query.model.RelationalOperator;
import org.plasma.query.model.Select;
import org.plasma.query.model.Term;
import org.plasma.query.model.Update;
import org.plasma.query.model.Variable;
import org.plasma.query.model.PredicateOperator;
import org.plasma.query.model.WildcardProperty;


/**
 * Default no-op query visitor implementation.
 * @see VisitorContext
 */
public abstract class DefaultQueryVisitor 
    implements QueryVisitor
{
	private VisitorContext context = new VisitorContext();
	
    public DefaultQueryVisitor() {}
    
    public VisitorContext getContext() {
    	return this.context;
    }
    
    public void start(Query query) { }
    public void start(Clause clause) { }
    public void start(Select select) { }
    public void start(Update update) { }
    public void start(Delete delete) { }
    public void start(From from) { }
    public void start(Where where) { }
    public void start(OrderBy orderBy) { }
    public void start(GroupBy groupBy) { }
    public void start(Join join) { }
    public void start(Expression expression) { }
    public void start(Term term) { }
    public void start(AbstractProperty property) { }
    public void start(Property property) { }
    public void start(WildcardProperty property) { }
    public void start(Entity entity) { }
    public void start(GroupOperator operator) { }
    public void start(RelationalOperator operator) { }
    public void start(LogicalOperator operator) { }
    public void start(ArithmeticOperator operator) { }
    public void start(PredicateOperator operator) { }
    public void start(Literal literal) { }
    public void start(NullLiteral nullLiteral) { }
    public void start(Variable variable) { }

    public void end(Query query) { }
    public void end(Clause clause) { }
    public void end(Select select) { }
    public void end(Update update) { }
    public void end(Delete delete) { }
    public void end(From from) { }
    public void end(Where where) { }
    public void end(OrderBy orderBy) { }
    public void end(GroupBy groupBy) { }
    public void end(Join join) { }
    public void end(Expression expression) { }
    public void end(Term term) { }
    public void end(AbstractProperty property) { }
    public void end(Property property) { }
    public void end(WildcardProperty property) { }
    public void end(Entity entity) { }
    public void end(GroupOperator operator) { }
    public void end(RelationalOperator operator) { }
    public void end(LogicalOperator operator) { }
    public void end(ArithmeticOperator operator) { }
    public void end(PredicateOperator operator) { }
    public void end(Literal literal) { }
    public void end(NullLiteral nullLiteral) { }
    public void end(Variable variable) { }
}

