import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import hk.com.novare.tempoplus.accountsystem.HrController;
import hk.com.novare.tempoplus.accountsystem.HrService;
import hk.com.novare.tempoplus.accountsystem.HumanResourceFullInfoDTO;
import hk.com.novare.tempoplus.accountsystem.HumanResourcePartialInfoDTO;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.springframework.ui.ModelMap;



public class HrControllerTest {
	

	
	@Mock
	HrService hrService;
	@Test
	public void testHrController_subPage() {
		
		/*HumanResourcePartialInfoDTO humanResourcePartialInfoDTO = new HumanResourcePartialInfoDTO();
		List<HumanResourcePartialInfoDTO> list = new ArrayList<HumanResourcePartialInfoDTO>();
		list.add(humanResourcePartialInfoDTO);*/
		/*when(hrService.retrieveAllEmployee()).thenReturn(list);*/
		
		//Test proper return;
		//HrService mockHrService = mock(HrService.class);
		/*HrController hrController = new HrController();
		ModelMap modelMap = new ModelMap();
		assertEquals("ViewHr", hrController.subPage(modelMap));
		
		@SuppressWarnings("unchecked")
		List list = (List<HumanResourcePartialInfoDTO>)modelMap.get("employeeList");
		assertNotNull(list);*/
		/*hrController.subPage(modelMap);
		when(modelMap.addAttribute("List", hrService.retrieveAllEmployee()));
		
		String expectedResult = "ViewHr";
		*/
			
	}

}
