package hk.com.novare.tempoplus.bmnmanager.shiftmanager;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

public class ShiftingDao implements Shifting {

	@Inject
	DataSource dataSource;

	@Override
	public void addShifting(String shiftname, String timein, String timeout) {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			PreparedStatement ps = connection
					.prepareStatement("INSERT INTO shifts (description, timeIn, timeOut) VALUES (?, ?, ?)");
			ps.setString(1, shiftname);
			ps.setString(2, timein);
			ps.setString(3, timeout);
			ps.executeUpdate();
		} catch (SQLException e) {

		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {

				}
			}
		}

	}

	@Override
	public List<ShiftingPOJO> showShift() throws DataAccessException {
		Connection connection = null;
		final ArrayList<ShiftingPOJO> shiftingarraylist = new ArrayList<ShiftingPOJO>();

		try {
			connection = dataSource.getConnection();
			PreparedStatement ps = connection
					.prepareStatement("Select id, description, timeIn, timeOut FROM shifts");
			ResultSet resultset = ps.executeQuery();
			
			while (resultset.next()) {
				final int shiftid  = resultset.getInt(1);
				final String shiftname = resultset.getString(2);
				final String timein = resultset.getString(3);
				final String timeout = resultset.getString(4);
				
				final ShiftingPOJO shiftingpojo = new ShiftingPOJO();
				shiftingpojo.setShiftid(shiftid);
				shiftingpojo.setShiftname(shiftname);
				shiftingpojo.setTimein(timein);
				shiftingpojo.setTimeout(timeout);
				
				
				shiftingarraylist.add(shiftingpojo);

			}
			resultset.close();
			return shiftingarraylist;
			
		} catch (SQLException e) {
			throw new DataAccessException(e);

		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new DataAccessException("cannot close", e);

				}
				


			}
			

		}
		
		
	}
	
	

	@Override
	public List<ShiftingPOJO> getShifting(int shiftid) throws DataAccessException {
		Connection connection = null;
		final ArrayList<ShiftingPOJO> shiftingarraylist = new ArrayList<ShiftingPOJO>();

		try {
			connection = dataSource.getConnection();
			PreparedStatement ps = connection
					.prepareStatement("Select id, description, timeIn, timeOut FROM shifts where id = ?");
			ps.setInt(1, shiftid);
			ResultSet resultset = ps.executeQuery();
			
			while (resultset.next()) {
				final int rsshiftid  = resultset.getInt(1);
				final String shiftname = resultset.getString(2);
				final String timein = resultset.getString(3);
				final String timeout = resultset.getString(4);
			
				
				final ShiftingPOJO shiftingpojo = new ShiftingPOJO();
				shiftingpojo.setShiftid(rsshiftid);
				shiftingpojo.setShiftname(shiftname);
				shiftingpojo.setTimein(timein);
				shiftingpojo.setTimeout(timeout);
				
				
				shiftingarraylist.add(shiftingpojo);

			}
			resultset.close();
			return shiftingarraylist;
			
		} catch (SQLException e) {
			throw new DataAccessException(e);

		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new DataAccessException("cannot close", e);

				}
				


			}
			

		}
	}

	@Override
	public void editshifing(int id, String shiftname, String timein, String timeout ) {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			PreparedStatement ps = connection
					.prepareStatement("UPDATE shifts SET description= ?, timeIn=?, timeOut=? WHERE id= ?");
			ps.setString(1, shiftname);
			ps.setString(2, timein);
			ps.setString(3, timeout);
			ps.setInt(4, id);
			ps.executeUpdate();
		} catch (SQLException e) {

		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {

				}
			}
		}

		
	}

}
