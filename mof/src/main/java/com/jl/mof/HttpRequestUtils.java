package com.jl.mof;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Map.Entry;

import javax.imageio.stream.FileImageInputStream;

import net.sf.json.JSONObject;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.*;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpRequestUtils {
	/**
	 * @Description:使用HttpClient发送post请求"Content-Type", "application/json;charset=utf-8"
	 *                                                  请求参数要求json格式
	 */
	public static String httpClientPost2(String urlParam, Map<String, Object> params, String charset) {
		StringBuffer resultBuffer = null;
		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(urlParam);
		httpPost.addHeader("Content-Type", "application/json;charset=utf-8");

		// 设置请求的参数
		JSONObject jsonParam = JSONObject.fromObject(params);

		BufferedReader br = null;

		try {
			StringEntity entity = new StringEntity(jsonParam.toString(), charset);
			entity.setContentEncoding("UTF-8");
			entity.setContentType("application/json");
			httpPost.setEntity(entity);
			HttpResponse response = client.execute(httpPost);
			// 读取服务器响应数据
			resultBuffer = new StringBuffer();
			br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String temp;
			while ((temp = br.readLine()) != null) {
				resultBuffer.append(temp);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					br = null;
					throw new RuntimeException(e);
				}
			}
		}
		return resultBuffer.toString();
	}

	/**
	 * @Description:使用HttpClient发送post请求
	 */
	public static String httpClientPost(String urlParam, Map<String, Object> params, String charset) {
		StringBuffer resultBuffer = null;
		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(urlParam);
		// 构建请求参数
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		Iterator<Entry<String, Object>> iterator = params.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Object> elem = iterator.next();
			list.add(new BasicNameValuePair(elem.getKey(), String.valueOf(elem.getValue())));
		}
		BufferedReader br = null;
		try {
			if (list.size() > 0) {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
				httpPost.setEntity(entity);
			}
			HttpResponse response = client.execute(httpPost);
			// 读取服务器响应数据
			resultBuffer = new StringBuffer();
			br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String temp;
			while ((temp = br.readLine()) != null) {
				resultBuffer.append(temp);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					br = null;
					throw new RuntimeException(e);
				}
			}
		}
		return resultBuffer.toString();
	}

	/**
	 * @Description:使用HttpClient发送get请求
	 */
	public static String httpClientGet(String urlParam, Map<String, Object> params, String charset) {
		StringBuffer resultBuffer = null;
		HttpClient client = new DefaultHttpClient();
		BufferedReader br = null;
		// 构建请求参数
		StringBuffer sbParams = new StringBuffer();
		if (params != null && params.size() > 0) {
			for (Entry<String, Object> entry : params.entrySet()) {
				sbParams.append(entry.getKey());
				sbParams.append("=");
				try {
					sbParams.append(URLEncoder.encode(String.valueOf(entry.getValue()), charset));
				} catch (UnsupportedEncodingException e) {
					throw new RuntimeException(e);
				}
				sbParams.append("&");
			}
		}
		if (sbParams != null && sbParams.length() > 0) {
			urlParam = urlParam + "?" + sbParams.substring(0, sbParams.length() - 1);
		}
		HttpGet httpGet = new HttpGet(urlParam);
		try {
			HttpResponse response = client.execute(httpGet);
			// 读取服务器响应数据
			br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String temp;
			resultBuffer = new StringBuffer();
			while ((temp = br.readLine()) != null) {
				resultBuffer.append(temp);
			}
			System.out.println("返回的状态码:" + response.getStatusLine().getStatusCode());
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					br = null;
					throw new RuntimeException(e);
				}
			}
		}
		
		return resultBuffer.toString();
	}

	/**
	 * 发送http请求，附带文件
	 * 
	 * @param urlParam
	 * @param filepath
	 * @return
	 */
	public static String sendPostWithFile(String url, Cookie[] cookies, String filepath) {
		DataOutputStream out = null;
		BufferedReader in = null;
		String result = null;
		HttpURLConnection conn = null;
		StringBuffer tmpcookies = new StringBuffer();
		for (Cookie c : cookies) {
			tmpcookies.append(c.toString() + ";");
			System.out.println("cookies = " + c.toString());
		}
		try {
			URL realUrl = new URL(url);
			// 打开和url之间的连接
			conn = (HttpURLConnection) realUrl.openConnection();

			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 定义数据分割线
			String BOUNDARY = "----WebKitFormBoundaryatUU82mkyZInxB5k";

			conn.setConnectTimeout(5000);
			conn.setReadTimeout(30000);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36");
			conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
			conn.connect();
			out = new DataOutputStream(conn.getOutputStream());

			File file = new File(filepath);

			// 上传文件
			StringBuilder sb = new StringBuilder();
			sb.append("--");
			sb.append(BOUNDARY);
			sb.append("\r\n");
			// 文件参数,photo参数名可以随意修改
			sb.append("Content-Disposition: form-data;name=\"uploadFile\";filename=\"" + file.getName() + "\"\r\n");
			System.out.println(file.getName());
			sb.append("Content-Type:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			// 参数头设置完以后需要两个换行，然后才是参数内容
			sb.append("\r\n");
			sb.append("\r\n");

			// 将参数头的数据写入到输出流中
			out.write(sb.toString().getBytes());

			// 数据输入流,用于读取文件数据
			DataInputStream inputStream = new DataInputStream(new FileInputStream(file));
			byte[] bufferOut = new byte[1024 * 8];
			int bytes = 0;
			// 每次读8KB数据,并且将文件数据写入到输出流中
			while ((bytes = inputStream.read(bufferOut)) != -1) {
				out.write(bufferOut, 0, bytes);
			}
			// 最后添加换行
			out.write("\r\n".getBytes());
			inputStream.close();

			// 定义最后数据分隔线，即--加上BOUNDARY再加上--。
			byte[] end_data = ("\r\n" + "--" + BOUNDARY + "--" + "\r\n").getBytes();
			// 写上结尾标识
			out.write(end_data);
			System.out.println(out.toString());
			out.flush();
			out.close();

			// 定义BufferedReader输入流来读取URL的响应

			int code = conn.getResponseCode();
			InputStream is;
			if (code == 200) {
				is = conn.getInputStream(); // 得到网络返回的输入流
			} else {
				is = conn.getErrorStream(); // 得到网络返回的输入流
			}

			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line = null;
			while ((line = reader.readLine()) != null) {
				result += line; // 这里读取的是上边url对应的上传文件接口的返回值，读取出来后，然后接着返回到前端，实现接口中调用接口的方式
			}
			System.out.println(result);
			reader.close();
			reader = null;
		} catch (Exception e) {
			System.out.println("发送POST请求出现异常！" + e);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
				conn = null;
			}
		}
		return result;
	}

	public static String sendWithFile2(CookieStore cookieStore, String cookie, String postUrl, String filePath,Map<String,ContentBody> reqParam) {
		String respStr = "";
		// 创建HttpClientBuilder
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// HttpClient
		CloseableHttpClient closeableHttpClient = null;
		if (cookieStore != null) {
			closeableHttpClient = httpClientBuilder.setDefaultCookieStore(cookieStore).build();
		} else {
			closeableHttpClient = httpClientBuilder.build();
		}
		HttpPost httpPost = new HttpPost(postUrl);
		File file=new File(filePath);
		FileBody fileBody = new FileBody(file);
		MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
		
		multipartEntityBuilder.addPart("uploadFile",fileBody);
//		multipartEntityBuilder.addPart("file", fileBody);

		httpPost.setHeader("Cookie", cookie);
		HttpEntity reqEntity  = multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE).build();
		httpPost.setEntity(reqEntity);
		try {
			CloseableHttpResponse response = closeableHttpClient.execute(httpPost);

			System.out.println("上传之后返回的状态码:" + response.getStatusLine().getStatusCode());
			System.out.println("response:" + response.toString());
			try {
				HttpEntity resEntity = response.getEntity();

				if (resEntity == null) {
				}
				InputStream is = resEntity.getContent();
				StringBuffer strBuf = new StringBuffer();
				byte[] buffer = new byte[4096];
				int r = 0;
				while ((r = is.read(buffer)) > 0) {
					strBuf.append(new String(buffer, 0, r, "UTF-8"));
				}
				System.out.println("resp=" + strBuf);

				EntityUtils.consume(reqEntity);

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

				response.close();
				closeableHttpClient.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return respStr;
	}
}
