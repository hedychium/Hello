package com.jl.crawl.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class anotherTest {

	public static void main(String[] args) {
		
		//"https://my.oschina.net/dtz/blog/1790159"
		String strurl = "http://robor.wmcloud-qa.com/v2/home";
		// TODO Auto-generated method stub
		URL url;
		try {
			url = new URL(strurl);
			// ͨ��url��������ҳ������
			URLConnection conn;
			try {
				conn = url.openConnection();
				// ͨ������ȡ����ҳ���ص�����
				InputStream is = conn.getInputStream();

				// ��ȡtext���͵�����
				if (conn.getContentType().startsWith("text")) {

				}
				System.out.println(conn.getContentEncoding());
				// һ�㰴�ж�ȡ��ҳ���ݣ����������ݷ���
				// �����BufferedReader��InputStreamReader���ֽ���ת��Ϊ�ַ����Ļ�����
				// ����ת��ʱ����Ҫ��������ʽ����
				try {
					BufferedReader br = new BufferedReader(
							new InputStreamReader(is, "GB2312"));
					
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

}
