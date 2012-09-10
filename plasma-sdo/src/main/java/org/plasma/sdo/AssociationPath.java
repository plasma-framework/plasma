package org.plasma.sdo;


/**
 * Defines the structural nature of the path or set of 
 * paths between two model types, where a path may involve one or more traversals or hops
 * from the source to the destination through any number of
 * SDO reference properties. 
 */
public enum AssociationPath {
	/** Two model elements are associated through any number of singular reference properties */
	singular,	
	/** Two model elements are associated through one-and-only-one singular reference property */
	singular_unary,
	/** Two model elements are associated through two singular reference properties */
	singular_binary,
	/** Two model elements are associated through three singular reference properties */
	singular_ternary,
	/** Two model elements are associated through more-than-three singular reference properties */
	singular_nary,
	
	/** Two model elements are associated through any number of multi-valued (many) reference properties */
	many,
	/** Two model elements are associated through one-and-only-one multi-valued (many) reference property */
	many_unary,
	/** Two model elements are associated through two multi-valued (many) reference properties */
	many_binary,
	/** Two model elements are associated through three multi-valued (many) reference properties */
	many_ternary,
	/** Two model elements are associated through more-than-three multi-valued (many) reference properties */
	many_nary,
}
