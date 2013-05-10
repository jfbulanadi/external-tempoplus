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
	
	
	ResultSet getEmail(String department);
	
	ResultSet getDateLogs();
	
	ResultSet getLogs(String date);
	
	ResultSet getNames();
	
	ResultSet getSingleRecipient(String email);
	
	ResultSet getDepartments();
	
	File[] getFiles(File folderName);
}
