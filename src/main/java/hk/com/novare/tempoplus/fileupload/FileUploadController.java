package hk.com.novare.tempoplus.fileupload;

import hk.com.novare.tempoplus.bmnmanager.biometric.BiometricService;
import hk.com.novare.tempoplus.bmnmanager.consolidation.ConsolidationService;
import hk.com.novare.tempoplus.bmnmanager.mantis.MantisService;
import hk.com.novare.tempoplus.bmnmanager.nt3.Nt3Service;
import hk.com.novare.tempoplus.employee.EmployeeService;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
@RequestMapping("/fileupload")
public class FileUploadController {

	@Inject	FileUploadService fileUploadService;
	@Inject	BiometricService biometricService;
	@Inject MantisService mantisService;
	@Inject EmployeeService employeeService;
	@Inject Nt3Service nt3Service;

	@Inject ConsolidationService consolidationService;
	
	static final Logger logger = Logger.getLogger(FileUploadController.class);
	
	@RequestMapping(value = "/uploadform", method = RequestMethod.GET)
	public String displayForm() {
		return "ViewUpload";
	}

	@RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
	public String handleFileUpload(@RequestParam CommonsMultipartFile[] file) throws Exception {
		//TODO Add Parameter Category
		//This is not yet finished
			int category = 5;
			
			switch (category) {
			case 1: biometricService.readData(file);
				break;
			case 2: mantisService.readData(file);	
//				mantisService.splitMantisData();
				break;
			case 3: nt3Service.readData(file);
				break;
			case 4: employeeService.readData(file);
				break;
			case 5: consolidationService.consolidateTimesheet();
			break;
			}
			
		return "ViewUpload";
	}
}
