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

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventLocator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.common.bind.BindingValidationEventHandler;
import org.plasma.config.ConfigurationException;

public class ProfileConfigValidationEventHandler implements BindingValidationEventHandler {
	
    private static Log log = LogFactory.getLog(ProfileConfigValidationEventHandler.class);
    private int errorCount;
    private boolean cumulative = true;
	
	public int getErrorCount() {
		return errorCount;
	}

	public ProfileConfigValidationEventHandler() {}	
	public ProfileConfigValidationEventHandler(boolean cumulative) {
		this.cumulative = cumulative;
	}	
	
    public boolean handleEvent(ValidationEvent ve) {
        boolean result = this.cumulative;
        this.errorCount++;        
        ValidationEventLocator vel = ve.getLocator();
        
        String message = "Line:Col:Offset[" + vel.getLineNumber() + ":" + vel.getColumnNumber() + ":" 
            + String.valueOf(vel.getOffset())
            + "] - " + ve.getMessage();
        
        switch (ve.getSeverity()) {
        case ValidationEvent.WARNING:
            log.warn(message);
            break;
        case ValidationEvent.ERROR:
        case ValidationEvent.FATAL_ERROR:
            log.fatal(message);
            throw new ConfigurationException(message);
        default:
            log.error(message);
        }
        return result;
    }
    
    public void reset()
    {
    	this.errorCount = 0;
    }

}
