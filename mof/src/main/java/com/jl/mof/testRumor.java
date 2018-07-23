package com.jl.mof;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class testRumor {
	
	public static Connection conMysql(String DB_URL,String user,String password){
		Connection conn = null;
		// 打开链接
		System.out.println("连接数据库..."+DB_URL);
		try {
			conn = DriverManager.getConnection(DB_URL,user,password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//第一步，创建一个workbook对应一个excel文件
		Workbook workbook = new XSSFWorkbook();;
        //第二部，在workbook中创建一个sheet对应excel中的sheet
        Sheet sheet =workbook.createSheet("news");
        //第三部，在sheet表中添加表头第0行，老版本的poi对sheet的行列有限制
        Row row = sheet.createRow(0);
        //第四步，创建单元格，设置表头
        Cell cell = row.createCell(0);
        cell.setCellValue("newsID");
        cell = row.createCell(1);
        cell.setCellValue("title");
        cell = row.createCell(2);
        cell.setCellValue("text");
		
		// 驱动程序名
        String driver = "com.mysql.jdbc.Driver";
        try {
        	Class.forName("com.mysql.jdbc.Driver");
			try {
				  // 打开链接
	            System.out.println("连接数据库..."+"bigdata");
				Connection BigData_con=DriverManager.getConnection("jdbc:mysql://real-stg01.datayes.com:3314/bigdata","app_qasvc_ro","sgijV4dzPCoKsRGjn");
				System.out.println("连接"+"bigdata成功");
				
				System.out.println("连接数据库..."+"news");
				Connection News_con=DriverManager.getConnection("jdbc:mysql://news-db01.stg.datayes.com:3306/news","app_gaea_ro","EQw6WquhnCKPp8Li");
				System.out.println("连接"+"news成功");
				
				
				// statement用来执行SQL语句
	            Statement statement_bigdata = BigData_con.createStatement();
	            Statement statement_news = News_con.createStatement();
	            // 要执行的SQL语句
	            String sql = "SELECT NEWS_ID FROM bigdata.stock_news_tag where IS_RUMOUR=1 and date_format(NEWS_EFFECTIVE_TIME,'%Y-%m-%d')  between '2018-07-17' and '2018-07-17'";
	            // 结果集
	            ResultSet rs = statement_bigdata.executeQuery(sql);
	            
	            String re;
	            int i=0;
	            while(rs.next()) {	                
	                // 选择NEWS_ID这列数据
	                 re = rs.getString("NEWS_ID");
	                // 输出结果
		             System.out.println(re);
	                 String sql_2="SELECT news_id,news_title, news_body FROM news.news_detail_backup where NEWS_ID="+re;
	                 ResultSet rs_2=statement_news.executeQuery(sql_2);
	                 System.out.println("-------");
	                 System.out.println();	                 
	                 while(rs_2.next()){
	                	 i++;
	                	 String id=rs_2.getString("news_id");
	                	 String title=rs_2.getString("news_title");
	                	 String text=rs_2.getString("news_body");
	                	 
	                	 Row row1 = sheet.createRow(i);
	                	 row1.createCell(0).setCellValue(id);
	                	 row1.createCell(1).setCellValue(title);
	                     row1.createCell(2).setCellValue(text);
		                 System.out.println(id);
		                 System.out.println(text);
	                 }
	                 
	                 System.out.println("----****************---"+i);
	               }	               

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      //将文件保存到指定的位置
        try {
            FileOutputStream fos = new FileOutputStream("./File/news.xlsx");
            workbook.write(fos);
            System.out.println("写入成功");
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
		
	}

}
