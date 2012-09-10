package org.plasma.sdo.core;

import org.plasma.sdo.PlasmaDataObject;
import org.plasma.sdo.PlasmaProperty;


/**
 * Stores a data object, the source/container data object and
 * source property that links or references it.  
 */
public class ContainmentNode {
	private PlasmaDataObject dataObject;
	private PlasmaProperty containmentProperty;
	private PlasmaDataObject container;
	
	@SuppressWarnings("unused")
	private ContainmentNode() {}
	public ContainmentNode(PlasmaDataObject dataObject,
			PlasmaDataObject container, PlasmaProperty containmentProperty) 
	{
		this.dataObject = dataObject;
		this.container = container;
		this.containmentProperty = containmentProperty;
	}
	public PlasmaProperty getContainmentProperty() {
		return containmentProperty;
	}
	public PlasmaDataObject getSourceContainer() {
		return container;
	}    
	public PlasmaDataObject getDataObject() {
		return dataObject;
	}  
}
