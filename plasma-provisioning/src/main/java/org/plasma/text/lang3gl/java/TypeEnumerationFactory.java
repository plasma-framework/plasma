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

package org.plasma.text.lang3gl.java;

import org.plasma.metamodel.Enumeration;
import org.plasma.metamodel.Package;
import org.plasma.sdo.DataType;
import org.plasma.text.lang3gl.EnumerationFactory;
import org.plasma.text.lang3gl.Lang3GLContext;

public class TypeEnumerationFactory extends DefaultFactory implements EnumerationFactory {

  public TypeEnumerationFactory(Lang3GLContext context) {
    super(context);
  }

  @Override
  public Lang3GLContext getContext() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Class<?> getTypeClass(DataType dataType) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String createBaseDirectoryName(Package pkg) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String createDirectoryName(Package pkg) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String createFileName(Enumeration clss) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String createContent(Package pkg, Enumeration type) {
    // TODO Auto-generated method stub
    return null;
  }

}
