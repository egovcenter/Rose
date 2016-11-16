package egovframework.com.uss.ion.apv.service;

import java.util.Date;

public class SignerChangeHistoryVO {
	
	private long seq;
	
	private Date changeDate;
	
	private String docId;
	
	private String initiatorId;
	
	private String initiatorNm;
	
	private String targetId;
	
	private String targetNm;
	
	private String activity;
	
	public SignerChangeHistoryVO() {
		super();
	}
	
	public long getSeq() {
		return seq;
	}
	
	public void setSeq(long seq) {
		this.seq = seq;
	}
	
	public Date getChangeDate() {
		return changeDate;
	}
	
	public void setChangeDate(Date date) {
		changeDate = date;
	}
	
	public String getDocId() {
		return docId;
	}
	
	public void setDocId(String id) {
		docId = id;
	}
	
	public String getInitiatorId() {
		return initiatorId;
	}
	
	public void setInitiatorId(String id) {
		initiatorId = id;
	}
	
	public String getInitiatorNm() {
		return initiatorNm;
	}
	
	public void setInitialtorNm(String name) {
		initiatorNm = name;
	}
	
	public String getTargetId() {
		return targetId;
	}
	
	public void setTargetId(String id) {
		targetId = id;
	}
	
	public String getTargetNm() {
		return targetNm;
	}
	
	public void setTargetNm(String name) {
		targetNm = name;
	}
	
	public String getActivity() {
		return activity;
	}
	
	public void setActivity(String log) {
		activity = log;
	}
}
