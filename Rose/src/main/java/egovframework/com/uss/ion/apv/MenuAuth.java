package egovframework.com.uss.ion.apv;

public interface MenuAuth extends Auth {

	/**
	 * 사용자ID
	 */
	public String getUserID();
	/**
	 * 사용자ID
	 */
	public void setUserID(String userID);
	/**
	 * 시스템 관리자 여부
	 */
	public String getSystemAdmin();
	/**
	 * 시스템 관리자 여부
	 */
	public void setSystemAdmin(String systemAdmin);

}
