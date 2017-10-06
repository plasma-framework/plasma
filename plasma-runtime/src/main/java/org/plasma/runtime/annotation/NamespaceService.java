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
package org.plasma.runtime.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.atteo.classindex.IndexAnnotated;
import org.plasma.runtime.DataAccessProviderName;
import org.plasma.runtime.DataStoreType;

/**
 * Provides hints enabling discovery of a service provider to associate with the
 * namespace as well as properties to be passed to the provider.
 */
@Target(ElementType.PACKAGE)
@Retention(RetentionPolicy.RUNTIME)
@IndexAnnotated
public @interface NamespaceService {
  /**
   * Returns the type or category of data store
   * 
   * @return the type or category of data store
   */
  public DataStoreType storeType();

  /**
   * Returns the data store provider name
   * 
   * @return the data store provider name
   */
  public DataAccessProviderName providerName();

  /**
   * Returns an array of property name value pairs delimited by the '=' char.
   * E.g. String[] = { foo=bar }
   * 
   * @return
   */
  public String[] properties() default {};
}
