package org.plasma.query;


/**
 * A clause describing the query extent or root type 
 */
public interface From {

	/**
	 * Returns the URI 
	 * @return the URI
	 */
    public String getUri();
    
	/**
	 * Returns the type (logical) name 
	 * @return the type (logical) name
	 */
    public String getName();
}