package com.jerry;





import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * @Author: Peijie
 * @Description:
 * @Date: Created in 2018/6/5
 */
public class HttpClientTest {

    public static void main(String[] args) {
        String respStr = null;

        // 1. 登录pinyougou,同时存入cookie
//  HttpClient httpClient = getCookie("http://localhost:9102/login");
        TestHttpClient testHttpClient = new TestHttpClient();
        CloseableHttpClient httpClient = null;
        try {
            httpClient = testHttpClient.testLogin1();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2. 使用HttpClient完成文件上传
        String uploadUrl = "http://localhost:81/upload";
        HttpPost httpPost = new HttpPost(uploadUrl);
        FileBody fileBody = new FileBody(new File("./file/haha.txt"));
        httpPost.setHeader("Cookie",TestHttpClient.cookie);
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        multipartEntityBuilder.addPart("file",fileBody);


        HttpEntity reqEntity  = multipartEntityBuilder.build();
        httpPost.setEntity(reqEntity);

        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            System.out.println("上传之后返回的状态码:"+response.getStatusLine().getStatusCode());
            try {
                HttpEntity resEntity = response.getEntity();
                respStr = getRespString(resEntity);
                EntityUtils.consume(reqEntity);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                response.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("resp=" + respStr);


    }


    /**
     * 将返回结果转化为String
     *
     * @param entity
     * @return
     * @throws Exception
     */
    private static String getRespString(HttpEntity entity) throws Exception {
        if (entity == null) {
            return null;
        }
        InputStream is = entity.getContent();
        StringBuffer strBuf = new StringBuffer();
        byte[] buffer = new byte[4096];
        int r = 0;
        while ((r = is.read(buffer)) > 0) {
            strBuf.append(new String(buffer, 0, r, "UTF-8"));
        }
        return strBuf.toString();
    }

    /**
     * 登陆后检测
     *
     * @param httpClient
     * @param afterLoginUrl
     */
    private static void afterLogin(HttpClient httpClient, String afterLoginUrl) {
        // 进行登录后的操作
        GetMethod getMethod = new GetMethod(afterLoginUrl);
        try {
            httpClient.executeMethod(getMethod);
            // 打印出返回数据，检验一下是否成功
            String responseText = getMethod.getResponseBodyAsString();
            System.out.println(responseText);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 使用HttpClient进行登录同时返回带有cookie信息的HttpClient对象
     * @param loginUrl
     * @return
     */
    private static HttpClient getCookie(String loginUrl) {

//        String loginUrl = "http://localhost:9102/login";

        HttpClient httpClient = new HttpClient();
        // 使用post方式进行登录
        PostMethod postMethod = new PostMethod(loginUrl);
        // 设置登录信息
        NameValuePair[] data = {
                new NameValuePair("username", "jerry"), new NameValuePair("password", "peijie"),
                new NameValuePair("m1", "2")
        };

        postMethod.setRequestBody(data);

        // 设置 HttpClient 接收 Cookie,用与浏览器一样的策略
        httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
        try {
            httpClient.executeMethod(postMethod);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 获得登陆后的 Cookie
        Cookie[] cookies = httpClient.getState().getCookies();
        StringBuffer tmpcookies = new StringBuffer();
        for (Cookie c : cookies) {
            tmpcookies.append(c.toString() + ";");
        }

        return httpClient;
    }

}



