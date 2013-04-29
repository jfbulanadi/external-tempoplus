package hk.com.novare.tempoplus.bmnmanager.holiday;
/**
 * 
 * @author Ernest
 *
 */
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/bmnholiday")
public class UploadHolidayController {

	@Autowired
	UploadHolidayService uploadHolidayService;

	@RequestMapping(value = "/uploadHoliday", method = RequestMethod.POST)
	public @ResponseBody
	void uploadHoliday(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// final String fileUrl = request.getParameter("fileUrl");
		final String fileUrl = "/home/esh/Holiday.xls";

		uploadHolidayService.insertDB(fileUrl);

	}

	@RequestMapping(value = "/retrieveHoliday")
	public @ResponseBody
	void retrieveHoliday(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		final String fileUrl = request.getParameter("fileUrl");

		uploadHolidayService.insertDB(fileUrl);

	}

}
