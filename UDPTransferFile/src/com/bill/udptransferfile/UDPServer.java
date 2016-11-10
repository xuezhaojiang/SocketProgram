package com.bill.udptransferfile;

import wangjie.MD5;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * Test file transfer of Server
 * 
 * @author Bill QQ:593890231
 * @since v1.0 2014/09/21
 * @improved by xuezhaojiang 602431866
 */
public class UDPServer {

	private final String SAVE_FILE_PATH = "E:\\19(2).rmvb";
	String receiveAddress = null;
	byte[] sendBuf = null;
	byte[] receiveBuf = null;
	String MD5String = null;
	File file = null;
	// 数据报包,用来实现无连接包投递服务。
	DatagramPacket sendDatagramPacket = null;
	DatagramPacket receiveDatagramPacket = null;
	// 套接字,用来发送和接收数据报包
	DatagramSocket datagramSocket = null;
	// 输出流
	BufferedOutputStream bufferedOutputStream = null;

	public UDPServer() throws IOException {
		this.sendBuf = new byte[100];
		this.receiveBuf = new byte[UDPUtils.BUFFER_SIZE];
		this.receiveAddress = "192.168.50.82";//消息接收方地址
		this.file = new File(SAVE_FILE_PATH);
		this.sendDatagramPacket = new DatagramPacket(this.sendBuf, this.sendBuf.length,
				new InetSocketAddress(InetAddress.getByName(this.receiveAddress), UDPUtils.PORT));
		this.receiveDatagramPacket = new DatagramPacket(this.receiveBuf, this.receiveBuf.length);
		this.datagramSocket = new DatagramSocket(UDPUtils.PORT + 1, InetAddress.getLocalHost());
		this.bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(SAVE_FILE_PATH));
	}

	public static void main(String[] args) throws IOException {
		UDPServer udpServer = new UDPServer();
		try {
			System.out.println(InetAddress.getByName(udpServer.receiveAddress));
			System.out.println(InetAddress.getLocalHost().getHostAddress());
			System.out.println("wait client ....");

			int readSize = 0;
			int readCount = 0;
			// Receives a datagram packet from this socket.
			// When this method returns, the DatagramPacket's buffer is filled
			// with the data received.
			// The datagram packet also contains the sender's IP address,
			// and the port number on the sender's machine.
			udpServer.datagramSocket.receive(udpServer.receiveDatagramPacket);

			while ((readSize = udpServer.receiveDatagramPacket.getLength()) != 0) {//接收到数据
				if (UDPUtils.isEqualsByteArray(UDPUtils.fileEndData, udpServer.receiveBuf, readSize)) {
					udpServer.sendDatagramPacket.setData(UDPUtils.successData, 0, UDPUtils.successData.length);
					udpServer.datagramSocket.send(udpServer.sendDatagramPacket);
					System.out.println("file end ...");
					break;
				}
				readCount++;
				udpServer.bufferedOutputStream.write(udpServer.receiveBuf, 0, readSize);
				udpServer.sendDatagramPacket.setData(UDPUtils.successData, 0, UDPUtils.successData.length);
				udpServer.datagramSocket.send(udpServer.sendDatagramPacket);//回复确认
				udpServer.datagramSocket.receive(udpServer.receiveDatagramPacket);
			}
			// last flush
			udpServer.bufferedOutputStream.flush();
			System.out.println("receive count of " + (readCount) + " !");
			udpServer.MD5String = MD5.getFileMD5String(udpServer.file);//计算MD5
			System.out.println("the MD5 of the file that server received : " + udpServer.MD5String);
			System.out.println("server receive MD5");
			udpServer.datagramSocket.receive(udpServer.receiveDatagramPacket);
			udpServer.sendDatagramPacket.setData(UDPUtils.successData, 0, UDPUtils.successData.length);
			udpServer.datagramSocket.send(udpServer.sendDatagramPacket);
			
			
			System.out.println("server send file message ....");
			//比较MD5是否正确
			if (UDPUtils.isEqualsByteArray(udpServer.MD5String.getBytes(),udpServer.receiveBuf,udpServer.receiveAddress.length())) {
				System.out.println("success ...");
				udpServer.sendDatagramPacket.setData(UDPUtils.rightFile, 0, UDPUtils.rightFile.length);
				udpServer.datagramSocket.send(udpServer.sendDatagramPacket);
			} else {
				System.out.println("fail ...");
				udpServer.sendDatagramPacket.setData(UDPUtils.wrongFile, 0, UDPUtils.wrongFile.length);
				udpServer.datagramSocket.send(udpServer.sendDatagramPacket);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (udpServer.datagramSocket != null)
					udpServer.datagramSocket.close();
				if (udpServer.bufferedOutputStream != null)
					udpServer.bufferedOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}