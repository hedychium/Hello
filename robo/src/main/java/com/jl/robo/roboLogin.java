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
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.omg.CORBA.PRIVATE_MEMBER;

public class roboLogin {
	public final static String PATH_QA="https://app.datayes-stg.com/server/usermaster/authenticate/v1.json";
	
	public static String QA_Login(){
		String retStr = "";
		// ´´½¨HttpClientBuilder
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(roboLogin.PATH_QA);
		
		HttpResponse response = null;

		Map<String,Object> p=new HashMap();
		p.put("username", "lijiang@datayes.com");
		p.put("password", "Datayes@123");	
		p.put("rememberMe", "false");	
		p.put("app", "cloud");		
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
                client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }		 		 
		return response.getFirstHeader("Set-Cookie").getValue();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
	}

}
