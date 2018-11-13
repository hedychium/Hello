package com.jl.crawl.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


/***
 * 
 * @author li.jiang
 *使用URLConnection访问url，到得返回页面信息，session、cookie不好实现
 */

public class anotherTest {

	public static void main(String[] args) {
		
		//"https://my.oschina.net/dtz/blog/1790159"
		String strurl = "https://robo.datayes.com/v2/fastreport/search?input=%E9%94%90%E7%A7%91%E6%BF%80%E5%85%89#reportSearch";
		// TODO Auto-generated method stub
		URL url;
		try {
			url = new URL(strurl);
			// 通过url建立与网页的连接
			URLConnection conn;
			try {
				conn = url.openConnection();
				// 通过链接取得网页返回的数据
				InputStream is = conn.getInputStream();

				// 提取text类型的数据
				if (conn.getContentType().startsWith("text")) {

				}
				System.out.println(conn.getContentEncoding());
				// 一般按行读取网页数据，并进行内容分析
				// 因此用BufferedReader和InputStreamReader把字节流转化为字符流的缓冲流
				// 进行转换时，需要处理编码格式问题
				try {
					BufferedReader br = new BufferedReader(
							new InputStreamReader(is, "GB2312"));
					//按行读取并打印
		            String line=null;
		            while((line=br.readLine())!=null){
		            	System.out.println(line);
		            }
					
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
