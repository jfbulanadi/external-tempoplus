package hk.com.novare.tempoplus.bmnmanager.shiftmanager;

public class DataAccessException extends Exception {
	
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
