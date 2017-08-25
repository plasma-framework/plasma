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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PredicateOperatorName.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PredicateOperatorName"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="LIKE"/&gt;
 *     &lt;enumeration value="IN"/&gt;
 *     &lt;enumeration value="NOT_IN"/&gt;
 *     &lt;enumeration value="EXISTS"/&gt;
 *     &lt;enumeration value="NOT_EXISTS"/&gt;
 *     &lt;enumeration value="BETWEEN"/&gt;
 *     &lt;enumeration value="SIMILAR"/&gt;
 *     &lt;enumeration value="NULL"/&gt;
 *     &lt;enumeration value="UNIQUE"/&gt;
 *     &lt;enumeration value="MATCH"/&gt;
 *     &lt;enumeration value="DISTINCT"/&gt;
 *     &lt;enumeration value="CONTAINS"/&gt;
 *     &lt;enumeration value="APP_OTHER_NAME"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "PredicateOperatorName")
@XmlEnum
public enum PredicateOperatorName {

    LIKE,
    IN,
    NOT_IN,
    EXISTS,
    NOT_EXISTS,
    BETWEEN,
    SIMILAR,
    NULL,
    UNIQUE,
    MATCH,
    DISTINCT,
    CONTAINS,
    APP_OTHER_NAME;

    public String value() {
        return name();
    }

    public static PredicateOperatorName fromValue(String v) {
        return valueOf(v);
    }

}
