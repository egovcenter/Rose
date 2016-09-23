package egovframework.com.uss.ion.apv.service;

import java.util.Date;

public class RecipientVO {

	/**
	 * 수신부서ID
	 */
	protected String recpId; //recp_id
	
	/**
	 * 문서ID
	 */
	protected String docId; //doc_id
	
	/**
	 * 순번
	 */
	protected int recpSeq; //recp_seq
	
	/**
	 * 내부부서여부
	 */
	protected String recpInnerFlag; //recp_inner_f
	
	/**
	 * 부서ID
	 */
	protected String deptId; //dept_id
	
	/**
	 * 부서명
	 */
	protected String recpDeptNm; //recp_dept_nm
	
	/**
	 * 발송방법
	 */
	protected String recpSendType; //recp_send_type
	
	/**
	 * 발송일시
	 */
	protected Date recpSendDt; //recp_send_dt
	
	/**
	 * 발신자ID
	 */
	protected String recpSendUserId; //recp_s_user_id
	
	/**
	 * 발신자명
	 */
	protected String recpSendUserNm; //recp_s_user_nm
	
	/**
	 * 도착일시
	 */
	protected Date recpArrivalDt; //recp_arrv_dt
	
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
	 * 접수완료일시
	 */
	protected Date recpFinishDt; //recp_fini_dt
	
	/**
	 * 접수완료자ID
	 */
	protected String recpFinishUserId; //recp_f_user_id
	
	/**
	 * 접수완료자명
	 */
	protected String recpFinishUserNm; //recp_f_user_nm
	
	/**
	 * 수신문서종류(1 : 수신문서, 2: 배부문서)
	 */
	protected String recpDocType; // recp_doc_type
	/**
	 * 접수처리방법(1 : 접수, 2: 배부)
	 */
	protected String recpMethod; // recp_method
	/**
	 * 배부부서ID
	 */
	protected String recpPassDeptId; //recp_pass_dept_id

	/**
	 * Outbox status : Type; 발송방법(SEND_TYPE)
	 */
	protected String type;
	/**
	 * Outbox status : Sent Date;  (발송부서의) 발송일시
	 */
	protected Date sentDate;
	/**
	 * Outbox status : Completed; (수신부서의) 접수완료일시
	 */
	protected Date completedDate; 
	/**
	 * Outbox status : process; (수신부서의) 접수 진행상태
	 */
	protected String process;
	/**
	 * Outbox status : Department; 
	 * <br/>If this.porcess='Sent' then '(empty)'
	 * <br/>If this.porcess='Received' then <b>접수부서</b>
	 * <br/>If this.porcess='Finished' then <b>접수(결재)완료부서</b>
	 */
	protected String department;
	/**
	 * Outbox status : Position;
	 * <br/>If this.porcess='Sent' then '(empty)'
	 * <br/>If this.porcess='Received' then <b>접수자 직위</b>
	 * <br/>If this.porcess='Finished' then <b>접수(결재)완료자 직위</b>
	 */
	protected String position;
	/**
	 * Outbox status : Name;
	 * <br/>If this.porcess='Sent' then '(empty)'
	 * <br/>If this.porcess='Received' then <b>접수자명</b>
	 * <br/>If this.porcess='Finished' then <b>접수(결재)완료자명</b>
	 */
	protected String name;
	
	protected String recpRemark;
	
	public String getRecpRemark() {
		return recpRemark;
	}

	public void setRecpRemark(String recpRemark) {
		this.recpRemark = recpRemark;
	}

	public String getRecpId() {
		return recpId;
	}

	public void setRecpId(String recpId) {
		this.recpId = recpId;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docID) {
		this.docId = docID;
	}

	public int getRecpSeq() {
		return recpSeq;
	}

	public void setRecpSeq(int recpSeq) {
		this.recpSeq = recpSeq;
	}

	public String getRecpInnerFlag() {
		return recpInnerFlag;
	}

	public void setRecpInnerFlag(String recpInnerFlag) {
		this.recpInnerFlag = recpInnerFlag;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getRecpDeptNm() {
		return recpDeptNm;
	}

	public void setRecpDeptNm(String recpDeptNm) {
		this.recpDeptNm = recpDeptNm;
	}

	public String getRecpSendType() {
		return recpSendType;
	}

	public void setRecpSendType(String recpSendType) {
		this.recpSendType = recpSendType;
	}

	public Date getRecpSendDt() {
		return recpSendDt;
	}

	public void setRecpSendDt(Date recpSendDt) {
		this.recpSendDt = recpSendDt;
	}

	public String getRecpSendUserId() {
		return recpSendUserId;
	}

	public void setRecpSendUserId(String recpSendUserID) {
		this.recpSendUserId = recpSendUserID;
	}

	public String getRecpSendUserNm() {
		return recpSendUserNm;
	}

	public void setRecpSendUserNm(String recpSendUserNm) {
		this.recpSendUserNm = recpSendUserNm;
	}

	public Date getRecpArrivalDt() {
		return recpArrivalDt;
	}

	public void setRecpArrivalDt(Date recpArrivalDt) {
		this.recpArrivalDt = recpArrivalDt;
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

	public Date getRecpFinishDt() {
		return recpFinishDt;
	}

	public void setRecpFinishDt(Date recpFinishDt) {
		this.recpFinishDt = recpFinishDt;
	}

	public String getRecpFinishUserId() {
		return recpFinishUserId;
	}

	public void setRecpFinishUserId(String recpFinishUserID) {
		this.recpFinishUserId = recpFinishUserID;
	}

	public String getRecpFinishUserNm() {
		return recpFinishUserNm;
	}

	public void setRecpFinishUserNm(String recpFinishUserNm) {
		this.recpFinishUserNm = recpFinishUserNm;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getSentDate() {
		return sentDate;
	}

	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}

	public Date getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "RecipientImpl [recpId=" + recpId + ", docID=" + docId
				+ ", recpSeq=" + recpSeq + ", recpInnerFlag=" + recpInnerFlag
				+ ", deptId=" + deptId + ", recpDeptNm=" + recpDeptNm
				+ ", recpSendType=" + recpSendType + ", recpSendDt="
				+ recpSendDt + ", recpSendUserID=" + recpSendUserId
				+ ", recpSendUserNm=" + recpSendUserNm + ", recpArrivalDt="
				+ recpArrivalDt + ", recpRecpDt=" + recpRecpDt
				+ ", recpRecpUserID=" + recpRecpUserId + ", recpRecpUserNm="
				+ recpRecpUserNm + ", recpFinishDt=" + recpFinishDt
				+ ", recpFinishUserID=" + recpFinishUserId
				+ ", recpFinishUserNm=" + recpFinishUserNm + ", recpDocType=" + recpDocType + ", recpMethod=" + recpMethod + ", recpPassDeptID="
				+ recpPassDeptId + ", type=" + type + ", sentDate=" + sentDate
				+ ", completedDate=" + completedDate + ", process=" + process
				+ ", department=" + department + ", position=" + position
				+ ", name=" + name + "]";
	}

	public void setRecpDocType(String recpDocType) {
		this.recpDocType = recpDocType;
	}

	public String getRecpDocType() {
		return this.recpDocType;
	}
	
	public void setRecpMethod(String recpMethod) {
		this.recpMethod = recpMethod;
	}

	public String getRecpMethod() {
		return this.recpMethod;
	}

	public void setRecpPassDeptID(String recpPassDeptID) {
		this.recpPassDeptId = recpPassDeptID;
	}

	public String getRecpPassDeptID() {
		return this.recpPassDeptId;
	}

}
