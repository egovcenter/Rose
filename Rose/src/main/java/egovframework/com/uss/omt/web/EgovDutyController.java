package egovframework.com.uss.omt.web;

import java.util.ArrayList;
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

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.uss.omt.service.DutyVO;
import egovframework.com.uss.omt.service.EgovDeptService;
import egovframework.com.uss.omt.service.EgovDutyService;
import egovframework.com.uss.omt.service.PositionVO;
import egovframework.com.uss.umt.service.EgovUserManageService;
import egovframework.com.uss.umt.service.UserManageVO;
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
public class EgovDutyController {
	
	@Resource(name = "EgovDutyService")
	private EgovDutyService dutyService;
	
	@Resource(name = "EgovUserManageService")
	private EgovUserManageService userManageService;
	
	@Resource(name = "EgovDeptService")
	private EgovDeptService deptService;
	
    @Resource(name = "egovDutyIdGnrService")
    private EgovIdGnrService idgenService;
   
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovDutyController.class);
	
    @RequestMapping(value="/dutyForm.do")
	public ModelAndView dutyForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView model = new ModelAndView();
    	LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
    	UserManageVO user = userManageService.getUser(loginVO.getUniqId());
    	
    	String deptId = loginVO.getOrgnztId();
    	String dutyId = request.getParameter("DUTY_ID");
    	String dutyNm = request.getParameter("DUTY_NM");
    	DutyVO duty = null;
    	
    	if (dutyId != null) {
    	    duty = new DutyVO();
    	    duty.setDeptId(deptId);
    	    duty.setDutyId(dutyId);
    	    duty.setDutyNm(dutyNm);
    	    model.addObject("duty", duty);
    	}
		model.addObject("user", user);
		model.setViewName("egovframework/com/uss/omt/EgovDutyFormModal");
		return model;
	}	
    
    @RequestMapping(value="/dutyView.do")
    public ModelAndView dutyView(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView model = new ModelAndView();
    	LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
    	UserManageVO user = userManageService.getUser(loginVO.getUniqId());
    	
    	String deptId = loginVO.getOrgnztId();
    	String dutyId = request.getParameter("DUTY_ID");
    	String dutyNm = request.getParameter("DUTY_NM");
    	
        int count = userManageService.getUserCntByDuty(dutyId);
        DutyVO duty = new DutyVO();
        duty.setDeletable(count == 0);
		duty.setDeptId(deptId);
		duty.setDutyId(dutyId);
		duty.setDutyNm(dutyNm);
		model.addObject("duty", duty);
    	model.addObject("user", user);
    	model.setViewName("egovframework/com/uss/omt/EgovDutyViewModal");
    	return model;
    }	
 
    @RequestMapping(value="/dutyList.do")
	public ModelAndView dutyListView(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView model = new ModelAndView();
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		UserManageVO user = userManageService.getUser(loginVO.getUniqId());
		
		String deptId = loginVO.getOrgnztId();
		String companyId = user.getCompanyId();
		List<DutyVO> dutyList = dutyService.getDutyList(companyId);
		dutyList = buildDeletableDuty(dutyList);
		
		model.addObject("user", user);
		model.addObject("list", dutyList);
        model.setViewName("egovframework/com/uss/omt/EgovDutyList");
		return model;
	}
    
    private List buildDeletableDuty(List list) throws Exception {
        List newList = new ArrayList();
        for (Object obj : list) {
            DutyVO duty = (DutyVO) obj;
            int count = userManageService.getUserCntByDuty(duty.getDutyId());
            duty.setDeletable(count == 0);
            newList.add(duty);
        }
        list = newList;
        return list;
    }

    @RequestMapping(value="/dutyDelete.do")
    public String dutyDelete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String idList = request.getParameter("DUTY_ID");
        String[] ids = null;
        if (idList != null) {
            ids = idList.split(",");
        }
        for (int i = 0; i < ids.length; i++) {
            String dutyId = ids[i];
            if ((dutyId != null) && (dutyId.isEmpty() == false)) {
                dutyService.deleteDuty(dutyId);
            }
        }
        return "forward:/dutyList.do";
    }
    
    @RequestMapping(value="/dutySave.do")
    public String dutySave(HttpServletRequest request, HttpServletResponse response) throws Exception {
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
        DutyVO duty = new DutyVO();
        duty.setDutyId(request.getParameter("DUTY_ID"));
        duty.setDutyNm(request.getParameter("DUTY_NM"));
        
        boolean isAdd = false;
        /* position add */
        if (StringUtils.isBlank(duty.getDutyId())) {
            isAdd = true;
        }

        if (isAdd) {
            /* position add*/
            String dutyId = idgenService.getNextStringId();
            duty.setDutyId(dutyId);
            duty.setDeptId(loginVO.getOrgnztId());
            dutyService.insertDuty(duty);
        } else {
            /* position modify*/
            if (request.getParameter("DUTY_NM") != null) {
                duty.setDutyNm(request.getParameter("DUTY_NM"));
            }
            if (request.getParameter("DEPT_ID") != null) {
                duty.setDeptId(request.getParameter("DEPT_ID"));
            }
            dutyService.updateDuty(duty);
        }
        return "forward:/dutyList.do";
    }       
}