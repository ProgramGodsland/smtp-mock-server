package com.smtp.mock.config;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.event.TransportListener;

import org.springframework.context.annotation.Configuration;

import com.smtp.mock.connection.SmtpConnection;
import com.smtp.mock.factory.SmtpConnectionFactory;
import com.smtp.mock.pool.SmtpConnectionPool;

@Configuration
public class SmtpDefaultSessionConfig {
	private final String smtpHost = "smtp.gmail.com";
	private final String smtpPort = "587";
	private final String userName = "test3001.rbbn@gmail.com";
	private final String password = "Temp1234@";

	private Session session;
	private List<TransportListener> defaultTransportListeners;
	private SmtpConnection connection;

	@PostConstruct
	private void init() {
		SmtpConnectionFactory factory = build();
		SmtpConnectionPool smtpConnectionPool = new SmtpConnectionPool(factory);

		try {
			this.connection = smtpConnectionPool.borrowObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private SmtpConnectionFactory build() {
		if (session == null) {
			System.err.println("Set session: ");
			session = setEmailSession();
			System.err.println(session);
			defaultTransportListeners = Collections.emptyList();
		}
		return new SmtpConnectionFactory(session, defaultTransportListeners);
	}

	private Session setEmailSession() {
		Properties props = new Properties();
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.port", smtpPort);
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "false");

		Authenticator authenticator = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		};

		Session session = Session.getInstance(props, authenticator);
		return session;
	}

	public SmtpConnection getConnection() {
		return connection;
	}

	public void setConnection(SmtpConnection connection) {
		this.connection = connection;
	}
	
}
