package hk.com.novare.tempoplus.sendmail;

import java.io.File;
import java.io.FilenameFilter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository("sendEmailDao")
public class SendEmailDAO implements SendEmailInterface {

	private Connection connection;
	private PreparedStatement statusQuery;
	private PreparedStatement ps = null;
	private ResultSet resultSet = null;

	private final String LOGGING_QUERY = "insert into emaillogs(date,recipient,status) values(?,?,?)";
	private final String GET_EMAIL_QUERY = "SELECT * FROM employees";
	private final String GET_LOGS_QUERY = "SELECT date, recipient, status FROM emaillogs where date='";
	private final String GET_EMPLOYEES_NAME_QUERY = "SELECT firstname,lastname,email FROM employees ORDER BY firstname ASC";
	private final String GET_EMPLOYEE_NAME = "SELECT * FROM employees where email ='";
	private final String GET_DEPARTMENTS = "SELECT DISTINCT departmentId FROM employees";

	private final Logger logger = Logger.getLogger(SendEmailDAO.class);

	
	@Override
	public void setConnection(Connection connection) {
		this.connection = connection;
		logger.info("Logger");
	}
	
	
	@Override
	public void closeConnection(){
		try {
		
			logger.info("Closing SQL Connection");
			this.connection.close();
			logger.info("SQL Connection Closed");
			
		} catch (SQLException e) {
			logger.info("Cant Close SQLConnection, Error: " + e.toString());
		}
	}

	@Override
	public void setLogQueryByDep() {
		try {
			statusQuery = connection.prepareStatement(LOGGING_QUERY);
		} catch (SQLException e) {

			logger.info("Error On Preparing Statement of Logging Query");
		}
	}

	@Override
	public ResultSet getEmail(String department) {

		if (department.equals("ALL")) {
			try {
				ps = connection.prepareStatement(GET_EMAIL_QUERY);
			} catch (SQLException e) {
				logger.info("Error on Getting All Email ");
			}
		} else {
			try {
				ps = connection.prepareStatement(GET_EMAIL_QUERY
						+ " where departmentId='" + department + "'");
			} catch (SQLException e) {
				logger.info("Error on Getting Email By: " + department);
			}
		}

		try {
			resultSet = (ResultSet) ps.executeQuery();
		} catch (SQLException e) {

			logger.info("Error on Executing Query of getting Emails");
		}

		return resultSet;
	}

	@Override
	public ResultSet getDateLogs() {

		logger.info("Getting Dates");

		try {
		
			ps = connection
					.prepareStatement("SELECT DISTINCT date FROM emaillogs");
			
			resultSet = (ResultSet) ps.executeQuery();
	
		} catch (SQLException e) {
			logger.info("Error on Getting Date Logs SQL");
		}

		return resultSet;
	}

	@Override
	public void sendMail(boolean isByDepartment, String logsRow, Message message) {

		// Initialize the Logs Status Attributes
		String status = "Failed Sending TimeSheet";
		String recipient = null;

		try {

			recipient = message.getRecipients(Message.RecipientType.TO)[0]
					.toString();

			logger.info("NOW SENDING MESSAGE TO: " + recipient);

			Transport.send(message);

			status = "Successed Sending TimeSheet";

			logger.info(status + " TO: " + recipient);

		} catch (MessagingException e1) {

			logger.info("Error On Transporting Message: " + e1.toString());
			status = "Failed Sending TimeSheet";
			logger.info(status + " TO: " + recipient);
		}

		if (isByDepartment) {

			try {
				
				statusQuery.setString(1, logsRow);

				statusQuery.setString(2, recipient);

				statusQuery.setString(3, status);

				statusQuery.addBatch();
			} catch (SQLException e) {

				System.out
						.println("Error on Preparing the parameters of Log Query: " + e.toString());
			}

		}
	}

	@Override
	public ResultSet getLogs(String date) {

		try {
			ps = connection.prepareStatement(GET_LOGS_QUERY + date + "'");
			resultSet = (ResultSet) ps.executeQuery();
		} catch (SQLException e) {
			logger.info("Error on Getting Logs Query");
		}

		logger.info("getting Logs Query Executed");

		return resultSet;
	}

	@Override
	public ResultSet getNames() {

		try {

			ps = connection.prepareStatement(GET_EMPLOYEES_NAME_QUERY);
			resultSet = (ResultSet) ps.executeQuery();

		} catch (SQLException e) {
			logger.info("Error on Getting Employee Names SQL");
		}

		return resultSet;
	}

	@Override
	public void executeLog() {
		try {
			logger.info("Setting Auto Commit to False");
			connection.setAutoCommit(false);

			logger.info("Executing Batch Query");
			statusQuery.executeBatch();

			logger.info("Committing");
			connection.commit();

		} catch (SQLException e) {
			logger.info("Error on Executing Log Query By Batch");
		}

	}

	@Override
	public ResultSet getSingleRecipient(String email) {

		try {
			logger.info("Getting Employee Name");

			ps = connection.prepareStatement(GET_EMPLOYEE_NAME + email + "'");

			logger.info("now getting the resultset");

			resultSet = (ResultSet) ps.executeQuery();

			logger.info("QueryExecuted");
		} catch (SQLException e) {
			logger.info("Error on Getting Employee Name");
		}
		return resultSet;
	}

	@Override
	public File[] getFiles(File folderName) {

		final File directory = folderName;

		return directory.listFiles(new FilenameFilter() {

			public boolean accept(File dir, String filename) {

				return filename.endsWith(".xls");

			}
		});
	}

	@Override
	public ResultSet getDepartments() {

		try {
 
			ps = connection.prepareStatement(GET_DEPARTMENTS);
			resultSet = (ResultSet) ps.executeQuery();

		} catch (SQLException e) {
			logger.info("Error on Getting Departments");
		}
		return resultSet;
	}

}
