package hk.com.novare.tempoplus.filedownload;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;

public class DownloadfileService {

	public void download(HttpServletResponse res) {
		try {
			String fn = "/timesheet.ods";
			URL url = getClass().getResource(fn);
			File f = new File(url.toURI());
			System.out.println("Loading file " + fn + "(" + f.getAbsolutePath()
					+ ")");
			if (f.exists()) {
				res.setContentType("application/xls");
				res.setContentLength(new Long(f.length()).intValue());
				res.setHeader("Content-Disposition",
						"attachment; filename=timesheet.ods");
				FileCopyUtils.copy(new FileInputStream(f),
						res.getOutputStream());
			} else {
				System.out.println("File" + fn + "(" + f.getAbsolutePath()
						+ ") does not exist");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
