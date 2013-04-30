package hk.com.novare.tempoplus.accountsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("hrService")
public class HrService {

	@Autowired
	HrDAO hrDAO;

	public List<EmployeePartialInfoDTO> retieveAllEmployee() {
		List<HumanResource> selectAllEmployee = hrDAO.selectAll();
		List<EmployeePartialInfoDTO> employeePartialInfoDTOs = new ArrayList<EmployeePartialInfoDTO>();

		for (HumanResource employee : selectAllEmployee) {
			employeePartialInfoDTOs
					.add(constructEmployeePartialInfoDTO(employee));
		}
		return employeePartialInfoDTOs;
	}

	// construct EmployeePartialDTO from employee instance
	private EmployeePartialInfoDTO constructEmployeePartialInfoDTO(
			HumanResource employee) {
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
			HumanResource employee) {
		EmployeeFullInfoDTO employeeFullInfoDto = new EmployeeFullInfoDTO();
		employeeFullInfoDto.setBiometrics(employee.getBiometrics());
		employeeFullInfoDto.setDepartment(employee.getDepartment());
		employeeFullInfoDto.setDepartmentId(employee.getDepartmentId());
		employeeFullInfoDto.setPositionId(employee.getPositionId());
		employeeFullInfoDto.setFirstName(employee.getFirstName());
		employeeFullInfoDto.setMiddleName(employee.getMiddleName());
		employeeFullInfoDto.setLastName(employee.getLastName());
		employeeFullInfoDto.setEmployeeId(employee.getEmployeeId());
		employeeFullInfoDto.setEmployeeEmail(employee.getEmployeeEmail());
		employeeFullInfoDto.setHiredDate(employee.getHiredDate());
		employeeFullInfoDto.setRegularizationDate(employee
				.getRegularizationDate());
		employeeFullInfoDto.setResignationDate(employee.getResignationDate());
		employeeFullInfoDto.setLevel(employee.getLevel());
		employeeFullInfoDto.setPosition(employee.getPosition());
		employeeFullInfoDto.setSupervisorEmail(employee.getSupervisorEmail());
		employeeFullInfoDto.setSupervisorName(employee.getSupervisorName());

		return employeeFullInfoDto;
	}

	// construct Employee from employeeFullDTO instance
	private HumanResource contructEmployee(
			EmployeeFullInfoDTO employeeFullInfoDTO) {
		HumanResource employee = new HumanResource();
		employee.setBiometrics(employeeFullInfoDTO.getBiometrics());
		employee.setFirstName(employeeFullInfoDTO.getFirstName());
		employee.setMiddleName(employeeFullInfoDTO.getMiddleName());
		employee.setLastName(employeeFullInfoDTO.getLastName());
		employee.setDepartment(employeeFullInfoDTO.getDepartment());
		employee.setEmployeeId(employeeFullInfoDTO.getEmployeeId());
		employee.setEmployeeEmail(employeeFullInfoDTO.getEmployeeEmail());
		employee.setHiredDate(employeeFullInfoDTO.getHiredDate());
		employee.setRegularizationDate(employeeFullInfoDTO
				.getRegularizationDate());
		employee.setResignationDate(employeeFullInfoDTO.getResignationDate());
		employee.setLevel(employeeFullInfoDTO.getLevel());
		employee.setPosition(employeeFullInfoDTO.getPosition());
		employee.setSupervisorName(employeeFullInfoDTO.getSupervisorName());
		employee.setSupervisorEmail(employeeFullInfoDTO.getSupervisorEmail());
		employee.setDepartmentId(employeeFullInfoDTO.getDepartmentId());
		employee.setPositionId(employeeFullInfoDTO.getPositionId());
		
		return employee;
	}

	// search by category that will return DTO;
	public EmployeeFullInfoDTO searchEmployee(String searchString,
			String category) {
		
		HumanResource employee = null;
		employee = hrDAO.search(searchString, "employees.employeeId");
		return constructEmployeeFullInfoDTO(employee);
	}

	public void saveEmployeeDetail(EmployeeFullInfoDTO employeeFullInfoDTO) {
		HumanResource humanResource = contructEmployee(employeeFullInfoDTO);
		int departmentId = hrDAO.retrieveDepartmentId(humanResource.getDepartment());
		int positionId = hrDAO.retrievePositionId(humanResource.getPosition());
		humanResource.setDepartmentId(departmentId);
		humanResource.setPositionId(positionId);
		
		hrDAO.save(humanResource);

	}
	
	public void createEmployeeDetail(EmployeeFullInfoDTO employeeFullInfoDTO) {
		HumanResource humanResource = contructEmployee(employeeFullInfoDTO);
		System.out.println("service"+humanResource.getFirstName());
		System.out.println("service"+humanResource.getDepartmentId());
		System.out.println("service"+humanResource.getPositionId());
		hrDAO.addEmployee(humanResource);
		int employeeId = Integer.parseInt(humanResource.getEmployeeId());
		hrDAO.createAccount(employeeId);
	}

	public Map<Integer, String> retrieveDepartment() {
		return hrDAO.retrieveDepartment();
	}
	
	public Map<Integer, String> retievePosition(int departmentId) {
		return hrDAO.retrievePosition(departmentId);
	}
	
	public Map<Integer, String> retieveSupervisor(int departmentId) {
		return hrDAO.retrieveSupervisor(departmentId);
	}
	
	
}
