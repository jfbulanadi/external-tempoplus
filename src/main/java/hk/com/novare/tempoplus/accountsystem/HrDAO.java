package hk.com.novare.tempoplus.accountsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

@Repository
public class HrDAO implements HrModel {

	@Inject
	DataSource dataSource;

	@Override
	public List<HumanResource> selectAll() {
		Connection connection = null;
		final ArrayList<HumanResource> employeesList = new ArrayList<HumanResource>();

		try {
			connection = dataSource.getConnection();
			final PreparedStatement ps = connection
					.prepareStatement("SELECT employees.firstname, employees.middlename, employees.lastname, "
							+ "employees.biometricId, employees.employeeId, employees.email, "
							+ "employees.hireDate, employees.regularizationDate, "
							+ "employees.resignationDate, "
							+ "departments.name, employees.level, positions.description "
							+ "FROM tempoplus.employees, departments, positions "
							+ "WHERE employees.departmentId = departments.id "
							+ "and positions.id = employees.positionId ");
			final ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				final String firstName = resultSet
						.getString("employees.firstname");
				final String middleName = resultSet
						.getString("employees.middlename");
				final String lastName = resultSet
						.getString("employees.lastname");
				final String biometricsNo = resultSet
						.getString("employees.biometricId");
				final String employeeId = resultSet
						.getString("employees.employeeId");
				final String employeeEmail = resultSet
						.getString("employees.email");
				final String hiredDate = resultSet
						.getString("employees.hireDate");
				final String regularizationDate = resultSet
						.getString("employees.regularizationDate");
				final String resignationDate = resultSet
						.getString("employees.resignationDate");
				final String departmentName = resultSet
						.getString("departments.name");
				final String level = resultSet.getString("employees.level");
				final String position = resultSet
						.getString("positions.description");
				


				// todo-- supervisor
				/*
				 * final String supervisorName = resultSet
				 * .getString("SUPERVISOR_NAME"); final String supervisorEmail =
				 * resultSet .getString("SUPERVISOR_EMAIL");
				 */

				// Store in an object
				final HumanResource employeeFullInfo = new HumanResource();
				employeeFullInfo.setFirstName(firstName);
				employeeFullInfo.setMiddleName(middleName);
				employeeFullInfo.setLastName(lastName);
				employeeFullInfo.setBiometrics(biometricsNo);
				employeeFullInfo.setEmployeeId(employeeId);
				employeeFullInfo.setEmployeeEmail(employeeEmail);
				employeeFullInfo.setHiredDate(hiredDate);
				employeeFullInfo.setRegularizationDate(regularizationDate);
				employeeFullInfo.setResignationDate(resignationDate);
				employeeFullInfo.setDepartment(departmentName);
				employeeFullInfo.setLevel(level);
				employeeFullInfo.setPosition(position);
				/*
				 * employeeFullInfo.setSupervisorName(supervisorName);
				 * employeeFullInfo.setSupervisorEmail(supervisorEmail);
				 */

				employeesList.add(employeeFullInfo);

			}
			resultSet.close();

		} catch (SQLException e) {
			// Throw a nested exception
			// Encapsulation of exceptions
			e.printStackTrace();
		} finally {
			// Always close the connection. Error or not.
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return employeesList;
	}

	@Override
	public void createEmployee(HumanResource employee) {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			// ? are placeholders for the parameters
			/*final PreparedStatement ps = connection
					.prepareStatement("INSERT INTO employees (firstname, middlename, lastname, biometricId,"
							+ " employeeId, email, hireDate, "
							+ "departmentId, positionId, level, supervisorId) values (?, ?,"
							+ " ?, ?, ?, ?, ?, ?, ?, ?, ? )");*/
			
			
			final PreparedStatement ps = connection
					.prepareStatement("INSERT INTO employees (firstname, lastname, middlename, biometricId, employeeId, email, hireDate, positionId, departmentId, supervisorId, level) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			// Convert object to a row
			
			ps.setString(1, employee.getFirstName());
			ps.setString(2, employee.getMiddleName());
			ps.setString(3, employee.getLastName());
			ps.setString(4, employee.getBiometrics());
			ps.setString(5, employee.getEmployeeId());
			ps.setString(6, employee.getEmployeeEmail());
			ps.setString(7, employee.getHiredDate());
			ps.setInt(8, employee.getPositionId());
			ps.setInt(9, employee.getDepartmentId());
			ps.setInt(10, 1);
			ps.setString(11, employee.getLevel());
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			

		} finally {
			// Always close the connection. Error or not.
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// Ignore. Can't do anything anymore.
				}
			}
		}
	}

	@Override
	public HumanResource search(String searchString, String category) {

		Connection connection = null;
		final HumanResource employee = new HumanResource();

		try {
			connection = dataSource.getConnection();
			final Statement st = connection.createStatement();
			final ResultSet resultSet = st
					.executeQuery("SELECT * FROM tempoplus.employees, departments, positions  "
							+ "WHERE employees.employeeId = "
							+ searchString
							+ " and "
							+ "positions.id = employees.positionId and departments.id = employees.departmentId;");

			while (resultSet.next()) {
				final String firstName = resultSet.getString("firstname");
				final String middleName = resultSet.getString("middlename");
				final String lastName = resultSet.getString("lastname");
				final String biometricsNo = resultSet.getString("biometricId");
				final String employeeId = resultSet.getString("employeeId");
				final String employeeEmail = resultSet.getString("email");
				final String hiredDate = resultSet.getString("hireDate");
				final String regularizationDate = resultSet
						.getString("employees.regularizationDate");
				final String resignationDate = resultSet
						.getString("employees.resignationDate");
				final String departmentName = resultSet
						.getString("departments.name");
				final String level = resultSet.getString("employees.level");
				final String position = resultSet
						.getString("positions.description");
				/*
				 * final String supervisorName = resultSet
				 * .getString("SUPERVISOR_NAME"); final String supervisorEmail =
				 * resultSet .getString("SUPERVISOR_EMAIL");
				 */

				// Store in an object

				employee.setFirstName(firstName);
				employee.setMiddleName(middleName);
				employee.setLastName(lastName);
				employee.setBiometrics(biometricsNo);
				employee.setEmployeeId(employeeId);
				employee.setEmployeeEmail(employeeEmail);
				employee.setHiredDate(hiredDate);
				employee.setRegularizationDate(regularizationDate);
				employee.setResignationDate(resignationDate);
				employee.setDepartment(departmentName);
				employee.setLevel(level);
				employee.setPosition(position);
				/*
				 * employee.setSupervisorName(supervisorName);
				 * employee.setSupervisorEmail(supervisorEmail);
				 */

			}
			resultSet.close();

		} catch (SQLException e) {
			// Throw a nested exception
			// Encapsulation of exceptions
			e.printStackTrace();
		} finally {
			// Always close the connection. Error or not.
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return employee;
	}
	 
	public int retrieveDepartmentId(String department) {
		Connection connection = null;
		int departmentId = 0;

		try {
			connection = dataSource.getConnection();
			final Statement st = connection.createStatement();
			final ResultSet resultSet = st
					.executeQuery("SELECT id FROM tempoplus.departments " +
							"where departments.name = '"+ department +"'");

			while (resultSet.next()) {
				departmentId = resultSet.getInt("id");
			
				// Store in an object
			}
			resultSet.close();

		} catch (SQLException e) {
			// Throw a nested exception
			// Encapsulation of exceptions
			e.printStackTrace();
		} finally {
			// Always close the connection. Error or not.
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return departmentId;
	}
	
	public Map<Integer, String> retrieveDepartment() {
		Connection connection = null;
		Map<Integer, String> map = new HashMap<Integer, String>();
		
		try {
			connection = dataSource.getConnection();
			final Statement st = connection.createStatement();
			final ResultSet resultSet = st
					.executeQuery("SELECT * FROM tempoplus.departments ");

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				// Store in an object
				map.put(id, name);
			}
			resultSet.close();

		} catch (SQLException e) {
			// Throw a nested exception
			// Encapsulation of exceptions
			e.printStackTrace();
		} finally {
			// Always close the connection. Error or not.
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return map;
	}
	
	public Map<Integer, String> retrievePosition(int departmentId) {
		Connection connection = null;
		Map<Integer, String> map = new HashMap<Integer, String>();
		
		try {
			connection = dataSource.getConnection();
			final Statement st = connection.createStatement();
			final ResultSet resultSet = st
					.executeQuery("SELECT positions.id, description FROM tempoplus.positions " +
							"WHERE positions.departmentId = "+ departmentId);
			
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("description");
				// Store in an object
				map.put(id, name);
			}
			resultSet.close();

		} catch (SQLException e) {
			// Throw a nested exception
			// Encapsulation of exceptions
			e.printStackTrace();
		} finally {
			// Always close the connection. Error or not.
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return map;
	}
	
	public int retrievePositionId(String position) {
		Connection connection = null;
		int positionId = 0;

		try {
			connection = dataSource.getConnection();
			final Statement st = connection.createStatement();
			final ResultSet resultSet = st
					.executeQuery("SELECT id FROM tempoplus.positions " +
							"where positions.description = '"+ position +"'");

			while (resultSet.next()) {
				positionId = resultSet.getInt("id");
			
				// Store in an object
			}
			resultSet.close();

		} catch (SQLException e) {
			// Throw a nested exception
			// Encapsulation of exceptions
			e.printStackTrace();
		} finally {
			// Always close the connection. Error or not.
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return positionId;
	}
	
	public List<Integer> retrieveAllEmployeeId() {
		Connection connection = null;
		List<Integer> employeeIdList = new ArrayList<Integer>();
		int empId;
		try {
			connection = dataSource.getConnection();
			final PreparedStatement ps = connection
					.prepareStatement("SELECT employeeId from employees");
			final ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				empId = resultSet.getInt("employeeId");
				// Store in an object
				employeeIdList.add(empId);
			}
			resultSet.close();

		} catch (SQLException e) {
			// Throw a nested exception
			// Encapsulation of exceptions
			e.printStackTrace();
		} finally {
			// Always close the connection. Error or not.
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return employeeIdList;
	}
	
	public Map<Integer, String> retrieveSupervisor(int departmentId) {
		Connection connection = null;
		Map<Integer, String> map = new HashMap<Integer, String>();
		
		try {
			connection = dataSource.getConnection();
			final Statement st = connection.createStatement();
			final ResultSet resultSet = st
					.executeQuery("SELECT firstname, lastname, employees.employeeId FROM tempoplus.employees, " +
							"departments, positions WHERE isSupervisor like 'true' " +
							"AND employees.departmentId = departments.id AND employees.positionId = positions.id " +
							"AND departments.id = " + departmentId);
			
			while (resultSet.next()) {

				int employeeId = resultSet.getInt("employees.employeeId");
				String firstName = resultSet.getString("firstname");
				String lastName = resultSet.getString("lastname");
				String name = firstName + " " + lastName;
				
				// Store in an object
				map.put(employeeId, name);
			}
			resultSet.close();

		} catch (SQLException e) {
			// Throw a nested exception
			// Encapsulation of exceptions
			e.printStackTrace();
		} finally {
			// Always close the connection. Error or not.
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return map;
	}
	

	public void updateEmployee(HumanResource employee) {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			// ? are placeholders for the parameters
			final PreparedStatement ps = connection
					.prepareStatement("UPDATE employees SET firstname= ?, middlename = ?, lastname = ?, biometricId = ?,"
							+ "employeeId = ?, email = ?, hireDate = ?, departmentId = ?, positionId = ?, "
							+ "level = ?  WHERE employeeId = '"
							+ employee.getEmployeeId() + "'");

			// Convert object to a row
			ps.setString(1, employee.getFirstName());
			ps.setString(2, employee.getMiddleName());
			ps.setString(3, employee.getLastName());
			ps.setString(4, employee.getBiometrics());
			ps.setString(5, employee.getEmployeeId());
			ps.setString(6, employee.getEmployeeEmail());
			ps.setString(7, employee.getHiredDate());
			ps.setInt(8, employee.getDepartmentId());
			ps.setInt(9, employee.getPositionId());
			ps.setString(10, employee.getLevel());
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Always close the connection. Error or not.
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// Ignore. Can't do anything anymore.
				}
			}
		}

	}
	
	public void createAccount(int employeeId){
        Connection connection = null;
       
        try {
            connection = dataSource.getConnection();
            final PreparedStatement createUserStatement =
                    connection.prepareStatement("INSERT INTO users (employeeId, password) " +
                            "values (?,?)");
            createUserStatement.setInt(1, employeeId);
            createUserStatement.setString(2, "MD5(default)");
            createUserStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
       
       
    }

}
