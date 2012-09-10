package org.plasma.common.naming;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.plasma.common.env.EnvProperties;

public class InitialContextFactory {
	
	public static String DEFAULT_JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";
	
	public static final String DPS_NAMING_URL = "dps.jndi.naming.provider.url";
	   
	private static InitialContextFactory contextFactory = new InitialContextFactory();

	public static InitialContextFactory getInstance() {
		return contextFactory;
	}
	
	private InitialContextFactory() {
	}
	
	public InitialContext getInitialContext(String namingUrl) throws NamingException 
	{
        if (namingUrl == null) {
        	throw new RuntimeException(DPS_NAMING_URL + " is a required.");
        }         
        
        Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, DEFAULT_JNDI_FACTORY);
        env.put(Context.PROVIDER_URL, namingUrl);
        
        return new InitialContext(env);
	}
	
	public InitialContext getInitialContext() throws NamingException {
        //String namingFactory = EnvProperties.instance().getProperty(Context.INITIAL_CONTEXT_FACTORY, InitialContextFactory.DEFAULT_JNDI_FACTORY);
        String namingUrl = EnvProperties.instance().getProperty(DPS_NAMING_URL);
        
        if (namingUrl == null) 
        {
        	throw new RuntimeException("The following required property is missing: '" + DPS_NAMING_URL + "':"
        	        + EnvProperties.instance().dumpProperties("\n    "));
        }

        return getInitialContext(namingUrl);
	}
}
