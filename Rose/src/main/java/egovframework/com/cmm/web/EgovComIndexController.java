package egovframework.com.cmm.web;

/**
 * 컴포넌트 설치 후 설치된 컴포넌트들을 IncludedInfo annotation을 통해 찾아낸 후
 * 화면에 표시할 정보를 처리하는 Controller 클래스
 * <Notice>
 * 		개발시 메뉴 구조가 잡히기 전에 배포파일들에 포함된 공통 컴포넌트들의 목록성 화면에
 * 		URL을 제공하여 개발자가 편하게 활용하도록 하기 위해 작성된 것으로,
 * 		실제 운영되는 시스템에서는 적용해서는 안 됨
 *      실 운영 시에는 삭제해서 배포해도 좋음
 * <Disclaimer>
 * 		운영시에 본 컨트롤을 사용하여 메뉴를 구성하는 경우 성능 문제를 일으키거나
 * 		사용자별 메뉴 구성에 오류를 발생할 수 있음
 * @author 공통컴포넌트 정진오
 * @since 2011.08.26
 * @version 2.0.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *  수정일		수정자		수정내용
 *  -------    	--------    ---------------------------
 *  2011.08.26	정진오 		최초 생성
 *  2011.09.16  서준식		컨텐츠 페이지 생성
 *  2011.09.26  이기하		header, footer 페이지 생성
 * </pre>
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import egovframework.com.cmm.IncludedCompInfoVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.annotation.IncludedInfo;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.uss.omt.service.DeptVO;
import egovframework.com.uss.omt.service.EgovDeptService;
import egovframework.com.uss.omt.service.TreePosVO;
import egovframework.com.uss.umt.service.EgovUserManageService;
import egovframework.com.uss.umt.service.UserManageVO;


@Controller
public class EgovComIndexController implements ApplicationContextAware, InitializingBean {
	
	Logger logger = org.slf4j.LoggerFactory.getLogger(EgovComIndexController.class);
	
	@Resource(name = "EgovDeptService")
	private EgovDeptService deptService;
	
	@Resource(name = "EgovUserManageService")
	private EgovUserManageService userService;
	
	private ApplicationContext applicationContext;

	private static final Logger LOGGER = LoggerFactory.getLogger(EgovComIndexController.class);

	private Map<Integer, IncludedCompInfoVO> map;

	public void afterPropertiesSet() throws Exception {}
	
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		
		LOGGER.info("EgovComIndexController setApplicationContext method has called!");
	}

	@RequestMapping("/index.do")
	public String index(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		return "egovframework/com/uat/uia/EgovLoginUsr";	/* TBS 2016.02.01 */
	}

	@RequestMapping("/EgovTop.do")
	public String top() {
		return "egovframework/com/cmm/EgovUnitTop";
	}

	@RequestMapping("/EgovBottom.do")
	public String bottom() {
		return "egovframework/com/cmm/EgovUnitBottom";
	}

	@RequestMapping("/EgovContent.do")
	public String setContent(ModelMap model) throws Exception {
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		UserManageVO user = userService.getUser(loginVO.getUniqId());
		model.addAttribute("loginVO", loginVO);
		model.addAttribute("user", user);
		
		//'U' : 일반사용자 / 'A' : 기관관리자 / 'S' : 시스템관리자  / 'D' : 부서관리자  
		if(loginVO.getUserType().equals("U") || loginVO.getUserType().equals("D"))
		{
			return "forward:/home.do";
		}else{
			return "forward:/userList.do?action=getDetailUserInfo&&checkType=multi";
		}
	}
		
	@RequestMapping("/org.do")
	protected ModelAndView setContentAdmin(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//logger.info("--- {} Start ...", "deptView");
		/* TBS 2016.02.17 start */
		ModelAndView model = new ModelAndView();
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		UserManageVO user = userService.getUser(loginVO.getUniqId());
		
		String uniqId = loginVO.getUniqId();
		String deptId = loginVO.getOrgnztId();
		//RequestUtil rutil = new RequestUtil(request);
		//User loginUser = rutil.getUser();
		//String deptId = rutil.getString("DEPT_ID", loginUser.getDeptId());
		//logger.debug("deptId = [{}]", deptId);
		//deptId = StringUtils.defaultString(deptId, loginUser.getDeptId());
		//DeptVO dept = (DeptVO)rutil.getObject("dept");
		//if (dept == null) {
		//	dept = orgContext.getDept(deptId);
		//}
		//if (dept == null) {
		//	dept = orgContext.getDept(loginUser.getDeptId());
		//}
		//logger.debug(rutil.toString());
		//logger.debug("{}", dept);
		// 직위, 직책, 사용자가 있는 경우 삭제불가 메시지 설정
//		boolean hasUndeletableMessage = false;
//		int countOfPosi = orgContext.getPosiCount(dept.getDeptId());
//		int countOfDuty = orgContext.getDutyCount(dept.getDeptId());
//		int countOfUser = orgContext.getUserCount(dept.getDeptId());
//		logger.debug("countOfPosi= {}", countOfPosi);
//		logger.debug("countOfDuty= {}", countOfDuty);
//		logger.debug("countOfUser= {}", countOfUser);
//		if (countOfPosi > 0 ||
//			countOfDuty > 0 ||
//			countOfUser > 0) {
//			hasUndeletableMessage = true;
//		}
//		String viewName = "View/03_AM/SCR-AM-001-005";
//		ModelAndView mav = new ModelAndView(viewName);
//		mav.addObject("dept", dept);
//		mav.addObject("tree", getTree(request));
//		mav.addObject("DEPT_BOX_USER_NM", orgContext.getUserNm(dept.getDeptBoxUserId()));
//		mav.addObject("DEPT_RBOX_USER_NM", orgContext.getUserNm(dept.getDeptRboxUserId()));
//		mav.addObject("DEPT_SBOX_USER_NM", orgContext.getUserNm(dept.getDeptSboxUserId()));
//		mav.addObject("DEPT_ADMIN_USER_NM", orgContext.getUserNm(dept.getDeptAdminUserId()));
//		// 자신의 부서가 아닌경우 삭제 가능
//		mav.addObject("isDeletable", ! StringUtils.equals(loginUser.getDeptId(), dept.getDeptId()));
//		mav.addObject("hasUndeletableMessage", hasUndeletableMessage);
//		return mav;
		/* OrgTree */
		List<DeptVO> tree = null;
		if(user.getUserType().equals("S")){
			tree = deptService.getCompanyTree();
			model.addObject("tree", tree);
		}else{
			tree = deptService.getDeptTree(deptId);
			model.addObject("tree", tree);
		}
//		model.addObject("userAuth",labelTree.getModel().get("userAuth"));
//		model.addObject("deptAuth",labelTree.getModel().get("deptAuth"));
		model.addObject("user", user);
//		model.addObject("docType",request.getParameter("docType"));
//		model.addObject("labelID",request.getParameter("labelID"));
//		model.addObject("labelNm",request.getParameter("labelNm"));
		model.setViewName("egovframework/com/uss/omt/EgovUserList");
		return model;
	}
	/* TBS 2016.02.17 end */
	
	@RequestMapping("/EgovLeft.do")
	public String setLeftMenu(ModelMap model) {

		/* 최초 한 번만 실행하여 map에 저장해 놓는다. */
		if (map == null) {
			map = new TreeMap<Integer, IncludedCompInfoVO>();
			RequestMapping rmAnnotation;
			IncludedInfo annotation;
			IncludedCompInfoVO zooVO;

			/*
			 * EgovLoginController가 AOP Proxy되는 바람에 클래스를 reflection으로 가져올 수 없음
			 */
			try {
				Class<?> loginController = Class.forName("egovframework.com.uat.uia.web.EgovLoginController");
				Method[] methods = loginController.getMethods();
				for (int i = 0; i < methods.length; i++) {
					annotation = methods[i].getAnnotation(IncludedInfo.class);

					if (annotation != null) {
						LOGGER.debug("Found @IncludedInfo Method : {}", methods[i]);
						zooVO = new IncludedCompInfoVO();
						zooVO.setName(annotation.name());
						zooVO.setOrder(annotation.order());
						zooVO.setGid(annotation.gid());

						rmAnnotation = methods[i].getAnnotation(RequestMapping.class);
						if ("".equals(annotation.listUrl()) && rmAnnotation != null) {
							zooVO.setListUrl(rmAnnotation.value()[0]);
						} else {
							zooVO.setListUrl(annotation.listUrl());
						}
						map.put(zooVO.getOrder(), zooVO);
					}
				}
			} catch (ClassNotFoundException e) {
				LOGGER.error("No egovframework.com.uat.uia.web.EgovLoginController!!");
			}
			/* 여기까지 AOP Proxy로 인한 코드 */

			/*@Controller Annotation 처리된 클래스를 모두 찾는다.*/
			Map<String, Object> myZoos = applicationContext.getBeansWithAnnotation(Controller.class);
			LOGGER.debug("How many Controllers : ", myZoos.size());
			for (final Object myZoo : myZoos.values()) {
				Class<? extends Object> zooClass = myZoo.getClass();

				Method[] methods = zooClass.getMethods();
				LOGGER.debug("Controller Detected {}", zooClass);
				for (int i = 0; i < methods.length; i++) {
					annotation = methods[i].getAnnotation(IncludedInfo.class);

					if (annotation != null) {
						//LOG.debug("Found @IncludedInfo Method : " + methods[i] );
						zooVO = new IncludedCompInfoVO();
						zooVO.setName(annotation.name());
						zooVO.setOrder(annotation.order());
						zooVO.setGid(annotation.gid());
						/*
						 * 목록형 조회를 위한 url 매핑은 @IncludedInfo나 @RequestMapping에서 가져온다
						 */
						rmAnnotation = methods[i].getAnnotation(RequestMapping.class);
						if ("".equals(annotation.listUrl())) {
							zooVO.setListUrl(rmAnnotation.value()[0]);
						} else {
							zooVO.setListUrl(annotation.listUrl());
						}

						map.put(zooVO.getOrder(), zooVO);
					}
				}
			}
		}

		model.addAttribute("resultList", map.values());
		
		LOGGER.debug("EgovComIndexController index is called ");

		return "egovframework/com/cmm/EgovUnitLeft";
	}
	@RequestMapping("/profile.do")
	public String profile(ModelMap model) throws Exception {
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		UserManageVO user = userService.getUser(loginVO.getUniqId());
		
		model.addAttribute("user", user);
		return "egovframework/com/cmm/EgovProfile";
	}	
}
