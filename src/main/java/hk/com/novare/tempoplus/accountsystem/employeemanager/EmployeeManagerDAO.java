/*
 * @author apmreyes
 */

package hk.com.novare.tempoplus.accountsystem.employeemanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;


public class EmployeeManagerDAO {
	
	@Inject DataSource dataSource;
	Connection connection = null;
	boolean hasFound = false;
	
	public List<EmployeeManager> searchSubordinates(int supervisorId){
		List<EmployeeManager> subordinatesList = new ArrayList<EmployeeManager>();
		Connection connection = null;
		
		try {
			connection = dataSource.getConnection();
			
			final PreparedStatement searchSubordinatesStatement =
					connection.prepareStatement("select * from employees where " +
							"supervisorId = " + supervisorId);
			ResultSet resultSet = searchSubordinatesStatement.executeQuery();
			
			
			while(resultSet.next()){
				
				EmployeeManager subordinate = new EmployeeManager();
				
				final int subEmployeeId = resultSet.getInt("employeeId");
				final String subLastName = resultSet.getString("lastname");
				final String subFirstName = resultSet.getString("firstname");
				final String subMiddleName = resultSet.getString("middlename");
				final int subDepartmentId = resultSet.getInt("departmentId");
				final int subPositionId = resultSet.getInt("positionId");
				
				subordinate.setEmployeeId(subEmployeeId);
				subordinate.setLastName(subLastName);
				subordinate.setFirstName(subFirstName);
				subordinate.setMiddleName(subMiddleName);
				subordinate.setDepartmentId(subDepartmentId);
				subordinate.setPositionId(subPositionId);
				subordinate.setDepartment(findDepartmentName(subDepartmentId));
				subordinate.setPosition(findPositionName(subPositionId));
				
				subordinatesList.add(subordinate);
				}
			
			
		} catch (SQLException e) {
		}finally{
			try {
				connection.close();
			} catch (SQLException e) {
			}
		}
		
		return subordinatesList;
	}
	
	//--------find subordinate's department name--------------
		public String findDepartmentName(int departmentId){
			
			String department = " ";
			Connection connection = null;
				
				try{
					connection = dataSource.getConnection();
					
					final PreparedStatement retrieveDepartmentName = 
							connection.prepareStatement("select name from departments " + 
									"where id = ?");
					retrieveDepartmentName.setInt(1, departmentId);
		
					ResultSet rs = retrieveDepartmentName.executeQuery();
		
					while(rs.next()) {
						department= rs.getString("name");
					}
					rs.close();
					
				}catch(SQLException e){
				
				}finally{
					try {
						connection.close();
					} catch (SQLException e) {
					
					}
				}
				return department;
		}
		
		
		//------find subordinate's position name---------
		public String findPositionName(int positionId){
			
			String position = "";
			Connection connection = null;
				
				try{
					connection = dataSource.getConnection();
					
					final PreparedStatement retrievePositionName = 
							connection.prepareStatement("select description from positions " + 
									"where id = ?");
					retrievePositionName.setInt(1, positionId);
		
					ResultSet rs = retrievePositionName.executeQuery();
		
					while(rs.next()) {
						position = rs.getString("description");
					}
					rs.close();
					
				}catch(SQLException e){
					e.printStackTrace();
				}finally{
					try {
						connection.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				return position;
		}
		
	//-----------------------search for add subordinates-----------------
		
		public List<EmployeeManager> searchToAddSubordinates(String employeeName){
			List<EmployeeManager> subordinatesList = new ArrayList<EmployeeManager>();
			

			try {
				connection = dataSource.getConnection();
				
				final PreparedStatement searchSubordinatesStatement =
						connection.prepareStatement("SELECT * FROM employees WHERE " +
								"lastname LIKE '%" + employeeName + "%' OR firstname LIKE '%" +
								employeeName + "%'");
				ResultSet resultSet = searchSubordinatesStatement.executeQuery();
				System.out.println("[ @ search ]" + employeeName);
				
				while(resultSet.next()){
					System.out.println("[ @ result set ]" + employeeName);
					hasFound = true;
					
					EmployeeManager subordinate = new EmployeeManager();
					
					final int subEmployeeId = resultSet.getInt("employeeId");
					final String subLastName = resultSet.getString("lastname");
					final String subFirstName = resultSet.getString("firstname");
					final String subMiddleName = resultSet.getString("middlename");
					final int subDepartmentId = resultSet.getInt("departmentId");
					final int subPositionId = resultSet.getInt("positionId");
					
					subordinate.setEmployeeId(subEmployeeId);
					subordinate.setLastName(subLastName);
					subordinate.setFirstName(subFirstName);
					subordinate.setMiddleName(subMiddleName);
					subordinate.setDepartmentId(subDepartmentId);
					subordinate.setPositionId(subPositionId);
					subordinate.setDepartment(findDepartmentName(subDepartmentId));
					subordinate.setPosition(findPositionName(subPositionId));
					
					subordinatesList.add(subordinate);
					}
				resultSet.close();
				
			} catch (SQLException e) {
			}finally{
				try {
					connection.close();
				} catch (SQLException e) {
				}
			}

			return subordinatesList;
		}
		
		public boolean employeeIsFound(){
			return hasFound;
		}
		

}
