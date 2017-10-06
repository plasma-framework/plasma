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

package org.plasma.query;

/**
 * A clause describing the query extent or root type
 */
public interface From {

  /**
   * Returns the URI
   * 
   * @return the URI
   */
  public String getUri();

  /**
   * Returns the type (logical) name
   * 
   * @return the type (logical) name
   */
  public String getName();

  /**
   * Gets the value of the randomSample property. A floating point value between
   * zero (result entity is never included) and 1 (result entity is always
   * included) used to sample or filter results. Cannot be used with with
   * (potentially) many types of predicates depending on the restrictions within
   * the underlying data store.
   * 
   * @return possible object is {@link Float }
   * 
   */
  public Float getRandomSample();
}