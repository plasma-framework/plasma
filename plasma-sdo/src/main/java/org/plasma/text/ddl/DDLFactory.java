/**
 *         PlasmaSDO™ License
 * 
 * This is a community release of PlasmaSDO™, a dual-license 
 * Service Data Object (SDO) 2.1 implementation. 
 * This particular copy of the software is released under the 
 * version 2 of the GNU General Public License. PlasmaSDO™ was developed by 
 * TerraMeta Software, Inc.
 * 
 * Copyright (c) 2013, TerraMeta Software, Inc. All rights reserved.
 * 
 * General License information can be found below.
 * 
 * This distribution may include materials developed by third
 * parties. For license and attribution notices for these
 * materials, please refer to the documentation that accompanies
 * this distribution (see the "Licenses for Third-Party Components"
 * appendix) or view the online documentation at 
 * <http://plasma-sdo.org/licenses/>.
 *  
 */
package org.plasma.text.ddl;


public interface DDLFactory {
    public String getType(Table table, Column column);
    public String createTable(Schema schema, Table table);
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
    public String enableCheckConstraint(Schema schema, Table table,
			Check check, boolean enable);
    public String createUniqueConstraint(Schema schema, Table table,
			Unique unique);
    public String dropUniqueConstraint(Schema schema, Table table,
			Unique unique);
    public String enableUniqueConstraint(Schema schema, Table table,
			Unique unique, boolean enable);
    public String createForeignKeyConstraint(Schema schema, Table table,
			Fk fk, Table toTable);
    public String dropForeignKeyConstraint(Schema schema, Table table, Fk fk,
			Table toTable);
    public String enableForeignKeyConstraint(Schema schema, Table table,
			Fk fk, Table toTable, boolean enable);
    
}
