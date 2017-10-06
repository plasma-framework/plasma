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
 * and sent to the app layer. We don't want to send ANY objects or properties we
 * don't have to. Serialization is expensive!
 */
public class DefaultEJBDataAccessClient implements DataAccessClient {
  // private static Log log = LogFactory.getLog(PersistenceServiceProxy.class);

  private DataAccessClient serviceDelegate;

  public DefaultEJBDataAccessClient() {
    serviceDelegate = new EJBDataAccessClient();
  }

  public DataGraph[] find(Query query) {
    return serviceDelegate.find(query);
  }

  public DataGraph[] find(Query query, int maxResults) {
    return serviceDelegate.find(query, maxResults);
  }

  public List find(Query[] queries) {
    return serviceDelegate.find(queries);
  }

  public int count(Query query) {
    return serviceDelegate.count(query);
  }

  public int[] count(Query[] queries) {
    return serviceDelegate.count(queries);
  }

  public void commit(DataGraph dataGraph, String username) {
    serviceDelegate.commit(dataGraph, username);
  }

  public void commit(DataGraph[] dataGraphs, String username) {
    serviceDelegate.commit(dataGraphs, username);
  }

  // serialize results for testing
  public void serialize(CoreObject[] results, String filename) {
    try {
      FileOutputStream fileOut = new FileOutputStream(filename);
      ObjectOutputStream out = new ObjectOutputStream(fileOut);
      out.writeObject(results);
      out.close();
      fileOut.close();
    } catch (IOException e) {
      throw new DataAccessException(e);
    }
  }

  // deserialize results for testing
  public CoreObject[] deserialize(String filename) {
    try {
      FileInputStream fileIn = new FileInputStream(filename);
      ObjectInputStream in = new ObjectInputStream(fileIn);
      CoreObject[] results = (CoreObject[]) in.readObject();
      in.close();
      fileIn.close();
      return results;
    } catch (IOException e) {
      throw new DataAccessException(e);
    } catch (ClassNotFoundException e) {
      throw new DataAccessException(e);
    }
  }

}