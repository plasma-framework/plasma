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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.plasma.config.Namespace;
import org.plasma.metamodel.Class;
import org.plasma.metamodel.ClassRef;
import org.plasma.metamodel.DataTypeRef;
import org.plasma.metamodel.Package;
import org.plasma.metamodel.Property;
import org.plasma.metamodel.adapter.FieldAdapter;
import org.plasma.query.DataProperty;
import org.plasma.query.Expression;
import org.plasma.query.IntegralDataProperty;
import org.plasma.query.RealDataProperty;
import org.plasma.query.StringDataProperty;
import org.plasma.query.TemporalDataProperty;
import org.plasma.query.dsl.DataNode;
import org.plasma.query.dsl.DomainRoot;
import org.plasma.query.dsl.PathNode;
import org.plasma.runtime.PlasmaRuntime;
import org.plasma.sdo.DataFlavor;
import org.plasma.sdo.helper.PlasmaTypeHelper;
import org.plasma.text.TextBuilder;
import org.plasma.text.lang3gl.ClassFactory;
import org.plasma.text.lang3gl.ClassNameResolver;
import org.plasma.text.lang3gl.Lang3GLContext;

public class DSLClassFactory extends SDODefaultFactory implements ClassFactory {

  protected SDOInterfaceNameResolver sdoInterfaceResolver = new SDOInterfaceNameResolver();
  protected DSLClassNameResolver dslClassNameResolver = new DSLClassNameResolver();

  public DSLClassFactory(Lang3GLContext context) {
    super(context);
  }

  public String createFileName(Class clss, Package pkg) {
    TextBuilder buf = new TextBuilder(LINE_SEP, this.context.getIndentationToken());
    String name = this.dslClassNameResolver.getName(clss);
    buf.append(name);
    buf.append(".java");
    return buf.toString();
  }

  public String createContent(Package pkg, Class clss) {
    TextBuilder buf = new TextBuilder(LINE_SEP, this.context.getIndentationToken());

    buf.append(this.createPackageDeclaration(pkg));
    buf.append(LINE_SEP);
    buf.append(this.createThirdPartyImportDeclarations(pkg, clss));
    buf.append(LINE_SEP);
    buf.append(this.createDSLImportDeclarations(pkg, clss));
    buf.append(LINE_SEP);
    buf.append(this.createSDOInterfaceReferenceImportDeclarations(pkg, clss));
    buf.append(LINE_SEP);
    buf.append(this.createDSLClassReferenceImportDeclarations(pkg, clss));

    Namespace sdoNamespace = PlasmaRuntime.getInstance().getSDONamespaceByURI(pkg.getUri());
    String interfacePackageName = sdoNamespace.getProvisioning().getPackageName();
    String packageName = this.createPackageName(pkg);

    // if impl class is in different package, need import
    if (!packageName.equals(interfacePackageName)) {
      buf.append(LINE_SEP);
      String qualifiedName = interfacePackageName + "." + sdoInterfaceResolver.getName(clss);
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
      buf.append(this.createOperations(adapter.getFieldPackage(), adapter.getFieldClass(),
          adapter.getField()));
    }

    buf.append(LINE_SEP);
    buf.append(this.endBody());
    return buf.toString();
  }

  protected String createPackageDeclaration(Package pkg) {
    TextBuilder buf = new TextBuilder(LINE_SEP, this.context.getIndentationToken());
    buf.append("package " + createPackageName(pkg));
    buf.append(";");
    return buf.toString();
  }

  protected String createTypeDeclaration(Package pkg, Class clss) {
    TextBuilder buf = new TextBuilder(LINE_SEP, this.context.getIndentationToken());
    buf.append(createTypeDeclarationJavadoc(pkg, clss));
    buf.append(newline(0));
    buf.append("public class ");
    buf.append(getImplementationClassName(clss));
    buf.append(" extends ");
    buf.append(DomainRoot.class.getSimpleName());

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
    TextBuilder buf = new TextBuilder(LINE_SEP, this.context.getIndentationToken());

    buf.appendln(1, "private ");
    buf.append(getImplementationClassName(clss));
    buf.append("() {");
    buf.appendln(2, "super(");
    buf.append(PlasmaTypeHelper.class.getSimpleName());
    buf.append(".INSTANCE.getType(");
    buf.append(sdoInterfaceResolver.getName(clss));
    buf.append(".class));");
    buf.appendln(1, "}");

    buf.appendln(1, "");

    buf.appendln(1, "/**"); // begin javadoc
    buf.appendln(1, " * Constructor which instantiates a domain query path node. A path may");
    buf.appendln(1, " * span multiple namespaces and therefore Java inplementation packages");
    buf.appendln(
        1,
        " * based on the <a href=\"http://docs.plasma-sdo.org/api/org/plasma/config/PlasmaConfiguration.html\">Condiguration</a>.");
    buf.appendln(1, " * Note: while this constructor is public, it is not for application use!");

    // params
    buf.appendln(1, " * @param source the source path node");
    buf.appendln(1, " * @param sourceProperty the source property logical name");

    buf.appendln(1, " */"); // end javadoc

    buf.appendln(1, "public ");
    buf.append(getImplementationClassName(clss));
    buf.append("(");
    buf.append(PathNode.class.getSimpleName());
    buf.append(" source, ");
    buf.append(String.class.getSimpleName());
    buf.append(" sourceProperty");
    buf.append(") {");
    buf.appendln(2, "super(source, sourceProperty);");
    buf.appendln(1, "}");

    buf.appendln(1, "");

    buf.appendln(1, "/**"); // begin javadoc
    buf.appendln(1, " * Constructor which instantiates a domain query path node. A path may");
    buf.appendln(1, " * span multiple namespaces and therefore Java inplementation packages");
    buf.appendln(
        1,
        " * based on the <a href=\"http://docs.plasma-sdo.org/api/org/plasma/config/PlasmaConfiguration.html\">Condiguration</a>.");
    buf.appendln(1, " * Note: while this constructor is public, it is not for application use!");

    // params
    buf.appendln(1, " * @param source the source path node");
    buf.appendln(1, " * @param sourceProperty the source property logical name");
    buf.appendln(1, " * @param expr the path predicate expression");

    buf.appendln(1, " */"); // end javadoc

    buf.appendln(1, "public ");
    buf.append(getImplementationClassName(clss));
    buf.append("(");
    buf.append(PathNode.class.getSimpleName());
    buf.append(" source, ");
    buf.append(String.class.getSimpleName());
    buf.append(" sourceProperty, ");
    buf.append(Expression.class.getSimpleName());
    buf.append(" expr");
    buf.append(") {");
    buf.appendln(2, "super(source, sourceProperty, expr);");
    buf.appendln(1, "}");

    return buf.toString();
  }

  protected String createStaticOperations(Package pkg, Class clss) {
    TextBuilder buf = new TextBuilder(LINE_SEP, this.context.getIndentationToken());

    buf.appendln(1, "/**"); // begin javadoc
    buf.appendln(
        1,
        " * Returns a new DSL query for <a href=\"http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html\">Type</a> ");
    buf.append("<b>");
    buf.append(clss.getName());
    buf.append("</b> which can be used either as a query root or");
    buf.appendln(1, " * as the start (entry point) for a new path predicate expression.");

    // return
    buf.appendln(1, " * @return a new DSL query");

    buf.appendln(1, " */"); // end javadoc

    buf.appendln(1, "public static ");
    buf.append(getImplementationClassName(clss));
    buf.append(" newQuery() {");
    buf.appendln(2, "return new ");
    buf.append(getImplementationClassName(clss));
    buf.append("();");
    buf.appendln(1, "}");

    return buf.toString();
  }

  protected String createOperations(Package pkg, Class clss) {
    TextBuilder buf = new TextBuilder(LINE_SEP, this.context.getIndentationToken());
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

    TextBuilder buf = new TextBuilder(LINE_SEP, this.context.getIndentationToken());
    if (field.getType() instanceof ClassRef) {
      buf.append(createReferencePropertyOperation(pkg, clss, field));
      if (field.isMany()) {
        buf.appendln(1, createReferencePropertyPredicateOperation(pkg, clss, field));
      }
    } else {
      buf.append(createDataPropertyOperation(pkg, clss, field));
    }

    return buf.toString();
  }

  protected String createDataPropertyOperation(Package pkg, Class clss, Property field) {
    TextBuilder buf = new TextBuilder(LINE_SEP, this.context.getIndentationToken());

    buf.appendln(1, "/**"); // begin javadoc
    buf.appendln(1, " * Returns a DSL data element for property, <b>");
    buf.append(field.getName());
    buf.append("</b>.");

    DataFlavor flavor = this.getDataFlavor((DataTypeRef) field.getType());

    // return
    buf.appendln(1, "");
    switch (flavor) {
    case temporal:
      buf.append(" * @return a Temporal DSL data element for property, <b>");
      break;
    case integral:
      buf.append(" * @return an Integral DSL data element for property, <b>");
      break;
    case real:
      buf.append(" * @return a Real DSL data element for property, <b>");
      break;
    case string:
      buf.append(" * @return a String DSL data element for property, <b>");
      break;
    case other:
    default:
      buf.append(" * @return a DSL data element for property, <b>");
    }
    buf.append(field.getName());
    buf.append("</b>.");

    buf.appendln(1, " */"); // end javadoc

    buf.appendln(1, "public ");
    switch (flavor) {
    case temporal:
      buf.append(TemporalDataProperty.class.getSimpleName());
      break;
    case integral:
      buf.append(IntegralDataProperty.class.getSimpleName());
      break;
    case real:
      buf.append(RealDataProperty.class.getSimpleName());
      break;
    case string:
      buf.append(StringDataProperty.class.getSimpleName());
      break;
    case other:
    default:
      buf.append(DataProperty.class.getSimpleName());
    }
    buf.append(" ");
    buf.append(field.getName());
    buf.append("() {");
    buf.appendln(2, "return new ");
    buf.append(DataNode.class.getSimpleName());
    buf.append("(this, ");
    buf.append(toQualifiedPropertyNameReference(pkg, clss, field));
    buf.append(");");
    buf.appendln(1, "}");

    return buf.toString();
  }

  protected String createReferencePropertyOperation(Package pkg, Class clss, Property field) {
    TextBuilder buf = new TextBuilder(LINE_SEP, this.context.getIndentationToken());

    Class otherClass = this.context.findClass((ClassRef) field.getType());

    buf.appendln(1, "/**"); // begin javadoc
    buf.appendln(1, " * Returns a DSL query element for reference property, <b>");
    buf.append(field.getName());
    buf.append("</b>.");

    // return
    buf.appendln(1, " * @return a DSL query element for reference property, <b>");
    buf.append(field.getName());
    buf.append("</b>.");

    buf.appendln(1, " */"); // end javadoc

    buf.appendln(1, "public ");
    buf.append(getImplementationClassName(otherClass));
    buf.append(" ");
    buf.append(field.getName());
    buf.append("() {");
    buf.appendln(2, "return new ");
    buf.append(getImplementationClassName(otherClass));
    buf.append("(this, ");
    buf.append(toQualifiedPropertyNameReference(pkg, clss, field));
    buf.append(");");
    buf.appendln(1, "}");

    return buf.toString();
  }

  protected String createReferencePropertyPredicateOperation(Package pkg, Class clss, Property field) {
    TextBuilder buf = new TextBuilder(LINE_SEP, this.context.getIndentationToken());

    Class otherClass = this.context.findClass((ClassRef) field.getType());

    buf.appendln(1, "/**"); // begin javadoc
    buf.appendln(1, " * Returns a DSL query element for reference property, <b>");
    buf.append(field.getName());
    buf.append("</b>, while adding the given path predicate expression. ");
    buf.appendln(1, " * Path predicate expressions are used to restrict");
    buf.appendln(
        1,
        " * the query results for a collection property within a <a href=\"http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaDataGraph.html\">DataGraph</a>.");

    // params
    buf.appendln(1, " * @param expr the path predicate expression");

    // return
    buf.appendln(1, " * @return a DSL query element for reference property, <b>");
    buf.append(field.getName());
    buf.append("</b>.");

    buf.appendln(1, " */"); // end javadoc

    buf.appendln(1, "public ");
    buf.append(getImplementationClassName(otherClass));
    buf.append(" ");
    buf.append(field.getName());
    buf.append("(");
    buf.append(Expression.class.getSimpleName());
    buf.append(" expr) {");
    buf.appendln(2, "return new ");
    buf.append(getImplementationClassName(otherClass));
    buf.append("(this, ");
    buf.append(toQualifiedPropertyNameReference(pkg, clss, field));
    buf.append(", expr);");
    buf.appendln(1, "}");

    return buf.toString();
  }

  public String createDirectoryName(Package pkg) {
    Namespace sdoNamespace = PlasmaRuntime.getInstance().getSDONamespaceByURI(pkg.getUri());
    String packageName = sdoNamespace.getProvisioning().getPackageName();
    String subpackage = PlasmaRuntime.getInstance().getSDO().getGlobalProvisioning().getQueryDSL()
        .getImplementation().getChildPackageName();
    if (subpackage != null && subpackage.trim().length() > 0)
      packageName = packageName + "." + subpackage;
    String packageDir = packageName.replace(".", "/");
    TextBuilder buf = new TextBuilder(LINE_SEP, this.context.getIndentationToken());
    buf.append(packageDir);
    return buf.toString();
  }

  protected String createPackageName(Package pkg) {
    Namespace sdoNamespace = PlasmaRuntime.getInstance().getSDONamespaceByURI(pkg.getUri());
    String packageName = sdoNamespace.getProvisioning().getPackageName();
    String subpackage = PlasmaRuntime.getInstance().getSDO().getGlobalProvisioning().getQueryDSL()
        .getImplementation().getChildPackageName();
    if (subpackage != null && subpackage.trim().length() > 0)
      packageName = packageName + "." + subpackage;
    TextBuilder buf = new TextBuilder(LINE_SEP, this.context.getIndentationToken());
    buf.append(packageName);
    return buf.toString();
  }

  protected String createPrivateFieldDeclaration(Class clss, Property field) {
    return ""; // no fixed fields, methods delegate to SDO DataObject impl class
               // structures for data accces
  }

  protected String createThirdPartyImportDeclarations(Package pkg, Class clss) {
    TextBuilder buf = new TextBuilder(LINE_SEP, this.context.getIndentationToken());
    buf.append(LINE_SEP);
    buf.append(this.createImportDeclaration(pkg, clss, PlasmaTypeHelper.class.getName()));

    return buf.toString();
  }

  protected String createDSLImportDeclarations(Package pkg, Class clss) {
    TextBuilder buf = new TextBuilder(LINE_SEP, this.context.getIndentationToken());
    buf.append(LINE_SEP);
    buf.append(this.createImportDeclaration(pkg, clss, DomainRoot.class.getName()));
    buf.append(LINE_SEP);
    buf.append(this.createImportDeclaration(pkg, clss, PathNode.class.getName()));
    buf.append(LINE_SEP);
    buf.append(this.createImportDeclaration(pkg, clss, DataNode.class.getName()));
    buf.append(LINE_SEP);
    buf.append(this.createImportDeclaration(pkg, clss, Expression.class.getName()));

    // collect unique data flavors from all fields including any super classes
    Set<DataFlavor> flavors = new HashSet<DataFlavor>();
    Map<String, FieldAdapter> fields = new TreeMap<String, FieldAdapter>();
    collectProvisioningFields(pkg, clss, pkg, clss, fields);
    Iterator<String> fieldIter = fields.keySet().iterator();
    while (fieldIter.hasNext()) {
      String name = fieldIter.next();
      FieldAdapter adapter = fields.get(name);
      if (adapter.getField().getType() instanceof DataTypeRef) {
        DataTypeRef type = (DataTypeRef) adapter.getField().getType();
        DataFlavor flavor = this.getDataFlavor(type);
        flavors.add(flavor);
      }
    }
    for (DataFlavor flavor : flavors) {
      buf.append(LINE_SEP);
      switch (flavor) {
      case temporal:
        buf.append(this.createImportDeclaration(pkg, clss, TemporalDataProperty.class.getName()));
        break;
      case integral:
        buf.append(this.createImportDeclaration(pkg, clss, IntegralDataProperty.class.getName()));
        break;
      case real:
        buf.append(this.createImportDeclaration(pkg, clss, RealDataProperty.class.getName()));
        break;
      case string:
        buf.append(this.createImportDeclaration(pkg, clss, StringDataProperty.class.getName()));
        break;
      case other:
      default:
        buf.append(this.createImportDeclaration(pkg, clss, DataProperty.class.getName()));
      }
    }

    return buf.toString();
  }

  protected String getImplementationClassName(Class clss) {
    String name = this.dslClassNameResolver.getName(clss);
    return name;
  }

  protected String createDSLClassReferenceImportDeclarations(Package pkg, Class clss) {
    TextBuilder buf = new TextBuilder(LINE_SEP, this.context.getIndentationToken());

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
