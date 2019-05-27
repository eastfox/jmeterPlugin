package app;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.thoughtworks.xstream.core.util.Base64Encoder;

public class Jmeter_AES {
	private static final String VERSION = "1.0.0.0";
	private static final String COMMENT = "2019-04-18 21:39 胡义东 初始化";

	public String getComment() {
		return COMMENT;
	}
	
	public String getVersion() {
		return VERSION;
	}

	/**
	 * 加密--把加密后的byte数组先进行二进制转16进制在进行base64编码
	 * 
	 * @param sSrc
	 * @param sKey
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String sSrc, String sKey) throws Exception {
		if (sKey == null) {
			throw new IllegalArgumentException("Argument sKey is null.");
		}
		if (sKey.length() != 16) {
			throw new IllegalArgumentException(
					"Argument sKey'length is not 16.");
		}
		byte[] raw = sKey.getBytes("ASCII");
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

		byte[] encrypted = cipher.doFinal(sSrc.getBytes("UTF-8"));
		String tempStr = parseByte2HexStr(encrypted);

		Base64Encoder encoder = new Base64Encoder();
		return encoder.encode(tempStr.getBytes("UTF-8"));
	}

	/**
	 * 解密--先 进行base64解码，在进行16进制转为2进制然后再解码
	 * 
	 * @param sSrc
	 * @param sKey
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String sSrc, String sKey) throws Exception {

		if (sKey == null) {
			throw new IllegalArgumentException("499");
		}
		if (sKey.length() != 16) {
			throw new IllegalArgumentException("498");
		}

		byte[] raw = sKey.getBytes("ASCII");
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);

		Base64Encoder encoder = new Base64Encoder();
		byte[] encrypted1 = encoder.decode(sSrc);

		String tempStr = new String(encrypted1, "utf-8");
		encrypted1 = parseHexStr2Byte(tempStr);
		byte[] original = cipher.doFinal(encrypted1);
		String originalString = new String(original, "utf-8");
		return originalString;
	}

	/**
	 * 将二进制转换成16进制
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 将16进制转换为二进制
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
					16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}
}