//建议代码的阅读顺序为：
//MainTest.Java–>ServerListener.java–>ChatSocket.java–> ChatManager.java

package com.mark.chatserver;

public class MainTest {
	public static void main(String[] args) {
		ServerListener serverListener = new ServerListener();
		serverListener.start();// call the run method
	}
}