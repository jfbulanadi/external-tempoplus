package hk.com.novare.tempoplus.accountsystem;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("mainpage")
public class mainpageController {
	
	@RequestMapping("/admin")
	public String adminPage(ModelMap modelMap) {
		return "hrmainpage";
	}
	
	@RequestMapping("/subadmin")
	public String subAdminPage(ModelMap modelMap) {
		return "administration";
	}

	

}
