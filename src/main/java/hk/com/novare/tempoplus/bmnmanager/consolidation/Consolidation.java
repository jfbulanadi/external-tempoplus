package hk.com.novare.tempoplus.bmnmanager.consolidation;

public class Consolidation {

	/*
	 * Test by Kenny
	 * Test by Kenny v2.
	 * 
	 */
	
	private int id;
	private int userId;
	private int biometricId;
	private int mantisId;
	private int nt3Id;
	private String name;
	private String periodStart;
	private String periodEnd;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getBiometricId() {
		return biometricId;
	}

	public void setBiometricId(int biometricId) {
		this.biometricId = biometricId;
	}

	public int getMantisId() {
		return mantisId;
	}

	public void setMantisId(int mantisId) {
		this.mantisId = mantisId;
	}

	public int getNt3Id() {
		return nt3Id;
	}

	public void setNt3Id(int nt3Id) {
		this.nt3Id = nt3Id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPeriodStart() {
		return periodStart;
	}

	public void setPeriodStart(String periodStart) {
		this.periodStart = periodStart;
	}

	public String getPeriodEnd() {
		return periodEnd;
	}

	public void setPeriodEnd(String periodEnd) {
		this.periodEnd = periodEnd;
	}

}
