package egovframework.com.uss.ion.apm.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.com.uss.ion.apm.service.LabelVO;


@Repository("LabelDAO")
public class LabelDAO extends EgovComAbstractDAO {

	public List<LabelVO> selectLabelTreeOfDeptID(String deptId) throws Exception {
		LabelVO vo = new LabelVO();
		vo.setDeptId(deptId);
		
		return (List<LabelVO>)list("LabelDAO.selectLableTreeOfDeptId", vo);
	}
	
	public LabelVO selectRootLabel(String deptId) {
	    LabelVO vo = new LabelVO();
	    vo.setDeptId(deptId);
	    
	    return (LabelVO)select("LabelDAO.selectRootLabel", vo);
	}
	
	public LabelVO selectLabel(String labelID) throws Exception {
		LabelVO vo = new LabelVO();
		vo.setLabelId(labelID);
		
		return (LabelVO)select("LabelDAO.selectLabel", vo);
	}
	
	public void insertLabel(LabelVO label) throws Exception {
		insert("LabelDAO.insertLabel", label);
	}
	
	public void updateLabel(LabelVO label) throws Exception {
		update("LabelDAO.updateLabel", label);
	}

	public void deleteLabel(String labelId) throws Exception {
		LabelVO vo = new LabelVO();
		vo.setLabelId(labelId);
		
		delete("LabelDAO.deleteLabel", vo);
	}
	
	public void shiftSeq(String parentID, int baseSeq) throws Exception {
		LabelVO vo = new LabelVO();
		vo.setLabelSeq(baseSeq);
		vo.setLabelParentId(parentID);
		
		update("LabelDAO.updateSeq", vo);
	}
	
	public int selectMaxSeq(String parentID) throws Exception {
		return (Integer) select("LabelDAO.selectMaxSeq", parentID);
	}
}
