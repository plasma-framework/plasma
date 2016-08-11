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
package org.plasma.config.adapter;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.plasma.config.ConfigurationException;
import org.plasma.config.Namespace;
import org.plasma.config.NamespaceProvisioning;
import org.plasma.config.Property;
import org.plasma.config.TypeBinding;

public class NamespaceAdapter {

	private Namespace namespace;
	private Map<String, TypeBindingAdapter> typeBindings;
	private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	private boolean systemArtifact = false;
	
	@SuppressWarnings("unused")
	private NamespaceAdapter() {}
	public NamespaceAdapter(Namespace namespace) {
		super();
		this.namespace = namespace;
	}
	public Namespace getNamespace() {
		return namespace;
	}
	
	public List<Property> getProperties() {
		return Collections.unmodifiableList(namespace.getProperties());
	}
	public NamespaceProvisioning getProvisioning() {
		return namespace.getProvisioning();
	}
	public List<TypeBinding> getTypeBindings() {
		return Collections.unmodifiableList(namespace.getTypeBindings());
	}
	public String getUri() {
		return namespace.getUri();
	}
	public String getArtifact() {
		return namespace.getArtifact();
	}
	public boolean isSystemArtifact() {
		return systemArtifact;
	}
	public void setSystemArtifact(boolean systemArtifact) {
		this.systemArtifact = systemArtifact;
	}
	public void addTypeBinding(TypeBinding typeBinding) {
		 
		if (this.typeBindings != null && this.typeBindings.get(typeBinding.getType()) != null)
			throw new ConfigurationException("duplicate type binding - "
				+ "a type binding for type '" + typeBinding.getType() + "' already exists "
				+ "within the configucation for namespace with URI, "
				+ namespace.getUri());
		if (this.typeBindings == null)
			this.typeBindings = new HashMap<String, TypeBindingAdapter>();
		this.lock.writeLock().lock();
		try {
		    this.typeBindings.put(typeBinding.getType(), new TypeBindingAdapter(this, typeBinding));
        } finally {
        	this.lock.writeLock().unlock();
        }
	}

	/**
	 * Maps an existing type binding to the binding logical name. 
	 * @param typeBinding
	 */
	public void remapTypeBinding(TypeBindingAdapter typeBinding) {
		if (typeBinding.getLogicalName() == null)
			throw new ConfigurationException("cloud not remap type binding "
					+ "for type '" + typeBinding.getType() + "' with no logical name");
		if (this.typeBindings != null && this.typeBindings.get(typeBinding.getLogicalName()) != null)
			throw new ConfigurationException("duplicate type binding - "
				+ "a type binding for type '" + typeBinding.getLogicalName() + "' already exists "
				+ "within the configucation for namespace with URI, "
				+ namespace.getUri());
		if (this.typeBindings == null)
			this.typeBindings = new HashMap<String, TypeBindingAdapter>();
		TypeBindingAdapter existing = this.typeBindings.remove(typeBinding.getType());		
		if (existing == null)
			throw new ConfigurationException("missing type binding - "
					+ "no type binding for type '" + typeBinding.getType() + "' exists "
					+ "within the configucation for namespace with URI, "
					+ namespace.getUri());
		this.lock.writeLock().lock();
		try {
		    this.typeBindings.put(typeBinding.getLogicalName(), existing);
        } finally {
        	this.lock.writeLock().unlock();
        }
	}
	
	public TypeBindingAdapter findTypeBinding(String typeName) {
		if (this.typeBindings != null) {
			TypeBindingAdapter adapter = this.typeBindings.get(typeName);
			if (adapter != null)
				return adapter;
			else
				return null;
		}
		else
			return null;
	}
}
