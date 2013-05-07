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
	
	
	// construct Employee from employeeFullDTO instance
	private HumanResourceDTO contructEmployeeFromEdited(HumanResourceFullInfoDTO employeeFullInfoDTO) {
		
		HumanResourceDTO employee = new HumanResourceDTO();
		employee.setBiometricId(Integer.parseInt(employeeFullInfoDTO.getBiometrics()));
		employee.setFirstName(employeeFullInfoDTO.getFirstName());
		employee.setMiddleName(employeeFullInfoDTO.getMiddleName());
		employee.setLastName(employeeFullInfoDTO.getLastName());
		employee.setDepartment(employeeFullInfoDTO.getDepartment());
		employee.setEmployeeId(Integer.parseInt(employeeFullInfoDTO.getEmployeeId()));
		employee.setEmployeeEmail(employeeFullInfoDTO.getEmployeeEmail());
		employee.setHiredDate(employeeFullInfoDTO.getHiredDate());
		employee.setRegularizationDate(employeeFullInfoDTO
				.getRegularizationDate());
		employee.setResignationDate(employeeFullInfoDTO.getResignationDate());
		employee.setLevel(Integer.parseInt(employeeFullInfoDTO.getLevel()));
		employee.setPosition(employeeFullInfoDTO.getPosition());
		employee.setSupervisorName(employeeFullInfoDTO.getSupervisorName());
		employee.setSupervisorEmail(employeeFullInfoDTO.getSupervisorEmail());
		employee.setDepartmentId(employeeFullInfoDTO.getDepartmentId());
		employee.setPositionId(employeeFullInfoDTO.getPositionId());
		employee.setShiftId(employeeFullInfoDTO.getShiftId());
		employee.setLocAssign(employeeFullInfoDTO.getLocAssign());
		return employee;
	}

	// search by category that will return DTO;
	public HumanResourceDTO searchEmployee(String searchString,
			String category) {
		
		HumanResourceDTO employee = null;
		SupervisorDTO supervisorDTO = null;
		
		employee = hrDAO.search(searchString, "employees.employeeId");

		supervisorDTO = hrDAO.retrieveSupervisorNameAndEmail(employee.getSupervisorId());
		employee.setSupervisorEmail(supervisorDTO.getEmail());
		employee.setSupervisorName(supervisorDTO.getName());
		
		return employee;
	}
	
	/**
	 * r
	 * @param employeeFullInfoDTO
	 * save the edited employee, 
	 * convert department name to departmentId
	 * convert position name to positionId
	 * convert shift to shiftId
	 * convert supervisor name to supervisorId
	 */
	public void saveEditedEmployeeDetail(HumanResourceFullInfoDTO employeeFullInfoDTO) {
		
		HumanResourceDTO humanResourceDTO = contructEmployeeFromEdited(employeeFullInfoDTO);
		int departmentId = hrDAO.retrieveDepartmentId(humanResourceDTO.getDepartment());
		int positionId = hrDAO.retrievePositionId(humanResourceDTO.getPosition());
		int shiftId = hrDAO.retrieveShiftId(humanResourceDTO.getShift());
		int supervisorId = hrDAO.retrieveSupervisorEmployeeId(humanResourceDTO.getSupervisorEmail());
		humanResourceDTO.setSupervisorId(supervisorId);
		humanResourceDTO.setDepartmentId(departmentId);
		humanResourceDTO.setPositionId(positionId);
		humanResourceDTO.setShiftId(shiftId);
		
		if(humanResourceDTO.getHiredDate().length() == 0) {
			String hiredDate = null;
			humanResourceDTO.setHiredDate(hiredDate);
		}
		if(humanResourceDTO.getRegularizationDate().length() == 0) {
			String RegularizationDate = null;
			humanResourceDTO.setRegularizationDate(RegularizationDate);
		}
		if(humanResourceDTO.getResignationDate().length() == 0) {
			String ResignationDate = null;
			humanResourceDTO.setResignationDate(ResignationDate);

		}
		hrDAO.updateEmployee(humanResourceDTO);

	}
	
	
	public void createEmployeeDetail(HumanResourceFullInfoDTO employeeDTO) {
		
		HumanResourceDTO humanResourceDTO = contructEmployeeFromEdited(employeeDTO);
		
		//int shiftId = hrDAO.retrieveShiftId(humanResourceDTO.getShift());
		//humanResourceDTO.setShiftId(shiftId);
		
		if(humanResourceDTO.getHiredDate().length() == 0) {
			String hiredDate = null;
			humanResourceDTO.setHiredDate(hiredDate);
		} 
		
		if(humanResourceDTO.getLevel() > 3) {
			humanResourceDTO.setIsSupervisor("true");
		}
		
		hrDAO.createEmployee(humanResourceDTO);
		
		int employeeId = Integer.parseInt(employeeDTO.getEmployeeId());
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
		
		//PHASE I - save humanresource that has no duplicate in database 
		for(HumanResourceDTO humanResource2: newhumanResourcesListToBeAddedToDatabase) {
			int departmentId = hrDAO.retrieveDepartmentId(humanResource2.getDepartment());
			int positionId = hrDAO.retrievePositionId(humanResource2.getPosition());
			int shiftId = hrDAO.retrieveShiftId(humanResource2.getShift());
			
			humanResource2.setDepartmentId(departmentId);
			humanResource2.setPositionId(positionId);
			humanResource2.setShiftId(shiftId);
			
			if(humanResource2.getLevel() >= 3) {
				humanResource2.setIsSupervisor("true");
			}
			hrDAO.createEmployeeFromUpload(humanResource2);
			hrDAO.createAccount(humanResource2.getEmployeeId());
		}
		
		//PHASE II - assigned designated supervisor using supervisor email
		for(HumanResourceDTO humanResourceDTO: newhumanResourcesListToBeAddedToDatabase) {
			int supervisorId = hrDAO.retrieveSupervisorEmployeeId(humanResourceDTO.getSupervisorEmail());
			humanResourceDTO.setSupervisorId(supervisorId);
			
			//update supervisorId
			hrDAO.updateSupervisorId(humanResourceDTO);
		}
		
	}
	
	public Map<Integer, String> retrieveShifts() {
		return hrDAO.retrieveShifts();
	}
	
}
