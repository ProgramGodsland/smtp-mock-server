package com.smtp.mock.exception;

public class MailSendException extends Exception {
	public MailSendException(String message) {
		super(message);
	}

	public MailSendException(String message, Throwable t) {
		super(message, t);
	}
}
