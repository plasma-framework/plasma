package org.plasma.text.lang3gl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.common.io.StreamAssembler;
import org.plasma.provisioning.Class;
import org.plasma.provisioning.Package;
import org.plasma.provisioning.Model;
import org.plasma.text.TextException;

public abstract class DefaultStreamAssembler extends DefaultLang3GLAssembler 
    implements StreamAssembler, Lang3GLAssembler {
    private static Log log =LogFactory.getLog(
    		DefaultStreamAssembler.class); 

    protected File dest;
    protected boolean indent = false;
	protected static final String LINE_SEP = System.getProperty("line.separator");
	protected static final String FILE_SEP = System.getProperty("file.separator");
	protected Map<String, Class> classMap = new HashMap<String, Class>();
	
	protected int resultInterfacesCount;
	protected int resultClassesCount;
	protected int resultEnumerationsCount;
    
	public DefaultStreamAssembler(Model packages, 
			Lang3GLFactory factory, 
			Lang3GLOperation operation,
			File dest) {
		super(packages, factory, operation);
    	if (this.packages == null)
    		throw new IllegalArgumentException("expected 'packages' argument");
    	if (this.factory == null)
    		throw new IllegalArgumentException("expected 'factory' argument");		
    	if (this.operation == null)
    		throw new IllegalArgumentException("expected 'operation' argument");
    	this.dest = dest;
    	if (this.dest == null)
    		throw new IllegalArgumentException("expected 'dest' argument");		
	}
	
    public void start() {
        try {
        	// map classes
	    	for (Package pkg : this.packages.getPackages()) {
	    		for (Class clss : pkg.getClazzs()) {	    			
	    			classMap.put(clss.getUri() + "#" + clss.getName(), clss);
	    		}
	    	}
			switch (this.operation) {
			case create:
		    	createEnumerationClasses();
				createInterfaceClasses();
				createInterfacePackageDocs();
		    	createImplementationClasses();
			    break;
			}
		} catch (IOException e) {
			throw new TextException(e);
		}
    }
    	
	public boolean isIndent() {
		return indent;
	}

	public void setIndent(boolean indent) {
		this.indent = indent;
	}

	public String getIndentationToken() {
		return "\t";
	}

	@Override
	public void setIndentationToken(String indentationToken) {
		// TODO Auto-generated method stub
		
	}

	public int getResultInterfacesCount() {
		return resultInterfacesCount;
	}

	public int getResultClassesCount() {
		return resultClassesCount;
	}

	public int getResultEnumerationsCount() {
		return resultEnumerationsCount;
	}
	
	
}
	
