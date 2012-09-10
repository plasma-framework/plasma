package org.plasma.sdo.access.model;

import org.plasma.common.env.EnvConstants;



@Deprecated
public interface EntityConstants
{

    //public static final String PLASMA_SDO_MODEL_PACKAGE_PROPERTY = "plasma.sdo.package";
    //public static final String PLASMA_SDO_ACCESS_MODEL_PACKAGE_PROPERTY = "plasma.sdo.access.package";

    public static final String JAVAX_JDO_OPTION_CONNECTIONUSERNAME = "javax.jdo.option.ConnectionUserName";
    public static final String JAVAX_JDO_OPTION_CONNECTIONURL = "javax.jdo.option.ConnectionURL";
    public static final String JAVAX_JDO_OPTION_CONNECTIONPASSWORD = "javax.jdo.option.ConnectionPassword";
    public static final String JAVAX_JDO_OPTION_CONNECTIONDRIVERNAME = "javax.jdo.option.ConnectionDriverName";
    public static final String JAVAX_JDO_OPTION_CONNECTIONFACTORYNAME = EnvConstants.PROPERTY_NAME_DATASOURCE;

    
    
    public static final String JPA_METADATA_INIT_ON_STARTUP = "org.plasma.sdo.jpa.metadataInitOnStartup";
    

    public static final String DATA_ACCESS_CLASS_MEMBER_PREFIX = "";
    public static final String DATA_ACCESS_CLASS_MEMBER_MULTI_VALUED_SUFFIX = "";
    public static final String DATA_ACCESS_TRAVERSAL_PATH_DELIMITER = ".";
    public static final String DATA_ACCESS_DECLARATION_DELIMITER = ",";
}