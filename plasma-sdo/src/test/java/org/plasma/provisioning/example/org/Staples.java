package org.plasma.provisioning.example.org;

import org.plasma.provisioning.example.common.Concurrent;
import org.plasma.sdo.DataType;
import org.plasma.sdo.annotation.Type;
import org.plasma.sdo.annotation.DataProperty;

@Type(name = "Staples", superTypes = {Organization.class, Concurrent.class})
public enum Staples {
	@DataProperty(dataType = DataType.Long, isNullable = false)
	STAPLES_STORE_ID_,
	@DataProperty(dataType = DataType.Long, isNullable = true, isMany = true)
	OTHER_NAMES;
}
