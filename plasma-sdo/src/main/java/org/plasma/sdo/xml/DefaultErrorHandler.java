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
package org.plasma.sdo.xml;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * A simple error handler using the given XMLOptions to determine
 * the desired behavior when an error is encountered.  
 */
public class DefaultErrorHandler implements org.xml.sax.ErrorHandler {

    private static Log log = LogFactory.getLog(DefaultErrorHandler.class);
	private XMLOptions options;
	
	@SuppressWarnings("unused")
	private DefaultErrorHandler() {}
	public DefaultErrorHandler(XMLOptions options) {
		this.options = options;
	}
	
	public void error(SAXParseException e) throws SAXException {
		String msg = "line:column[" + e.getLineNumber()
            + ":" + e.getColumnNumber() + "]";
	    msg += " - " + e.getMessage();
		if (options.isFailOnValidationError()) {
			throw new SAXParseException(msg, new ErrorLocator(e));
		}
		else {
			if (options.getValidationLog() != null) {
				options.getValidationLog().error(msg);
			}
			else
				log.error(msg);					
		}
	}

	public void fatalError(SAXParseException e) throws SAXException {
		String msg = "line:column[" + e.getLineNumber()
            + ":" + e.getColumnNumber() + "]";
        msg += " - " + e.getMessage();
		if (options.isFailOnValidationError()) {
			throw new SAXParseException(msg, new ErrorLocator(e));
		}
		else {
			if (options.getValidationLog() != null) {
				options.getValidationLog().fatal(msg);
			}
			else
				log.fatal(msg);					
		}
	}

	public void warning(SAXParseException e) throws SAXException {
		String msg = "line:column[" + e.getLineNumber()
            + ":" + e.getColumnNumber() + "]";
		msg += " - " + e.getMessage();
		if (options.getValidationLog() != null) {
			options.getValidationLog().warn(msg);
		}
		else
			log.warn(msg);					
	}    	
}
