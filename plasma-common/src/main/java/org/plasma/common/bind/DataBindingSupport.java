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

package org.plasma.common.bind;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import javax.xml.transform.Source;

public class DataBindingSupport {

  protected JAXBContext context;

  @SuppressWarnings("unused")
  private DataBindingSupport() {
  }

  public DataBindingSupport(JAXBContext context) {
    this.context = context;
  }

  public String marshal(Object root) throws JAXBException {
    StringWriter writer = new StringWriter();
    Marshaller m = context.createMarshaller();
    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
    m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
    m.marshal(root, writer);
    return writer.toString();
  }

  public void marshal(Object root, OutputStream stream) throws JAXBException {
    Marshaller m = context.createMarshaller();
    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
    m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
    m.marshal(root, stream);
  }

  public void marshal(Object root, OutputStream stream, boolean formattedOutput)
      throws JAXBException {
    Marshaller m = context.createMarshaller();
    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(formattedOutput));
    m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
    m.marshal(root, stream);
  }

  public Object unmarshal(String xml) throws JAXBException {
    Unmarshaller u = context.createUnmarshaller();
    return u.unmarshal(new StringReader(xml));
  }

  public Object unmarshal(Reader reader) throws JAXBException {
    Unmarshaller u = context.createUnmarshaller();
    return u.unmarshal(reader);
  }

  public Object unmarshal(Source source) throws JAXBException {
    Unmarshaller u = context.createUnmarshaller();
    return u.unmarshal(source);
  }

  public Object unmarshal(InputStream stream) throws JAXBException {
    StringWriter writer = new StringWriter();
    Unmarshaller u = context.createUnmarshaller();
    return u.unmarshal(stream);
  }

}
