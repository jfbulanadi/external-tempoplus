package hk.com.novare.tempoplus.bmnmanager.consolidation;

import hk.com.novare.tempoplus.bmnmanager.mantis.Mantis;
import hk.com.novare.tempoplus.bmnmanager.timesheet.Timesheet;
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
	
	public ArrayList<ConsolidationDTO> viewConsolidation() {
		ResultSet resultSet = null;
		Connection connection = null;
		PreparedStatement ps = null;
		
		
		
		final ArrayList<ConsolidationDTO> list = new ArrayList<ConsolidationDTO>();
		
		try {
				connection = dataSource.getConnection();
				logger.info("viewConsolidation: Created MySQL connection.");
				ps = connection.prepareStatement("SELECT e.employeeId, e.id, e.biometricId, e.firstname, e.middlename, e.lastname, e.email, p.description AS position, CONCAT_WS(', ' , e.lastname, e.firstname) AS fullName, e.hiredate, e.regularizationdate, "
       + "(SELECT CONCAT_WS(', ' , lastname, firstname) AS fullName FROM employees WHERE employeeId = e.supervisorId) AS supervisor, "
       + "c.mantisId, c.nt3Id, t.date, t.timeIn, t.timeOut, t.duration, m.ticketId, m.startDate, m.endDate, m.hours, m.minutes, "
       + "m.category, m.status, n.startDate, n.endDate, n.duration, n.absenceType, n.absenceStatus, "
       + "(SELECT MIN(bIn.logTime) FROM biometrics AS bIn WHERE log = 0 AND logDate = t.date AND biometricId = e.biometricId) AS bioTimeIn, "
       + "(SELECT MAX(bOut.logTime)  FROM biometrics AS bOut WHERE log = 1 AND logDate = t.date AND biometricId = e.biometricId) AS bioTimeOut "
       + "FROM timelogs AS t JOIN employees AS e ON t.employeeId = e.employeeId "
       + "LEFT JOIN positions p ON p.id = e.positionId "
       + "LEFT JOIN consolidations c ON t.id = c.timelogId "
       + "LEFT JOIN mantises m ON m.id = c.mantisId "
       + "LEFT JOIN nt3s n ON n.id = c.nt3Id ORDER BY e.id");
				resultSet = ps.executeQuery();
				logger.info("viewConsolidation: Executed query for displaying consolidation.");
				
				while(resultSet.next()) {
					
					String biometricId = resultSet.getString("biometricId");
					String employeeId = resultSet.getString("employeeId");
					String firstname = resultSet.getString("firstname");
					String middlename = resultSet.getString("middlename");
					String lastname = resultSet.getString("lastname");
					String position = resultSet.getString("position");
					String email = resultSet.getString("email");
					String timeIn = resultSet.getString("bioTimeIn");
					String timeOut = resultSet.getString("bioTimeOut");
					String date = resultSet.getString("date");
					String mantisId = resultSet.getString("mantisId");
					String nt3Id = resultSet.getString("nt3Id");
					
					final ConsolidationDTO consolidations = new ConsolidationDTO();
					
					consolidations.setEmployeeId(employeeId);
					consolidations.setBiometricId(biometricId);
					consolidations.setFirstname(firstname);
					consolidations.setMiddlename(middlename);
					consolidations.setLastname(lastname);
					consolidations.setPosition(position);
					consolidations.setEmail(email);
					consolidations.setDate(date);
					consolidations.setTimeIn(timeIn);
					consolidations.setTimeOut(timeOut); 
					consolidations.setMantisId(mantisId);
					consolidations.setNt3Id(nt3Id);
					

					
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

	public void updateConsolidations(String employeeId,
			String timeIn, String timeOut, String date)  {
		

			Connection connection = null;
			PreparedStatement ps = null;
			
			//connection = dataSource.getConnection();
			//ps = connection.prepareStatement("UPDATE");
			//ps.executeUpdate();
			
			
	}
	
	public ArrayList<Mantis> fetchMantisTickets(String employeeId) {
		
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		ArrayList<Mantis> list = new ArrayList<Mantis>();
		

		try {
			
			connection = dataSource.getConnection();
			logger.info("fetchMantisTickets: Created MySQL connection.");
			ps = connection.prepareStatement("SELECT ticketId, employeeId, dateSubmitted, startDate, endDate, category, timeIn, timeOut, status FROM mantises WHERE employeeId = ?");
			ps.setString(1, employeeId);	
			resultSet = ps.executeQuery();
			logger.info("fetchMantisTickets: Executed MySQL query.");
			
			
			while(resultSet.next()) {
				final int ticketId = resultSet.getInt("ticketId");
				final String dateSubmitted = resultSet.getString("dateSubmitted");
				final String startDate = resultSet.getString("startDate");
				final String endDate = resultSet.getString("endDate");
				final String category = resultSet.getString("category");
				final String timeIn = resultSet.getString("timeIn");
				final String timeOut = resultSet.getString("timeOut");
				final String status = resultSet.getString("status");
				
				
				
				final Mantis mantis = new Mantis();
				mantis.setTicketId(ticketId);
				mantis.setDateSubmitted(dateSubmitted);
				mantis.setStartDate(startDate);
				mantis.setEndDate(endDate);
				mantis.setCategory(category);
				mantis.setStartTime(timeIn);
				mantis.setEndTime(timeOut);
				mantis.setStatus(status);
				
				
				list.add(mantis);
				}
			
		} catch (SQLException e) {
			
		} finally {
			try {
				connection.close();
				logger.info("fetchMantisTickets: Closed MySQL connection.");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
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
