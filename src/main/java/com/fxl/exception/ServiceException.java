package com.fxl.exception;

public class ServiceException extends Exception {
	private static final long serialVersionUID = 7696590695741706297L;
	private String errorMessage = null;

	private String errorTitle = null;

	private int errorType = 0;

	private ExcpMessage excpMessage = null;

	public ServiceException() {
		this.errorMessage = "Service layer is Error!!";
	}

	public ServiceException(String argMessage) {
		super(argMessage);
		this.errorMessage = argMessage;
	}

	public ServiceException(int errorType, String errorTitle) {
		super(errorTitle);
		this.errorTitle = errorTitle;
		this.errorMessage = errorTitle;
		this.errorType = errorType;
	}

	public ServiceException(ExcpMessage excpMessage) {
		super(excpMessage.getMsg());
		this.errorTitle = excpMessage.getMsg();
		this.errorMessage = excpMessage.getMsg();
		this.errorType = excpMessage.getCode().intValue();
		this.excpMessage = excpMessage;
	}

	public ServiceException(int errorType, String errorTitle, String argMessage) {
		super(argMessage);
		this.errorMessage = argMessage;
		this.errorType = errorType;
		this.errorTitle = errorTitle;
	}

	public ServiceException(String argMessage, Throwable argThr) {
		super(argMessage, argThr);
	}

	public ServiceException(int errorType, String argMessage, Throwable argThr) {
		super(argMessage, argThr);
		this.errorType = errorType;
	}

	public ServiceException(int errorType, String errorTitle,
			String argMessage, Throwable argThr) {
		super(argMessage, argThr);
		this.errorType = errorType;
		this.errorTitle = errorTitle;
	}

	public ServiceException(Throwable argThr) {
		super(argThr);
		this.errorMessage = argThr.getLocalizedMessage();
	}

	@Override
	public String toString() {
		StringBuffer sqlString = new StringBuffer("【SERVER异常信息:】\n");
		sqlString
				.append("****************************************ServiceException Start****************************************\n");
		sqlString.append(this.errorMessage);
		sqlString
				.append("\n****************************************ServiceException End****************************************");
		return sqlString.toString();
	}

	public int getErrorType() {
		return this.errorType;
	}

	public String getErrorMessage() {
		return this.errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void setErrorType(int errorType) {
		this.errorType = errorType;
	}

	public String getErrorTitle() {
		return this.errorTitle;
	}

	public void setErrorTitle(String errorTitle) {
		this.errorTitle = errorTitle;
	}

	public ExcpMessage getExcpMessage() {
		return this.excpMessage;
	}

	public void setExcpMessage(ExcpMessage excpMessage) {
		this.excpMessage = excpMessage;
	}
}