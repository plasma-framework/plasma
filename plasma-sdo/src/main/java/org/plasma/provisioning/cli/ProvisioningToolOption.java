package org.plasma.provisioning.cli;

public enum ProvisioningToolOption implements OptionEnum {
	
	/** prints help on this tool */
    help("prints help on this tool"),
	/** whether to log or print detailed messages */
    verbose("whether to log or print detailed messages"),
	/** whether to log or print no messages at all (typically for testing)*/
    silent("whether to log or print no messages at all (typically for testing)"),
    /** the primary action or command performed by this tool */
    command("the primary action or command performed by this tool"),
    /** the vocabulary or usage which is characteristic for this context */
    dialect("the vocabulary or usage which is characteristic for this context"),
    /** the UML modeling or other platform for this context */
    platform("the UML modeling or other platform for this context"),
    /** a comma separated list of namespace URIs */
    namespaces("a comma separated list of namespace URIs"),
    /** a comma separated list of schema names */
    schemas("a comma separated list of schema names"),
    
    /** the fully qualified tool input source file or directory name */
    source("the fully qualified tool input source file or directory name"),
    /** the fully qualified tool output destination file or directory name */
    dest("the fully qualified tool output destination file or directory name"),
    /** a qualifier describing the input source */
    sourceType("a qualifier describing the input source"),
    /** a qualifier describing the output destination */
    destType("a qualifier describing the output destination"),
   /** a long integer representing the last time the tool was executed */
    lastExecution("a long integer representing the last time the tool was executed");
    
	private String description;
   
	private ProvisioningToolOption(String description) {
	    this.description = description;
	}

	@Override
	public String getDescription() {
		return this.description;
	}
	 
	public static String asString() {
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < ProvisioningToolOption.values().length; i++) {
			if (i > 0)
				buf.append(", ");
			buf.append(ProvisioningToolOption.values()[i].name());
		}
		return buf.toString();
	}
}
