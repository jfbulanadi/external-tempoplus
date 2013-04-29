package hk.com.novare.tempoplus.fileupload;

import org.springframework.web.multipart.MultipartFile;

public class FileUpload {

	
	
	private MultipartFile file;

	/**
	 * Test documentaion
	 * @return
	 */
	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

}
