package com.jl.mof;

public class RPT_FTP {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.print(testLogin.login("http://momrpt-ci.wmcloud-qa.com/finbook/v2/tempUser/identity", "user115@datayes.com", "123456"));
		String filePath= "./File/配置户_月度数据统计表_97_2018-01-31.xlsx";
		HttpRequestUtils.sendPostWithFile("http://momrpt-ci.wmcloud-qa.com/finbook/v2/api/report/65058/upload",filePath);

	}
;
}
