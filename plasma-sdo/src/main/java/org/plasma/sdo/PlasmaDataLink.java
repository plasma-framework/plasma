package org.plasma.sdo;

import org.plasma.sdo.core.CoreEdge;
import org.plasma.sdo.core.TraversalDirection;

public class PlasmaDataLink extends CoreEdge 
    implements PlasmaEdge 
{        
    protected PlasmaNode left;
    protected PlasmaNode right;

    @SuppressWarnings("unused")
    private PlasmaDataLink() {}
    
    public PlasmaDataLink(PlasmaNode left, PlasmaNode right) {
        this.left = left;
        this.right = right;
    }
    
    public boolean canTraverse(PlasmaNode fromNode) {
        return this.getDirection() == TraversalDirection.RIGHT && left.equals(fromNode);
    }
    
    public PlasmaNode getOpposite(PlasmaNode fromNode) {
        if (this.left.getUUIDAsString().equals(fromNode.getUUIDAsString()))
            return right;
        else if (this.right.getUUIDAsString().equals(fromNode.getUUIDAsString()))
            return left;
        else
            throw new IllegalArgumentException("given node '" 
                    + fromNode.getUUIDAsString() + "' not found");
    }
    
    public PlasmaNode getLeft() {
        return left;
    }
    public void setLeft(PlasmaNode left) {
        this.left = left;
    }
    public PlasmaNode getRight() {
        return right;
    }
    public void setRight(PlasmaNode right) {
        this.right = right;
    }
}
