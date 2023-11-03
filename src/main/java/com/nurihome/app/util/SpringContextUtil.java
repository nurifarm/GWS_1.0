package com.nurihome.app.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * <p>SpringContextUtil 빈 관리 클래스</p>
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

	/**
	 * central interface to provide configuration for an application. (this is read-only while the application is running)
	 */
	private static ApplicationContext applicationContext;

	/**
	 * @see http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/context/ApplicationContext.html
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * @see http://docs.spring.io/spring/docs/current/javadoc-api/index.html?org/springframework/context/ApplicationContextAware.html
	 */
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		applicationContext = context;
	}

	/**
	 * <p>return an instance of the specified bean with the given name.</p>
	 * 
	 * @see http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/beans/factory/ListableBeanFactory.html
	 */
	public static Object getBean(final String name) throws BeansException {
		return applicationContext.getBean(name);
	}

	/**
	 * <p>return an instance of the specified bean with the given class.</p>
	 * 
	 * @see http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/beans/factory/ListableBeanFactory.html
	 */
	public static <T> T getBean(final Class<T> cls) throws BeansException {
		return applicationContext.getBean(cls);
	}

	/**
	 * <p>check if this bean factory contains a bean definition with the given name</p>
	 * 
	 * @see http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/beans/factory/ListableBeanFactory.html
	 */
	public static boolean containsBean(String name) {
		boolean isExist = false;
		
		if (applicationContext.containsBeanDefinition(name)) {
			isExist = true;
		}
		
		return isExist;
	}

}
