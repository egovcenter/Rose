package egovframework.com.uss.ion.apv.service;

import java.util.List;

import egovframework.com.uss.ion.apu.Pagination;
import egovframework.com.uss.ion.apv.Doc;
import egovframework.com.uss.umt.service.UserManageVO;

/**
 * @Class Name : EgovAttachFileService.java
 * @Description : Service for managing attached file information
 * @Modification Information
 *
 *    Date         Author	   Content
 *   -----------  -------     -------------------
 *    2016.02.04   paul        initial
 *
 * @since 2016.02.04.
 * @version
 * @see
 *
 */
public interface EgovApprovalDocService {


	/**
	 * 결재 대기 카운트
	 * @param userID 사용자ID
	 * @return 결재 대기 카운트
	 */
	public int getWaitingDocCnt(String userID) throws Exception;
	
	/**
	 * 결재 대기 목록 
	 * @param userID 사용자ID
	 * @return List<Doc> 결재 대기 목록
	 */
	public List<WaitingDocVO> getWaitingDocList(String userID) throws Exception;
	
	/**
	 * 결재 대기 목록 - 페이징된 목록
	 * @param userID 사용자ID
	 * @param pagination 페이징 정보
	 * @return List<Doc> 결재 대기 목록
	 */
	public List<WaitingDocVO> getWaitingDocList(String userID, Pagination pagination) throws Exception;
	
	
	
	
	
	
	
	/**
	 * 발송 대기 카운트
	 * @param deptId 부서ID
	 * @return 발송 대기 카운트
	 */
	public int getOutgoingDocCnt(String deptId) throws Exception;
	
	/**
	 * 발송 대기 목록 
	 * @param deptId 부서ID
	 * @return List<WaitingDoc> 발송 대기 목록
	 */
	public List<OutgoingDocVO> getOutgoingDocList(String deptId) throws Exception;
	
	/**
	 * 발송 대기 목록 
	 * @param deptId 부서ID
	 * @param pagination 페이징 정보
	 * @return List<OutgoingDoc> 발송 대기 목록
	 */
	public abstract List<OutgoingDocVO> getOutgoingDocList(String deptId, Pagination pagination) throws Exception;

	
	
	
	/**
	 * 접수 대기 카운트
	 * @param deptId 부서ID
	 * @return 접수 대기 카운트
	 */
	public abstract int getIncomingDocCnt(String deptId) throws Exception;
	
	/**
	 * 접수 대기 목록 
	 * @param deptId 부서ID
	 * @param pagination 페이징 정보
	 * @return List<IncomingDoc> 접수 대기 목록
	 */
	public abstract List<IncomingDocVO> getIncomingDocList(String deptId) throws Exception;

	/**
	 * 접수 대기 목록 
	 * @param deptId 부서ID
	 * @return List<IncomingDoc> 접수 대기 목록
	 */
	public abstract List<IncomingDocVO> getIncomingDocList(String deptId, Pagination pagination) throws Exception;

	
	
	
	/**
	 * 진행 중인 문서 카운트
	 * @param userID 사용자ID
	 * @return 진행 중인 문서 카운트
	 */
	public abstract int getOngoingDocCnt(String userID) throws Exception;

	/**
	 * 진행 중인 문서 목록 
	 * @param userID 사용자ID
	 * @return List<OngoingDoc>진행 중인 목록
	 */
	public abstract List<OngoingDocVO> getOngoingDocList(String userID) throws Exception;

	/**
	 * 진행 중인 문서 목록 
	 * @param userID 사용자ID
	 * @param pagination 페이징 정보
	 * @return List<OngoingDoc> 진행 중인 목록
	 */
	public abstract List<OngoingDocVO> getOngoingDocList(String userID, Pagination pagination) throws Exception;

	
	
	
	/**
	 * 기안한 문서 카운트
	 * @param userID 사용자ID
	 * @return 기안한 문서 카운트
	 */
	public abstract int getDraftDocCnt(String userID) throws Exception;

	/**
	 * 기안한 문서 목록 
	 * @param userID 사용자ID
	 * @return List<DraftDoc> 기안한 문서 목록
	 */
	public abstract List<DraftDocVO> getDraftDocList(String userID) throws Exception;

	/**
	 * 기안한 문서 목록 
	 * @param userID 사용자ID
	 * @param pagination 페이징 정보
	 * @return List<DraftDoc> 기안한 문서 목록
	 */
	public abstract List<DraftDocVO> getDraftDocList(String userID, Pagination pagination) throws Exception;

	
	
	
	/**
	 * 결재한 문서 카운트
	 * @param userID 사용자ID
	 * @return 결재한 문서 카운트
	 */
	public abstract int getApprovedDocCnt(String userID) throws Exception;

	/**
	 * 결재한 문서 목록 
	 * @param userID 사용자ID
	 * @return List<ApprovedDoc> 결재한 문서 목록
	 */
	public abstract List<ApprovedDocVO> getApprovedDocList(String userID) throws Exception;

	/**
	 * 결재한 문서 목록 
	 * @param userID 사용자ID
	 * @param pagination 페이징 정보
	 * @return List<ApprovedDoc> 결재한 문서 목록
	 */
	public abstract List<ApprovedDocVO>  getApprovedDocList(String userID, Pagination pagination) throws Exception;

	
	
	
	/**
	 * 수신함(접수함) 문서 카운트
	 * @param userID 사용자ID
	 * @return 수신함(접수함) 문서 카운트
	 */
	public abstract int getInboxDocCnt(String deptId) throws Exception;

	/**
	 * 수신함(접수함) 문서 목록 
	 * @param userID 사용자ID
	 * @return List<InboxDoc> 수신함(접수함) 문서 목록
	 */
	public abstract List<InboxDocVO> getInboxDocList(String deptId) throws Exception;

	/**
	 * 수신함(접수함) 문서 목록 
	 * @param userID 사용자ID
	 * @param pagination 페이징 정보
	 * @return List<InboxDoc> 수신함(접수함) 문서 목록
	 */
	public abstract List<InboxDocVO> getInboxDocList(String deptId, Pagination pagination) throws Exception;

	
	
	
	/**
	 * 발송함(문서함) 문서 카운트 
	 * @param userID 사용자ID
	 * @return 발송함(문서함) 문서 카운트
	 */
	public abstract int getOutboxDocCnt(String deptId) throws Exception;

	/**
	 * 발송함(문서함) 문서 목록 
	 * @param userID 사용자ID
	 * @return List<OutboxDoc> 발송함(문서함) 대기 목록
	 */
	public abstract List<OutboxDocVO> getOutboxDocList(String deptId) throws Exception;

	/**
	 * 발송함(문서함) 문서 목록 
	 * @param userID 사용자ID
	 * @param pagination 페이징 정보
	 * @return List<OutboxDoc> 발송함(문서함) 문서 목록
	 */
	public abstract List<OutboxDocVO> getOutboxDocList(String detpID, Pagination pagination) throws Exception;

	
	
	
	/**
	 * 부서 문서함 문서 카운트 
	 * @param userID 사용자ID
	 * @return 부서 문서함 문서 카운트 
	 */
	public abstract int getLabelDocCnt(String deptId, String labelID) throws Exception;

	/**
	 * 부서 문서함 문서 목록
	 * @param userID 사용자ID
	 * @return List<LabelDoc> 부서 문서함 문서 목록
	 */
	public abstract List<LabelDocVO> getLabelDocList(String deptId, String labelID) throws Exception;

	/**
	 * 부서 문서함 문서 목록 
	 * @param userID 사용자ID
	 * @param pagination 페이징 정보
	 * @return List<LabelDoc> 부서 문서함 문서 목록
	 */
	public abstract List<LabelDocVO> getLabelDocList(String deptId, String labelID, Pagination pagination) throws Exception;

	
	
	
	/**
	 * 사용자가 발송담당자로 지정된 부서를 구한다. 
	 * @param userID 사용자ID
	 * @return String 부서ID
	 */
	public String getDeptIdBySBoxUserId(String userID) throws Exception;
	
	/**
	 * 사용자가 접수담당자로 지정된 부서를 구한다.
	 * @param userID 사용자ID
	 * @return String 부서ID
	 */
	public String getDeptIdByRBoxUserId(String userID) throws Exception;
	
	/**
	 * 사용자ID로 소속 부서를 구한다. 
	 * @param userID 사용자ID
	 * @return String 부서ID
	 */
	public String getDeptIdByUserId(String userID) throws Exception;
	
	
	
	/**
	 * 문서 추가 
	 * @param doc
	 * @return
	 * @throws Exception
	 */
	public void insertDoc(Doc doc) throws Exception;
	
	public void updateDoc(Doc doc) throws Exception;
	
	public Doc getDoc(String docID) throws Exception;
	
	/**
	 * 문서일련 번호 생성
	 * @param year 년도 4자리
	 * @param tableName 품의(draft), 수신(recv)
	 * @param deptId 부서ID
	 * @param formId 양식ID
	 * @return 문서 일련 번호
	 */
	public int getNextDocNumber(String year, String tableName, String deptId, String formId);
	
	public int getDocCntByLabelId(String labelID);
	
	public int getDocCntByFormId(String formID);

	/**
	 * 배부함(문서함) 문서 카운트 
	 * @param userID 사용자ID
	 * @return 배부함(문서함) 문서 카운트
	 */
	public int getPassboxDocCnt(String deptId) throws Exception;
	
	/**
	 * 배부함(문서함) 문서 목록 
	 * @param userID 사용자ID
	 * @return List<PassboxDoc> 배부함(문서함) 문서 목록
	 */
	public List<PassboxDocVO> getPassboxDocList(String deptId) throws Exception;
	/**
	 * 배부함(문서함) 문서 목록 
	 * @param userID 사용자ID
	 * @param pagination 페이징 정보
	 * @return List<PassboxDoc> 배부함(문서함) 문서 목록
	 */
	public List<PassboxDocVO> getPassboxDocList(String deptId, Pagination pagination) throws Exception;

    public String createDocBody(Doc orgDoc)throws Exception;
    
    public String getDocBody(Doc doc) throws Exception;
    
    public String getNextDocId() throws Exception;
}
