package hk.com.novare.tempoplus.bmnmanager.biometric;

/**
 * 
 * @author Jeffrey
 *
 */
public class Biometric {

	private int biometricsId;
	private String date;
	private String time;
	private int log;
	private int unknown;

	public int getBiometricsId() {
		return biometricsId;
	}

	public void setBiometricsId(int biometricsId) {
		this.biometricsId = biometricsId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getLog() {
		return log;
	}

	public void setLog(int log) {
		this.log = log;
	}

	public int getUnknown() {
		return unknown;
	}

	public void setUnknown(int unknown) {
		this.unknown = unknown;
	}

}
