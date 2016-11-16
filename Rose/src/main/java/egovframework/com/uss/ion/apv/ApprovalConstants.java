package egovframework.com.uss.ion.apv;

import java.util.Date;

public class ApprovalConstants {

	//첨부파일 변경 내용
	//  - Add : 추가
	//  - Delete : 삭제
	public static final String ATTACH_ACTION_ADD = "add";
	public static final String ATTACH_ACTION_DELETE = "delete";
	
	//수신함 보유 여부
	//  - 1 : 보유
	//  - 2 : 미보유
	public static final String DEPT_RECEIVE_DOC_BOX_USE = "1";
	public static final String DEPT_RECEIVE_DOC_BOX_UNUSE = "0";
	
	//문서함 보유 여부
	//  - 1 : 보유
	//  - 2 : 미보유
	public static final String DEPT_DOC_BOX_USE = "1";
	public static final String DEPT_DOC_BOX_UNUSE = "0";

	
	
	//대리결재 여부	
	//  - 1 : 대리결재	
	//  - 2 : 대리결재 아님
	public static final String APPROVAL_SIGN_SUBUSER = "1";
	public static final String APPROVAL_SIGN_NON_SUBUSER = "2";
		
	//보존년한(DOC_SYEAR) 	
	//	0 : 영구
	//	숫자 : 보존년한
	public static final String APPROVAL_DOC_SYEAR_FOREVER = "0";
	
	//긴급여부(DOC_EM_F)
	//  - 1 : 긴급
	//  - 2 : 보통
	public static final String APPROVAL_DOC_EM_F_EMERGENCY = "1";
	public static final String APPROVAL_DOC_EM_F_NORMAL = "2";
	
	//종이문서 여부(DOC_PP_F)
	//  - 1 : 종이
	//  - 2 : 전자
	public static final String APPROVAL_DOC_PP_F_PAPER = "1";
	public static final String APPROVAL_DOC_PP_F_ELECTRONIC = "2";

	//첨부유무(DOC_ATTA_F)
	//  - 1 : 있다
	//  - 2 : 없다
	public static final String APPROVAL_DOC_ATTA_F_EXIST = "1";
	public static final String APPROVAL_DOC_ATTA_F_NOTEXIST = "2";
	
	//의견유무(DOC_OPN_F)
	//  - 1 : 있다
	//  - 2 : 없다
	public static final String APPROVAL_DOC_OPN_F_EXIST = "1";
	public static final String APPROVAL_DOC_OPN_F_NOTEXIST = "2";
	
	public static final String APPROVAL_DOC_SLVL_OPEN = "99";
	public static final String APPROVAL_DOC_SLVL_SECRET = "1";

	
		
	//문서진행상태(DOC_P_STATE)	
	//  - DP00 : 진행	
	//  - DP01 : 보류	
	//  - DP02 : 반송	
	//  - DP09 : 완료	
	/**
	 * 문서진행상태(DOC_P_STATE)	DP00 : 진행
	 */
	public static final String DOC_PROGRESS_STATE_ONGOING = "DP00";
	/**
	 * 문서진행상태(DOC_P_STATE)	DP01 : 보류
	 */
	public static final String  DOC_PROGRESS_STATE_HOLDING = "DP01";
	/**
	 * 문서진행상태(DOC_P_STATE)	DP02 : 반송
	 */
	public static final String DOC_PROGRESS_STATE_RETURN = "DP02";
	/**
	 * 문서진행상태(DOC_P_STATE)	DP09 : 완료
	 */
	public static final String DOC_PROGRESS_STATE_FINISHED = "DP09";
	
	//문서완료상태(DOC_F_STATE)	
	//  - DF00 : 진행	
	//  - DF01 : 결재	
	//  - DF02 : 취소  	
	public static final String DOC_FINISH_STATE_ONGOING = "DF00";
	public static final String DOC_FINISH_STATE_COMPLETED = "DF01";
	public static final String DOC_FINISH_STATE_CANCLED = "DF02";
	
	//결재자 결재유형(SNGR_KIND)	
	//  - SK00 : 기안	
	//  - SK01 : 검토	
	//  - SK02 : 결재	
	//  - SK03 : 접수	
	//  - SK04 : 재기안	
	//  - SK05 : 등록	
	/**
	 * 결재자 결재유형(SNGR_KIND)	SK00 : 기안
	 */
	public static final String SIGNER_KIND_DRAFT = "SK00";
	/**
	 * 결재자 결재유형(SNGR_KIND) SK01 : 검토
	 */
	public static final String SIGNER_KIND_REVIEW = "SK01";
	/**
	 * 결재자 결재유형(SNGR_KIND)	SK02 : 결재
	 */
	public static final String SIGNER_KIND_APPROVAL = "SK02";
	/**
	 * 결재자 결재유형(SNGR_KIND)	SK03 : 접수
	 */
	public static final String SIGNER_KIND_INCOMING = "SK03";
	/**
	 * 결재자 결재유형(SNGR_KIND)	SK04 : 재기안
	 */
	public static final String SIGNER_KIND_RE_DRAFT = "SK04";
	/**
	 * 결재자 결재유형(SNGR_KIND)	SK05 : 등록
	 */
	public static final String SIGNER_KIND_REGISTER = "SK05";

	//결재자 결재상태(SNGR_STATE)	
	//  - SS00 : 진행	
	//  - SS01 : 보류	
	//  - SS02 : 반송	
	//  - SS03 : 대기	
	//  - SS09 : 완료
	/**
	 * 결재자 결재상태(SNGR_STATE) SS00 : 진행
	 */
	public static final String SIGNER_STATE_ONGOING = "SS00";
	/**
	 * 결재자 결재상태(SNGR_STATE) SS01 : 보류
	 */
	public static final String SIGNER_STATE_HOLDING = "SS01";
	/**
	 * 결재자 결재상태(SNGR_STATE) SS02 : 반송
	 */
	public static final String SIGNER_STATE_RETURN = "SS02";
	/**
	 * 결재자 결재상태(SNGR_STATE) SS03 : 대기
	 */
	public static final String SIGNER_STATE_WAIT = "SS03";
	/**
	 * 결재자 결재상태(SNGR_STATE) SS09 : 완료
	 */
	public static final String SIGNER_STATE_FINISHED = "SS09";
	
	//문서종류(DOC_TYPE)	
	//  -1 : 내부문서(INTERNAL)	
	//  -2 : 발신문서(OUTGOING)	
	//  -3 : 수신문서(INCOMING)	
	public static final String DOC_TYPE_INTERNAL = "DT01";
	public static final String DOC_TYPE_OUTGOING = "DT02";
	public static final String DOC_TYPE_INCOMING = "DT03";

	//문서 목록 종류(DOCLIST)	
	/**
	 * 문서 목록 종류(DOCLIST)	진행중 문서 목록
	 */
	public static final String APP_DOCLIST_ONGOING = "DL01";
	/**
	 * 문서 목록 종류(DOCLIST)	처리할 문서 목록
	 */
	public static final String APP_DOCLIST_WAITING = "DL02";
	/**
	 * 문서 목록 종류(DOCLIST)	발송할 문서 목록
	 */
	public static final String APP_DOCLIST_OUTGOING = "DL03";
	/**
	 * 문서 목록 종류(DOCLIST)	접수할 문서 목록
	 */
	public static final String APP_DOCLIST_INCOMING = "DL04";
	/**
	 * 문서 목록 종류(DOCLIST)	기안한 문서 목록
	 */
	public static final String APP_DOCLIST_DRAFT = "DL05";
	/**
	 * 문서 목록 종류(DOCLIST)	처리한 문서 목록
	 */
	public static final String APP_DOCLIST_APPROVED = "DL06";
	/**
	 * 문서 목록 종류(DOCLIST)	발송한 문서 목록
	 */
	public static final String APP_DOCLIST_OUTBOX = "DL07";
	/**
	 * 문서 목록 종류(DOCLIST)	접수한 문서 목록
	 */
	public static final String APP_DOCLIST_INBOX = "DL08";
	/**
	 * 문서 목록 종류(DOCLIST)	문서분류별 문서 목록
	 */
	public static final String APP_DOCLIST_LABEL = "DL09";
	/**
	 * 문서 목록 종류(DOCLIST)	배부한 문서 목록
	 */
	public static final String APP_DOCLIST_PASSBOX = "DL10";
	/**
	 * 수신문서 종류 : 접수
	 */
	public static final String RECP_DOC_TYPE_RECEIVE = "1";
	/**
	 * 수신문서 종류 : 배부
	 */
	public static final String RECP_DOC_TYPE_PASS = "2";
	
	/**
	 * 접수처리방법(RECP_METHOD) 접수
	 */
	public static final String RECP_METHOD_SEND = "1";
	/**
	 * 접수처리방법(RECP_METHOD) 배부
	 */
	public static final String RECP_METHOD_PASS = "2";
	
	
	//  파일종류(ATTA_TYPE)
	//  - 4001 : TXT
	//  - 4002 : DOC
	//  - 4003 : XLS
	//  - 4004 : PPT
	//  - 4005 : PDF
	//  - 4006 : IMG (이미지)
	//  - 4007 : MOV (동영상)
	public static final String ATTACH_TYPE_TXT = "4001";
	public static final String ATTACH_TYPE_DOC = "4002";
	public static final String ATTACH_TYPE_XLS = "4003";
	public static final String ATTACH_TYPE_PPT = "4004";
	public static final String ATTACH_TYPE_PDF = "4005";
	public static final String ATTACH_TYPE_IMG = "4006";
	public static final String ATTACH_TYPE_MOV = "4007";

	//발송방법(SEND_TYPE)
	//  - ST01 : AUTO
	//  - ST02 : Email
	//  - ST03 : FAX
	//  - ST04 : POST
	//  - ST05 : Manual
	public static final String SEND_TYPE_AUTO 	= "ST01";
	public static final String SEND_TYPE_EMAIL	= "ST02";
	public static final String SEND_TYPE_FAX	= "ST03";
	public static final String SEND_TYPE_POST	= "ST04";
	public static final String SEND_TYPE_MANUAL = "ST05";
	
	public static final String USE_F_TRUE = "1";
	public static final String USE_F_FALSE = "0";

	public static final String DOC_NUM_TABLE_NAME_DRAFT = "draft";
	public static final String DOC_NUM_TABLE_NAME_RECV = "recv";
	public static final String DOC_NUM_YYYY_ALL = "0000";
	public static final String DOC_NUM_DEPT_ID_ALL = "000000000";
	public static final String DOC_NUM_FORM_ID_ALL = "000000000";
	
	public static final String FLAG_YES = "1";
	public static final String FLAG_NO = "2";
	
	public static final String NULL_ID_STRING = "000000000";
	public static final String NULL_DOC_NUM = "N/A";
	public static final String ROOT_LABEL_ID = "000000001";
	
	public static final String POSITION_LOWER = "lower";
	public static final String POSITION_UPPER = "upper";
	public static final String POSITION_SIBLING = "sibling";
}
