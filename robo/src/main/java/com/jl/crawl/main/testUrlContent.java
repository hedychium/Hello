package com.jl.crawl.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


import org.apache.http.client.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.impl.client.*;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



import com.jl.crawl.page.Page;
import com.jl.crawl.page.PageParserTool;
import com.jl.robo.LoginWithHttpclient;
import com.jl.robo.checkReport;


public class testUrlContent {
	 
	 public static String  sendRequstAndGetResponse2(String url ) {
	        Page page = null;
	        String cookie="";
	        // 1.���� HttpClinet �������ò���
	        HttpClient httpClient = new HttpClient();
	        // ���� HTTP ���ӳ�ʱ 5s
	        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
	        
	        // 2.���� GetMethod �������ò���
	        GetMethod getMethod = new GetMethod(url);
//	        getMethod.setRequestHeader("cookie", cookie);
	        // ���� get ����ʱ 5s
	        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
	        // �����������Դ���
	        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
	        // 3.ִ�� HTTP GET ����
	        try {
	            int statusCode = httpClient.executeMethod(getMethod);
	        // �жϷ��ʵ�״̬��
	            if (statusCode != HttpStatus.SC_OK) {
	                System.err.println("Method failed: " + getMethod.getStatusLine());
	            }
	        // 4.���� HTTP ��Ӧ����
	            byte[] responseBody = getMethod.getResponseBody();// ��ȡΪ�ֽ� ����
	            String contentType = getMethod.getResponseHeader("Content-Type").getValue(); // �õ���ǰ��������
	            System.out.println(responseBody);
	            cookie=getMethod.getResponseHeader("Set-Cookie").getValue();
//	            page = new Page(responseBody,url,contentType); //��װ��Ϊҳ��
	        } catch (HttpException e) {
	        // �����������쳣��������Э�鲻�Ի��߷��ص�����������
	            System.out.println("Please check your provided http address!");
	            e.printStackTrace();
	        } catch (IOException e) {
	        // ���������쳣
	            e.printStackTrace();
	        } finally {
	        // �ͷ�����
	            getMethod.releaseConnection();
	        }
	        return cookie;
	    }
	 
	 public static Page  sendRequstAndGetResponse(String url,String cookie ) {
	        Page page = null;
	        // 1.���� HttpClinet �������ò���
	        HttpClient httpClient = new HttpClient();
	        // ���� HTTP ���ӳ�ʱ 5s
	        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
	        
	        // 2.���� GetMethod �������ò���
	        GetMethod getMethod = new GetMethod(url);
//	        getMethod.setRequestHeader("cookie", cookie);
	        // ���� get ����ʱ 5s
	        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
	        // �����������Դ���
	        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
	        // 3.ִ�� HTTP GET ����
	        try {
	            int statusCode = httpClient.executeMethod(getMethod);
	        // �жϷ��ʵ�״̬��
	            if (statusCode != HttpStatus.SC_OK) {
	                System.err.println("Method failed: " + getMethod.getStatusLine());
	            }
	        // 4.���� HTTP ��Ӧ����
	            byte[] responseBody = getMethod.getResponseBody();// ��ȡΪ�ֽ� ����
	            String contentType = getMethod.getResponseHeader("Content-Type").getValue(); // �õ���ǰ��������
	            System.out.println(responseBody);
	            page = new Page(responseBody,url,contentType); //��װ��Ϊҳ��
	        } catch (HttpException e) {
	        // �����������쳣��������Э�鲻�Ի��߷��ص�����������
	            System.out.println("Please check your provided http address!");
	            e.printStackTrace();
	        } catch (IOException e) {
	        // ���������쳣
	            e.printStackTrace();
	        } finally {
	        // �ͷ�����
	            getMethod.releaseConnection();
	        }
	        return page;
	    }
	 

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String cookie = null;
		HttpClient client = new HttpClient();
		HttpGet httpGet = new HttpGet("http://robor.wmcloud-qa.com/v2/");
//		HttpResponse response = client.executeMethod(httpGet);
//		cookie = response.getFirstHeader("Set-Cookie").getValue();
		
		Page page=testUrlContent.sendRequstAndGetResponse("https://robo.datayes.com/v2/fastreport/search?input=%E9%94%90%E7%A7%91%E6%BF%80%E5%85%89#reportSearch",cookie);
		System.out.println(page.getHtml());
		System.out.println(page.getUrl());
		Elements es = PageParserTool.select(page,"meta[name=\"viewport\"]");
		if(!es.isEmpty()){
            System.out.println("���潫��ӡ����meta��ǩ�� ");
            System.out.println(es);
        }
		
		Elements titles = PageParserTool.select(page,"title");
		if(!es.isEmpty()){
            System.out.println("���潫��ӡ����title��ǩ�� ");
            System.out.println(titles);
        }
				
		ArrayList<String> list=PageParserTool.getAttrs(page, "meta", "name");
		System.out.println(list);
		
	}
}
