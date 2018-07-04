package com.jl.hangye;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

public class compareJson {
	public static boolean compare(String str1, String str2) {
		JsonParser parser = new JsonParser();
		JsonObject obj = (JsonObject) parser.parse(str1);
		JsonParser parser1 = new JsonParser();
		JsonObject obj1 = (JsonObject) parser1.parse(str2);
		if (obj.equals(obj1)) {
			return true;
		}
		return false;
	}

	public static List<quanxianObj> outJsonStr(String filePath) {
		String str1 = TestJson.readFileByLines(filePath);
		JSONArray array = JSONArray.fromObject(str1);
		
		List<quanxianObj> objs = new ArrayList<quanxianObj>();
		
		for (int i = 0; i < array.size(); i++) {
			quanxianObj obj = new quanxianObj();
			obj.setUser(array.getJSONObject(i).getString("user"));
			obj.setObjectName(array.getJSONObject(i).getString("objectName"));
			obj.setObjectClass(array.getJSONObject(i).getString("objectClass"));
			obj.setIsGranted(array.getJSONObject(i).getString("isGranted"));
			obj.setObjectLabel(array.getJSONObject(i).getString("objectLabel"));
			obj.setIsExpired(array.getJSONObject(i).getString("isExpired"));
			objs.add(obj);
		}
		return objs;
	}
	public static boolean bijiao(List<quanxianObj> list0,List<quanxianObj> list1 ){
		boolean flag=false;
		if(list0.size()!=list1.size()){
//			flag=false;
			
//			return false;
		}
		List<quanxianObj> exists=new ArrayList<quanxianObj>();
		List<quanxianObj> notexists=new ArrayList<quanxianObj>();
		int count=0;
		int j=0;
		for(;j<list0.size();j++){
			int k=0;
			boolean find=false;
			for(;k<list1.size();k++){

				if(list0.get(j).equals(list1.get(k))){
					find=true;
					count++;
					exists.add(list0.get(j));
					continue;
				}
				
			}
			if(k==list1.size()&&find==false){
				notexists.add(list0.get(j));
			}
		}
		if(count==list0.size()){
			flag=true;
		}
		JSONArray arraynot = JSONArray.fromObject(notexists);
		JSONArray array = JSONArray.fromObject(exists);
		System.out.println("list0中存在于list1的"+array.size()+"-----"+array);
		System.out.println("list0中不存在于list1的"+arraynot.size()+"-----"+arraynot);	
		return flag;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filePath1="./data/mof/p-str2.txt";
		List<quanxianObj> list0=outJsonStr(filePath1);
//		System.out.println(str1.toString());
		String filePath2="./data/云平台/p-str2.txt";
		List<quanxianObj> list1=outJsonStr(filePath2);		
	 
		boolean flag1=bijiao(list0,list1);
		boolean flag2=bijiao(list1,list0);
		System.out.println(flag1&&flag2);
	}
}
