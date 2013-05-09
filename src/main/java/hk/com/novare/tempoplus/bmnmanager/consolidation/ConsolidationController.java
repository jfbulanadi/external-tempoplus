package hk.com.novare.tempoplus.bmnmanager.consolidation;

import hk.com.novare.tempoplus.bmnmanager.mantis.Mantis;
import hk.com.novare.tempoplus.bmnmanager.timesheet.Timesheet;
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
	public @ResponseBody ArrayList<ConsolidationDTO> fetchConsolidations(@RequestParam String selectedTimesheet)  {
		logger.info("fetchConsolidations: Loading consolidated records.");
		return consolidationService.viewConsolidation(selectedTimesheet);
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
	
	@RequestMapping(value = "/ajaxFetchTickets", method = RequestMethod.POST)
	public @ResponseBody ArrayList<Mantis> fetchTicketDetails(
			@RequestParam String employeeId) {
		
		logger.info("Fetching NT3/Mantis tickets for the user");
		return consolidationService.fetchTicket(employeeId);
		
	}
	
	@RequestMapping(value = "/ajaxFetchTimesheets", method = RequestMethod.POST)
	public @ResponseBody ArrayList<String> fetchTimesheets() {
		logger.info("fetchTimesheets: Loading timesheets");
		return consolidationService.fetchTimesheets();
		
	}

	
	

	
	
	
}
