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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.plasma.query.visitor.QueryVisitor;


/**
 * <p>Java class for AbstractProperty complex type.
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbstractProperty", propOrder = {
    "path"
})
public abstract class AbstractProperty implements Comparable<AbstractProperty> {

    @XmlElement(name = "Path", namespace = "")
    protected Path path;

    /**
     * A qualified name used for identity
     */
    protected transient String qualifiedName;
    public abstract String getQualifiedName();
    
    /**
     * A unique id, such as a repository UUID as string or
     * xmi-id from UML source. 
     */
    protected transient String uniqueId;
    public String getUniqueId() {
    	return this.uniqueId;
    }
    public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	/**
     * Gets the value of the path property.
     * 
     * @return
     *     possible object is
     *     {@link Path }
     *     
     */
    public Path getPath() {
        return path;
    }

    /**
     * Sets the value of the path property.
     * 
     * @param value
     *     allowed object is
     *     {@link Path }
     *     
     */
    public void setPath(Path value) {
        this.path = value;
    }
    
    public void accept(QueryVisitor visitor)
    {
        visitor.start(this);
	    visitor.end(this);
    }
}
