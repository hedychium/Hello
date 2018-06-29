package com.jl.attribution;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;

import org.apache.poi.ss.usermodel.*;

public class analysisEXCEL {
	 private Sheet sheet;    //表格类实例
	 
	 //读取excel文件，创建表格实例  
	    private void loadExcel(String filePath) {  
	        FileInputStream inStream = null;  
	        try {  
	            inStream = new FileInputStream(new File(filePath));  
	            Workbook workBook = WorkbookFactory.create(inStream);  
	             
	            sheet = workBook.getSheetAt(0);           
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }finally{  
	            try {  
	                if(inStream!=null){  
	                    inStream.close();  
	                }                  
	            } catch (IOException e) {                  
	                e.printStackTrace();  
	            }  
	        }  
	    }  
	    
	    //获取单元格的值  
	    private String getCellValue(Cell cell) {  
	        String cellValue = "";  
	        DataFormatter formatter = new DataFormatter();  
	        if (cell != null) {  
	            //判断单元格数据的类型，不同类型调用不同的方法  
	            switch (cell.getCellType()) {  
	                //数值类型  
	                case Cell.CELL_TYPE_NUMERIC:  
	                    //进一步判断 ，单元格格式是日期格式   
	                    if (DateUtil.isCellDateFormatted(cell)) {  
	                        cellValue = formatter.formatCellValue(cell);  
	                    } else {  
	                        //数值  
	                        double value = cell.getNumericCellValue();  
	                        int intValue = (int) value;  
	                        cellValue = value - intValue == 0 ? String.valueOf(intValue) : String.valueOf(value);  
	                    }  
	                    break;  
	                case Cell.CELL_TYPE_STRING:  
	                    cellValue = cell.getStringCellValue();  
	                    break;  
	                case Cell.CELL_TYPE_BOOLEAN:  
	                    cellValue = String.valueOf(cell.getBooleanCellValue());  
	                    break;  
	                    //判断单元格是公式格式，需要做一种特殊处理来得到相应的值  
	                case Cell.CELL_TYPE_FORMULA:{  
	                    try{  
	                        cellValue = String.valueOf(cell.getNumericCellValue());  
	                    }catch(IllegalStateException e){  
	                        cellValue = String.valueOf(cell.getRichStringCellValue());  
	                    }  
	                      
	                }  
	                    break;  
	                case Cell.CELL_TYPE_BLANK:  
	                    cellValue = "";  
	                    break;  
	                case Cell.CELL_TYPE_ERROR:  
	                    cellValue = "";  
	                    break;  
	                default:  
	                    cellValue = cell.toString().trim();  
	                    break;  
	            }  
	        }  
	        return cellValue.trim();  
	    }	
	    
	    
	    public static void main(String[] args) {
			
		}
	    
}
