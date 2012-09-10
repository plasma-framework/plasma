package org.plasma.sdo.access;

import org.plasma.sdo.PlasmaDataGraph;

/**
 * Constructs a data graph starting with a given root SDO type.
 */
public interface DataGraphAssembler {
	
    /**
    * Returns the assembled data graph.
    */
    public PlasmaDataGraph getDataGraph();

	/**
	 * Resets the assembler.
	 */
	public void clear();

}