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
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * A simple error handler using the given XMLOptions to determine the desired
 * behavior when an error is encountered.
 */
public class DefaultErrorHandler implements org.xml.sax.ErrorHandler {

  private static Log log = LogFactory.getLog(DefaultErrorHandler.class);
  private XMLOptions options;

  @SuppressWarnings("unused")
  private DefaultErrorHandler() {
  }

  public DefaultErrorHandler(XMLOptions options) {
    this.options = options;
  }

  public void error(SAXParseException e) throws SAXException {
    String msg = "line:column[" + e.getLineNumber() + ":" + e.getColumnNumber() + "]";
    msg += " - " + e.getMessage();
    if (options.isFailOnValidationError()) {
      throw new SAXParseException(msg, new ErrorLocator(e));
    } else {
      if (options.getValidationLog() != null) {
        options.getValidationLog().error(msg);
      } else
        log.error(msg);
    }
  }

  public void fatalError(SAXParseException e) throws SAXException {
    String msg = "line:column[" + e.getLineNumber() + ":" + e.getColumnNumber() + "]";
    msg += " - " + e.getMessage();
    if (options.isFailOnValidationError()) {
      throw new SAXParseException(msg, new ErrorLocator(e));
    } else {
      if (options.getValidationLog() != null) {
        options.getValidationLog().fatal(msg);
      } else
        log.fatal(msg);
    }
  }

  public void warning(SAXParseException e) throws SAXException {
    String msg = "line:column[" + e.getLineNumber() + ":" + e.getColumnNumber() + "]";
    msg += " - " + e.getMessage();
    if (options.getValidationLog() != null) {
      options.getValidationLog().warn(msg);
    } else
      log.warn(msg);
  }
}
