package hk.com.novare.tempoplus.accountsystem.employeemanager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class EmployeeManagerService {
	
	@Inject EmployeeManagerDAO employeeManagerDao;

	public List<EmployeeManager> retrieveSubordinatesList(int supervisorId){
		return employeeManagerDao.searchSubordinates(supervisorId);
	}
	
	public List<EmployeeManager> retrieveAddSubordinatesDetails(String employeeName){

		return employeeManagerDao.searchToAddSubordinates(employeeName);
	}
	
	public boolean hasEmployeeRecord(){

		return employeeManagerDao.employeeIsFound();
	}
	
	public boolean updateSupervisor(int supervisorId, ArrayList<Integer> subordinateIds){

		return employeeManagerDao.changeEmployeeSupervisor(supervisorId, subordinateIds);
	}
}
