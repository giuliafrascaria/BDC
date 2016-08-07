package exceptions;

public class NothingFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7525924134021066419L;

	public NothingFoundException() {
		super("Nothing found for the Parameters.");
	}

	public NothingFoundException(Throwable cause) {
		super(cause);
	}
}
