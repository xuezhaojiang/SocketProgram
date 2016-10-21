package com.xuezhaojiang.client;

import java.io.IOException;
import java.net.Socket;
/**
 * @author Ñ¦Õ×½­
 * ¿Í»§¶Ë02
 */
public class Client02 {

	public Client02() {
		// TODO Auto-generated constructor stub
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Socket socket = new Socket("127.0.0.1", 9999);
			String name = "Student002";
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
