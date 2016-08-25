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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.sdo.AssociationPath;
import org.plasma.sdo.repository.Class_;
import org.plasma.sdo.repository.Classifier;
import org.plasma.sdo.repository.Property;
import org.plasma.sdo.repository.RelationCache;
import org.plasma.sdo.repository.RepositoryException;



/**
 * Caches results for relation path queries 
 */
class FumlRelationCache implements RelationCache {
    private static Log log = LogFactory.getFactory().getInstance(
    		FumlRelationCache.class);
    private Map<String, RelationPathResult> map = new HashMap<String, RelationPathResult>();    
    
	/* (non-Javadoc)
	 * @see org.plasma.sdo.repository.fuml.RelationCache#isRelation(org.plasma.sdo.repository.fuml.FumlClassifier, org.plasma.sdo.repository.fuml.FumlClassifier, org.plasma.sdo.AssociationPath)
	 */
	@Override
	public boolean isRelation(Class_ target, Class_ source, AssociationPath relation) {
		switch (relation) {
		case singular:
			String key = createHashKey(target, source);
			RelationPathResult result = this.map.get(key);
			if (result == null) {
			    boolean isRelated = isLateralSingularRelation(target, source, null,
			    		new HashMap<String, Integer>());
			    result = new RelationPathResult(AssociationPath.singular, isRelated);
		        if (log.isDebugEnabled())
		            log.debug("cacheing ("+isRelated+") lateral-singular relation: "+ target.getName() 
		                    + "/" + source.getName() + " ("+key+")");
			    synchronized (map) {
			    	this.map.put(key, result);
			    }
			}
			else {
		        if (log.isDebugEnabled())
		            log.debug("cache hit ("+result.isRelated()+") lateral-singular relation: "+ target.getName() 
		                    + "/" + source.getName() + " ("+key+")");
			}
		    return result.isRelated();
		default:
			//TODO: other cases
			return false;
		}
	}
    
	/* (non-Javadoc)
	 * @see org.plasma.sdo.repository.fuml.RelationCache#clear()
	 */
	@Override
	public void clear() {
		this.map.clear();
	}
	
    private String createHashKey(Classifier targetType, Classifier sourceType) {
    	return targetType.getId() + "/" + sourceType.getId();
    }

    private String createHashKey(Property target, Property source) {
    	return target.getId() + "/" + source.getId();
    }
        
    /**
     * Returns true if the given target type is related to the given source type
     * through one or more singular reference properties.
     * @param targetType the target type
     * @param sourceType the source type
     * @return true if the given target type is related to the given source type
     * through one or more singular reference properties.
     */
    private boolean isLateralSingularRelation(Class_ targetType, Class_ sourceType, 
    		Property traversalSourceProperty,
    		Map<String, Integer> visited) {
        
        if (log.isDebugEnabled())
            log.debug("comparing targetType/sourceType "+ targetType.toString() 
                    + " / " + sourceType.toString());
                
        List<Property> targetProperties = targetType.getAllProperties();
        if (log.isDebugEnabled()) {
            log.debug("checking targetType  "+ targetType.toString() 
                    + " with " + targetProperties.size() + " properties");
        }
        
        for (Property targetProperty : targetProperties) {
            if (log.isDebugEnabled())  
                log.debug("checking "+ targetType.toString() + "." + getDebugName(targetProperty));
            

            if (targetProperty.getType().isDataType()) { 
                if (log.isDebugEnabled()) 
                    log.debug("not a reference property "+ targetType.toString() + "." + getDebugName(targetProperty));
                continue; // not a reference       
            }
            
            // We are traversing BACK-TO the source type, so need to look at the multiplicity of the opposite
            // properties.
            Property targetPropertyOpposite = targetProperty.getOpposite();
            if (targetPropertyOpposite == null) {
                if (log.isDebugEnabled()) 
                    log.debug("no opposite "+ targetType.toString() + "." + getDebugName(targetProperty));
            	continue;
            }
            
            if (targetPropertyOpposite.isMany())
            {
                if (log.isDebugEnabled()) 
                    log.debug("opposite not singular "+ targetType.toString() + "." + getDebugName(targetProperty));
            	continue;
            }                        
            	
            if (log.isDebugEnabled()) {
                log.debug("processing "+ targetType.toString() + "." + getDebugName(targetProperty));
            }

            // Our metadata is not a directed graph so we 
        	// can't bypass traversals based on directionality, so
        	// detect a loop based on source and target properties
        	// And only visit a particular link
        	// ever once.
        	if (traversalSourceProperty != null) {
            	String linkKey = createHashKey(targetProperty, traversalSourceProperty);
            	Integer count = null;
            	if ((count = visited.get(linkKey)) == null)
            		visited.put(linkKey, Integer.valueOf(1));
            	else {	
            		/*
                	org.modeldriven.fuml.repository.Property opposite = targetProperty.getOpposite();
                	if (opposite != null && opposite.isSingular()) {
            		    log.warn("singular property "
                				+ targetType.getName() + "."
                        		+ (targetProperty.getName() == null ? targetProperty.getXmiId() : targetProperty.getName())
                        		+ " has opposite " 
                        		+ opposite.getClass_().getName()+"."
                        		+ (opposite.getName() == null ? opposite.getXmiId() : opposite.getName())
                        		+ " which is also singular");
                	}
                	*/
                    if (log.isDebugEnabled()) {
                        log.debug("exiting - visited  "+ targetType.toString() + "." + getDebugName(targetProperty));
                    }

                	return false;
            	}
        	}
        	
            
            if (log.isDebugEnabled()) {
                log.debug("comparing " + targetType.toString()
                        + "." + getDebugName(targetProperty) + " ("+targetProperty.getType().getId()+")"
                        + "->" + sourceType.toString() + " ("+sourceType.getId()+")");
            }
            // if we've arrived at the target via singular relations
        	if (targetProperty.getType().getId().equals(sourceType.getId())) {
                if (targetType.getId().equals(sourceType.getId())) {
                    log.warn("potential circular reference: " + targetType.toString()
	                        + "." + getDebugName(targetProperty)
	                        + "->" + sourceType.toString());
                }
                if (log.isDebugEnabled())
                    log.debug("found child link property " + targetType.toString()
                        + "." + getDebugName(targetProperty)
                        + "->" + sourceType.toString());
                return true; 
        	}
        	else { 
        		// if we've not arrived at the target/root
                if (!(targetProperty.getType().getId().equals(targetType.getId()))) {
                		
                    if (log.isDebugEnabled())
                        log.debug("traversing "+ targetType.toString() + "."
                        		+ getDebugName(targetProperty));
                    //FIXME: classifiers returned via XMI ID have no properties !!
                    // See re-lookup by qualified name below
                    org.plasma.sdo.repository.Classifier tempClassifier = FumlRepository.getFumlRepositoryInstance().getClassifierById(
                    		targetProperty.getType().getId());
                    
                    String urn = tempClassifier.getArtifactNamespaceURI();
                    String qualifiedName = urn + "#" + tempClassifier.getName();
                    org.plasma.sdo.repository.Classifier targetClassifier = FumlRepository.getFumlRepositoryInstance().getClassifier(
                    		qualifiedName);
                    org.plasma.sdo.repository.Class_ nextClassifier = null;
                    if (org.plasma.sdo.repository.Class_.class.isAssignableFrom(targetClassifier.getClass()))        	
                    	nextClassifier = (org.plasma.sdo.repository.Class_)targetClassifier;
                    else
                    	throw new RepositoryException("expected classifier assignable to class");
                    
        		    if (isLateralSingularRelation(nextClassifier, 
        		    		sourceType, // Note: stays constant across traversals 
        		    		targetProperty, 
        		    		visited))
        		    	return true;
                }
        	}
        }
        
        return false;
    }
    
    /**
     * UML properties are not required to be named, so at the repository level
     * for debugging, construct some recognizable name. 
     * @param prop the property
     * @return the debug name
     */
    private String getDebugName(Property prop) {
    	if (prop.getName() != null && prop.getName().trim().length() > 0) {
    		return prop.getName();
    	}
    	else {
    		return prop.getType().getName();
    	}
    }
    
    class RelationPathResult {
        private AssociationPath relationPath;
        private boolean related;
        @SuppressWarnings("unused")
    	private RelationPathResult() {}
    	public RelationPathResult(AssociationPath relationPath, boolean related) {
    		super();
    		this.relationPath = relationPath;
    		this.related = related;
    	}
    	public AssociationPath getRelationPath() {
    		return relationPath;
    	}
    	public boolean isRelated() {
    		return related;
    	}    
    }    
}
