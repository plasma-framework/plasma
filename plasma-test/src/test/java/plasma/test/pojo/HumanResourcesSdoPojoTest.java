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
package plasma.test.pojo;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.plasma.runtime.DataAccessProviderName;
import org.plasma.sdo.PlasmaDataGraph;
import org.plasma.sdo.access.AccessServiceContext;
import org.plasma.sdo.access.client.PojoDataAccessClient;
import org.plasma.sdo.access.client.SDODataAccessClient;
import org.plasma.sdo.core.CoreChange;
import org.plasma.sdo.helper.PlasmaDataFactory;
import org.plasma.sdo.helper.PlasmaTypeHelper;

import commonj.sdo.DataGraph;
import commonj.sdo.Type;
import quickstart.pojo.model.Organization;
import quickstart.pojo.model.OrgCat;
import quickstart.pojo.model.Person;

public class HumanResourcesSdoPojoTest {
  private static Log log = LogFactory.getFactory().getInstance(HumanResourcesSdoPojoTest.class);

  // @Test
  public void testGraphCreate() throws IOException {
    AccessServiceContext accessContext = new AccessServiceContext() {
      @Override
      public void close() {
      }

      @Override
      public Properties getProperties() {
        // TODO Auto-generated method stub
        return new Properties();
      }
    };
    SDODataAccessClient client = new SDODataAccessClient(new PojoDataAccessClient(
        DataAccessProviderName.NOOP, accessContext));

    DataGraph dataGraph = PlasmaDataFactory.INSTANCE.createDataGraph();
    dataGraph.getChangeSummary().beginLogging();
    Type rootType = PlasmaTypeHelper.INSTANCE.getType(Organization.class);
    String randomSuffix = String.valueOf(System.nanoTime()).substring(10);

    Organization org = (Organization) dataGraph.createRootObject(rootType);
    org.setName("Best Buy Corporation Inc. (" + randomSuffix + ")");
    org.setCategory(OrgCat.RETAIL.getInstanceName());
    org.setCreatedDate(new Date());

    Organization child = org.createChild();
    child.setName("Best Buy Sales (" + randomSuffix + ")");
    child.setCategory(OrgCat.RETAIL.getInstanceName());
    child.setCreatedDate(new Date());

    Person pers = child.createEmployee();
    pers.setFirstName("Mark");
    pers.setLastName("Hamburg (" + randomSuffix + ")");
    pers.setAge(55);
    pers.setCreatedDate(new Date());

    client.commit(dataGraph, "test");

  }

  @Test
  public void testGraphLargeVectorCreate() throws IOException {
    AccessServiceContext accessContext = new AccessServiceContext() {
      @Override
      public void close() {
      }

      @Override
      public Properties getProperties() {
        // TODO Auto-generated method stub
        return new Properties();
      }
    };
    SDODataAccessClient client = new SDODataAccessClient(new PojoDataAccessClient(
        DataAccessProviderName.NOOP, accessContext));

    DataGraph dataGraph = PlasmaDataFactory.INSTANCE.createDataGraph();
    dataGraph.getChangeSummary().beginLogging();
    Type rootType = PlasmaTypeHelper.INSTANCE.getType(Organization.class);
    String randomSuffix = String.valueOf(System.nanoTime()).substring(10);

    Organization bestBuy = (Organization) dataGraph.createRootObject(rootType);
    bestBuy.setName("Best Buy Corporation Inc. (" + randomSuffix + ")");
    bestBuy.setCategory(OrgCat.RETAIL.getInstanceName());
    bestBuy.setCreatedDate(new Date());

    Organization bestBuySales = bestBuy.createChild();
    bestBuySales.setName("Best Buy Sales (" + randomSuffix + ")");
    bestBuySales.setCategory(OrgCat.RETAIL.getInstanceName());
    bestBuySales.setCreatedDate(new Date());

    Organization bestBuyComputerSales = bestBuySales.createChild();
    bestBuyComputerSales.setName("Computer Sales");
    bestBuyComputerSales.setCategory(OrgCat.RETAIL.getInstanceName());
    bestBuyComputerSales.setCreatedDate(new Date());

    Organization bestBuyPlasmaTvSales = bestBuySales.createChild();
    bestBuyPlasmaTvSales.setName("Plasma TV Sales");
    bestBuyPlasmaTvSales.setCategory(OrgCat.RETAIL.getInstanceName());
    bestBuyPlasmaTvSales.setCreatedDate(new Date());

    long start = System.currentTimeMillis();
    Random rand = new Random();
    int personNum = 15000;
    int embedSize = 256;
    for (int persId = 0; persId < personNum; persId++) {
      Person pers = bestBuyPlasmaTvSales.createEmployee();
      pers.setFirstName("First" + persId);
      pers.setLastName("Last (" + randomSuffix + ")" + persId);
      pers.setAge(rand.nextInt(80));
      pers.setCreatedDate(new Date());
      double[] embed = new double[embedSize];
      for (int i = 0; i < embedSize; i++)
        embed[i] = rand.nextDouble();
      pers.setProfileImageEmbed(embed);
    }
    client.commit(dataGraph, "test");
    log.info("creation time: " + String.valueOf(System.currentTimeMillis() - start));

    start = System.currentTimeMillis();
    for (Person pers : bestBuyPlasmaTvSales.getEmployee()) {
      double[] embed2 = new double[embedSize];
      for (int i = 0; i < embedSize; i++)
        embed2[i] = rand.nextDouble();
      pers.setProfileImageEmbed(embed2);
    }
    client.commit(dataGraph, "test");
    log.info("modification time: " + String.valueOf(System.currentTimeMillis() - start));

  }

}
