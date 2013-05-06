package hk.com.novare.tempoplus.accountsystem;

import java.util.List;


public interface HrModel {

	List<HumanResource> selectAll();
	void createEmployee(HumanResource employee );
	HumanResourceDTO search(String searches, String category);
	void createEmployeeFromUpload(HumanResourceDTO employee);

	
}
