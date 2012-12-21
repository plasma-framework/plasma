package org.plasma.text.lang3gl.java;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.config.Namespace;
import org.plasma.config.PlasmaConfig;
import org.plasma.provisioning.Class;
import org.plasma.provisioning.ClassRef;
import org.plasma.text.lang3gl.ClassNameResolver;

public class SDOClassNameResolver extends DefaultNameResolver
    implements ClassNameResolver 
{
    private static Log log =LogFactory.getLog(
    		SDOClassNameResolver.class); 

	@Override
	public String getName(Class clss) {		
		String name = PlasmaConfig.getInstance().getSDOImplementationClassName(clss.getUri(), clss.getName());
		return replaceReservedCharacters(name);
	}
	
	@Override
	public String getQualifiedName(Class clss) {		
		Namespace sdoNamespace = PlasmaConfig.getInstance().getSDONamespaceByURI(clss.getUri());
		String packageName = sdoNamespace.getProvisioning().getPackageName();
		String name = PlasmaConfig.getInstance().getSDOImplementationClassName(clss.getUri(), clss.getName());
		String qualifiedName = packageName + "." 
		    + replaceReservedCharacters(name); 				
		
		return qualifiedName;
	}

	@Override
	public String getQualifiedName(ClassRef clssRef) {
		Namespace sdoNamespace = PlasmaConfig.getInstance().getSDONamespaceByURI(clssRef.getUri());
		String packageName = sdoNamespace.getProvisioning().getPackageName();
		String name = PlasmaConfig.getInstance().getSDOImplementationClassName(clssRef.getUri(), clssRef.getName());
		String qualifiedName = packageName + "." 
		    + replaceReservedCharacters(name); 				
		return qualifiedName;
	}


}
