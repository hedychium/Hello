package mommpci;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.datayes.qalibs.GatewayAgent;

public class uploadFile{
	static String accountName = "";
	static String fileDIR="D:\\我的工作\\MOM\\MOM导入\\自动化导入\\";
	
	protected static GatewayAgent gateway = new GatewayAgent();
	
	static {	
		
		Map<String, String> parameters = new HashMap<String, String>();
//		parameters.put("username", "datayes@mom.com");
//		parameters.put("password", "123456");
//		parameters.put("rememberMe", "false");	
		parameters.put("username", "sijia.guo2@datayes.com");
		parameters.put("password", "datayes@123");
		parameters.put("rememberMe", "false");	
		try {
			gateway.doPostForm("https://gw.datayes-stg.com/usermaster/authenticate/v1.json", parameters);
			//gateway.doPostForm("https://gw.datayes.com/usermaster/authenticate/v1.json", parameters);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//private static final String Gurl = "https://gw.datayes.com/mom";
	private static final String Gurl = "https://gw.datayes-stg.com/mom_stg";
	//private static final String Gurl ="http://test.mof.wmcloud-dev.com:6110";
	
	static Logger log = LogManager.getLogger(uploadFile.class.getName());
//	protected static GatewayAgent gateway = new GatewayAgent();

	public static String init() {
		String url = Gurl + "/api/accountUpload/init";
		String result = "";
		try {
			Map<String, String> parameters = new HashMap<String, String>();
			HttpResponse response = gateway.doPostForm(url, parameters);
			log.info(parameters);
			result = EntityUtils.toString(response.getEntity());
			log.info(result);
			return result;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;

	}

	public static String uploadnetValue(String account_id,String fileName) {
		String result = "";
		String url = Gurl + "/api/accountUpload/netValue/" + account_id;
		File file = new File(fileDIR+fileName);
		log.info(fileDIR+fileName);

		HttpResponse response;
		try {
			response = gateway.doPostFile(url, file, false);
			result = EntityUtils.toString(response.getEntity());
			log.info(result);
			return account_id;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static String uploadTransaction(String account_id,String fileName) {
		String result = "";
		String url = Gurl + "/api/accountUpload/transaction/" + account_id;
		File file = new File(fileDIR+fileName);

		HttpResponse response;
		try {
			response = gateway.doPostFile(url, file, false);
			result = EntityUtils.toString(response.getEntity());
			log.info(result);
			return account_id;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static String uploadposition(String account_id,String fileName) {
		String result = "";
		String url = Gurl + "/api/accountUpload/position/" + account_id;
		File file = new File(fileDIR+fileName);

		HttpResponse response;
		try {
			response = gateway.doPostFile(url, file, false);
			result = EntityUtils.toString(response.getEntity());
			log.info(result);
			return account_id;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static String HybriddaySave(){
		String result = "";
		String account_id = init();
		uploadnetValue(account_id,"netvalue-all-day-2016.csv");
		uploadposition(account_id,"position-H-2016-day.csv");
		String url = Gurl + "/api/accountUpload/saveAsync/" + account_id+"?accountId=";
	
		HttpResponse response;
//		String jsonString ="{\"initCash\":\"100000000\",\"futureMargin\":0,\"isList\":true,\"accountName\":\"sjtest\",\"code\":\""+account_id+"\",\"manager\":\"\",\"category\":\"E\",\"benchmarkList\":[{\"id\":1782,\"calType\":10000,\"weight\":1}],\"strategy\":\"股票策略\",\"childStrategy\":\"股票多头\",\"frequency\":\"day\",\"navFrequency\":\"day\",\"openDate\":\"2016-03-01\"}";
		
		String jsonString ="{\"initCash\":\"100000000\",\"futureMargin\":0,\"isList\":true,\"accountName\":\""+accountName+"\",\"code\":\""+account_id+"\",\"manager\":\"simico\",\"category\":\"H\",\"benchmarkList\":[{\"id\":1,\"calType\":10000,\"weight\":1}],\"strategy\":\"复合策略\",\"childStrategy\":\"复合策略\",\"frequency\":\"day\",\"navFrequency\":\"day\",\"openDate\":\"2016-03-01\",\"investConsultant\":\"simico\"}";
		
		try {
			response = gateway.doPostJson(url, jsonString);		
			result = EntityUtils.toString(response.getEntity());
			log.info(result);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
				
	}
	
	public static String netvalueSave(){
		String result = "";	
		String account_id = init();
		uploadnetValue(account_id,"netvalue-all-day-2016.csv");
		String url = Gurl + "/api/accountUpload/saveAsync/" + account_id+"?accountId=";
		
		log.info(url);
	
		HttpResponse response;
		String jsonString ="{\"initCash\":\"100000000\",\"futureMargin\":0,\"isList\":true,\"accountName\":\""+accountName+"\",\"code\":\""+account_id+"\",\"manager\":\"simico1\",\"category\":\"E\",\"benchmarkList\":[{\"id\":1782,\"calType\":100,\"weight\":1}],\"strategy\":\"股票策略\",\"childStrategy\":\"股票多头\",\"frequency\":\"day\",\"navFrequency\":\"day\",\"openDate\":\"2016-01-01\",\"investConsultant\":\"simico1\"}";
		log.info(jsonString);
		
		try {
			response = gateway.doPostJson(url, jsonString);		
			result = EntityUtils.toString(response.getEntity());
			log.info(result);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
				
	}
	
	
	
	public static String StockdaySave(){
		String result = "";
		String account_id = init();
		uploadnetValue(account_id,"netvalue-all-day-2016.csv");
		uploadTransaction(account_id,"stock-trans-day-2016.csv");
		String url = Gurl + "/api/accountUpload/saveAsync/" + account_id+"?accountId=";
	
		HttpResponse response;

		String jsonString ="{\"initCash\":100000000,\"futureMargin\":0,\"isList\":true,\"accountName\":\""+accountName+"\",\"code\":\""+account_id+"\",\"manager\":\"\",\"accountId\":\""+account_id+"\",\"category\":\"E\",\"openDate\":\"2016-03-01\",\"benchmarkList\":[{\"parentID\":\"\",\"id\":1782,\"name\":\"\",\"calType\":100,\"weight\":1}],\"strategy\":\"股票策略\",\"childStrategy\":\"股票多头\",\"investConsultant\":\"simico\",\"frequency\":\"day\",\"navFrequency\":\"day\"}";
		try {
			response = gateway.doPostJson(url, jsonString);		
			result = EntityUtils.toString(response.getEntity());
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;		
	}
	
	public static String bonddaySave(){
		String result = "";		
		String account_id = init();
		uploadnetValue(account_id,"netvalue-all-day-2016.csv");
		uploadposition(account_id,"bond-Y-day-pos-2016.csv");
		String url = Gurl + "/api/accountUpload/saveAsync/" + account_id+"?accountId=";
	
		HttpResponse response;

		String jsonString ="{\"initCash\":100000000,\"futureMargin\":0,\"isList\":true,\"accountName\":\""+accountName+"\",\"code\":\""+account_id+"\",\"manager\":\"simico\",\"accountId\":\""+account_id+"\",\"category\":\"B\",\"openDate\":\"2016-03-01\",\"benchmarkList\":[{\"parentID\":\"\",\"id\":26,\"name\":\"企债指数\",\"calType\":100,\"weight\":1}],\"strategy\":\"债券策略\",\"childStrategy\":\"债券策略\",\"investConsultant\":\"simico\",\"frequency\":\"day\",\"navFrequency\":\"day\"}";
		try {
			response = gateway.doPostJson(url, jsonString);		
			result = EntityUtils.toString(response.getEntity());
			log.info(result);
			return result;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
				
	}
	
	public static String FutureweekSave(){
		log.info(accountName);
		String result = "";
		
		String account_id = init();
		uploadnetValue(account_id,"future-netvalue-week.csv");
		uploadposition(account_id,"future-postion-week.csv");
		String url = Gurl + "/api/accountUpload/saveAsync/" + account_id+"?accountId=";
		//https://gw.wmcloud-stg.com/mom_qa/api/accountUpload/saveAsync/99c05fef364547ef9f862534fbc88f37?accountId=1fce9d15223f43c1be1e7ac03f601d7c
	
		HttpResponse response;

		String jsonString ="{\"initCash\":\"0\",\"futureMargin\":100000000,\"isList\":true,\"accountName\":\""+accountName+"\",\"code\":\""+account_id+"\",\"manager\":\"\",\"category\":\"FU\",\"benchmarkList\":[{\"id\":10004561,\"calType\":100,\"weight\":1}],\"strategy\":\"管理期货\",\"childStrategy\":\"管理期货复合策略\",\"frequency\":\"week\",\"navFrequency\":\"week\",\"openDate\":\"2015-05-01\",\"investConsultant\":\"simico\"}";
		try {
			response = gateway.doPostJson(url, jsonString);		
			result = EntityUtils.toString(response.getEntity());
			log.info(result);
			return result;
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;			
	}
	
	public static String getTID(String result){
	   	JSONObject js;
		try {
			js = new JSONObject(result).getJSONObject("job");
		   	String taskID =  js.getString("jobID");
		   	return taskID;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;		
	}
	
	public static String QueryAttr(String tid) throws Exception{	

		String url =Gurl+"/api/task/"+tid;
		log.info("归因查询:"+url);
		HttpResponse response = gateway.doGet(url);
	   	String result= EntityUtils.toString(response.getEntity());
	   	
	   	JSONObject js = new JSONObject(result);
	   	String status =  js.getString("status");
	   	return status;
	   	
	}
	
	public static String reportErr(String td) throws Exception{	
	String url =Gurl+"/api/task/"+td;
		HttpResponse response = gateway.doGet(url);
	   	String result= EntityUtils.toString(response.getEntity());
	   	return result;
	   	
	}
	
	public static void main(String args[]) throws Exception{
		accountName = "股票自动化20180712";
		String result ="";
		boolean isMatch = false;
		if(isMatch = Pattern.matches("期货.*", accountName)){
			result =FutureweekSave();
		}
		else if(isMatch = Pattern.matches("混合.*", accountName)){
			result =HybriddaySave();
		}
		else if(isMatch = Pattern.matches("净值.*", accountName)){
			result = netvalueSave();
		}
		else if(isMatch = Pattern.matches("股票.*", accountName)){
			result = StockdaySave();
		}
		else result = bonddaySave();

		String tid=getTID(result);			
	   	String status = QueryAttr(tid);
	   		   	
		while(!status.equals("SUCCESS")){			
			status=QueryAttr(tid);
			log.info(status);	
			if (status.equals("SUCCESS")){
				log.info("SUCCESS");
				break;
				}
			else if(status.equals("ERROR") ||status.equals("EXCEPTION")||status.equals("PARTIAL_SUCCESS")){
				log.error("status");
				log.error(reportErr(tid));
				break;
				}
			Thread.sleep(2*1000);
			}				
	}
}
