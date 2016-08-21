package exceptions;

public class ClassNotExistsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5889444257268613595L;

	public ClassNotExistsException() {
		super("The input spectral class not exists in the database.");
	}

	public ClassNotExistsException(Throwable cause) {
		super(cause);
	}
}
