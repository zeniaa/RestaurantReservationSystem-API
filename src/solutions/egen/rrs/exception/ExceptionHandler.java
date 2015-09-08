package solutions.egen.rrs.exception;

public class ExceptionHandler extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2300766411249095676L;

	public ExceptionHandler(String msg) {
		super(msg);
	}

	public ExceptionHandler(String msg, Throwable cause) {
		super(msg, cause);
	}

}
