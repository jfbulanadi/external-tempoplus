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
	int countUser() throws DataAccessException;
	int getUserID(int i) throws DataAccessException;
	String getShiftDesc(int id) throws DataAccessException;
	String getShiftInReal(String desc) throws DataAccessException;
	String getShiftOut(int id) throws DataAccessException;
	int checkTime(String d, int uid) throws DataAccessException;
	void insertRec(String d, int uid) throws DataAccessException;
	void updateFlag(String d, int uid, int fid) throws DataAccessException;
	int getFlagId(String desc) throws DataAccessException;
	String checkTimeIn(String d, int uid) throws DataAccessException;
	String getTimeIn(String d, int uid) throws DataAccessException;
	String checkTimeOut(String d, int uid) throws DataAccessException;
	String getTimeOut(String d, int uid) throws DataAccessException;
	
	List retrieveSubordinates(int id) throws DataAccessException;
	List<TimeLogging> retrieveTimelog(int id, String from, String to) throws DataAccessException;
	List<TimeLogging> retrieveMylog(int id, String from, String to) throws DataAccessException;
	Map retrieveSubordinatesMap(int id) throws DataAccessException;
	
	/*int getLevelId(int id) throws DataAccessException;*/
	boolean isSupervisor(int id) throws DataAccessException;
	/*String getPosition(int id) throws DataAccessException;*/
	String isHR(int id) throws DataAccessException;
	
	//Hr view
		List<Employee> searchEmployees(String name) throws DataAccessException;
		
	//Timein TimeOut
		void insertTimeIn(int id, TimeLogging time) throws DataAccessException;
		void validateout(int id, TimeLogging time) throws DataAccessException;
		int validatetimeIn(int id) throws DataAccessException;	
}
