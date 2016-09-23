package egovframework.com.uss.ion.apv.service;

import java.io.Serializable;

public class DeptApprovalAuthVO implements Serializable {
	
	private static final long serialVersionUID = -2841317005725686336L;
	
	protected String	deptId;		
	protected String	outboxAuth = "0";		// 발신함
	protected String	inboxAuth = "0";		// 수신함
	
	public String getDeptId() {
		return deptId;
	}
	
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
	public String getOutboxAuth() {
		return outboxAuth;
	}
	
	public void setOutboxAuth(String outboxAuth) {
		this.outboxAuth = outboxAuth;
	}
	
	public String getInboxAuth() {
		return inboxAuth;
	}
	
	public void setInboxAuth(String inboxAuth) {
		this.inboxAuth = inboxAuth;
	}
}
