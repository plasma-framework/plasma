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

package org.plasma.common.naming;

import javax.naming.Context;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ServiceLocator {
  private static ServiceLocator me;
  protected static Log log = LogFactory.getLog(ServiceLocator.class);

  private ServiceLocator() throws NamingException {
  }

  // Returns the instance of ServiceLocator class
  public static ServiceLocator getInstance() throws NamingException {
    if (me == null) {
      initInstance(); // double-checked locking pattern
    }
    return me;
  }

  private static synchronized void initInstance() throws NamingException {
    if (me == null) {
      me = new ServiceLocator();
    }
  }

  /**
   * @deprecated use getStub(Class ejbInterfaceClass) or getStub(String ejbName)
   *             instead
   * @param ejbName
   * @param ejbInterfaceClass
   * @return
   * @throws NamingException
   */
  public Object getStub(String ejbName, Class ejbInterfaceClass) throws NamingException {
    Context ctx = InitialContextFactory.getInstance().getInitialContext();
    // Object stub = ctx.lookup(ejbInterfaceClass.getName());
    Object stub = ctx.lookup(ejbName);
    return stub;
  }

  /**
   * Get an ejb stub found with the supplied interface class in the local JNDI
   * tree
   * 
   * @param ejbInterfaceClass
   * @return An ejb stub implementing the supplied interface class.
   * @throws NamingException
   */
  public Object getStub(Class ejbInterfaceClass) throws NamingException {
    Context ctx = InitialContextFactory.getInstance().getInitialContext();
    return ctx.lookup("java:/comp/env/ejb/" + ejbInterfaceClass.getSimpleName());
  }

  /**
   * Get an ejb stub found with the supplied name
   * 
   * @param ejbName
   * @return An ejb stub found at the supplied name
   * @throws NamingException
   */
  public Object getStub(String ejbName) throws NamingException {
    Context ctx = InitialContextFactory.getInstance().getInitialContext();
    Object stub = ctx.lookup(ejbName);
    return stub;
  }
}
