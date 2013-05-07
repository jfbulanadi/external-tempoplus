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
	public void createEmployee(HumanResourceDTO employee) {
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
					.prepareStatement("INSERT INTO employees (firstname, lastname, middlename, biometricId, employeeId, email, hireDate, positionId, departmentId, supervisorId, level, shiftId, isSupervisor) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			// Convert object to a row
			
			ps.setString(1, employee.getFirstName());
			ps.setString(2, employee.getMiddleName());
			ps.setString(3, employee.getLastName());
			ps.setInt(4, employee.getBiometricId());
			ps.setInt(5, employee.getEmployeeId());
			ps.setString(6, employee.getEmployeeEmail());
			ps.setString(7, employee.getHiredDate());
			ps.setInt(8, employee.getPositionId());
			ps.setInt(9, employee.getDepartmentId());
			ps.setInt(10, employee.getSupervisorId());
			ps.setInt(11, employee.getLevel());
			ps.setInt(12, employee.getShiftId());
			ps.setString(13, employee.getIsSupervisor());
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
	public void createEmployeeFromUpload(HumanResourceDTO employee) {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			
			final PreparedStatement ps = connection
					.prepareStatement("INSERT INTO employees (firstname, lastname, middlename, " +
							"biometricId, departmentId, shiftId, employeeId, positionId, level, " +
							"hireDate, regularizationDate, resignationDate, email, locationAssign, isSupervisor)" +
							" VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			// Convert object to a row
			
			ps.setString(1, employee.getFirstName());
			ps.setString(2, employee.getLastName());
			ps.setString(3, employee.getMiddleName());
			ps.setInt(4, employee.getBiometricId());
			ps.setInt(5, employee.getDepartmentId());
			ps.setInt(6, employee.getShiftId());
			ps.setInt(7, employee.getEmployeeId());
			ps.setInt(8, employee.getPositionId());
			ps.setInt(9, employee.getLevel());
			ps.setString(10, employee.getHiredDate());
			ps.setString(11, employee.getRegularizationDate());
			ps.setString(12, employee.getResignationDate());
			ps.setString(13, employee.getEmployeeEmail());
			ps.setString(14, employee.getLocAssign());
			ps.setString(15, employee.getIsSupervisor());
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
	public HumanResourceDTO search(String searchString, String category) {

		Connection connection = null;
		final HumanResourceDTO employee = new HumanResourceDTO();

		try {
			connection = dataSource.getConnection();
			final Statement st = connection.createStatement();
			final ResultSet resultSet = st
					.executeQuery("SELECT firstname, lastname, middlename, biometricId, shiftId, employeeId, " +
							"positionId, level, hireDate, regularizationDate, resignationDate, email, " +
							"locationAssign, departments.name, positions.description, locationAssign, supervisorId, shifts.description , employees.departmentId " +
							" FROM tempoplus.employees, departments, positions, shifts  "
							+ "WHERE employees.employeeId = "
							+ searchString
							+ " and "
							+ "positions.id = employees.positionId and departments.id = employees.departmentId " +
							"and shifts.id = employees.shiftId");

			while (resultSet.next()) {
				final String firstName = resultSet.getString("firstname");
				final String middleName = resultSet.getString("middlename");
				final String lastName = resultSet.getString("lastname");
				final int biometricsNo = resultSet.getInt("biometricId");
				final int employeeId = resultSet.getInt("employeeId");
				final String employeeEmail = resultSet.getString("email");
				final String hiredDate = resultSet.getString("hireDate");
				final String regularizationDate = resultSet
						.getString("employees.regularizationDate");
				final String resignationDate = resultSet
						.getString("employees.resignationDate");
				final String departmentName = resultSet
						.getString("departments.name");
				final int level = resultSet.getInt("employees.level");
				final String position = resultSet
						.getString("positions.description");
				final String location = resultSet
						.getString("locationAssign");
				final int supervisorId = resultSet.getInt("supervisorId");
				final String shift = resultSet.getString("shifts.description");
				final int departmentId = resultSet.getInt("departmentId");

				// Store in an object

				employee.setFirstName(firstName);
				employee.setMiddleName(middleName);
				employee.setLastName(lastName);
				employee.setBiometricId(biometricsNo);
				employee.setEmployeeId(employeeId);
				employee.setEmployeeEmail(employeeEmail);
				employee.setHiredDate(hiredDate);
				employee.setRegularizationDate(regularizationDate);
				employee.setResignationDate(resignationDate);
				employee.setDepartment(departmentName);
				employee.setLevel(level);
				employee.setPosition(position);
				employee.setLocAssign(location);
				employee.setSupervisorId(supervisorId);
				employee.setShift(shift);
				employee.setDepartmentId(departmentId);

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
	
	public int retrieveShiftId(String shift) {
		Connection connection = null;
		int shiftId = 0;

		try {
			connection = dataSource.getConnection();
			final Statement st = connection.createStatement();
			final ResultSet resultSet = st
					.executeQuery("SELECT id from shifts where description like '"+ shift + "'");

			while (resultSet.next()) {
				shiftId = resultSet.getInt("id");
			
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
		return shiftId;
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
	
	public SupervisorDTO retrieveSupervisorNameAndEmail(int supervisorId) {
		Connection connection = null;
		SupervisorDTO supervisorDTO = new SupervisorDTO();
		try {
			connection = dataSource.getConnection();
			final Statement st = connection.createStatement();
			final ResultSet resultSet = st.executeQuery("SELECT firstname, lastname, email FROM tempoplus.employees WHERE " +
							"isSupervisor like 'true' and employeeId = '"+ supervisorId+"'");
			
			while (resultSet.next()) {

				final String firstName = resultSet.getString("firstname");
				final String lastName = resultSet.getString("lastname");
				final String email= resultSet.getString("email");
				
				// Store in an object
				supervisorDTO.setFirstname(firstName);
				supervisorDTO.setLastname(lastName);
				supervisorDTO.setEmail(email);
				
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
		return supervisorDTO;
	}
	
	
	public int retrieveSupervisorEmployeeId(String supervisorEmail) {
		Connection connection = null;
		int employeeId = 0;
		try {
			connection = dataSource.getConnection();
			final Statement st = connection.createStatement();
			final ResultSet resultSet = st
					.executeQuery("SELECT employeeId FROM tempoplus.employees WHERE " +
							"isSupervisor like 'true' and email like '"+ supervisorEmail+"'");
			
			while (resultSet.next()) {

				employeeId = resultSet.getInt("employeeId");
				
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
		return employeeId;
	}
	
	public Map<Integer, String> retrieveShifts() {
		Connection connection = null;
		Map<Integer, String> map = new HashMap<Integer, String>();
		
		try {
			connection = dataSource.getConnection();
			final Statement st = connection.createStatement();
			final ResultSet resultSet = st
					.executeQuery("SELECT * FROM tempoplus.shifts ");

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String description = resultSet.getString("description");
				// Store in an object
				map.put(id, description);
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
	

	public void updateEmployee(HumanResourceDTO employee) {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			// ? are placeholders for the parameters
			final PreparedStatement ps = connection
					.prepareStatement("UPDATE employees SET firstname= ?, middlename = ?, lastname = ?, " +
							"employeeId = ?, biometricId = ?, departmentId = ?, positionId = ?, shiftId = ?, " +
							"level = ?, hireDate = ?, regularizationDate = ?, resignationDate = ?, supervisorId = ?," +
							" locationAssign = ?, email = ?  WHERE employeeId = ?");

			// Convert object to a row
			ps.setString(1, employee.getFirstName());
			ps.setString(2, employee.getMiddleName());
			ps.setString(3, employee.getLastName());			
			ps.setInt(4, employee.getEmployeeId());
			ps.setInt(5, employee.getBiometricId());
			ps.setInt(6, employee.getDepartmentId());
			ps.setInt(7, employee.getPositionId());
			ps.setInt(8, employee.getShiftId());
			ps.setInt(9, employee.getLevel());
			ps.setString(10, employee.getHiredDate());
			ps.setString(11, employee.getRegularizationDate());
			ps.setString(12, employee.getResignationDate());
			ps.setInt(13, employee.getSupervisorId());
			ps.setString(14, employee.getLocAssign());
			ps.setString(15, employee.getEmployeeEmail());
			ps.setInt(16, employee.getEmployeeId());
			
			
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
	
	public void updateSupervisorId(HumanResourceDTO employee) {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			// ? are placeholders for the parameters
			final PreparedStatement ps = connection
					.prepareStatement("UPDATE employees SET supervisorId= ? WHERE employeeId = '"+ employee.getEmployeeId() + "'");

			// Convert object to a row
			ps.setInt(1, employee.getSupervisorId());
			
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
            createUserStatement.setString(2, "MD5('default')");
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
