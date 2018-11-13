package com.jl.crawl.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class PhantomJsUtil {

	public static WebDriver getPhantomJs() {
		String osname = System.getProperties().getProperty("os.name");
		if (osname.equals("Linux")) {// 判断系统win or Linux
			System.setProperty("phantomjs.binary.path", "/usr/bin/phantomjs");
		} else {
			System.setProperty("phantomjs.binary.path",
					"D:/安装包/phantomjs-2.1.1-windows/bin/phantomjs.exe");// 配置PhantomJs路径
		}
		DesiredCapabilities desiredCapabilities = DesiredCapabilities
				.phantomjs();
		// ���ò���
		desiredCapabilities
				.setCapability(
						"phantomjs.page.settings.userAgent",
						"Mozilla/5.0 (Windows NT 6.3; Win64; x64; rv:50.0) Gecko/20100101 　　Firefox/50.0");
		desiredCapabilities
				.setCapability(
						"phantomjs.page.customHeaders.User-Agent",
						"Mozilla/5.0 (Windows NT 6.3; Win64; x64; rv:50.0) Gecko/20100101 　　Firefox/50.0");
		// if (Constant.isProxy) {//是否使用代理
		// org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
		// proxy.setProxyType(org.openqa.selenium.Proxy.ProxyType.MANUAL);
		// proxy.setAutodetect(false);
		// String proxyStr = "";
		// do {
		// proxyStr = ProxyUtil.getProxy();// �Զ��庯�������ش���ip���˿�
		// } while (proxyStr.length() == 0);
		// proxy.setHttpProxy(proxyStr);
		// desiredCapabilities.setCapability(CapabilityType.PROXY, proxy);
		// }
		return new PhantomJSDriver(desiredCapabilities);
	}

}
