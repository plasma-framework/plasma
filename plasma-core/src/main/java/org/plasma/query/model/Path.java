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

package org.plasma.query.model;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Path", propOrder = { "pathNodes" })
@XmlRootElement(name = "Path")
public class Path {

  @XmlElement(name = "PathNode", required = true)
  protected List<PathNode> pathNodes;
  private transient String pathString;

  public Path() {
    super();
  }

  public Path(String e1) {
    this();
    addPathNode(createPathNode(e1));
  }

  public Path(String e1, String e2) {
    this();
    addPathNode(createPathNode(e1));
    addPathNode(createPathNode(e2));
  }

  public Path(String e1, String e2, String e3) {
    this();
    addPathNode(createPathNode(e1));
    addPathNode(createPathNode(e2));
    addPathNode(createPathNode(e3));
  }

  public Path(String e1, String e2, String e3, String e4) {
    this();
    addPathNode(createPathNode(e1));
    addPathNode(createPathNode(e2));
    addPathNode(createPathNode(e3));
    addPathNode(createPathNode(e4));
  }

  public Path(String e1, String e2, String e3, String e4, String e5) {
    this();
    addPathNode(createPathNode(e1));
    addPathNode(createPathNode(e2));
    addPathNode(createPathNode(e3));
    addPathNode(createPathNode(e4));
    addPathNode(createPathNode(e5));
  }

  public Path(String e1, String e2, String e3, String e4, String e5, String e6) {
    this();
    addPathNode(createPathNode(e1));
    addPathNode(createPathNode(e2));
    addPathNode(createPathNode(e3));
    addPathNode(createPathNode(e4));
    addPathNode(createPathNode(e5));
    addPathNode(createPathNode(e6));
  }

  public Path(String e1, String e2, String e3, String e4, String e5, String e6, String e7) {
    this();
    addPathNode(createPathNode(e1));
    addPathNode(createPathNode(e2));
    addPathNode(createPathNode(e3));
    addPathNode(createPathNode(e4));
    addPathNode(createPathNode(e5));
    addPathNode(createPathNode(e6));
    addPathNode(createPathNode(e7));
  }

  public Path(String e1, String e2, String e3, String e4, String e5, String e6, String e7, String e8) {
    this();
    addPathNode(createPathNode(e1));
    addPathNode(createPathNode(e2));
    addPathNode(createPathNode(e3));
    addPathNode(createPathNode(e4));
    addPathNode(createPathNode(e5));
    addPathNode(createPathNode(e6));
    addPathNode(createPathNode(e7));
    addPathNode(createPathNode(e8));
  }

  public Path(String[] elements) {
    this();
    for (int i = 0; i < elements.length; i++)
      addPathNode(createPathNode(elements[i]));
  }

  public static Path forName(String name) {
    return new Path(name);
  }

  public static Path forNames(String[] names) {
    return new Path(names);
  }

  public void addPathNode(PathNode node) {
    getPathNodes().add(node);
  }

  private PathNode createPathNode(String name) {
    return new PathNode(name);
  }

  public List<PathNode> getPathNodes() {
    if (pathNodes == null) {
      pathNodes = new ArrayList<PathNode>();
    }
    return this.pathNodes;
  }

  public int size() {
    return getPathNodes().size();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + toString().hashCode();
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Path other = (Path) obj;
    if (!toString().equals(other.toString()))
      return false;
    return true;
  }

  @Override
  public String toString() {
    if (pathString == null) {
      StringBuilder buf = new StringBuilder();
      int i = 0;
      for (PathNode node : getPathNodes()) {
        if (i > 0)
          buf.append("/");
        buf.append(node.toString());
        i++;
      }
      this.pathString = buf.toString();
    }
    return this.pathString;
  }

}
