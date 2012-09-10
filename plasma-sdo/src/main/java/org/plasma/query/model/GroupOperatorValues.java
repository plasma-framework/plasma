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
public enum GroupOperatorValues {

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

    GroupOperatorValues(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static GroupOperatorValues fromValue(String v) {
        for (GroupOperatorValues c: GroupOperatorValues.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
