package org.plasma.provisioning.ant;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.ant.BuildException;
import org.plasma.provisioning.cli.DSLTool;

public class DSLTask extends ProvisioningTask {
    private static Log log =LogFactory.getLog(
            DSLTask.class); 
   
    private String dialect;
    private String dest;

    public void execute() throws BuildException {
        getCommandLine().setClassname(DSLTool.class.getName()); 
        if (this.dialect != null)
	        getCommandLine().getJavaCommand().addArguments(new String[] {
	            COMMAND_PREFIX + command, 
	            dialect,
	            dest});
        else
	        getCommandLine().getJavaCommand().addArguments(new String[] {
		            COMMAND_PREFIX + command, 
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
