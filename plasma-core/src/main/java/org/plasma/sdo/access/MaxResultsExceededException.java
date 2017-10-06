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

package org.plasma.sdo.access;

public class MaxResultsExceededException extends DataAccessException {
  private int numResults;
  private int maxResults;

  public MaxResultsExceededException(int numResults, int maxResults) {
    super("maximum results exceeded - expected max of " + String.valueOf(maxResults)
        + " results but found " + String.valueOf(numResults) + " results");
    this.numResults = numResults;
    this.maxResults = maxResults;
  }

  public int getNumResults() {
    return numResults;
  }

  public int getMaxResults() {
    return maxResults;
  }
}