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

package org.plasma.sdo.access.service;

import java.util.Date;

public class LockedEntityEJBException extends javax.ejb.EJBException {
  private String entityName;
  private String userName;
  private Date lockedDate;

  private LockedEntityEJBException() {
    super("unsuppoted");
  }

  private LockedEntityEJBException(String message) {
    super("unsuppoted");
  }

  private LockedEntityEJBException(Throwable t) {
    super("unsuppoted");
  }

  public LockedEntityEJBException(org.plasma.sdo.access.LockedEntityException e) {
    super("found existing lock for entity '" + e.getEntityName() + "' by user '" + e.getUserName()
        + "' at time '" + String.valueOf(e.getLockedDate()));
    this.entityName = e.getEntityName();
    this.userName = e.getUserName();
    this.lockedDate = e.getLockedDate();
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