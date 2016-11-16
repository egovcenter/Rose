package egovframework.com.uss.omt;

/**
 * @author kklim
 * @version 2.0
 * @created 17-4-2012 오전 10:52:38
 */
public interface OrgConstant {
	public static final String SESSION_KEY = "user";
	public static final String COOKIE_KEY = "key";
	public static final String COOKIE_USERID = "userID";
	public static final String COOKIE_EMP = "emp";
	public static final String COOKIE_DEPTID = "deptId";
	
	public static final String DIRECTORY_LOCALE = "FRAMEWORK_DIRECTORY_LOCALE";
	
	public static final String DIRECTORY_DATA_URL = "/directory/data";
	
	/**
	 * <p>사용자ID로 로그인할때 login type 에 사용하는 코드
	 * 
	 * <p> when a user login by user's ID, use this code 
	 */
	public static final String LOGIN_TYPE_USERID = "userid";
	/**
	 * <p>이름으로 로그인할때 login type 에 사용하는 코드
	 * 
	 * <p> when a user login by user's name, use this code 
	 */
	public static final String LOGIN_TYPE_NAME = "name";
	/**
	 * <p>사원번호로 로그인할때 login type 에 사용하는 코드
	 * 
	 * <p> when a user login by user's employee code, use this code 
	 */
	public static final String LOGIN_TYPE_EMPCODE = "empcode";
	/**
	 * <p>로그인ID로 로그인할때 login type 에 사용하는 코드
	 * 
	 * <p> when a user login by user's loginid, use this code 
	 */
	public static final String LOGIN_TYPE_LOGINID = "loginid";
	
	/**
	 * <p>최상위 부서의 부서ID
	 * 
	 * <p>top root department's ID
	 */
	public static final String ORGID_ROOT 	= "ORGNZT_0000000000000";
	/**
	 * <p>최상위 부서의 상위부서 ID
	 * 
	 * <p>top root department's parent dept's ID
	 */	
	public static final String ORGID_ROOT_PARID = "000000000";
	/**
	 * <p>전체 관리자의 사용자 ID
	 * 
	 * <p>the user's ID for system administrator 
	 */
	public static final String ORGID_ADMIN 	= "000000001";
	
	public static final int UF_CABINET				= 0x00000001;	// 문서함 가짐
	public static final int UF_SUPERVISOR			= 0x00000004;	// 관리자
	public static final int UF_RBOX					= 0x00000008;	// 수신함 가짐
	public static final int UF_AUDIT				= 0x00000010;	// 감사/검사부
	public static final int UF_AUDIT_LOG			= 0x00000020;	// 감사일지 생성
	public static final int UF_OFFICER				= 0x00000040;	// 부서장
	public static final int UF_DOCADMIN 			= 0x00000100;	// 수발신담당자
	public static final int UF_DOCMANAGER			= 0x00000200;	// 기록물관리자
	public static final int UF_OUT_CONTROL			= 0x00000400;	// 대외심사자
	public static final int UF_IN_CONTROL			= 0x00000800;	// 대내심사자
	public static final int UF_SIGNER				= 0x00001000;	// 서명관
	public static final int UF_REPORT_CONTROL		= 0x00002000;	// 보고심사자
	public static final int UF_ETC_CONTROL			= 0x00008000;	// 기타심사자
	public static final int UF_ABS					= 0x00010000;	// 부재
	public static final int UF_DEF_FLOW_END			= 0x00020000;	// 디폴트 자동결재선 최종 결재자
	public static final int UF_INKE_DEPT			= 0x00040000;	// 인계부서 여부  : 2005.05.15
	public static final int UF_COMPLIANCE_LEADER 	= 0x00080000;   // 준법감시 : 2007.04.05 
	public static final int UF_BUDGET_CONTROL 		= 0x00100000;   // 예산통제 
	public static final int UF_FULLTIME_AUDIT 		= 0x00200000;   // 상근감사 
	public static final int UF_SENIOR_ACTURARY	 	= 0x00400000;   // 선임계리
	public static final int UF_FORM_CONTROL	 	    = 0x00800000;   // 양식관리 권한 가짐
	public static final int UF_COMPLIANCE           = 0x02000000;   // 준법감시 
	public static final int UF_CUSTOM_SIGN_TYPE_5	= 0x04000000;	// 커스텀사인타입 특별협조자5
	public static final int UF_REP_DEPT				= 0x08000000;	// 대표부서
	public static final int UF_LOCK_F				= 0x10000000;	// 로그인금지flag
	
	/**
	 * <p>그룹의 application 중 시스템 그룹
	 * <p>dir_group_app 테이블에 존재하지 않는다.
	 * <p>시스템에서 사용하는 그룹 종류이며, 그룹관리 화면의 그룹종류 목록에 나타나지 않는다.
	 */
	public static final String SYSTEM_GROUP_APP		= "SYS";
	public static final String SYSTEM_GROUP_FAVORITEUSER		= "Favorite user";		// 시스템 그룹 - 즐겨찾는 사용자
	public static final String SYSTEM_GROUP_RECENTSEARCHUSER	= "Recent search user";	// 시스템 그룹 - 최근 검색한 사용자
	
	/**
	 * <p>그룹의 application 중 메일 그룹
	 * 
	 * <p>the mail group of the group's application 
	 * 
	 * <p>({@link GroupImpl#application})
	 */
	public static final String MAIL_GROUP			= "M"; 
	/**
	 * <p>그룹의 application 중 메일 그룹 (공용)
	 * 
	 * <p>the public mail group of the group's application 
	 */
	public static final String PUBLIC_GROUP			= "P";	// 메일 그룹 (공용)
//	public static final String SANC_GROUP			= "A";	// 결재 그룹 (LDAP 및 그룹간 연동 그룹 포함)
//	public static final String RECEIVE_GROUP		= "S";	// 결재 그룹 (수신자 그룹)
//	public static final String LDAP_GROUP			= "L";	// LDAP 그룹
//	public static final String GONGRAM_GROUP		= "G";	// 공람 그룹
//	public static final String OFFICE_GROUP			= "O";	// 그룹간 연동 그룹
//	public static final String UNGR_GROUP			= "T";	// 통합 수신자 그룹
//	public static final String EXGR_GROUP			= "U";	// 수기입력 수신자 그룹
//	public static final String BMS_GRPPATH			= "D";	// (온나라) 문서보고경로그룹
//	public static final String BMS_RDOC_JOINGRP		= "J";	// (온나라) 문서관리카드 공유그룹
//	public static final String BMS_INSPECT_RCVGRP	= "R";	// (온나라) 문서관리카드 수신그룹
	
	/**
	 * <p>그룹의 멤버중 사용자 유형
	 * 
	 * <p>the member type of the group member
	 * <p>({@link MemberImpl#type})({@link User})
	 */
	public static final char MEMBER_USER 		= '0'; 	// 사용자
	/**
	 * <p>그룹의 멤버중 부서 유형
	 * 
	 * <p>the member type of the group member
	 * <p>({@link MemberImpl#type})({@link Dept})
	 */
	public static final char MEMBER_DEPT 		= '1'; 	// 부서
	public static final char MEMBER_EMAIL 		= '2'; 	// 이메일
	public static final char MEMBER_DUTY 		= '3';	// 직책
	public static final char MEMBER_LDAP 		= '4';	// LDAP 수신처(결재용)
	public static final char MEMBER_SUBDEPT 	= '5';  // 하위 부서 포함 부서	
	public static final char MEMBER_OFFICE 		= '6';  // 그룹간 연동 수신처(결재용)	
	public static final char MEMBER_EXTERNAL 	= '7';	// 외부수신처(결재용)
	
	/**
	 * <p>시스템관리자 권한.
	 * <p>authority for administering the system.
	 * <p>({@link UserAuthImpl#auth})({@link AuthImpl#code})
	 * @since directory 2.0
	 */
	public static final String AUTH_SYSTEM_ADMINISTRATOR			= "SYS";	// 시스템관리자
	/**
	 * <p>전체관리자 권한. 기존 usr_global 의 mgr_type 값으로 존재하던것을 권한으로 뺌.
	 * <p>authority for administering the all organization in the community.
	 * <p>({@link UserAuthImpl#auth})({@link AuthImpl#code})
	 * @since directory 2.0
	 */
	public static final String AUTH_ADMINISTRATOR			= "ADM";	// 전체관리자, 커뮤니티관리자
	/**
	 * <p>수발신 담당자 권한
	 * <p>authority for sending and receiving the approval documents
	 * <p>({@link UserAuthImpl#auth})({@link AuthImpl#code})
	 */
	public static final String AUTH_RECVSEND_MANAGER 		= "D1";	// 수발신담당자
//	public static final String AUTH_SCHEDULE_MANAGER		= "D2";	// 일정관리자
//	public static final String AUTH_NAMECARD_MANAGER 		= "D3";	// 명함관리자
//	public static final String AUTH_DEPT_DOC_MANAGER 		= "D4";	// 문서관리자, 기록물관리자
	/**
	 * <p>부서관리자
	 * <p>authority for administering the department and users in the that
	 * <p>({@link UserAuthImpl#auth})({@link AuthImpl#code})
	 */
	public static final String AUTH_DEPT_MANAGER 			= "D5";	// 부서관리자
//	public static final String AUTH_RECVSEND_DEPUTY 		= "D6";	// 대리수발신담당자
//	public static final String AUTH_REVIEWER_DEPUTY 		= "D7";	// 대리심사자	
//	public static final String AUTH_INSTITUTION_MANAGER    	= "D8";	// 기관관리자
//	public static final String AUTH_SUPER_MANAGER    		= "D9";	// 관리자
	/**
	 * <p>부서장
	 * <p>authority for departments's manager
	 * <p>({@link UserAuthImpl#auth})({@link AuthImpl#code})
	 */
	public static final String AUTH_DEPT_ADMIN 				= "S1";	// 부서장
//	public static final String AUTH_BUDGET_PLANNER 			= "S2";	// 기획예산자
//	public static final String AUTH_DEPT_ADMIN2 			= "S3";	// 기관장	
//	public static final String AUTH_DEPT_INCOLTROLLER 		= "Da";	// 발송담당자
//	public static final String AUTH_DEPT_REPORTCONTROL 		= "Db";	// 보고심사자
//	public static final String AUTH_DEPT_LAWCONTROL 		= "Dc";	// 법규심사자
	public static final String AUTH_ADDITIONAL_OFFICE 		= "U1";	// 겸직 권한
//	public static final String AUTH_BOARD_MANAGER 			= "B1";	// 게시판 관리자
//	public static final String AUTH_FORM_MANAGER 			= "B2";	// 양식 관리자
//	public static final String AUTH_INFO_MANAGER 			= "B3";	// 사이트 관리자
//	public static final String AUTH_RESEARCH_MANAGER 		= "B4";	// 설문 관리자
//	public static final String AUTH_COP_MANAGER 			= "B5";	// 서버 COP 관리자
//	public static final String AUTH_DOC_VIEWER 				= "B6";	// 전체 문서 검토자
//	public static final String AUTH_EXAMPLE_FOLDER_ADMIN 	= "B7";	// 기록물 예시 관리자
//	public static final String AUTH_BOARD_IDENTIFY_MANAGER	= "B8";	// 특정 게시판 관리자	
//	public static final String AUTH_COMPLIANCE_LEADER 		= "Ba";	// 준법감시자
//	public static final String AUTH_BUDGET_CONTROL 			= "Bb";	// 예산통제자
//	public static final String AUTH_FULLTIME_AUDIT 			= "Bc";	// 상근감시자
//	public static final String AUTH_SENIOR_ACTURARY 		= "Bd";	// 선임계리자
//	public static final String AUTH_USTOM_SIGN_TYPE_5 		= "Be";	// 특별협조자5
//	public static final String AUTH_RES_MANAGER 			= "R1";	// 서버 공유설비 관리자
//	public static final String AUTH_RES_IDENTIFY_MANAGER 	= "R2";	// 특정 공유설비 관리자
	public static final String AUTH_DEPT_ISCOMP 		= "CMP";	// 회사여부 권한
	public static final String AUTH_INTERNAL_USER		= "100" ; // 내부사용자
	public static final String AUTH_EXTERNAL_USER		= "110" ; // 외부사용자
	public static final String AUTH_EXTERNAL_USER_MNG = "EUM" ; // 외부사용자 관리 권한
	
	public static final int SCOPE_OBJECT 	= 0; 	// 해당 노드 혹은 전체 트리
	/**
	 * <p>한 레벨 하위
	 * <p>in the one level
	 * <p>({@link OrgContext#getUserList(String, String, int, com.tbs.edirectory.search.SearchKey)})
	 * <p>({@link OrgContext#getDeptList(String, String, int, com.tbs.edirectory.search.SearchKey)})
	 */
	public static final int SCOPE_ONELEVEL 	= 1; 	// 한 레벨 하위
	/**
	 * <p>서브 트리
	 * <p>in the one level and sub-levels
	 * <p>({@link OrgContext#getUserList(String, String, int, com.tbs.edirectory.search.SearchKey)})
	 * <p>({@link OrgContext#getDeptList(String, String, int, com.tbs.edirectory.search.SearchKey)})
	 */
	public static final int SCOPE_SUBTREE 	= 2; 	// 서브 트리
	/**
	 * <p>상위로 루트	
	 * <p>in the tree-based hierarchical path from the base level to the root level
	 * <p>({@link OrgContext#getUserList(String, String, int, com.tbs.edirectory.search.SearchKey)})
	 * <p>({@link OrgContext#getDeptList(String, String, int, com.tbs.edirectory.search.SearchKey)}) 
	 */
	public static final int SCOPE_PATH 		= 3;	// 상위로 루트	
	
	public static final int CONTACT_OBJECT 	= 0; 	// 해당 노드(주소록)
	public static final int CONTACT_USER 	= 1;	// 사용자(주소록)
	public static final int CONTACT_DEPT 	= 2;	// 부서(주소록)
	public static final int CONTACT_GROUP 	= 3;	// 그룹(주소록)
	
	public static String SUPERMANAGER_ID = "000000001";		// 전체관리자 ID
	
	public static final char ABS_WAITING		= '0';    /* 해당 결재자 대기 */
	public static final char ABS_AGT_APPROVAL	= '1';    /* 대리자 결재 */
	public static final char ABS_NO_APPROVAL	= '2';    /* 결재안함 */
	public static final char ABS_NOT_YET	    = '3';    /* 부재 설정했으나 시작 안됨 */
	public static final char ABS_TERM_PASSED    = '4';    /* 부재 설정했으나 시작 안됨 */
	
	public static final String TRUE = "1";
    public static final String FALSE = "0";
	public static final String TRUE_STR = "true";
    public static final String FALSE_STR = "false";
    
    public static final String DEPT_COLUMN_AUDIT = "audit_f";			// 감사부서 여부
    public static final String DEPT_COLUMN_STATUS = "status";		// 부서상태
    public static final String DEPT_COLUMN_ABBREVIATION2 = "abbreviation2";	// 부서약어2
    
    public static final String USER_COLUMN_NAME = "name";			// 사용자명    
    public static final String USER_COLUMN_MNGR = "mngr_type";    // 관리자유형
    public static final String USER_COLUMN_STATUS = "status";    	// 사용자 상태
    
    public static final String USER_COLUMN_APPR_PROFILE = "appr_profile";		// 결재사용
    
    public static final String USER_COLUMN_SANC_LEVEL = "sanc_level";		// 결재등급
    public static final String USER_COLUMN_SANC_PASSWD = "sanc_passwd_chk_f";		// 결재암호검사여부
    public static final String USER_COLUMN_SANC_METHOD = "sanc_method";		// 결재방법
    
    public static final String USER_COLUMN_NOTY_TYPE = "noty_type";		// 알림유형    
    public static final String USER_COLUMN_SANC_RET_NOTY = "sanc_ret_noty_f";		// 결재반송알림여부
    public static final String USER_COLUMN_SANC_END_NOTY = "sanc_end_noty_f";		// 결재완료알림여부
    public static final String USER_COLUMN_SANC_ARV_NOTY = "sanc_arv_noty_f";		// 결재문서도착알리여부
    public static final String USER_COLUMN_SANC_PROC_NOTY = "sanc_proc_noty_f";		// 결재처리알림여부
        
    public static final String MNGR_NONE = "0";		// 일반사용자
    public static final String MNGR_MAIN = "1";		// Community 전체 관리자(admin)
    public static final String MNGR_GUEST = "4";	// 비정규직 사용자
    public static final String MNGR_GLOBAL_COMMUNITY	= "7";		//전체 관리자(aspadmin)
    public static final String MNGR_COMMUNITY_SERVER	= "8";		//Community 서버 관리자(Community 추가시 시스템이 추가함)
    public static final String MNGR_PUBLICVIEWER = "9";				//공람사용자
    /**
     * <p>사용자의 상태 코드 값 중 정상
     * 
     * <p>the normal status of the user's status
     * ({@link UserImpl#status})
     */
    public static final String USER_STATUS_NORMAL	= "1";			// 정상
    /**
     * <p>사용자의 상태 코드 값 중 삭제
     * 
     * <p>the deleted status of the user's status
     * ({@link UserImpl#status})
     */
    public static final String USER_STATUS_DELETED	= "4";			// 삭제됨
    /**
     * <p>사용자의 상태 코드 값 중 숨김
     * 
     * <p>the hidden status of the user's status
     * ({@link UserImpl#status})
     */
    public static final String USER_STATUS_HIDDEN	= "8";			// 숨김
    /**
     * <p>부서의 상태 코드 값 중 정상
     * 
     * <p>the normal status of the department's status
     * ({@link DeptImpl#status})
     */
    public static final String DEPT_STATUS_NORMAL	= "1";			// 정상
    /**
     * <p>부서의 상태 코드 값 중 삭제
     * 
     * <p>the deleted status of the department's status
     * ({@link DeptImpl#status})
     */
    public static final String DEPT_STATUS_DELETED	= "4";			// 삭제됨
    /**
     * <p>부서의 상태 코드 값 중 숨김
     * 
     * <p>the hidden status of the department's status
     * ({@link DeptImpl#status})
     */
    public static final String DEPT_STATUS_HIDDEN	= "8";			// 숨김
    
    
    /*
     * batch 관련 코드
     */
    
    public static final String BATCH_TYPE_USER	= "0";
	public static final String BATCH_TYPE_DEPT	= "1";
	public static final String BATCH_TYPE_POS	= "2";
	public static final String BATCH_TYPE_RANK	= "3";
	public static final String BATCH_TYPE_DUTY	= "4";
	public static final String BATCH_TYPE_AUTH	= "5";

	public static final String BATCH_OP_ADD		= "0";
	public static final String BATCH_OP_MODIFY	= "1";
	public static final String BATCH_OP_DELETE	= "2";
	public static final String BATCH_OP_UNION	= "3";
	public static final String BATCH_OP_ADDMODIFY	= "4";
	
	public static final String BATCH_STATUS_WAIT	= "0";
	public static final String BATCH_STATUS_SUCCESS	= "1";
	public static final String BATCH_STATUS_ONGOING	= "2";
	public static final String BATCH_STATUS_FAIL	= "3";
	
	public static final String BATCH_ERROR_TYPE_SYSTEM		= "0";
	public static final String BATCH_ERROR_TYPE_ORG			= "1";
	public static final String BATCH_ERROR_TYPE_APPROVAL	= "2";
	
    public final static String BATCH_END_OF_INFO 	= "###";
    public final static String BATCH_ADD_KEYWORD 	= "ADD";
    public final static String BATCH_MODIFY_KEYWORD = "MODIFY";
    public final static String BATCH_DELETE_KEYWORD = "DELETE";
    public final static String BATCH_ADDMODIFY_KEYWORD	= "ADDMODIFY";
    
    public final static String BATCH_KEY_OPERATION 	= "*OPERATION";

    //	static value for field indexing
    public final static int BATCH_OPERATION = 0;

    public final static String BATCH_PARSE_USER = "0";
    public final static String BATCH_PARSE_DEPT = "1";
    public final static String BATCH_PARSE_POS  = "2";
    public final static String BATCH_PARSE_RANK = "3";
    public final static String BATCH_PARSE_DUTY = "4";
    public final static String BATCH_PARSE_AUTH = "5";
    public final static String BATCH_PARSE_UNKNOWN = "9";
    
    public final static String BATCH_PARSE_ENCODING = "UTF-8";
    
    
    public final static int BATCH_MSG_MAX_SIZE = 3500;
    public final static int BATCH_SUMMARY_MAX_SIZE = 100;
    
    public final static int BATCH_EXPORT_MAX_BUF_LEN = 4096;    
    public final static int BATCH_LIST_PER_PROCESS = 100;
    
    public final static int PAGE_LIST_PER_PAGE = 15;
    public final static int PAGE_SHORT_CUT_SIZE = 10;
    
    public final static String PAGE_DISPLAY_DEFAULT 		= "0";
    public final static String PAGE_DISPLAY_WITH_ABSENCE 	= "1";
    public final static String PAGE_DISPLAY_WITH_LOGIN_INFO	= "2";     
    
    public final static String PAGE_ORDER_DEFAULT 	= "0";    
    public final static String PAGE_ORDER_POSITION 	= "1";    
    public final static String PAGE_ORDER_NAME		= "2";    
    public final static String PAGE_ORDER_EMAIL		= "3";    
    public final static String PAGE_ORDER_PHONE		= "4";
    public final static String PAGE_ORDER_MOBILE	= "5";
    public final static String PAGE_ORDER_DEPTNAME  = "6";
    public final static String PAGE_ORDER_ABSENCE_START		= "7";
    public final static String PAGE_ORDER_ABSENCE_END		= "8";
    public final static String PAGE_ORDER_ABSENCE_REASON	= "9";
    public final static String PAGE_ORDER_ABSENCE_MESSAGE	= "10";
    public final static String PAGE_ORDER_LOGIN_DATE		= "11";
    public final static String PAGE_ORDER_SUBTREE 			= "12";
    public final static String PAGE_ORDER_SEC_LEVEL			= "13";
    public final static String PAGE_ORDER_SEQ   			= "14";
    public final static String PAGE_ORDER_EMPCODE  			= "15";
    public final static String PAGE_ORDER_NAME_ENG			= "16";
    public final static String PAGE_ORDER_RANK				= "17";
    public final static String PAGE_ORDER_LOCK_F			= "18";
    public final static String PAGE_ORDER_DUTY				= "19";
    
    public final static String DEPT_ORDER_NAME = "2";
    public final static String DEPT_ORDER_PAR_ID = "3";
    public final static String DEPT_ORDER_SEQ = "5";
    public final static String DEPT_ORDER_DEPTH = "99";
    
    
    /*******************************************
     *   결재 Application 에러코드 (3XXX )     *
	 *******************************************/
    public final static int HOMS_NULL_DATA_LIST = 3202;

	/**
	 * 부서
	 */
	public static final int ORG_DEPT_SID = 65537;
	/**
	 * 수신자 그룹(결재그룹(사용자, 전체관리자 설정),
	 */
	public static final int ORG_GRUP_SID = 65538;
	/**
	 * 타기관 (전자문서유통)
	 */
	public static final int ORG_ORGU_SID = 65536;
	/**
	 * 미지정
	 */
	public static final int ORG_UNKNOWN_SID = 0;
	/**
	 * 사용자
	 */
	public static final int ORG_USER_SID = 1;

    /**
     * LOGINSTATUS_TRUE - 일반 로그인 상태 (메신저를 제외한 모든 어플리케이션)
     * LOGINSTATUS_MESSENGER - 메신저 로그인 상태
     * LOGINSTATUS_TRUE_MESSENGER - 일반/메신저 동시 로그인 상태
     */
	public final static String LOGINSTATUS_TRUE = "1";
    public final static String LOGINSTATUS_MESSENGER = "2";
    public final static String LOGINSTATUS_TRUE_MESSENGER = "3";
    
    /**
     * LOGIN시 Application Type.
     * 기본은 default 이며, UC 의 경우 "uc"
     * mUC 의 경우 device id
     */
    public final static String LOGIN_APP_DEFAULT = "DEFAULT";
    public final static String LOGIN_APP_UC_PC = "UC-PC";
    public final static String LOGIN_APP_UC_ANDROID = "UC-ANDROID";
    public final static String LOGIN_APP_UC_IPHONE = "UC-IPHONE";
    public final static String LOGIN_APP_UC_TABLET = "UC-TABLET";
    public final static String LOGIN_APP_UC_IPAD = "UC-IPAD";
	
	
	/**
	 * 부서 이동 위치 - 0.하부로, 1. 동등 레벨 위로, 2. 동등 레벨 아래로
	 */
	public final static String DEPT_MOVE_SUB = "0";
	public final static String DEPT_MOVE_ABOVE = "1";
	public final static String DEPT_MOVE_BELOW = "2";
	
	/**
	 * 권한 타입 : 권한자(사용자) -> 대상자(없음,자신)
	 */
	public final static String AUTH_TYPE_USER = "0";
	/**
	 * 권한 타입 : 권한자(사용자) -> 대상자(사용자)
	 * 예) 겸직
	 */
	public final static String AUTH_TYPE_USER_USER = "1";
	/**
	 * 권한 타입 : 권한자(사용자) -> 대상자(부서)
	 * 예) 부서관리자 등
	 */
	public final static String AUTH_TYPE_USER_DEPT = "2";
	/**
	 * 권한 타입 : 권한자(부서) -> 대상자(없음,자신)
	 * 예) 현재 없음, 
	 */
	public final static String AUTH_TYPE_DEPT = "3";
	
	/***
	 * 암호 규칙 문자
	 */
    public static final String	NUMBER_RULE	= "0123456789";
    public static final String	NUMBER_RULE2	= "9876543210";

    public static final String	ALPHA_RULE = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    public static final String	ALPHA_RULE2 = "zyxwvutsrqponmlkjihgfedcbaZYXWVUTSRQPONMLKJIHGFEDCBA";

    public static final String  SPECIAL_RULE = "~`!@#$%^&*()_+-=<>?,./[]{}|;:\\'\"";
     
    public static final String IS_GREATER_THAN_CONDITION = "1";
    public static final String IS_LESS_THAN_CONDITION = "2";
    public static final String IS_GREATER_THAN_OR_EQUAL_CONDITION = "3";
    public static final String IS_LESS_THAN_OR_EQUAL_CONDITION = "4";
    
    public static final String GW_MNGR_TYPE_ADMIN = "1";
	/**
	 * 부서내의 사용자들에 대한 목록 순서를 지정을 위한 값 
	 * - pos : 직위순(디폴트값) 으로 정렬, 직위순(직위의 보안등급) > 순번 > 이름 의 순으로 정렬 {@link OrgConstant#LISTUSERS_ORDER_POS} 
	 * - seq : 순번으로 정렬, 순번 > 보안등급 > 이름 의 순으로 정렬 {@link OrgConstant#LISTUSERS_ORDER_SEQ}
	 * - sec_level : 보안등급(직위등급순) > 이름 {@link OrgConstant#LISTUSERS_ORDER_SEC_LEVEL}
	 * This value support to order the users of the department.
	 * - pos : order by the "Position"(default) , Position(Position's Security Level) > User's sequence > User's name in order.{@link OrgConstant#LISTUSERS_ORDER_POS} 
	 * - seq : order by user's sequence, user's sequence > Position(Position's Security Level) > User's name in order.{@link OrgConstant#LISTUSERS_ORDER_SEQ}
	 * - sec_level : Position's Security Level > User's name {@link OrgConstant#LISTUSERS_ORDER_SEC_LEVEL}
	 */
    public static final String LISTUSERS_ORDER_POS = "pos";
    public static final String LISTUSERS_ORDER_SEQ = "seq";
    public static final String LISTUSERS_ORDER_SEC_LEVEL = "sec_level";
    
	//----------- 암호 유형(문성광:2000-03-07)
    /**
     * 암호 유형이 로그인 암호
     */
	public static final String	PASSWORD_TYPE_LOGIN	= "0";
    /**
     * 암호 유형이 결재 암호(현재 사용안함)
     */
	public static final String	PASSWORD_TYPE_SANC	= "1";
	
	public static final String DBMSNAME_ORACLE = "oracle";
	
	/**
	 * 사용자그룹(계층,공용)의 그룹 계층 타입
	 */
	public static final String DIRGROUP_TYPE_HIERARCHY = "H";	// 그룹 계층
	/**
	 * 사용자그룹(계층,공용)의 그룹 타입
	 */
	public static final String DIRGROUP_TYPE_GROUP = "G";		// 그룹
	/**
     * 사용자그룹(계층,공용)의 상태 코드 값 중 정상
     */
    public static final String DIRGROUP_STATUS_NORMAL	= "1";	// 정상
    /**
     * 사용자그룹(계층,공용)의 상태 코드 값 중 숨김
     */
    public static final String DIRGROUP_STATUS_HIDDEN	= "8";	// 숨김
    
    public static final String IMAGE_FILE_TYPE_PHOTO = "photo";
    public static final String IMAGE_FILE_TYPE_SIGN = "sign";
    public static final String IMAGE_FILE_TYPE_IMAGE = "image";
    
    public static final String DIRECTORY_ORGFOLDER_BASEDIR = "edirectory.orgfolder.basedir";
}