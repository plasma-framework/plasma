package org.plasma.provisioning.xsd;

import org.plasma.xml.schema.AbstractSimpleType;
import org.plasma.xml.schema.Restriction;

public class SimpleTypeUtils {
	public static boolean isEnumeration(AbstractSimpleType simpleType) {
    	Restriction restriction = simpleType.getRestriction();
    	if (restriction != null) {
    	    if (restriction.getMinExclusivesAndMinInclusivesAndMaxExclusives().size() > 0)
    	        for (Object obj : restriction.getMinExclusivesAndMinInclusivesAndMaxExclusives()) 
    		        if (obj instanceof org.plasma.xml.schema.Enumeration)
                        return true;
    	    
    	}
    	return false;
    }

}
