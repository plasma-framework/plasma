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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.sdo.AssociationPath;



/**
 * Caches results for relation path queries 
 */
public class RelationCache {
    private static Log log = LogFactory.getFactory().getInstance(
    		RelationCache.class);
    private Map<String, RelationPathResult> map = new HashMap<String, RelationPathResult>();    
    
	public boolean isRelation(Classifier target, Classifier source, AssociationPath relation) {
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
		                    + "/" + source.getName());
			    synchronized (map) {
			    	this.map.put(key, result);
			    }
			}
			return result.isRelated();
		default:
			//TODO: other cases
			return false;
		}
	}
    
	public void clear() {
		this.map.clear();
	}
	
    private String createHashKey(Classifier targetType, Classifier sourceType) {
    	return targetType.getId() + "/" + sourceType.getId();
    }

    private String createHashKey(org.modeldriven.fuml.repository.Property target, 
    		org.modeldriven.fuml.repository.Property source) {
    	return target.getXmiId() + "/" + source.getXmiId();
    }
    
    /**
     * Returns true if the given target type is related to the given source type
     * through one or more singular reference properties.
     * @param targetType the target type
     * @param sourceType the source type
     * @return true if the given target type is related to the given source type
     * through one or more singular reference properties.
     */
    private boolean isLateralSingularRelation(Classifier targetType, Classifier sourceType, 
    		org.modeldriven.fuml.repository.Property traversalSourceProperty,
    		Map<String, Integer> visited) {
        
        if (log.isDebugEnabled())
            log.debug("comparing "+ targetType.getName() 
                    + "/" + sourceType.getName());
                
        for (org.modeldriven.fuml.repository.Property targetProperty : targetType.getAllProperties()) {
            if (targetProperty.getType().isDataType()) 
                continue; // not a reference       
            
            if (targetProperty.isSingular()) {
            	
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
	                    if (log.isDebugEnabled())
	                        log.debug("exiting - visited "+ targetType.getName() + "."
	                        		+ targetProperty.getName());

	                	return false;
	            	}
            	}
            	
                if (log.isDebugEnabled())
                    log.debug("processing "+ targetType.getName() + "."
                    		+ targetProperty.getName());
                
                // if we've arrived at the target via singular relations
            	if (targetProperty.getType().getXmiId().equals(sourceType.getId())) {
	                if (targetType.getId().equals(sourceType.getId())) {
	                    log.warn("potential circular reference: " + targetType.getNamespaceURI() + "#"+ targetType.getName()
		                        + "." + targetProperty.getName()
		                        + "->" + sourceType.getNamespaceURI() + "#"+ sourceType.getName());
	                }
	                if (log.isDebugEnabled())
	                    log.debug("found child link property " + targetType.getName()
	                        + "." + targetProperty.getName()
	                        + "->" + sourceType.getName());
	                return true; 
            	}
            	else { 
            		// if we've not arrived at the target/root
                    if (!(targetProperty.getType().getXmiId().equals(targetType.getId()))) {
                    		
                        if (log.isDebugEnabled())
                            log.debug("traversing "+ targetType.getName() + "."
                            		+ targetProperty.getName());
                        org.modeldriven.fuml.repository.Classifier targetClassifier = (org.modeldriven.fuml.repository.Classifier)Repository.INSTANCE.getElementById(
                        		targetProperty.getType().getXmiId());
            		    if (isLateralSingularRelation(new Classifier(targetClassifier), 
            		    		sourceType, // Note: stays constant across traversals 
            		    		targetProperty, 
            		    		visited))
            		    	return true;
                    }
            	}
            }    
        }
        
        return false;
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
