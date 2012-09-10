package org.plasma.sdo.access.provider.common;

import java.util.List;

import org.plasma.sdo.PlasmaDataObject;

import commonj.sdo.DataObject;

public interface CommitCollector {
	public List<PlasmaDataObject> getResult();
}
