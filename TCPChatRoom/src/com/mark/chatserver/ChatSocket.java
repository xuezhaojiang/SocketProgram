package com.mark.chatserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

/**
 * 用来和客户端进行通信的线程类
 * 
 * @author lhdn
 *
 */
public class ChatSocket extends Thread {
	private Socket socket;

	public ChatSocket(Socket s) {
		this.socket = s;
	}

	/**
	 * 向客户端输出信息（以字节的方式）
	 * 
	 * @param str
	 */
	public void out(String str) {
		try {
			OutputStream os = socket.getOutputStream();
			os.write((str + "\n").getBytes("UTF-8"));
			os.flush();
		} catch (Exception e) {
			throw new RuntimeException("没有向客户端输出正确的信息");
		}
	}

	@Override
	public void run() {
		// out("\nwelcom to beijing\n");
		try {
			while (true) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
				String line = "";
				if ((line = reader.readLine()) != null) {
					System.out.println(line);
					ChatManager.getChatManager().publish(this, line);
				}

			}
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			try {//有客户端退出
				socket.close();//关闭socket
				ChatManager.getChatManager().remove(this);//移除队列
				System.out.println("客户端退出");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			//e.printStackTrace();
		}
	}
}