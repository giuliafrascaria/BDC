package exceptions;

public class PositionTableEmptyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PositionTableEmptyException() {
		super("The table 'position' of the DB is empty.");
	}

	public PositionTableEmptyException(Throwable cause) {
		super(cause);
	}
}
