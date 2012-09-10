package org.plasma.text.ddl;


public abstract class DDLAssembler {
	protected Schemas schemas;
	protected DDLFactory factory;
	protected DDLOperation operation;
	
	@SuppressWarnings("unused")
	private DDLAssembler() {}
	
	protected DDLAssembler(Schemas schemas,
			DDLFactory factory, 
			DDLOperation operation) {
		this.schemas = schemas;
		this.operation = operation;
    	this.factory = factory;
	}

}
