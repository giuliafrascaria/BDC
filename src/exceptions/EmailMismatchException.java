package exceptions;

public class EmailMismatchException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1637338714287138854L;

	public EmailMismatchException() {
		super("The email and the repeated email do not match.");
	}

	public EmailMismatchException(String mail, String mailCopy) {
		super("The email, " + mail + ", and the repeated email, " + mailCopy + ", do not match.");
	}

	public EmailMismatchException(Throwable cause) {
		super(cause);
	}
}
