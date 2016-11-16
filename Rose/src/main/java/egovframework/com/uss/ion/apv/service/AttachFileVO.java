package egovframework.com.uss.ion.apv.service;

import java.io.Serializable;
import java.util.Date;

public class AttachFileVO implements Serializable {

	private static final long serialVersionUID = 8677276619905539487L;
	
	/**
	 * attach id
	 */
	protected String attachID; //atta_id	char(9)

	/**
	 * document id
	 */
	protected String docID; //doc_id	char(9)

	/**
	 * attached file name
	 */
	protected String attachNm; //atta_nm	varchar(50)
	
	/**
	 * sequence of attached file
	 */
	protected int attachSeq; //atta_seq	int(3)
	
	/**
	 * file size
	 */
	protected int attachSize; //atta_size	int(11)
	
	/**
	 * file type
	 */
	protected String attachType; //atta_type	char(4)
	
	/**
	 * file datetime
	 */
	protected Date attachDateTime; //atta_datetime	datetime

	/**
	 * file signer id
	 */
	protected String attachSignerId; //atta_signer_id char(20)

	/**
	 * file action
	 */
	protected String attachAction; //atta_action char(20)
	
	/**
	 * file Signer Nm
	 */
	protected String attachSignerNm; //atta_signer_nm char(20)
	
	public String getAttachSignerNm() {
		return attachSignerNm;
	}

	public void setAttachSignerNm(String attachSignerNm) {
		this.attachSignerNm = attachSignerNm;
	}

	public String getAttachID() {
		return attachID;
	}

	public void setAttachID(String attachID) {
		this.attachID = attachID;
	}

	public String getDocID() {
		return docID;
	}

	public void setDocID(String docID) {
		this.docID = docID;
	}

	public String getAttachNm() {
		return attachNm;
	}

	public void setAttachNm(String attachNm) {
		this.attachNm = attachNm;
	}

	public int getAttachSeq() {
		return attachSeq;
	}

	public void setAttachSeq(int attachSeq) {
		this.attachSeq = attachSeq;
	}

	public int getAttachSize() {
		return attachSize;
	}

	public void setAttachSize(int attachSize) {
		this.attachSize = attachSize;
	}

	public String getAttachType() {
		return attachType;
	}

	public void setAttachType(String attachType) {
		this.attachType = attachType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getAttachDateTime() {
		return attachDateTime;
	}

	public void setAttachDateTime(Date attachDateTime) {
		this.attachDateTime = attachDateTime;
	}

	public String getAttachSignerId() {
		return attachSignerId;
	}

	public void setAttachSignerId(String attachSignerId) {
		this.attachSignerId = attachSignerId;
	}

	public String getAttachAction() {
		return attachAction;
	}

	public void setAttachAction(String attachAction) {
		this.attachAction = attachAction;
	}

	@Override
	public String toString() {
		return "AttachImpl [attachID=" + attachID + ", docID=" + docID
				+ ", attachNm=" + attachNm + ", attachSeq=" + attachSeq
				+ ", attachSize=" + attachSize + ", attachType=" + attachType
				+ ", attachDateTime=" + attachDateTime + ", attachSignerId=" + attachSignerId
				+ ", attachAction=" + attachAction
				+ "]";
	}


}
