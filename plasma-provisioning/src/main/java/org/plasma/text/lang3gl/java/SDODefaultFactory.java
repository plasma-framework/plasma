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

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.plasma.metamodel.Class;
import org.plasma.metamodel.Package;
import org.plasma.metamodel.Property;
import org.plasma.runtime.EnumSource;
import org.plasma.runtime.InterfaceProvisioning;
import org.plasma.runtime.PlasmaRuntime;
import org.plasma.runtime.PropertyNameStyle;
import org.plasma.text.TextBuilder;
import org.plasma.text.TextProvisioningException;
import org.plasma.text.lang3gl.ClassNameResolver;
import org.plasma.text.lang3gl.Lang3GLContext;

public abstract class SDODefaultFactory extends DefaultFactory {

  protected static String[] SDO_RESERVED_NAMES = { "type", "dataobject", "sequence", "list" };

  private Map<String, String> reservedGetterNameMap = new HashMap<String, String>();
  protected InterfaceProvisioning globalInterfaceProvisioning;
  protected ClassNameResolver interfaceResolver = new SDOInterfaceNameResolver();
  protected ClassNameResolver classResolver = new SDOClassNameResolver();
  protected ClassNameResolver enumResolver = new SDOEnumerationNameResolver();

  public SDODefaultFactory(Lang3GLContext context) {
    super(context);
    for (String name : SDO_RESERVED_NAMES)
      this.reservedGetterNameMap.put(name, name);

    this.globalInterfaceProvisioning = PlasmaRuntime.getInstance().getSDO().getGlobalProvisioning()
        .getInterface();
    if (globalInterfaceProvisioning == null) {
      globalInterfaceProvisioning = new InterfaceProvisioning();
      globalInterfaceProvisioning.setPropertyNameStyle(PropertyNameStyle.ENUMS);
      globalInterfaceProvisioning.setEnumSource(EnumSource.DERIVED);
    }
  }

  protected String createPackageDeclaration(Package pkg) {
    String packageName = PlasmaRuntime.getInstance().getSDOInterfacePackageName(pkg.getUri());
    TextBuilder buf = new TextBuilder(LINE_SEP, this.context.getIndentationToken());
    buf.append("package " + packageName);
    buf.append(";");
    return buf.toString();
  }

  public String createBaseDirectoryName(Package pkg) {
    String packageName = PlasmaRuntime.getInstance().getSDOInterfacePackageName(pkg.getUri());
    String packageDir = packageName.replace(".", "/");
    TextBuilder buf = new TextBuilder(LINE_SEP, this.context.getIndentationToken());
    buf.append(packageDir);
    return buf.toString();
  }

  public String createDirectoryName(Package pkg) {
    String packageName = PlasmaRuntime.getInstance().getSDOInterfacePackageName(pkg.getUri());
    String packageDir = packageName.replace(".", "/");
    TextBuilder buf = new TextBuilder(LINE_SEP, this.context.getIndentationToken());
    buf.append(packageDir);
    return buf.toString();
  }

  protected String toQualifiedPropertyNameReference(Package pkg, Class clss, Property field) {
    TextBuilder buf = new TextBuilder(LINE_SEP, this.context.getIndentationToken());

    InterfaceProvisioning interfaceProvisioning = PlasmaRuntime.getInstance()
        .getSDOInterfaceProvisioning(pkg.getUri());
    if (interfaceProvisioning == null)
      interfaceProvisioning = this.globalInterfaceProvisioning;

    switch (interfaceProvisioning.getPropertyNameStyle()) {
    case ENUMS:
      switch (interfaceProvisioning.getEnumSource()) {
      case DERIVED:
        // use generated enums derived from metadata
        buf.append(this.interfaceResolver.getName(clss));
        buf.append(".PROPERTY.");
        buf.append(field.getName());
        buf.append(".name()");
        break;
      case EXTERNAL:
        // use eternally created enums
        buf.append(this.enumResolver.getQualifiedName(clss, pkg));
        buf.append(".");
        buf.append(field.getAlias().getLocalName());
        buf.append(".name()");
        break;
      default:
        throw new TextProvisioningException("unexpected enum source, "
            + interfaceProvisioning.getEnumSource());
      }
      break;
    case CONSTANTS:
      buf.append(this.interfaceResolver.getName(clss));
      buf.append(".");
      buf.append(toConstantName(field.getName()));
      break;
    case NONE:
    default:
      buf.append("\"");
      buf.append(field.getName());
      buf.append("\"");
      break;
    }
    return buf.toString();
  }

  protected String createSDOInterfaceReferenceImportDeclarations(Package pkg, Class clss) {
    TextBuilder buf = new TextBuilder(LINE_SEP, this.context.getIndentationToken());

    Map<String, String> nameMap = new TreeMap<String, String>();
    ClassNameResolver resolver = new SDOInterfaceNameResolver();
    collectSuperclassNames(pkg, clss, nameMap, resolver);
    for (String name : nameMap.values()) {
      buf.append(LINE_SEP);
      buf.append("import ");
      buf.append(name);
      buf.append(";");
    }

    return buf.toString();
  }

  protected String getImplementationClassName(Class clss, Package pkg) {
    SDOClassNameResolver resolver = new SDOClassNameResolver();
    String name = resolver.getName(clss);
    return name;
  }

  protected String toMethodFieldName(String name) {
    String result = firstToUpperCase(name);
    if (this.reservedGetterNameMap.get(name.toLowerCase()) != null)
      result += "_";
    return result;
  }
}
