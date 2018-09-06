package com.jl.robo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jl.UTILS.HttpRequestUtils;

public class checkDate {
	
	public static List<String> uncrectDate(String re) {
		JsonParser parser =new JsonParser();
		JsonObject jOb=(JsonObject) parser.parse(re);
		JsonObject data=jOb.get("data").getAsJsonObject();
		JsonObject data_data=data.get("data").getAsJsonObject();
		JsonObject pageInfo=data_data.get("pageInfo").getAsJsonObject();
		JsonArray list=pageInfo.getAsJsonArray("list");
		List<String> drugList=new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			JsonObject info=list.get(i).getAsJsonObject();
			JsonElement e1=info.get("productionDate");
			String productionDate=info.get("productionDate").getAsString();	
			JsonElement e2=info.get("entryDate");
			String entryDate=info.get("entryDate").getAsString();
			String drugId=info.get("drugId").getAsString();

			if(e1!=null&&e2!=null&&!productionDate.equals("")&&productionDate!=null&&!entryDate.equals("")&&entryDate!=null){
				Date sdf_productionDate = null;
				Date sdf_entryDate = null ;
				try {
					sdf_productionDate = new SimpleDateFormat("yyyy-MM-dd").parse(productionDate);
					sdf_entryDate = new SimpleDateFormat("yyyy-MM-dd").parse(entryDate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(sdf_productionDate.getTime()>sdf_entryDate.getTime()){
					drugList.add(drugId);
					System.out.println(drugId);
				}
			}
		}
		return drugList;
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String httpType = "https://";
		String domain = "app.datayes-stg.com";
		String loginUrl = httpType + "/server/usermaster/authenticate/v1.json";

		Map<String,Object> p=new HashMap();
		p.put("username", "lijiang@datayes.com");
		p.put("password", "Datayes@123");	
		p.put("rememberMe", "false");	
		p.put("app", "cloud");		

		HttpResponse reponse = checkReport.login(
				"https://app.datayes-stg.com/server/usermaster/authenticate/v1.json",
				p, "UTF-8");
		String cookie=reponse.getFirstHeader("Set-Cookie").getValue();
		Map<String,Object> searchParams2=new HashMap();
		searchParams2.put("ticker", "002680");
		searchParams2.put("classifications", "");
		searchParams2.put("diseases", "");	
		searchParams2.put("pageNow", "1");	
		searchParams2.put("pageSize", "630");	
		searchParams2.put("tab", "dev");	
		
		String re2=HttpRequestUtils.httpClientGet("https://gw.datayes-stg.com/rrp_adventure/medicine/industry/dev", cookie, searchParams2, "utf-8");
		System.out.println(re2);
		List<String> drugList=uncrectDate(re2);
		System.out.println(drugList.size());
	}

}
