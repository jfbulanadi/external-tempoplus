package hk.com.novare.tempoplus.accountsystem;

import java.util.List;


public interface HrModel {

	List<Employee> selectAll();
	void addEmployee(Employee employee ) throws HrDataAccessException;
	List<Employee> search(String searches, String category);

	
}
