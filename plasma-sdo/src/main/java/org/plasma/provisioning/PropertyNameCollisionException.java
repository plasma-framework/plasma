package org.plasma.provisioning;


public class PropertyNameCollisionException extends ProvisioningException {
	private static final long serialVersionUID = 1L;

	public PropertyNameCollisionException(String msg) {
        super(msg);
    }
}
