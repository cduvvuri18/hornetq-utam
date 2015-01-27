package com.cduvvuri.hqutam.pool;

import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ConnectionPool<T> {
	private static Log LOGGER = LogFactory.getLog(ConnectionPool.class
			.getName());

	private LinkedList<Connection> availableConnections;
	private LinkedList<Connection> busyConnections;
	private ReentrantLock lock = new ReentrantLock();
	private AbstractConnectionProxy<T> proxy;
	private ScheduledExecutorService executorService;
	private int maxPoolSize = 0;
	final Condition noConnection = lock.newCondition();

	public ConnectionPool(int maxPoolSize, AbstractConnectionProxy<T> proxy) {
		this.maxPoolSize = maxPoolSize;
		this.proxy = proxy;
		availableConnections = new LinkedList<Connection>();
		busyConnections = new LinkedList<Connection>();
		executorService = Executors.newScheduledThreadPool(1);
		//TODO Removing 
		executorService.scheduleAtFixedRate(new RemoveIdle(), 100, 100,
				TimeUnit.MILLISECONDS);
	}

	public T getConnection() {
		LOGGER.info("getting connection...............");
		try {
			lock.lock();
			if (availableConnections.size() == 0) {
				if (availableConnections.size() + busyConnections.size() < maxPoolSize) {
					createConnection();
				}
			}
			LOGGER.info("taking connection............");
			if (availableConnections.size() < 1)
				noConnection.await();
			Connection connection = availableConnections.poll();
			LOGGER.info("took connection............");
			busyConnections.add(connection);

			//Just for debugging purpose
			if (busyConnections.size() > maxPoolSize) {
				throw new RuntimeException("busyConnections.size() > 5");
			}

			return connection.t;
		} catch (InterruptedException e) {
			throw new RuntimeException("Failed to obtain connection", e);
		} finally {
			lock.unlock();
		}
	}

	private void createConnection() {
		LOGGER.info("creating new connection...............");
		availableConnections.add(new Connection(proxy.create()));
	}

	public void releaseConnection(T t) {
		LOGGER.info("release connection...............");
		try {
			lock.lock();
			LOGGER.info("removing from busy connections "
					+ busyConnections.size());
			busyConnections.remove(new Connection(t));
			LOGGER.info("size of busy connections after release "
					+ busyConnections.size());
			LOGGER.info("available connections " + availableConnections.size());
			availableConnections.add(new Connection(t));

			// Just for debugging purpose
			if (availableConnections.size() > maxPoolSize) {
				throw new RuntimeException("busyConnections.size() > 5");
			}
			noConnection.signal();
		} finally {
			lock.unlock();
		}
	}

	private class Connection {
		private T t;
		private Date lastAccessed;

		public Connection(T t) {
			this.t = t;
			this.lastAccessed = new Date();
		}

		public boolean canRemove() {
			if (new Date().getTime() - lastAccessed.getTime() > 500) {
				return true;
			}
			return false;
		}

		public int hashCode() {
			return t.hashCode();
		}

		public boolean equals(Object obj) {
			if (t.hashCode() == obj.hashCode()) {
				return true;
			}
			return false;
		}
	}

	private class RemoveIdle implements Runnable {
		public void run() {
			try {
				lock.lock();
				Object[] objects = availableConnections.toArray();
				for (int i = 0; i < objects.length; i++) {
					@SuppressWarnings("unchecked")
					Connection con = (Connection) objects[i];
					if (con.canRemove()) {
						LOGGER.info("connection removed....................");
						availableConnections.remove(con);
						proxy.close(con.t);
					}
				}
				noConnection.signal();
			} finally {
				lock.unlock();
			}
		}
	}
}
