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
package plasma.test.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.query.model.Query;
import org.plasma.runtime.DataAccessProviderName;
import org.plasma.runtime.DataStoreType;
import org.plasma.runtime.annotation.PlasmaServiceProvider;
import org.plasma.sdo.access.AccessServiceContext;
import org.plasma.sdo.access.PlasmaDataAccessService;
import org.plasma.sdo.core.SnapshotMap;

import commonj.sdo.DataGraph;
import io.reactivex.Observable;

@PlasmaServiceProvider(storeType = DataStoreType.OTHER, providerName = DataAccessProviderName.NOOP)
public class NoOpGraphService implements PlasmaDataAccessService {

  private static Log log = LogFactory.getLog(NoOpGraphService.class);
  private AccessServiceContext context;

  public NoOpGraphService() {
  }

  public void initialize(AccessServiceContext context) {
    // noop
  }

  public void close() {
  }

  @Override
  public int count(Query query) {
    // noop
    return 0;
  }

  @Override
  public int[] count(Query[] queries) {
    // noop
    return null;
  }

  @Override
  public DataGraph[] find(Query query) {
    // noop
    return null;
  }

  @Override
  public DataGraph[] find(Query query, int maxResults) {
    // noop
    return null;
  }

  @Override
  public List<DataGraph[]> find(Query[] queries) {
    // noop
    return null;
  }

  @Override
  public SnapshotMap commit(DataGraph dataGraph, String username) {
    return new SnapshotMap();
  }

  @Override
  public SnapshotMap commit(DataGraph[] dataGraphs, String username) {
    // noop
    return null;
  }

  @Override
  public Observable<DataGraph> findAsStream(Query query) {
    // noop
    return null;
  }

  @Override
  public Observable<DataGraph> findAsStream(Query query, int maxResults) {
    // noop
    return null;
  }

  @Override
  public List<Observable<DataGraph>> findAsStream(Query[] queries) {
    // noop
    return null;
  }

}
