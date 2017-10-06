package org.plasma.runtime.annotation;

import org.plasma.runtime.ConfigurationException;

public class AmbiguousProviderException extends ConfigurationException {

  private static final long serialVersionUID = 1L;

  public AmbiguousProviderException(String message) {
    super(message);
  }

  public AmbiguousProviderException(Throwable t) {
    super(t);
  }

}
