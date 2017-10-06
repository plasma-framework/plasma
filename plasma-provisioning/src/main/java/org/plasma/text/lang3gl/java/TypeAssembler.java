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
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.metamodel.adapter.ProvisioningModel;
import org.plasma.text.lang3gl.DefaultStreamAssembler;
import org.plasma.text.lang3gl.Lang3GLFactory;
import org.plasma.text.lang3gl.Lang3GLOperation;

public class TypeAssembler extends DefaultStreamAssembler {

  private static Log log = LogFactory.getLog(TypeAssembler.class);

  public TypeAssembler(ProvisioningModel provisioningModel, Lang3GLFactory factory,
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
  }

  @Override
  public void createInterfacePackageDocs() throws IOException {
    // noop
  }

}
