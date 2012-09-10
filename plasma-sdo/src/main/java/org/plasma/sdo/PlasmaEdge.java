package org.plasma.sdo;

import org.plasma.sdo.core.Edge;


public interface PlasmaEdge extends Edge {

    public PlasmaNode getLeft();
    public void setLeft(PlasmaNode left);
    public PlasmaNode getRight();
    public void setRight(PlasmaNode right);
    public boolean canTraverse(PlasmaNode fromNode);
    public PlasmaNode getOpposite(PlasmaNode fromNode);
    
}
