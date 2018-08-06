package com.jl.UTILS;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Map.Entry;

import javax.imageio.stream.FileImageInputStream;

import net.sf.json.JSONObject;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.*;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpRequestUtils {
	/**
	 * @Description:ʹ��HttpClient����post����"Content-Type", "application/json;charset=utf-8"
	 *                                                  �������Ҫ��json��ʽ
	 */
	public static String httpClientPost2(String urlParam, Map<String, Object> params, String charset) {
		StringBuffer resultBuffer = null;
		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(urlParam);
		httpPost.addHeader("Content-Type", "application/json;charset=utf-8");

		// ��������Ĳ���
		JSONObject jsonParam = JSONObject.fromObject(params);

		BufferedReader br = null;

		try {
			StringEntity entity = new StringEntity(jsonParam.toString(), charset);
			entity.setContentEncoding("UTF-8");
			entity.setContentType("application/json");
			httpPost.setEntity(entity);
			HttpResponse response = client.execute(httpPost);
			// ��ȡ��������Ӧ����
			resultBuffer = new StringBuffer();
			br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String temp;
			while ((temp = br.readLine()) != null) {
				resultBuffer.append(temp);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					br = null;
					throw new RuntimeException(e);
				}
			}
		}
		return resultBuffer.toString();
	}

	/**
	 * @Description:ʹ��HttpClient����post����
	 */
	public static String httpClientPost(String urlParam, String cookie,Map<String, Object> params, String charset) {
		StringBuffer resultBuffer = null;
		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(urlParam);
		httpPost.addHeader("Cookie", cookie);
		// �����������
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		Iterator<Entry<String, Object>> iterator = params.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Object> elem = iterator.next();
			list.add(new BasicNameValuePair(elem.getKey(), String.valueOf(elem.getValue())));
		}
		BufferedReader br = null;
		try {
			if (list.size() > 0) {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
				httpPost.setEntity(entity);
			}
			HttpResponse response = client.execute(httpPost);
			// ��ȡ��������Ӧ����
			resultBuffer = new StringBuffer();
			br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String temp;
			while ((temp = br.readLine()) != null) {
				resultBuffer.append(temp);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					br = null;
					throw new RuntimeException(e);
				}
			}
		}
		return resultBuffer.toString();
	}

	/**
	 * @Description:ʹ��HttpClient����get����
	 */
	public static String httpClientGet(String urlParam,String cookie, Map<String, Object> params, String charset) {
		String res  = null;
		HttpClient client = new DefaultHttpClient();
		BufferedReader br = null;
		// �����������
		StringBuffer sbParams = new StringBuffer();
		if (params != null && params.size() > 0) {
			for (Entry<String, Object> entry : params.entrySet()) {
				sbParams.append(entry.getKey());
				sbParams.append("=");
				try {
					sbParams.append(URLEncoder.encode(String.valueOf(entry.getValue()), charset));
				} catch (UnsupportedEncodingException e) {
					throw new RuntimeException(e);
				}
				sbParams.append("&");
			}
		}
		if (sbParams != null && sbParams.length() > 0) {
			urlParam = urlParam + "?" + sbParams.substring(0, sbParams.length() - 1);
		}
		HttpGet httpGet = new HttpGet(urlParam);
		httpGet.setHeader("Cookie", cookie);
		try {
			HttpResponse response = client.execute(httpGet);
			// ��ȡ��������Ӧ����
			res = EntityUtils.toString(response.getEntity(), charset);
			
			System.out.println("���ص�״̬��:" + response.getStatusLine().getStatusCode());
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					br = null;
					throw new RuntimeException(e);
				}
			}
		}
		
		return res;
	}

	
	public static String sendWithFile2(CookieStore cookieStore, String cookie, String postUrl, String filePath,Map<String,ContentBody> reqParam) {
		String respStr = "";
		// ����HttpClientBuilder
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// HttpClient
		CloseableHttpClient closeableHttpClient = null;
		if (cookieStore != null) {
			closeableHttpClient = httpClientBuilder.setDefaultCookieStore(cookieStore).build();
		} else {
			closeableHttpClient = httpClientBuilder.build();
		}
		HttpPost httpPost = new HttpPost(postUrl);
		File file=new File(filePath);
		FileBody fileBody = new FileBody(file);
		MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
		
		multipartEntityBuilder.addPart("uploadFile",fileBody);
//		multipartEntityBuilder.addPart("file", fileBody);

		httpPost.setHeader("Cookie", cookie);
		HttpEntity reqEntity  = multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE).build();
		httpPost.setEntity(reqEntity);
		try {
			CloseableHttpResponse response = closeableHttpClient.execute(httpPost);

			System.out.println("�ϴ�֮�󷵻ص�״̬��:" + response.getStatusLine().getStatusCode());
			System.out.println("response:" + response.toString());
			try {
				HttpEntity resEntity = response.getEntity();

				if (resEntity == null) {
				}
				InputStream is = resEntity.getContent();
				StringBuffer strBuf = new StringBuffer();
				byte[] buffer = new byte[4096];
				int r = 0;
				while ((r = is.read(buffer)) > 0) {
					strBuf.append(new String(buffer, 0, r, "UTF-8"));
				}
				System.out.println("resp=" + strBuf);

				EntityUtils.consume(reqEntity);

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

				response.close();
				closeableHttpClient.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return respStr;
	}
}
