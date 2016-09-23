package egovframework.com.uss.ion.apv.service;

import java.io.Serializable;
import java.util.List;

import egovframework.com.uss.ion.apv.Attach;

public class RegisterIncomingVO implements Serializable {
	private static final long serialVersionUID = -3713617852998753016L;
	
	private String docID;
	private String title;
	private String labelID;
	private String securityLevel;
	private String senderDeptName;
	private String senderPositionName;
	private String senderName;
	private String registerDeptID;
	private String registerDeptName;
	private String registerPosition;
	private String registerUserID;
	private String registerUserName;
	private List<AttachFileVO> attachList;
	private String registerDocNum;
	
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
	public String getSecurityLevel() {
		return securityLevel;
	}
	public void setSecurityLevel(String securityLevel) {
		this.securityLevel = securityLevel;
	}
	public String getSenderDeptName() {
		return senderDeptName;
	}
	public void setSenderDeptName(String senderDeptName) {
		this.senderDeptName = senderDeptName;
	}
	public String getSenderPositionName() {
		return senderPositionName;
	}
	public void setSenderPositionName(String senderPositionName) {
		this.senderPositionName = senderPositionName;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getRegisterDeptID() {
		return registerDeptID;
	}
	public void setRegisterDeptID(String registerDeptID) {
		this.registerDeptID = registerDeptID;
	}
	public String getRegisterDeptName() {
		return registerDeptName;
	}
	public void setRegisterDeptName(String registerDeptName) {
		this.registerDeptName = registerDeptName;
	}
	public String getRegisterPosition() {
		return registerPosition;
	}
	public void setRegisterPosition(String registerPosition) {
		this.registerPosition = registerPosition;
	}
	public String getRegisterUserID() {
		return registerUserID;
	}
	public void setRegisterUserID(String registerUserID) {
		this.registerUserID = registerUserID;
	}
	public String getRegisterUserName() {
		return registerUserName;
	}
	public void setRegisterUserName(String registerUserName) {
		this.registerUserName = registerUserName;
	}
	public List<AttachFileVO> getAttachList() {
		return attachList;
	}
	public void setAttachList(List<AttachFileVO> attachList) {
		this.attachList = attachList;
	}
	public String getRegisterDocNum() {
		return registerDocNum;
	}
	public void setRegisterDocNum(String registerDocNum) {
		this.registerDocNum = registerDocNum;
	}
}
