package exceptions;

public class AdminException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8218896428719100868L;

	public AdminException() {
		super("The user is an administrator.");
	}

	public AdminException(Throwable cause) {
		super(cause);
	}
}
