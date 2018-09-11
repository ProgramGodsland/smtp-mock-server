package com.smtp.mock.factory;

import java.util.Collection;
import java.util.Collections;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.event.TransportListener;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import com.smtp.mock.connection.SmtpConnection;

public class SmtpConnectionFactory implements PooledObjectFactory<SmtpConnection> {

	private final Session session;
	protected Collection<TransportListener> defaultTransportListeners;

	public SmtpConnectionFactory(Session session) {
		this(session, Collections.<TransportListener>emptyList());

	}

	public SmtpConnectionFactory(Session session, Collection<TransportListener> defaultTransportListeners) {
		this.session = session;
		this.defaultTransportListeners = defaultTransportListeners;
	}

	@Override
	public PooledObject<SmtpConnection> makeObject() throws Exception {
		System.err.println("makeObject");

		Transport transport = session.getTransport();
		transport.connect();

		SmtpConnection smtpTransport = new SmtpConnection(transport);
		initDefaultListeners(smtpTransport);

		return new DefaultPooledObject<SmtpConnection>(smtpTransport);
	}

	private void initDefaultListeners(SmtpConnection smtpConnection) {
		for (TransportListener transportListener : defaultTransportListeners) {
			smtpConnection.addTransportListener(transportListener);
		}
	}

	@Override
	public void destroyObject(PooledObject<SmtpConnection> pooledObject) throws Exception {
		try {

			System.err.println("destroyObject [{}]" + pooledObject.getObject().isConnected());

			pooledObject.getObject().clearListeners();
			pooledObject.getObject().getTransport().close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean validateObject(PooledObject<SmtpConnection> pooledObject) {
		boolean connected = pooledObject.getObject().isConnected();
		System.err.println("Is connected [{}]" + connected);
		return connected;
	}

	@Override
	public void activateObject(PooledObject<SmtpConnection> pooledObject) throws Exception {
		initDefaultListeners(pooledObject.getObject());
	}

	@Override
	public void passivateObject(PooledObject<SmtpConnection> pooledObject) throws Exception {

		System.err.println("passivateObject [{}]" + pooledObject.getObject().isConnected());

		pooledObject.getObject().clearListeners();

	}

	public Collection<TransportListener> getDefaultTransportListeners() {
		return defaultTransportListeners;
	}

	public void setDefaultTransportListeners(Collection<TransportListener> defaultTransportListeners) {
		this.defaultTransportListeners = defaultTransportListeners;
	}

	public Session getSession() {
		return session;
	}

}
