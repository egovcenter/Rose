package egovframework.com.uss.ion.apv.service;

import java.util.Date;

import egovframework.com.uss.ion.apv.ApprovedDoc;
import egovframework.com.uss.ion.apv.DocImpl;


public class ApprovedDocVO extends DocImpl implements ApprovedDoc {

	/**
	 * 기안일자
	 */
	protected Date draftDateTime;
	/**
	 * 기안부서ID
	 */
	protected String draftDeptID;
	/**
	 * 기안부서명
	 */
	protected String draftDeptName;
	/**
	 * 기안자ID
	 */
	protected String drafterID;
	/**
	 * 기안자명
	 */
	protected String drafterName;
	/**
	 * 결재일자
	 */
	protected Date approvedDateTime;

	public Date getDraftDateTime() {
		return draftDateTime;
	}
	public void setDraftDateTime(Date draftDateTime) {
		this.draftDateTime = draftDateTime;
	}
	public String getDraftDeptID() {
		return draftDeptID;
	}
	public void setDraftDeptID(String draftDeptID) {
		this.draftDeptID = draftDeptID;
	}
	public String getDraftDeptName() {
		return draftDeptName;
	}
	public void setDraftDeptName(String draftDeptName) {
		this.draftDeptName = draftDeptName;
	}
	public String getDrafterID() {
		return drafterID;
	}
	public void setDrafterID(String drafterID) {
		this.drafterID = drafterID;
	}
	public String getDrafterName() {
		return drafterName;
	}
	public void setDrafterName(String drafterName) {
		this.drafterName = drafterName;
	}
	public Date getApprovedDateTime() {
		return approvedDateTime;
	}
	public void setApprovedDateTime(Date approvedDateTime) {
		this.approvedDateTime = approvedDateTime;
	}

}
