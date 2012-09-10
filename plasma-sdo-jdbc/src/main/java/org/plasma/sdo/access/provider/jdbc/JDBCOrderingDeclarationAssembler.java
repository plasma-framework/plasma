package org.plasma.sdo.access.provider.jdbc;

// java imports
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.query.model.OrderBy;
import org.plasma.query.model.Path;
import org.plasma.query.model.Property;
import org.plasma.query.model.QueryConstants;
import org.plasma.query.visitor.DefaultQueryVisitor;
import org.plasma.query.visitor.Traversal;
import org.plasma.sdo.PlasmaProperty;
import org.plasma.sdo.PlasmaType;
import org.plasma.sdo.access.model.EntityConstants;

public class JDBCOrderingDeclarationAssembler extends DefaultQueryVisitor
    implements QueryConstants, EntityConstants
{
    private static Log log = LogFactory.getLog(JDBCOrderingDeclarationAssembler.class);

    private PlasmaType contextType;
    private commonj.sdo.Property contextProp;
    private StringBuilder orderingDeclaration = new StringBuilder();
    private AliasMap aliasMap;

    @SuppressWarnings("unused")
	private JDBCOrderingDeclarationAssembler() {}

    public JDBCOrderingDeclarationAssembler(OrderBy orderby,
    		PlasmaType contextType, AliasMap aliasMap)
    {
        this.contextType = contextType;
        this.aliasMap = aliasMap;
        
        if (orderby.getTextContent() == null)
            orderby.accept(this); // traverse
        else
            orderingDeclaration.append(orderby.getTextContent().getValue());            
    }

    public String getOrderingDeclaration() { return orderingDeclaration.toString(); }

    public void start(Property property)                  
    {                
        if (orderingDeclaration.length() == 0)
        	orderingDeclaration.append("ORDER BY ");
        	
    	if (orderingDeclaration.length() > "ORDER BY ".length())
            orderingDeclaration.append(", ");
    	PlasmaType targetType = contextType;
        if (property.getPath() != null)
        {
            Path path = property.getPath();
            for (int i = 0 ; i < path.getPathNodes().size(); i++)
            {
                PlasmaProperty prop = (PlasmaProperty)targetType.getProperty(
                	path.getPathNodes().get(i).getPathElement().getValue());
                targetType = (PlasmaType)prop.getType();
            }
        }
        PlasmaProperty endpoint = (PlasmaProperty)targetType.getProperty(property.getName());        
        contextProp = endpoint;
        
        String alias = this.aliasMap.getAlias(targetType);
        orderingDeclaration.append(alias);
        orderingDeclaration.append(".");
        orderingDeclaration.append(endpoint.getPhysicalName());
        if (property.getDirection() == null || property.getDirection().ordinal() == org.plasma.query.model.SortDirectionValues.ASC.ordinal())
            orderingDeclaration.append(" ASC");
        else  
            orderingDeclaration.append(" DESC");
        this.getContext().setTraversal(Traversal.ABORT);
    } 
}