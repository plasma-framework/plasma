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

package org.plasma.sdo;

public enum DataFlavor {
  temporal, integral, real, string, other;

  public static DataFlavor fromDataType(DataType dataType) {
    switch (dataType) {
    case Decimal:
    case Double:
    case Float:
      return DataFlavor.real;
    case Boolean:
    case Int:
    case Integer:
    case Long:
    case Short:
      return DataFlavor.integral;
    case Character:
    case String:
    case Strings:
      return DataFlavor.string;
    case Date:
    case Duration:
    case DateTime:
    case Day:
    case Month:
    case MonthDay:
    case Year:
    case YearMonth:
    case YearMonthDay:
    case Time:
      return DataFlavor.temporal;
    case Byte:
    case Bytes:
    case URI:
    case Object:
    default:
      return DataFlavor.other;
    }
  }

}
