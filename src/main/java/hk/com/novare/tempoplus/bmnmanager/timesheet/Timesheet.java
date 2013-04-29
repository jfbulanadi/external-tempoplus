package hk.com.novare.tempoplus.bmnmanager.timesheet;

import hk.com.novare.tempoplus.bmnmanager.mantis.Mantis;
import hk.com.novare.tempoplus.bmnmanager.nt3.Nt3;
import hk.com.novare.tempoplus.employee.Employee;
import hk.com.novare.tempoplus.timelogging.TimeLogging;

public class Timesheet {

	private int id;
	private Employee employee;
	private TimeLogging timelog;
	private Mantis mantis;
	private Nt3 nt3;

	public TimeLogging getTimelog() {
		return timelog;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
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

}
