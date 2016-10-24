package com.xuezhaojiang.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author Ñ¦Õ×½­ ¿Í»§¶Ë02
 */
public class Client02 {
	/**
	 * @param args
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {
		Client client02 = new Client("Student002",new Socket("127.0.0.1", 9999));
		try {
			client02.communicationInputThread.start();
			client02.communicationOutputThread.start();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			client02.communicationOutputThread.interrupt();
			client02.communicationInputThread.interrupt();
		}
	}
}
