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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

import org.plasma.query.QueryException;
import org.plasma.query.visitor.QueryVisitor;
import org.plasma.sdo.helper.DataConverter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Literal", propOrder = {
    "value"
})
@XmlRootElement(name = "Literal")
public class Literal implements org.plasma.query.Term {

    @XmlValue
    protected String value;


    public Literal() {
        super();
        setValue("");
    } 
    
    public Literal(String content) {
        this();
        setValue(content);
    } 

    public Literal(BigDecimal content) {
        this();
        setValue(String.valueOf(content));
    } 

    public Literal(Date content) {
        this();
        setValue(DataConverter.INSTANCE.getDateTimeFormat().format(content));
    } 
    
    public Literal(Boolean content) {
        this();
        setValue(String.valueOf(content));
    } 

    public Literal(Short content) {
        this();
        setValue(String.valueOf(content));
    } 
    
    public Literal(Integer content) {
        this();
        setValue(String.valueOf(content));
    } 
    
    public Literal(Long content) {
        this();
        setValue(String.valueOf(content));
    }
    
    public Literal(BigInteger content) {
        this();
        setValue(String.valueOf(content));
    } 

    public Literal(Float content) {
        this();
        setValue(String.valueOf(content));
    } 

    public Literal(Double content) {
        this();
        setValue(String.valueOf(content));
    } 
    
    public static Literal valueOf(String content)
    {
    	return new Literal(content);
    }
    
    public static Literal valueOf(Object content)
    {
    	if (content == null)
    		throw new IllegalArgumentException("expected non-null argument");
    	
    	if (content instanceof String)
    	    return new Literal((String)content);
    	else if (content instanceof Date)
    	    return new Literal((Date)content);
    	else if (content instanceof Boolean)
    	    return new Literal((Boolean)content);
    	else if (content instanceof Short)
    	    return new Literal((Short)content);
    	else if (content instanceof Integer)
    	    return new Literal((Integer)content);
    	else if (content instanceof Long)
    	    return new Literal((Long)content);
    	else if (content instanceof BigInteger)
    	    return new Literal((BigInteger)content);
    	else if (content instanceof Float)
    	    return new Literal((Float)content);
    	else if (content instanceof Double)
    	    return new Literal((Double)content);
    	else if (content instanceof BigDecimal)
    	    return new Literal((BigDecimal)content);
    	else if (content instanceof Literal)
    	    return (Literal)content;
    	else
    		throw new QueryException("unsupported " + content.getClass().getName());
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

    public void accept(QueryVisitor visitor)
    {
        visitor.start(this);
    	visitor.end(this);
    }

}
