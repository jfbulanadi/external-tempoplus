package hk.com.novare.tempoplus.accountsystem;

import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("hr")
public class HrController {

	@Autowired
	@Qualifier("hrService")
	HrService hrService;
	
	@Autowired
	SingleFileUploadForm singleFileUploadForm;
	

	@RequestMapping(value = "/employeemanager")
	public String subPage(ModelMap modelMap) {		
		modelMap.addAttribute("employeeList", hrService.retrieveAllEmployee());
		return "ViewHr";
	}

	@RequestMapping(value = "/selectAllJSON")
	public @ResponseBody
	List<HumanResourcePartialInfoDTO> subPage() {
		return hrService.retrieveAllEmployee();

	}
	
	@RequestMapping(value = "/searchEmployee")
	public @ResponseBody
	HumanResourceDTO searchEmployee( @RequestParam(value = "employeeId") String searchString) {
		return hrService.searchEmployee(searchString, "employeeId");
	}
	
	@RequestMapping(value = "/saveEditEmployee", method = RequestMethod.POST)
	public String saveEditEmployee(ModelMap modelMap, 
			@RequestParam(value = "firstName") String firstName,
			@RequestParam(value = "middleName") String middleName,
			@RequestParam(value = "lastName") String lastName,
			@RequestParam(value = "employeeId") String employeeId,
			@RequestParam(value = "biometrics") String biometrics,
			@RequestParam(value = "departmentId") int departmentId,
			@RequestParam(value = "positionId") int positionId,
			@RequestParam(value = "shiftId") int shiftId,
			@RequestParam(value = "level") String level,
			@RequestParam(value = "hireDate") String hireDate,
			@RequestParam(value = "regularizationDate") String regularizationDate,
			@RequestParam(value = "resignationDate") String resignationDate,
			@RequestParam(value = "supervisorId") String supervisorId,
			@RequestParam(value = "employeeEmail") String employeeEmail,
			@RequestParam(value = "locAssign") String locAssign){
		
		
		HumanResourceFullInfoDTO employeeFullInfoDTO = new HumanResourceFullInfoDTO();
		
		employeeFullInfoDTO.setFirstName(firstName);
		employeeFullInfoDTO.setMiddleName(middleName);
		employeeFullInfoDTO.setLastName(lastName);
		employeeFullInfoDTO.setBiometrics(biometrics);
		employeeFullInfoDTO.setEmployeeId(employeeId);
		employeeFullInfoDTO.setDepartmentId(departmentId);
		employeeFullInfoDTO.setPositionId(positionId);
		employeeFullInfoDTO.setShiftId(shiftId);
		employeeFullInfoDTO.setLevel(level);
		employeeFullInfoDTO.setHiredDate(hireDate);
		employeeFullInfoDTO.setRegularizationDate(regularizationDate);
		employeeFullInfoDTO.setResignationDate(resignationDate);
		employeeFullInfoDTO.setSupervisorName(supervisorId);
		employeeFullInfoDTO.setEmployeeEmail(employeeEmail);
		employeeFullInfoDTO.setLocAssign(locAssign);
		
		hrService.saveEditedEmployeeDetail(employeeFullInfoDTO);
		
		
		return "ViewHr";
	}
	
	

	@RequestMapping(value = "/createEmployee")
	public String employeeForm(ModelMap model,
			@RequestParam(value = "firstName") String firstName,
			@RequestParam(value = "middleName") String middleName,
			@RequestParam(value = "lastName") String lastName,
			@RequestParam(value = "employeeId") String employeeId,
			@RequestParam(value = "biometrics") String biometrics,
			@RequestParam(value = "department") int departmentId,
			@RequestParam(value = "position") int positionId,
			@RequestParam(value = "shift") int shift,
			@RequestParam(value = "level") String level,
			@RequestParam(value = "hireDate") String hireDate,
			@RequestParam(value = "supervisorName") String supervisorName,
			@RequestParam(value = "employeeEmail") String employeeEmail) {
		
		HumanResourceFullInfoDTO employeeFullInfoDTO = new HumanResourceFullInfoDTO();
		
		employeeFullInfoDTO.setFirstName(firstName);
		employeeFullInfoDTO.setMiddleName(middleName);
		employeeFullInfoDTO.setLastName(lastName);
		employeeFullInfoDTO.setEmployeeId(employeeId);
		employeeFullInfoDTO.setBiometrics(biometrics);
		employeeFullInfoDTO.setDepartmentId(departmentId);
		employeeFullInfoDTO.setPositionId(positionId);
		employeeFullInfoDTO.setShiftId(shift);
		employeeFullInfoDTO.setLevel(level);
		employeeFullInfoDTO.setHiredDate(hireDate);
		employeeFullInfoDTO.setSupervisorName(supervisorName);
		employeeFullInfoDTO.setEmployeeEmail(employeeEmail);
		hrService.createEmployeeDetail(employeeFullInfoDTO);
		
		return "ViewHr";
	}
	
	@RequestMapping(value = "/retrieveDepartmentJSON")
	public @ResponseBody
	Map<Integer, String> retrieveDepartment() {
		return hrService.retrieveDepartment();
	}
	
	@RequestMapping(value = "/retrievePositionJSON")
	public @ResponseBody
	Map<Integer, String> retrievePosition(
			@RequestParam(value = "department") int departmentId) {		
		return hrService.retievePosition(departmentId);
	}
	
	@RequestMapping(value = "/retrieveAllPositionJSON")
	public @ResponseBody
	Map<Integer, String> retrieveAllPosition() {		
		return hrService.retieveAllPosition();
	}
	
	@RequestMapping(value = "/retrieveSupervisorJSON")
	public @ResponseBody
	Map<Integer, String> retrieveSupervisor(
			@RequestParam(value = "department") int departmentId) {		
		return hrService.retieveSupervisor(departmentId);
	}
	
	@RequestMapping(value = "/retrieveShiftJSON")
	public @ResponseBody
	Map<Integer, String> retrieveSupervisor() {		
		return hrService.retrieveShifts();
	}

/*	@RequestMapping(value = "/uploadUserDB", method = RequestMethod.POST)
	public String getFileUpload(
			@ModelAttribute("uploadForm") SingleFileUploadForm uploadForm,
			ModelMap modelMap) {

		// upload file
		hrService.userDBFileUpload(uploadForm.getFile());
		
		return "redirect: ../../../mainpage/admin#loadAdmin";

	}*/
	
	@RequestMapping(value = "/uploadUserDB", method = RequestMethod.POST)
	/*@Consumes("multipart/form-data")*/
	public String getFileUpload(
			@RequestParam("file") MultipartFile file,
			ModelMap modelMap) {

		// upload file
//		hrService.userDBFileUpload(uploadForm.getFile());
		System.out.println(file.toString());
		hrService.userDBFileUpload(file);
		
		return "redirect: ../../../mainpage/admin#loadAdmin";

	}
	
	@RequestMapping(value = "/uploadPage")
	public String uploadPage() {

		// upload file
		//hrService.userDBFileUpload(uploadForm.getFile());
		//System.out.println("files" + uploadForm.toString());
		return "upload";
	}
	
	
	@RequestMapping(value = "/uploadUserDBFromTest")
	public String getFileUpload() {

		// upload file
		//hrService.userDBFileUpload(uploadForm.getFile());
		//System.out.println("files" + uploadForm.toString());
		return "upload";
	}
}
