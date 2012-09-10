package org.plasma.sdo.access.client;

import org.plasma.config.DataAccessProviderName;
import org.plasma.config.PlasmaConfig;

public class JDBCPojoDataAccessClient extends PojoDataAccessClient {
    
	public JDBCPojoDataAccessClient() {
    	super();
    	this.service = createProvider(
    			PlasmaConfig.getInstance().getDataAccessProvider(
    					DataAccessProviderName.JDBC).getClassName());
    }
}
