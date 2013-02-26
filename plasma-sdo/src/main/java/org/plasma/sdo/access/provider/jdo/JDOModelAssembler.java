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
package org.plasma.sdo.access.provider.jdo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.sdo.access.provider.jdo.Class_;
import org.plasma.sdo.access.provider.jdo.Collection;
import org.plasma.sdo.access.provider.jdo.Column;
import org.plasma.sdo.access.provider.jdo.Discriminator;
import org.plasma.sdo.access.provider.jdo.Field;
import org.plasma.sdo.access.provider.jdo.Inheritance;
import org.plasma.sdo.access.provider.jdo.Jdo;
import org.plasma.sdo.access.provider.jdo.Join;
import org.plasma.sdo.access.provider.jdo.Package_;
import org.plasma.sdo.access.provider.jdo.Version;
import org.plasma.sdo.access.model.EntityConstants;
import org.plasma.config.DataAccessProviderName;
import org.plasma.config.NamespaceProvisioning;
import org.plasma.config.NonExistantNamespaceException;
import org.plasma.config.PlasmaConfig;
import org.plasma.provisioning.Class;
import org.plasma.provisioning.ClassRef;
import org.plasma.provisioning.KeyType;
import org.plasma.provisioning.Package;
import org.plasma.provisioning.Model;
import org.plasma.provisioning.ProvisioningException;

public class JDOModelAssembler {
    private static Log log = LogFactory.getLog(
    		JDOModelAssembler.class); 

    private boolean multipleInheritance = true;
    private Model pkgs;
    private Jdo result; 
    private Map<String, List<Class>> namespaceMap = new HashMap<String, List<Class>>();
    private Map<String, Class> entityMap = new HashMap<String, Class>();
    
    public JDOModelAssembler(Model pkgs) {
    	this.pkgs = pkgs;
    }
    
    public Jdo getJdo() {
    	if (this.result == null)
    		buildJdoModel();
    	return this.result;
    }
    
    private Jdo buildJdoModel() {
    	for (Package pkg : this.pkgs.getPackages()) {
        	for (Class clss : pkg.getClazzs()) {
        		
	    		List<Class> defs = namespaceMap.get(clss.getUri());
	    		if (defs == null) {
	    			defs = new ArrayList<Class>();
	    			namespaceMap.put(clss.getUri(), defs);
	    		}
	    		defs.add(clss);
	    		entityMap.put(clss.getUri() + "#" + clss.getName(), clss);
        	}
    	}
    	
    	result = new Jdo();
    	
    	for (String key : namespaceMap.keySet()) {
    		String packageName = null;
    		try {   		
    		    packageName = PlasmaConfig.getInstance().getServiceImplementationPackageName(
    		    		DataAccessProviderName.JDO, key);
    		}
    		catch (NonExistantNamespaceException e) {
    			continue; 
    		}
    		Package_ pkg = new Package_();
    	    pkg.setName(packageName);
    	    result.getExtensionsAndPackagesAndQueries().add(pkg);
    		List<Class> defs = namespaceMap.get(key);
    		for (Class provisioningClass : defs) {
    			// If not multiple-inheritance, we can have JDO classes
    			// under 'subclass-table' Kodo inheritance strategy
    			// that have no table or columns but just props, otherwise
    			//if no physical name, then no JDO class metadata
    			//if (this.multipleInheritance && 
    			//	!hasPhysicalName(def))
    			//	continue;
    			if (provisioningClass.isAbstract())
    				continue;
    			
    			Class_ cls = buildClass(provisioningClass);
    			pkg.getInterfacesAndClazzsAndSequences().add(cls);
    			
    			if (provisioningClass.isAbstract())
    				continue;
    			
    			for (org.plasma.provisioning.Property pdef : provisioningClass.getProperties()) {
    				Field field = buildField(provisioningClass, null, pdef);
    				cls.getFields().add(field);
    			}
    			
    			// recursively inherit properties from base classes
    			inherit(provisioningClass, provisioningClass, cls);
    		}
    	}
    	
    	Object[] objs = new Object[result.getExtensionsAndPackagesAndQueries().size()];
    	result.getExtensionsAndPackagesAndQueries().toArray(objs);
    	for (Object obj : objs) {
    		Package_ pkg = (Package_)obj;
    	    if (pkg.getInterfacesAndClazzsAndSequences().size() == 0)
    	        result.getExtensionsAndPackagesAndQueries().remove(pkg);
    	}

    	return result;
    }
    
    private void inherit(Class def, Class baseDef, Class_ target)
    {
		for (ClassRef baseEntity : baseDef.getSuperClasses()) {
			Class nextBaseDef = entityMap.get(baseEntity.getUri() + "#" + baseEntity.getName());
			
			// base has no physical name, assume we should inherit
			// its properties
			if (!hasPhysicalName(nextBaseDef)) {
			
				for (org.plasma.provisioning.Property pdef : nextBaseDef.getProperties()) {
    				Field field = buildField(def, nextBaseDef, pdef);
    				target.getFields().add(field);
    			}
				inherit (def, nextBaseDef, target);
			}
		}    	
    }
    
    private Class_ buildClass(Class sourceClass) {
		String className = PlasmaConfig.getInstance().getServiceImplementationClassName(DataAccessProviderName.JDO, 
				sourceClass.getUri(), sourceClass.getName());
		Class_ cls = new Class_();
		cls.setName(className);
		Version version = new Version();
		version.setStrategy("none");
		cls.setVersion(version);
		
		if (sourceClass.isAbstract()) {
			//cls.setPersistenceModifier("persistence-aware");
		    //cls.setObjectidClass(sourceClass.getName() + "AppId");
            //return cls;
		}
		
		// No physical name 
		if (!hasPhysicalName(sourceClass)) {
			
			// assume classes/tables may want to subclass us
			// so use Kodo subclass-table strategy
			Inheritance inheritance = new Inheritance();
			inheritance.setStrategy("subclass-table");
			cls.setInheritance(inheritance);
            cls.setObjectidClass(sourceClass.getName() + "AppId");
            return cls;
		}
		
		cls.setTable(sourceClass.getAlias().getPhysicalName());
		// this class is a leaf
		if (!this.isBaseEntity(sourceClass)) {
			Inheritance inheritance = new Inheritance();
			cls.setInheritance(inheritance);
			// no physical base entity, it does not need a strategy
			if (!hasPhysicalBaseEntity(sourceClass)) {
				Discriminator discriminator = new Discriminator();
		        discriminator.setStrategy("final");
				inheritance.setDiscriminator(discriminator);
			    cls.setObjectidClass(sourceClass.getName() + "AppId");
			}    
			else { // has physical base entity
				inheritance.setStrategy("new-table");
				Join join = new Join();
				join.setDeleteAction("restrict");
				inheritance.setJoin(join);
				Column joinColumn = new Column();
				join.getColumns().add(joinColumn);
				// FIXME what if multiple supers or PKs
				ClassRef baseRef = getPhysicalBaseEntityRef(sourceClass);
				String baseKey = baseRef.getUri() + "#" + baseRef.getName();
				Class base = entityMap.get(baseRef.getUri() + "#" + baseRef.getName());
				if ((base.getAlias() == null || base.getAlias().getPhysicalName() == null) || 
					 base.getAlias().getPhysicalName().trim().length() == 0)
					throw new IllegalStateException("expected physical name for base entity '"
							+ baseKey + "'");

				joinColumn.setName(base.getAlias().getPhysicalName());
				String parent = base.getAlias().getPhysicalName() + ".";
				org.plasma.provisioning.Property[] basePriKeys = this.getPriKeys(base);
				if (basePriKeys.length == 0)
				    throw new IllegalStateException("no primary key properties found for base entity '"
						+ baseKey + "'");
				if (basePriKeys.length > 1)
				    throw new IllegalStateException("multiple primary key properties found for base entity '"
						+ baseKey + "'");
				parent += basePriKeys[0].getAlias().getPhysicalName();
				joinColumn.setTarget(parent);
				//<join column="ID" delete-action="restrict"/>
				//<join>
				//<column name="ID" target="CNTRCT.SUB.ID"/>
				//</join>
			    //cls.setObjectidClass(def.getName() + "AppId");
			}
		}
		else { //it is a physical superclass to someone
			// no inheritance strategy needed
		    cls.setObjectidClass(sourceClass.getName() + "AppId");
		}

		
		return cls;
    }
    
    private Field buildField(Class def, Class baseDef, org.plasma.provisioning.Property pdef) {
    	if (pdef.getType() instanceof ClassRef ) {
    		if (!pdef.isMany())
    			return buildSingularReferenceField(def, baseDef, pdef);
    		else 
    			return buildMultiReferenceField(def, baseDef, pdef);
    	}
    	else {
			return buildDataField(def, baseDef, pdef);
    	}    	
    }
 
    private Field buildSingularReferenceField(Class def, Class baseDef, org.plasma.provisioning.Property pdef) {
    	Field field = new Field();
    	if (baseDef == null)
    	    field.setName(buildFieldName(def, pdef));
    	else
        	field.setName(buildQualifiedFieldName(def, baseDef, pdef));
    	field.setPersistenceModifier("persistent");
    	field.setDefaultFetchGroup("false");
		field.setDeleteAction("restrict"); 
		field.setIndexed("true"); // FIXME - add indexed to staging model 
		if (hasPhysicalName(def)) {
    		Column column = new Column();
    		if (!hasPhysicalName(pdef))
    			throw new ProvisioningException("Expected physical name for singular property "
    					+ def.getUri() + "#" + def.getName() + "." + pdef.getName());
    		column.setName(pdef.getAlias().getPhysicalName());
    		field.getColumns().add(column);
		}
    		
        return field;
    }
    
    private Field buildMultiReferenceField(Class clss, Class baseClss, org.plasma.provisioning.Property prop) {
    	Field field = new Field();
    	
    	if (baseClss == null)
    	    field.setName(buildFieldName(clss, prop));
    	else {
    		if (!baseClss.isAbstract())
        	    field.setName(buildQualifiedFieldName(clss, baseClss, prop));
    		else
        	    field.setName(buildFieldName(clss, prop));
    	}
		
		field.setDefaultFetchGroup("false");
		
		String oppositeName = prop.getOpposite();
		if (oppositeName != null) {
		    field.setMappedBy(EntityConstants.DATA_ACCESS_CLASS_MEMBER_PREFIX 
				+ oppositeName);
		}
		
		Collection collection = new Collection();
		ClassRef propTypeRef = (ClassRef)prop.getType();
		Class propTypeClass = entityMap.get(propTypeRef.getUri() + "#" + propTypeRef.getName());
		
		
		NamespaceProvisioning provisioning = PlasmaConfig.getInstance().getProvisioningByNamespaceURI(
				DataAccessProviderName.JDO, propTypeClass.getUri());
		collection.setElementType(provisioning.getPackageName() + "."
				+ propTypeClass.getName());
/*
		if (!propTypeClass.isAbstract()) {
			NamespaceProvisioning provisioning = PlasmaConfig.getInstance().getProvisioningByNamespaceURI(
					DataAccessServiceName.JPA, propTypeClass.getUri());
			collection.setElementType(provisioning.getPackageName() + "."
					+ propTypeClass.getName()+ "Impl");
		}
		else {
			// assume this class has inherited
			NamespaceProvisioning provisioning = PlasmaConfig.getInstance().getProvisioningByNamespaceURI(
					DataAccessServiceName.JPA, clss.getUri());
			collection.setElementType(provisioning.getPackageName() + "."
					+ clss.getName());
		}
*/		
		field.setCollection(collection);
        return field;
    }

    private Field buildDataField(Class def, Class baseDef, 
    		org.plasma.provisioning.Property pdef)
    {
    	Field field = new Field();
    	if (baseDef == null)
    	    field.setName(buildFieldName(def, pdef));
    	else
        	field.setName(buildQualifiedFieldName(def, baseDef, pdef));
		field.setNullValue("default");
		field.setDefaultFetchGroup("false");
		if (pdef.getKey() != null && 
			pdef.getKey().getType().ordinal() == KeyType.PRIMARY.ordinal()) {
			field.setPrimaryKey("true");
		}
		
		if (hasPhysicalName(def)) {
    		Column column = new Column();
    		column.setName(pdef.getAlias().getPhysicalName());
    		column.setDefaultValue("BOGUS_DEFAULT_VALUE");
    		if (pdef.getValueConstraint() != null && 
    			pdef.getValueConstraint().getMaxLength() != null) {
    			Long maxLength = Long.valueOf(pdef.getValueConstraint().getMaxLength());
    			if (maxLength.longValue() > 4000)
    			    column.setJdbcType("clob");
    		}
    		field.getColumns().add(column);
		}
    	
        return field;
    }
    
    private String buildFieldName(Class def, org.plasma.provisioning.Property pdef)
    {
    	String result;
		if (pdef.isMany()) {
			result = EntityConstants.DATA_ACCESS_CLASS_MEMBER_PREFIX 
			  + pdef.getName()+ EntityConstants.DATA_ACCESS_CLASS_MEMBER_MULTI_VALUED_SUFFIX;
		}
		else {
			result = EntityConstants.DATA_ACCESS_CLASS_MEMBER_PREFIX + pdef.getName();
		}
    	return result;
    }
 
    private String buildQualifiedFieldName(Class def, Class baseDef, org.plasma.provisioning.Property pdef)
    {
    	String result;
		if (pdef.isMany()) {
			NamespaceProvisioning baseProvisioning = PlasmaConfig.getInstance().getProvisioningByNamespaceURI(
					DataAccessProviderName.JDO, baseDef.getUri());
			result = baseProvisioning.getPackageName() + "." + baseDef.getName()
		    		+ "." + EntityConstants.DATA_ACCESS_CLASS_MEMBER_PREFIX 
	    			+ pdef.getName()+ EntityConstants.DATA_ACCESS_CLASS_MEMBER_MULTI_VALUED_SUFFIX;    				
		}
		else {
			NamespaceProvisioning baseProvisioning = PlasmaConfig.getInstance().getProvisioningByNamespaceURI(
					DataAccessProviderName.JDO, baseDef.getUri());
    		
    		if (!multipleInheritance)
    			result = baseProvisioning.getPackageName() + "." + baseDef.getName()
	    		    + "." + EntityConstants.DATA_ACCESS_CLASS_MEMBER_PREFIX + pdef.getName();
    		else
    			result = EntityConstants.DATA_ACCESS_CLASS_MEMBER_PREFIX + pdef.getName();
		}
    	return result;
    }    
    
    private boolean hasPhysicalName(Class cls) {
    	return cls.getAlias() != null && cls.getAlias().getPhysicalName() != null && 
    	    cls.getAlias().getPhysicalName().trim().length() > 0;    	
    }

    private boolean hasPhysicalName(org.plasma.provisioning.Property prop) {
    	return prop.getAlias() != null && prop.getAlias().getPhysicalName() != null && 
    	    prop.getAlias().getPhysicalName().trim().length() > 0;    	
    }
    
    /**
     * Returns true if any other entities in the model have 
     * the given entity as a base entity.
     * @param def the entity def
     * @return true if any other entities in the model have 
     * the given entity as a base entity
     */
    private boolean isBaseEntity(Class def) {
    	for (Class def2 : entityMap.values()) {
    		for (ClassRef ref : def2.getSuperClasses()) {
    			if (ref.getUri().equals(def.getUri()) &&
    					ref.getName().equals(def.getName()))
    				return true;
    		}
    	}
    	return false;
    }
     
    /**
     * Returns true if the given entity has any base entities
     * which have a physical name.
     * @param def the entity def
     * @return true if the given entity has any base entities
     * which have a physical name
     */
    private boolean hasPhysicalBaseEntity(Class def) {
		for (ClassRef ref : def.getSuperClasses()) {
			Class base = entityMap.get(ref.getUri() + "#" + ref.getName());
			if (hasPhysicalName(base))
				return true;
		}
    	return false;
    }

    /**
     * Returns the first physical base entity ref found for the given
     * entity def.
     * @param def the entity def
     * @return the first physical base entity ref found for the given
     * entity def
     */
    private ClassRef getPhysicalBaseEntityRef(Class def) {
		for (ClassRef ref : def.getSuperClasses()) {
			Class base = entityMap.get(ref.getUri() + "#" + ref.getName());
			if (hasPhysicalName(base))
				return ref;
		}
    	return null;
    }    
    
    private boolean hasPriKey(Class def) {
    	if (def.getProperties() != null)
		    for (org.plasma.provisioning.Property prop : def.getProperties()) {
				if (prop.getKey() != null && 
					prop.getKey().getType().ordinal() == KeyType.PRIMARY.ordinal()) {
					return true;
				}
		    }
	    return false;
    }
    
    private org.plasma.provisioning.Property[] getPriKeys(Class def) {
    	List<org.plasma.provisioning.Property> list = new ArrayList<org.plasma.provisioning.Property>();
	    if (def.getProperties() != null)
	    	for (org.plasma.provisioning.Property prop : def.getProperties()) {
				if (prop.getKey() != null && 
					prop.getKey().getType().ordinal() == KeyType.PRIMARY.ordinal()) {
					list.add(prop);
				}
		    }
	    org.plasma.provisioning.Property[] result = new org.plasma.provisioning.Property[list.size()];
	    list.toArray(result);
	    return result;
    }
}
