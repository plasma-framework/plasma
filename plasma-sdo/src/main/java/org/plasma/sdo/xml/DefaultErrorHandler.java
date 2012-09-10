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
