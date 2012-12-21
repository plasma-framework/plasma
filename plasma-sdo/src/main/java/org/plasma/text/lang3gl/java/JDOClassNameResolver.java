package org.plasma.text.lang3gl.java;

import org.plasma.config.DataAccessProviderName;
import org.plasma.config.Namespace;
import org.plasma.config.NamespaceProvisioning;
import org.plasma.config.PlasmaConfig;
import org.plasma.provisioning.Class;
import org.plasma.provisioning.ClassRef;
import org.plasma.text.lang3gl.ClassNameResolver;

public class JDOClassNameResolver extends DefaultNameResolver implements ClassNameResolver {

	@Override
	public String getName(Class clss) {		
		return clss.getName(); // FIXME:  configure this
	}

	@Override
	public String getQualifiedName(Class clss) {		
		NamespaceProvisioning provisioning = PlasmaConfig.getInstance().getProvisioningByNamespaceURI(
				DataAccessProviderName.JDO, clss.getUri());
		String packageName = provisioning.getPackageName();
		String name = this.replaceReservedCharacters(clss.getName());
		String qualifiedName = packageName + "." + name;  	 	
		return qualifiedName;
	}

	@Override
	public String getQualifiedName(ClassRef clssRef) {
		NamespaceProvisioning provisioning = PlasmaConfig.getInstance().getProvisioningByNamespaceURI(
				DataAccessProviderName.JDO, clssRef.getUri());
		String packageName = provisioning.getPackageName();
		String name = this.replaceReservedCharacters(clssRef.getName());
		String qualifiedName = packageName + "." + name; // FIXME:  configure this				
		return qualifiedName;
	}

}
