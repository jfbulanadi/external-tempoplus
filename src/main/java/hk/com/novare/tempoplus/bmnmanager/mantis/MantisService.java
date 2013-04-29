package hk.com.novare.tempoplus.bmnmanager.mantis;

import hk.com.novare.tempoplus.utilities.ExcelWorkbookUtility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.inject.Inject;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class MantisService {
	
	@Inject	MantisDao mantisDao;
//	@Inject OvertimeDao overtimeDao;
//	@Inject TimelogAdjustmentDao timelogAdjustmentDao;
//	@Inject ChangeScheduleDao changeScheduleDao;
//	@Inject UndertimeDao undertimeDao;
//	@Inject OfficialBusinessDao officialBusinessDao;
	@Inject ExcelWorkbookUtility excelWorkbookUtility;
	
	public void readData(CommonsMultipartFile[] file) {

		ArrayList<Mantis> list = new ArrayList<Mantis>();
		Mantis mantis = null;
		
		Sheet sheet = excelWorkbookUtility.getExcelWorkbook(file);
		for (Row row : sheet) {
			
			mantis = new Mantis();
			for (int col = 0; col < row.getLastCellNum(); col++) {

				Cell cell = row.getCell(col, Row.CREATE_NULL_AS_BLANK);

				switch (col) {
				case 0: {
					mantis.setTicketId((int) cell.getNumericCellValue());
					break;
				}
				case 1: {
					String dateFormat = null;
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					dateFormat = sdf.format(cell.getDateCellValue());
					mantis.setStartDate(dateFormat);
					//Set time format
					System.out.println("TimeFormat");
					sdf = new SimpleDateFormat("h:mm");
					dateFormat = sdf.format(cell.getDateCellValue());
					mantis.setStartTime(dateFormat);
					System.out.println(dateFormat);
					break;
				}
				case 2: {
					String dateFormat = null;
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					dateFormat = sdf.format(cell.getDateCellValue());
					mantis.setEndDate(dateFormat);
					//Set time format
					sdf = new SimpleDateFormat("h:mm");
					dateFormat = sdf.format(cell.getDateCellValue());
					mantis.setEndTime(dateFormat);
					break;
				}
				case 3: {
					mantis.setHours((int) cell.getNumericCellValue());
					break;
				}
				case 4: {
					mantis.setMinutes((int) cell.getNumericCellValue());
					break;
				}
				case 5: {
					mantis.setEmployeeId((int) cell.getNumericCellValue());
					break;
				}
				case 8: {
					String dateFormat = null;
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					dateFormat = sdf.format(cell.getDateCellValue());					
					mantis.setDateSubmitted(dateFormat);
					break;
				}
				case 9: {
					mantis.setCategory(cell.getStringCellValue());
					break;
				}
				case 10: {
					mantis.setStatus(cell.getStringCellValue());
					break;
				}
				}
			}
			list.add(mantis);

		}

		mantisDao.addMantisData(list);

	}
	
//	public void splitMantisData() {
//		
//		ArrayList<Mantis> list = mantisDao.retriveMantisData();
//		
//		for(Mantis mantis:list) {
//			if("Change Schedule".equals(mantis.getCategory())) {
//				changeScheduleDao.addChangeScheduleData(mantis);
//			} else if ("Extra Hours".equals(mantis.getCategory())) {
//				extraHoursDao.addExtraHoursData(mantis);
//			} else if ("Time Log Adjustments".equals(mantis.getCategory())) {
//				timelogAdjustmentDao.addTimelogAdjustmentData(mantis);
//			} else if ("Official Business".equals(mantis.getCategory())) {
//				officialBusinessDao.addOfficialBusinessData(mantis);
//			} else if ("Undertime".equals(mantis.getCategory())) {
//				undertimeDao.addUndertimeData(mantis);
//			} else if ("Overtime".equals(mantis.getCategory())) {
//				overtimeDao.addOvertimeData(mantis);
//			} else if ("Offset".equals(mantis.getCategory())) {
//				offsetDao.addOffsetData(mantis);
//			} else if ("Night Shift Schedule".equals(mantis.getCategory())) {
//				nightShiftScheduleDao.addNightShiftScheduleData(mantis);
//			}
//			
//		}
//
//	}
}
