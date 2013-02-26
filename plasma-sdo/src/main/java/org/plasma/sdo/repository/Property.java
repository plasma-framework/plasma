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
package org.plasma.sdo.repository;

import java.util.ArrayList;
import java.util.List;

import org.modeldriven.fuml.repository.Stereotype;
import org.plasma.common.exception.PlasmaRuntimeException;
import org.plasma.sdo.profile.ConcurrencyType;
import org.plasma.sdo.profile.ConcurrentDataFlavor;
import org.plasma.sdo.profile.KeyType;
import org.plasma.sdo.profile.SDOAlias;
import org.plasma.sdo.profile.SDOConcurrent;
import org.plasma.sdo.profile.SDOEnumerationConstraint;
import org.plasma.sdo.profile.SDOKey;
import org.plasma.sdo.profile.SDOSort;
import org.plasma.sdo.profile.SDOTemporal;
import org.plasma.sdo.profile.SDOUniqueConstraint;
import org.plasma.sdo.profile.SDOValueConstraint;
import org.plasma.sdo.profile.SDOValueSetConstraint;
import org.plasma.sdo.profile.SDOXmlProperty;

import fUML.Syntax.Classes.Kernel.VisibilityKind;


public class Property extends Element {

	private org.modeldriven.fuml.repository.Property property;

	@SuppressWarnings("unused")
	private Property() {}
	
	public Property(org.modeldriven.fuml.repository.Property property) {
		super();
		this.property = property;
	}
	
	public String getName() {
		return this.property.getName();
	}
	
	public Object findPropertyDefault() {
		return this.property.findPropertyDefault();
	}
	
    public boolean isMany() {
    	
        return !this.property.isSingular();
    }
    
    public boolean isNullable() {
        return !this.property.isRequired();
    }
	
	public org.modeldriven.fuml.repository.Property getOpposite() {
		return this.property.getOpposite();
	}
	 
	public List<Comment> getComments() {
		List<Comment> result = new ArrayList<Comment>();
		for (fUML.Syntax.Classes.Kernel.Comment comment : property.getDelegate().ownedComment)
			result.add(new Comment(comment));
		return result;	
	}
	
	public boolean isDataType() {
		return this.property.getType().isDataType();
	}
	
	public VisibilityKind getVisibility()
	{
        return this.property.getDelegate().visibility;		
	}	
	
	public boolean getIsReadonly()
	{
		return this.property.getDelegate().isReadOnly;
	}
	
    public String findPhysicalName() 
    {
        List<Stereotype> stereotypes = PlasmaRepository.getInstance().getStereotypes(property);
        validate(stereotypes);
        if (stereotypes != null) {
            for (Stereotype stereotype : stereotypes)
                if (stereotype.getDelegate() instanceof SDOAlias) {
                	SDOAlias sdoAliasStereotype = (SDOAlias)stereotype.getDelegate();
                    if (sdoAliasStereotype.getPhysicalName() != null)
                        return sdoAliasStereotype.getPhysicalName();
                    else    
                    	return null;
                }
        }
        else
            throw new PlasmaRuntimeException("no stereotypes found for property, '"
                + property.getClass_().getName() + "." + property.getName() + "'");
        return null;
    }    

    public String getLocalName() 
    {
        List<Stereotype> stereotypes = PlasmaRepository.getInstance().getStereotypes(property);
        validate(stereotypes);
        if (stereotypes != null) {
            for (Stereotype stereotype : stereotypes)
                if (stereotype.getDelegate() instanceof SDOAlias) {
                	SDOAlias sdoAliasStereotype = (SDOAlias)stereotype.getDelegate();
                    if (sdoAliasStereotype.getLocalName() != null)
                        return sdoAliasStereotype.getLocalName();
                    else    
                        throw new PlasmaRuntimeException("expected 'localName' property for Stereotype, '"
                                + sdoAliasStereotype.name + "'");
                }
        }
        else
            throw new PlasmaRuntimeException("no stereotypes found for property, '"
                + property.getClass_().getName() + "." + property.getName() + "'");
        return null;
    } 
    
    public SDOAlias findAlias() 
    {
        List<Stereotype> stereotypes = PlasmaRepository.getInstance().getStereotypes(property);
        if (stereotypes != null) {
            for (Stereotype stereotype : stereotypes)
                if (stereotype.getDelegate() instanceof SDOAlias) {
                	return (SDOAlias)stereotype.getDelegate();
                }
        }
        return null;
    }        

    public SDOKey findKey() {
        List<Stereotype> stereotypes = PlasmaRepository.getInstance().getStereotypes(property);
        if (stereotypes != null) {
            for (Stereotype stereotype : stereotypes)
                if (stereotype.getDelegate() instanceof SDOKey) {
                	return (SDOKey)stereotype.getDelegate();
                }
        }
        return null;
	}
    
    public SDOConcurrent findConcurrent() {
        List<Stereotype> stereotypes = PlasmaRepository.getInstance().getStereotypes(property);
        if (stereotypes != null) {
            for (Stereotype stereotype : stereotypes)
                if (stereotype.getDelegate() instanceof SDOConcurrent) {
                	return (SDOConcurrent)stereotype.getDelegate();
                }
        }
        return null;
	}
    

    public SDOTemporal findTemporal() {
        List<Stereotype> stereotypes = PlasmaRepository.getInstance().getStereotypes(property);
        if (stereotypes != null) {
            for (Stereotype stereotype : stereotypes)
                if (stereotype.getDelegate() instanceof SDOTemporal) {
                	return (SDOTemporal)stereotype.getDelegate();
                }
        }
        return null;
	}

    public SDOEnumerationConstraint findEnumerationConstraint() {
        List<Stereotype> stereotypes = PlasmaRepository.getInstance().getStereotypes(property);
        if (stereotypes != null) {
            for (Stereotype stereotype : stereotypes)
                if (stereotype.getDelegate() instanceof SDOEnumerationConstraint) {
                	return (SDOEnumerationConstraint)stereotype.getDelegate();
                }
        }
        return null;
	}
    

    public SDOValueSetConstraint findValueSetConstraint() {
        List<Stereotype> stereotypes = PlasmaRepository.getInstance().getStereotypes(property);
        if (stereotypes != null) {
            for (Stereotype stereotype : stereotypes)
                if (stereotype.getDelegate() instanceof SDOValueSetConstraint) {
                	return (SDOValueSetConstraint)stereotype.getDelegate();
                }
        }
        return null;
	}
    
    public SDOValueConstraint findValueConstraint() {
        List<Stereotype> stereotypes = PlasmaRepository.getInstance().getStereotypes(property);
        if (stereotypes != null) {
            for (Stereotype stereotype : stereotypes)
                if (stereotype.getDelegate() instanceof SDOValueConstraint) {
                	return (SDOValueConstraint)stereotype.getDelegate();
                }
        }
        return null;
	}
    
    public SDOSort findSort() {
        List<Stereotype> stereotypes = PlasmaRepository.getInstance().getStereotypes(property);
        if (stereotypes != null) {
            for (Stereotype stereotype : stereotypes)
                if (stereotype.getDelegate() instanceof SDOSort) {
                	return (SDOSort)stereotype.getDelegate();
                }
        }
        return null;
	}
    
    public SDOUniqueConstraint findUniqueConstraint() {
        List<Stereotype> stereotypes = PlasmaRepository.getInstance().getStereotypes(property);
        if (stereotypes != null) {
            for (Stereotype stereotype : stereotypes)
                if (stereotype.getDelegate() instanceof SDOUniqueConstraint) {
                	return (SDOUniqueConstraint)stereotype.getDelegate();
                }
        }
        return null;
	}    
    
    
    public boolean getIsPriKey() 
    {
        if (!property.isDataType())
            throw new IllegalArgumentException("property " 
                    + property.getClass_().getName() + "." + 
                    property.getName() + " is not a datatype property");
        List<Stereotype> stereotypes = PlasmaRepository.getInstance().getStereotypes(property);
        if (stereotypes != null) {
            for (Stereotype stereotype : stereotypes)
                if (stereotype.getDelegate() instanceof SDOKey) {
                	SDOKey sdoKeyStereotype = (SDOKey)stereotype.getDelegate();
                    return sdoKeyStereotype.getType().ordinal() == KeyType.primary.ordinal();
                }
        }
        else
            throw new PlasmaRuntimeException("no stereotypes found for property, '"
                + property.getClass_().getName() + "." + property.getName() + "'");
        return false;
    }    

    public Long getMaxLength() 
    {
        if (!property.isDataType())
            throw new IllegalArgumentException("property " 
                    + property.getClass_().getName() + "." + 
                    property.getName() + " is not a datatype property");
        List<Stereotype> stereotypes = PlasmaRepository.getInstance().getStereotypes(property);
        if (stereotypes != null) {
            for (Stereotype stereotype : stereotypes)
                if (stereotype.getDelegate() instanceof SDOValueConstraint) {
                	SDOValueConstraint sdoValueConstraintStereotype = (SDOValueConstraint)stereotype.getDelegate();
                    if (sdoValueConstraintStereotype.getMaxLength() != null)
                	    return Long.valueOf(sdoValueConstraintStereotype.getMaxLength());
                }
        }
        else
            throw new PlasmaRuntimeException("no stereotypes found for property, '"
                + property.getClass_().getName() + "." + property.getName() + "'");
        return null;
    }    
 
    public Enumeration getRestriction() 
    {
        SDOEnumerationConstraint sdoEnumerationConstraintStereotype = this.findEnumerationConstraint();
        if (sdoEnumerationConstraintStereotype != null) {
        	org.modeldriven.fuml.repository.Enumeration enumeration = (org.modeldriven.fuml.repository.Enumeration)PlasmaRepository.getInstance().getElementById(sdoEnumerationConstraintStereotype.getValue().getXmiId());        	
        	return new Enumeration(enumeration);
        }
        return null;
    }  
    
    public SDOXmlProperty findXmlProperty() {
        List<Stereotype> stereotypes = PlasmaRepository.getInstance().getStereotypes(property);
        if (stereotypes != null) {
            for (Stereotype stereotype : stereotypes)
                if (stereotype.getDelegate() instanceof SDOXmlProperty) {
                	return (SDOXmlProperty)stereotype.getDelegate();
                }
        }
        return null;
	}      

    public boolean getIsConcurrencyUser() 
    {
        List<Stereotype> stereotypes = PlasmaRepository.getInstance().getStereotypes(property);
        if (stereotypes != null) {
            for (Stereotype stereotype : stereotypes)
                if (stereotype.getDelegate() instanceof SDOConcurrent) {
                	SDOConcurrent sdoConcurrentStereotype = (SDOConcurrent)stereotype.getDelegate();
                    return sdoConcurrentStereotype.getType().ordinal() == ConcurrencyType.optimistic.ordinal() &&
                        sdoConcurrentStereotype.getDataFlavor().ordinal() == ConcurrentDataFlavor.user.ordinal();
                }
        }
        return false;
    }    
   
    public boolean getIsConcurrencyVersion() 
    {
        if (!property.isDataType())
            throw new IllegalArgumentException("property " 
                    + property.getClass_().getName() + "." + 
                    property.getName() + " is not a datatype property");
        List<Stereotype> stereotypes = PlasmaRepository.getInstance().getStereotypes(property);
        if (stereotypes != null) {
            for (Stereotype stereotype : stereotypes)
                if (stereotype.getDelegate() instanceof SDOConcurrent) {
                	SDOConcurrent sdoConcurrentStereotype = (SDOConcurrent)stereotype.getDelegate();
                    return sdoConcurrentStereotype.getType().ordinal() == ConcurrencyType.optimistic.ordinal() && 
                        (sdoConcurrentStereotype.getDataFlavor().ordinal() == ConcurrentDataFlavor.version.ordinal() ||
                        sdoConcurrentStereotype.getDataFlavor().ordinal() == ConcurrentDataFlavor.time.ordinal());
                }
        }
        return false;
    }    

    public boolean getIsLockingUser() 
    {
        List<Stereotype> stereotypes = PlasmaRepository.getInstance().getStereotypes(property);
        if (stereotypes != null) {
            for (Stereotype stereotype : stereotypes)
                if (stereotype.getDelegate() instanceof SDOConcurrent) {
                	SDOConcurrent sdoConcurrentStereotype = (SDOConcurrent)stereotype.getDelegate();
                    return sdoConcurrentStereotype.getDataFlavor().ordinal() == ConcurrentDataFlavor.user.ordinal()
                        && sdoConcurrentStereotype.getType().ordinal() == ConcurrencyType.pessimistic.ordinal();
                }
        }
        return false;
    }    
   
    public boolean getIsLockingTimestamp() 
    {
        if (!property.isDataType())
            throw new IllegalArgumentException("property " 
                    + property.getClass_().getName() + "." + 
                    property.getName() + " is not a datatype property");
        List<Stereotype> stereotypes = PlasmaRepository.getInstance().getStereotypes(property);
        if (stereotypes != null) {
            for (Stereotype stereotype : stereotypes)
                if (stereotype.getDelegate() instanceof SDOConcurrent) {
                	SDOConcurrent sdoConcurrentStereotype = (SDOConcurrent)stereotype.getDelegate();
                    return sdoConcurrentStereotype.getDataFlavor().ordinal() == ConcurrentDataFlavor.time.ordinal()
                        && sdoConcurrentStereotype.getType().ordinal() == ConcurrencyType.pessimistic.ordinal();
                }
        }
        return false;
    }    

    public boolean getIsOriginationUser() 
    {
        List<Stereotype> stereotypes = PlasmaRepository.getInstance().getStereotypes(property);
        if (stereotypes != null) {
            for (Stereotype stereotype : stereotypes)
                if (stereotype.getDelegate() instanceof SDOConcurrent) {
                	SDOConcurrent sdoConcurrentStereotype = (SDOConcurrent)stereotype.getDelegate();
                    return sdoConcurrentStereotype.getDataFlavor().ordinal() == ConcurrentDataFlavor.user.ordinal()
                        && sdoConcurrentStereotype.getType().ordinal() == ConcurrencyType.origination.ordinal();
                }
        }
        return false;
    }    
   
    public boolean getIsOriginationTimestamp() 
    {
        if (!property.isDataType())
            throw new IllegalArgumentException("property " 
                    + property.getClass_().getName() + "." + 
                    property.getName() + " is not a datatype property");
        List<Stereotype> stereotypes = PlasmaRepository.getInstance().getStereotypes(property);
        if (stereotypes != null) {
            for (Stereotype stereotype : stereotypes)
                if (stereotype.getDelegate() instanceof SDOConcurrent) {
                	SDOConcurrent sdoConcurrentStereotype = (SDOConcurrent)stereotype.getDelegate();
                    return sdoConcurrentStereotype.getDataFlavor().ordinal() == ConcurrentDataFlavor.time.ordinal()
                        && sdoConcurrentStereotype.getType().ordinal() == ConcurrencyType.origination.ordinal();
                }
        }
        return false;
    }    

    public boolean getIsUnique() 
    {
        List<Stereotype> stereotypes = PlasmaRepository.getInstance().getStereotypes(property);
        if (stereotypes != null) {
            for (Stereotype stereotype : stereotypes)
                if (stereotype.getDelegate() instanceof SDOUniqueConstraint) {
                	//SDOUniqueConstraint sdoUniqueConstraintStereotype = (SDOUniqueConstraint)stereotype.getDelegate();
                    return true;
                }
        }
        else
            throw new PlasmaRuntimeException("no stereotypes found for property, '"
                + property.getName() + "'");
        return false;
    }
    
    private void validate(List<Stereotype> stereotypes) {
    	
    }
   
}
