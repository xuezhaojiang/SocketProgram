/**
 * 
 */
package com.xuezhaojiang.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author Ñ¦Õ×½­ ¿Í»§¶Ë03
 */
public class Client03 {
	/**
	 * @param args
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {
		Client client03 = new Client("Student003",new Socket("127.0.0.1", 9999));
		try {
			client03.communicationInputThread.start();
			client03.communicationOutputThread.start();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			client03.communicationOutputThread.interrupt();
			client03.communicationInputThread.interrupt();
		}
	}
}