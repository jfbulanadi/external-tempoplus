package hk.com.novare.tempoplus.accountsystem.employeemanager;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/manager")
@SessionAttributes({"userEmployeeId", "userEmail"})
public class EmployeeManagerController {
	@Inject EmployeeManagerService employeeManagerService;
	
	@RequestMapping(value = "/viewSubordinates")
	public String viewSubordinates(ModelMap modelMap) {	
		int supervisorId = Integer.parseInt(modelMap.get("userEmployeeId").toString());
		modelMap.addAttribute("subordinatesList", 
				employeeManagerService.retrieveSubordinatesList(supervisorId));
		
		return "ViewSubordinates";
		}
	
	/*@RequestMapping(value = "/searchSubordinateEmployeeId")
	public String searchEmployee(@RequestParam(value = "searchEmployeeId") int employeeIdSearch ){
		
	}*/
}
