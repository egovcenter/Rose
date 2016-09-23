package egovframework.com.uss.omt.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.com.uss.omt.service.AbsenceVO;

@Repository("AbsenceDAO")
public class AbsenceDAO  extends EgovComAbstractDAO {

	/*
	 * 주의사항: 테이블명은 반드시 소문자 사용할 것. 테이블명의 대소문자 구별을 함.
	 */	
	public void insertAbsence(AbsenceVO absence) throws Exception {
		insert("AbsenceDAO.insertAbsence", absence);
	}

	public void updateAbsence(AbsenceVO absence) throws Exception {
		update("AbsenceDAO.updateAbsence", absence);
	}

	public void deleteAbsence(AbsenceVO absence) throws Exception {
		delete("AbsenceDAO.deleteAbsence", absence);
	}

	public AbsenceVO getAbsence(AbsenceVO absence) throws Exception {
		return (AbsenceVO) select("AbsenceDAO.selectAbsence", absence);
	}
	
	public List<AbsenceVO> getAbsenceList() throws Exception {
		return (List<AbsenceVO>) list("AbsenceDAO.selectAbsenceList");
	}

}
