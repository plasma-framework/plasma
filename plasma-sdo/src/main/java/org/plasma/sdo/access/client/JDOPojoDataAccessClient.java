package org.plasma.sdo.access.client;

import org.plasma.config.DataAccessProviderName;
import org.plasma.config.PlasmaConfig;

public class JDOPojoDataAccessClient extends PojoDataAccessClient {
    
	public JDOPojoDataAccessClient() {
    	super();
    	this.service = createProvider(
    			PlasmaConfig.getInstance().getDataAccessProvider(
    					DataAccessProviderName.JDO).getClassName());
    }
}
