package egovframework.com.uss.omt.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.com.uss.omt.service.DeptVO;
import egovframework.com.uss.omt.service.TreePosVO;

@Repository("DeptDAO")
public class DeptDAO extends EgovComAbstractDAO {

	
	public void insertDept(DeptVO dept) {
		list("proc_dept_add",dept);
	}

	public void updateDept(DeptVO dept) {
		list("proc_dept_modify",dept);
	}

	public void deleteDept(DeptVO dept) {
		delete("DeptDAO.deleteDept", dept);
	}
	
	public DeptVO selectDept(DeptVO dept) {
		return (DeptVO)select("DeptDAO.selectDept", dept);
	}

	public List<DeptVO> selectDeptTree(DeptVO dept) {
		return (List<DeptVO>) list("DeptDAO.selectDeptTree", dept);
	}
	
	public List<DeptVO> selectCompanyTree(){
		return (List<DeptVO>) list("DeptDAO.selectCompanyTree");
	}
	
	public String selectCompanyId(DeptVO dept) {
	    return (String)select("DeptDAO.selectCompanyId", dept);
	}
	
    public List<DeptVO> selectDeptTree(String communityId, String base, int scope) {
        DeptVO vo = new DeptVO();
        
        vo.setDeptId(communityId);
        vo.setBaseOrgnztId(base);
        vo.setDeptLevel(scope);

        return (List<DeptVO>) list("DeptDAO.selectDeptTreeWithDepth", vo);
    }

	public TreePosVO selectTreePos(DeptVO dept) {
		return (TreePosVO) select("DeptDAO.selectTreePos", dept);
	}

    public List<DeptVO> selectAllDeptTree(DeptVO dept) {
        return (List<DeptVO>) list("DeptDAO.selectAllDeptTree", dept);
    }
}
