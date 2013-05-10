package hk.com.novare.tempoplus.timelogging;

import hk.com.novare.tempoplus.employee.Employee;

import java.text.ParseException;
import java.util.List;
import java.util.Map;



public interface TimelogDAOInt {
	
	/**
	 * Timelog DAO interface
	 * @return
	 * @throws DataAccessException
	 */
	int countUser() ;
	int getUserID(int i);
	String getShiftDesc(int id);
	String getShiftInReal(String desc);
	String getShiftOut(int id);
	int checkTime(String d, int userId);
	void insertRec(String d, int userId);
	void updateFlag(String d, int userId, int flagId);
	int getFlagId(String desc);
	String checkTimeIn(String d, int userId);
	String getTimeIn(String d, int userId);
	String checkTimeOut(String d, int userId);
	String getTimeOut(String d, int userId);
	
	List retrieveSubordinates(int id);
	List<TimeLogging> retrieveTimelog(int id, String from, String to);
	List<TimeLogging> retrieveMylog(int id, String from, String to);
	Map retrieveSubordinatesMap(int id);
	
	int checkData(int id, String from, String to);
	
	/*int getLevelId(int id) throws DataAccessException;*/
	boolean isSupervisor(int id);
	/*String getPosition(int id) throws DataAccessException;*/
	String isHR(int id);
	
	//Hr view
		List<Employee> searchEmployees(String name);
		int getValidationOfEmployeeSearch();
		
	//Timein TimeOut
		void insertTimeIn(String datestring, String timestring, int id, TimeLogging time);
		void validateout(String totalHours, String datestring, String timestring,int id, TimeLogging time);
		int validatetimeIn(String datestring,int id);	
		String getTimeIn(String dateString, String timeString, int id);

}
