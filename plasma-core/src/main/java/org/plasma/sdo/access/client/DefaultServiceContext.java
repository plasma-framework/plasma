package org.plasma.sdo.access.client;

import java.util.Properties;

import org.plasma.sdo.access.AccessServiceContext;

public class DefaultServiceContext implements AccessServiceContext {
  private Properties properties;

  public DefaultServiceContext() {
    this.properties = new Properties();
  }

  public DefaultServiceContext(Properties properties) {
    this.properties = properties;
  }

  @Override
  public void close() {
  }

  @Override
  public Properties getProperties() {
    return this.properties;
  }
}
