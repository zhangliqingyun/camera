package com.camera.utils;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description 读取properties配置文件信息的方法
 * @author 张立增
 * @Date 2019年3月28日 上午11:36:29
 */
@Component
public class PropertyMessage {

	public static String host;
	public static String appKey;
	public static String appSecret;
	
	@Value("${host}")  
	private String hostTemp;
	
	@Value("${appKey}")  
	private String appKeyTemp;
	
	@Value("${appSecret}")  
	private String appSecretTemp;
	
	@PostConstruct  
	public void init() {  
		host = hostTemp;
		appKey = appKeyTemp;
		appSecret = appSecretTemp;
	}
	
}
