package com.xuezhaojiang.unicast;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	ServerSocket serverSocket;
	Socket socket=null;
	String name=null;
	CommunicationInputThread communicationInputThread = null;
	CommunicationOutputThread communicationOutputThread=null;
	public Server(String name,ServerSocket serverSocket) throws Exception {
		this.serverSocket = serverSocket;
		this.socket = this.serverSocket.accept();
		System.out.println("现在有客户端连接上了服务器！");
		this.name = name;
		this.communicationInputThread = new CommunicationInputThread(this.socket, this.name);
		this.communicationOutputThread = new CommunicationOutputThread(this.socket, this.name);
	}
	public static void main(String[] args) throws Exception {
		Server server = new Server("Teacher",new ServerSocket(9999));
		try {
			server.communicationInputThread.start();
			server.communicationOutputThread.start();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			server.communicationOutputThread.interrupt();
			server.communicationInputThread.interrupt();
		}
	}
}
