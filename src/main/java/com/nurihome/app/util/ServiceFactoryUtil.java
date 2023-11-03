package com.nurihome.app.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nurihome.app.exception.APIMessageException;
import com.nurihome.app.exception.ServiceException;
import com.nurihome.app.web.entity.ModelEntity;
import com.nurihome.app.web.entity.ParamEntity;

public final class ServiceFactoryUtil {

	private static final Logger LOG = LoggerFactory.getLogger(ServiceFactoryUtil.class);

	/**
	 * <p>private constructor</p>
	 */
	private ServiceFactoryUtil() {}


	/**
	 * <p>return a instance of the specified bean with the given service id</p>
	 * 
	 * <blockquote>
	 * <pre>{@code
	 * Usage:
	 *     Object service = ServiceFactoryUtil.serviceLookup("serviceId");
	 * }</pre>
	 * </blockquote>
	 */
	private static Object serviceLookup(final String serviceId) {
		return ServiceFactory.getService(serviceId);
	}

	/**
	 * <p>returns a Method object that reflects the specified declared method of the class</p>
	 * 
	 * <blockquote>
	 * <pre>{@code
	 * Usage:
	 *     Method command = ServiceFactoryUtil.commandLookup(Object instance, "commandId", Class<T>);
	 * }</pre>
	 * </blockquote>
	 */
	private static Method commandLookup(final Object serviceInstance, final String commandId, Class<?>... parameterTypes) {
		try {
			return serviceInstance.getClass().getDeclaredMethod(commandId, parameterTypes);
		}catch (NoSuchMethodException | SecurityException ignore)
		{
			//--- nothing
		}

		return null;
	}

	public static void invokeCommand(final ParamEntity paramEntity, ModelEntity modelEntity, Class<?>... parameterTypes) {
		String serviceId = paramEntity.getServiceId();
		String commandId = paramEntity.getCommandId();

		Object service = serviceLookup(serviceId);
		
		if (service != null) {
			Method command = commandLookup(service, commandId, parameterTypes);
			
			if (command != null) {
				try {
					modelEntity.setData(command.invoke(service, paramEntity));
				}catch (IllegalAccessException | IllegalArgumentException e)
				{
					LOG.error("[ServiceFactoryUtil] serviceId={}, commandId={}, msg={}", serviceId, commandId, e);
					
					modelEntity.setError("error processing the request");
				}catch (InvocationTargetException e)
				{
					Throwable t = e.getCause();
					
					if (t instanceof ServiceException) {
						ServiceException se = (ServiceException) t;
						
						if (t.getCause() != null) {
							LOG.error("[ServiceFactoryUtil] serviceId={}, commandId={}, msg={}, error={}", serviceId, commandId, se.getErrorMessage(), e);
						}else
						{
							LOG.info("[ServiceFactoryUtil] serviceId={}, commandId={}, msg={}", serviceId, commandId, se.getErrorMessage());
						}
						
						modelEntity.setError(se.getErrorMessage());
					}else if (t instanceof APIMessageException)
					{
						APIMessageException ae = (APIMessageException) t;
						
						modelEntity.setApiMessage(ae.getErrorMessage());
					}else
					{
						LOG.error("[ServiceFactoryUtil] serviceId={}, commandId={}, msg={}", serviceId, commandId, e);
						
						modelEntity.setError("error processing the request");
					}
				}
			}else
			{
				// ------------------------------------------------------------
				// error obtaining declared method - no such method named or invalid argument
				// ------------------------------------------------------------
				modelEntity.setError("invalid parameter value. check the commandId");
			}
		}else
		{
			// ------------------------------------------------------------
			// error obtaining bean instance
			// ------------------------------------------------------------
			LOG.info("no service bean named is defined or could not find service bean. check the serviceId. serviceId={}, commandId={}", serviceId, commandId);
			
			modelEntity.setError("invalid parameter value. check the serviceId");
		}
	}

}