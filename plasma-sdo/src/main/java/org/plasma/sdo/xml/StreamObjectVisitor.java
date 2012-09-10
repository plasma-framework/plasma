package org.plasma.sdo.xml;

import org.plasma.sdo.PlasmaProperty;





public interface StreamObjectVisitor 
{
    public void visit(StreamObject target, StreamObject source, PlasmaProperty sourceProperty, int level);   
}