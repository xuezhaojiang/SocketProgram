package com.mark.chatserver;

import java.util.Vector;

/**
 * 通信客户端管理类 实现的业务就是将产生的客户端添加到服务器端的一个队列中 并把所有客户端产生的信息，发送给所有的客户端对象
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
	 * 添加一个客户端到队列中
	 * 
	 * @param cm
	 */
	public void add(ChatSocket cm) {
		vector.add(cm);
	}
	//从队列中移除
	public void remove(ChatSocket cm){
		vector.remove(cm);
	}
	/**
	 * 发布客户端的消息
	 * 
	 * @param chatSocket
	 * @param line
	 */
	public void publish(ChatSocket chatSocket, String line)// 发布，向所以别的客户端发布
	{
		for (int i = 0; i < vector.size(); i++) {
			ChatSocket temp = (ChatSocket) vector.get(i);
			// 过滤自身发送给服务器端的信息
			if (!temp.equals(chatSocket))// any non-null reference value x,
											// x.equals(x) should return true.
			{
				temp.out(line);
			}
		}
	}
}