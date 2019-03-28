package com.camera;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * @Description 程序启动类
 * @author 张立增
 * @Date 2019�?1�?20�? 下午4:39:59
 */
@ServletComponentScan
@SpringBootApplication
public class StartApplication extends SpringBootServletInitializer{
   
	public static void main( String[] args )
    {
		 SpringApplication.run(StartApplication.class, args);
    }
	
	//为了打包springboot项目
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }
}
