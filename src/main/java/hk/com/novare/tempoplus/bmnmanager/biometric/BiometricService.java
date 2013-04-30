package hk.com.novare.tempoplus.bmnmanager.biometric;

import hk.com.novare.tempoplus.timelogging.TimeLoggingDao;
import hk.com.novare.tempoplus.utilities.ExcelWorkbookUtility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.inject.Inject;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class BiometricService {
	
	@Inject	BiometricDao biometricDao;
	@Inject TimeLoggingDao timelogDAO;
	@Inject	ExcelWorkbookUtility excelWorkbookUtility;

	public void readData(CommonsMultipartFile[] file) {

		ArrayList<Biometric> list = new ArrayList<Biometric>();
		Biometric bio = null;
		
		Sheet sheet = excelWorkbookUtility.getExcelWorkbook(file);
		
		for (Row row : sheet) {

			bio = new Biometric();
			for (int col = 0; col < row.getLastCellNum(); col++) {

				Cell cell = row.getCell(col, Row.CREATE_NULL_AS_BLANK);

				switch (col) {
				case 0: {
					bio.setBiometricId((int) cell.getNumericCellValue());
					break;
				}
				case 1: {
					String dateFormat = null;
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					dateFormat = sdf.format(cell.getDateCellValue());
					bio.setDate(dateFormat);
					break;
				}
				case 2: {
					String timeFormat = null;
					SimpleDateFormat sdf = new SimpleDateFormat("k:mm");
					timeFormat = sdf.format(cell.getDateCellValue());
					bio.setTime(timeFormat);
					break;
				}
				case 3: {
					bio.setLog((int) cell.getNumericCellValue());
					break;
				}
				case 4: {
					bio.setUnknown((int) cell.getNumericCellValue());
					break;
				}
				}
			}
			list.add(bio);

		}

		biometricDao.addBiometricData(list);

	}
	
	public ArrayList<DailyBiometric> retrieveBiometricRecordsPerDay() {
		
		ArrayList<DailyBiometric> list = new ArrayList<DailyBiometric>();
		
		
		
		return list;
	}
	
	public void updateTimelog() {		
		timelogDAO.updateTimeLoggingDataPhase1(biometricDao.retrieveTimeInData());
		timelogDAO.updateTimeLoggingDataPhase2(biometricDao.retrieveTimeOutData());		
		
	}

}
