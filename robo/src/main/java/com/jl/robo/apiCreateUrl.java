package com.jl.robo;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

/*
 * 构造api的url
 * 所有参数全排列方式
 * 随机选取几个参数，参数值随机的方式
 */

public class apiCreateUrl {
	
	/**
	 * 返回参数，map<key,valueList>形式
	 * @param filePath
	 * @return
	 */
	public static Map<String,Object> getParamsFromFile(String filePath,int n){
		File file=new File(filePath);
		Map<String,Object> params=new HashMap<String, Object>();
		try {
			List<String[]> excelData=ExcelData.getExcelData(file);
			JSONArray json = JSONArray.fromObject(excelData); 
			System.out.println(json);
			for(int i=0;i<excelData.size();i++){
				String key=excelData.get(i)[0];
				String values=excelData.get(i)[1];
				String[] sList=values.split(",");
				List<String> vList=Arrays.asList(sList);
				params.put(key, vList);
			}			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return params;
	}
	private static Map<String,Object> createRandomList(Map<String,Object> paramsMap,int n){
		Map map = new HashMap();
		Map<String,Object> pNew=new HashMap<String, Object>();
		if(paramsMap.size()<=n){
			
			return pNew;
		}
		else{
			
			return pNew;
		}
		
	}
	
	private static List<Object> createRandomList(List<Object> list,int n){
		Map map = new HashMap();
		List listNew = new ArrayList();
        if (list.size() <= n) {
            return list;
        } else {
            while (map.size() < n) {
                int random = (int) (Math.random() * list.size());
                if (!map.containsKey(random)) {
                    map.put(random, "");
                    System.out.println(random + "===========" + list.get(random));
                    listNew.add(list.get(random));
                }
            }
            return listNew;
        }
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filePath="./File/Params.xlsx";
		apiCreateUrl.getParamsFromFile(filePath, 2);
	}
}
