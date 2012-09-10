package org.plasma.sdo.access.provider.jdbc;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.plasma.sdo.PlasmaType;

/**
 * Simple collection managing SQL table aliases
 */
public class AliasMap {

	private Map<PlasmaType, String> map = null;
	
	@SuppressWarnings("unused")
	private AliasMap() {}
	public AliasMap(PlasmaType root) {
		this.map = new HashMap<PlasmaType, String>();
		this.map.put(root, "t0");
	}
	
	/**
	 * Returns the table alias for the given type
	 * @param type the type
	 * @return the alias
	 */
	public String getAlias(PlasmaType type) {
		return this.map.get(type);
	}
	
	/**
	 * Return the types which are keys for this map. 
	 * @return the type keys
	 */
	public Iterator<PlasmaType> getTypes() {
		return this.map.keySet().iterator();
	}
	
	/**
	 * Returs the alias names for this map
	 * @return the alias names
	 */
	public Collection<String> getAliases() {
		return this.map.values();
	}
	
	/**
	 * Adds and returns the table alias for the given type,
	 * or existing alias of already mapped
	 * @param type the type
	 * @return the new alias or existing alias of already mapped
	 */
	public String addAlias(PlasmaType type) {
		String existing = this.map.get(type);
		if (existing == null) {
			String alias = "t" + String.valueOf(this.map.size());
		    this.map.put(type, alias);
		    return alias;
		}
		return existing;
	}
	
}
