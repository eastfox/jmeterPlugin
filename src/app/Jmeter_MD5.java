package app;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Jmeter_MD5 {
	private static final String VERSION = "1.0.0.0";
	private static final String COMMENT = "2018-02-02 13:39 胡义东 初始化";

	public String getComment() {
		return COMMENT;
	}

	public String getVersion() {
		return VERSION;
	}

	public static String MD5(String sourceStr) {
		String result = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			try {
				md.update(sourceStr.getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			result = buf.toString().toUpperCase();
			// System.out.println(result);
		} catch (NoSuchAlgorithmException e) {
			System.out.println(e);
		}
		return result;
	}
}