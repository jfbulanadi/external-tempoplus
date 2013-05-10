package hk.com.novare.tempoplus.bmnmanager.holiday;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.mysql.jdbc.PreparedStatement;

@Component
public class UploadHolidayDao implements UploadHolidayInterface {

	@Override
	public ResultSet getHoliday(Connection connection) {

		final String retrieveQuery = "Select * From holiday";

		ResultSet resultSet = null;

		try {
	
			
			java.sql.PreparedStatement ps = connection
					.prepareStatement(retrieveQuery);
			resultSet = ps.executeQuery();
			System.out.println("getHolidaySuccess");
		} catch (SQLException e) {
			System.out.println("getHolidayError: " + e.toString());
		}
		return resultSet;
	}

	@Override
	public void insertHoliday(Connection connection, HolidayPojo holidayPojo) {
			try {

			final String holidayDate = holidayPojo.getDate();
			final String holidayDay = holidayPojo.getDay();
			final String holidayCode = holidayPojo.getCode();
			final String holidayName = holidayPojo.getName();
			final String holidayProclamationNumber = holidayPojo.getProcNo();
		

		
			// a query to insert holiday in database
			final String insertQuery = "Insert into holiday Values ('"
					+ holidayDate + "','" + holidayDay + "','" + holidayCode
					+ "','" + holidayName + "','" + holidayProclamationNumber
					+ "')";

			Statement insertStatementQuery;
			System.out.println("Inserting Data");
			insertStatementQuery = connection.createStatement();
			insertStatementQuery.execute(insertQuery);
			System.out.println("Insert Success");
		} catch (SQLException e) {
			System.out.println("Insert Failed");
		}

	}

}
