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
	void flaggingProcess() throws DataAccessException, ParseException;
	String ValidateInput(int id,String name,String from , String to) throws ParseException;
	
	List retrieveSubordinates(int id) throws DataAccessException;
	List<TimeLogging> retrieveTimelog(String name, String from, String to) throws DataAccessException;
	List<TimeLogging> retrieveMylog(int id, String from, String to)  throws DataAccessException;
	String checkUser(int id) throws DataAccessException;
	
	//Hr View
		String checkName(String name);
		List<Employee> searchEmployees(String name) throws DataAccessException;
		
	//TimeLogs
		void logTimeIn(TimeLogging timelogs) throws DataAccessException;
		int getEmployeeId();
		void logTimeOut(TimeLogging timeLogging) throws DataAccessException;
		
}

