package hk.com.novare.tempoplus.utilities;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class ExcelWorkbookUtility {

	public Sheet getExcelWorkbook(CommonsMultipartFile[] file) {
		
		Workbook workbook = null;
		
		if (file != null && file.length > 0) {
			for (CommonsMultipartFile aFile : file) {

				InputStream inputStream;
				try {
					inputStream = aFile.getInputStream();
					workbook = WorkbookFactory.create(inputStream);

				} catch (IOException e) {
					e.printStackTrace();
				} catch (InvalidFormatException e) {
					e.printStackTrace();
				}

			}
		}

		return workbook.getSheetAt(0);
	}

}
