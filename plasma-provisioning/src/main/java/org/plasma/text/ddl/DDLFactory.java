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

package org.plasma.text.ddl;

public interface DDLFactory {
  public String getType(Table table, Column column);

  public String createSchema(Schema schema);

  public String createTable(Schema schema, Table table);

  public String dropSchema(Schema schema);

  public String dropTable(Schema schema, Table table);

  public String createView(Schema schema, Table table, Behavior create);

  public String dropView(Schema schema, Table table, Behavior drop);

  public String truncateTable(Schema schema, Table table);

  public String createSequence(Schema schema, Table table);

  public String dropSequence(Schema schema, Table table);

  public String createIndex(Schema schema, Table table, Index index);

  public String dropIndex(Schema schema, Table table, Index index);

  public String createCheckConstraint(Schema schema, Table table, Check check);

  public String dropCheckConstraint(Schema schema, Table table, Check check);

  public String enableCheckConstraint(Schema schema, Table table, Check check, boolean enable);

  public String createUniqueConstraint(Schema schema, Table table, Unique unique);

  public String dropUniqueConstraint(Schema schema, Table table, Unique unique);

  public String enableUniqueConstraint(Schema schema, Table table, Unique unique, boolean enable);

  public String createForeignKeyConstraint(Schema schema, Table table, Fk fk, Table toTable);

  public String dropForeignKeyConstraint(Schema schema, Table table, Fk fk, Table toTable);

  public String enableForeignKeyConstraint(Schema schema, Table table, Fk fk, Table toTable,
      boolean enable);

}
