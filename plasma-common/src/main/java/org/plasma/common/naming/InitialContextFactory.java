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

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.plasma.common.env.EnvProperties;

public class InitialContextFactory {

  public static String DEFAULT_JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";

  public static final String DPS_NAMING_URL = "dps.jndi.naming.provider.url";

  private static InitialContextFactory contextFactory = new InitialContextFactory();

  public static InitialContextFactory getInstance() {
    return contextFactory;
  }

  private InitialContextFactory() {
  }

  public InitialContext getInitialContext(String namingUrl) throws NamingException {
    if (namingUrl == null) {
      throw new RuntimeException(DPS_NAMING_URL + " is a required.");
    }

    Properties env = new Properties();
    env.put(Context.INITIAL_CONTEXT_FACTORY, DEFAULT_JNDI_FACTORY);
    env.put(Context.PROVIDER_URL, namingUrl);

    return new InitialContext(env);
  }

  public InitialContext getInitialContext() throws NamingException {
    // String namingFactory =
    // EnvProperties.instance().getProperty(Context.INITIAL_CONTEXT_FACTORY,
    // InitialContextFactory.DEFAULT_JNDI_FACTORY);
    String namingUrl = EnvProperties.instance().getProperty(DPS_NAMING_URL);

    if (namingUrl == null) {
      throw new RuntimeException("The following required property is missing: '" + DPS_NAMING_URL
          + "':" + EnvProperties.instance().dumpProperties("\n    "));
    }

    return getInitialContext(namingUrl);
  }
}
