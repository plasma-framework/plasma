package org.plasma.query.dsl;

import org.plasma.query.Wildcard;
import org.plasma.query.model.AbstractProperty;
import org.plasma.query.model.Path;
import org.plasma.query.model.WildcardProperty;

/**
 * A domain query node which is a wild card data end point within
 * a query graph.
 */
public class WildcardDataNode extends DomainEndpoint 
    implements Wildcard 
{
		
	public WildcardDataNode(PathNode source, String name) {
		super (source, name);  
		// Note: the source property is a data property here
		// This seems funky
		if (this.source != null) {
			Path path = createPath();
			if (!Wildcard.WILDCARD_CHAR.equals(name)) {
				if (path != null)
					this.property = new org.plasma.query.model.Property(name, path);
				else
					this.property = new org.plasma.query.model.Property(name);
			}
			else {
				if (path != null)
					this.property = new WildcardProperty(path);
				else
					this.property = new WildcardProperty();
			}
		}
		else {
			if (!Wildcard.WILDCARD_CHAR.equals(name)) 
		        this.property = new org.plasma.query.model.Property(name);
			else
				this.property = new WildcardProperty();
		}
	}
	
	AbstractProperty getModel() {
		return this.property;
	}	
	
}
