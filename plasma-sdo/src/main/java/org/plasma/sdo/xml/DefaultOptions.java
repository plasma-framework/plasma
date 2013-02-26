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
import org.xml.sax.ErrorHandler;

public class DefaultOptions implements XMLOptions {
	private boolean failOnValidationError; 
	private String rootElementName;
	private String rootElementNamespaceURI;
	private String rootNamespacePrefix = "tns";
	private Log validationLog;
	private boolean validate = true;
	private ErrorHandler errorHandler;
	
	public DefaultOptions(String rootElementNamespaceURI) {
		super();
		this.rootElementNamespaceURI = rootElementNamespaceURI;
	}
 
	public DefaultOptions(String rootElementNamespaceURI, String rootElementName) {
		super();
		this.rootElementName = rootElementName;
		this.rootElementNamespaceURI = rootElementNamespaceURI;
	}

	public boolean isFailOnValidationError() {
		return failOnValidationError;
	}

	public void setFailOnValidationError(boolean failOnValidationError) {
		this.failOnValidationError = failOnValidationError;
		if (this.failOnValidationError)
			this.validate = true;
	}

	public boolean isValidate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	public void setRootElementName(String rootElementName) {
		this.rootElementName = rootElementName;
	}

	public void setRootElementNamespaceURI(String rootElementNamespaceURI) {
		this.rootElementNamespaceURI = rootElementNamespaceURI;
	}

	public void setRootNamespacePrefix(String rootNamespacePrefix) {
		this.rootNamespacePrefix = rootNamespacePrefix;
	}

	public void setValidationLog(Log validationLog) {
		this.validationLog = validationLog;
	}

	public String getRootElementName() {
		return rootElementName;
	}

	public String getRootElementNamespaceURI() {
		return rootElementNamespaceURI;
	}

	public String getRootNamespacePrefix() {
		return rootNamespacePrefix;
	}

	public Log getValidationLog() {
		return validationLog;
	}

	public ErrorHandler getErrorHandler() {
		return errorHandler;
	}

	public void setErrorHandler(ErrorHandler handler) {
		this.errorHandler = handler;		
	}

}
