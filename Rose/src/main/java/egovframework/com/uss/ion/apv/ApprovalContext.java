package egovframework.com.uss.ion.apv;

import java.util.List;

import egovframework.com.uss.ion.apv.service.AttachFileVO;
import egovframework.com.uss.ion.apv.service.RecipientVO;
import egovframework.com.uss.ion.apv.service.SignerVO;

public class ApprovalContext {
	private Doc doc;
	private List<SignerVO> signerList;
	private List<AttachFileVO> attachList;
	private List<RecipientVO> recipientList;
	public ApprovalContext(Doc doc, List<SignerVO> signerList,
			List<AttachFileVO> attachList) {
		super();
		this.doc = doc;
		this.signerList = signerList;
		this.attachList = attachList;
	}
	public ApprovalContext(Doc doc, List<SignerVO> signerList,
			List<AttachFileVO> attachList, List<RecipientVO> recipientList) {
		super();
		this.doc = doc;
		this.signerList = signerList;
		this.attachList = attachList;
		this.recipientList = recipientList;
	}
	public Doc getDoc() {
		return doc;
	}
	public void setDoc(Doc doc) {
		this.doc = doc;
	}
	public List<SignerVO> getSignerList() {
		return signerList;
	}
	public void setSignerList(List<SignerVO> signerList) {
		this.signerList = signerList;
	}
	public List<AttachFileVO> getAttachList() {
		return attachList;
	}
	public void setAttachList(List<AttachFileVO> attachList) {
		this.attachList = attachList;
	}
	public List<RecipientVO> getRecipientList() {
		return recipientList;
	}
	public void setRecipientList(List<RecipientVO> recipientList) {
		this.recipientList = recipientList;
	}
	
}
