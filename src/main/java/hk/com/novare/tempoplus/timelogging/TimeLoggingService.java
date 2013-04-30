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
		// TODO Auto-generated method stub
		int id;
		String desc,tIn,tOut,sIn,sInF,sOut;
		boolean cTime,cIn,cOut,cLate;
		cLate = false;
		final String yDate = getDate();
		final int cUser = timelogDAOInt.countUser();
		for(i = 0; i<cUser; i++)
		{
			id = timelogDAOInt.getUserID(i);
			desc = timelogDAOInt.getShiftDesc(id);
			
			sInF = timelogDAOInt.getShiftInReal(desc);
			sInF =  yDate + " " + sInF;
			
			
			sIn = getShiftIn(id,desc,sInF);
			
			sOut = timelogDAOInt.getShiftOut(id);
			sOut = yDate + " " + sOut;
			
			if(timelogDAOInt.checkTime(yDate,id)==0)
			{
				cTime = false;
			}
			else
			{
				cTime = true;
			}
			if(timelogDAOInt.checkTimeIn(yDate,id)==null)
			{
				cIn = false;
			}
			else
			{
				cIn = true;
			}
			if(timelogDAOInt.checkTimeOut(yDate,id)==null)
			{
				cOut = false;
			}
			else
			{
				cOut = true;
			}		
			//Check if there is a time
			if(cTime==false)
			{	
				
				//Insert to db
				timelogDAOInt.insertRec(yDate,id); 
			}
			else
			{
				//Insert flag
				if(cIn == true)
				{	
					//check first if late
					tIn = timelogDAOInt.getTimeIn(yDate,id);
					
					SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
					SimpleDateFormat printFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					java.util.Date dateIn = parseFormat.parse(tIn);
					
					tIn=printFormat.format(dateIn);
					cLate = checkLate(yDate,id,sIn,tIn);
					
					System.out.println("sIn: " + sIn);
					System.out.println("tIn: " + tIn);
					System.out.println("cLate: "+ cLate);
					
					if(cOut==true)
					{
						tOut = timelogDAOInt.getTimeOut(yDate,id);
						try {
							java.util.Date dateOut = parseFormat.parse(tOut);
							tOut = printFormat.format(dateOut);
							
							System.out.println("sOut: " + sOut);
							System.out.println("tOut: " + tOut);
							
							flagUndertime(yDate, id,sInF,sIn,tIn, sOut, tOut,desc,cLate);
							flagOvertime(yDate, id,sInF,sIn,tIn, sOut, tOut,desc,cLate);
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					else
					{
						timelogDAOInt.updateFlag(yDate,id,timelogDAOInt.getFlagId("No TimeOut"));
					}
					
				}
				else if(cIn == false)
				{
					timelogDAOInt.updateFlag(yDate,id,timelogDAOInt.getFlagId("No TimeIn")); 
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
		//Get Shift In
		private String getShiftIn(int id, String desc,String sInF) throws ParseException
		{
			 String sIn;
			 sIn ="";
			if(desc.equals("Flexi"))
			{
				sIn = addHour(sInF,2);
			}
			else
			{
				sIn = addMinutes(sInF,10);
			}
			 return sIn;
		}
		
		//check if late and return boolean
		private boolean checkLate(String d, int uid,String sin,String tin) throws DataAccessException
		{
			int comp;
			boolean flag;
			flag = false;
			comp = tin.compareTo(sin);
			if(comp > 0)
			{
				//late
				flag = true;

				timelogDAOInt.updateFlag(d,uid,timelogDAOInt.getFlagId("Late"));
			}
			return flag;
		}
		//flag for undertime
		private void flagUndertime(String d, int uid,String sinf,String sin,String tin, String sout, String tout,String desc,boolean clate) throws DataAccessException, ParseException
		{
			int comp;
			String ntime;
			if(desc.equals("Flexi"))
			{	
				if((tin.compareTo(sinf)) > 0)
				{
					ntime = addHour(tin,9);
				}
				else
				{
					ntime = addHour(sinf,9);
				}
				
				comp = ntime.compareTo(tout);
				
			}
			else
			{
				if((tin.compareTo(sinf) > 0) && (tin.compareTo(sin) > 0))
				{
					ntime = addHour(tin,9);
				}
				else
				{
					ntime = addHour(sinf,9);
				}
				//shift out for shifting
				comp = ntime.compareTo(tout);
			}
			if(comp > 0)
			{
				//undertime
				if(clate == false)
				{
					timelogDAOInt.updateFlag(d,uid,timelogDAOInt.getFlagId("Undertime"));
				}
				else
				{
					timelogDAOInt.updateFlag(d,uid,timelogDAOInt.getFlagId("Late/Undertime"));
				}
			}
		}
		private void flagOvertime(String d, int uid,String sinf,String sin,String tin, String sout, String tout,String desc,boolean clate) throws DataAccessException, ParseException
		{
			int comp;
			String ntime;
			if(desc.equals("Flexi"))
			{
				if((tin.compareTo(sinf)) > 0)
				{
					ntime = addHour(tin,9);
					ntime = addHour(ntime,1);
				}
				else
				{
					ntime = addHour(sinf,9);
					ntime = addHour(ntime,1);
				}
			}
			else
			{
				if((tin.compareTo(sinf) > 0) && (tin.compareTo(sin) > 0))
				{
						ntime = addHour(tin,9);
						ntime = addHour(ntime,1);
				}
				else
				{
					ntime = addHour(sinf,9);
					ntime = addHour(ntime,1);
				}
				
			}
			comp = tout.compareTo(ntime);
			if(comp >= 0)
			{
				//overtime
				if(clate == false)
				{
					timelogDAOInt.updateFlag(d,uid,timelogDAOInt.getFlagId("Overtime"));
				}
				else
				{
					timelogDAOInt.updateFlag(d,uid,timelogDAOInt.getFlagId("Late/Overtime"));
				}
			}
		}
		private String addHour(String tin, int n) throws ParseException
		{
			String ntime;
			ntime = "";
			SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar c = Calendar.getInstance();
			c.setTime(time.parse(tin));
			c.add(Calendar.HOUR,n);
			ntime = time.format(c.getTime());
			return ntime;
		}
		private String addMinutes(String tin, int n) throws ParseException
		{
			String ntime;
			ntime = "";
			SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar c = Calendar.getInstance();
			c.setTime(time.parse(tin));
			c.add(Calendar.MINUTE,n);
			ntime = time.format(c.getTime());
			return ntime;
		}
		
		@Override
		public String ValidateInput(int id,String name,String from, String to) throws ParseException {
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
						nwrponse = "OK";
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
			
			String newresponse = "";
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
		public void logTimeIn(TimeLogging timelogs){
			
			employeeid = user.getEmployeeId();
						
			try {
			int count =	timelogDAOInt.validatetimeIn(employeeid);
			
			if(count == 0){
				
				timelogDAOInt.insertTimeIn(employeeid, timelogs);
			}
				
			} catch (DataAccessException e) {

				e.printStackTrace();
			}
			
			
		}
		
		@Override
		public int getEmployeeId(){
			int empID = employeeid;
			
			return empID;
			
		}
		
		
		@Override
		public void logTimeOut(TimeLogging timeLogging) throws DataAccessException{
			
			timelogDAOInt.validateout(employeeid, timeLogging);
		}

}
