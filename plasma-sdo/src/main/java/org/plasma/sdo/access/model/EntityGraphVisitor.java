package org.plasma.sdo.access.model;


/**
 * Receives visit events across a POM graph structure. Implementors should
 * expect multiple calls, in some cases, for a single POM entity node. 
 */
public interface EntityGraphVisitor 
{
    public void visit(Entity entity, Entity source, String sourceKey, String path);
}

