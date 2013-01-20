package org.plasma.text.lang3gl.java;

import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.plasma.common.WordWrap;
import org.plasma.config.Namespace;
import org.plasma.config.PlasmaConfig;
import org.plasma.provisioning.Class;
import org.plasma.provisioning.ClassRef;
import org.plasma.provisioning.Documentation;
import org.plasma.provisioning.Package;
import org.plasma.provisioning.Property;
import org.plasma.text.lang3gl.InterfaceFactory;
import org.plasma.text.lang3gl.Lang3GLContext;


public class JDOInterfaceFactory extends JDODefaultFactory 
    implements InterfaceFactory {

	public JDOInterfaceFactory(Lang3GLContext context) {
		super(context);
	}
		
	public String createContent(Package pkg, Class clss) {
		StringBuilder buf = new StringBuilder();
		
		buf.append(this.createPackageDeclaration(pkg));
		buf.append(LINE_SEP);
		buf.append(this.createInterfaceReferenceImportDeclarations(pkg, clss));
		buf.append(LINE_SEP);
		buf.append(LINE_SEP);
		buf.append(this.createTypeDeclaration(pkg, clss));
		buf.append(LINE_SEP);
		buf.append(this.beginBody());

		buf.append(LINE_SEP);
		buf.append(this.createMethodDeclarations(clss));
		
		for (Property field : clss.getProperties()) {
			buf.append(LINE_SEP);
			buf.append(this.createMethodDeclarations(clss, field));
		}
		
		buf.append(LINE_SEP);
		buf.append(this.endBody());
		return buf.toString();
	}

	protected String createThirdPartyImportDeclarations(Package pkg, Class clss) {
		StringBuilder buf = new StringBuilder();
		
		// FIXME: add array/list accessor collection config option
		//if (!hasOnlySingilarFields(clss)) {
		//	buf.append(LINE_SEP);
		//	buf.append(this.createImportDeclaration(pkg, clss, List.class.getName()));
		//}
		return buf.toString();
	}
	
	protected String createStaticFieldDeclarations(Class clss) {
		StringBuilder buf = new StringBuilder();
		
		// the namespace URI
		buf.append(this.indent(1));
		buf.append("/** The SDO namespace URI associated with the SDO Type for this class */");
		buf.append(LINE_SEP);
		buf.append(this.indent(1));
		buf.append("public static final String NAMESPACE_URI = \"");
		buf.append(clss.getUri());
		buf.append("\";");
		buf.append(LINE_SEP);
		
		//the entity name
		buf.append(LINE_SEP);
		buf.append(this.indent(1));
		buf.append("public static final String ETY_");
		buf.append(toConstantName(clss.getName()));
		buf.append(" \t= \"");
		buf.append(clss.getName());
		buf.append("\";");

		// the fields
		for (Property field : clss.getProperties()) {
		    buf.append(LINE_SEP);	
			
		    String javadoc = createStaticFieldDeclarationJavadoc(clss, field);
		    buf.append(javadoc);
			
			buf.append(LINE_SEP);
			buf.append(this.indent(1));
			buf.append("public static final String PTY_");
			buf.append(toConstantName(field.getName()));
			buf.append(" \t= \"");
			buf.append(field.getName());
			buf.append("\";");
		}

		return buf.toString();
	}

	private String createStaticFieldDeclarationJavadoc(Class clss, Property field)
	{
		StringBuilder buf = new StringBuilder();
		buf.append(LINE_SEP);
		buf.append(this.indent(1));
		buf.append("/**"); // begin javadoc
		
		// add formatted doc from UML or derived default doc
		if (field.getDocumentations() != null) {
			for (Documentation doc : field.getDocumentations()) {
				String docText = doc.getBody().getValue().trim();
				String wrappedDoc = WordWrap.wordWrap(docText, 60, Locale.ENGLISH);
				String[] docLines = wrappedDoc.split("\n");
				for (String line : docLines) {
				    buf.append(LINE_SEP);	
					buf.append(this.indent(1));
					buf.append(" * ");
				    buf.append(line); 
				}
		    }
		}
		else {
		    buf.append(LINE_SEP);	
			buf.append(this.indent(1));
			buf.append(" * The logical property <b>");
			buf.append(field.getName());
			buf.append("</b> which is part of the SDO Type <b>");
			buf.append(clss.getUri() + "#" + clss.getName());
			buf.append("</b>."); 			
		}
		
		// data store mapping
		if (clss.getAlias() != null && clss.getAlias().getPhysicalName() != null && 
				field.getAlias() != null && field.getAlias().getPhysicalName() != null) {
		    buf.append(LINE_SEP);	
			buf.append(this.indent(1));
			buf.append(" *"); 
		    buf.append(LINE_SEP);	
			buf.append(this.indent(1));
			buf.append(" * <p></p>");
		    buf.append(LINE_SEP);	
			buf.append(this.indent(1));
			buf.append(" * <b>Data Store Mapping:</b>");
		    buf.append(LINE_SEP);	
			buf.append(this.indent(1));
			buf.append(" * Corresponds to the physical data store field <b>");
			buf.append(clss.getAlias().getPhysicalName() + "." + field.getAlias().getPhysicalName());
			buf.append("</b>.");
		    buf.append(LINE_SEP);	
			buf.append(this.indent(1));
			buf.append(" * <p></p>");		
		}		
		
		buf.append(LINE_SEP);
		buf.append(this.indent(1));
		buf.append(" */"); // end javadoc			
		return buf.toString();
	}
	
	protected String createTypeDeclaration(Package pkg, Class clss) {
		StringBuilder buf = new StringBuilder();
		
		JDOInterfaceNameResolver itfNameResolver = new JDOInterfaceNameResolver();
		String javadoc = createTypeDeclarationJavadoc(pkg, clss);
		buf.append(javadoc);
		
	    buf.append(LINE_SEP);	
		buf.append("public interface ");
		
		buf.append(itfNameResolver.getName(clss));
		if (clss.getSuperClasses() != null && clss.getSuperClasses().size() > 0) {
			buf.append(" extends ");
		    int i = 0;
			for (ClassRef ref : clss.getSuperClasses()) {
				if (i > 0)
			        buf.append(", ");
				buf.append(ref.getName());
				i++;
		    }	
		}		
			
		return buf.toString();
	}
	
	
	private String createTypeDeclarationJavadoc(Package pkg, Class clss) {
		StringBuilder buf = new StringBuilder();
		
		buf.append("/**"); // begin javadoc
		
		// add formatted doc from UML or derived default doc
		if (clss.getDocumentations() != null) {
			for (Documentation doc : clss.getDocumentations()) {
				String docText = doc.getBody().getValue().trim();
				String wrappedDoc = WordWrap.wordWrap(docText, 60, Locale.ENGLISH);
				String[] docLines = wrappedDoc.split("\n");
				for (String line : docLines) {
				    buf.append(LINE_SEP);	
					buf.append(" * ");
				    buf.append(line); 
				}
		    }
		}
		else {
		    buf.append(LINE_SEP);	
			buf.append(" * An SDO interface representing <b>");
			buf.append(clss.getName());
			buf.append("</b> which is part of the SDO namespace <b>");
			buf.append(clss.getUri());
			buf.append("</b>."); 			
		}
		
		// data store mapping
		if (clss.getAlias() != null && clss.getAlias().getPhysicalName() != null) {
		    buf.append(LINE_SEP);	
			buf.append(" *"); 
		    buf.append(LINE_SEP);	
			buf.append(" * <p></p>");
		    buf.append(LINE_SEP);	
			buf.append(" * <b>Data Store Mapping:</b>");
		    buf.append(LINE_SEP);	
			buf.append(" * Corresponds to the physical data store entity <b>");
			buf.append(clss.getAlias().getPhysicalName());
			buf.append("</b>.");
		    buf.append(LINE_SEP);	
			buf.append(" * <p></p>");		
		    buf.append(LINE_SEP);	
			buf.append(" *"); 
		}
		

		// add @see items for referenced classes
		Map<String, Class> classMap = new TreeMap<String, Class>();
		if (clss.getSuperClasses() != null && clss.getSuperClasses().size() > 0)		
		    this.collectProvisioningSuperclasses(pkg, clss, classMap);
		//for interfaces we have definitions for all methods generated
		// based on local fields, not fields from superclasses
		collectProvisioningClasses(pkg, clss, classMap);
		for (Class refClass : classMap.values()) {
			Namespace sdoNamespace = PlasmaConfig.getInstance().getSDONamespaceByURI(refClass.getUri());
			String packageName = sdoNamespace.getProvisioning().getPackageName();
			String packageQualifiedName = packageName + "." + refClass.getName(); 	
		    buf.append(LINE_SEP);	
			buf.append(" * @see ");
			buf.append(packageQualifiedName);
			buf.append(" ");
			buf.append(refClass.getName());			
		}
		
		
	    buf.append(LINE_SEP);	
		buf.append(" */"); // end javadoc
		
		return buf.toString();
	}

	protected String createMethodDeclarations(Class clss) {
		// TODO Auto-generated method stub
		return "";
	}

	protected String createMethodDeclarations(Class clss, Property field) {
		StringBuilder buf = new StringBuilder();
		TypeClassInfo typeClassName = this.getTypeClassName(field.getType());
		
		if (!field.isMany()) {
			buf.append(LINE_SEP);			    
			createSingularGetterDeclaration(null, clss, field, typeClassName, buf);
			buf.append(";");
			
			buf.append(LINE_SEP);			    
			createSingularSetterDeclaration(null, clss, field, typeClassName, buf);
			buf.append(";");
		}
		else {
			buf.append(LINE_SEP);			    
			createManyGetterDeclaration(null, clss, field, typeClassName, buf);
			buf.append(";");
			
			buf.append(LINE_SEP);			    
			createManyIndexGetterDeclaration(null, clss, field, typeClassName, buf);
			buf.append(";");
			
			buf.append(LINE_SEP);			    
			createManyCountDeclaration(null, clss, field, typeClassName, buf);
			buf.append(";");

			buf.append(LINE_SEP);			    
			createManySetterDeclaration(null, clss, field, typeClassName, buf);
			buf.append(";");

			buf.append(LINE_SEP);			    
			createManyAdderDeclaration(null, clss, field, typeClassName, buf);
			buf.append(";");
			
			buf.append(LINE_SEP);			    
			createManyRemoverDeclaration(null, clss, field, typeClassName, buf);
			buf.append(";");			
			
		}		
		
		return buf.toString();
	}
	
	public String createFileName(Class clss) {
		StringBuilder buf = new StringBuilder();
		buf.append(clss.getName());
		buf.append(".java");		
		return buf.toString();
	}
	
}
