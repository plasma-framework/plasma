package org.plasma.config.adapter;

import org.plasma.config.DataAccessProvider;

public class DataAccessProviderAdapter {
    private DataAccessProvider provider;

    @SuppressWarnings("unused")
	private DataAccessProviderAdapter() {}
	public DataAccessProviderAdapter(DataAccessProvider provider) {
		super();
		this.provider = provider;
	}

	public DataAccessProvider getProvider() {
		return provider;
	}
	
	
    
}
