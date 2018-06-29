package com.jl.hangye;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 这个小程序是为了解析工行中用到的申万一级和申万二级行业市值文件的
 * 调用数据部门的一个接口，将返回结果保存在txt中，然后解析这个txt，输出csv
 * @author li.jiang
 *
 */
public class TestJson {
	public static ArrayList<HangYeClass> analysisJSON(String jsonString,ArrayList<String> indexList) {
		JSONObject json = JSONObject.fromObject(jsonString);

		JSONArray jsonArray1 = JSONArray.fromObject(json.getString("data"));
		System.out.println(jsonArray1);

		ArrayList<HangYeClass> hangyeObjects = new ArrayList<HangYeClass>();
		for (int i = 0; i < jsonArray1.size(); i++) {
			HangYeClass hangye = new HangYeClass();
			hangye.setTicker(jsonArray1.getJSONObject(i).getString("ticker"));
			hangye.setSecShortName(jsonArray1.getJSONObject(i).getString(
					"secShortName"));
			hangye.setnegMarketValueA(jsonArray1.getJSONObject(i).getString(
					"negMarketValueA"));
			if (indexList.contains(jsonArray1.getJSONObject(i).getString(
					"ticker"))) {
				hangyeObjects.add(hangye);
			}
		}
		// System.out.println(hangyeObjects.size());
		return hangyeObjects;
	}

	public static String readFileByLines(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		String outString = "";
		try {

			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				if (tempString != null) {
					outString = outString + tempString;
				}
			}

			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return outString;
	}

	public static ArrayList<String> readFileByLinesforIndex(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		ArrayList<String> indexList = new ArrayList<String>();
		try {

			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				if (tempString != null) {
					indexList.add(tempString);
				}
			}

			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return indexList;
	}

	public static void main(String[] args) {

		String data = readFileByLines("./data/HangYeJson.txt");
		ArrayList<String> indexList = readFileByLinesforIndex("./data/index.txt");
		ArrayList<HangYeClass> hangyeObjects=analysisJSON(data, indexList);

		File writename = new File("./data/output.csv"); // 相对路径，如果没有则要建立一个新的output.txt文件
		try {
			writename.createNewFile();
			BufferedWriter out;
			try {
				out = new BufferedWriter(new FileWriter(writename));
				try {
					for (int i = 0; i < hangyeObjects.size(); i++) {
						out.append(hangyeObjects.get(i).toString()+"\r\n");
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // \r\n即为换行
				out.flush(); // 把缓存区内容压入文件
				out.close(); // 最后记得关闭文件
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
