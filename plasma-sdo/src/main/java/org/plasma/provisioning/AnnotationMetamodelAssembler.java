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
package org.plasma.provisioning;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.atteo.classindex.ClassIndex;
import org.plasma.config.PlasmaConfig;
import org.plasma.metamodel.Alias;
import org.plasma.metamodel.Body;
import org.plasma.metamodel.Class;
import org.plasma.metamodel.ClassProvisioning;
import org.plasma.metamodel.ClassRef;
import org.plasma.metamodel.ConcurentDataFlavor;
import org.plasma.metamodel.ConcurrencyType;
import org.plasma.metamodel.Concurrent;
import org.plasma.metamodel.DataTypeRef;
import org.plasma.metamodel.Documentation;
import org.plasma.metamodel.DocumentationType;
import org.plasma.metamodel.Enumeration;
import org.plasma.metamodel.EnumerationConstraint;
import org.plasma.metamodel.EnumerationLiteral;
import org.plasma.metamodel.EnumerationRef;
import org.plasma.metamodel.Key;
import org.plasma.metamodel.KeyType;
import org.plasma.metamodel.Model;
import org.plasma.metamodel.NamespaceProvisioning;
import org.plasma.metamodel.Package;
import org.plasma.metamodel.Property;
import org.plasma.metamodel.PropertyProvisioning;
import org.plasma.metamodel.Sort;
import org.plasma.metamodel.TypeRef;
import org.plasma.metamodel.ValueConstraint;
import org.plasma.metamodel.VisibilityType;
import org.plasma.metamodel.XmlNodeType;
import org.plasma.metamodel.XmlProperty;
import org.plasma.provisioning.common.NameUtils;
import org.plasma.sdo.DataType;
import org.plasma.sdo.annotation.DataProperty;
import org.plasma.sdo.annotation.Namespace;
import org.plasma.sdo.annotation.ReferenceProperty;
import org.plasma.sdo.annotation.Type;

/**
 * Constructs a meta model based on any properly annotated enums
 * 
 * @since 1.2.4
 */
public class AnnotationMetamodelAssembler implements AnnotationConverter {
	private static final String DERIVED_ARTIFACT_URI_PREFIX = "http://derived-artifact/";

	private static Log log = LogFactory.getLog(AnnotationMetamodelAssembler.class);

	private Model model;
	/** maps namespace URI strings and fully qualified package names to packages */
	private Map<String, Package> packageURIMap = new HashMap<String, Package>();
	private Map<String, Class> classMap = new HashMap<String, Class>();
	private Map<String, Enumeration> enumerationMap = new HashMap<String, Enumeration>();	
	private List<java.lang.Class<?>> dataObjectClasses = new ArrayList<java.lang.Class<?>>();

	public AnnotationMetamodelAssembler() {		
		for (java.lang.Class<?> c : ClassIndex.getAnnotated(Type.class)) {
			if (!c.isEnum())
				throw new InvalidAnnotationException("annotation " + Type.class.getName() + " may only be applied to java enumeration (enum) classes");
			dataObjectClasses.add(c);
		}
	}
	
	public boolean hasAnnotatedClasses() {
		return dataObjectClasses.size() > 0;
	}

	public Model getModel() {
		if (this.model == null)
			this.model = buildModel();
		return this.model;
	}

	@Override
	public Model buildModel() {

		// init packages
		createPachageHierarchy();
		
		// init classes
		for (java.lang.Class<?> c : dataObjectClasses) {
			if (log.isDebugEnabled())
				log.debug("discovered " + c.getName());
			java.lang.Package javaPkg = c.getPackage();
			Package packg = this.packageURIMap.get(javaPkg.getName());

			Type type = c.getAnnotation(Type.class);
			Class clss = this.createClass(type.name(), type.isAbstract(), c, type.superTypes(),
					packg);
			String qualifiedName = packg.getUri() + "#" + clss.getName();
			log.debug("initializing class: " + qualifiedName);
			assert(clss.getUri().equals(packg.getUri()));
			this.classMap.put(qualifiedName, clss);
		}

		// add properties
		for (java.lang.Class<?> c : dataObjectClasses) {
			java.lang.Package javaPkg = c.getPackage();
			Package packg = this.packageURIMap.get(javaPkg.getName());

			Type dataObject = c.getAnnotation(Type.class);
			String qualifiedName = packg.getUri() + "#" + dataObject.name();
			if (log.isDebugEnabled())
			    log.debug("processing class: " + qualifiedName);
			Class clss = this.classMap.get(qualifiedName);

			try {
			    for (Object o : c.getEnumConstants()) {
			    	Enum<?> enm = (Enum<?>) o;
					Field field = c.getField(enm.name());
					DataProperty dataProperty = field.getAnnotation(DataProperty.class);
					if (dataProperty != null) {
						if (log.isDebugEnabled())
						    log.debug("processing data field: " + qualifiedName + "." + enm.name());
						this.createDataProperty(dataProperty, field, enm, clss, packg);
					}
					else {
						ReferenceProperty referenceProperty = field.getAnnotation(ReferenceProperty.class);
						if (referenceProperty != null) {
							if (log.isDebugEnabled())
							    log.debug("processing reference field: " + qualifiedName + "." + enm.name());
						   this.createReferenceProperty(referenceProperty, field, enm, clss, packg);
						}
						else
							throw new MissingAnnotationException("expected either " + DataProperty.class.getName() 
									+ " or " + ReferenceProperty.class.getName() + " annotation for enum field "
									+ clss.getName() + "." + enm.name());
					}
			    }
			} catch (NoSuchFieldException | SecurityException e) { 
				throw new ProvisioningException(e);
			}
		}

		return this.model;
	}
	
	private void createPachageHierarchy()
	{
		Map<String, Package> map = new HashMap<String, Package>();
		for (java.lang.Class<?> c : dataObjectClasses) {
			java.lang.Package javaPkg = c.getPackage();
			if (log.isDebugEnabled())
			    log.debug("processing package " + javaPkg.getName());
			Namespace namespace = javaPkg.getAnnotation(Namespace.class);
			String[] tokens = javaPkg.getName().split("\\.");
			StringBuilder key = new StringBuilder();
			Package parent = null;
			for (int i = 0; i < tokens.length; i++) {
				boolean isLeaf = i+1 == tokens.length;
				if (i > 0)
					key.append(".");
				key.append(tokens[i]);
				Package pkg = map.get(key.toString());
				if (pkg != null) {
					parent = pkg;
					continue;
				}
				if (this.model == null) {
					pkg = this.createPackage(tokens[i], namespace, javaPkg, isLeaf, true);
					this.model = (Model)pkg;
					this.model.setUri(DERIVED_ARTIFACT_URI_PREFIX + UUID.randomUUID().toString());
				}
				else
					pkg = this.createPackage(tokens[i], namespace, javaPkg, isLeaf, false);
				map.put(key.toString(), pkg);
				if (parent != null) 
					parent.getPackages().add(pkg);
				 
				if (isLeaf) {
					String uri = null;
					if (namespace != null) {
						uri = namespace.uri();
					} else {
						uri = deriveUri(javaPkg);
					}					
					this.packageURIMap.put(uri, pkg);
					this.packageURIMap.put(javaPkg.getName(), pkg);
					if (log.isDebugEnabled())
					    log.debug("created leaf package " + javaPkg.getName() + " as " + uri);
				}
				parent = pkg;
			}
		}		
	}

	private String deriveUri(java.lang.Package pkg) {
		String uri = "http://" + pkg.getName();
		return uri;
	}

	private Package createPackage(String nameToken, Namespace namespace, java.lang.Package javaPackage, 
			boolean leafPackage, boolean modelPackage) {
		Package pkg = null;
		if (!modelPackage)
			pkg = new Package();
		else
			pkg = new Model();
		pkg.setName(nameToken);
		pkg.setId(UUID.randomUUID().toString());
		if (leafPackage) {
			if (namespace != null)
		        pkg.setUri(namespace.uri());
			else
		        pkg.setUri(this.deriveUri(javaPackage));
			NamespaceProvisioning nsProv = new NamespaceProvisioning();
			pkg.setNamespaceProvisioning(nsProv);
			nsProv.setOriginatingPackageName(javaPackage.getName());
			org.plasma.config.annotation.NamespaceProvisioning nsProvAnnot = javaPackage.getAnnotation(org.plasma.config.annotation.NamespaceProvisioning.class);
			if (nsProvAnnot != null) {
				nsProv.setPackageName(nsProvAnnot.rootPackageName());
			}
		}

		Alias alias = null;
		org.plasma.sdo.annotation.Alias srcAlias = javaPackage.getAnnotation(org.plasma.sdo.annotation.Alias.class);
		if (srcAlias != null) {
			alias = createAlias(srcAlias);
		} 	
		
		if (leafPackage) {
			
			if (alias == null) {
				alias = this.createAlias(nameToken, namespace, javaPackage);
				pkg.setAlias(alias);
			}
			else {
			    if (alias.getLocalName() != null && alias.getLocalName().length() > 0)
				    log.warn("alias local name for package should not be used in this context - overwriting local name for " + javaPackage.getName());
			    alias.setLocalName(javaPackage.getName());
			}

			org.plasma.sdo.annotation.Comment srcComment = javaPackage
					.getAnnotation(org.plasma.sdo.annotation.Comment.class);
			Documentation doc = new Documentation();
			doc.setType(DocumentationType.DEFINITION);
			Body body = new Body();
			doc.setBody(body);
			pkg.getDocumentations().add(doc);
			if (srcComment != null && srcComment.body().length() > 0) {
				body.setValue(srcComment.body());
			} else {
				body.setValue("Derived from package " + javaPackage.getName());
			}
		}
		return pkg;
	}
	
	private Class createClass(String name, boolean isAbstract, java.lang.Class<?> javaClass,
			java.lang.Class<?>[] generalizations, Package pkg) {

		Class clss = new Class();
		pkg.getClazzs().add(clss);
		clss.setId(UUID.randomUUID().toString());

		clss.setName(name);
		clss.setUri(pkg.getUri());
		clss.setAbstract(isAbstract);
		
		ClassProvisioning classProv = new ClassProvisioning();
		clss.setClassProvisioning(classProv);
		classProv.setOriginatingClassName(javaClass.getSimpleName());

		org.plasma.sdo.annotation.Alias srcAlias = javaClass.getAnnotation(org.plasma.sdo.annotation.Alias.class);

		if (srcAlias != null) {
			Alias alias = createAlias(srcAlias);
			if (alias != null) {
				clss.setAlias(alias);		
			    if (alias.getLocalName() != null && alias.getLocalName().length() > 0)
				    log.warn("alias local name for property should not be used in this context - overwriting local name for " + clss.getName());
				alias.setLocalName(javaClass.getSimpleName());
			}
		}
		else {
			Alias alias = new Alias();
			clss.setAlias(alias);
			alias.setLocalName(javaClass.getSimpleName());			
		}
		
		// check for unresolvable name collision of annotated class/enum with provisioning target
		String localName = pkg.getAlias().getLocalName() + "." + clss.getAlias().getLocalName();
		String targetName = pkg.getNamespaceProvisioning().getPackageName() + "." + clss.getName();
		if (localName.equals(targetName))
			log.warn("potential unresolvable name collision: " + targetName);		
		
		for (java.lang.Class<?> gen : generalizations) {
			java.lang.Package genJavaPkg = gen.getPackage();
			Package genPkg = this.packageURIMap.get(genJavaPkg.getName());
			Type genDataObject = gen.getAnnotation(Type.class);
			if (genDataObject == null)
				throw new MissingAnnotationException("expected " + Type.class.getName() 
						+ " annotation for enum class " + gen.getName());
			ClassRef ref = new ClassRef();
			ref.setName(genDataObject.name());
			ref.setUri(genPkg.getUri());
			clss.getSuperClasses().add(ref);
		}

		org.plasma.sdo.annotation.Comment srcComment = javaClass.getAnnotation(org.plasma.sdo.annotation.Comment.class);
		Documentation doc = new Documentation();
		doc.setType(DocumentationType.DEFINITION);
		Body body = new Body();
		doc.setBody(body);
		clss.getDocumentations().add(doc);
		if (srcComment != null && srcComment.body().length() > 0) {
			body.setValue(srcComment.body());
		} else {
			body.setValue("Derived from class " + javaClass.getName());
		}

		return clss;
	}

	private Property createDataProperty(DataProperty dataProperty, Field javaField, Enum sourceEnum, Class clss,
			Package pkg) throws NoSuchFieldException, SecurityException {
		Property property = createProperty(javaField, sourceEnum, dataProperty.isNullable(), dataProperty.isMany(), dataProperty.isReadOnly(),
				clss, pkg);
		clss.getProperties().add(property);

		DataType sdoType = dataProperty.dataType();
		TypeRef type = createDatatype(sdoType.name());
		property.setType(type);

		org.plasma.sdo.annotation.Key srcKey = javaField.getAnnotation(org.plasma.sdo.annotation.Key.class);
		if (srcKey != null) {
			Key key = new Key();
			// target provisioning enum is JAXB generated so upper case
			key.setType(KeyType.valueOf(srcKey.type().name().toUpperCase()));
			property.setKey(key);
		}

		org.plasma.sdo.annotation.Concurrent srcConcurrent = javaField
				.getAnnotation(org.plasma.sdo.annotation.Concurrent.class);
		if (srcConcurrent != null) {
			Concurrent conc = new Concurrent();
			// target provisioning enum is JAXB generated so upper case
			conc.setType(ConcurrencyType.valueOf(srcConcurrent.type().name().toUpperCase()));
			conc.setDataFlavor(ConcurentDataFlavor.valueOf(srcConcurrent.dataFlavor().name().toUpperCase()));
			property.setConcurrent(conc);
		}

		org.plasma.sdo.annotation.XmlProperty srcXmlProperty = javaField
				.getAnnotation(org.plasma.sdo.annotation.XmlProperty.class);
		if (srcXmlProperty != null) {
			XmlProperty xmlProp = new XmlProperty();
			// target provisioning enum is JAXB generated so upper case
			xmlProp.setNodeType(XmlNodeType.valueOf(srcXmlProperty.nodeType().name().toUpperCase()));
			property.setXmlProperty(xmlProp);
		}

		org.plasma.sdo.annotation.ValueConstraint srcValueConstraint = javaField
				.getAnnotation(org.plasma.sdo.annotation.ValueConstraint.class);
		if (srcValueConstraint != null) {
			ValueConstraint valueConstraint = new ValueConstraint();
			if (srcValueConstraint.fractionDigits() != null && srcValueConstraint.fractionDigits().length() > 0)
				valueConstraint.setFractionDigits(srcValueConstraint.fractionDigits());
			if (srcValueConstraint.maxExclusive() != null && srcValueConstraint.maxExclusive().length() > 0)
				valueConstraint.setMaxExclusive(srcValueConstraint.maxExclusive());
			if (srcValueConstraint.maxInclusive() != null && srcValueConstraint.maxInclusive().length() > 0)
				valueConstraint.setMaxInclusive(srcValueConstraint.maxInclusive());
			if (srcValueConstraint.maxLength() != null && srcValueConstraint.maxLength().length() > 0)
				valueConstraint.setMaxLength(srcValueConstraint.maxLength());
			if (srcValueConstraint.minExclusive() != null && srcValueConstraint.minExclusive().length() > 0)
				valueConstraint.setMinExclusive(srcValueConstraint.minExclusive());
			if (srcValueConstraint.minInclusive() != null && srcValueConstraint.minInclusive().length() > 0)
				valueConstraint.setMinInclusive(srcValueConstraint.minInclusive());
			if (srcValueConstraint.minLength() != null && srcValueConstraint.minLength().length() > 0)
				valueConstraint.setMinLength(srcValueConstraint.minLength());
			if (srcValueConstraint.pattern() != null && srcValueConstraint.pattern().length() > 0)
				valueConstraint.setPattern(srcValueConstraint.pattern());

			property.setValueConstraint(valueConstraint);
		}

		org.plasma.sdo.annotation.EnumConstraint srcEnumerationConstraint = javaField.getAnnotation(org.plasma.sdo.annotation.EnumConstraint.class);
		if (srcEnumerationConstraint != null) {
			// FIXME - really, same URI as class??
			String enumKey = clss.getUri() + "#" + srcEnumerationConstraint.targetEnum().getSimpleName();
			
			Enumeration enumeration = this.enumerationMap.get(enumKey);
			if (enumeration == null) {
				enumeration = createEnumeration(clss, srcEnumerationConstraint.targetEnum());
				pkg.getEnumerations().add(enumeration);
				this.enumerationMap.put(enumKey, enumeration);
			}

			EnumerationConstraint enumConstraint = new EnumerationConstraint();
			EnumerationRef enumRef = new EnumerationRef();
			enumRef.setName(enumeration.getName());
			enumRef.setUri(enumeration.getUri());
			enumConstraint.setValue(enumRef);
			property.setEnumerationConstraint(enumConstraint);
		}
		return property;
	}

	private Property createReferenceProperty(ReferenceProperty referenceProperty, Field javaField, Enum sourceEnum,
			Class clss, Package pkg) {
		Property property = createProperty(javaField, sourceEnum, referenceProperty.isNullable(), referenceProperty.isMany(),
				referenceProperty.readOnly(), clss, pkg);
		clss.getProperties().add(property);
		String qualifiedName = null;

		java.lang.Class<?> targetJavaClass = referenceProperty.targetClass();
		java.lang.Package targetJavaPkg = targetJavaClass.getPackage();
		Package targetPackage = this.packageURIMap.get(targetJavaPkg.getName());
		Type targetDataObject = targetJavaClass.getAnnotation(Type.class);

		qualifiedName = targetPackage.getUri() + "#" + targetDataObject.name();

		Class targetPropertyClass = this.classMap.get(qualifiedName);
		if (targetPropertyClass == null)
			throw new ProvisioningException("could not find class, " + qualifiedName);

		ClassRef ref = new ClassRef();
		ref.setName(targetPropertyClass.getName());
		ref.setUri(targetPropertyClass.getUri());
		property.setType(ref);
		property.setOpposite(referenceProperty.targetProperty());
		return property;
	}

	private Property createProperty(Field javaField, Enum sourceEnum, boolean isNullable, boolean isMany, boolean isReadOnly,
			Class clss, Package pkg) {
		Property property = new Property();
		property.setId(UUID.randomUUID().toString());
		property.setName(NameUtils.toCamelCase(sourceEnum.name()));
		property.setNullable(isNullable);
		property.setReadOnly(isReadOnly);
		property.setMany(isMany);
		property.setVisibility(VisibilityType.PUBLIC);
		
		PropertyProvisioning propProv = new PropertyProvisioning();
		property.setPropertyProvisioning(propProv);
		propProv.setOriginatingPropertyName(sourceEnum.name());

		org.plasma.sdo.annotation.Alias srcAlias = javaField.getAnnotation(org.plasma.sdo.annotation.Alias.class);
		if (srcAlias != null) {
			Alias alias = createAlias(srcAlias);
			if (alias != null) {
				property.setAlias(alias);
			    if (alias.getLocalName() != null && alias.getLocalName().length() > 0)
				    log.warn("alias local name for property should not be used in this context - overwriting local name for " + property.getName());
				alias.setLocalName(sourceEnum.name());
			}
		}
		else {
			Alias alias = new Alias();
			property.setAlias(alias);
			alias.setLocalName(sourceEnum.name());			
		}

		org.plasma.sdo.annotation.Sort srcSort = javaField.getAnnotation(org.plasma.sdo.annotation.Sort.class);
		if (srcSort != null) {
			Sort sequence = new Sort();
			sequence.setKey(srcSort.key());
			property.setSort(sequence);
		}

		org.plasma.sdo.annotation.Comment srcComment = javaField.getAnnotation(org.plasma.sdo.annotation.Comment.class);
		Documentation doc = new Documentation();
		doc.setType(DocumentationType.DEFINITION);
		Body body = new Body();
		doc.setBody(body);
		property.getDocumentations().add(doc);
		if (srcComment != null && srcComment.body().length() > 0) {
			body.setValue(srcComment.body());
		} else {
			body.setValue("Derived from field " + javaField.getName());
		}

		return property;
	}
	
	private Enumeration createEnumeration(Class clss, java.lang.Class<?> srcEnumClass) throws NoSuchFieldException, SecurityException {
		
		org.plasma.sdo.annotation.Enumeration dataEnumeration = srcEnumClass.getAnnotation(org.plasma.sdo.annotation.Enumeration.class);
		if (dataEnumeration == null)
			throw new MissingAnnotationException("expected " + org.plasma.sdo.annotation.Enumeration.class.getName() 
					+ " annotation for enum class " + srcEnumClass.getName());
		
		Enumeration enumeration = new Enumeration();
		enumeration.setName(dataEnumeration.name());
		enumeration.setUri(clss.getUri()); // FIXME - a bad assumption that these are same URI
		enumeration.setId(UUID.randomUUID().toString());
		org.plasma.sdo.annotation.Alias srcAlias = srcEnumClass.getAnnotation(org.plasma.sdo.annotation.Alias.class);
		if (srcAlias != null) {
			Alias alias = createAlias(srcAlias);
			if (alias != null)
				enumeration.setAlias(alias);			
		}				
	    for (Object o : srcEnumClass.getEnumConstants()) {
	    	Enum<?> enm = (Enum<?>) o;
			Field field = srcEnumClass.getField(enm.name());
			EnumerationLiteral literal = new EnumerationLiteral();
			literal.setName(enm.name());
			literal.setValue(enm.name());
			org.plasma.sdo.annotation.Alias fieldAlias = field.getAnnotation(org.plasma.sdo.annotation.Alias.class);
			if (fieldAlias != null) {
				Alias alias = createAlias(fieldAlias);
				if (alias != null)
				    literal.setAlias(alias);			
			}				

			org.plasma.sdo.annotation.Comment srcComment = field.getAnnotation(org.plasma.sdo.annotation.Comment.class);
			Documentation doc = new Documentation();
			doc.setType(DocumentationType.DEFINITION);
			Body body = new Body();
			doc.setBody(body);
			literal.getDocumentations().add(doc);
			if (srcComment != null && srcComment.body().length() > 0) {
				body.setValue(srcComment.body());
			} else {
				body.setValue("Derived from field " + field.getName());
			}
			
			enumeration.getEnumerationLiterals().add(literal);
		}		
		
		return enumeration;
	}
	
	private Alias createAlias(String nameToken, Namespace namespace, java.lang.Package javaPackage) {
		Alias alias = new Alias();
		alias.setPhysicalName(nameToken);
		alias.setLocalName(javaPackage.getName());
		return alias;
	}
	
	private Alias createAlias(org.plasma.sdo.annotation.Alias srcAlias) {
		Alias alias = null;
		if (srcAlias.physicalName() != null && srcAlias.physicalName().trim().length() > 0) {
			if (alias == null) {
				alias = new Alias();
			}
		    alias.setPhysicalName(srcAlias.physicalName());
		}
		if (srcAlias.localName() != null && srcAlias.localName().trim().length() > 0) {
			if (alias == null) {
				alias = new Alias();
			}
		    alias.setLocalName(srcAlias.localName());
		}
		if (srcAlias.businessName() != null && srcAlias.businessName().trim().length() > 0) {
			if (alias == null) {
				alias = new Alias();
			}
		    alias.setBusinessName(srcAlias.businessName());
		}
		return alias;
	}

	private DataTypeRef createDatatype(String name) {
		DataTypeRef dataTypeRef = new DataTypeRef();
		dataTypeRef.setName(name);
		dataTypeRef.setUri(PlasmaConfig.getInstance().getSDODataTypesNamespace().getUri());
		return dataTypeRef;
	}
}
