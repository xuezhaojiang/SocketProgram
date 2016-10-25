package com.bill.udptransferfile;

import java.io.BufferedOutputStream;
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

	private static final String SAVE_FILE_PATH = "E:\\latex(2).pdf";

	public static void main(String[] args) {
		byte[] sendBuf = new byte[100];
		byte[] receiveBuf = new byte[UDPUtils.BUFFER_SIZE];
		// 数据报包,用来实现无连接包投递服务。
		DatagramPacket sendDatagramPacket = null;
		DatagramPacket receiveDatagramPacket = null;
		// 套接字,用来发送和接收数据报包
		DatagramSocket datagramSocket = null;
		// 输出流
		BufferedOutputStream bufferedOutputStream = null;
		try {
			String receiveAddress = "192.168.60.243";
			System.out.println(InetAddress.getByName(receiveAddress));
			System.out.println(InetAddress.getLocalHost().getHostAddress());
			sendDatagramPacket = new DatagramPacket(sendBuf, sendBuf.length,
					new InetSocketAddress(InetAddress.getByName(receiveAddress), UDPUtils.PORT));
			receiveDatagramPacket = new DatagramPacket(receiveBuf, receiveBuf.length);
			datagramSocket = new DatagramSocket(UDPUtils.PORT + 1, InetAddress.getLocalHost());
			bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(SAVE_FILE_PATH));

			System.out.println("wait client ....");

			int readSize = 0;
			int readCount = 0;
			int flushSize = 0;
			// Receives a datagram packet from this socket.
			// When this method returns, the DatagramPacket's buffer is filled
			// with the data received.
			// The datagram packet also contains the sender's IP address,
			// and the port number on the sender's machine.
			datagramSocket.receive(receiveDatagramPacket);
			
			while ((readSize = receiveDatagramPacket.getLength()) != 0) {
				// validate client send exit flag
				if (UDPUtils.isEqualsByteArray(UDPUtils.exitData, receiveBuf, readSize)) {
					System.out.println("server exit ...");
					// send exit flag
					sendDatagramPacket.setData(UDPUtils.exitData, 0, UDPUtils.exitData.length);
					datagramSocket.send(sendDatagramPacket);
					break;
				}

				bufferedOutputStream.write(receiveBuf, 0, readSize);
				if (++flushSize % 1000 == 0) {
					flushSize = 0;
					bufferedOutputStream.flush();
				}
				sendDatagramPacket.setData(UDPUtils.successData, 0, UDPUtils.successData.length);
				datagramSocket.send(sendDatagramPacket);

				// receiveDatagramPacket.setData(receiveBuf,0,receiveBuf.length);
				System.out.println("receive count of " + (++readCount) + " !");
				datagramSocket.receive(receiveDatagramPacket);
			}

			// last flush
			bufferedOutputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedOutputStream != null)
					bufferedOutputStream.close();
				if (datagramSocket != null)
					datagramSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}