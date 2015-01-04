package org.plasma.provisioning.cli;

/**
 * The UML editing tool dialect
 */
public enum UMLDialect implements OptionEnum {
    papyrus("the Eclipse Papyrus specific UML dialect, particularly targeting the specifics related to UML profiles"),
    magicdraw("the No Magic Inc. MagicDraw specific UML dialect, particularly targeting the specifics related to UML profiles");
    
	private String description;

	private UMLDialect(String description) {
		this.description = description;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

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
