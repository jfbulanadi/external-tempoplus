package hk.com.novare.tempoplus.accountsystem.employeemanager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/manager")
@SessionAttributes({"userEmployeeId", "userEmail"})
public class EmployeeManagerController {
	
	@Inject EmployeeManagerService employeeManagerService;
	List<EmployeeManager> searchEmployeeList;
	
	@RequestMapping(value = "/viewSubordinates")
	public String viewSubordinates(ModelMap modelMap) {	
		int supervisorId = Integer.parseInt(modelMap.get("userEmployeeId").toString());
		modelMap.addAttribute("subordinatesList", 
				employeeManagerService.retrieveSubordinatesList(supervisorId));
		
		return "ViewSubordinates";
		}
	
	@RequestMapping(value = "/searchEmployeeSubordinates", method = RequestMethod.POST)
	public @ResponseBody boolean searchEmployee(@RequestParam(value = "employeeName") String employeeName){
		System.out.println("[ @ controller 1");
		searchEmployeeList = employeeManagerService.retrieveAddSubordinatesDetails(employeeName);
		System.out.println("[ @ controller 2");
		
		System.out.println(employeeManagerService.hasEmployeeRecord());
		return employeeManagerService.hasEmployeeRecord();
	}
	
	@RequestMapping(value = "/retrieveFoundEmployees", method = RequestMethod.POST)
	public @ResponseBody List<EmployeeManager> retrieveFoundEmployees(@RequestParam(value = "employeeName") String employeeName){
		return searchEmployeeList;
	}
	
	@RequestMapping(value = "/saveNewSubordinates", method = RequestMethod.POST) 
	public @ResponseBody String saveNewSubordinates(@RequestParam(value = "employeeIdsJSON") String newSubordinates,
			ModelMap modelMap) throws JSONException{
		
		JSONObject newSubordinatesJSON = new JSONObject(newSubordinates);
		JSONArray idArray = newSubordinatesJSON.getJSONArray("ids");	
		ArrayList<Integer> subordinateIds = new ArrayList<Integer>();	
		
			for(int i = 0; i < idArray.length(); i++){
				subordinateIds.add(Integer.parseInt(idArray.getString(i)));
			}
			
			for(int id : subordinateIds) {
				System.out.println(id);
			}
		int managerEmployeeId = Integer.parseInt(modelMap.get("userEmployeeId").toString());
		
		return subordinateIds.get(0).toString();
	}
	
	
}
