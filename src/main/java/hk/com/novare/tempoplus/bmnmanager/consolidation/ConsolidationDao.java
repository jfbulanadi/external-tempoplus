package hk.com.novare.tempoplus.bmnmanager.consolidation;

import hk.com.novare.tempoplus.bmnmanager.timesheet.Timesheet;
import hk.com.novare.tempoplus.employee.Employee;
import hk.com.novare.tempoplus.timelogging.TimeLogging;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

import javax.inject.Inject;
import javax.sql.DataSource;



public class ConsolidationDao {

	@Inject
	DataSource dataSource;

	private Connection connection = null;

	/**
	 * Checks if timesheet is ready of Consolidation.
	 * 
	 * @return
	 */
	public boolean isReadyForConsolidation() {

		return true;
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

			PreparedStatement preparedStatement = connection
					.prepareStatement("INSERT INTO consolidations (consolidationName, periodStart, periodEnd)"
							+ "VALUES (?, ?, ?)");

			preparedStatement.setString(1, name);
			preparedStatement.setString(2, periodStart);
			preparedStatement.setString(3, periodEnd);

			preparedStatement.executeUpdate();
			preparedStatement.close();

		} catch (SQLException e) {
			// TODO Create Custom Exception
			return false;
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {

				}
			}
		}

		return true;
	}
	
	
	public void updateConsolidatedTimesheetById(int id, Consolidation consolidation) {
		

		try {
			connection = dataSource.getConnection();

			PreparedStatement preparedStatement = connection
					.prepareStatement("UPDATE consolidations (userId, biometricId, mantisId, nt3Id, name, " +
							" periodStart, periodEnd) VALUES (?, ?, ?, ?, ?, ?, ?, ?) WHERE id = ?");
			
			preparedStatement.setInt(1, consolidation.getUserId());
			preparedStatement.setInt(2, consolidation.getBiometricId());
			preparedStatement.setInt(3, consolidation.getMantisId());
			preparedStatement.setInt(4, consolidation.getNt3Id());
			preparedStatement.setString(5, consolidation.getName());
			preparedStatement.setString(6, consolidation.getPeriodStart());
			preparedStatement.setString(7, consolidation.getPeriodEnd());
			preparedStatement.setInt(8, id);

			preparedStatement.executeUpdate();
			preparedStatement.close();

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
		
	}
	
	public Consolidation isReadyForConsolidation(int id) {
		
		Consolidation consolidation = new Consolidation();
		try {
			connection = dataSource.getConnection();

			PreparedStatement preparedStatement = connection
					.prepareStatement("SELECT userId, biometricId, mantisId, nt3Id, name, " +
							" periodStart, periodEnd FROM consolidations WHERE id=?");
			
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

	public void consolidateTimeSheet(ArrayList<Integer> idList) {
		
		try {
			connection = dataSource.getConnection();

			PreparedStatement preparedStatement = connection
					.prepareStatement("INSERT INTO consolidations (employeeId, timelogId, date) SELECT employeeId, id, date FROM timelog WHERE employeeId = ?");

			for(int id:idList) {
				System.out.println(id);
				preparedStatement.setInt(1, id);
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
			preparedStatement.close();

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
		
	}
	
	public ArrayList<Timesheet> viewConsolidated() throws SQLException {
		Connection connection = null;
		connection = dataSource.getConnection();
		PreparedStatement ps = null;
		final ArrayList<Timesheet> list = new ArrayList<Timesheet>();		
		
		
	ps = connection.prepareStatement("SELECT e.employeeId, e.id, e.biometricId, e.firstname, e.middlename, e.lastname, e.email, CONCAT_WS(',', e.lastname, e.firstname), p.description, e.hiredate, e.regularizationdate, t.date, t.timeIn, t.timeOut, t.duration, m.ticketId, m.startDate, m.endDate, m.hours, m.minutes, m.category, m.status, n.startDate, n.endDate, n.duration, n.absenceType, n.absenceStatus, (SELECT MIN(logTime) FROM biometrics WHERE log = 0 AND biometricId = e.biometricId AND logDate=t.date GROUP BY biometricId, logDate), (SELECT MAX(logTime) FROM biometrics WHERE log = 1 AND biometricId = e.biometricId AND logDate=t.date GROUP BY biometricId, logDate) FROM timelogs AS t JOIN employees AS e ON t.employeeId = e.employeeId JOIN positions as p on p.id = e.positionId LEFT JOIN consolidations c ON t.id = c.timelogId LEFT JOIN mantises m ON m.id = c.mantisId LEFT JOIN nt3s n ON n.id = c.nt3Id ORDER BY e.id");
	ResultSet resultSet = ps.executeQuery();
	
	while(resultSet.next()) {
		final Employee employee = new Employee();
		final TimeLogging timelog = new TimeLogging();
		final Timesheet timesheet = new Timesheet();
		

		final int employeeId = resultSet.getInt("employeeId");
		final int biometricId = resultSet.getInt("biometricId");
		final String name = resultSet.getString("CONCAT_WS(',', e.lastname, e.firstname)");
		final String firstname = resultSet.getString("firstname");
		final String middlename = resultSet.getString("middlename");
		final String lastname = resultSet.getString("lastname");
		final String email = resultSet.getString("email");
		final String position = resultSet.getString("description");
		final String dateIn = resultSet.getString("date");
		final String timeIn = resultSet.getString("timeIn");
		final String timeOut = resultSet.getString("timeOut");
		final String duration = resultSet.getString("duration");

		
		System.out.println("----------------------");
		System.out.println(resultSet.getString("firstname"));
		System.out.println(employeeId);
		System.out.println(firstname);
		System.out.println(middlename);
		System.out.println(lastname);
		System.out.println(dateIn);
		System.out.println(timeIn + "this");
		System.out.println(timeOut);
		System.out.println(duration);
		System.out.println("----------------------");		

		
		employee.setEmployeeId(employeeId);
		employee.setBiometricId(biometricId);
		employee.setFirstname(firstname);
		employee.setMiddlename(middlename);
		employee.setLastname(lastname);
		employee.setEmail(email);
		
		timelog.setDate(dateIn);
		timelog.setTimeIn(timeIn);
		timelog.setTimeOut(timeOut);
		timelog.setDuration(duration);
		
		
		timesheet.setEmployee(employee);
		timesheet.setTimelog(timelog);
		
		
		
		list.add(timesheet);
		
		}
		
	
		return list;
		
	}
	
	public ArrayList<Employee> updateViewConsolidated(int employeeid, String firstname) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		ArrayList<Employee> list = new ArrayList<Employee>();
	
		
		connection = dataSource.getConnection();
		ps = connection.prepareStatement("UPDATE employees SET firstname=? WHERE employeeId=?");
		ps.setString(1, firstname);
		ps.setInt(2, employeeid);
		ps.executeUpdate();
		ps.close();
		
		ps = connection.prepareStatement("SELECT * FROM employees");
		ResultSet resultSet = ps.executeQuery();
		
			while (resultSet.next()) {
				final int employeeidupdated = resultSet.getInt(2);
				final String firstnameupdated = resultSet.getString(3);
				
				
				Employee employee = new Employee();
				employee.setEmployeeId(employeeidupdated);
				employee.setFirstname(firstnameupdated);
				
				list.add(employee);
			}
			resultSet.close();
			
			connection.close();
			return list;
			
		

}
	
	

	public void consolidateTimeSheetPhase1() {

		try {
			connection = dataSource.getConnection();

			PreparedStatement preparedStatement = connection
					.prepareStatement("INSERT INTO consolidations (employeeId, timelogId, date) SELECT employeeId, id, date FROM timelogs");

			preparedStatement.executeUpdate();
			preparedStatement.close();

		} catch (SQLException e) {
			// TODO Create Custom Exception
			System.out.println("Error in Consolidation Phase1");
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

	public void consolidateTimeSheetPhase2(ArrayList<TimeLogging> list) {
		
		try {
			connection = dataSource.getConnection();
			System.out.println("Phase 2");
			PreparedStatement preparedStatement = connection
					.prepareStatement("UPDATE consolidations SET mantisId = " +
							"(SELECT id FROM mantises WHERE employeeId = ? AND startDate = ?) WHERE" +
							" employeeId = ? AND date = ?");

			for (TimeLogging timeLog: list) {

				preparedStatement.setInt(1, timeLog.getEmployeeId());
				preparedStatement.setString(2, timeLog.getDate());
				preparedStatement.setInt(3, timeLog.getEmployeeId());
				preparedStatement.setString(4, timeLog.getDate());
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
			preparedStatement.close();

		} catch (SQLException e) {
			System.out.println("Error in Consolidation Phase2");

		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					
				}
			}
		}

	}
	
	public void consolidateTimeSheetPhase3(ArrayList<TimeLogging> list) {
		
		try {
			connection = dataSource.getConnection();

			PreparedStatement preparedStatement = connection
					.prepareStatement("UPDATE consolidations SET nt3Id = " +
							"(SELECT id FROM nt3s WHERE employeeId = ? AND startDate = ?) WHERE" +
							" employeeId = ? AND date = ?");

			for (TimeLogging timeLog: list) {
				preparedStatement.setInt(1, timeLog.getEmployeeId());
				preparedStatement.setString(2, timeLog.getDate());
				preparedStatement.setInt(3, timeLog.getEmployeeId());
				preparedStatement.setString(4, timeLog.getDate());
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
			preparedStatement.close();

		} catch (SQLException e) {
			System.out.println("Error in Consolidation Phase3");

		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					
				}
			}
		}

	}
	
	
}
