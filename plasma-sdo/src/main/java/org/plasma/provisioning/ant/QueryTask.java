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


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.ant.BuildException;
import org.plasma.provisioning.cli.QueryTool;

public class QueryTask extends ProvisioningTask {
    private static Log log =LogFactory.getLog(
            QueryTask.class); 
   
    private String sourceDir;
    private String classesDir;
    private String file;
    private String destFile;
    private String destFileType;
    private String destNamespaceURI;
    private String destNamespacePrefix = "tns";
    private String dialect;
    private String dest;

    // The method executing the task
    public void execute() throws BuildException {
        
        log.info("execute");
        getCommandLine().setClassname(QueryTool.class.getName()); 
        getCommandLine().getJavaCommand().addArguments(new String[] {
            COMMAND_PREFIX + command,
            file,
            destFile,
            destFileType,
            destNamespaceURI,
            destNamespacePrefix,
            sourceDir,
            classesDir});
        //setFork(true);  
        //setNewenvironment(true);            
        super.execute();
    }

    public String getFile() {
        return file;
    }

    public void setFile(String queryFile) {
        this.file = queryFile;
    }

    public String getDestFile() {
        return destFile;
    }

    public void setDestFile(String destFile) {
        this.destFile = destFile;
    }

    public String getDestNamespaceURI() {
        return destNamespaceURI;
    }

    public void setDestNamespaceURI(String destNamespaceURI) {
        this.destNamespaceURI = destNamespaceURI;
    }

    public String getDestNamespacePrefix() {
		return destNamespacePrefix;
	}

	public void setDestNamespacePrefix(String destNamespacePrefix) {
		this.destNamespacePrefix = destNamespacePrefix;
	}

	public String getSourceDir() {
        return sourceDir;
    }

    public void setSourceDir(String sourceDir) {
        this.sourceDir = sourceDir;
    }

    public String getClassesDir() {
        return classesDir;
    }

    public void setClassesDir(String classesDir) {
        this.classesDir = classesDir;
    }

	public String getDestFileType() {
		return destFileType;
	}

	public void setDestFileType(String destFileType) {
		this.destFileType = destFileType;
	}


}
