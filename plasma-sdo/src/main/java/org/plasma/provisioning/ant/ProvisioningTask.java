package org.plasma.provisioning.ant;

import org.apache.tools.ant.taskdefs.Java;


/**
 * Common superclass for provisioning tasks. 
 * 
 * Note: issues with making custom tasks inherit from Any Java task are:
 * 1.) the classpath declared in the taskdef does not seem to propogate
 * through to the task execution
 */
public abstract class ProvisioningTask extends Java {
    
    protected static String COMMAND_PREFIX = "-";
    protected String command;
    protected String modelFile;
    
    
    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getModelFile() {
        return modelFile;
    }

    public void setModelFile(String modelFile) {
        this.modelFile = modelFile;
    }

}
