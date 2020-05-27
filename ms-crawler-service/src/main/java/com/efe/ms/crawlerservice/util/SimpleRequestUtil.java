package com.efe.ms.crawlerservice.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.UriUtils;

/**
 * 简单的请求工具类
 * @author Tianlong Liu
 * @2020年4月16日 上午11:09:08
 */
public final class SimpleRequestUtil {
	// 默认编码类型
	private final static String DEFAULT_CHARSET = "UTF-8";
	// GET请求方式
	private final static String METHOD_GET = "GET";
	// POST请求方式
	private final static String METHOD_POST = "POST";
	// 默认超时时间
	private final static int DEFAULT_TIME_OUT = 1000 * 15;
	// URL验证正则表达式
	private final static String URL_VALIDATE_REGEX = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
	// 正则表达式验证器
	private final static Pattern PATTERN = Pattern.compile(URL_VALIDATE_REGEX);

	/**
	 * GET请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public final static byte[] doGet(String url) throws Exception {
		return doRequest(url, null, METHOD_GET, DEFAULT_CHARSET);
	}

	public final static byte[] doGet(String url, String charset) throws Exception {
		return doRequest(url, null, METHOD_GET, charset);
	}

	public final static byte[] doGet(String url, Map<String, String> params) throws Exception {
		return doRequest(concatGetParams(url, params), params, METHOD_GET, DEFAULT_CHARSET);
	}

	public final static byte[] doGet(String url, Map<String, String> params, String charset) throws Exception {
		return doRequest(concatGetParams(url, params), params, METHOD_GET, charset);
	}

	public final static String doGetAsString(String url) throws Exception {
		byte[] bytes = doRequest(url, null, METHOD_GET, DEFAULT_CHARSET);
		return bytes == null ? null : new String(bytes, DEFAULT_CHARSET);
	}

	public final static String doGetAsString(String url, String charset) throws Exception {
		byte[] bytes = doRequest(url, null, METHOD_GET, charset);
		return bytes == null ? null : new String(bytes, charset);
	}

	public final static String doGetAsString(String url, Map<String, String> params) throws Exception {
		byte[] bytes = doRequest(concatGetParams(url, params), params, METHOD_GET, DEFAULT_CHARSET);
		return bytes == null ? null : new String(bytes, DEFAULT_CHARSET);
	}

	public final static String doGetAsString(String url, Map<String, String> params, String charset) throws Exception {
		byte[] bytes = doRequest(concatGetParams(url, params), params, METHOD_GET, charset);
		return bytes == null ? null : new String(bytes, charset);
	}

	/**
	 * POST请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public final static byte[] doPost(String url) throws Exception {
		return doRequest(url, null, METHOD_POST, DEFAULT_CHARSET);
	}

	public final static byte[] doPost(String url, String charset) throws Exception {
		return doRequest(url, null, METHOD_POST, charset);
	}

	public final static byte[] doPost(String url, Map<String, String> params) throws Exception {
		return doRequest(url, params, METHOD_POST, DEFAULT_CHARSET);
	}

	public final static byte[] doPost(String url, Map<String, String> params, String charset) throws Exception {
		return doRequest(url, params, METHOD_POST, charset);
	}

	public final static String doPostAsString(String url) throws Exception {
		byte[] bytes = doRequest(url, null, METHOD_POST, DEFAULT_CHARSET);
		return bytes == null ? null : new String(bytes, DEFAULT_CHARSET);
	}

	public final static String doPostAsString(String url, String charset) throws Exception {
		byte[] bytes = doRequest(url, null, METHOD_POST, charset);
		return bytes == null ? null : new String(bytes, charset);
	}

	public final static String doPostAsString(String url, Map<String, String> params) throws Exception {
		byte[] bytes = doRequest(url, params, METHOD_POST, DEFAULT_CHARSET);
		return bytes == null ? null : new String(bytes, DEFAULT_CHARSET);
	}

	public final static String doPostAsString(String url, Map<String, String> params, String charset) throws Exception {
		byte[] bytes = doRequest(url, params, METHOD_POST, charset);
		return bytes == null ? null : new String(bytes, charset);
	}

	private final static byte[] doRequest(String urlStr, Map<String, String> params, String method, String charset)
			throws Exception {
		InputStream is = null;
		ByteArrayOutputStream os = null;
		byte[] buff = new byte[1024];
		int len = 0;
		HttpURLConnection conn = null;
		try {
			URL url = new URL(UriUtils.encodePath(urlStr, charset));
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty("Content-Type", "plain/text;charset=" + charset);
			conn.setRequestProperty("charset", charset);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod(method);
			conn.setReadTimeout(DEFAULT_TIME_OUT);
			conn.connect();
			if (METHOD_POST.equalsIgnoreCase(method)) {
				setPostParams(conn, params, charset);
			}
			is = conn.getInputStream();
			os = new ByteArrayOutputStream();
			while ((len = is.read(buff)) != -1) {
				os.write(buff, 0, len);
			}
		} catch (IOException e) {
			throw e;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
				}
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
		return os.toByteArray();
	}

	private final static String concatGetParams(String url, Map<String, String> params) {
		if (StringUtils.isBlank(url)) {
			return null;
		}
		if (params != null) {
			Set<Map.Entry<String, String>> set = params.entrySet();
			Iterator<Map.Entry<String, String>> iterator = set.iterator();
			Map.Entry<String, String> entry = null;
			String name = null;
			while (iterator.hasNext()) {
				entry = iterator.next();
				name = entry.getKey() + "=" + entry.getValue();
				if (!url.contains("?")) {
					url += "?";
				}
				url += "&" + name;
			}
		}
		return url;
	}

	private final static void setPostParams(HttpURLConnection conn, Map<String, String> params, String charset) {
		if (params == null) {
			return;
		}
		// 建立输入流，向指向的URL传入参数
		DataOutputStream dos = null;
		try {
			dos = new DataOutputStream(conn.getOutputStream());
			Set<Map.Entry<String, String>> set = params.entrySet();
			Iterator<Map.Entry<String, String>> iterator = set.iterator();
			Map.Entry<String, String> entry = null;
			while (iterator.hasNext()) {
				entry = iterator.next();
				dos.writeBytes(entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), charset));
			}
		} catch (IOException e) {

		} finally {
			if (dos != null) {
				try {
					dos.flush();
					dos.close();
				} catch (IOException e) {
				}
			}
		}
	}

	/**
	 * 验证是否是URL
	 * 
	 * @param url
	 * @return
	 */
	public final static boolean isURL(String url) {
		return PATTERN.matcher(url).matches();
	}
}
