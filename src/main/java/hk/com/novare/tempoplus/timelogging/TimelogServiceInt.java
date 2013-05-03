package hk.com.novare.tempoplus.timelogging;

import hk.com.novare.tempoplus.employee.Employee;

import java.text.ParseException;
import java.util.List;

public interface TimelogServiceInt {
	
	/**
	 * Timelog Service Interface
	 * @throws DataAccessException
	 * @throws ParseException
	 */
	void flaggingProcess() ;
	String validateInput(int id,String name,String from , String to);
	
	List retrieveSubordinates(int id);
	List<TimeLogging> retrieveTimelog(String name, String from, String to);
	List<TimeLogging> retrieveMylog(int id, String from, String to);
	String checkUser(int id);
	
	//Hr View
		String checkName(String name);
		List<Employee> searchEmployees(String name);
		
	//TimeLogs
		void logTimeIn(TimeLogging timelogs);
		int getEmployeeId();
		void logTimeOut(TimeLogging timeLogging);
		String getCurrentTime();
		String getCurrentDate();

		
}

