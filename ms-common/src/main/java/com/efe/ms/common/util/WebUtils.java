package com.efe.ms.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
/**
 * 网络工具类。
 *
 * @author carver.gu
 * @since 1.0, Sep 12, 2009
 */
public abstract class WebUtils {

	private static final String DEFAULT_CHARSET = Constants.CHARSET_UTF8;
	private static boolean ignoreSSLCheck = true; // 忽略SSL检查
	private static boolean ignoreHostCheck = true; // 忽略HOST检查

	public static class TrustAllTrustManager implements X509TrustManager {
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}
	}

	private WebUtils() {
	}

	public static void setIgnoreSSLCheck(boolean ignoreSSLCheck) {
		WebUtils.ignoreSSLCheck = ignoreSSLCheck;
	}

	public static void setIgnoreHostCheck(boolean ignoreHostCheck) {
		WebUtils.ignoreHostCheck = ignoreHostCheck;
	}

	/**
	 * 执行HTTP POST请求。
	 *
	 * @param url 请求地址
	 * @param params 请求参数
	 * @return 响应字符串
	 */
	public static String doPost(String url, Map<String, String> params, int connectTimeout, int readTimeout) throws IOException {
		return doPost(url, params, DEFAULT_CHARSET, connectTimeout, readTimeout);
	}

	/**
	 * 执行HTTP POST请求。
	 *
	 * @param url 请求地址
	 * @param params 请求参数
	 * @param charset 字符集，如UTF-8, GBK, GB2312
	 * @return 响应字符串
	 */
	public static String doPost(String url, Map<String, String> params, String charset, int connectTimeout, int readTimeout) throws IOException {
		return doPost(url, params, charset, connectTimeout, readTimeout, null, null);
	}

	public static String doPost(String url, Map<String, String> params, String charset, int connectTimeout, int readTimeout, Map<String, String> headerMap, Proxy proxy) throws IOException {
		String ctype = "application/x-www-form-urlencoded;charset=" + charset;
		String query = buildQuery(params, charset);
		byte[] content = {};
		if (query != null) {
			content = query.getBytes(charset);
		}
		return _doPost(url, ctype, content, connectTimeout, readTimeout, headerMap, proxy);
	}

	public static String doPost(String url, String apiBody, String charset, int connectTimeout, int readTimeout, Map<String, String> headerMap) throws IOException {
		String ctype = "text/plain;charset=" + charset;
		byte[] content = apiBody.getBytes(charset);
		return _doPost(url, ctype, content, connectTimeout, readTimeout, headerMap, null);
	}

	/**
	 * 执行HTTP POST请求。
	 *
	 * @param url 请求地址
	 * @param ctype 请求类型
	 * @param content 请求字节数组
	 * @return 响应字符串
	 */
	public static String doPost(String url, String ctype, byte[] content, int connectTimeout, int readTimeout) throws IOException {
		return _doPost(url, ctype, content, connectTimeout, readTimeout, null, null);
	}

	/**
	 * 执行HTTP POST请求。
	 *
	 * @param url 请求地址
	 * @param ctype 请求类型
	 * @param content 请求字节数组
	 * @param headerMap 请求头部参数
	 * @return 响应字符串
	 */
	public static String doPost(String url, String ctype, byte[] content, int connectTimeout, int readTimeout, Map<String, String> headerMap, Proxy proxy) throws IOException {
		return _doPost(url, ctype, content, connectTimeout, readTimeout, headerMap, proxy);
	}

	private static String _doPost(String url, String ctype, byte[] content, int connectTimeout, int readTimeout, Map<String, String> headerMap, Proxy proxy) throws IOException {
		HttpURLConnection conn = null;
		OutputStream out = null;
		String rsp = null;
		try {
			conn = getConnection(new URL(url), Constants.METHOD_POST, ctype, headerMap, proxy);
			conn.setConnectTimeout(connectTimeout);
			conn.setReadTimeout(readTimeout);
			out = conn.getOutputStream();
			out.write(content);
			rsp = getResponseAsString(conn);
		} finally {
			if (out != null) {
				out.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}

		return rsp;
	}

	/**
	 * 执行带文件上传的HTTP POST请求。
	 *
	 * @param url 请求地址
	 * @param fileParams 文件请求参数
	 * @return 响应字符串
	 */
	public static String doPost(String url, Map<String, String> params, Map<String, FileItem> fileParams, int connectTimeout, int readTimeout) throws IOException {
		if (fileParams == null || fileParams.isEmpty()) {
			return doPost(url, params, DEFAULT_CHARSET, connectTimeout, readTimeout);
		} else {
			return doPost(url, params, fileParams, DEFAULT_CHARSET, connectTimeout, readTimeout);
		}
	}

	public static String doPost(String url, Map<String, String> params, Map<String, FileItem> fileParams, String charset, int connectTimeout, int readTimeout) throws IOException {
		return doPost(url, params, fileParams, charset, connectTimeout, readTimeout, null);
	}

	/**
	 * 执行带文件上传的HTTP POST请求。
	 *
	 * @param url 请求地址
	 * @param fileParams 文件请求参数
	 * @param charset 字符集，如UTF-8, GBK, GB2312
	 * @param headerMap 需要传递的header头，可以为空
	 * @return 响应字符串
	 */
	public static String doPost(String url, Map<String, String> params, Map<String, FileItem> fileParams, String charset,
								int connectTimeout, int readTimeout, Map<String, String> headerMap) throws IOException {
		if (fileParams == null || fileParams.isEmpty()) {
			return doPost(url, params, charset, connectTimeout, readTimeout, headerMap, null);
		} else {
			return _doPostWithFile(url, params, fileParams, charset, connectTimeout, readTimeout, headerMap);
		}
	}

	/**
	 * 执行请求
	 * content_type: aplication/json
	 *
	 * @param url
	 * @param params
	 * @param charset
	 * @param connectTimeout
	 * @param readTimeout
	 * @return
	 * @throws IOException
	 */
	public static String doPostWithJson(String url, Map<String, Object> params, String charset, int connectTimeout, int readTimeout) throws IOException {
		String ctype = "application/json;charset=" + charset;
		byte[] content = {};

		String body = JSON.toJSONString(params);
		if (body != null) {
			content = body.getBytes(charset);
		}
		return _doPost(url, ctype, content, connectTimeout, readTimeout, null, null);
	}

	private static String _doPostWithFile(String url, Map<String, String> params, Map<String, FileItem> fileParams,
										  String charset, int connectTimeout, int readTimeout, Map<String, String> headerMap) throws IOException {
		String boundary = String.valueOf(System.nanoTime()); // 随机分隔线
		HttpURLConnection conn = null;
		OutputStream out = null;
		String rsp = null;
		try {
			String ctype = "multipart/form-data;charset=" + charset + ";boundary=" + boundary;
			conn = getConnection(new URL(url), Constants.METHOD_POST, ctype, headerMap, null);
			conn.setConnectTimeout(connectTimeout);
			conn.setReadTimeout(readTimeout);
			out = conn.getOutputStream();
			byte[] entryBoundaryBytes = ("\r\n--" + boundary + "\r\n").getBytes(charset);

			// 组装文本请求参数
			Set<Entry<String, String>> textEntrySet = params.entrySet();
			for (Entry<String, String> textEntry : textEntrySet) {
				byte[] textBytes = getTextEntry(textEntry.getKey(), textEntry.getValue(), charset);
				out.write(entryBoundaryBytes);
				out.write(textBytes);
			}

			// 组装文件请求参数
			Set<Entry<String, FileItem>> fileEntrySet = fileParams.entrySet();
			for (Entry<String, FileItem> fileEntry : fileEntrySet) {
				FileItem fileItem = fileEntry.getValue();
				if (!fileItem.isValid()) {
					throw new IOException("FileItem is invalid");
				}
				byte[] fileBytes = getFileEntry(fileEntry.getKey(), fileItem.getFileName(), fileItem.getMimeType(), charset);
				out.write(entryBoundaryBytes);
				out.write(fileBytes);
				fileItem.write(out);
			}

			// 添加请求结束标志
			byte[] endBoundaryBytes = ("\r\n--" + boundary + "--\r\n").getBytes(charset);
			out.write(endBoundaryBytes);
			rsp = getResponseAsString(conn);
		} finally {
			if (out != null) {
				out.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}

		return rsp;
	}

	private static byte[] getTextEntry(String fieldName, String fieldValue, String charset) throws IOException {
		StringBuilder entry = new StringBuilder();
		entry.append("Content-Disposition:form-data;name=\"");
		entry.append(fieldName);
		entry.append("\"\r\nContent-Type:text/plain\r\n\r\n");
		entry.append(fieldValue);
		return entry.toString().getBytes(charset);
	}

	private static byte[] getFileEntry(String fieldName, String fileName, String mimeType, String charset) throws IOException {
		StringBuilder entry = new StringBuilder();
		entry.append("Content-Disposition:form-data;name=\"");
		entry.append(fieldName);
		entry.append("\";filename=\"");
		entry.append(fileName);
		entry.append("\"\r\nContent-Type:");
		entry.append(mimeType);
		entry.append("\r\n\r\n");
		return entry.toString().getBytes(charset);
	}

	/**
	 * 执行HTTP GET请求。
	 *
	 * @param url 请求地址
	 * @param params 请求参数
	 * @return 响应字符串
	 */
	public static String doGet(String url, Map<String, String> params) throws IOException {
		return doGet(url, params, DEFAULT_CHARSET);
	}

	/**
	 * 执行HTTP GET请求。
	 *
	 * @param url 请求地址
	 * @param params 请求参数
	 * @param charset 字符集，如UTF-8, GBK, GB2312
	 * @return 响应字符串
	 */
	public static String doGet(String url, Map<String, String> params, String charset) throws IOException {
		HttpURLConnection conn = null;
		String rsp = null;

		try {
			String ctype = "application/x-www-form-urlencoded;charset=" + charset;
			String query = buildQuery(params, charset);
			conn = getConnection(buildGetUrl(url, query), Constants.METHOD_GET, ctype, null, null);
			rsp = getResponseAsString(conn);
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}

		return rsp;
	}

	private static HttpURLConnection getConnection(URL url, String method, String ctype, Map<String, String> headerMap, Proxy proxy) throws IOException {
		HttpURLConnection conn = null;
		if(proxy == null) {
			conn = (HttpURLConnection) url.openConnection();
		} else {
			conn = (HttpURLConnection) url.openConnection(proxy);
		}
		if (conn instanceof HttpsURLConnection) {
			HttpsURLConnection connHttps = (HttpsURLConnection) conn;
			if (ignoreSSLCheck) {
				try {
					SSLContext ctx = SSLContext.getInstance("TLS");
					ctx.init(null, new TrustManager[] { new TrustAllTrustManager() }, new SecureRandom());
					connHttps.setSSLSocketFactory(ctx.getSocketFactory());
					connHttps.setHostnameVerifier(new HostnameVerifier() {
						public boolean verify(String hostname, SSLSession session) {
							return true;
						}
					});
				} catch (Exception e) {
					throw new IOException(e.toString());
				}
			} else {
				if (ignoreHostCheck) {
					connHttps.setHostnameVerifier(new HostnameVerifier() {
						public boolean verify(String hostname, SSLSession session) {
							return true;
						}
					});
				}
			}
			conn = connHttps;
		}

		conn.setRequestMethod(method);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		if(headerMap != null && headerMap.get(Constants.TOP_HTTP_DNS_HOST) != null){
			conn.setRequestProperty("Host", headerMap.get(Constants.TOP_HTTP_DNS_HOST));
		}else{
			conn.setRequestProperty("Host", url.getHost());
		}
		conn.setRequestProperty("Accept", "text/xml,text/javascript");
		conn.setRequestProperty("User-Agent", "top-sdk-java");
		conn.setRequestProperty("Content-Type", ctype);
		if (headerMap != null) {
			for (Map.Entry<String, String> entry : headerMap.entrySet()) {
				if(!Constants.TOP_HTTP_DNS_HOST.equals(entry.getKey())){
					conn.setRequestProperty(entry.getKey(), entry.getValue());
				}
			}
		}
		return conn;
	}

	private static URL buildGetUrl(String url, String query) throws IOException {
		if (StringUtils.isEmpty(query)) {
			return new URL(url);
		}

		return new URL(buildRequestUrl(url, query));
	}

	public static String buildRequestUrl(String url, String... queries) {
		if (queries == null || queries.length == 0) {
			return url;
		}

		StringBuilder newUrl = new StringBuilder(url);
		boolean hasQuery = url.contains("?");
		boolean hasPrepend = url.endsWith("?") || url.endsWith("&");

		for (String query : queries) {
			if (!StringUtils.isEmpty(query)) {
				if (!hasPrepend) {
					if (hasQuery) {
						newUrl.append("&");
					} else {
						newUrl.append("?");
						hasQuery = true;
					}
				}
				newUrl.append(query);
				hasPrepend = false;
			}
		}
		return newUrl.toString();
	}

	public static String buildQuery(Map<String, String> params, String charset) throws IOException {
		if (params == null || params.isEmpty()) {
			return null;
		}

		StringBuilder query = new StringBuilder();
		Set<Entry<String, String>> entries = params.entrySet();
		boolean hasParam = false;

		for (Entry<String, String> entry : entries) {
			String name = entry.getKey();
			String value = entry.getValue();
			// 忽略参数名或参数值为空的参数
			if (areNotEmpty(name, value)) {
				if (hasParam) {
					query.append("&");
				} else {
					hasParam = true;
				}

				query.append(name).append("=").append(URLEncoder.encode(value, charset));
			}
		}

		return query.toString();
	}

	protected static String getResponseAsString(HttpURLConnection conn) throws IOException {
		String charset = getResponseCharset(conn.getContentType());
		if (conn.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
			String contentEncoding = conn.getContentEncoding();
			if (Constants.CONTENT_ENCODING_GZIP.equalsIgnoreCase(contentEncoding)) {
				return getStreamAsString(new GZIPInputStream(conn.getInputStream()), charset);
			} else {
				return getStreamAsString(conn.getInputStream(), charset);
			}
		} else {
			// OAuth bad request always return 400 status
			if (conn.getResponseCode() == HttpURLConnection.HTTP_BAD_REQUEST) {
				InputStream error = conn.getErrorStream();
				if (error != null) {
					return getStreamAsString(error, charset);
				}
			}
			// Client Error 4xx and Server Error 5xx
			throw new IOException(conn.getResponseCode() + " " + conn.getResponseMessage());
		}
	}

	public static String getStreamAsString(InputStream stream, String charset) throws IOException {
		try {
			Reader reader = new InputStreamReader(stream, charset);
			StringBuilder response = new StringBuilder();

			final char[] buff = new char[1024];
			int read = 0;
			while ((read = reader.read(buff)) > 0) {
				response.append(buff, 0, read);
			}

			return response.toString();
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
	}

	public static String getResponseCharset(String ctype) {
		String charset = DEFAULT_CHARSET;

		if (!StringUtils.isEmpty(ctype)) {
			String[] params = ctype.split(";");
			for (String param : params) {
				param = param.trim();
				if (param.startsWith("charset")) {
					String[] pair = param.split("=", 2);
					if (pair.length == 2) {
						if (!StringUtils.isEmpty(pair[1])) {
							charset = pair[1].trim();
						}
					}
					break;
				}
			}
		}

		return charset;
	}

	/**
	 * 使用默认的UTF-8字符集反编码请求参数值。
	 *
	 * @param value 参数值
	 * @return 反编码后的参数值
	 */
	public static String decode(String value) {
		return decode(value, DEFAULT_CHARSET);
	}

	/**
	 * 使用默认的UTF-8字符集编码请求参数值。
	 *
	 * @param value 参数值
	 * @return 编码后的参数值
	 */
	public static String encode(String value) {
		return encode(value, DEFAULT_CHARSET);
	}

	/**
	 * 使用指定的字符集反编码请求参数值。
	 *
	 * @param value 参数值
	 * @param charset 字符集
	 * @return 反编码后的参数值
	 */
	public static String decode(String value, String charset) {
		String result = null;
		if (!StringUtils.isEmpty(value)) {
			try {
				result = URLDecoder.decode(value, charset);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return result;
	}

	/**
	 * 使用指定的字符集编码请求参数值。
	 *
	 * @param value 参数值
	 * @param charset 字符集
	 * @return 编码后的参数值
	 */
	public static String encode(String value, String charset) {
		String result = null;
		if (!StringUtils.isEmpty(value)) {
			try {
				result = URLEncoder.encode(value, charset);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return result;
	}

	/**
	 * 从URL中提取所有的参数。
	 *
	 * @param query URL地址
	 * @return 参数映射
	 */
	public static Map<String, String> splitUrlQuery(String query) {
		Map<String, String> result = new HashMap<String, String>();

		String[] pairs = query.split("&");
		if (pairs != null && pairs.length > 0) {
			for (String pair : pairs) {
				String[] param = pair.split("=", 2);
				if (param != null && param.length == 2) {
					result.put(param[0], param[1]);
				}
			}
		}

		return result;
	}
	
	public static boolean areNotEmpty(String... values) {
		boolean result = true;
		if (values == null || values.length == 0) {
			result = false;
		} else {
			for (String value : values) {
				result &= !StringUtils.isBlank(value);
			}
		}
		return result;
	}
	
	public static class FileItem {

		private Contract contract;

		/**
		 * 基于本地文件的构造器，适用于上传本地文件。
		 * 
		 * @param file 本地文件
		 */
		public FileItem(final File file) {
			this.contract = new LocalContract(file);
		}

		/**
		 * 基于文件绝对路径的构造器，适用于上传本地文件。
		 * 
		 * @param filePath 文件绝对路径
		 */
		public FileItem(String filePath) {
			this(new File(filePath));
		}

		/**
		 * 基于文件名和字节数组的构造器。
		 * 
		 * @param fileName 文件名
		 * @param content 文件字节数组
		 */
		public FileItem(String fileName, byte[] content) {
			this(fileName, content, null);
		}

		/**
		 * 基于文件名、字节数组和媒体类型的构造器。
		 * 
		 * @param fileName 文件名
		 * @param content 文件字节数组
		 * @param mimeType 媒体类型，如：image/jpeg, text/plain
		 */
		public FileItem(String fileName, byte[] content, String mimeType) {
			this.contract = new ByteArrayContract(fileName, content, mimeType);
		}

		/**
		 * 基于文件名和字节流的构造器，适应于全流式上传，减少本地内存开销。
		 * 
		 * @param fileName 文件名
		 * @param content 文件字节流
		 */
		public FileItem(String fileName, InputStream stream) {
			this(fileName, stream, null);
		}

		/**
		 * 基于文件名、字节流和媒体类型的构造器，适应于全流式上传，减少本地内存开销。
		 * 
		 * @param fileName 文件名
		 * @param content 文件字节流
		 * @param mimeType 媒体类型，如：image/jpeg, text/plain
		 */
		public FileItem(String fileName, InputStream stream, String mimeType) {
			this.contract = new StreamContract(fileName, stream, mimeType);
		}

		public boolean isValid() {
			return this.contract.isValid();
		}

		public String getFileName() {
			return this.contract.getFileName();
		}

		public String getMimeType() throws IOException {
			return this.contract.getMimeType();
		}

		public long getFileLength() {
			return this.contract.getFileLength();
		}

		public void write(OutputStream output) throws IOException {
			this.contract.write(output);
		}

		private static interface Contract {
			public boolean isValid();
			public String getFileName();
			public String getMimeType();
			public long getFileLength();
			public void write(OutputStream output) throws IOException;
		}

		private static class LocalContract implements Contract {
			private File file;

			public LocalContract(File file) {
				this.file = file;
			}

			public boolean isValid() {
				return this.file != null && this.file.exists() && this.file.isFile();
			}

			public String getFileName() {
				return this.file.getName();
			}

			public String getMimeType() {
				return Constants.MIME_TYPE_DEFAULT;
			}

			public long getFileLength() {
				return this.file.length();
			}

			public void write(OutputStream output) throws IOException {
				InputStream input = null;
				try {
					input = new FileInputStream(this.file);
					byte[] buffer = new byte[Constants.READ_BUFFER_SIZE];
					int n = 0;
					while (-1 != (n = input.read(buffer))) {
						output.write(buffer, 0, n);
					}
				} finally {
					if (input != null) {
						input.close();
					}
				}
			}
		}

		private static class ByteArrayContract implements Contract {
			private String fileName;
			private byte[] content;
			private String mimeType;

			public ByteArrayContract(String fileName, byte[] content, String mimeType) {
				this.fileName = fileName;
				this.content = content;
				this.mimeType = mimeType;
			}

			public boolean isValid() {
				return this.content != null && this.fileName != null;
			}

			public String getFileName() {
				return this.fileName;
			}

			public String getMimeType() {
				if (this.mimeType == null) {
					return Constants.MIME_TYPE_DEFAULT;
				} else {
					return this.mimeType;
				}
			}

			public long getFileLength() {
				return this.content.length;
			}

			public void write(OutputStream output) throws IOException {
				output.write(this.content);
			}
		}

		private static class StreamContract implements Contract {
			private String fileName;
			private InputStream stream;
			private String mimeType;

			public StreamContract(String fileName, InputStream stream, String mimeType) {
				this.fileName = fileName;
				this.stream = stream;
				this.mimeType = mimeType;
			}

			public boolean isValid() {
				return this.stream != null && this.fileName != null;
			}

			public String getFileName() {
				return this.fileName;
			}

			public String getMimeType() {
				if (this.mimeType == null) {
					return Constants.MIME_TYPE_DEFAULT;
				} else {
					return this.mimeType;
				}
			}

			public long getFileLength() {
				return 0L;
			}

			public void write(OutputStream output) throws IOException {
				try {
					byte[] buffer = new byte[Constants.READ_BUFFER_SIZE];
					int n = 0;
					while (-1 != (n = stream.read(buffer))) {
						output.write(buffer, 0, n);
					}
				} finally {
					if (stream != null) {
						stream.close();
					}
				}
			}
		}

	}
	
	public static class Constants {

		/** TOP协议入参共享参数 **/
		public static final String APP_KEY = "app_key";
		public static final String FORMAT = "format";
		public static final String METHOD = "method";
		public static final String TIMESTAMP = "timestamp";
		public static final String VERSION = "v";
		public static final String SIGN = "sign";
		public static final String SIGN_METHOD = "sign_method";
		public static final String PARTNER_ID = "partner_id";
		public static final String SESSION = "session";
		public static final String SIMPLIFY = "simplify";
		public static final String TARGET_APP_KEY = "target_app_key";

		/** TOP协议出参共享参数 */
		public static final String ERROR_RESPONSE = "error_response";
		public static final String ERROR_CODE = "code";
		public static final String ERROR_MSG = "msg";
		public static final String ERROR_SUB_CODE = "sub_code";
		public static final String ERROR_SUB_MSG = "sub_msg";

		/** 奇门协议共享参数 */
		public static final String QIMEN_CLOUD_ERROR_RESPONSE = "response";
		public static final String QM_ROOT_TAG = "request";
		public static final String QM_CUSTOMER_ID = "customerId";
		public static final String QM_CONTENT_TYPE = "text/xml;charset=utf-8";
		public static final String QM_CONTENT_TYPE_JSON = "application/json;charset=utf-8";

		/** TOP默认时间格式 **/
		public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

		/** TOP Date默认时区 **/
		public static final String DATE_TIMEZONE = "GMT+8";

		/** UTF-8字符集 **/
		public static final String CHARSET_UTF8 = "UTF-8";

		/** HTTP请求相关 **/
		public static final String METHOD_POST = "POST";
		public static final String METHOD_GET = "GET";
		public static final String CTYPE_FORM_DATA = "application/x-www-form-urlencoded";
		public static final String CTYPE_FILE_UPLOAD = "multipart/form-data";
		public static final String CTYPE_TEXT_XML = "text/xml";
		public static final String CTYPE_APPLICATION_XML = "application/xml";
		public static final String CTYPE_TEXT_PLAIN = "text/plain";
		public static final String CTYPE_APP_JSON = "application/json";

		/** GBK字符集 **/
		public static final String CHARSET_GBK = "GBK";

		/** TOP JSON 应格式 */
		public static final String FORMAT_JSON = "json";
		/** TOP XML 应格式 */
		public static final String FORMAT_XML = "xml";

		/** TOP JSON 新格式 */
		public static final String FORMAT_JSON2 = "json2";
		/** TOP XML 新格式 */
		public static final String FORMAT_XML2 = "xml2";

		/** MD5签名方式 */
		public static final String SIGN_METHOD_MD5 = "md5";
		/** HMAC签名方式 */
		public static final String SIGN_METHOD_HMAC = "hmac";
		/** HMAC-SHA256签名方式 */
		public static final String SIGN_METHOD_HMAC_SHA256 = "hmac-sha256";

		/** SDK版本号 */
		public static final String SDK_VERSION = "top-sdk-java-20200923";

		/** 异步多活SDK版本号 */
		public static final String SDK_VERSION_CLUSTER = "top-sdk-java-cluster-20200923";
		
		/** httpdns SDK版本号 */
	    public static final String SDK_VERSION_HTTPDNS = "top-sdk-java-httpdns-20200923";
	    
	    /** httpdns SDK版本号 */
	    public static final String QIMEN_SDK_VERSION_HTTPDNS = "top-qimen-sdk-java-httpdns";

		/** 响应编码 */
		public static final String ACCEPT_ENCODING = "Accept-Encoding";
		public static final String CONTENT_ENCODING = "Content-Encoding";
		public static final String CONTENT_ENCODING_GZIP = "gzip";

		/** 默认媒体类型 **/
		public static final String MIME_TYPE_DEFAULT = "application/octet-stream";

		/** 默认流式读取缓冲区大小 **/
		public static final int READ_BUFFER_SIZE = 1024 * 4;
		
		
		public static final String  TOP_HTTP_DNS_HOST  = "TOP_HTTP_DNS_HOST";

		/** API网关请求content type **/
		public static final String CONTENT_TYPE_XML = "xml";
		public static final String CONTENT_TYPE_JSON = "json";
		public static final String CONTENT_TYPE_FORM = "form";

		public static final String RESPONSE_TYPE_TOP = "top";
		public static final String RESPONSE_TYPE_QIMEN = "qimen1";
		public static final String RESPONSE_TYPE_QIMEN2 = "qimen2";
		public static final String RESPONSE_TYPE_DINGTALK_OAPI = "dingtalk";

		/** 钉钉调用方式 TOP标准格式，form-data */
		public static final String CALL_TYPE_TOP = "top";

		/** 钉钉调用方式 OAPI兼容格式，application/json */
		public static final String CALL_TYPE_OAPI = "oapi";
	}

}
