package com.bill.udptransferfile;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import wangjie.MD5;

/**
 * Test File transfer of Client
 * 
 * @author Bill QQ:593890231
 * @since v1.0 2014/09/21
 * @improved by xuezhaojiang 602431866
 */
public class UDPClient {

	private final String SEND_FILE_PATH = "E:\\19.rmvb";
	byte[] sendBuf = null;
	String receiveAddress = null;
	String MD5String = null;
	byte[] receiveBuf = null;
	File file = null;
	int readSize;
	RandomAccessFile randomAccessFile = null;
	DatagramPacket sendDatagramPacket = null;
	DatagramPacket receiveDatagramPacket = null;
	DatagramSocket datagramSocket = null;

	public UDPClient() throws IOException {
		this.readSize = -1;
		this.receiveAddress = "192.168.50.82";//消息接收方地址
		this.sendBuf = new byte[UDPUtils.BUFFER_SIZE];
		this.receiveBuf = new byte[128];
		this.file = new File(SEND_FILE_PATH);
		this.randomAccessFile = new RandomAccessFile(SEND_FILE_PATH, "r");
		// 填写接收方地址，端口号
		this.sendDatagramPacket = new DatagramPacket(this.sendBuf, this.sendBuf.length,
				new InetSocketAddress(InetAddress.getByName(this.receiveAddress), UDPUtils.PORT + 1));
		this.receiveDatagramPacket = new DatagramPacket(this.receiveBuf, this.receiveBuf.length);
		this.datagramSocket = new DatagramSocket(UDPUtils.PORT, InetAddress.getLocalHost());
		this.MD5String = MD5.getFileMD5String(this.file);
	}

	public int readFromRandomAccessFile(UDPClient udpClient) {
		try {
			udpClient.readSize = udpClient.randomAccessFile.read(udpClient.sendBuf, 0, udpClient.sendBuf.length);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return udpClient.readSize;
	}

	public void sendData(UDPClient udpClient) {
		try {
			udpClient.datagramSocket.send(udpClient.sendDatagramPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void ServerReceived(UDPClient udpClient) {
		while (true) {
			try {
				udpClient.datagramSocket.receive(udpClient.receiveDatagramPacket);
				if (!UDPUtils.isEqualsByteArray(UDPUtils.successData, udpClient.receiveBuf,
						udpClient.receiveDatagramPacket.getLength())) {
					System.out.println("resend ...");
					udpClient.datagramSocket.send(udpClient.sendDatagramPacket);
				} else
					break;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws IOException {
		UDPClient udpClient = new UDPClient();
		long startTime = System.currentTimeMillis();//记时
		int sendCount = 0;
		try {
			System.out.println(InetAddress.getByName(udpClient.receiveAddress));
			System.out.println(InetAddress.getLocalHost().getHostAddress());

			// 发送文件
			while ((udpClient.readSize = udpClient.readFromRandomAccessFile(udpClient)) != -1) {
				udpClient.sendDatagramPacket.setData(udpClient.sendBuf, 0, udpClient.readSize);
				udpClient.sendData(udpClient);
				udpClient.ServerReceived(udpClient);
				sendCount++;
			}
			System.out.println("send count of " + (sendCount) + "!");

			// 发送文件结束
			System.out.println("client send file end message ....");
			udpClient.sendDatagramPacket.setData(UDPUtils.fileEndData, 0, UDPUtils.fileEndData.length);
			udpClient.sendData(udpClient);
			udpClient.ServerReceived(udpClient);

			// 发送MD5校验
			System.out.println("the MD5 of the file that client send : " + udpClient.MD5String);
			System.out.println("client send MD5 message ....");
			udpClient.sendDatagramPacket.setData(udpClient.MD5String.getBytes(), 0,
					udpClient.MD5String.getBytes().length);
			udpClient.sendData(udpClient);
			udpClient.ServerReceived(udpClient);
			System.out.println("client receive file message ....");
			udpClient.datagramSocket.receive(udpClient.receiveDatagramPacket);

			if (UDPUtils.isEqualsByteArray(UDPUtils.rightFile, udpClient.receiveBuf,
					udpClient.receiveDatagramPacket.getLength())) {
				System.out.println("success ...");
			} else if (UDPUtils.isEqualsByteArray(UDPUtils.wrongFile, udpClient.receiveBuf,
					udpClient.receiveDatagramPacket.getLength())) {
				System.out.println("fail ...");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (udpClient.randomAccessFile != null)
					udpClient.randomAccessFile.close();
				if (udpClient.datagramSocket != null)
					udpClient.datagramSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		long endTime = System.currentTimeMillis();
		System.out.println("time: " + (endTime - startTime)+" ms");
	}
}
