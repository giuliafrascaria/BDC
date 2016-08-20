package exceptions;

public class FluxNotExistsException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2468499743460381342L;
	
	private int type; //1 for num 2 for den

	public int getType() {
		return type;
	}
	
	public FluxNotExistsException() {
		super("The flux doesn't exists.");
	}

	public FluxNotExistsException(String galaxy, int type) {
		super("The input flux doesn't exists for the galaxy " + galaxy);
		this.type = type;
	}

	public FluxNotExistsException(Throwable cause) {
		super(cause);
	}
}
