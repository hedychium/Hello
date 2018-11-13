package com.jl.crawl.main;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jsoup.select.Elements;
import org.testng.annotations.*;

import com.jl.UTILS.ExcelData;
import com.jl.crawl.page.Page;
import com.jl.crawl.page.PageParserTool;

public class NewTestMeta {
	public  List<String[]> params=null;
	
	
	@Test
	@Parameters("filePath")
	public void getDataFormFile(String filePath){
		File file=new File(filePath);
		params=null;
		try {
			params=ExcelData.getExcelData(file);
			System.out.println(params);
		} catch (IOException e) {
			// TODO Auto-generated catch blockk
			e.printStackTrace();
		}		
	}
	

	@Test(dependsOnMethods = {"getDataFormFile"})
	@Parameters("domain")
	public void testMeta(String domain) {
	    
		
		
		for(int i=0;i<params.size();i++){
			String url=domain+params.get(i)[1];
			System.out.println(url);
			Page page=testUrlContent.sendRequstAndGetResponse(url,null);
			System.out.println(page.getHtml());
			Elements titles = PageParserTool.select(page,"meta[name=\"title\"]");
			String title=titles.get(0).text();
			Elements keywords1 = PageParserTool.select(page,"meta[name=\"title\"]");
			String keywords=keywords1.get(0).text();
		}
		
	}
	
	public void getElem(String a){}
}
