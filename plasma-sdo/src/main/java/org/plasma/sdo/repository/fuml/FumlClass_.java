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
package org.plasma.sdo.repository.fuml;

import java.util.ArrayList;
import java.util.List;

import org.modeldriven.fuml.repository.OpaqueBehavior;
import org.plasma.sdo.AssociationPath;
import org.plasma.sdo.repository.Class_;
import org.plasma.sdo.repository.Property;
import org.plasma.sdo.repository.RepositoryException;

class FumlClass_ extends FumlClassifier<org.modeldriven.fuml.repository.Class_> implements Class_ {

	public FumlClass_(org.modeldriven.fuml.repository.Class_ class_) {
		super(class_);
	}
	
    /* (non-Javadoc)
	 * @see org.plasma.sdo.repository.fuml.Classifier#getDeclaredProperties()
	 */
    @Override
	public List<Property> getDeclaredProperties() {
    	List<Property> result = new ArrayList<>();
    	for (org.modeldriven.fuml.repository.Property p : ((org.modeldriven.fuml.repository.Class_)this.element).getDeclaredProperties())
    		result.add(new FumlProperty(p));
    	return result;
    }
    
    /* (non-Javadoc)
	 * @see org.plasma.sdo.repository.fuml.Classifier#getAllProperties()
	 */
    @Override
	public List<Property> getAllProperties() {
    	List<Property> result = new ArrayList<>();
    	for (org.modeldriven.fuml.repository.Property p : ((org.modeldriven.fuml.repository.Class_)this.element).getAllProperties())
    		result.add(new FumlProperty(p));
    	return result;
    }

	/* (non-Javadoc)
	 * @see org.plasma.sdo.repository.fuml.Class_#findOpaqueBehaviorBody(java.lang.String, java.lang.String)
	 */
	@Override
	public String findOpaqueBehaviorBody(String name, String language) {
		return getOpaqueBehaviorBody(name, language, true);
	}
	
	/* (non-Javadoc)
	 * @see org.plasma.sdo.repository.fuml.Class_#getOpaqueBehaviorBody(java.lang.String, java.lang.String)
	 */
	@Override
	public String getOpaqueBehaviorBody(String name, String language) {
		return getOpaqueBehaviorBody(name, language, false);
	}
	
	private String getOpaqueBehaviorBody(String name, String language, 
			boolean supressError) {
		
		String result = null;
		for (OpaqueBehavior behavior : ((org.modeldriven.fuml.repository.Class_)this.element).getOpaqueBehaviors()) {
			if (behavior.getName().equals(name) && behavior.getLanguage().equals(language)) {
				result = behavior.getBody();
			}				
		}
		if (result == null && !supressError)
			throw new RepositoryException("could not find opaque behavior for name: " +
					name + ", language: " + language);
		
		return result;
	}
	
	/* (non-Javadoc)
	 * @see org.plasma.sdo.repository.fuml.Class_#getOpaqueBehaviors(java.lang.String)
	 */
	@Override
	public List<OpaqueBehavior> getOpaqueBehaviors(String language) {
		
		List<OpaqueBehavior> result = new ArrayList<OpaqueBehavior>();
		for (OpaqueBehavior behavior : ((org.modeldriven.fuml.repository.Class_)this.element).getOpaqueBehaviors()) {
			if (behavior.getLanguage().equalsIgnoreCase(language)) {
				result.add(behavior);
			}				
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * @see org.plasma.sdo.repository.fuml.Classifier#isRelation(org.plasma.sdo.repository.fuml.FumlClassifier, org.plasma.sdo.AssociationPath)
	 */
	@Override
	public boolean isRelation(org.plasma.sdo.repository.Class_ other, AssociationPath relation) {
		return FumlRepository.getInstance().getRelationCache().isRelation(
				this, other, relation);
	}

}
