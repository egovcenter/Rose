package egovframework.com.uss.ion.apv;

import java.util.Date;

public interface InboxDoc extends Doc {
	
	/**
	 * 기안일자
	 * @return Date({@link java.util.Date}) 
	 */
	public Date getDraftDateTime();

	/**
	 * 기안일자
	 * @param draftDateTime
	 */
	public void setDraftDateTime(Date draftDateTime);
	
	/**
	 * 기안부서ID
	 * @return
	 */
	public String getDraftDeptId();
	
	/**
	 * 기안부서ID
	 * @param draftDeptID
	 */
	public void setDraftDeptId(String draftDeptID);
	
	/**
	 * 기안부서명
	 * @return
	 */
	public String getDraftDeptName();
	
	/**
	 * 기안부서명
	 * @param draftDeptName
	 */
	public void setDraftDeptName(String draftDeptName);
	
	/**
	 * 기안자ID
	 * @return
	 */
	public String getDrafterId();
	
	/**
	 * 기안자ID
	 * @param drafterID
	 */
	public void setDrafterId(String drafterID);
	
	/**
	 * 기안자명
	 * @return
	 */
	public String getDrafterName();
	
	/**
	 * 기안자명
	 * @param drafterName
	 */
	public void setDrafterName(String drafterName);

	/**
	 * 결재일자
	 * @return Date({@link java.util.Date}) 
	 */
	public Date getApprovedDateTime();

	/**
	 * 결재일자
	 * @param draftDateTime
	 */
	public void setApprovedDateTime(Date approvedDateTime);
	
	/**
	 * 발신자명
	 * @return
	 */
	public String getSenderName();
	
	/**
	 * 발신자명
	 * @param drafterName
	 */
	public void setSenderName(String senderName);


}
