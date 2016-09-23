package egovframework.com.uss.ion.apv.service;

import java.util.List;

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
public interface EgovApprovalAuthService {

	/**
	 * 사용자의 결재 권한 정보
	 * @param userID 사용자ID
	 * @return 결재 권한 정보
	 * @throws Exception 
	 */
	public UserApprovalAuthVO getUserApprovalAuth(String userId) throws Exception;

	/**
	 * 부서의 문서함 보유 여부 정보
	 * @param deptId 부서ID
	 * @return 문서함 보유 정보
	 */
	public DeptApprovalAuthVO getDeptApprovalAuth(String deptId)throws Exception;
	
}
