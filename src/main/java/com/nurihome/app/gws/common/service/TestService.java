package com.nurihome.app.gws.common.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.nurihome.app.web.entity.ParamEntity;

/**
 * <p>TestService 클래스</p>
 */
@Service("common.test")
public class TestService {
	
	private static final Logger LOG = LoggerFactory.getLogger(TestService.class);
	
	/**
	 * <p> Service Test Method
	 */
	public Map<String, Object> retrieveTestInfo(ParamEntity paramEntity) {
		Map<String, Object> rs = new HashMap<String, Object>();
		
		rs.put("MSG", "TEST Service Success!");
		
		return rs;
	}
}

