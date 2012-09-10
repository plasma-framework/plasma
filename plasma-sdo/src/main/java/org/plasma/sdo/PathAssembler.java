package org.plasma.sdo;


/**
 * A graph visitor which stores a list of candidate paths from the root 
 * data-object to the given target data-object and return the minimum (least hops) path 
 * or set of paths as SDO compliant path-strings.
 */
public interface PathAssembler extends PlasmaDataGraphVisitor {
    
    public String getMinimumPathString();
    public int getMinimumPathDepth();
    public int getPathCount();    
    public String[] getAllPaths();
}
