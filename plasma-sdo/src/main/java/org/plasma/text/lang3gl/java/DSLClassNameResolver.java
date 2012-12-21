package org.plasma.text.lang3gl.java;

import org.plasma.config.Namespace;
import org.plasma.config.PlasmaConfig;
import org.plasma.provisioning.Class;
import org.plasma.provisioning.ClassRef;
import org.plasma.text.lang3gl.ClassNameResolver;

public class DSLClassNameResolver  extends DefaultNameResolver
    implements ClassNameResolver {

	@Override
	public String getQualifiedName(Class clss) {		
		return getQualifiedName(clss.getUri(), clss.getName());
	}

	@Override
	public String getQualifiedName(ClassRef clssRef) {
		return getQualifiedName(clssRef.getUri(), clssRef.getName());
	}
	
	private String getQualifiedName(String uri, String name) {
		Namespace sdoNamespace = PlasmaConfig.getInstance().getSDONamespaceByURI(uri);
		String packageName = sdoNamespace.getProvisioning().getPackageName();
		String subpackage = PlasmaConfig.getInstance().getSDO().getGlobalProvisioning().getQueryDSL().getImplementation().getChildPackageName();		
		if (subpackage != null && subpackage.trim().length() > 0)
			packageName = packageName + "." + subpackage;
		String className = PlasmaConfig.getInstance().getQueryDSLImplementationClassName(uri, name);
		className = this.replaceReservedCharacters(className);
		String qualifiedName = packageName + "." + className; 				
		return qualifiedName;
	}

	@Override
	public String getName(Class clss) {
		String className = PlasmaConfig.getInstance().getQueryDSLImplementationClassName(clss.getUri(), clss.getName());
		className = this.replaceReservedCharacters(className);
		return className;
	}

}
