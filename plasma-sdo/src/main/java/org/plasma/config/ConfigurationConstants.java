package org.plasma.config;

public class ConfigurationConstants {
	
	public static final String JDBC_PROVIDER_PROPERTY_PREFIX = "org.plasma.sdo.access.provider.jdbc.";
	
	/** The fully qualified Java class name of the connection pool or data source provider */
    public static final String JDBC_PROVIDER_NAME = JDBC_PROVIDER_PROPERTY_PREFIX + "ConnectionProviderName";
    
	/** The fully qualified JNDI name of the JDBC datasource  */
    public static final String JDBC_DATASOURCE_NAME = JDBC_PROVIDER_PROPERTY_PREFIX + "ConnectionDataSourceName";

    /** The fully qualified Java class name of the JDBC driver */
    public static final String JDBC_DRIVER_NAME = JDBC_PROVIDER_PROPERTY_PREFIX + "ConnectionDriverName";

    /** The name of the JDBC vendor */
    public static final String JDBC_VENDOR = JDBC_PROVIDER_PROPERTY_PREFIX + "Vendor";
    
    /** The driver specific JDBC connection URL */
    public static final String JDBC_URL = JDBC_PROVIDER_PROPERTY_PREFIX + "ConnectionURL";
    
    /** The user name */
    public static final String JDBC_USER = JDBC_PROVIDER_PROPERTY_PREFIX + "ConnectionUserName";
    
    /** The user password */
    public static final String JDBC_PASSWORD = JDBC_PROVIDER_PROPERTY_PREFIX + "ConnectionPassword";

}
