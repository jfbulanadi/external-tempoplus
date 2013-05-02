package hk.com.novare.tempoplus.timelogging;


import hk.com.novare.tempoplus.employee.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.sql.DataSource;

public class TimeLoggingDao implements TimelogDAOInt {


	int validateSearchEmployee = 0;


	@Inject
	DataSource dataSource;
	
	@Override
	public int countUser() throws DataAccessException {
		int countUser;
		countUser=0;
		Connection connection = null;
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
			throw new DataAccessException("cannot connect",e);

		}  finally
		{
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new DataAccessException("cannot close", e);
				}
			}
		}
		
		return countUser;
	}

	@Override
	public int getUserID(int i) throws DataAccessException {
		int id;
		id=0;
		Connection connection = null;
		final List getID = new ArrayList();
		getID.clear();
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
			throw new DataAccessException("cannot connect", e);
		} finally
		{
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new DataAccessException("cannot close", e);
				}
			}
		}
		return id;
	}

	@Override
	public String getShiftDesc(int id) throws DataAccessException {
		String desc;
		desc ="";
		Connection connection = null;
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
			throw new DataAccessException("cannot connect", e);
		} finally
		{
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new DataAccessException("cannot close", e);
				}
			}
		}
		return desc;
	}
	
	@Override
	public String getShiftInReal(String desc) throws DataAccessException {
	String shiftIn;
	shiftIn = "";
	Connection connection = null;
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
		throw new DataAccessException("cannot connect", e);
	} finally
	{
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new DataAccessException("cannot close", e);
			}
		}
	}
		return shiftIn;
	}

	@Override
	public String getShiftOut(int id) throws DataAccessException {
		String shiftOut;
		shiftOut = "";
		Connection connection = null;
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
			throw new DataAccessException("cannot connect", e);
		} finally
		{
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new DataAccessException("cannot close", e);
				}
			}
		}
			return shiftOut;
	}
	
	
	@Override
	public int checkTime(String d, int userid) throws DataAccessException {
		int checkTime;
		checkTime=0;
		Connection connection = null;
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
			throw new DataAccessException("cannot connect", e);
		} finally
		{
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new DataAccessException("cannot close", e);
				}
			}
		}
		return checkTime;
	}
	
	@Override
	public void insertRec(String d, int userid) throws DataAccessException {
		Connection connection;
		connection = null;
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
			throw new DataAccessException("cannot connect", e);
		} finally
		{
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new DataAccessException("cannot close", e);
				}
			}
		}
		
	}

	@Override
	public void updateFlag(String date, int userId, int flagId)
			throws DataAccessException {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			final PreparedStatement ps = connection
					.prepareStatement("UPDATE timelogs set flag = ? where employeeId = ? and date = ? ");
			ps.setInt(1, flagId);
			ps.setInt(2,userId);
			ps.setString(3,date);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException("cannot connect", e);
		} finally
		{
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new DataAccessException("cannot close", e);
				}
			}
		}
		
	}

	@Override
	public int getFlagId(String desc) throws DataAccessException {
		int flagID;
		flagID = 0;
		Connection connection = null;
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
			throw new DataAccessException("cannot connect", e);
		} finally
		{
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new DataAccessException("cannot close", e);
				}
			}
		}
		return flagID;
	}

	@Override
	public String checkTimeIn(String date, int userId) throws DataAccessException {
		String timeIn;
		timeIn = "";
		Connection connection = null;
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
			throw new DataAccessException("cannot connect", e);
		} finally
		{
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new DataAccessException("cannot close", e);
				}
			}
		}
		return timeIn;
	}

	@Override
	public String getTimeIn(String date, int userId) throws DataAccessException {
		String timeIn;
		timeIn = "";
		Connection connection = null;
		
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
			throw new DataAccessException("cannot connect", e);
		} finally
		{
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new DataAccessException("cannot close", e);
				}
			}
		}
		return timeIn;
	}

	@Override
	public String checkTimeOut(String date, int userId) throws DataAccessException {
		String timeOut;
		timeOut = "";
		Connection connection = null;
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
						throw new DataAccessException("cannot connect", e);
					} finally
					{
						if (connection != null) {
							try {
								connection.close();
							} catch (SQLException e) {
								throw new DataAccessException("cannot close", e);
							}
						}
					}
					return timeOut;
	}
	@Override
	public String getTimeOut(String date, int userId) throws DataAccessException {
		String timeOut;
		timeOut = "";
		Connection connection = null;
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
			throw new DataAccessException("cannot connect", e);
		} finally
		{
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new DataAccessException("cannot close", e);
				}
			}
		}
		return timeOut;
	}

	@Override
	public List retrieveSubordinates(int id) throws DataAccessException {
		Connection connection;
		connection = null;
		final List sub = new ArrayList();
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
			throw new DataAccessException("cannot connect",e);
		}finally {
			// Always close the connection. Error or not.
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new DataAccessException("cannot close", e);
				}
			}
		}
		return sub;

	}
	@Override
	public List<TimeLogging> retrieveTimelog(int id, String from, String to)
			throws DataAccessException {

		Connection connection = null;
		
		try {
			connection = dataSource.getConnection();
			final PreparedStatement ps = connection
					.prepareStatement("SELECT date,timeIn,timeOut,duration FROM timelogs WHERE employeeId = ? AND date between ? AND ?");
			ps.setInt(1, id);
			ps.setString(2, from);
			ps.setString(3, to);
			final ResultSet resultSet = ps.executeQuery();
			final ArrayList<TimeLogging> tm = new ArrayList<TimeLogging>();
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
			
			return tm;
		} catch (SQLException e) {
			// Throw a nested exception
			// Encapsulation of exceptions
			throw new DataAccessException("cannot connect", e);
		} finally {
			// Always close the connection. Error or not.
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new DataAccessException("cannot close", e);
				}
			}
		}
	}

	@Override
	public List<TimeLogging> retrieveMylog(int id, String from, String to) throws DataAccessException {
		
		
		Connection connection = null;
		
		try {
			connection = dataSource.getConnection();
			final PreparedStatement ps = connection
					.prepareStatement("SELECT date,timeIn,timeOut,duration FROM timelogs WHERE employeeId = ? AND date between ? AND ?");
			ps.setInt(1, id);
			ps.setString(2, from);
			ps.setString(3, to);
			final ResultSet resultSet = ps.executeQuery();
			final ArrayList<TimeLogging> tm = new ArrayList<TimeLogging>();
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
			
			return tm;
		} catch (SQLException e) {
			// Throw a nested exception
			// Encapsulation of exceptions
			throw new DataAccessException("cannot connect", e);
		} finally {
			// Always close the connection. Error or not.
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new DataAccessException("cannot close", e);
				}
			}
		}
	}

	@Override
	public Map retrieveSubordinatesMap(int id) throws DataAccessException {

		Connection connection = null;
		final Map subMap = new HashMap();
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
			throw new DataAccessException("cannot connect", e);
		}finally {
			// Always close the connection. Error or not.
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new DataAccessException("cannot close", e);
				}
			}
		}
		return subMap;


	}


	//Hr Search
		@Override
		public List<Employee> searchEmployees(String name)
				throws DataAccessException {
			Connection con = null;
			int count = 0;
					
			final List<Employee> employee = new ArrayList<Employee>();	
			employee.clear();
			
			
			
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
					return employee;
			} catch (SQLException e) {
				
				throw new DataAccessException("cannot connect", e);
			}finally {
				// Always close the connection. Error or not.
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						throw new DataAccessException("cannot close", e);
					}
				}
			}
		
		}

		
		@Override
		public boolean isSupervisor(int id) throws DataAccessException {
			boolean userType=false;
			Connection connection = null;
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
				throw new DataAccessException("cannot connect", e);
			}finally {
				// Always close the connection. Error or not.
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						throw new DataAccessException("cannot close", e);
					}
				}
			}
			return userType;
		}
		
		@Override
		public String isHR(int id) throws DataAccessException {
			String position = "";
			Connection connection = null;
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
				throw new DataAccessException("cannot connect", e);
			}finally {
				// Always close the connection. Error or not.
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						throw new DataAccessException("cannot close", e);
					}
				}
			}
			return position;
		}
		
		@Override
		public int checkData(int id, String from, String to) throws DataAccessException
		{
		int cData;
		cData=0;
		Connection connection = null;
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
			throw new DataAccessException("cannot connect", e);
		} finally
		{
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new DataAccessException("cannot close", e);
				}
			}
		}
		return cData;
		}
		
		@Override
		public void insertTimeIn(String datestring, String timestring, int id, TimeLogging time)
				throws DataAccessException {

			Connection connection = null;
			
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
				throw new DataAccessException("cannot connect",e);

			} finally {
				// Always close the connection. Error or not.
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						throw new DataAccessException("cannot close", e);
					}
				}
			}
		
			
		}

		public String getTimeIn(String dateString, String timeString, int id) throws DataAccessException{
			Connection connection = null;
				
			String timeIn = null;
				
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
				return timeIn;
			}catch (SQLException e) {
				
				throw new DataAccessException("cannot connect",e);
			}finally{
				
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						throw new DataAccessException("cannot close", e);
					}
				}
				
			}
		}

		
		@Override

		public void validateout(String totalHours, String datestring, String timestring,int id, TimeLogging time)
				throws DataAccessException {


			Connection connection = null;

			String finalDate = datestring;
			
			String timeIn = null;
			String duration = "00:00:00";
			
			int count = 0;
			
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
				
				throw new DataAccessException("cannot connect",e);

			}finally{
				
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						throw new DataAccessException("cannot close", e);
					}
				}
				
			}
			
		}

		@Override
		public int validatetimeIn(String datestring,int id) throws DataAccessException {
			Connection connection = null;
			int count = 0;
			
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
				return count;
				
			} catch (SQLException e) {
				// Throw a nested exception
				// Encapsulation of exceptions
				throw new DataAccessException("cannot connect", e);
			} finally {
				// Always close the connection. Error or not.
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						throw new DataAccessException("cannot close", e);
					}
				}
			}

		}
		

		@Override
		public int getValidationOfEmployeeSearch() {
			return validateSearchEmployee;

		}
		

		

	
}
