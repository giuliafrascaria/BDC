package exceptions;

public class GalaxyNotExistsException extends Exception {
	private String galaxyName;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getName () {
		return galaxyName;
	}
	
	public GalaxyNotExistsException() {
		super("The galaxy of the flux doesn't exists so violates foreign key constraint.");
	}

	public GalaxyNotExistsException(String galaxy) {
		super("The galaxy, " + galaxy + "doesn't exists, so the flux violates foreign key constraint.");
		galaxyName = galaxy;
	}

	public GalaxyNotExistsException(Throwable cause) {
		super(cause);
	}
}
