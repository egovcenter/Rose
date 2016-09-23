package egovframework.com.uss.omt.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.uss.ion.apm.service.EgovLabelService;
import egovframework.com.uss.ion.apm.service.LabelVO;
import egovframework.com.uss.omt.OrgConstant;
import egovframework.com.uss.omt.service.DeptVO;
import egovframework.com.uss.omt.service.EgovDeptService;
import egovframework.com.uss.umt.service.EgovUserManageService;
import egovframework.com.uss.umt.service.UserManageVO;
import egovframework.com.utl.sim.service.EgovFileScrty;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

/**
 * 게시물 관리를 위한 컨트롤러 클래스
 * @author 공통서비스개발팀 이삼섭
 * @since 2009.06.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------       --------    ---------------------------
 *   2009.3.19  이삼섭          최초 생성
 *   2009.06.29	한성곤			2단계 기능 추가 (댓글관리, 만족도조사)
 *   2011.07.01 안민정		 	댓글, 스크랩, 만족도 조사 기능의 종속성 제거
 *   2011.8.26	정진오			IncludedInfo annotation 추가
 *   2011.09.07 서준식           유효 게시판 게시일 지나도 게시물이 조회되던 오류 수정
 * </pre>
 */


@Controller
public class EgovDeptController {
	
	@Resource(name = "EgovDeptService")
	private EgovDeptService deptService;
	
	@Resource(name = "egovDeptIdGnrService")
    private EgovIdGnrService idgenService;
	
    @Resource(name = "EgovUserManageService")
    private EgovUserManageService userService;

    @Resource(name = "EgovLabelService")
    private EgovLabelService labelService;
    
	/** userManageService */
	@Resource(name = "EgovUserManageService")
	private EgovUserManageService userManageService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovDeptController.class);
	
    @RequestMapping(value="/deptSelectionModal.do")
	public ModelAndView deptSelectionModal(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
    	UserManageVO user = userService.getUser(loginVO.getUniqId());
    	String deptId = loginVO.getOrgnztId();
		ModelAndView model = new ModelAndView();
		/* OrgTree */
		List<DeptVO> tree = null;
		if(user.getUserType().equals("S")){
			tree = deptService.getCompanyTree();
			model.addObject("tree", tree);
		}else{
			tree = deptService.getDeptTree(deptId);
			model.addObject("tree", tree);
		}
		model.addObject("user", user);
		model.setViewName("egovframework/com/uss/omt/cmm/EgovDeptSelectionModal");
		return model;
	}

    @RequestMapping(value="/deptView.do")
	public ModelAndView deptView(HttpServletRequest request, HttpServletResponse response, boolean isAdd) throws Exception{
		System.out.println(String.format("--- %s Start ...", "deptView"));
		ModelAndView model = new ModelAndView();
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		UserManageVO user = userService.getUser(loginVO.getUniqId());
		
		/* 선택한 부서 정보 */
		String seletedDeptId = request.getParameter("deptId");
		DeptVO dept = new DeptVO(); 
		dept = deptService.getDept(seletedDeptId);
		
    	// 수정할 부서의 PAR 객체
    	DeptVO 	baseDept = new DeptVO();
    	baseDept = deptService.getDept(dept.getDeptParId());
    	model.addObject("DEPT_PAR_NM", baseDept.getDeptNm());
		
		model.addObject("user", user);
		model.addObject("dept", dept);
		
		String viewType = request.getParameter("action");
		
		if (viewType.equalsIgnoreCase("getDeptJson")) {
		    model.setViewName("egovframework/com/uss/omt/deptJsonInfo");
		} else if (viewType.equalsIgnoreCase("manageDept")) {
		    model.setViewName("egovframework/com/uss/omt/EgovDeptViewModal");
		} else {
	          model.setViewName("egovframework/com/uss/omt/EgovDeptViewModal");
		}
		return model;
	}    
    
    @RequestMapping(value="/deptAdd.do")
    public ModelAndView deptAdd(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	LOGGER.info("--- {} Start ...", "deptAdd");
    	ModelAndView model = new ModelAndView();
    	LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
    	UserManageVO user = userService.getUser(loginVO.getUniqId());
    	
    	// 기준 부서를 위한 객체
    	String 	baseDeptId = request.getParameter("deptId");
    	DeptVO 	baseDept = new DeptVO();
    	
    	if(!(baseDeptId.equals("") || baseDeptId == null || baseDeptId.equalsIgnoreCase("undefined")))
    	{
    		baseDept = deptService.getDept(baseDeptId);
    		model.addObject("DEPT_PAR_ID", baseDept.getDeptId());  
    		model.addObject("DEPT_PAR_NM", baseDept.getDeptNm());
    	} else if (user.getUserType().equals("S")) {
            baseDept = deptService.getDept(user.getOrgnztId());
            model.addObject("DEPT_PAR_ID", baseDept.getDeptId());  
            model.addObject("DEPT_PAR_NM", baseDept.getDeptNm());
    	}
    	
    	// 새로운 부서를 위한 객체
    	DeptVO 	dept = new DeptVO();

    	//logger.debug("{}", dept.toString());
		
    	model.addObject("dept", dept);
    	model.addObject("user", user);
    	
    	if(user.getUserType().equalsIgnoreCase("S")){
    		model.setViewName("egovframework/com/uss/omt/EgovCompanyForm");
    	}else{
    		model.setViewName("egovframework/com/uss/omt/EgovDeptForm");
    	}
    	return model;
    }    
    @RequestMapping(value="/deptModify.do")
    public ModelAndView deptModify(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	LOGGER.info("--- {} Start ...", "deptModify");
    	ModelAndView model = new ModelAndView();
    	LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
    	UserManageVO user = userService.getUser(loginVO.getUniqId());
    	// 수정할 부서 객체
    	String 	deptId = request.getParameter("deptId");
    	DeptVO 	dept = new DeptVO();
    	dept = deptService.getDept(deptId);
    	
    	// 수정할 부서의 PAR 객체
    	DeptVO 	baseDept = new DeptVO();
    	baseDept = deptService.getDept(dept.getDeptParId());

    	//logger.debug("{}", dept.toString());
		
    	model.addObject("dept", dept);
    	model.addObject("user", user);
    	// 현재 선택된 부서가 기준 부서가 된다.
    	model.addObject("DEPT_PAR_ID", baseDept.getDeptId());  
    	model.addObject("DEPT_PAR_NM", baseDept.getDeptNm());
    	
    	if(dept.getDeptTopF().equals("1")){
    		model.setViewName("egovframework/com/uss/omt/EgovCompanyForm");
    	}else{
    		model.setViewName("egovframework/com/uss/omt/EgovDeptForm");
    	}
    	return model;
    }    
    private String getCurrentTime() {
        Calendar cal = Calendar.getInstance();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(cal.getTime());
    }
	/**
	 * 부서 저장
	 * [AM-M01-001-002] 부서 등록
	 * [AM-M01-001-003] 부서 수정
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws DeptException
	 */
	@RequestMapping(value="/deptSave.do")
	public String deptSave(HttpServletRequest request, HttpServletResponse response) throws Exception{
		LOGGER.info("--- {} Start ...", "deptSave");
    	LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
    	UserManageVO user = userService.getUser(loginVO.getUniqId());
    	
		DeptVO dept = new DeptVO();
		dept.setDeptId(request.getParameter("DEPT_ID"));
		dept.setDeptNm(request.getParameter("DEPT_NM"));
		dept.setDeptCd(request.getParameter("DEPT_CD"));
		dept.setDeptRboxF(request.getParameter("DEPT_RBOX_F"));
		dept.setDeptBoxF(request.getParameter("DEPT_BOX_F"));
		dept.setDeptTopF(request.getParameter("DEPT_TOP_F"));
		dept.setDeptDc(request.getParameter("DEPT_DC"));
		dept.setDeptSeq(Integer.parseInt(request.getParameter("DEPT_SEQ")));
		
		String deptParId = null;
		if(request.getParameter("DEPT_PAR_NM").equalsIgnoreCase("ROOT")||request.getParameter("DEPT_PAR_NM").equals("")){
			deptParId = user.getCompanyId();
		}else{
			deptParId = request.getParameter("DEPT_PAR_ID");
		}
		
		boolean isAdd = false;
		if (StringUtils.isBlank(dept.getDeptId())) {
			// Lower
			String id = deptService.getNextDeptId();
			dept.setDeptId(id);
			isAdd = true;
		}
		
		/* user save */
		if(request.getParameter("USER_LOGIN") != ""){
			UserManageVO newUser = new UserManageVO();
			newUser.setEmplyrNm(request.getParameter("USER_NM"));
			newUser.setSeq(request.getParameter("USER_SEQ"));
			newUser.setEmplNo(request.getParameter("USER_EM_CD"));
			newUser.setPositionId(request.getParameter("POSI_ID"));
			newUser.setDutyId(request.getParameter("DUTY_ID"));
			newUser.setSecurityLvl(Integer.parseInt(request.getParameter("USER_SLVL")));
			newUser.setEmplyrId(request.getParameter("USER_LOGIN"));
			newUser.setSbscrbDe(getCurrentTime());
			newUser.setUpdateDt(getCurrentTime());
			newUser.setOrgnztId(dept.getDeptId());
	
			String userlpwd = request.getParameter("USER_LPWD");
			String userspwd = request.getParameter("USER_SPWD");
			/* 비밀번호 암호화 */
			if (StringUtils.isNotBlank(userlpwd)) {
				String md5userlpwd = EgovFileScrty.encryptPassword(userlpwd, newUser.getEmplyrId());
				newUser.setPassword(md5userlpwd);
			}
			if (StringUtils.isNotBlank(userspwd)) {
				String md5userspwd = EgovFileScrty.encryptPassword(userspwd, newUser.getEmplyrId());
				newUser.setApprovalPassword(md5userspwd);
			}
			if(request.getParameter("saveUserType").equalsIgnoreCase("DEPT_ADMIN_USER_NM")){
				newUser.setComapnyId(dept.getDeptId());
				newUser.setUserType("A");
				String uniqId = userManageService.insertUser(newUser);
				dept.setDeptAdminUserId(uniqId);
			}else{
				newUser.setComapnyId(deptService.getCompanyId(user.getOrgnztId()));
				newUser.setUserType("D");
				String uniqId = userManageService.insertUser(newUser);
				dept.setDeptBoxUserId(uniqId);
			}
		}else{
    		dept.setDeptRboxUserId(request.getParameter("DEPT_RBOX_USER_ID"));
    		dept.setDeptSboxUserId(request.getParameter("DEPT_SBOX_USER_ID"));
    		dept.setDeptAdminUserId(request.getParameter("DEPT_ADMIN_USER_ID"));
    		dept.setDeptBoxUserId(request.getParameter("DEPT_BOX_USER_ID"));
    		
    		if(!(dept.getDeptTopF().equals("1"))){
    			UserManageVO boxUser = userManageService.getUser(dept.getDeptBoxUserId());
    			boxUser.setOrgnztId(dept.getDeptId());
    			boxUser.setUserType("D");
    			userManageService.updateUser(boxUser);
    		}
		}

		LOGGER.debug(dept.toString());
		
		if (isAdd) {
		    dept.setBaseOrgnztId(deptParId);
			dept.setFuncType("CA");

            dept.setDeptParId(deptParId);
			dept.setDeptStatus("1");
			dept.setDeptUpdateDt(new Date());
			deptService.insertDept(dept);
			
			labelService.insertRootLabel(dept.getDeptId());
		} else {
			/* IF MODIFY */
			dept.setBaseOrgnztId(deptParId);
			dept.setFuncType("U");
			dept.setDeptStatus("1");
			dept.setDeptUpdateDt(new Date());
			deptService.updateDept(dept);
			dept.setFuncType("CM");

			deptService.updateDept(dept);
		}
		return "forward:/userList.do?action=getDetailUserInfo&&checkType=multi";
	}
	
	@RequestMapping(value="/deptDelete.do")
	public String deptDelete(HttpServletRequest request, HttpServletResponse response) throws Exception{
		LOGGER.info("--- {} Start ...", "deptDelete");
		
		String deptId = request.getParameter("deptId");
		deptService.deleteDept(deptId);
		
		return "forward:/userList.do?action=getDetailUserInfo&&checkType=multi";
	}    
	
	@RequestMapping(value="/initDeptTree.do")
    public ModelAndView initOrgTree(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
        UserManageVO user = userService.getUser(loginVO.getUniqId());
        String companyId = user.getCompanyId();
        DeptVO CompanyDept = deptService.getDept(companyId);
        boolean isExpandAll = OrgConstant.TRUE_STR.equals(request.getParameter("isExpandAll"));

        String base = companyId;
        int scope = OrgConstant.SCOPE_SUBTREE;
        List<DeptVO> tree = deptService.getDeptList(CompanyDept.getDeptId(), base, scope);
        
        List<DeptVO> newtree = new ArrayList<DeptVO>();
        DeptVO dtv = new DeptVO();
        dtv.setDeptId(user.getOrgnztId());
        dtv.setDeptNm(CompanyDept.getDeptNm());
        dtv.setDeptParId(CompanyDept.getDeptParId());
        
        newtree.add(dtv);
        for (Object row : tree) {
            dtv = (DeptVO) row;
            dtv.setDeptLevel(dtv.getDeptLevel() +1);
            newtree.add(dtv);
        }
        
        String data = buildTreeData(CompanyDept, newtree, isExpandAll);
        
        ModelAndView model = new ModelAndView();
        model.addObject("data", data);
        model.setViewName("egovframework/com/uss/omt/EgovDeptList");
        
        return model;          
    }
    
    private String buildTreeData(DeptVO dept, List<DeptVO> pathList, boolean isExpandAll) throws Exception {
        String children = "";
        for (DeptVO tmp : pathList) {
            if (tmp.getDeptParId().equals(dept.getDeptId())) { // child dept
                if (StringUtils.isNotEmpty(children)) children += ", ";
                children += buildTreeData(tmp, pathList, isExpandAll);
            }
        }

        StringBuffer sb = new StringBuffer();
        sb.append("{\"key\": \"");
        sb.append(dept.getDeptId() + "\",");
        sb.append("\"title\": \"");
        sb.append(dept.getDeptNm() + "\",");
        sb.append("\"isFolder\": ");
        if (children.length() > 0) {
            sb.append("true");
        } else {
            sb.append("false");
        }
        sb.append(", \"expand\": ");
        sb.append(isExpandAll + ",");
        sb.append("\n\"children\": [");
        sb.append(children);
        sb.append("]}");
        
        return sb.toString();
    }
}
