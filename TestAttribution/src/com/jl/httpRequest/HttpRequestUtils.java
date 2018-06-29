package com.jl.httpRequest;

import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.Map.Entry;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.*;
import org.apache.http.message.BasicNameValuePair;

public class HttpRequestUtils {
	  /**  
     * @Description:使用HttpClient发送post请求  
     */  
    public static String httpClientPost(String urlParam, Map<String, Object> params, String charset) {  
        StringBuffer resultBuffer = null;  
        HttpClient client = new DefaultHttpClient();  
        HttpPost httpPost = new HttpPost(urlParam);  
        // 构建请求参数  
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
            // 读取服务器响应数据  
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
     * @Description:使用HttpClient发送get请求 
     */  
    public static String httpClientGet(String urlParam, Map<String, Object> params, String charset) {  
        StringBuffer resultBuffer = null;  
        HttpClient client = new DefaultHttpClient();  
        BufferedReader br = null;  
        // 构建请求参数  
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
        try {  
            HttpResponse response = client.execute(httpGet);  
            // 读取服务器响应数据  
            br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));  
            String temp;  
            resultBuffer = new StringBuffer();  
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
}
