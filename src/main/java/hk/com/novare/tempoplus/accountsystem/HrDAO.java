package hk.com.novare.tempoplus.accountsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

@Repository
public class HrDAO implements HrModel {

	@Inject
	DataSource dataSource;

	@Override
	public List<Employee> selectAll() {
		Connection connection = null;
		final ArrayList<Employee> employeesList = new ArrayList<Employee>();

		try {
			connection = dataSource.getConnection();
			final PreparedStatement ps = connection
					.prepareStatement("SELECT * FROM employees");
			final ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				final String firstName = resultSet.getString("FIRST_NAME");
				final String middleName = resultSet.getString("MIDDLE_NAME");
				final String lastName = resultSet.getString("LAST_NAME");
				final String biometricsNo = resultSet
						.getString("BIOMETRICS_ID");
				final String employeeId = resultSet.getString("EMPLOYEE_ID");
				final String employeeEmail = resultSet
						.getString("EMPLOYEE_EMAIL");
				final String hiredDate = resultSet.getString("HIRE_DATE");
				final String regularizationDate = resultSet
						.getString("REGULARIZATION_DATE");
				final String resignationDate = resultSet
						.getString("RESIGNATION_DATE");
				final String departmentName = resultSet
						.getString("DEPARTMENT_NAME");
				final String level = resultSet.getString("LEVEL");
				final String position = resultSet.getString("POSITION");
				final String supervisorName = resultSet
						.getString("SUPERVISOR_NAME");
				final String supervisorEmail = resultSet
						.getString("SUPERVISOR_EMAIL");

				// Store in an object
				final Employee employeeFullInfo = new Employee();
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
				employeeFullInfo.setSupervisorName(supervisorName);
				employeeFullInfo.setSupervisorEmail(supervisorEmail);

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
	public void addEmployee(Employee employee) throws HrDataAccessException {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			// ? are placeholders for the parameters
			final PreparedStatement ps = connection
					.prepareStatement("INSERT INTO employee_table (FIRST_NAME, MIDDLE_NAME, LAST_NAME, BIOMETRICS_ID,"
							+ " EMPLOYEE_ID, EMPLOYEE_EMAIL, HIRE_DATE, "
							+ "DEPARTMENT_NAME, LEVEL, POSITION, SUPERVISOR_NAME, SUPERVISOR_EMAIL    ) values (?, ?,"
							+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			// Convert object to a row
			ps.setString(1, employee.getFirstName());
			ps.setString(2, employee.getMiddleName());
			ps.setString(3, employee.getLastName());
			ps.setString(4, employee.getBiometrics());
			ps.setString(5, employee.getEmployeeId());
			ps.setString(6, employee.getEmployeeEmail());
			ps.setString(7, employee.getHiredDate());
			ps.setString(8, employee.getDepartment());
			ps.setString(9, employee.getLevel());
			ps.setString(10, employee.getPosition());
			ps.setString(11, employee.getSupervisorName());
			ps.setString(12, employee.getSupervisorName());

			final int rowCount = ps.executeUpdate();

			if (rowCount != 1) {
				throw new HrDataAccessException("unknown row count: "
						+ rowCount);
			}
		} catch (SQLException e) {
			throw new HrDataAccessException(e);

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
	public List<Employee> search(String searchString, String category) {

		Connection connection = null;
		final ArrayList<Employee> employeesList = new ArrayList<Employee>();
		try {
			connection = dataSource.getConnection();
			final Statement st = connection.createStatement();
			final ResultSet resultSet = st
					.executeQuery("SELECT * FROM employees WHERE " + category
							+ " like '" + searchString + "%'");

			while (resultSet.next()) {
				final String firstName = resultSet.getString("FIRST_NAME");
				final String middleName = resultSet.getString("MIDDLE_NAME");
				final String lastName = resultSet.getString("LAST_NAME");
				final String biometricsNo = resultSet
						.getString("BIOMETRICS_ID");
				final String employeeId = resultSet.getString("EMPLOYEE_ID");
				final String employeeEmail = resultSet
						.getString("EMPLOYEE_EMAIL");
				final String hiredDate = resultSet.getString("HIRE_DATE");
				final String regularizationDate = resultSet
						.getString("REGULARIZATION_DATE");
				final String resignationDate = resultSet
						.getString("RESIGNATION_DATE");
				final String departmentName = resultSet
						.getString("DEPARTMENT_NAME");
				final String level = resultSet.getString("LEVEL");
				final String position = resultSet.getString("POSITION");
				final String supervisorName = resultSet
						.getString("SUPERVISOR_NAME");
				final String supervisorEmail = resultSet
						.getString("SUPERVISOR_EMAIL");

				// Store in an object
				final Employee employee = new Employee();
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
				employee.setSupervisorName(supervisorName);
				employee.setSupervisorEmail(supervisorEmail);

				employeesList.add(employee);
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

	public Employee searchByEmployee(String searchString) {

		Connection connection = null;
		final Employee employee = new Employee();
		try {
			connection = dataSource.getConnection();
			final Statement st = connection.createStatement();
			final ResultSet resultSet = st
					.executeQuery("SELECT * FROM employees WHERE EMPLOYEE_ID = '"
							+ searchString + "'");

			final String firstName = resultSet.getString("FIRST_NAME");
			final String middleName = resultSet.getString("MIDDLE_NAME");
			final String lastName = resultSet.getString("LAST_NAME");
			final String biometricsNo = resultSet.getString("BIOMETRICS_ID");
			final String employeeId = resultSet.getString("EMPLOYEE_ID");
			final String employeeEmail = resultSet.getString("EMPLOYEE_EMAIL");
			final String hiredDate = resultSet.getString("HIRE_DATE");
			final String regularizationDate = resultSet
					.getString("REGULARIZATION_DATE");
			final String resignationDate = resultSet
					.getString("RESIGNATION_DATE");
			final String departmentName = resultSet
					.getString("DEPARTMENT_NAME");
			final String level = resultSet.getString("LEVEL");
			final String position = resultSet.getString("POSITION");
			final String supervisorName = resultSet
					.getString("SUPERVISOR_NAME");
			final String supervisorEmail = resultSet
					.getString("SUPERVISOR_EMAIL");

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
			employee.setSupervisorName(supervisorName);
			employee.setSupervisorEmail(supervisorEmail);

			System.out.println(employee.getFirstName());

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

	public void save(Employee employee) {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			// ? are placeholders for the parameters
			final PreparedStatement ps = connection
					.prepareStatement("UPDATE employees SET FIRST_NAME = ?, MIDDLE_NAME = ?, LAST_NAME = ?, BIOMETRICS_ID = ?,"
							+ "EMPLOYEE_ID = ?, EMPLOYEE_EMAIL = ?, HIRE_DATE = ?, DEPARTMENT_NAME = ?, LEVEL = ?, "
							+ "POSITION = ?, SUPERVISOR_NAME = ?, SUPERVISOR_EMAIL = ?  WHERE EMPLOYEE_ID = '"
							+ employee.getEmployeeId() + "'");

			// Convert object to a row
			ps.setString(1, employee.getFirstName());
			ps.setString(2, employee.getMiddleName());
			ps.setString(3, employee.getLastName());
			ps.setString(4, employee.getBiometrics());
			ps.setString(5, employee.getEmployeeId());
			ps.setString(6, employee.getEmployeeEmail());
			ps.setString(7, employee.getHiredDate());
			ps.setString(8, employee.getDepartment());
			ps.setString(9, employee.getLevel());
			ps.setString(10, employee.getPosition());
			ps.setString(11, employee.getSupervisorName());
			ps.setString(12, employee.getSupervisorEmail());
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

}
