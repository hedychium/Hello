package com.jl.robo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;


public class checkReport {

	public static HttpResponse login(String postUrl, Map<String,Object> p,
			String charset) {
		String retStr = "";
		// 创建HttpClientBuilder
		 CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(postUrl);

		httpPost.setHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=utf-8");		
		
		HttpResponse response = null;
		
		try {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			Iterator<Entry<String, Object>> iterator = p.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, Object> elem = iterator.next();
				nvps.add(new BasicNameValuePair(elem.getKey(), String.valueOf(elem.getValue())));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
            httpPost.setHeader("Content-type", "application/x-www-form-urlencoded"); 
            response = client.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode==200){
                HttpEntity entity = response.getEntity();
                retStr = EntityUtils.toString(entity);
                System.out.println(retStr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }		 		 
		return response;
	}

	public static String getJsonString(String urlParam, String cookie,
			Map<String, Object> params, String charset) {
		return HttpRequestUtils
				.httpClientGet(urlParam, cookie, params, charset);
	}

	/*
	 * public static boolean checkReturnJson(Object a, Object b){
	 * 
	 * 
	 * }
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String httpType = "https://";
		String domain = "app.datayes-stg.com";
		String loginUrl = httpType + "/server/usermaster/authenticate/v1.json";

		Map<String,Object> p=new HashMap();
		p.put("username", "test096@datayes.com");
		p.put("password", "datayes@123");	
		p.put("rememberMe", "false");	
		p.put("app", "cloud");		
		
		HttpResponse reponse = login(
				"https://app.datayes-stg.com/server/usermaster/authenticate/v1.json",
				p, "UTF-8");
		String cookie=reponse.getFirstHeader("Set-Cookie").getValue();
		 
		Map<String,Object> searchParams=new HashMap();
		searchParams.put("input", "疫苗");
		searchParams.put("sortOrder", "desc");	
		searchParams.put("pageNow", "1");	
		searchParams.put("pageSize", "20");	
		searchParams.put("pubTimeStart", "20080723000000");	
		searchParams.put("pubTimeEnd", "20180723235959");	
		searchParams.put("industry", "医药生物");		
		
		String re=HttpRequestUtils.httpClientGet("https://gw.datayes-stg.com/adventure_rrp_stg/researchQuickReport/search", cookie, searchParams, "utf-8");
		System.out.println(re);
	}

}
