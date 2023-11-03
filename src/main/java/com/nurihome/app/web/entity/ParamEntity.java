package com.nurihome.app.web.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * <p>Helper class for storing request parameters.</p>
 */
@SuppressWarnings("serial")
public class ParamEntity implements Serializable {

	/**
	 * store attach files
	 */
	private Map<String, List<FileEntity>> files;

	/**
	 * store http request parameters
	 */
	private Map<String, String> parameters;
	
	/**
	 * service id
	 */
	private String serviceId;
	
	/**
	 * command id
	 */
	private String commandId;
	
	/**
	 * client IP address
	 */
	private String clientIp;

	/**
	 * Locale
	 */
	private Locale locale;

	/**
	 * <p>Constructs a new ParamEntity</p>
	 */
	public ParamEntity() {
		this.parameters = new HashMap<String, String>();
	}

	/**
	 * <p>Constructs a new ParamEntity with the specified parameters.</p>
	 */
	public ParamEntity(Map<String, String> parameters) {
		if (parameters != null) {
			this.parameters = parameters;	
		}else
		{
			this.parameters = new HashMap<String, String>();
		}
	}

	/**
	 * <p>Constructs a new ParamEntity with the specified parameters and serviceId, commandId.</p>
	 */
	public ParamEntity(Map<String, String> parameters, String serviceId, String commandId) {
		if (parameters != null) {
			this.parameters = parameters;	
		}else
		{
			this.parameters = new HashMap<String, String>();
		}
		
		this.serviceId = serviceId;
		this.commandId = commandId;
	}

	/**
	 * <p>Constructs a new ParamEntity with the specified parameters and serviceId, commandId, clientIp.</p>
	 */
	public ParamEntity(Map<String, String> parameters, String serviceId, String commandId, String clientIp) {
		if (parameters != null) {
			this.parameters = parameters;	
		}else
		{
			this.parameters = new HashMap<String, String>();
		}
		
		this.serviceId = serviceId;
		this.commandId = commandId;
		this.clientIp = clientIp;
	}

	/**
	 * <p>Constructs a new ParamEntity with the specified parameters and serviceId, commandId, clientIp, locale.</p>
	 */
	public ParamEntity(Map<String, String> parameters, String serviceId, String commandId, String clientIp, Locale locale) {
		if (parameters != null) {
			this.parameters = parameters;	
		}else
		{
			this.parameters = new HashMap<String, String>();
		}
		
		this.serviceId = serviceId;
		this.commandId = commandId;
		this.clientIp = clientIp;
		this.locale = locale;
	}

	/**
	 * <p>returns the attachment files</p>
	 */
	public List<FileEntity> getFiles(String name) {
		if ((this.files != null) && (this.files.containsKey(name))) {
			return this.files.get(name);
		}else
		{
			return new ArrayList<FileEntity>();
		}
	}

	/**
	 * <p>returns the attachment file</p>
	 */
	public FileEntity getFile(String name) {
		FileEntity fileEntity = null;
		
		if (this.files != null) {
			if (this.files.containsKey(name)) {
				if (this.files.get(name).size() != 0) {
					fileEntity = this.files.get(name).get(0);
				}
			}			
		}

		return fileEntity;
	}

	/**
	 * <p>add attachment file</p>
	 */
	public void addFile(String name, FileEntity file) {
		if (name != null) {
			if (this.files == null) {
				this.files = new HashMap<String, List<FileEntity>>();
			}

			if (!this.files.containsKey(name)) {
				this.files.put(name, new ArrayList<FileEntity>());
			}
			
			this.files.get(name).add(file);
		}
	}

	/**
	 * <p>returns the http request parameters</p>
	 */
	public Map<String, String> getParameters() {
		return this.parameters;
	}

	/**
	 * <p>returns the value of a request parameter as a key, or "" if the parameter does not exist.</p>
	 */
	public String getParameter(String key) {
		if (parameters.containsKey(key)) {
			return parameters.get(key);
		}else
		{
			return "";
		}
	}

	/**
	 * <p>sets the http request parameters</p>
	 */
	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

	/**
	 * <p>sets the specified value with the specified key in parameters map</p>
	 */
	public void setParameter(String key, String value) {
		if ((key != null) && (value != null)) {
			parameters.put(key, value);
		}
	}

	/**
	 * <p>returns the service id</p>
	 */
	public String getServiceId() {
		return serviceId;
	}
	
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	/**
	 * <p>returns the command id</p>
	 */
	public String getCommandId() {
		return commandId;
	}

	public void setCommandId(String commandId) {
		this.commandId = commandId;
	}

	/**
	 * <p>returns the client ip</p>
	 */
	public String getClientIp() {
		if (clientIp != null) {
			return clientIp;
		}

		return "";
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	/**
	 * <p>returns the locale</p>
	 */
	public Locale getLocale() {
		if (locale != null) {
			return locale;
		}
		
		return Locale.getDefault();
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	/**
	 * <p>returns the language</p>
	 */
	public String getLanguage() {
		if (locale != null) {
			return locale.getLanguage().toUpperCase();
		}
		
		return Locale.getDefault().getLanguage().toUpperCase();
	}


	@Override
	public String toString() {
		// ------------------------------------------------------------
		// print all http request parameters
		// ------------------------------------------------------------
		StringBuilder sb = new StringBuilder();

		sb.append("serviceId=");
		sb.append(this.serviceId);
		sb.append(", commandId=");
		sb.append(this.commandId);
		sb.append(", parameters=");
		sb.append(this.parameters.toString());

		return sb.toString();
	}

}
