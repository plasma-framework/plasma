package org.plasma.text.lang3gl.java;

import org.plasma.config.Namespace;
import org.plasma.config.PlasmaConfig;
import org.plasma.provisioning.Class;
import org.plasma.provisioning.ClassRef;
import org.plasma.text.lang3gl.ClassNameResolver;

public class SDOInterfaceNameResolver implements ClassNameResolver {

	@Override
	public String getName(Class clss) {		
		return clss.getName();
	}
	
	@Override
	public String getQualifiedName(Class clss) {		
		Namespace sdoNamespace = PlasmaConfig.getInstance().getSDONamespaceByURI(clss.getUri());
		String packageName = sdoNamespace.getProvisioning().getPackageName();
		String qualifiedName = packageName + "." + clss.getName(); 				
		return qualifiedName;
	}

	@Override
	public String getQualifiedName(ClassRef clssRef) {
		Namespace sdoNamespace = PlasmaConfig.getInstance().getSDONamespaceByURI(clssRef.getUri());
		String packageName = sdoNamespace.getProvisioning().getPackageName();
		String qualifiedName = packageName + "." + clssRef.getName(); 				
		return qualifiedName;
	}

}
