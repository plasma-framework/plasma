package org.plasma.sdo.helper;

import org.plasma.sdo.DataType;


public class InvalidDataConversionException extends ClassCastException {

    private static final long serialVersionUID = 1L;
    
    public InvalidDataConversionException(DataType target, DataType source, Object value) {
        super("invalid data conversion - Java class " + value.getClass().getName() 
                + "(" +  source.toString() + ") to target type " 
                + target.toString());
    }
    
    public InvalidDataConversionException(Throwable t) {
        super(t.getMessage());
    }

}
