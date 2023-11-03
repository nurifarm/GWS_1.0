package com.nurihome.app.exception;

/**
 * <p>The APIMessageException wraps all unchecked standard java exception with a custom api error message.</p>
 */
@SuppressWarnings("serial")
public class APIMessageException extends RuntimeException {

	/**
	 * <p>API 서비스 요청 처리 에러 메시지</p>
	 */
	private final String errorMessage;

	/**
	 * <p>Constructs a new api service exception with the specified error message.</p>
	 * 
	 * @param errorMessage api error message
	 */
	public APIMessageException(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}

	/**
	 * <p>Constructs a new api service exception with the specified error message and throwable.</p>
	 * 
	 * @param errorMessage api error message
	 * @param cause        Throwable
	 */
	public APIMessageException(String errorMessage, Throwable cause) {
		super(cause);
		this.errorMessage = errorMessage;
	}

	/**
	 * <p>returns the api error message</p>
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

}
