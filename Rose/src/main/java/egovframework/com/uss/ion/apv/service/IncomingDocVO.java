package egovframework.com.uss.ion.apv.service;

import java.util.Date;

import egovframework.com.uss.ion.apv.DocImpl;
import egovframework.com.uss.ion.apv.IncomingDoc;


public class IncomingDocVO extends DocImpl implements IncomingDoc {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3978032824968607086L;
	/**
	 * 도착일자(등록일자)
	 */
	protected Date arrivalDateTime;
	/**
	 * 발송자ID
	 */
	protected String senderUserId;
	/**
	 * 발송자명
	 */
	protected String senderUserName;
	/**
	 * 접수일시
	 */
	protected Date recpRecpDt; //recp_recp_dt

	/**
	 * 접수자ID
	 */
	protected String recpRecpUserId; //recp_r_user_id
	
	/**
	 * 접수자명
	 */
	protected String recpRecpUserNm; //recp_r_user_nm
	/**
	 * 수신문서ID
	 */
	protected String recpRecpDocId; // none
	
	
	public Date getArrivalDateTime() {
		return arrivalDateTime;
	}
	public void setArrivalDateTime(Date arrivalDateTime) {
		this.arrivalDateTime = arrivalDateTime;
	}
	public String getSenderUserId() {
		return senderUserId;
	}
	public void setSenderUserId(String senderUserID) {
		this.senderUserId = senderUserID;
	}
	public String getSenderUserName() {
		return senderUserName;
	}
	public void setSenderUserName(String senderUserName) {
		this.senderUserName = senderUserName;
	}
	public Date getRecpRecpDt() {
		return recpRecpDt;
	}
	public void setRecpRecpDt(Date recpRecpDt) {
		this.recpRecpDt = recpRecpDt;
	}
	public String getRecpRecpUserId() {
		return recpRecpUserId;
	}
	public void setRecpRecpUserId(String recpRecpUserID) {
		this.recpRecpUserId = recpRecpUserID;
	}
	public String getRecpRecpUserNm() {
		return recpRecpUserNm;
	}
	public void setRecpRecpUserNm(String recpRecpUserNm) {
		this.recpRecpUserNm = recpRecpUserNm;
	}
	/**
	 * 수신문서ID
	 */
	public String getRecpRecpDocId(){
		return recpRecpDocId;
	}
	
	/**
	 * 수신문서ID
	 */
	public void setRecpRecpDocId(String recpRecpDocID){
		this.recpRecpDocId = recpRecpDocID;
	}		

	@Override
	public String toString() {
		return "IncomingDocImpl [arrivalDateTime=" + arrivalDateTime
				+ ", senderUserID=" + senderUserId + ", senderUserName="
				+ senderUserName + ", recpRecpDt=" + recpRecpDt
				+ ", recpRecpUserID=" + recpRecpUserId + ", recpRecpUserNm="
				+ recpRecpUserNm + ",recpRecpDocID=" + recpRecpDocId + ", docID=" + docID + ", lbelID=" + lbelId
				+ ", docTitle=" + docTitle + ", docCd=" + docCd + ", docSyear="
				+ docSyear + ", docSlvl=" + docSlvl + ", docEmF=" + docEmF
				+ ", docPpF=" + docPpF + ", docAttaF=" + docAttaF
				+ ", docOpnF=" + docOpnF + ", docPState=" + docPState
				+ ", docFState=" + docFState + ", formID=" + formId
				+ ", docOrgID=" + docOrgId + ", docType=" + docType + "]";
	}

}
