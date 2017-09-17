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
 * A non-reference property with a type which is a String data type.
 */
public interface StringDataProperty extends DataProperty {

  /**
   * Constructs a function, which returns the start of the string data before
   * the given string value occurs in it, and returns this data property for use
   * in subsequent operations
   * 
   * @return this data property for use in subsequent operations
   */
  public StringDataProperty substringBefore(String value);

  /**
   * Constructs a function, which returns the tail of the string data after the
   * given string value occurs in it, and returns this data property for use in
   * subsequent operations
   * 
   * @return this data property for use in subsequent operations
   */
  public StringDataProperty substringAfter(String value);

  /**
   * Constructs a function, which returns the upper case for the string data,
   * and returns this data property for use in subsequent operations
   * 
   * @return this data property for use in subsequent operations
   */
  public StringDataProperty upperCase();

  /**
   * Constructs a function, which returns the lower case for the string data,
   * and returns this data property for use in subsequent operations
   * 
   * @return this data property for use in subsequent operations
   */
  public StringDataProperty lowerCase();

  /**
   * Constructs a function, which removes leading and trailing spaces from the
   * specified string, and replaces all internal sequences of white space with
   * one and returns the result.
   * 
   * @return this data property for use in subsequent operations
   */
  public StringDataProperty normalizeSpace();
}