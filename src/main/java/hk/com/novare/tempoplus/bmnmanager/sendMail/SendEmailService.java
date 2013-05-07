package hk.com.novare.tempoplus.bmnmanager.sendmail;

import hk.com.novare.tempoplus.useraccount.user.User;
import hk.com.novare.tempoplus.useraccount.user.UserService;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.SessionAttributes;
@SessionAttributes({ "userEmployeeId", "userEmail" })
@Repository("sendEmailService")
public class SendEmailService {
	
	private String username;
	private String password;
	private String department;
	private String email;
	private Session session;
	

	private final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	private final String MAIL_SERVER = "mails.novare.com.hk";
	private final String MAIL_EXTENSION = "@novare.com.hk";
	private final String FILE_DIRECTORY = System.getProperty( "user.home" ) + "/";
	private final Logger logger = Logger.getLogger(SendEmailService.class);

	
	@Autowired
	SendEmailInterface sendEmailDao;

	@Autowired
	UserService userService;

	@Autowired
	User user;

	@Autowired
	DataSource dataSource;

	// Sending Mail Service ( Initiation of Mail Properties )
	public void prepMailProps() {

		Properties props = new Properties();

		props.put("mail.smtp.host", MAIL_SERVER);
		props.put("mail.smtp.user", username);
		// To use TLS
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.password", password);

		// Authenticate accessing mail server
		session = Session.getInstance(props, new javax.mail.Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication(username, password);
			}
		});
	}

	public boolean validatePassword(String password) {
		boolean isCorrect = false;

		if (!(password == null)) {

			final String hashedPassword = userService.hashPassword(password);

			logger.info("Current User: " + user.getEmail());

			isCorrect = userService.validateNewPassword(hashedPassword,
					user.getPassword());

			if (isCorrect) {
				this.password = password;
				this.username = user.getEmail() + MAIL_EXTENSION;

				logger.info("Sender: " + username);
			}
		}
		return isCorrect;
	}

	public ArrayList<SingleRecipientPojo> getNames() throws SQLException {

		this.initConnection();
		
		logger.info("Getting Names");

		final ResultSet resultSet = sendEmailDao.getNames();

		final ArrayList<SingleRecipientPojo> singleRecipientPojos = new ArrayList<SingleRecipientPojo>();

		while (resultSet.next()) {

			final SingleRecipientPojo tempSingleRecipientPojo = new SingleRecipientPojo();

			tempSingleRecipientPojo.setFirstName(resultSet.getString(1));
			tempSingleRecipientPojo.setLastName(resultSet.getString(2));
			tempSingleRecipientPojo.setEmail(resultSet.getString(3));

			singleRecipientPojos.add(tempSingleRecipientPojo);

		}

		sendEmailDao.closeConnection();
		return singleRecipientPojos;
	}

	public ArrayList<MailPojo> retrieveEmails(String department,
			String msgBody, String period, String msgTitle)
			throws SQLException, ClassNotFoundException {

		this.initConnection();
		
		final ResultSet resultSet = sendEmailDao.getEmail(department);
		final ArrayList<MailPojo> mailPojo = new ArrayList<MailPojo>();

		this.department = department;

		// Create and assign the mailPojo Properties

		try {

			while (resultSet.next()) {

				final MailPojo tempMailPojo = convertToPojo(true, resultSet,
						msgBody, period, msgTitle);

				mailPojo.add(tempMailPojo);
			}

		} catch (SQLException e) {

			logger.info("Transferring resultSet to ArrayList Error");
		}

		sendEmailDao.closeConnection();
		return mailPojo;
	}

	public ArrayList<String> retrieveDateLogs() throws SQLException {

		this.initConnection();
		
		final ResultSet resultSet = sendEmailDao.getDateLogs();
		final ArrayList<String> dateLogs = new ArrayList<String>();

		while (resultSet.next()) {
			dateLogs.add(resultSet.getString(1));
		}

		sendEmailDao.closeConnection();
		return dateLogs;
	}

	public ArrayList<String> retrieveDepartments() throws SQLException {

		this.initConnection();
		
		logger.info("Getting Departments");

		final ResultSet resultSet = sendEmailDao.getDepartments();
		final ArrayList<String> departments = new ArrayList<String>();

		while (resultSet.next()) {
			if (!(resultSet.getString(1) == null))
				departments.add(resultSet.getString(1));
		}

		sendEmailDao.closeConnection();
		return departments;
	}

	public ArrayList<SendMailLogsPojo> retrieveLogs(String date)
			throws SQLException {

		this.initConnection();
		
		
		final ResultSet resultSet = sendEmailDao.getLogs(date);

		final ArrayList<SendMailLogsPojo> sendMailLogsPojos = new ArrayList<SendMailLogsPojo>();

		while (resultSet.next()) {

			final SendMailLogsPojo tempSendMailLogsPojos = new SendMailLogsPojo();

			tempSendMailLogsPojos.setDate(resultSet.getString(1));
			tempSendMailLogsPojos.setRecipient(resultSet.getString(2));
			tempSendMailLogsPojos.setStatus(resultSet.getString(3));

			sendMailLogsPojos.add(tempSendMailLogsPojos);
		}

		sendEmailDao.closeConnection();
		return sendMailLogsPojos;
	}

	public void sendMailByDep(ArrayList<MailPojo> mailPojos)
			throws ClassNotFoundException, SQLException {

		this.initConnection();
		
		final Date date = new Date();

		final String logsRow = department + " " + DATE_FORMAT.format(date);

		// Set up the Logs Properties
		sendEmailDao.setLogQueryByDep();

		// Start Sending 1 by 1
		for (MailPojo tempMailPojo : mailPojos) {

			try {
				// Set Up Message for an employee
				final Message message = setUpMessage(tempMailPojo);

				// Send Mail
				sendEmailDao.sendMail(true, logsRow, message);

			} catch (Exception e) {
				logger.info("Preparing Message error: " + e.toString());
			}

		}
		// Save the logs to database
		
		sendEmailDao.executeLog();
		sendEmailDao.closeConnection();
	}

	public String sendByIndividual(String msgBody, String period,
			String msgTitle) throws SQLException, MessagingException {
		
		this.initConnection();
		
		final String fixedPeriod = FILE_DIRECTORY + this.email + "/" + period;

		final String logsRow = null;

		final ResultSet resultSet = sendEmailDao.getSingleRecipient(this.email);

		String status = null;

		logger.info(" Recipient email: " + this.email);

		try {

			while (resultSet.next()) {

				final MailPojo mailPojo = convertToPojo(false, resultSet,
						msgBody, fixedPeriod, msgTitle);

				final Message message = setUpMessage(mailPojo);

				sendEmailDao.sendMail(false, logsRow, message);

				status = "Success Sending Timesheet";

			}
		} catch (Exception e) {

			status = "Failed Sending Timesheet, Error: " + e.toString();
			logger.info("ERRRR: " + e.toString());
		}
		// sendEmailDao.executeLog();

		sendEmailDao.closeConnection();
		return status;
	}

	private Message setUpMessage(MailPojo tempMailPojo)
			throws MessagingException {
		// Create new Message
		Message message = new MimeMessage(session);
		try {
			// Set Message Properties
			message.setFrom(new InternetAddress(tempMailPojo.getFromUsername()));

			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(tempMailPojo.getEmailRecipient()));

			message.setSubject(tempMailPojo.getMsgTitle());

			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();

			// Fill the message
			messageBodyPart.setContent(tempMailPojo.getMsgBody(),
					"text/html; charset=utf-8");

			// Create a multipart message
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Part two is attachment
			messageBodyPart = new MimeBodyPart();

			FileDataSource source = new FileDataSource(
					tempMailPojo.getFileUrl());

			messageBodyPart.setDataHandler(new DataHandler(source));

			messageBodyPart.setFileName(tempMailPojo.getFileName());

			multipart.addBodyPart(messageBodyPart);

			// Send the complete message parts
			message.setContent(multipart);
		} catch (Exception e) {
			logger.info("Preparing the message failed: " + e.toString());
		}
		return message;
	}

	private MailPojo convertToPojo(boolean isByDepartment, ResultSet resultSet,
			String msgBody, String period, String msgTitle) throws SQLException {

		logger.info("Setting MailPojo Props");

		MailPojo tempMailPojo = new MailPojo();

		tempMailPojo.setEmailRecipient(resultSet.getString(8)
				+ MAIL_EXTENSION);

		if (!isByDepartment) {
			tempMailPojo.setFileName(period.replace(
					FILE_DIRECTORY + resultSet.getString(8) + "/", ""));

			tempMailPojo.setFileUrl(period);
		} else {
			tempMailPojo.setFileName(resultSet.getString(8) + "_Timesheet.xls");

			tempMailPojo.setFileUrl(FILE_DIRECTORY + resultSet.getString(8)
					+ "/" + resultSet.getString(8) + "_TimeSheet_" + period
					+ ".xls");

		}

		tempMailPojo.setFromUsername(username);

		tempMailPojo.setMsgBody(msgBody);

		tempMailPojo.setMsgTitle(msgTitle);

		logger.info("************************************");
		logger.info("Recipient: " + tempMailPojo.getEmailRecipient());
		logger.info("File Name: " + tempMailPojo.getFileName());
		logger.info("File Url: " + tempMailPojo.getFileUrl());
		logger.info("Sender: " + tempMailPojo.getFromUsername());

		return tempMailPojo;
	}

	public ArrayList<String> getFileList(String employee) {

		final ArrayList<String> fileList = new ArrayList<String>();
		
		final String fixedString = employee.replace(".com.hk ", ".com.hk")
				.replace("	", "").replace("\n", " ");

		final int lastSpace = fixedString.lastIndexOf(" ") + 1;

		final String emailStr = fixedString.substring(lastSpace,
				fixedString.length());

		final String folderName = emailStr.substring(0, emailStr.indexOf('@'));

		logger.info("folderName: " + folderName);

		final File directory = new File(FILE_DIRECTORY + folderName + "/");

		logger.info("directory: " + directory.toString());

		try {

			final File[] files = sendEmailDao.getFiles(directory);

		
			this.email = folderName;

			for (int x = 0; x < files.length; x++) {
				final String fileLoc = files[x].toString();
				final String fixedfileLoc = fileLoc.replace(FILE_DIRECTORY
						+ folderName + "/", "");
				fileList.add(fixedfileLoc);
				logger.info("Files To display: " + fixedfileLoc);
			}
		} catch (NullPointerException e) {
			logger.info("There's No File for " + folderName);
		}

		return fileList;
	}

	private Connection initConnection() {

		Connection connection = null;

		try {

			connection = dataSource.getConnection();
			logger.info("Successed loading driver, Setting Dao SQL Connection");

			// Set the Dao SQL Connection
			sendEmailDao.setConnection(connection);

		} catch (Exception e) {

			logger.info("Cant Load Driverrrrr");

		}

		return connection;
	}
}