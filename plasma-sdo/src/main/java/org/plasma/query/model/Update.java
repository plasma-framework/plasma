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



import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.plasma.query.visitor.QueryVisitor;
import org.plasma.query.visitor.Traversal;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Update", propOrder = {
    "entity",
	"expressions"
})
@XmlRootElement(name = "Update")
public class Update {

    @XmlElement(name = "Entity", required = true)
    protected Entity entity;
    @XmlElement(name = "Expression", required = true)
    protected List<Expression> expressions;
    
    public Update() {
        super();
    } 

    public Update(Expression expr) {
        this();
        getExpressions().add(expr);
    } 

    public Update(Expression[] exprs) {
        this();
        getExpressions().add(new Expression(exprs));
    } 

    /**
     * Gets the value of the entity property.
     * 
     * @return
     *     possible object is
     *     {@link Entity }
     *     
     */
    public Entity getEntity() {
        return entity;
    }

    /**
     * Sets the value of the entity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Entity }
     *     
     */
    public void setEntity(Entity value) {
        this.entity = value;
    }
    
    public void addExpression(Expression e) {
        getExpressions().add(e);
    }
    
    public List<Expression> getExpressions() {
        if (expressions == null) {
            expressions = new ArrayList<Expression>();
        }
        return this.expressions;
    }
    
    public int getExpressionCount() {
    	return getExpressions().size();
    }
    
    public void accept(QueryVisitor visitor)
    {
        visitor.start(this);
        if (visitor.getContext().getTraversal().ordinal() == Traversal.CONTINUE.ordinal())
        {
            for (int i = 0; i < this.getExpressions().size(); i++)
                ((Expression)this.getExpressions().get(i)).accept(visitor);  
        }
    	visitor.end(this);
    }
}
