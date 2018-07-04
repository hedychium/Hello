package com.peijie.servlet;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServletConfiguration {//方法一
    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        return new ServletRegistrationBean(new TsetServlet(),"/test");
    }

}

/*@Configuration
@ServletComponentScan
public class ServletConfiguration { //方法二

}*/