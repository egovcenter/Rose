package egovframework.com.uss.ion.apv;

import java.util.Date;

public interface IncomingDoc extends Doc{

	/**
	 * 도착일자(등록일자)
	 */
	public Date getArrivalDateTime();
	/**
	 * 도착일자(등록일자)
	 */
	public void setArrivalDateTime(Date arrivalDateTime);
	/**
	 * 발송자ID
	 */
	public String getSenderUserId();
	/**
	 * 발송자ID
	 */
	public void setSenderUserId(String senderUserID);
	/**
	 * 발송자명
	 */
	public String getSenderUserName();
	/**
	 * 발송자명
	 */
	public void setSenderUserName(String senderUserName);
	/**
	 * 접수일시
	 */
	public Date getRecpRecpDt();
	/**
	 * 접수일시
	 */
	public void setRecpRecpDt(Date recpRecpDt);

	/**
	 * 접수자ID
	 */
	public String getRecpRecpUserId();
	/**
	 * 접수자ID
	 */
	public void setRecpRecpUserId(String recpRecpUserID);
	
	/**
	 * 접수자명
	 */
	public String getRecpRecpUserNm();
	
	/**
	 * 접수자명
	 */
	public void setRecpRecpUserNm(String recpRecpUserNm);
	
	/**
	 * 수신문서ID
	 */
	public String getRecpRecpDocId();
	
	/**
	 * 수신문서ID
	 */
	public void setRecpRecpDocId(String recpRecpDocId);

	public String toString();
}
