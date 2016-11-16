package egovframework.com.uss.ion.apv.service;

import java.util.Date;

public class SignerHistoryVO {
	private String signerHistoryID;//sngr_h_id char(9)
	private String docID;//doc_id char(9)
	private int docVersion;//doc_version int(2)
	private int signerHistorySeq;//sngr_h_seq int(3)
	private String signerHistoryUserID;//sngr_h_user_id char(9)
	private String signerHistoryUserName;//sngr_h_user_nm varchar(50)
	private String signerHistoryPositionName;//sngr_h_posi_nm varchar(50)
	private String signerHistoryDutyName;//sngr_h_duty_nm varchar(50)
	private String signerHistoryDeptID;//sngr_h_dept_id char(9)
	private String signerHistoryDeptName;//sngr_h_dept_nm varchar(50)
	private String signerHistoryKind;//sngr_h_kind char(4)
	private String signerHistoryState;//sngr_h_state char(4)
	private Date signerHistoryDate;//sngr_h_dt datetime
	private String signerOpinion;//sngr_h_opinion varchar(500)

	public SignerHistoryVO() {
		super();
	}
	public SignerHistoryVO(String signerHistoryID, SignerVO signer){
		this.signerHistoryID = signerHistoryID;
		this.docID = signer.getDocID();
		this.docVersion = signer.getDocVersion();
		this.signerHistorySeq = signer.getSignSeq();
		this.signerHistoryUserID = signer.getUserID();
		this.signerHistoryUserName = signer.getSignerName();
		this.signerHistoryPositionName = signer.getSignerPositionName();
		this.signerHistoryDutyName = signer.getSignerDutyName();
		this.signerHistoryDeptID = signer.getSignerDeptID();
		this.signerHistoryDeptName = signer.getSignerDeptName();
		this.signerHistoryKind = signer.getSignKind();
		this.signerHistoryState = signer.getSignState();
		this.signerHistoryDate = signer.getSignDate();
		this.signerOpinion = signer.getOpinion();
	}
	/* (non-Javadoc)
	 * @see com.tbs.approval.info.impl.SignerHistory#getSignerOpinion()
	 */
	public String getSignerOpinion() {
		return signerOpinion;
	}
	/* (non-Javadoc)
	 * @see com.tbs.approval.info.impl.SignerHistory#setSignerOpinion(java.lang.String)
	 */
	public void setSignerOpinion(String signerOpinion) {
		this.signerOpinion = signerOpinion;
	}
	/* (non-Javadoc)
	 * @see com.tbs.approval.info.impl.SignerHistory#getSignerHistoryID()
	 */
	public String getSignerHistoryID() {
		return signerHistoryID;
	}
	/* (non-Javadoc)
	 * @see com.tbs.approval.info.impl.SignerHistory#setSignerHistoryID(java.lang.String)
	 */
	public void setSignerHistoryID(String signerHistoryID) {
		this.signerHistoryID = signerHistoryID;
	}
	/* (non-Javadoc)
	 * @see com.tbs.approval.info.impl.SignerHistory#getDocID()
	 */
	public String getDocID() {
		return docID;
	}
	/* (non-Javadoc)
	 * @see com.tbs.approval.info.impl.SignerHistory#setDocID(java.lang.String)
	 */
	public void setDocID(String docID) {
		this.docID = docID;
	}
	/* (non-Javadoc)
	 * @see com.tbs.approval.info.impl.SignerHistory#getDocVersion()
	 */
	public int getDocVersion() {
		return docVersion;
	}
	/* (non-Javadoc)
	 * @see com.tbs.approval.info.impl.SignerHistory#setDocVersion(java.lang.String)
	 */
	public void setDocVersion(int docVersion) {
		this.docVersion = docVersion;
	}
	/* (non-Javadoc)
	 * @see com.tbs.approval.info.impl.SignerHistory#getSignerHistorySeq()
	 */
	public int getSignerHistorySeq() {
		return signerHistorySeq;
	}
	/* (non-Javadoc)
	 * @see com.tbs.approval.info.impl.SignerHistory#setSignerHistorySeq(int)
	 */
	public void setSignerHistorySeq(int signerHistorySeq) {
		this.signerHistorySeq = signerHistorySeq;
	}
	/* (non-Javadoc)
	 * @see com.tbs.approval.info.impl.SignerHistory#getSignerHistoryUserID()
	 */
	public String getSignerHistoryUserID() {
		return signerHistoryUserID;
	}
	/* (non-Javadoc)
	 * @see com.tbs.approval.info.impl.SignerHistory#setSignerHistoryUserID(java.lang.String)
	 */
	public void setSignerHistoryUserID(String signerHistoryUserID) {
		this.signerHistoryUserID = signerHistoryUserID;
	}
	/* (non-Javadoc)
	 * @see com.tbs.approval.info.impl.SignerHistory#getSignerHistoryUserName()
	 */
	public String getSignerHistoryUserName() {
		return signerHistoryUserName;
	}
	/* (non-Javadoc)
	 * @see com.tbs.approval.info.impl.SignerHistory#setSignerHistoryUserName(java.lang.String)
	 */
	public void setSignerHistoryUserName(String signerHistoryUserName) {
		this.signerHistoryUserName = signerHistoryUserName;
	}
	/* (non-Javadoc)
	 * @see com.tbs.approval.info.impl.SignerHistory#getSignerHistoryPositionName()
	 */
	public String getSignerHistoryPositionName() {
		return signerHistoryPositionName;
	}
	/* (non-Javadoc)
	 * @see com.tbs.approval.info.impl.SignerHistory#setSignerHistoryPositionName(java.lang.String)
	 */
	public void setSignerHistoryPositionName(String signerHistoryPositionName) {
		this.signerHistoryPositionName = signerHistoryPositionName;
	}
	/* (non-Javadoc)
	 * @see com.tbs.approval.info.impl.SignerHistory#getSignerHistoryDutyName()
	 */
	public String getSignerHistoryDutyName() {
		return signerHistoryDutyName;
	}
	/* (non-Javadoc)
	 * @see com.tbs.approval.info.impl.SignerHistory#setSignerHistoryDutyName(java.lang.String)
	 */
	public void setSignerHistoryDutyName(String signerHistoryDutyName) {
		this.signerHistoryDutyName = signerHistoryDutyName;
	}
	/* (non-Javadoc)
	 * @see com.tbs.approval.info.impl.SignerHistory#getSignerHistoryDeptID()
	 */
	public String getSignerHistoryDeptID() {
		return signerHistoryDeptID;
	}
	/* (non-Javadoc)
	 * @see com.tbs.approval.info.impl.SignerHistory#setSignerHistoryDeptID(java.lang.String)
	 */
	public void setSignerHistoryDeptID(String signerHistoryDeptID) {
		this.signerHistoryDeptID = signerHistoryDeptID;
	}
	/* (non-Javadoc)
	 * @see com.tbs.approval.info.impl.SignerHistory#getSignerHistoryDeptName()
	 */
	public String getSignerHistoryDeptName() {
		return signerHistoryDeptName;
	}
	/* (non-Javadoc)
	 * @see com.tbs.approval.info.impl.SignerHistory#setSignerHistoryDeptName(java.lang.String)
	 */
	public void setSignerHistoryDeptName(String signerHistoryDeptName) {
		this.signerHistoryDeptName = signerHistoryDeptName;
	}
	/* (non-Javadoc)
	 * @see com.tbs.approval.info.impl.SignerHistory#getSignerHistoryKind()
	 */
	public String getSignerHistoryKind() {
		return signerHistoryKind;
	}
	/* (non-Javadoc)
	 * @see com.tbs.approval.info.impl.SignerHistory#setSignerHistoryKind(java.lang.String)
	 */
	public void setSignerHistoryKind(String signerHistoryKind) {
		this.signerHistoryKind = signerHistoryKind;
	}
	/* (non-Javadoc)
	 * @see com.tbs.approval.info.impl.SignerHistory#getSignerHistoryState()
	 */
	public String getSignerHistoryState() {
		return signerHistoryState;
	}
	/* (non-Javadoc)
	 * @see com.tbs.approval.info.impl.SignerHistory#setSignerHistoryState(java.lang.String)
	 */
	public void setSignerHistoryState(String signerHistoryState) {
		this.signerHistoryState = signerHistoryState;
	}
	/* (non-Javadoc)
	 * @see com.tbs.approval.info.impl.SignerHistory#getSignerHistoryDate()
	 */
	public Date getSignerHistoryDate() {
		return signerHistoryDate;
	}
	/* (non-Javadoc)
	 * @see com.tbs.approval.info.impl.SignerHistory#setSignerHistoryDate(java.util.Date)
	 */
	public void setSignerHistoryDate(Date signerHistoryDate) {
		this.signerHistoryDate = signerHistoryDate;
	}
	public String toString() {
		return "SignerHistoryImpl [signerHistoryID=" + signerHistoryID
				+ ", docID=" + docID + ", docVersion=" + docVersion 
				+ ", signerHistorySeq=" + signerHistorySeq
				+ ", signerHistoryUserID=" + signerHistoryUserID
				+ ", signerHistoryUserName=" + signerHistoryUserName
				+ ", signerHistoryPositionName=" + signerHistoryPositionName
				+ ", signerHistoryDutyName=" + signerHistoryDutyName
				+ ", signerHistoryDeptID=" + signerHistoryDeptID
				+ ", signerHistoryDeptName=" + signerHistoryDeptName
				+ ", signerHistoryKind=" + signerHistoryKind
				+ ", signerHistoryState=" + signerHistoryState
				+ ", signerHistoryDate=" + signerHistoryDate
				+ ", signerOpinion=" + signerOpinion + "]";
	}
	
}
