package egovframework.com.uss.ion.apv.web;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.lang.StringUtils;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.uss.ion.apm.service.EgovFormService;
import egovframework.com.uss.ion.apm.service.EgovLabelService;
import egovframework.com.uss.ion.apm.service.FormVO;
import egovframework.com.uss.ion.apm.service.LabelVO;
import egovframework.com.uss.ion.apu.Pagination;
import egovframework.com.uss.ion.apu.PathUtil;
import egovframework.com.uss.ion.apu.EgovRequestUtilService;
import egovframework.com.uss.omt.service.DeptVO;
import egovframework.com.uss.omt.service.EgovDeptService;
import egovframework.com.uss.umt.service.EgovUserManageService;
import egovframework.com.uss.umt.service.UserManageVO;
import egovframework.com.uss.ion.apv.ApprovalConstants;
import egovframework.com.uss.ion.apv.service.DeptApprovalAuthVO;
import egovframework.com.uss.ion.apv.service.EgovApprovalAuthService;
import egovframework.com.uss.ion.apv.service.UserApprovalAuthVO;

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
public class EgovApprovalAuthController {
	
	@Resource(name = "EgovApprovalAuthService")
	private EgovApprovalAuthService approvalAuthService;

	@Resource(name = "EgovUserManageService")
	private EgovUserManageService userService;
	
	@Resource(name = "EgovLabelService")
	private EgovLabelService labelService;
	
	@Resource(name = "EgovDeptService")
	private EgovDeptService deptService;
	
	@Resource(name = "EgovFormService")
	private EgovFormService formService;
	
	@Resource(name = "EgovRequestUtilService")
	private EgovRequestUtilService requestUtilService;
	
	protected boolean isAuthorization(String uid, String relID, String authes) {
		// 권한 검사. 권한이 없는 경우 throw new NoAuthorizationException(OrgErrorCode.ORG_NO_AUTHORIZATION, "message");
		// OrgConstant.AUTH_ADMINISTRATOR 의 경우 relID 는 자기자신이며
		// OrgConstant.AUTH_DEPT_MANAGER 의 경우 relID 는 대상 부서이며, 대상부서와 하위부서에 대한 권한을 가진다. 
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
        
        return isAuthenticated;
	}
	
	protected boolean isAuthentication(HttpServletRequest request, HttpServletResponse response) {
		
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
        
        return isAuthenticated;
	}
	
	@RequestMapping("/mngLabel.do")
	protected ModelAndView mngLabel(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
    	UserManageVO user = userService.getUser(loginVO.getUniqId());
    	ModelAndView model = new ModelAndView();
		ModelAndView labelTree = labelService.getLabelTree(user.getUniqId(), user.getOrgnztId());
		/* OrgTree */
		List<DeptVO> tree = null;
		if(user.getUserType().equals("S")){
			tree = deptService.getCompanyTree();
			model.addObject("tree", tree);
		}else{
			tree = deptService.getDeptTree(user.getOrgnztId());
			model.addObject("tree", tree);
		}
		
		model.addObject("labelTree", labelTree.getModel().get("labelList"));
    	model.addObject("user", user);
    	model.setViewName("egovframework/com/uss/omt/EgovLabelForm");
    	return model;
	}
	
	@RequestMapping("/viewLabel.do")
	protected ModelAndView viewLabel(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView model = new ModelAndView();
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
        
		if(isAuthenticated){
			UserManageVO user = userService.getUser(loginVO.getUniqId());
			
			String labelID = request.getParameter("labelID");
			
			LabelVO label = labelService.getLabel(labelID);
			String deptId = label.getDeptId();
			DeptVO dept = deptService.getDept(deptId);
			DeptVO topDept = deptService.getTopDept(loginVO.getOrgnztId());

			model.addObject("user", user);
			model.addObject("label",label);
			model.addObject("dept", dept);
			model.addObject("topDept", topDept);
			model.setViewName("admin/viewLabel");
		}else{
			model.setViewName("egovframework/com/uat/uia/EgovLoginUsr");
		}
		
		return model;
	}
	@RequestMapping("/viewAddLabel.do")
	protected ModelAndView viewAddLabel(HttpServletRequest request,HttpServletResponse response)throws Exception{
        ModelAndView model = new ModelAndView();
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
        
		if(isAuthenticated){
			UserManageVO user = userService.getUser(loginVO.getUniqId());
			String deptId = user.getOrgnztId();
			
			deptId = StringUtils.isEmpty(deptId) ? user.getOrgnztId() : deptId;
			DeptVO dept = deptService.getDept(deptId);
			DeptVO topDept = deptService.getTopDept(user.getOrgnztId());

			List<LabelVO> labelList = labelService.getLabelTreeOfDeptID(deptId);
			if(labelList == null || labelList.size() < 1){
				LabelVO rootLabel = labelService.getRootLabel(user.getOrgnztId());
				model.addObject("rootLabel", rootLabel);
			}
			ModelAndView labelTree = labelService.getLabelTree(user.getUniqId(), user.getOrgnztId());
			
			model.addObject("labelTree", labelTree.getModel().get("labelList"));
			model.addObject("user", user);
			model.addObject("dept", dept);
			model.addObject("topDept", topDept);
			model.setViewName("egovframework/com/uss/omt/EgovAddLabel");
		}else{
			model.setViewName("egovframework/com/uat/uia/EgovLoginUsr");
		}
		return model;
	}
	
	@RequestMapping("/addLabel.do")
	protected ModelAndView addLabel(HttpServletRequest request,HttpServletResponse response){
        ModelAndView model = new ModelAndView();
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

		if(isAuthenticated){
			try {
				UserManageVO user = userService.getUser(loginVO.getUniqId());
				String targetLabelID = request.getParameter("targetLabelID");
				String targetPosition = request.getParameter("targetPosition"); //lower, upper, sibling
				LabelVO label = requestUtilService.getLabel(request);
				
				label = labelService.insertLabel(label, targetLabelID, targetPosition, user);
				model.addObject("user", user);
				model.addObject("label", label);
				model.addObject("result", "success");
				model.setViewName("egovframework/com/uss/omt/cmm/EgovLabelResult");
			} catch (Exception e) {
				model.addObject("result", "error");
				model.addObject("error_msg", e.getMessage());
				model.setViewName("egovframework/com/uss/omt/cmm/EgovLabelResult");
			}
		} else {
			model.setViewName("egovframework/com/uat/uia/EgovLoginUsr");
		}
		return model;
	}
	
	@RequestMapping("/viewModifyLabel.do")
	protected ModelAndView viewModifyLabel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView model = new ModelAndView();
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
        
		if(isAuthenticated){
            UserManageVO user = userService.getUser(loginVO.getUniqId());
			String labelId = request.getParameter("labelId");
			LabelVO label = labelService.getLabel(labelId);
			String deptId = label.getDeptId();
			DeptVO dept = deptService.getDept(deptId);
			DeptVO topDept = deptService.getTopDept(user.getOrgnztId());
			
			model.addObject("user", user);
			model.addObject("label", label);
			model.addObject("dept", dept);
			model.addObject("topDept", topDept);
			model.setViewName("egovframework/com/uss/omt/EgovModifyLabel");
		}else{
			model.setViewName("egovframework/com/uat/uia/EgovLoginUsr");
		}
		return model;
	}
	
	@RequestMapping("/modifyLabel.do")
	protected ModelAndView modifyLabel(HttpServletRequest request, HttpServletResponse response){
		ModelAndView model = new ModelAndView();
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

		if(isAuthenticated){
			try {
	            UserManageVO user = userService.getUser(loginVO.getUniqId());
				String labelId = request.getParameter("labelId");
				String targetLabelID = request.getParameter("targetLabelID");
				String targetPosition = request.getParameter("targetPosition"); //lower, upper, sibling
				LabelVO label = requestUtilService.getLabel(request);
				
				labelService.updateLabel(label, targetLabelID, targetPosition, user);
				label = labelService.getLabel(labelId);
				model.addObject("user", user);
				model.addObject("label", label);
				model.addObject("result", "success");
				model.setViewName("egovframework/com/uss/omt/cmm/EgovLabelResult");
			} catch (Exception e) {
//				logger.error("fail to modifyLabel", e);
				model.addObject("result", "error");
				model.addObject("error_msg", e.getMessage());
				model.setViewName("egovframework/com/uss/omt/cmm/EgovLabelResult");
			}
		}else{
			model.setViewName("egovframework/com/uat/uia/EgovLoginUsr");
		}
		return model;
	}
	
	@RequestMapping("/deleteLabel.do")
	protected ModelAndView deleteLabel(HttpServletRequest request,HttpServletResponse response){
		ModelAndView model = new ModelAndView();
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

		if(isAuthenticated){
			try{
			    UserManageVO user = userService.getUser(loginVO.getUniqId());
				String labelId = request.getParameter("labelId");
				
				labelService.deleteLabel(labelId);
				model.addObject("result", "success");
				model.setViewName("egovframework/com/uss/omt/cmm/EgovLabelResult");
			}catch(Exception e) {
//				logger.error("fail to deleteLabel", e);
				model.addObject("result", "error");
				model.addObject("error_msg", e.getMessage());
				model.setViewName("egovframework/com/uss/omt/cmm/EgovLabelResult");
			}
		}else{
			model.setViewName("egovframework/com/uat/uia/EgovLoginUsr");
		}
		return model;
	}
	
	@RequestMapping("/viewLabelTreeLayer.do")
	protected ModelAndView viewLabelTreeLayer(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView model = new ModelAndView();
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(isAuthenticated){
            UserManageVO user = userService.getUser(loginVO.getUniqId());
			String deptId = user.getOrgnztId();
			deptId = StringUtils.isEmpty(deptId) ? user.getOrgnztId() : deptId;
			
//			ApprovalLabel appLabel = new ApprovalLabel();
//			ApprovalAuth appAuth = new ApprovalAuth();
			
			List<LabelVO> labelList = labelService.getLabelTreeOfDeptID(deptId);
			UserApprovalAuthVO userAuth = approvalAuthService.getUserApprovalAuth(user.getUniqId());
			DeptApprovalAuthVO deptAuth = approvalAuthService.getDeptApprovalAuth(deptId);
			LabelVO rootLabel = labelService.getRootLabel(user.getOrgnztId());
			
			model.addObject("rootLabel", rootLabel);
			model.addObject("labelList",labelList);
			model.addObject("userAuth",userAuth);
			model.addObject("deptAuth",deptAuth);
			model.addObject("user", user);
			model.setViewName("egovframework/com/uss/omt/cmm/EgovLabelSelectionModal");
		}else{
			model.setViewName("egovframework/com/uat/uia/EgovLoginUsr");
		}
		return model;
	}
	
	@RequestMapping("/mngForm.do")
	protected ModelAndView mngForm(@ModelAttribute("page") Pagination page, HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView model = new ModelAndView();
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(isAuthenticated){
            UserManageVO user = userService.getUser(loginVO.getUniqId());
			DeptVO dept = deptService.getDept(user.getOrgnztId());
			DeptVO topDept = deptService.getTopDept(user.getOrgnztId());
			
			if( page.getOrderColumn()==null || "".equals(page.getOrderColumn()) ){
				page.setOrderColumn("2");
				page.setOrderMethod("asc");
			}
			
			List<FormVO> formList = formService.getFormList(dept.getDeptId(), page);
			
			String dept_id = user.getOrgnztId();

			UserApprovalAuthVO userAuth = approvalAuthService.getUserApprovalAuth(user.getUniqId());
			DeptApprovalAuthVO deptAuth = approvalAuthService.getDeptApprovalAuth(dept_id);
			
			model.addObject("formList", formList);
			model.addObject("userAuth",userAuth);
			model.addObject("deptAuth",deptAuth);
			model.addObject("user", user);
			model.addObject("dept", dept);
			model.addObject("topDept", topDept);
			model.setViewName("admin/mngForm");
		}else{
			model.setViewName("egovframework/com/uat/uia/EgovLoginUsr");
		}
		
		return model;
	}	
}
