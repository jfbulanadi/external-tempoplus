package hk.com.novare.tempoplus.bmnmanager.timesheet;

import hk.com.novare.tempoplus.bmnmanager.biometric.BiometricDetails;
import hk.com.novare.tempoplus.bmnmanager.mantis.Mantis;
import hk.com.novare.tempoplus.bmnmanager.nt3.Nt3;
import hk.com.novare.tempoplus.employee.EmployeeDetails;
import hk.com.novare.tempoplus.timelogging.TimeLogging;

public class Timesheet {

	private int id;
	private EmployeeDetails employeeDetails;
	private TimeLogging timelog;
	private Mantis mantis;
	private Nt3 nt3;
	private BiometricDetails biometricDetails;
	
	public TimeLogging getTimelog() {
		return timelog;
	}

	public EmployeeDetails getEmployeeDetails() {
		return employeeDetails;
	}

	public void setEmployeeDetails(EmployeeDetails employeeDetails) {
		this.employeeDetails = employeeDetails;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TimeLogging getTimelogList() {
		return timelog;
	}

	public void setTimelog(TimeLogging timelog) {
		this.timelog = timelog;
	}

	public Mantis getMantis() {
		return mantis;
	}

	public void setMantis(Mantis mantis) {
		this.mantis = mantis;
	}

	public Nt3 getNt3() {
		return nt3;
	}

	public void setNt3(Nt3 nt3) {
		this.nt3 = nt3;
	}

	public BiometricDetails getBiometricDetails() {
		return biometricDetails;
	}

	public void setBiometricDetails(BiometricDetails biometricDetails) {
		this.biometricDetails = biometricDetails;
	}

	
}
