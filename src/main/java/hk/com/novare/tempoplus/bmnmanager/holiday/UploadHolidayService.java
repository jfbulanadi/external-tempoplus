package hk.com.novare.tempoplus.bmnmanager.holiday;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;

@Service
public class UploadHolidayService {

	UploadHolidayInterface uploadHolidayDao = new UploadHolidayDao();

	private Connection initConnection() {
		Connection SQLConnection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("mySQL Driver Detected");
			try {
				SQLConnection = DriverManager.getConnection(
						"jdbc:mysql://localhost/Holiday", "root", "dreamer");
				System.out.println("Connection has been set up "
						+ SQLConnection.toString());
			} catch (SQLException e) {
				System.out.println("Cant Make Connection");
			}

		} catch (ClassNotFoundException e) {
			System.out.println("No mySQL Driver found");
		}

		return SQLConnection;
	}

	public void insertDB(String fileUrl) throws SQLException {

		final Connection SQLConnection = initConnection();

		final ArrayList<Object> holiday = new ArrayList<Object>();
		final HolidayPojo holidayPojo = new HolidayPojo();
		
		//This will delete clear the database
		final String clearHoliday = "DELETE FROM holiday";
		SQLConnection.prepareStatement(clearHoliday).execute();
	

		try {

			FileInputStream file = new FileInputStream(new File(fileUrl));

			// Get the workbook instance for XLS fileUpload.
			HSSFWorkbook workbook = new HSSFWorkbook(file);

			// Get first sheet from the workbook>
			HSSFSheet sheet = workbook.getSheetAt(0);

			// Iterate through each rows from first sheet
			Iterator<Row> rowIterator = sheet.iterator();

			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				// For each row, iterate through each columns
				Iterator<Cell> cellIterator = row.cellIterator();

				for (int colCount = 0; colCount < 5; colCount++) {

					Cell cell = cellIterator.next();

					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_BOOLEAN:
						System.out.print(cell.getBooleanCellValue() + "|");
						holiday.add(cell.getStringCellValue());

						break;
					case Cell.CELL_TYPE_NUMERIC:

						if (cell.getNumericCellValue() > 999) {

							System.out.print(cell.getDateCellValue() + "|");
							holiday.add((String.valueOf(cell.getDateCellValue())));
						} else {
							System.out.print(cell.getNumericCellValue() + "|");
							holiday.add((String.valueOf(cell
									.getNumericCellValue())));
						}
						break;
					case Cell.CELL_TYPE_STRING:
						System.out.print(cell.getStringCellValue() + "|");
						holiday.add(cell.getStringCellValue());

						break;
					}
				}

				holidayPojo.setDate(holiday.get(0).toString());
				holidayPojo.setDay(holiday.get(1).toString());
				holidayPojo.setCode(holiday.get(2).toString());
				holidayPojo.setName(holiday.get(3).toString());
				holidayPojo.setProcNo(holiday.get(4).toString());

				try {

					uploadHolidayDao.insertHoliday(SQLConnection, holidayPojo);
				} catch (Exception e) {
					System.out.println("Cant Upload, Error: " + e.toString());
				}
				holiday.clear();
				file.close();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

	ArrayList<HolidayPojo> retrieveHolidays() {
		final Connection SQLConnection = initConnection();
		final ResultSet resultSet = uploadHolidayDao.getHoliday(SQLConnection);

		final ArrayList<HolidayPojo> holidayPojo = new ArrayList<HolidayPojo>();
		try {
			while (resultSet.next()) {
				final HolidayPojo tempHolidayPojo = new HolidayPojo();
				tempHolidayPojo.setCode(resultSet.getString(1));
				tempHolidayPojo.setDate(resultSet.getString(2));
				tempHolidayPojo.setDay(resultSet.getString(3));
				tempHolidayPojo.setName(resultSet.getString(4));
				tempHolidayPojo.setProcNo(resultSet.getString(5));

				holidayPojo.add(tempHolidayPojo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Transferring resultSet to ArrayList Error");
		}

		return holidayPojo;
	}

}
