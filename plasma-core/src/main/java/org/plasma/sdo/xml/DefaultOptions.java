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

package org.plasma.sdo.xml;

import org.apache.commons.logging.Log;
import org.xml.sax.ErrorHandler;

public class DefaultOptions implements XMLOptions {
  private boolean failOnValidationError;
  private String rootElementName;
  private String rootElementNamespaceURI;
  private String rootNamespacePrefix = "tns";
  private Log validationLog;
  private boolean validate = true;
  private ErrorHandler errorHandler;
  private String encoding;
  private boolean prettyPrint = true;

  public DefaultOptions(String rootElementNamespaceURI) {
    super();
    this.rootElementNamespaceURI = rootElementNamespaceURI;
  }

  public DefaultOptions(String rootElementNamespaceURI, String rootElementName) {
    super();
    this.rootElementName = rootElementName;
    this.rootElementNamespaceURI = rootElementNamespaceURI;
  }

  public boolean isFailOnValidationError() {
    return failOnValidationError;
  }

  public void setFailOnValidationError(boolean failOnValidationError) {
    this.failOnValidationError = failOnValidationError;
    if (this.failOnValidationError)
      this.validate = true;
  }

  public boolean isValidate() {
    return validate;
  }

  public void setValidate(boolean validate) {
    this.validate = validate;
  }

  public void setRootElementName(String rootElementName) {
    this.rootElementName = rootElementName;
  }

  public void setRootElementNamespaceURI(String rootElementNamespaceURI) {
    this.rootElementNamespaceURI = rootElementNamespaceURI;
  }

  public void setRootNamespacePrefix(String rootNamespacePrefix) {
    this.rootNamespacePrefix = rootNamespacePrefix;
  }

  public void setValidationLog(Log validationLog) {
    this.validationLog = validationLog;
  }

  public String getRootElementName() {
    return rootElementName;
  }

  public String getRootElementNamespaceURI() {
    return rootElementNamespaceURI;
  }

  public String getRootNamespacePrefix() {
    return rootNamespacePrefix;
  }

  public Log getValidationLog() {
    return validationLog;
  }

  public ErrorHandler getErrorHandler() {
    return errorHandler;
  }

  public void setErrorHandler(ErrorHandler handler) {
    this.errorHandler = handler;
  }

  public String getEncoding() {
    return encoding;
  }

  public void setEncoding(String encoding) {
    this.encoding = encoding;
  }

  @Override
  public boolean isPrettyPrint() {
    return prettyPrint;
  }

  @Override
  public void setPrettyPrint(boolean prettyPrint) {
    this.prettyPrint = prettyPrint;
  }

}
