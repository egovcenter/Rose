package egovframework.com.uss.ion.apv.service.impl;

import java.util.Iterator;
import java.util.List;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.com.uss.ion.apv.service.AttachFileVO;
import egovframework.com.uss.ion.apv.service.DeptApprovalAuthVO;
import egovframework.com.uss.ion.apv.service.UserApprovalAuthVO;

import org.springframework.stereotype.Repository;


/**
 * Data access class for managing attached file information
 * @author paul
 * @since 2016.02.04
 * @version 1.0
 * @see
 *
 * <pre>
 * << Modification Information >>
 *   
 *   Date         Owner       Content
 *  ---------    --------    ---------------------------
 *   2016.02.02   paul        initial
 * </pre>
 */
@Repository("ApprovalAuthDAO")
public class ApprovalAuthDAO extends EgovComAbstractDAO {

	/**
	 * Retrieve user authentication information for give user 
	 * @param userInf
	 * @return
	 */
	public UserApprovalAuthVO selectUserApprovalAuth(UserApprovalAuthVO vo) {
		String recvAuth = (String)select("ApprovalDAO.selectUserApprovalRecvAuth",vo);
		String sendAuth = (String)select("ApprovalDAO.selectUserApprovalSendAuth",vo);
		
		vo.setSendAuth(sendAuth);
		vo.setReceiveAuth(recvAuth);
		return vo;
	}


	/**
	 * Retrieve approval authentication information for given department
	 * @param deptInf
	 * @return
	 */
	public DeptApprovalAuthVO selectDeptApprovalAuth(DeptApprovalAuthVO vo) {
		return (DeptApprovalAuthVO) select("ApprovalDAO.selectDeptApprovalAuth", vo);
	}	
}
