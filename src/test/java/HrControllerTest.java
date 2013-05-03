import static org.junit.Assert.assertEquals;
import hk.com.novare.tempoplus.accountsystem.EmployeePartialInfoDTO;
import hk.com.novare.tempoplus.accountsystem.HrController;
import hk.com.novare.tempoplus.accountsystem.HrService;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.ui.ModelMap;



public class HrControllerTest {

	@Test
	public void test() {
		HrController hrController = new HrController();
		List<EmployeePartialInfoDTO> employeeList = new ArrayList<EmployeePartialInfoDTO>();
		
		EmployeePartialInfoDTO employeePartialInfoDTO1 = new EmployeePartialInfoDTO();
		employeePartialInfoDTO1.setEmployeeId("1234");
		EmployeePartialInfoDTO employeePartialInfoDTO2 = new EmployeePartialInfoDTO();
		employeePartialInfoDTO2.setEmployeeId("1235");
		EmployeePartialInfoDTO employeePartialInfoDTO3 = new EmployeePartialInfoDTO();
		employeePartialInfoDTO3.setEmployeeId("1236");
		
		employeeList.add(employeePartialInfoDTO1);
		employeeList.add(employeePartialInfoDTO2);
		employeeList.add(employeePartialInfoDTO3);
		
		HrService mockService = Mockito.mock(HrService.class);
		Mockito.when(mockService.retrieveAllEmployee()).thenReturn(employeeList);
		
		ModelMap mockMap = Mockito.mock(ModelMap.class);
		Mockito.when(mockMap.addAttribute("employeeList", mockService));
		
		assertEquals(hrController.subPage(mockMap), mockService );
			
	}

}
