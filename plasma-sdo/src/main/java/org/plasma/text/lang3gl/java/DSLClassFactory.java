package org.plasma.text.lang3gl.java;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.plasma.config.Namespace;
import org.plasma.config.PlasmaConfig;
import org.plasma.provisioning.Class;
import org.plasma.provisioning.ClassRef;
import org.plasma.provisioning.Package;
import org.plasma.provisioning.Property;
import org.plasma.provisioning.adapter.FieldAdapter;
import org.plasma.query.DataProperty;
import org.plasma.query.Expression;
import org.plasma.query.dsl.DataNode;
import org.plasma.query.dsl.DomainRoot;
import org.plasma.query.dsl.PathNode;
import org.plasma.sdo.helper.PlasmaTypeHelper;
import org.plasma.text.lang3gl.ClassFactory;
import org.plasma.text.lang3gl.Lang3GLContext;
import org.plasma.text.lang3gl.ClassNameResolver;


public class DSLClassFactory extends SDODefaultFactory 
    implements ClassFactory {

	protected SDOInterfaceNameResolver sdoInterfaceResolver = new SDOInterfaceNameResolver();
    protected DSLClassNameResolver dslClassNameResolver = new DSLClassNameResolver();
	
	public DSLClassFactory(Lang3GLContext context) {
		super(context);
	}
	
	public String createFileName(Class clss) {
		StringBuilder buf = new StringBuilder();
		String name = this.dslClassNameResolver.getName(clss);
		buf.append(name);
		buf.append(".java");		
		return buf.toString();
	}
	
	public String createContent(Package pkg, Class clss) {
		StringBuilder buf = new StringBuilder();
		
		buf.append(this.createPackageDeclaration(pkg));
		buf.append(LINE_SEP);
		buf.append(this.createThirdPartyImportDeclarations(pkg, clss));
		buf.append(LINE_SEP);
		buf.append(this.createSDOInterfaceReferenceImportDeclarations(pkg, clss));
		buf.append(LINE_SEP);
		buf.append(this.createDSLClassReferenceImportDeclarations(pkg, clss));
		
		Namespace sdoNamespace = PlasmaConfig.getInstance().getSDONamespaceByURI(pkg.getUri());
		String interfacePackageName = sdoNamespace.getProvisioning().getPackageName();
        String packageName = this.createPackageName(pkg);		
		
		// if impl class is in different package, need import
		if (!packageName.equals(interfacePackageName)) {
			buf.append(LINE_SEP);
			String qualifiedName = interfacePackageName + "." 
			    + sdoInterfaceResolver.getName(clss); 
			buf.append(this.createImportDeclaration(pkg, clss, qualifiedName));
		}
		
		buf.append(LINE_SEP);
		buf.append(LINE_SEP);
		buf.append(this.createTypeDeclaration(pkg, clss));
		buf.append(LINE_SEP);
		buf.append(this.beginBody());
		
		buf.append(LINE_SEP);

		for (Property field : clss.getProperties()) {
			buf.append(this.createPrivateFieldDeclaration(clss, field));
		}
				
		buf.append(LINE_SEP);
		buf.append(this.createConstructors(pkg, clss));    			
		buf.append(LINE_SEP);
		buf.append(this.createStaticOperations(pkg, clss));

		Map<String, FieldAdapter> fields = new TreeMap<String, FieldAdapter>();
		collectProvisioningFields(pkg, clss, pkg, clss, fields);
		Iterator<String> fieldIter = fields.keySet().iterator();
		while (fieldIter.hasNext()) {
			String name = fieldIter.next();
			FieldAdapter adapter = fields.get(name);
			buf.append(LINE_SEP);
			buf.append(this.createOperations(adapter.getFieldPackage(), 
					adapter.getFieldClass(), adapter.getField()));
		}

		buf.append(LINE_SEP);
		buf.append(this.endBody());
		return buf.toString();
	}	
	
	protected String createPackageDeclaration(Package pkg) {
		StringBuilder buf = new StringBuilder();
		buf.append("package " + createPackageName(pkg));
		buf.append(";");
		return buf.toString();
	}

	protected String createTypeDeclaration(Package pkg, Class clss) {
		StringBuilder buf = new StringBuilder();
		buf.append(createTypeDeclarationJavadoc(pkg, clss));
	    buf.append(newline(0));	
		buf.append("public class ");
		buf.append(getImplementationClassName(clss));
		buf.append(" extends ");
		buf.append(DomainRoot.class.getSimpleName());
		
		return buf.toString();
	}

	private String createTypeDeclarationJavadoc(Package pkg, Class clss) {
		StringBuilder buf = new StringBuilder();
		
		buf.append("/**"); // begin javadoc
		
		// add formatted doc from UML if exists		
		// always put model definition first so it appears
		// on package summary line for class
		String docs = getWrappedDocmentations(clss.getDocumentations(), 0);
		if (docs.trim().length() > 0) {
		    buf.append(docs);
		    
		    // if we have model docs, set up the next section
		    buf.append(newline(0));	
			buf.append(" * <p></p>");
		}
		
	    buf.append(newline(0));	
		buf.append(" * Generated Domain Specific Language (DSL) implementation class representing the domain model entity <b>");
		buf.append(clss.getName());
		buf.append("</b>."); 
		
		// data store mapping
		if (clss.getAlias() != null && clss.getAlias().getPhysicalName() != null) {
		    buf.append(newline(0));	
			buf.append(" *"); 
		    buf.append(newline(0));	
			buf.append(" * <p></p>");
		    buf.append(newline(0));	
			buf.append(" * <b>Data Store Mapping:</b>");
		    buf.append(newline(0));	
			buf.append(" * Corresponds to the physical data store entity <b>");
			buf.append(clss.getAlias().getPhysicalName());
			buf.append("</b>.");
		    buf.append(newline(0));	
			buf.append(" *"); 
		}		

	    buf.append(newline(0));	
		buf.append(" */"); // end javadoc
		
		return buf.toString();
	}	
	
	protected String createConstructors(Package pkg, Class clss) {
		StringBuilder buf = new StringBuilder();

		buf.append(newline(1));
		buf.append("private ");
		buf.append(getImplementationClassName(clss));
		buf.append("() {");
		buf.append(newline(2));
		buf.append("super(");
		buf.append(PlasmaTypeHelper.class.getSimpleName());
		buf.append(".INSTANCE.getType(");
		buf.append(sdoInterfaceResolver.getName(clss));
		buf.append(".class));");
		buf.append(newline(1));
		buf.append("}");
		
		buf.append(newline(1));

		buf.append(newline(1));
		buf.append("/**"); // begin javadoc
		buf.append(newline(1));	
		buf.append(" * Constructor which instantiates a domain query path node. A path may");
		buf.append(newline(1));	
		buf.append(" * span multiple namespaces and therefore Java inplementation packages");
		buf.append(newline(1));	
		buf.append(" * based on the <a href=\"http://docs.plasma-sdo.org/api/org/plasma/config/PlasmaConfiguration.html\">Condiguration</a>.");
		buf.append(newline(1));	
		buf.append(" * Note: while this constructor is public, it is not for application use!");
	    
	    // params
	    buf.append(newline(1));	
		buf.append(" * @param source the source path node");
	    buf.append(newline(1));	
		buf.append(" * @param sourceProperty the source property logical name");
	    
	    buf.append(newline(1));	
		buf.append(" */"); // end javadoc
		
		buf.append(newline(1));
		buf.append("public ");
		buf.append(getImplementationClassName(clss));
		buf.append("(");
		buf.append(PathNode.class.getSimpleName());
		buf.append(" source, ");
		buf.append(String.class.getSimpleName());
		buf.append(" sourceProperty");
		buf.append(") {");
		buf.append(newline(2));
		buf.append("super(source, sourceProperty);");
		buf.append(newline(1));
		buf.append("}");

		buf.append(newline(1));

		buf.append(newline(1));
		buf.append("/**"); // begin javadoc
		buf.append(newline(1));	
		buf.append(" * Constructor which instantiates a domain query path node. A path may");
		buf.append(newline(1));	
		buf.append(" * span multiple namespaces and therefore Java inplementation packages");
		buf.append(newline(1));	
		buf.append(" * based on the <a href=\"http://docs.plasma-sdo.org/api/org/plasma/config/PlasmaConfiguration.html\">Condiguration</a>.");
		buf.append(newline(1));	
		buf.append(" * Note: while this constructor is public, it is not for application use!");
	    
	    // params
	    buf.append(newline(1));	
		buf.append(" * @param source the source path node");
	    buf.append(newline(1));	
		buf.append(" * @param sourceProperty the source property logical name");
	    buf.append(newline(1));	
		buf.append(" * @param expr the path predicate expression");
	    
	    buf.append(newline(1));	
		buf.append(" */"); // end javadoc
		
		buf.append(newline(1));
		buf.append("public ");
		buf.append(getImplementationClassName(clss));
		buf.append("(");
		buf.append(PathNode.class.getSimpleName());
		buf.append(" source, ");
		buf.append(String.class.getSimpleName());
		buf.append(" sourceProperty, ");
		buf.append(Expression.class.getSimpleName());
		buf.append(" expr");
		buf.append(") {");
		buf.append(newline(2));
		buf.append("super(source, sourceProperty, expr);");
		buf.append(newline(1));
		buf.append("}");
		
	    return buf.toString();
    }
	
	protected String createStaticOperations(Package pkg, Class clss) {
		StringBuilder buf = new StringBuilder();

		buf.append(this.newline(1));
		buf.append("/**"); // begin javadoc
		buf.append(newline(1));	
		buf.append(" * Returns a new DSL query for <a href=\"http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html\">Type</a> ");
		buf.append("<b>");	
		buf.append(clss.getName());
		buf.append("</b> which can be used either as a query root or");	
	    buf.append(newline(1));	
		buf.append(" * as the start (entry point) for a new path predicate expression.");	
	    
	    // return
	    buf.append(newline(1));	
		buf.append(" * @return a new DSL query");
	    
	    buf.append(newline(1));	
		buf.append(" */"); // end javadoc
		
		buf.append(newline(1));
		buf.append("public static "); 
		buf.append(getImplementationClassName(clss));
		buf.append(" newQuery() {"); 
		buf.append(newline(2));
		buf.append("return new "); 
		buf.append(getImplementationClassName(clss));
		buf.append("();"); 
		buf.append(newline(1));
		buf.append("}");
		
		return buf.toString();
	}
	
	protected String createOperations(Package pkg, Class clss) {
		StringBuilder buf = new StringBuilder();
		if (clss.getSuperClasses() != null)
		    for (ClassRef cref : clss.getSuperClasses()) {
			   Class sclss = this.context.findClass(cref);
			   for (Property field : sclss.getProperties()) {
				   String sclassOpers = createOperations(pkg, sclss, field);
				   buf.append(sclassOpers);
			   }
			   Package spkg = this.context.findPackage(cref);
			   buf.append(createOperations(spkg, sclss));
		    }
		return buf.toString();
	}

	protected String createOperations(Package pkg, Class clss, Property field) {
		
		StringBuilder buf = new StringBuilder();
		if (field.getType() instanceof ClassRef) {
			buf.append(createReferencePropertyOperation(pkg, clss, field));
			if (field.isMany()) {
			    buf.append(newline(1));
			    buf.append(createReferencePropertyPredicateOperation(pkg, clss, field));
			}
		}
		else {
			buf.append(createDataPropertyOperation(pkg, clss, field));
	    }
		

	    return buf.toString();
	}	
	
	protected String createDataPropertyOperation(Package pkg, Class clss, Property field) {
		StringBuilder buf = new StringBuilder();
		
		buf.append(this.newline(1));
		buf.append("/**"); // begin javadoc
		buf.append(newline(1));	
		buf.append(" * Returns a DSL data element for property, <b>");
		buf.append(field.getName());
		buf.append("</b>.");	
	    
	    // return
	    buf.append(newline(1));	
		buf.append(" * @return a DSL data element for property, <b>");
		buf.append(field.getName());
		buf.append("</b>.");	
	    
	    buf.append(newline(1));	
		buf.append(" */"); // end javadoc

		buf.append(newline(1));
		buf.append("public "); 
		buf.append(DataProperty.class.getSimpleName());
		buf.append(" "); 
		buf.append(field.getName()); 
		buf.append("() {"); 
		buf.append(newline(2));
		buf.append("return new ");
		buf.append(DataNode.class.getSimpleName());
		buf.append("(this, ");
	    buf.append(toQualifiedPropertyNameReference(clss, field));
		buf.append(");"); 
		buf.append(newline(1));
		buf.append("}");
		
		return buf.toString();
	}

	protected String createReferencePropertyOperation(Package pkg, Class clss, Property field) {
		StringBuilder buf = new StringBuilder();
		
        Class otherClass = this.context.findClass((ClassRef)field.getType());

		buf.append(this.newline(1));
		buf.append("/**"); // begin javadoc
		buf.append(newline(1));	
		buf.append(" * Returns a DSL query element for reference property, <b>");
		buf.append(field.getName());
		buf.append("</b>.");	
	    
	    // return
	    buf.append(newline(1));	
		buf.append(" * @return a DSL query element for reference property, <b>");
		buf.append(field.getName());
		buf.append("</b>.");	
	    
	    buf.append(newline(1));	
		buf.append(" */"); // end javadoc
        
        buf.append(newline(1));
		buf.append("public "); 		
		buf.append(getImplementationClassName(otherClass));
		buf.append(" "); 
		buf.append(field.getName()); 
		buf.append("() {"); 
		buf.append(newline(2));
		buf.append("return new ");
		buf.append(getImplementationClassName(otherClass));
		buf.append("(this, ");
	    buf.append(toQualifiedPropertyNameReference(clss, field));
		buf.append(");"); 
		buf.append(newline(1));
		buf.append("}");

		return buf.toString();
	}

	protected String createReferencePropertyPredicateOperation(Package pkg, Class clss, Property field) {
		StringBuilder buf = new StringBuilder();
		
        Class otherClass = this.context.findClass((ClassRef)field.getType());

		buf.append(this.newline(1));
		buf.append("/**"); // begin javadoc
		buf.append(newline(1));	
		buf.append(" * Returns a DSL query element for reference property, <b>");
		buf.append(field.getName());
		buf.append("</b>, while adding the given path predicate expression. ");	
		buf.append(newline(1));	
		buf.append(" * Path predicate expressions are used to restrict");	
		buf.append(newline(1));	
		buf.append(" * the query results for a collection property within a <a href=\"http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaDataGraph.html\">DataGraph</a>.");	
	    
	    // params
	    buf.append(newline(1));	
		buf.append(" * @param expr the path predicate expression");
		
	    // return
	    buf.append(newline(1));	
		buf.append(" * @return a DSL query element for reference property, <b>");
		buf.append(field.getName());
		buf.append("</b>.");	
	    
	    buf.append(newline(1));	
		buf.append(" */"); // end javadoc        
        
        buf.append(newline(1));
		buf.append("public "); 		
		buf.append(getImplementationClassName(otherClass));
		buf.append(" "); 
		buf.append(field.getName()); 
	    buf.append("("); 
	    buf.append(Expression.class.getSimpleName()); 
	    buf.append(" expr) {"); 
		buf.append(newline(2));
		buf.append("return new ");
		buf.append(getImplementationClassName(otherClass));
		buf.append("(this, ");
	    buf.append(toQualifiedPropertyNameReference(clss, field));
		buf.append(", expr);");
		buf.append(newline(1));
		buf.append("}");

		return buf.toString();
	}
	
	public String createDirectoryName(Package pkg) {
		Namespace sdoNamespace = PlasmaConfig.getInstance().getSDONamespaceByURI(pkg.getUri());
		String packageName = sdoNamespace.getProvisioning().getPackageName();
		String subpackage = PlasmaConfig.getInstance().getSDO().getGlobalProvisioning().getQueryDSL().getImplementation().getChildPackageName();		
		if (subpackage != null && subpackage.trim().length() > 0)
			packageName = packageName + "." + subpackage;			
		String packageDir = packageName.replace(".", "/");
		StringBuilder buf = new StringBuilder();
		buf.append(packageDir);
		return buf.toString();
	}

	protected String createPackageName(Package pkg) {
		Namespace sdoNamespace = PlasmaConfig.getInstance().getSDONamespaceByURI(pkg.getUri());
		String packageName = sdoNamespace.getProvisioning().getPackageName();
		String subpackage = PlasmaConfig.getInstance().getSDO().getGlobalProvisioning().getQueryDSL().getImplementation().getChildPackageName();		
		if (subpackage != null && subpackage.trim().length() > 0)
			packageName = packageName + "." + subpackage;
		StringBuilder buf = new StringBuilder();
		buf.append(packageName);
		return buf.toString();
	}
		
	
	protected String createPrivateFieldDeclaration(Class clss, Property field) {
		return ""; // no fixed fields, methods delegate to SDO DataObject impl class structures for data accces
	}

	protected String createThirdPartyImportDeclarations(Package pkg, Class clss) {
		StringBuilder buf = new StringBuilder();
		buf.append(LINE_SEP);
		buf.append(this.createImportDeclaration(pkg, clss, PlasmaTypeHelper.class.getName()));
		buf.append(LINE_SEP);
		buf.append(this.createImportDeclaration(pkg, clss, DomainRoot.class.getName()));
		buf.append(LINE_SEP);
		buf.append(this.createImportDeclaration(pkg, clss, PathNode.class.getName()));
		buf.append(LINE_SEP);
		buf.append(this.createImportDeclaration(pkg, clss, DataProperty.class.getName()));
		buf.append(LINE_SEP);
		buf.append(this.createImportDeclaration(pkg, clss, DataNode.class.getName()));
		buf.append(LINE_SEP);
		buf.append(this.createImportDeclaration(pkg, clss, Expression.class.getName()));
				
		return buf.toString();
	}

	protected String getImplementationClassName(Class clss) {		
		String name = this.dslClassNameResolver.getName(clss);
		return name;
	}
	
	protected String createDSLClassReferenceImportDeclarations(Package pkg, Class clss) {
		StringBuilder buf = new StringBuilder();
		
		ClassNameResolver resolver = new DSLClassNameResolver();
		Map<String, String> nameMap = new TreeMap<String, String>();
		collectReferenceFieldClassNamesDeep(pkg, clss, nameMap, resolver, true, -1);
		for (String name : nameMap.values()) {
		    buf.append(LINE_SEP);	
		    buf.append("import ");
		    buf.append(name);
		    buf.append(";");
		}
		
		return buf.toString();
	}
}
