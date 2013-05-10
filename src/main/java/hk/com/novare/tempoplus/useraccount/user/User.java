package hk.com.novare.tempoplus.useraccount.user;

public class User {
	
	private int employeeId;
	private int biometricId;
	
	private String lastname;
	private String firstname;
	private String secondname;
	private String middlename;
	
	private String email;
	private String password; 
	
	private String hireDate;
	private String regularizationDate;
	private String resignationDate;
	
	private int departmentId;
	private int positionId;
	private int level;
	private int shiftId;
	
	private String department;
	private String position;
	private String shift;
	
	private int supervisorId;
	private int isSupervisor;
	private int flag;
	private String supervisor;
	private String supervisorEmail;

	/*
	 * getters and setters for user details
	 */
	
	//IDs
	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	
	public int getBiometricId() {
		return biometricId;
	}

	public void setBiometricId(int biometricId) {
		this.biometricId = biometricId;
	}
	
	
	//employee name
	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getSecondname() {
		return secondname;
	}

	public void setSecondname(String secondname) {
		this.secondname = secondname;
	}

	public String getMiddlename() {
		return middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	
	//login details
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
	
	
	//work details
	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public int getPositionId() {
		return positionId;
	}

	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getShiftId() {
		return shiftId;
	}

	public void setShiftId(int shiftId) {
		this.shiftId = shiftId;
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

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	
	
	//Employee's supervisor
	public int getSupervisorId() {
		return supervisorId;
	}

	public void setSupervisorId(int supervisorId) {
		this.supervisorId = supervisorId;
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
	
	
	
	public int getIsSupervisor() {
		return isSupervisor;
	}

	public void setIsSupervisor(int isSupervisor) {
		this.isSupervisor = isSupervisor;
	}
	
	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
}
