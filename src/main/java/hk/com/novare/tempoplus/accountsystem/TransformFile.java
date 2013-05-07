package hk.com.novare.tempoplus.accountsystem;



import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface TransformFile {

	List<HumanResourceDTO> toExcel(MultipartFile multipartFile);

}
