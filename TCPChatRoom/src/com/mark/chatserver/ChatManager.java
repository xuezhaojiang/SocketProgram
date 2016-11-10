package com.mark.chatserver;

import java.util.Vector;

/**
 * ͨ�ſͻ��˹����� ʵ�ֵ�ҵ����ǽ������Ŀͻ�����ӵ��������˵�һ�������� �������пͻ��˲�������Ϣ�����͸����еĿͻ��˶���
 * 
 * @author lhdn
 *
 */
public class ChatManager {
	private ChatManager() {
	}

	private static final ChatManager newInstance = new ChatManager();
	public static Vector<ChatSocket> vector = new Vector<ChatSocket>();

	public static ChatManager getChatManager() {
		return newInstance;
	}

	/**
	 * ���һ���ͻ��˵�������
	 * 
	 * @param cm
	 */
	public void add(ChatSocket cm) {
		vector.add(cm);
	}
	//�Ӷ������Ƴ�
	public void remove(ChatSocket cm){
		vector.remove(cm);
	}
	/**
	 * �����ͻ��˵���Ϣ
	 * 
	 * @param chatSocket
	 * @param line
	 */
	public void publish(ChatSocket chatSocket, String line)// �����������Ա�Ŀͻ��˷���
	{
		for (int i = 0; i < vector.size(); i++) {
			ChatSocket temp = (ChatSocket) vector.get(i);
			// ���������͸��������˵���Ϣ
			if (!temp.equals(chatSocket))// any non-null reference value x,
											// x.equals(x) should return true.
			{
				temp.out(line);
			}
		}
	}
}