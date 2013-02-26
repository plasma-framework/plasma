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
package org.plasma.provisioning;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class NameUtils {
	/**
	 * Tokenizes the given URI as an array of names suitable
	 * to be used as a UML package hierarchy. 
	 * @param uri
	 * @return the name array
	 */
	public static String[] toPackageTokens(String uri)
	{
		List<String> list = new ArrayList<String>();
		
		try {
			java.net.URL url = new java.net.URL(uri);
			String[] authority = url.getAuthority().split("\\.");
			for (int i = authority.length -1; i >= 0; i--)
				if (authority[i] != null && authority[i].trim().length() > 0)
				    list.add(authority[i]);
			if (url.getPath() != null) {
			    String[] path = url.getPath().split("\\/");
			    for (int i = 0; i < path.length; i++)
					if (path[i] != null && path[i].trim().length() > 0)
				        list.add(path[i]);
			}
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException(e); 
		}
		
		String[] result = new String[list.size()];
		list.toArray(result);		
		
		return result;		
	}
	
	/**
	 * Creates a capitalized, underscore delimited name
	 * from the given camel case name. 
	 * @param name the name
	 * @return the result name
	 */
	public static String toConstantName(String name) {
    	StringBuilder buf = new StringBuilder();
    	char[] array = name.toCharArray();
        for (int i = 0; i < array.length; i++) {
        	if (i > 0) {
        	   if (Character.isLetter(array[i]) && Character.isUpperCase(array[i]))
        		   buf.append("_");
        	}
        	if (Character.isLetterOrDigit(array[i])) {
        	    buf.append(Character.toUpperCase(array[i]));        		
        	}
        	else
        	    buf.append("_");
        }
        return buf.toString();
    }

	/**
	 * Creates a capitalized, underscore delimited name
	 * from the given camel case name and removes consonant
	 * characters where the name exceeds the given max.  
	 * @param name the name
	 * @return
	 */
	public static String toAbbreviatedName(String name) {
	    return toAbbreviatedName(name, 4);
	}
	
	/**
	 * Creates a capitalized, underscore delimited name
	 * from the given camel case name and removes consonant
	 * characters where the name exceeds the given max.  
	 * @param name the name
	 * @param max the max size to allow before abbreviation begins.
	 * @return
	 */
	public static String toAbbreviatedName(String name, int max) {
		
		String constantName = toConstantName(name);
		if (constantName.length() <= max)
			return constantName;
    	StringBuilder buf = new StringBuilder();
    	char[] array = constantName.toCharArray();
        for (int i = 0; i < array.length; i++) {
        	if (i == 0) {
        	    buf.append(array[i]);
        		continue;
        	}
        	if (array[i] == 'A' || array[i] == 'E' || array[i] == 'I' || array[i] == 'O' || array[i] == 'U') {
        		continue;        		
        	}
        	else
        	    buf.append(array[i]);
        }
        return buf.toString();
    }

	public static String firstToUpperCase(String name) {
		if (Character.isLowerCase(name.charAt(0))) {
		    return name.substring(0, 1).toUpperCase() + name.substring(1);	
		}
			
		return name;
    }
	
	public static String firstToLowerCase(String name) {
		if (Character.isUpperCase(name.charAt(0))) {
		    return name.substring(0, 1).toLowerCase() + name.substring(1);	
		}
			
		return name;
    }
}
