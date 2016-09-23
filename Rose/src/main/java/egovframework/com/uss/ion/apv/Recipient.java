package egovframework.com.uss.ion.apv;

import java.util.Date;

public interface Recipient {

	/**
	 * 수신부서ID
	 */
	public abstract String getRecpID();

	/**
	 * 수신부서ID
	 */
	public abstract void setRecpID(String recpId);

	/**
	 * 문서ID
	 */
	public abstract String getDocID();

	/**
	 * 문서ID
	 */
	public abstract void setDocID(String docID);

	/**
	 * 순번
	 */
	public abstract int getRecpSeq();

	/**
	 * 순번
	 */
	public abstract void setRecpSeq(int recpSeq);

	/**
	 * 내부부서여부
	 */
	public abstract String getRecpInnerFlag();

	/**
	 * 내부부서여부
	 */
	public abstract void setRecpInnerFlag(String recpInnerFlag);

	/**
	 * 부서ID
	 */
	public abstract String getDeptID();

	/**
	 * 부서ID
	 */
	public abstract void setDeptID(String deptId);

	/**
	 * 부서명
	 */
	public abstract String getRecpDeptNm();

	/**
	 * 부서명
	 */
	public abstract void setRecpDeptNm(String recpDeptNm);

	/**
	 * 발송방법
	 */
	public abstract String getRecpSendType();

	/**
	 * 발송방법
	 */
	public abstract void setRecpSendType(String recpSendType);

	/**
	 * 발송일시
	 */
	public abstract Date getRecpSendDt();

	/**
	 * 발송일시
	 */
	public abstract void setRecpSendDt(Date recpSendDt);

	/**
	 * 발신자ID
	 */
	public abstract String getRecpSendUserID();

	/**
	 * 발신자ID
	 */
	public abstract void setRecpSendUserID(String recpSendUserID);

	/**
	 * 발신자명
	 */
	public abstract String getRecpSendUserNm();

	/**
	 * 발신자명
	 */
	public abstract void setRecpSendUserNm(String recpSendUserNm);

	/**
	 * 도착일시
	 */
	public abstract Date getRecpArrivalDt();

	/**
	 * 도착일시
	 */
	public abstract void setRecpArrivalDt(Date recpArrivalDt);

	/**
	 * 접수일시
	 */
	public abstract Date getRecpRecpDt();

	/**
	 * 접수일시
	 */
	public abstract void setRecpRecpDt(Date recpRecpDt);

	/**
	 * 접수자ID
	 */
	public abstract String getRecpRecpUserID();

	/**
	 * 접수자ID
	 */
	public abstract void setRecpRecpUserID(String recpRecpUserID);

	/**
	 * 접수자명
	 */
	public abstract String getRecpRecpUserNm();

	/**
	 * 접수자명
	 */
	public abstract void setRecpRecpUserNm(String recpRecpUserNm);

	/**
	 * 접수완료일시
	 */
	public abstract Date getRecpFinishDt();

	/**
	 * 접수완료일시
	 */
	public abstract void setRecpFinishDt(Date recpFinishDt);

	/**
	 * 접수완료자ID
	 */
	public abstract String getRecpFinishUserID();

	/**
	 * 접수완료자ID
	 */
	public abstract void setRecpFinishUserID(String recpFinishUserID);

	/**
	 * 접수완료자명
	 */
	public abstract String getRecpFinishUserNm();

	/**
	 * 접수완료자명
	 */
	public abstract void setRecpFinishUserNm(String recpFinishUserNm);

	/**
	 * 비고
	 */
	public abstract String getRecpRemark();

	/**
	 * 비고
	 */
	public abstract void setRecpRemark(String recpRemark);
	/**
	 * 수신문서종류
	 * @param recpDocType
	 */
	public void setRecpDocType(String recpDocType);
	/**
	 * 수신문서종류
	 * @return
	 */
	public String getRecpDocType();
	/**
	 * 접수처리방법
	 * @param recpMethod
	 */
	public void setRecpMethod(String recpMethod);
	/**
	 * 접수처리방법
	 * @return
	 */
	public String getRecpMethod();
	
	/**
	 * 배부부서ID
	 * @param recpDeptID
	 */
	public void setRecpPassDeptID(String recpPassDeptID);
	/**
	 * 배부부서ID
	 * @return
	 */
	public String getRecpPassDeptID();

	/**
	 * Outbox status : Type; 발송방법(SEND_TYPE)
	 */
	public String getType();

	/**
	 * Outbox status : Type; 발송방법(SEND_TYPE)
	 */
	public void setType(String type);

	/**
	 * Outbox status : Sent Date;  (발송부서의) 발송일시
	 */
	public Date getSentDate();

	/**
	 * Outbox status : Sent Date;  (발송부서의) 발송일시
	 */
	public void setSentDate(Date sentDate);

	/**
	 * Outbox status : Completed; (수신부서의) 접수완료일시
	 */
	public Date getCompletedDate();

	/**
	 * Outbox status : Completed; (수신부서의) 접수완료일시
	 */
	public void setCompletedDate(Date completedDate);

	/**
	 * Outbox status : process; (수신부서의) 접수 진행상태
	 */
	public String getProcess();

	/**
	 * Outbox status : process; (수신부서의) 접수 진행상태
	 */
	public void setProcess(String process);

	/**
	 * Outbox status : Department; 
	 * <br/>If this.porcess='Sent' then '(empty)'
	 * <br/>If this.porcess='Received' then <b>접수부서</b>
	 * <br/>If this.porcess='Finished' then <b>접수(결재)완료부서</b>
	 */
	public String getDepartment();

	/**
	 * Outbox status : Department; 
	 * <br/>If this.porcess='Sent' then '(empty)'
	 * <br/>If this.porcess='Received' then <b>접수부서</b>
	 * <br/>If this.porcess='Finished' then <b>접수(결재)완료부서</b>
	 */
	public void setDepartment(String department);

	/**
	 * Outbox status : Position;
	 * <br/>If this.porcess='Sent' then '(empty)'
	 * <br/>If this.porcess='Received' then <b>접수자 직위</b>
	 * <br/>If this.porcess='Finished' then <b>접수(결재)완료자 직위</b>
	 */
	public String getPosition();

	/**
	 * Outbox status : Position;
	 * <br/>If this.porcess='Sent' then '(empty)'
	 * <br/>If this.porcess='Received' then <b>접수자 직위</b>
	 * <br/>If this.porcess='Finished' then <b>접수(결재)완료자 직위</b>
	 */
	public void setPosition(String position);

	/**
	 * Outbox status : Name;
	 * <br/>If this.porcess='Sent' then '(empty)'
	 * <br/>If this.porcess='Received' then <b>접수자명</b>
	 * <br/>If this.porcess='Finished' then <b>접수(결재)완료자명</b>
	 */
	public String getName();

	/**
	 * Outbox status : Name;
	 * <br/>If this.porcess='Sent' then '(empty)'
	 * <br/>If this.porcess='Received' then <b>접수자명</b>
	 * <br/>If this.porcess='Finished' then <b>접수(결재)완료자명</b>
	 */
	public void setName(String name);

}
