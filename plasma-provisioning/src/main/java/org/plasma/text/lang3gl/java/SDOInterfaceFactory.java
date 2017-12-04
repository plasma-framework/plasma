/**
 * Copyright 2017 TerraMeta Software, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.plasma.text.lang3gl.java;

import java.util.Map;
import java.util.TreeMap;

import org.plasma.metamodel.Class;
import org.plasma.metamodel.ClassRef;
import org.plasma.metamodel.Package;
import org.plasma.metamodel.Property;
import org.plasma.runtime.InterfaceProvisioning;
import org.plasma.runtime.Namespace;
import org.plasma.runtime.PlasmaRuntime;
import org.plasma.sdo.PlasmaDataObject;
import org.plasma.text.TextBuilder;
import org.plasma.text.TextProvisioningException;
import org.plasma.text.lang3gl.ClassNameResolver;
import org.plasma.text.lang3gl.InterfaceFactory;
import org.plasma.text.lang3gl.Lang3GLContext;

public class SDOInterfaceFactory extends SDODefaultFactory implements InterfaceFactory {

  public SDOInterfaceFactory(Lang3GLContext context) {
    super(context);
  }

  public String createContent(Package pkg, Class clss) {
    TextBuilder buf = new TextBuilder(LINE_SEP, this.context.getIndentationToken());

    buf.append(this.createPackageDeclaration(pkg));
    buf.append(LINE_SEP);
    buf.append(this.createThirdPartyImportDeclarations(pkg, clss));
    buf.append(LINE_SEP);

    buf.append(this.createSDOInterfaceReferenceImportDeclarations(pkg, clss));
    buf.append(LINE_SEP);
    buf.append(LINE_SEP);
    buf.append(this.createTypeDeclaration(pkg, clss));
    buf.append(LINE_SEP);
    buf.append(this.beginBody());

    buf.append(LINE_SEP);
    buf.append(this.createStaticFieldDeclarations(pkg, clss));

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
    TextBuilder buf = new TextBuilder(LINE_SEP, this.context.getIndentationToken());

    // FIXME: add array/list accessor collection config option
    // if (!hasOnlySingilarFields(clss)) {
    // buf.append(LINE_SEP);
    // buf.append(this.createImportDeclaration(pkg, clss,
    // List.class.getName()));
    // }
    return buf.toString();
  }

  protected String createTypeDeclaration(Package pkg, Class clss) {
    TextBuilder buf = new TextBuilder(LINE_SEP, this.context.getIndentationToken());

    String javadoc = createTypeDeclarationJavadoc(pkg, clss);
    buf.append(javadoc);
    SDOInterfaceNameResolver interfaceResolver = new SDOInterfaceNameResolver();

    buf.append(LINE_SEP);
    buf.append("public interface ");
    buf.append(interfaceResolver.getName(clss));
    buf.append(" extends ");
    if (clss.getSuperClasses() != null && clss.getSuperClasses().size() > 0) {
      int i = 0;
      for (ClassRef ref : clss.getSuperClasses()) {
        if (i > 0)
          buf.append(", ");
        buf.append(ref.getName());
        i++;
      }
    } else {
      // always extends DO so we can cast from its impl to any generated
      // interface
      buf.append(PlasmaDataObject.class.getSimpleName());
    }

    return buf.toString();
  }

  private String createTypeDeclarationJavadoc(Package pkg, Class clss) {
    TextBuilder buf = new TextBuilder(LINE_SEP, this.context.getIndentationToken());

    buf.append("/**"); // begin javadoc

    // add formatted doc from UML if exists
    // always put model definition first so it appears
    // on package summary line for class
    String docs = getWrappedDocmentations(clss.getDocumentations(), 0);
    if (docs.trim().length() > 0) {
      buf.append(docs);

      // if we have model docs, set up the next section w/a "header"
      buf.append(newline(0));
      buf.append(" * <p></p>");
    }

    buf.append(newline(0));
    buf.append(" * Generated interface representing the domain model entity <b>");
    buf.append(clss.getName());
    buf.append("</b>. This <a href=\"http://plasma-sdo.org\">SDO</a> interface directly reflects the");
    buf.append(newline(0));
    buf.append(" * class (single or multiple) inheritance lattice of the source domain model(s) ");
    buf.append(" and is part of namespace <b>");
    buf.append(clss.getUri());
    buf.append("</b> defined within the <a href=\"http://docs.plasma-sdo.org/api/org/plasma/config/package-summary.html\">Configuration</a>.");

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
      buf.append(" * <p></p>");
      buf.append(newline(0));
      buf.append(" *");
    }

    // add @see items for referenced classes
    Map<String, Class> classMap = new TreeMap<String, Class>();
    if (clss.getSuperClasses() != null && clss.getSuperClasses().size() > 0)
      this.collectProvisioningSuperclasses(pkg, clss, classMap);
    // for interfaces we have definitions for all methods generated
    // based on local fields, not fields from superclasses
    collectProvisioningClasses(pkg, clss, classMap);
    for (Class refClass : classMap.values()) {
      Namespace sdoNamespace = PlasmaRuntime.getInstance().getSDONamespaceByURI(refClass.getUri());
      String packageName = sdoNamespace.getProvisioning().getPackageName();
      String packageQualifiedName = packageName + "." + refClass.getName();
      buf.append(newline(0));
      buf.append(" * @see ");
      buf.append(packageQualifiedName);
      buf.append(" ");
      buf.append(refClass.getName());
    }

    buf.append(newline(0));
    buf.append(" */"); // end javadoc

    return buf.toString();
  }

  protected String createStaticFieldDeclarations(Package pkg, Class clss) {
    InterfaceProvisioning interfaceProvisioning = PlasmaRuntime.getInstance()
        .getSDOInterfaceProvisioning(pkg.getUri());
    if (interfaceProvisioning == null)
      interfaceProvisioning = this.globalInterfaceProvisioning;
    TextBuilder buf = new TextBuilder(LINE_SEP, this.context.getIndentationToken());

    // the namespace URI
    buf.appendln(
        1,
        "/** The <a href=\"http://plasma-sdo.org\">SDO</a> namespace URI associated with the <a href=\"http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html\">Type</a> for this class. */");
    buf.appendln(1, "public static final String NAMESPACE_URI = \"");
    buf.append(clss.getUri());
    buf.append("\";");
    buf.append(LINE_SEP);

    // the entity name
    buf.appendln(
        1,
        "/** The entity or <a href=\"http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html\">Type</a> logical name associated with this class. */");
    buf.appendln(1, "public static final String TYPE_NAME_");
    buf.append(toConstantName(clss.getName()));
    buf.append(" = \"");
    buf.append(clss.getName());
    buf.append("\";");

    buf.appendln(1, "");

    switch (interfaceProvisioning.getPropertyNameStyle()) {
    case ENUMS:
      switch (interfaceProvisioning.getEnumSource()) {
      case DERIVED:
        // the static enums
        buf.appendln(
            1,
            "/** The declared logical property names for this <a href=\"http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html\">Type</a>. */");
        buf.appendln(1, "public static enum PROPERTY {");
        int enumCount = 0;
        for (Property field : clss.getProperties()) {
          if (enumCount > 0)
            buf.append(",");
          buf.append(this.newline(2));
          String javadoc = createStaticFieldDeclarationJavadoc(clss, field, 2);
          buf.append(javadoc);
          buf.append(this.newline(2));
          buf.append(field.getName());
          enumCount++;
        }
        buf.appendln(1, "}");
        break;
      case EXTERNAL: // noop
        break;
      default:
        throw new TextProvisioningException("unexpected enum source, "
            + interfaceProvisioning.getEnumSource());
      }
      break;
    case CONSTANTS:
      // static constants
      buf.appendln(1, "");
      for (Property field : clss.getProperties()) {
        String javadoc = createStaticFieldDeclarationJavadoc(clss, field, 1);
        buf.appendln(1, javadoc);

        buf.appendln(1, "public static final String ");
        buf.append(toConstantName(field.getName()));
        buf.append(" = \"");
        buf.append(field.getName());
        buf.append("\";");
      }
      buf.appendln(1, "");
      break;
    default:
    }

    return buf.toString();
  }

  private String createStaticFieldDeclarationJavadoc(Class clss, Property field, int indent) {
    TextBuilder buf = new TextBuilder(LINE_SEP, this.context.getIndentationToken());
    buf.append(this.newline(indent));
    buf.append("/**"); // begin javadoc

    // add formatted doc from UML if exists
    // always put model definition first so it appears
    // on package summary line for class
    String docs = getWrappedDocmentations(field.getDocumentations(), indent);
    if (docs.trim().length() > 0) {
      buf.append(docs);
      buf.append(newline(indent));
      buf.append(" * <p></p>");
      buf.append(newline(indent));
      buf.append(" *");
    }

    buf.append(newline(indent));
    buf.append(" * Represents the logical <a href=\"http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html\">Property</a> <b>");
    buf.append(field.getName());
    buf.append("</b> which is part of the <a href=\"http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html\">Type</a> <b>");
    buf.append(clss.getName());
    buf.append("</b>.");

    // data store mapping
    if (clss.getAlias() != null && clss.getAlias().getPhysicalName() != null
        && field.getAlias() != null && field.getAlias().getPhysicalName() != null) {
      buf.append(this.newline(indent));
      buf.append(" *");
      buf.append(this.newline(indent));
      buf.append(" * <p></p>");
      buf.append(this.newline(indent));
      buf.append(" * <b>Data Store Mapping:</b>");
      buf.append(this.newline(indent));
      buf.append(" * Corresponds to the physical data store element <b>");
      buf.append(clss.getAlias().getPhysicalName() + "." + field.getAlias().getPhysicalName());
      buf.append("</b>.");
    }

    buf.append(this.newline(indent));
    buf.append(" */"); // end javadoc
    return buf.toString();
  }

  protected String createMethodDeclarations(Class clss) {
    // TODO Auto-generated method stub
    return "";
  }

  protected String createMethodDeclarations(Class clss, Property field) {
    TextBuilder buf = new TextBuilder(LINE_SEP, this.context.getIndentationToken());
    MetaClassInfo typeClassName = this.getTypeClassName(field.getType());

    buf.append(LINE_SEP);
    createIsSetDeclaration(null, clss, field, typeClassName, buf);
    buf.append(";");

    buf.append(LINE_SEP);
    createUnsetterDeclaration(null, clss, field, typeClassName, buf);
    buf.append(";");

    if (field.getType() instanceof ClassRef) {
      Class targetClass = this.context.findClass((ClassRef) field.getType());

      if (!targetClass.isAbstract()) {
        buf.append(LINE_SEP);
        createCreatorDeclaration(null, clss, field, typeClassName, buf);
        buf.append(";");
      } else {
        buf.append(LINE_SEP);
        createCreatorByAbstractClassDeclaration(null, clss, field, typeClassName, buf);
        buf.append(";");
      }
    }

    if (!field.isMany()) {
      buf.append(LINE_SEP);
      createSingularGetterDeclaration(null, clss, field, typeClassName, buf);
      buf.append(";");

      buf.append(LINE_SEP);
      if (field.getIncrement() == null) {
        createSingularSetterDeclaration(null, clss, field, typeClassName, buf);
      } else {
        createSingularIncrementerDeclaration(null, clss, field, typeClassName, buf);
      }
      buf.append(";");
    } else {
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

  public String createFileName(Class clss, Package pkg) {
    SDOInterfaceNameResolver interfaceResolver = new SDOInterfaceNameResolver();
    TextBuilder buf = new TextBuilder(LINE_SEP, this.context.getIndentationToken());
    buf.append(interfaceResolver.getName(clss));
    buf.append(".java");
    return buf.toString();
  }

  protected String createSDOInterfaceReferenceImportDeclarations(Package pkg, Class clss) {
    TextBuilder buf = new TextBuilder(LINE_SEP, this.context.getIndentationToken());

    // for interfaces we extend our superclasses, so need to reference them
    // FIXME: only 1 level though
    ClassNameResolver resolver = new SDOInterfaceNameResolver();
    Map<String, String> nameMap = new TreeMap<String, String>();
    if (clss.getSuperClasses() != null && clss.getSuperClasses().size() > 0)
      this.collectSuperclassNames(pkg, clss, nameMap, resolver);
    else
      // it extends DataObject, so import it
      nameMap.put(PlasmaDataObject.class.getName(), PlasmaDataObject.class.getName());

    // for interfaces we have definitions for all methods generated
    // based on local fields, not fields from superclasses
    collectDataClassNames(pkg, clss, nameMap, resolver);
    collectReferenceClassNames(pkg, clss, nameMap, resolver);

    for (String name : nameMap.values()) {
      if (name.startsWith("java.lang."))
        continue;
      buf.append(LINE_SEP);
      buf.append("import ");
      buf.append(name);
      buf.append(";");
    }

    return buf.toString();
  }

}
