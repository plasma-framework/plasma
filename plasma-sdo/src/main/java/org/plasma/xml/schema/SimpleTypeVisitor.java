package org.plasma.xml.schema;

import javax.xml.namespace.QName;

/**
 * Helps implementation classes avoid the (breadth-first) 
 * traversal logic for XSD SimpleType hierarchies
 * across restrictions, lists, unions and other elements,
 * receiving "visit" events ala. the Visitor pattern.
 * @see org.plasma.xml.schema.Restriction
 * @see org.plasma.xml.schema.List
 * @see org.plasma.xml.schema.Union
 */
public interface SimpleTypeVisitor {
	/**
	 * The top event received when a simple type is encountered
	 * in the course of traversal.
     * @param target the target or "child" type
     * @param source the source or "parent" type
     * @param level the traversal level
	 */
    public void visit(AbstractSimpleType target, AbstractSimpleType source, int level);
    
    /**
     * Since the the type hierarchy traversal will encounter 
     * qualified name references to many simple types, the
     * traversal logic requests a simple type from the visitor client. 
     * @param name the qualified name of the simple type
     * @return the top level simple type
     */
    public SimpleType getTopLevelSimpleType(QName name);
    
    /**
     * Returns the target namespace for a particular 
     * schema instance. 
     * @return the target namespace
     */
    public String getTargetNamespace();
}
