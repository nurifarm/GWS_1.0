package com.nurihome.app.web.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * <p>Helper class for storing response data.</p>
 */
@SuppressWarnings("serial")
public class ModelEntity implements Serializable {

	/**
	 * api response message
	 */
	private Map<String, String> api;

	/**
	 * response message
	 */
	private String message;
	
	/**
	 * exception message
	 */
	private String stackTrace;

	/**
	 * response data
	 */
	private Object rs;

	/**
	 * default constructor
	 */
	public ModelEntity() {
		this.api = new HashMap<String, String>();
		this.message = "OK";
	}

	public String getApiMessage() {
		if (api.containsKey("message")) {
			return api.get("message");
		}
		
		return "";
	}

	public void setApiMessage(String message) {
		api.put("message", message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}

	public Object getData() {
		return rs;
	}

	public void setData(Object data) {
		this.rs = data;
	}

	public void setError(String message) {
		this.message = message;
	}

	public void setError(String message, String stackTrace) {
		this.message = message;
		this.stackTrace = stackTrace;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("message=");
		sb.append(this.message);
		sb.append(", stackTrace=");
		sb.append(this.stackTrace);

		return sb.toString();
	}

}
