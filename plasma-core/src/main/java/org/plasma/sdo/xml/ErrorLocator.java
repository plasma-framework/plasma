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

import org.xml.sax.Locator;
import org.xml.sax.SAXParseException;

public class ErrorLocator implements Locator {
  private int lineNumber;
  private int columnNumber;
  private String publicId;
  private String systemId;

  public ErrorLocator(SAXParseException e) {
    this.lineNumber = e.getLineNumber();
    this.columnNumber = e.getColumnNumber();
    this.publicId = e.getPublicId();
    this.systemId = e.getSystemId();
  }

  public ErrorLocator(int lineNumber, int columnNumber, String publicId, String systemId) {
    super();
    this.lineNumber = lineNumber;
    this.columnNumber = columnNumber;
    this.publicId = publicId;
    this.systemId = systemId;
  }

  public int getLineNumber() {
    return lineNumber;
  }

  public void setLineNumber(int lineNumber) {
    this.lineNumber = lineNumber;
  }

  public int getColumnNumber() {
    return columnNumber;
  }

  public void setColumnNumber(int columnNumber) {
    this.columnNumber = columnNumber;
  }

  public String getPublicId() {
    return publicId;
  }

  public void setPublicId(String publicId) {
    this.publicId = publicId;
  }

  public String getSystemId() {
    return systemId;
  }

  public void setSystemId(String systemId) {
    this.systemId = systemId;
  }
}
