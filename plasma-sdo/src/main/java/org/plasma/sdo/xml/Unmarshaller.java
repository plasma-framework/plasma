package org.plasma.sdo.xml;


import commonj.sdo.helper.XMLDocument;



public abstract class Unmarshaller extends XMLProcessor {
	protected XMLDocument result;
	protected UnmarshallerFlavor flavor;
    
	protected Unmarshaller(UnmarshallerFlavor flavor) {
		this.flavor = flavor;
	}    
}
