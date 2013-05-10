package hk.com.novare.tempoplus.employee;

import hk.com.novare.tempoplus.utilities.ExcelWorkbookUtility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.inject.Inject;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class EmployeeService {

	@Inject
	EmployeeDao employeeDao;
	@Inject
	ExcelWorkbookUtility excelWorkbookUtility;

	public void readData(CommonsMultipartFile[] file) {

		ArrayList<EmployeeDetails> list = new ArrayList<EmployeeDetails>();
		EmployeeDetails employeeDetails = null;

		Sheet sheet = excelWorkbookUtility.getExcelWorkbook(file);
		for (Row row : sheet) {
			employeeDetails = new EmployeeDetails();
			for (int col = 1; col < row.getLastCellNum(); col++) {

				Cell cell = row.getCell(col, Row.CREATE_NULL_AS_BLANK);

				switch (col) {
				case 1: {
					cell.setCellType(Cell.CELL_TYPE_NUMERIC);
					employeeDetails.setBiometricId((int) cell
							.getNumericCellValue());
					break;
				}
				case 2: {
					employeeDetails.setFullName(cell.getStringCellValue());
					break;
				}
				case 3: {
					employeeDetails.setDepartment(cell.getStringCellValue());
					break;
				}
				case 5: {
					employeeDetails.setShift(cell.getStringCellValue());
					break;
				}
				case 6: {
					employeeDetails.setEmployeeId((int) cell
							.getNumericCellValue());
					break;
				}
				case 7: {
					employeeDetails.setPosition(cell.getStringCellValue());
					break;
				}
				case 8: {
					employeeDetails.setLevel((int) cell.getNumericCellValue());
					break;
				}
				case 9: {
					String dateFormat = null;
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					dateFormat = sdf.format(cell.getDateCellValue());
					employeeDetails.setHireDate(dateFormat);
					break;
				}
				case 10: {
					String dateFormat = null;
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					dateFormat = sdf.format(cell.getDateCellValue());
					employeeDetails.setRegularizationDate(dateFormat);
					break;
				}
				case 11: {
					if (cell.getCellType() != Cell.CELL_TYPE_BLANK
							&& cell.getCellType() != Cell.CELL_TYPE_ERROR) {
						String dateFormat = null;
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd");
						dateFormat = sdf.format(cell.getDateCellValue());
						employeeDetails.setResignationDate(dateFormat);
					}
					break;
				}
				case 12: {
					employeeDetails.setEmail(cell.getStringCellValue());
					break;
				}
				case 14: {
					employeeDetails.setSupervisorEmail(cell
							.getStringCellValue());
					break;
				}
				}
			}
			list.add(employeeDetails);

		}

		employeeDao.insertEmployeeData(list);

	}
}
