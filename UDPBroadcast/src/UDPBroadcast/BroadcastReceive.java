/**
 * ClassName: BuddhaBless 
 * @author lanhm
 * @date forever
 * @version 1.0
 * Description:
 * 
 * ......................我佛慈悲......................
 *                       _oo0oo_
 *                      o8888888o
 *                      88" . "88
 *                      (| -_- |)
 *                      0\  =  /0
 *                    ___/`---'\___
 *                  .' \\|     |// '.
 *                 / \\|||  :  |||// \
 *                / _||||| -卍-|||||- \
 *               |   | \\\  -  /// |   |
 *               | \_|  ''\---/''  |_/ |
 *               \  .-\__  '-'  ___/-. /
 *             ___'. .'  /--.--\  `. .'___
 *          ."" '<  `.___\_<|>_/___.' >' "".
 *         | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 *         \  \ `_.   \_ __\ /__ _/   .-` /  /
 *     =====`-.____`.___ \_____/___.-`___.-'=====
 *                       `=---='
 *                       
 *..................佛祖开光 ,永无BUG...................
 * 
 */

package UDPBroadcast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

public class BroadcastReceive {
	public static void main(String[] args) throws IOException {

		DatagramPacket receive = new DatagramPacket(new byte[1024], 1024);
		DatagramSocket server = new DatagramSocket(8888);

		System.out.println("---------------------------------");
		System.out.println("Server current start......");
		System.out.println("---------------------------------");

		while (true) {
			server.receive(receive);
			byte[] recvByte = Arrays.copyOfRange(receive.getData(), 0, receive.getLength());
			System.out.println("Server receive msg:" + new String(recvByte));
		}
	}
}