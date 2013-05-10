package hk.com.novare.tempoplus.accountsystem;

import org.springframework.stereotype.Service;


public class HumanResource {
	
	private String firstName;
	private String middleName;
	private String lastName;
	private String employeeId;
	private String biometrics;
	private String department;	
	private String shift;
	private String position;
	private String level;
	private String hiredDate;
	private String regularizationDate;
	private String resignationDate;
	private String supervisorName;
	private String supervisorEmail;
	private String locAssign;
	private String payrollName;
	private String employeeEmail;
	private String active;
	private int departmentId;
	private int positionId;
	
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
	public String getShift() {
		return shift;
	}
	public void setShift(String shift) {
		this.shift = shift;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getHiredDate() {
		return hiredDate;
	}
	public void setHiredDate(String hiredDate) {
		this.hiredDate = hiredDate;
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
	public String getSupervisorName() {
		return supervisorName;
	}
	public void setSupervisorName(String supervisorName) {
		this.supervisorName = supervisorName;
	}
	public String getSupervisorEmail() {
		return supervisorEmail;
	}
	public void setSupervisorEmail(String supervisorEmail) {
		this.supervisorEmail = supervisorEmail;
	}
	public String getLocAssign() {
		return locAssign;
	}
	public void setLocAssign(String locAssign) {
		this.locAssign = locAssign;
	}
	public String getPayrollName() {
		return payrollName;
	}
	public void setPayrollName(String payrollName) {
		this.payrollName = payrollName;
	}
	public String getEmployeeEmail() {
		return employeeEmail;
	}
	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	
	
	
	
	
}
