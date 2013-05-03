package hk.com.novare.tempoplus.timelogging;


import hk.com.novare.tempoplus.bmnmanager.biometric.Biometric;
import hk.com.novare.tempoplus.employee.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class TimeLoggingDao implements TimelogDAOInt {

	final Logger logger = Logger.getLogger(TimeLoggingDao.class);
	int validateSearchEmployee = 0;


	@Inject
	DataSource dataSource;
	
	@Override
	public int countUser() {
		int countUser;
		countUser=0;
		Connection connection = null;
		
		
		try
		{
			try {
				connection = dataSource.getConnection();
				final PreparedStatement ps = connection
						.prepareStatement("SELECT count(id) FROM employees");
				final ResultSet resultSet = ps.executeQuery();
				while (resultSet.next()) {
					countUser = resultSet.getInt(1);
				}
				resultSet.close();
			} catch (SQLException e) {
				throw new DataAccessException("Cannot count employee",e);
			}  finally
			{
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						throw new DataAccessException("Cannot close",e);
					}
				}
			}
			
		}
		catch(DataAccessException daException)
		{
			logger.info(daException.getMessage());
		} 
		return countUser;
	}

	@Override
	public int getUserID(int i) {
		int id;
		id=0;
		Connection connection = null;
		final List getID = new ArrayList();
		getID.clear();
		
		try
		{
			try {
				connection = dataSource.getConnection();
				final PreparedStatement ps = connection
						.prepareStatement("SELECT employeeId FROM employees");
				final ResultSet resultSet = ps.executeQuery();
				while (resultSet.next()) {
				getID.add(resultSet.getInt(1));	
				}
				resultSet.close();
			
				id = (Integer) getID.get(i);
			} catch (SQLException e) {
				throw new DataAccessException("Cannot retrieve employee id",e);
			} finally
			{
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						throw new DataAccessException("Cannot close", e);
					}
				}
			}
		}
		catch(DataAccessException daException)
		{
			logger.info(daException.getMessage());
		}
		return id;
	}

	@Override
	public String getShiftDesc(int id) {
		String desc;
		desc ="";
		Connection connection = null;
		
		
		try
		{
			try {
				connection = dataSource.getConnection();
				final PreparedStatement ps = connection
						.prepareStatement("SELECT shifts.description FROM shifts inner join employees on employees.shiftId = shifts.id where employees.employeeId = ?");
				ps.setInt(1, id);
				final ResultSet resultSet = ps.executeQuery();
				while (resultSet.next()) {
				desc = resultSet.getString(1);
				}
				resultSet.close();
			} catch (SQLException e) {
				throw new DataAccessException("Cannot retrieve shift description", e);
			} finally
			{
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						throw new DataAccessException("Cannot close", e);
					}
				}
			}
		}
		catch(DataAccessException daException)
		{
			logger.info(daException.getMessage());
		}
		
		
		return desc;
	}
	
	@Override
	public String getShiftInReal(String desc) {
	String shiftIn;
	shiftIn = "";
	Connection connection = null;
	
	try
	{
		try {
			connection = dataSource.getConnection();
			final PreparedStatement ps = connection
					.prepareStatement("SELECT timeIn FROM shifts where description = ?");
			ps.setString(1, desc);
			final ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				shiftIn = resultSet.getString(1);
			}
			resultSet.close();
		} catch (SQLException e) {
			throw new DataAccessException("Cannot retrieve shift in", e);
		} finally
		{
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new DataAccessException("Cannot close", e);
				}
			}
		}
	}
	catch(DataAccessException daException)
	{
		logger.info(daException.getMessage());
	}
	
		return shiftIn;
	}

	@Override
	public String getShiftOut(int id){
		String shiftOut;
		shiftOut = "";
		Connection connection = null;
		
		try
		{
			try {
				connection = dataSource.getConnection();
				final PreparedStatement ps = connection
						.prepareStatement("SELECT shifts.timeOut FROM shifts inner join employees on shifts.id = employees.shiftId where employees.employeeId = ?");
				ps.setInt(1, id);
				final ResultSet resultSet = ps.executeQuery();
				while (resultSet.next()) {
					shiftOut = resultSet.getString(1);
				}
				resultSet.close();
			} catch (SQLException e) {
				throw new DataAccessException("Cannot retrieve shift out", e);
			} finally
			{
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						throw new DataAccessException("Cannot close", e);
					}
				}
			}
		}
		catch(DataAccessException daException)
		{
			logger.info(daException.getMessage());
		}
		
			return shiftOut;
	}
	
	
	@Override
	public int checkTime(String d, int userid) {
		int checkTime;
		checkTime=0;
		Connection connection = null;
		
		try
		{
			try {
				connection = dataSource.getConnection();
				final PreparedStatement ps = connection
						.prepareStatement("SELECT count(employeeId) FROM timelogs WHERE date = ? and employeeId = ?");
				ps.setString(1, d);
				ps.setInt(2, userid);
				final ResultSet resultSet = ps.executeQuery();
				while (resultSet.next()) {
					checkTime = resultSet.getInt(1);
				}
				resultSet.close();
		
			} catch (SQLException e) {
				throw new DataAccessException("Cannot checktime", e);
			} finally
			{
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						throw new DataAccessException("Cannot close", e);
					}
				}
			}
		}
		catch(DataAccessException daException)
		{
			logger.info(daException.getMessage());
		}
		
	
		return checkTime;
	}
	
	@Override
	public void insertRec(String d, int userid){
		Connection connection;
		connection = null;
		
		try
		{
			try {
				connection = dataSource.getConnection();

				final PreparedStatement ps = connection
						.prepareStatement("INSERT into timelogs(employeeId,date,duration,flag) VALUES(?,?,?,?)");
				ps.setInt(1, userid);
				ps.setString(2, d);
				ps.setString(3, "00:00:00");
				ps.setInt(4, getFlagId("No Log"));
				ps.executeUpdate();
			} catch (SQLException e) {
				throw new DataAccessException("Cannot insert records with flags", e);
			} finally
			{
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						throw new DataAccessException("Cannot close", e);
					}
				}
			}
		}
		catch(DataAccessException daException)
		{
			logger.info(daException.getMessage());
		}
	
		
	}

	@Override
	public void updateFlag(String date, int userId, int flagId){
		Connection connection = null;
		
		try
		{
			try {
				connection = dataSource.getConnection();
				final PreparedStatement ps = connection
						.prepareStatement("UPDATE timelogs set flag = ? where employeeId = ? and date = ? ");
				ps.setInt(1, flagId);
				ps.setInt(2,userId);
				ps.setString(3,date);
				ps.executeUpdate();
			} catch (SQLException e) {
				throw new DataAccessException("Cannot update flag", e);
			} finally
			{
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						throw new DataAccessException("Cannot close", e);
					}
				}
			}
		}
		catch(DataAccessException daException)
		{
			logger.info(daException.getMessage());
		}
	}

	@Override
	public int getFlagId(String desc){
		int flagID;
		flagID = 0;
		Connection connection = null;
		
		try
		{
			try {
				connection = dataSource.getConnection();
				final PreparedStatement ps = connection
						.prepareStatement("SELECT id FROM flags WHERE description = ?");
				ps.setString(1, desc);
				final ResultSet resultSet = ps.executeQuery();
				while (resultSet.next()) {
					flagID = resultSet.getInt(1);
				}
				resultSet.close();
		
			} catch (SQLException e) {
				throw new DataAccessException("Cannot retrieve flag id", e);
			} finally
			{
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						throw new DataAccessException("Cannot close", e);
					}
				}
			}
		}
		catch(DataAccessException daException)
		{
			logger.info(daException.getMessage());
		}
		
		
		return flagID;
	}

	@Override
	public String checkTimeIn(String date, int userId){
		String timeIn;
		timeIn = "";
		Connection connection = null;
		
		try
		{
			try {
				connection = dataSource.getConnection();
				final PreparedStatement ps = connection
						.prepareStatement("SELECT timeIn FROM timelogs WHERE date = ? and employeeId = ?");
				ps.setString(1, date);
				ps.setInt(2, userId);
				final ResultSet resultSet = ps.executeQuery();
				while (resultSet.next()) {
					timeIn = resultSet.getString(1);
				}
					
				resultSet.close();
		
			} catch (SQLException e) {
				throw new DataAccessException("Cannot check time in", e);
			} finally
			{
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						throw new DataAccessException("Cannot close", e);
					}
				}
			}
		}
		catch(DataAccessException daException)
		{
			logger.info(daException.getMessage());
		}
		return timeIn;
	}

	@Override
	public String getTimeIn(String date, int userId){
		String timeIn;
		timeIn = "";
		Connection connection = null;
		
		try
		{
			try {
				connection = dataSource.getConnection();
				final PreparedStatement ps = connection
						.prepareStatement("SELECT timeIn FROM timelogs WHERE date = ? and employeeId = ?");
				ps.setString(1, date);
				ps.setInt(2, userId);
				final ResultSet resultSet = ps.executeQuery();
				while (resultSet.next()) {
					timeIn = resultSet.getString(1);
				}
				resultSet.close();
		
			} catch (SQLException e) {
				throw new DataAccessException("Cannot retrieve time in", e);
			} finally
			{
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						throw new DataAccessException("Cannot close", e);
					}
				}
			}
		}
		catch(DataAccessException daException)
		{
			logger.info(daException.getMessage());
		}
		return timeIn;
	}

	@Override
	public String checkTimeOut(String date, int userId){
		String timeOut;
		timeOut = "";
		Connection connection = null;
		
		try
		{
			try {
				connection = dataSource.getConnection();
				final PreparedStatement ps = connection
						.prepareStatement("SELECT timeOut FROM timelogs WHERE date = ? and employeeId = ?");
				ps.setString(1, date);
				ps.setInt(2, userId);
				final ResultSet resultSet = ps.executeQuery();
				while (resultSet.next()) {
					timeOut = resultSet.getString(1);
					}
								
					resultSet.close();
					
					} catch (SQLException e) {
						throw new DataAccessException("Cannot check time out", e);
					} finally
					{
						if (connection != null) {
							try {
								connection.close();
							} catch (SQLException e) {
								throw new DataAccessException("Cannot close", e);
							}
						}
					}
		}
		catch(DataAccessException daException)
		{
			logger.info(daException.getMessage());
		}
					return timeOut;
	}
	@Override
	public String getTimeOut(String date, int userId){
		String timeOut;
		timeOut = "";
		Connection connection = null;
		
		
		try
		{

			try {
				connection = dataSource.getConnection();
				final PreparedStatement ps = connection
						.prepareStatement("SELECT timeOut FROM timelogs WHERE date = ? and employeeId = ?");
				ps.setString(1, date);
				ps.setInt(2, userId);
				final ResultSet resultSet = ps.executeQuery();
				while (resultSet.next()) {
					timeOut = resultSet.getString(1);
				}
				resultSet.close();
		
			} catch (SQLException e) {
				throw new DataAccessException("Cannot retrieve time out", e);
			} finally
			{
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						throw new DataAccessException("Cannot close", e);
					}
				}
			}
		}
		catch(DataAccessException daException)
		{
			logger.info(daException.getMessage());
		}
		
		return timeOut;
	}

	@Override
	public List retrieveSubordinates(int id){
		Connection connection;
		connection = null;
		final List sub = new ArrayList();
		
		try
		{
			try {
				connection = dataSource.getConnection();
				final PreparedStatement ps = connection
						.prepareStatement("SELECT employeeId,firstname,middlename,lastname FROM employees where supervisorId = ?");
				ps.setInt(1, id);
				final ResultSet resultSet = ps.executeQuery();
				sub.clear();
				while(resultSet.next())
				{
					sub.add(resultSet.getString(2) + " " + resultSet.getString(3) + " " + resultSet.getString(4));
				}
				
			} catch (SQLException e) {
				throw new DataAccessException("Cannot retrieve subordinates",e);
			}finally {
				// Always close the connection. Error or not.
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						throw new DataAccessException("Cannot close", e);
					}
				}
			}
		}
		catch(DataAccessException daException)
		{
			logger.info(daException.getMessage());
		}
		
		return sub;

	}
	@Override
	public List<TimeLogging> retrieveTimelog(int id, String from, String to){
		final ArrayList<TimeLogging> tm = new ArrayList<TimeLogging>();
		Connection connection = null;
		
		try
		{
			try {
				connection = dataSource.getConnection();
				final PreparedStatement ps = connection
						.prepareStatement("SELECT date,timeIn,timeOut,duration FROM timelogs WHERE employeeId = ? AND date between ? AND ?");
				ps.setInt(1, id);
				ps.setString(2, from);
				ps.setString(3, to);
				final ResultSet resultSet = ps.executeQuery();
				
				tm.clear();
				while (resultSet.next()) {
					final String dd = resultSet.getString(1);
					final String timeIn = resultSet.getString(2);
					final String timeOut = resultSet.getString(3);
					final String tHours = resultSet.getString(4);
					// Store in an object
					final TimeLogging tmpojo = new TimeLogging();
					tmpojo.setDate(dd);
					tmpojo.setTimeIn(timeIn);
					tmpojo.setTimeOut(timeOut);
					tmpojo.setDuration(tHours);
					
					tm.add(tmpojo);
				}
				resultSet.close();
				
				
			} catch (SQLException e) {
				// Throw a nested exception
				// Encapsulation of exceptions
				throw new DataAccessException("Cannot retrieve time log", e);
			} finally {
				// Always close the connection. Error or not.
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						throw new DataAccessException("Cannot close", e);
					}
				}
			}
			
		}
		catch(DataAccessException daException)
		{
			logger.info(daException.getMessage());
		}
		return tm;
	}

	@Override
	public List<TimeLogging> retrieveMylog(int id, String from, String to){
		
		final ArrayList<TimeLogging> tm = new ArrayList<TimeLogging>();
		Connection connection = null;
		try
		{
			try {
				connection = dataSource.getConnection();
				final PreparedStatement ps = connection
						.prepareStatement("SELECT date,timeIn,timeOut,duration FROM timelogs WHERE employeeId = ? AND date between ? AND ?");
				ps.setInt(1, id);
				ps.setString(2, from);
				ps.setString(3, to);
				final ResultSet resultSet = ps.executeQuery();
				
				tm.clear();
				while (resultSet.next()) {
					final String dd = resultSet.getString(1);
					final String timeIn = resultSet.getString(2);
					final String timeOut = resultSet.getString(3);
					final String tHours = resultSet.getString(4);
					
					// Store in an object
					final TimeLogging tmpojo = new TimeLogging();
					tmpojo.setDate(dd);
					tmpojo.setTimeIn(timeIn);
					tmpojo.setTimeOut(timeOut);
					tmpojo.setDuration(tHours);
					
					
					tm.add(tmpojo);
					
				}
				resultSet.close();
				
				
			} catch (SQLException e) {
				// Throw a nested exception
				// Encapsulation of exceptions
				throw new DataAccessException("Cannot retrieve user log", e);
			} finally {
				// Always close the connection. Error or not.
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						throw new DataAccessException("Cannot close", e);
					}
				}
			}
			
		}
		catch(DataAccessException daException)
		{
			logger.info(daException.getMessage());
		}
		return tm;
	}

	@Override
	public Map retrieveSubordinatesMap(int id){

		Connection connection = null;
		final Map subMap = new HashMap();
		
		
		try
		{
			try {
				connection = dataSource.getConnection();
				final PreparedStatement ps = connection
						.prepareStatement("SELECT employeeId,firstname,middlename,lastname FROM employees where supervisorId = ?");
				ps.setInt(1, id);
				final ResultSet resultSet = ps.executeQuery();
				subMap.clear();
				while(resultSet.next())
				{
					subMap.put(resultSet.getInt(1),resultSet.getString(2) + " " + resultSet.getString(3) + " " + resultSet.getString(4));
				}
				
			} catch (SQLException e) {
				throw new DataAccessException("Cannot retrieve subordinates", e);
			}finally {
				// Always close the connection. Error or not.
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						throw new DataAccessException("Cannot close", e);
					}
				}
			}
		}
		catch(DataAccessException daException)
		{
			logger.info(daException.getMessage());
		}
		return subMap;


	}


	//Hr Search
		@Override
		public List<Employee> searchEmployees(String name){
			Connection con = null;
			int count = 0;
					
			final List<Employee> employee = new ArrayList<Employee>();	
			employee.clear();
			
			try
			{
				try {
					con = dataSource.getConnection();
					final PreparedStatement preparedStatement = con.prepareStatement("SELECT employeeId,firstname,middlename,lastname FROM employees WHERE lastname LIKE '%' ? '%' OR firstname LIKE '%' ? '%'");
					preparedStatement.setString(1, name);
					preparedStatement.setString(2, name);
					
					final ResultSet resultSet = preparedStatement.executeQuery();
					
					
					while(resultSet.next()){
						count = 1;						
						final Employee details = new Employee();
						
						details.setEmployeeId(resultSet.getInt(1));
						details.setFirstname(resultSet.getString(2));
						details.setLastname(resultSet.getString(4));
						details.setMiddlename(resultSet.getString(3));
						
						
						employee.add(details);
				
					}
					validateSearchEmployee = count;
					resultSet.close();
					
				} catch (SQLException e) {
				
				throw new DataAccessException("Cannot search employees", e);
				}finally {
				// Always close the connection. Error or not.
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						throw new DataAccessException("Cannot close", e);
					}
				}
				}
			}
			catch(DataAccessException daException)
			{
				logger.info(daException.getMessage());
			}
			return employee;
		}

		
		@Override
		public boolean isSupervisor(int id) {
			boolean userType=false;
			Connection connection = null;
			
			
			
			try
			{
				try {
					connection = dataSource.getConnection();
					final PreparedStatement ps = connection
							.prepareStatement("SELECT isSupervisor FROM employees where employeeId = ?");
					ps.setInt(1, id);
					final ResultSet resultSet = ps.executeQuery();
					while(resultSet.next())
					{
						userType = resultSet.getBoolean(1);
					}
					
				} catch (SQLException e) {
					throw new DataAccessException("Cannot identify if supervisor", e);
				}finally {
					// Always close the connection. Error or not.
					if (connection != null) {
						try {
							connection.close();
						} catch (SQLException e) {
							throw new DataAccessException("Cannot close", e);
						}
					}
				}
			}
			catch(DataAccessException daException)
			{
				logger.info(daException.getMessage());
			}
			
			
			return userType;
		}
		
		@Override
		public String isHR(int id){
			String position = "";
			Connection connection = null;
			
			try
			{
				try {
					connection = dataSource.getConnection();
					final PreparedStatement ps = connection
					.prepareStatement("SELECT departments.name FROM departments inner join employees on departments.id = employees.departmentId where employees.employeeId = ?");
					ps.setInt(1, id);
					final ResultSet resultSet = ps.executeQuery();
					while(resultSet.next())
					{
						position = resultSet.getString(1);
					}
					
				} catch (SQLException e) {
					throw new DataAccessException("Cannot identify if Hr", e);
				}finally {
					// Always close the connection. Error or not.
					if (connection != null) {
						try {
							connection.close();
						} catch (SQLException e) {
							throw new DataAccessException("Cannot close", e);
						}
					}
				}
			}
			catch(DataAccessException daException)
			{
				logger.info(daException.getMessage());
			}
			
			
			return position;
		}
		
		@Override
		public int checkData(int id, String from, String to)
		{
		int cData;
		cData=0;
		Connection connection = null;
		
		try
		{
			try {
				connection = dataSource.getConnection();
				final PreparedStatement ps = connection
						.prepareStatement("SELECT count(*) FROM timelogs WHERE employeeId = ? AND date between ? AND ?");
				ps.setInt(1, id);
				ps.setString(2, from);
				ps.setString(3, to);
				final ResultSet resultSet = ps.executeQuery();
				while (resultSet.next()) {
					cData = resultSet.getInt(1);
				}
				resultSet.close();
			} catch (SQLException e) {
				throw new DataAccessException("Cannot check data", e);
			} finally
			{
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						throw new DataAccessException("Cannot close", e);
					}
				}
			}
		}
		catch(DataAccessException daException)
		{
			logger.info(daException.getMessage());
		}
		
		
		return cData;
		}
		
		@Override
		public void insertTimeIn(String datestring, String timestring, int id, TimeLogging time){

			Connection connection = null;
			
			try
			{
				try {
					connection = dataSource.getConnection();

					final PreparedStatement pstate = connection
							.prepareStatement("INSERT INTO timelogs (employeeId, date, timeIn, duration) values (?, ?, ?, ?)");

					// Save in db
					pstate.setInt(1, id);
					pstate.setString(2, datestring);
					pstate.setString(3, timestring);
					pstate.setString(4, "00:00:00");
					pstate.executeUpdate();
					
					
				} catch (SQLException e) {
					// Throw a nested exception
					// Encapsulation of exceptions
					throw new DataAccessException("Cannot insert time in",e);

				} finally {
					// Always close the connection. Error or not.
					if (connection != null) {
						try {
							connection.close();
						} catch (SQLException e) {
							throw new DataAccessException("Cannot close", e);
						}
					}
				}
			}
			catch(DataAccessException daException)
			{
				logger.info(daException.getMessage());
			}
			
			
		
			
		}

		public String getTimeIn(String dateString, String timeString, int id){
			Connection connection = null;
				
			String timeIn = null;
			
			try
			{
				try {
					connection = dataSource.getConnection();
					
					final PreparedStatement ps = connection.prepareStatement("SELECT timeIn FROM timelogs WHERE employeeId = ? and date=? ");
					ps.setInt(1, id);
					ps.setString(2, dateString);
					
					final ResultSet resultSet = ps.executeQuery();
					
					while(resultSet.next()){
					
						timeIn = resultSet.getString(1);
					
					}
					resultSet.close();
					
				}catch (SQLException e) {
					
					throw new DataAccessException("Cannot retrieve time in",e);
				}finally{
					
					if (connection != null) {
						try {
							connection.close();
						} catch (SQLException e) {
							throw new DataAccessException("Cannot close", e);
						}
					}
					
				}
			}
			
			catch(DataAccessException daException)
			{
				logger.info(daException.getMessage());
			}
			return timeIn;
		}

		
		@Override

		public void validateout(String totalHours, String datestring, String timestring,int id, TimeLogging time){


			Connection connection = null;

			String finalDate = datestring;
			
			String timeIn = null;
			String duration = "00:00:00";
			
			int count = 0;
			
			try
			{
				try {
					connection = dataSource.getConnection();
					
					final PreparedStatement ps = connection.prepareStatement("SELECT timeIn FROM timelogs WHERE employeeId = ? and date=? ");
					ps.setInt(1, id);
					ps.setString(2, datestring);
					
					final ResultSet resultSet = ps.executeQuery();
					
					while(resultSet.next()){
					
						timeIn = resultSet.getString(1);
						count = 1;
					}
					
					if (count==0){
						
						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						Calendar cal = Calendar.getInstance();
						cal.add(Calendar.DATE, -1);
						String dateyesterday = dateFormat.format(cal.getTime());
						
						finalDate = dateyesterday;
						
					}
		
					final PreparedStatement pstate = connection
							.prepareStatement("UPDATE timelogs set timeOut = ?, duration = ? where employeeId = ? and date = ? ");
					pstate.setString(1, timestring);
								
					duration = totalHours;
					pstate.setString(2, duration);
					pstate.setInt(3, id);
					pstate.setString(4, finalDate);
					pstate.executeUpdate();

					
					resultSet.close();
					
				} catch (SQLException e) {
					throw new DataAccessException("Cannot validate out",e);
					
				}finally{
					
					if (connection != null) {
						try {
							connection.close();
						} catch (SQLException e) {
							throw new DataAccessException("Cannot close", e);
						}
					}
					
				}
			}
			catch(DataAccessException daException)
			{
				logger.info(daException.getMessage());
			}
			
			
		}

		@Override
		public int validatetimeIn(String datestring,int id){
			Connection connection = null;
			int count = 0;
			
			try
			{
				try {
					connection = dataSource.getConnection();
					final PreparedStatement ps = connection
							.prepareStatement("SELECT employeeId, date, timeIn, timeOut FROM timelogs WHERE employeeId = ? and date = ?" );
					
						ps.setInt(1, id);
						ps.setString(2, datestring);
					final ResultSet resultSet = ps.executeQuery();

						while (resultSet.next()) {
							 count = resultSet.getInt(1);
							 				 
						}
						
					
					resultSet.close();
					
					
				} catch (SQLException e) {
					// Throw a nested exception
					// Encapsulation of exceptions
					throw new DataAccessException("Cannot validate time in", e);
				} finally {
					// Always close the connection. Error or not.
					if (connection != null) {
						try {
							connection.close();
						} catch (SQLException e) {
							throw new DataAccessException("Cannot close", e);
						}
					}
				}
			}
			catch(DataAccessException daException)
			{
				logger.info(daException.getMessage());
			}
			return count;
		}
		
		@Override
		public int getValidationOfEmployeeSearch() {
			return validateSearchEmployee;

		}
		
		
		/*
		 * Jeffrey's Methods
		 */
		
		public void updateTimeLoggingDataPhase1(ArrayList<Biometric> list) {
			Connection connection = null;
			try {
				connection = dataSource.getConnection();

				PreparedStatement preparedStatement = connection
						.prepareStatement("INSERT INTO timelogs (employeeId, date, timeIn)"
								+ " values ((SELECT employeeId FROM employees WHERE biometricId = ?), ?, ?)");

				for (Biometric biometric : list) {
					preparedStatement.setInt(1, biometric.getBiometricId());
					preparedStatement.setString(2, biometric.getDate());
					preparedStatement.setString(3, biometric.getTime());
					preparedStatement.addBatch();
				}

				preparedStatement.executeBatch();
				preparedStatement.close();

			} catch (SQLException e) {

			} finally {
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {

					}
				}
			}

		}

		// Not yet DONE
		public void updateTimeLoggingDataPhase2(ArrayList<Biometric> list) {
			Connection connection = null;
			try {
				connection = dataSource.getConnection();

				PreparedStatement preparedStatement = connection
						.prepareStatement("UPDATE timelogs SET timeOut=?, duration = (SELECT TIMEDIFF(timeOut,timeIn)) WHERE employeeId = "
								+ "(SELECT employeeId FROM employees WHERE biometricId = ?) AND date=?");

				for (Biometric biometric : list) {
					preparedStatement.setString(1, biometric.getTime());
					// preparedStatement.setString(2, biometric.getDate());
					preparedStatement.setInt(2, biometric.getBiometricId());
					preparedStatement.setString(3, biometric.getDate());
					preparedStatement.addBatch();
				}

				preparedStatement.executeBatch();
				preparedStatement.close();

			} catch (SQLException e) {

			} finally {
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {

					}
				}
			}

		}

		public ArrayList<TimeLogging> retrieveTimeLogs() {
			ArrayList<TimeLogging> list = new ArrayList<TimeLogging>();
			Connection connection = null;
			try {
				System.out.println("Here");
				connection = dataSource.getConnection();

				PreparedStatement preparedStatement = connection
						.prepareStatement("SELECT id, employeeId, date, timeIn, timeOut, duration FROM timelogs");

				ResultSet resultSet = preparedStatement.executeQuery();

				int ctr = 0;
				while (resultSet.next()) {
					System.out.println(++ctr);
					TimeLogging timelogging = new TimeLogging();

					timelogging.setId(resultSet.getInt("id"));
					timelogging.setEmployeeId(resultSet.getInt("employeeId"));
					timelogging.setDate(resultSet.getString("date"));

					list.add(timelogging);
				}
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {

					}
				}
			}

			return list;

		}
		

	
}
