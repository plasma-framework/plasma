package org.plasma.sdo.access;

import commonj.sdo.DataObject;

public interface SequenceGenerator {
    public Number get(DataObject dataObject)  
        throws SequenceGeneratorException;
    public void initialize();
    public void close();
}
