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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.metamodel.Class;
import org.plasma.metamodel.Documentation;
import org.plasma.metamodel.Enumeration;
import org.plasma.metamodel.Package;
import org.plasma.metamodel.adapter.ProvisioningModel;
import org.plasma.text.lang3gl.ClassFactory;
import org.plasma.text.lang3gl.DefaultStreamAssembler;
import org.plasma.text.lang3gl.EnumerationFactory;
import org.plasma.text.lang3gl.InterfaceFactory;
import org.plasma.text.lang3gl.Lang3GLFactory;
import org.plasma.text.lang3gl.Lang3GLOperation;

public class SDOAssembler extends DefaultStreamAssembler {

  private static Log log = LogFactory.getLog(SDOAssembler.class);

  public SDOAssembler(ProvisioningModel provisioningModel, Lang3GLFactory factory,
      Lang3GLOperation operation, File dest) {
    super(provisioningModel, factory, operation, dest);
  }

  @Override
  public void createEnumerationClasses() throws IOException {
    EnumerationFactory enumFactory = factory.getEnumerationFactory();
    for (Package pkg : this.provisioningModel.getLeafPackages()) {
      File dir = new File(dest, factory.getEnumerationFactory().createBaseDirectoryName(pkg));
      log.debug("processing package: " + dir.getAbsolutePath());
      if (!dir.exists()) {
        if (!dir.mkdirs())
          throw new IllegalArgumentException("package directory '" + dir.getAbsolutePath()
              + "' could not be created");
        log.debug("created package: " + dir.getAbsolutePath());
      }
      for (Enumeration enumeration : pkg.getEnumerations()) {
        File file = new File(dir, factory.getEnumerationFactory().createFileName(enumeration));
        log.debug("creating file: " + file.getAbsolutePath());
        FileOutputStream stream = new FileOutputStream(file);
        StringBuilder buf = new StringBuilder();
        buf.append(enumFactory.createContent(pkg, enumeration));
        stream.write(buf.toString().getBytes());
        stream.flush();
        stream.close();
        this.resultEnumerationsCount++;
      }
    }
  }

  @Override
  public void createInterfaceClasses() throws IOException {
    InterfaceFactory interfaceFactory = factory.getInterfaceFactory();

    for (Package pkg : this.provisioningModel.getLeafPackages()) {
      File dir = new File(dest, interfaceFactory.createBaseDirectoryName(pkg));
      log.debug("processing package: " + dir.getAbsolutePath());
      if (!dir.exists()) {
        if (!dir.mkdirs())
          throw new IllegalArgumentException("package directory '" + dir.getAbsolutePath()
              + "' could not be created");
        log.debug("created package: " + dir.getAbsolutePath());
      }
      for (Class clss : pkg.getClazzs()) {
        File file = new File(dir, interfaceFactory.createFileName(clss, pkg));
        log.debug("creating file: " + file.getAbsolutePath());
        FileOutputStream stream = new FileOutputStream(file);
        StringBuilder buf = new StringBuilder();
        buf.append(interfaceFactory.createContent(pkg, clss));
        stream.write(buf.toString().getBytes());
        stream.flush();
        stream.close();
        this.resultInterfacesCount++;
      }
    }
  }

  @Override
  public void createImplementationClasses() throws IOException {
    ClassFactory classFactory = factory.getClassFactory();

    for (Package pkg : this.provisioningModel.getLeafPackages()) {
      File dir = new File(dest, classFactory.createDirectoryName(pkg));
      log.debug("processing package: " + dir.getAbsolutePath());
      if (!dir.exists()) {
        if (!dir.mkdirs())
          throw new IllegalArgumentException("package directory '" + dir.getAbsolutePath()
              + "' could not be created");
        log.debug("created package: " + dir.getAbsolutePath());
      }
      for (Class clss : pkg.getClazzs()) {
        File file = new File(dir, classFactory.createFileName(clss, pkg));
        log.debug("creating file: " + file.getAbsolutePath());
        FileOutputStream stream = new FileOutputStream(file);

        StringBuilder buf = new StringBuilder();
        buf.append(classFactory.createContent(pkg, clss));

        stream.write(buf.toString().getBytes());
        stream.flush();
        stream.close();
        this.resultClassesCount++;
      }
    }
  }

  @Override
  public void createInterfacePackageDocs() throws IOException {
    for (Package pkg : this.provisioningModel.getLeafPackages()) {
      File dir = new File(dest, factory.getInterfaceFactory().createBaseDirectoryName(pkg));
      log.debug("processing package: " + dir.getAbsolutePath());
      if (!dir.exists()) {
        if (!dir.mkdirs())
          throw new IllegalArgumentException("package directory '" + dir.getAbsolutePath()
              + "' could not be created");
        log.debug("created package: " + dir.getAbsolutePath());
      }
      File file = new File(dir, "package.html");
      log.debug("creating file: " + file.getAbsolutePath());
      FileOutputStream stream = new FileOutputStream(file);
      StringBuilder buf = new StringBuilder();
      buf.append("<html><head></head>");
      buf.append("<body>");
      if (pkg.getDocumentations() != null)
        for (Documentation doc : pkg.getDocumentations()) {
          buf.append(doc.getBody().getValue());
        }
      buf.append("</body>");
      buf.append("</html>");

      stream.write(buf.toString().getBytes());
      stream.flush();
      stream.close();
    }
  }

}
