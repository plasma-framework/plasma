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

package org.plasma.sdo.repository.fuml;

import java.util.List;

import org.plasma.sdo.Alias;
import org.plasma.sdo.repository.Comment;
import org.plasma.sdo.repository.OpaqueBehavior;

public class FumlOpaqueBehavior extends FumlElement<org.modeldriven.fuml.repository.OpaqueBehavior>
    implements OpaqueBehavior {

  protected FumlOpaqueBehavior(org.modeldriven.fuml.repository.OpaqueBehavior delegate) {
    super(delegate);
  }

  @Override
  public String getName() {
    return element.getName();
  }

  @Override
  public String getId() {
    return element.getXmiId();
  }

  @Override
  public String getPhysicalName() {
    return super.getPhysicalName();
  }

  @Override
  public Alias findAlias() {
    return super.findAlias();
  }

  @Override
  public List<Comment> getComments() {
    return super.getComments();
  }

  @Override
  public String getLanguage() {
    return element.getLanguage();
  }

  @Override
  public String getBody() {
    return element.getBody();
  }

}
