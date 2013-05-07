package hk.com.novare.tempoplus.timelogging;

import hk.com.novare.tempoplus.employee.Employee;

import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/timelog")
public class TimeLoggingController {

	static final Logger logger = Logger.getLogger(TimeLoggingController.class);
		@Resource(name = "timelogService")
		
		TimelogServiceInt timelogServiceInt;
		
		@RequestMapping(value = "/flagging", method = RequestMethod.GET)
		public @ResponseBody String flagging(ModelMap modelMap){
			logger.info("Flagging process");
			timelogServiceInt.flaggingProcess();
			return "Background Process";
		}
		
		@RequestMapping(value = "/userLog", method = RequestMethod.GET)
		public String sayUserLog(ModelMap modelMap) {
			logger.info("Initialize timelog view");
			modelMap.addAttribute("id", timelogServiceInt.getEmployeeId());
			return "ViewTimelog"; // view
		}
		
		@RequestMapping(value = "/validateInput", method=RequestMethod.POST)
		public @ResponseBody String SearchTimeLog(@RequestParam(value = "id") int id,
				@RequestParam(value = "name") String name,
				@RequestParam(value = "from") String from,
				@RequestParam(value = "to") String to){
				logger.info("Validate input from user");
				return timelogServiceInt.validateInput(id,name,from,to);
		}
		
		@RequestMapping(value = "/checkUser", method=RequestMethod.POST)
		public @ResponseBody String checkUser(@RequestParam(value = "id") int id){
			logger.info("Check user type");
			return timelogServiceInt.checkUser(id);
		}
		
		@RequestMapping(value = "/retrieveSub", method=RequestMethod.POST)
		public @ResponseBody List retrieveSub(@RequestParam(value = "id") int id){
			logger.info("Retrieve Manager's subordinates");
			return timelogServiceInt.retrieveSubordinates(id);
		}
		
		@RequestMapping(value = "/retrieveTimelog", method=RequestMethod.POST)
		public @ResponseBody List<TimeLogging> retrieveTimelog(@RequestParam(value = "name") String name,
				@RequestParam(value = "from") String from,
				@RequestParam(value = "to") String to){
			logger.info("Retrieve timelog history of employee");
			return timelogServiceInt.retrieveTimelog(name,from,to);
		}
		
		
		@RequestMapping(value = "/retrieveMylog", method=RequestMethod.POST)
		public @ResponseBody List<TimeLogging> retrieveMylog(@RequestParam(value = "id") int id,
				@RequestParam(value = "from") String from,
				@RequestParam(value = "to") String to){
			logger.info("Retrieve own timelog history");
			return timelogServiceInt.retrieveMylog(id,from,to);
		}
		//Hr Search
			@RequestMapping(value = "/searchEmployee", method= RequestMethod.POST)
			public @ResponseBody String employeeSearch(@RequestParam(value = "empName") String empName){
				logger.info("Search employee name");
				return timelogServiceInt.checkName(empName);
				
			}
			//Hr Retrieve result
			@RequestMapping(value = "/retrieveEmployee", method= RequestMethod.POST)
			public @ResponseBody List<Employee> retriveEmployee(@RequestParam(value = "empName") String empName) throws DataAccessException{
				logger.info("Retrieve employee info");
				return timelogServiceInt.searchEmployees(empName);	

			}

}
