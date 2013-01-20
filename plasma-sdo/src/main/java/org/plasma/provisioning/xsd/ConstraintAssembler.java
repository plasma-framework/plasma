package org.plasma.provisioning.xsd;

import javax.xml.bind.JAXBElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.provisioning.EnumerationConstraint;
import org.plasma.provisioning.EnumerationRef;
import org.plasma.provisioning.ValueConstraint;
import org.plasma.xml.schema.AbstractSimpleType;
import org.plasma.xml.schema.Facet;
import org.plasma.xml.schema.Pattern;
import org.plasma.xml.schema.Restriction;

public class ConstraintAssembler extends AbstractAssembler {
	
	private static Log log = LogFactory.getLog(
			   ConstraintAssembler.class); 
		
	public ConstraintAssembler(String destNamespaceURI,
			String destNamespacePrefix) {
		super(destNamespaceURI, destNamespacePrefix);
	}

	public EnumerationConstraint buildEnumerationConstraint(AbstractSimpleType simpleType, AbstractSimpleType source)
    {
	    EnumerationConstraint constraint = new EnumerationConstraint();
	    EnumerationRef enumRef = new EnumerationRef();
	    String localName = null;
	    if (simpleType.getName() != null)
	    	localName = simpleType.getName();
	    else if (source.getName() != null)
	    	localName = source.getName();
	    else
	    	throw new IllegalStateException("could not create enumeration reference");
	    String logicalName = this.formatLocalClassName(localName);
	    enumRef.setName(logicalName);
	    enumRef.setUri(this.destNamespaceURI);   
	    constraint.setValue(enumRef);
	    return constraint;
    }
	
	public ValueConstraint buildValueConstraint(AbstractSimpleType simpleType)
    {
		return buildValueConstraint(simpleType.getRestriction());
    }
	
	public ValueConstraint buildValueConstraint(Restriction rest)
    {
    	ValueConstraint constraint = new ValueConstraint();
    	for (Object obj : rest.getMinExclusivesAndMinInclusivesAndMaxExclusives()) {
    		if (obj instanceof JAXBElement<?>) {
    			JAXBElement<Facet> facet = (JAXBElement<Facet>)obj;
    			if (facet.getName().getLocalPart().equals("maxInclusive")) {
    				Facet numFacet = (Facet)facet.getValue();
    				constraint.setMaxInclusive(Float.valueOf(numFacet.getValue()));
    			}
    			else if (facet.getName().getLocalPart().equals("minInclusive")) {
    				Facet numFacet = (Facet)facet.getValue();
    				constraint.setMinInclusive(Float.valueOf(numFacet.getValue()));
    			}
    			else if (facet.getName().getLocalPart().equals("minExclusive")) {
    				Facet numFacet = (Facet)facet.getValue();
    				constraint.setMinExclusive(Float.valueOf(numFacet.getValue()));
    			}
    			else if (facet.getName().getLocalPart().equals("maxExclusive")) {
    				Facet numFacet = (Facet)facet.getValue();
    				constraint.setMaxExclusive(Float.valueOf(numFacet.getValue()));
    			}
    			else if (facet.getName().getLocalPart().equals("totalDigits")) {
    				Facet numFacet = (Facet)facet.getValue();
    				constraint.setTotalDigits(Integer.valueOf(numFacet.getValue()));
    			}
    			else if (facet.getName().getLocalPart().equals("fractionDigits")) {
    				Facet numFacet = (Facet)facet.getValue();
    				constraint.setFractionDigits(Integer.valueOf(numFacet.getValue()));
    			}
    			else if (facet.getName().getLocalPart().equals("maxLength")) {
    				Facet numFacet = (Facet)facet.getValue();
    				constraint.setMaxLength(Integer.valueOf(numFacet.getValue()));
    			}
    			else if (facet.getName().getLocalPart().equals("minLength")) {
    				Facet numFacet = (Facet)facet.getValue();
    				constraint.setMinLength(Integer.valueOf(numFacet.getValue()));
    			}
    		}
    		else if (obj instanceof Pattern) {
    			Pattern pattern = (Pattern)obj;
    			constraint.setPattern(pattern.getValue());
    		}
    	} // for
    	return constraint;
    }
    
}
