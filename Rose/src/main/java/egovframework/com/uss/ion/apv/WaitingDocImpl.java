package egovframework.com.uss.ion.apv;

import java.sql.Timestamp;
import java.util.Date;

public class WaitingDocImpl extends DocImpl implements WaitingDoc {

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

	public Date getDraftDateTime() {
		return draftDateTime;
	}
	public void setDraftDateTime(Date draftDateTime) {
		
		this.draftDateTime = draftDateTime;
	}
	public void setDraftDateTime(Timestamp draftDateTime) {
		
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

	@Override
	public String toString() {
		return "DocImpl [docID=" + docID + ", lbelID=" + lbelId + ", docTitle="
				+ docTitle + ", docCd=" + docCd + ", docSyear=" + docSyear
				+ ", docSlvl=" + docSlvl + ", docEmF=" + docEmF + ", docPpF="
				+ docPpF + ", docAttaF=" + docAttaF + ", docOpnF=" + docOpnF
				+ ", docPState=" + docPState + ", docFState=" + docFState
				+ ", formID=" + formId + ", docOrgID=" + docOrgId
				+ ", docType=" + docType + ", draftDateTime=" + draftDateTime
				+ ", draftDeptID=" + draftDeptID + ", draftDeptName="
				+ draftDeptName + ", drafterID=" + drafterID + ", drafterName="
				+ drafterName + "]";
	}

}
