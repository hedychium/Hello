package com.jl.crawl.main;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class helo {

	public static String html(String url, String cookies) throws Exception {
		String html = "";
		CloseableHttpClient httpClient = HttpClients.createDefault();

		RequestConfig config = RequestConfig.custom().setConnectTimeout(60000)
				.setConnectionRequestTimeout(60000).setSocketTimeout(60000)
				.build();

		HttpGet httpGet = new HttpGet(url);

		httpGet.setConfig(config);

		httpGet.setHeader(
				"user-agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
		httpGet.setHeader("Cookie", cookies);
		CloseableHttpResponse respone = httpClient.execute(httpGet);
		int statusCode = respone.getStatusLine().getStatusCode();
		if (statusCode == 302) {

		}
		HttpEntity entity = respone.getEntity();

		if (entity != null) {

			html = EntityUtils.toString(entity, "UTF-8");

			return html;
		}
		EntityUtils.consume(entity);
		respone.close();

		httpClient.close();

		return html;
	}

	public static void main(String[] args) {
		String url = "https://stock.xueqiu.com/v5/stock/history/trade.json?symbol=SZ300647&count=10";
		try {
			Document doc = Jsoup
					.connect(url)
					.header("Cookie",
							"device_id=3f2bccf8b3a4b6433a396aadf28d01d8; s=gd11637sgd; bid=3b030a466696508b49add666804b75f5_ji5lo07e; _ga=GA1.2.484070856.1528354943; Hm_lvt_1db88642e346389874251b5a1eded6e3=1534121990,1536196956; snbim_minify=true; _gid=GA1.2.106499852.1536544453; remember=1; remember.sig=K4F3faYzmVuqC0iXIERCQf55g2Y; xq_a_token=d3f190bd542f23a490d2fe0b99e8d78202e3e7c0; xq_a_token.sig=Mknnos9UAFA56gKcdy59qDwkW8I; xq_r_token=d0d8e628c07bcbf311b2c2f83011008c4777c1cf; xq_r_token.sig=rah1XrPSgHy5ORp6B3U-A4LOk6A; xq_is_login=1; xq_is_login.sig=J3LxgPVPUzbBg3Kee_PquUfih7Q; u=6192900354; u.sig=EwOu-1wsbBXzvRTVaV7ChMq5O_I; Hm_lpvt_1db88642e346389874251b5a1eded6e3=1536634606; _gat_gtag_UA_16079156_4=1")
					.header("Host", "stock.xueqiu.com")
					.header("Origin", "https://xueqiu.com")
					.header("Referer", "https://xueqiu.com/S/SH601318")
					.header("User-Agent",
							"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36")
					.ignoreContentType(true).get();
			System.out.println(doc.text());
		} catch (IOException e) {
			e.printStackTrace();
		}
		// getData();
	}

}
