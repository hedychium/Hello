package com.jl.robo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jl.UTILS.ExcelData;
import com.jl.UTILS.HttpRequestUtils;
import com.mysql.fabric.xmlrpc.base.Array;
/**
 * 指数问答api测试
 * @author li.jiang
 *
 */
public class indexApiTest {
	//获取指数list
	public static  List<String> getIndexNameList(String url){
		List<String> indexNameList=new ArrayList<String>();
		String re=HttpRequestUtils.httpClientGet(url, "", null, "utf-8");
		JsonParser parser =new JsonParser();
		JsonObject jOb=(JsonObject) parser.parse(re);
		if(Integer.parseInt(jOb.get("retCode").getAsString())==-1||jOb.get("data").getAsJsonArray()==null){
			return null;
		}else{
			JsonArray list=jOb.get("data").getAsJsonArray();
//			System.out.println(list.size());
			for(int i=0;i<list.size();i++){
				JsonObject indexObject=list.get(i).getAsJsonObject();
				System.out.println(indexObject.toString());
				if(indexObject.has("indexTypeCD")){
					if(indexObject.get("indexTypeCD").equals("01")||indexObject.get("indexTypeCD").equals("1600")||indexObject.get("indexTypeCD").equals("1700")||indexObject.get("indexTypeCD").equals("2400"))
						indexNameList.add(indexObject.get("secShortName").getAsString());
				}
				
			}
			return indexNameList;
		}				
	}
	
	//从接口获取申万二级行业名称
	public static  List<String> getIndustryNameList(String url,String cookie){
		List<String> industryNameList=new ArrayList<String>();
		String re=HttpRequestUtils.httpClientGet(url, cookie, null, "UTF-8");
		JsonParser parser =new JsonParser();
		JsonObject jOb=(JsonObject) parser.parse(re);
		if(Integer.parseInt(jOb.get("code").getAsString())!=1){
			return null;
		}else{
			JsonObject dataObj=jOb.get("data").getAsJsonObject();
			JsonArray industryTree=dataObj.get("industryTree").getAsJsonArray();
			for(int i=0;i<industryTree.size();i++){
				JsonObject Lv1_Object=industryTree.get(i).getAsJsonObject();			
				industryNameList.add(Lv1_Object.get("industryName").getAsString());
				JsonArray Lv2_Array=Lv1_Object.get("children").getAsJsonArray();
				if(Lv2_Array!=null){
					for(int j=0;j<Lv2_Array.size();j++){
						JsonObject Lv2_Object=Lv2_Array.get(j).getAsJsonObject();			
						industryNameList.add(Lv2_Object.get("industryName").getAsString());
						JsonArray Lv3_Array=Lv2_Object.get("children").getAsJsonArray();
						if(Lv3_Array!=null){
							for(int k=0;k<Lv3_Array.size();k++){
								JsonObject Lv3_Object=Lv3_Array.get(k).getAsJsonObject();			
								industryNameList.add(Lv3_Object.get("industryName").getAsString());
							}
						}
					}
				}
			}
			return industryNameList;
		}				
	}
	
	//从文件中获取指标query词
	public static List<String> getTargeteQuery(File file){
		List<String[]> excelData = null;
		List<String> targetQueryList=new ArrayList<String>();
		try {
			excelData = ExcelData.getExcelData(file);
			JSONArray json = JSONArray.fromObject(excelData); 
//			System.out.println(json);
			for(int i=0;i<excelData.size();i++){
				targetQueryList.add(excelData.get(i)[0]);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return targetQueryList;
	}
	
		
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		List<String> indexNameList=indexApiTest.getIndexNameList("http://robo-dataapi-docker.wmcloud-stg.com/api/idx/getIdx.json?field=&ticker=&secID=");
		
		//登录QA环境,不然这个接口报需要登录
//		String cookie=roboLogin.QA_Login();		
//		List<String> industryNameList=indexApiTest.getIndustryNameList("https://gw.datayes-stg.com/rrp_adventure/industry/stock?ticker=601360",cookie);

		List<String> targetQueryList=indexApiTest.getTargeteQuery(new File("./File/指标.xlsx"));
	
		
	}

}
