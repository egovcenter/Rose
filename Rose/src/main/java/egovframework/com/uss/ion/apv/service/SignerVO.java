package egovframework.com.uss.ion.apv.service;

import java.io.Serializable;
import java.util.Date;

public class SignerVO implements Serializable, Cloneable {

	private static final long serialVersionUID = -20160212144200L;
		
	/**
	 * 결재선ID
	 */
	protected String signerID; //sngr_id	char(9)

	/**
	 * 문서ID
	 */
	protected String docID; //doc_id	char(9)

	/**
	 * 순번
	 */
	protected int signSeq; //sngr_seq	int(3)

	/**
	 * 사용자ID
	 */
	protected String userID; //sngr_user_id	char(9)

	/**
	 * 사용자명
	 */
	protected String signerName; //sngr_user_nm	varchar(50)

	/**
	 * 직위명
	 */
	protected String signerPositionName; //sngr_posi_nm	varchar(50)

	/**
	 * 직책명
	 */
	protected String signerDutyName; //sngr_duty_nm	varchar(50)

	/**
	 * 소속부서ID
	 */
	protected String signerDeptID; //sngr_dept_id	char(9)

	/**
	 * 소속부서명
	 */
	protected String signerDeptName; //sngr_dept_nm	varchar(50)

	/**
	 * 결재유형
	 */
	protected String signKind; //sngr_kind	char(4)

	/**
	 * 결재상태
	 */
	protected String signState; //sngr_state	char(4)

	/**
	 * 결재일시
	 */
	protected Date signDate; //sngr_dt	datetime

	/**
	 * 대리결재여부
	 */
	protected String signSubUserFlag; //sngr_subuser_f	char(1)

	/**
	 * 원결재자ID
	 */
	protected String signOrignUserID; //sngr_orguser_id	char(9)
	
	/* TBS 2016.02.16 start */
	/**
	 * 사용자 고유 ID
	 * 2016.02.16
	 */
	protected String uniqID;

	public String getUniqID() {
		return uniqID;
	}

	public void setUniqID(String uniqID) {
		this.uniqID = uniqID;
	}
	/* TBS 2016.02.16 end */

	protected String opinion;
	
	/* (non-Javadoc)
	 * @see com.tbs.approval.info.Signer#getSignerID()
	 */
	public String getSignerID() {
		return signerID;
	}

	/* (non-Javadoc)
	 * @see com.tbs.approval.info.Signer#setSignerID(java.lang.String)
	 */
	public void setSignerID(String signerID) {
		this.signerID = signerID;
	}

	/* (non-Javadoc)
	 * @see com.tbs.approval.info.Signer#getDocID()
	 */
	public String getDocID() {
		return docID;
	}

	/* (non-Javadoc)
	 * @see com.tbs.approval.info.Signer#setDocID(java.lang.String)
	 */
	public void setDocID(String docID) {
		this.docID = docID;
	}

	/* (non-Javadoc)
	 * @see com.tbs.approval.info.Signer#getSignSeq()
	 */
	public int getSignSeq() {
		return signSeq;
	}

	/* (non-Javadoc)
	 * @see com.tbs.approval.info.Signer#setSignSeq(java.lang.String)
	 */
	public void setSignSeq(int signSeq) {
		this.signSeq = signSeq;
	}

	/* (non-Javadoc)
	 * @see com.tbs.approval.info.Signer#getUserID()
	 */
	public String getUserID() {
		return userID;
	}

	/* (non-Javadoc)
	 * @see com.tbs.approval.info.Signer#setUserID(java.lang.String)
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}

	/* (non-Javadoc)
	 * @see com.tbs.approval.info.Signer#getSignerName()
	 */
	public String getSignerName() {
		return signerName;
	}

	/* (non-Javadoc)
	 * @see com.tbs.approval.info.Signer#setSignerName(java.lang.String)
	 */
	public void setSignerName(String signerName) {
		this.signerName = signerName;
	}

	/* (non-Javadoc)
	 * @see com.tbs.approval.info.Signer#getSignerPositionName()
	 */
	public String getSignerPositionName() {
		return signerPositionName;
	}

	/* (non-Javadoc)
	 * @see com.tbs.approval.info.Signer#setSignerPositionName(java.lang.String)
	 */
	public void setSignerPositionName(String signerPositionName) {
		this.signerPositionName = signerPositionName;
	}

	/* (non-Javadoc)
	 * @see com.tbs.approval.info.Signer#getSignerDutyName()
	 */
	public String getSignerDutyName() {
		return signerDutyName;
	}

	/* (non-Javadoc)
	 * @see com.tbs.approval.info.Signer#setSignerDutyName(java.lang.String)
	 */
	public void setSignerDutyName(String signerDutyName) {
		this.signerDutyName = signerDutyName;
	}

	/* (non-Javadoc)
	 * @see com.tbs.approval.info.Signer#getSignerDeptID()
	 */
	public String getSignerDeptID() {
		return signerDeptID;
	}

	/* (non-Javadoc)
	 * @see com.tbs.approval.info.Signer#setSignerDeptID(java.lang.String)
	 */
	public void setSignerDeptID(String signerDeptID) {
		this.signerDeptID = signerDeptID;
	}

	/* (non-Javadoc)
	 * @see com.tbs.approval.info.Signer#getSignerDeptName()
	 */
	public String getSignerDeptName() {
		return signerDeptName;
	}

	/* (non-Javadoc)
	 * @see com.tbs.approval.info.Signer#setSignerDeptName(java.lang.String)
	 */
	public void setSignerDeptName(String signerDeptName) {
		this.signerDeptName = signerDeptName;
	}

	/* (non-Javadoc)
	 * @see com.tbs.approval.info.Signer#getSignKind()
	 */
	public String getSignKind() {
		return signKind;
	}

	/* (non-Javadoc)
	 * @see com.tbs.approval.info.Signer#setSignKind(java.lang.String)
	 */
	public void setSignKind(String signKind) {
		this.signKind = signKind;
	}

	/* (non-Javadoc)
	 * @see com.tbs.approval.info.Signer#getSignState()
	 */
	public String getSignState() {
		return signState;
	}

	/* (non-Javadoc)
	 * @see com.tbs.approval.info.Signer#setSignState(java.lang.String)
	 */
	public void setSignState(String signState) {
		this.signState = signState;
	}

	/* (non-Javadoc)
	 * @see com.tbs.approval.info.Signer#getSignDate()
	 */
	public Date getSignDate() {
		return signDate;
	}

	/* (non-Javadoc)
	 * @see com.tbs.approval.info.Signer#setSignDate(java.lang.String)
	 */
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	/* (non-Javadoc)
	 * @see com.tbs.approval.info.Signer#getSignSubUserFlag()
	 */
	public String getSignSubUserFlag() {
		return signSubUserFlag;
	}

	/* (non-Javadoc)
	 * @see com.tbs.approval.info.Signer#setSignSubUserFlag(java.lang.String)
	 */
	public void setSignSubUserFlag(String signSubUserFlag) {
		this.signSubUserFlag = signSubUserFlag;
	}

	/* (non-Javadoc)
	 * @see com.tbs.approval.info.Signer#getSignOrignUserID()
	 */
	public String getSignOrignUserID() {
		return signOrignUserID;
	}

	/* (non-Javadoc)
	 * @see com.tbs.approval.info.Signer#setSignOrignUserID(java.lang.String)
	 */
	public void setSignOrignUserID(String signOrignUserID) {
		this.signOrignUserID = signOrignUserID;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public Object clone() throws CloneNotSupportedException {
		SignerVO signer = (SignerVO) super.clone();
		return signer;
	}
	@Override
	public String toString() {
		return "SignerImpl [signerID=" + signerID + ", docID=" + docID
				+ ", signSeq=" + signSeq + ", userID=" + userID
				+ ", signerName=" + signerName + ", signerPositionName="
				+ signerPositionName + ", signerDutyName=" + signerDutyName
				+ ", signerDeptID=" + signerDeptID + ", signerDeptName="
				+ signerDeptName + ", signKind=" + signKind + ", signState="
				+ signState + ", signDate=" + signDate + ", signSubUserFlag="
				+ signSubUserFlag + ", signOrignUserID=" + signOrignUserID
				+ "]";
	}
	
}
