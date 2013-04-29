package hk.com.novare.tempoplus.bmnmanager.sendMail;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.DriverManager;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.inject.Inject;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("sendEmailDao")
public class SendEmailDao implements SendEmailInterface {

	@Inject
	DataSource dataSource;
	

	
	@Override
	public ResultSet getEmail(String department) throws SQLException,
			ClassNotFoundException {
		
		Connection connection = null;
		PreparedStatement ps = null;

		try {
		//	connection = dataSource.getConnection();
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost/employees", "root", "dreamer");
			System.out.println("Successed loading driver");
		} catch (Exception e) {
			System.out.println("Cant Load Driver");
		}

		
		if(department.equals("ALL")){
		ps = connection
				.prepareStatement("SELECT * FROM employees");
		} else{
			ps = connection.prepareStatement("SELECT * FROM employees where department='"+department+"'");
		}
			
		
			ResultSet resultSet = (ResultSet) ps.executeQuery();
		System.out.println("QueryExecuted");


		return resultSet;
	}

	@Override
	public void sendEmail(ArrayList<String> email, MailPojo mailPojo, Session session) throws AddressException, MessagingException {
	

		try {
			for (String emailRecipient : email) {
				System.out.println("Sending To: "+emailRecipient);
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(mailPojo.getFromUsername()));
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(emailRecipient));
				message.setSubject(mailPojo.getMsgTitle());
				


				 // Create the message part 
		         BodyPart messageBodyPart = new MimeBodyPart();

		         // Fill the message
		         messageBodyPart.setContent(mailPojo.getMsgBody(), "text/html; charset=utf-8");
		         
		         // Create a multipar message
		         Multipart multipart = new MimeMultipart();

		         // Set text message part
		         multipart.addBodyPart(messageBodyPart);

		         // Part two is attachment
		         messageBodyPart = new MimeBodyPart();
		        
		         FileDataSource source = new FileDataSource(mailPojo.getFileUrl());
		         messageBodyPart.setDataHandler(new DataHandler(source));
		         messageBodyPart.setFileName(mailPojo.getFileName());
		         multipart.addBodyPart(messageBodyPart);

		         // Send the complete message parts
		         message.setContent(multipart );
				
				
				
				Transport.send(message);
				System.out.println("Message Sent: " + emailRecipient);
			}
		} catch (MessagingException e) {
			System.out.println(e.toString());
		}

	}

}

