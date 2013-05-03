package hk.com.novare.tempoplus.accountsystem;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.multipart.MultipartFile;

public class UserDb implements TransformFile {

	Sheet sheet;

	@Override
	public List<EmployeeFullInfoDTO> toExcel(MultipartFile multipartFile) {

		try {
			final InputStream is = multipartFile.getInputStream();
			final Workbook wb = WorkbookFactory.create(is);
			sheet = wb.getSheetAt(0);

		} catch (IOException ioException) {
			ioException.printStackTrace();
		} catch (InvalidFormatException invalidFormatException) {
			invalidFormatException.printStackTrace();
		}

		List<EmployeeFullInfoDTO> personList = new ArrayList<EmployeeFullInfoDTO>();
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			final EmployeeFullInfoDTO p = new EmployeeFullInfoDTO();
			Row row = sheet.getRow(i);
			for (int x = 0; x < row.getLastCellNum(); x++) {
				Cell cell = row.getCell(x);

				switch (x) {
				case 1:
					//BIOMETRIC ID
					System.out.print(cell.getNumericCellValue());
					/*
					 * if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					 * p.setBiometrics((int) cell.getNumericCellValue());
					 * 
					 * }
					 */
					break;
				case 2:
					//NAME
					System.out.println(cell.getStringCellValue());
					/*
					 * if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
					 * //p.setFirstName(cell.getStringCellValue());
					 * System.out.println(cell.getStringCellValue()); }
					 */
					break;
				// System.out.print(cell.getStringCellValue() + " \n");
				case 3:
					//Department
					/*
					 * if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
					 * //p.setDepartment(cell.getStringCellValue()); }
					 */
					break;
				case 5:
					//Shifting
					/*
					 * if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
					 * //p.setShift(cell.getStringCellValue()); }
					 */
					System.out.println(cell.getStringCellValue());
					break;
				case 6:
					//Employee Id
					System.out.println(cell.getStringCellValue());
					/*
					 * if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					 * p.setEmployeeId((int) cell.getNumericCellValue()); }
					 */
					break;
				case 7:
					//Position
					System.out.println(cell.getStringCellValue());
					break;

				case 8:
					//Level
					System.out.println(cell.getStringCellValue());
					break;
				case 9:
					// Hire Date
					System.out.println(cell.getStringCellValue());
					break;

				case 10:
					// Regularization Date
					System.out.println(cell.getStringCellValue());
					break;
				case 11:
					// Resignation Date
					System.out.println(cell.getStringCellValue());
					break;
				case 12:
					// Employee's Email
					System.out.println(cell.getStringCellValue());
					break;
				case 13:
					// Supervisor in NT3
					System.out.println(cell.getStringCellValue());
					break;
				case 14:
					// Supervisor's Email in NT3
					System.out.println(cell.getStringCellValue());
					break;
				case 15:
					// Location Assignment
					System.out.println(cell.getStringCellValue());
					break;
				}
				personList.add(p);
			}
		}
		return personList;
	}
}
