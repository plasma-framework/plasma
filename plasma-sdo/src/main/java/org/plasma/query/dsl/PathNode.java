package org.plasma.query.dsl;

import org.plasma.query.Expression;
import org.plasma.query.PathProperty;
import org.plasma.query.QueryException;
import org.plasma.query.Wildcard;
import org.plasma.query.model.GroupOperator;
import org.plasma.query.model.GroupOperatorValues;
import org.plasma.query.model.Path;
import org.plasma.query.model.Term;

import commonj.sdo.Type;

/**
 * A domain object which is not an end point but
 * a single step within a path within a query graph. 
 */
public class PathNode extends DomainObject implements PathProperty {

	protected Type type;
	//FIXME: why can't we create a root node once only
	protected boolean isRoot;
	protected Expression expr;
		
	protected PathNode(Type type) {
		super(null, null); // the root
		this.type = type;
	}
	
	protected PathNode(PathNode source, String sourceProperty) {
		super(source, sourceProperty);
	}	

	protected PathNode(PathNode source, String sourceProperty, Expression expr) {
		this(source, sourceProperty);
		this.expr = expr;
	}	
	
	public boolean isRoot() {
		return isRoot;
	}
	
	protected PathNode getSource() {
		return this.source;
	}
	
	protected String getSourceProperty() {
		return this.sourceProperty;
	}
	
	Expression getPredicate() {
		return this.expr;
	}

	public Wildcard wildcard() {
		return new WildcardDataNode(this, Wildcard.WILDCARD_CHAR);
	}
	
	public Expression group(Expression source) {
		// we may have been re-parented
		org.plasma.query.model.Expression root = (org.plasma.query.model.Expression)source;
		while (root.getParent() != null) {
			root = root.getParent();
		}
		root.getTerms().add(0, 
			new Term(new GroupOperator(GroupOperatorValues.RP_1)));
		root.getTerms().add( 
				new Term(new GroupOperator(GroupOperatorValues.LP_1)));
		return root;
	}

	@Override
	public Expression isNotNull() {
		return createProperty().isNotNull();
	}

	@Override
	public Expression isNull() {
		return createProperty().isNull();
	}
		
	private org.plasma.query.model.Property createProperty() {
		org.plasma.query.model.Property modelProperty = null;
		if (this.source != null) {
			String[] path = getPath();
			if (path != null && path.length > 0)
				modelProperty = new org.plasma.query.model.Property(this.sourceProperty, new Path(path));
			else
				modelProperty = new org.plasma.query.model.Property(this.sourceProperty);
		}
		else
			throw new QueryException("expected path");
		return modelProperty;
	}	
}
