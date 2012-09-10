package org.plasma.query.model;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.plasma.query.visitor.QueryVisitor;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WildcardProperty")
@XmlRootElement(name = "WildcardProperty")
public class WildcardProperty
    extends AbstractProperty
{
    @XmlAttribute(required = true)
    protected WildcardPropertyTypeValues type;

    public WildcardProperty() {
        super();
        type = WildcardPropertyTypeValues.DATA;
    } //-- org.plasma.mda.query.WildcardProperty()

    public WildcardProperty(Path path) {
        this();
        this.setPath(path);
    } //-- org.plasma.mda.query.WildcardProperty(Path path)


    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link WildcardPropertyTypeValues }
     *     
     */
    public WildcardPropertyTypeValues getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link WildcardPropertyTypeValues }
     *     
     */
    public void setType(WildcardPropertyTypeValues value) {
        this.type = value;
    }

    public void accept(QueryVisitor visitor)
    {
        visitor.start(this);
    	visitor.end(this);
    }

}
