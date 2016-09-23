package egovframework.com.uss.umt.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.annotation.IncludedInfo;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.uss.ion.apu.PathUtil;
import egovframework.com.uss.omt.service.DeptVO;
import egovframework.com.uss.omt.service.DutyVO;
import egovframework.com.uss.omt.service.EgovDeptService;
import egovframework.com.uss.omt.service.EgovDutyService;
import egovframework.com.uss.omt.service.EgovPositionService;
import egovframework.com.uss.omt.service.PositionVO;
import egovframework.com.uss.omt.web.EgovDeptController;
import egovframework.com.uss.umt.service.EgovUserManageService;
import egovframework.com.uss.umt.service.UserDefaultVO;
import egovframework.com.uss.umt.service.UserManageVO;
import egovframework.com.utl.sim.service.EgovFileScrty;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;

/**
 * 업무사용자관련 요청을  비지니스 클래스로 전달하고 처리된결과를  해당   웹 화면으로 전달하는  Controller를 정의한다
 * @author 공통서비스 개발팀 조재영
 * @since 2009.04.10
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.04.10  조재영          최초 생성
 *   2011.08.26	 정진오			IncludedInfo annotation 추가
 *   2014.12.08	 이기하			암호화방식 변경(EgovFileScrty.encryptPassword)
 *   2015.06.16	 조정국			수정시 유효성체크 후 에러발생 시 목록으로 이동하여 에러메시지 표시
 *   2015.06.19	 조정국			미인증 사용자에 대한 보안처리 기준 수정 (!isAuthenticated)
 *
 * </pre>
 */

@Controller
public class EgovUserManageController {

	/** userManageService */
	@Resource(name = "EgovUserManageService")
	private EgovUserManageService userManageService;

	/** cmmUseService */
	@Resource(name = "EgovCmmUseService")
	private EgovCmmUseService cmmUseService;

	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/* TBS 2016-020-23 start */
	@Resource(name = "EgovDeptService")
	private EgovDeptService deptService;

	@Resource(name = "EgovPositionService")
	private EgovPositionService positionService;
	
	@Resource(name = "EgovDutyService")
	private EgovDutyService dutyService;
	
    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;
	/* TBS 2016-020-23 end */

	/** DefaultBeanValidator beanValidator */
	@Autowired
	private DefaultBeanValidator beanValidator;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovUserManageController.class);

	/**
	 * 사용자목록을 조회한다. (pageing)
	 * @param userSearchVO 검색조건정보
	 * @param model 화면모델
	 * @return cmm/uss/umt/EgovUserManage
	 * @throws Exception
	 */
	@IncludedInfo(name = "업무사용자관리", order = 460, gid = 50)
	@RequestMapping(value = "/uss/umt/EgovUserManage.do")
	public String selectUserList(@ModelAttribute("userSearchVO") UserDefaultVO userSearchVO, ModelMap model) throws Exception {
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			return "index";
		}
		/** EgovPropertyService */
		userSearchVO.setPageUnit(propertiesService.getInt("pageUnit"));
		userSearchVO.setPageSize(propertiesService.getInt("pageSize"));
		/** pageing */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(userSearchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(userSearchVO.getPageUnit());
		paginationInfo.setPageSize(userSearchVO.getPageSize());

		userSearchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		userSearchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		userSearchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		List<?> userList = userManageService.getUserList(userSearchVO);
		model.addAttribute("resultList", userList);

		int totCnt = userManageService.getUserCnt(userSearchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);

		//사용자상태코드를 코드정보로부터 조회
		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		vo.setCodeId("COM013");
		List<?> emplyrSttusCode_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("emplyrSttusCode_result", emplyrSttusCode_result);//사용자상태코드목록

		return "egovframework/com/uss/umt/EgovUserManage";
	}

	/**
	 * 사용자등록화면으로 이동한다.
	 * @param userSearchVO 검색조건정보
	 * @param userManageVO 사용자초기화정보
	 * @param model 화면모델
	 * @return cmm/uss/umt/EgovUserInsert
	 * @throws Exception
	 */
	@RequestMapping("/uss/umt/EgovUserInsertView.do")
	public String insertUserView(@ModelAttribute("userSearchVO") UserDefaultVO userSearchVO, @ModelAttribute("userManageVO") UserManageVO userManageVO, Model model)
			throws Exception {
		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			return "index";
		}

		ComDefaultCodeVO vo = new ComDefaultCodeVO();

		//패스워드힌트목록을 코드정보로부터 조회
		vo.setCodeId("COM022");
		List<?> passwordHint_result = cmmUseService.selectCmmCodeDetail(vo);
		//성별구분코드를 코드정보로부터 조회
		vo.setCodeId("COM014");
		List<?> sexdstnCode_result = cmmUseService.selectCmmCodeDetail(vo);
		//사용자상태코드를 코드정보로부터 조회
		vo.setCodeId("COM013");
		List<?> emplyrSttusCode_result = cmmUseService.selectCmmCodeDetail(vo);
		//소속기관코드를 코드정보로부터 조회 - COM025
		vo.setCodeId("COM025");
		List<?> insttCode_result = cmmUseService.selectCmmCodeDetail(vo);
		//조직정보를 조회 - ORGNZT_ID정보
		vo.setTableNm("COMTNORGNZTINFO");
		List<?> orgnztId_result = cmmUseService.selectOgrnztIdDetail(vo);
		//그룹정보를 조회 - GROUP_ID정보
		vo.setTableNm("COMTNORGNZTINFO");
		List<?> groupId_result = cmmUseService.selectGroupIdDetail(vo);

		model.addAttribute("passwordHint_result", passwordHint_result); //패스워트힌트목록
		model.addAttribute("sexdstnCode_result", sexdstnCode_result); //성별구분코드목록
		model.addAttribute("emplyrSttusCode_result", emplyrSttusCode_result);//사용자상태코드목록
		model.addAttribute("insttCode_result", insttCode_result); //소속기관코드목록
		model.addAttribute("orgnztId_result", orgnztId_result); //조직정보 목록
		model.addAttribute("groupId_result", groupId_result); //그룹정보 목록
		return "egovframework/com/uss/umt/EgovUserInsert";
	}

	/**
	 * 사용자등록처리후 목록화면으로 이동한다.
	 * @param userManageVO 사용자등록정보
	 * @param bindingResult 입력값검증용 bindingResult
	 * @param model 화면모델
	 * @return forward:/uss/umt/EgovUserManage.do
	 * @throws Exception
	 */
	@RequestMapping("/uss/umt/EgovUserInsert.do")
	public String insertUser(@ModelAttribute("userManageVO") UserManageVO userManageVO, BindingResult bindingResult, Model model) throws Exception {
		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			return "index";
		}

		beanValidator.validate(userManageVO, bindingResult);
		if (bindingResult.hasErrors()) {
			return "egovframework/com/uss/umt/EgovUserInsert";
		} else {
			if (userManageVO.getOrgnztId().equals("")) {
				userManageVO.setOrgnztId(null);
			}
			if (userManageVO.getGroupId().equals("")) {
				userManageVO.setGroupId(null);
			}
			userManageService.insertUser(userManageVO);
			//Exception 없이 진행시 등록성공메시지
			model.addAttribute("resultMsg", "success.common.insert");
		}
		return "forward:/uss/umt/EgovUserManage.do";
	}

	/**
	 * 사용자정보 수정을 위해 사용자정보를 상세조회한다.
	 * @param uniqId 상세조회대상 사용자아이디
	 * @param userSearchVO 검색조건
	 * @param model 화면모델
	 * @return uss/umt/EgovUserSelectUpdt
	 * @throws Exception
	 */
	@RequestMapping("/uss/umt/EgovUserSelectUpdtView.do")
	public String updateUserView(@RequestParam("selectedId") String uniqId, @ModelAttribute("searchVO") UserDefaultVO userSearchVO, Model model) throws Exception {
		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			return "index";
		}

		ComDefaultCodeVO vo = new ComDefaultCodeVO();

		//패스워드힌트목록을 코드정보로부터 조회
		vo.setCodeId("COM022");
		List<?> passwordHint_result = cmmUseService.selectCmmCodeDetail(vo);
		//성별구분코드를 코드정보로부터 조회
		vo.setCodeId("COM014");
		List<?> sexdstnCode_result = cmmUseService.selectCmmCodeDetail(vo);
		//사용자상태코드를 코드정보로부터 조회
		vo.setCodeId("COM013");
		List<?> emplyrSttusCode_result = cmmUseService.selectCmmCodeDetail(vo);
		//소속기관코드를 코드정보로부터 조회 - COM025
		vo.setCodeId("COM025");
		List<?> insttCode_result = cmmUseService.selectCmmCodeDetail(vo);
		//조직정보를 조회 - ORGNZT_ID정보
		vo.setTableNm("COMTNORGNZTINFO");
		List<?> orgnztId_result = cmmUseService.selectOgrnztIdDetail(vo);
		//그룹정보를 조회 - GROUP_ID정보
		vo.setTableNm("COMTNORGNZTINFO");
		List<?> groupId_result = cmmUseService.selectGroupIdDetail(vo);

		model.addAttribute("passwordHint_result", passwordHint_result); //패스워트힌트목록
		model.addAttribute("sexdstnCode_result", sexdstnCode_result); //성별구분코드목록
		model.addAttribute("emplyrSttusCode_result", emplyrSttusCode_result);//사용자상태코드목록
		model.addAttribute("insttCode_result", insttCode_result); //소속기관코드목록
		model.addAttribute("orgnztId_result", orgnztId_result); //조직정보 목록
		model.addAttribute("groupId_result", groupId_result); //그룹정보 목록

		UserManageVO userManageVO = new UserManageVO();
		userManageVO = userManageService.getUser(uniqId);
		model.addAttribute("userSearchVO", userSearchVO);
		model.addAttribute("userManageVO", userManageVO);
		return "egovframework/com/uss/umt/EgovUserSelectUpdt";
	}

	/**
	 * 사용자정보 수정후 목록조회 화면으로 이동한다.
	 * @param userManageVO 사용자수정정보
	 * @param bindingResult 입력값검증용 bindingResult
	 * @param model 화면모델
	 * @return forward:/uss/umt/EgovUserManage.do
	 * @throws Exception
	 */
	@RequestMapping("/uss/umt/EgovUserSelectUpdt.do")
	public String updateUser(@ModelAttribute("userManageVO") UserManageVO userManageVO, BindingResult bindingResult, Model model) throws Exception {
		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			return "index";
		}

		beanValidator.validate(userManageVO, bindingResult);
		if (bindingResult.hasErrors()) {
			model.addAttribute("resultMsg", bindingResult.getAllErrors().get(0).getDefaultMessage());
			return "forward:/uss/umt/EgovUserManage.do";
		} else {
			//업무사용자 수정시 히스토리 정보를 등록한다.
			userManageService.insertUserHistory(userManageVO);
			if (userManageVO.getOrgnztId().equals("")) {
				userManageVO.setOrgnztId(null);
			}
			if (userManageVO.getGroupId().equals("")) {
				userManageVO.setGroupId(null);
			}
			userManageService.updateUser(userManageVO);
			//Exception 없이 진행시 수정성공메시지
			model.addAttribute("resultMsg", "success.common.update");
			return "forward:/uss/umt/EgovUserManage.do";
		}
	}

	/**
	 * 사용자정보삭제후 목록조회 화면으로 이동한다.
	 * @param checkedIdForDel 삭제대상아이디 정보
	 * @param userSearchVO 검색조건
	 * @param model 화면모델
	 * @return forward:/uss/umt/EgovUserManage.do
	 * @throws Exception
	 */
	@RequestMapping("/uss/umt/EgovUserDelete.do")
	public String deleteUser(@RequestParam("checkedIdForDel") String checkedIdForDel, @ModelAttribute("searchVO") UserDefaultVO userSearchVO, Model model) throws Exception {
		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			return "index";
		}

		userManageService.deleteUser(checkedIdForDel);
		//Exception 없이 진행시 등록성공메시지
		model.addAttribute("resultMsg", "success.common.delete");
		return "forward:/uss/umt/EgovUserManage.do";
	}

	/**
	 * 입력한 사용자아이디의 중복확인화면 이동
	 * @param model 화면모델
	 * @return uss/umt/EgovIdDplctCnfirm
	 * @throws Exception
	 */
	@RequestMapping(value = "/uss/umt/EgovIdDplctCnfirmView.do")
	public String checkIdDplct(ModelMap model) throws Exception {
		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			return "index";
		}
		model.addAttribute("checkId", "");
		model.addAttribute("usedCnt", "-1");
		return "egovframework/com/uss/umt/EgovIdDplctCnfirm";
	}

	/**
	 * 입력한 사용자아이디의 중복여부를 체크하여 사용가능여부를 확인
	 * @param commandMap 파라메터전달용 commandMap
	 * @param model 화면모델
	 * @return uss/umt/EgovIdDplctCnfirm
	 * @throws Exception
	 */
	@RequestMapping(value = "/uss/umt/EgovIdDplctCnfirm.do")
	public String checkIdDplct(@RequestParam Map<String, Object> commandMap, ModelMap model) throws Exception {
		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			return "index";
		}

		String checkId = (String) commandMap.get("checkId");
		checkId = new String(checkId.getBytes("ISO-8859-1"), "UTF-8");

		if (checkId == null || checkId.equals(""))
			return "forward:/uss/umt/EgovIdDplctCnfirmView.do";

		int usedCnt = userManageService.checkIdDplct(checkId);
		model.addAttribute("usedCnt", usedCnt);
		model.addAttribute("checkId", checkId);
		return "egovframework/com/uss/umt/EgovIdDplctCnfirm";
	}

	/**
	 * 업무사용자 암호 수정처리 후 화면 이동
	 * @param model 화면모델
	 * @param commandMap 파라메터전달용 commandMap
	 * @param userSearchVO 검색조 건
	 * @param userManageVO 사용자수정정보(비밀번호)
	 * @return uss/umt/EgovUserPasswordUpdt
	 * @throws Exception
	 */
	@RequestMapping(value = "/uss/umt/EgovUserPasswordUpdt.do")
	public String updatePassword(ModelMap model, @RequestParam Map<String, Object> commandMap, @ModelAttribute("searchVO") UserDefaultVO userSearchVO,
			@ModelAttribute("userManageVO") UserManageVO userManageVO) throws Exception {
		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			return "index";
		}

		String oldPassword = (String) commandMap.get("oldPassword");
		String newPassword = (String) commandMap.get("newPassword");
		String newPassword2 = (String) commandMap.get("newPassword2");
		String uniqId = (String) commandMap.get("uniqId");

		boolean isCorrectPassword = false;
		UserManageVO resultVO = new UserManageVO();
		userManageVO.setPassword(newPassword);
		userManageVO.setOldPassword(oldPassword);
		userManageVO.setUniqId(uniqId);

		String resultMsg = "";
		resultVO = userManageService.getPassword(userManageVO);
		//패스워드 암호화
		String encryptPass = EgovFileScrty.encryptPassword(oldPassword, userManageVO.getEmplyrId());
		if (encryptPass.equals(resultVO.getPassword())) {
			if (newPassword.equals(newPassword2)) {
				isCorrectPassword = true;
			} else {
				isCorrectPassword = false;
				resultMsg = "fail.user.passwordUpdate2";
			}
		} else {
			isCorrectPassword = false;
			resultMsg = "fail.user.passwordUpdate1";
		}

		if (isCorrectPassword) {
			userManageVO.setPassword(EgovFileScrty.encryptPassword(newPassword, userManageVO.getEmplyrId()));
			userManageService.updatePassword(userManageVO);
			model.addAttribute("userManageVO", userManageVO);
			resultMsg = "success.common.update";
		} else {
			model.addAttribute("userManageVO", userManageVO);
		}
		model.addAttribute("userSearchVO", userSearchVO);
		model.addAttribute("resultMsg", resultMsg);
		return "egovframework/com/uss/umt/EgovUserPasswordUpdt";
	}

	/**
	 * 업무사용자 암호 수정  화면 이동
	 * @param model 화면모델
	 * @param commandMap 파라메터전달용 commandMap
	 * @param userSearchVO 검색조건
	 * @param userManageVO 사용자수정정보(비밀번호)
	 * @return uss/umt/EgovUserPasswordUpdt
	 * @throws Exception
	 */
	@RequestMapping(value = "/uss/umt/EgovUserPasswordUpdtView.do")
	public String updatePasswordView(ModelMap model, @RequestParam Map<String, Object> commandMap, @ModelAttribute("searchVO") UserDefaultVO userSearchVO,
			@ModelAttribute("userManageVO") UserManageVO userManageVO) throws Exception {
		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			return "index";
		}

		String userTyForPassword = (String) commandMap.get("userTyForPassword");
		userManageVO.setUserTy(userTyForPassword);

		model.addAttribute("userManageVO", userManageVO);
		model.addAttribute("userSearchVO", userSearchVO);
		return "egovframework/com/uss/umt/EgovUserPasswordUpdt";
	}
	
    @RequestMapping(value="/userSelectionModal.do")
   	public ModelAndView userSelectionModal(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView model = new ModelAndView();
       	LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
       	UserManageVO user = userManageService.getUser(loginVO.getUniqId());
       	String deptId = loginVO.getOrgnztId();
       	String selectedDeptId = request.getParameter("DEPT_ID");
       	
       	/* OrgTree */
       	List<DeptVO> tree = null;
		if(user.getUserType().equals("S")){
			tree = deptService.getCompanyTree();
			model.addObject("tree", tree);
		}else{
			tree = deptService.getDeptTree(deptId);
			model.addObject("tree", tree);
		}
		
       	UserManageVO vo = new UserManageVO();
       	if(selectedDeptId == null || selectedDeptId.equals("")){
       		vo.setOrgnztId(tree.get(0).getDeptId());
       	}else {
			vo.setOrgnztId(selectedDeptId);
		}
       	List<UserManageVO> list = userManageService.getUserList(vo);

		model.addObject("user", user);
   		model.addObject("list", list);
   		model.addObject("deptId", vo.getOrgnztId());
   		model.setViewName("egovframework/com/uss/omt/cmm/EgovUserSelectionModal");
   		return model;
   	}
	
    @RequestMapping("/user.do")
    public ModelAndView user(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String userId = request.getParameter("userId");
        String selectedDeptId = request.getParameter("deptId");
        String companyId = null;
        
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        UserManageVO user = userManageService.getUser(loginVO.getUniqId());
        
        if(selectedDeptId != null){
        	companyId = user.getCompanyId();
        }
        UserManageVO searchVO = new UserManageVO();
        searchVO.setUniqId(userId);
        searchVO.setComapnyId(companyId);
        UserManageVO searchUser = userManageService.getUser(searchVO);
        
        ModelAndView model = new ModelAndView("egovframework/com/uss/umt/EgovUserJson");
        model.addObject("user", searchUser);
        return model;
    }
    
	@RequestMapping(value="/userList.do")
	protected ModelAndView userList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView model = new ModelAndView();
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		UserManageVO user = userManageService.getUser(loginVO.getUniqId());
	    String viewType = request.getParameter("action");
	    String checkType = request.getParameter("checkType");
	    if((checkType == null) || (checkType.isEmpty())){
	    	checkType = "multi";
	    }
	       
		/* 선택한 부서 ID */
		UserManageVO searchVO = new UserManageVO();
		String selectedDeptId = request.getParameter("deptId");

		/* OrgTree */
		List<DeptVO> tree = null;
		if(user.getUserType().equals("S")){
			tree = deptService.getCompanyTree();
			model.addObject("tree", tree);
		}else if(user.getUserType().equals("A")){
			tree = deptService.getDeptTree(user.getCompanyId());
			model.addObject("tree", tree);
		} else {
			tree = deptService.getDeptTree(user.getOrgnztId());
            model.addObject("tree", tree);
		}

		if(selectedDeptId == null || selectedDeptId.equals(""))
		{
			searchVO.setOrgnztId(tree.get(0).getDeptId());
		}else{
			searchVO.setOrgnztId(selectedDeptId);
		}
		
		DeptVO dept = deptService.getDept(searchVO.getOrgnztId());
		
		if((dept == null) || (dept.equals(""))){
			searchVO.setOrgnztId(tree.get(0).getDeptId());
			dept = deptService.getDept(searchVO.getOrgnztId());
		}
		if(!(dept.getDeptTopF().equals("1"))){
			String companyId = user.getCompanyId();
			searchVO.setComapnyId(companyId);
		}
		/* 사용자 리스트 조회 */
		List<UserManageVO> list = userManageService.getUserList(searchVO);
		
		/* 기관관리자의 경우 사용자 목록에서 제거 */
		/*if (user.getUserType().equals("A")) {
			List<UserManageVO> newList = new ArrayList<UserManageVO>();
			for (Object obj : list) {
				UserManageVO userList = (UserManageVO) obj;
				if (StringUtils.equals(userList.getEmplyrId(), loginVO.getId())) {
					continue;
				} else {
					newList.add(userList);
				}
			}
			list = newList;
		}*/
		
		model.addObject("user", user);
		model.addObject("selectedDeptId", selectedDeptId);
		model.addObject("list", list);
		model.addObject("checkType", checkType);
		
		if ((viewType == null) || (viewType.isEmpty())) {
	          model.setViewName("egovframework/com/uss/omt/EgovUserList");
		} else if (viewType.equalsIgnoreCase("getDetailUserInfo")) {
	        model.setViewName("egovframework/com/uss/omt/EgovUserList");
		} else if (viewType.equalsIgnoreCase("getUserInfoForPopup")) {
		    model.addObject("dept", dept);
		    model.addObject("display", createOptionMap(request.getParameter("display")));
		    model.addObject("dutiesUsed", createOptionMap(request.getParameter("dutiesUsed")));
	        model.setViewName("egovframework/com/uss/omt/cmm/EgovUserListForPopup");
		} else if (viewType.equalsIgnoreCase("getUserListByDept")) {
	        model.setViewName("egovframework/com/uss/omt/EgovUserListByDept");
		}
		return model;
	}
    
    @RequestMapping(value="/userForm.do")
 	public ModelAndView userForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
 		ModelAndView model = new ModelAndView();
 		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
 		DeptVO dept = new DeptVO();
 		
 		List<PositionVO> posiList = new ArrayList<PositionVO>();			
 		List<DutyVO> dutyList = new ArrayList<DutyVO>();

 		String userId = request.getParameter("userId");
 		String deptId = request.getParameter("deptId");

 		String companyID = null;
 		UserManageVO user = null;
 		UserManageVO loginUser = userManageService.getUser(loginVO.getUniqId()); 
 		
 		if ((userId != null) && (userId.isEmpty() == false)) {
     		try {
     			//If UserModify
     			user = userManageService.getUser(userId);
     			companyID = user.getCompanyId();
     			if ((companyID == null) || (companyID.isEmpty())) {
     			    companyID = user.getCompanyId();
     			} 
     			deptId = user.getOrgnztId();
     		} catch (Exception e) {
     			;
     		}
 		}
 		
 		//If UserAdd
 		if (user == null) {
 			user = new UserManageVO();
 		}
 		
 		//If UserModify
 		if (user != null) {
 			LOGGER.debug(user.toString());
 		}
 		
 		/* 부서 정보 조회 */
 		dept = deptService.getDept(deptId);
 		if (dept == null) {
 		    dept = new DeptVO();
 		}
 		
 		if ((companyID == null) || (companyID.isEmpty())) {
 		    companyID = loginUser.getCompanyId();
 		}
 		//직책, 직위,보안등급 정보 조회
 		posiList = positionService.getPositionListByDeptId(companyID);
 		dutyList = dutyService.getDutyList(companyID);
 		
 		model.addObject("user", loginUser);
 		model.addObject("selectedUser", user);
 		model.addObject("dept", dept);
 		model.addObject("posiList", posiList);
 		model.addObject("dutyList", dutyList);
 		model.addObject("userId", userId);
 		model.setViewName("egovframework/com/uss/omt/EgovUserForm");
 		return model;
 	}
    
    @RequestMapping(value="/simpleUserForm.do")
    public ModelAndView simpleUserForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView model = new ModelAndView();
    	LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
    	UserManageVO loginUser = userManageService.getUser(loginVO.getUniqId()); 
    	
    	String deptName = request.getParameter("deptName");
    	String inputName = request.getParameter("inputName");
    	
    	String companyID = null;
    	if ((companyID == null) || (companyID.isEmpty())) {
    		companyID = loginUser.getCompanyId();
    	}

    	//직책, 직위,보안등급 정보 조회
    	List<PositionVO> posiList = new ArrayList<PositionVO>();			
    	List<DutyVO> dutyList = new ArrayList<DutyVO>();
    	posiList = positionService.getPositionListByDeptId(companyID);
    	dutyList = dutyService.getDutyList(companyID);
    	
    	model.addObject("user", loginUser);
    	model.addObject("posiList", posiList);
    	model.addObject("dutyList", dutyList);
    	model.addObject("deptName", deptName);
    	model.addObject("inputName", inputName);
    	model.setViewName("egovframework/com/uss/omt/EgovSimpleUserFormModal");
    	return model;
    }
    
    @RequestMapping(value="/userView.do")
    public ModelAndView userView(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView model = new ModelAndView();
    	LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
    	DeptVO dept = new DeptVO();
    	
    	String userId = request.getParameter("userId");
    	String deptId = request.getParameter("deptId");
    	
    	String companyID = null;
    	UserManageVO user = null;
    	UserManageVO loginUser = userManageService.getUser(loginVO.getUniqId()); 
    	
		try {
			user = userManageService.getUser(userId);
			companyID = user.getCompanyId();
			if ((companyID == null) || (companyID.isEmpty())) {
				companyID = user.getCompanyId();
			} 
			deptId = user.getOrgnztId();
		} catch (Exception e) {
			;
		}
    	    	
    	/* 부서 정보 조회 */
    	dept = deptService.getDept(deptId);
    	if (dept == null) {
    		dept = new DeptVO();
    	}
    	
    	model.addObject("user", loginUser);
    	model.addObject("selectedUser", user);
    	model.addObject("dept", dept);
    	model.addObject("userId", userId);
    	model.setViewName("egovframework/com/uss/omt/EgovUserView");
    	return model;
    }
    
	/**
	 * [AM-M01-004-002] 사용자 등록
	 * [AM-M01-004-003] 사용자 수정
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
    @RequestMapping(value="/userSave.do")
	public ModelAndView userSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		ModelAndView model = new ModelAndView();
		UserManageVO user = new UserManageVO();
		
        boolean isAdd = false;
        if (StringUtils.isBlank(request.getParameter("USER_ID"))) {
            isAdd = true;
        } else {
            user = userManageService.getUser(request.getParameter("USER_ID"));
        }
		        
		user.setUniqId(request.getParameter("USER_ID"));
		user.setOrgnztId(request.getParameter("DEPT_ID"));
		user.setEmplyrNm(request.getParameter("USER_NM"));
		user.setSeq(request.getParameter("USER_SEQ"));
		user.setEmplNo(request.getParameter("USER_EM_CD"));
		user.setPositionId(request.getParameter("POSI_ID"));
		user.setDutyId(request.getParameter("DUTY_ID"));
		user.setSecurityLvl(Integer.parseInt(request.getParameter("USER_SLVL")));
		user.setEmplyrId(request.getParameter("USER_LOGIN"));
		String userlpwd = request.getParameter("USER_LPWD");
		
		String companyId = deptService.getCompanyId(user.getOrgnztId());
		user.setComapnyId(companyId);
		
		/* 비밀번호 암호화 */
		if (StringUtils.isNotBlank(userlpwd)) {
			String md5userlpwd = EgovFileScrty.encryptPassword(userlpwd, user.getEmplyrId());
			user.setPassword(md5userlpwd);
		}
		String userspwd = request.getParameter("USER_SPWD");
		if (StringUtils.isNotBlank(userspwd)) {
			String md5userspwd = EgovFileScrty.encryptPassword(userspwd, user.getEmplyrId());
			user.setApprovalPassword(md5userspwd);
		}
		
		user.setUserAbsF(request.getParameter("USER_ABS_F"));
		if (StringUtils.isBlank(user.getUserAbsF())) {
			user.setUserAbsF("2");
		}
		user.setUserStatus(request.getParameter("USER_STATUS"));
		if (StringUtils.isBlank(user.getUserStatus())) {
			user.setUserStatus("1");
		}
		
		user.setEmailAdres(request.getParameter("USER_EMAIL"));
		user.setOffmTelno(request.getParameter("USER_TEL"));
		user.setFxnum(request.getParameter("USER_FAX"));
		user.setMoblphonNo(request.getParameter("USER_MOBILE"));
		user.setRemark(request.getParameter("USER_RMRK"));
		
		if (isAdd) {
		    user.setSbscrbDe(getCurrentTime());
		}
		user.setUpdateDt(getCurrentTime());
		user.setPhotoPath(request.getParameter("PHOTO_PATH"));
		user.setSignPath(request.getParameter("SIGN_PATH"));
		

		LOGGER.debug(user.toString());
		
		if (StringUtils.isNotBlank(user.getPhotoPath())) {
			try {
				String path = user.getPhotoPath();
				String filename = StringUtils.join(new String[] {
						user.getOrgnztId(),
						user.getUniqId(),
						"-photo"
				}, "");
				moveUserImage(path, filename);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (StringUtils.isNotBlank(user.getSignPath())) {
			try {
				String path = user.getSignPath();
				String filename = StringUtils.join(new String[] {
						user.getOrgnztId(),
						user.getUniqId(),
						"-sign"
				}, "");
				moveUserImage(path, filename);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (isAdd) {
			/* 아이디 중복시 */
			int idCheck = userManageService.checkIdDplct(user.getEmplyrId());
			if (idCheck > 0) {
				//직책, 직위,보안등급 정보 조회
				List<?> posiList = positionService.getPositionListByDeptId(loginVO.getOrgnztId());
				List<?> dutyList = dutyService.getDutyList(loginVO.getOrgnztId());
				
		 		/* 부서 정보 조회 */
		 		DeptVO dept = deptService.getDept(user.getOrgnztId());
		 		
		 		model.addObject("dept", dept);
				model.addObject("user", user);
				model.addObject("message", egovMessageSource.getMessage("org.user.msg.008"));
				model.addObject("posiList", posiList);
				model.addObject("dutyList", dutyList);
				model.setViewName("egovframework/com/uss/omt/EgovUserForm");
				return model;
			} else {
				user.setUserType("U");
				userManageService.insertUser(user);
			}
		} else {
			UserManageVO preUser = userManageService.getUser(user.getUniqId());
			
			if (StringUtils.isNotBlank(user.getPassword()) &&
					! StringUtils.equals(preUser.getPassword(), user.getPassword())) {
				user.setUserLpwdDt("2016-02-22 15:15");
			} else {
				user.setPassword(preUser.getPassword());
			}
			if (StringUtils.isNotBlank(user.getApprovalPassword()) &&
					! StringUtils.equals(preUser.getApprovalPassword(), user.getApprovalPassword())) {
				;
			} else {
				user.setApprovalPassword(preUser.getApprovalPassword());
			}
			userManageService.updateUser(user);
		}
		model.addObject("user", user);
		model.setViewName("forward:/userList.do");
		return model;
	}
    
    @RequestMapping(value="/profileSave.do")
    public ModelAndView profileSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
    	ModelAndView model = new ModelAndView();
    	UserManageVO user = userManageService.getUser(loginVO.getUniqId());
		
    	user.setEmailAdres(request.getParameter("USER_EMAIL"));
    	user.setOffmTelno(request.getParameter("USER_TEL"));
    	user.setFxnum(request.getParameter("USER_FAX"));
    	user.setMoblphonNo(request.getParameter("USER_MOBILE"));
    	user.setRemark(request.getParameter("USER_RMRK"));
    	user.setUpdateDt(getCurrentTime());
    	user.setPhotoPath(request.getParameter("PHOTO_PATH"));
    	user.setSignPath(request.getParameter("SIGN_PATH"));
    	
    	LOGGER.debug(user.toString());
    	
    	if (StringUtils.isNotBlank(user.getPhotoPath())) {
    		try {
    			String path = user.getPhotoPath();
    			String filename = StringUtils.join(new String[] {
    					user.getOrgnztId(),
    					user.getUniqId(),
    					"-photo"
    			}, "");
    			moveUserImage(path, filename);
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	}
    	if (StringUtils.isNotBlank(user.getSignPath())) {
    		try {
    			String path = user.getSignPath();
    			String filename = StringUtils.join(new String[] {
    					user.getOrgnztId(),
    					user.getUniqId(),
    					"-sign"
    			}, "");
    			moveUserImage(path, filename);
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	}
		UserManageVO preUser = userManageService.getUser(user.getUniqId());
		user.setPassword(preUser.getPassword());
		user.setApprovalPassword(preUser.getApprovalPassword());
		userManageService.updateUser(user);
		
		model.setViewName("egovframework/com/uss/umt/EgovProfileSetting");
    	model.addObject("user", user);
    	return model;
    }
	
    private String getCurrentTime() {
        Calendar cal = Calendar.getInstance();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(cal.getTime());
    }

    @RequestMapping("/userListDelete.do")
    public ModelAndView deleteUserList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView model = new ModelAndView();
        
        // 미인증 사용자에 대한 보안처리
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
        if (!isAuthenticated) {
            model.setViewName("index");
            return model;
        }
        String userIdList = request.getParameter("idList");
        userManageService.deleteUserList(userIdList);
        //Exception 없이 진행시 등록성공메시지
        model.setViewName("forward:/userList.do");
        return model;
    }
    
    @RequestMapping("/userDelete.do")
    public ModelAndView deleteUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView model = new ModelAndView();
    	// 미인증 사용자에 대한 보안처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if (!isAuthenticated) {
    		model.setViewName("index");
    		return model;
    	}
    	String userId = request.getParameter("userId");
    	userManageService.deleteUser(userId);
    	model.setViewName("forward:/userList.do");
    	return model;
    }
    
	private void moveUserImage(String path, String filename) throws IOException {
	    String srcFilePath = (new PathUtil()).getTempPath();
		File srcFile = new File(srcFilePath, path);
		
		String destFolderPath = (new PathUtil()).getUserDataPath();
		FileUtils.forceMkdir(new File(destFolderPath));
		File destFile = new File(destFolderPath, filename);
		if (destFile.exists()) {
			FileUtils.deleteQuietly(destFile);
		}
		FileUtils.moveFile(srcFile, destFile);
	}
	
	@RequestMapping(value="/userCheckLoginId.do")
	public ModelAndView userCheckLoginId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView model = new ModelAndView();
		String userLogin = request.getParameter("USER_LOGIN");
		String result = "none";
		
		if (StringUtils.isNotBlank(userLogin)) {
			int usercheck = userManageService.checkIdDplct(userLogin);
			if(usercheck != 0){
				result = "exist";
			}
		}
		model.setViewName("egovframework/com/uss/omt/cmm/EgovResult");
		model.addObject("result", result);
		return model;
	}
	
	@RequestMapping(value="/profileSetting.do")
	public ModelAndView profileSetting(HttpServletRequest request, HttpServletResponse response) throws Exception {
 		ModelAndView model = new ModelAndView();
 		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
 		UserManageVO user = userManageService.getUser(loginVO.getUniqId()); 
 		
		model.setViewName("egovframework/com/uss/umt/EgovProfileSetting");
		model.addObject("user", user);
		return model;
	}
	
	@RequestMapping(value="/passwordSetting.do")
	public ModelAndView passwordSetting(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView model = new ModelAndView();
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		UserManageVO user = userManageService.getUser(loginVO.getUniqId()); 
		
		model.setViewName("egovframework/com/uss/umt/EgovPasswordSetting");
		model.addObject("user", user);
		return model;
	}
	
	@RequestMapping(value="/passwordCheck.do")
	public ModelAndView passwordCheck(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView model = new ModelAndView();
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		UserManageVO user = userManageService.getUser(loginVO.getUniqId()); 
		String oldPwd = request.getParameter("oldPW");
		String result = "none";
		
		if (StringUtils.isNotBlank(oldPwd)) {
			String md5oldpwd = EgovFileScrty.encryptPassword(oldPwd, user.getEmplyrId());
			if(!(user.getPassword().equals(md5oldpwd))){
				result = "NoMatch";
			}
		}
		model.setViewName("egovframework/com/uss/omt/cmm/EgovResult");
		model.addObject("result", result);
		return model;
	}
	
	@RequestMapping(value="/passwordSave.do")
	public String passwordSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		UserManageVO user = userManageService.getUser(loginVO.getUniqId()); 
		String newPwd = request.getParameter("newPW");
		
		if (StringUtils.isNotBlank(newPwd)) {
			String md5newpwd = EgovFileScrty.encryptPassword(newPwd, user.getEmplyrId());
			user.setPassword(md5newpwd);
			userManageService.updatePassword(user);
		}
		return "forward:/home.do";
	}
	
    private Map<String, Boolean> createOptionMap(String str) {
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        if (StringUtils.isNotEmpty(str)) {
            String[] arr = StringUtils.split(str, ",");
            for (String s : arr) {
                if (StringUtils.isNotEmpty(s.trim())) {
                    map.put(s.trim(), true);
                }
            }
        }
        return map;
    }
}
