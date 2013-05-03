package hk.com.novare.tempoplus.accountsystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service("hrService")
public class HrService {

	@Autowired
	HrDAO hrDAO;

	public List<HumanResourcePartialInfoDTO> retrieveAllEmployee() {
		List<HumanResource> selectAllEmployee = hrDAO.selectAll();
		List<HumanResourcePartialInfoDTO> employeePartialInfoDTOs = new ArrayList<HumanResourcePartialInfoDTO>();

		for (HumanResource employee : selectAllEmployee) {
			employeePartialInfoDTOs
					.add(constructEmployeePartialInfoDTO(employee));
		}
		return employeePartialInfoDTOs;
	}

	// construct EmployeePartialDTO from employee instance
	private HumanResourcePartialInfoDTO constructEmployeePartialInfoDTO(
			HumanResource employee) {
		HumanResourcePartialInfoDTO employeePartialInfoDTO = new HumanResourcePartialInfoDTO();
		employeePartialInfoDTO.setBiometrics(employee.getBiometrics());
		employeePartialInfoDTO.setDepartment(employee.getDepartment());
		employeePartialInfoDTO.setFirstName(employee.getFirstName());
		employeePartialInfoDTO.setMiddleName(employee.getMiddleName());
		employeePartialInfoDTO.setLastName(employee.getLastName());
		employeePartialInfoDTO.setEmployeeId(employee.getEmployeeId());
		return employeePartialInfoDTO;
	}

	// construct EmployeeFullDTO from employee instance
	private HumanResourceFullInfoDTO constructEmployeeFullInfoDTO(
			HumanResource employee) {
		HumanResourceFullInfoDTO employeeFullInfoDto = new HumanResourceFullInfoDTO();
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
			HumanResourceFullInfoDTO employeeFullInfoDTO) {
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
	public HumanResourceFullInfoDTO searchEmployee(String searchString,
			String category) {
		
		HumanResource employee = null;
		employee = hrDAO.search(searchString, "employees.employeeId");
		return constructEmployeeFullInfoDTO(employee);
	}

	public void saveEmployeeDetail(HumanResourceFullInfoDTO employeeFullInfoDTO) {
		HumanResource humanResource = contructEmployee(employeeFullInfoDTO);
		int departmentId = hrDAO.retrieveDepartmentId(humanResource.getDepartment());
		int positionId = hrDAO.retrievePositionId(humanResource.getPosition());
		humanResource.setDepartmentId(departmentId);
		humanResource.setPositionId(positionId);
		
		hrDAO.updateEmployee(humanResource);

	}
	
	public void createEmployeeDetail(HumanResourceFullInfoDTO employeeFullInfoDTO) {
		HumanResource humanResource = contructEmployee(employeeFullInfoDTO);
		hrDAO.createEmployee(humanResource);
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
	
	public void userDBFileUpload(MultipartFile multipartfile) {
		//convert excel file to array of humanresource2
		TransformFile transformFile = new UserDb();
		// Obj from file upload
		List<HumanResourceDTO> uploadHumanResources = transformFile.toExcel(multipartfile);
		
		//retrieve all employeeId from Database
		List<Integer> employeeIdList = hrDAO.retrieveAllEmployeeId();

		//put the new employeeId
		List<HumanResourceDTO> newhumanResourcesListToBeAddedToDatabase = new ArrayList<HumanResourceDTO>();
		
		//check employeeId for duplicate
		for(int in = 0; in < uploadHumanResources.size(); in++) {
			if(!employeeIdList.contains(uploadHumanResources.get(in).getEmployeeId())) {
				newhumanResourcesListToBeAddedToDatabase.add(uploadHumanResources.get(in));
			}
		}
		
		//save humanresource that has no duplicate in database
		for(HumanResourceDTO humanResource2: newhumanResourcesListToBeAddedToDatabase) {
			System.out.println(humanResource2.getFirstName());
			System.out.println(humanResource2.getEmployeeId());
		}
		
		
	}
	
}
