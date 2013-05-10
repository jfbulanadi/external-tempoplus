package hk.com.novare.tempoplus.bmnmanager.consolidation;

import hk.com.novare.tempoplus.bmnmanager.mantis.Mantis;
import hk.com.novare.tempoplus.bmnmanager.nt3.Nt3;
import hk.com.novare.tempoplus.bmnmanager.timesheet.Timesheet;
import hk.com.novare.tempoplus.bmnmanager.timesheet.TimesheetPartialDTO;
import hk.com.novare.tempoplus.employee.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/consolidation")
public class ConsolidationController {

	@Inject
	ConsolidationService consolidationService;
	final Logger logger = Logger.getLogger(ConsolidationController.class);

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	private String createConsolidatedTimesheet(@RequestParam String name,
			@RequestParam String periodStart, @RequestParam String periodEnd) {

		// consolidationService.createConsolidatedTimesheet(name, periodStart,
		// periodEnd);
		logger.info("createConsolidatedTimesheet: Parameters(" + name + "," + periodStart + "," + periodEnd+ ")");
		return "upload";
	}

//	@RequestMapping(value = "/check", method = RequestMethod.POST)
//	private String isReadyForConsolidation(@RequestParam int id) {
//		return consolidationService.isReadyForConsolidation(id);
//	}
	
	@RequestMapping(value = "/ajaxFetchConsolidations", method=RequestMethod.GET)
	public @ResponseBody ArrayList<ConsolidationDTO> fetchConsolidations(@RequestParam String id)  {
		logger.info("fetchConsolidations: Loading consolidated records.");
		return consolidationService.viewConsolidation(id);
	}
	

	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String viewConsolidated() throws SQLException {
		logger.info("viewConsolidated: Displaying consolidated records.");
		return "ViewBMN";
	}
	
	@RequestMapping(value = "/ajaxUpdateConsolidations", method = RequestMethod.POST)
	public @ResponseBody Boolean updateConsolidations(@RequestParam String timeIn,
			@RequestParam String timeOut,
			@RequestParam String employeeId,
			@RequestParam String date) {
		logger.info("Updating record consolidations.");
		consolidationService.updateConsolidations(employeeId, timeIn, timeOut, date);
		
		return true;
	}
	
	@RequestMapping(value = "/ajaxFetchMantises", method = RequestMethod.POST)
	public @ResponseBody ArrayList<Mantis> fetchMantisDetails(
			@RequestParam String employeeId, String date) {
		
		logger.info("Fetching Mantis tickets for the user");
		return consolidationService.fetchMantises(employeeId, date);
		
	}
	
	@RequestMapping(value = "/ajaxFetchNt3s", method = RequestMethod.POST)
	public @ResponseBody ArrayList<Nt3> fetchNt3Details(@RequestParam String employeeId, String date) {
		
	
		
		logger.info("Fetching NT3 tickets for the user");
		return consolidationService.fetchNt3s(employeeId, date);
		
	}
	
	
	@RequestMapping(value = "/ajaxFetchTimesheets", method = RequestMethod.POST)
	public @ResponseBody ArrayList<TimesheetPartialDTO> fetchTimesheets() {
		logger.info("fetchTimesheets: Loading timesheets");
		return consolidationService.fetchTimesheets();
		
	}

	
	

	
	
	
}
