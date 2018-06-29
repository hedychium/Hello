package com.jl.hangye;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * ���С������Ϊ�˽����������õ�������һ�������������ҵ��ֵ�ļ���
 * �������ݲ��ŵ�һ���ӿڣ������ؽ��������txt�У�Ȼ��������txt�����csv
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
			// һ�ζ���һ�У�ֱ������nullΪ�ļ�����
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
			// һ�ζ���һ�У�ֱ������nullΪ�ļ�����
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

		File writename = new File("./data/output.csv"); // ���·�������û����Ҫ����һ���µ�output.txt�ļ�
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
				} // \r\n��Ϊ����
				out.flush(); // �ѻ���������ѹ���ļ�
				out.close(); // ���ǵùر��ļ�
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
