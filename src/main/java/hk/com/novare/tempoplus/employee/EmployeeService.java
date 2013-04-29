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

		ArrayList<Employee> list = new ArrayList<Employee>();
		Employee emp = null;

		Sheet sheet = excelWorkbookUtility.getExcelWorkbook(file);
		int ctr=0;
		for (Row row : sheet) {
			++ctr;
			emp = new Employee();
			for (int col = 1; col < row.getLastCellNum(); col++) {

				Cell cell = row.getCell(col, Row.CREATE_NULL_AS_BLANK);

				switch (col) {
				case 1: {
					cell.setCellType(Cell.CELL_TYPE_NUMERIC);
					emp.setBiometricId((int) cell.getNumericCellValue());
					break;
				}
				case 2: {
					String[] name = cell.getStringCellValue().split(",");				
					emp.setFirstname(name[1]);
					emp.setLastname(name[0]);
					emp.setMiddlename(name[2]);
					break;
				}
				case 3: {
					emp.setDepartmentId(1);
					break;
				}
				case 5: {
					emp.setShiftId(1);
					break;
				}
				case 6: {
					emp.setEmployeeId((int) cell.getNumericCellValue());
					break;
				}
				case 7: {
					emp.setPositionId(1);
					break;
				}
				case 8: {
					emp.setLevel(1);
					break;
				}
				case 9: {
					String dateFormat = null;
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					dateFormat = sdf.format(cell.getDateCellValue());
					emp.setHireDate(dateFormat);
					break;
				}
				case 10: {
					String dateFormat = null;
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					dateFormat = sdf.format(cell.getDateCellValue());
					emp.setRegularizationDate(dateFormat);
					break;
				}
				case 11: {
					System.out.println(cell.getCellType());
					if(cell.getCellType() != Cell.CELL_TYPE_BLANK && cell.getCellType() != Cell.CELL_TYPE_ERROR){
						System.out.println("In " + cell.getCellType());
					String dateFormat = null;
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					dateFormat = sdf.format(cell.getDateCellValue());
					emp.setResignationDate(dateFormat);
					}
					break;
				}
				case 12: {
					emp.setEmail(cell.getStringCellValue());
					break;
				}
				case 13: {
					emp.setSupervisorId(1);
					break;
				}
				}
			}
			list.add(emp);

		}

		employeeDao.addEmployeeData(list);

	}
}
