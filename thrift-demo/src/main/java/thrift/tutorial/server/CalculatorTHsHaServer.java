package thrift.tutorial.server;

import java.net.InetSocketAddress;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;

import thrift.tutorial.CalculatorServiceImpl;
import thrift.tutorial.TCalculatorService;
import thrift.tutorial.client.CalculatorTNonblockingClient;

/**
 * <pre>
 *	服务端：半同步半异步、服务端和客户端传输层指定 TFramedTransport并且协议保持一致。
 * 	客户端：{@link CalculatorTNonblockingClient}
 * </pre>
 * 
 * @author zhaohe
 *
 */
public class CalculatorTHsHaServer {
	public static final int SERVER_PORT = 9090;
	public static final String ADDR = "127.0.0.1";

	public static void main(String[] args) {
		serve();
	}

	public static void serve() {
		try {
			InetSocketAddress bindAddress = new InetSocketAddress(ADDR, SERVER_PORT);
			TNonblockingServerSocket serverSocket = new TNonblockingServerSocket(bindAddress);
			THsHaServer.Args thhsArgs = new THsHaServer.Args(serverSocket);
			thhsArgs.processor(new TCalculatorService.Processor<TCalculatorService.Iface>(new CalculatorServiceImpl()));
			thhsArgs.transportFactory(new TFramedTransport.Factory());
			thhsArgs.protocolFactory(new TBinaryProtocol.Factory());// new TCompactProtocol.Factory()
			TServer server = new THsHaServer(thhsArgs);
			System.out.println("Starting the THsHaServer server...");
			server.serve();
		} catch (Exception e) {
			System.out.println("Server start error!!!");
			e.printStackTrace();
		}
	}
}
