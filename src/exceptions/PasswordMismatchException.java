package exceptions;

public class PasswordMismatchException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8166047651992805123L;

	public PasswordMismatchException() {
		super("The email and the repeated email do not match.");
	}

	public PasswordMismatchException(String password, String passwordCopy) {
		super("The email, " + password + ", and the repeated email, " + passwordCopy + ", do not match.");
	}

	public PasswordMismatchException(Throwable cause) {
		super(cause);
	}
}
