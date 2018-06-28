package com.jl.mof;

import java.util.Map;



import net.sf.json.JSONObject;

import org.apache.commons.collections.map.HashedMap;


public class testLogin {
	
	public static String login(String api,String userName,String password){
		Map<String,Object>	params=new HashedMap();
		params.put("password", password);
		params.put("username", userName);		
//		JSONObject jsonObject = JSONObject.fromObject(params);
//		System.out.println(jsonObject.toString());
		return HttpRequestUtils.httpClientPost2(api, params, "utf-8");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		System.out.print(login("https://app.wmcloud-stg.com/server/usermaster/authenticate/v1.json","lijiang@datayes.com","Datayes@123"));
		System.out.print(login("http://momrpt-ci.wmcloud-qa.com/finbook/v2/tempUser/login", "user115@datayes.com", "123456"));

	}
}
