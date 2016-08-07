package exceptions;

public class CityNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -61690244429667098L;
	
	public CityNotFoundException() {
		super("We don't have any advertisement in the city.");
	}

	public CityNotFoundException(String city) {
		super("We don't have any advertisement in this city: " + city);
	}

	public CityNotFoundException(Throwable cause) {
		super(cause);
	}
}
