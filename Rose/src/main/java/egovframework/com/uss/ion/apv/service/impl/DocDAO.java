package egovframework.com.uss.ion.apv.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.com.uss.ion.apu.Pagination;
import egovframework.com.uss.ion.apv.Doc;
import egovframework.com.uss.ion.apv.SearchCriteria;
import egovframework.com.uss.ion.apv.service.ApprovedDocVO;
import egovframework.com.uss.ion.apv.service.DeptApprovalAuthVO;
import egovframework.com.uss.ion.apv.service.DraftDocVO;
import egovframework.com.uss.ion.apv.service.InboxDocVO;
import egovframework.com.uss.ion.apv.service.IncomingDocVO;
import egovframework.com.uss.ion.apv.service.LabelDocVO;
import egovframework.com.uss.ion.apv.service.OngoingDocVO;
import egovframework.com.uss.ion.apv.service.OutboxDocVO;
import egovframework.com.uss.ion.apv.service.OutgoingDocVO;
import egovframework.com.uss.ion.apv.service.PassboxDocVO;
import egovframework.com.uss.ion.apv.service.RecipientVO;
import egovframework.com.uss.ion.apv.service.SignerVO;
import egovframework.com.uss.ion.apv.service.UserApprovalAuthVO;
import egovframework.com.uss.ion.apv.service.WaitingDocVO;
import egovframework.com.uss.omt.service.DeptVO;




@Repository("DocDAO")
public class DocDAO extends EgovComAbstractDAO {
    private Object DOC_NUM_LOCK = new Object();
    
	/**
	 * 결재 대기 카운트
	 * @param userID 사용자ID
	 * @return 결재 대기 카운트
	 */
	public int selectWaitingDocCnt(SignerVO signer) throws Exception {
		return (Integer)select("DocDAO.selectWaitingDocCnt", signer);
	}
	/**
	 * 결재 대기 목록 
	 * @param userID 사용자ID
	 * @return List<Doc> 결재 대기 목록
	 */
	public List<WaitingDocVO> selectWaitingDocs(SearchCriteria signer) throws Exception {
		return (List<WaitingDocVO>) list("DocDAO.selectWaitingDocs", signer);
	}
	/**
	 * 결재 대기 목록 - 페이징된 목록
	 * @param userID 사용자ID
	 * @param pagination 페이징 정보
	 * @return List<Doc> 결재 대기 목록
	 */
	public List<WaitingDocVO> selectWaitingDocs(SearchCriteria vo, Pagination pagination) throws Exception {
		int firstRecordIndex = pagination.getFirstRecordIndex()-1 ;
		
		return (List<WaitingDocVO>) list("DocDAO.selectWaitingDocs", vo, firstRecordIndex, pagination.getPageSize());
	}
	/**
	 * 발송 대기 카운트
	 * @param deptId 부서ID
	 * @return 발송 대기 카운트
	 */
	public int selectOutgoingDocCnt(RecipientVO vo) throws Exception {
		return (Integer)select("DocDAO.selectOutgoingDocCnt", vo);
	}
	/**
	 * 발송 대기 목록 
	 * @param deptId 부서ID
	 * @return List<WaitingDoc> 발송 대기 목록
	 */
	public List<OutgoingDocVO> selectOutgoingDocs(SearchCriteria vo) throws Exception {
		return (List<OutgoingDocVO>) list("DocDAO.selectOutgoingDocs", vo);
	}
	/**
	 * 발송 대기 목록 
	 * @param deptId 부서ID
	 * @param pagination 페이징 정보
	 * @return List<OutgoingDoc> 발송 대기 목록
	 */
	public List<OutgoingDocVO> selectOutgoingDocs(SearchCriteria vo, Pagination pagination) throws Exception {
		int firstRecordIndex = pagination.getFirstRecordIndex()-1 ;
		return (List<OutgoingDocVO>) list("DocDAO.selectOutgoingDocs", vo, firstRecordIndex, pagination.getPageSize());
	}
	/**
	 * 접수 대기 카운트
	 * @param deptId 부서ID
	 * @return 접수 대기 카운트
	 */
	public int selectIncomingDocCnt(RecipientVO vo) throws Exception {
		return (Integer) select("DocDAO.selectIncomingDocCnt", vo);
	}
	/**
	 * 접수 대기 목록 
	 * @param deptId 부서ID
	 * @param pagination 페이징 정보
	 * @return List<IncomingDoc> 접수 대기 목록
	 */
	public List<IncomingDocVO> selectIncomingDocs(SearchCriteria vo) throws Exception {
		return (List<IncomingDocVO>) list("DocDAO.selectIncomingDocs", vo);
	}
	/**
	 * 접수 대기 목록 
	 * @param deptId 부서ID
	 * @return List<IncomingDoc> 접수 대기 목록
	 */
	public List<IncomingDocVO> selectIncomingDocs(SearchCriteria vo, Pagination pagination) throws Exception {
		int firstRecordIndex = pagination.getFirstRecordIndex()-1 ;
		return (List<IncomingDocVO>) list("DocDAO.selectIncomingDocs", vo, firstRecordIndex, pagination.getPageSize());
	}
	/**
	 * 진행 중인 문서 카운트
	 * @param userID 사용자ID
	 * @return 진행 중인 문서 카운트
	 */
	public int selectOngoingDocCnt(SignerVO signer) throws Exception {
		return (Integer)select("DocDAO.selectOngoingDocCnt", signer);
	}
	/**
	 * 진행 중인 문서 목록 
	 * @param userID 사용자ID
	 * @return List<OngoingDoc>진행 중인 목록
	 */
	public List<OngoingDocVO> selectOngoingDocs(SearchCriteria vo) throws Exception {
		return (List<OngoingDocVO>)list("DocDAO.selectOngoingDocs", vo);
	}
	/**
	 * 진행 중인 문서 목록 
	 * @param userID 사용자ID
	 * @param pagination 페이징 정보
	 * @return List<OngoingDoc> 진행 중인 목록
	 */
	public List<OngoingDocVO> selectOngoingDocs(SearchCriteria vo, Pagination pagination) throws Exception {
		int firstRecordIndex = pagination.getFirstRecordIndex()-1 ;
		return (List<OngoingDocVO>)list("DocDAO.selectOngoingDocs", vo, firstRecordIndex, pagination.getPageSize());
	}
	/**
	 * 기안한 문서 카운트
	 * @param userID 사용자ID
	 * @return 기안한 문서 카운트
	 */
	public int selectDraftDocCnt(SignerVO signer) throws Exception {
		return (Integer)select("DocDAO.selectDraftDocCnt", signer);
	}
	/**
	 * 기안한 문서 목록 
	 * @param userID 사용자ID
	 * @return List<DraftDoc> 기안한 문서 목록
	 */
	public List<DraftDocVO> selectDraftDocs(SearchCriteria vo) throws Exception {
		return (List<DraftDocVO>) list("DocDAO.selectDraftDocs", vo);
	}
	/**
	 * 기안한 문서 목록 
	 * @param userID 사용자ID
	 * @param pagination 페이징 정보
	 * @return List<DraftDoc> 기안한 문서 목록
	 */
	public List<DraftDocVO> selectDraftDocs(SearchCriteria vo, Pagination pagination) throws Exception {
		int firstRecordIndex = pagination.getFirstRecordIndex()-1 ;
		return (List<DraftDocVO>) list("DocDAO.selectDraftDocs", vo, firstRecordIndex, pagination.getPageSize());
	}
	/**
	 * 결재한 문서 카운트
	 * @param userID 사용자ID
	 * @return 결재한 문서 카운트
	 */
	public int selectApprovedDocCnt(SignerVO signer) throws Exception {
		return (Integer)select("DocDAO.selectApprovedDocCnt", signer);
	}
	/**
	 * 결재한 문서 목록 
	 * @param userID 사용자ID
	 * @return List<ApprovedDoc> 결재한 문서 목록
	 */
	public List<ApprovedDocVO> selectApprovedDocs(SearchCriteria vo) throws Exception {
		return (List<ApprovedDocVO>) list("DocDAO.selectApprovedDocs", vo);
	}
	/**
	 * 결재한 문서 목록 
	 * @param userID 사용자ID
	 * @param pagination 페이징 정보
	 * @return List<ApprovedDoc> 결재한 문서 목록
	 */
	public List<ApprovedDocVO>  selectApprovedDocs(SearchCriteria vo, Pagination pagination) throws Exception {
		int firstRecordIndex = pagination.getFirstRecordIndex()-1 ;
		return (List<ApprovedDocVO>) list("DocDAO.selectApprovedDocs", vo, firstRecordIndex, pagination.getPageSize());
	}
	/**
	 * 수신함(접수함) 문서 카운트
	 * @param deptId department ID
	 * @return 수신함(접수함) 문서 카운트
	 */
	public int selectInboxDocCnt(DeptVO dept) throws Exception {
		return (Integer)select("DocDAO.selectInboxDocCnt", dept);
	}
	/**
	 * 수신함(접수함) 문서 목록 
	 * @param deptId department ID
	 * @return List<InboxDoc> 수신함(접수함) 문서 목록
	 */
	public List<InboxDocVO> selectInboxDocs(SearchCriteria vo) throws Exception {
		return (List<InboxDocVO>) list("DocDAO.selectInboxDocs", vo);
	}
	/**
	 * 수신함(접수함) 문서 목록 
	 * @param deptId department ID
	 * @param pagination 페이징 정보
	 * @return List<InboxDoc> 수신함(접수함) 문서 목록
	 */
	public List<InboxDocVO> selectInboxDocs(SearchCriteria vo, Pagination pagination) throws Exception {
		int firstRecordIndex = pagination.getFirstRecordIndex()-1 ;
		return (List<InboxDocVO>) list("DocDAO.selectInboxDocs", vo, firstRecordIndex, pagination.getPageSize());
	}
	/**
	 * 발송함(문서함) 문서 카운트 
	 * @param deptId department ID
	 * @return 발송함(문서함) 문서 카운트
	 */
	public int selectOutboxDocCnt(DeptVO dept) throws Exception {
		return (Integer)select("DocDAO.selectOutboxDocCnt", dept);
	}
	/**
	 * 발송함(문서함) 문서 목록 
	 * @param deptId department ID
	 * @return List<OutboxDoc> 발송함(문서함) 대기 목록
	 */
	public List<OutboxDocVO> selectOutboxDocs(SearchCriteria vo) throws Exception {
		return (List<OutboxDocVO>) list("DocDAO.selectOutboxDocs", vo);
	}
	/**
	 * 발송함(문서함) 문서 목록 
	 * @param deptId department ID
	 * @param pagination 페이징 정보
	 * @return List<OutboxDoc> 발송함(문서함) 문서 목록
	 */
	public List<OutboxDocVO> selectOutboxDocs(SearchCriteria vo, Pagination pagination) throws Exception {
		int firstRecordIndex = pagination.getFirstRecordIndex()-1 ;
		return (List<OutboxDocVO>) list("DocDAO.selectOutboxDocs", vo, firstRecordIndex, pagination.getPageSize());
	}
	/**
	 * 부서 문서함 문서 카운트 
	 * @param userID 사용자ID
	 * @return 부서 문서함 문서 카운트 
	 */
	public int selectLabelDocCnt(LabelDocVO vo) throws Exception {
		return (Integer)select("DocDAO.selectLabelDocCnt", vo); 
	}
	/**
	 * 부서 문서함 문서 목록
	 * @param userID 사용자ID
	 * @return List<LabelDoc> 부서 문서함 문서 목록
	 */
	public List<LabelDocVO> selectLabelDocs(LabelDocVO lbel) throws Exception {
		return (List<LabelDocVO>) list("DocDAO.selectLabelDocs", lbel);
	}
	/**
	 * 부서 문서함 문서 목록 
	 * @param userID 사용자ID
	 * @param pagination 페이징 정보
	 * @return List<LabelDoc> 부서 문서함 문서 목록
	 */
	public List<LabelDocVO> selectLabelDocs(LabelDocVO lbel, Pagination pagination) throws Exception {
		int firstRecordIndex = pagination.getFirstRecordIndex()-1 ;
		return (List<LabelDocVO>) list("DocDAO.selectLabelDocs", lbel, firstRecordIndex, pagination.getPageSize());
	}
	/**
	 * 사용자가 발송담당자로 지정된 부서를 구한다. 
	 * @param userID 사용자ID
	 * @return String 부서ID
	 */
	public String selectDeptIdBySBoxUserId(UserApprovalAuthVO userInf) throws Exception {
		return (String) select("DocDAO.selectDeptIdBySBoxUserId", userInf);
	}
	/**
	 * 사용자가 접수담당자로 지정된 부서를 구한다.
	 * @param userID 사용자ID
	 * @return String 부서ID
	 */
	public String selectDeptIdByRBoxUserId(UserApprovalAuthVO userInf) throws Exception {
		return (String) select("DocDAO.selectDeptIdByRBoxUserId", userInf);
	}
	/**
	 * 사용자ID로 소속 부서를 구한다. 
	 * @param userID 사용자ID
	 * @return String 부서ID
	 */
	public String selectDeptIdByUserId(UserApprovalAuthVO userInf) throws Exception {
		return (String) select("DocDAO.selectDeptIdByUserId", userInf);
	}
	/**
	 * 문서 추가 
	 * @param doc
	 * @return
	 * @throws Exception
	 */
	public void insertDoc(Doc doc) throws Exception {
		insert("DocDAO.insertDoc", doc);
	}
	public void updateDoc(Doc doc) throws Exception {
		update("DocDAO.updateDoc", doc);
	}
	public Doc selectDoc(Doc docInf) throws Exception {
		return (Doc)select("DocDAO.selectDoc", docInf);
	}
	/**
	 * 문서일련 번호 생성
	 * @param year 년도 4자리
	 * @param tableName 품의(draft), 수신(recv)
	 * @param deptId 부서ID
	 * @param formId 양식ID
	 * @return 문서 일련 번호
	 */
//	public int geNextDocNumber(String year, String tableName, String deptId, String formId);
	public int selectDocCntByLabelId(Doc docInf) {
		return (Integer)select("DocDAO.selectDocCntByLabelId", docInf);
	}
	public int selectDocCntByFormId(Doc docInf) {
		return (Integer)select("DocDAO.selectDocCntByFormId", docInf);
	}
	/**
	 * 배부함(문서함) 문서 카운트 
	 * @param userID 사용자ID
	 * @return 배부함(문서함) 문서 카운트
	 */
	public int selectPassboxDocCnt(PassboxDocVO userInf) throws Exception {
		return (Integer)select("DocDAO.selectPassboxDocCnt", userInf);
	}
	/**
	 * 배부함(문서함) 문서 목록 
	 * @param userID 사용자ID
	 * @return List<PassboxDoc> 배부함(문서함) 문서 목록
	 */
	public List<PassboxDocVO> selectPassboxDocs(PassboxDocVO userInf) throws Exception {
		return (List<PassboxDocVO>)list("DocDAO.selectPassboxDocs", userInf);
	}
	/**
	 * 배부함(문서함) 문서 목록 
	 * @param userID 사용자ID
	 * @param pagination 페이징 정보
	 * @return List<PassboxDoc> 배부함(문서함) 문서 목록
	 */
	public List<PassboxDocVO> selectPassboxDocs(PassboxDocVO userInf, Pagination pagination) throws Exception {
		int firstRecordIndex = pagination.getFirstRecordIndex()-1 ;
		return (List<PassboxDocVO>)list("DocDAO.selectPassboxDocs", userInf, firstRecordIndex, pagination.getPageSize());
	}
	//TODO... implement making next document id
    public int selectNextDocNumber(String year, String tableName, String deptId, String formId) {
        //TODO MIGRATION_DATABASE
//        String updateSql = sqlProps.getProperty("doc_num.update");
//        String selectSql = sqlProps.getProperty("doc_num.select");
//        String insertSql = sqlProps.getProperty("doc_num.insert");
//
//        List<Object> params = new ArrayList<Object>();
//        params.add(year);
//        params.add(tableName);
//        params.add(deptId);
//        params.add(formId);
//
//        int saveNum = 0;
//        synchronized (DOC_NUM_LOCK) {
//            int count = getJdbcTemplate().update(updateSql, params.toArray());
//            // no data , insert
//            if (count < 1) {
//                params.add(1);
//                count = getJdbcTemplate().update(insertSql, params.toArray());
//                params.remove(4);
//            }
//            saveNum = getJdbcTemplate().queryForInt(selectSql, params.toArray());
//        }
//        return saveNum;
        return 0;
    }
}
