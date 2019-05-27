package app;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class Jmeter_HMACSHA1 {

	/* The default encoding. */
	private static final String DEFAULT_ENCODING = "UTF-8";

	/* Signature method. */
	private static final String ALGORITHM = "HmacSHA1";

	/* Signature version. */
	private static final String VERSION = "1.0.0.0";

	private static final Object LOCK = new Object();

	private static final String COMMENT = "2018-02-02 13:39 胡义东 初始化";

	public String getComment() {
		return COMMENT;
	}

	/* Prototype of the Mac instance. */
	private static Mac macInstance;

	public String getAlgorithm() {
		return ALGORITHM;
	}

	public String getVersion() {
		return VERSION;
	}

	// encode to base64
	public static String computeSignature(String key, String data) {
		try {
			byte[] signData = sign(key.getBytes(DEFAULT_ENCODING),
					data.getBytes(DEFAULT_ENCODING));
			return new String(Base64.encodeBase64(signData));
		} catch (UnsupportedEncodingException ex) {
			throw new RuntimeException("Unsupported algorithm: "
					+ DEFAULT_ENCODING, ex);
		}
	}

	private static byte[] sign(byte[] key, byte[] data) {
		try {
			// Because Mac.getInstance(String) calls a synchronized method, it
			// could block on
			// invoked concurrently, so use prototype pattern to improve perf.
			if (macInstance == null) {
				synchronized (LOCK) {
					if (macInstance == null) {
						macInstance = Mac.getInstance(ALGORITHM);
					}
				}
			}

			Mac mac = null;
			try {
				mac = (Mac) macInstance.clone();
			} catch (CloneNotSupportedException e) {
				// If it is not clonable, create a new one.
				mac = Mac.getInstance(ALGORITHM);
			}
			mac.init(new SecretKeySpec(key, ALGORITHM));
			return mac.doFinal(data);
		} catch (NoSuchAlgorithmException ex) {
			throw new RuntimeException("Unsupported algorithm: " + ALGORITHM,
					ex);
		} catch (InvalidKeyException ex) {
			throw new RuntimeException("Invalid key: " + key, ex);
		}
	}
}