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
package org.plasma.sdo.access.client;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import org.plasma.query.Query;
import org.plasma.sdo.access.DataAccessException;
import org.plasma.sdo.core.CoreObject;

import commonj.sdo.DataGraph;

/**
 * Hides the complexity of collecting only the objects that need to be committed
 * and sent to the app layer. We don't want to send ANY objects or properties
 * we don't have to. Serialization is expensive!
 */
public class DefaultEJBDataAccessClient implements DataAccessClient
{
//    private static Log log = LogFactory.getLog(PersistenceServiceProxy.class);

    private DataAccessClient serviceDelegate;

    public DefaultEJBDataAccessClient()
    {
    	serviceDelegate = new EJBDataAccessClient();        
    }

    public DataGraph[] find(Query query)
    {
        return serviceDelegate.find(query); 
    }

    public DataGraph[] find(Query query, int maxResults)
    {
        return serviceDelegate.find(query, maxResults); 
    }

    public List find(Query[] queries)
    {
        return serviceDelegate.find(queries);  
    }

    public int count(Query query)
    {
        return serviceDelegate.count(query);
    }

    public int[] count(Query[] queries)
    {
        return serviceDelegate.count(queries);
    }

    public void commit(DataGraph dataGraph, String username) {
        serviceDelegate.commit(dataGraph, username);
    }

    public void commit(DataGraph[] dataGraphs, String username) {
        serviceDelegate.commit(dataGraphs, username);
    }
    
    // serialize results for testing
    public void serialize(CoreObject[] results, String filename)
    {
        try {
            FileOutputStream fileOut = new FileOutputStream(filename);   
            ObjectOutputStream out = new ObjectOutputStream(fileOut);         
            out.writeObject(results);                                         
            out.close();                                                      
            fileOut.close();                                                  
        }
        catch (IOException e) {
            throw new DataAccessException(e);
        }
    }

    // deserialize results for testing
    public CoreObject[] deserialize(String filename)
    {        
        try {
            FileInputStream  fileIn = new FileInputStream(filename);   
            ObjectInputStream in = new ObjectInputStream(fileIn); 
            CoreObject[] results = (CoreObject[])in.readObject();        
            in.close();
            fileIn.close();
            return results;
        }
        catch (IOException e) {
            throw new DataAccessException(e);
        }
        catch (ClassNotFoundException e) {
            throw new DataAccessException(e);
        }
    }



}