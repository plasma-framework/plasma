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

public interface ErrorConstants {
  public static String ERROR_SEVERITY_INFO = "error_severity_info";
  public static String ERROR_SEVERITY_WARNING = "error_severity_warning";
  public static String ERROR_SEVERITY_FATAL = "error_severity_fatal";
  public static String ERROR_TYPE_INTERNAL = "error_type_internal";
  public static String ERROR_TYPE_USER_INPUT = "error_type_user_input";
  public static String ERROR_TYPE_USER_CONCURRENCY = "error_type_user_concurrency";
  public static String ERROR_TYPE_USER_SESSION = "error_type_user_session";

  public static String ERROR_MESSAGE_INTERNAL = "error_message_internal";
  public static String ERROR_MESSAGE_MAX_WILDCARDS_EXCEEDED = "error_message_max_wildcards_exceeded";
  public static String ERROR_MESSAGE_MAX_RESULTS_EXCEEDED = "error_message_max_results_exceeded";
  public static String ERROR_MESSAGE_NO_RESULTS_FOUND = "error_message_no_results_found";
  public static String ERROR_MESSAGE_NO_CRITERIA_ENTERED = "error_message_no_criteria_entered";
  public static String ERROR_MESSAGE_INVALID_DATE_FORMAT = "error_message_invalid_date_format";
  public static String ERROR_MESSAGE_INVALID_DATE_FORMAT2 = "error_message_invalid_date_format2";
  public static String ERROR_MESSAGE_INVALID_NUMBER_FORMAT = "error_message_invalid_number_format";
  public static String ERROR_MESSAGE_INVALID_SNAPSHOT = "error_message_invalid_snapshot";
  public static String ERROR_MESSAGE_RECORD_LOCKED = "error_message_record_locked";
  public static String ERROR_MESSAGE_SESSION_TIMEOUT = "error_message_session_timeout";
  public static String ERROR_MESSAGE_USER_ROLE_NOT_FOUND = "error_message_user_role_not_found";
  public static String ERROR_MESSAGE_PPSO_NOT_FOUND = "error_message_ppso_not_found";
  public static String ERROR_MESSAGE_CHANNEL_NOT_FOUND = "error_message_channel_not_found";
}