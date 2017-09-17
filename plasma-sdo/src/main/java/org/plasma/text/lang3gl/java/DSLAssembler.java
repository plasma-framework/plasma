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
import org.plasma.config.PlasmaConfig;
import org.plasma.metamodel.Class;
import org.plasma.metamodel.Package;
import org.plasma.provisioning.adapter.ProvisioningModel;
import org.plasma.text.lang3gl.ClassFactory;
import org.plasma.text.lang3gl.DefaultStreamAssembler;
import org.plasma.text.lang3gl.Lang3GLFactory;
import org.plasma.text.lang3gl.Lang3GLOperation;

public class DSLAssembler extends DefaultStreamAssembler {

  private static Log log = LogFactory.getLog(DSLAssembler.class);

  public DSLAssembler(ProvisioningModel provisioningModel, Lang3GLFactory factory,
      Lang3GLOperation operation, File dest) {
    super(provisioningModel, factory, operation, dest);
  }

  @Override
  public void createEnumerationClasses() throws IOException {
    // noop
  }

  @Override
  public void createInterfaceClasses() throws IOException {
    // noop
  }

  @Override
  public void createImplementationClasses() throws IOException {
    ClassFactory classFactory = factory.getClassFactory();

    for (Package pkg : this.provisioningModel.getLeafPackages()) {
      File dir = new File(dest, classFactory.createDirectoryName(pkg));
      log.debug("processing package: " + dir.getAbsolutePath());
      for (Class clss : pkg.getClazzs()) {
        if (!PlasmaConfig.getInstance().generateQueryDSL(clss.getUri(), clss.getName()))
          continue;

        if (!dir.exists()) {
          if (!dir.mkdirs())
            throw new IllegalArgumentException("package directory '" + dir.getAbsolutePath()
                + "' could not be created");
          log.debug("created package: " + dir.getAbsolutePath());
        }

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
    // noop
  }

}
