package exceptions;

public class UserAlreadyRegistredException extends Exception {
	//Mario exception for the registration

	/**
	 * 
	 */
	private static final long serialVersionUID = 698067566767772941L;

	public UserAlreadyRegistredException() {
		super("There was already an User with the input mail.");
	}

	public UserAlreadyRegistredException(String mail) {
		super("There was already an User with this email: " + mail);
	}

	public UserAlreadyRegistredException(Throwable cause) {
		super(cause);
	}
}
