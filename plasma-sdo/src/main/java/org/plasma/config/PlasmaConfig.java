package org.plasma.config;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.Fuml;
import org.modeldriven.fuml.io.FileArtifact;
import org.modeldriven.fuml.io.ResourceArtifact;
import org.plasma.common.bind.DefaultValidationEventHandler;
import org.plasma.common.env.EnvConstants;
import org.plasma.common.env.EnvProperties;
import org.plasma.common.exception.PlasmaRuntimeException;
import org.plasma.config.adapter.NamespaceAdapter;
import org.plasma.config.adapter.PropertyBindingAdapter;
import org.plasma.config.adapter.TypeBindingAdapter;
import org.xml.sax.SAXException;


public class PlasmaConfig {

    private static Log log = LogFactory.getLog(PlasmaConfig.class);
    private static PlasmaConfig instance = null;
    
    private static final String defaultConfigFileName = "plasma-config.xml";  
    
    private PlasmaConfiguration config;
    private Map<String, Artifact> artifactMap = new HashMap<String, Artifact>();
    private Map<String, NamespaceAdapter> sdoNamespaceMap = new HashMap<String, NamespaceAdapter>();
    private Map<DataAccessProviderName, DataAccessProvider> dataAccessServiceConfigMap 
        = new HashMap<DataAccessProviderName, DataAccessProvider>();

    private Map<DataAccessProviderName, Map<String, NamespaceProvisioning>> dataAccessServiceProvisioningMap 
        = new HashMap<DataAccessProviderName,  Map<String, NamespaceProvisioning>>();    
        
    private PlasmaConfig()
    {
        log.debug("initializing...");
        try {
            
            String fileName = EnvProperties.instance().getProperty(
                    EnvConstants.PROPERTY_NAME_ENV_CONFIG);
            
            if (fileName == null)
                fileName = defaultConfigFileName;
            
            PlasmaConfigDataBinding configBinding = new PlasmaConfigDataBinding(
	        		new PlasmaConfigValidationEventHandler());
	        
            config = unmarshalConfig(fileName, configBinding);
            
            
            for (Artifact artifact : config.getRepository().getArtifacts()) {
                artifactMap.put(artifact.getNamespaceUri(), artifact);
            }
            
            if (artifactMap.get(config.getSDO().getDefaultNamespace().getArtifact()) == null)
                throw new PlasmaRuntimeException("Invalid SDO Namespace - could not find repository artifact based on URI '"
                        + config.getSDO().getDefaultNamespace().getArtifact() + "'");
            sdoNamespaceMap.put(config.getSDO().getDefaultNamespace().getUri(), 
                    new NamespaceAdapter(config.getSDO().getDefaultNamespace()));
            
            for (Namespace namespace : config.getSDO().getNamespaces()) {  
                if (artifactMap.get(namespace.getArtifact()) == null)
                    throw new PlasmaRuntimeException("Invalid SDO Namespace - could not find repository artifact based on URI '"
                            + namespace.getArtifact() + "'"); 
                
                if (this.sdoNamespaceMap.get(namespace.getUri()) != null)
        			throw new ConfigurationException("duplicate SDO namespace configuration - "
        					+ "a namespace with URI '" + namespace.getUri() + "' already exists "
        					+ "within the configucation");
                
                // create deflt provisioning based on namespace URI
                if (namespace.getProvisioning() == null) {
                	NamespaceProvisioning provisioning = createDefaultProvisioning(namespace.getUri());
                	namespace.setProvisioning(provisioning);
                }

                
                //map namespaces by URI and interface package
                NamespaceAdapter namespaceAdapter = new NamespaceAdapter(namespace);
                
                this.sdoNamespaceMap.put(namespace.getUri(),  namespaceAdapter);
                if (this.sdoNamespaceMap.get(namespace.getProvisioning().getPackageName()) != null)
        			throw new ConfigurationException("duplicate SDO namespace configuration - "
        					+ "a namespace with provisioning package name '" + namespace.getProvisioning().getPackageName() + "' already exists "
        					+ "within the configucation");
                this.sdoNamespaceMap.put(namespace.getProvisioning().getPackageName(),  namespaceAdapter);
                
                for (TypeBinding typeBinding : namespace.getTypeBindings()) {
                	namespaceAdapter.addTypeBinding(typeBinding);
                }
            }
            
        	// create default SDO namespace configs based on artifact where
            // config does not exist
            Iterator<String> iter = artifactMap.keySet().iterator();
            while (iter.hasNext()) {
            	String artifactUri = iter.next();
            	Artifact artifact = artifactMap.get(artifactUri);
                if (this.sdoNamespaceMap.get(artifact.getNamespaceUri()) == null) {
                	Namespace namespace = new Namespace();	
                	namespace.setArtifact(artifact.getNamespaceUri());
                	namespace.setUri(artifact.getNamespaceUri()); // SDO Namespace URI same as artifact URI 
                	NamespaceProvisioning provisioning = createDefaultProvisioning(artifact.getNamespaceUri());
                	namespace.setProvisioning(provisioning);
                    NamespaceAdapter namespaceAdapter = new NamespaceAdapter(namespace);                    
                    this.sdoNamespaceMap.put(namespace.getUri(),  namespaceAdapter);
                }
            }
            
            for (DataAccessService daconfig : config.getSDO().getDataAccessServices()) {
            	
             	for (DataAccessProvider provider : daconfig.getDataAccessProviders())
             	{
	            	dataAccessServiceConfigMap.put(provider.getName(), provider);
	            	Map<String, NamespaceProvisioning> provMap = dataAccessServiceProvisioningMap.get(provider.getName());
	            	if (provMap == null) {
	            		provMap = new HashMap<String, NamespaceProvisioning>();
	            		dataAccessServiceProvisioningMap.put(provider.getName(), provMap);
	            	}
	            	for (NamespaceLink namespaceLink : provider.getNamespaceLinks()) {
	            		if (namespaceLink.getUri() == null)
	                        throw new ConfigurationException("expected namespace URI for Data Access Service configuration '"
	                                + provider.getName().toString() + "'");  
	            		
	            		NamespaceAdapter adapter = sdoNamespaceMap.get(namespaceLink.getUri());
	            		if (adapter == null)
	                        throw new ConfigurationException("Invalid SDO Namespace - could not find SDO namespace based on namespace URI '"
	                                + namespaceLink.getUri() + "' for Data Access Service configuration '"
	                                + provider.getName().toString() + "'");  
	            		if (adapter.getNamespace().getProvisioning() != null && 
	            			adapter.getNamespace().getProvisioning().getPackageName() != null &&
	            			namespaceLink.getProvisioning() != null) 
	            		{
	            			if (adapter.getNamespace().getProvisioning().getPackageName().equals(
	            					namespaceLink.getProvisioning().getPackageName())) 
	            			{
	                            throw new ConfigurationException("Duplicate provisioning package name ("
	                            		+ namespaceLink.getProvisioning().getPackageName() 
	                            		+ ") for SDO Namespace '"
	                                    + namespaceLink.getUri() + "' and Data Access Service configuration '"
	                                    + provider.getName().toString() + "'");  
	            			}				
	            		}
	            		if (namespaceLink.getProvisioning() != null)
	            		    provMap.put(namespaceLink.getUri(), 
	            		    		namespaceLink.getProvisioning());		            		
	            	}
                }
            }
        }
        catch (SAXException e) {
            throw new ConfigurationException(e);
        }
        catch (JAXBException e) {
            throw new ConfigurationException(e);
        }
    }
    
    private NamespaceProvisioning createDefaultProvisioning(String uri) {
    	NamespaceProvisioning provisioning = new NamespaceProvisioning();
    	String[] tokens = ConfigUtils.toPackageTokens(uri);
    	StringBuilder buf = new StringBuilder();
    	for (int i = 0; i < tokens.length; i++) {
    		if (i > 0)
    			buf.append(".");
    		buf.append(tokens[i]);
    	}
    	provisioning.setPackageName(buf.toString());
    	return provisioning;
    }
    
    @SuppressWarnings("unchecked")
    private PlasmaConfiguration unmarshalConfig(String configFileName, PlasmaConfigDataBinding binding)
    {
    	try {
	        InputStream stream = PlasmaConfig.class.getResourceAsStream(configFileName);
	        if (stream == null)
	            stream = PlasmaConfig.class.getClassLoader().getResourceAsStream(configFileName);
	        if (stream == null)
	            throw new PlasmaRuntimeException("could not find configuration file resource '" 
	                    + configFileName 
	                    + "' on the current classpath");        
	        
            PlasmaConfiguration result = (PlasmaConfiguration)binding.validate(stream);
            return result;
    	}
        catch (UnmarshalException e) {
            throw new ConfigurationException(e);
        }
        catch (JAXBException e) {
            throw new ConfigurationException(e);
        }
    }
    
    public void marshal(OutputStream stream) {
        try {
            PlasmaConfigDataBinding configBinding = new PlasmaConfigDataBinding(
                    new DefaultValidationEventHandler());
            configBinding.marshal(this.config, stream);
        } catch (JAXBException e1) {
            throw new ConfigurationException(e1);
        } catch (SAXException e1) {
            throw new ConfigurationException(e1);
        }
    }
    
    public static PlasmaConfig getInstance()
        throws PlasmaRuntimeException
    {
        if (instance == null)
            initializeInstance();
        return instance;
    }
    
    private static synchronized void initializeInstance()
        throws PlasmaRuntimeException
    {
        if (instance == null)
            instance = new PlasmaConfig();
    }

    public PlasmaConfiguration getConfig() {
        return config;
    } 
    
    public SDO getSDO() {
        
        return config.getSDO();
    }
    
    public Namespace getSDONamespaceByURI(String uri) {
        NamespaceAdapter result = sdoNamespaceMap.get(uri);
        if (result != null)
            return result.getNamespace();
        else
            throw new PlasmaRuntimeException("no configured SDO namespace found for URI '"
                    + uri + "'");
    }
    
    public Namespace getSDONamespaceByInterfacePackage(String packageName) {
    	NamespaceAdapter result = sdoNamespaceMap.get(packageName);
        if (result != null)
            return result.getNamespace();
        else
            throw new PlasmaRuntimeException("no configured SDO namespace found for interface package name '"
                    + packageName + "'");
    }

    public TypeBinding findTypeBinding(String uri, String typeName) {
        NamespaceAdapter namespaceAdapter = sdoNamespaceMap.get(uri);
        if (namespaceAdapter != null) {
        	TypeBindingAdapter resultAdapter = namespaceAdapter.findTypeBinding(typeName);
        	if (resultAdapter != null)
        	    return resultAdapter.getBinding();
        	else
        		return null;
        }
        else
            throw new PlasmaRuntimeException("no configured SDO namespace found for URI '"
                    + uri + "'");
    }

    public PropertyBinding findPropertyBinding(String uri, String typeName, String propertyName) {
        NamespaceAdapter namespaceAdapter = sdoNamespaceMap.get(uri);
        if (namespaceAdapter != null) {
        	TypeBindingAdapter typeBinding = namespaceAdapter.findTypeBinding(typeName);
        	if (typeBinding != null)
        	{
        		PropertyBindingAdapter resultAdapter = typeBinding.findPropertyBinding(propertyName);
        		if (resultAdapter != null)
        			return resultAdapter.getBinding();
        		else
        			return null;
        	}
        	else
        		return null;
        }
        else
            throw new PlasmaRuntimeException("no configured SDO namespace found for URI '"
                    + uri + "'");
    }
    
    /**
     * Creates a dynamic SDO namespace configuration 
     * @param uri the dynamic namespace URI
     * @param supplierUri the original static URI which supplied the derived dynamic namespace
     */
    public void addDynamicSDONamespace(String uri, String supplierUri) {
    	Namespace namespace = new Namespace();
    	namespace.setArtifact(uri); // assumed artifact is created in-line dynamically
    	namespace.setUri(uri);
        if (this.sdoNamespaceMap.get(namespace.getUri()) != null)
			throw new ConfigurationException("duplicate SDO namespace configuration - "
					+ "a namespace with URI '" + namespace.getUri() + "' already exists "
					+ "within the configucation");
    	this.sdoNamespaceMap.put(uri, new NamespaceAdapter(namespace));
    	
    	// find the supplier URI where mapped and add a mapping to the
    	// new dynamic URI
    	for (Map<String, NamespaceProvisioning> map : dataAccessServiceProvisioningMap.values()) {
    		Iterator<String> iter = map.keySet().iterator();
    		NamespaceProvisioning provisioning = null;
    		while (iter.hasNext()) {
    			String key = iter.next();
    			if (key.equals(supplierUri)) {
    				provisioning = map.get(key);
    			}
    		}
    		if (provisioning != null)
    			map.put(uri, provisioning);
    	}
    	
    }

    public DataAccessProvider getDataAccessProvider(DataAccessProviderName providerName) {
    	DataAccessProvider result = this.dataAccessServiceConfigMap.get(providerName);
        if (result != null)
            return result;
        else
            throw new PlasmaRuntimeException("no data access provider configuration found for name'"
                    + providerName.value() + "'");
    }

    public NamespaceProvisioning getProvisioningByNamespaceURI(DataAccessProviderName providerName, String uri) {
    	NamespaceProvisioning result = this.dataAccessServiceProvisioningMap.get(providerName).get(uri);
    	if (result != null)
            return result;
        else
            throw new NonExistantNamespaceException("no configured '"+ providerName.value() + "' provider namespace found for URI '"
                    + uri + "'");
    }
    
    public Repository getRepository() {
        
        return config.getRepository();
    }

    public String getSDOInterfaceClassName(String uri, String name) {
    	String result = name;
    	@SuppressWarnings("unused")
		Namespace namespace = getSDONamespaceByURI(uri); // validate URI for now
        return result;
    }

    public String getSDOInterfacePackageName(String uri) {		
		Namespace sdoNamespace = this.getSDONamespaceByURI(uri);
		String packageName = sdoNamespace.getProvisioning().getPackageName();
    	return packageName;
    }
    
    public String getSDOImplementationClassName(String uri, String name) {
    	String result = name;
    	
    	@SuppressWarnings("unused")
		Namespace namespace = getSDONamespaceByURI(uri); // validate URI for now
    	if (namespace != null) { 
    		String prefix = null;
    		String suffix = null;
    		if (this.getSDO() != null && this.getSDO().getGlobalProvisioning() != null &&
    				this.getSDO().getGlobalProvisioning().getImplementation() != null) {
    			ImplementationProvisioning prov = this.getSDO().getGlobalProvisioning().getImplementation();
    			prefix = prov.getClassNamePrefix();
    			suffix = prov.getClassNameSuffix();
    		}
			if (prefix != null && prefix.trim().length() > 0)
				result = prefix + result;
			if (suffix != null && suffix.trim().length() > 0)
				result = result + suffix;		
	    	return result;
    	}
    	return null;
    }
    
    public String getSDOImplementationPackageName(String uri) {		
    	Namespace sdoNamespace = this.getSDONamespaceByURI(uri);
    	if (sdoNamespace != null && sdoNamespace.getProvisioning() != null) {
		    String packageName = sdoNamespace.getProvisioning().getPackageName();
		    String subpackage = null;
		    if (this.getSDO() != null && this.getSDO().getGlobalProvisioning() != null &&
    				this.getSDO().getGlobalProvisioning().getImplementation() != null) {
    			ImplementationProvisioning prov = this.getSDO().getGlobalProvisioning().getImplementation();
    			subpackage = prov.getChildPackageName();
    		}
		    if (subpackage != null && subpackage.trim().length() > 0)
			    packageName = packageName + "." + subpackage;
    	    return packageName;
    	}
    	return null;
    }

    public boolean generateQueryDSL(String uri, String name) {
    	@SuppressWarnings("unused")
		Namespace namespace = getSDONamespaceByURI(uri); // validate URI for now
		if (this.getSDO() != null && this.getSDO().getGlobalProvisioning() != null &&
			this.getSDO().getGlobalProvisioning().getQueryDSL() != null) {
			
			// turned off globally
			QueryDSLProvisioning queryDsl = this.getSDO().getGlobalProvisioning().getQueryDSL();		
			if (!queryDsl.isGenerate())
				return false;
			
			
			NamespaceProvisioning prov = namespace.getProvisioning();
			if (prov != null && prov.getQueryDSL() != null)
				if (!prov.getQueryDSL().isGenerate())
					return false;
		}
    	return true;
    }
    
    public String getQueryDSLImplementationClassName(String uri, String name) {
    	String result = name;
    	
    	@SuppressWarnings("unused")
		Namespace namespace = getSDONamespaceByURI(uri); // validate URI for now
    	if (namespace != null) { 
    		String prefix = null;
    		String suffix = null;
    		if (this.getSDO() != null && this.getSDO().getGlobalProvisioning() != null &&
    				this.getSDO().getGlobalProvisioning().getQueryDSL() != null) {
    			QueryDSLProvisioning queryDsl = this.getSDO().getGlobalProvisioning().getQueryDSL();
    			if (queryDsl.getImplementation() != null) {
	    			ImplementationProvisioning prov = queryDsl.getImplementation();
	    			prefix = prov.getClassNamePrefix();
	    			suffix = prov.getClassNameSuffix();
    			}
    		}
			if (prefix != null && prefix.trim().length() > 0)
				result = prefix + result;
			if (suffix != null && suffix.trim().length() > 0)
				result = result + suffix;		
	    	return result;
    	}
    	return null;
    }
    
    
    public String getServiceImplementationClassName(DataAccessProviderName providerName, 
    		String uri, String name) {
    	String result = name;
    	DataAccessProvider config = this.getDataAccessProvider(providerName);
    	@SuppressWarnings("unused")
		Namespace namespace = getSDONamespaceByURI(uri); // validate URI for now
    	return result;
    } 
    
    public String getServiceImplementationPackageName(DataAccessProviderName serviceName, 
    		String uri) {
    	
    	NamespaceProvisioning provisioning = getProvisioningByNamespaceURI(serviceName, uri);
    	@SuppressWarnings("unused")
		Namespace namespace = getSDONamespaceByURI(uri); // validate URI for now
    	return provisioning.getPackageName();
    }    
    
}
