package org.plasma.provisioning.example.org;

import org.plasma.provisioning.example.common.Party;
import org.plasma.provisioning.example.pers.Person;
import org.plasma.sdo.DataType;
import org.plasma.sdo.annotation.Alias;
import org.plasma.sdo.annotation.Type;
import org.plasma.sdo.annotation.DataProperty;
import org.plasma.sdo.annotation.EnumConstraint;
import org.plasma.sdo.annotation.ReferenceProperty;
import org.plasma.sdo.annotation.ValueConstraint;

@Type(name = "Organization", superTypes = { Party.class })
@Alias(physicalName = "O")
public enum Organization {
  @ValueConstraint(maxLength = "12")
  @Alias(physicalName = "N")
  @DataProperty(dataType = DataType.String, isNullable = false)
  name, @EnumConstraint(targetEnum = OrgType.class)
  @Alias(physicalName = "T")
  @DataProperty(dataType = DataType.String, isNullable = false)
  type, @Alias(physicalName = "P")
  @ReferenceProperty(isNullable = false, isMany = true, targetClass = Person.class, targetProperty = "org")
  person;
}
