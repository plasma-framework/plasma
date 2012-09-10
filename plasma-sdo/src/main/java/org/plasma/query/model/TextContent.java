package org.plasma.query.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TextContent", propOrder = {
    "value"
})
@XmlRootElement(name = "TextContent")
public class TextContent {

    @XmlValue
    protected String value;
    @XmlAttribute(required = true)
    protected QueryLanguageValues language;

    public TextContent() {
        super();
        setValue("");
        setLanguage(QueryLanguageValues.JDOQL);
    } 
    
    public TextContent(QueryLanguageValues language) {
        super();
        setValue("");
        setLanguage(language);
    } 

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the language property.
     * 
     * @return
     *     possible object is
     *     {@link QueryLanguageValues }
     *     
     */
    public QueryLanguageValues getLanguage() {
        return language;
    }

    /**
     * Sets the value of the language property.
     * 
     * @param value
     *     allowed object is
     *     {@link QueryLanguageValues }
     *     
     */
    public void setLanguage(QueryLanguageValues value) {
        this.language = value;
    }

}
