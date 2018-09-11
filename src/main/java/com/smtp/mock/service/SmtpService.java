package com.smtp.mock.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smtp.mock.config.SmtpDefaultSessionConfig;
import com.smtp.mock.connection.SmtpConnection;
import com.smtp.mock.entities.EmailMessage;

@Service
public class SmtpService {
	@Autowired
	private SmtpDefaultSessionConfig smtpConfig;

	public EmailMessage send(String userId, EmailMessage emailMessage) {
		if (emailMessage == null) {
			System.err.println("No requested message!!");
			return null;
		}
		try {
			List<String> addresses = emailMessage.getAddress();

			SmtpConnection connection = smtpConfig.getConnection();
			MimeMessage mimeMessage = new MimeMessage(connection.getSession());
			if (addresses.size() == 1) {

				mimeMessage.setRecipients(RecipientType.TO, addresses.get(0));

			} else {
				String dest = addresses.stream().collect(Collectors.joining(","));
				mimeMessage.setRecipients(RecipientType.TO, dest);
			}
			mimeMessage.setSubject(emailMessage.getSubject());
			mimeMessage.setText(emailMessage.getMessage());

			System.err.println("MimeMessage prepared: " + mimeMessage.toString());

			connection.sendMessage(mimeMessage);
			System.err.println("Message sent!");

		} catch (MessagingException e) {
			System.err.println("Message sending failed!!");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String messageId = UUID.randomUUID().toString();
		String resourceURL = String.format("/cpaas/email/%s/messages/%s", userId, messageId);
		emailMessage.setResourceURL(resourceURL);
		return emailMessage;
	}
}
