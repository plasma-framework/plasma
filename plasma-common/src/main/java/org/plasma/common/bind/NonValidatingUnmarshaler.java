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
import java.io.Reader;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;

import org.xml.sax.SAXException;

public class NonValidatingUnmarshaler extends DataBindingSupport {

  private Unmarshaller unmarshaler;

  private NonValidatingUnmarshaler() {
    super(null);
  }

  public NonValidatingUnmarshaler(JAXBContext context) throws JAXBException, SAXException {
    super(context);
    this.unmarshaler = createUnmarshaler(context);
  }

  /**
   * Creates an unmarshaler using the given factories.
   * 
   * @param context
   *          the JAXB context
   * @return the unmarshaler
   * @throws JAXBException
   * @throws SAXException
   */
  private Unmarshaller createUnmarshaler(JAXBContext context) throws JAXBException, SAXException {
    Unmarshaller u = context.createUnmarshaller();
    // adds a custom object factory
    // u.setProperty("com.sun.xml.bind.ObjectFactory",new
    // ObjectFactoryEx());

    return u;
  }

  public Object unmarshal(String xml) throws JAXBException, UnmarshalException {
    return unmarshaler.unmarshal(new StringReader(xml));
  }

  public Object unmarshal(InputStream xml) throws JAXBException, UnmarshalException {
    return unmarshaler.unmarshal(xml);
  }

  public Object unmarshal(Reader reader) throws JAXBException, UnmarshalException {
    return unmarshaler.unmarshal(reader);
  }

  public Object unmarshal(Source source) throws JAXBException, UnmarshalException {
    return unmarshaler.unmarshal(source);
  }
}
