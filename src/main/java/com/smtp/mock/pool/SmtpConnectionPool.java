package com.smtp.mock.pool;

import javax.mail.Session;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;

import com.smtp.mock.connection.SmtpConnection;
import com.smtp.mock.factory.SmtpConnectionFactory;

public class SmtpConnectionPool extends GenericObjectPool<SmtpConnection> {
	private Logger log;
	// private final ReentrantLock lock;

	public SmtpConnectionPool(SmtpConnectionFactory factory) {
		super(factory);
	}

	public SmtpConnectionPool(SmtpConnectionFactory factory, GenericObjectPoolConfig config) {
		super(factory, config);
	}

	@Override
	public SmtpConnection borrowObject() throws Exception {
		SmtpConnection object = super.borrowObject();
		log.info("Smtp connection pool borrow object: " + object);
		return object;
	}

	@Override
	public SmtpConnection borrowObject(long borrowMaxWaitMillis) throws Exception {
		SmtpConnection object = super.borrowObject(borrowMaxWaitMillis);
		log.info("Smtp connection pool borrow object: " + object);
		return object;
	}

	public Session getSession() {
		return ((SmtpConnectionFactory) getFactory()).getSession();
	}

//	  private final Queue<SmtpConnection> connections;
//	  private int poolSize;
//	  private SmtpConnectionFactory smtpConnectionFactory;
//
//	  private SmtpConnectionPool() {
//	    log.debug("Initializing smtp connection pool...");
//	    this.connections= new LinkedBlockingQueue<SmtpConnection>();
//	    this.lock = new ReentrantLock();
//
//	  }
//	  
//	  /**
//	   * Fill in connection data source.
//	   */
//	  public void init(final SmtpConnectionFactory smtpConnectionFactory, final int initialConnection) {
//
//	    log.debug("Initialize smtp connection pool size to "   + initialConnection);
//	    this.smtpConnectionFactory = smtpConnectionFactory;
//	    this.poolSize = initialConnection;
//	    for (; this.connections.size() < initialConnection;) {
//	      try {
//	        this.lock.tryLock(10, TimeUnit.SECONDS);
//	        this.addObject();
//	        if (this.lock.isHeldByCurrentThread()) {
//	          this.lock.unlock();
//	        }
//	      } catch (final IOException e) {
//	        log.error("IOException: " + e.getMessage());
//	      } catch (final IllegalStateException e) {
//	        log.error("IllegalStateException: " + e.getMessage());
//	      } catch (final UnsupportedOperationException e) {
//	        log.error("UnsupportedOperationException: " + e.getMessage());
//	      } catch (final Exception e) {
//	        log.error("Unknow exception: " + e.getMessage());
//	      }
//	    }
//	  }

//	@Override
//	public void returnObject(SmtpConnection obj) throws Exception {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void invalidateObject(SmtpConnection obj) throws Exception {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void addObject() throws Exception, IllegalStateException, UnsupportedOperationException {
//		  final SmtpConnection smtpConnection = this.smtpConnectionFactory.makeObject();
//
//		    if (this.enableAck) {
//		      channel.addReturnListener(RabbitmqReturnListener.getInstance());
//		      channel.addConfirmListener(RabbitmqConfirmListener.getInstance());
//		      channel.confirmSelect();
//		    }
//
//		    this.channels.add(channel);
//		    log.debug("Add channel to pool, channel pool size: " + this.channels.size());
//		
//	}
//
//	@Override
//	public int getNumIdle() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public int getNumActive() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public void clear() throws Exception, UnsupportedOperationException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void close() {
//		// TODO Auto-generated method stub
//		
//	}

}
