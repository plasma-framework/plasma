package org.plasma.sdo.core;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.sdo.PlasmaNode;
import org.plasma.sdo.PlasmaSetting;
import org.plasma.sdo.core.NullValue;

import commonj.sdo.ChangeSummary;
import commonj.sdo.DataObject;
import commonj.sdo.Property;

public class CoreSetting implements PlasmaSetting {

    private static Log log = LogFactory.getLog(CoreSetting.class);
    private DataObject source;
    private Property property;
    private Object value;
    private String valuePath; // need to capture before it changes
    private boolean isSet;
    private long createdMillis = System.currentTimeMillis();
    
    @SuppressWarnings("unused")
    private CoreSetting() {}
    
    public CoreSetting(DataObject source, Property property, Object value) {
        this.source = source;
        this.property = property;
        this.value = value;
        this.isSet = source.isSet(property); // captured as setting is created and added to summary/history
        if (!property.getType().isDataType()) {
            
            if (this.value instanceof List) {
                List<DataObject> list = (List<DataObject>)this.value;
                StringBuffer buf = new StringBuffer();
                buf.append("list[");
                if (list != null) {
                    
                    for (int i = 0; i < list.size(); i++) {
                        DataObject dataObject = list.get(i);
                        if (i > 0)
                            buf.append(", ");
                        buf.append(((PlasmaNode)dataObject).getUUIDAsString());
                    }
                }
                buf.append("]");
                valuePath = buf.toString();
            }
            else {
                if (this.value != null)
                    if (this.value instanceof PlasmaNode) {
                        valuePath = ((PlasmaNode)this.value).getUUIDAsString();
                    }
                    else if (!(this.value instanceof NullValue))
                        log.error("expected instance of PlasmaNode or NullValue");                    
            }
        }
    }
    
    /**
     * Returns the property of the setting.
     * @return the setting property.
     */
    public Property getProperty() {
        return property;
    }

    /**
     * Returns the value of the setting.
     * @return the setting value.
     */
    public Object getValue() {
        return value;
    }

    
    public String getValuePath() {
        return valuePath;
    }

    /**
     * Returns whether or not the property is set.
     * @return <code>true</code> if the property is set.
     */
    public boolean isSet() {
        return this.isSet;
    }
    
    public long getCreatedMillis() {
        return createdMillis;
    }
    
}
