package org.plasma.provisioning.cli;

/**
 * The UML editing tool dialect
 */
public enum UMLDialect {
    papyrus,
    magicdraw;
    
    public static String asString() {
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < UMLDialect.values().length; i++) {
			if (i > 0)
				buf.append(", ");
			buf.append(UMLDialect.values()[i].name());
		}
		return buf.toString();
    }
}
