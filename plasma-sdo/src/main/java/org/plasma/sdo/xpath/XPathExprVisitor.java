package org.plasma.sdo.xpath;

import org.jaxen.expr.Expr;

/**
 * Receives notifications as a Jaxen XPATH 
 * expression tree is traversed.  
 */
public interface XPathExprVisitor {
    public void visit(Expr target, Expr source, int level);       
}
