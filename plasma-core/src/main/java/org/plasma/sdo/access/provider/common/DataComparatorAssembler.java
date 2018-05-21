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

package org.plasma.sdo.access.provider.common;

import org.plasma.query.model.GroupBy;
import org.plasma.query.model.OrderBy;
import org.plasma.query.model.Property;
import org.plasma.query.model.SortDirection;
import org.plasma.query.visitor.DefaultQueryVisitor;
import org.plasma.query.visitor.Traversal;

import commonj.sdo.Type;

public abstract class DataComparatorAssembler extends DefaultQueryVisitor {

  protected Type rootType;
  protected DataComparator dataComparator;

  protected DataComparatorAssembler(OrderBy orderby, Type rootType, DataComparator dataComparator) {
    this.rootType = rootType;
    this.dataComparator = dataComparator;
    orderby.accept(this);
  }

  protected DataComparatorAssembler(GroupBy groupBy, Type rootType, DataComparator dataComparator) {
    this.rootType = rootType;
    this.dataComparator = dataComparator;
    groupBy.accept(this);
  }

  public void start(Property property) {
    if (property.getDirection() == null
        || property.getDirection().ordinal() == SortDirection.ASC.ordinal())
      dataComparator.addAscending(property);
    else
      dataComparator.addDescending(property);

    this.getContext().setTraversal(Traversal.ABORT);
  }
}
