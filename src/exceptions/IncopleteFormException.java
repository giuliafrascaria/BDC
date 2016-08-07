package exceptions;

public class IncopleteFormException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6479739361354063906L;

	public IncopleteFormException() {
		super("The form is not fully complet.");
	}

	public IncopleteFormException(Throwable cause) {
		super(cause);
	}
}
