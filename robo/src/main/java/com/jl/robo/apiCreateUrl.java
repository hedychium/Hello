package com.jl.robo;

import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.protocol.HTTP;

import com.jl.UTILS.RandomUtils;

import net.sf.json.JSONArray;

/*
 * 构造api的url
 * 所有参数全排列方式
 * 随机选取几个参数，参数值随机的方式
 */

public class apiCreateUrl {
	
	/**
	 * 随机返回n个参数，map<key,valueList>形式
	 * @param filePath
	 * @return
	 */
	public static Map<String,Object> getParamsFromFile(String filePath,int n){
		File file=new File(filePath);
		
		Map<String,Object> params=new HashMap<String,Object>();
		try {
			List<String[]> excelData=ExcelData.getExcelData(file);
			JSONArray json = JSONArray.fromObject(excelData); 
			System.out.println(json);
			Map<String,List> allParams=new HashMap<String, List>();
			for(int i=0;i<excelData.size();i++){
				String key=excelData.get(i)[0];
				String values=excelData.get(i)[1];
				String[] sList=values.split(",");
				List<String> vList=Arrays.asList(sList);
				allParams.put(key, vList);
			}
			params=createRandomList(allParams,n);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return params;
	}
	
	/**
	 * 随机选n个参数
	 * @param paramsMap
	 * @param n
	 * @return
	 */
	private static Map<String,Object> createRandomList(Map<String,List> paramsMap,int n){
		Map map = new HashMap();
		Map<String,Object> pNew=new HashMap<String, Object>();
		if(paramsMap.size()<=n){
			Iterator<Map.Entry<String, List>> entries = paramsMap.entrySet().iterator(); 
			while (entries.hasNext()) { 
			  Entry<String, List> entry = entries.next(); 
			  pNew.put(entry.getKey(), RandomUtils.getRandomElement(entry.getValue())); 
			}
			return pNew;
		}
		else{
			while(pNew.size()<n){
				String key=RandomUtils.getRandomKeyFromMap(paramsMap);
				if(!pNew.containsKey(key)){
					List list=paramsMap.get(key);
					pNew.put(key, RandomUtils.getRandomElement(list));
				}
			}			
			return pNew;
		}		
	}
	
	public static String  createUrl(String urlParam,String filePath,int n){
		Map<String,Object> params=getParamsFromFile(filePath,n);
		StringBuffer sbParams = new StringBuffer();
		if (params != null && params.size() > 0) {
			for (Entry<String, Object> entry : params.entrySet()) {
				sbParams.append(entry.getKey());
				sbParams.append("=");
				try {
					sbParams.append(URLEncoder.encode(String.valueOf(entry.getValue()), HTTP.UTF_8));
				} catch (UnsupportedEncodingException e) {
					throw new RuntimeException(e);
				}
				sbParams.append("&");
			}
		}
		if (sbParams != null && sbParams.length() > 0) {
			urlParam = urlParam + "?" + sbParams.substring(0, sbParams.length() - 1);
		}
		return urlParam;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filePath="./File/Params.xlsx";
		String urlParam="www.baidu.com";
		System.out.println(createUrl(urlParam,filePath,3));
	}
}
