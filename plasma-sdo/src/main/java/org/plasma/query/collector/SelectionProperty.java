package org.plasma.query.collector;

import java.util.Comparator;

import commonj.sdo.Property;

public class SelectionProperty {
    private commonj.sdo.Property property;
    private int sequence;
    
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((property == null) ? 0 : property.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SelectionProperty other = (SelectionProperty) obj;
		if (property == null) {
			if (other.property != null)
				return false;
		} else if (!property.equals(other.property))
			return false;
		return true;
	}
	public SelectionProperty(Property property, int sequence) {
		super();
		this.property = property;
		this.sequence = sequence;
	}
	public commonj.sdo.Property getProperty() {
		return property;
	}
	public int getSequence() {
		return sequence;
	}
	
	static Comparator<SelectionProperty> comp;
	static Comparator<SelectionProperty> getComparator() {
		if (comp == null) {
			comp = new Comparator<SelectionProperty>() {
				@Override
				public int compare(SelectionProperty o1, SelectionProperty o2) {
					return Integer.valueOf(o1.getSequence()).compareTo(Integer.valueOf(o2.getSequence()));
				}			
		    };
		}
		return comp;
	}
    
}
