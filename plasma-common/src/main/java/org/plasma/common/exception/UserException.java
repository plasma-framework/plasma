/**
 *         PlasmaSDO™ License
 * 
 * This is a community release of PlasmaSDO™, a dual-license 
 * Service Data Object (SDO) 2.1 implementation. 
 * This particular copy of the software is released under the 
 * version 2 of the GNU General Public License. PlasmaSDO™ was developed by 
 * TerraMeta Software, Inc.
 * 
 * Copyright (c) 2013, TerraMeta Software, Inc. All rights reserved.
 * 
 * General License information can be found below.
 * 
 * This distribution may include materials developed by third
 * parties. For license and attribution notices for these
 * materials, please refer to the documentation that accompanies
 * this distribution (see the "Licenses for Third-Party Components"
 * appendix) or view the online documentation at 
 * <http://plasma-sdo.org/licenses/>.
 *  
 */
package org.plasma.common.exception;



/**
 */
public class UserException extends PlasmaRuntimeException 
{
    protected String severityId;
    protected String typeId;
    protected String messageId;
    protected Object[] params;

    private UserException() {}

    public UserException(String severityId, String typeId, String messageId) 
    {
        super("user error");
        this.severityId = severityId;
        this.typeId = typeId;
        this.messageId = messageId;
    }

    public UserException(String severityId, String typeId, String messageId,
        String defaultErrorText) 
    {
        super(defaultErrorText);
        this.severityId = severityId;
        this.typeId = typeId;
        this.messageId = messageId;
    }

    public UserException(String severityId, String typeId, String messageId, Object[] params) 
    {
        super("user error");
        this.severityId = severityId;
        this.typeId = typeId;
        this.messageId = messageId;
        this.params = params;
    }

    public UserException(String severityId, String typeId, String messageId, 
        Object[] params, String defaultErrorText) 
    {
        super(defaultErrorText);
        this.severityId = severityId;
        this.typeId = typeId;
        this.messageId = messageId;
        this.params = params;
    }

    public String getSeverityId() { return severityId; }
    public String getTypeId() { return typeId; }
    public String getMessageId() { return messageId; }    
    public Object[] getParams() { return params; }
    public boolean hasParams() { return params != null; }
}

