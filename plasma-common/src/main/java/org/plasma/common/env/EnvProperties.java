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
package org.plasma.common.env;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;


/**
 * Singleton class for critical deployment property management and
 * error handling. 
 */
public class EnvProperties
{
    private static EnvProperties instance;
    private Properties props = new Properties();
    private EnvName env;
    private String fileName = "";

    private EnvProperties()
    {      	
        fileName = System.getProperty(EnvConstants.PROPERTY_NAME_ENV_PROPERTIES,
        		EnvConstants.PROPERTY_NAME_DEFAULT_ENV_PROPERTIES);
         
        try {
        	FileInputStream fis = new FileInputStream(fileName);        	
        	props.load(fis);            
        }
        catch (IOException  e) {
            System.out.println("\n\nNo properties file found. Using only system properties.\n\n");
        }
        // add/override with system properties
    	props.putAll(System.getProperties());

    	//trim values...in case spaces were inadvertently added in properties file.
    	Iterator keys = props.keySet().iterator();
		while(keys.hasNext()) {
			String key = (String)keys.next();
			props.setProperty(key, props.getProperty(key).trim());
		}   	

    	try {
        	String envName = props.getProperty(EnvConstants.PROPERTY_NAME_ENV_NAME);
        	if (envName != null) {
        		envName = envName.toUpperCase();
        		env = EnvName.valueOf(envName);
        	}
        	else
        		env = EnvName.DEV;
        }
        catch (IllegalArgumentException e) {
            env = EnvName.DEV;
        }
    }

    public String dumpProperties(String separator)
    {
        StringBuffer buf = new StringBuffer();

        Iterator itr = props.keySet().iterator();
        while (itr.hasNext()) {
            String key = (String) itr.next();
            String value = props.getProperty(key);
            buf.append(separator + key + "='" + value + "'");
        }

        return buf.toString();
    }

    public static EnvProperties instance()
    {
        if (instance == null)
            initInstance(); // double-checked locking pattern 
        return instance;     
    }

    private static synchronized void initInstance()
    {
        if (instance == null)
            instance = new EnvProperties();
    }

    public Properties getProperties()
    {
		return props;                          
    } 

    public String getProperty(String name)
    {
		return props.getProperty(name);                          
    } 

    public String getProperty(String name, String defaultValue)
    {
		return props.getProperty(name, defaultValue);                          
    } 
    
    public String getPropertiesFileName()
    {
		return fileName;                          
    } 
    
    public EnvName getEnv()
    {
        return env;
    } 
}