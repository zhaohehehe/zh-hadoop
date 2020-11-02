package thrift.tutorial.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import thrift.tutorial.TCalculatorService;

public class CalculatorTNonblockingClient {

	public static final String ADDR = "127.0.0.1";
	public static final int SERVER_PORT = 9090;
	public static final int TIMEOUT = 30000;

	public static void main(String[] args) {
		TTransport transport = null;
		try {
			transport = new TFramedTransport(new TSocket(ADDR, SERVER_PORT, TIMEOUT));
			TProtocol protocol = new TBinaryProtocol(transport);// new TCompactProtocol(transport);
			TCalculatorService.Client client = new TCalculatorService.Client(protocol);
			transport.open();
			CalculatorTSimpleClient.perform(client);
		} catch (TTransportException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		} finally {
			if (null != transport) {
				transport.close();
			}
		}
	}
}
