package hk.com.novare.tempoplus.bmnmanager.mantis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class MantisDao {

	@Inject
	DataSource dataSource;

	private Connection connection = null;
	static final Logger logger = Logger.getLogger(MantisDao.class);

	public int[] insertMantisData(ArrayList<Mantis> list) {

		int[] rows = null;
		
		try {
			connection = dataSource.getConnection();
			
			PreparedStatement preparedStatement = connection
					.prepareStatement("INSERT INTO mantises (ticketId, startDate, endDate, hours, minutes, " +
							"employeeId, timeIn, timeOut, dateSubmitted, category, status, timestamp) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())");

			for (Mantis mantis : list) {
				preparedStatement.setInt(1, mantis.getTicketId());
				preparedStatement.setString(2, mantis.getStartDate());
				preparedStatement.setString(3, mantis.getEndDate());
				preparedStatement.setInt(4, mantis.getHours());
				preparedStatement.setInt(5, mantis.getMinutes());
				preparedStatement.setInt(6, mantis.getEmployeeId());
				preparedStatement.setString(7, mantis.getStartTime());
				preparedStatement.setString(8, mantis.getEndTime());
				preparedStatement.setString(9, mantis.getDateSubmitted());
				preparedStatement.setString(10, mantis.getCategory());
				preparedStatement.setString(11, mantis.getStatus());
				preparedStatement.addBatch();
			}

			rows = preparedStatement.executeBatch();
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
		
		return rows;
	}

	public ArrayList<Mantis> retriveMantisData() {
		ArrayList<Mantis> list = new ArrayList<Mantis>();

		try {
			connection = dataSource.getConnection();

			PreparedStatement preparedStatement = connection
					.prepareStatement("SELECT ticketId, startDate, endDate, hours, minutes, employeeId, "
							+ " timeIn, timeOut, dateSubmitted, category, status FROM mantises");

			ResultSet resultSet = preparedStatement.executeQuery();
			Mantis mantis;
			while (resultSet.next()) {
				mantis = new Mantis();

				mantis.setTicketId(resultSet.getInt(1));
				mantis.setStartDate(resultSet.getDate(2).toString());
				mantis.setEndDate(resultSet.getDate(3).toString());
				mantis.setHours(resultSet.getInt(4));
				mantis.setMinutes(resultSet.getInt(5));
				mantis.setEmployeeId(resultSet.getInt(6));
				mantis.setStartTime(resultSet.getString(7));
				mantis.setEndTime(resultSet.getString(8));
				mantis.setDateSubmitted(resultSet.getDate(9).toString());
				mantis.setCategory(resultSet.getString(10));
				mantis.setStatus(resultSet.getString(11));

				list.add(mantis);
			}

		} catch (SQLException e) {

		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {

				}
			}
		}

		return list;
	}

}
