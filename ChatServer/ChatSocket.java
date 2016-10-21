package com.mark.chatserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * �����Ϳͻ��˽���ͨ�ŵ��߳���
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
	 * ��ͻ��������Ϣ�����ֽڵķ�ʽ��
	 * 
	 * @param str
	 */
	public void out(String str) {
		try {
			OutputStream os = socket.getOutputStream();
			os.write((str + "\n").getBytes("UTF-8"));
			os.flush();
		} catch (Exception e) {
			throw new RuntimeException("û����ͻ��������ȷ����Ϣ");
		}
	}

	@Override
	public void run() {
		// out("\nwelcom to beijing\n");
		while (true) {
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
				String line = "";
				if ((line = reader.readLine()) != null) {
					System.out.println(line);
					ChatManager.getChatManager().publish(this, line);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}