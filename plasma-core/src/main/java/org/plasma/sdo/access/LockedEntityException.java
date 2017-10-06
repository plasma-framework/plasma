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

import java.util.Date;

public class LockedEntityException extends DataAccessException {
  private String entityName;
  private String userName;
  private Date lockedDate;

  private LockedEntityException() {
    super("unsuppoted");
  }

  private LockedEntityException(String message) {
    super("unsuppoted");
  }

  private LockedEntityException(Throwable t) {
    super("unsuppoted");
  }

  public LockedEntityException(String entityName, String userName, Date lockedDate) {
    super("found existing lock for entity '" + entityName + "' by user '" + userName
        + "' at time '" + String.valueOf(lockedDate));
    this.entityName = entityName;
    this.userName = userName;
    this.lockedDate = lockedDate;
  }

  public String getEntityName() {
    return entityName;
  }

  public String getUserName() {
    return userName;
  }

  public Date getLockedDate() {
    return lockedDate;
  }
}