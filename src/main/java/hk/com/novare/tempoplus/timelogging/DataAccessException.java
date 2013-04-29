package hk.com.novare.tempoplus.timelogging;

public class DataAccessException extends Exception {
	/**
	 * Timelog Customize Exception
	 */
	
	public DataAccessException() {
		super();
	}

	public DataAccessException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataAccessException(String message) {
		super(message);
	}

	public DataAccessException(Throwable cause) {
		super(cause);
	}
}
