package org.plasma.sdo.access.provider.jdbc;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.sdo.PlasmaDataGraph;
import org.plasma.sdo.PlasmaDataObject;
import org.plasma.sdo.PlasmaProperty;
import org.plasma.sdo.PlasmaType;
import org.plasma.sdo.access.DataAccessException;
import org.plasma.sdo.access.DataGraphAssembler;
import org.plasma.sdo.access.provider.common.PropertyPair;
import org.plasma.sdo.core.CoreConstants;
import org.plasma.sdo.core.CoreNode;
import org.plasma.sdo.helper.PlasmaDataFactory;
import org.plasma.sdo.profile.KeyType;

import commonj.sdo.DataGraph;
import commonj.sdo.Property;
import commonj.sdo.Type;

public class JDBCDataGraphAssembler extends JDBCDispatcher
    implements DataGraphAssembler {

    private static Log log = LogFactory.getLog(JDBCDataGraphAssembler.class);
	private PlasmaType rootType;
	private PlasmaDataObject root;
	private Map<Type, List<String>> propertyMap;
	private Timestamp snapshotDate;
	private Connection con;
	private JDBCDataConverter converter;
	
	@SuppressWarnings("unused")
	private JDBCDataGraphAssembler() {}
	
	public JDBCDataGraphAssembler(PlasmaType rootType,
			Map<Type, List<String>> propertyMap, Timestamp snapshotDate,
			Connection con) {
		this.rootType = rootType;
		this.propertyMap = propertyMap;
		this.snapshotDate = snapshotDate;
		this.con = con;
		this.converter = JDBCDataConverter.INSTANCE;
	}
	
	public void assemble(List<PropertyPair> pairs) {
		
    	DataGraph dataGraph = PlasmaDataFactory.INSTANCE.createDataGraph();
        //dataGraph.getChangeSummary().beginLogging(); // log changes from this point
    	this.root = (PlasmaDataObject)dataGraph.createRootObject(this.rootType);		
		
		CoreNode rootNode = (CoreNode)this.root;
        // add concurrency fields
        if (snapshotDate != null)
        	rootNode.setValue(CoreConstants.PROPERTY_NAME_SNAPSHOT_TIMESTAMP, snapshotDate);
		
		for (PropertyPair pair : pairs) {
			if (pair.getProp().getType().isDataType()) {
				rootNode.setValue(pair.getProp().getName(), 
						pair.getValue());
			}
		}
		
		// singular reference props
		for (PropertyPair pair : pairs) {
			if (pair.getProp().getType().isDataType())
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
	
	private void assemble(PlasmaType targetType, PlasmaDataObject source, PlasmaProperty sourceProperty, 
			List<PropertyPair> childKeyPairs) {
		List<String> names = this.propertyMap.get(targetType);
		if (log.isDebugEnabled())
			log.debug("assemble: " + targetType.getName() + ": "
					+ names);
		
		StringBuilder query = createSelect(targetType, names, 
				childKeyPairs);
		List<List<PropertyPair>> result = fetch(targetType, query, this.con);
		if (log.isDebugEnabled())
			log.debug("results: " + result.size());
	    
		for (List<PropertyPair> row : result) {
			if (log.isDebugEnabled())
				log.debug("create: " + source.getType().getName() 
						+ "." + sourceProperty.getName());
			PlasmaDataObject target = (PlasmaDataObject)source.createDataObject(sourceProperty);				
			CoreNode targetNode = (CoreNode)target;
	        // add concurrency fields
	        if (snapshotDate != null)
	        	targetNode.setValue(CoreConstants.PROPERTY_NAME_SNAPSHOT_TIMESTAMP, snapshotDate);
			for (PropertyPair pair : row) {
				if (pair.getProp().getType().isDataType()) {
					if (log.isDebugEnabled())
						log.debug("set: (" + pair.getValue() 
								+ ") " + pair.getProp().getContainingType().getName() 
								+ "." + pair.getProp().getName());
					targetNode.setValue(pair.getProp().getName(), pair.getValue());
				}
			}
			// traverse singular results props
			for (PropertyPair pair : row) {
				if (pair.getProp().getType().isDataType()) 
				    continue;
				List<PropertyPair> nextKeyPairs = new ArrayList<PropertyPair>();
				List<Property> nextPkProps = ((PlasmaType)pair.getProp().getType()).findProperties(KeyType.primary);
			    
				// FIXME: need profile link to target PK props where there are multiples !!
				if (nextPkProps.size() == 1) {
			    	nextKeyPairs.add(
			    		new PropertyPair((PlasmaProperty)nextPkProps.get(0),
			    			pair.getValue()));
			    }
				else
					throwPriKeyError(nextPkProps, 
							pair.getProp().getType(), pair.getProp());
								    
				if (log.isDebugEnabled())
					log.debug("traverse: (" + pair.getProp().isMany() 
							+ ") " + pair.getProp().getType().getName() 
							+ "." + pair.getProp().getName());
				assemble((PlasmaType)pair.getProp().getType(), 
						target, pair.getProp(), nextKeyPairs);				
			}
			// traverse multi props
			for (String name : names) {
				PlasmaProperty prop = (PlasmaProperty)targetType.getProperty(name);
				if (prop.isMany() && !prop.getType().isDataType()) {
			    	PlasmaProperty opposite = (PlasmaProperty)prop.getOpposite();
			    	if (opposite == null)
				    	throw new DataAccessException("no opposite property found"
					        + " - cannot map from many property, "
					        + prop.getType() + "." + prop.getName());			    				    	
					List<PropertyPair> childKeyProps = new ArrayList<PropertyPair>();
					List<Property> nextPkProps = ((PlasmaType)targetType).findProperties(KeyType.primary);
				    if (nextPkProps.size() == 1) {
				    	childKeyProps.add(
				    		new PropertyPair(opposite,
				    				target.get(nextPkProps.get(0))));
				    }
				    else
					    throwPriKeyError(nextPkProps, 
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
	}
	
}
