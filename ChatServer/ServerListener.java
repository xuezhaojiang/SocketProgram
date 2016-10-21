package com.mark.chatserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

/**
 * 主要的框架类，用于侦听客户端的链接并将服务器端产生的socket以一个新的线程的方式进行运行
 * 
 * @author lhdn
 *
 */
public class ServerListener extends Thread {
	@Override
	public void run() {
		try {
			ServerSocket server = new ServerSocket(9999);

			while (true) {
				Socket socket = server.accept();
				System.out.println("现在有客户端连接上了服务器！");
				// JOptionPane.showMessageDialog(null, "现在有客户端连接上了服务器！");
				ChatSocket chatSocket = new ChatSocket(socket);
				ChatManager.getChatManager().add(chatSocket);
				chatSocket.start();
			}
		} catch (IOException e) {
			// TODO: handle exception
		}
	}
}