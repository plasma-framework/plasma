package org.plasma.query.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArithmeticOperatorValues.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ArithmeticOperatorValues">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="minus"/>
 *     &lt;enumeration value="plus"/>
 *     &lt;enumeration value="div"/>
 *     &lt;enumeration value="mod"/>
 *     &lt;enumeration value="mult"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ArithmeticOperatorValues")
@XmlEnum
public enum ArithmeticOperatorValues {

    @XmlEnumValue("minus")
    MINUS("minus"),
    @XmlEnumValue("plus")
    PLUS("plus"),
    @XmlEnumValue("div")
    DIV("div"),
    @XmlEnumValue("mod")
    MOD("mod"),
    @XmlEnumValue("mult")
    MULT("mult");
    private final String value;

    ArithmeticOperatorValues(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ArithmeticOperatorValues fromValue(String v) {
        for (ArithmeticOperatorValues c: ArithmeticOperatorValues.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
