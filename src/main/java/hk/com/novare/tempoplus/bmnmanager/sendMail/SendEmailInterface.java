package hk.com.novare.tempoplus.bmnmanager.sendMail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;

public interface SendEmailInterface {

	ResultSet getEmail(String department) throws SQLException, ClassNotFoundException;
	void sendEmail(ArrayList<String> email, MailPojo mailPojo, Session session) throws AddressException, MessagingException;
}
