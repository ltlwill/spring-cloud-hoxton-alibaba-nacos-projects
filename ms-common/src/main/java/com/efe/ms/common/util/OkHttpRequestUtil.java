package com.efe.ms.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * okhttp请求工具
 * 
 * @author Tianlong Liu 2020年8月21日 下午12:01:57
 */
public final class OkHttpRequestUtil {

	protected static final String DEFAULT_CHARSET = "UTF-8";
	protected static final String DEFAULT_ALGORITHM_NAME = "HmacSHA256";
	protected static final String HTTP_PREFIX = "http://";
	protected static final String HTTPS_PREFIX = "https://";
	protected static final long DEFAULT_TIMEOUT_MINUTES = 10; // 默认超时10分钟

	public static final class ContentType {
		public static final MediaType TEXT_PLAIN = MediaType.parse(org.springframework.http.MediaType.TEXT_PLAIN_VALUE);
		public static final MediaType TEXT_XML = MediaType.parse(org.springframework.http.MediaType.TEXT_XML_VALUE);
		public static final MediaType TEXT_HTML = MediaType.parse(org.springframework.http.MediaType.TEXT_HTML_VALUE);
		public static final MediaType APPLICATION_JSON = MediaType
				.parse(org.springframework.http.MediaType.APPLICATION_JSON_VALUE);
		public static final MediaType APPLICATION_XML = MediaType
				.parse(org.springframework.http.MediaType.APPLICATION_XML_VALUE);
		public static final MediaType APPLICATION_OCTET_STREAM = MediaType
				.parse(org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE);
		public static final MediaType MULTIPART_FORM_DATA = MediaType
				.parse(org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE);

	}

	/**
	 * 创建text/plain类型的请求体
	 * 
	 * @param content
	 * @return
	 */
	public static RequestBody crateTextPlainBody(String content) {
		return createBody(ContentType.TEXT_PLAIN, content);
	}

	/**
	 * 创建text/xml类型的请求体
	 * 
	 * @param content
	 * @return
	 */
	public static RequestBody createTextXMLBody(String content) {
		return createBody(ContentType.TEXT_XML, content);
	}

	/**
	 * 创建text/html类型的请求体
	 * 
	 * @param content
	 * @return
	 */
	public static RequestBody createTextHTMLBody(String content) {
		return createBody(ContentType.TEXT_HTML, content);
	}

	/**
	 * 创建application/json类型的请求体
	 * 
	 * @param content
	 * @return
	 */
	public static RequestBody createApplicationJSONBody(String content) {
		return createBody(ContentType.APPLICATION_JSON, content);
	}

	/**
	 * 创建application/xml类型的请求体
	 * 
	 * @param content
	 * @return
	 */
	public static RequestBody createApplicationXMLBody(String content) {
		return createBody(ContentType.APPLICATION_XML, content);
	}

	public static RequestBody createBody(MediaType mediaType, String content) {
		return RequestBody.create(mediaType, content);
	}

	public static Response doGet(String url) {
		return doRequest(createGetRequest(url, null));
	}

	public static Response doGet(String url, Map<String, String> headers) {
		return doRequest(createGetRequest(url, headers));
	}

	public static String doGetAsString(String url) {
		return doRequestAsString(createGetRequest(url, null));
	}

	public static String doGetAsString(String url, Map<String, String> headers) {
		return doRequestAsString(createGetRequest(url, headers));
	}

	public static Response doPost(String url, RequestBody body) {
		return doRequest(createPostRequest(url, null, body));
	}

	public static Response doPost(String url, Map<String, String> headers, RequestBody body) {
		return doRequest(createPostRequest(url, headers, body));
	}

	public static Response doPost(String url, String jsonBody) {
		return doRequest(createPostRequest(url, null, createApplicationJSONBody(jsonBody)));
	}

	public static Response doPost(String url, Map<String, String> headers, String jsonBody) {
		return doRequest(createPostRequest(url, headers, createApplicationJSONBody(jsonBody)));
	}

	public static String doPostAsString(String url, RequestBody body) {
		return doRequestAsString(createPostRequest(url, null, body));
	}

	public static String doPostAsString(String url, Map<String, String> headers, RequestBody body) {
		return doRequestAsString(createPostRequest(url, headers, body));
	}

	public static String doPostAsString(String url, String jsonBody) {
		return doRequestAsString(createPostRequest(url, null, createApplicationJSONBody(jsonBody)));
	}

	public static String doPostAsString(String url, Map<String, String> headers, String jsonBody) {
		return doRequestAsString(
				createPostRequest(url, headers, createApplicationJSONBody(jsonBody)));
	}

	public static Response doPut(String url, RequestBody body) {
		return doRequest(createPutRequest(url, null, body));
	}

	public static Response doPut(String url, Map<String, String> headers, RequestBody body) {
		return doRequest(createPutRequest(url, headers, body));
	}

	public static Response doPut(String url, String jsonBody) {
		return doRequest(createPutRequest(url, null, createApplicationJSONBody(jsonBody)));
	}

	public static Response doPut(String url, Map<String, String> headers, String jsonBody) {
		return doRequest(createPutRequest(url, headers, createApplicationJSONBody(jsonBody)));
	}

	public static String doPutAsString(String url, RequestBody body) {
		return doRequestAsString(createPutRequest(url, null, body));
	}

	public static String doPutAsString(String url, Map<String, String> headers, RequestBody body) {
		return doRequestAsString(createPutRequest(url, headers, body));
	}

	public static String doPutAsString(String url, String jsonBody) {
		return doRequestAsString(createPutRequest(url, null, createApplicationJSONBody(jsonBody)));
	}

	public static String doPutAsString(String url, Map<String, String> headers, String jsonBody) {
		return doRequestAsString(
				createPutRequest(url, headers, createApplicationJSONBody(jsonBody)));
	}

	public static Response doDelete(String url) {
		return doRequest(createDeleteRequest(url, null));
	}
	
	public static Response doDelete(String url, Map<String, String> headers) {
		return doRequest(createDeleteRequest(url, headers));
	}
	
	public static Response doDelete(String url, RequestBody body) {
		return doRequest(createDeleteRequest(url, null, body));
	}

	public static Response doDelete(String url, Map<String, String> headers, RequestBody body) {
		return doRequest(createDeleteRequest(url, headers, body));
	}

	public static Response doDelete(String url, String jsonBody) {
		return doRequest(createDeleteRequest(url, null, createApplicationJSONBody(jsonBody)));
	}

	public static Response doDelete(String url, Map<String, String> headers, String jsonBody) {
		return doRequest(createDeleteRequest(url, headers, createApplicationJSONBody(jsonBody)));
	}

	public static String doDeleteAsString(String url, RequestBody body) {
		return doRequestAsString(createDeleteRequest(url, null, body));
	}

	public static String doDeleteAsString(String url, Map<String, String> headers, RequestBody body) {
		return doRequestAsString(createDeleteRequest(url, headers, body));
	}

	public static String doDeleteAsString(String url, String jsonBody) {
		return doRequestAsString(
				createDeleteRequest(url, null, createApplicationJSONBody(jsonBody)));
	}

	public static String doDeleteAsString(String url, Map<String, String> headers, String jsonBody) {
		return doRequestAsString(
				createDeleteRequest(url, headers, createApplicationJSONBody(jsonBody)));
	}

	public static Response doRequest(Request request) {
		try {
			return new OkHttpClient.Builder()
					.callTimeout(DEFAULT_TIMEOUT_MINUTES, TimeUnit.MINUTES)
					.connectTimeout(DEFAULT_TIMEOUT_MINUTES, TimeUnit.MINUTES)
					.readTimeout(DEFAULT_TIMEOUT_MINUTES, TimeUnit.MINUTES)
					.build().newCall(request).execute();
		} catch (IOException e) {
			throw new RuntimeException("okhttp请求失败", e);
		}
	}

	public static ResponseBody doRequestAsReponseBody(Request request) {
		return doRequest(request).body();
	}

	public static String doRequestAsString(Request request) {
		return getResponseResult(doRequestAsReponseBody(request));
	}

	public static String getResponseResult(ResponseBody responseBody) {
		InputStream is = null;
		BufferedReader reader = null;
		StringBuilder result = null;
		try {
			is = responseBody.byteStream();
			result = new StringBuilder();
			reader = new BufferedReader(new InputStreamReader(is));
			String s = null;
			while (((s = reader.readLine()) != null)) {
				result.append(s);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException ie) {
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException ie) {
				}
			}
		}
		return result.toString();
	}

	public static Request createGetRequest(String url, Map<String, String> headers) {
		return addHeaders(createBuilder(url), headers).get().build();
	}

	public static Request createPostRequest(String url, Map<String, String> headers, RequestBody body) {
		return addHeaders(createBuilder(url), headers).post(body).build();
	}

	public static Request createPutRequest(String url, Map<String, String> headers, RequestBody body) {
		return addHeaders(createBuilder(url), headers).put(body).build();
	}

	public static Request createDeleteRequest(String url, Map<String, String> headers, RequestBody body) {
		return addHeaders(createBuilder(url), headers).delete(body).build();
	}
	
	public static Request createDeleteRequest(String url, Map<String, String> headers) {
		return addHeaders(createBuilder(url), headers).delete().build();
	}

	public static Builder createBuilder(String url) {
		return new Request.Builder().url(url);
	}

	public static Builder addHeaders(final Builder builder, Map<String, String> headers) {
		if (headers == null || headers.isEmpty() || builder == null) {
			return builder;
		}
		headers.forEach((key, value) -> builder.addHeader(key, value));
		return builder;
	}

	public static String addGetParameters(String url, Map<String, String> parameters) {
		if (url == null || "".equals(url.trim()) || parameters == null || parameters.size() == 0) {
			return url;
		}
		Set<Map.Entry<String, String>> set = parameters.entrySet();
		Iterator<Map.Entry<String, String>> iterator = set.iterator();
		Map.Entry<String, String> entry = null;
		String name = null;
		while (iterator.hasNext()) {
			entry = iterator.next();
			if(entry.getValue() == null) {
				continue;
			}
			name = entry.getKey() + "=" + entry.getValue();
			if (!url.contains("?")) {
				url += "?";
			}
			url += url.endsWith("&") ? name : "&" + name;
		}
		return url;
	}

	public static String hmacSha256Encrypt(String content, String key) {
		try {
			Mac hmac256 = Mac.getInstance(DEFAULT_ALGORITHM_NAME);
			SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(DEFAULT_CHARSET), DEFAULT_ALGORITHM_NAME);
			hmac256.init(secretKey);
			return Hex.encodeHexString(hmac256.doFinal(content.getBytes(DEFAULT_CHARSET)));
		} catch (Exception e) {
			throw new RuntimeException("HmacSHA256算法加密失败", e);
		}
	}

	public static String concatUrl(String prefixUrl, String suffixUrl) {
		prefixUrl = prefixUrl == null ? "" : prefixUrl;
		suffixUrl = suffixUrl == null ? "" : suffixUrl;
		String url = (prefixUrl + "/" + suffixUrl);
		if (url.startsWith(HTTP_PREFIX)) {
			return HTTP_PREFIX + url.substring(HTTP_PREFIX.length()).replaceAll("//", "/");
		} else if (url.startsWith(HTTPS_PREFIX)) {
			return HTTPS_PREFIX + url.substring(HTTPS_PREFIX.length()).replaceAll("//", "/");
		}
		return url;
	}
}
