package app;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Jmeter_WriteFile {
	/* The default encoding. */
	private static final String DEFAULT_ENCODING = "UTF-8";
	/* version. */
	private static final String VERSION = "1.0.0.0";
	private static final String COMMENT = "2018-02-02 13:39 胡义东 初始化";

	public String getComment() {
		return COMMENT;
	}

	public String getVersion() {
		return VERSION;
	}

	public static void writeFile(String fileName, String content, String method)
			throws IOException {
		/* 写入文件类 */
		File file = new File(fileName);
		/* 获取文件目录 */
		File dir = file.getParentFile();
		/* 如果目录不存在,创建目录 */
		if (!dir.exists()) {
			dir.mkdirs();
		}
		/* 如果文件不存在,创建文件 */
		if (!file.exists()) {
			file.createNewFile();
		}
		/* 定义默认方法为追加ture */
		boolean methodB = true;
		/* 如果方法为"o",就为复写"overWrite" */
		if (method.equals("o")) {
			methodB = false;
		}
		/* 新建缓存字符输出流 */
		BufferedWriter writer = null;
		/* 将字符输出流的文件设为fileName,写入方法为methodB,编码设置为UTF-8 */
		writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(fileName, methodB), DEFAULT_ENCODING));
		/* 写入字符串加换行符 */
		content = content + "\r\n";
		/* 文件中写入字符串 */
		writer.write(content);
		writer.close();
	}
}