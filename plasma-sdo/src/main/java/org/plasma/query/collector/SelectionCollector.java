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
import org.plasma.sdo.PlasmaProperty;
import org.plasma.sdo.PlasmaType;

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
	private Select select;
	// FIXME: what to do with repeated/multiple predicates
	private Map<commonj.sdo.Property, Where> predicateMap;
	private Map<Type, Set<commonj.sdo.Property>> propertyMap;
	private Map<Type, Set<commonj.sdo.Property>> singularPropertyMap;
	private Map<Type, Set<commonj.sdo.Property>> inheritedPropertyMap;

	private Map<commonj.sdo.Property, Map<Integer, Where>> predicateLevelMap;
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

	@Override
	public void collect(Where predicate) {
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
		predicate.accept(visitor);
	}

	private void collect() {
		if (this.propertyMap == null) {
			this.propertyMap = new HashMap<Type, Set<commonj.sdo.Property>>();
			this.singularPropertyMap = new HashMap<Type, Set<commonj.sdo.Property>>();
			this.inheritedPropertyMap = new HashMap<Type, Set<commonj.sdo.Property>>();
			this.predicateMap = new HashMap<commonj.sdo.Property, Where>();

			this.propertyLevelMap = new HashMap<Type, Map<Integer, Set<commonj.sdo.Property>>>();
			this.singularPropertyLevelMap = new HashMap<Type, Map<Integer, Set<commonj.sdo.Property>>>();
			this.inheritedPropertyLevelMap = new HashMap<Type, Map<Integer, Set<commonj.sdo.Property>>>();
			this.predicateLevelMap = new HashMap<commonj.sdo.Property, Map<Integer, Where>>();

			this.propertyEdgeMap = new HashMap<Type, Map<commonj.sdo.Property, Set<commonj.sdo.Property>>>();
			this.singularPropertyEdgeMap = new HashMap<Type, Map<commonj.sdo.Property, Set<commonj.sdo.Property>>>();
			this.inheritedPropertyEdgeMap = new HashMap<Type, Map<commonj.sdo.Property, Set<commonj.sdo.Property>>>();
			this.predicateEdgeMap = new HashMap<commonj.sdo.Property, Map<commonj.sdo.Property, Where>>();

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
	}

	@Deprecated
	public Map<commonj.sdo.Property, Where> getPredicateMap() {
		collect();
		return predicateMap;
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
		collect();
		return this.singularPropertyMap.get(type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.plasma.query.collector.PropertySelection#getProperties(commonj.sdo
	 * .Type)
	 */
	@Override
	public Set<commonj.sdo.Property> getProperties(Type type) {
		collect();
		return propertyMap.get(type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.plasma.query.collector.PropertySelection#getInheritedProperties(commonj
	 * .sdo.Type)
	 */
	@Override
	public Set<commonj.sdo.Property> getInheritedProperties(Type type) {
		collect();
		return inheritedPropertyMap.get(type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.plasma.query.collector.PropertySelection#getTypes()
	 */
	@Override
	public List<Type> getTypes() {
		collect();
		List<Type> result = new ArrayList<Type>();
		result.addAll(this.propertyMap.keySet());
		result.addAll(this.inheritedPropertyMap.keySet());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.plasma.query.collector.PropertySelection#hasType(commonj.sdo.Type)
	 */
	@Override
	public boolean hasType(Type type) {
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
		collect();
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
		Set<commonj.sdo.Property> props = this.inheritedPropertyMap.get(type);
		if (props != null && props.size() > 0) {
			if (props.contains(property))
				return true;
		}
		return false;
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
	 * Recursively collects properties from the given path into the given
	 * property map including both wildcard paths and properties
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

			if (currPathode.getWhere() != null)
				this.predicateMap.put(prop, currPathode.getWhere());

			if (prop.isMany() && this.isOnlySingularProperties())
				return;

			Type nextType = prop.getType(); // traverse

			if (path.getPathNodes().size() > curPathElementIndex + 1) { // more
																		// nodes
				this.addProperty(currType, prop, this.propertyMap);
				this.addInheritedProperty(currType, prop, this.inheritedPropertyMap);
				
				this.addProperty(currType, level, prop, this.propertyLevelMap);
				this.addInheritedProperty(currType, level, prop, this.inheritedPropertyLevelMap);
				
				if (edge != null) {
					this.addProperty(currType, edge, prop, this.propertyEdgeMap);
					this.addInheritedProperty(currType, edge, prop, this.inheritedPropertyEdgeMap);
				}

				int nextPathElementIndex = curPathElementIndex + 1;
				PathNode nextPathNode = path.getPathNodes().get(
						nextPathElementIndex);
				collect(path, nextType, nextPathNode, nextPathElementIndex,
						abstractProperty, prop, level + 1);
			} else {
				this.addProperty(currType, prop, this.propertyMap);
				this.addInheritedProperty(currType, prop,
						this.inheritedPropertyMap);
				this.addProperty(currType, level, prop, this.propertyLevelMap);
				this.addInheritedProperty(currType, level, prop,
						this.inheritedPropertyLevelMap);

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

				Type nextType = prop.getType();

				if (path.getPathNodes().size() > curPathElementIndex + 1) { // more path nodes
					this.addProperty(currType, prop, this.propertyMap);
					this.addInheritedProperty(currType, prop,
							this.inheritedPropertyMap);
					this.addProperty(currType, level, prop,
							this.propertyLevelMap);
					this.addInheritedProperty(currType, level, prop,
							this.inheritedPropertyLevelMap);
					if (edge != null) {
						this.addProperty(currType, edge, prop,
								this.propertyEdgeMap);
						this.addInheritedProperty(currType, edge, prop,
								this.inheritedPropertyEdgeMap);
					}

					int nextPathElementIndex = curPathElementIndex + 1;
					PathNode nextPathNode = path.getPathNodes().get(
							nextPathElementIndex);
					collect(path, nextType, nextPathNode, nextPathElementIndex,
							abstractProperty, prop, level + 1);
				} else {
					this.addProperty(currType, prop, this.propertyMap);
					this.addInheritedProperty(currType, prop,
							this.inheritedPropertyMap);
					this.addProperty(currType, level+1, prop,
							this.propertyLevelMap);
					this.addInheritedProperty(currType, level+1, prop,
							this.inheritedPropertyLevelMap);
					this.addProperty(currType, edge, prop,
							this.propertyEdgeMap);
					this.addInheritedProperty(currType, edge, prop,
							this.inheritedPropertyEdgeMap);
					
					commonj.sdo.Property[] props = this.findProperties(
							nextType, abstractProperty);

					this.mapProperties(nextType, props, this.propertyMap);
					this.mapInheritedProperties(nextType, props,
							this.inheritedPropertyMap);					
					this.mapProperties(nextType, level+1, props,
							this.propertyLevelMap);
					this.mapInheritedProperties(nextType, level+1, props,
							this.inheritedPropertyLevelMap);
					// cure property is the edge
					this.mapProperties(nextType, prop, props,
							this.propertyEdgeMap);
					this.mapInheritedProperties(nextType, prop, props,
							this.inheritedPropertyEdgeMap);

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
			PlasmaType type = (PlasmaType) typeIter.next();
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
			PlasmaType type = (PlasmaType) typeIter.next();
			buf.append("\n" + type.getURI() + "#" + type.getName());
			Set<commonj.sdo.Property> props = this.inheritedPropertyMap
					.get(type);
			for (commonj.sdo.Property prop : props) {
				buf.append("\n\t" + prop.getName());
			}
		}
		return buf.toString();
	}

	@Override
	public Set<commonj.sdo.Property> getProperties(Type type, int level) {
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

}
