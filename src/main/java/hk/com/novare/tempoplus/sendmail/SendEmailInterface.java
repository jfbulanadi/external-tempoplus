package hk.com.novare.tempoplus.sendmail;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.mail.Message;

public interface SendEmailInterface {

	void setConnection(Connection connection);
	
	void closeConnection();
	
	void sendMail(boolean isByDepartment, String logsRow, Message message);
	
	void executeLog();
	
	void setLogQueryByDep();
	
	
	ResultSet retrieveEmail(int departmentId);
	
	ResultSet retrieveDateLogs();
	
	ResultSet retrieveLogs(String date);
	
	ResultSet retrieveNames();
	
	ResultSet retrieveSingleRecipient(String email);
	
	ResultSet retrieveDepartments();
	
	ResultSet retrieveDepartmentId(String departmentName);
	
	ResultSet retrievePeriods();
	
	File[] retrieveFiles(File folderName);
}
