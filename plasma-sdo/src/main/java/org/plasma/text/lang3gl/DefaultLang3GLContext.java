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
package org.plasma.text.lang3gl;

import org.plasma.metamodel.Class;
import org.plasma.metamodel.ClassRef;
import org.plasma.metamodel.Package;
import org.plasma.provisioning.adapter.ProvisioningModel;
import org.plasma.provisioning.adapter.TypeAdapter;
import org.plasma.text.TextProvisioningException;

public class DefaultLang3GLContext implements Lang3GLContext {

	private ProvisioningModel ProvisioningModel;
	
	@SuppressWarnings("unused")
	private DefaultLang3GLContext() {}
	
	public DefaultLang3GLContext(ProvisioningModel ProvisioningModel) {
		this.ProvisioningModel = ProvisioningModel;
	}

	public Class findClass(ClassRef cref) {
		String key = cref.getUri() + "#" + cref.getName();
		return findClass(key);
	}
	
	public Class findClass(String qualifiedName) {
		TypeAdapter type = this.ProvisioningModel.findType(qualifiedName);
		if (!type.isClass())
			throw new TextProvisioningException("expected instanceof Class not, " + type.getType().getClass());
		
		return (Class)type.getType();
	}
	
	public Package findPackage(ClassRef cref) {
		String key = cref.getUri() + "#" + cref.getName();
		return this.findPackage(key);
	}
	
	public Package findPackage(String qualifiedName) {
		TypeAdapter type = this.ProvisioningModel.findType(qualifiedName);
		return this.ProvisioningModel.getPackage(type);
	}

	public boolean usePrimitives() {
		return true;
	}
	
	public String getIndentationToken() {
		return "\t";
	}


}
