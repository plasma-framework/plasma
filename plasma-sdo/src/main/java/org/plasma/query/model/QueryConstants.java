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

package org.plasma.query.model;

import org.plasma.query.Wildcard;
import org.plasma.sdo.access.provider.common.EntityConstants;

public interface QueryConstants {
  public static final String PATH_DELIMITER = "/";
  // public static final String DATE_FORMAT = "MM/dd/yyyy HH:mm:ss";
  // public static final String TIMESTAMP_FORMAT = "MM/dd/yyyy HH:mm:ss";
  public static final String WILDCARD = Wildcard.WILDCARD_CHAR;
  public static final int MAX_WILDCARDS = 2;
  public static final int MAX_RESULTS = 15000;

  public static final String FREE_TEXT_SELECT_PATH_DELIMITER_REGEXP = "['"
      + EntityConstants.DATA_ACCESS_TRAVERSAL_PATH_DELIMITER + "']";
  public static final String FREE_TEXT_SELECT_DECLARATION_DELIMITER_REGEXP = "['"
      + EntityConstants.DATA_ACCESS_DECLARATION_DELIMITER + "']";
}