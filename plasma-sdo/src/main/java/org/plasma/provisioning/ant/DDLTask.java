package org.plasma.provisioning.ant;


import org.apache.tools.ant.BuildException;
import org.plasma.provisioning.cli.DDLTool;

public class DDLTask extends ProvisioningTask {
    
    private String dialect;
    private String dest;

    public void execute() throws BuildException {
        getCommandLine().setClassname(DDLTool.class.getName()); 
        getCommandLine().getJavaCommand().addArguments(new String[] {
            COMMAND_PREFIX + command, 
            dialect,
            dest});
        //setFork(true);  
        //setNewenvironment(true);            
        super.execute();
    }

    public String getDialect() {
		return dialect;
	}

	public String getDest() {
		return dest;
	}

	public void setDest(String dest) {
        this.dest = dest;
    }

	public void setDialect(String dialect) {
		this.dialect = dialect;
	}

}
