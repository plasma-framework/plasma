package org.plasma.sdo;

public class IllegalArgumentUserException extends IllegalArgumentException {

    private static final long serialVersionUID = 1L;

    public IllegalArgumentUserException(String message) {
        super(message);
    }

	public IllegalArgumentUserException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalArgumentUserException(Throwable cause) {
		super(cause);
	}
}
