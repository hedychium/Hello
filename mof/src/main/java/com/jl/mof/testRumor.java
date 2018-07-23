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
		// ������
		System.out.println("�������ݿ�..."+DB_URL);
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
		
		//��һ��������һ��workbook��Ӧһ��excel�ļ�
		Workbook workbook = new XSSFWorkbook();;
        //�ڶ�������workbook�д���һ��sheet��Ӧexcel�е�sheet
        Sheet sheet =workbook.createSheet("news");
        //����������sheet������ӱ�ͷ��0�У��ϰ汾��poi��sheet������������
        Row row = sheet.createRow(0);
        //���Ĳ���������Ԫ�����ñ�ͷ
        Cell cell = row.createCell(0);
        cell.setCellValue("newsID");
        cell = row.createCell(1);
        cell.setCellValue("title");
        cell = row.createCell(2);
        cell.setCellValue("text");
		
		// ����������
        String driver = "com.mysql.jdbc.Driver";
        try {
        	Class.forName("com.mysql.jdbc.Driver");
			try {
				  // ������
	            System.out.println("�������ݿ�..."+"bigdata");
				Connection BigData_con=DriverManager.getConnection("jdbc:mysql://real-stg01.datayes.com:3314/bigdata","app_qasvc_ro","sgijV4dzPCoKsRGjn");
				System.out.println("����"+"bigdata�ɹ�");
				
				System.out.println("�������ݿ�..."+"news");
				Connection News_con=DriverManager.getConnection("jdbc:mysql://news-db01.stg.datayes.com:3306/news","app_gaea_ro","EQw6WquhnCKPp8Li");
				System.out.println("����"+"news�ɹ�");
				
				
				// statement����ִ��SQL���
	            Statement statement_bigdata = BigData_con.createStatement();
	            Statement statement_news = News_con.createStatement();
	            // Ҫִ�е�SQL���
	            String sql = "SELECT NEWS_ID FROM bigdata.stock_news_tag where IS_RUMOUR=1 and date_format(NEWS_EFFECTIVE_TIME,'%Y-%m-%d')  between '2018-07-17' and '2018-07-17'";
	            // �����
	            ResultSet rs = statement_bigdata.executeQuery(sql);
	            
	            String re;
	            int i=0;
	            while(rs.next()) {	                
	                // ѡ��NEWS_ID��������
	                 re = rs.getString("NEWS_ID");
	                // ������
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
      //���ļ����浽ָ����λ��
        try {
            FileOutputStream fos = new FileOutputStream("./File/news.xlsx");
            workbook.write(fos);
            System.out.println("д��ɹ�");
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
		
	}

}
