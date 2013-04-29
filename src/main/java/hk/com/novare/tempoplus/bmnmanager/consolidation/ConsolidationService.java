package hk.com.novare.tempoplus.bmnmanager.consolidation;

import java.sql.SQLException;
import java.util.ArrayList;

import hk.com.novare.tempoplus.employee.Employee;
import hk.com.novare.tempoplus.employee.EmployeeDao;

import javax.inject.Inject;



public class ConsolidationService {

	@Inject ConsolidationDao consolidationDao;
	@Inject EmployeeDao employeeDao;
	
//	public boolean isReadyForConsolidation(int id) {			
//		
//		Consolidation consolidation = consolidationDao.isReadyForConsolidation(id);
//		if(consolidation.getBiometricId() != 0 && consolidation.getMantisId() != 0 && consolidation.)
//		
//	}

	public boolean createConsolidatedTimesheet(String name, String periodStart, String periodEnd) {		
		return consolidationDao.createCondolidatedTimesheet(name, periodStart, periodEnd);
	}
	
	public void consolidateTimesheet() {
		
		consolidationDao.consolidateTimeSheet(employeeDao.retrieveEmployeeIds());
		
	}
	
	public ArrayList<Employee> viewConsolidated() throws SQLException  {
		return consolidationDao.viewConsolidated();
	}
	
	public ArrayList<Employee> updateViewConsolidated(int employeeid, String firstname) throws SQLException {
		return consolidationDao.updateViewConsolidated(employeeid, firstname);
		
		
	}	

	
}
