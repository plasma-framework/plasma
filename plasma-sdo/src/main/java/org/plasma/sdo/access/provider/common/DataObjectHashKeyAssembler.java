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
import org.plasma.query.model.Path;
import org.plasma.query.model.Property;
import org.plasma.query.model.Select;
import org.plasma.query.visitor.DefaultQueryVisitor;
import org.plasma.sdo.PlasmaDataObject;
import org.plasma.sdo.access.DataAccessException;

import commonj.sdo.Type;


public class DataObjectHashKeyAssembler extends DefaultQueryVisitor
{
    private Select select;    
    private PlasmaDataObject root;    
    private Type rootType;    
    private StringBuffer hashKey = new StringBuffer("24");

    @SuppressWarnings("unused")
    private DataObjectHashKeyAssembler() {}

    public DataObjectHashKeyAssembler(Select select, Type rootType)
    {
        this.select = select;
        this.rootType = rootType;
    }

    public String getHashKey(PlasmaDataObject v)
    {
        reset();
        this.root = v;
        select.accept(this);
        return hashKey.toString();
    }

    public void reset() {
        hashKey = new StringBuffer("24");
    }

    @Override
    public void start(Property property)                                                                            
    {                   
        //if (!property.getDistinct())
        //    return true;                                                                                            
        Type contextType = rootType; // init
        PlasmaDataObject contextValueObject = root;
        if (property.getPath() != null)                                                                             
        {                                                                                                           
            Path path = property.getPath();
            for (int i = 0 ; i < path.getPathNodes().size(); i++)
            {
                commonj.sdo.Property contextProp = contextType.getProperty(
                		path.getPathNodes().get(i).getPathElement().getValue());
                if (contextProp.isMany())
                    throw new DataAccessException("traversal of milti-valued properties not supported");                                    
                contextType = contextProp.getOpposite().getContainingType();
                contextValueObject = (PlasmaDataObject)contextValueObject.get(contextProp.getName());   
                if (contextValueObject == null)
                    break;             
            }                                                                                                       
        }                                                                                                           
        commonj.sdo.Property endpointPDef = contextType.getProperty(property.getName());
        if (contextValueObject != null)
        {  
            Object endpoint = contextValueObject.get(endpointPDef.getName());
            hashKey.append(String.valueOf(endpoint));
        } 
                      
        super.start(property);                                                                  
    }                                                                                                                                                                                                                                                                                                                                                           
}