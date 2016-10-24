package com.bill.udptransferfile;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * Test File transfer of Client
 * 
 * @author Bill QQ:593890231
 * @since v1.0 2014/09/21
 * @improved by xuezhaojiang 602431866
 */
public class UDPClient {

	private static final String SEND_FILE_PATH = "E:\\latex.pdf";

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();

		byte[] sendBuf = new byte[UDPUtils.BUFFER_SIZE];
		byte[] receiveBuf = new byte[100];

		RandomAccessFile randomAccessFile = null;
		DatagramPacket sendDatagramPacket = null;
		DatagramPacket receiveDatagramPacket = null;
		DatagramSocket datagramSocket = null;
		int readSize = -1;
		try {
			byte[] receiveAddress = { -64, -88, 60, 18 };
			randomAccessFile = new RandomAccessFile(SEND_FILE_PATH, "r");
			// 填写接收方地址，端口号
			sendDatagramPacket = new DatagramPacket(sendBuf, sendBuf.length,
					new InetSocketAddress(InetAddress.getByAddress(receiveAddress), UDPUtils.PORT + 1));
			receiveDatagramPacket = new DatagramPacket(receiveBuf, receiveBuf.length);
			// 自己的端口号
			datagramSocket = new DatagramSocket(UDPUtils.PORT, InetAddress.getLocalHost());
			int sendCount = 0;
			while ((readSize = randomAccessFile.read(sendBuf, 0, sendBuf.length)) != -1) {
				sendDatagramPacket.setData(sendBuf, 0, readSize);
				datagramSocket.send(sendDatagramPacket);
				// wait server response
				while (true) {
					// receiveDatagramPacket.setData(receiveBuf, 0,
					// receiveBuf.length);
					datagramSocket.receive(receiveDatagramPacket);

					// confirm server receive
					if (!UDPUtils.isEqualsByteArray(UDPUtils.successData, receiveBuf,
							receiveDatagramPacket.getLength())) {
						System.out.println("resend ...");
						sendDatagramPacket.setData(sendBuf, 0, readSize);
						datagramSocket.send(sendDatagramPacket);
					} else
						break;
				}

				System.out.println("send count of " + (++sendCount) + "!");
			}
			// send exit wait server response
			while (true) {
				System.out.println("client send exit message ....");
				sendDatagramPacket.setData(UDPUtils.exitData, 0, UDPUtils.exitData.length);
				datagramSocket.send(sendDatagramPacket);
				// receiveDatagramPacket.setData(receiveBuf,0,receiveBuf.length);
				datagramSocket.receive(receiveDatagramPacket);
				if (!UDPUtils.isEqualsByteArray(UDPUtils.exitData, receiveBuf, receiveDatagramPacket.getLength())) {
					System.out.println("client Resend exit message ....");
					datagramSocket.send(sendDatagramPacket);
				} else
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (randomAccessFile != null)
					randomAccessFile.close();
				if (datagramSocket != null)
					datagramSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		long endTime = System.currentTimeMillis();
		System.out.println("time:" + (endTime - startTime));
	}
}