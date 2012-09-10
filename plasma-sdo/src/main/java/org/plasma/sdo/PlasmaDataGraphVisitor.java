package org.plasma.sdo;

import commonj.sdo.DataObject;




public interface PlasmaDataGraphVisitor 
{
    public void visit(DataObject target, DataObject source, String sourcePropertyName, int level);   
}