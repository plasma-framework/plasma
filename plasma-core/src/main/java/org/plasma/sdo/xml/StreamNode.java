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

import javax.xml.namespace.QName;
import javax.xml.stream.Location;

import org.plasma.sdo.PlasmaType;

public abstract class StreamNode {
  private PlasmaType type;
  protected int line;
  protected int column;
  protected QName name;

  @SuppressWarnings("unused")
  private StreamNode() {
  }

  public StreamNode(PlasmaType type, QName name, Location loc) {
    if (type == null)
      throw new IllegalArgumentException("expected type argument");
    this.type = type;
    this.name = name;
    this.line = loc.getLineNumber();
    this.column = loc.getColumnNumber();
  }

  public PlasmaType getType() {
    return type;
  }

  public int getLine() {
    return line;
  }

  public int getColumn() {
    return column;
  }

  public String getLocalName() {
    return name.getLocalPart();
  }

  public String getNamespaceURI() {
    return name.getNamespaceURI();
  }

  public String getPrefix() {
    return name.getPrefix();
  }

}
