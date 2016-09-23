package egovframework.com.uss.omt.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import egovframework.com.uss.omt.DeptTreeView;
import egovframework.com.uss.omt.OrgConstant;
import egovframework.com.uss.omt.service.DeptVO;
import egovframework.com.uss.omt.service.EgovDeptService;
import egovframework.com.uss.omt.service.TreePosVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

/**
 * 일반 로그인, 인증서 로그인을 처리하는 비즈니스 인터페이스 클래스
 * @author 공통서비스 개발팀 박지욱
 * @since 2009.03.06
 * @version 1.0
 * @see
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2009.03.06  박지욱          최초 생성 
 *  2011.08.26  서준식          EsntlId를 이용한 로그인 추가
 *  </pre>
 */
@Service("EgovDeptService")
public class EgovDeptServiceImpl extends EgovAbstractServiceImpl implements EgovDeptService  {


    @Resource(name="DeptDAO")
    private DeptDAO deptDAO;
    
    @Resource(name="egovDeptIdGnrService")
    private EgovIdGnrService idGnrService;
    
	@Override
	public void insertDept(DeptVO dept) throws Exception {
		deptDAO.insertDept(dept);		
	}

	@Override
	public void updateDept(DeptVO dept) throws Exception {
		deptDAO.updateDept(dept);
	}

	@Override
	public void deleteDept(String deptId) throws Exception {
		DeptVO vo = new DeptVO();
		vo.setDeptId(deptId);
		
		deptDAO.deleteDept(vo);
	}

	@Override
	public DeptVO getDept(String deptId) throws Exception {
		DeptVO vo = new DeptVO();
		vo.setDeptId(deptId);
		
		return deptDAO.selectDept(vo);
	}

	@Override
	public List<DeptVO> getDeptTree(String deptId) throws Exception {
	    List<DeptVO> deptTree = new ArrayList<DeptVO>();
		DeptVO vo = new DeptVO();
		vo.setDeptId(deptId);
		
		DeptVO deptInfo = deptDAO.selectDept(vo);
		deptTree.add(0, deptInfo);
		deptTree.addAll(deptDAO.selectDeptTree(vo));
		return deptTree;
	}
	
	@Override
	public List<DeptVO> getCompanyTree(){
		return deptDAO.selectCompanyTree();
	}

	@Override
	public TreePosVO getTreePos(String deptId) throws Exception {
		DeptVO vo = new DeptVO();
		vo.setDeptId(deptId);
		
		return (TreePosVO)deptDAO.selectTreePos(vo); 
	}

	@Override
	public String getNextDeptId() throws Exception {
		return idGnrService.getNextStringId();
	}
	    
	@Override
    public List<DeptVO> getDeptList(String communityID, String base, int scope) throws Exception {
        List<DeptVO> list = null;
        if (scope == OrgConstant.SCOPE_PATH) {
            list = deptDAO.selectDeptTree(communityID, communityID, scope);
            System.out.println("OrgConstant.SCOPE_PATH start...");
            
            List<DeptVO> newlist = null;
            boolean foundBase = false;
            int baseDeptDepth = 0;
            
            for (Object obj : list) {
                DeptVO dept = (DeptVO) obj;
                if (dept.getDeptLevel() == 1) {
//                  foundBase = false;
                    if (foundBase == true) {
//                        logger.debug("[0] communityID={}, base={}, deptId={}, deptNM={}", communityID, base, dept.getDeptId(), dept.getDeptNm());
                        break;
                    } else {
                        newlist = new ArrayList<DeptVO>();
                    }
                }
                
                if (foundBase) {
                    if (baseDeptDepth >= dept.getDeptLevel()) {
                        baseDeptDepth = Integer.MAX_VALUE;
                    }
                }
                
                if (newlist != null) {
                    newlist.add(dept);
                }
                
//                logger.debug("[1] communityID={}, base={}, deptId={}, deptNM={}", communityID, base, dept.getDeptId(), dept.getDeptNm());
                if (StringUtils.equals(dept.getDeptId(), base)) {
//                    logger.debug("OrgConstant.SCOPE_PATH fire...");
                    foundBase = true;
                    baseDeptDepth = dept.getDeptLevel();
                }
            }
            list = newlist;
        } else {
            list = deptDAO.selectDeptTree(communityID, base, scope);
        }
        return list;
    }
    
    @Override
    public DeptVO getTopDept(String deptId) throws Exception {
        DeptVO dept = getDept(deptId);
        if(dept == null) return null;
        if("1".equals(dept.getDeptTopF())){
            return dept;
        }
        
        return getTopDept(dept.getDeptParId());
    }

    @Override
    public String getCompanyId(String deptId) throws Exception {
        DeptVO dept = new DeptVO();
        dept.setDeptId(deptId);
        
        return (String)deptDAO.selectCompanyId(dept);
    }
}
