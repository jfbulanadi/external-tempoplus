package hk.com.novare.tempoplus.bmnmanager.consolidation;


import hk.com.novare.tempoplus.employee.Employee;
import hk.com.novare.tempoplus.timelogging.TimeLogging;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	
	public ArrayList<Employee> viewConsolidated() throws SQLException {
		Connection connection = null;
		connection = dataSource.getConnection();
		PreparedStatement sql = null;
		
	
		sql = connection.prepareStatement("SELECT * FROM employees");
	
		
		//ps = connection.prepareStatement("SELECT * FROM consolidations WHERE usersId=?, biometrics=?, mantises=?, mantisesId=?, nt3sId=?, overtimesId=?, officialBusinessesId=?, deductionsId=?, timelogAdjustmentsId=?, consolidationName=?, periodStart=?, periodEnd=?, timestamp=?");
		ResultSet resultSet = sql.executeQuery();
		
		final ArrayList<Employee> bmn = new ArrayList<Employee>();
		
		while(resultSet.next()) {
			
			
			final Employee employee = new Employee();
			final int employeeId = resultSet.getInt(2);
			final String firstname = resultSet.getString(4);
			final String middlename = resultSet.getString(6);
			final String lastname = resultSet.getString(3);

			employee.setEmployeeId(employeeId);
			employee.setFirstname(firstname);
			employee.setMiddlename(middlename);
			employee.setLastname(lastname);

			bmn.add(employee);
		}
		resultSet.close();
		connection.close();
		return bmn;
		
		
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
	
	
	public ArrayList<String> getEmployeeById(int employeeId) throws SQLException {
		
		ArrayList<String> listofemployee = new ArrayList<String>();
		
		Connection connection = null;
		PreparedStatement ps = null;

		connection = dataSource.getConnection();
		ps = connection.prepareStatement("SELECT * FROM employees WHERE employeeId=?");
		ps.setInt(1, employeeId);
				
		ResultSet resultSet = ps.executeQuery();
			while(resultSet.next()) {
				final int employeeid = resultSet.getInt("employeeId");
				final String firstname = resultSet.getString("firstname");
				final String middlename = resultSet.getString("middlename");
				final String lastname = resultSet.getString("lastname");
				final String email = resultSet.getString("email");
				
				
				Employee employee = new Employee();
				employee.setEmployeeId(employeeid);
				employee.setFirstname(firstname);
				employee.setMiddlename(middlename);
				employee.setLastname(lastname);
				employee.setEmail(email);
				
				//System.out.println(employeeid + "<>" + firstname + "<>" + middlename + "<>" +lastname+ "<>" + email);
			}
			
			connection.close();	
		return listofemployee;
		
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
