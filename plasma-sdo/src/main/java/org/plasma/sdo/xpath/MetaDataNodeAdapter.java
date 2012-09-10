package org.plasma.sdo.xpath;

import commonj.sdo.Property;
import commonj.sdo.Type;

/**
 * Wraps a result or "endpoint" of a Jaxen XPATH parse, supplying 
 * source traversal information, e.g. the Type and Property which served as the
 * source or "parent" of a particular traversal path.  
 */
public abstract class MetaDataNodeAdapter {
	protected Type source;
	protected Property sourceProperty;
    @SuppressWarnings("unused")
	private MetaDataNodeAdapter() {}
	public MetaDataNodeAdapter(Type source) {
		super();
		this.source = source; 
		if (this.source == null)
			throw new IllegalArgumentException("expected non-null argument 'source'");
	}
	public MetaDataNodeAdapter(Type source, Property sourceProperty) {
		super();
		this.source = source; 
		this.sourceProperty = sourceProperty; 
		if (this.source == null)
			throw new IllegalArgumentException("expected non-null argument 'source'");
		if (this.sourceProperty == null)
			throw new IllegalArgumentException("expected non-null argument 'sourceProperty'");
	}
	
	
	/**
	 * Returns the Type traversal source
	 * @return the source data object
	 */
	public Type getSource() {
		return this.source;
	}
	
	/**
	 * Returns the property traversal source
	 * @return the source property
	 */
	public Property getSourceProperty() {
		return this.sourceProperty;
	}
}
