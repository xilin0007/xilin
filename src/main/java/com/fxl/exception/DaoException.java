package com.fxl.exception;

public class DaoException extends Exception {
	private static final long serialVersionUID = 7696590695741706297L;
	private String errorMessage = null;

	private String errorTitle = null;

	private int errorType = 0;

	public DaoException() {
		this.errorMessage = "DAO layer is Error!!";
	}

	public DaoException(int errorType, String errorTitle) {
		super(errorTitle);
		this.errorTitle = errorTitle;
		this.errorMessage = errorTitle;
		this.errorType = errorType;
	}

	public DaoException(String argMessage) {
		super(argMessage);
		this.errorMessage = argMessage;
	}

	public DaoException(int errorType, String argMessage, Throwable argThr) {
		super(argMessage, argThr);
		this.errorType = errorType;
	}

	public DaoException(String argMessage, Throwable argThr) {
		super(argMessage, argThr);
	}

	public DaoException(Throwable argThr) {
		super(argThr);
		this.errorMessage = argThr.getLocalizedMessage();
	}

	public String getErrorMessage() {
		return this.errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorTitle() {
		return this.errorTitle;
	}

	public void setErrorTitle(String errorTitle) {
		this.errorTitle = errorTitle;
	}

	public int getErrorType() {
		return this.errorType;
	}

	public void setErrorType(int errorType) {
		this.errorType = errorType;
	}

	@Override
	public String toString() {
		StringBuffer sqlString = new StringBuffer("【DAO异常信息:】\n");
		sqlString
				.append("****************************************DaoException Start****************************************\n");
		sqlString.append(this.errorMessage);
		sqlString
				.append("\n****************************************DaoException End****************************************");
		return sqlString.toString();
	}
}