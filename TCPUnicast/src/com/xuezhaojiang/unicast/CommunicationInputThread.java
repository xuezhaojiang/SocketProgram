package com.xuezhaojiang.unicast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author Ѧ�׽� ����������server���룬�������Ļ
 */
public class CommunicationInputThread extends Thread {
	private Socket socket;
	String name;

	public CommunicationInputThread(Socket socket, String name) {
		this.socket = socket;
		this.name = name;
		// TODO Auto-generated constructor stub
	}

	public void run() {
		try {
			BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			// ��Socket����õ�����������������Ӧ��BufferedReader����
			String readline;
			while (true) {
				if ((readline = is.readLine()) != "") {
					System.out.println(readline);// ��Server����һ�ַ���������ӡ����׼�����
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
