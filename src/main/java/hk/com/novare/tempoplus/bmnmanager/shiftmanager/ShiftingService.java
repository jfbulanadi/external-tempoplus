package hk.com.novare.tempoplus.bmnmanager.shiftmanager;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

public class ShiftingService {
	@Resource(name = "shiftingdaosource")
	ShiftingDao shiftingdao;
	
	public void validate(String shiftname, String timein, String timeout){
		DateFormat dateformat = new SimpleDateFormat("k:mm");
		try{
			Date timeinDate  =  dateformat.parse(timein);
			Date timeoutDate =  dateformat.parse(timeout);
			String timeinString = dateformat.format(timeinDate);
			String timeoutString = dateformat.format(timeoutDate);
			shiftingdao.addShifting(shiftname, timeinString, timeoutString);		
		}
		catch(ParseException e){
			e.printStackTrace();
		}
	}
	
	public List<ShiftingPOJO> retrieveShift() throws DataAccessException{
	     return shiftingdao.showShift();
	}
	
	public List<ShiftingPOJO> getToeditshift(int shiftid) throws DataAccessException{
		return shiftingdao.getShifting(shiftid);
	}
	
	public void editshift(int shiftid, String shiftname, String timein, String timeout ){
		shiftingdao.editshifing(shiftid, shiftname, timein, timeout);
	}
	

}
