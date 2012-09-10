package org.plasma.sdo;

import commonj.sdo.DataObject;




public interface PlasmaDataGraphEventVisitor 
{
    public void start(PlasmaDataObject target, PlasmaDataObject source, String sourcePropertyName, int level);   
    public void end(PlasmaDataObject target, PlasmaDataObject source, String sourcePropertyName, int level);   
}