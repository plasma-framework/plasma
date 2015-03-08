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
package org.plasma.query.collector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.plasma.query.InvalidPathPredicateException;
import org.plasma.query.QueryException;
import org.plasma.query.model.AbstractPathElement;
import org.plasma.query.model.AbstractProperty;
import org.plasma.query.model.Function;
import org.plasma.query.model.OrderBy;
import org.plasma.query.model.Path;
import org.plasma.query.model.PathElement;
import org.plasma.query.model.PathNode;
import org.plasma.query.model.Property;
import org.plasma.query.model.Select;
import org.plasma.query.model.Where;
import org.plasma.query.model.WildcardPathElement;
import org.plasma.query.model.WildcardProperty;
import org.plasma.query.visitor.DefaultQueryVisitor;
import org.plasma.query.visitor.QueryVisitor;

import commonj.sdo.Type;

/**
 * Traverses a given {@link Select) clause collecting the specified property
 * names, as well as any path-reference or key properties required for graph
 * structure, into a simple list of logical property name strings mapped to the
 * respective {@link Type} definition.
 * 
 * @see org.plasma.query.model.Select
 * @see commonj.sdo.Type
 */
public class SelectionCollector extends CollectorSupport implements Selection {
	private static Set<commonj.sdo.Property> EMPTY_PROPERTY_SET = new HashSet<commonj.sdo.Property>();
	private static List<Function> EMPTY_FUNCTION_LIST = new ArrayList<Function>();
	private Select select;
	private Where where;
	private OrderBy orderBy;
	
	// FIXME: what to do with repeated/multiple predicates
	private Map<commonj.sdo.Property, Where> predicateMap;
	private Map<commonj.sdo.Property, List<Function>> functionMap;
	private Map<Type, Set<commonj.sdo.Property>> propertyMap;
	private Map<Type, Set<commonj.sdo.Property>> singularPropertyMap;
	private Map<Type, Set<commonj.sdo.Property>> inheritedPropertyMap;

	private Map<commonj.sdo.Property, Map<Integer, Where>> predicateLevelMap;
	private Map<commonj.sdo.Property, Map<Integer, List<Function>>> functionLevelMap;
	private Map<Type, Map<Integer, Set<commonj.sdo.Property>>> propertyLevelMap;
	private Map<Type, Map<Integer, Set<commonj.sdo.Property>>> singularPropertyLevelMap;
	private Map<Type, Map<Integer, Set<commonj.sdo.Property>>> inheritedPropertyLevelMap;

	private Map<commonj.sdo.Property, Map<commonj.sdo.Property, Where>> predicateEdgeMap;
	private Map<Type, Map<commonj.sdo.Property, Set<commonj.sdo.Property>>> propertyEdgeMap;
	private Map<Type, Map<commonj.sdo.Property, Set<commonj.sdo.Property>>> singularPropertyEdgeMap;
	private Map<Type, Map<commonj.sdo.Property, Set<commonj.sdo.Property>>> inheritedPropertyEdgeMap;

	public SelectionCollector(Select select, Type rootType) {
		super(rootType);
		this.select = select;
	}

	public SelectionCollector(Select select, Type rootType,
			boolean onlySingularProperties) {
		super(rootType, onlySingularProperties);
		this.select = select;
	}
	
	public SelectionCollector(Select select, Where where, Type rootType) {
		super(rootType);
		this.select = select;
		this.where = where;
	}

	public SelectionCollector(Select select, Where where, Type rootType,
			boolean onlySingularProperties) {
		super(rootType, onlySingularProperties);
		this.select = select;
		this.where = where;
	}
	
	public SelectionCollector(Select select, Where where, OrderBy orderBy, Type rootType) {
		super(rootType);
		this.select = select;
		this.where = where;
		this.orderBy = orderBy;
	}

	public SelectionCollector(Select select, Where where, OrderBy orderBy, Type rootType,
			boolean onlySingularProperties) {
		super(rootType, onlySingularProperties);
		this.select = select;
		this.where = where;
		this.orderBy = orderBy;
	}	
	
	public SelectionCollector(Where where, Type rootType) {
		super(rootType);
		this.where = where;
	}

	public SelectionCollector(Where where, Type rootType,
			boolean onlySingularProperties) {
		super(rootType, onlySingularProperties);
		this.where = where;
	}
	
	private boolean initialized() {
		return this.propertyMap != null;
	}

	private void init() {
		if (!initialized()) {
			this.propertyMap = new HashMap<Type, Set<commonj.sdo.Property>>();
			this.singularPropertyMap = new HashMap<Type, Set<commonj.sdo.Property>>();
			this.inheritedPropertyMap = new HashMap<Type, Set<commonj.sdo.Property>>();
			this.predicateMap = new HashMap<commonj.sdo.Property, Where>();
			this.functionMap = new HashMap<commonj.sdo.Property, List<Function>>();

			this.propertyLevelMap = new HashMap<Type, Map<Integer, Set<commonj.sdo.Property>>>();
			this.singularPropertyLevelMap = new HashMap<Type, Map<Integer, Set<commonj.sdo.Property>>>();
			this.inheritedPropertyLevelMap = new HashMap<Type, Map<Integer, Set<commonj.sdo.Property>>>();
			this.predicateLevelMap = new HashMap<commonj.sdo.Property, Map<Integer, Where>>();
			this.functionLevelMap = new HashMap<commonj.sdo.Property, Map<Integer, List<Function>>>();

			this.propertyEdgeMap = new HashMap<Type, Map<commonj.sdo.Property, Set<commonj.sdo.Property>>>();
			this.singularPropertyEdgeMap = new HashMap<Type, Map<commonj.sdo.Property, Set<commonj.sdo.Property>>>();
			this.inheritedPropertyEdgeMap = new HashMap<Type, Map<commonj.sdo.Property, Set<commonj.sdo.Property>>>();
			this.predicateEdgeMap = new HashMap<commonj.sdo.Property, Map<commonj.sdo.Property, Where>>();
			
			if (this.select != null) {
				QueryVisitor visitor = new DefaultQueryVisitor() {
					@Override
					public void start(Property property) {
						collect(property);
						super.start(property);
					}
	
					@Override
					public void start(WildcardProperty wildcardProperty) {
						collect(wildcardProperty);
						super.start(wildcardProperty);
					}
				};
				this.select.accept(visitor);
			}
			if (this.where != null) {
				QueryVisitor visitor = new DefaultQueryVisitor() {
					@Override
					public void start(Property property) {
						collect(property);
						super.start(property);
					}
	
					@Override
					public void start(WildcardProperty wildcardProperty) {
						collect(wildcardProperty);
						super.start(wildcardProperty);
					}
				};
				this.where.accept(visitor);
			}
			if (this.orderBy != null) {
				QueryVisitor visitor = new DefaultQueryVisitor() {
					@Override
					public void start(Property property) {
						collect(property);
						super.start(property);
					}
	
					@Override
					public void start(WildcardProperty wildcardProperty) {
						collect(wildcardProperty);
						super.start(wildcardProperty);
					}
				};
				this.orderBy.accept(visitor);
			}
		}
	}
	
	@Deprecated
	public Map<commonj.sdo.Property, Where> getPredicateMap() {
		if (!initialized())
		    init();
		return predicateMap;
	}

	/**
	 * (non-Javadoc)
	 */
	public boolean hasPredicates() {
		if (!initialized())
		    init();
		if (predicateMap == null)
			return false;
		return predicateMap.size() > 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.plasma.query.collector.PropertySelection#getPredicate(commonj.sdo
	 * .Property)
	 */
	@Override
	public Where getPredicate(commonj.sdo.Property property) {
		if (!initialized())
			init();
		if (this.predicateMap != null)
			return this.predicateMap.get(property);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.plasma.query.collector.PropertySelection#getSingularProperties(commonj
	 * .sdo.Type)
	 */
	@Override
	public Set<commonj.sdo.Property> getSingularProperties(Type type) {
		if (!initialized())
			init();
		Set<commonj.sdo.Property> result = this.singularPropertyMap.get(type);
		if (result != null)
			return result;
		return EMPTY_PROPERTY_SET;
	}

	/*
	 * (non-Javadoc)
	 * 
	 */
	@Override
	public Set<commonj.sdo.Property> getProperties(Type type) {
		if (!initialized())
			init();
		Set<commonj.sdo.Property> result = propertyMap.get(type);
		if (result != null)
			return result;
		return EMPTY_PROPERTY_SET;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.plasma.query.collector.Selection#getInheritedProperties(Type type)
	 */
	@Override
	public Set<commonj.sdo.Property> getInheritedProperties(Type type) {
		if (!initialized())
			init();
		Set<commonj.sdo.Property> result = inheritedPropertyMap.get(type);
		if (result != null)
			return result;
		return EMPTY_PROPERTY_SET;
	}
	
	@Override
	public Set<commonj.sdo.Property> getInheritedProperties(Type type,
			commonj.sdo.Property sourceProperty) {
		if (!initialized())
			init();
		Map<commonj.sdo.Property, Set<commonj.sdo.Property>> edgeMap = this.inheritedPropertyEdgeMap.get(type);
		if (edgeMap != null) {
		    Set<commonj.sdo.Property> set = edgeMap.get(sourceProperty);
		    if (set != null)
		    	return set;
		}				 
		return EMPTY_PROPERTY_SET;
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.plasma.query.collector.Selection#getInheritedProperties(Type type, int level)
	 */
	@Override
	public Set<commonj.sdo.Property> getInheritedProperties(Type type, int level) {
		if (!initialized())
			init();
		Map<Integer, Set<commonj.sdo.Property>> levelMap = this.inheritedPropertyLevelMap.get(type);
		if (levelMap != null) {
		    Set<commonj.sdo.Property> set = levelMap.get(level);
		    if (set != null)
		    	return set;
		}				 
		return EMPTY_PROPERTY_SET;
	}

	@Override
	public Set<commonj.sdo.Property> getInheritedProperties(Type type,
			commonj.sdo.Property sourceProperty, int level) {
		Set<commonj.sdo.Property> result = new HashSet<commonj.sdo.Property>();
		Set<commonj.sdo.Property> levelProps = getInheritedProperties(type, level);
		Set<commonj.sdo.Property> edgeProps = getInheritedProperties(type, sourceProperty);		
		for (commonj.sdo.Property p : levelProps) {
			if (edgeProps.contains(p))
				result.add(p);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.plasma.query.collector.Selection#getTypes()
	 */
	@Override
	public List<Type> getTypes() {
		if (!initialized())
			init();
		List<Type> result = new ArrayList<Type>();
		result.addAll(this.propertyMap.keySet());
		result.addAll(this.inheritedPropertyMap.keySet());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.plasma.query.collector.Selection#hasType(commonj.sdo.Type)
	 */
	@Override
	public boolean hasType(Type type) {
		if (!initialized())
			init();
		return this.propertyMap.get(type) != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.plasma.query.collector.PropertySelection#getInheritedTypes(commonj
	 * .sdo.Type)
	 */
	@Override
	public List<Type> getInheritedTypes() {
		if (!initialized())
			init();
		List<Type> result = new ArrayList<Type>();
		result.addAll(this.inheritedPropertyMap.keySet());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.plasma.query.collector.PropertySelection#hasInheritedType(commonj
	 * .sdo.Type)
	 */
	@Override
	public boolean hasInheritedType(Type type) {
		if (!initialized())
			init();
		return this.inheritedPropertyMap.get(type) != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.plasma.query.collector.PropertySelection#hasProperty(commonj.sdo.
	 * Type, commonj.sdo.Property)
	 */
	@Override
	public boolean hasProperty(Type type, commonj.sdo.Property property) {
		if (!initialized())
			init();
		Set<commonj.sdo.Property> props = this.propertyMap.get(type);
		if (props != null && props.size() > 0) {
			if (props.contains(property))
				return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.plasma.query.collector.PropertySelection#addProperty(commonj.sdo.
	 * Type, commonj.sdo.Property)
	 */
	@Override
	public List<Type> addProperty(Type rootType, String path) {
		List<Type> result = new ArrayList<Type>();
		Type contextType = rootType;
		StringBuilder buf = new StringBuilder();
		String[] tokens = path.split("/");
		for (int i = 0; i < tokens.length; i++) {
			if (i > 0)
				buf.append("/");
			String token = tokens[i];
			int right = token.indexOf("[");
			if (right >= 0) // remove predicate - were just after the path
				token = token.substring(0, right);
			int attr = token.indexOf("@");
			if (attr == 0)
				token = token.substring(1);
			commonj.sdo.Property prop = contextType.getProperty(token);
			this.addProperty(contextType, prop, this.propertyMap);
			this.addInheritedProperty(contextType, prop,
					this.inheritedPropertyMap);
			if (!prop.getType().isDataType()) {
				contextType = prop.getType(); // traverse
				result.add(contextType);
			}
			buf.append(prop.getName());
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.plasma.query.collector.PropertySelection#hasInheritedProperty(commonj
	 * .sdo.Type, commonj.sdo.Property)
	 */
	@Override
	public boolean hasInheritedProperty(Type type, commonj.sdo.Property property) {
		if (!initialized())
			init();
		Set<commonj.sdo.Property> props = this.inheritedPropertyMap.get(type);
		if (props != null && props.size() > 0) {
			if (props.contains(property))
				return true;
		}
		return false;
	}


	@Override
	public Set<commonj.sdo.Property> getProperties(Type type, int level) {
		if (!initialized())
			init();
		Map<Integer, Set<commonj.sdo.Property>> levelMap = this.propertyLevelMap.get(type);
		if (levelMap != null) {
		    Set<commonj.sdo.Property> set = levelMap.get(level);
		    if (set != null)
		    	return set;
		}				 
		return EMPTY_PROPERTY_SET;
	}

	@Override
	public Set<commonj.sdo.Property> getProperties(Type type,
			commonj.sdo.Property sourceProperty) {
		if (!initialized())
			init();
		Map<commonj.sdo.Property, Set<commonj.sdo.Property>> edgeMap = this.propertyEdgeMap.get(type);
		if (edgeMap != null) {
		    Set<commonj.sdo.Property> set = edgeMap.get(sourceProperty);
		    if (set != null)
		    	return set;
		}				 
		return EMPTY_PROPERTY_SET;
	}

	@Override
	public Set<commonj.sdo.Property> getProperties(Type type,
			commonj.sdo.Property sourceProperty, int level) {
		Set<commonj.sdo.Property> result = new HashSet<commonj.sdo.Property>();
		result.addAll(getProperties(type, level));
		result.addAll(getProperties(type, sourceProperty));
		return result;
	}

	@Override
	public Where getPredicate(commonj.sdo.Property property,
			commonj.sdo.Property sourceProperty) {
		if (!initialized())
			init();
		Map<commonj.sdo.Property, Where> edgeMap = this.predicateEdgeMap.get(property);
		if (edgeMap != null) {
			return edgeMap.get(sourceProperty);
		}
		return null;
	}

	@Override
	public Where getPredicate(commonj.sdo.Property property, int level) {
		if (!initialized())
			init();
		Map<Integer, Where> levelMap = this.predicateLevelMap.get(property);
		if (levelMap != null) {
			return levelMap.get(level);
		}
		return null;
	}
	
	@Override
	public List<Function> getFunctions(commonj.sdo.Property property) {
		if (!initialized())
			init();
		List<Function> list = this.functionMap.get(property);
		if (list != null) {
			return list;
		}
		return EMPTY_FUNCTION_LIST;
	}

	@Override
	public List<Function> getFunctions(commonj.sdo.Property property, int level) {
		if (!initialized())
			init();
		Map<Integer, List<Function>> levelMap = this.functionLevelMap.get(property);
		if (levelMap != null) {
			List<Function> list = levelMap.get(level);
			if (list != null)
				return list;
		}
		return EMPTY_FUNCTION_LIST;
	}
	
	private void collect(AbstractProperty abstractProperty) {
		Path path = null;
		if (abstractProperty instanceof Property) {
			path = ((Property) abstractProperty).getPath();
		} else if (abstractProperty instanceof WildcardProperty) {
			path = ((WildcardProperty) abstractProperty).getPath();
		} else
			throw new IllegalArgumentException("unknown property class, "
					+ abstractProperty.getClass().getName());
		if (path == null) {
			commonj.sdo.Property[] props = this.findProperties(rootType,
					abstractProperty);
			this.mapProperties(this.rootType, props, this.propertyMap);
			this.mapInheritedProperties(this.rootType, props,
					this.inheritedPropertyMap);
			Integer level = Integer.valueOf(0);
			this.mapProperties(this.rootType, level, props,
					this.propertyLevelMap);
			this.mapInheritedProperties(this.rootType, level, props,
					this.inheritedPropertyLevelMap);
			if (abstractProperty instanceof Property) {
				Property property = (Property)abstractProperty;
				commonj.sdo.Property prop = this.rootType.getProperty(property.getName());
				List<Function> functions = property.getFunctions();
				if (functions != null && functions.size() > 0) {
					this.mapFunctions(prop, functions, this.functionMap);
					this.mapFunctions(prop, level, functions,
							this.functionLevelMap);
				}
			}
			// no source property so don't add source property mappings
		} else {
			if (!this.isOnlySingularProperties()) {
				collect(path, rootType, path.getPathNodes().get(0), 0,
						abstractProperty, null, 0);
			} else {
				if (this.isSingularPath(path, rootType, abstractProperty))
					collect(path, rootType, path.getPathNodes().get(0), 0,
							abstractProperty, null, 0);
			}
		}
	}

	/**
	 * Recursively collects properties, predicates and functions from the given path. 
	 * 
	 * @param path
	 *            the path
	 * @param currType
	 *            the current type
	 * @param currPathElement
	 *            the current path element/node
	 * @param curPathElementIndex
	 *            the current path element/node index
	 * @param abstractProperty
	 *            the property
	 * @param map
	 *            the property map
	 */
	private void collect(Path path, Type currType, PathNode currPathode,
			int curPathElementIndex, AbstractProperty abstractProperty,
			commonj.sdo.Property edge, int level) {

		AbstractPathElement currPathElement = currPathode.getPathElement();
		if (currPathElement instanceof PathElement) {
			PathElement pathElement = (PathElement) currPathElement;
			commonj.sdo.Property prop = currType.getProperty(pathElement
					.getValue());

			if (prop.getType().isDataType())
				if (abstractProperty instanceof Property)
					throw new QueryException("traversal path for property '"
							+ ((Property) abstractProperty).getName()
							+ "' from root '" + rootType.getName()
							+ "' contains a non-reference property '"
							+ prop.getName() + "'");
				else
					throw new QueryException(
							"traversal path for wildcard property "
									+ "' from root '" + rootType.getName()
									+ "' contains a non-reference property '"
									+ prop.getName() + "'");


			if (prop.isMany() && this.isOnlySingularProperties())
				return;
			
			this.addProperty(currType, prop, this.propertyMap);
			this.addInheritedProperty(currType, prop, this.inheritedPropertyMap);
			
			this.addProperty(currType, level, prop, this.propertyLevelMap);
			this.addInheritedProperty(currType, level, prop, this.inheritedPropertyLevelMap);

			if (currPathode.getWhere() != null) {
				this.predicateMap.put(prop, currPathode.getWhere());
				this.addPredicate(prop, level, currPathode.getWhere(), this.predicateLevelMap);
			}

			Type nextType = prop.getType(); // traverse

			if (path.getPathNodes().size() > curPathElementIndex + 1) { // more
																		// nodes
				if (edge != null) {
					this.addProperty(currType, edge, prop, this.propertyEdgeMap);
					this.addInheritedProperty(currType, edge, prop, this.inheritedPropertyEdgeMap);
					if (currPathode.getWhere() != null)
					    this.addPredicate(prop, edge, currPathode.getWhere(), this.predicateEdgeMap);
				}

				int nextPathElementIndex = curPathElementIndex + 1;
				PathNode nextPathNode = path.getPathNodes().get(
						nextPathElementIndex);
				collect(path, nextType, nextPathNode, nextPathElementIndex,
						abstractProperty, prop, level + 1);
			} else { // reached the path endpoint
				commonj.sdo.Property[] props = this.findProperties(nextType,
						abstractProperty);
				this.mapProperties(nextType, props, this.propertyMap);
				this.mapInheritedProperties(nextType, props,
						this.inheritedPropertyMap);
				this.mapProperties(nextType, level+1, props,
						this.propertyLevelMap);
				this.mapInheritedProperties(nextType, level+1, props,
						this.inheritedPropertyLevelMap);
				// current property is the edge
				this.mapProperties(nextType, prop, props,
						this.propertyEdgeMap);
				this.mapInheritedProperties(nextType, prop, props,
						this.inheritedPropertyEdgeMap);
				if (abstractProperty instanceof Property) {
					Property property = (Property)abstractProperty;
					List<Function> functions = property.getFunctions();
					if (functions != null && functions.size() > 0) {
						this.mapFunctions(prop, functions, this.functionMap);
						this.mapFunctions(prop, level, functions,
								this.functionLevelMap);
					}	
				}
			}
		} else if (currPathElement instanceof WildcardPathElement) {
			List<commonj.sdo.Property> properties = null;
			if (this.onlyDeclaredProperties)
				properties = currType.getDeclaredProperties();
			else
				properties = currType.getProperties();

			if (currPathode.getWhere() != null)
				throw new InvalidPathPredicateException(
						"path predicate found on wildcard path element");

			for (commonj.sdo.Property prop : properties) {
				if (prop.getType().isDataType())
					continue;
				if (prop.isMany() && this.isOnlySingularProperties())
					return;

				this.addProperty(currType, prop, this.propertyMap);
				this.addInheritedProperty(currType, prop,
						this.inheritedPropertyMap);
				if (edge != null) {
					this.addProperty(currType, edge, prop,
							this.propertyEdgeMap);
					this.addInheritedProperty(currType, edge, prop,
							this.inheritedPropertyEdgeMap);
				}
				
				Type nextType = prop.getType();

				if (path.getPathNodes().size() > curPathElementIndex + 1) { // more path nodes
					this.addProperty(currType, level, prop,
							this.propertyLevelMap);
					this.addInheritedProperty(currType, level, prop,
							this.inheritedPropertyLevelMap);
					int nextPathElementIndex = curPathElementIndex + 1;
					PathNode nextPathNode = path.getPathNodes().get(
							nextPathElementIndex);
					collect(path, nextType, nextPathNode, nextPathElementIndex,
							abstractProperty, prop, level + 1);
				} else {
					this.addProperty(currType, level+1, prop,
							this.propertyLevelMap);
					this.addInheritedProperty(currType, level+1, prop,
							this.inheritedPropertyLevelMap);
					commonj.sdo.Property[] props = this.findProperties(
							nextType, abstractProperty);

					this.mapProperties(nextType, props, this.propertyMap);
					this.mapInheritedProperties(nextType, props,
							this.inheritedPropertyMap);					
					this.mapProperties(nextType, level+1, props,
							this.propertyLevelMap);
					this.mapInheritedProperties(nextType, level+1, props,
							this.inheritedPropertyLevelMap);
					// cur property is the edge
					this.mapProperties(nextType, prop, props,
							this.propertyEdgeMap);
					this.mapInheritedProperties(nextType, prop, props,
							this.inheritedPropertyEdgeMap);

					if (abstractProperty instanceof Property) {
						Property property = (Property)abstractProperty;
						List<Function> functions = property.getFunctions();
						if (functions != null && functions.size() > 0) {
							this.mapFunctions(prop, functions, this.functionMap);
							this.mapFunctions(prop, level, functions,
									this.functionLevelMap);
						}	
					}
				}
			}
		} else
			throw new IllegalArgumentException("unknown path element class, "
					+ currPathElement.getClass().getName());
	}

	public String dumpProperties() {
		StringBuilder buf = new StringBuilder();
		Iterator<Type> typeIter = this.propertyMap.keySet().iterator();
		while (typeIter.hasNext()) {
			Type type = typeIter.next();
			buf.append("\n" + type.getURI() + "#" + type.getName());
			Set<commonj.sdo.Property> props = this.propertyMap.get(type);
			for (commonj.sdo.Property prop : props) {
				buf.append("\n\t" + prop.getName());
			}
		}
		return buf.toString();
	}

	public String dumpInheritedProperties() {
		StringBuilder buf = new StringBuilder();
		Iterator<Type> typeIter = this.inheritedPropertyMap.keySet().iterator();
		while (typeIter.hasNext()) {
			Type type = typeIter.next();
			buf.append("\n" + type.getURI() + "#" + type.getName());
			Set<commonj.sdo.Property> props = this.inheritedPropertyMap
					.get(type);
			for (commonj.sdo.Property prop : props) {
				buf.append("\n\t" + prop.getName());
			}
		}
		return buf.toString();
	}


}
