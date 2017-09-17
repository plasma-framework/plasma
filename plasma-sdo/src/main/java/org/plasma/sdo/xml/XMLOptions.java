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

public interface XMLOptions {

  /**
   * The desired root namespace prefix.
   * 
   * @return the desired root namespace prefix
   */
  public String getRootNamespacePrefix();

  /**
   * Sets desired root namespace prefix.
   * 
   * @param rootNamespacePrefix
   *          the desired root namespace prefix
   */
  public void setRootNamespacePrefix(String rootNamespacePrefix);

  /**
   * The root namespace URI.
   * 
   * @return the root namespace URI
   */
  public String getRootElementNamespaceURI();

  /**
   * Sets root namespace URI.
   * 
   * @param rootElementName
   *          the root namespace URI
   */
  public void setRootElementNamespaceURI(String rootElementName);

  /**
   * The root element name.
   * 
   * @return the root element name
   */
  public String getRootElementName();

  /**
   * Sets root element name
   * 
   * @param rootElementName
   *          the root element name
   */
  public void setRootElementName(String rootElementName);

  /**
   * Returns whether to perform XML Schema (XSD) validation when reading XML
   * input. By default, any errors encountered are logged within the context of
   * the parsing utility unless a log is supplied.
   * 
   * @return whether to perform XML Schema (XSD) validation when reading XML
   *         input.
   * @see org.apache.commons.logging.Log
   */
  public boolean isValidate();

  /**
   * Set whether to perform XML Schema (XSD) validation when reading XML input.
   * By default, any errors encountered are logged within the context of the
   * parsing utility unless a log is supplied.
   * 
   * @param validate
   *          whether to perform XML Schema (XSD) validation
   */
  public void setValidate(boolean validate);

  /**
   * Returns the current XML Schema (XSD) validation error log.
   * 
   * @return current XML Schema (XSD) validation error log
   */
  public Log getValidationLog();

  /**
   * Sets the current XML Schema (XSD) validation error log
   * 
   * @param validationLog
   *          the log
   */
  public void setValidationLog(Log validationLog);

  /**
   * Returns whether to propagate XML parser errors encountered when validating
   * an XML document against an associated XML Schema. Only 'ERROR' and 'FATAL'
   * errors are propagated.
   * 
   * @return whether to propagate XML parser errors encountered when validating
   *         an XML document against an associated XML Schema
   */
  public boolean isFailOnValidationError();

  /**
   * Returns the current XML Schema (XSD) validation error handler
   * 
   * @return the current XML Schema (XSD) validation error handler
   */
  public ErrorHandler getErrorHandler();

  /**
   * Sets the current XML Schema (XSD) validation error handler
   * 
   * @param handler
   *          the current XML Schema (XSD) validation error handler
   */
  public void setErrorHandler(ErrorHandler handler);

  /**
   * Returns the character encoding
   * 
   * @return the character encoding
   */
  public String getEncoding();

  /**
   * Sets the character encoding. E.g. ISO-8859-1
   * 
   * @param encoding
   *          the character encoding
   */
  public void setEncoding(String encoding);

  /**
   * For XML writer utilities, returns whether to indent and format the XML
   * output.
   * 
   * @return
   */
  public boolean isPrettyPrint();

  /**
   * For XML writer utilities, sets whether to indent and format the XML output.
   * 
   * @param prettyPrint
   *          whether to indent and format the XML output
   */
  public void setPrettyPrint(boolean prettyPrint);

}
