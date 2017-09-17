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

import org.plasma.metamodel.ClassRef;
import org.plasma.metamodel.DataTypeRef;
import org.plasma.sdo.DataType;

public class MetaClassInfo {

  private boolean usePrimitives;

  // used for data properties
  private DataTypeRef dataTypeRef;
  private DataType sdoType;
  private java.lang.Class<?> primitiveTypeClass;
  private java.lang.Class<?> primitiveWrapperTypeClass;

  // used for reference properties
  private ClassRef classRef;

  public MetaClassInfo(DataTypeRef dataTypeRef, DataType sdoType,
      java.lang.Class<?> primitiveTypeClass, java.lang.Class<?> primitiveWrapperTypeClass,
      boolean usePrimitives) {
    super();
    this.dataTypeRef = dataTypeRef;
    this.sdoType = sdoType;
    this.primitiveTypeClass = primitiveTypeClass;
    this.primitiveWrapperTypeClass = primitiveWrapperTypeClass;
    this.usePrimitives = usePrimitives;
  }

  public MetaClassInfo(ClassRef classRef, boolean usePrimitives) {
    super();
    this.classRef = classRef;
    this.usePrimitives = usePrimitives;
  }

  public String getSimpleName() {
    if (this.dataTypeRef != null) {
      if (this.usePrimitives)
        return this.primitiveTypeClass.getSimpleName();
      else
        return this.primitiveWrapperTypeClass.getSimpleName();
    } else {
      return this.classRef.getName();
    }
  }

  /**
   * Return the simple (unqualified) type name applicable for use in collection
   * classes, for primitive types (e.g. double) the equivalent wrapper class
   * (Double) name is used.
   * 
   * @return
   */
  public String getCollectionSimpleName() {
    if (this.dataTypeRef != null) {
      return this.primitiveWrapperTypeClass.getSimpleName();
    } else {
      return this.classRef.getName();
    }
  }
}
