package hk.com.novare.tempoplus.useraccount.user;

import hk.com.novare.tempoplus.employee.Employee;

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

	
	private String accessLevel = "";
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(){
	
		return "index";
	}
	
	@RequestMapping(value = "/login	",  method = RequestMethod.POST)
	public String loginValidation(@ModelAttribute(value="timelogs") TimeLogging timelogs,HttpServletRequest httpServletRequest,
			HttpSession session,
			ModelMap modelMap,
			@RequestParam(value = "userName")String userEmail,
			@RequestParam(value = "password") String password) {
		
		String redirect = "";
		if(userEmail.equals("") || password.equals("")){
			modelMap.addAttribute("outputMsg", "Input username/pasword.");
			redirect = "index";
		}
		else{
			boolean isValid = userService.validateLogInAccess(userEmail, password);
			
			if(isValid){
				
				timelogService.logTimeIn(timelogs);
				
				redirect = "redirect:identifyHome";
	
			}else{
				modelMap.addAttribute("outputMsg", "Invalid e-mail address/password.");
			redirect = "index";
			}
		}
		return redirect;
	}
	
	@RequestMapping(value = "/identifyHome", method = RequestMethod.GET)
	public String home(ModelMap modelMap){
		
		modelMap.addAttribute("employeeDetailsList", userService.retrieveUserInformation(user.getEmail()));
		modelMap.addAttribute("supervisorDetails", userService.retrieveSupervisorInformation(user.getSupervisorId()));
		
		//set to session attributes
		modelMap.addAttribute("userEmployeeId", user.getEmployeeId());
		modelMap.addAttribute("userEmail",user.getEmail());
		
		modelMap.addAttribute("userId", modelMap.get("userEmployeeId"));
		if(user.getDepartmentId() == 2){
			//for HR access
			 accessLevel ="homeHR";
		}
		else if(user.getLevel() >= 5){
			//for Supervisor access
			accessLevel = "homeSupervisor";
		}else {
			//for for all access
			accessLevel = "homeUser";
		}		
	return accessLevel;
					
	}
	/*change password for user*/
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
		return accessLevel;
	}
	
	
	@RequestMapping("/logout")
	public String logout(ModelMap modelMap, HttpServletRequest httpServletRequest){
		httpServletRequest.getSession().invalidate();
		modelMap.clear();
		
		//clear all objects in memory
		List<User> userList = userService.retrieveUserInformation(user.getEmail());
		List<Employee> supervisorList = userService.retrieveSupervisorInformation(user.getSupervisorId());
		userList.clear();
		supervisorList.clear();
		return "redirect:index";
	}
	
	/*@RequestMapping(value = "/login	",  method = RequestMethod.GET)
	public String accessLogin(
			ModelMap modelMap,HttpServletRequest httpServletRequest){
		
		if(modelMap.get("userEmployeeId").equals(null)){
			return "redirect:index";
		}
		else{
			return "redirect:home";
		}
	}*/
	
	/*	@RequestMapping(value = "/homeHR", method = RequestMethod.GET)
	public String homeHR(){
		return "hrmainpage";
	}
	
	@RequestMapping(value = "/homeSupervisor", method = RequestMethod.GET)
	public String homeSupervisor(){
		return "";
	}*/
}
