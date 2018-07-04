package com.jerry;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.impl.cookie.BestMatchSpecFactory;
import org.apache.http.impl.cookie.BrowserCompatSpecFactory;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.junit.Test;


/**
 * @Author: Peijie
 * @Description:
 * @Date: Created in 2018/6/5
 */
public class TestHttpClient {
    static CookieStore cookieStore = null;
    static HttpClientContext context = null;
    static String cookie = "";
    String loginUrl = "http://localhost:81/user/login";
    String testUrl = "http://localhost:81/itemCat/findByParentId.do?parentId=1";
    String loginErrorUrl = "http://localhost:81/user/login";

    public CloseableHttpClient testLogin() throws Exception {
        System.out.println("----testLogin");

        // // 创建HttpClientBuilder
        // HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // // HttpClient
        // CloseableHttpClient client = httpClientBuilder.build();
        // 直接创建client
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        HttpPost httpPost = new HttpPost(loginUrl);
//        Map parameterMap = new HashMap();
//        parameterMap.put("username", "jerry");
//        parameterMap.put("password", "peijie");
        StringBody userName = new StringBody("jerry", ContentType.create(
                "text/plain", Consts.UTF_8));
        StringBody password = new StringBody("peijie", ContentType.create(
                "text/plain", Consts.UTF_8));
//        UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(
//                getParam(parameterMap), "UTF-8");
        FileBody bin = new FileBody(new File("D:/gitlab/haha.txt"));
        HttpEntity postEntity = MultipartEntityBuilder.create()
                .addPart("file", bin)
                // 相当于<input type="text" name="userName" value=userName>
                .addPart("username", userName)
                .addPart("pwd", password)
                .build();
        httpPost.setEntity(postEntity);
        System.out.println("request line:" + httpPost.getRequestLine());
        // 发起请求 并返回请求的响应
        response = client.execute(httpPost);
        System.out.println("The response value of token:" + response.getFirstHeader("token"));
        // 获取响应对象
        HttpEntity resEntity = response.getEntity();
        if (resEntity != null) {
            // 打印响应长度
            System.out.println("Response content length: " + resEntity.getContentLength());
            // 打印响应内容
            System.out.println(EntityUtils.toString(resEntity, Charset.forName("UTF-8")));
        }

//        try {
//            // 执行post请求
//            HttpResponse httpResponse = client.execute(httpPost);
//            String location = httpResponse.getFirstHeader("Location")
//                    .getValue();
//            if (location != null && location.startsWith(loginErrorUrl)) {
//                System.out.println("----loginError");
//            }
//            printResponse(httpResponse);
//
//            // 执行get请求
//            System.out.println("----the same client");
//            HttpGet httpGet = new HttpGet(testUrl);
//            System.out.println("request line:" + httpGet.getRequestLine());
//            HttpResponse httpResponse1 = client.execute(httpGet);
//            printResponse(httpResponse1);
//
//
//            // cookie store
//           setCookieStore(httpResponse);
//            // context
//            setContext();
//            return client;
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
        return client;
    }


    public static void printResponse(HttpResponse httpResponse)
            throws ParseException, IOException {
        // 获取响应消息实体
        HttpEntity entity = httpResponse.getEntity();
        // 响应状态
        System.out.println("status:" + httpResponse.getStatusLine());
        System.out.println("headers:");
        HeaderIterator iterator = httpResponse.headerIterator();
        while (iterator.hasNext()) {
            System.out.println("\t" + iterator.next());
        }
        // 判断响应实体是否为空
        if (entity != null) {
            String responseString = EntityUtils.toString(entity);
            System.out.println("response length:" + responseString.length());
            System.out.println("response content:"
                    + responseString.replace("\r\n", ""));
        }
    }

    public static void setContext() {
        System.out.println("----setContext");
        context = HttpClientContext.create();
        Registry<CookieSpecProvider> registry = RegistryBuilder
                .<CookieSpecProvider>create()
                .register(CookieSpecs.BEST_MATCH, new BestMatchSpecFactory())
                .register(CookieSpecs.BROWSER_COMPATIBILITY,
                        new BrowserCompatSpecFactory()).build();
        context.setCookieSpecRegistry(registry);
        context.setCookieStore(cookieStore);
    }

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
        cookie.setDomain("127.0.0.1");
        cookie.setPath("/CwlProClient");
        // cookie.setAttribute(ClientCookie.VERSION_ATTR, "0");
        // cookie.setAttribute(ClientCookie.DOMAIN_ATTR, "127.0.0.1");
        // cookie.setAttribute(ClientCookie.PORT_ATTR, "8080");
        // cookie.setAttribute(ClientCookie.PATH_ATTR, "/CwlProWeb");
        cookieStore.addCookie(cookie);
    }

    public static List<NameValuePair> getParam(Map parameterMap) {
        List<NameValuePair> param = new ArrayList<NameValuePair>();
        Iterator it = parameterMap.entrySet().iterator();
        while (it.hasNext()) {
            Entry parmEntry = (Entry) it.next();
            param.add(new BasicNameValuePair((String) parmEntry.getKey(),
                    (String) parmEntry.getValue()));
        }
        return param;
    }


    public CloseableHttpClient testLogin1() throws Exception {
        System.out.println("----testLogin");

        // // 创建HttpClientBuilder
        // HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // // HttpClient
        // CloseableHttpClient client = httpClientBuilder.build();
        // 直接创建client
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        HttpPost httpPost = new HttpPost(loginUrl);
        //设置参数x-www-form-urlencode
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("username", "jerry"));
        nvps.add(new BasicNameValuePair("pwd", "peijie"));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        response = client.execute(httpPost);
        //如果模拟登录成功
        if(response.getStatusLine().getStatusCode() == 200) {
            Header[] headers = response.getAllHeaders();
            for (Header header : headers) {
                System.out.println(header.getName() + ": " + header.getValue());
            }
        }
        cookie = response.getFirstHeader("Set-Cookie")
                .getValue();
        System.out.println(cookie);
        setCookieStore(response);
        return client;

    }

}
