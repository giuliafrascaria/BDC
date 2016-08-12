package exceptions;

public class GalaxyNotExistsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GalaxyNotExistsException() {
		super("The galaxy of the flux doesn't exists so violates foreign key constraint.");
	}

	public GalaxyNotExistsException(String galaxy) {
		super("The galaxy, " + galaxy + "doesn't exists, so the flux violates foreign key constraint.");
	}

	public GalaxyNotExistsException(Throwable cause) {
		super(cause);
	}
}
