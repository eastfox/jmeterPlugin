package app;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;

public class Jmeter_CangoSign {
	private static final String VERSION = "1.0.0.0";
	private static final String COMMENT = "2019-04-22 09:22  胡义东 初始化";

	public String getComment() {
		return COMMENT;
	}

	public String getVersion() {
		return VERSION;
	}

	/**
	 * 创建签名
	 * 
	 * @param paramStr
	 *            请求数据
	 * @param key
	 *            加密串
	 * @return 签名
	 * @throws Exception
	 */
	public static String createSign(String paramStr, String key)
			throws Exception {
		JSONObject param = JSONObject.parseObject(paramStr);
		param.put("key", key);
		Map<String, String> params = jsonToMap(param);
		String data = getSignMsg(params);
		String md5Str = Jmeter_CangoSign.MD5(data);
		return HMACSHA256(md5Str, key);

	}

	/**
	 * 验证签名
	 * 
	 * @param paramStr
	 *            请求数据
	 * @param key
	 *            加密串
	 * @return 验签结果
	 * @throws Exception
	 */
	public static boolean checkSign(String paramStr, String key)
			throws Exception {
		JSONObject param = JSONObject.parseObject(paramStr);
		String sign = param.getString("sign");
		String creageSign = Jmeter_CangoSign.createSign(paramStr, key);
		if (StringUtils.isEmpty(sign)) {
			return false;
		}
		return creageSign.equals(sign);
	}

	/**
	 * 生成 MD5
	 * 
	 * @param data
	 *            待处理数据
	 * @return MD5结果
	 */
	public static String MD5(String data) throws Exception {
		java.security.MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] array = md.digest(data.getBytes("UTF-8"));
		StringBuilder sb = new StringBuilder();
		for (byte item : array) {
			sb.append(Integer.toHexString((item & 0xFF) | 0x100)
					.substring(1, 3));
		}
		return sb.toString().toUpperCase();
	}

	/**
	 * 生成 HMACSHA256
	 * 
	 * @param data
	 *            待处理数据
	 * @param key
	 *            密钥
	 * @return 加密结果
	 * @throws Exception
	 */
	public static String HMACSHA256(String data, String key) throws Exception {
		Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
		SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"),
				"HmacSHA256");
		sha256_HMAC.init(secret_key);
		byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
		StringBuilder sb = new StringBuilder();
		for (byte item : array) {
			sb.append(Integer.toHexString((item & 0xFF) | 0x100)
					.substring(1, 3));
		}
		return sb.toString().toUpperCase();
	}

	public static Map<String, String> sortMapByKey(Map<String, String> map) {
		if (map == null || map.isEmpty()) {
			return null;
		}

		Map<String, String> sortMap = new TreeMap<String, String>(
				new MapKeyComparator());

		sortMap.putAll(map);

		return sortMap;
	}

	public static Map<String, String> jsonToMap(JSONObject json) {
		Map<String, String> map = new HashMap<>();
		for (Object key : json.keySet()) {
			String value = json.getString((String) key);
			map.put((String) key, value);
		}
		return sortMapByKey(map);
	}

	public static String getSignMsg(Map<String, String> params)
			throws Exception {

		if (params == null || params.size() == 0) {
			return "";
		}

		List<String> keys = new ArrayList<>(params.size());

		for (String key : params.keySet()) {
			if ("sign".equals(key))
				continue;
			if (StringUtils.isEmpty(params.get(key)))
				continue;
			keys.add(key);
		}

		Collections.sort(keys);

		StringBuilder buf = new StringBuilder();

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);

			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				buf.append(key + "=" + value);
			} else {
				buf.append(key + "=" + value + "&");
			}
		}

		return buf.toString();
	}

}

class MapKeyComparator implements Comparator<String> {

	@Override
	public int compare(String str1, String str2) {

		return str1.compareTo(str2);
	}
}