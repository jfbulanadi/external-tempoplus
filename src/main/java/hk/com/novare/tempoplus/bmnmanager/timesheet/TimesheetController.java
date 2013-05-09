package hk.com.novare.tempoplus.bmnmanager.timesheet;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/timesheet")
public class TimesheetController {
	
	@Inject TimesheetService timesheetService;
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	private String createTimesheet(@RequestParam String name) {
		timesheetService.createTimesheet(name);		
		return "ViewUpload";
	}
	
	@RequestMapping(value = "/retrieve", method = RequestMethod.POST)
	private String retrieveTimesheets() {
		
		timesheetService.retrieveTimesheets();
		return "upload";
	}

}
