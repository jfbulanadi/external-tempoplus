package hk.com.novare.tempoplus.accountsystem;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.multipart.MultipartFile;

public class UserDb implements TransformFile {
	
	final Logger logger = Logger.getLogger(UserDb.class);
	Sheet sheet;

	@Override
	public List<HumanResourceDTO> toExcel(MultipartFile multipartFile) {
		List<HumanResourceDTO> humanResourcefromUpload = new ArrayList<HumanResourceDTO>();
		HumanResourceDTO employee = null;

		try {
			final InputStream is = multipartFile.getInputStream();
			final Workbook wb = WorkbookFactory.create(is);
			sheet = wb.getSheetAt(0);
			
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				employee = new HumanResourceDTO();
				for (int col = 0; col < row.getLastCellNum(); col++) {
					
					Cell cell = row.getCell(col, Row.CREATE_NULL_AS_BLANK);
					
					switch (col) {
					case 1:
						int biometrics = 0;
						// BIOMETRIC ID\
						if (cell.getNumericCellValue() == 0) {
							biometrics = 0;
						} else {
							biometrics = (int) cell.getNumericCellValue();
							
							employee.setBiometricId(biometrics);
							
						}
						
						break;
					case 2:
						// NAME
						String firstName = null;
						String middleName = null;
						String lastName = null;

						if (cell.getStringCellValue() == null) {

						} else {
							String[] name = cell.getStringCellValue().split(",");
							try {
								if (name[0] != null) {
									lastName = name[0];
								}
								if (name[1] != null) {
									firstName = name[1];
								}
								if (name[2] != null) {
									middleName = name[2];
								}

							} catch (ArrayIndexOutOfBoundsException e) {
								// do nothing
							}
							employee.setFirstName(firstName);
							employee.setMiddleName(middleName);
							employee.setLastName(lastName);
						}
						break;
					case 3:
						// Department
						String department;
						if (cell.getStringCellValue() == null) {
							department = null;
						} else {
							department = cell.getStringCellValue();
						}
						employee.setDepartment(department);
						break;
					case 5:
						// Shifting

						String shift;
						if (cell.getStringCellValue() == null) {
							shift = null;
						} else {
							shift = cell.getStringCellValue();
						}
						employee.setShift(shift);
						break;
					case 6:
						// Employee Id
						int employeeId;
						if (cell.getNumericCellValue() == 0) {
							employeeId = 0;
						} else {
							employeeId = (int) cell.getNumericCellValue();
						}

						employee.setEmployeeId(employeeId);
						break;
					case 7:
						// Position
						String position;
						if (cell.getStringCellValue() == null) {
							position = null;
						} else {
							position = cell.getStringCellValue();
						}

						employee.setPosition(position);
						break;

					case 8:
						// Level
						int level;
						if (cell.getNumericCellValue() == 0) {
							level = 0;
						} else {
							level = (int) cell.getNumericCellValue();
						}

						employee.setLevel(level);
						break;
					case 9:
						// Hire Date
						String hireDate;
						if (cell.getDateCellValue() == null) {
							hireDate = null;
						} else {
							SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
									"yyyy-MM-dd");
							hireDate = simpleDateFormat.format(cell
									.getDateCellValue());
						}
						employee.setHiredDate(hireDate);

						break;

					case 10:
						// Regularization Date
						String regularizationDate;
						if (cell.getDateCellValue() == null) {
							regularizationDate = null;
						} else {
							SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(
									"yyyy-MM-dd");
							regularizationDate = simpleDateFormat2.format(cell
									.getDateCellValue());
						}
						employee.setRegularizationDate(regularizationDate);

						break;
					case 11:
						// Resignation Date
						String resignationDate;
						if (cell.getDateCellValue() == null) {
							resignationDate = null;
						} else {
							SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat(
									"yyyy-MM-dd");
							resignationDate = simpleDateFormat3.format(cell
									.getDateCellValue());
						}
						employee.setResignationDate(resignationDate);
						break;
					case 12:
						// Employee's Email
						String email;
						if (cell.getStringCellValue() == null) {
							email = null;
						} else {
							email = cell.getStringCellValue();
						}
						employee.setEmployeeEmail(email);
						break;
					case 13:
						// Supervisor in NT3
						String supervisor;
						if (cell.getStringCellValue() == null) {
							supervisor = null;
						} else {
							supervisor = cell.getStringCellValue();
						}
						employee.setSupervisorName(supervisor);
						
					case 14:
						//supervisor email
						String supervisoremail;
						if (cell.getStringCellValue() == null) {
							supervisoremail = null;
						} else {
							supervisoremail = cell.getStringCellValue();
						}
						employee.setSupervisorEmail(supervisoremail);

						break;
					case 15:
						// Location Assignment
						String location;
						if (cell.getStringCellValue() == null) {
							location = null;
						} else {
							location = cell.getStringCellValue();
						}
						employee.setLocAssign(location);
						break;
					}
					

				}
				humanResourcefromUpload.add(employee);
			}
			
		} catch (IOException ioException) {
			logger.info(ioException.getMessage());
			
		} catch (InvalidFormatException invalidFormatException) {
			logger.info(invalidFormatException.getMessage());
		} catch (IllegalArgumentException illegalArgumentException) {
			logger.info(illegalArgumentException.getMessage());
		}

		return humanResourcefromUpload;
	}
}
