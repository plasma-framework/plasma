/**
 * Copyright 2017 TerraMeta Software, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.plasma.sdo.access;

import io.reactivex.Observable;

import java.util.List;

import org.plasma.query.model.Query;
import org.plasma.sdo.core.SnapshotMap;

import commonj.sdo.DataGraph;

public interface DataAccessService {

  public void initialize(AccessServiceContext context);

  public void close();

  public int count(Query query);

  public int[] count(Query[] queries);

  public DataGraph[] find(Query query);

  public DataGraph[] find(Query query, int maxResults);

  public List<DataGraph[]> find(Query[] queries);

  public SnapshotMap commit(DataGraph dataGraph, String username);

  public SnapshotMap commit(DataGraph[] dataGraphs, String username);

  public Observable<DataGraph> findAsStream(Query query);

  public Observable<DataGraph> findAsStream(Query query, int maxResults);

  public List<Observable<DataGraph>> findAsStream(Query[] queries);
}
