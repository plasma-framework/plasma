package org.plasma.runtime.annotation;

import org.plasma.runtime.ConfigurationException;

public class UnknownProviderException extends ConfigurationException {

  private static final long serialVersionUID = 1L;

  public UnknownProviderException(String message) {
    super(message);
  }

  public UnknownProviderException(Throwable t) {
    super(t);
  }

}
