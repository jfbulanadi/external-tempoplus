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

	public void insertEmployeeData(ArrayList<EmployeeDetails> list) {

		try {
			connection = dataSource.getConnection();

			PreparedStatement preparedStatement = connection
					.prepareStatement("INSERT INTO employees (employeeId, lastname, firstname, middlename, email, "
							+ "hireDate, regularizationDate, resignationDate, positionId, biometricId, shiftId, departmentId, level, supervisorId) "
							+ "values (?, ?, ?, ?, ?, ?, ?, ?, (SELECT id FROM positions WHERE description = ?), ?,"
							+ "(SELECT id FROM shifts WHERE description = ?), (SELECT id FROM departments WHERE name = ?), ?, ?)");

			for (EmployeeDetails employeeDetails : list) {

				String[] employeeName = employeeDetails.getFullName()
						.split(",");

				preparedStatement.setInt(1, employeeDetails.getEmployeeId());
				preparedStatement.setString(2, employeeName[0]);
				preparedStatement.setString(3, employeeName[1]);
				preparedStatement.setString(4, employeeName[2]);
				preparedStatement.setString(5, employeeDetails.getEmail());
				preparedStatement.setString(6, employeeDetails.getHireDate());
				preparedStatement.setString(7,
						employeeDetails.getRegularizationDate());
				preparedStatement.setString(8,
						employeeDetails.getResignationDate());
				preparedStatement.setString(9, employeeDetails.getPosition());
				// preparedStatement.setString(10,
				// employeeDetails.getSupervisorEmail());
				preparedStatement.setInt(10, employeeDetails.getBiometricId());
				preparedStatement.setString(11, employeeDetails.getShift());
				preparedStatement
						.setString(12, employeeDetails.getDepartment());
				preparedStatement.setInt(13, employeeDetails.getLevel());
				preparedStatement.setInt(14, 0);
				preparedStatement.addBatch();
			}

			preparedStatement.executeBatch();
			
			
			preparedStatement = connection
					.prepareStatement("UPDATE employees AS e1, (SELECT employeeId FROM employees WHERE email = ?) " +
							"AS e2 SET e1.supervisorId = e2.employeeId WHERE e1.employeeId = ?");
			
			for (EmployeeDetails employeeDetails : list) {							
				preparedStatement.setString(1, employeeDetails.getSupervisorEmail());
				preparedStatement.setInt(2, employeeDetails.getEmployeeId());
				preparedStatement.addBatch();
			}
			
			preparedStatement.executeBatch();
			preparedStatement.close();

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

	}

}
