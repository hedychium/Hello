package com.jl.mof;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import net.sf.json.JSONObject;

import org.apache.commons.collections.map.HashedMap;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class LoginWithHttpclient {
	static CookieStore cookieStore = null;
	static String domain = "";
	static String path = "";
	static String httpType = "";

	/**
	 * 组装登录参数
	 * 
	 * @return
	 */
	public static Map<String, Object> getLoginNameValuePairList() {
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("username", "user115@datayes.com"));
		params.add(new BasicNameValuePair("password", "123456"));
		return (Map<String, Object>) params;
	}

	/**
	 * 组装操作参数
	 * 
	 * @return
	 */
	public static List<NameValuePair> getQueryNameValuePairList() {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("regionNo", "xxx"));
		params.add(new BasicNameValuePair("pageNo", "xxx"));
		params.add(new BasicNameValuePair("pageSize", "xxx"));
		return params;
	}

	/**
	 * 将cookie保存到静态变量中供后续调用
	 * 
	 * @param httpResponse
	 */	
	   public static void setCookieStore(HttpResponse httpResponse) {
	        System.out.println("----setCookieStore");
	        cookieStore = new BasicCookieStore();
	        // JSESSIONID
	        String setCookie = httpResponse.getFirstHeader("Set-Cookie")
	                .getValue();
	        String JSESSIONID = setCookie.substring("JSESSIONID=".length(),
	                setCookie.indexOf(";"));
	        System.out.println("JSESSIONID:" + JSESSIONID);
	        // 新建一个Cookie
	        BasicClientCookie cookie = new BasicClientCookie("JSESSIONID",
	                JSESSIONID);
	        cookie.setVersion(0);
	        cookie.setDomain(domain);
	        cookie.setPath(path);
	        // cookie.setAttribute(ClientCookie.VERSION_ATTR, "0");
	        // cookie.setAttribute(ClientCookie.DOMAIN_ATTR, "127.0.0.1");
	        // cookie.setAttribute(ClientCookie.PORT_ATTR, "8080");
	        // cookie.setAttribute(ClientCookie.PATH_ATTR, "/CwlProWeb");
	        cookieStore.addCookie(cookie);
	    }
	/**
	 * post请求"Content-Type", "application/json
	 * 
	 * @param postUrl
	 * @param map
	 * @return
	 */
	public static HttpResponse doPost2(String postUrl, Map<String, Object> map, String charset) {
		String retStr = "";
		// 创建HttpClientBuilder
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// HttpClient
		HttpClient httpClient = null;
		if (cookieStore != null) {
			httpClient = httpClientBuilder.setDefaultCookieStore(cookieStore).build();
		} else {
			httpClient = httpClientBuilder.build();
		}
		HttpPost httpPost = new HttpPost(postUrl);
//		httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
		// 设置请求和传输超时时间
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();
		httpPost.setConfig(requestConfig);
		HttpResponse response = null;
		try {

			JSONObject jsonParam = JSONObject.fromObject(map);
			StringEntity entity = new StringEntity(jsonParam.toString(), charset);
			entity.setContentEncoding("UTF-8");
			entity.setContentType("application/json");
			httpPost.setEntity(entity);
			response = httpClient.execute(httpPost);
			System.out.println(response.toString());
			setCookieStore(response);

			HttpEntity httpEntity = response.getEntity();
			retStr = EntityUtils.toString(httpEntity, "UTF-8");
			System.out.println(retStr);		

		} catch (Exception e) {
		}

		return response;
	}

	public static String doPost(String postUrl, Map<String, Object> params, String charset) {
		String retStr = "";
		// 创建HttpClientBuilder
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// HttpClient
		CloseableHttpClient closeableHttpClient = null;
		if (cookieStore != null) {
			closeableHttpClient = httpClientBuilder.setDefaultCookieStore(cookieStore).build();
		} else {
			closeableHttpClient = httpClientBuilder.build();
		}
		HttpPost httpPost = new HttpPost(postUrl);
		httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
		// 设置请求和传输超时时间
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();
		httpPost.setConfig(requestConfig);

		List<NameValuePair> list = new ArrayList<NameValuePair>();
		Iterator<Entry<String, Object>> iterator = params.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Object> elem = iterator.next();
			list.add(new BasicNameValuePair(elem.getKey(), String.valueOf(elem.getValue())));
		}

		try {
			if (list.size() > 0) {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
				httpPost.setEntity(entity);
			}

			CloseableHttpResponse response = closeableHttpClient.execute(httpPost);
			System.out.println(response.getStatusLine());
			setCookieStore(response);

			HttpEntity httpEntity = response.getEntity();
			retStr = EntityUtils.toString(httpEntity, "UTF-8");
			System.out.println(retStr);
			// 释放资源
			closeableHttpClient.close();
		} catch (Exception e) {
		}
		return retStr;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		httpType = "https://";
		domain = "momrpt-qy-uat.wmcloud.com";
		path = "/finbook/v2";
		String loginUrl = httpType + domain + path + "/tempUser/login";
		String queryReginUrl = "http://domani/xxx/system/getRegionList.do";
		String filePath = "./File/外贸博道1号_管理人策略周报_17_2018-06-29.xlsx";
		File file=new File(filePath);
		String uploadUrl = httpType + domain + path + "/api/report/4056/upload";
		Map<String, Object> params = new HashedMap();
		params.put("password", "123456");
		params.put("username", "bdtz@report.com.cn");
//		params.put("username", "admin@datayes.com");

		// 第一次登录会保存cookie
		HttpResponse response = doPost2(loginUrl, params,"utf-8");
		String cookie = response.getFirstHeader("Set-Cookie").getValue();
		System.out.println("cookie:   "+cookie);
		
		 Map<String,ContentBody> reqParam = new HashMap<String,ContentBody>();
		 reqParam.put("uploadFile", new StringBody(filePath, ContentType.MULTIPART_FORM_DATA));
		   
		String re = HttpRequestUtils.sendWithFile2(null, cookie, uploadUrl, filePath,reqParam);
		System.out.println(re);

	}

}
