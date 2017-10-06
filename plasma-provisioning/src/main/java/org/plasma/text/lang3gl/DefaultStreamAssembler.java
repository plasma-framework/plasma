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

package org.plasma.text.lang3gl;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.common.io.StreamAssembler;
import org.plasma.metamodel.adapter.ProvisioningModel;
import org.plasma.text.TextProvisioningException;

public abstract class DefaultStreamAssembler extends DefaultLang3GLAssembler implements
    StreamAssembler, Lang3GLAssembler {
  private static Log log = LogFactory.getLog(DefaultStreamAssembler.class);

  protected File dest;
  protected boolean indent = false;
  protected static final String LINE_SEP = System.getProperty("line.separator");
  protected static final String FILE_SEP = System.getProperty("file.separator");

  protected int resultInterfacesCount;
  protected int resultClassesCount;
  protected int resultEnumerationsCount;

  public DefaultStreamAssembler(ProvisioningModel provisioningModel, Lang3GLFactory factory,
      Lang3GLOperation operation, File dest) {
    super(provisioningModel, factory, operation);
    if (this.provisioningModel == null)
      throw new IllegalArgumentException("expected 'provisioningModel' argument");
    if (this.factory == null)
      throw new IllegalArgumentException("expected 'factory' argument");
    if (this.operation == null)
      throw new IllegalArgumentException("expected 'operation' argument");
    this.dest = dest;
    if (this.dest == null)
      throw new IllegalArgumentException("expected 'dest' argument");
  }

  public abstract void createEnumerationClasses() throws IOException;

  public abstract void createInterfaceClasses() throws IOException;

  public abstract void createInterfacePackageDocs() throws IOException;

  public abstract void createImplementationClasses() throws IOException;

  public void start() {
    try {
      switch (this.operation) {
      case create:
        createEnumerationClasses();
        createInterfaceClasses();
        createInterfacePackageDocs();
        createImplementationClasses();
        break;
      }
    } catch (IOException e) {
      throw new TextProvisioningException(e);
    }
  }

  public boolean isIndent() {
    return indent;
  }

  public void setIndent(boolean indent) {
    this.indent = indent;
  }

  public String getIndentationToken() {
    return "\t";
  }

  @Override
  public void setIndentationToken(String indentationToken) {
    // TODO Auto-generated method stub

  }

  public int getResultInterfacesCount() {
    return resultInterfacesCount;
  }

  public int getResultClassesCount() {
    return resultClassesCount;
  }

  public int getResultEnumerationsCount() {
    return resultEnumerationsCount;
  }

}
