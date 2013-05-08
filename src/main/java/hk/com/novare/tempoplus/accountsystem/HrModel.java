package hk.com.novare.tempoplus.accountsystem;

import java.util.List;


public interface HrModel {

	List<HumanResource> selectAll();
	HumanResourceDTO search(String searches, String category);
	void createEmployeeFromUpload(HumanResourceDTO employee);
	void createEmployee(HumanResourceDTO employee);

	
}
