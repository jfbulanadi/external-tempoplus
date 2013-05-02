package hk.com.novare.tempoplus.timelogging;

import hk.com.novare.tempoplus.employee.Employee;
import hk.com.novare.tempoplus.useraccount.user.User;
import hk.com.novare.tempoplus.useraccount.user.UserDao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class TimeLoggingService implements TimelogServiceInt{

	@Inject
	TimelogDAOInt timelogDAOInt;
	
	@Inject
	User user;
	
	public int i;
	public int employeeid;
	Map subMap = new HashMap();
	
	@Override
	public void flaggingProcess() throws DataAccessException, ParseException {
		int id;
		String desc,timeIn,timeOut,shiftIn,shiftInReal,shiftOut;
		boolean checkTime,checkIn,checkOut,checkLate;
		final String yDate;
		final int cUser;
		int c;
		
		yDate = getDate();
		checkLate = false;
		cUser = timelogDAOInt.countUser();
		
		for(i = 0; i<cUser; i++)
		{
			id = timelogDAOInt.getUserID(i);
			desc = timelogDAOInt.getShiftDesc(id);
			shiftInReal = timelogDAOInt.getShiftInReal(desc);
			shiftInReal =  yDate + " " + shiftInReal;
			
			
			shiftIn = getShiftIn(id,desc,shiftInReal);
			
			shiftOut = timelogDAOInt.getShiftOut(id);

			c = shiftIn.compareTo(yDate + " " +"22:00:00");
			if(c>=0)
			{
				shiftOut = nowDate() + " " + shiftOut;
			}
			else
			{
				shiftOut = yDate + " " + shiftOut;
			}
			
			shiftOut = yDate + " " + shiftOut;

			if(timelogDAOInt.checkTime(yDate,id)==0)
			{
				checkTime = false;
			}
			else
			{
				checkTime = true;
			}
			if(timelogDAOInt.checkTimeIn(yDate,id)==null)
			{
				checkIn = false;
			}
			else
			{
				checkIn = true;
			}
			if(timelogDAOInt.checkTimeOut(yDate,id)==null)
			{
				checkOut = false;
			}
			else
			{
				checkOut = true;
			}		
			//Check if there is a time
			if(checkTime==false)
			{	
				
				//Insert to db
				timelogDAOInt.insertRec(yDate,id); 
			}
			else
			{
				//Insert flag
				if(checkIn)
				{	
					
					//check first if late
					timeIn = timelogDAOInt.getTimeIn(yDate,id);

					SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
					SimpleDateFormat printFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					java.util.Date dateIn = parseFormat.parse(timeIn);
					
					timeIn=printFormat.format(dateIn);

					checkLate = checkLate(yDate,id,shiftIn,timeIn);

					
					if(checkOut)

					{
						timeOut = timelogDAOInt.getTimeOut(yDate,id);
							java.util.Date dateOut = parseFormat.parse(timeOut);
							timeOut = printFormat.format(dateOut);
							flagUndertime(yDate, id,shiftInReal,shiftIn,timeIn, shiftOut, timeOut,desc,checkLate);
							flagOvertime(yDate, id,shiftInReal,shiftIn,timeIn, shiftOut, timeOut,desc,checkLate);
						
					}
					else
					{
						timelogDAOInt.updateFlag(yDate,id,timelogDAOInt.getFlagId("No Time Out"));
					}
					
				}
				else if(checkIn == false)
				{
					timelogDAOInt.updateFlag(yDate,id,timelogDAOInt.getFlagId("No Time In")); 
				}
			}
			
		}
	}
	
	//Get Yesterday's Date
		private String getDate()
		{
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -1);
			return dateFormat.format(cal.getTime());
		}
		private String nowDate()
		{
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			//cal.add(Calendar.DATE);
			return dateFormat.format(cal.getTime());
		}
		//Get Shift In
		private String getShiftIn(int id, String desc,String shiftInReal) throws ParseException
		{
			 String shiftIn;
			 shiftIn ="";
			if(desc.equals("Flexi"))
			{
				shiftIn = addHour(shiftInReal,2);
			}
			else
			{
				shiftIn = addMinutes(shiftInReal,10);
				
			}
			 return shiftIn;
		}
		
		//check if late and return boolean
		private boolean checkLate(String d, int uid,String shiftIn,String timeIn) throws DataAccessException
		{
			int comp;
			boolean flag;
			flag = false;
			comp = timeIn.compareTo(shiftIn);
			if(comp > 0)
			{
				//late
				flag = true;

				timelogDAOInt.updateFlag(d,uid,timelogDAOInt.getFlagId("Late"));
			}
			return flag;
		}
		//flag for undertime
		private void flagUndertime(String d, int uid,String shiftInReal,String shiftIn,String timeIn, String shiftOut, String timeOut,String desc,boolean checkLate) throws DataAccessException, ParseException
		{
			int comp;
			String ntime;
			if(desc.equals("Flexi"))
			{	
				if((timeIn.compareTo(shiftInReal)) > 0)
				{
					ntime = addHour(timeIn,9);
				}
				else
				{
					ntime = addHour(shiftInReal,9);
				}
				
				comp = ntime.compareTo(timeOut);
				
			}
			else
			{
				if((timeIn.compareTo(shiftInReal) > 0) && (timeIn.compareTo(shiftIn) > 0))
				{
					ntime = addHour(timeIn,9);
				}
				else
				{
					ntime = addHour(shiftInReal,9);
				}
				//shift out for shifting
				comp = ntime.compareTo(timeOut);
			}
			if(comp > 0)
			{
				//undertime
				if(checkLate == false)
				{
					timelogDAOInt.updateFlag(d,uid,timelogDAOInt.getFlagId("Undertime"));
				}
				else
				{
					timelogDAOInt.updateFlag(d,uid,timelogDAOInt.getFlagId("Late / Undertime"));
				}
			}
		}
		private void flagOvertime(String d, int uid,String shiftInReal,String shiftIn,String timeIn, String shiftOut, String timeOut,String desc,boolean checkLate) throws DataAccessException, ParseException
		{
			int comp;
			String ntime;
			if(desc.equals("Flexi"))
			{
				if((timeIn.compareTo(shiftInReal)) > 0)
				{
					ntime = addHour(timeIn,9);
					ntime = addHour(ntime,1);
				}
				else
				{
					ntime = addHour(shiftInReal,9);
					ntime = addHour(ntime,1);
				}
			}
			else
			{
				if((timeIn.compareTo(shiftInReal) > 0) && (timeIn.compareTo(shiftIn) > 0))
				{
						ntime = addHour(timeIn,9);
						ntime = addHour(ntime,1);
				}
				else
				{
					ntime = addHour(shiftInReal,9);
					ntime = addHour(ntime,1);
				}
				
			}
			comp = timeOut.compareTo(ntime);
			if(comp >= 0)
			{
				//overtime
				if(checkLate == false)
				{
					timelogDAOInt.updateFlag(d,uid,timelogDAOInt.getFlagId("Overtime"));
				}
				else
				{
					timelogDAOInt.updateFlag(d,uid,timelogDAOInt.getFlagId("Late / Overtime"));
				}
			}
		}
		private String addHour(String timeIn, int n) throws ParseException
		{
			String ntime;
			ntime = "";
			SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar c = Calendar.getInstance();
			c.setTime(time.parse(timeIn));
			c.add(Calendar.HOUR,n);
			ntime = time.format(c.getTime());
			return ntime;
		}
		private String addMinutes(String timeIn, int n) throws ParseException
		{
			String ntime;
			ntime = "";
			SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar c = Calendar.getInstance();
			c.setTime(time.parse(timeIn));
			c.add(Calendar.MINUTE,n);
			ntime = time.format(c.getTime());
			return ntime;
		}
		
		@Override
		public String validateInput(int id,String name,String from, String to) throws ParseException, DataAccessException {
			String nwrponse;
			nwrponse = "";
			if(id == 0 || name.equals(""))
			{
				nwrponse = "No selected Employee";
			}
			else
			{
				if(from.equals("") || to.equals(""))
				{
					nwrponse = "Incomplete Input";
				}
				else
				{
					SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-mm-dd" );  
						java.util.Date dfrom = sdf.parse(from);  
						java.util.Date dto = sdf.parse(to);
				
					if(dfrom.before(dto) || dfrom.equals(dto))
					{
						if(timelogDAOInt.checkData(id, from, to)>0)
						{
							nwrponse = "OK";
						}
						else
						{
							nwrponse = "No Data";
						}
						
					}
					else
					{
						nwrponse = "Invalid Input"; 
					}
				}
			}
			
			return nwrponse;
		}

		@Override
		public List retrieveSubordinates(int id) throws DataAccessException {
			
			subMap.clear();
			subMap = timelogDAOInt.retrieveSubordinatesMap(id);
			return timelogDAOInt.retrieveSubordinates(id);
		}

		@Override
		public List<TimeLogging> retrieveTimelog(String name, String from,
				String to) throws DataAccessException {
		
			//method internal only
			//paramater(name)
			//get value
			int id = getIDMap(name);
			return timelogDAOInt.retrieveTimelog(id, from, to);
			
		}
		
		public int getIDMap(String name)
		{
			int k;
			k = 0;
			Object val;
			for(Object key : subMap.keySet())
			{
				val = subMap.get(key);
				if(val.equals(name))
				{
					k = (Integer) key;
				}
			}
			return k;
		}
		
		@Override
		public List<TimeLogging> retrieveMylog(int id, String from,
				String to) throws DataAccessException {
	
			return timelogDAOInt.retrieveMylog(id, from, to);
		}

		@Override
		public String checkUser(int id) throws DataAccessException {
	
			String user,position;
			user = "";
			int levelID;
			//manager

			/*levelID = timelogDAOInt.getLevelId(id);
	
			if(levelID >= 5)
			{
				user = "manager";
			}
			else
			{
				user = "";
			}*/
			if(timelogDAOInt.isSupervisor(id))
			{
				user = "manager";

			}
			else
			{
				user = "";
			}

		
			
			//hr
			/*position = timelogDAOInt.getPosition(id);
			if(position.equals("HR Officer"))
			{
				user = "hr";
			}
			else
			{
				if(user.equals("manager"))
				{
					user = "manager";
				}
				else
				{
					user = "";
				}
			}*/
			if(timelogDAOInt.isHR(id).equals("Human Resources Department"))
			{
				user = "hr";
			}
			else
			{
				if(user.equals("manager"))
				{
					user = "manager";
				}
				else
				{
					user = "";
				}
			}
			return user;
		}
		
		@Override
		public String checkName(String name) {
			String newresponse;
			newresponse= "";
			if (name.equals("")){
				newresponse = "Invalid Input";
			}else{
				newresponse = "OK";
			}

			return newresponse;
		}

		@Override
		public List<Employee> searchEmployees(String name)
				throws DataAccessException {
			
			return timelogDAOInt.searchEmployees(name);
		}
		
		@Override
		public void logTimeIn(TimeLogging timelogs) throws DataAccessException{
			
			employeeid = user.getEmployeeId();
						
			int count;
			count =	timelogDAOInt.validatetimeIn(employeeid);
			
			if(count == 0){
				
				timelogDAOInt.insertTimeIn(employeeid, timelogs);
			}
		}
		
		@Override
		public int getEmployeeId(){
			int empID;
			empID = employeeid;
			
			return empID;
			
		}
		
		@Override
		public void logTimeOut(TimeLogging timeLogging) throws DataAccessException, ParseException{
			
			timelogDAOInt.validateout(employeeid, timeLogging);
			//labas si hourscompute, with method service
			//
		}

}
