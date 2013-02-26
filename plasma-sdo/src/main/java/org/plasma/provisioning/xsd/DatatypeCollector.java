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
package org.plasma.provisioning.xsd;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.plasma.xml.schema.AbstractSimpleType;
import org.plasma.xml.schema.Restriction;
import org.plasma.xml.schema.Schema;
import org.plasma.xml.schema.SchemaConstants;
import org.plasma.xml.schema.SimpleType;
import org.plasma.xml.schema.SimpleTypeVisitor;

/**
 * Schema (XSD) datatype name collector (simple type visitor) which
 * receives visitor events and detects "leaf" nodes within a
 * simple type hierarchy which are XSD fundamental types
 * and collects the type name(s). Clients then decide
 * how to handle the collected type(s).
 * @see SimpleTypeVisitor
 */
public class DatatypeCollector extends AbstractCollector implements SimpleTypeVisitor {

	private Schema schema;
	private Map<String, SimpleType> simpleTypeMap;
	private List<QName> result = new ArrayList<QName>();
	private boolean isListType = false;
	
	public DatatypeCollector(Schema schema,
			Map<String, SimpleType> simpleTypeMap) {
		super();
		this.schema = schema;
		this.simpleTypeMap = simpleTypeMap;
	}	

	public List<QName> getResult() {
		return result;
	}

	public boolean isListType() {
		return isListType;
	}

	@Override
	public void visit(AbstractSimpleType target,
			AbstractSimpleType source, int level) {
		Restriction restriction = target.getRestriction();
		if (restriction != null) {
	    	QName typeName = restriction.getBase();
			if (typeName != null) {
				if (typeName.getNamespaceURI() != null) {
	    			if (typeName.getNamespaceURI().equals(SchemaConstants.XMLSCHEMA_NAMESPACE_URI)) {
	    				this.result.add(typeName);
	    			}
				}
			}
		}
    	else if (target.getList() != null) {
    		this.isListType = true;
    		org.plasma.xml.schema.List typeList = target.getList();
    		QName typeName = typeList.getItemType();
    		if (typeName != null && typeName.getNamespaceURI() != null ) {
    			if (typeName.getNamespaceURI().equals(SchemaConstants.XMLSCHEMA_NAMESPACE_URI)) {
    				this.result.add(typeName);
    			}
    		}
    	}
 	}

	@Override
	public SimpleType getTopLevelSimpleType(QName name) {
		return simpleTypeMap.get(name.getLocalPart());
	}

	@Override
	public String getTargetNamespace() {
		return schema.getTargetNamespace();
	}
}
