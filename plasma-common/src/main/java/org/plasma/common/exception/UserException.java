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

package org.plasma.common.exception;

/**
 */
public class UserException extends PlasmaRuntimeException {
  protected String severityId;
  protected String typeId;
  protected String messageId;
  protected Object[] params;

  private UserException() {
  }

  public UserException(String severityId, String typeId, String messageId) {
    super("user error");
    this.severityId = severityId;
    this.typeId = typeId;
    this.messageId = messageId;
  }

  public UserException(String severityId, String typeId, String messageId, String defaultErrorText) {
    super(defaultErrorText);
    this.severityId = severityId;
    this.typeId = typeId;
    this.messageId = messageId;
  }

  public UserException(String severityId, String typeId, String messageId, Object[] params) {
    super("user error");
    this.severityId = severityId;
    this.typeId = typeId;
    this.messageId = messageId;
    this.params = params;
  }

  public UserException(String severityId, String typeId, String messageId, Object[] params,
      String defaultErrorText) {
    super(defaultErrorText);
    this.severityId = severityId;
    this.typeId = typeId;
    this.messageId = messageId;
    this.params = params;
  }

  public String getSeverityId() {
    return severityId;
  }

  public String getTypeId() {
    return typeId;
  }

  public String getMessageId() {
    return messageId;
  }

  public Object[] getParams() {
    return params;
  }

  public boolean hasParams() {
    return params != null;
  }
}
