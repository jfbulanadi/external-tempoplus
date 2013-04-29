package hk.com.novare.tempoplus.timelogging;

import hk.com.novare.tempoplus.employee.Employee;

import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/timelog")
public class TimeLoggingController {
	
		@Resource(name = "timelogService")
		
		TimelogServiceInt timelogServiceInt;
		
		@RequestMapping(value = "/flagging", method = RequestMethod.GET)
		public @ResponseBody String flagging(ModelMap modelMap) throws ParseException {
			try {
				timelogServiceInt.flaggingProcess();
			} catch (DataAccessException e) {
				
				e.printStackTrace();
			}
			return "Background Process";
		}
		
		@RequestMapping(value = "/UserLog", method = RequestMethod.GET)
		public String sayUserLog(ModelMap modelMap) {
			
			modelMap.addAttribute("id", timelogServiceInt.getEmployeeId());
			System.out.println(timelogServiceInt.getEmployeeId());
			return "ViewTimelog"; // view
		}
		
		@RequestMapping(value = "/validateInput", method=RequestMethod.POST)
		public @ResponseBody String SearchTimeLog(@RequestParam(value = "id") int id,
				@RequestParam(value = "name") String name,
				@RequestParam(value = "from") String from,
				@RequestParam(value = "to") String to) throws ParseException {
				return timelogServiceInt.ValidateInput(id,name,from,to);
		}
		
		@RequestMapping(value = "/checkUser", method=RequestMethod.POST)
		public @ResponseBody String checkUser(@RequestParam(value = "id") int id) throws ParseException, DataAccessException {
//			System.out.println("pumasok");
		
			return timelogServiceInt.checkUser(id);
		}
		
		@RequestMapping(value = "/retrieveSub", method=RequestMethod.POST)
		public @ResponseBody List retrieveSub(@RequestParam(value = "id") int id) throws ParseException, DataAccessException {
				
			return timelogServiceInt.retrieveSubordinates(id);
		}
		
		@RequestMapping(value = "/retrieveTimelog", method=RequestMethod.POST)
		public @ResponseBody List<TimeLogging> retrieveTimelog(@RequestParam(value = "name") String name,
				@RequestParam(value = "from") String from,
				@RequestParam(value = "to") String to) throws ParseException, DataAccessException {
			return timelogServiceInt.retrieveTimelog(name,from,to);
		}
		
		
		@RequestMapping(value = "/retrieveMylog", method=RequestMethod.POST)
		public @ResponseBody List<TimeLogging> retrieveMylog(@RequestParam(value = "id") int id,
				@RequestParam(value = "from") String from,
				@RequestParam(value = "to") String to) throws ParseException, DataAccessException {
			return timelogServiceInt.retrieveMylog(id,from,to);
		}
		//Hr Search
			@RequestMapping(value = "/searchEmployee", method= RequestMethod.POST)
			public @ResponseBody String employeeSearch(@RequestParam(value = "empName") String empName) throws DataAccessException{
				
				return timelogServiceInt.checkName(empName);
				
			}
			//Hr Retrieve result
			@RequestMapping(value = "/retrieveEmployee", method= RequestMethod.POST)
			public @ResponseBody List<Employee> retriveEmployee(@RequestParam(value = "empName") String empName) throws DataAccessException{
					
				return timelogServiceInt.searchEmployees(empName);	

			}

}
