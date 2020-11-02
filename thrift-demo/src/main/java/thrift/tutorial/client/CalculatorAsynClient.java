package thrift.tutorial.client;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TNonblockingTransport;

import thrift.tutorial.TCalculatorService;

public class CalculatorAsynClient {

	public static final String ADDR = "127.0.0.1";
	public static final int SERVER_PORT = 9090;
	public static final int TIMEOUT = 30000;

	public static void main(String[] args) {
		try {
			TAsyncClientManager clientManager = new TAsyncClientManager();
			TNonblockingTransport transport = new TNonblockingSocket(ADDR, SERVER_PORT, TIMEOUT);
			TProtocolFactory tprotocol = new TBinaryProtocol.Factory();//// new TCompactProtocol.Factory()
			TCalculatorService.AsyncClient asyncClient = new TCalculatorService.AsyncClient(tprotocol, clientManager,
					transport);
			CountDownLatch latch = new CountDownLatch(1);
			AsynCallback callBack = new CalculatorAsynClient().new AsynCallback(latch);
			System.out.println("call method add start ...");
			asyncClient.add(1, 2, callBack);
			System.out.println("call method add .... end");
			boolean wait = latch.await(30, TimeUnit.SECONDS);
			System.out.println("latch.await =:" + wait);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("startClient end.");
	}

	public class AsynCallback implements AsyncMethodCallback<Integer> {
		private CountDownLatch latch;

		public AsynCallback(CountDownLatch latch) {
			this.latch = latch;
		}

		@Override
		public void onComplete(Integer response) {
			System.out.println("onComplete");
			try {
				// Thread.sleep(1000L * 1);
				System.out.println("AsynCall result =:" + response.toString());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				latch.countDown();
			}
		}

		@Override
		public void onError(Exception exception) {
			System.out.println("onError :" + exception.getMessage());
			latch.countDown();
		}
	}
}
