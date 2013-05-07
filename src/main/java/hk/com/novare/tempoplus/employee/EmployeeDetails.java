package hk.com.novare.tempoplus.employee;

public class EmployeeDetails {

	private int id;
	private int employeeId;
	private String fullName;
	private int biometricId;
	private String shift;
	private String email;
	private String hireDate;
	private String regularizationDate;
	private String resignationDate;
	private String department;
	private String position;
	private int level;
	private String supervisor;
	private String supervisorEmail;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getBiometricId() {
		return biometricId;
	}

	public void setBiometricId(int biometricId) {
		this.biometricId = biometricId;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHireDate() {
		return hireDate;
	}

	public void setHireDate(String hireDate) {
		this.hireDate = hireDate;
	}

	public String getRegularizationDate() {
		return regularizationDate;
	}

	public void setRegularizationDate(String regularizationDate) {
		this.regularizationDate = regularizationDate;
	}

	public String getResignationDate() {
		return resignationDate;
	}

	public void setResignationDate(String resignationDate) {
		this.resignationDate = resignationDate;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	public String getSupervisorEmail() {
		return supervisorEmail;
	}

	public void setSupervisorEmail(String supervisorEmail) {
		this.supervisorEmail = supervisorEmail;
	}

}
