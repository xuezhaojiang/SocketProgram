/**
 * 
 */
package com.xuezhaojiang.client;

import java.io.IOException;
import java.net.Socket;

/**
 * @author Ѧ�׽�
 * �ͻ���03
 */
public class Client03 {

	/**
	 * 
	 */
	public Client03() {
		// TODO Auto-generated constructor stub
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Socket socket = new Socket("127.0.0.1", 9999);
			String name = "Student003";
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
