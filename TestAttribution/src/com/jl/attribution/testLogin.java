package com.jl.attribution;

import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

import com.jl.httpRequest.HttpRequestUtils;

public class testLogin {
	
	public static String login(String api,String userName,String password){
		Map<String,Object>	params=new HashedMap();
		params.put("username", userName);
		params.put("password", password);
		return HttpRequestUtils.httpClientPost(api, params, "UTF-8");
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.print(login("https://app.wmcloud-stg.com/server/usermaster/authenticate/v1.json","lijiang@datayes.com","Datayes@123"));
	}
}
