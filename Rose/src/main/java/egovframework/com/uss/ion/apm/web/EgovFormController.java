package egovframework.com.uss.ion.apm.web;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.uss.ion.apm.service.EgovFormService;
import egovframework.com.uss.ion.apm.service.EgovLabelService;
import egovframework.com.uss.ion.apm.service.FormVO;
import egovframework.com.uss.ion.apm.service.LabelVO;
import egovframework.com.uss.ion.apu.EgovRequestUtilService;
import egovframework.com.uss.ion.apu.Pagination;
import egovframework.com.uss.ion.apu.PathUtil;
import egovframework.com.uss.ion.apv.service.DeptApprovalAuthVO;
import egovframework.com.uss.ion.apv.service.EgovApprovalAuthService;
import egovframework.com.uss.ion.apv.service.UserApprovalAuthVO;
import egovframework.com.uss.omt.OrgConstant;
import egovframework.com.uss.omt.service.DeptVO;
import egovframework.com.uss.omt.service.EgovDeptService;
import egovframework.com.uss.omt.web.EgovDutyController;
import egovframework.com.uss.umt.service.EgovUserManageService;
import egovframework.com.uss.umt.service.UserManageVO;

/**
 * 게시물 관리를 위한 컨트롤러 클래스
 * 
 * @author 공통서비스개발팀 이삼섭
 * @since 2009.06.01
 * @version 1.0
 * @see
 *
 *      <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------       --------    ---------------------------
 *   2009.3.19  이삼섭          최초 생성
 *   2009.06.29	한성곤			2단계 기능 추가 (댓글관리, 만족도조사)
 *   2011.07.01 안민정		 	댓글, 스크랩, 만족도 조사 기능의 종속성 제거
 *   2011.8.26	정진오			IncludedInfo annotation 추가
 *   2011.09.07 서준식           유효 게시판 게시일 지나도 게시물이 조회되던 오류 수정
 *      </pre>
 */

@Controller
public class EgovFormController {

    @Resource(name = "EgovFormService")
    private EgovFormService formService;

    @Resource(name = "EgovUserManageService")
    private EgovUserManageService userManageService;

    @Resource(name = "EgovDeptService")
    private EgovDeptService deptService;

    @Resource(name = "EgovApprovalAuthService")
    private EgovApprovalAuthService apprAuthService;

    @Resource(name = "EgovRequestUtilService")
    private EgovRequestUtilService requestUtilService;

    private static final Logger LOGGER = LoggerFactory.getLogger(EgovFormController.class);

    @RequestMapping("/listForm.do")
    public ModelAndView getFormList(@ModelAttribute("page") Pagination page, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ModelAndView model = new ModelAndView();
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
        if (isAuthenticated) {
            UserManageVO user = userManageService.getUser(loginVO.getUniqId());
            DeptVO dept = deptService.getDept(user.getOrgnztId());
            String companyId = user.getCompanyId();
            DeptVO topDept = deptService.getTopDept(user.getOrgnztId());
            
            int totalCount = 0;
            if (page.getOrderColumn() == null || "".equals(page.getOrderColumn())) {
                page.setOrderColumn("2");
                page.setOrderMethod("asc");
            }
            List<FormVO> formList = formService.getFormList(companyId, page);
            /* listform 에 총 list Count 추가 */
            totalCount = formService.getTotalFormCount(companyId, page);
            page.setTotalRecordCount(totalCount);

            model.addObject("page", page);
            model.addObject("user", user);
            model.addObject("formList", formList);
            model.addObject("dept", dept);
            model.addObject("topDept", topDept);
            model.setViewName("egovframework/com/uss/ion/apm/EgovFormList");
        } else {
            model.setViewName("egovframework/com/uat/uia/EgovLoginUsr");
        }
        return model;
    }

    @RequestMapping("/inputFormInfo.do")
    public ModelAndView inputFormInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView model = new ModelAndView();
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        if (isAuthentication(request, response)) {
            String uid = loginVO.getUniqId();
            UserManageVO user = userManageService.getUser(uid);
            DeptVO dept = deptService.getDept(user.getOrgnztId());
            DeptVO topDept = deptService.getTopDept(user.getOrgnztId());

            model.addObject("user", user);
            model.addObject("dept", dept);
            model.addObject("topDept", topDept);
            model.setViewName("egovframework/com/uss/ion/apm/EgovAddForm");
        } else {
            model.setViewName("egovframework/com/uat/uia/EgovLoginUsr");
        }
        return model;
    }

    @RequestMapping(value = "/addForm.do")
    public String addForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        if (isAuthentication(request, response)) {
	        String uid = loginVO.getUniqId();
	        UserManageVO user = userManageService.getUser(uid);
	        FormVO form = requestUtilService.getForm(request);
	        
	        int formType = Integer.parseInt(request.getParameter("formType"));
	        form = formService.insertForm(form, formType);
	
	        return "forward:/listForm.do";
        } else {
    		return "forward:/index.do";
        }
    }

    @RequestMapping(value = "/viewForm.do")
    public ModelAndView viewForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView model = new ModelAndView();
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        if (isAuthentication(request, response)) {
            UserManageVO user = userManageService.getUser(loginVO.getUniqId());
            DeptVO dept = deptService.getDept(user.getOrgnztId());
            DeptVO topDept = deptService.getTopDept(user.getOrgnztId());
            String formId = request.getParameter("selectedFormId");
            FormVO form = formService.getForm(formId);
            File formFile = PathUtil.getFormPath(form);
            
            String formFilePath = null;
            if (formFile != null) {
                formFilePath = formFile.getAbsolutePath();
            }

            model.addObject("user", user);
            model.addObject("form", form);
            model.addObject("formFilePath", formFilePath);
            model.addObject("dept", dept);
            model.addObject("topDept", topDept);
            model.setViewName("egovframework/com/uss/ion/apm/EgovViewForm");
        } else {
            model.setViewName("egovframework/com/uat/uia/EgovLoginUsr");
        }
        return model;
    }

    @RequestMapping(value = "/modifyForm.do")
    public String modifyForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        if (isAuthentication(request, response)) {
	        UserManageVO user = userManageService.getUser(loginVO.getUniqId());
	        FormVO form = requestUtilService.getForm(request);
	        
	        int formType = Integer.parseInt(request.getParameter("formType"));
	        form = formService.updateForm(form, formType);
	        
	        return "forward:/listForm.do";
        } else {
    		return "forward:/index.do";
        }
    }

    @RequestMapping(value = "/viewModifyForm.do")
    public ModelAndView viewModifyForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView model = new ModelAndView();
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        if (isAuthentication(request, response)) {
            UserManageVO user = userManageService.getUser(loginVO.getUniqId());
            DeptVO dept = deptService.getDept(user.getOrgnztId());
            DeptVO topDept = deptService.getTopDept(user.getOrgnztId());
            String formId = request.getParameter("formId");
            FormVO form = formService.getForm(formId);

            String formFilePath = null;
            if (form != null) {
                File formFile = PathUtil.getFormPath(form);
                if (formFile != null) {
                    formFilePath = formFile.getAbsolutePath();
                }
            }

            model.addObject("user", user);
            model.addObject("form", form);
            model.addObject("formFilePath", formFilePath);
            model.addObject("dept", dept);
            model.addObject("topDept", topDept);
            model.setViewName("egovframework/com/uss/ion/apm/EgovViewModifyForm");
        } else {
            model.setViewName("egovframework/com/uat/uia/EgovLoginUsr");
        }
        return model;
    }

    @RequestMapping(value = "/deleteFormList.do")
    public ModelAndView deleteFormList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView model = new ModelAndView();
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        if (isAuthentication(request, response)) {
            try {
                UserManageVO user = userManageService.getUser(loginVO.getUniqId());
                String[] formIdList= request.getParameterValues("formId");

                formService.deleteFormList(formIdList);

                model.addObject("user", user);
                model.addObject("result", "success");
                model.setViewName("egovframework/com/uss/ion/apm/EgovFormResult");
            } catch (Exception e) {
                model.addObject("result", "error");
                model.addObject("error_msg", e.getMessage());
                model.setViewName("egovframework/com/uss/ion/apm/EgovFormResult");
            }
        } else {
            model.setViewName("egovframework/com/uat/uia/EgovLoginUsr");
        }
        return model;
    }

    @RequestMapping(value = "/deleteForm.do")
    public ModelAndView deleteForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView model = new ModelAndView();
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        if (isAuthentication(request, response)) {
            try {
                UserManageVO user = userManageService.getUser(loginVO.getUniqId());
                String formId = request.getParameter("formId");

                formService.deleteForm(formId);

                model.addObject("user", user);
                model.addObject("result", "success");
                model.setViewName("egovframework/com/uss/ion/apm/EgovFormResult");
            } catch (Exception e) {
                model.addObject("result", "error");
                model.addObject("error_msg", e.getMessage());
                model.setViewName("egovframework/com/uss/ion/apm/EgovFormResult");
            }
        } else {
            model.setViewName("egovframework/com/uat/uia/EgovLoginUsr");
        }
        return model;
    }

    @RequestMapping(value="/downloadForm.do")
    public void downloadForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String formId = (String) request.getParameter("formId");
        FormVO form = formService.getForm(formId);
        if (form == null){throw new Exception("The form, formId[" + formId + "] does not exist.");}
        
        try 
        {
            File formFile = PathUtil.getFormPath(form);
            if (formFile == null || !formFile.exists()) {
                throw new Exception("The form file, formId[" + form.getFormId() + "], path["
                        + formFile.getAbsolutePath() + "] does not exist.");
            }
            requestUtilService.transfer(request, response, form.getFormName() + ".html", formFile, "text/html");
        } catch (Exception e) {
            throw e;
        }
    }
    
    protected boolean isAuthentication(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
        if (isAuthenticated == false) {
            return false;
        }
        return true;
    }
}
