package com.jl.mof;

import java.io.*;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class RandomEmailGenerator {

	public static String generator_a_mail(int length, String mailAddress) {
		String mail = new RandomString().generateUpperLowerString(length)+ mailAddress;
		// System.out.println(mail);
		return mail;
	}
/**
 * 
 * @param file
 * @param length
 * @param mailAddress
 */
	public static void writeExcel(File file,int num, int length, String mailAddress) {
		try {
			 FileInputStream input = new FileInputStream(file);
			 System.out.println("file.getAbsoluteFile()：" + file.getAbsoluteFile());
			 System.out.println("input：" + input);
			 XSSFWorkbook workBook=new XSSFWorkbook(input);
				// sheet 对应一个工作页
				XSSFSheet sheet = workBook.getSheetAt(0);

//				int rowLast = sheet.getLastRowNum();//从上次结尾最后开始追加（曾有数据删除后行数会变）
				int rowLast=0;//从第一行开始追加
				System.out.println("rowLast：" + rowLast);

				Row hhsfRow = sheet.getRow(0);
				int columnNumCount = hhsfRow.getPhysicalNumberOfCells();
				System.out.println("rowLast：" + columnNumCount);
				/**
				 * 往Excel中写新数据
				 */
				for (int j = rowLast; j < rowLast + num; j++) {
					// 创建一行：从下一行行开始
					Row row = sheet.createRow(j + 1);					

					for (int k = 0; k <= columnNumCount; k++) {
						String username = new RandomString().generateUpperLowerString(length);
						String mail = username + mailAddress;
						// 在一行内循环
						Cell first = row.createCell(0);
						first.setCellValue(username);

						Cell second = row.createCell(1);
						second.setCellValue(mail);

						Cell third = row.createCell(2);
						third.setCellValue("随机生成的");
						
						Cell Forth = row.createCell(3);
						Forth.setCellValue(username.substring(0,1).toUpperCase());
					}
				}
				FileOutputStream xlsStream = new FileOutputStream(file);
				workBook.write(xlsStream);
				xlsStream.close();
				System.out.println("完成");
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
    public static void main(String[] args) throws IOException {  
    	File file=new File("./File/尽调对象批量导入模板.xlsx");
    	writeExcel(file,600,15,"@datayes.com");
    } 

}
