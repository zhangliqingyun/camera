package com.demo;

import com.alibaba.fastjson.JSONObject;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ArtemisPostTest {
	/**
	 * 请根据自己的appKey和appSecret更换static静态块中的三个参数. [1 host]
	 * 如果你选择的是同open8200对接,也就是说非现场环境,海康总部的演示环境,host不用修改.默认为open8200.hikvision.com
	 * 如果你选择的是同现场环境对接,host要修改为现场环境的ip,网关1.0 时，端口默认为9999.网关2.0 时，端口默认为443.例如:10.33.25.22:9999 或者10.33.25.22:443 [2 appKey和appSecret]
	 * 请按照或得到的appKey和appSecret更改.
	 *
	 * ps. 如果同海康open8200的演示环境对接成功后,切换到现场环境,请按照现场情况,更换三个参数.
	 *
	 * TODO 调用前看清接口传入的是什么，是传入json就用doPostStringArtemis方法，是表单提交就用doPostFromArtemis方法,下载图片doPostStringImgArtemis方法
	 *
	 */
	static {
		ArtemisConfig.host ="open8200.hikvision.com"; // 代理API网关nginx服务器ip端口
		ArtemisConfig.appKey ="24747926";  // 秘钥appkey
		ArtemisConfig.appSecret ="mcsioUGkT5GRMZTvjwA";// 秘钥appSecret

	}
	/**
	 * 能力开放平台的网站路径
	 * TODO 路径不用修改，就是/artemis
	 */
	private static final String ARTEMIS_PATH = "/artemis";

	/**
	 * 调用post请求表单类型的接口,这里以简单的加法接口示例
	 *
	 * @return
	 */
	public static String callPostFormApi() {
		/**
		 * https://open8200.hikvision.com/artemis-portal/document?version=58&docId=278&apiBlock=1
		 * 根据API文档可以看出来,这是一个POST请求的Rest接口, 而且传入的参数值为是一个form值.
		 * ArtemisHttpUtil工具类提供了doPostFormArtemis这个函数, 一共六个参数在文档里写明其中的意思. 因为接口是https,
		 * 所以第一个参数path是个hashmap类型,请put一个key-value, querys为传入的参数.
		 * paramMap 为form的参数值.
		 * queryString不存在,所以传入null,accept和contentType不指定按照默认传null.
		 * header没有额外参数可不传,指定为null
		 */
		String getCamsApi = ARTEMIS_PATH + "/api/artemis/v1/plus";
		Map<String, String> paramMap = new HashMap<String, String>();// post请求Form表单参数
		paramMap.put("a", "3");
		paramMap.put("b", "2");
		Map<String, String> path = new HashMap<String, String>(2) {
			{
				put("https://", ARTEMIS_PATH + "/api/artemis/v1/plus");
			}
		};
		String result = ArtemisHttpUtil.doPostFormArtemis(path, paramMap, null, null, null,null);
		return result;
	}

	/**
	 * 调用POST请求类型(application/json)接口，这里以查询过车数据为例
	 * https://open8200.hikvision.com/artemis-portal/document?version=4&docId=290&apiBlock=10
	 *
	 * @return
	 */
	public static String callPostStringApi(){
		/**
		 * https://open8200.hikvision.com/artemis-portal/document?version=4&docId=290&apiBlock=10
		 * 根据API文档可以看出来，这是一个POST请求的Rest接口，而且传入的参数值为一个json和query
		 * ArtemisHttpUtil工具类提供了doPostStringArtemis这个函数，一共六个参数在文档里写明其中的意思，因为接口是https，
		 * 所以第一个参数path是一个hashmap类型，请put一个key-value，query为传入的参数，body为传入的json数据
		 * 传入的contentType为application/json，accept不指定为null
		 * header没有额外参数可不传,指定为null
		 *
		 */
		final String VechicleDataApi = ARTEMIS_PATH +"/api/fms/v3/human/findSnapHuman";
		Map<String,String> path = new HashMap<String,String>(2){
			{
				put("https://",VechicleDataApi);
			}
		};

		JSONObject jsonBody = new JSONObject();
		jsonBody.put("deviceCodes","-1");
		jsonBody.put("sex",-1);
		jsonBody.put("beginDate","2017-09-01 12:00:00");
		jsonBody.put("endDate","2017-09-12 12:00:00");
		jsonBody.put("similarityMin",0.1);
		jsonBody.put("similarityMax",1);
		jsonBody.put("picUrl", "http://10.33.44.61:6501/pic?=d82i649e*4ebi05f-646618-52c3ca0764e4cai5b1*=1d6s4*=6dpi*=1d3i4t2pe*m5i13=-1050e3-10z1cas=630bd1");
		jsonBody.put("pageNo",1);
		jsonBody.put("pageSize",12);
		JSONObject jsonBody1 = new JSONObject();
		jsonBody1.put("height","0.981481");
		jsonBody1.put("width","1");
		jsonBody1.put("x","0");
		jsonBody1.put("y","0.018519");
		jsonBody.put("recommendFaceRect",jsonBody1);

		String body = jsonBody.toJSONString();
		String body1 = jsonBody.toString();
		System.out.println("body: "+body);
		System.out.println("bod1: "+body1);

		String result =ArtemisHttpUtil.doPostStringArtemis(path,body,null,null,"application/json",null);// post请求application/json类型参数
		return result;
	}

	/**
	 * 调用POST请求下载图片类型接口，这里以提取人员图片接口为例
	 * https://10.40.239.202/artemis-web/debug/100
	 *
	 * @return
	 */
	public static void callPostImgStringApi(){
		/**
		 * https://10.40.239.202/artemis-web/debug/100
		 * 根据API文档可以看出来，这是一个POST请求的Rest接口，而且传入的参数值为一个json
		 * ArtemisHttpUtil工具类提供了doPostStringImgArtemis这个函数，一共六个参数在文档里写明其中的意思，因为接口是https，
		 * 所以第一个参数path是一个hashmap类型，请put一个key-value，query为传入的参数，body为传入的json数据
		 * 传入的contentType为application/json，accept不指定为null
		 * header没有额外参数可不传,指定为null
		 *
		 */
		final String VechicleDataApi = ARTEMIS_PATH +"/api/acs/v1/event/pictures";
		Map<String,String> path = new HashMap<String,String>(2){
			{
				put("https://",VechicleDataApi);
			}
		};

		JSONObject jsonBody = new JSONObject();
		jsonBody.put("svrIndexCode", "30a80833-9ced-46c0-bf53-441a6121856e");
		jsonBody.put("picUri", "/pic?6dd599z4a-=s72e48118f119--7037797c5e819i0b2*=ids1*=idp3*=*d3i0t=pe4m5115-726ccd4*ef8bi12i73=");
		String body = jsonBody.toJSONString();
		System.out.println("body: "+body);
		HttpResponse result =ArtemisHttpUtil.doPostStringImgArtemis(path,body,null,null,"application/json",null);
		try {
			HttpResponse resp = result;
			if (200==resp.getStatusLine().getStatusCode()) {
				HttpEntity entity = resp.getEntity();
				InputStream in = entity.getContent();
				Tools.savePicToDisk(in, "d:/", "test3.jpg");
			}else{
				System.out.println("下载出错");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	public static void main(String[] args) {
		String fomeResult =callPostFormApi();
		System.out.println("fomeResult结果示例: "+fomeResult);
		String StringeResult = callPostStringApi();
		System.out.println("StringeResult结果示例: "+StringeResult);
		callPostImgStringApi();
	}*/
}
