package hk.com.novare.tempoplus.bmnmanager.consolidation;

import hk.com.novare.tempoplus.bmnmanager.mantis.Mantis;
import hk.com.novare.tempoplus.bmnmanager.nt3.Nt3;
import hk.com.novare.tempoplus.bmnmanager.timesheet.Timesheet;
import hk.com.novare.tempoplus.bmnmanager.timesheet.TimesheetPartialDTO;
import hk.com.novare.tempoplus.employee.Employee;
import hk.com.novare.tempoplus.timelogging.TimeLogging;
import hk.com.novare.tempoplus.timelogging.TimeLoggingDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestParam;


public class ConsolidationDao {

	@Inject
	DataSource dataSource;
	
	final Logger logger = Logger.getLogger(ConsolidationDao.class);
	
	private Connection connection = null;

	/**
	 * Checks if timesheet is ready of Consolidation.
	 * 
	 * @return
	 */
	public boolean isReadyForConsolidation(String name) {
		
		try {
			
			connection = dataSource.getConnection();
			logger.info("isReadyForConsolidation : Created MySQL connection.");
			PreparedStatement preparedStatement = connection
					.prepareStatement("SELECT flag FROM timesheets WHERE description = ?");

			preparedStatement.setString(1, name);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			logger.info("isReadyForConsolidation : Executed MySQL query.");
			preparedStatement.close();
			
			if(resultSet.getInt("flag") == 1) {
				return true;
			}
			
		} catch (SQLException e) {

		} finally {
			if (connection != null) {
				try {
					connection.close();
					logger.info("isReadyForConsolidation : Closed MySQL connection.");
				} catch (SQLException e) {

				}
			}
		}
		
		return false;
	}

	/**
	 * Creates a Consolidation record in Consolidations Table.
	 * 
	 * @param name
	 * @param periodStart
	 * @param periodEnd
	 * @return
	 */
	public boolean createCondolidatedTimesheet(String name, String periodStart,
			String periodEnd) {

		try {
			connection = dataSource.getConnection();
			logger.info("createCondolidatedTimesheet : Created MySQL connection.");
			PreparedStatement preparedStatement = connection
					.prepareStatement("INSERT INTO consolidations (consolidationName, periodStart, periodEnd)"
							+ "VALUES (?, ?, ?)");

			preparedStatement.setString(1, name);
			preparedStatement.setString(2, periodStart);
			preparedStatement.setString(3, periodEnd);

			preparedStatement.executeUpdate();
			logger.info("createCondolidatedTimesheet : Executed MySQL update query.");
			preparedStatement.close();

		} catch (SQLException e) {
			// TODO Create Custom Exception
			return false;
		} finally {
			if (connection != null) {
				try {
					connection.close();
					logger.info("createCondolidatedTimesheet : Closed MySQL connection.");
				} catch (SQLException e) {

				}
			}
		}

		return true;
	}

	public void updateConsolidatedTimesheetById(int id,
			Consolidation consolidation) {

		try {
			connection = dataSource.getConnection();
			logger.info("updateConsolidatedTimesheetById : Created MySQL connection.");
			PreparedStatement preparedStatement = connection
					.prepareStatement("UPDATE consolidations (userId, biometricId, mantisId, nt3Id, name, "
							+ " periodStart, periodEnd) VALUES (?, ?, ?, ?, ?, ?, ?, ?) WHERE id = ?");

			preparedStatement.setInt(1, consolidation.getUserId());
			preparedStatement.setInt(2, consolidation.getBiometricId());
			preparedStatement.setInt(3, consolidation.getMantisId());
			preparedStatement.setInt(4, consolidation.getNt3Id());
			preparedStatement.setString(5, consolidation.getName());
			preparedStatement.setString(6, consolidation.getPeriodStart());
			preparedStatement.setString(7, consolidation.getPeriodEnd());
			preparedStatement.setInt(8, id);

			preparedStatement.executeUpdate();
			logger.info("updateConsolidatedTimesheetById : Executing MySQL query update.");
			preparedStatement.close();

		} catch (SQLException e) {
			// TODO Create Custom Exception
		} finally {
			if (connection != null) {
				try {
					connection.close();
					logger.info("updateConsolidatedTimesheetById : Closed MySQL connection.");
				} catch (SQLException e) {

				}
			}
		}

	}

	/*public Consolidation isReadyForConsolidation(int id) {

		Consolidation consolidation = new Consolidation();
		try {
			connection = dataSource.getConnection();

			PreparedStatement preparedStatement = connection
					.prepareStatement("SELECT userId, biometricId, mantisId, nt3Id, name, "
							+ " periodStart, periodEnd FROM consolidations WHERE id=?");

			preparedStatement.setInt(1, id);

			ResultSet resultSet = preparedStatement.executeQuery();

			consolidation.setUserId(resultSet.getInt(1));
			consolidation.setBiometricId(resultSet.getInt(2));
			consolidation.setMantisId(resultSet.getInt(3));
			consolidation.setNt3Id(resultSet.getInt(4));
			consolidation.setName(resultSet.getString(5));
			consolidation.setPeriodStart(resultSet.getString(6));
			consolidation.setPeriodEnd(resultSet.getString(7));

			preparedStatement.close();
			resultSet.close();

		} catch (SQLException e) {
			// TODO Create Custom Exception
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {

				}
			}
		}

		return consolidation;

	}
*/
	
	public void consolidateTimeSheet(ArrayList<Integer> idList) {

		try {
			connection = dataSource.getConnection();
			logger.info("consolidateTimeSheet : Created MySQL connection.");
			PreparedStatement preparedStatement = connection
					.prepareStatement("INSERT INTO consolidations (employeeId, timelogId, date) SELECT employeeId, id, date FROM timelog WHERE employeeId = ?");
			
			for (int id : idList) {
				preparedStatement.setInt(1, id);
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
			logger.info("consolidateTimeSheet : Executing Insert query.");
			preparedStatement.close();

		} catch (SQLException e) {
			// TODO Create Custom Exception

		} finally {
			if (connection != null) {
				try {
					connection.close();
					logger.info("consolidateTimeSheet : Closed MySQL connection.");
				} catch (SQLException e) {

				}
			}
		}

	}
	
	public ArrayList<ConsolidationDTO> viewConsolidation(String id) {
		ResultSet resultSet = null;
		Connection connection = null;
		PreparedStatement ps = null;
		
		
		
		final ArrayList<ConsolidationDTO> list = new ArrayList<ConsolidationDTO>();
		
		try {
				connection = dataSource.getConnection();
				logger.info("viewConsolidation: Created MySQL connection.");
				ps = connection.prepareStatement("SELECT consolidations.id, employees.employeeId, employees.biometricId, employees.firstname, employees.middlename, employees.lastname, employees.email, shifts.description AS shift, departments.name AS department, positions.description AS position, timelogs.timeIn, timelogs.timeOut, timelogs.duration AS timeDuration, timelogs.date AS timelogDate, consolidations.mantisId, mantises.ticketId, mantises.startDate AS mantisStartDate, mantises.endDate AS mantisEndDate, mantises.hours AS mantisHours, mantises.minutes AS mantisMinutes, mantises.category as mantisCategory, mantises.dateSubmitted AS mantisDateSubmitted, mantises.timeIn AS mantisTimeIn, mantises.timeOut AS mantisTimeOut, mantises.status AS mantisStatus, nt3s.id AS nt3Id, nt3s.startDate AS nt3StartDate, nt3s.endDate AS nt3EndDate, nt3s.duration AS nt3Duration, nt3s.absenceType AS nt3AbsenceType, nt3s.description AS nt3Description, nt3s.absenceStatus AS nt3AbsenceStatus, consolidations.timesheetId, timesheets.description AS timesheetName FROM consolidations INNER JOIN employees ON consolidations.employeeId = employees.employeeId INNER JOIN shifts ON employees.shiftId = shifts.id INNER JOIN departments ON employees.departmentId = departments.id INNER JOIN positions ON employees.positionId = positions.id LEFT JOIN biometrics ON employees.biometricId = biometrics.id LEFT JOIN mantises ON consolidations.mantisId = mantises.id LEFT JOIN nt3s ON consolidations.nt3Id = nt3s.id LEFT JOIN timesheets ON consolidations.timesheetId = timesheets.id LEFT JOIN timelogs ON consolidations.timelogId = timelogs.id WHERE consolidations.timesheetId = ?");
				ps.setString(1, id);
				resultSet = ps.executeQuery();
				logger.info("viewConsolidation: Executed query for displaying consolidation.");
				
				while(resultSet.next()) {
					
					final ConsolidationDTO consolidations = new ConsolidationDTO();
					//EMPLOYEE
					consolidations.setEmployeeId(resultSet.getString("employeeId"));
					consolidations.setBiometricId(resultSet.getString("biometricId"));
					consolidations.setFirstname(resultSet.getString("firstname"));
					consolidations.setMiddlename(resultSet.getString("middlename"));
					consolidations.setLastname(resultSet.getString("lastname"));
					consolidations.setEmail(resultSet.getString("email"));
					consolidations.setShift(resultSet.getString("shift"));
					consolidations.setDepartment(resultSet.getString("department"));
					consolidations.setPosition(resultSet.getString("position"));
					//TIMELOG
					consolidations.setTimeIn(resultSet.getString("timeIn"));
					consolidations.setTimeOut(resultSet.getString("timeOut"));
					consolidations.setTimeDuration(resultSet.getString("timeDuration"));
					consolidations.setTimelogDate(resultSet.getString("timelogDate"));
					//MANTIS
					consolidations.setMantisId(resultSet.getString("mantisId"));
					consolidations.setTicketId(resultSet.getString("ticketId"));
					consolidations.setMantisStartDate(resultSet.getString("mantisStartDate"));
					consolidations.setMantisEndDate(resultSet.getString("mantisEndDate"));
					consolidations.setMantisHours(resultSet.getString("mantisHours"));
					consolidations.setMantisMinutes(resultSet.getString("mantisMinutes"));
					consolidations.setMantisCategory(resultSet.getString("mantisCategory"));
					consolidations.setMantisDateSubmitted(resultSet.getString("mantisDateSubmitted"));
					consolidations.setMantisTimeIn(resultSet.getString("mantisTimeIn"));
					consolidations.setMantisTimeOut(resultSet.getString("mantisTimeOut"));
					consolidations.setMantisStatus(resultSet.getString("mantisStatus"));
					//NT3
					consolidations.setNt3Id(resultSet.getString("nt3Id"));
					consolidations.setNt3StartDate(resultSet.getString("nt3StartDate"));
					consolidations.setNt3EndDate(resultSet.getString("nt3EndDate"));
					consolidations.setNt3Duration(resultSet.getString("nt3Duration"));
					consolidations.setNt3AbsenceType(resultSet.getString("nt3AbsenceType"));
					consolidations.setNt3Description(resultSet.getString("nt3Description"));
					consolidations.setNt3AbsenceStatus(resultSet.getString("nt3AbsenceStatus"));
					//TIME SHEET
					consolidations.setTimesheetId(resultSet.getString("timesheetId"));
					consolidations.setTimesheetId(resultSet.getString("timesheetName"));
				
					
					list.add(consolidations);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("viewConsolidation: Closed MySQL connection.");
			
		return list;
		
		
	}
	
	public ArrayList<TimesheetPartialDTO> fetchTimesheets() {
		
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		ArrayList<TimesheetPartialDTO> list = new ArrayList<TimesheetPartialDTO>();
		
		try {
			connection = dataSource.getConnection();
			ps = connection
					.prepareStatement("SELECT id, description, periodEnd, periodStart FROM timesheets");
			resultSet = ps.executeQuery();

			while (resultSet.next()) {
				
				final TimesheetPartialDTO timesheet = new TimesheetPartialDTO();
				timesheet.setId(resultSet.getString("id"));
				timesheet.setDescription(resultSet.getString("description"));
				timesheet.setPeriodEnd(resultSet.getString("periodEnd"));
				timesheet.setPeriodStart(resultSet.getString("periodStart"));
				
				list.add(timesheet);
			
			}

		} catch (SQLException e) {

		}
		return list;
	}

	public void updateConsolidations(String employeeId,
			String timeIn, String timeOut, String date)  {
		

			Connection connection = null;
			PreparedStatement ps = null;
			
//			try {
//				connection = dataSource.getConnection();
//				ps = connection.prepareStatement("UPDATE SET ");
//				ps.executeUpdate();
//				connection.close();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			
	}
	
	public ArrayList<Mantis> fetchMantises(String employeeId, String date) {
		
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		ArrayList<Mantis> list = new ArrayList<Mantis>();
		int rowCount = 0;

		try {
			
			connection = dataSource.getConnection();
			logger.info("fetchMantisTickets: Created MySQL connection.");
			ps = connection.prepareStatement("SELECT ticketId, employeeId, dateSubmitted, startDate, endDate, category, timeIn, timeOut, status FROM mantises WHERE employeeId = ? and startDate = ?");
			ps.setString(1, employeeId);	
			ps.setString(2, date);
			resultSet = ps.executeQuery();
			logger.info("fetchMantisTickets: Executed MySQL query.");
			
			
			while(resultSet.next()) {
				
				final Mantis mantis = new Mantis();
				mantis.setTicketId(resultSet.getInt("ticketId"));
				mantis.setDateSubmitted(resultSet.getString("dateSubmitted"));
				mantis.setStartDate(resultSet.getString("startDate"));
				mantis.setEndDate(resultSet.getString("endDate"));
				mantis.setCategory(resultSet.getString("category"));
				mantis.setStartTime(resultSet.getString("timeIn"));
				mantis.setEndTime(resultSet.getString("timeOut"));
				mantis.setStatus(resultSet.getString("status"));
				
				
				list.add(mantis);
				rowCount++;
				}
			
		} catch (SQLException e) {
			
		} finally {
			try {
				connection.close();
				logger.info("fetchMantisTickets: Closed MySQL connection.");
			} catch (SQLException e) {
				logger.info(e.toString());
					
			}
		}
		
		logger.info("rowCount is :" + rowCount);
		return list;
	}
	
	public ArrayList<Nt3> fetchNt3s(String employeeId, String date) {
		
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		ArrayList<Nt3> list = new ArrayList<Nt3>();
		int rowCount = 0;
		

		try {
			connection = dataSource.getConnection();
			logger.info("fetchNt3s: Created MySQL connection.");
			ps = connection.prepareStatement("SELECT id, employeeId, startDate, endDate, duration, absenceType, description, absenceStatus, flag, timestamp FROM tempoplus.nt3s WHERE employeeId=? and startDate=?");
			ps.setString(1, employeeId);
			
			ps.setString(2, date);
			resultSet = ps.executeQuery();
			logger.info("fetchNt3s: Executed MySQL query.");
			
			
			while(resultSet.next()) {
				final Nt3 nt3 = new Nt3();
				
				nt3.setEmployeeId(resultSet.getInt("employeeId"));
				nt3.setStartDate(resultSet.getString("startDate"));
				nt3.setEndDate(resultSet.getString("endDate"));
				nt3.setDuration(resultSet.getFloat("duration"));
				nt3.setAbsenceType(resultSet.getString("absenceType"));
				nt3.setDescription(resultSet.getString("description"));
				nt3.setAbsenceStatus(resultSet.getString("absenceStatus"));
				
				list.add(nt3);
				rowCount++;
			}
			
			
		} catch (SQLException e) {
			logger.info(e.toString());
		} finally {
			try {
				connection.close();
				logger.info("fetchNt3s: Closed MySQL connection.");
			} catch (SQLException e) {
				logger.info(e.toString());
					
			}
		}
		
		
		logger.info("rowCount is :" + rowCount);
		return list;
	}
	
	

	public void consolidateTimeSheetTimeLog() {

		try {
			connection = dataSource.getConnection();

			PreparedStatement preparedStatement = connection
					.prepareStatement("INSERT INTO consolidations (employeeId, timelogId, date) SELECT employeeId, id, date FROM timelogs");

			preparedStatement.executeUpdate();
			preparedStatement.close();

		} catch (SQLException e) {
			// TODO Create Custom Exception
			logger.info("consolidateTimeSheetTimeLog: Error in Consolidation Phase1");
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {

				}
			}
		}

	}

	public void consolidateTimeSheetMantis(ArrayList<TimeLogging> list) {

		try {
			connection = dataSource.getConnection();
			logger.info("consolidateTimeSheetMantis: Created MySQL connection");
			PreparedStatement preparedStatement = connection
					.prepareStatement("UPDATE consolidations SET mantisId = "
							+ "(SELECT id FROM mantises WHERE employeeId = ? AND startDate = ?) WHERE"
							+ " employeeId = ? AND date = ?");

			for (TimeLogging timeLog : list) {

				preparedStatement.setInt(1, timeLog.getEmployeeId());
				preparedStatement.setString(2, timeLog.getDate());
				preparedStatement.setInt(3, timeLog.getEmployeeId());
				preparedStatement.setString(4, timeLog.getDate());
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
			logger.info("consolidateTimeSheetMantis: Executed insert query");
			preparedStatement.close();

		} catch (SQLException e) {
			e.printStackTrace();
			logger.info("consolidateTimeSheetMantis: Error in Consolidation Phase2");

		} finally {
			if (connection != null) {
				try {
					connection.close();
					logger.info("consolidateTimeSheetMantis: Closed MySql connection");
				} catch (SQLException e) {

				}
			}
		}

	}

	public void consolidateTimeSheetNt3(ArrayList<TimeLogging> list) {

		try {
			connection = dataSource.getConnection();
			logger.info("consolidateTimeSheetNt3: Created MySQL connection.");
			
			PreparedStatement preparedStatement = connection
					.prepareStatement("UPDATE consolidations SET nt3Id = "
							+ "(SELECT id FROM nt3s WHERE employeeId = ? AND startDate = ?) WHERE"
							+ " employeeId = ? AND date = ?");

			for (TimeLogging timeLog : list) {
				preparedStatement.setInt(1, timeLog.getEmployeeId());
				preparedStatement.setString(2, timeLog.getDate());
				preparedStatement.setInt(3, timeLog.getEmployeeId());
				preparedStatement.setString(4, timeLog.getDate());
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
			logger.info("consolidateTimeSheetNt3: Executing update query.");
			preparedStatement.close();

		} catch (SQLException e) {
			logger.info("consolidateTimeSheetNt3: Error in Consolidation Phase3.");

		} finally {
			if (connection != null) {
				try {
					connection.close();
					logger.info("consolidateTimeSheetNt3: Closing MySQL connection.");
				} catch (SQLException e) {

				}
			}
		}

	}

}
