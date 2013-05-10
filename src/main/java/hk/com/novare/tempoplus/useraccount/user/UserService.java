package hk.com.novare.tempoplus.useraccount.user;

import hk.com.novare.tempoplus.employee.Employee;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.inject.Inject;


public class UserService {
	
	@Inject UserDao userDao;
	@Inject User user;
	@Inject Employee employee;
	
	//user login validation
		public boolean validateLogInAccess(String userEmail, String userPassword){
			
			boolean checkAccess = false;
			
		 	//encrypt user password input 
			final String hashedPassword = hashPassword(userPassword);
			
			//find employee id in db using user email input
		 	int employeeId = userDao.searchEmployeeIdInDatabase(userEmail);
		 	userDao.searchEmployeePassword(employeeId);
		 	
		 	
		 	if(user.getEmail().equals(userEmail) 
		 			&& user.getPassword().equals(hashedPassword)){
		 		
		 		checkAccess = true;
		 		user.setEmail(userEmail);
		 		user.setPassword(hashedPassword);
		 		user.setEmployeeId(employeeId);
		 	}
		 	
		 	else{
		 		checkAccess = false;
		 	}
		 	
		 	return checkAccess;
		}
		
	//conversion of input password for validation in database
		public String hashPassword(String password){

			 String convertedPassword =  "";
			 MessageDigest messageDigest;
			try {
				messageDigest = MessageDigest.getInstance("MD5");
				messageDigest.reset();
				messageDigest.update(password.getBytes());
				byte[] digestByte = messageDigest.digest();
				BigInteger bigInt = new BigInteger(1, digestByte);
				 
				String hashText = bigInt.toString(16);
				 
				while(hashText.length() <32){
					 hashText = "0" + hashText;
				 }
				convertedPassword = hashText;
				
			} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
			}
			 
			return convertedPassword;
		}
		
			
		//----------------retrieve user information for profile viewing----------------
		public User retrieveUserInformation(String userEmail) {

			return userDao.retrieveUserDetails(userEmail).get(0);
			
		}
				
		//----------------retrieve supervisor details for profile viewing---------------------------
		public Employee retrieveSupervisorInformation(int supervisorId) {

			return userDao.findSupervisor(supervisorId).get(0);
		}
		
		
		//----------------clear user information from object----------------
				public List<User> clearUserInformation(String userEmail) {

					return userDao.retrieveUserDetails(userEmail);
					
				}
						
		//---------------clear supervisor details from object---------------------------
				public List<Employee> clearSupervisorInformation(int supervisorId) {

					return userDao.findSupervisor(supervisorId);
				}
				
		
		
		

		//-----------------check id password input matches password from database------------
		
		public boolean matchCurrentPassword(String currentPassword, String validatedPassword){
			boolean isMatched = false;
			
			if(validatedPassword.equals(currentPassword)){
				isMatched = true;
				
			}else{
				isMatched = false;
			}
			
			return isMatched;
		}
		
		
		//------------check the new password input if it matches the repeated password input--------
		
		public boolean validateNewPassword(String newPassword, String repeatedNewPassword){
			boolean isMatched = false;
			if(newPassword.equals(repeatedNewPassword)){
				if(repeatedNewPassword.length() >= 6){
					isMatched = true;
				}
				else{
					isMatched = false;
				}
			}
			else{
				isMatched = false;
			}
			
			return isMatched;
		}
		
		//--------------save new password from change password------------------------
		public void saveNewPassword(int employeeId, String newPassword){
			
			userDao.saveNewPasswordToDB(employeeId, newPassword);
		}
		
		
		
		
}
