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

import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;

public interface DataBinding {

  public String marshal(Object root) throws JAXBException;

  public Object unmarshal(String xml) throws JAXBException;

  public Object unmarshal(InputStream stream) throws JAXBException;

  public Object validate(String xml) throws JAXBException;

  public Object validate(InputStream xml) throws JAXBException, UnmarshalException;

  public BindingValidationEventHandler getValidationEventHandler() throws JAXBException;
}
