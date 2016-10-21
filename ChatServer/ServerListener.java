package com.mark.chatserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

/**
 * ��Ҫ�Ŀ���࣬���������ͻ��˵����Ӳ����������˲�����socket��һ���µ��̵߳ķ�ʽ��������
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
				System.out.println("�����пͻ����������˷�������");
				// JOptionPane.showMessageDialog(null, "�����пͻ����������˷�������");
				ChatSocket chatSocket = new ChatSocket(socket);
				ChatManager.getChatManager().add(chatSocket);
				chatSocket.start();
			}
		} catch (IOException e) {
			// TODO: handle exception
		}
	}
}