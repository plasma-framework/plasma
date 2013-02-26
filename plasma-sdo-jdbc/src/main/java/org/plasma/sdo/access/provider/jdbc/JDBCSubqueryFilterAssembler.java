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
package org.plasma.sdo.access.provider.jdbc;

// java imports
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.query.QueryException;
import org.plasma.query.model.AbstractPathElement;
import org.plasma.query.model.AbstractProperty;
import org.plasma.query.model.FunctionValues;
import org.plasma.query.model.Literal;
import org.plasma.query.model.Path;
import org.plasma.query.model.PathElement;
import org.plasma.query.model.Property;
import org.plasma.query.model.Query;
import org.plasma.query.model.Select;
import org.plasma.query.model.SubqueryOperator;
import org.plasma.query.model.Where;
import org.plasma.query.model.WildcardOperator;
import org.plasma.query.model.WildcardPathElement;
import org.plasma.query.visitor.Traversal;
import org.plasma.sdo.access.DataAccessException;
import org.plasma.sdo.access.provider.common.SQLQueryFilterAssembler;

import commonj.sdo.Type;

public class JDBCSubqueryFilterAssembler extends SQLQueryFilterAssembler
{
    private static Log log = LogFactory.getLog(JDBCSubqueryFilterAssembler.class);
    
    private String alias;
    private Object[] filterParams;
    private int variableCount = 0;

    public JDBCSubqueryFilterAssembler(String alias, Query query, List params, Type contextType)
    {
        super(contextType, params);
        this.alias = alias;
        this.contextType = contextType;
        this.filterParams = filterParams; 
        query.accept(this);
    }

    public void start(Select select)
    {
        if (select.getProperties().size() > 1)
            throw new QueryException("multiple properties on subqueries not supported");
        if (select.getProperties().size() == 0)
            throw new QueryException("found no properties on subquery");
        AbstractProperty property = select.getProperties().get(0);
        if (!(property instanceof Property))
            throw new QueryException("properties of type '" + 
                    property.getClass().getSimpleName() + "' not supported on subquery 'select'" );            
        if (property.getPath() != null)
            throw new QueryException("property paths not supported on subquery 'select'" );            
             
        commonj.sdo.Property prop = contextType.getProperty(((Property)property).getName());
        if (!prop.getType().isDataType())
            throw new QueryException("reference properties (" 
                +  contextType.getName() + "." + prop.getName() + ") not supported on subquery 'select'" );            
        if (prop.isMany())
            throw new QueryException("multi-valued properties (" 
                +  contextType.getName() + "." + prop.getName() + ") not supported on subquery 'select'" );            
                
        filter.append("select ");
        
        FunctionValues function = ((Property)property).getFunction();
        if (function != null)
            switch (function)
            {
                case MIN:
                    filter.append("min( ");
                    break;
                case MAX:
                    filter.append("max( ");
                    break;
                case AVG:
                    filter.append("avg( ");
                    break;
                default:
                    throw new QueryException("unsupported function '" + function.toString() + "'");
            }
        
        filter.append(alias + ".");
        filter.append(DATA_ACCESS_CLASS_MEMBER_PREFIX + prop.getName() + " ");
        if (function != null)
            filter.append(") ");
        filter.append("from org.plasma.sdo.das.pom." + contextType.getName() + " " + alias);
        
        this.getContext().setTraversal(Traversal.ABORT);
        // abort further traversal
    }                               

    public void start(Where where)
    {
        filter.append(" where ");        
        super.start(where); // continue QOM traversal
    }                               

    public void start(Property property)
    {                
    	if (log.isDebugEnabled()) {
            log.debug("visit Property, " + property.getName());    
    	}

        FunctionValues function = property.getFunction();
        if (function != null)
            switch (function)
            {
                case MIN:
                    filter.append(" min( ");
                    break;
                case MAX:
                    filter.append(" max( ");
                    break;
                case AVG:
                    filter.append(" avg( ");
                    break;
                default:
                    throw new QueryException("unsupported function '" + function.toString() + "'");
            }

        Path path = property.getPath();

        if (filter.length() > 0)
            filter.append(" " + alias + ".");

        //EntityDef targetEntityDef = contextType;
        Type targetType = contextType;
        if (path != null)
        {
            for (int i = 0 ; i < path.getPathNodes().size(); i++)
            {
                AbstractPathElement pathElem = path.getPathNodes().get(i).getPathElement();
                if (pathElem instanceof WildcardPathElement)
                    throw new DataAccessException("wildcard path elements applicable for 'Select' clause paths only, not 'Where' clause paths");
                
                commonj.sdo.Property pdef = targetType.getProperty(
                		((PathElement)pathElem).getValue());
                
                targetType = pdef.getOpposite().getContainingType();

                if (!pdef.isMany())
                {
                    filter.append(DATA_ACCESS_CLASS_MEMBER_PREFIX + pdef.getName());
                    filter.append(".");
                }
                else
                {
                    String variableName = alias + String.valueOf(variableCount);
                    filter.append(DATA_ACCESS_CLASS_MEMBER_PREFIX + pdef.getName() + DATA_ACCESS_CLASS_MEMBER_MULTI_VALUED_SUFFIX
                        + ".contains(" + variableName + ") && "
                        + variableName + ".");
                    variableCount++;
                }
            }
        }
        //PropertyDef endpoint = mom.findPropertyDef(targetEntityDef, property.getName());
        commonj.sdo.Property endpoint = targetType.getProperty(property.getName());
        contextProperty = endpoint;
        filter.append(DATA_ACCESS_CLASS_MEMBER_PREFIX + endpoint.getName());
        
        if (function != null)
            filter.append(" )");
        
        super.start(property);
    }

	@Override
	protected void assembleSubquery(Property property, SubqueryOperator oper,
			Query query) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void processWildcardExpression(Property property,
			WildcardOperator oper, Literal literal) {
		// TODO Auto-generated method stub
		
	} 
       
}