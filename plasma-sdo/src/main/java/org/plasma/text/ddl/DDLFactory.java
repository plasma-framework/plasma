package org.plasma.text.ddl;


public interface DDLFactory {
    public String getType(Table table, Column column);
    public String createTable(Schema schema, Table table);
    public String dropTable(Schema schema, Table table);
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
