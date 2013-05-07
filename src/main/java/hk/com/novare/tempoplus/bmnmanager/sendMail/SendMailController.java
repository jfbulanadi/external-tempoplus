package hk.com.novare.tempoplus.bmnmanager.sendmail;

import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sendMail")
public class SendMailController {

	@Autowired
	SendEmailService sendEmailService;

	private final Logger logger = Logger.getLogger(SendMailController.class);

	@RequestMapping(value = "/mail", method = RequestMethod.GET)
	public String mailTimeSheet(ModelMap modelMap) throws SQLException {

		logger.info("Setting Up Send Mail View");

		final ArrayList<SingleRecipientPojo> singleRecipientPojos = sendEmailService
				.getNames();

		final ArrayList<String> departments = sendEmailService
				.retrieveDepartments();

		modelMap.addAttribute("departments", departments);
		modelMap.addAttribute("names", singleRecipientPojos);

		return "ViewSendMail";
	}

	
	
	@RequestMapping(value = "/validatePassword", method = RequestMethod.POST)
	public @ResponseBody
	boolean validatePassword(@RequestParam("password") final String password)
			throws Exception {

		boolean isCorrect = sendEmailService.validatePassword(password);

		return isCorrect;

	}

	@RequestMapping(value = "/logs", method = RequestMethod.GET)
	public String mailTimeSheetLogs(ModelMap modelMap) throws SQLException {


		// Get Date of Logs
		final ArrayList<String> dateLogs = sendEmailService.retrieveDateLogs();

		modelMap.addAttribute("dateLogs", dateLogs);

		return "ViewSendMailLogs";
	}

	@RequestMapping(value = "/getLogs", method = RequestMethod.GET)
	public @ResponseBody
	ArrayList<SendMailLogsPojo> getLogs(
			@RequestParam("selectedLogs") final String selectedLogs)
			throws Exception {

		final ArrayList<SendMailLogsPojo> sendMailLogsPojo = sendEmailService
				.retrieveLogs(selectedLogs);

		return sendMailLogsPojo;
	}

	@RequestMapping(value = "/getFiles", method = RequestMethod.GET)
	public @ResponseBody
	ArrayList<String> getFiles(
			@RequestParam("selectedEmployee") final String selectedEmployee)
			throws Exception {

		final ArrayList<String> filesList = sendEmailService
				.getFileList(selectedEmployee);

		return filesList;
	}

	@RequestMapping(value = "/send", method = RequestMethod.POST)
	public @ResponseBody
	String sendMail(@RequestParam("selectedDepartment") String department,
			@RequestParam("msgBody") final String msgBody,
			@RequestParam("selectedPeriod") final String period,
			@RequestParam("msgTitle") final String msgTitle, ModelMap modelMap)
			throws Exception {

		System.out.println("Sending Department : " + department);
		System.out.println("Selected Period: " + period);

		// prepare Service Mail Properties
		sendEmailService.prepMailProps();

		// set up mail Pojos of each employee
		final ArrayList<MailPojo> mailPojo = sendEmailService.retrieveEmails(
				department, msgBody, period, msgTitle);

		// Send mail
		sendEmailService.sendMailByDep(mailPojo);

		final String status = "Done Processing";

		return status;
	}

	@RequestMapping(value = "/sendInd", method = RequestMethod.POST)
	public @ResponseBody
	String sendMailInd(@RequestParam("msgBody") final String msgBody,
			@RequestParam("selectedPeriod") final String period,
			@RequestParam("msgTitle") final String msgTitle, ModelMap modelMap)
			throws Exception {

		// prepare Service Mail Properties
		sendEmailService.prepMailProps();

		// Send Mail bydeparment
		final String status = sendEmailService.sendByIndividual(msgBody,
				period, msgTitle);

		return status;

	}
}
