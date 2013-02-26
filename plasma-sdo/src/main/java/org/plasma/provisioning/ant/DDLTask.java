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
