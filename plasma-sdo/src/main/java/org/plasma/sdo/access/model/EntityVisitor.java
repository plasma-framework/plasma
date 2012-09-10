package org.plasma.sdo.access.model;


/**
 */
public interface EntityVisitor 
{
    public boolean visit(Entity entity, Entity source, String sourceKey);
}

