package egovframework.com.uss.ion.apv.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.com.uss.ion.apv.service.DeptApprovalAuthVO;
import egovframework.com.uss.ion.apv.service.EgovApprovalAuthService;
import egovframework.com.uss.ion.apv.service.UserApprovalAuthVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("EgovApprovalAuthService")
public class EgovApprovalAuthServiceImpl extends EgovAbstractServiceImpl implements EgovApprovalAuthService{
	
	@Resource(name = "ApprovalAuthDAO")
	private ApprovalAuthDAO approvalAuthDAO;
	
	@Override
	public UserApprovalAuthVO getUserApprovalAuth(String userId) throws Exception {
		UserApprovalAuthVO vo = new UserApprovalAuthVO();
		vo.setUserId(userId);
		
		return approvalAuthDAO.selectUserApprovalAuth(vo);
	}

	@Override
	public DeptApprovalAuthVO getDeptApprovalAuth(String deptId) throws Exception{
		DeptApprovalAuthVO vo = new DeptApprovalAuthVO();
		vo.setDeptId(deptId);
		
		return approvalAuthDAO.selectDeptApprovalAuth(vo);
	}
	
}
