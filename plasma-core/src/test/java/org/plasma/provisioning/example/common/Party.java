package org.plasma.provisioning.example.common;

import org.plasma.sdo.DataType;
import org.plasma.sdo.annotation.Alias;
import org.plasma.sdo.annotation.Type;
import org.plasma.sdo.annotation.DataProperty;

@Type(name = "Party")
public enum Party {
  @Alias(physicalName = "CD")
  @DataProperty(dataType = DataType.DateTime, isNullable = false)
  createdDate, @Alias(physicalName = "LUB")
  @DataProperty(dataType = DataType.String)
  lastUpdatedBy, @Alias(physicalName = "LUD")
  @DataProperty(dataType = DataType.DateTime)
  lastUpdatedDate;
}
