package hk.com.novare.tempoplus.bmnmanager.holiday;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

public interface UploadHolidayInterface {

	ResultSet getHoliday(Connection connection);

	void insertHoliday(Connection connection, HolidayPojo holidayPojo);

	
}
