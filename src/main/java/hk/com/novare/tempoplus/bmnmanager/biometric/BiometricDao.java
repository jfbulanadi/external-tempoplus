package hk.com.novare.tempoplus.bmnmanager.biometric;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class BiometricDao {

	@Inject
	DataSource dataSource;

	private Connection connection = null;

	public int[] insertBiometricData(ArrayList<Biometric> list) {
		int[] rows = null;
		try {
			connection = dataSource.getConnection();

			PreparedStatement preparedStatement = connection
					.prepareStatement("INSERT INTO biometrics (biometricId, logDate, logTime, log, log2) values (?, ?, ?, ?, ?)");

			for (Biometric biometric : list) {
				preparedStatement.setInt(1, biometric.getBiometricId());
				preparedStatement.setString(2, biometric.getDate());
				preparedStatement.setString(3, biometric.getTime());
				preparedStatement.setInt(4, biometric.getLog());
				preparedStatement.setInt(5, biometric.getUnknown());
				preparedStatement.addBatch();
			}

			rows = preparedStatement.executeBatch();
			preparedStatement.close();

		} catch (SQLException e) {
			//TODO use LOGS
			System.out.println("Problem with Biometrics");
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

	public ArrayList<Biometric> retrieveAllBiometricData() {

		final ArrayList<Biometric> list = new ArrayList<Biometric>();

		try {
			connection = dataSource.getConnection();

			PreparedStatement preparedStatement = connection
					.prepareStatement("SELECT biometricId, date, time, log, log2 FROM biometrics");

			final ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				final Biometric bio = new Biometric();

				bio.setBiometricId(resultSet.getInt("biometricId"));
				bio.setDate(resultSet.getString("date"));
				bio.setTime(resultSet.getString("time"));
				bio.setLog(resultSet.getInt("log"));
				bio.setUnknown(resultSet.getInt("log2"));

				list.add(bio);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public ArrayList<Integer> retrieveBiometricIdList() {

		final ArrayList<Integer> list = new ArrayList<Integer>();

		try {
			connection = dataSource.getConnection();

			PreparedStatement preparedStatement = connection
					.prepareStatement("SELECT biometricId FROM biometrics");

			final ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				list.add(resultSet.getInt("biometricId"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public ArrayList<Biometric> retrieveTimeInData() {
		ArrayList<Biometric> list = new ArrayList<Biometric>();

		try {
			connection = dataSource.getConnection();

			PreparedStatement preparedStatement = connection
					.prepareStatement("SELECT id, biometricId, logDate, MIN(logTime), log "
							+ "FROM biometrics WHERE log = 0 "
							+ "GROUP BY biometricId, logDate");

			final ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				final Biometric bio = new Biometric();

				bio.setId(resultSet.getInt("id"));
				bio.setBiometricId(resultSet.getInt("biometricId"));
				bio.setDate(resultSet.getString("logDate"));
				bio.setTime(resultSet.getString("MIN(logTime)"));
				bio.setLog(resultSet.getInt("log"));

				list.add(bio);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public ArrayList<Biometric> retrieveTimeOutData() {
		ArrayList<Biometric> list = new ArrayList<Biometric>();

		try {
			connection = dataSource.getConnection();

			PreparedStatement preparedStatement = connection
					.prepareStatement("SELECT id, biometricId, logDate, MAX(logTime), log "
							+ "FROM biometrics WHERE log = 1 "
							+ "GROUP BY biometricId, logDate");

			final ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				final Biometric bio = new Biometric();

				bio.setId(resultSet.getInt("id"));
				bio.setBiometricId(resultSet.getInt("biometricId"));
				bio.setDate(resultSet.getString("logDate"));
				bio.setTime(resultSet.getString("MAX(logTime)"));
				bio.setLog(resultSet.getInt("log"));

				list.add(bio);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public ArrayList<BiometricDetails> retrieveDailyBiometric() {

		ArrayList<BiometricDetails> list = new ArrayList<BiometricDetails>();

		try {
			connection = dataSource.getConnection();

			PreparedStatement preparedStatement = connection
					.prepareStatement("SELECT id, biometricId, logDate, MIN(logTime) as timeIn, "
							+ "(SELECT MAX(logTime) FROM biometrics b2 WHERE log = 1 AND b1.biometricId = b2.biometricId AND b1.logDate = b2.logDate) "
							+ "AS timeOut ,log FROM biometrics b1 "
							+ "WHERE log = 0 "
							+ "GROUP BY biometricId, logDate;");

			final ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				final BiometricDetails bio = new BiometricDetails();

				bio.setBiometricId(resultSet.getInt("biometricId"));
				bio.setDate(resultSet.getString("logDate"));
				bio.setTimeIn(resultSet.getString("timeIn"));
				bio.setTimeOut(resultSet.getString("timeOut"));

				list.add(bio);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;

	}
}
