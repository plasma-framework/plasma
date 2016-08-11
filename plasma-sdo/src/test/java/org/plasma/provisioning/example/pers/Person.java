package org.plasma.provisioning.example.pers;

import org.plasma.provisioning.example.org.Organization;
import org.plasma.sdo.DataType;
import org.plasma.sdo.annotation.Alias;
import org.plasma.sdo.annotation.Type;
import org.plasma.sdo.annotation.DataProperty;
import org.plasma.sdo.annotation.ReferenceProperty;

@Alias(physicalName = "P")
@Type(name = "Person")
public enum Person {
	@Alias(physicalName = "N")
	@DataProperty(dataType = DataType.String, isNullable = false)
    name,
	@Alias(physicalName = "A")
	@DataProperty(dataType = DataType.Int)
    age,
	@Alias(physicalName = "DOB")
	@DataProperty(dataType = DataType.Date)
    dateOfBirth,
	@Alias(physicalName = "O")
	@ReferenceProperty(targetClass = Organization.class, targetProperty = "pers")
    org;
}
