package hk.com.novare.tempoplus.bmnmanager.shiftmanager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.expression.ParseException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping("/shift")
public class ShiftingController {
		@Resource(name="shiftingservice")
		ShiftingService shiftingservice;
	
	@RequestMapping(value="/shifting", method = RequestMethod.GET)
	public String showshifting(ModelMap modelmap) throws DataAccessException{
		modelmap.addAttribute("shiftlist", shiftingservice.retrieveShift());
		return "Shifting";
	}
	
	
	@RequestMapping(value = "/addshift", method = RequestMethod.POST)
	public @ResponseBody List<ShiftingPOJO> addshift(@RequestParam(value="shiftname")String shiftname,
			@RequestParam(value="timein")String timein, @RequestParam(value="timeout")String timeout) throws ParseException, DataAccessException{
			shiftingservice.validate(shiftname, timein, timeout);
		    return shiftingservice.retrieveShift(); 
	}
	
	
	@RequestMapping(value = "/editshift", method = RequestMethod.POST)
	public @ResponseBody List<ShiftingPOJO> editshift(@RequestParam(value="shiftid")int shiftid) throws ParseException, DataAccessException{
			return shiftingservice.getToeditshift(shiftid);
		    
	}
	@RequestMapping(value = "/editshifting", method = RequestMethod.POST)
	public String editshifting(@RequestParam(value="shiftid")int shiftid, 
			@RequestParam(value="newshiftname")String shiftname, @RequestParam(value="newtimein")String timein,
			@RequestParam(value="newtimeout")String timeout) throws DataAccessException{
		    shiftingservice.editshift(shiftid, shiftname, timein, timeout);
			return "Shifting";
		    
	}	
}
