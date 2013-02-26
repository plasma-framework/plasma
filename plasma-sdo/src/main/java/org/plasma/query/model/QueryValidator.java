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

// java imports
import org.plasma.config.PlasmaConfig;
import org.plasma.query.EmptySelectClauseException;
import org.plasma.query.InvalidPathElementException;
import org.plasma.query.QueryException;
import org.plasma.sdo.helper.PlasmaTypeHelper;

import commonj.sdo.Type;

public class QueryValidator extends AbstractQueryValidator
    implements QueryConstants
{
    private Type rootType;
    private Type contextType;

    @SuppressWarnings("unused")
	private QueryValidator() {}

    public QueryValidator(Query query, Type contextType)
    {
        this.rootType = contextType;
        this.contextType = contextType;
        query.accept(this);
    }
    
    public QueryValidator(Where where, Type contextType)
    {
        this.rootType = contextType;
        this.contextType = contextType;
        where.accept(this);
    }
 
    /**
     * Ensures class associated with From clause entity exists
     */
    public void start(Select select)
    {        
        if (select.getProperties().size() == 0)
        	throw new EmptySelectClauseException("Select clause has no properties");

        super.start(select);
    }
    
    /**
     * Ensures class associated with From clause entity exists
     */
    public void start(From from)
    {
        Type type = PlasmaTypeHelper.INSTANCE.getType(from.getEntity().getNamespaceURI(), 
        		from.getEntity().getName());
        
        try {
            String pkgName = PlasmaConfig.getInstance().getSDONamespaceByURI(type.getURI()).getProvisioning().getPackageName();
            Class.forName(pkgName + "." + type.getName()); // just validate it
        }
        catch (ClassNotFoundException e) {
            throw new QueryException(e);
        }
        super.start(from);
    }

    public void start(Expression expression)
    {
        for (int i = 0; i < expression.getTerms().size(); i++)
        {
            WildcardOperator oper = expression.getTerms().get(i).getWildcardOperator();
            if (oper != null)
            {
                if (i == 0)
                    throw new QueryException("did not expect WildcardOperator as first term in expression");
                Term t = expression.getTerms().get(i-1);
                if (t.getProperty() == null)
                    throw new QueryException("expected Property before WildcardOperator in expression");
                t = expression.getTerms().get(i+1);
                if (t.getLiteral() == null)
                    throw new QueryException("expected Literal after WildcardOperator in expression");
            }
        }
        super.start(expression);
    }

    // replace this with path-item
    public void start(Property property)
    {
        if (property.getPath() != null)
        {
            try {
                Path path = property.getPath();
                for (int i = 0 ; i < path.getPathNodes().size(); i++)
                {
                    PathNode node = path.getPathNodes().get(i);
                    AbstractPathElement elem = node.getPathElement();
                    if (elem instanceof PathElement) {
                        commonj.sdo.Property prop = contextType.getProperty(((PathElement)elem).getValue());
                        	
                        if (prop.getType().isDataType())
                            throw new InvalidPathElementException("expected reference property as path element");
                        this.contextType = prop.getType();
                    }
                    if (node.getWhere() != null)
                    	new QueryValidator(node.getWhere(), this.contextType);
                }
            }
            catch (QueryException e) {
                throw new InvalidPathElementException(e);
            }
        }
        
        if (!WILDCARD.equals(property.getName())) {
            commonj.sdo.Property endpoint = contextType.getProperty(property.getName());
            if (endpoint.isMany())
                throw new QueryException("expected singular property as endpoint, not '"
                    + endpoint.getName() + "'");
        }
        
        this.contextType = this.rootType; // reset after property traversal
        super.start(property);
    }

    public void start(Literal literal)
    {
        if (literal.getValue() == null)
            throw new QueryException("found null content for literal");
        super.start(literal);
    }       
}
