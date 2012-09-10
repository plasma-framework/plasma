package org.plasma.sdo.xml;

public abstract class XMLProcessor {
    private XMLOptions options;

    protected XMLProcessor() {}
    
    protected XMLProcessor(XMLOptions options) {
    	this.options = options;
    }
    
	public XMLOptions getOptions() {
		return options;
	}

	public void setOptions(XMLOptions options) {
		this.options = options;
	}
}
