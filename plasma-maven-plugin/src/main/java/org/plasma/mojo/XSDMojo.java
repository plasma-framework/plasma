package org.plasma.mojo;

import org.apache.maven.plugin.MojoExecutionException;
import org.plasma.provisioning.cli.XSDTool;
import org.plasma.provisioning.cli.XSDToolAction;

/**
 * Mojo implementation for generating SDO model artifacts from
 * XML Schema, such as UML/XMI. 
 * Sets up the mojo envoronment and then calls
 * the Plasma XSD command-line (CLI) tool, passing it mojo args. 
 * 
 * @author Scott Cinnamond
 * @since 1.1.3
 * 
 * @goal xsd
 * @phase generate-sources
 * 
 * @see org.plasma.provisioning.cli.XSDTool
 */
public class XSDMojo extends ClassRealmMojo
{    
    /**
    * The tool action to be performed
    * @parameter expression="${xsd.action}" default-value="convert"
    */
    private String action;

    /**
    * The XML Schema source file name
    * @parameter expression="${xsd.sourceFile}"
    */
    private String sourceFile;
    
    /**
    * The destination or target file name
    * @parameter expression="${xsd.destFile}"
    */
    private String destFile;

    /**
    * The destination or target file type
    * @parameter expression="${xsd.destFileType}"
    * @see org.plasma.provisioning.cli.ProvisioningTarget
    */
    private String destFileType;
    
    /**
    * The destination or target namespage URI
    * @parameter expression="${xsd.destNamespaceURI}" 
    */
    private String destNamespaceURI;

    /**
    * The destination or target namespage prefix
    * @parameter expression="${xsd.destNamespacePrefix}" default-value="tns"
    */
    private String destNamespacePrefix;
    
    
    public void execute() throws MojoExecutionException
    {
    	super.execute();
    	
        getLog().debug( "tool: " + XSDTool.class.getName());
        getLog().debug( "classRealm: " + this.classRealm);

        
        try
        {        
        	XSDToolAction toolAction = getToolAction(this.action);
        	
            String[] args = {
                	"-"+toolAction.name(), 
                	this.sourceFile,
                	this.destFile,
                	this.destFileType,
                	this.destNamespaceURI,
                	this.destNamespacePrefix
                };
        	
            getLog().info( "executing tool: "  + XSDTool.class.getName());
            XSDTool.main(args);
        }
        catch (IllegalArgumentException e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }
        catch (Exception e)
        {
            throw new MojoExecutionException(e.getMessage(), e);
        }        
    }
    
    private XSDToolAction getToolAction(String action)
    {
    	XSDToolAction command = null;
    	try {
    		command = XSDToolAction.valueOf(action);
    	}
    	catch (IllegalArgumentException e) {
    		StringBuilder buf = new StringBuilder();
    		for (int i = 0; i < XSDToolAction.values().length; i++) {
    			if (i > 0)
    				buf.append(", ");
    			buf.append(XSDToolAction.values()[i].name());
    		}
    			
    		throw new IllegalArgumentException("'" + action + "' - expected one of ["
    				+ buf.toString() + "]");
    	}  
    	return command;
    }
}