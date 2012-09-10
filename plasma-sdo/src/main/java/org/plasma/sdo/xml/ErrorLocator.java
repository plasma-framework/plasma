package org.plasma.sdo.xml;

import org.xml.sax.Locator;
import org.xml.sax.SAXParseException;

public class ErrorLocator implements Locator {
    private int lineNumber;
	private int columnNumber;
	private String publicId;
	private String systemId;
	
	
	public ErrorLocator(SAXParseException e) {
	    this.lineNumber = e.getLineNumber();
	    this.columnNumber = e.getColumnNumber();
	    this.publicId = e.getPublicId();
	    this.systemId = e.getSystemId();
	}
	
	public ErrorLocator(int lineNumber, int columnNumber, String publicId,
			String systemId) {
		super();
		this.lineNumber = lineNumber;
		this.columnNumber = columnNumber;
		this.publicId = publicId;
		this.systemId = systemId;
	}
	public int getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	public int getColumnNumber() {
		return columnNumber;
	}
	public void setColumnNumber(int columnNumber) {
		this.columnNumber = columnNumber;
	}
	public String getPublicId() {
		return publicId;
	}
	public void setPublicId(String publicId) {
		this.publicId = publicId;
	}
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
}
