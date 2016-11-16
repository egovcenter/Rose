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
import egovframework.com.uss.omt.service.EgovDeptService;
import egovframework.com.uss.omt.service.EgovPositionService;
import egovframework.com.uss.omt.service.PositionVO;
import egovframework.com.uss.umt.service.EgovUserManageService;
import egovframework.com.uss.umt.service.UserManageVO;

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
public class EgovPositionController {
	
	@Resource(name = "EgovPositionService")
	private EgovPositionService positionService;
	
    @Resource(name = "EgovUserManageService")
    private EgovUserManageService userManageService;
    
	@Resource(name = "EgovDeptService")
	private EgovDeptService deptService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovPositionController.class);
	
    @RequestMapping(value="/positionForm.do")
	public ModelAndView positionForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView model = new ModelAndView();
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		UserManageVO user = userManageService.getUser(loginVO.getUniqId());
		String posiId = request.getParameter("POSI_ID");
		
		PositionVO posi = null;
		if (StringUtils.isNotBlank(posiId)) {
			posi = positionService.getPosition(posiId);
		}
		model.addObject("user", user);
		model.addObject("posi", posi);
		model.setViewName("egovframework/com/uss/omt/EgovPositionFormModal");
		return model;
	}   	
    
    @RequestMapping(value="/positionList.do")
	public ModelAndView positionListView(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView model = new ModelAndView();
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		UserManageVO user = userManageService.getUser(loginVO.getUniqId());
		
		String deptId = loginVO.getOrgnztId();
		String companyId = user.getCompanyId();
		List<PositionVO> list = positionService.getPositionListByDeptId(companyId);
		list = buildDeletablePosition(list);
		        
		model.addObject("list", list);
		model.addObject("user", user);
		model.setViewName("egovframework/com/uss/omt/EgovPositionList");
		return model;
	}    
    
	private List buildDeletablePosition(List list) throws Exception {
		List newList = new ArrayList();
		for (Object obj : list) {
			PositionVO posi = (PositionVO) obj;
			int count = userManageService.getUserCntByPosition(posi.getPosiId());
			posi.setDeletable(count == 0);
			newList.add(posi);
		}
		list = newList;
		return list;
	}
    
	/**
	 * 직위 등록/수정 저장
	 * [AM-M01-002-002] 직위 등록
	 * [AM-M01-002-003] 직위 수정
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws DeptException 
	 */
    @RequestMapping(value="/positionSave.do")
	public String positionSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		PositionVO posi = new PositionVO();
		posi.setPosiId(request.getParameter("POSI_ID"));
		posi.setPosiNm(request.getParameter("POSI_NM"));
		posi.setPosiLv(Integer.parseInt(request.getParameter("POSI_LV")));
		
		boolean isAdd = false;
		/* position add */
		if (StringUtils.isBlank(posi.getPosiId())) {
			isAdd = true;
		}
		
		if (isAdd) {
			/* position add*/
			posi.setDeptId(loginVO.getOrgnztId());
			positionService.insertPosition(posi);
		} else {
			/* position modify*/
			posi.setDeptId(request.getParameter("DEPT_ID"));
			positionService.updatePosition(posi);
		}
		return "forward:/positionList.do";
	}     
    
	/**
	 * [AM-M01-002-005] 직위 내용 조회 
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws DeptException 
	 */
    @RequestMapping(value="/positionView.do")
	public ModelAndView positionView(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		UserManageVO user = userManageService.getUser(loginVO.getUniqId());
		ModelAndView model = new ModelAndView();
		String posiId = request.getParameter("POSI_ID");

        int count = userManageService.getUserCntByPosition(posiId);
		PositionVO posi = positionService.getPosition(posiId);
		posi.setDeletable(count == 0);
		
		model.addObject("posi", posi);
		model.addObject("user", user);
		model.setViewName("egovframework/com/uss/omt/EgovPositionViewModal");
		return model;
	}
    
    @RequestMapping(value="/positionDelete.do")
    public String posiDelete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String idList = request.getParameter("POSI_ID");
        String[] ids = null;

        if (idList != null) {
            ids = idList.split(",");
        }
        for (int i = 0; i < ids.length; i++) {
            String posiId = ids[i];
            if ((posiId != null) && (posiId.isEmpty() == false)) {
                positionService.deletePosition(posiId);
            }
        }
        return "forward:/positionList.do";
    }
}