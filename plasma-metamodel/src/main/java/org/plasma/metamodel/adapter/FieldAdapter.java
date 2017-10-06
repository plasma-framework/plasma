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

package org.plasma.metamodel.adapter;

import org.plasma.metamodel.Class;
import org.plasma.metamodel.Package;
import org.plasma.metamodel.Property;

public class FieldAdapter {
  private Package fieldPackage;
  private Class fieldClass;
  private Property field;

  @SuppressWarnings("unused")
  private FieldAdapter() {
  }

  public FieldAdapter(Package fieldPackage, Class fieldClass, Property field) {
    super();
    this.fieldPackage = fieldPackage;
    this.fieldClass = fieldClass;
    this.field = field;
    if (this.fieldPackage == null)
      throw new IllegalArgumentException("missing argument, 'fieldPackage'");
    if (this.fieldClass == null)
      throw new IllegalArgumentException("missing argument, 'fieldClass'");
    if (this.field == null)
      throw new IllegalArgumentException("missing argument, 'field'");
  }

  public Package getFieldPackage() {
    return fieldPackage;
  }

  public Class getFieldClass() {
    return fieldClass;
  }

  public Property getField() {
    return field;
  }
}
