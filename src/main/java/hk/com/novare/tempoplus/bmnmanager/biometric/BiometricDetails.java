package hk.com.novare.tempoplus.bmnmanager.biometric;

public class BiometricDetails {
	
	private int biometricId;
	private String date;
	private String timeIn;
	private String timeOut;
	
	public int getBiometricId() {
		return biometricId;
	}
	public void setBiometricId(int biometricId) {
		this.biometricId = biometricId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTimeIn() {
		return timeIn;
	}
	public void setTimeIn(String timeIn) {
		this.timeIn = timeIn;
	}
	public String getTimeOut() {
		return timeOut;
	}
	public void setTimeOut(String timeOut) {
		this.timeOut = timeOut;
	}

	
	
}
