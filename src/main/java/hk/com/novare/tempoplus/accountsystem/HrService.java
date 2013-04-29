package hk.com.novare.tempoplus.accountsystem;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("hrService")
public class HrService {

	@Autowired
	HrDAO hrDAO;

	public List<EmployeePartialInfoDTO> retieveAllEmployee() {
		List<Employee> selectAllEmployee = hrDAO.selectAll();
		List<EmployeePartialInfoDTO> employeePartialInfoDTOs = new ArrayList<EmployeePartialInfoDTO>();

		for (Employee employee : selectAllEmployee) {
			employeePartialInfoDTOs
					.add(constructEmployeePartialInfoDTO(employee));
		}
		return employeePartialInfoDTOs;
	}

	// construct EmployeePartialDTO from employee instance
	private EmployeePartialInfoDTO constructEmployeePartialInfoDTO(
			Employee employee) {
		EmployeePartialInfoDTO employeePartialInfoDTO = new EmployeePartialInfoDTO();
		employeePartialInfoDTO.setBiometrics(employee.getBiometrics());
		employeePartialInfoDTO.setDepartment(employee.getDepartment());
		employeePartialInfoDTO.setFirstName(employee.getFirstName());
		employeePartialInfoDTO.setMiddleName(employee.getMiddleName());
		employeePartialInfoDTO.setLastName(employee.getLastName());
		employeePartialInfoDTO.setEmployeeId(employee.getEmployeeId());
		return employeePartialInfoDTO;
	}
	
	// construct EmployeeFullDTO from employee instance
	private EmployeeFullInfoDTO constructEmployeeFullInfoDTO(
			Employee employee) {
		EmployeeFullInfoDTO employeeFullInfoDto = new EmployeeFullInfoDTO();
		employeeFullInfoDto.setBiometrics(employee.getBiometrics());
		employeeFullInfoDto.setDepartment(employee.getDepartment());
		employeeFullInfoDto.setFirstName(employee.getFirstName());
		employeeFullInfoDto.setMiddleName(employee.getMiddleName());
		employeeFullInfoDto.setLastName(employee.getLastName());
		employeeFullInfoDto.setEmployeeId(employee.getEmployeeId());
		employeeFullInfoDto.setEmployeeEmail(employee.getEmployeeEmail());
		employeeFullInfoDto.setHiredDate(employee.getHiredDate());
		employeeFullInfoDto.setRegularizationDate(employee.getRegularizationDate());
		employeeFullInfoDto.setResignationDate(employee.getResignationDate());
		employeeFullInfoDto.setLevel(employee.getLevel());
		employeeFullInfoDto.setPosition(employee.getPosition());
		employeeFullInfoDto.setSupervisorEmail(employee.getSupervisorEmail());
		employeeFullInfoDto.setSupervisorName(employee.getSupervisorName());
		
		return employeeFullInfoDto;
	}
	
	// construct Employee from employeeFullDTO instance
	private Employee contructEmployee(EmployeeFullInfoDTO employeeFullInfoDTO) {
		Employee employee = new Employee();
		employee.setBiometrics(employeeFullInfoDTO.getBiometrics());
		employee.setFirstName(employeeFullInfoDTO.getFirstName());
		employee.setMiddleName(employeeFullInfoDTO.getMiddleName());
		employee.setLastName(employeeFullInfoDTO.getLastName());
		employee.setDepartment(employeeFullInfoDTO.getDepartment());
		employee.setEmployeeId(employeeFullInfoDTO.getEmployeeId());
		employee.setEmployeeEmail(employeeFullInfoDTO.getEmployeeEmail());
		employee.setHiredDate(employeeFullInfoDTO.getHiredDate());
		employee.setRegularizationDate(employeeFullInfoDTO.getRegularizationDate());
		employee.setResignationDate(employeeFullInfoDTO.getResignationDate());
		employee.setLevel(employeeFullInfoDTO.getLevel());
		employee.setPosition(employeeFullInfoDTO.getPosition());
		employee.setSupervisorName(employeeFullInfoDTO.getSupervisorName());
		employee.setSupervisorEmail(employeeFullInfoDTO.getSupervisorEmail());
		
		
		System.out.println(employee.getLastName());
		
		return employee;
	}

	

	// search by category that will return DTO;
	public List<EmployeeFullInfoDTO> searchByCategory(String searchString,
			String category) {

		List<EmployeeFullInfoDTO> employeeFullInfoDto = new ArrayList<EmployeeFullInfoDTO>();
		List<Employee> employeeList = null;

		if (category.equals("name")) {
			employeeList = hrDAO.search(searchString, "FIRST_NAME");
		} else if (category.equals("department")) {
			employeeList = hrDAO.search(searchString, "DEPARTMENT_NAME");
		} else if (category.equals("employeeId")) {
			employeeList = hrDAO.search(searchString, "EMPLOYEE_ID");
		} else if (category.equals("all")) {
			employeeList = hrDAO.selectAll();
		}

		for (Employee employee : employeeList) {
			employeeFullInfoDto
					.add(constructEmployeeFullInfoDTO(employee));
		}

		return employeeFullInfoDto;
	}
	
	public void saveEmployeeDetail(EmployeeFullInfoDTO employeeFullInfoDTO) {
		hrDAO.save(contructEmployee(employeeFullInfoDTO));
		
	}

	public EmployeePartialInfoDTO searchByEmployeeId(String searchString) {
		
		Employee employee = hrDAO.searchByEmployee(searchString);
		return constructEmployeePartialInfoDTO(employee);
	}

}
