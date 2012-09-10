package org.plasma.sdo.xml;

import org.apache.commons.logging.Log;
import org.xml.sax.ErrorHandler;

import commonj.sdo.DataGraph;

public interface XMLOptions {
	
	/**
	 * The desired root namespace prefix.
	 * @return the desired root namespace prefix
	 */
	public String getRootNamespacePrefix();
	
	/**
	 * Sets desired root namespace prefix.
	 * @param rootNamespacePrefix the desired root namespace prefix
	 */
	public void setRootNamespacePrefix(String rootNamespacePrefix);

	/**
	 * The root namespace URI.
	 * @return the root namespace URI
	 */
	public String getRootElementNamespaceURI(); 
	
	/**
	 * Sets root namespace URI.
	 * @param rootElementName the root namespace URI
	 */
	public void setRootElementNamespaceURI(String rootElementName);

	/**
	 * The root element name.
	 * @return the root element name
	 */
	public String getRootElementName();	
	
	/**
	 * Sets root element name
	 * @param rootElementName the root element name
	 */
	public void setRootElementName(String rootElementName);
	
	/**
	 * Returns whether to perform XML Schema (XSD) validation
	 * when reading XML input. By default, any errors encountered are logged
	 * within the context of the parsing utility unless a
	 * log is supplied. 
	 * @return whether to perform XML Schema (XSD) validation
	 * when reading XML input.
	 * @see org.apache.commons.logging.Log
	 */
    public boolean isValidate();
    
    /**
     * Set whether to perform XML Schema (XSD) validation
	 * when reading XML input. By default, any errors encountered are logged
	 * within the context of the parsing utility unless a
	 * log is supplied.
     * @param validate whether to perform XML Schema (XSD) validation
     */
	public void setValidate(boolean validate);

	/**
     * Returns the current XML Schema (XSD) validation 
     * error log. 
     * @return current XML Schema (XSD) validation 
     * error log
     */
    public Log getValidationLog();
 
    /**
     * Sets the current XML Schema (XSD) validation 
     * error log
     * @param validationLog the log
     */
	public void setValidationLog(Log validationLog);
    
    /**
     * Returns whether to propagate XML parser errors
     * encountered when validating an XML document against
     * an associated XML Schema. Only 'ERROR' and 'FATAL' errors
     * are propagated. 
     * @return whether to propagate XML parser errors
     * encountered when validating an XML document against
     * an associated XML Schema
     */
    public boolean isFailOnValidationError();
    

    /**
     * Returns the current XML Schema (XSD) validation 
     * error handler
     * @return the current XML Schema (XSD) validation 
     * error handler
     */
    public ErrorHandler getErrorHandler();
    
    /**
     * Sets the current XML Schema (XSD) validation 
     * error handler
     * @param handler the current XML Schema (XSD) validation 
     * error handler
     */
    public void setErrorHandler(ErrorHandler handler);



}
