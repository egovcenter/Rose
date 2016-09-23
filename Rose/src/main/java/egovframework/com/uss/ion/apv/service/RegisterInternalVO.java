package egovframework.com.uss.ion.apv.service;

import java.io.Serializable;
import java.util.List;

import egovframework.com.uss.ion.apv.Attach;


public class RegisterInternalVO implements Serializable {
	private static final long serialVersionUID = -6673899314522735813L;
	private String docID;
	private String title;
	private String labelID;
	private String docNum;
	private String docType;
	private String signerUserID;
	private String signerUserNm;
	private String signerDeptID;
	private String signerDeptNm;
	private List<AttachFileVO> attachList;
	private String securityLevel;
	private String opinion;

	public String getOpinion() {
		return opinion;
	}
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	public String getDocID() {
		return docID;
	}
	public void setDocID(String docID) {
		this.docID = docID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLabelID() {
		return labelID;
	}
	public void setLabelID(String labelID) {
		this.labelID = labelID;
	}
	public String getDocNum() {
		return docNum;
	}
	public void setDocNum(String docNum) {
		this.docNum = docNum;
	}
	public String getSignerUserID() {
		return signerUserID;
	}
	public void setSignerUserID(String signerUserID) {
		this.signerUserID = signerUserID;
	}
	public String getSignerUserNm() {
		return signerUserNm;
	}
	public void setSignerUserNm(String signerUserNm) {
		this.signerUserNm = signerUserNm;
	}
	public String getSignerDeptID() {
		return signerDeptID;
	}
	public void setSignerDeptID(String signerDeptID) {
		this.signerDeptID = signerDeptID;
	}
	public String getSignerDeptNm() {
		return signerDeptNm;
	}
	public void setSignerDeptNm(String signerDeptNm) {
		this.signerDeptNm = signerDeptNm;
	}
	public List<AttachFileVO> getAttachList() {
		return attachList;
	}
	public void setAttachList(List<AttachFileVO> attachList) {
		this.attachList = attachList;
	}
	public String getSecurityLevel() {
		return securityLevel;
	}
	public void setSecurityLevel(String securityLevel) {
		this.securityLevel = securityLevel;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
		
	}
}
