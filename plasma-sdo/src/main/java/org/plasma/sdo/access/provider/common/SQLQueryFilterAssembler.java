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
package org.plasma.sdo.access.provider.common;

// java imports
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.config.PlasmaConfig;
import org.plasma.query.model.Literal;
import org.plasma.query.model.LogicalOperator;
import org.plasma.query.model.NullLiteral;
import org.plasma.query.model.QueryConstants;
import org.plasma.query.model.RelationalOperator;
import org.plasma.query.model.WildcardOperator;
import org.plasma.sdo.access.DataAccessException;
import org.plasma.sdo.helper.DataConverter;
import org.plasma.sdo.helper.PlasmaTypeHelper;

import commonj.sdo.Type;

public abstract class SQLQueryFilterAssembler extends TextQueryFilterAssembler
		implements QueryConstants, EntityConstants {
	
    private static Log log = LogFactory.getLog(SQLQueryFilterAssembler.class);

	public static final String ALIAS_PREFIX = "a";
	public String parameterChar = "?";
	public String wildcardChar = "%";

	protected Type stringType;
	protected RelationalOperator contextRelationalOperator;
	protected WildcardOperator contextWildcardOperator;

	@SuppressWarnings("unused")
	private SQLQueryFilterAssembler() {
	}

	public SQLQueryFilterAssembler(Type contextType) {
		this.contextType = contextType;
		this.params = new ArrayList<Object>();
		
		String uri = PlasmaConfig.getInstance().getSDODataTypesNamespace().getUri();
		stringType = PlasmaTypeHelper.INSTANCE.getType(uri, "String");
	}

	public SQLQueryFilterAssembler(Type contextType, List<Object> params) {
		this.contextType = contextType;
		this.params = params;
		String uri = PlasmaConfig.getInstance().getSDODataTypesNamespace().getUri();
		stringType = PlasmaTypeHelper.INSTANCE.getType(uri, "String");
	}

	public String getParameterChar() {
		return parameterChar;
	}

	public void setParameterChar(String parameterChar) {
		this.parameterChar = parameterChar;
	}

	public String getWildcardChar() {
		return wildcardChar;
	}

	public void setWildcardChar(String wildcardChar) {
		this.wildcardChar = wildcardChar;
	}

	public void start(LogicalOperator operator) {
		if (filter.length() > 0)
			filter.append(" ");

		switch (operator.getValue()) {
		case AND:
			filter.append("AND");
			break;
		case OR:
			filter.append("OR");
			break;
		default:
			throw new DataAccessException("unknown operator '"
					+ operator.getValue().toString() + "'");
		}
		super.start(operator);
	}

	public void start(RelationalOperator operator) {
		this.contextRelationalOperator = operator;
		this.contextWildcardOperator = null;
		super.start(operator);
	}
	
	public void start(WildcardOperator operator) {
		this.contextWildcardOperator = operator;
		this.contextRelationalOperator = null;
		super.start(operator);
	}

	public void start(Literal literal) {
		if (filter.length() > 0)
			filter.append(" ");
		
		String content = literal.getValue();
		if (this.contextWildcardOperator == null) {
			if (this.contextRelationalOperator == null)
				throw new IllegalStateException("expected context relational operator");
		    filter.append(toString(this.contextRelationalOperator));
		}
		else {
			content = content.replace(WILDCARD, this.wildcardChar);
		    filter.append(toString(contextWildcardOperator));
		}				
		
		params.add(
			DataConverter.INSTANCE.convert(
				this.contextProperty.getType(), 
				this.stringType,
				content));
		filter.append(" ");
		filter.append(this.parameterChar);
	}

	public void start(NullLiteral nullLiteral) {
		if (filter.length() > 0)
			filter.append(" ");
		if (this.contextRelationalOperator == null)
			throw new IllegalStateException("expected context relational operator");
		switch (this.contextRelationalOperator.getValue()) {
		case EQUALS:
			filter.append("IS NULL");
			break;
		case NOT_EQUALS:
			filter.append("IS NOT NULL");
			break;
		default:
			throw new DataAccessException("invalid operator for null literal'"
					+ this.contextRelationalOperator.getValue().toString() + "'");
		}
	}
	
	protected String toString(RelationalOperator operator) {
		String result = null;
		switch (operator.getValue()) {
		case EQUALS:
			result = "=";
			break;
		case NOT_EQUALS:
			result = "!=";
			break;
		case GREATER_THAN:
			result = ">";
			break;
		case GREATER_THAN_EQUALS:
			result = ">=";
			break;
		case LESS_THAN:
			result = "<";
			break;
		case LESS_THAN_EQUALS:
			result = "<=";
			break;
		default:
			throw new DataAccessException("unknown operator '"
					+ operator.getValue().toString() + "'");
		}
        return result;
	}
	
	protected String toString(WildcardOperator operator) {
		String result = null;
		switch (operator.getValue()) {
		case LIKE:
			result = "LIKE";
			break;
		default:
			throw new DataAccessException("unknown wildcard operator '"
					+ operator.getValue().toString() + "'");
		}
		return result;
	}
	
}