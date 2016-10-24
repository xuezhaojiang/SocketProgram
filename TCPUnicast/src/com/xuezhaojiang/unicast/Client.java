package com.xuezhaojiang.unicast;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	Socket socket=null;
	String name=null;
	CommunicationInputThread communicationInputThread = null;
	CommunicationOutputThread communicationOutputThread=null;
	public Client(String name,Socket socket) throws UnknownHostException, IOException {
		this.socket = socket;
		this.name = name;
		this.communicationInputThread = new CommunicationInputThread(this.socket, this.name);
		this.communicationOutputThread = new CommunicationOutputThread(this.socket, this.name);
	}
	public static void main(String[] args) throws UnknownHostException, IOException {
		Client client = new Client("Student",new Socket("127.0.0.1", 9999));
		try {
			client.communicationInputThread.start();
			client.communicationOutputThread.start();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			client.communicationOutputThread.interrupt();
			client.communicationInputThread.interrupt();
		}
	}
}
