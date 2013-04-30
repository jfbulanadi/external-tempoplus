package hk.com.novare.tempoplus.bmnmanager.timesheet;

import hk.com.novare.tempoplus.bmnmanager.biometric.BiometricDao;
import hk.com.novare.tempoplus.bmnmanager.biometric.DailyBiometric;
import hk.com.novare.tempoplus.bmnmanager.mantis.Mantis;
import hk.com.novare.tempoplus.bmnmanager.nt3.Nt3;
import hk.com.novare.tempoplus.employee.Employee;
import hk.com.novare.tempoplus.timelogging.TimeLogging;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.inject.Inject;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;

@Service
public class TimesheetService {

	@Inject
	TimesheetDao timesheetDao;
	@Inject
	BiometricDao biometricDao;

	public void createTimesheetSummary() {

		ArrayList<Timesheet> timesheetList = timesheetDao
				.retrieveTimesheetData();

		TimeLogging timelog;
		Employee employee;
		Nt3 nt3;
		Mantis mantis;
		DailyBiometric dailyBiometric;

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Sample sheet");
		int rowCounter = 0;

		for (Timesheet timesheet : timesheetList) {
			employee = timesheet.getEmployee();
			timelog = timesheet.getTimelog();
			nt3 = timesheet.getNt3();
			mantis = timesheet.getMantis();
			dailyBiometric = timesheet.getDailyBiometric();

			Row row = sheet.createRow(rowCounter);

			Cell cell;
			if (rowCounter == 0) {
				
				for (int counter = 0; counter < 61; counter++) {
					cell = row.createCell(counter);
					switch (counter) {
					case 0:
						cell.setCellValue("Employee ID");
						break;
					case 1:
						cell.setCellValue("No");
						break;
					case 2:
						cell.setCellValue("Biometric ID");
						break;
					case 3:
						cell.setCellValue("Name");
						break;
					case 4:
						cell.setCellValue("Department");
						break;
					case 5:
						cell.setCellValue("Level");
						break;
					case 6:
						cell.setCellValue("Hire Date");
						break;
					case 7:
						cell.setCellValue("Regularization Date");
						break;
					case 8:
						cell.setCellValue("Shift Type");
						break;
					case 9:
						cell.setCellValue("Employee's Email");
						break;
					case 10:
						cell.setCellValue("Supervisor's Email");
						break;
					case 11:
						cell.setCellValue("Date");
						break;
					case 12:
						cell.setCellValue("Day");
						break;
					case 13:
						cell.setCellValue("Holiday");
						break;
					case 14:
						cell.setCellValue("Time In");
						break;
					case 15:
						cell.setCellValue("Time Out");
						break;
					case 16:
						cell.setCellValue("Duration");
						break;
					case 17:
						cell.setCellValue("Tardiness");
						break;
					case 18:
						cell.setCellValue("Undertime");
						break;
					case 19:
						cell.setCellValue("HalfDay Undertime");
						break;
					case 20:
						cell.setCellValue("Overtime Ticket No.");
						break;
					case 21:
						cell.setCellValue("Overtime");
						break;
					case 22:
						cell.setCellValue("Extra Hours Ticket No.");
						break;
					case 23:
						cell.setCellValue("Extra Hours");
						break;
					case 24:
						cell.setCellValue("Night Shift Schedule Ticket");
						break;
					case 25:
						cell.setCellValue("Night Shift Schedule");
						break;
					case 26:
						cell.setCellValue("Official Business Ticket");
						break;
					case 27:
						cell.setCellValue("Official Business");
						break;
					case 28:
						cell.setCellValue("Change Schedule Ticket");
						break;
					case 29:
						cell.setCellValue("Change Schedule");
						break;
					case 30:
						cell.setCellValue("Undertime Ticket");
						break;
					case 31:
						cell.setCellValue("Undetime");
						break;
					case 32:
						cell.setCellValue("Offset Ticket");
						break;
					case 33:
						cell.setCellValue("Offset");
						break;
					case 34:
						cell.setCellValue("Time Log Adjustments Ticket");
						break;
					case 35:
						cell.setCellValue("Time Log Adjustments");
						break;
					case 36:
						cell.setCellValue("Other HR Related Issue/Request Ticket");
						break;
					case 37:
						cell.setCellValue("Other HR Related Issue/Request");
						break;
					case 38:
						cell.setCellValue("Sick Leave");
						break;
					case 39:
						cell.setCellValue("Vacation Leave");
						break;
					case 40:
						cell.setCellValue("Emergency Leave");
						break;
					case 41:
						cell.setCellValue("Leave w/o Pay");
						break;
					case 42:
						cell.setCellValue("Solo Parent Leave");
						break;
					case 43:
						cell.setCellValue("Maternity Leave");
						break;
					case 44:
						cell.setCellValue("Paternity");
						break;
					case 45:
						cell.setCellValue("Travel");
						break;
					case 46:
						cell.setCellValue("Training");
						break;
					case 47:
						cell.setCellValue("Woman's Special Leave");
						break;
					case 48:
						cell.setCellValue("Bank Holiday");
						break;
					case 49:
						cell.setCellValue("HR/Admin Remark");
						break;
					case 50:
						cell.setCellValue("Employee Remark");
						break;
					case 51:
						cell.setCellValue("Biometric Time In");
						break;
					case 52:
						cell.setCellValue("Biometric Time Out");
						break;
					case 53:
						cell.setCellValue("Official Business Ticket");
						break;
					case 54:
						cell.setCellValue("Official Business");
						break;
					case 55:
						cell.setCellValue("OB Time In");
						break;
					case 56:
						cell.setCellValue("OB Time Out");
						break;
					case 57:
						cell.setCellValue("Time Log Adjustments Ticket");
						break;
					case 58:
						cell.setCellValue("Time Log Adjustments");
						break;
					case 59:
						cell.setCellValue("Time Log Adjustments Time In");
						break;
					case 60:
						cell.setCellValue("Time Log Adjustments Time Out");
						break;

					}

				}

			} else {

				for (int counter = 0; counter < 61; counter++) {
					cell = row.createCell(counter);
					switch (counter) {
					case 0:
						cell.setCellValue(employee.getEmployeeId());
						break;
					case 1:
						cell.setCellValue(employee.getId());
						break;
					case 2:
						cell.setCellValue(employee.getBiometricId());
						break;
					case 3:
						cell.setCellValue(employee.getLastname() + ", "
								+ employee.getFirstname());
						break;
					case 4:
						cell.setCellValue("Department");
						break;
					case 5:
						cell.setCellValue("Level");
						break;
					case 6:
						cell.setCellValue(employee.getHireDate());
						break;
					case 7:
						cell.setCellValue(employee.getRegularizationDate());
						break;
					case 8:
						cell.setCellValue("Shift");
						break;
					case 9:
						cell.setCellValue(employee.getEmail());
						break;
					case 10:
						cell.setCellValue("Supervisor's Email");
						break;
					case 11:
						cell.setCellValue(timelog.getDate());
						break;
					case 12:
						String dateFormat = null;
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						
						Date date = null;
						try {
							date = sdf.parse(timelog.getDate());
							sdf = new SimpleDateFormat("EEE");
							dateFormat = sdf.format(date);
							cell.setCellValue(dateFormat);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						break;
					case 13:
						cell.setCellValue("Holiday");
						break;
					case 14:
						// TODO
						cell.setCellValue(timelog.getTimeIn());
						break;
					case 15:
						// TODO
						cell.setCellValue(timelog.getTimeOut());
						break;
					case 16:
						cell.setCellValue(timelog.getDuration());
						break;
					case 17:
						// TODO
						// Need shift
						cell.setCellValue("Tardiness");
						break;
					case 18:
						// TODO
						// Need shift
						cell.setCellValue("Undertime");
						break;
					case 19:
						// TODO
						// Need shift
						cell.setCellValue("HalfDay Undertime");
						break;
					case 20:
						if (null != mantis.getCategory()
								&& "Overtime".equals(mantis.getCategory())) {
							cell.setCellValue(mantis.getTicketId());
						}
						break;
					case 21:
						if (null != mantis.getCategory()
								&& "Overtime".equals(mantis.getCategory())) {
							cell.setCellValue(mantis.getDuration());
						}
						break;
					case 22:
						if (null != mantis.getCategory()
								&& "Extra Hours".equals(mantis.getCategory())) {
							cell.setCellValue(mantis.getTicketId());
						}
						break;
					case 23:
						if (null != mantis.getCategory()
								&& "Extra Hours".equals(mantis.getCategory())) {
							cell.setCellValue(mantis.getDuration());
						}
						break;
					case 24:
						if (null != mantis.getCategory()
								&& "Night Shift".equals(mantis.getCategory())) {
							cell.setCellValue(mantis.getTicketId());
						}
						break;
					case 25:
						if (null != mantis.getCategory()
								&& "Night Shift".equals(mantis.getCategory())) {
							cell.setCellValue(mantis.getDuration());
						}
						break;
					case 26:
						if (null != mantis.getCategory()
								&& "Official Business".equals(mantis
										.getCategory())) {
							cell.setCellValue(mantis.getTicketId());
						}
						break;
					case 27:
						if (null != mantis.getCategory()
								&& "Official Business".equals(mantis
										.getCategory())) {
							cell.setCellValue(mantis.getDuration());
						}
						break;
					case 28:
						if (null != mantis.getCategory()
								&& "Change Schedule".equals(mantis
										.getCategory())) {
							cell.setCellValue(mantis.getTicketId());
						}
						break;
					case 29:
						if (null != mantis.getCategory()
								&& "Change Schedule".equals(mantis
										.getCategory())) {
							cell.setCellValue(mantis.getDuration());
						}
						break;
					case 30:
						if (null != mantis.getCategory()
								&& "Undertime".equals(mantis.getCategory())) {
							cell.setCellValue(mantis.getTicketId());
						}
						break;
					case 31:
						if (null != mantis.getCategory()
								&& "Undertime".equals(mantis.getCategory())) {
							cell.setCellValue(mantis.getDuration());
						}
						break;
					case 32:
						if (null != mantis.getCategory()
								&& "Offset".equals(mantis.getCategory())) {
							cell.setCellValue(mantis.getTicketId());
						}
						break;
					case 33:
						if (null != mantis.getCategory()
								&& "Offset".equals(mantis.getCategory())) {
							cell.setCellValue(mantis.getDuration());
						}
						break;
					case 34:
						if (null != mantis.getCategory()
								&& "Time Log Adjustments".equals(mantis
										.getCategory())) {
							cell.setCellValue(mantis.getTicketId());
						}
						break;
					case 35:
						if (null != mantis.getCategory()
								&& "Time Log Adjustments".equals(mantis
										.getCategory())) {
							cell.setCellValue(mantis.getDuration());
						}
						break;
					case 36:
						if (null != mantis.getCategory()
								&& "Other HR Related Issue/Request"
										.equals(mantis.getCategory())) {
							cell.setCellValue(mantis.getTicketId());
						}
						break;
					case 37:
						if (null != mantis.getCategory()
								&& "Other HR Related Issue/Request"
										.equals(mantis.getCategory())) {
							// Unknown Values to insert here
							// cell.setCellValue("Other HR Related Issue/Request");
						}
						break;
					case 38:
						if (null != nt3.getAbsenceType()
								&& "Sick Leave".equals(nt3.getAbsenceType())) {
							cell.setCellValue(nt3.getDuration() + " D "
									+ nt3.getStartDate() + " - "
									+ nt3.getEndDate());
						}
						break;
					case 39:
						if (null != nt3.getAbsenceType()
								&& "Vacation Leave"
										.equals(nt3.getAbsenceType())) {
							cell.setCellValue(nt3.getDuration() + " D "
									+ nt3.getStartDate() + " - "
									+ nt3.getEndDate());
						}
						break;
					case 40:
						if (null != nt3.getAbsenceType()
								&& "Emergency Leave".equals(nt3
										.getAbsenceType())) {
							cell.setCellValue(nt3.getDuration() + " D "
									+ nt3.getStartDate() + " - "
									+ nt3.getEndDate());
						}
						break;
					case 41:
						if (null != nt3.getAbsenceType()
								&& "Leave w/o Pay".equals(nt3.getAbsenceType())) {
							cell.setCellValue(nt3.getDuration() + " D "
									+ nt3.getStartDate() + " - "
									+ nt3.getEndDate());
						}
						break;
					case 42:
						if (null != nt3.getAbsenceType()
								&& "Solo Parent Leave".equals(nt3
										.getAbsenceType())) {
							cell.setCellValue(nt3.getDuration() + " D "
									+ nt3.getStartDate() + " - "
									+ nt3.getEndDate());
						}
						break;
					case 43:
						if (null != nt3.getAbsenceType()
								&& "Maternity Leave".equals(nt3
										.getAbsenceType())) {
							cell.setCellValue(nt3.getDuration() + " D "
									+ nt3.getStartDate() + " - "
									+ nt3.getEndDate());
						}
						break;
					case 44:
						if (null != nt3.getAbsenceType()
								&& "Paternity Leave".equals(nt3
										.getAbsenceType())) {
							cell.setCellValue(nt3.getDuration() + " D "
									+ nt3.getStartDate() + " - "
									+ nt3.getEndDate());
						}
						break;
					case 45:
						if (null != nt3.getAbsenceType()
								&& "Travel".equals(nt3.getAbsenceType())) {
							cell.setCellValue(nt3.getDuration() + " D "
									+ nt3.getStartDate() + " - "
									+ nt3.getEndDate());
						}
						break;
					case 46:
						if (null != nt3.getAbsenceType()
								&& "Training".equals(nt3.getAbsenceType())) {
							cell.setCellValue(nt3.getDuration() + " D "
									+ nt3.getStartDate() + " - "
									+ nt3.getEndDate());
						}
						break;
					case 47:
						if (null != nt3.getAbsenceType()
								&& "Woman's Special Leave".equals(nt3
										.getAbsenceType())) {
							cell.setCellValue(nt3.getDuration() + " D "
									+ nt3.getStartDate() + " - "
									+ nt3.getEndDate());
						}
						break;
					case 48:
						if (null != nt3.getAbsenceType()
								&& "Bank Holiday".equals(nt3.getAbsenceType())) {
							// cell.setCellValue("Bank Holiday");
						}
						break;
					case 49:
						// What's This?
						// cell.setCellValue("HR/Admin Remark");
						break;
					case 50:
						// What's This?
						// cell.setCellValue("Employee Remark");
						break;
					case 51:
						// TODO
						cell.setCellValue(dailyBiometric.getTimeIn());
						break;
					case 52:
						// TODO
						cell.setCellValue(dailyBiometric.getTimeOut());
						break;
					case 53:
						if (null != mantis.getCategory()
								&& "Official Business".equals(mantis
										.getCategory())) {
							cell.setCellValue(mantis.getTicketId());
						}
						break;
					case 54:
						if (null != mantis.getCategory()
								&& "Official Business".equals(mantis
										.getCategory())) {
							cell.setCellValue(mantis.getDuration());
						}
						break;
					case 55:
						if (null != mantis.getCategory()
								&& "Official Business".equals(mantis
										.getCategory())) {
							cell.setCellValue(timelog.getTimeIn());
						}
						break;
					case 56:
						if (null != mantis.getCategory()
								&& "Official Business".equals(mantis
										.getCategory())) {
							cell.setCellValue(timelog.getTimeOut());
						}
						break;
					case 57:
						if (null != mantis.getCategory()
								&& "Time Log Adjustments".equals(mantis
										.getCategory())) {
							cell.setCellValue(mantis.getTicketId());
						}
						break;
					case 58:
						if (null != mantis.getCategory()
								&& "Time Log Adjustments".equals(mantis
										.getCategory())) {
							cell.setCellValue(mantis.getDuration());
						}
						break;
					case 59:
						// TODO
						cell.setCellValue("Time Log Adjustments Time In");
						break;
					case 60:
						// TODO
						cell.setCellValue("Time Log Adjustments Time Out");
						break;

					}

				}

			}
			rowCounter++;
		}

		FileOutputStream outFile;
		try {
			outFile = new FileOutputStream(new File(
					"D:\\ConsolidatedTimesheetSummary.xls"));
			workbook.write(outFile);
			outFile.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}