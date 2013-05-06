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
	@Inject ExcelWorkbookUtility excelWorkbookUtility;
	
	public ArrayList<Mantis> readData(CommonsMultipartFile[] file) {

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
					sdf = new SimpleDateFormat("h:mm");
					dateFormat = sdf.format(cell.getDateCellValue());
					mantis.setStartTime(dateFormat);
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

		return list;
	}
	
	public int insertMantisData(ArrayList<Mantis> list) {
	
			return mantisDao.insertMantisData(list).length;
	}
}
