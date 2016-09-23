package egovframework.com.uss.omt.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.com.uss.omt.service.DutyVO;

@Repository("DutyDAO")
public class DutyDAO extends EgovComAbstractDAO {
	/*
	 * 주의사항: 테이블명은 반드시 소문자 사용할 것. 테이블명의 대소문자 구별을 함.
	 */
	
	public void insertDuty(DutyVO duty) throws Exception {
		insert("DutyDAO.insertDuty", duty);
	}
	
	public void updateDuty(DutyVO duty) throws Exception {
		update("DutyDAO.updateDuty", duty);
	}
	
	public void deleteDuty(DutyVO duty) throws Exception {
		delete("DutyDAO.deleteDuty", duty);
	}
	
	public DutyVO selectDuty(DutyVO duty) throws Exception {
		return (DutyVO)select("DutyDAO.selectDuty", duty);
	}

	public List<DutyVO> selectDutyList(String deptId) throws Exception {
		DutyVO duty = new DutyVO();
		duty.setDeptId(deptId);
		
		return (List<DutyVO>) list("DutyDAO.selectDutyList", duty);
	}
	
	public int selectDutyCnt(String deptId) throws Exception {
		DutyVO duty = new DutyVO();
		duty.setDutyId(deptId);
		
		return (Integer) select("DutyDAO.selectDutyCnt", duty);
	}
}
