package com.cduvvuri.hqutam.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class TestConnectionPool {
	private static Log LOGGER = LogFactory.getLog(TestConnectionPool.class
			.getName());

	class TestConnection {
		public void close() {
			LOGGER.info("close test connection.........");
		}
	}

	class TestRunnable implements Runnable {
		private ConnectionPool<TestConnection> pool;
		public TestRunnable(ConnectionPool<TestConnection> pool) {
			this.pool = pool;
		}
		public void run() {
			try {
				TestConnection con = pool.getConnection();
				Thread.sleep(1000);
				pool.releaseConnection(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void testDummyConnectionPool() throws InterruptedException {
		AbstractConnectionProxy<TestConnection> proxy = new AbstractConnectionProxy<TestConnection>() {
			@Override
			public TestConnection create() {
				return new TestConnection();
			}

			@Override
			public boolean close(TestConnection t) {
				t.close();
				return true;
			}

		};
		ConnectionPool<TestConnection> pool = new ConnectionPool<TestConnection>(
				(short) 5, proxy);

		ExecutorService service = Executors.newCachedThreadPool();
		List<Future> futures = new ArrayList<Future>();
		for (int i = 0; i < 100; i++) {
			futures.add(service.submit(new TestRunnable(pool)));
		}
		for (int i = 0; i < 100; i++) {
			futures.get(i);
		}
		service.shutdown();
		service.awaitTermination(5, TimeUnit.MINUTES);
		while(true) {
			Thread.sleep(1000);
		}
	}
}
