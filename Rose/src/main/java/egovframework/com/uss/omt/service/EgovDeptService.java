package egovframework.com.uss.omt.service;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;
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
public interface EgovDeptService {

	public DeptVO getDept(String deptId) throws Exception;

	public void insertDept(DeptVO dept) throws Exception;
	
	public void updateDept(DeptVO dept) throws Exception;
	
	public void deleteDept(String deptId) throws Exception;
	
	public List<DeptVO> getDeptTree(String deptId) throws Exception;
	
	public List<DeptVO> getCompanyTree();
	
	public List<DeptVO> getDeptList(String communityID, String base, int scope) throws Exception;
	
	public TreePosVO getTreePos(String deptId) throws Exception; 
	
	public DeptVO getTopDept(String deptId) throws Exception;
	
	public String getNextDeptId() throws Exception;
	
	public String getCompanyId(String deptId) throws Exception;
}
