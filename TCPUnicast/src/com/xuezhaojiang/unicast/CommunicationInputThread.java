package com.xuezhaojiang.unicast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author 薛兆江 输入流，从server读入，输出到屏幕
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
			// 由Socket对象得到输入流，并构造相应的BufferedReader对象
			String readline;
			while (true) {
				if ((readline = is.readLine()) != "") {
					System.out.println(readline);// 从Server读入一字符串，并打印到标准输出上
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
