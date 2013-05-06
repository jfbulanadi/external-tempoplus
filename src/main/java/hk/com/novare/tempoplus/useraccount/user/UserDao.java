package hk.com.novare.tempoplus.useraccount.user;

import hk.com.novare.tempoplus.employee.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

public class UserDao {
	@Inject
	DataSource dataSource;
	@Inject
	User user;
	@Inject
	Employee employee;

	private List<User> userDetailsList = new ArrayList<User>();
	private List<Employee> supervisorDetailsList = new ArrayList<Employee>();

	// ---------find employee id in database based from employee's email
	// input-------------
	public int searchEmployeeIdInDatabase(String userEmail) {

		int employeeIdFromDb = 0;
		Connection connection = null;

		try {
			connection = dataSource.getConnection();
			final PreparedStatement searchUserStatement = connection
					.prepareStatement("select employeeId, email from "
							+ "employees where email = ?");
			searchUserStatement.setString(1, userEmail);
			ResultSet resultSet = searchUserStatement.executeQuery();

			while (resultSet.next()) {

				employeeIdFromDb = resultSet.getInt("employeeId");

				// set details to temporary storage
				user.setEmployeeId(employeeIdFromDb);
				user.setEmail(resultSet.getString("email"));
			}

			resultSet.close();
		} catch (SQLException e) {

		} finally {

			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return employeeIdFromDb;
	}

	// -----------search employee password based from employee id-------------
	public void searchEmployeePassword(int employeeId) {

		Connection connection = null;

		try {

			connection = dataSource.getConnection();
			final PreparedStatement searchPasswordStatement = connection
					.prepareStatement("select password from "
							+ "users where employeeId = ?");
			searchPasswordStatement.setInt(1, employeeId);
			ResultSet resultSet = searchPasswordStatement.executeQuery();

			while (resultSet.next()) {

				user.setPassword(resultSet.getString("password"));
			}

			resultSet.close();
		} catch (SQLException e) {

		} finally {

			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	// Retrieving employee's user details
	public List<User> retrieveUserDetails(String userEmail) {
		Connection connection = null;

		try {

			connection = dataSource.getConnection();

			final PreparedStatement retrieveDetailsStatement = connection
					.prepareStatement("select * from employees "
							+ "where email = ?");
			retrieveDetailsStatement.setString(1, userEmail);

			ResultSet rs = retrieveDetailsStatement.executeQuery();

			while (rs.next()) {

				final int biometricsID = rs.getInt("biometricId");

				final String lastName = rs.getString("lastname");
				final String firstName = rs.getString("firstname");
				final String middleName = rs.getString("middlename");

				final int departmentId = rs.getInt("departmentId");
				final int positionId = rs.getInt("positionId");
				final int level = rs.getInt("level");

				final String regularizationDate = rs
						.getString("regularizationDate");
				final String hireDate = rs.getString("hireDate");
				final String resignationDate = rs.getString("resignationDate");

				final int supervisorId = rs.getInt("supervisorId");
				final int isSupervisor = rs.getInt("isSupervisor");

				// save details to object

				user.setBiometricId(biometricsID);

				user.setLastname(lastName);
				user.setFirstname(firstName);
				user.setMiddlename(middleName);

				user.setDepartmentId(departmentId);
				user.setPositionId(positionId);
				user.setLevel(level);
				user.setSupervisorId(supervisorId);

				user.setDepartment(findDepartmentName(departmentId));
				user.setPosition(findPositionName(positionId));
				user.setIsSupervisor(isSupervisor);

				user.setHireDate(hireDate);
				user.setRegularizationDate(regularizationDate);
				user.setResignationDate(resignationDate);

			}
			userDetailsList.add(user);
			rs.close();
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

		return userDetailsList;
	}

	// --------find department name for user profile viewing--------------
	public String findDepartmentName(int departmentId) {

		String department = " ";
		Connection connection = null;

		try {
			connection = dataSource.getConnection();

			final PreparedStatement retrieveDepartmentName = connection
					.prepareStatement("select name from departments "
							+ "where id = ?");
			retrieveDepartmentName.setInt(1, departmentId);

			ResultSet rs = retrieveDepartmentName.executeQuery();

			while (rs.next()) {
				department = rs.getString("name");
			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return department;
	}

	// ------find position name for user profile viewing---------
	public String findPositionName(int positionId) {

		String position = "";
		Connection connection = null;

		try {
			connection = dataSource.getConnection();

			final PreparedStatement retrievePositionName = connection
					.prepareStatement("select description from positions "
							+ "where id = ?");
			retrievePositionName.setInt(1, positionId);

			ResultSet rs = retrievePositionName.executeQuery();

			while (rs.next()) {
				position = rs.getString("description");
			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return position;
	}

	// ------------find supervisor's details--------------
	public List<Employee> findSupervisor(int supervisorId) {
		Connection connection = null;
		Employee employee = new Employee();
		try {
			connection = dataSource.getConnection();

			final PreparedStatement retrieveSupervisor = connection
					.prepareStatement("select firstname, lastname,"
							+ " email from employees where employeeId = ?");

			retrieveSupervisor.setInt(1, supervisorId);

			ResultSet rs = retrieveSupervisor.executeQuery();

			while (rs.next()) {

				String first = rs.getString("firstname");
				String last = rs.getString("lastname");
				String email = rs.getString("email");

				employee.setFirstname(first);
				employee.setLastname(last);
				employee.setEmail(email);

			}
			supervisorDetailsList.add(employee);
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return supervisorDetailsList;

	}

	// ------------------save new password to db-----------------
	public void saveNewPasswordToDB(int employeeId, String newPassword) {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			final PreparedStatement updateNewPasswordStatement = connection
					.prepareStatement("update users " + "set password = MD5(?)"
							+ " where employeeId= ?");
			updateNewPasswordStatement.setString(1, newPassword);
			updateNewPasswordStatement.setInt(2, employeeId);
			updateNewPasswordStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
