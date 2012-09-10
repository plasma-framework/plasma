
package org.plasma.sdo.access.provider.jdbc;

import java.util.Collection;


/**
 * Encapsulates the collection resulting from query execution along
 * with the Query object itself. Hopefully usefull where the query
 * construction is seperated off and the results are a collection rather than
 * a single object. In this event after collection iteration is complete, the
 * query must be closed via. Query.closeAll(), Query.close(), Extent.close(Iterator),
 * or sume such JDO call which frees appropriate re sources associated with query
 * execution.  
 *
 */
public class JDBCQueryResults 
{
    private Collection collection;
    
    private JDBCQueryResults () {}   
        
    public JDBCQueryResults(Collection collection) {
        this.collection = collection;
    }    
    
    public Collection getResults() { return collection; }
    
}
