package org.plasma.text.lang3gl.java;

import org.plasma.provisioning.Class;
import org.plasma.provisioning.Enumeration;
import org.plasma.provisioning.EnumerationLiteral;
import org.plasma.provisioning.Package;
import org.plasma.sdo.PlasmaEnum;
import org.plasma.text.lang3gl.EnumerationFactory;
import org.plasma.text.lang3gl.Lang3GLContext;

public class SDOEnumerationFactory extends SDODefaultFactory
    implements EnumerationFactory {

	public SDOEnumerationFactory(Lang3GLContext context) {
		super(context);
	}
	

	public String createFileName(Enumeration clss) {
		StringBuilder buf = new StringBuilder();
		buf.append(clss.getName());
		buf.append(".java");		
		return buf.toString();
	}

	public String createContent(Package pkg, Enumeration enm) {
		StringBuilder buf = new StringBuilder();
		buf.append(this.createPackageDeclaration(pkg));
		buf.append(LINE_SEP);
		buf.append(this.createThirdPartyImportDeclarations(pkg, null));
		buf.append(LINE_SEP);
		buf.append(this.createSDOInterfaceReferenceImportDeclarations(pkg, null));
		buf.append(LINE_SEP);
		buf.append(LINE_SEP);
		buf.append(this.createTypeDeclaration(pkg, enm));
		buf.append(LINE_SEP);
		buf.append(this.beginBody());
		
		buf.append(LINE_SEP);
		buf.append(this.createStaticFieldDeclarations(enm));
		buf.append(LINE_SEP);
		buf.append(this.createPrivateFieldDeclarations(enm));
		buf.append(LINE_SEP);
		buf.append(this.createConstructors(pkg, enm));
		buf.append(LINE_SEP);
		buf.append(this.createOperations(pkg, enm));
		
		for (EnumerationLiteral lit : enm.getEnumerationLiterals()) {
			buf.append(LINE_SEP);
			buf.append(this.createOperations(pkg, enm, lit));
		}		
		
		buf.append(LINE_SEP);
		buf.append(this.endBody());
		return buf.toString();
	}
	
	protected String createConstructors(Package pkg, Enumeration enm) {
		StringBuilder buf = new StringBuilder();
		buf.append(this.newline(1));
		buf.append("private ");
		buf.append(enm.getName());
		buf.append("(String instanceName, String description) {");
		buf.append(this.newline(1));
		buf.append("    this.instanceName = instanceName;");
		buf.append(this.newline(1));
		buf.append("    this.description = description;");
		buf.append(this.newline(1));
		buf.append("}");
		buf.append(LINE_SEP);        
		return buf.toString();
	}

	protected String createOperations(Package pkg, Enumeration enm) {
		StringBuilder buf = new StringBuilder();

		buf.append(this.newline(1));
		buf.append("/**"); 
		buf.append(this.newline(1));
		buf.append("* Returns the logical name associated with this enumeration literal.");
		buf.append(this.newline(1));
		buf.append("*/"); 
		buf.append(this.newline(1));
		buf.append("public String getName()");
		buf.append(this.newline(1));
		buf.append("{");
		buf.append(this.newline(1));
		buf.append("    return this.name();");
		buf.append(this.newline(1));
		buf.append("}");
		buf.append(LINE_SEP);        

		buf.append(this.newline(1));
		buf.append("/**"); 
		buf.append(this.newline(1));
		buf.append("* Returns the physical or instance name associated with this enumeration literal.");
		buf.append(this.newline(1));
		buf.append("*/"); 
		buf.append(this.newline(1));
		buf.append("public String getInstanceName() {");
		buf.append(this.newline(1));
		buf.append("    return this.instanceName;");
		buf.append(this.newline(1));
		buf.append("}");
		buf.append(LINE_SEP);        

		buf.append(this.newline(1));
		buf.append("/**"); 
		buf.append(this.newline(1));
		buf.append("* Returns the descriptive text associated with this enumeration literal.");
		buf.append(this.newline(1));
		buf.append("*/"); 
		buf.append(this.newline(1));
		buf.append("public String getDescription() {");
		buf.append(this.newline(1));
		buf.append("    return this.description;");
		buf.append(this.newline(1));
		buf.append("}");
		buf.append(LINE_SEP);        

		buf.append(this.newline(1));
		buf.append("/**"); 
		buf.append(this.newline(1));
		buf.append("* Returns the enum values for this class as an array of implemented interfaces");
		buf.append(this.newline(1));
		buf.append("* @see "); 
		buf.append(PlasmaEnum.class.getSimpleName()); 
		buf.append(this.newline(1));
		buf.append("*/"); 
		buf.append(this.newline(1));
		buf.append("public static ");
		buf.append(PlasmaEnum.class.getSimpleName());
		buf.append("[] enumValues()");
		buf.append(this.newline(1));
		buf.append("{");
		buf.append(this.newline(1));
		buf.append("    return values();");
		buf.append(this.newline(1));
		buf.append("}");
		buf.append(LINE_SEP);   
		
		buf.append(this.newline(1));
		buf.append("/**"); 
		buf.append(this.newline(1));
		buf.append("* Returns the enumeration value matching the given name.");
		buf.append(this.newline(1));
		buf.append("*/"); 
		buf.append(this.newline(1));
		buf.append("public static ");
		buf.append(enm.getName());
		buf.append(" fromName(String name) {");
		buf.append(this.newline(2));
		buf.append("for (");
		buf.append(PlasmaEnum.class.getSimpleName());
		buf.append(" enm : enumValues()) {");
		buf.append(this.newline(3));
		buf.append("if (enm.getName().equals(name))");
		buf.append(this.newline(4));
		buf.append("return (");
		buf.append(enm.getName());
		buf.append(")enm;");
		buf.append(this.newline(2));
		buf.append("}");
		buf.append(this.newline(3));
		buf.append("throw new ");
		buf.append(IllegalArgumentException.class.getSimpleName());
		buf.append("(\"no enumeration value found for name '\" + name + \"'\");");
		buf.append(this.newline(1));
		buf.append("}");		
		buf.append(LINE_SEP);   

		buf.append(this.newline(1));
		buf.append("/**"); 
		buf.append(this.newline(1));
		buf.append("* Returns the enumeration value matching the given physical or instance name.");
		buf.append(this.newline(1));
		buf.append("*/"); 
		buf.append(this.newline(1));
		buf.append("public static ");
		buf.append(enm.getName());
		buf.append(" fromInstanceName(String instanceName) {");
		buf.append(this.newline(2));
		buf.append("for (");
		buf.append(PlasmaEnum.class.getSimpleName());
		buf.append(" enm : enumValues()) {");
		buf.append(this.newline(3));
		buf.append("if (enm.getInstanceName().equals(instanceName))");
		buf.append(this.newline(4));
		buf.append("return (");
		buf.append(enm.getName());
		buf.append(")enm;");
		buf.append(this.newline(2));
		buf.append("}");
		buf.append(this.newline(3));
		buf.append("throw new ");
		buf.append(IllegalArgumentException.class.getSimpleName());
		buf.append("(\"no enumeration value found for instance name '\" + instanceName + \"'\");");
		buf.append(this.newline(1));
		buf.append("}");
		
		return buf.toString();
	}

	protected String createOperations(Package pkg, Enumeration enm,
			EnumerationLiteral lit) {
		// TODO Auto-generated method stub
		return "";
	}

	protected String createSDOInterfaceReferenceImportDeclarations(Package pkg, Class clss) {
		StringBuilder buf = new StringBuilder();
		return buf.toString();
	}

	protected String createThirdPartyImportDeclarations(Package pkg, Class clss) {
		StringBuilder buf = new StringBuilder();
		buf.append("import ");
		buf.append(PlasmaEnum.class.getName());
		buf.append(";");
		return buf.toString();
	}
	
	protected String createStaticFieldDeclarations(Enumeration enumeration) {
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < enumeration.getEnumerationLiterals().size(); i++) {
			EnumerationLiteral literal = enumeration.getEnumerationLiterals().get(i);
			if (i > 0)
    			buf.append(",");    					
			buf.append(LINE_SEP);
			buf.append(this.indent(1));
			buf.append(toEnumLiteralName(literal.getName()));
			if (literal.getAlias() != null && 
				literal.getAlias().getPhysicalName() != null && 
				literal.getAlias().getPhysicalName().length() > 0) {
			    buf.append("(\"");
			    buf.append(literal.getAlias().getPhysicalName());
			    buf.append("\"");
			}
			else {
			    buf.append("(null");
			}
			buf.append(",\"");
			if (literal.getDocumentations() != null && literal.getDocumentations().size() > 0) {
				String body = literal.getDocumentations().get(0).getBody().getValue();			    
				body = replaceQuot(body);
				body = body.replace('\n', ' ');
				buf.append(body);
			}
			buf.append("\")");
		}
	    buf.append(";");    					
		buf.append(LINE_SEP);
		return buf.toString();
	}
	
	private String replaceQuot(String s) {
	   return s.replaceAll("\"", "\\\\\""); 	
	}

	protected String createTypeDeclaration(Package pkg, Enumeration enumeration) {
		StringBuilder buf = new StringBuilder();
		buf.append("public enum ");
		buf.append(enumeration.getName());
		buf.append(" implements ");
		buf.append(PlasmaEnum.class.getSimpleName());
		return buf.toString();
	}

	protected String createPrivateFieldDeclarations(Enumeration enm) {
		StringBuilder buf = new StringBuilder();
		buf.append(this.indent(1));
		buf.append("private String instanceName;");
		buf.append(LINE_SEP);        
		buf.append(this.indent(1));
		buf.append("private String description;");
		return buf.toString();
	}


}
