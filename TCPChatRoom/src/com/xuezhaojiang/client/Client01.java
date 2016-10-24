package com.xuezhaojiang.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author Ñ¦Õ×½­ ¿Í»§¶Ë01
 */
public class Client01 {
	/**
	 * @param args
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {
		Client client01 = new Client("Student001",new Socket("127.0.0.1", 9999));
		try {
			client01.communicationInputThread.start();
			client01.communicationOutputThread.start();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			client01.communicationOutputThread.interrupt();
			client01.communicationInputThread.interrupt();
		}
	}
}
