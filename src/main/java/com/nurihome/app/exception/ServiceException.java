package com.nurihome.app.exception;

/**
 * <p>The ServiceException wraps all unchecked standard java exception with a custom error message.</p>
 */
@SuppressWarnings("serial")
public class ServiceException extends RuntimeException {

	/**
	 * <p>서비스 요청 처리 에러 메시지</p>
	 */
	private final String errorMessage;

	/**
	 * <p>Constructs a new service exception with the specified error message.</p>
	 * 
	 * @param errorMessage error message
	 */
	public ServiceException(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}

	/**
	 * <p>Constructs a new service exception with the specified error message and throwable.</p>
	 * 
	 * @param errorMessage error message
	 * @param cause        Throwable
	 */
	public ServiceException(String errorMessage, Throwable cause) {
		super(cause);
		this.errorMessage = errorMessage;
	}

	/**
	 * <p>returns the error message</p>
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

}