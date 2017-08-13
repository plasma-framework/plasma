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

import org.plasma.query.Expression;
import org.plasma.query.PathProperty;
import org.plasma.query.QueryException;
import org.plasma.query.Wildcard;
import org.plasma.query.model.GroupOperator;
import org.plasma.query.model.GroupOperatorName;
import org.plasma.query.model.Path;
import org.plasma.query.model.Term;
import org.plasma.query.model.WildcardPropertyType;

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

	/**
	 * Returns all data properties for current {@link Type}. The
	 * current {@link Type} is the domain classifier or class for the current
	 * query path step or segment.
	 * @return all data properties for current {@link Type}.
	 */
	public Wildcard wildcard() {
		return new WildcardNode(this, Wildcard.WILDCARD_CHAR, 
			WildcardPropertyType.DATA);
	}

	/**
	 * Returns all data properties for current {@link Type}. The
	 * current {@link Type} is the domain classifier or class for the current
	 * query path step or segment.
	 * @return all data properties for current {@link Type}.
	 */
	public Wildcard dataWildcard() {
		return new WildcardNode(this, Wildcard.WILDCARD_CHAR, 
			WildcardPropertyType.DATA);
	}

	/**
	 * Returns all reference properties for current {@link Type}. The
	 * current {@link Type} is the domain classifier or class for the current
	 * query path step or segment.
	 * @return all reference properties for current {@link Type}.
	 */
	public Wildcard referenceWildcard() {
		return new WildcardNode(this, Wildcard.WILDCARD_CHAR, 
				WildcardPropertyType.REFERENCE);
	}

	/**
	 * Returns all data properties for current {@link Type} and all of its sub types. The
	 * current {@link Type} is the domain classifier or class for the current
	 * query path step or segment.
	 * @return all data properties for current {@link Type} and all of its sub types.
	 */
	public Wildcard subclassDataWildcard() {
		return new WildcardNode(this, Wildcard.WILDCARD_CHAR, 
			WildcardPropertyType.SUBCLASS___DATA);
	}
	
	
	public Expression group(Expression source) {
		// we may have been re-parented
		org.plasma.query.model.Expression root = (org.plasma.query.model.Expression)source;
		while (root.getParent() != null) {
			root = root.getParent();
		}
		root.getTerms().add(0, 
			new Term(new GroupOperator(GroupOperatorName.RP_1)));
		root.getTerms().add( 
				new Term(new GroupOperator(GroupOperatorName.LP_1)));
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
