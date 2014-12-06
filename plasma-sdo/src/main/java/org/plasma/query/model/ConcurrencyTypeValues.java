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
 * <p>Java class for ConcurrencyTypeValues.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ConcurrencyTypeValues">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="none"/>
 *     &lt;enumeration value="threadPool"/>
 *     &lt;enumeration value="forkJoin"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ConcurrencyTypeValues")
@XmlEnum
public enum ConcurrencyTypeValues {

    @XmlEnumValue("none")
    NONE("none"),
    @XmlEnumValue("threadPool")
    THREAD_POOL("threadPool"),
    @XmlEnumValue("forkJoin")
    FORK_JOIN("forkJoin");
    private final String value;

    ConcurrencyTypeValues(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ConcurrencyTypeValues fromValue(String v) {
        for (ConcurrencyTypeValues c: ConcurrencyTypeValues.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
