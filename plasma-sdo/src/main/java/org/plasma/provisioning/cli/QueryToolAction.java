package org.plasma.provisioning.cli;

public enum QueryToolAction {
    
    /** 
     * Takes the XML representation for a query, as described by a PlasmaQuery<sup>TM</sup> or other supported
     * declarative query framework, and generates an output model based on the
     * target type.
     * @see org.plasma.query.Query 
     * @see QueryToolTargetType
     */
    compile,  
    /** 
     * Takes the XML representation for a query, as described by a PlasmaQuery<sup>TM</sup> or other supported
     * declarative query framework, runs the query against the Plasma SDO runtime, 
     * and generates/returns SDO compliant XML. 
     * @see org.plasma.query.Query 
     */
    run  
}
