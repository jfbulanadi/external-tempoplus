package hk.com.novare.tempoplus.bmnmanager.shiftmanager;



import java.util.List;

public interface Shifting {
 void addShifting(String shiftname, String timein, String timeout);
 public List<ShiftingPOJO> showShift() throws DataAccessException;
 public List<ShiftingPOJO> getShifting(int shiftid) throws DataAccessException;
 public void editshifing(int id, String shiftname, String timein, String timeout);
}
