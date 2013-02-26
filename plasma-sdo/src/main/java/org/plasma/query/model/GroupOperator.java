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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

import org.plasma.query.visitor.QueryVisitor;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GroupOperator", propOrder = {
    "value"
})
@XmlRootElement(name = "GroupOperator")
public class GroupOperator implements org.plasma.query.Operator {

    @XmlValue
    protected GroupOperatorValues value;


      //----------------/
     //- Constructors -/
    //----------------/

    public GroupOperator() {
        super();
    } 

    public GroupOperator(String oper) {
        super();
        value = GroupOperatorValues.valueOf(oper);
    } 

    public GroupOperator(GroupOperatorValues oper) {
        super();
        value = oper;
    } 

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link GroupOperatorValues }
     *     
     */
    public GroupOperatorValues getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link GroupOperatorValues }
     *     
     */
    public void setValue(GroupOperatorValues value) {
        this.value = value;
    }

    public void accept(QueryVisitor visitor)
    {
        visitor.start(this);
    	visitor.end(this);
    }

}
