package com.dlpauth.model;

import com.dlpauth.exception.ErrorDetails;

public class DlpauthOutputResult {

	private Object data;

	private ErrorDetails errorInfo;

	private String status;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public ErrorDetails getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(ErrorDetails errorInfo) {
		this.errorInfo = errorInfo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "DlpauthOutputResult [data=" + data + ", errorInfo=" + errorInfo + ", status=" + status + "]";
	}

}
