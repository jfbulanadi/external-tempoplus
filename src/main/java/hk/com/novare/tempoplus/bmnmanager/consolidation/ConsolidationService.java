package hk.com.novare.tempoplus.bmnmanager.consolidation;

import hk.com.novare.tempoplus.bmnmanager.biometric.BiometricDao;
import hk.com.novare.tempoplus.bmnmanager.timesheet.Timesheet;
import hk.com.novare.tempoplus.employee.Employee;
import hk.com.novare.tempoplus.employee.EmployeeDao;
import hk.com.novare.tempoplus.timelogging.TimeLoggingDao;
import hk.com.novare.tempoplus.utilities.TimeFormatConverter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.inject.Inject;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ConsolidationService {

	@Inject ConsolidationDao consolidationDao;
	@Inject EmployeeDao employeeDao;
	@Inject BiometricDao biometricDao;
	@Inject TimeLoggingDao timelogDao;
	@Inject TimeFormatConverter timeFormatConverter;
	// public boolean isReadyForConsolidation(int id) {
	//
	// Consolidation consolidation =
	// consolidationDao.isReadyForConsolidation(id);
	// if(consolidation.getBiometricId() != 0 && consolidation.getMantisId() !=
	// 0 && consolidation.)
	//
	// }

	public boolean createConsolidatedTimesheet(String name, String periodStart,
			String periodEnd) {
		return consolidationDao.createCondolidatedTimesheet(name, periodStart,
				periodEnd);
	}

	public ArrayList<ConsolidationDTO> viewConsolidation() {
		return consolidationDao.viewConsolidation();
		
	}

	public Boolean updateConsolidations(String employeeId,
			String timeIn, String timeOut, String date)  {
		 consolidationDao.updateConsolidations(employeeId, timeIn, timeOut, date);
		 return true;
	}

	/*
	 * Jeffrey's Methods
	 */
	
	public void consolidateTimesheet() {
		
		
		timelogDao.updateTimeLoggingDataPhase1(timeFormatConverter.convertToDateTime(biometricDao.retrieveTimeInData()));
		timelogDao.updateTimeLoggingDataPhase2(timeFormatConverter.convertToDateTime(biometricDao.retrieveTimeOutData()));
		
		consolidationDao.consolidateTimeSheetTimeLog();
		consolidationDao.consolidateTimeSheetMantis(timelogDao.retrieveTimeLogs());
		consolidationDao.consolidateTimeSheetNt3(timelogDao.retrieveTimeLogs());
	}

}
