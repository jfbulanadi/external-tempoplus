package hk.com.novare.tempoplus.bmnmanager.sendMail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class SendEmailService {

	@Autowired
	SendEmailInterface sendEmailDao;

	@Autowired
	MailPojo mailPojo;

	private Session session;

	private final String username = "ernesto.hilvano@novare.com.hk";

	public void sendMail(String department, String msgBody) throws SQLException,
			ClassNotFoundException, AddressException, MessagingException {

		mailPojo.setFileName("timesheet.xls");
		mailPojo.setFileUrl("/home/esh/test.xls");
		mailPojo.setFromUsername(username);
		mailPojo.setMsgBody(msgBody);
		mailPojo.setMsgTitle("Time Sheet");

		final String password = "gr4nd4ut0";

		Properties props = new Properties();
		props.put("mail.smtp.host", "mails.novare.com.hk");
		props.put("mail.smtp.user", username);

		// To use TLS
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.password", password);

		session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		sendEmailDao.sendEmail(retrieveEmails(department), mailPojo, session);
	}

	private ArrayList<String> retrieveEmails(String department)
			throws SQLException, ClassNotFoundException {

		ResultSet resultSet = sendEmailDao.getEmail(department);

		final ArrayList<String> email = new ArrayList<String>();
		try {
			while (resultSet.next()) {

				email.add(resultSet.getString(1));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Transferring resultSet to ArrayList Error");
		}

		return email;
	}
}
