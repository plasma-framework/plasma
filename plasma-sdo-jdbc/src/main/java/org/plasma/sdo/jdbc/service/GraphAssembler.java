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
package org.plasma.sdo.jdbc.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.query.collector.PropertySelectionCollector;
import org.plasma.query.model.Where;
import org.plasma.sdo.PlasmaDataGraph;
import org.plasma.sdo.PlasmaDataObject;
import org.plasma.sdo.PlasmaProperty;
import org.plasma.sdo.PlasmaType;
import org.plasma.sdo.access.DataAccessException;
import org.plasma.sdo.access.DataGraphAssembler;
import org.plasma.sdo.access.provider.common.PropertyPair;
import org.plasma.sdo.access.provider.jdbc.AliasMap;
import org.plasma.sdo.core.CoreConstants;
import org.plasma.sdo.core.CoreNode;
import org.plasma.sdo.core.TraversalDirection;
import org.plasma.sdo.helper.PlasmaDataFactory;
import org.plasma.sdo.jdbc.filter.FilterAssembler;
import org.plasma.sdo.profile.KeyType;

import commonj.sdo.DataGraph;
import commonj.sdo.DataObject;
import commonj.sdo.Property;
import commonj.sdo.Type;

public class GraphAssembler extends JDBCSupport
    implements DataGraphAssembler {

    private static Log log = LogFactory.getLog(GraphAssembler.class);
	private PlasmaType rootType;
	private PlasmaDataObject root;
	private Map<Type, List<String>> propertyMap;
    private Map<commonj.sdo.Property, Where> predicateMap; 
	private Timestamp snapshotDate;
	private Connection con;
	private RDBDataConverter converter;
	private Map<Integer, PlasmaDataObject> dataObjectMap = new HashMap<Integer, PlasmaDataObject>();
	private Comparator<PropertyPair> nameComparator;
	
	@SuppressWarnings("unused")
	private GraphAssembler() {}
	
	public GraphAssembler(PlasmaType rootType,
			PropertySelectionCollector collector, Timestamp snapshotDate,
			Connection con) {
		this.rootType = rootType;
		this.propertyMap = collector.getResult();
		this.predicateMap = collector.getPredicateMap();
		this.snapshotDate = snapshotDate;
		this.con = con;
		this.converter = RDBDataConverter.INSTANCE;
		
		this.nameComparator = new Comparator<PropertyPair>() {
			public int compare(PropertyPair o1, PropertyPair o2) {				
				return o1.getProp().getName().compareTo(
						o2.getProp().getName());
			}
		};		
	}
	
	/**
	 * Initiates the assembly of a data graph based on the 
	 * given results list. 
	 * @param results the results list
	 * 
	 * @see DataGraphAssembler.getDataGraph()
	 */
	public void assemble(List<PropertyPair> results) {
		
    	DataGraph dataGraph = PlasmaDataFactory.INSTANCE.createDataGraph();
    	this.root = (PlasmaDataObject)dataGraph.createRootObject(this.rootType);		
		if (log.isDebugEnabled())
			log.debug("assembling root: " 
		        + this.root.getType().getName());
		
		CoreNode rootNode = (CoreNode)this.root;
        // add concurrency fields
        if (snapshotDate != null)
        	rootNode.setValue(CoreConstants.PROPERTY_NAME_SNAPSHOT_TIMESTAMP, snapshotDate);
		// set data properties
		for (PropertyPair pair : results) {
			if (pair.getProp().getType().isDataType()) {
				rootNode.setValue(pair.getProp().getName(), 
						pair.getValue());
			}
		}
		
		// singular reference props
		for (PropertyPair pair : results) {
			if (pair.getProp().isMany() || pair.getProp().getType().isDataType())
			    continue;
			List<PropertyPair> childKeyProps = new ArrayList<PropertyPair>();
			List<Property> childPkProps = ((PlasmaType)pair.getProp().getType()).findProperties(KeyType.primary);
		    if (childPkProps.size() == 1) {
		    	childKeyProps.add(
		    		new PropertyPair((PlasmaProperty)childPkProps.get(0),
		    			pair.getValue()));
		    }
		    else
			    throwPriKeyError(childPkProps, 
			    		pair.getProp().getType(), pair.getProp());
		    assemble((PlasmaType)pair.getProp().getType(), 
				(PlasmaDataObject)this.root, pair.getProp(),
				childKeyProps);
		    
		}
		
		// multi reference props (not found in results)
		List<String> names = this.propertyMap.get(this.rootType);
		for (String name : names) {
			PlasmaProperty prop = (PlasmaProperty)rootType.getProperty(name);
			if (prop.isMany() && !prop.getType().isDataType()) {
		    	PlasmaProperty opposite = (PlasmaProperty)prop.getOpposite();
		    	if (opposite == null)
			    	throw new DataAccessException("no opposite property found"
				        + " - cannot map from many property, "
				        + prop.getType() + "." + prop.getName());			    				    	
				List<PropertyPair> childKeyProps = new ArrayList<PropertyPair>();
				List<Property> rootPkProps = ((PlasmaType)root.getType()).findProperties(KeyType.primary);
			    if (rootPkProps.size() == 1) {
			    	childKeyProps.add(
			    		new PropertyPair(opposite,
			    				root.get(rootPkProps.get(0))));
			    }
			    else
				    throwPriKeyError(rootPkProps, 
				    		root.getType(), prop);
			    assemble((PlasmaType)prop.getType(), 
						(PlasmaDataObject)this.root, prop,
						childKeyProps);
			}
		}
	}
	
	/**
	 * 
	 * @param targetType
	 * @param source
	 * @param sourceProperty
	 * @param childKeyPairs
	 */
	private void assemble(PlasmaType targetType, PlasmaDataObject source, PlasmaProperty sourceProperty, 
			List<PropertyPair> childKeyPairs) {
		List<String> names = this.propertyMap.get(targetType);
		if (log.isDebugEnabled())
			log.debug("assemble: " + source.getType().getName() 
					+ "." + sourceProperty.getName() + ": "
					+ names);
		
		List<List<PropertyPair>> result = null;
		Where where = this.predicateMap.get(sourceProperty);
		if (where == null) { 
			try {
			    StringBuilder query = createSelect(targetType, names, childKeyPairs);
			    result = fetch(targetType, query, this.con);
			}
			catch (SQLException e) {
				throw new DataAccessException(e);
			}
		}
		else {
	        AliasMap aliasMap = new AliasMap(targetType);
			FilterAssembler filterAssembler = new FilterAssembler(where, 
					targetType, aliasMap);	
			try {
				List<Object> params = new ArrayList<Object>();	
				StringBuilder query = createSelect(targetType,
			    	names, childKeyPairs, filterAssembler, params, aliasMap);
				Object[] paramArray = new Object[params.size()];
				params.toArray(paramArray);
				
				result = fetch(targetType, query, paramArray,
					this.con);
			}
			catch (SQLException e) {
				throw new DataAccessException(e);
			}
		}		
		
		if (log.isDebugEnabled())
			log.debug("results: " + result.size());
	    
		// first create (or link existing) data objects 
		// "filling out" the containment hierarchy at this traversal level
		// BEFORE recursing, as we may "cancel" out an object
		// at the current level if it is first encountered
		// within the recursion.
		Map<PlasmaDataObject, List<PropertyPair>> resultMap = new HashMap<PlasmaDataObject, List<PropertyPair>>();
		for (List<PropertyPair> row : result) {
			
			PlasmaDataObject target = findDataObject(targetType, row);
			// if no existing data-object in graph
			if (target == null) {
			    target = createDataObject(row, source, 
					sourceProperty);
			    resultMap.put(target, row);
			}
			else { 
				link(target, source, sourceProperty);
				continue; 
				// Assume we traverse no farther given no traversal
				// direction or containment info. We only know that we 
				// encountered an existing node. Need more path specific 
				// info including containment and traversal direction to construct
				// a directed graph here. 
				// Since the current selection collector maps any and all
				// properties selected to a type, for each type/data-object
				// we will, at this point, have gotten all the properties we expect anyway.
				// So we create a link from the source to the existing DO, but
				// traverse no further. 
			}   
		}	
		
		// now traverse
		Iterator<PlasmaDataObject> iter = resultMap.keySet().iterator();
		while (iter.hasNext()) {
			PlasmaDataObject target = iter.next();
			List<PropertyPair> row = resultMap.get(target);
			// traverse singular results props
			for (PropertyPair pair : row) {
				if (pair.getProp().isMany() || pair.getProp().getType().isDataType()) 
				    continue; // only singular reference props
				List<PropertyPair> nextKeyPairs = new ArrayList<PropertyPair>();
				List<Property> nextKeyProps = ((PlasmaType)pair.getProp().getType()).findProperties(KeyType.primary);
			    			
				// FIXME: need UML profile link to target PK props 
				// where there are multiple PKs !!
				if (nextKeyProps.size() == 1) {
			    	nextKeyPairs.add(
			    		new PropertyPair((PlasmaProperty)nextKeyProps.get(0),
			    			pair.getValue()));
			    }
				else
					throwPriKeyError(nextKeyProps, 
							pair.getProp().getType(), pair.getProp());
								    
				if (log.isDebugEnabled())
					log.debug("traverse: (" + pair.getProp().isMany() 
							+ ") " + pair.getProp().getType().getName() 
							+ "." + pair.getProp().getName());
				assemble((PlasmaType)pair.getProp().getType(), 
						target, pair.getProp(), nextKeyPairs);				
			}
			
			// traverse multi props based, not on the results
			// row, but on keys within this data object
			for (String name : names) {
				PlasmaProperty prop = (PlasmaProperty)targetType.getProperty(name);
				if (!prop.isMany() || prop.getType().isDataType())
				    continue; // only many reference props
				
		    	PlasmaProperty opposite = (PlasmaProperty)prop.getOpposite();
		    	if (opposite == null)
			    	throw new DataAccessException("no opposite property found"
				        + " - cannot map from many property, "
				        + prop.getType() + "." + prop.getName());			    				    	
				List<PropertyPair> childKeyProps = new ArrayList<PropertyPair>();
				List<Property> nextKeyProps = ((PlasmaType)targetType).findProperties(KeyType.primary);
			    if (nextKeyProps.size() == 1) {
			    	childKeyProps.add(
			    		new PropertyPair(opposite,
			    				target.get(nextKeyProps.get(0))));
			    }
			    else
				    throwPriKeyError(nextKeyProps, 
				    		targetType, prop);
				if (log.isDebugEnabled())
					log.debug("traverse: (" + prop.isMany() 
							+ ") " + target.getType().getName() 
							+ "." + prop.getName());
			    assemble((PlasmaType)prop.getType(), 
			    		target, prop,
						childKeyProps);
			}				
		}
	}
	
	/**
	 * Creates a new data object contained by the given source
	 * data object and source property.
	 * @param row the results row
	 * @param source the source data object
	 * @param sourceProperty the source containment property
	 * @return the new data object
	 */
	private PlasmaDataObject createDataObject(List<PropertyPair> row,
			PlasmaDataObject source, PlasmaProperty sourceProperty) {
		PlasmaDataObject target = (PlasmaDataObject)source.createDataObject(sourceProperty);				
		if (log.isDebugEnabled())
			log.debug("create: " + source.getType().getName() 
					+ "." + sourceProperty.getName()
					+ "->" + target.getType().getName());
		CoreNode node = (CoreNode)target;
        
		// add concurrency fields
        if (snapshotDate != null)
        	node.setValue(CoreConstants.PROPERTY_NAME_SNAPSHOT_TIMESTAMP, snapshotDate);
	    
        // set data properties bypassing SDO "setter" API
        // to avoid triggering read-only property error 
        for (PropertyPair pair : row) {
			if (pair.getProp().getType().isDataType()) {
				if (log.isDebugEnabled())
					log.debug("set: (" + pair.getValue() 
							+ ") " + pair.getProp().getContainingType().getName() 
							+ "." + pair.getProp().getName());
				node.setValue(pair.getProp().getName(), pair.getValue());
			}
		}
        
        // map it
        int key = createHashKey(
        	(PlasmaType)target.getType(), row);
        if (log.isDebugEnabled())
        	log.debug("mapping " + key + "->" + target);
        this.dataObjectMap.put(key, target);        
        
        return target;
	}

	/**
	 * Finds and returns an existing data object based on hte
	 * given results row which is part
	 * if this assembly unit, or returns null if not exists
	 * @param type the target type
	 * @param row the results row
	 * @return the data object
	 */
	private PlasmaDataObject findDataObject(PlasmaType type, List<PropertyPair> row) {
        int key = createHashKey(type, row);
        PlasmaDataObject result = this.dataObjectMap.get(key);
        if (log.isDebugEnabled()) {
            if (result != null)
            	log.debug("found existing mapping " + key + "->" + result);
            else
            	log.debug("found no existing mapping for key: " + key);
        }	
        return result;        
	}
	
	/**
	 * Creates a unique mappable key using the qualified type name
	 * and all key property values from the given row.
	 * @param type the type
	 * @param row the data values
	 * @return the key
	 */
	private int createHashKey(PlasmaType type, List<PropertyPair> row) {
		PropertyPair[] pairs = new PropertyPair[row.size()];
		row.toArray(pairs);
		Arrays.sort(pairs, this.nameComparator);
		int result = type.getQualifiedName().hashCode();
		
		int pks = 0;
		for (int i = 0; i < pairs.length; i++) {
			if (pairs[i].getProp().isKey(KeyType.primary)) {
				Object value = pairs[i].getValue();
				result = result ^ value.hashCode();
				pks++;
			}
		}
		if (pks == 0)
			throw new IllegalStateException("cannot create hash key - no primary keys found for type, "
					+ type.toString());
		return result;
	}
	
	/**
	 * Creates a directed (link) between the given
	 * source and target data objects. The reference is
	 * created as a containment reference only if the given target
	 * has no container.
	 * @param target the data object which is the target 
	 * @param source the source data object
	 * @param sourceProperty the source property
	 * 
	 * @see TraversalDirection
	 */
    private void link(PlasmaDataObject target, PlasmaDataObject source, PlasmaProperty sourceProperty)
    {      
        if (log.isDebugEnabled())
            log.debug("linking source (" + source.getUUIDAsString() + ") "
                    + source.getType().getURI() + "#" + source.getType().getName() 
                    + "." + sourceProperty.getName() + "->("
                    + target.getUUIDAsString() + ") "
                    + target.getType().getURI() + "#" + target.getType().getName());
        
        if (sourceProperty.isMany()) {
        	
        	PlasmaProperty opposite = (PlasmaProperty)sourceProperty.getOpposite();
        	if (opposite != null && !opposite.isMany() && target.isSet(opposite)) {
                PlasmaDataObject existingOpposite = (PlasmaDataObject)target.get(opposite);
                if (existingOpposite != null) {
                	if (log.isDebugEnabled())
                        log.debug("encountered existing opposite (" + existingOpposite.getType().getName()
                            + ") value found while creating link (" + source.getUUIDAsString() + ") "
                            + source.getType().getURI() + "#" + source.getType().getName() 
                            + "." + sourceProperty.getName() + "->("
                            + target.getUUIDAsString() + ") "
                            + target.getType().getURI() + "#" + target.getType().getName() + " - no link created");
        		    return;
                }
        	}
            @SuppressWarnings("unchecked")
			List<DataObject> list = source.getList(sourceProperty);
            if (list == null) 
                list = new ArrayList<DataObject>();
            if (log.isDebugEnabled()) {
                for (DataObject existingObject : list) {
                    log.debug("existing: (" + 
                            ((org.plasma.sdo.PlasmaNode)existingObject).getUUIDAsString()
                            + ") " + existingObject.getType().getURI() + "#" + existingObject.getType().getName());
                }
            } 
            if (!list.contains(target)) {
                if (log.isDebugEnabled())
                    log.debug("adding target  (" + source.getUUIDAsString() + ") "
                        + source.getType().getURI() + "#" + source.getType().getName() 
                        + "." + sourceProperty.getName() + "->(" + target.getUUIDAsString() + ") "
                        + target.getType().getURI() + "#" + target.getType().getName());
                if (target.getContainer() == null) {
                    target.setContainer(source);
                    target.setContainmentProperty(sourceProperty);
                }
                list.add(target);                
                source.setList(sourceProperty, list); 
                // FIXME: replaces existing list according to SDO spec (memory churn)
                // store some temp instance-property list on DO and only set using SDO
                // API on completion of graph. 
            }
        }
        else {
            // Selection map keys are paths from the root entity and
            // elements in the path are often repeated. Expect repeated 
        	// events for repeated path elements, which
            // may be useful for some implementations, but not this one. So
            // we screen these out here. 
            PlasmaDataObject existing = (PlasmaDataObject)source.get(sourceProperty);
            if (existing == null) {
                source.set(sourceProperty, target); 
                // While the SDO spec seems to indicate (see 3.1.6 Containment) that
                // a Type may have only 1 reference property which a containment 
                // property, this seems too inflexible given the almost infinite
                // number of ways a graph could be constructed. We therefore allow any reference
                // property to be a containment property, and let the graph assembly
                // order determine which properties are containment properties for a particular
                // graph result. The SDO spec is crystal clear that every Data Object
                // other than the root, must have one-and-only-one container. We set the container
                // here as well as the specific reference property that currently is
                // the containment property, based on graph traversal order. Note it would be
                // possible to specify exactly which property is containment in a
                // query specification. We set no indication of containment on the 
                // (source) container object because all reference properties are 
                // potentially containment properties.  
                if (target.getContainer() == null) {
                    target.setContainer(source);
                    target.setContainmentProperty(sourceProperty);
                }
            }
            else
                if (!existing.getUUIDAsString().equals(target.getUUIDAsString()))
                	if (log.isDebugEnabled())
                        log.debug("encountered existing (" + existing.getType().getName()
	                        + ") value found while creating link (" + source.getUUIDAsString() + ") "
	                        + source.getType().getURI() + "#" + source.getType().getName() 
	                        + "." + sourceProperty.getName() + "->("
	                        + target.getUUIDAsString() + ") "
	                        + target.getType().getURI() + "#" + target.getType().getName());
        }
    }
	
	private void throwPriKeyError(List<Property> rootPkProps, 
			Type type, Property prop) {
		if (prop.isMany())
		    if (rootPkProps.size() == 0)
		    	throw new DataAccessException("no pri-keys found for "
			        + type.getURI() + "#" + type.getName()
			        + " - cannot map from many property, "
			        + prop.getType() + "." + prop.getName());			    	
		    else	
		    	throw new DataAccessException("multiple pri-keys found for "
		    		+ type.getURI() + "#" + type.getName()
		            + " - cannot map from many property, "
		            + prop.getType() + "." + prop.getName());
		else
		    if (rootPkProps.size() == 0)
		    	throw new DataAccessException("no pri-keys found for "
			    	+ type.getURI() + "#" + type.getName()
			        + " - cannot map from singular property, "
			        + prop.getType() + "." + prop.getName());			    	
		    else	
		    	throw new DataAccessException("multiple pri-keys found for "
		    		+ type.getURI() + "#" + type.getName()
		            + " - cannot map from singular property, "
		            + prop.getType() + "." + prop.getName());			    					
	}
	 
	public PlasmaDataGraph getDataGraph() {
		return (PlasmaDataGraph)this.root.getDataGraph();
	}
	 
	public void clear() {
		this.root = null;
		this.dataObjectMap.clear();
	}
	
}
