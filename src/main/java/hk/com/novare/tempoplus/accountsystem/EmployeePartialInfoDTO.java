package hk.com.novare.tempoplus.accountsystem;

public class EmployeePartialInfoDTO {
	
	private String firstName;
	private String middleName;
	private String lastName;
	private String employeeId;
	private String biometrics;
	private String department;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getBiometrics() {
		return biometrics;
	}
	public void setBiometrics(String biometrics) {
		this.biometrics = biometrics;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}	
	
	

}
