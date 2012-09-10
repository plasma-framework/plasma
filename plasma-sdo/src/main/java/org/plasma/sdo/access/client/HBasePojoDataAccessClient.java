package org.plasma.sdo.access.client;

import org.plasma.config.DataAccessProviderName;
import org.plasma.config.PlasmaConfig;

public class HBasePojoDataAccessClient extends PojoDataAccessClient {
    
	public HBasePojoDataAccessClient() {
    	super(DataAccessProviderName.HBASE);
    }
}
