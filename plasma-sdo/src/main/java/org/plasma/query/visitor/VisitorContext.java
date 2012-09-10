package org.plasma.query.visitor;

/**
 * Encapsulates logic and data specific to
 * a single traversal.
 */
public class VisitorContext {
    private Traversal traversal = Traversal.CONTINUE;

	public Traversal getTraversal() {
		return traversal;
	}

	public void setTraversal(Traversal traversal) {
		this.traversal = traversal;
	}
	
	/**
	 * Returns true if traversal should continue.
	 * @return true if traversal should continue
	 */
	public boolean isContunue() {
		return this.traversal.ordinal() == Traversal.CONTINUE.ordinal();
	}
	
	/**
	 * Signals the termination of traversal.
	 */
	public void abort() {
		traversal = Traversal.ABORT;
	}
}
