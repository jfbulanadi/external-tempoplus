package hk.com.novare.tempoplus.useraccount.user;

import hk.com.novare.tempoplus.employee.Employee;


import hk.com.novare.tempoplus.timelogging.DataAccessException;

import java.text.ParseException;
import java.util.List;

import hk.com.novare.tempoplus.timelogging.TimeLogging;
import hk.com.novare.tempoplus.timelogging.TimeLoggingService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/user")
@SessionAttributes({"userEmployeeId", "userEmail"})

public class UserController {
	
	@Inject UserService userService;
	
	@Inject
	TimeLoggingService timelogService;
	@Inject User user;

	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(){
		return "index";
	}
	
	@RequestMapping(value = "/login	",  method = RequestMethod.POST)
	public String loginValidation(@ModelAttribute(value="timelogs") TimeLogging timelogs,HttpServletRequest httpServletRequest,
			HttpSession session,
			ModelMap modelMap,
			@RequestParam(value = "userName")String userEmail,
			@RequestParam(value = "password") String password) throws DataAccessException {
		
		boolean isValid = userService.validateLogInAccess(userEmail, password);
		
		if(isValid){

			timelogService.logTimeIn(timelogs);

			return "redirect:home";

		}else{
			modelMap.addAttribute("outputMsg", "Invalid e-mail address/password.");
			return "ViewLogin";
		}
	}
	
	@RequestMapping(value = "/login	",  method = RequestMethod.GET)
	public String accessLogin(
			ModelMap modelMap,HttpServletRequest httpServletRequest){
		
		if(modelMap.get("userEmployeeId").equals(null)){
			return "redirect:index";
		}
		else{
			return "redirect:home";
		}
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(ModelMap modelMap){
		modelMap.addAttribute("employeeDetailsList", userService.retrieveUserInformation(user.getEmail()));
		modelMap.addAttribute("supervisorDetails", userService.retrieveSupervisorInformation(user.getSupervisorId()));
		
		modelMap.addAttribute("userEmployeeId", user.getEmployeeId());
		modelMap.addAttribute("userEmail",user.getEmail());
		
		modelMap.addAttribute("userId", modelMap.get("userEmployeeId"));
		
		return "home";
	}
	
	
	@RequestMapping("/changePassword")
	public String changePasswordProcess(ModelMap modelMap,
			@RequestParam("currentPassword") String currentPassword,
			@RequestParam("newPassword") String newPassword,
			@RequestParam("repeatNewPassword") String repeatedNewPassword) {
		
		boolean isCurrentPasswordMatched = userService.matchCurrentPassword(
						userService.hashPassword(currentPassword),user.getPassword());
		boolean isNewPasswordAcceptable = 
				userService.validateNewPassword(newPassword, repeatedNewPassword);
	
		if(isCurrentPasswordMatched && isNewPasswordAcceptable){
			
			userService.saveNewPassword(user.getEmployeeId(), repeatedNewPassword);
			modelMap.addAttribute("passwordMsg", "New password is saved.");
			
		}else{
			
			modelMap.addAttribute("passwordMsg", "Check your input.");
		}
		
		return "home";
	}
	
	
	@RequestMapping("/logout")
	public String logout(ModelMap modelMap, HttpServletRequest httpServletRequest,@ModelAttribute(value="timelogs") TimeLogging timelogs) throws ParseException{
		httpServletRequest.getSession().invalidate();
		modelMap.clear();
		try {
			timelogService.logTimeOut(timelogs);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		List<User> userList = userService.retrieveUserInformation(user.getEmail());
		List<Employee> supervisorList = userService.retrieveSupervisorInformation(user.getSupervisorId());
		userList.clear();
		supervisorList.clear();
		return "redirect:index";

	}
}

