package com.jl.crawl.main;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.jl.UTILS.ExcelData;
import com.jl.crawl.util.PhantomJsUtil;

public class usePhantomJs {
	/**
	 * 去除特殊字符
	 * @param text
	 * @return
	 */
	public static String reMoveSpacialChars(String text){
//		System.out.println("text:"+text);
		String text_new=text.replaceAll("\\s*", ""); 
		text_new=text_new.replaceAll("[^\u4e00-\u9fa5a-zA-Z0-9]","");
//		System.out.println("text_new:"+text_new);
		return text_new;
	}
	
	public static void checkMeta(String domain,String filePath){
		WebDriver webDriver = PhantomJsUtil.getPhantomJs();
		List<String[]> params = null;
		try {
			try {
				params = ExcelData.getExcelData(new File(filePath));
				System.out.println("params.size():"+params.size());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(int i=0;i<params.size();i++){
				String url=domain+params.get(i)[1];
				webDriver.get(url);
				System.out.println("url:"+url);
//				WebDriverWait wait = new WebDriverWait(webDriver, 40);
//				 wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("meta[name=\"description\"]")));//��ʼ����ҳ���ȴ�����Ԫ�س���

//				webDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				try {
					Thread.sleep(20000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Document document = Jsoup.parse(webDriver.getPageSource());

				String title_text=params.get(i)[2];
				title_text=usePhantomJs.reMoveSpacialChars(title_text);
				String keywords_text=params.get(i)[3];
				keywords_text=usePhantomJs.reMoveSpacialChars(keywords_text);
				String description_text=params.get(i)[4];
				description_text=usePhantomJs.reMoveSpacialChars(description_text);
				
				// TODO
				boolean title_flag=false;
				boolean kw_flag=false;
				boolean dc_flag=false;
				
				List<Element> infoListEle = document.getElementsByTag("title");
//				System.out.println(infoListEle.size());
				
				for(Element element:infoListEle){
					// System.out.println(element.getElementsByTag("h2").first().getElementsByTag("a").attr("href"));
					System.out.println(element);
					String title=usePhantomJs.reMoveSpacialChars(element.text());
					if(title.equals(title_text)){
						title_flag=true;
						break;
					}else{
						System.out.println("orginal:"+title_text);
						System.out.println("get:"+title);
					}
				}
				List<Element> infoListEle2 = document.getElementsByAttributeValue("name", "keywords");// ��ȡԪ�ؽڵ��
//				System.out.println(infoListEle2.size());
				
				for(Element element:infoListEle2){
					// System.out.println(element.getElementsByTag("h2").first().getElementsByTag("a").attr("href"));
					System.out.println(element);
					String keywords=usePhantomJs.reMoveSpacialChars(element.attr("content"));
					if(keywords.equals(keywords_text)){
						kw_flag=true;
						break;
					}else{
						System.out.println("orginal:"+keywords_text);
						System.out.println("get:"+keywords);
					}
				}
				List<Element> infoListEle3 = document.getElementsByAttributeValue("name", "description");// ��ȡԪ�ؽڵ��
//				System.out.println(infoListEle3.size());
				
				for(Element element:infoListEle3){
					// System.out.println(element.getElementsByTag("h2").first().getElementsByTag("a").attr("href"));
					System.out.println(element);
					String description=usePhantomJs.reMoveSpacialChars(element.attr("content"));
					if(description.equals(description_text)){
						dc_flag=true;
						break;
					}else{
						System.out.println("orginal:"+description_text);
						System.out.println("get:"+description);
					}
				}
				if(title_flag==true&&kw_flag==true&&dc_flag==true){
					System.out.println("pass  "+params.get(i)[0]+" "+url);
				}else{
					System.out.println("fail  "+params.get(i)[0]+" "+url);
				}
				System.out.println();
			}
		} finally {
			if (webDriver != null) {
				webDriver.quit();
			}
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String domain="https://robo.datayes-stg.com/";
		String filePath="./File/meta.xls";
		usePhantomJs.checkMeta(domain, filePath);

	}

}
