package org.plasma.text.ddl;

import org.plasma.config.DataAccessProviderName;
import org.plasma.config.PlasmaConfig;
import org.plasma.config.SequenceConfiguration;

public abstract class DefaultDDLFactory {

	public abstract String getType(Table table, Column column);

	public String createTable(Schema schema, Table table) {
		StringBuilder buf = new StringBuilder();

		buf.append("CREATE TABLE ");
		buf.append(schema.getName());
		buf.append(".");
		buf.append(table.getName());
		buf.append(" ( ");
		int i = 0;
		for (Column column : table.getColumns()) {
			if (i > 0)
				buf.append(", ");
			buf.append(column.getName());
			buf.append(" ");
			buf.append(getType(table, column));
			if (!column.isNullable())
				buf.append(" NOT NULL");
			i++;
		}
		if (table.getPk() != null) {
			buf.append(", PRIMARY KEY (");
			i = 0;
			for (On on : table.getPk().getOns()) {
				if (i > 0)
					buf.append(", ");
				buf.append(on.getColumn());
				i++;
			}
			buf.append(" )");
		}
		buf.append(" );\n");
		return buf.toString();
	}

	public String dropTable(Schema schema, Table table) {
		StringBuilder buf = new StringBuilder();
		buf.append("DROP TABLE ");
		buf.append(schema.getName());
		buf.append(".");
		buf.append(table.getName());
		buf.append(";\n");
		return buf.toString();
	}

	public String truncateTable(Schema schema, Table table) {
		StringBuilder buf = new StringBuilder();
		buf.append("TRUNCATE TABLE ");
		buf.append(schema.getName());
		buf.append(".");
		buf.append(table.getName());
		buf.append(";\n");
		return buf.toString();
	}

	public String createSequence(Schema schema, Table table) {
		StringBuilder buf = new StringBuilder();
		SequenceConfiguration config = PlasmaConfig.getInstance().getDataAccessProvider(DataAccessProviderName.JDO).getSequenceConfiguration();
		if (config != null) {
		    buf.append("CREATE SEQUENCE ");
		    if (config.getPrefix() != null && config.getPrefix().trim().length() > 0) {
		        buf.append(config.getPrefix());
		    }
		    buf.append(table.getName());
		    if (config.getSuffix() != null && config.getSuffix().trim().length() > 0) {
		        buf.append(config.getSuffix());
		    }
		    buf.append(" MINVALUE ");
		    buf.append(config.getMinValue());
		    buf.append(" MAXVALUE ");
		    buf.append(config.getMaxValue());
		    buf.append(" START WITH ");
		    buf.append(config.getStartValue());
		    buf.append(" INCREMENT BY ");
		    buf.append(config.getIncrementBy());
		    buf.append(" CACHE ");
		    buf.append(config.getCache());
			buf.append(";\n");
		}
		return buf.toString();
	}

	public String dropSequence(Schema schema, Table table) {
		StringBuilder buf = new StringBuilder();
		SequenceConfiguration config = PlasmaConfig.getInstance().getDataAccessProvider(DataAccessProviderName.JDO).getSequenceConfiguration();
		if (config != null) {
			buf.append("DROP SEQUENCE ");
		    if (config.getPrefix() != null && config.getPrefix().trim().length() > 0) {
		        buf.append(config.getPrefix());
		    }
		    buf.append(table.getName());
		    if (config.getSuffix() != null && config.getSuffix().trim().length() > 0) {
		        buf.append(config.getSuffix());
		    }
			buf.append(";\n");
		}
		return buf.toString();
	}

	public String createIndex(Schema schema, Table table, Index index) {
		StringBuilder buf = new StringBuilder();
		buf.append("CREATE INDEX ");
		buf.append(index.getName());
		buf.append(" ON ");
		buf.append(schema.getName());
		buf.append(".");
		buf.append(table.getName());
		buf.append(" ( ");
		buf.append(index.getColumn());
		buf.append(" );\n");
		return buf.toString();
	}

	public String dropIndex(Schema schema, Table table, Index index) {
		StringBuilder buf = new StringBuilder();
		buf.append("DROP INDEX ");
		buf.append(index.getName());
		buf.append(";\n");
		return buf.toString();
	}

	public String createCheckConstraint(Schema schema, Table table, Check check) {
		StringBuilder buf = new StringBuilder();
		buf.append("ALTER TABLE ");
		buf.append(schema.getName());
		buf.append(".");
		buf.append(table.getName());
		buf.append(" ADD CONSTRAINT ");
		buf.append(check.getName());
		buf.append(" CHECK ( ");
		buf.append(check.getColumn());
		buf.append(" IN ( ");
		int i = 0;
		for (String value : check.getValues()) {
			if (i > 0)
				buf.append(", ");
			buf.append("'");
			buf.append(value);
			buf.append("'");
			i++;
		}
		buf.append(" ) ) ENABLE;\n");

		return buf.toString();
	}

	public String dropCheckConstraint(Schema schema, Table table, Check check) {
		StringBuilder buf = new StringBuilder();
		buf.append("ALTER TABLE ");
		buf.append(schema.getName());
		buf.append(".");
		buf.append(table.getName());
		buf.append(" DROP CONSTRAINT ");
		buf.append(check.getName());
		buf.append(";\n");
		return buf.toString();
	}

	public String enableCheckConstraint(Schema schema, Table table,
			Check check, boolean enable) {
		StringBuilder buf = new StringBuilder();
		buf.append("ALTER TABLE ");
		buf.append(schema.getName());
		buf.append(".");
		buf.append(table.getName());
		if (enable)
			buf.append(" ENABLE CONSTRAINT ");
		else
			buf.append(" DISABLE CONSTRAINT ");
		buf.append(check.getName());
		buf.append(";\n");
		return buf.toString();
	}

	public String createUniqueConstraint(Schema schema, Table table,
			Unique unique) {
		StringBuilder buf = new StringBuilder();

		buf.append("ALTER TABLE ");
		buf.append(schema.getName());
		buf.append(".");
		buf.append(table.getName());
		buf.append(" ADD CONSTRAINT ");
		buf.append(unique.getName());
		buf.append(" UNIQUE ( ");
		int i = 0;
		for (On on : unique.getOns()) {
			if (i > 0)
				buf.append(", ");
			buf.append(on.getColumn());
			i++;
		}
		buf.append(" ) ENABLE;\n");

		return buf.toString();
	}

	public String dropUniqueConstraint(Schema schema, Table table,
			Unique unique) {
		StringBuilder buf = new StringBuilder();
		buf.append("ALTER TABLE ");
		buf.append(schema.getName());
		buf.append(".");
		buf.append(table.getName());
		buf.append(" DROP CONSTRAINT ");
		buf.append(unique.getName());
		buf.append(";\n");
		return buf.toString();
	}

	public String enableUniqueConstraint(Schema schema, Table table,
			Unique unique, boolean enable) {
		StringBuilder buf = new StringBuilder();
		buf.append("ALTER TABLE ");
		buf.append(schema.getName());
		buf.append(".");
		buf.append(table.getName());
		if (enable)
			buf.append(" ENABLE CONSTRAINT ");
		else
			buf.append(" DISABLE CONSTRAINT ");
		buf.append(unique.getName());
		buf.append(";\n");
		return buf.toString();
	}

	public String createForeignKeyConstraint(Schema schema, Table table,
			Fk fk, Table toTable) {
		StringBuilder buf = new StringBuilder();
		buf.append("ALTER TABLE ");
		buf.append(schema.getName());
		buf.append(".");
		buf.append(table.getName());
		buf.append(" ADD CONSTRAINT ");
		buf.append(fk.getName());
		buf.append(" FOREIGN KEY ( ");
		buf.append(fk.getColumn());
		buf.append(" ) REFERENCES ");
		buf.append(schema.getName());
		buf.append(".");
		buf.append(toTable.getName());
		buf.append(" ( ");
		int i = 0;
		for (On on : toTable.getPk().getOns()) {
			if (i > 0)
				buf.append(", ");
			buf.append(on.getColumn());
			i++;
		}
		buf.append(" ) DEFERRABLE;\n");
		return buf.toString();
	}

	public String dropForeignKeyConstraint(Schema schema, Table table, Fk fk,
			Table toTable) {
		StringBuilder buf = new StringBuilder();
		buf.append("ALTER TABLE ");
		buf.append(schema.getName());
		buf.append(".");
		buf.append(table.getName());
		buf.append(" DROP CONSTRAINT ");
		buf.append(fk.getName());
		buf.append(";\n");
		return buf.toString();
	}

	public String enableForeignKeyConstraint(Schema schema, Table table,
			Fk fk, Table toTable, boolean enable) {
		StringBuilder buf = new StringBuilder();
		buf.append("ALTER TABLE ");
		buf.append(schema.getName());
		buf.append(".");
		buf.append(table.getName());
		if (enable)
			buf.append(" ENABLE CONSTRAINT ");
		else
			buf.append(" DISABLE CONSTRAINT ");
		buf.append(fk.getName());
		buf.append(";\n");
		return buf.toString();
	}

}
