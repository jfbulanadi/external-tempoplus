package hk.com.novare.tempoplus.bmnmanager.nt3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.inject.Inject;
import javax.sql.DataSource;

public class Nt3Dao {

	@Inject	DataSource dataSource;

	private Connection connection = null;

	public void addNt3Data(ArrayList<Nt3> list) {

		try {
			connection = dataSource.getConnection();

			PreparedStatement preparedStatement = connection
					.prepareStatement("INSERT INTO nt3s (employeeId, startDate, endDate, duration, "
							+ "absenceType, description, absenceStatus, timestamp) "
							+ "values (?, ?, ?, ?, ?, ?, ?, NOW())");
			
			for (Nt3 nt3 : list) {
				preparedStatement.setInt(1, nt3.getEmployeeId());
				preparedStatement.setString(2, nt3.getStartDate());
				preparedStatement.setString(3, nt3.getEndDate());
				preparedStatement.setFloat(4, nt3.getDuration());
				preparedStatement.setString(5, nt3.getAbsenceType());
				preparedStatement.setString(6, nt3.getDescription());
				preparedStatement.setString(7, nt3.getAbsenceStatus());
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
