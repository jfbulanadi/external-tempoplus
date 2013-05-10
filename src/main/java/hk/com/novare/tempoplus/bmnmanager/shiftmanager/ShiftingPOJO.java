package hk.com.novare.tempoplus.bmnmanager.shiftmanager;

public class ShiftingPOJO {
 private String shiftname;
 private String timein;
 private String timeout;
 private int shiftid;
 
public int getShiftid(){
		return shiftid;
}
public void setShiftid(int shiftid){
		this.shiftid = shiftid;
}  
public String getShiftname() {
	return shiftname;
}
public void setShiftname(String shiftname) {
	this.shiftname = shiftname;
}
public String getTimein() {
	return timein;
}
public void setTimein(String timein) {
	this.timein = timein;
}
public String getTimeout() {
	return timeout;
}
public void setTimeout(String timeout) {
	this.timeout = timeout;
}
 
}
