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
	static final Logger logger = Logger.getLogger(BiometricDao.class);
	
	public void addBiometricData(ArrayList<Biometric> list) {

		try {
			connection = dataSource.getConnection();

			PreparedStatement preparedStatement = connection
					.prepareStatement("INSERT INTO biometrics (biometricId, logDate, logTime, log, log2, timestamp) values (?, ?, ?, ?, ?, NOW())");

			for (Biometric biometric : list) {
				preparedStatement.setInt(1, biometric.getBiometricsId());
				preparedStatement.setString(2, biometric.getDate());
				preparedStatement.setString(3, biometric.getTime());
				preparedStatement.setInt(4, biometric.getLog());
				preparedStatement.setInt(5, biometric.getUnknown());
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

	public ArrayList<Biometric> retrieveAllBiometricData() {

		final ArrayList<Biometric> list = new ArrayList<Biometric>();

		try {
			connection = dataSource.getConnection();

			PreparedStatement preparedStatement = connection
					.prepareStatement("SELECT biometricId, date, time, log, log2 FROM biometrics");

			final ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				final Biometric bio = new Biometric();

				bio.setBiometricsId(resultSet.getInt("biometricId"));
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

	
//	public void update(int id) {
//		
////		SELECT biometricId, logDate, MIN(logTime), log FROM BIOMETRICS WHERE BIOMETRICID = 79  AND log = 0 GROUP BY logDate;
//		
//		try {
//			connection = dataSource.getConnection();
//
//			PreparedStatement preparedStatement = connection
//					.prepareStatement("SELECT biometricId, date, time, log, log2 FROM biometrics");
//
//			final ResultSet resultSet = preparedStatement.executeQuery();
//
//			while (resultSet.next()) {
//				final Biometric bio = new Biometric();
//
//				bio.setBiometricsId(resultSet.getInt("biometricId"));
//				bio.setDate(resultSet.getString("date"));
//				bio.setTime(resultSet.getString("time"));
//				bio.setLog(resultSet.getInt("log"));
//				bio.setUnknown(resultSet.getInt("log2"));
//
//				list.add(bio);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		
//	}
	
}
