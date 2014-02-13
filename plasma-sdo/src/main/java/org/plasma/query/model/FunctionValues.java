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
package org.plasma.query.model;


import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FunctionValues.
 * 
 * 
 */
@XmlType(name = "FunctionValues")
@XmlEnum
public enum FunctionValues {

    @XmlEnumValue("min")
    MIN("min"),
    @XmlEnumValue("max")
    MAX("max"),
    @XmlEnumValue("avg")
    AVG("avg"),
    @XmlEnumValue("sum")
    SUM("sum"),
    @XmlEnumValue("abs")
    ABS("abs"),
    @XmlEnumValue("ceiling")
    CEILING("ceiling"),
    @XmlEnumValue("floor")
    FLOOR("floor"),
    @XmlEnumValue("round")
    ROUND("round"),
    @XmlEnumValue("substringBefore")
    SUBSTRING_BEFORE("substringBefore"),
    @XmlEnumValue("substringAfter")
    SUBSTRING_AFTER("substringAfter"),
    @XmlEnumValue("normalizeSpace")
    NORMALIZE_SPACE("normalizeSpace"),
    @XmlEnumValue("upperCase")
    UPPER_CASE("upperCase"),
    @XmlEnumValue("lowerCase")
    LOWER_CASE("lowerCase"),
    @XmlEnumValue("yearFromDate")
    YEAR_FROM_DATE("yearFromDate"),
    @XmlEnumValue("monthFromDate")
    MONTH_FROM_DATE("monthFromDate"),
    @XmlEnumValue("dayFromDate")
    DAY_FROM_DATE("dayFromDate");
    private final String value;

    FunctionValues(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FunctionValues fromValue(String v) {
        for (FunctionValues c: FunctionValues.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
