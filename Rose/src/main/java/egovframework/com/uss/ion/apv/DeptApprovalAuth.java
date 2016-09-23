package egovframework.com.uss.ion.apv;

public interface DeptApprovalAuth extends Auth {

	/**
	 * 부서ID 
	 */
	public String getDeptID();
	/**
	 * 부서ID 
	 */
	public void setDeptID(String deptId);
	/**
	 * 발신함 보유 여부
	 * @return 발신함 보유 여부
	 * <br/>&nbsp;&nbsp;&nbsp;1 - 발신함 보유 
	 * <br/>&nbsp;&nbsp;&nbsp;0 - 발신함 미보유
	 */
	public String getOutboxAuth();
	/**
	 * 발신함 보유 여부
	 * @param outboxAuth 발신함 보유 여부
	 * <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1 - 발신함 보유 
	 * <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;0 - 발신함 미보유
	 */
	public void setOutboxAuth(String outboxAuth);
	/**
	 * 수신함 보유 여부
	 * @return 수신함 보유 여부
	 * <br/>&nbsp;&nbsp;&nbsp;1 - 수신함 보유 
	 * <br/>&nbsp;&nbsp;&nbsp;0 - 수신함 미보유
	 */
	public String getInboxAuth();
	/**
	 * 수신함 보유 여부
	 * @param inboxAuth 수신함 보유 여부
	 * <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1 - 수신함 보유 
	 * <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;0 - 수신함 미보유
	 */
	public void setInboxAuth(String inboxAuth);

}
