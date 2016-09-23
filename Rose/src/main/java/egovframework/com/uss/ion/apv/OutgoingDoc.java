package egovframework.com.uss.ion.apv;

import java.util.Date;
import java.util.List;

public interface OutgoingDoc extends Doc{

	/**
	 * 기안일자
	 */
	public Date getDraftDateTime();
	
	/**
	 * 기안일자
	 */
	public void setDraftDateTime(Date draftDateTime);
	
	/**
	 * 수신자 정보
	 */
	public List<Recipient> getRecipient();
	
	/**
	 * 수신자 정보
	 */
	public void setRecipient(List<Recipient> recipient);
	
	/**
	 * 수신자명을 하나의 문자열로 리턴
	 * <br>구분자는 " , "
	 * @return String 수신자명
	 */
	public void setRecipientUserNames();
	
	/**
	 * 수신자명을 하나의 문자열로 붙여 리턴
	 * <br>구분자는 " , "
	 * @return String 수신자명
	 */
	public String getRecipientUserNames();
	
	/**
	 * 수신부서명을 하나의 문자열로 리턴
	 * <br>구분자는 " , "
	 * @return String 수신부서들
	 */
	public String getRecipientDeptNames();
	public void setRecipientDeptNames(String recipientDeptNames);
	
}
