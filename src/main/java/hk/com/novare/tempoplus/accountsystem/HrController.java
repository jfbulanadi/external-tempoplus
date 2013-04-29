package hk.com.novare.tempoplus.accountsystem;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("hr")
public class HrController {

	@Autowired
	@Qualifier("hrService")
	HrService hrService;
	
	
	
	@RequestMapping(value = "/employeemanager")
	public String subPage(ModelMap modelMap) {
		
		modelMap.addAttribute("employeeList", hrService.retieveAllEmployee());
		return "ViewHr";
	}
	
	@RequestMapping(value = "/selectAllJSON")
	public @ResponseBody
	List<EmployeePartialInfoDTO> subPage() {
		return hrService.retieveAllEmployee();

	}
	
	@RequestMapping(value = "/searchEmployee")
	public @ResponseBody
	EmployeeFullInfoDTO searchEmployee( @RequestParam(value = "employeeId") String searchString) {
		return hrService.searchByCategory(searchString, "employeeId").get(0);
	}
	
	@RequestMapping(value = "/saveEditEmployee", method = RequestMethod.POST)
	public String saveEditEmployee(ModelMap modelMap, 
			@RequestParam(value = "firstName") String firstName,
			@RequestParam(value = "middleName") String middleName,
			@RequestParam(value = "lastName") String lastName,
			@RequestParam(value = "employeeId") String employeeId,
			@RequestParam(value = "biometrics") String biometrics,
			@RequestParam(value = "department") String department,
			@RequestParam(value = "shift") String shift,
			@RequestParam(value = "level") String level,
			@RequestParam(value = "hireDate") String hireDate,
			@RequestParam(value = "regularizationDate") String regularizationDate,
			@RequestParam(value = "resignationDate") String resignationDate,
			@RequestParam(value = "supervisorName") String supervisorName,
			@RequestParam(value = "supervisorEmail") String supervisorEmail,
			@RequestParam(value = "employeeEmail") String employeeEmail) {
		
		
		EmployeeFullInfoDTO employeeFullInfoDTO = new EmployeeFullInfoDTO();
		
		employeeFullInfoDTO.setFirstName(firstName);
		employeeFullInfoDTO.setMiddleName(middleName);
		employeeFullInfoDTO.setLastName(lastName);
		employeeFullInfoDTO.setBiometrics(biometrics);
		employeeFullInfoDTO.setEmployeeId(employeeId);
		employeeFullInfoDTO.setDepartment(department);
		employeeFullInfoDTO.setShift(shift);
		//employeeFullInfoDTO.setLevel(level);
		employeeFullInfoDTO.setHiredDate(hireDate);
		employeeFullInfoDTO.setRegularizationDate(regularizationDate);
		employeeFullInfoDTO.setResignationDate(resignationDate);
		employeeFullInfoDTO.setSupervisorName(supervisorName);
		employeeFullInfoDTO.setSupervisorEmail(supervisorEmail);
		employeeFullInfoDTO.setEmployeeEmail(employeeEmail);		
		
		hrService.saveEmployeeDetail(employeeFullInfoDTO);
		
		return "ViewHr";
	}
	
	

	@RequestMapping(value = "/createEmployee")
	public String employeeForm(ModelMap model,
			@RequestParam(value = "firstName") String firstName,
			@RequestParam(value = "middleName") String middleName,
			@RequestParam(value = "lastName") String lastName,
			@RequestParam(value = "employeeId") String employeeId,
			@RequestParam(value = "biometrics") String biometrics,
			@RequestParam(value = "department") String department,
			@RequestParam(value = "shift") String shift,
			@RequestParam(value = "level") String level,
			@RequestParam(value = "hireDate") String hireDate,
			@RequestParam(value = "supervisorName") String supervisorName,
			@RequestParam(value = "supervisorEmail") String supervisorEmail,
			@RequestParam(value = "employeeEmail") String employeeEmail) {
		
		EmployeeFullInfoDTO employeeFullInfoDTO = new EmployeeFullInfoDTO();
		employeeFullInfoDTO.setFirstName(firstName);
		employeeFullInfoDTO.setMiddleName(middleName);
		employeeFullInfoDTO.setLastName(lastName);
		employeeFullInfoDTO.setEmployeeId(employeeId);
		employeeFullInfoDTO.setBiometrics(biometrics);
		employeeFullInfoDTO.setDepartment(department);
		employeeFullInfoDTO.setShift(shift);
		employeeFullInfoDTO.setLevel(level);
		employeeFullInfoDTO.setHiredDate(hireDate);
		employeeFullInfoDTO.setSupervisorEmail(supervisorEmail);
		employeeFullInfoDTO.setSupervisorName(supervisorName);
		employeeFullInfoDTO.setEmployeeEmail(employeeEmail);
		
		hrService.saveEmployeeDetail(employeeFullInfoDTO);
		
		return "ViewHr";
	}
	
	

	@RequestMapping(value = "/addemployeeAjax")
	public String addEmployeeAjax(ModelMap model) {
		return "addemployee";
	}
}
