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

package org.plasma.sdo.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import commonj.sdo.Property;
import commonj.sdo.Type;
import commonj.sdo.helper.DataHelper;

public class PlasmaDataHelper implements DataHelper {

  static public volatile DataHelper INSTANCE = initializeInstance();

  private PlasmaDataHelper() {
  }

  private static DataHelper initializeInstance() {
    if (INSTANCE == null)
      INSTANCE = new PlasmaDataHelper();
    return INSTANCE;
  }

  /**
   * Convert the specified value to an {@link Type#getInstanceClass() instance}
   * of the specified type. Supported conversions are listed in Section 16 of
   * the SDO specification.
   * 
   * @param type
   *          the target {@link Type#isDataType() data type}.
   * @param value
   *          the value to convert
   * @return a value of the specified type's instance class
   * @throws IllegalArgumentException
   *           if the value could not be converted
   * @see #convert(Property, Object)
   */
  public Object convert(Type type, Object value) {
    return DataConverter.INSTANCE.convert(type, value);
  }

  /**
   * Convert the specified value to an {@link Type#getInstanceClass() instance}
   * of the specified property's {@link Property#getType() type}. The specified
   * value must be a List if the property is {@link Property#isMany() many
   * valued}. In this case, all the values in the List are converted.
   * 
   * @param property
   *          the target {@link Type#isDataType() data type} property.
   * @param value
   *          the value or List of values to convert
   * @return a converted value or list of converted values
   * @throws IllegalArgumentException
   *           if the value could not be converted
   * @see #convert(Type, Object)
   */
  @Override
  public Object convert(Property property, Object value) {
    return DataConverter.INSTANCE.convert(property.getType(), value);
  }

  @Override
  public Calendar toCalendar(String dateString) {
    if (dateString == null) {
      return null;
    }

    Date date = toDate(dateString);
    if (date == null) {
      return null;
    }

    Calendar calendar = new GregorianCalendar();
    calendar.setTime(date);

    return calendar;
  }

  @Override
  public Calendar toCalendar(String dateString, Locale locale) {
    if (dateString == null || locale == null) {
      return null;
    }

    Date date = toDate(dateString);
    if (date == null) {
      return null;
    }

    Calendar calendar = new GregorianCalendar(locale);
    calendar.setTime(date);
    return calendar;
  }

  @Override
  public Date toDate(String dateString) {
    if (dateString == null) {
      return null;
    }

    DateFormat[] formats = DataConverter.INSTANCE.getDateFormats();
    for (int i = 0; i < formats.length; ++i) {
      try {
        return formats[i].parse(dateString);
      } catch (ParseException parseException) {
      }
    }
    return null;
  }

  @Override
  public String toDateTime(Date date) {
    if (date == null) {
      return null;
    }
    return DataConverter.INSTANCE.getDateTimeFormat().format(date);
  }

  @Override
  public String toDateTime(Calendar calendar) {
    if (calendar == null) {
      return null;
    }

    return toDateTime(calendar.getTime());
  }

  @Override
  public String toDay(Date date) {
    if (date == null) {
      return null;
    }

    return DataConverter.INSTANCE.getDayFormat().format(date);
  }

  @Override
  public String toDay(Calendar calendar) {
    if (calendar == null) {
      return null;
    }

    return toDay(calendar.getTime());
  }

  @Override
  public String toDuration(Date date) {
    if (date == null) {
      return null;
    }

    SimpleDateFormat f = new SimpleDateFormat("'P'yyyy'Y' MM'M' dd'D' 'T' HH'H' mm'M' ss'S.'SSS");

    return f.format(date);
  }

  @Override
  public String toDuration(Calendar calendar) {
    if (calendar == null) {
      return null;
    }

    return toDuration(calendar.getTime());
  }

  @Override
  public String toMonth(Date date) {
    if (date == null) {
      return null;
    }

    return DataConverter.INSTANCE.getMonthFormat().format(date);
  }

  @Override
  public String toMonth(Calendar calendar) {
    if (calendar == null) {
      return null;
    }

    return toMonth(calendar.getTime());
  }

  @Override
  public String toMonthDay(Date date) {
    if (date == null) {
      return null;
    }

    return DataConverter.INSTANCE.getMonthDayFormat().format(date);
  }

  @Override
  public String toMonthDay(Calendar calendar) {
    if (calendar == null) {
      return null;
    }

    return toMonthDay(calendar.getTime());
  }

  @Override
  public String toTime(Date date) {
    if (date == null) {
      return null;
    }
    return DataConverter.INSTANCE.getTimeFormat().format(date);
  }

  @Override
  public String toTime(Calendar calendar) {
    if (calendar == null) {
      return null;
    }

    return toTime(calendar.getTime());
  }

  @Override
  public String toYear(Date date) {
    if (date == null) {
      return null;
    }

    return DataConverter.INSTANCE.getYearFormat().format(date);
  }

  @Override
  public String toYear(Calendar calendar) {
    if (calendar == null) {
      return null;
    }

    return toYear(calendar.getTime());
  }

  @Override
  public String toYearMonth(Date date) {
    if (date == null) {
      return null;
    }
    return DataConverter.INSTANCE.getYearMonthFormat().format(date);
  }

  @Override
  public String toYearMonth(Calendar calendar) {
    if (calendar == null) {
      return null;
    }

    return toYearMonth(calendar.getTime());
  }

  @Override
  public String toYearMonthDay(Date date) {
    if (date == null) {
      return null;
    }

    return DataConverter.INSTANCE.getYearMonthDayFormat().format(date);
  }

  @Override
  public String toYearMonthDay(Calendar calendar) {
    if (calendar == null) {
      return null;
    }

    return toYearMonthDay(calendar.getTime());
  }

}
