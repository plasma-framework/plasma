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




public interface EnvConstants
{
    
    
    
    public static final String PROPERTY_NAME_ENV_NAME = "plasma.env"; 
    /** common runtime property file. */
    public static final String PROPERTY_NAME_ENV_PROPERTIES = "plasma.env.property.filename"; 
    public static final String PROPERTY_NAME_DEFAULT_ENV_PROPERTIES = "plasma.properties"; 
    public static final String PROPERTY_NAME_ENV_CONFIG = "plasma.configuration"; 
    public static final String PROPERTY_NAME_DATASOURCE = "javax.jdo.option.ConnectionFactoryName";
    
    public static final String PROPERTY_NAME_MAIL_HANDLER = "mil.army.sddc.dps.mail.handler.class";
    public static final String ENV_PROPERTY_NAME_PREFIX = "$";

}