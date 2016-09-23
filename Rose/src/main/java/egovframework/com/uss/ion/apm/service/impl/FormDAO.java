package egovframework.com.uss.ion.apm.service.impl;

import java.util.List;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.com.uss.ion.apm.service.FormVO;
import egovframework.com.uss.ion.apu.Pagination;
import egovframework.com.uss.omt.service.DeptVO;

import org.springframework.stereotype.Repository;


@Repository("FormDAO")
public class FormDAO extends EgovComAbstractDAO {
	
	public void insertForm(FormVO formInf) {
		insert("FormDAO.insertForm", formInf);
	}
	
	public void updateForm(FormVO formInf) {
		update("FormDAO.updateForm", formInf);
	}
	
	public void deleteForm(FormVO formInf) {
		delete("FormDAO.deleteForm", formInf);
	}

	public FormVO selectForm(FormVO formInf) {
		return (FormVO)select("FormDAO.selectForm", formInf);
	}

	public List<FormVO> selectRegisterFormList(DeptVO vo) {
	    return (List<FormVO>) list("FormDAO.selectRegisterFormList", vo);
	}
	
	public List<FormVO> selectDraftFormList(DeptVO vo) {
	    return (List<FormVO>) list("FormDAO.selectDraftFormList", vo);
	}
	
	public List<FormVO> selectForms(FormVO formInf, Pagination pagination) {
		return (List<FormVO>)listWithPaging("FormDAO.selectForms", formInf, (pagination.getCurrentPageNo()-1), pagination.getPageSize());
	}
	
	public int selectFormCnt(FormVO formInf) {
		return (Integer)select("FormDAO.selectFormCnt", formInf);
	}

	/*listform 에 총 list Count 추가*/
	public int selectListFormCnt(FormVO formInf, Pagination pagination) {
	    int count = (Integer)select("FormDAO.selectListFormCnt", formInf);

		return count;
	}
}
