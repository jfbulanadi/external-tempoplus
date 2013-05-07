package hk.com.novare.tempoplus.bmnmanager.nt3;

import hk.com.novare.tempoplus.bmnmanager.mantis.Mantis;
import hk.com.novare.tempoplus.utilities.ExcelWorkbookUtility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.inject.Inject;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class Nt3Service {

	@Inject
	Nt3Dao nt3Dao;
	@Inject
	ExcelWorkbookUtility excelWorkbookUtility;

	public ArrayList<Nt3> readData(CommonsMultipartFile[] file) {

		ArrayList<Nt3> list = new ArrayList<Nt3>();
		Nt3 nt3 = null;

		Sheet sheet = excelWorkbookUtility.getExcelWorkbook(file);

		outerLoop: for (Row row : sheet) {

			nt3 = new Nt3();
			for (int col = 0; col < row.getLastCellNum(); col++) {

				Cell cell = row.getCell(col, Row.CREATE_NULL_AS_BLANK);

				switch (col) {
				case 0: {
					try {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						nt3.setEmployeeId(Integer.parseInt(cell
								.getStringCellValue()));
					} catch (Exception e) {
						continue outerLoop;
					}
					break;
				}
				case 2: {
					if (DateUtil.isValidExcelDate(cell.getNumericCellValue())) {
						String dateFormat = null;
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd");
						dateFormat = sdf.format(cell.getDateCellValue());
						nt3.setStartDate(dateFormat);
						break;
					} else {
						continue outerLoop;
					}
				}
				case 3: {
					if (DateUtil.isValidExcelDate(cell.getNumericCellValue())) {
						String dateFormat = null;
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd");
						dateFormat = sdf.format(cell.getDateCellValue());
						nt3.setEndDate(dateFormat);
						break;
					} else {
						continue outerLoop;
					}
				}
				case 4: {
					if (cell.getCellType() == 0) {
						nt3.setDuration((float) cell.getNumericCellValue());
						break;
					} else {
						continue outerLoop;
					}
				}
				case 5: {
					if (cell.getCellType() == 1) {
						nt3.setAbsenceType(cell.getStringCellValue());
						break;
					} else {
						continue outerLoop;
					}
				}
				case 6: {
					try {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						nt3.setDescription(cell.getStringCellValue());
					} catch (Exception e) {
						continue outerLoop;
					}
					break;
				}
				case 7: {
					if (cell.getCellType() == 1 || cell.getCellType() == 3) {
						nt3.setAbsenceStatus(cell.getStringCellValue());
						break;
					} else {
						continue outerLoop;
					}
				}
				}
			}
			list.add(nt3);

		}

		return list;
	}

	public int insertNt3Data(ArrayList<Nt3> list) {

		return nt3Dao.insertNt3Data(list).length;
	}
}
