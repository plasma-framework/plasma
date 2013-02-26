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

import org.plasma.provisioning.EnumerationConstraint;
import org.plasma.provisioning.ValueConstraint;
import org.plasma.xml.schema.AbstractSimpleType;
import org.plasma.xml.schema.Schema;
import org.plasma.xml.schema.SimpleType;
import org.plasma.xml.schema.SimpleTypeVisitor;

/**
 * Provisioning constraint collector (simple type visitor) which
 * receives visitor events and and calls a delegate
 * assembler/builder for constraints, storing the
 * constraint for clients.  
 * @see SimpleTypeVisitor
 */
public class ConstraintCollector extends AbstractCollector implements SimpleTypeVisitor {

	private Schema schema;
	private Map<String, SimpleType> simpleTypeMap;
	private ConstraintAssembler assembler;
	private List<EnumerationConstraint> enumerationConstraints = new ArrayList<EnumerationConstraint>();	
	private List<ValueConstraint> valueConstraints = new ArrayList<ValueConstraint>();	
	
	public ConstraintCollector(Schema schema,
			Map<String, SimpleType> simpleTypeMap,
			ConstraintAssembler assembler) {
		super();
		this.schema = schema;
		this.simpleTypeMap = simpleTypeMap;
		this.assembler = assembler;
	}	

	public List<EnumerationConstraint> getEnumerationConstraints() {
		return enumerationConstraints;
	}

	public List<ValueConstraint> getValueConstraints() {
		return valueConstraints;
	}

	@Override
	public void visit(AbstractSimpleType target,
			AbstractSimpleType source, int level) {
    	if (target.getRestriction() != null) 
    	{
    		if (SimpleTypeUtils.isEnumeration(target)) {
    			EnumerationConstraint constraint = 
    					assembler.buildEnumerationConstraint(target, source);
    			this.enumerationConstraints.add(constraint);
    		}
    		else {
    			ValueConstraint constraint = 
    					assembler.buildValueConstraint(target);
      			this.valueConstraints.add(constraint);
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
