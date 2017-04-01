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
package org.plasma.query.dsl;

import org.plasma.query.Wildcard;
import org.plasma.query.model.AbstractProperty;
import org.plasma.query.model.Path;
import org.plasma.query.model.WildcardProperty;
import org.plasma.query.model.WildcardPropertyTypeValues;

/**
 * A domain query node which is a wild card end point within
 * a query graph.
 */
class WildcardNode extends DomainEndpoint 
    implements Wildcard 
{
	
	public WildcardNode(PathNode source, String name, WildcardPropertyTypeValues wildcardType) {
		super (source, name);  
		if (this.source != null) {
			Path path = createPath();
			if (!Wildcard.WILDCARD_CHAR.equals(name)) { //FIXME: why is this ever the case
				if (path != null)
					this.property = new org.plasma.query.model.Property(name, path);
				else
					this.property = new org.plasma.query.model.Property(name);
			}
			else {
				WildcardProperty wcProp = null;
				if (path != null)
					wcProp = new WildcardProperty(path);
				else
					wcProp = new WildcardProperty();
				wcProp.setType(wildcardType);
				this.property = wcProp;
			}
		}
		else {
			if (!Wildcard.WILDCARD_CHAR.equals(name)) { //FIXME: why is this ever the case 
		        this.property = new org.plasma.query.model.Property(name);
			}
		    else {
				WildcardProperty wcProp = new WildcardProperty();
				wcProp.setType(wildcardType);
				this.property = wcProp;
		    }
		}
	}
	
	AbstractProperty getModel() {
		return this.property;
	}	
	
}
