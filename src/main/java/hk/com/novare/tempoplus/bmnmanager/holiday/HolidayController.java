package hk.com.novare.tempoplus.bmnmanager.holiday;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/holiday")
public class HolidayController {

	@Autowired
	UploadHolidayService uploadHolidayService;

	@RequestMapping(value = "/uploadHoliday", method = RequestMethod.POST)
	public @ResponseBody
	void uploadHoliday(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// final String fileUrl = request.getParameter("fileUrl");
		final String fileUrl = "/home/esh/Holiday.xls";

		new UploadHolidayService().insertDB(fileUrl);
	}

	@RequestMapping(value = "/retrieveHoliday")
	public String getHoliday(ModelMap modelMap) {

		final ArrayList<HolidayPojo> holidayPojo = uploadHolidayService
				.retrieveHolidays();
		modelMap.addAttribute("holidayPojo", holidayPojo);

		return "ViewHoliday";

	}

}
