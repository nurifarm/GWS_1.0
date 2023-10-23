package com.nurihome.gws.web.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nurihome.gws.util.GsonUtils;
import com.nurihome.gws.util.Utils;
import com.nurihome.gws.web.entity.ModelEntity;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/app")
public class HttpController {
	private static final Logger LOG = LoggerFactory.getLogger(HttpController.class);

	
	/**
	 * <p>creating a "JSON" response</p>
	 */
	private ResponseEntity<String> createContent(final Object o) {
		// ------------------------------------------------------------
		// set http response headers and body content
		// ------------------------------------------------------------
		return ResponseEntity.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.cacheControl(CacheControl.noCache())
			.body(GsonUtils.convertObject2Json(o));
	}
	
	/*
	 * <p> AJAX 서비스 요청 처리</p>
	 */
	@RequestMapping(value = {"/1.0/service/ajax"})
	public ResponseEntity<String> process(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, String> params) {
		
		ModelEntity modelEntity = new ModelEntity();
		
		String serviceId = params.get("serviceId");
		String commandId = params.get("commandId");
		
		// ------------------------------------------------------------
		// check for required parameters
		// ------------------------------------------------------------
		if ((!Utils.isEmpty(serviceId)) && (!Utils.isEmpty(commandId))) {
			// TODO
			
		} else {
			// ------------------------------------------------------------
			// missing required parameters
			// ------------------------------------------------------------
			modelEntity.setError("missing required parameters in the HTTP request");
			
			LOG.info("[DEBUG] missing required parameters in the HTTP request. serviceId={}, commandId={}", serviceId, commandId);
		}
		
		// ------------------------------------------------------------
		// make a JSON response
		// ------------------------------------------------------------
		return createContent(modelEntity);
	}
	
}
