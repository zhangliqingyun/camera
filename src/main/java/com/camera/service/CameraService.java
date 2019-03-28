package com.camera.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.camera.utils.PropertyMessage;

/**
 * @Description 视频类的service层
 * @author 张立增
 * @Date 2019年3月28日 上午11:30:57
 */
@Service
public class CameraService {
     
	
	public String findDataByRoadCode(String roadCode) {
		return "{host:"+PropertyMessage.host+",appKey:"+PropertyMessage.appKey+",appSecret:"+PropertyMessage.appSecret+"}";
	}

	
}
