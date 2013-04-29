package hk.com.novare.tempoplus.bmnmanager.consolidation;

import hk.com.novare.tempoplus.employee.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping("/consolidation")
public class ConsolidationController {

	@Inject
	ConsolidationService consolidationService;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	private String createConsolidatedTimesheet(@RequestParam String name,
			@RequestParam String periodStart, @RequestParam String periodEnd) {

		// consolidationService.createConsolidatedTimesheet(name, periodStart,
		// periodEnd);

		return "upload";
	}

//	@RequestMapping(value = "/check", method = RequestMethod.POST)
//	private String isReadyForConsolidation(@RequestParam int id) {
//		return consolidationService.isReadyForConsolidation(id);
//	}
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String viewConsolidated(ModelMap modelMap) throws SQLException {
		
		
		
		
	modelMap.addAttribute("content", consolidationService.viewConsolidated());
	return "ViewBMN";
	}
	
	@RequestMapping(value = "/mail", method = RequestMethod.GET)
	public String mailTimeSheet() {
	return "ViewSendMail";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody ArrayList<Employee> updateViewContent(@RequestParam(value="employeeid") int employeeid, 
			@RequestParam(value="firstname") String firstname) throws SQLException {
		
			return consolidationService.updateViewConsolidated(employeeid, firstname);
		
	}
	

	
	
	
}
