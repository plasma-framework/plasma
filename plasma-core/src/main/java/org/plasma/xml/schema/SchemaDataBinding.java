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

package org.plasma.xml.schema;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.UnmarshalException;
import javax.xml.transform.Source;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.common.bind.BindingValidationEventHandler;
import org.plasma.common.bind.DataBinding;
import org.plasma.common.bind.ValidatingUnmarshaler;
import org.xml.sax.SAXException;

public class SchemaDataBinding implements DataBinding {

  private static Log log = LogFactory.getLog(SchemaDataBinding.class);
  public static String FILENAME_SCHEMA_CHAIN_ROOT = "XMLSchema.xsd";
  // public static String FILENAME_SCHEMA_CHAIN_ROOT = "sdoXMLSchema.xsd";

  // just classes in the same package where can find the above respective
  // schema files via Class.getResource*
  public static Class<?> RESOURCE_CLASS = SchemaDataBinding.class;

  private static ValidatingUnmarshaler validatingUnmarshaler;

  public static Class<?>[] FACTORIES = { org.plasma.xml.schema.ObjectFactory.class,
      org.plasma.xml.sdox.ObjectFactory.class, org.plasma.metamodel.ObjectFactory.class, };

  @SuppressWarnings("unused")
  private SchemaDataBinding() {
  }

  public SchemaDataBinding(BindingValidationEventHandler validationEventHandler)
      throws JAXBException, SAXException {
    if (validatingUnmarshaler == null) {
      log.info("loading schema chain...");
      validatingUnmarshaler = new ValidatingUnmarshaler(
          RESOURCE_CLASS.getResource(FILENAME_SCHEMA_CHAIN_ROOT),
          JAXBContext.newInstance(FACTORIES), validationEventHandler);
    }
  }

  public Class<?>[] getObjectFactories() {
    return FACTORIES;
  }

  public String marshal(Object root) throws JAXBException {
    return validatingUnmarshaler.marshal(root);
  }

  public void marshal(Object root, OutputStream stream) throws JAXBException {
    validatingUnmarshaler.marshal(root, stream);
  }

  public void marshal(Object root, OutputStream stream, boolean formattedOutput)
      throws JAXBException {
    validatingUnmarshaler.marshal(root, stream, formattedOutput);
  }

  public Object unmarshal(String xml) throws JAXBException {
    return validatingUnmarshaler.unmarshal(xml);
  }

  public Object unmarshal(Reader reader) throws JAXBException {
    return validatingUnmarshaler.unmarshal(reader);
  }

  public Object unmarshal(Source source) throws JAXBException {
    return validatingUnmarshaler.unmarshal(source);
  }

  public Object unmarshal(InputStream stream) throws JAXBException {
    return validatingUnmarshaler.unmarshal(stream);
  }

  public Object validate(String xml) throws JAXBException {
    return validatingUnmarshaler.validate(xml);
  }

  public Object validate(InputStream xml) throws JAXBException, UnmarshalException {
    return validatingUnmarshaler.validate(xml);
  }

  public BindingValidationEventHandler getValidationEventHandler() throws JAXBException {
    return this.validatingUnmarshaler.getValidationEventHandler();
  }

}
