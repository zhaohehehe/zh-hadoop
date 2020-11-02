package thrift.tutorial.server;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportFactory;

import thrift.tutorial.CalculatorServiceImpl;
import thrift.tutorial.TCalculatorService;
import thrift.tutorial.client.CalculatorTSimpleClient;

/**
 * <pre>
 *	服务端：多线程、阻塞IO。
 * 	客户端：{@link CalculatorTSimpleClient}
 * </pre>
 * 
 * @author zhaohe
 *
 */
public class CalculatorTThreadPoolServer {
	public static final int SERVER_PORT = 9090;
	public static final String ADDR = "127.0.0.1";

	public static final long stringLengthLimit_ = 10l;
	public static final long containerLengthLimit_ = 10;
	public static final int requestTimeout = 10;
	public static final int beBackoffSlotLength = 10;

	public static final int corePoolSize = 10;
	public static final int maximumPoolSize = 10;
	public static final long keepAliveTime = 10;

	public static void main(String[] args) {
		serve();
	}

	public static void serve() {
		try {
			InetSocketAddress bindAddress = new InetSocketAddress(ADDR, SERVER_PORT);
			TServerSocket serverSocket = new TServerSocket(bindAddress);
			TThreadPoolServer.Args ttpsArgs = new TThreadPoolServer.Args(serverSocket);
			ttpsArgs.processor(new TCalculatorService.Processor<TCalculatorService.Iface>(new CalculatorServiceImpl()));
			ttpsArgs.transportFactory(new TTransportFactory());
			ttpsArgs.protocolFactory(new TBinaryProtocol.Factory());
			ttpsArgs.inputProtocolFactory(
					new TBinaryProtocol.Factory(true, true, stringLengthLimit_, containerLengthLimit_))
					.requestTimeout(requestTimeout).requestTimeoutUnit(TimeUnit.MILLISECONDS)
					.beBackoffSlotLength(beBackoffSlotLength).beBackoffSlotLengthUnit(TimeUnit.MILLISECONDS);
			ExecutorService executorService = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime,
					TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
			ttpsArgs.executorService(executorService);
			TServer server = new TThreadPoolServer(ttpsArgs);
			System.out.println("Starting the ThreadPool server...");
			server.serve();
		} catch (Exception e) {
			System.out.println("Server start error!!!");
			e.printStackTrace();
		}
	}
}
