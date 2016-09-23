package egovframework.com.uss.ion.apv;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class DocImpl extends SearchCriteria implements Doc, Serializable {

	private static final long serialVersionUID = 1353942882453124753L;

	/**
	 * 문서ID
	 */
	protected String docID;
	
	/**
	 * 분류태그ID
	 */
	protected String lbelId;
	/**
	 * 제목
	 */
	protected String docTitle;
	/**
	 * 문서번호
	 */
	protected String docCd;
	/**
	 * 보존년한
	 */
	protected String docSyear;
	/**
	 * 보안등급
	 */
	protected String docSlvl;
	/**
	 * 긴급여부
	 */
	protected String docEmF;
	/**
	 * 종이문서여부
	 */
	protected String docPpF;
	/**
	 * 첨부유무
	 */
	protected String docAttaF;
	/**
	 * 의견유무
	 */
	protected String docOpnF;
	/**
	 * 진행상태
	 */
	protected String docPState;
	/**
	 * 완료상태
	 */
	protected String docFState;
	/**
	 * 서식ID
	 */
	protected String formId;
	/**
	 * 원문서ID
	 */
	protected String docOrgId;
	/**
	 * 문서유형
	 */
	protected String docType;
	/**
	 * 기안일자
	 */
	protected Date draftDateTime;
	/**
	 * 도착일자
	 */
	protected Date approvedDateTime;
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
	
	
	public Date getDraftDateTime() {
		return draftDateTime;
	}
	public void setDraftDateTime(Date draftDateTime) {
		this.draftDateTime = draftDateTime;
	}
	public Date getApprovedDateTime() {
		return approvedDateTime;
	}
	public void setApprovedDateTime(Date approvedDateTime) {
		this.approvedDateTime = approvedDateTime;
	}
	public String getDraftDeptId() {
		return draftDeptId;
	}
	public void setDraftDeptId(String draftDeptId) {
		this.draftDeptId = draftDeptId;
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
	public void setDrafterId(String drafterId) {
		this.drafterId = drafterId;
	}
	public String getDrafterName() {
		return drafterName;
	}
	public void setDrafterName(String drafterName) {
		this.drafterName = drafterName;
	}
	public String getDocID() {
		return docID;
	}
	public void setDocID(String docID) {
		this.docID = docID;
	}
	public String getLbelId() {
		return lbelId;
	}
	public void setLbelId(String lbelID) {
		this.lbelId = lbelID;
	}
	public String getDocTitle() {
		return docTitle;
	}
	public void setDocTitle(String docTitle) {
		this.docTitle = docTitle;
	}
	public String getDocCd() {
		return docCd;
	}
	public void setDocCd(String docCd) {
		this.docCd = docCd;
	}
	public String getDocSyear() {
		return docSyear;
	}
	public void setDocSyear(String docSyear) {
		this.docSyear = docSyear;
	}
	public String getDocSlvl() {
		return docSlvl;
	}
	public void setDocSlvl(String docSlvl) {
		this.docSlvl = docSlvl;
	}
	public String getDocEmF() {
		return docEmF;
	}
	public void setDocEmF(String docEmF) {
		this.docEmF = docEmF;
	}
	public String getDocPpF() {
		return docPpF;
	}
	public void setDocPpF(String docPpF) {
		this.docPpF = docPpF;
	}
	public String getDocAttaF() {
		return docAttaF;
	}
	public void setDocAttaF(String docAttaF) {
		this.docAttaF = docAttaF;
	}
	public String getDocOpnF() {
		return docOpnF;
	}
	public void setDocOpnF(String docOpnF) {
		this.docOpnF = docOpnF;
	}
	public String getDocPState() {
		return docPState;
	}
	public void setDocPState(String docPState) {
		this.docPState = docPState;
	}
	public String getDocFState() {
		return docFState;
	}
	public void setDocFState(String docFState) {
		this.docFState = docFState;
	}
	public String getFormId() {
		return formId;
	}
	public void setFormId(String formID) {
		this.formId = formID;
	}
	public String getDocOrgId() {
		return docOrgId;
	}
	public void setDocOrgId(String docOrgID) {
		this.docOrgId = docOrgID;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "DocImpl [docID=" + docID + ", lbelID=" + lbelId + ", docTitle="
				+ docTitle + ", docCd=" + docCd + ", docSyear=" + docSyear
				+ ", docSlvl=" + docSlvl + ", docEmF=" + docEmF + ", docPpF="
				+ docPpF + ", docAttaF=" + docAttaF + ", docOpnF=" + docOpnF
				+ ", docPState=" + docPState + ", docFState=" + docFState
				+ ", formID=" + formId + ", docOrgID=" + docOrgId
				+ ", docType=" + docType + "]";
	}


}
