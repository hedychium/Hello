package com.jl.robo;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jl.UTILS.HttpRequestUtils;

public class getRommer {

	public static Map<String, String> getDataFromStock(String re) {
		Map<String, String> map = new HashMap<String, String>();
		JsonParser parser = new JsonParser();
		JsonObject jOb = (JsonObject) parser.parse(re);
		JsonObject data = jOb.get("data").getAsJsonObject();
		JsonArray news_list = data.get("list").getAsJsonArray();
		for (int i = 0; i < news_list.size(); i++) {
			Boolean isRumor = news_list.get(i).getAsJsonObject().get("rumor")
					.getAsBoolean();
			JsonArray securities= news_list.get(i).getAsJsonObject().getAsJsonArray("securities");
			if (isRumor&&securities.size()>1) {
				String id = news_list.get(i).getAsJsonObject().get("id")
						.getAsString();
				String title = news_list.get(i).getAsJsonObject().get("title")
						.getAsString();
				map.put(id, title);
				System.out.println(id+":"+title);
			}
		}
		return map;

	}
	
	public static Map<String, String> getDataFromBond(String re) {
		Map<String, String> map = new HashMap<String, String>();
		JsonParser parser = new JsonParser();
		JsonObject jOb = (JsonObject) parser.parse(re);
		JsonObject data = jOb.get("data").getAsJsonObject();
		JsonArray news_list = data.get("list").getAsJsonArray();
		for (int i = 0; i < news_list.size(); i++) {
			String type = news_list.get(i).getAsJsonObject().get("type").getAsString();;
//			System.out.println(type);
			if (!type.equals("news")) {
				String id = news_list.get(i).getAsJsonObject().get("id")
						.getAsString();
				String title = news_list.get(i).getAsJsonObject().get("title")
						.getAsString();
				map.put(id, title);
				System.out.println(id+":"+title);
			}
		}
		return map;

	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String httpType = "https://";
		String domain = "app.datayes-stg.com";
		String loginUrl = httpType + "/server/usermaster/authenticate/v1.json";

		Map<String, Object> p = new HashMap();
		p.put("username", "pp_719demo2@wmcloud.com");
		p.put("password", "datayes@123");
		p.put("rememberMe", "false");
		p.put("app", "cloud");

		HttpResponse reponse = checkReport
				.login("https://app.datayes-stg.com/server/usermaster/authenticate/v1.json",
						p, "UTF-8");
		String cookie = reponse.getFirstHeader("Set-Cookie").getValue();

		Map<String, Object> searchParams = new HashMap();
		searchParams.put("beginDate", "20180101");
		searchParams.put("endDate", "20180825");
		searchParams.put("onlyStock", "false");
		searchParams.put("pageNow", "1");
		searchParams.put("pageSize", "500");
		searchParams.put("queryInput", "³ÎÇå");
		searchParams.put("queryType", "1");

//		String re = HttpRequestUtils
//				.httpClientGet(
//						"https://gw.datayes-stg.com/rrp_adventure/web/stockwarningnews",
//						cookie, searchParams, "utf-8");
//		System.out.println(re);
//
//		Map<String, String> map = getDataFromStock(re);
//		System.out.println(map.size());
		
		
		Map<String, Object> searchParams2 = new HashMap();
		searchParams2.put("beginDate", "20180827");
		searchParams2.put("endDate", "20180903");
		searchParams2.put("pageNow", "1");
		searchParams2.put("pageSize", "2730");
//		searchParams2.put("queryInput", "¹«¸æ");
//		searchParams2.put("queryType", "2");

		String re2 = HttpRequestUtils
				.httpClientGet(
						"https://gw.datayes-stg.com/rrp_adventure/web/stockwarningnews",
						cookie, searchParams2, "utf-8");
		System.out.println(re2);

		Map<String, String> map2 = getDataFromStock(re2);
		System.out.println(map2.size());

	}

}
