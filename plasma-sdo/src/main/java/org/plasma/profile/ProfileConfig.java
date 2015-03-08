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
package org.plasma.profile;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.common.bind.DefaultValidationEventHandler;
import org.plasma.common.exception.PlasmaRuntimeException;
import org.plasma.config.ConfigurationException;
import org.xml.sax.SAXException;

/**
 * Supports the configuration and dynamic, on-demand loading of PlasmaSDO Profile and 
 * related UML artifacts across multiple profile version.
 */
public class ProfileConfig {

    private static Log log = LogFactory.getLog(ProfileConfig.class);
    private static volatile ProfileConfig instance;
    private static final String defaultConfigFileName = "profile-config.xml";  
    private String configFileName;
    private long configFileLastModifiedDate = System.currentTimeMillis();
    private ProfileConfiguration config;
    private Map<ProfileURN, ProfileArtifact> artifactURNMap = new HashMap<ProfileURN, ProfileArtifact>();
    private Map<String, ProfileArtifact> artifactURIMap = new HashMap<String, ProfileArtifact>();
        
    private ProfileConfig()
    {
        log.debug("initializing...");
        try {
            this.configFileName = defaultConfigFileName;            
            ProfileConfigDataBinding configBinding = new ProfileConfigDataBinding(
	        		new ProfileConfigValidationEventHandler());
	        
            config = unmarshalConfig(this.configFileName, configBinding);  
            
            for (ProfileArtifact artifact : config.getArtifacts()) {
            	artifactURNMap.put(artifact.getUrn(), artifact);   
            	artifactURIMap.put(artifact.getNamespaceUri(), artifact);
            }           
        }
        catch (SAXException e) {
            throw new ConfigurationException(e);
        }
        catch (JAXBException e) {
            throw new ConfigurationException(e);
        }
    }
    
    public ProfileArtifact findArtifactByUrn(ProfileURN urn) {
    	return this.artifactURNMap.get(urn);
    }
    
    public ProfileArtifact findArtifactByUri(String namespaceUri) {
    	return this.artifactURIMap.get(namespaceUri);
    }
    
    public Collection<ProfileArtifact> getArtifacts()
    {
    	return Collections.unmodifiableCollection(this.artifactURNMap.values());
    }
    
    public String getConfigFileName() {
		return configFileName;
	}

	public long getConfigFileLastModifiedDate() {
		return configFileLastModifiedDate;
	}
    
    private ProfileConfiguration unmarshalConfig(String configFileName, ProfileConfigDataBinding binding)
    {
    	try {
	        InputStream stream = ProfileConfig.class.getResourceAsStream(configFileName);
	        if (stream == null)
	            stream = ProfileConfig.class.getClassLoader().getResourceAsStream(configFileName);
	        if (stream == null)
	            throw new ConfigurationException("could not find configuration file resource '" 
	                    + configFileName 
	                    + "' on the current classpath");        
	        
	        ProfileConfiguration result = (ProfileConfiguration)binding.validate(stream);

            URL url = ProfileConfig.class.getResource(configFileName);
            if (url == null)
            	url = ProfileConfig.class.getClassLoader().getResource(configFileName);
            if (url != null) {
                File urlFile = new File(url.getFile());
                if (urlFile.exists()) 
                	this.configFileLastModifiedDate = urlFile.lastModified();
            }
            
            
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
        	ProfileConfigDataBinding configBinding = new ProfileConfigDataBinding(
                    new DefaultValidationEventHandler());
            configBinding.marshal(this.config, stream);
        } catch (JAXBException e1) {
            throw new ConfigurationException(e1);
        } catch (SAXException e1) {
            throw new ConfigurationException(e1);
        }
    }
    
    public static ProfileConfig getInstance()
        throws PlasmaRuntimeException
    {
        if (instance == null)
            initializeInstance();
        return instance;
    }
    
    private static synchronized void initializeInstance()
        throws ConfigurationException
    {
        if (instance == null)
            instance = new ProfileConfig();
    }

    public ProfileConfiguration getConfig() {
        return config;
    } 
    
}
