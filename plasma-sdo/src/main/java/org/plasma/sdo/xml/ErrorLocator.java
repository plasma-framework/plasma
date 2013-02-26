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
