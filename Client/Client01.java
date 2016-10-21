package com.xuezhaojiang.client;

import java.io.IOException;
import java.net.Socket;

/**
 * @author 薛兆江
 * 客户端01
 */
public class Client01 {

	/**
	 * 无成员
	 */

	public Client01() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Socket socket = new Socket("127.0.0.1", 9999);
			String name = "Student001";
			ClientInput clientInput = new ClientInput(socket, name);
			ClientOutput clientOutput = new ClientOutput(socket, name);
			clientInput.start();
			clientOutput.start();
		} catch (IOException e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}

}
