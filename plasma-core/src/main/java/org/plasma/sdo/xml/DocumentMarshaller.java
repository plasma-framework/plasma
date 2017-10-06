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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Stack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom2.Attribute;
import org.jdom2.CDATA;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.plasma.sdo.PlasmaDataGraphVisitor;
import org.plasma.sdo.PlasmaDataObject;
import org.plasma.sdo.PlasmaNode;
import org.plasma.sdo.PlasmaProperty;
import org.plasma.sdo.helper.PlasmaXSDHelper;

import commonj.sdo.DataObject;
import commonj.sdo.Property;
import commonj.sdo.helper.XMLDocument;

/**
 * A Document Object Model (DOM) based assembler/builder which constructs a JDOM
 * document while traversing/visiting a DataGraph.
 */
@Deprecated
public class DocumentMarshaller extends Marshaller {

  private static Log log = LogFactory.getFactory().getInstance(DocumentMarshaller.class);

  private Stack<PathNode> currentPath = new Stack<PathNode>();
  private Document result;

  public DocumentMarshaller(XMLDocument document) {
    super(MarshallerFlavor.DOM, document);
  }

  /**
   * Begins the document assembly process
   */
  public void start() {
    ((PlasmaDataObject) this.document.getRootObject()).accept(new DocumentAssemblerVisitor());
  }

  public Document getDocument() {
    return result;
  }

  @SuppressWarnings("deprecation")
  public String getContent() {
    XMLOutputter outputter = new XMLOutputter();
    return getContent(outputter);
  }

  @SuppressWarnings("deprecation")
  public String getContent(java.lang.String indent, boolean newlines) {
    XMLOutputter outputter = new XMLOutputter();
    outputter.getFormat().setIndent(indent);
    return getContent(outputter);
  }

  public String getContent(Format format) {
    return getContent(new XMLOutputter(format));
  }

  private String getContent(XMLOutputter outputter) {
    String result = null;
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    try {
      if (this.result == null)
        throw new IllegalStateException(
            "no content yet assembled - first traverse a DataGraph using PlasmaDataObject.accept()");
      outputter.output(this.result, os);
      os.flush();
      result = new String(os.toByteArray());
    } catch (java.io.IOException e) {
    } finally {
      try {
        os.close();
      } catch (IOException e) {
      }
    }
    return result;
  }

  class DocumentAssemblerVisitor implements PlasmaDataGraphVisitor {

    public void visit(DataObject target, DataObject source, String sourcePropertyName, int level) {
      PlasmaProperty sourceProperty = null;
      if (source != null)
        sourceProperty = (PlasmaProperty) source.getType().getProperty(sourcePropertyName);

      if (log.isDebugEnabled())
        if (source == null)
          log.debug("visit: " + target.getType().getName() + "("
              + ((PlasmaNode) target).getUUIDAsString() + ")");
        else
          log.debug("visit: " + source.getType().getName() + "("
              + ((PlasmaNode) source).getUUIDAsString() + ")." + sourceProperty.getName() + "->"
              + target.getType().getName() + "(" + ((PlasmaNode) target).getUUIDAsString() + ")");

      Element element = new Element(PlasmaXSDHelper.INSTANCE.getLocalName(target.getType()));
      addContent(element, target);
      if (source == null) {// root
        result = new Document(element);
        currentPath.push(new PathNode(target, element, source, sourceProperty));
        return;
      }

      if (log.isDebugEnabled())
        log.debug("level (" + level + "), stack-size (" + currentPath.size() + "): "
            + target.getType().getName());
      PathNode pathNode = currentPath.peek();
      while (currentPath.size() > level)
        // pop to current level
        pathNode = currentPath.pop();
      if (currentPath.size() != level)
        throw new IllegalStateException("unexpected path size, " + currentPath.size()
            + " and traversal level, " + String.valueOf(level));
      Element parent = (Element) pathNode.getUserObject();
      parent.addContent(element);
      currentPath.push(pathNode);
      currentPath.push(new PathNode(target, element, source, sourceProperty));
    }

  }

  private void addContent(Element element, DataObject dataObject) {
    for (Property property : dataObject.getType().getDeclaredProperties()) {
      PlasmaProperty prop = (PlasmaProperty) property;
      if (!prop.getType().isDataType())
        continue;
      Object value = dataObject.get(prop);
      if (value == null)
        continue;
      if (prop.isXMLAttribute()) {

        element.setAttribute(new Attribute(PlasmaXSDHelper.INSTANCE.getLocalName(prop), String
            .valueOf(value)));
      } else {
        Element propertyElement = new Element(PlasmaXSDHelper.INSTANCE.getLocalName(prop));
        propertyElement.addContent(new CDATA(String.valueOf(value)));
        element.addContent(propertyElement);
      }

    }
  }

}
