package hk.com.novare.tempoplus.filedownload;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/downloadpage")
public class DownloadfileController {

	@Resource(name = "downloadfileservice")
	DownloadfileService downloadfileservice;

	@RequestMapping(value = "/downloadlink", method = RequestMethod.GET)
	public String viewDownloadlink(ModelMap modelmap) {
		return "download";
	}

	@RequestMapping(value = "/Excel", method = RequestMethod.GET)
	public void handleFileDownload(HttpServletResponse res) {
		downloadfileservice.download(res);
	}
	
}
