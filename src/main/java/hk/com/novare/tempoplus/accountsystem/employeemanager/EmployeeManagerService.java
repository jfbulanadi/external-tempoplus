package hk.com.novare.tempoplus.accountsystem.employeemanager;

import java.util.List;

import javax.inject.Inject;

public class EmployeeManagerService {
	
	@Inject EmployeeManagerDAO employeeManagerDao;

	public List<EmployeeManager> retrieveSubordinatesList(int supervisorId){
		return employeeManagerDao.searchSubordinates(supervisorId);
	}
	
	public List<EmployeeManager> retrieveEmployeeDetails(int employeeId){
		return employeeManagerDao.searchSubordinates(employeeId);
	}
}
