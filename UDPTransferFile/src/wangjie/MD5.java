package wangjie;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	/**
	 * Ĭ�ϵ������ַ�����ϣ��������ֽ�ת���� 16 ���Ʊ�ʾ���ַ�,�����Ϊapache2У�������ļ���ȷ�Ե�Ĭ�����
	 */
	protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	protected static MessageDigest messagedigest = null;
	static {
		try {
			messagedigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException nsaex) {
			System.err.println(MD5.class.getName()
					+ "��ʼ��ʧ�ܣ�MessageDigest��֧��MD5Util��");
			nsaex.printStackTrace();
		}
	}

	/**
	 * �ж�һ��md5У�����Ƿ���һ����֪��md5����ƥ��
	 * 
	 * @param password
	 *            ҪУ����ַ���
	 * @param md5PwdStr
	 *            ��֪��md5У����
	 * @return
	 */
	public static boolean checkPassword(String md5, String md5PwdStr) {
		return md5.equals(md5PwdStr);
	}

	/**
	 * �����ļ���md5У��ֵ
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String getFileMD5String(File file) throws IOException {
		InputStream fis;
		fis = new FileInputStream(file);
		byte[] buffer = new byte[1024];
		int numRead = 0;
		while ((numRead = fis.read(buffer)) > 0) {
			messagedigest.update(buffer, 0, numRead);
		}
		fis.close();
		return bufferToHex(messagedigest.digest());
	}

	public static String getMD5String(byte[] bytes) {
		messagedigest.update(bytes);
		return bufferToHex(messagedigest.digest());
	}

	private static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			appendHexPair(bytes[l], stringbuffer);
		}
		return stringbuffer.toString();
	}

	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		char c0 = hexDigits[(bt & 0xf0) >> 4];// ȡ�ֽ��и� 4 λ������ת��, >>>
		// Ϊ�߼����ƣ�������λһ������,�˴�δ�������ַ����кβ�ͬ
		char c1 = hexDigits[bt & 0xf];// ȡ�ֽ��е� 4 λ������ת��
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}
}
