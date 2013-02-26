/**
 *         PlasmaSDO™ License
 * 
 * This is a community release of PlasmaSDO™, a dual-license 
 * Service Data Object (SDO) 2.1 implementation. 
 * This particular copy of the software is released under the 
 * version 2 of the GNU General Public License. PlasmaSDO™ was developed by 
 * TerraMeta Software, Inc.
 * 
 * Copyright (c) 2013, TerraMeta Software, Inc. All rights reserved.
 * 
 * General License information can be found below.
 * 
 * This distribution may include materials developed by third
 * parties. For license and attribution notices for these
 * materials, please refer to the documentation that accompanies
 * this distribution (see the "Licenses for Third-Party Components"
 * appendix) or view the online documentation at 
 * <http://plasma-sdo.org/licenses/>.
 *  
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

	static public DataHelper INSTANCE = initializeInstance();
	private DateFormat[] DATE_FORMATS = {
			new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.'SSS Z"),
			new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.'SSS"),
			new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"),
			new SimpleDateFormat("yyyy-MM-dd'T'HH:mm"),
			new SimpleDateFormat(
					"'P'yyyy'Y' MM'M' dd'D' 'T' HH'H' mm'M' ss'S.'SSS"),
			new SimpleDateFormat("--MM zz"),
			new SimpleDateFormat("--MM-dd zz"),
			new SimpleDateFormat("---dd zz"),
			new SimpleDateFormat("HH:mm:ss'.'SSS"),
			new SimpleDateFormat("yyyy-MM-dd"),
			new SimpleDateFormat("yyyy-MM"), new SimpleDateFormat("yyyy") };

	private PlasmaDataHelper() {
	}

	private static DataHelper initializeInstance() {
		if (INSTANCE == null)
			INSTANCE = new PlasmaDataHelper();
		return INSTANCE;
	}

	/**
	 * Convert the specified value to an {@link Type#getInstanceClass()
	 * instance} of the specified type. Supported conversions are listed in
	 * Section 16 of the SDO specification.
	 * 
	 * @param type
	 *            the target {@link Type#isDataType() data type}.
	 * @param value
	 *            the value to convert
	 * @return a value of the specified type's instance class
	 * @throws IllegalArgumentException
	 *             if the value could not be converted
	 * @see #convert(Property, Object)
	 */
	public Object convert(Type type, Object value) {
		return DataConverter.INSTANCE.convert(type, value);
	}

	/**
	 * Convert the specified value to an {@link Type#getInstanceClass()
	 * instance} of the specified property's {@link Property#getType() type}.
	 * The specified value must be a List if the property is
	 * {@link Property#isMany() many valued}. In this case, all the values in
	 * the List are converted.
	 * 
	 * @param property
	 *            the target {@link Type#isDataType() data type} property.
	 * @param value
	 *            the value or List of values to convert
	 * @return a converted value or list of converted values
	 * @throws IllegalArgumentException
	 *             if the value could not be converted
	 * @see #convert(Type, Object)
	 */
	public Object convert(Property property, Object value) {
		return DataConverter.INSTANCE.convert(property.getType(), value);
	}

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

	public Date toDate(String dateString) {
		if (dateString == null) {
			return null;
		}
		for (int i = 0; i < DATE_FORMATS.length; ++i) {
			try {
				return DATE_FORMATS[i].parse(dateString);
			} catch (ParseException parseException) {
			}
		}
		return null;
	}

	public String toDateTime(Date date) {
		if (date == null) {
			return null;
		}

		SimpleDateFormat f = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss'.'SSS zz");

		return f.format(date);
	}

	public String toDateTime(Calendar calendar) {
		if (calendar == null) {
			return null;
		}

		return toDateTime(calendar.getTime());
	}

	public String toDay(Date date) {
		if (date == null) {
			return null;
		}

		SimpleDateFormat f = new SimpleDateFormat("---dd zz");

		return f.format(date);
	}

	public String toDay(Calendar calendar) {
		if (calendar == null) {
			return null;
		}

		return toDay(calendar.getTime());
	}

	public String toDuration(Date date) {
		if (date == null) {
			return null;
		}

		SimpleDateFormat f = new SimpleDateFormat(
				"'P'yyyy'Y' MM'M' dd'D' 'T' HH'H' mm'M' ss'S.'SSS");

		return f.format(date);
	}

	public String toDuration(Calendar calendar) {
		if (calendar == null) {
			return null;
		}

		return toDuration(calendar.getTime());
	}

	public String toMonth(Date date) {
		if (date == null) {
			return null;
		}

		SimpleDateFormat f = new SimpleDateFormat("--MM zz");

		return f.format(date);
	}

	public String toMonth(Calendar calendar) {
		if (calendar == null) {
			return null;
		}

		return toMonth(calendar.getTime());
	}

	public String toMonthDay(Date date) {
		if (date == null) {
			return null;
		}

		SimpleDateFormat f = new SimpleDateFormat("--MM-dd zz");

		return f.format(date);
	}

	public String toMonthDay(Calendar calendar) {
		if (calendar == null) {
			return null;
		}

		return toMonthDay(calendar.getTime());
	}

	public String toTime(Date date) {
		if (date == null) {
			return null;
		}

		SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss'.'SSS zz");

		return f.format(date);
	}

	public String toTime(Calendar calendar) {
		if (calendar == null) {
			return null;
		}

		return toTime(calendar.getTime());
	}

	public String toYear(Date date) {
		if (date == null) {
			return null;
		}

		SimpleDateFormat f = new SimpleDateFormat("yyyy zz");

		return f.format(date);
	}

	public String toYear(Calendar calendar) {
		if (calendar == null) {
			return null;
		}

		return toYear(calendar.getTime());
	}

	public String toYearMonth(Date date) {
		if (date == null) {
			return null;
		}

		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM zz");

		return f.format(date);
	}

	public String toYearMonth(Calendar calendar) {
		if (calendar == null) {
			return null;
		}

		return toYearMonth(calendar.getTime());
	}

	public String toYearMonthDay(Date date) {
		if (date == null) {
			return null;
		}

		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd zz");

		return f.format(date);
	}

	public String toYearMonthDay(Calendar calendar) {
		if (calendar == null) {
			return null;
		}

		return toYearMonthDay(calendar.getTime());
	}

}
