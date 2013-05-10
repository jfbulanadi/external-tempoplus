package hk.com.novare.tempoplus.bmnmanager.timesheet;

import hk.com.novare.tempoplus.bmnmanager.biometric.BiometricDetails;
import hk.com.novare.tempoplus.bmnmanager.mantis.Mantis;
import hk.com.novare.tempoplus.bmnmanager.nt3.Nt3;
import hk.com.novare.tempoplus.employee.EmployeeDetails;
import hk.com.novare.tempoplus.timelogging.TimeLogging;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

@Repository
public class TimesheetDao {

	@Inject
	private DataSource dataSource;

	private Connection connection = null;
	private ResultSet resultSet = null;

	public ArrayList<Timesheet> retrieveTimesheetData() {
		ArrayList<Timesheet> list = new ArrayList<Timesheet>();

		Timesheet timesheet = null;
		TimeLogging timelog;
		EmployeeDetails employeeDetails;
		Nt3 nt3;
		Mantis mantis;
		BiometricDetails biometricDetails;

		try {
			connection = dataSource.getConnection();

			PreparedStatement preparedStatement = connection
					.prepareStatement("SELECT e.employeeId, e.id, e.biometricId, CONCAT_WS(', ' , e.lastname, e.firstname) AS fullName, e.hiredate, e.regularizationdate, "
							+ "(SELECT CONCAT_WS(', ' , lastname, firstname) AS fullName FROM employees WHERE employeeId = e.supervisorId) AS supervisor, "
							+ "t.date, t.timeIn, t.timeOut, t.duration, m.ticketId, m.startDate, m.endDate, m.hours, m.minutes, "
							+ "m.category, m.status, n.startDate, n.endDate, n.duration, n.absenceType, n.absenceStatus, "
							+ "(SELECT MIN(bIn.logTime) FROM biometrics AS bIn WHERE log = 0 AND logDate = t.date AND biometricId = e.biometricId) AS bioTimeIn, "
							+ "(SELECT MAX(bOut.logTime)  FROM biometrics AS bOut WHERE log = 1 AND logDate = t.date AND biometricId = e.biometricId) AS bioTimeOut "
							+ "FROM timelogs AS t JOIN employees AS e ON t.employeeId = e.employeeId "
							+ "LEFT JOIN consolidations c ON t.id = c.timelogId "
							+ "LEFT JOIN mantises m ON m.id = c.mantisId "
							+ "LEFT JOIN nt3s n ON n.id = c.nt3Id ORDER BY e.id");

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				timesheet = new Timesheet();
				employeeDetails = new EmployeeDetails();
				timelog = new TimeLogging();
				nt3 = new Nt3();
				mantis = new Mantis();
				biometricDetails = new BiometricDetails();

				// employeeDetails.setId(resultSet.getInt("e.id"));
				employeeDetails.setEmployeeId(resultSet.getInt("e.employeeId"));
				employeeDetails.setBiometricId(resultSet
						.getInt("e.biometricId"));
				employeeDetails.setFullName(resultSet.getString("fullName"));
				employeeDetails.setHireDate(resultSet.getString("e.hiredate"));
				employeeDetails.setRegularizationDate(resultSet
						.getString("e.regularizationdate"));
				employeeDetails
						.setSupervisor(resultSet.getString("supervisor"));

				biometricDetails.setTimeIn(resultSet.getString("bioTimeIn"));
				biometricDetails.setTimeOut(resultSet.getString("bioTimeOut"));

				timelog.setDate(resultSet.getString("t.date"));
				timelog.setTimeIn(resultSet.getString("t.timeIn"));
				timelog.setTimeOut(resultSet.getString("t.timeout"));
				timelog.setDuration(resultSet.getString("t.duration"));
				mantis.setTicketId(resultSet.getInt("m.ticketId"));
				mantis.setStartDate(resultSet.getString("m.startDate"));
				mantis.setEndDate(resultSet.getString("m.endDate"));
				mantis.setHours(resultSet.getInt("m.hours"));
				mantis.setMinutes(resultSet.getInt("m.minutes"));
				mantis.setCategory(resultSet.getString("m.category"));
				mantis.setStatus(resultSet.getString("m.status"));
				nt3.setStartDate(resultSet.getString("n.startDate"));
				nt3.setEndDate(resultSet.getString("n.endDate"));
				nt3.setDuration(resultSet.getInt("n.duration"));
				nt3.setAbsenceType(resultSet.getString("n.absenceType"));
				nt3.setAbsenceStatus(resultSet.getString("n.absenceStatus"));

				timesheet.setEmployeeDetails(employeeDetails);
				timesheet.setTimelog(timelog);
				timesheet.setMantis(mantis);
				timesheet.setNt3(nt3);
				timesheet.setBiometricDetails(biometricDetails);
				list.add(timesheet);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			// TODO CLOSE
		}

		return list;

	}

	public int createTimesheet(String name) {
		int rows = 0;
		try {
			connection = dataSource.getConnection();

			PreparedStatement preparedStatement = connection
					.prepareStatement("INSERT INTO timesheets (description) VALUES(?)");

			preparedStatement.setString(1, name);

			rows = preparedStatement.executeUpdate();

		} catch (SQLException e) {

		}

		return rows;
	}

	public ArrayList<TimesheetList> retrieveTimesheetList() {

		ArrayList<TimesheetList> list = new ArrayList<TimesheetList>();
		TimesheetList timesheetList = null;
		try {
			connection = dataSource.getConnection();

			PreparedStatement preparedStatement = connection
					.prepareStatement("SELECT name FROM timesheets");

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				timesheetList = new TimesheetList();

				timesheetList.setName(resultSet.getString("name"));

				list.add(timesheetList);
			}
			preparedStatement.close();
			resultSet.close();
		} catch (SQLException e) {

		}

		return list;
	}

	public void updateTimeLogTimesheetRecord(String description) {

		try {
			connection = dataSource.getConnection();

			PreparedStatement preparedStatement = connection
					.prepareStatement("UPDATE timesheets SET timelogId = 1 WHERE description = ?");

			preparedStatement.setString(1, description);
			preparedStatement.executeUpdate();
			preparedStatement.close();

		} catch (SQLException e) {

		}
	}

	public void updateMantisTimesheetRecord(String description) {

		try {
			connection = dataSource.getConnection();

			PreparedStatement preparedStatement = connection
					.prepareStatement("UPDATE timesheets SET mantisId = 1 WHERE description = ?");

			preparedStatement.setString(1, description);
			preparedStatement.executeUpdate();
			preparedStatement.close();

		} catch (SQLException e) {

		}
	}

	public void updateNt3TimesheetRecord(String description) {

		try {
			connection = dataSource.getConnection();

			PreparedStatement preparedStatement = connection
					.prepareStatement("UPDATE timesheets SET nt3Id = 1 WHERE description = ?");

			preparedStatement.setString(1, description);
			preparedStatement.executeUpdate();
			preparedStatement.close();

		} catch (SQLException e) {

		}
	}

}
