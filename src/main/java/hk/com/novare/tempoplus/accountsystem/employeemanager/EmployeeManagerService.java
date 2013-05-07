package hk.com.novare.tempoplus.accountsystem.employeemanager;

import java.util.List;

import javax.inject.Inject;

public class EmployeeManagerService {
	
	@Inject EmployeeManagerDAO employeeManagerDao;

	public List<EmployeeManager> retrieveSubordinatesList(int supervisorId){
		return employeeManagerDao.searchSubordinates(supervisorId);
	}
	
	public List<EmployeeManager> retrieveAddSubordinatesDetails(String employeeName){
		System.out.println("[ @ retrieve service ");
		return employeeManagerDao.searchToAddSubordinates(employeeName);
	}
	
	public boolean hasEmployeeRecord(){
		System.out.println("[ @ found service ");
		return employeeManagerDao.employeeIsFound();
	}
}
