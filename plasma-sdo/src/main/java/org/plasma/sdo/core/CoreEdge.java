package org.plasma.sdo.core;

public class CoreEdge implements Edge {
    
    protected TraversalDirection direction = TraversalDirection.RIGHT;
    protected double weight;

    public TraversalDirection getDirection() {
        return direction;
    }

    public void setDirection(TraversalDirection direction) {
        this.direction = direction;
    }
}
