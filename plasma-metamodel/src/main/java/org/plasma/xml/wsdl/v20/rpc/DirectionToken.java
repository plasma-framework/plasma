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
package org.plasma.xml.wsdl.v20.rpc;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for directionToken.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="directionToken">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="#in"/>
 *     &lt;enumeration value="#out"/>
 *     &lt;enumeration value="#inout"/>
 *     &lt;enumeration value="#return"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "directionToken", namespace = "http://www.w3.org/ns/wsdl/rpc")
@XmlEnum
public enum DirectionToken {

    @XmlEnumValue("#in")
    __IN("#in"),
    @XmlEnumValue("#out")
    __OUT("#out"),
    @XmlEnumValue("#inout")
    __INOUT("#inout"),
    @XmlEnumValue("#return")
    __RETURN("#return");
    private final String value;

    DirectionToken(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DirectionToken fromValue(String v) {
        for (DirectionToken c: DirectionToken.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
