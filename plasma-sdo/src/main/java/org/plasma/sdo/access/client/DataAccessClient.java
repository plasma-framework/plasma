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

import java.util.List;

import org.plasma.query.Query;

import commonj.sdo.DataGraph;

public interface DataAccessClient
{
    public DataGraph[] find(Query query);
    public DataGraph[] find(Query query, int maxResults);    
    public List<DataGraph[]> find(Query[] queries);
    public int count(Query query);
    public int[] count(Query[] queries);
    public void commit(DataGraph dataGraph, String username);
    public void commit(DataGraph[] dataGraphs, String username);
}