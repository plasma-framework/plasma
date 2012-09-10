package org.plasma.sdo.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.sdo.PathAssembler;
import org.plasma.sdo.PlasmaNode;

import commonj.sdo.DataObject;
import commonj.sdo.Property;

/**
 * A graph visitor which assembles and stores a list of candidate 
 * paths from the root data-object to the given target data-object 
 * and returns the minimum (least hops) path 
 * or set of paths as SDO path-strings.
 */
public class CorePathAssembler implements PathAssembler {
    
    private static Log log = LogFactory.getFactory().getInstance(CorePathAssembler.class);
    
    public static String PATH_PREFIX = "#/namespace:";
    public static String PATH_DELIM = "/";    
    public static String PATH_INDEX_RIGHT = "[";
    public static String PATH_INDEX_LEFT = "]";
    
    private PlasmaNode pathTarget;
    private Stack<PathNode> currentPath = new Stack<PathNode>();
    private List<Stack<PathNode>> pathList = new ArrayList<Stack<PathNode>>();
    
    @SuppressWarnings("unused")
    private CorePathAssembler() {}
    
    public CorePathAssembler(DataObject pathTarget) {
        this.pathTarget = (PlasmaNode)pathTarget;
        
        DataObject root = this.pathTarget.getDataObject().getDataGraph().getRootObject();
        
        if (log.isDebugEnabled())
            log.debug("finding paths from root: " + 
                    root.getType().getName()
                    + "("+((PlasmaNode)root).getUUIDAsString()+") to target "
                    + this.pathTarget.getDataObject().getType().getName() 
                    + "("+this.pathTarget.getUUIDAsString()+")");
    }

    public void visit(DataObject target, DataObject source, String propertyName, int level) {
                    
        Property sourceProperty = null;
        if (source != null) {
            sourceProperty = source.getType().getProperty(propertyName);
        }
        else { // the root
            DataObject root = this.pathTarget.getDataObject().getDataGraph().getRootObject();
        	if (!((PlasmaNode)target).getUUIDAsString().equals(
        			((PlasmaNode)root).getUUIDAsString())) {  
        		throw new IllegalArgumentException("expected data graph root as "
        				+ "traversal root");
        	}
        }
        if (log.isDebugEnabled())
            if (source == null)
                log.debug("visit: " + target.getType().getName() 
                        + "("+((PlasmaNode)target).getUUIDAsString()+")");
            else
                log.debug("visit: " + source.getType().getName() 
                        + "("+((PlasmaNode)source).getUUIDAsString()+ ")."
                        + sourceProperty.getName() + "->"
                        + target.getType().getName() + "("+((PlasmaNode)target).getUUIDAsString()+")");
        
        if ("Dependancy".equals(target.getType().getName())) {
        	int foo = 0;
        	foo++;
        }
        
        if (level == currentPath.size()) {
            currentPath.push(new PathNode(target, 0, source, sourceProperty));
            if (((PlasmaNode)target).getUUIDAsString().equals(pathTarget.getUUIDAsString())) {
                
                Stack<PathNode> path = new Stack<PathNode>();
                path.addAll(currentPath); // copy current state
                pathList.add(path);
            }           
        }
        else {
            PathNode pathNode = null;
            while (currentPath.size() > level) // pop to current level
                pathNode = currentPath.pop();
            if (currentPath.size() != level)
                throw new IllegalStateException("unexpected path size, "
                        + currentPath.size() + " and traversal level, "
                        + level);
            int siblingIndex = 0;
            // If current node is a sibling (e.g. same parent and same source property), assume
            // it's index is the previous path-node index +1
            if (((PlasmaNode)pathNode.getSource()).getUUIDAsString().equals(
                    ((PlasmaNode)source).getUUIDAsString())) {
                if (pathNode.getSourceProperty().getName().equals(
                        sourceProperty.getName())) 
                    siblingIndex = pathNode.getIndex()+1;
            }
            
            currentPath.push(new PathNode(target, siblingIndex, 
                    source, sourceProperty));
            
            if (((PlasmaNode)target).getUUIDAsString().equals(pathTarget.getUUIDAsString())) 
            {    
                Stack<PathNode> path = new Stack<PathNode>();
                path.addAll(currentPath); // copy current state
                pathList.add(path);
            }           
        }  
    }
    
    @SuppressWarnings("unchecked")
    public String getMinimumPathString() {
        Stack<PathNode> path = getMinimumPath();
        Stack<PathNode> clone = (Stack<PathNode>)path.clone();
        
        String result = getPathString(clone);
        return result;
    }

    public int getMinimumPathDepth() {
        return getMinimumPath().size();
    }
    
    private Stack<PathNode> getMinimumPath() {
        if (pathList.size() == 0) {
            DataObject root = this.pathTarget.getDataObject().getDataGraph().getRootObject();
            throw new IllegalStateException("no paths from root data-object, " 
            		+ root.getType().getURI() + "#" + root.getType().getName() 
            		+ " (" + ((PlasmaNode)root).getUUIDAsString() + ") "
            		+ "to target, "
            		+ this.pathTarget.getDataObject().getType().getURI() + "#"
            		+ this.pathTarget.getDataObject().getType().getName() + " ("
            		+ this.pathTarget.getUUIDAsString() + ")");
        }    
        
        Stack<PathNode> minPath = null;
        
        for (Stack<PathNode> path : pathList) {
            if (minPath == null || path.size() < minPath.size())
                minPath = path;
        }
        return minPath;
    }
    
    public int getPathCount() {
        return this.pathList.size();
    }
    
    public String[] getAllPaths() {
        String[] result = new String[this.pathList.size()];
        for (int i = 0; i < this.pathList.size(); i++)
            result[i] = getPathString(this.pathList.get(i));
        return result;
    }
    
    private String getPathString(Stack<PathNode> path) {
        
        PathNode pathNode = null;
        int size = path.size();
        List<String> tokens = new ArrayList<String>(); 
        for (int i = 0; i < size; i++) {
            pathNode = path.pop();
            if (pathNode.getSource() != null)
            {    
                if (i > 0)
                    tokens.add(PATH_DELIM);
                if (pathNode.getSourceProperty().isMany())
                    tokens.add(pathNode.getSourceProperty().getName() 
                        + PATH_INDEX_RIGHT + String.valueOf(pathNode.getIndex()) + PATH_INDEX_LEFT);
                else
                    tokens.add(pathNode.getSourceProperty().getName());
            }
        }
        tokens.add(PATH_PREFIX);
        StringBuffer buf = new StringBuffer();
        for (int i = tokens.size()-1; i >= 0; i--)
            buf.append(tokens.get(i));
        
        return buf.toString();
    }
    
    class PathNode {
        
        private DataObject target;
        private DataObject source;
        private Property sourceProperty;
        private int index = -1;
        @SuppressWarnings("unused")
        private PathNode() {}
        
        public PathNode(DataObject target, int index, 
                DataObject source, Property sourceProperty) {
            this.target = target;
            this.index = index;
            this.source = source;
            this.sourceProperty = sourceProperty;
        }

        public DataObject getTarget() {
            return target;
        }

        public DataObject getSource() {
            return source;
        }

        public Property getSourceProperty() {
            return sourceProperty;
        }

        public int getIndex() {
            return index;
        }
    }
}
