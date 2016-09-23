package egovframework.com.uss.ion.apv.service;

import java.util.Date;

import egovframework.com.uss.ion.apv.DocImpl;
import egovframework.com.uss.ion.apv.OutboxDoc;

public class OutboxDocVO extends DocImpl implements OutboxDoc {

	/**
	 * 기안일자
	 */
	protected Date draftDateTime;
	/**
	 * 기안부서ID
	 */
	protected String draftDeptId;
	/**
	 * 기안부서명
	 */
	protected String draftDeptName;
	/**
	 * 기안자ID
	 */
	protected String drafterId;
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
	public String getDraftDeptId() {
		return draftDeptId;
	}
	public void setDraftDeptId(String draftDeptID) {
		this.draftDeptId = draftDeptID;
	}
	public String getDraftDeptName() {
		return draftDeptName;
	}
	public void setDraftDeptName(String draftDeptName) {
		this.draftDeptName = draftDeptName;
	}
	public String getDrafterId() {
		return drafterId;
	}
	public void setDrafterId(String drafterID) {
		this.drafterId = drafterID;
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
