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
    extends AbstractProperty implements org.plasma.query.Term
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
