package egovframework.com.uss.ion.apv;

import java.util.Date;

public interface WaitingDoc extends Doc{

	/**
	 * 기안일자
	 */
	public Date getDraftDateTime();
	
	/**
	 * 기안일자
	 */
	public void setDraftDateTime(Date draftDateTime);
	
	/**
	 * 기안부서ID
	 */
	public String getDraftDeptID();
	
	/**
	 * 기안부서ID
	 */
	public void setDraftDeptID(String draftDeptID);
	
	/**
	 * 기안부서명
	 */
	public String getDraftDeptName();
	
	/**
	 * 기안부서명
	 */
	public void setDraftDeptName(String draftDeptName);
	
	/**
	 * 기안자ID
	 */
	public String getDrafterID();
	
	/**
	 * 기안자ID
	 */
	public void setDrafterID(String drafterID);
	
	/**
	 * 기안자명
	 */
	public String getDrafterName();
	
	/**
	 * 기안자명
	 */
	public void setDrafterName(String drafterName);
	
	public abstract String toString();

}
