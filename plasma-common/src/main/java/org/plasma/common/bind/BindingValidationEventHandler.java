
package org.plasma.common.bind;

import javax.xml.bind.ValidationEventHandler;

public interface BindingValidationEventHandler extends ValidationEventHandler {

    public int getErrorCount();
}
