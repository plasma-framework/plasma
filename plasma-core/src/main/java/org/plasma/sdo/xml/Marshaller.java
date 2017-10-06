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

import org.plasma.sdo.helper.DataConverter;

import commonj.sdo.DataObject;
import commonj.sdo.Property;
import commonj.sdo.Type;
import commonj.sdo.helper.XMLDocument;

/**
 * An data-graph traversal based assembler/builder abstract superclass.
 */
public abstract class Marshaller extends XMLProcessor {

  protected XMLDocument document;
  protected MarshallerFlavor flavor;

  @SuppressWarnings("unused")
  private Marshaller() {
  }

  protected Marshaller(MarshallerFlavor flavor, XMLDocument document) {
    super();
    this.flavor = flavor;
    this.document = document;
    if (this.document == null)
      throw new IllegalArgumentException("expected 'document' argument");
  }

  protected Marshaller(XMLDocument document, XMLOptions options) {
    super(options);
    this.document = document;
    if (this.document == null)
      throw new IllegalArgumentException("expected 'document' argument");
  }

  protected String fromObject(Type sourceType, Object value) {
    return DataConverter.INSTANCE.toString(sourceType, value);
  }

  protected class PathNode {

    private DataObject target;
    private DataObject source;
    private Property sourceProperty;
    private Object userObject;
    private boolean terminated;

    @SuppressWarnings("unused")
    private PathNode() {
    }

    public PathNode(DataObject target, Object userObject, DataObject source, Property sourceProperty) {
      this.target = target;
      this.userObject = userObject;
      this.source = source;
      this.sourceProperty = sourceProperty;
    }

    public PathNode(DataObject target, DataObject source, Property sourceProperty) {
      this.target = target;
      this.source = source;
      this.sourceProperty = sourceProperty;
    }

    public DataObject getTarget() {
      return target;
    }

    public DataObject getSource() {
      return source;
    }

    public Property getSourceProperty() {
      return sourceProperty;
    }

    public Object getUserObject() {
      return userObject;
    }

    public boolean isTerminated() {
      return terminated;
    }

    public void setTerminated(boolean terminated) {
      this.terminated = terminated;
    }

  }

}
