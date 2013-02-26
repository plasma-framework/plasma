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

import javax.xml.namespace.QName;
import javax.xml.stream.Location;

import org.plasma.sdo.PlasmaType;

public abstract class StreamNode {
	private PlasmaType type;
	protected int line;
	protected int column;
	protected QName name;
	
	
	@SuppressWarnings("unused")
	private StreamNode() {}
	public StreamNode(PlasmaType type, QName name, Location loc) {
		if (type == null)
			throw new IllegalArgumentException("expected type argument");
		this.type = type;
		this.name = name;
		this.line = loc.getLineNumber();
		this.column = loc.getColumnNumber();
	}
	public PlasmaType getType() {
		return type;
	}
	public int getLine() {
		return line;
	}
	public int getColumn() {
		return column;
	}
	public String getLocalName() {
		return name.getLocalPart();
	}
	public String getNamespaceURI() {
		return name.getNamespaceURI();
	}
	public String getPrefix() {
		return name.getPrefix();
	}
	
	
}

