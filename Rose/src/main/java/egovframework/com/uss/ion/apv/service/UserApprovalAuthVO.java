package egovframework.com.uss.ion.apv.service;

import java.io.Serializable;

public class UserApprovalAuthVO implements Serializable {

	private static final long serialVersionUID = 8728048427851827905L;
	protected String	userId;			
	protected String	sendAuth;			// 발송 권한 여부
	protected String	receiveAuth;		// 수신 권한 여부	
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userID) {
		this.userId = userID;
	}
	
	public String getSendAuth() {
		return sendAuth;
	}
	
	public void setSendAuth(String sendAuth) {
		this.sendAuth = sendAuth;
	}
	
	public String getReceiveAuth() {
		return receiveAuth;
	}
	
	public void setReceiveAuth(String receiveAuth) {
		this.receiveAuth = receiveAuth;
	}

	
}
