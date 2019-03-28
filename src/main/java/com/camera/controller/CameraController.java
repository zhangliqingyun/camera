package com.camera.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.camera.service.CameraService;

/**
 * @Description 图像控制层
 * @author 张立增
 * @Date 2019年3月28日 上午10:20:23
 */
@RestController
@RequestMapping("/camera")
public class CameraController {
	
	@Autowired
	CameraService cameraService;

	@RequestMapping(value = "/updateData",produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
	public Object updateData(HttpServletRequest request,HttpServletResponse response){  
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");   
			String roadCode = request.getParameter("roadCode");   
			System.out.println("roadCode="+roadCode);    
			String callback = request.getParameter("callback");        
			String message = cameraService.findDataByRoadCode(roadCode);
			//MappingJacksonValue在spring4.1后出的，它将数据封装，并可以设置sjonp返回函数。
			MappingJacksonValue mappingJacksonValue=new MappingJacksonValue(message);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;        
	    } catch (Exception e) {  
	        return "500";  
	    }  
	}
	
	
	
}
