package egovframework.com.uss.ion.apv.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import egovframework.com.uss.ion.apv.DocImpl;
import egovframework.com.uss.ion.apv.OutgoingDoc;
import egovframework.com.uss.ion.apv.Recipient;


public class OutgoingDocVO extends DocImpl implements OutgoingDoc {

	/**
	 * 기안일자
	 */
	protected Date draftDateTime;
	/**
	 * 수신자 정보
	 */
	protected List<Recipient> recipient;
	
	protected String recipientDeptNames;

	/**
	 * 수신자 문자열
	 */
	protected String recipientUserNames;
	
	public Date getDraftDateTime() {
		return draftDateTime;
	}
	public void setDraftDateTime(Date draftDateTime) {
		this.draftDateTime = draftDateTime;
	}
	public List<Recipient> getRecipient() {
		return null;
	}
	public void setRecipient(List<Recipient> recipient) {
		this.recipient = recipient;
		
	}
	public void setRecipientUserNames(){
		this.recipientUserNames = getRecipientUserNames(",");
	}
	
	public String getRecipientUserNames() {
		return this.recipientUserNames;
	}
		
	private String getRecipientUserNames(String separator) {
			
		if(recipient!=null){
			List<String> arr = new ArrayList<String>();
			for( Recipient recp : recipient ){
				arr.add(recp.getRecpRecpUserNm());
			}
			
			
			return StringUtils.join(arr, separator);
		}else{
			return "";
		}
		
	}
	public String getRecipientDeptNames() {
		return recipientDeptNames;
	}
	public void setRecipientDeptNames(String recipientDeptNames) {
		this.recipientDeptNames = recipientDeptNames;
	}
}
