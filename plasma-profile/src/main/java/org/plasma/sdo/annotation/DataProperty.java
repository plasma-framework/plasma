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

package org.plasma.sdo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.plasma.sdo.DataType;

/**
 * Associates property related meta data with a Java enum literal. Only Java
 * enums (enumerations) should include this annotation.
 * <p>
 * </p>
 * Note: Only Java enum types (enumerations) should include this annotation,
 * otherwise an <@link InvalidAnnotationException> will occur during processing.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataProperty {
  public DataType dataType();

  public boolean isNullable() default true;

  public boolean isMany() default false;

  public boolean isReadOnly() default false;
}
