package org.plasma.sdo;

import commonj.sdo.ChangeSummary;

public interface PlasmaSetting extends ChangeSummary.Setting {
  
    public String getValuePath();

    public long getCreatedMillis();    
}
