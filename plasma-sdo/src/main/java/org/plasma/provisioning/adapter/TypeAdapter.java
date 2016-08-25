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
package org.plasma.provisioning.adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.plasma.metamodel.Class;
import org.plasma.metamodel.Documentation;
import org.plasma.metamodel.Enumeration;
import org.plasma.metamodel.Property;
import org.plasma.metamodel.Type;

public class TypeAdapter {
	private static List<Documentation> EMPTY_DOC_LIST = new ArrayList<Documentation>();
    private Type type;
    private Map<String, Property> properties;
    private Map<String, Property> declaredProperties;
    private Map<String, Property> aliasedProperties;
    private String key;
    
    @SuppressWarnings("unused")
	private TypeAdapter() {}
    @SuppressWarnings("unchecked")
	public TypeAdapter(Type type) {
    	this.type = type;
    	if (type == null)
    		throw new IllegalArgumentException("expected argument 'type'");
    	if (this.type instanceof Class) {
    		Class clss = (Class)this.type;
    		this.key = clss.getUri() + "#" + clss.getName();
    	}
    	else if (this.type instanceof Enumeration) {
    		Enumeration enm = (Enumeration)this.type;
    		this.key = enm.getUri() + "#" + enm.getName();
    	}
		this.properties = new HashMap<String, Property>();
		this.declaredProperties = new HashMap<String, Property>();
		this.aliasedProperties = new HashMap<String, Property>();
    }
    
	public Type getType() {
		return type;
	}
	
	public boolean isClass() {
    	return this.type instanceof Class;
	}
	
	public boolean isEnumeration() {
    	return this.type instanceof Enumeration;
	}
    
	public String getKey() {
		return key;
	}
	
	public String getName() {
    	if (this.type instanceof Class) {
    		return ((Class)this.type).getName();
    	}
    	else {
    		return ((Enumeration)this.type).getName();
    	}
	}
	
	public String getLocalName() {
    	if (this.type instanceof Class) {
    		Class clss = ((Class)this.type);
    		if (clss.getAlias() != null && clss.getAlias().getLocalName() != null)
    			return clss.getAlias().getLocalName();
    		else
    			return clss.getName();
    	}
    	else {
    		Enumeration enm = ((Enumeration)this.type);
    		if (enm.getAlias() != null && enm.getAlias().getLocalName() != null)
    			return enm.getAlias().getLocalName();
    		else
    			return enm.getName();
    	}
	}
	
	public String getPhysicalName() {
    	if (this.type instanceof Class) {
    		Class clss = ((Class)this.type);
    		if (clss.getAlias() != null && clss.getAlias().getPhysicalName() != null)
    			return clss.getAlias().getPhysicalName();
    		else
    			return clss.getName();
    	}
    	else {
    		Enumeration enm = ((Enumeration)this.type);
    		if (enm.getAlias() != null && enm.getAlias().getPhysicalName() != null)
    			return enm.getAlias().getPhysicalName();
    		else
    			return enm.getName();
    	}
	}
	
	public String getUri() {
    	if (this.type instanceof Class) {
    		return ((Class)this.type).getUri();
    	}
    	else {
    		return ((Enumeration)this.type).getUri();
    	}
	}
	
	public List<Documentation> getDocumentation() {
		List<Documentation> list = null;
    	if (this.type instanceof Class) {
    		list = ((Class)this.type).getDocumentation();
    	}
    	else {
    		list = ((Enumeration)this.type).getDocumentation();
    	}
    	if (list != null)
    	    return list;
    	else
    		return EMPTY_DOC_LIST; 
	}
	
	public String getAllDocumentation() {
		List<Documentation> list = null;
    	if (this.type instanceof Class) {
    		list = ((Class)this.type).getDocumentation();
    	}
    	else {
    		list = ((Enumeration)this.type).getDocumentation();
    	}
    	StringBuilder buf = new StringBuilder();
    	if (list != null)
    		for (Documentation doc : list)
    			buf.append(doc.getBody().getValue());
    	return buf.toString();
	}
	
	public void putProperty(String key, Property property) {
		this.properties.put(key, property);
	}
	
	public Property getProperty(String key) {
		return this.properties.get(key);
	}
	
	public Collection<Property> getProperties() {
		return this.properties.values();
	}
	
	public Property[] getPropertiesArray() {
		Property[] result = new Property[this.properties.size()];
		this.properties.values().toArray(result);
		Arrays.sort(result, new PropertyComparator());
		return result;
	}
	
	public void putDeclaredProperty(String key, Property property) {
		this.declaredProperties.put(key, property);
	}
	
	public Property getDeclaredProperty(String key) {
		return this.declaredProperties.get(key);
	}
	
	public Collection<Property> getDeclaredProperties() {
		return this.declaredProperties.values();
	}
	
	public Property[] getDeclaredPropertiesArray() {
		Property[] result = new Property[this.declaredProperties.size()];
		this.declaredProperties.values().toArray(result);
		Arrays.sort(result, new PropertyComparator());
		return result;
	}
	
	public void putAliasedProperty(String key, Property property) {
		this.aliasedProperties.put(key, property);
	}
	
	public Property getAliasedProperty(String key) {
		return this.aliasedProperties.get(key);
	}
	
	public Collection<Property> getAliasedProperties() {
		return this.aliasedProperties.values();
	}
	
	public Property[] getAliasedPropertiesArray() {
		Property[] result = new Property[this.aliasedProperties.size()];
		this.aliasedProperties.values().toArray(result);
		Arrays.sort(result, new PropertyComparator());
		return result;
	}
	
	class PropertyComparator implements Comparator<Property> {
		public int compare(Property p1, Property p2) {
			if (p1.getSort() != null && p2.getSort() != null)
				return p1.getSort().getKey().compareTo(p2.getSort().getKey());
			else
			    return p1.getName().compareTo(p2.getName());
		}
	}
}
