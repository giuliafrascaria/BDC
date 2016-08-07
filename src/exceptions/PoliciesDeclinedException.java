package exceptions;

public class PoliciesDeclinedException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7580287446955474168L;

	public PoliciesDeclinedException() {
		super("The policies ware not accepted");
	}

	public PoliciesDeclinedException(Throwable cause) {
		super(cause);
	}
}
