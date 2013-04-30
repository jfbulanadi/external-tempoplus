package hk.com.novare.tempoplus.bmnmanager.timesheet;

import hk.com.novare.tempoplus.bmnmanager.biometric.DailyBiometric;
import hk.com.novare.tempoplus.bmnmanager.mantis.Mantis;
import hk.com.novare.tempoplus.bmnmanager.nt3.Nt3;
import hk.com.novare.tempoplus.employee.Employee;
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
	DataSource dataSource;

	private Connection connection = null;

	public ArrayList<Timesheet> retrieveTimesheetData() {
		ArrayList<Timesheet> list = new ArrayList<Timesheet>();

		Timesheet timesheet = null;
		TimeLogging timelog;
		Employee employee;
		Nt3 nt3;
		Mantis mantis;
		DailyBiometric dailyBiometric;

		try {
			connection = dataSource.getConnection();

			PreparedStatement preparedStatement = connection
					.prepareStatement("SELECT e.employeeId, e.id, e.biometricId, e.lastname, e.firstname, e.hiredate, e.regularizationdate, "
							+ "t.date, t.timeIn, t.timeOut, t.duration, m.ticketId, m.startDate, m.endDate, m.hours, m.minutes, "
							+ "m.category, m.status, n.startDate, n.endDate, n.duration, n.absenceType, n.absenceStatus, "
							+ "(SELECT MIN(bIn.logTime) FROM biometrics AS bIn WHERE log = 0 AND logDate = t.date AND biometricId = e.biometricId) AS bioTimeIn, "
							+ "(SELECT MAX(bOut.logTime)  FROM biometrics AS bOut WHERE log = 1 AND logDate = t.date AND biometricId = e.biometricId) AS bioTimeOut "
							+ "FROM timelogs AS t JOIN employees AS e ON t.employeeId = e.employeeId "
							+ "LEFT JOIN consolidations c ON t.id = c.timelogId "
							+ "LEFT JOIN mantises m ON m.id = c.mantisId "
							+ "LEFT JOIN nt3s n ON n.id = c.nt3Id ORDER BY e.id");

			final ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				timesheet = new Timesheet();
				employee = new Employee();
				timelog = new TimeLogging();
				nt3 = new Nt3();
				mantis = new Mantis();
				dailyBiometric = new DailyBiometric();


				employee.setId(resultSet.getInt("e.id"));
				employee.setEmployeeId(resultSet.getInt("e.employeeId"));
				employee.setBiometricId(resultSet.getInt("e.biometricId"));
				employee.setLastname(resultSet.getString("e.lastname"));
				employee.setFirstname(resultSet.getString("e.firstname"));
				employee.setHireDate(resultSet.getString("e.hiredate"));
				employee.setRegularizationDate(resultSet
						.getString("e.regularizationdate"));
	
				dailyBiometric.setTimeIn(resultSet.getString("bioTimeIn"));
				dailyBiometric.setTimeOut(resultSet.getString("bioTimeOut"));
				
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

				timesheet.setEmployee(employee);
				timesheet.setTimelog(timelog);
				timesheet.setMantis(mantis);
				timesheet.setNt3(nt3);
				timesheet.setDailyBiometric(dailyBiometric);
				list.add(timesheet);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;

	}

}
