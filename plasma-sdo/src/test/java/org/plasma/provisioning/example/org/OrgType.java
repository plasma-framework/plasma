package org.plasma.provisioning.example.org;

import org.plasma.sdo.annotation.Alias;
import org.plasma.sdo.annotation.Enumeration;

@Enumeration(name = "OrgType")
public enum OrgType {
  @Alias(physicalName = "R")
  retail, @Alias(physicalName = "W")
  wholesale
}
