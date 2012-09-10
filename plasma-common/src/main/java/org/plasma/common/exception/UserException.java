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

