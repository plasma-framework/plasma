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
 * <p>Java class for GroupOperatorValues.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="GroupOperatorValues">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="LP1"/>
 *     &lt;enumeration value="LP2"/>
 *     &lt;enumeration value="LP3"/>
 *     &lt;enumeration value="RP1"/>
 *     &lt;enumeration value="RP2"/>
 *     &lt;enumeration value="RP3"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "GroupOperatorValues")
@XmlEnum
public enum GroupOperatorName {

    @XmlEnumValue("LP1")
    LP_1("LP1"),
    @XmlEnumValue("LP2")
    LP_2("LP2"),
    @XmlEnumValue("LP3")
    LP_3("LP3"),
    @XmlEnumValue("RP1")
    RP_1("RP1"),
    @XmlEnumValue("RP2")
    RP_2("RP2"),
    @XmlEnumValue("RP3")
    RP_3("RP3");
    private final String value;

    GroupOperatorName(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static GroupOperatorName fromValue(String v) {
        for (GroupOperatorName c: GroupOperatorName.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
