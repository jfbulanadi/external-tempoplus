package hk.com.novare.tempoplus.employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

@Repository
public class EmployeeDao {
	
	@Inject
	DataSource dataSource;
	
	private Connection connection = null;

	public ArrayList<Integer> retrieveEmployeeIds() {
		
		ArrayList<Integer> idList = new ArrayList<Integer>();
		
		try {
			connection = dataSource.getConnection();

			PreparedStatement preparedStatement = connection
					.prepareStatement("SELECT employeeId FROM employees");

			final ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				idList.add(resultSet.getInt("employeeId"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idList;
		
	}
	
	public void addEmployeeData(ArrayList<Employee> list) {
		
		try {
			connection = dataSource.getConnection();

			PreparedStatement preparedStatement = connection
					.prepareStatement("INSERT INTO employees (employeeId, lastname, firstname, middlename, email, " +
							"hireDate, regularizationDate, resignationDate, positionId, supervisorId, biometricId) " +
							"values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			for (Employee employee : list) {
				preparedStatement.setInt(1, employee.getEmployeeId());
				preparedStatement.setString(2, employee.getLastname());
				preparedStatement.setString(3, employee.getFirstname());
				preparedStatement.setString(4, employee.getMiddlename());
				preparedStatement.setString(5, employee.getEmail());
				preparedStatement.setString(6, employee.getHireDate());
				preparedStatement.setString(7, employee.getRegularizationDate());
				preparedStatement.setString(8, employee.getResignationDate());
				preparedStatement.setInt(9, employee.getPositionId());
				preparedStatement.setInt(10, employee.getSupervisorId());
				preparedStatement.setInt(11, employee.getBiometricId());
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

}
