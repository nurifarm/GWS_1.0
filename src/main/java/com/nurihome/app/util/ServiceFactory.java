package com.nurihome.app.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Classes for service dispatching in the spring application context.</p>
 */
public class ServiceFactory {
	
	private static final Logger LOG = LoggerFactory.getLogger(ServiceFactory.class);

	/**
	 * <p>private constructor</p>
	 */
	private ServiceFactory() {}
	
	/**
	 * <p>return an instance of the specified bean with the given Spring @Service annotation name</p>
	 * 
	 * <blockquote>
	 * <pre>{@code
	 * Usage:
	 *     Foo service = (Foo) ServiceFactory.getService("Spring @Service annotation name");
	 * }</pre>
	 * </blockquote>
	 */
	public static Object getService(final String name) {
		Object serviceInstance = null;
		
		try {
			if (SpringContextUtil.containsBean(name)) {
				serviceInstance = SpringContextUtil.getBean(name);
			}
		}catch (Exception e)
		{
			LOG.error("[ServiceFactory] {}", e);
		}

		return serviceInstance;
	}
	
	/**
	 * <p>check if a bean definition with the given Spring @Service annotation name</p>
	 */
	public static boolean exists(final String name) {
		return SpringContextUtil.containsBean(name);
	}
}
