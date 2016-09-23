package egovframework.com.uss.ion.apv.service;

import java.io.Serializable;
import java.util.Date;


public class OpinionVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8605755262787923183L;
	/**
	 * 의견ID
	 */
	protected String opinionID; //opin_id	char(9)
	/**
	 * 문서ID
	 */
	protected String docID; // doc_id	char(9)
	/**
	 * 순번
	 */
	protected int opinionSeq; //opin_seq	int(3)
	/**
	 * 등록자ID
	 */
	protected String opinionUserID; //opin_user_id	char(9)
	/**
	 * 등록자명
	 */
	protected String opinionUserNm; //opin_user_nm	varchar(50)
	/**
	 * 직위명
	 */
	protected String opinionPosionNm; //opin_posi_nm	varchar(50)
	/**
	 * 직책명
	 */
	protected String opinionDutyNm; //opin_duty_nm	varchar(50)
	/**
	 * 소속부서ID
	 */
	protected String opinionDeptID; //opin_dept_id	char(9)
	/**
	 * 소속부서명
	 */
	protected String opinionDeptNm; //opin_dept_nm	varchar(50)
	/**
	 * 등록일시
	 */
	protected Date opinionRegDt; //opin_reg_dt	datetime
	/**
	 * 의견내용
	 */
	protected String opinionMsg; //opin_msg	varchar(4000)
	public String getOpinionID() {
		return opinionID;
	}
	public void setOpinionID(String opinionID) {
		this.opinionID = opinionID;
	}
	public String getDocID() {
		return docID;
	}
	public void setDocID(String docID) {
		this.docID = docID;
	}
	public int getOpinionSeq() {
		return opinionSeq;
	}
	public void setOpinionSeq(int opinionSeq) {
		this.opinionSeq = opinionSeq;
	}
	public String getOpinionUserID() {
		return opinionUserID;
	}
	public void setOpinionUserID(String opinionUserID) {
		this.opinionUserID = opinionUserID;
	}
	public String getOpinionUserNm() {
		return opinionUserNm;
	}
	public void setOpinionUserNm(String opinionUserNm) {
		this.opinionUserNm = opinionUserNm;
	}
	public String getOpinionPosionNm() {
		return opinionPosionNm;
	}
	public void setOpinionPosionNm(String opinionPosionNm) {
		this.opinionPosionNm = opinionPosionNm;
	}
	public String getOpinionDutyNm() {
		return opinionDutyNm;
	}
	public void setOpinionDutyNm(String opinionDutyNm) {
		this.opinionDutyNm = opinionDutyNm;
	}
	public String getOpinionDeptID() {
		return opinionDeptID;
	}
	public void setOpinionDeptID(String opinionDeptID) {
		this.opinionDeptID = opinionDeptID;
	}
	public String getOpinionDeptNm() {
		return opinionDeptNm;
	}
	public void setOpinionDeptNm(String opinionDeptNm) {
		this.opinionDeptNm = opinionDeptNm;
	}
	public Date getOpinionRegDt() {
		return opinionRegDt;
	}
	public void setOpinionRegDt(Date opinionRegDt) {
		this.opinionRegDt = opinionRegDt;
	}
	public String getOpinionMsg() {
		return opinionMsg;
	}
	public void setOpinionMsg(String opinionMsg) {
		this.opinionMsg = opinionMsg;
	}
	
	
}
