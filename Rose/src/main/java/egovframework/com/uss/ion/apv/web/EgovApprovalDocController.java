package egovframework.com.uss.ion.apv.web;

import java.io.File;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.uss.ion.apm.service.EgovFormService;
import egovframework.com.uss.ion.apm.service.EgovLabelService;
import egovframework.com.uss.ion.apm.service.FormVO;
import egovframework.com.uss.ion.apm.service.LabelVO;
import egovframework.com.uss.ion.apu.Pagination;
import egovframework.com.uss.ion.apu.PathUtil;
import egovframework.com.uss.ion.apu.EgovRequestUtilService;
import egovframework.com.uss.ion.apv.ApprovalConstants;
import egovframework.com.uss.ion.apv.ApprovalContext;
import egovframework.com.uss.ion.apv.Doc;
import egovframework.com.uss.ion.apv.service.ApprovedDocVO;
import egovframework.com.uss.ion.apv.service.AttachFileVO;
import egovframework.com.uss.ion.apv.service.DeptApprovalAuthVO;
import egovframework.com.uss.ion.apv.service.DraftDocVO;
import egovframework.com.uss.ion.apv.service.EgovApprovalAuthService;
import egovframework.com.uss.ion.apv.service.EgovApprovalDocService;
import egovframework.com.uss.ion.apv.service.EgovAttachFileService;
import egovframework.com.uss.ion.apv.service.EgovRecipientService;
import egovframework.com.uss.ion.apv.service.EgovSignerChangeHistoryService;
import egovframework.com.uss.ion.apv.service.EgovSignerHistoryService;
import egovframework.com.uss.ion.apv.service.EgovSignerService;
import egovframework.com.uss.ion.apv.service.InboxDocVO;
import egovframework.com.uss.ion.apv.service.IncomingDocVO;
import egovframework.com.uss.ion.apv.service.LabelDocVO;
import egovframework.com.uss.ion.apv.service.OngoingDocVO;
import egovframework.com.uss.ion.apv.service.OutboxDocVO;
import egovframework.com.uss.ion.apv.service.OutgoingDocVO;
import egovframework.com.uss.ion.apv.service.PassboxDocVO;
import egovframework.com.uss.ion.apv.service.RecipientVO;
import egovframework.com.uss.ion.apv.service.RegisterIncomingVO;
import egovframework.com.uss.ion.apv.service.RegisterInternalVO;
import egovframework.com.uss.ion.apv.service.SignerChangeHistoryVO;
import egovframework.com.uss.ion.apv.service.SignerHistoryVO;
import egovframework.com.uss.ion.apv.service.SignerVO;
import egovframework.com.uss.ion.apv.service.UserApprovalAuthVO;
import egovframework.com.uss.ion.apv.service.WaitingDocVO;
import egovframework.com.uss.omt.OrgConstant;
import egovframework.com.uss.omt.RuleParameter;
import egovframework.com.uss.omt.service.DeptVO;
import egovframework.com.uss.omt.service.EgovDeptService;
import egovframework.com.uss.omt.service.EgovPositionService;
import egovframework.com.uss.omt.service.PositionVO;
import egovframework.com.uss.umt.service.EgovUserManageService;
import egovframework.com.uss.umt.service.UserManageVO;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

/**
 * 게시물 관리를 위한 컨트롤러 클래스
 * 
 * @author 공통서비스개발팀 이삼섭
 * @since 2009.06.01
 * @version 1.0
 * @see
 *
 * 		<pre>
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
public class EgovApprovalDocController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EgovApprovalDocController.class);
    
	@Resource(name = "EgovApprovalDocService")
	private EgovApprovalDocService approvalDocService;

	@Resource(name = "EgovLabelService")
	private EgovLabelService labelService;

	@Resource(name = "EgovDeptService")
	private EgovDeptService deptService;
	
	@Resource(name = "egovDocIdGnrService")
    private EgovIdGnrService idgenService;

	@Resource(name = "EgovUserManageService")
	private EgovUserManageService userService;
	
	@Resource(name = "EgovFormService")
	private EgovFormService formService;
	
	@Resource(name = "EgovSignerService")
	private EgovSignerService signerService;
	
	@Resource(name = "EgovSignerHistoryService")
	private EgovSignerHistoryService signerHistoryService;
	
	@Resource(name = "EgovSignerChangeHistoryService")
	private EgovSignerChangeHistoryService signerChangeHistoryService;
	
	@Resource(name = "EgovRecipientService")
	private EgovRecipientService recipientService;
	
	@Resource(name = "EgovPositionService")
	private EgovPositionService posiService;
	
	@Resource(name = "EgovApprovalAuthService")
	private EgovApprovalAuthService authService;
	
	@Resource(name = "EgovAttachFileService")
	private EgovAttachFileService attachFileService;
	
	@Resource(name = "EgovRequestUtilService")
	private EgovRequestUtilService requestUtilService;
	
    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;
    
	@RequestMapping("/home.do")
	protected ModelAndView setContentInit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView model = new ModelAndView();
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		UserManageVO user = userService.getUser(loginVO.getUniqId());
		String uniqId = user.getUniqId();
		String deptId = user.getOrgnztId();
		List<Integer> myCount = new ArrayList<Integer>();

		// my count
		int inboxCount = 0;
		int waitingCount = approvalDocService.getWaitingDocCnt(uniqId);
		int ongoingCount = approvalDocService.getOngoingDocCnt(uniqId);
		int incomingCount = approvalDocService.getIncomingDocCnt(deptId);
		int outgoingCount = approvalDocService.getOutgoingDocCnt(deptId);
		myCount.add(inboxCount);
		myCount.add(waitingCount);
		myCount.add(ongoingCount);
		myCount.add(incomingCount);
		myCount.add(outgoingCount);
		
		ModelAndView labelTree = treeList(loginVO.getUniqId());
		
		model.addObject("list", null);
		model.addObject("labelTree",labelTree.getModel().get("labelList"));
		model.addObject("userAuth",labelTree.getModel().get("userAuth"));
		model.addObject("deptAuth",labelTree.getModel().get("deptAuth"));
		model.addObject("user", user);
		model.addObject("myCountList", myCount);
		model.setViewName("egovframework/com/cmm/EgovUnitContentInit");
		return model;
	}
    
	protected boolean isAuthorization(LoginVO loginVO, String relID, String authes) throws Exception {
      
		// 권한 검사. 권한이 없는 경우 throw new NoAuthorizationException(OrgErrorCode.ORG_NO_AUTHORIZATION, "message");
		// OrgConstant.AUTH_ADMINISTRATOR 의 경우 relID 는 자기자신이며
		// OrgConstant.AUTH_DEPT_MANAGER 의 경우 relID 는 대상 부서이며, 대상부서와 하위부서에 대한 권한을 가진다. 
		DeptVO userDept = deptService.getDept(loginVO.getOrgnztId());
		
		String[] arrAuthes = StringUtils.split(authes, ",;");
		for (int i = 0; i < arrAuthes.length; i++) {
			String auth = arrAuthes[i];
			RuleParameter parameter = null;
			if (OrgConstant.AUTH_ADMINISTRATOR.equals(auth)) {
				parameter = new RuleParameter(null, auth, false);
				return StringUtils.equals(OrgConstant.SUPERMANAGER_ID, loginVO.getId());
			} else {
				parameter = new RuleParameter(relID, auth, true);
				return StringUtils.equals(userDept.getDeptBoxUserId(), loginVO.getId());
			}
		}
		return false;
	}
    
	@RequestMapping("/mytodo.do")
	protected ModelAndView mytodo(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView model = new ModelAndView();
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
		if(isAuthentication(request, response, loginVO)) {
			String docType = "mytodo";
			UserManageVO user = userService.getUser(loginVO.getUniqId());
			ApprovalConstants appConstants = new ApprovalConstants();

			List<WaitingDocVO> listApprovalWaiting  = approvalDocService.getWaitingDocList(loginVO.getUniqId());
			List<OutgoingDocVO> listApprovalOutgoing = approvalDocService.getOutgoingDocList(loginVO.getOrgnztId());
			List<IncomingDocVO> listApprovalIncoming = approvalDocService.getIncomingDocList(loginVO.getOrgnztId());
			ModelAndView labelTree = treeList(loginVO.getUniqId());
			
			model.addObject("labelTree",labelTree.getModel().get("labelList"));
			model.addObject("userAuth",labelTree.getModel().get("userAuth"));
			model.addObject("deptAuth",labelTree.getModel().get("deptAuth"));
			model.addObject("listWaiting", listApprovalWaiting);
			model.addObject("listOutgoing", listApprovalOutgoing);
			model.addObject("listIncoming", listApprovalIncoming);
			model.addObject("appConstants", appConstants);
			model.addObject("user", user);
	    	model.addObject("docType",docType);
	    	model.addObject("labelID",request.getParameter("labelId"));
	    	model.addObject("labelNm",request.getParameter("labelNm"));
			model.setViewName("egovframework/com/uss/ion/apv/EgovMytodo");
		}else{
			model.setViewName("egovframework/com/uat/uia/EgovLoginUsr");
		}
		return model;
	}
	
	@RequestMapping("/approvalTabList.do")
	protected ModelAndView approvalTabList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView model = new ModelAndView();
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if(isAuthentication(request, response, loginVO)) {
			String docType = request.getParameter("docType");
			UserManageVO user = userService.getUser(loginVO.getUniqId());

			if(docType.equalsIgnoreCase("waiting")){
				List<WaitingDocVO> listApprovalWaiting = approvalDocService.getWaitingDocList(loginVO.getUniqId());
				model.addObject("list", listApprovalWaiting);
				model.addObject("tabName", docType);
			}else if (docType.equalsIgnoreCase("ongoing")) {
				List<OngoingDocVO> listApprovalOngoing = approvalDocService.getOngoingDocList(loginVO.getUniqId());
				model.addObject("list", listApprovalOngoing);
				model.addObject("tabName", docType);
			}else {
				model.addObject("list", null);
				model.addObject("tabName", docType);
			}
			model.addObject("user", user);
			model.addObject("docType",docType);
			model.setViewName("egovframework/com/cmm/EgovApprovalTabList");
		}else{
			model.setViewName("egovframework/com/uat/uia/EgovLoginUsr");
		}
		return model;
	}
	
	@RequestMapping("/draft.do")
	public String draft(HttpServletRequest request, HttpServletResponse response)throws Exception{
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        String url = null;
        
        if (isAuthentication(request, response, loginVO)) {
			UserManageVO user = userService.getUser(loginVO.getUniqId());

			String userId = user.getUniqId();
			String formId = request.getParameter("formId");
			String docId = request.getParameter("docId");
            String docType = request.getParameter("docType");
			UserManageVO drafter = userService.getUser(userId);

			if(docId.equals("no_doc_id")){
				docId = idgenService.getNextStringId();
			}
			Doc doc = requestUtilService.getDoc(request, docId);
			List<SignerVO> signerList = requestUtilService.getSignerList(request, docId, drafter.getUniqId(), doc.getDocVersion());
			String docBody = requestUtilService.getDocBody(request);
			String opinion = request.getParameter("opinion");

			List<AttachFileVO> attachList = null;
			List<RecipientVO> recipientList = requestUtilService.getRecipientList(request, docId);
			if (request instanceof MultipartHttpServletRequest) {
                opinion = URLDecoder.decode(opinion, "UTF-8");
                setOpinion(signerList, userId, opinion);
                
                attachList = requestUtilService.getAttachFileList((MultipartHttpServletRequest)request, doc.getDocID());
			} else {
				setOpinion(signerList, userId, opinion);
			    attachList = requestUtilService.getAttachList(request, doc);
			}

			Map parameterMap = requestUtilService.getParameterMap(request);
			
			FormVO form = formService.getForm(formId);
			if(form == null){
				throw new Exception("can not find the form info["+formId+"]");
			}
			
	        String preAttachFileListJson = request.getParameter("preAttachedFileList");
	            
			ApprovalContext approvalContext = new ApprovalContext(doc, signerList, attachList, recipientList);
			signerService.approveForDraft(drafter, approvalContext, docBody, parameterMap, preAttachFileListJson);
			url = "forward:/approvalDocPageList.do?docType="+ docType;
		}
		return url;
	}
	
	@RequestMapping("/redraft.do")
	public String redraft(HttpServletRequest request, HttpServletResponse response)throws Exception{
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		String url = null;
		
		if (isAuthentication(request, response, loginVO)) {
			UserManageVO user = userService.getUser(loginVO.getUniqId());
			
			String userId = user.getUniqId();
			String formId = request.getParameter("formId");
			String docId = request.getParameter("docId");
			String docType = request.getParameter("docType");
			String editFlag = request.getParameter("editFlag");
			String preAttachFileListJson = request.getParameter("preAttachedFileList");
			UserManageVO drafter = userService.getUser(userId);
			int docVersion = 1;
			
			Doc doc = requestUtilService.getDoc(request, docId);
			if(editFlag.equalsIgnoreCase("true")){
				docVersion = doc.getDocVersion()+1;
				doc.setDocVersion(docVersion);
				PathUtil.moveFile(doc);
			}else{
				docVersion = doc.getDocVersion();
			}
			
			List<SignerVO> signerList = requestUtilService.getSignerList(request, docId, drafter.getUniqId(), doc.getDocVersion());
			String docBody = signerService.getDocForView(doc);
			String opinion = request.getParameter("opinion");
			
			List<AttachFileVO> oldAttachList = attachFileService.getAttachFileListByDocId(doc.getDocID());
			List<AttachFileVO> newAttachList = null;
			if (request instanceof MultipartHttpServletRequest) {
	            opinion = URLDecoder.decode(opinion, "UTF-8");
	            setOpinion(signerList, userId, opinion);
	            
	            newAttachList = requestUtilService.getAttachFileList((MultipartHttpServletRequest)request, doc.getDocID());
	            preAttachFileListJson = request.getParameter("preAttachedFileList");
	            if(preAttachFileListJson.length() > 2){
	            	List<String> preAttachFileListString = signerService.getPreAttachedFileIdList(preAttachFileListJson);
	            	for(int i=0; i<preAttachFileListString.size(); i++){
		            	AttachFileVO preAttachFileList = new AttachFileVO();
		            	preAttachFileList.setAttachID(preAttachFileListString.get(i));
		            	newAttachList.add(preAttachFileList);
	            	}//end of for
	            }
			}else{
				setOpinion(signerList, userId, opinion);
				newAttachList = requestUtilService.getAttachList(request, doc);
			}
			makeChangedAttachmentList(oldAttachList, newAttachList, userId);
			
			List<RecipientVO> recipientList = requestUtilService.getRecipientList(request, docId);
			Map parameterMap = requestUtilService.getParameterMap(request);
			
			FormVO form = formService.getForm(formId);
			if(form == null){
				throw new Exception("can not find the form info["+formId+"]");
			}
			
			ApprovalContext approvalContext = new ApprovalContext(doc, signerList, newAttachList, recipientList);
			signerService.approveForDraft(drafter, approvalContext, docBody, parameterMap, preAttachFileListJson);
			url = "forward:/approvalDocPageList.do?docType="+ docType;
		}
		return url;
	}
	
	private void setOpinion(List<SignerVO> signerList, String userId, String opinion) {
		if ((signerList == null)
				|| (userId == null)) {
			return;
		}
		
		for (SignerVO user : signerList) {
			if (user.getUserID().equalsIgnoreCase(userId)) {
				user.setOpinion(opinion);
				break;
			}
		}
	}
	
	@RequestMapping("/signerline.do")
	public ModelAndView signerline(HttpServletRequest request, HttpServletResponse response)throws Exception{
		ModelAndView model = new ModelAndView();
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
        if(isAuthentication(request, response, loginVO)) {
            UserManageVO user = userService.getUser(loginVO.getUniqId());
			String redraft = request.getParameter("redraft");
			
			List<SignerVO> signerList = requestUtilService.getSignerList(request, null, null, 0);
			Collections.sort(signerList, new Comparator<SignerVO>() {
				public int compare(SignerVO o1, SignerVO o2) {
					return o2.getSignSeq() - o1.getSignSeq();
				}
			});
			model.addObject("signerList", signerList);
			model.addObject("user", user);
			model.addObject("redraft", redraft);
			model.setViewName("egovframework/com/uss/ion/cmm/EgovSignerModal");
		}else{
			model.setViewName("egovframework/com/uat/uia/EgovLoginUsr");
		}
		return model;
	}
	
	@RequestMapping("/selectUser.do")
	public ModelAndView selectUser(HttpServletRequest request, HttpServletResponse response)throws Exception{
		ModelAndView model = new ModelAndView();
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
        if(isAuthentication(request, response, loginVO)) {
            UserManageVO user = userService.getUser(loginVO.getUniqId());
			String checkType = request.getParameter("checkType");
			
			model.addObject("user", user);
			model.addObject("checkType", checkType);
			model.setViewName("egovframework/com/uss/ion/cmm/EgovUserSelectionModal");
		}else{
			model.setViewName("egovframework/com/uat/uia/EgovLoginUsr");
		}
		return model;
	}
	
	@RequestMapping("/recipient.do")
	public ModelAndView recipient(HttpServletRequest request, HttpServletResponse response)throws Exception{
		ModelAndView model = new ModelAndView();
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
        if(isAuthentication(request, response, loginVO)) {
            UserManageVO user = userService.getUser(loginVO.getUniqId());
			String docId = request.getParameter("docId");
			
			List<RecipientVO> recipientList = requestUtilService.getRecipientList(request, docId);
			
			model.addObject("recipientList", recipientList);
			model.addObject("user", user);
			model.setViewName("egovframework/com/uss/ion/cmm/EgovRecipientModal");
		}else{
			model.setViewName("egovframework/com/uat/uia/EgovLoginUsr");
		}
		return model;
	}
	
	@RequestMapping("/orgPopup.do")
	public ModelAndView orgPopup(HttpServletRequest request, HttpServletResponse response)throws Exception{
		ModelAndView model = new ModelAndView();
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
        if(isAuthentication(request, response, loginVO)) {
            UserManageVO user = userService.getUser(loginVO.getUniqId());
			
			String checkType = request.getParameter("checkType");
			model.addObject("user", user);
			model.addObject("checkType", checkType);
			model.setViewName("egovframework/com/uss/ion/cmm/EgovOrgnztSelectionModal");
		}else{
			model.setViewName("egovframework/com/uat/uia/EgovLoginUsr");
		}
		return model;
	}
	
	@RequestMapping("/opinionAdd.do")
	public ModelAndView opinionAdd(HttpServletRequest request, HttpServletResponse response)throws Exception{
		ModelAndView model = new ModelAndView();
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
        if(isAuthentication(request, response, loginVO)) {
            UserManageVO user = userService.getUser(loginVO.getUniqId());
			String type = request.getParameter("type");
			
			model.addObject("type", type);
			model.addObject("user", user);
			model.setViewName("egovframework/com/uss/ion/cmm/EgovAddOpinionModal");
		}else{
			model.setViewName("egovframework/com/uat/uia/EgovLoginUsr");
		}
		return model;
	}
	
	@RequestMapping("/opinionView.do")
	public ModelAndView opinionView(HttpServletRequest request, HttpServletResponse response)throws Exception{
		ModelAndView model = new ModelAndView();
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
        if(isAuthentication(request, response, loginVO)) {
            UserManageVO user = userService.getUser(loginVO.getUniqId());
			String opinion = request.getParameter("opinion");
			
			model.addObject("opinion", opinion);
			model.addObject("user", user);
			model.setViewName("egovframework/com/uss/ion/cmm/EgovViewOpinionModal");
		}else{
			model.setViewName("egovframework/com/uat/uia/EgovLoginUsr");
		}
		return model;
	}
	
	@RequestMapping("/approvalHistory.do")
	public ModelAndView displaySignerLineHistory(HttpServletRequest request, HttpServletResponse response)throws Exception{
		ModelAndView model = new ModelAndView();
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
        if(isAuthentication(request, response, loginVO)) {
			String docId = request.getParameter("docId");
            UserManageVO user = userService.getUser(loginVO.getUniqId());
            
			List<SignerChangeHistoryVO> signerChangeHistory = signerChangeHistoryService.getChangeHistory(docId);
			List<SignerHistoryVO> signerHistoryList = signerHistoryService.getSignerHistoryList(docId);

			Collections.sort(signerHistoryList, new Comparator<SignerHistoryVO>() {
				public int compare(SignerHistoryVO o1, SignerHistoryVO o2) {
					if(o2.getSignerHistoryDate() == null && o2.getSignerHistoryDate() != null){
						return -1;
					}
					if(o2.getSignerHistoryDate() != null && o2.getSignerHistoryDate() == null){
						return 1;
					}
					return o2.getSignerHistoryDate().before(o1.getSignerHistoryDate()) ? 1 : -1;
				}
			});
			model.addObject("signerChangeHistoryList", signerChangeHistory);
			model.addObject("signerHistoryList", signerHistoryList);
			model.addObject("user", user);
			model.addObject("docId", docId);
			model.setViewName("egovframework/com/uss/ion/cmm/EgovSignerLineHistoryModal");
		}else{
			model.setViewName("egovframework/com/uat/uia/EgovLoginUsr");
		}
		return model;
	}
		
	@RequestMapping("/recipienthistory.do")
	public ModelAndView recipienthistory(HttpServletRequest request, HttpServletResponse response)throws Exception{
		ModelAndView model = new ModelAndView();
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
        if(isAuthentication(request, response, loginVO)) {
            UserManageVO user = userService.getUser(loginVO.getUniqId());
			String docId = request.getParameter("docId");
			List<RecipientVO> recipientList = recipientService.getRecipientList(docId);

			if(recipientList != null){
				Map<String, String> recpUserIDMap = new HashMap<String, String>();
				List<String> userIDList = new ArrayList<String>();
				for(int i=0; i<recipientList.size(); i++){
					RecipientVO recipient = recipientList.get(i);
					String recpUserId = null;
					
					if(recipient.getRecpFinishDt() != null && recipient.getRecpFinishUserId() != null){
						recpUserId = recipient.getRecpFinishUserId(); 
					}else if(recipient.getRecpRecpDt() != null && recipient.getRecpRecpUserId() != null){
						recpUserId = recipient.getRecpRecpUserId(); 
					}
					if(recpUserId != null){
						recpUserIDMap.put(recipient.getRecpId(), recpUserId);
						userIDList.add(recpUserId);
					}
				}
				if(recpUserIDMap.size() > 0){
					List<UserManageVO> userList = userService.getUserList(userIDList);
					
					for(int i=0; i<recipientList.size(); i++){
						RecipientVO recipient = recipientList.get(i);
						String recpUserId = (String)recpUserIDMap.get(recipient.getRecpId());
						if(recpUserId != null && userList != null){
							for(int j=0; j < userList.size(); j++){
								UserManageVO recpUser = (UserManageVO)userList.get(j);
								if(recpUserId.equals(recpUser.getUniqId())){
									PositionVO vo = posiService.getPosition(recpUser.getPositionId());
									String posiNm = null;
									if (posiNm != null) {
									    posiNm = vo.getPosiNm();
									}
									recipient.setPosition(posiNm);
									recipient.setName(recpUser.getEmplyrNm());
								}
							}
						}
					}
				}
			}
			model.addObject("recipientList", recipientList);
			model.addObject("user", user);
			model.setViewName("egovframework/com/uss/ion/cmm/EgovRecipientHistoryModal");
		}else{
			model.setViewName("egovframework/com/uat/uia/EgovLoginUsr");
		}
		return model;
	}
	@RequestMapping("/attachmentHistory.do")
	public ModelAndView attachmentHistory(HttpServletRequest request, HttpServletResponse response)throws Exception{
		ModelAndView model = new ModelAndView();
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if(isAuthentication(request, response, loginVO)) {
			UserManageVO user = userService.getUser(loginVO.getUniqId());
			String docId = request.getParameter("docId");
			List<AttachFileVO> attachmentList = attachFileService.getAttachFileListHistoryByDocId(docId);
			
			model.addObject("attachmentList", attachmentList);
			model.addObject("user", user);
			model.setViewName("egovframework/com/uss/ion/cmm/EgovattachmentHistoryModal");
		}else{
			model.setViewName("egovframework/com/uat/uia/EgovLoginUsr");
		}
		return model;
	}
	
	@RequestMapping("/documentEditor.do")
	public ModelAndView documentEditor(HttpServletRequest request, HttpServletResponse response)throws Exception{
		ModelAndView model = new ModelAndView();
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
        if(isAuthentication(request, response, loginVO)) {
            UserManageVO user = userService.getUser(loginVO.getUniqId());
            String docId = request.getParameter("docId");
            String action = request.getParameter("action");
            int docVersion = Integer.parseInt(request.getParameter("docVersion"));
            Doc doc = approvalDocService.getDoc(docId);
            doc.setDocVersion(docVersion);
            String docBody = signerService.getDocForView(doc);
            
			model.addObject("user", user);
			model.addObject("docBody", docBody);
			model.addObject("doc", doc);
			model.addObject("action", action);
			model.setViewName("egovframework/com/uss/ion/cmm/EgovDocumentEditorPopup");
		}else{
			model.setViewName("egovframework/com/uat/uia/EgovLoginUsr");
		}
		return model;
	}
	
	@RequestMapping("/deleteTmpDocument.do")
	public void deleteTmpDocument(HttpServletRequest request, HttpServletResponse response)throws Exception{
		String docId = request.getParameter("docId");
		Doc doc = approvalDocService.getDoc(docId);
		int docVersion = doc.getDocVersion()+1;
		doc.setDocVersion(docVersion);
		
		PathUtil.deleteTmpFile(doc);
		
	}
	
	@RequestMapping("/saveDocument.do")
	public void saveDocument(HttpServletRequest request, HttpServletResponse response)throws Exception{
		String docId = request.getParameter("docId");
		Doc doc = approvalDocService.getDoc(docId);
		String docBody = request.getParameter("docBody");
		int docVersion = doc.getDocVersion()+1;
		//docBody를 임시파일로 저장 
		//이후 임시파일 검색해서 있으면 버전 up해서 정보 변경하고 파일 위치변경?
		doc.setDocVersion(docVersion);
		PathUtil.createTmpDocFile(doc, docBody);
	}
	
	@RequestMapping("/merge.do")
	public ModelAndView merge(HttpServletRequest request, HttpServletResponse response)throws Exception{
		// @TODO must get the user'id from the authentication Info.
		String type = request.getParameter("type");
		String targetId = request.getParameter("targetId"); 
		
		Doc doc = requestUtilService.getDoc(request, null);
		List<SignerVO> signerList = requestUtilService.getSignerList(request, null, null, 0);
		
		String content = "";
		if(type == null || type.equals("sign")){
			content = signerService.mergeSignTable(doc, targetId, signerList);
		}
		ModelAndView model = new ModelAndView();
		model.addObject("content", content);
		model.setViewName("egovframework/com/uss/ion/cmm/EgovMerge");
		return model;
	}
	
	@RequestMapping("/approved.do")
	public String approved(HttpServletRequest request, HttpServletResponse response)throws Exception{
		// @TODO must get the user'id from the authentication Info.
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        String url = null;
        
        if (isAuthentication(request, response, loginVO)) {
        	String userId = request.getParameter("userId");
        	String formId = request.getParameter("formId");
        	String docId = request.getParameter("docId");
        	String preAttachFileListJson = null;
        	String editFlag = request.getParameter("editFlag");
        	String docType = request.getParameter("docType");
        	int docVersion = 1;
			UserManageVO approver = userService.getUser(loginVO.getUniqId());
			
			Doc doc = approvalDocService.getDoc(docId);
			if(doc == null){
				throw new Exception("can not find the doc info["+docId+"]");
			}
			
			if(editFlag.equalsIgnoreCase("true")){
				docVersion = doc.getDocVersion()+1;
				doc.setDocVersion(docVersion);
				approvalDocService.updateDoc(doc);
				PathUtil.moveFile(doc);
			}else{
				docVersion = doc.getDocVersion();
			}
			
			List<SignerVO> signerList = requestUtilService.getSignerList(request, docId, approver.getUniqId(), docVersion);
			String docBody = signerService.getDocForView(doc);
			List<RecipientVO> recipientList = recipientService.getRecipientList(docId);
			Map parameterMap = requestUtilService.getParameterMap(request);
			String opinion = request.getParameter("opinion");
			setOpinion(signerList, userId, opinion);
			
			List<AttachFileVO> oldAttachList = attachFileService.getAttachFileListByDocId(doc.getDocID());
			List<AttachFileVO> newAttachList = null;
			if (request instanceof MultipartHttpServletRequest) {
	            opinion = URLDecoder.decode(opinion, "UTF-8");
	            setOpinion(signerList, userId, opinion);
	            
	            newAttachList = requestUtilService.getAttachFileList((MultipartHttpServletRequest)request, doc.getDocID());
	            preAttachFileListJson = request.getParameter("preAttachedFileList");
	            if(preAttachFileListJson.length() > 2){
	            	List<String> preAttachFileListString = signerService.getPreAttachedFileIdList(preAttachFileListJson);
	            	for(int i=0; i<preAttachFileListString.size(); i++){
		            	AttachFileVO preAttachFileList = new AttachFileVO();
		            	preAttachFileList.setAttachID(preAttachFileListString.get(i));
		            	newAttachList.add(preAttachFileList);
	            	}//end of for
	            }
			}else{
				setOpinion(signerList, userId, opinion);
				newAttachList = requestUtilService.getAttachList(request, doc);
			}
			makeChangedAttachmentList(oldAttachList, newAttachList, userId);
			
			FormVO form = formService.getForm(formId);
			if(form == null){
				throw new Exception("can not find the form info["+formId+"]");
			}
			ApprovalContext approvalContext = new ApprovalContext(doc, signerList, newAttachList, recipientList);
			signerService.approveForApprove(approver, approvalContext, docBody, parameterMap, preAttachFileListJson);
		
			url = "forward:/approvalDocPageList.do?docType="+ docType;
        }
        return url;
	}
	
	public void makeChangedAttachmentList(List<AttachFileVO> oldAttachList, List<AttachFileVO> newAttachList, String userId) throws Exception{
		for(int i=oldAttachList.size()-1; i>=0; i--){
			for(int j=newAttachList.size()-1; j>=0; j--){
				if(oldAttachList.get(i).getAttachID().equalsIgnoreCase(newAttachList.get(j).getAttachID())){
					oldAttachList.remove(i);
					newAttachList.remove(j);
					break;
				}
			}//end of for newAttach
		}//end of for orgAttach
		//Action:delete 
		for(AttachFileVO oldAttach : oldAttachList){
			if(!(oldAttach.equals(null))){
				oldAttach.setAttachDateTime(new Date());
				oldAttach.setAttachSignerId(userId);
				oldAttach.setAttachAction("delete");
				attachFileService.insertChangedAttachFile(oldAttach);
			}
		}
		//Action:add
		for(AttachFileVO newAttach : newAttachList){
			if(!(newAttach.equals(null))){
				newAttach.setAttachDateTime(new Date());
				newAttach.setAttachSignerId(userId);
				newAttach.setAttachAction("add");
				attachFileService.insertChangedAttachFile(newAttach);
			}
		}
	}
	
	@RequestMapping("/rejected.do")
	public ModelAndView rejected(HttpServletRequest request, HttpServletResponse response)throws Exception{
		// @TODO must get the user'id from the authentication Info.
		String docId = request.getParameter("docId");
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		UserManageVO rejector = userService.getUser(loginVO.getUniqId());
		
		String opinion = request.getParameter("opinion");
		
		Doc doc = approvalDocService.getDoc(docId);
		if (doc == null){
			throw new Exception("can not find the doc info["+docId+"]");
		}
		File docFile = signerService.approveForReject(rejector, doc, opinion);
		
		String docBody = FileUtils.readFileToString(docFile, "utf-8");
		
		ModelAndView model = new ModelAndView();
		model.addObject("rejector", rejector);
		model.addObject("doc", doc);
		model.addObject("docBody", docBody);
		model.setViewName("egovframework/com/uss/ion/apv/EgovWaitList");

		String editFlag = request.getParameter("editFlag");
		if(editFlag.equalsIgnoreCase("true")){
			int docVersion = doc.getDocVersion()+1;
			doc.setDocVersion(docVersion);
			PathUtil.deleteTmpFile(doc);
		}
		return model;
	}

    @RequestMapping("/hold.do")
    public ModelAndView hold(HttpServletRequest request, HttpServletResponse response)throws Exception{
        // @TODO must get the user'id from the authentication Info.
        String docId = request.getParameter("docId");
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        UserManageVO holder = userService.getUser(loginVO.getUniqId());
        
        String opinion = request.getParameter("opinion");
        
        Doc doc = approvalDocService.getDoc(docId);
        if (doc == null){
            throw new Exception("can not find the doc info["+docId+"]");
        }
        File docFile = signerService.approveForHold(holder, doc, opinion);
        String docBody = FileUtils.readFileToString(docFile, "utf-8");
        
        ModelAndView model = new ModelAndView();
        model.addObject("holder", holder);
        model.addObject("doc", doc);
        model.addObject("docBody", docBody);
        model.setViewName("egovframework/com/uss/ion/apv/EgovWaitList");
        
        String editFlag = request.getParameter("editFlag");
        if(editFlag.equalsIgnoreCase("true")){
        	int docVersion = doc.getDocVersion()+1;
        	doc.setDocVersion(docVersion);
        	PathUtil.deleteTmpFile(doc);
        }
        return model;
    }
    
    @RequestMapping(value="/listApprovalRecipient.do" , method=RequestMethod.POST)
   	public void listApprovalRecipient(HttpServletRequest request,HttpServletResponse response) throws Exception {
    	List<SignerVO> listSigner = new ArrayList<SignerVO>();
   		String docId = request.getParameter("docId");
   		StringBuffer sb = new StringBuffer();
   		response.setContentType("text/xml;charset=UTF-8");
   		response.setHeader("Cache-Control", "no-cache");
   		PrintWriter out = response.getWriter();

   		if(docId != null){
   			listSigner = signerService.getApprovalSignerList(docId);
   		}
   		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		for(int i = 0 ; i < listSigner.size(); i++){
			SignerVO sn = listSigner.get(i);
			
			sb.append("<tr class='listSignerTr'>");
			sb.append("<td>"+sn.getSignSeq()+"</td>");
			sb.append("<td class=''>"+sn.getSignerDeptName()+"</td>");
			sb.append("<td>"+sn.getSignerPositionName()+"</td>");
			sb.append("<td>"+sn.getSignerName()+"</td>");
			
			if (sn.getSignState().equals("SS00")) {
			    sb.append("<td>"+ egovMessageSource.getMessage("appvl.signerState.SS00")+" </td>");
			} else if (sn.getSignState().equals("SS01")) {
			    sb.append("<td>"+ egovMessageSource.getMessage("appvl.signerState.SS01")+" </td>");
			} else if (sn.getSignState().equals("SS02")) {
			    sb.append("<td>"+ egovMessageSource.getMessage("appvl.signerState.SS02")+" </td>");
			} else if (sn.getSignState().equals("SS03")) {
			    sb.append("<td>"+ egovMessageSource.getMessage("appvl.signerState.SS03")+" </td>");
			} else if (sn.getSignState().equals("SS09")) {
			    sb.append("<td>"+ egovMessageSource.getMessage("appvl.signerState.SS09")+" </td>");
			}
			
			if(sn.getSignDate() != null) {
			    sb.append("<td class=''>"+ dateFormat.format(sn.getSignDate())+"</td>");
			} else {
			    sb.append("<td class=''> </td>");
			}
			sb.append("</tr>");
		}
       	out.write(sb.toString());
   		out.flush();
       }
    
    @RequestMapping(value="/approvalDocPageList.do" , method=RequestMethod.POST)
	public ModelAndView listApprovalDocument(@ModelAttribute("page") Pagination page,HttpServletRequest request,HttpServletResponse response) throws Exception{
    	ModelAndView model = new ModelAndView();
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
        if(isAuthentication(request, response, loginVO)) {
            UserManageVO user = userService.getUser(loginVO.getUniqId());
    	
			int totalCount = 0;
			List<WaitingDocVO> waitingList = new ArrayList<WaitingDocVO>();
			List<OutgoingDocVO> outgoingList = new ArrayList<OutgoingDocVO>();
			List<IncomingDocVO> incomingList = new ArrayList<IncomingDocVO>();
			List<OngoingDocVO> ongoingList = new ArrayList<OngoingDocVO>();
			List<DraftDocVO> draftList = new ArrayList<DraftDocVO>();
			List<ApprovedDocVO> approvalList = new ArrayList<ApprovedDocVO>();
			List<OutboxDocVO> outboxList = new ArrayList<OutboxDocVO>();
			List<InboxDocVO> inboxList = new ArrayList<InboxDocVO>();
			List<LabelDocVO> labelList = new ArrayList<LabelDocVO>();
			List<PassboxDocVO> passboxList = new ArrayList<PassboxDocVO>();
			
			String docType = request.getParameter("docType").toString();
			ModelAndView labelTree = treeList(user.getUniqId());
			model.addObject("labelTree",labelTree.getModel().get("labelList"));
			model.addObject("userAuth",labelTree.getModel().get("userAuth"));
			model.addObject("deptAuth",labelTree.getModel().get("deptAuth"));
			
			if(docType.equals("waiting")){
				if( page.getOrderColumn()==null || "".equals(page.getOrderColumn()) ){
					page.setOrderColumn("2");
					page.setOrderMethod("desc");
				}
				waitingList = approvalDocService.getWaitingDocList(user.getUniqId(), page);
				totalCount = approvalDocService.getWaitingDocCnt(user.getUniqId());
				
				model.addObject("waitingList",waitingList);
				model.setViewName("egovframework/com/uss/ion/apv/EgovWaitList");
			}
			else if(docType.equals("outgoing")){
				if( page.getOrderColumn()==null || "".equals(page.getOrderColumn()) ){
					page.setOrderColumn("1");
					page.setOrderMethod("desc");
				}
				outgoingList = approvalDocService.getOutgoingDocList(user.getOrgnztId(), page);
				totalCount = approvalDocService.getOutgoingDocCnt(user.getOrgnztId());
				
				model.addObject("outgoingList", outgoingList);
				model.setViewName("egovframework/com/uss/ion/apv/EgovOutgoingList");
			}
			else if(docType.equals("incoming")){
				if( page.getOrderColumn()==null || "".equals(page.getOrderColumn()) ){
					page.setOrderColumn("1");
					page.setOrderMethod("desc");
				}
				incomingList = approvalDocService.getIncomingDocList(user.getOrgnztId(), page);
				totalCount = approvalDocService.getIncomingDocCnt(user.getOrgnztId());
				
				model.addObject("incomingList",incomingList);
				model.setViewName("egovframework/com/uss/ion/apv/EgovIncomingList");
			}
			else if(docType.equals("ongoing")){
				if( page.getOrderColumn()==null || "".equals(page.getOrderColumn()) ){
					page.setOrderColumn("2");
					page.setOrderMethod("desc");
				}
				ongoingList = approvalDocService.getOngoingDocList(user.getUniqId(), page);
				totalCount = approvalDocService.getOngoingDocCnt(user.getUniqId());
				
				model.addObject("ongoingList",ongoingList);
				model.setViewName("egovframework/com/uss/ion/apv/EgovOngoingList");
			}
			else if(docType.equals("draft")){
				if( page.getOrderColumn()==null || "".equals(page.getOrderColumn()) ){
					page.setOrderColumn("2");
					page.setOrderMethod("desc");
				}
				draftList = approvalDocService.getDraftDocList(user.getUniqId(), page);
				totalCount = approvalDocService.getDraftDocCnt(user.getUniqId());
				
				model.addObject("draftList",draftList);
				model.setViewName("egovframework/com/uss/ion/apv/EgovDraftList");
			}
			else if(docType.equals("approval")){
				if( page.getOrderColumn()==null || "".equals(page.getOrderColumn()) ){
					page.setOrderColumn("2");
					page.setOrderMethod("desc");
				}
				approvalList = approvalDocService.getApprovedDocList(user.getUniqId(), page);
				totalCount = approvalDocService.getApprovedDocCnt(user.getUniqId());
				
				model.addObject("approvalList",approvalList);
				model.setViewName("egovframework/com/uss/ion/apv/EgovApprovalList");
			}
			else if(docType.equals("outbox")){
				if( page.getOrderColumn()==null || "".equals(page.getOrderColumn()) ){
					page.setOrderColumn("2");
					page.setOrderMethod("desc");
				}
				outboxList = approvalDocService.getOutboxDocList(user.getOrgnztId(), page);
				totalCount = approvalDocService.getOutboxDocCnt(user.getOrgnztId());
				
				model.addObject("outboxList",outboxList);
				model.setViewName("egovframework/com/uss/ion/apv/EgovOutboxList");
			}
			else if(docType.equals("inbox")){
				if( page.getOrderColumn()==null || "".equals(page.getOrderColumn()) ){
					page.setOrderColumn("2");
					page.setOrderMethod("desc");
				}
				inboxList = approvalDocService.getInboxDocList(user.getOrgnztId(), page);
				totalCount = approvalDocService.getInboxDocCnt(user.getOrgnztId());
				
				model.addObject("inboxList",inboxList);
				model.setViewName("egovframework/com/uss/ion/apv/EgovInboxList");
			}else if(docType.equals("label")){
				if( page.getOrderColumn()==null || "".equals(page.getOrderColumn()) ){
					page.setOrderColumn("2");
					page.setOrderMethod("desc");
				}
				String labelID = request.getParameter("labelId");
				String labelNm = request.getParameter("labelNm");
				LabelVO selectedLabel = labelService.getLabel(labelID);
				labelList = approvalDocService.getLabelDocList(selectedLabel.getDeptId(), labelID, page);
				totalCount = approvalDocService.getLabelDocCnt(selectedLabel.getDeptId(), labelID);
				
				model.addObject("labelList",labelList);
				model.addObject("labelNm",labelNm);
				model.addObject("labelID",labelID);
				model.setViewName("egovframework/com/uss/ion/apv/EgovLabelList");
			}else if(docType.equals("passbox")){
				if( page.getOrderColumn()==null || "".equals(page.getOrderColumn()) ){
					page.setOrderColumn("2");
					page.setOrderMethod("desc");
				}
				passboxList = approvalDocService.getPassboxDocList(user.getOrgnztId(), page);
				totalCount = approvalDocService.getPassboxDocCnt(user.getOrgnztId());
				
				model.addObject("passboxList",passboxList);
				model.setViewName("egovframework/com/uss/ion/apv/EgovPassboxList");
			}
			page.setTotalRecordCount(totalCount);

			model.addObject("page",page);
	    	model.addObject("user",user);
	    	model.addObject("docType",request.getParameter("docType"));
	    	model.addObject("labelID",request.getParameter("labelId"));
	    	model.addObject("labelNm",request.getParameter("labelNm"));
		}else{
			model.setViewName("egovframework/com/uat/uia/EgovLoginUsr");
		}
     	return model;
    }
    
	@RequestMapping("/tree.do")
	protected ModelAndView treeList(String userId) throws Exception{
		ModelAndView model = new ModelAndView();
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        UserManageVO user = userService.getUser(loginVO.getUniqId());
		String dept_id = user.getOrgnztId();

		List<LabelVO> labelList = labelService.getLabelTreeOfDeptID(dept_id);
		UserApprovalAuthVO userAuth = authService.getUserApprovalAuth(user.getUniqId());
		DeptApprovalAuthVO deptAuth = authService.getDeptApprovalAuth(dept_id);
		
		model.addObject("labelList",labelList);
		model.addObject("userAuth",userAuth);
		model.addObject("deptAuth",deptAuth);
		return model;
	}
	
	@RequestMapping("/approvalDraft.do")
	public ModelAndView approvalDraft(HttpServletRequest request,HttpServletResponse response)throws Exception{
		ModelAndView model = new ModelAndView();
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
        if(isAuthentication(request, response, loginVO)) {
            UserManageVO user = userService.getUser(loginVO.getUniqId());
			
			String userId = user.getUniqId();
			String formId = request.getParameter("selectformID");
			String formType = request.getParameter("selectformType");
			FormVO form = formService.getForm(formId);
			if(form == null){
				throw new Exception("can not find the form info["+formId+"]");
			}
			
			String companyId = user.getCompanyId();
			UserManageVO searchVO = new UserManageVO();
			searchVO.setUniqId(userId);
			searchVO.setComapnyId(companyId);
			UserManageVO drafter = userService.getUser(searchVO);
			
			ModelAndView labelTree = treeList(userId);
			
			List<SignerVO> signerList = signerService.getDefaultSignerList(form, drafter);
			Collections.sort(signerList, new Comparator<SignerVO>() {
				public int compare(SignerVO o1, SignerVO o2) {
					return o2.getSignSeq() - o1.getSignSeq();
				}
			});
			String docBody = signerService.createDraftDoc(form, drafter, null, null);
			
			model.addObject("drafter", drafter);
			model.addObject("formId", formId);
			model.addObject("docBody", docBody);
			model.addObject("signerList", signerList);
			model.addObject("labelTree", labelTree.getModel().get("labelList"));
			model.addObject("userAuth", labelTree.getModel().get("userAuth"));
			model.addObject("deptAuth", labelTree.getModel().get("deptAuth"));
			model.addObject("userId", userId);
			model.addObject("formType", formType);
			model.addObject("user", user);
			model.setViewName("egovframework/com/uss/ion/apv/EgovApprDraft");
		}else{
			model.setViewName("egovframework/com/uat/uia/EgovLoginUsr");
		}
		return model;
	}
	
	@RequestMapping("/approvalRedraft.do")
	public ModelAndView approvalRedraft(HttpServletRequest request,HttpServletResponse response)throws Exception{
		ModelAndView model = new ModelAndView();
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
        if(isAuthentication(request, response, loginVO)) {
            UserManageVO user = userService.getUser(loginVO.getUniqId());
			String userId = user.getUniqId();
			String docId = request.getParameter("docId");
			
			Doc doc =  approvalDocService.getDoc(docId);
			if(doc == null){
				throw new Exception("can not find the doc info["+docId+"]");
			}
			
			List<SignerVO> signerList = signerService.getSignerListForRedraft(doc);
			Collections.sort(signerList, new Comparator<SignerVO>() {
				public int compare(SignerVO o1, SignerVO o2) {
					return o2.getSignSeq() - o1.getSignSeq();
				}
			});
			
			String docBody = null;
			Doc orgDoc =  approvalDocService.getDoc(doc.getDocOrgId());

			if(doc.getDocType().equalsIgnoreCase(ApprovalConstants.DOC_TYPE_INCOMING)){
				docBody = signerService.getDocForView(orgDoc);
			}else {
				docBody = signerService.getWriteDocBody(doc);
			}
			
			List<AttachFileVO> attachList = attachFileService.getAttachFileListByDocId(doc.getDocID());
			/* 20160330_SUJI.H
			List<Attach> orgAttachList = null;
			if(doc.getDocOrgID() != null){
				orgAttachList = ApprovalContextFactory.getApprovalAttach().listApprovalAttach(doc.getDocOrgID());
			}
			*/
			
			List<RecipientVO> recipientList = recipientService.getRecipientList(docId);
			LabelVO label = labelService.getLabel(doc.getLbelId());
			UserManageVO drafter = userService.getUser(userId);
			ModelAndView labelTree = treeList(userId);
			
			if(doc.getDocType().equalsIgnoreCase(ApprovalConstants.DOC_TYPE_INCOMING)){
				model.setViewName("egovframework/com/uss/ion/apv/EgovApprRedraftForIncoming");
			}else{
				model.setViewName("egovframework/com/uss/ion/apv/EgovApprRedraft");
			}
			model.addObject("drafter", drafter);
			model.addObject("doc", doc);
			model.addObject("docBody", docBody);
			model.addObject("signerList", signerList);
			model.addObject("labelTree",labelTree.getModel().get("labelList"));
			model.addObject("userAuth",labelTree.getModel().get("userAuth"));
			model.addObject("deptAuth",labelTree.getModel().get("deptAuth"));
			model.addObject("userId",userId);
			model.addObject("attachList",attachList);
			model.addObject("recipientList",recipientList);
			model.addObject("label",label);
			model.addObject("user", user);
		}else{
			model.setViewName("egovframework/com/uat/uia/EgovLoginUsr");
		}
		return model;
	}
	
	@RequestMapping("/approvalApprove.do")
	public ModelAndView approvalApprove(HttpServletRequest request,HttpServletResponse response)throws Exception{
		ModelAndView model = new ModelAndView();
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
        if(isAuthentication(request, response, loginVO)) {
            UserManageVO user = userService.getUser(loginVO.getUniqId());
			
			String userId = user.getUniqId();
			String docId = request.getParameter("docId");
			
			Doc doc = approvalDocService.getDoc(docId);
			if(doc == null){
				throw new Exception("can not find the doc info["+docId+"]");
			}
			
			List<SignerVO> signerList = signerService.getApprovalSignerList(doc.getDocID());
			Collections.sort(signerList, new Comparator<SignerVO>() {
				public int compare(SignerVO o1, SignerVO o2) {
					return o2.getSignSeq() - o1.getSignSeq();
				}
			});
			
			String docBody = null;
			Doc orgDoc = approvalDocService.getDoc(doc.getDocOrgId());

			if(doc.getDocType().equalsIgnoreCase(ApprovalConstants.DOC_TYPE_INCOMING)){
				docBody = signerService.getDocForView(orgDoc);
			}else {
				docBody = signerService.getDocForView(doc);
			}
			
			List<AttachFileVO> attachList = attachFileService.getAttachFileListByDocId(doc.getDocID());
			List<AttachFileVO> orgAttachList = null;
			if(doc.getDocOrgId() != null){
				orgAttachList = attachFileService.getAttachFileListByDocId(doc.getDocOrgId());
			}
			
			List<RecipientVO> recipientList = recipientService.getRecipientList(docId);
			LabelVO label = labelService.getLabel(doc.getLbelId());
			
			UserManageVO approver = userService.getUser(userId);
			
			ModelAndView labelTree = treeList(userId);
			
			model.addObject("approver", approver);
			model.addObject("doc", doc);
			model.addObject("docBody", docBody);
			model.addObject("signerList", signerList);
			model.addObject("labelTree",labelTree.getModel().get("labelList"));
			model.addObject("userAuth",labelTree.getModel().get("userAuth"));
			model.addObject("deptAuth",labelTree.getModel().get("deptAuth"));
			model.addObject("userId",userId);
			model.addObject("attachList",attachList);
			model.addObject("orgAttachList",orgAttachList);
			model.addObject("recipientList",recipientList);
			model.addObject("label",label);
			model.addObject("user", user);
			model.setViewName("egovframework/com/uss/ion/apv/EgovApprApprove");
		}else{
			model.setViewName("egovframework/com/uat/uia/EgovLoginUsr");
		}
		return model;
	}
	
	@RequestMapping("/approvalView.do")
	public ModelAndView approvalView(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView model = new ModelAndView();
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
        if(isAuthentication(request, response, loginVO)) {
            UserManageVO user = userService.getUser(loginVO.getUniqId());
			
			String userId = user.getUniqId();
			String docId = request.getParameter("docId");
			
			Doc doc = approvalDocService.getDoc(docId);
			if(doc == null){
				throw new Exception("can not find the doc info["+docId+"]");
			}
			
			List<SignerVO> signerList = signerService.getApprovalSignerList(doc.getDocID());
			Collections.sort(signerList, new Comparator<SignerVO>() {
				public int compare(SignerVO o1, SignerVO o2) {
					return o2.getSignSeq() - o1.getSignSeq();
				}
			});
			
			String docBody = null;
			if(ApprovalConstants.APPROVAL_DOC_PP_F_PAPER.equals(doc.getDocPpF())){
				docBody = null;
			}else{
				docBody = signerService.getDocForView(doc);
			}
			
			List<AttachFileVO> attachList = attachFileService.getAttachFileListByDocId(doc.getDocID());
			List<AttachFileVO> orgAttachList = null;
			if(doc.getDocOrgId() != null){
				orgAttachList =  attachFileService.getAttachFileListByDocId(doc.getDocOrgId());
			}
			
			List<RecipientVO> recipientList = recipientService.getRecipientList(docId);
			LabelVO label = labelService.getLabel(doc.getLbelId());
			
			UserManageVO viewer = userService.getUser(userId);
			
			ModelAndView labelTree = treeList(userId);
			
			model.addObject("viewer", viewer);
			model.addObject("doc", doc);
			model.addObject("docBody", docBody);
			model.addObject("signerList", signerList);
			model.addObject("labelTree",labelTree.getModel().get("labelList"));
			model.addObject("userAuth",labelTree.getModel().get("userAuth"));
			model.addObject("deptAuth",labelTree.getModel().get("deptAuth"));
			model.addObject("userId",userId);
			model.addObject("attachList",attachList);
			model.addObject("orgAttachList",orgAttachList);
			model.addObject("recipientList",recipientList);
			model.addObject("label", label);
			model.addObject("user", user);
			model.setViewName("egovframework/com/uss/ion/apv/EgovApprView");
		}else{
			model.setViewName("egovframework/com/uat/uia/EgovLoginUsr");
		}
		return model;
	}
	
	@RequestMapping("/approvalReceive.do")
	public ModelAndView approvalReceive(HttpServletRequest request,HttpServletResponse response)throws Exception{
		ModelAndView model = new ModelAndView();
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
        if(isAuthentication(request, response, loginVO)) {
            UserManageVO user = userService.getUser(loginVO.getUniqId());
            
			String userId = user.getUniqId();
			String formId = null;
			String orgdocId = request.getParameter("orgdocId");
			
			Doc orgDoc = approvalDocService.getDoc(orgdocId);
			if(orgDoc == null){
				throw new Exception("can not find the original doc info["+orgdocId+","+orgDoc+"]");
			}
			
			UserManageVO receiver = userService.getUser(userId);
			
			RecipientVO recipient = recipientService.getRecipient(orgdocId, receiver.getOrgnztId());
			if (recipient == null){
				throw new Exception("can not find the original doc's recipient info["+orgdocId+","+receiver.getOrgnztId()+"]");
			}
			if ((recipient.getRecpRecpDt() != null) && (recipient.getRecpRecpUserId() != null)) { 
				throw new Exception("This document.is already registered.");
			}
			
			String selectformID = request.getParameter("selectformID");
			if(!StringUtils.isEmpty(selectformID)){
				formId = selectformID;
				orgDoc.setFormId(selectformID);
			}else{
				formId = orgDoc.getFormId();
			}			
			
			FormVO form = formService.getForm(orgDoc.getFormId());
			if(form == null){
				throw new Exception("can not find the original doc's form info["+orgDoc.getFormId()+"]");
			}
			
			List<SignerVO> signerList = signerService.getDefaultReceiveSignerList(form, receiver);
			Collections.sort(signerList, new Comparator<SignerVO>() {
				public int compare(SignerVO o1, SignerVO o2) {
					return o2.getSignSeq() - o1.getSignSeq();
				}
			});
			
			String docBody = null;
			if(ApprovalConstants.APPROVAL_DOC_PP_F_PAPER.equals(orgDoc.getDocPpF())){
				docBody = approvalDocService.createDocBody(orgDoc);
			}else{
				docBody = approvalDocService.getDocBody(orgDoc);
			}
			
			List<AttachFileVO> orgAttachList = attachFileService.getAttachFileListByDocId(orgDoc.getDocID());
			
			ModelAndView labelTree = treeList(userId);
			
			model.addObject("receiver", receiver);
			model.addObject("orgDoc", orgDoc);
			model.addObject("formId", formId);
			model.addObject("docBody", docBody);
			model.addObject("signerList", signerList);
			model.addObject("labelTree",labelTree.getModel().get("labelList"));
			model.addObject("userAuth",labelTree.getModel().get("userAuth"));
			model.addObject("deptAuth",labelTree.getModel().get("deptAuth"));
			model.addObject("userId",userId);
			model.addObject("orgAttachList",orgAttachList);
			model.addObject("user", user);
			model.setViewName("egovframework/com/uss/ion/apv/EgovApprReceive");
		}else{
			model.setViewName("egovframework/com/uat/uia/EgovLoginUsr");
		}
		return model;
	}
	
	@RequestMapping("/received.do")
	public ModelAndView received(HttpServletRequest request, HttpServletResponse response)throws Exception{
		// @TODO must get the user'id from the authentication Info.
		String formId = request.getParameter("formId");
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		UserManageVO receiver = userService.getUser(loginVO.getUniqId());
		
		String orgdocId = request.getParameter("orgdocId");
		Doc orgDoc = approvalDocService.getDoc(orgdocId);
		
		String docId = idgenService.getNextStringId();
		Doc doc = requestUtilService.getDocForReceive(request, docId, orgDoc);

		List<SignerVO> signerList = requestUtilService.getSignerList(request, docId, receiver.getUniqId(), doc.getDocVersion());
		String docBody = requestUtilService.getDocBody(request);
		List<RecipientVO> recipientList = requestUtilService.getRecipientList(request, docId);
		List<AttachFileVO> attachList = requestUtilService.getAttachListFromOrgDoc(request, doc);
		Map parameterMap = requestUtilService.getParameterMap(request);
		
		String opinion = request.getParameter("opinion");
		setOpinion(signerList, receiver.getUniqId(), opinion);
		
		FormVO form = formService.getForm(formId);
		if(form == null){
			throw new Exception("can not find the form info["+formId+"]");
		}
		
		ApprovalContext approvalContext = new ApprovalContext(doc, signerList, attachList, recipientList);
		File docFile = signerService.approveForReceive(receiver, approvalContext, docBody, parameterMap);
		
		docBody = FileUtils.readFileToString(docFile, "utf-8");
		
		ModelAndView model = new ModelAndView();
		model.addObject("drafter", receiver);
		model.addObject("doc", doc);
		model.addObject("docBody", docBody);
		model.addObject("signerList", signerList);
		model.setViewName("egovframework/com/uss/ion/apv/EgovIncomingList");
		return model;
	}
	
	@RequestMapping("/redraftForIncoming.do")
	public String redraftForIncoming(HttpServletRequest request, HttpServletResponse response)throws Exception{
		// @TODO must get the user'id from the authentication Info.
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		UserManageVO user = userService.getUser(loginVO.getUniqId());
		String url = null;
		
		if (isAuthentication(request, response, loginVO)) {
			String docType = request.getParameter("docType");
			String docId = request.getParameter("docId");
			String labelId = request.getParameter("labelId");
			String preAttachFileListJson = request.getParameter("preAttachedFileList");
			Doc doc = approvalDocService.getDoc(docId);
			doc.setLbelId(labelId);
			
			List<SignerVO> signerList = requestUtilService.getSignerList(request, docId, user.getUniqId(), doc.getDocVersion());
			String docBody = requestUtilService.getDocBody(request);
			Map parameterMap = requestUtilService.getParameterMap(request);
			
			String opinion = request.getParameter("opinion");
			setOpinion(signerList, user.getUniqId(), opinion);
			
			FormVO form = formService.getForm(doc.getFormId());
			if(form == null){
				throw new Exception("can not find the form info["+doc.getFormId()+"]");
			}
			
			ApprovalContext approvalContext = new ApprovalContext(doc, signerList, null, null);
			signerService.approveForDraft(user, approvalContext, docBody, parameterMap, preAttachFileListJson);
			
			url = "forward:/approvalDocPageList.do?docType="+ docType;
		}
		return url;
	}
	
	@RequestMapping("/view.do")
	protected ModelAndView view(HttpServletRequest request,HttpServletResponse response) throws Exception{
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        String userId = loginVO.getUniqId();
		ModelAndView model = new ModelAndView();
		ModelAndView labelTree = treeList(userId);
		
		model.addObject("labelTree",labelTree.getModel().get("labelList"));
		model.addObject("userAuth",labelTree.getModel().get("userAuth"));
		model.addObject("deptAuth",labelTree.getModel().get("deptAuth"));
		model.addObject("userId",userId);
		model.setViewName("approval/box/view");
		return model;
	}
	
	@RequestMapping("/register.do")
	protected ModelAndView register(HttpServletRequest request,HttpServletResponse response) throws Exception{
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        String userId = loginVO.getUniqId();
        
		ModelAndView model = new ModelAndView();
		ModelAndView labelTree = treeList(userId);
		
		model.addObject("labelTree",labelTree.getModel().get("labelList"));
		model.addObject("userAuth",labelTree.getModel().get("userAuth"));
		model.addObject("deptAuth",labelTree.getModel().get("deptAuth"));
		model.addObject("userId",userId);
		model.setViewName("approval/box/register");
		return model;
	}

	@RequestMapping(value="/selectLabel.do")
	protected ModelAndView selectLabel(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView model = new ModelAndView();
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
        if(isAuthentication(request, response, loginVO)) {
            UserManageVO user = userService.getUser(loginVO.getUniqId());
			String dept_id = user.getOrgnztId();
			
            List<LabelVO> listLabel = labelService.getLabelTreeOfDeptID(dept_id);
            
            for(int i = 0; i < listLabel.size(); i++){
                LabelVO lb = listLabel.get(i);
                for (int j = 0; j < listLabel.size(); j++) {
                    LabelVO lb2 = listLabel.get(j);
                    if(lb.getLabelId().equals(lb2.getLabelParentId())){
                        listLabel.get(i).setChildLabel("1");
                    }
                }
                    
            }
			String labelId = request.getParameter("labelId");
			String labelNm = request.getParameter("labelNm");
			
			model.addObject("listLabel", listLabel);
			model.addObject("labelId", labelId);
			model.addObject("labelNm", labelNm);
			model.setViewName("egovframework/com/uss/ion/cmm/EgovLabelSelectionModal");
		}else{
			model.setViewName("egovframework/com/uat/uia/EgovLoginUsr");
		}
		return model;
	}
		
	@RequestMapping(value="/formSelectLabel.do" , method=RequestMethod.POST)
   	public void formSelectLabel(HttpServletRequest request,HttpServletResponse response) throws Exception {
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
        if(isAuthentication(request, response, loginVO)) {
            UserManageVO user = userService.getUser(loginVO.getUniqId());
			String CompanyID = user.getCompanyId();
			
			String buttonType = request.getParameter("buttonType");
			List<FormVO> formList = formService.getApprovalFormList(user.getOrgnztId(), CompanyID, buttonType);
			
	   		StringBuffer sb = new StringBuffer();
	   		response.setContentType("text/xml;charset=UTF-8");
	   		response.setHeader("Cache-Control", "no-cache");
	   		PrintWriter out = response.getWriter();
	   			   		
	   		for(FormVO form : formList){
		   		sb.append("<tr class=\"formLabelTr\" style=\"cursor: pointer;\" onmouseover=\"this.style.backgroundColor='#f3f3f3'\" onmouseout=\"this.style.backgroundColor='#ffffff'\"  onclick=\"javascript:selectForm(this)\">");
		   		sb.append("<input type='hidden' value='"+form.getFormId()+"' name='formId'/>");
		   		sb.append("<input type='hidden' value='"+form.getFormType()+"' name='formType'/>");
		   		sb.append("<td>");
		   		if( CompanyID.equals(form.getOrgId()) ){
		   			sb.append("Common");
		   		}else{
		   			sb.append("Department");
		   		}
	   			sb.append("</td>");
		   		sb.append("<td>"+form.getFormName()+"</td>");
		   		sb.append("<td>"+form.getFormRemark()+"</td>");
		   		sb.append("</tr>");
	   		}
	       	out.write(sb.toString());
	   		out.flush();
		}
    }
	
	@RequestMapping(value="/downloadAttach.do" , method=RequestMethod.POST)
	public void readFilesUpload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String docID = (String) request.getParameter("docId");
		String attachId = (String) request.getParameter("attachId");

		AttachFileVO attach = new AttachFileVO();
        attach.setAttachID(attachId);
        
		attach = attachFileService.getAttachFileByAttachId(attach);
		if(attach == null){
			throw new Exception("The attach, attachId["+attachId+"] docID["+docID+"] does not exist.");
		}
		
		String contentType = null;
		try{
			contentType = request.getSession().getServletContext().getMimeType(attach.getAttachNm());
		}catch(Exception e){
			// ignore this.
		}
		if(contentType == null) contentType = "application/octet-stream";
		
		try {
			File attachFile = PathUtil.getAttachPath(attach);
			if(attachFile == null || !attachFile.exists()){
				throw new Exception("The attach file, attachId["+attachId+"] docID["+docID+"], path["+attachFile.getAbsolutePath()+"] does not exist.");
			}
			requestUtilService.transfer(request, response, attach.getAttachNm(), attachFile, contentType);
		} catch (Exception e) {
			throw e;
		}
	}

	protected boolean isAuthentication(HttpServletRequest request, HttpServletResponse response, LoginVO loginVO) throws Exception {
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
        if (isAuthenticated == false) {
            return false;
        }
		request.getSession().setAttribute("isAdmin", isAuthorization(loginVO, null, OrgConstant.AUTH_ADMINISTRATOR));
		request.getSession().setAttribute("isDeptAdmin", isAuthorization(loginVO, null, OrgConstant.AUTH_DEPT_MANAGER));
		return true;
	}

	@RequestMapping("/attachList.do")
	public void attachList(HttpServletRequest request,HttpServletResponse response)throws Exception{
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
        if(isAuthentication(request, response, loginVO)) {
            UserManageVO user = userService.getUser(loginVO.getUniqId());
			
			String userId = user.getUniqId();
			String docID = request.getParameter("docId");

	   		StringBuffer sb = new StringBuffer();
	   		response.setContentType("text/xml;charset=UTF-8");
	   		response.setHeader("Cache-Control", "no-cache");
	   		PrintWriter out = response.getWriter();
	   		
	   		if(docID!=null && !"".equals(docID)){
				List<AttachFileVO> attachList = attachFileService.getAttachFileListByDocId(docID);
				
		   		for(AttachFileVO attach : attachList){
		   			sb.append("<tr class='listAttachTr'>");
		   			sb.append("	<td>");
		   			sb.append("		<a href=\"javascript:downloadFile('"+request.getContextPath()+"/downloadAttach.do', '"+attach.getDocID()+"','"+attach.getAttachID()+"','"+userId+"')\">"+attach.getAttachNm()+"</a>" );
		   			sb.append("	</td>");
		   			sb.append("	<td>");
		   			if(attach.getAttachSize()/1024 < 1){
		   				sb.append("1 KB");
		   			}else if(attach.getAttachSize()/(1024*1024) < 1){
		   				sb.append((new DecimalFormat("###,###,###")).format(attach.getAttachSize() / 1024) + " KB");
		   			}else{
		   				sb.append((new DecimalFormat("###,###,###")).format(attach.getAttachSize() / (1024*1024)) + " MB");
		   			}
		   			sb.append("	</td>");
		   			sb.append("</tr>");
		   		}
			}
	       	out.write(sb.toString());
	   		out.flush();
   		}
	}

	@RequestMapping("/approvalRegisterIncoming.do")
	public ModelAndView approvalRegisterIncoming(HttpServletRequest request,HttpServletResponse response)throws Exception{
		ModelAndView model = new ModelAndView();
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
        if(isAuthentication(request, response, loginVO)) {
            UserManageVO user = userService.getUser(loginVO.getUniqId());
			
			String userId = user.getUniqId();
			UserManageVO drafter = userService.getUser(userId);
			
			ModelAndView labelTree = treeList(userId);
			
			model.addObject("drafter", drafter);
			model.addObject("labelTree",labelTree.getModel().get("labelList"));
			model.addObject("userAuth",labelTree.getModel().get("userAuth"));
			model.addObject("deptAuth",labelTree.getModel().get("deptAuth"));
			model.addObject("userId",userId);
			model.addObject("user", user);
			model.setViewName("egovframework/com/uss/ion/apv/EgovApprRegisterIncoming");
		}else{
			model.setViewName("egovframework/com/uat/uia/EgovLoginUsr");
		}
		return model;
	}
	
	@RequestMapping("/registeredIncoming.do")
	public String registeredIncoming(HttpServletRequest request,HttpServletResponse response)throws Exception{
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
        String url = null;
        if(isAuthentication(request, response, loginVO)) {
            UserManageVO user = userService.getUser(loginVO.getUniqId());
			
			RegisterIncomingVO incoming = requestUtilService.getRegisterIncoming(request, user);
			signerService.registerIncoming(incoming, user);
	        url = "forward:/approvalDocPageList.do?docType=incoming";
		}
		return url;
	}
	
	@RequestMapping("/approvalRegisterInternal.do")
	public ModelAndView approvalRegisterInternal(HttpServletRequest request,HttpServletResponse response)throws Exception{
		ModelAndView model = new ModelAndView();
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
        if(isAuthentication(request, response, loginVO)) {
            UserManageVO user = userService.getUser(loginVO.getUniqId());
			
			String userId = user.getUniqId();
			UserManageVO drafter = userService.getUser(userId);
			
			ModelAndView labelTree = treeList(userId);
			String labelId = request.getParameter("labelId");
			String labelNm = request.getParameter("labelNm");
			
			model.addObject("drafter", drafter);
			model.addObject("labelTree",labelTree.getModel().get("labelList"));
			model.addObject("userAuth",labelTree.getModel().get("userAuth"));
			model.addObject("deptAuth",labelTree.getModel().get("deptAuth"));
			model.addObject("userId",userId);
			model.addObject("labelId",labelId);
			model.addObject("labelNm",labelNm);
			model.addObject("user", user);
			model.setViewName("egovframework/com/uss/ion/apv/EgovApprRegisterInternal");
		}else{
			model.setViewName("egovframework/com/uat/uia/EgovLoginUsr");
		}
		return model;
	}
	
	@RequestMapping("/registeredInternal.do")
	public String registeredInternal(HttpServletRequest request,HttpServletResponse response)throws Exception{
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        String url = null;
        
        if(isAuthentication(request, response, loginVO)) {
            UserManageVO user = userService.getUser(loginVO.getUniqId());
			
			RegisterInternalVO internal = requestUtilService.getRegisterInternal(request, user);
			internal.setDocType(ApprovalConstants.DOC_TYPE_INTERNAL);
			
			signerService.registerInternal(internal, user);
			String labelId = request.getParameter("register_selectlabelId");
			String labelNm = request.getParameter("register_selectlabelNm");
	        url = "forward:/approvalDocPageList.do?docType=label&labelId=" + labelId + "&labelNm=" + labelNm;
        }        
		return url;
	}
	
	@RequestMapping("/approvalSend.do")
	public ModelAndView approvalSend(HttpServletRequest request,HttpServletResponse response)throws Exception{
		ModelAndView model = new ModelAndView();
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
        if(isAuthentication(request, response, loginVO)) {
            UserManageVO user = userService.getUser(loginVO.getUniqId());
			
			String userId = user.getUniqId();
			String docId = request.getParameter("docId");
			
			Doc doc = approvalDocService.getDoc(docId);
			if(doc == null){
				throw new Exception("can not find the doc info["+docId+"]");
			}
			
			List<SignerVO> signerList = signerService.getApprovalSignerList(doc.getDocID());
			Collections.sort(signerList, new Comparator<SignerVO>() {
				public int compare(SignerVO o1, SignerVO o2) {
					return o2.getSignSeq() - o1.getSignSeq();
				}
			});
			
			String docBody = null;
			if(ApprovalConstants.APPROVAL_DOC_PP_F_PAPER.equals(doc.getDocPpF())){
				docBody = null;
			}else{
				docBody = signerService.getDocForView(doc);
			}
			
			List<AttachFileVO> attachList = attachFileService.getAttachFileListByDocId(doc.getDocID());
			List<AttachFileVO> orgAttachList = null;
			
			if(doc.getDocOrgId() != null){
				orgAttachList = attachFileService.getAttachFileListByDocId(doc.getDocOrgId());
			}
			
			List<RecipientVO> recipientList = recipientService.getRecipientList(docId);
			LabelVO label = labelService.getLabel(doc.getLbelId());
			
			UserManageVO viewer = userService.getUser(userId);
			
			ModelAndView labelTree = treeList(userId);
			
			model.addObject("viewer", viewer);
			model.addObject("doc", doc);
			model.addObject("docBody", docBody);
			model.addObject("signerList", signerList);
			model.addObject("labelTree",labelTree.getModel().get("labelList"));
			model.addObject("userAuth",labelTree.getModel().get("userAuth"));
			model.addObject("deptAuth",labelTree.getModel().get("deptAuth"));
			model.addObject("userId",userId);
			model.addObject("attachList",attachList);
			model.addObject("orgAttachList",orgAttachList);
			model.addObject("recipientList",recipientList);
			model.addObject("label", label);
			model.addObject("user", user);
			model.setViewName("egovframework/com/uss/ion/apv/EgovApprSend");
		}else{
			model.setViewName("egovframework/com/uat/uia/EgovLoginUsr");
		}
		return model;
	}
	
	@RequestMapping("/send.do")
	public ModelAndView send(HttpServletRequest request,HttpServletResponse response)throws Exception{
		ModelAndView model = new ModelAndView();
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
        if(isAuthentication(request, response, loginVO)) {
            UserManageVO user = userService.getUser(loginVO.getUniqId());
			String docId = request.getParameter("docId");
			
			Doc doc = approvalDocService.getDoc(docId);
			if(doc == null){
				throw new Exception("can not find the doc info["+docId+"]");
			}
			List<RecipientVO> recipientList = requestUtilService.getRecipientList4Send(request);
			recipientService.updateRecipient(recipientList);

			model.addObject("doc", doc);
			model.addObject("recipientList",recipientList);
			model.addObject("user", user);
			model.setViewName("egovframework/com/uss/ion/apv/EgovOutgoingList");
		}else{
			model.setViewName("egovframework/com/uat/uia/EgovLoginUsr");
		}
		return model;
	}
	
	@RequestMapping("/approvalPass.do")
	public ModelAndView approvalPass(HttpServletRequest request,HttpServletResponse response)throws Exception{
		ModelAndView model = new ModelAndView();
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
        if(isAuthentication(request, response, loginVO)) {
            UserManageVO user = userService.getUser(loginVO.getUniqId());
            
			String userId = user.getUniqId();
			String orgdocId = request.getParameter("orgdocId");
			
			Doc orgdoc = approvalDocService.getDoc(orgdocId);
			if(orgdoc == null){
				throw new Exception("can not find the orginal doc info["+orgdocId+"]");
			}
			
			String deptId = user.getOrgnztId();
			RecipientVO recipient = recipientService.getRecipient(orgdocId, deptId);
			
			ModelAndView labelTree = treeList(userId);
			
			model.addObject("labelTree",labelTree.getModel().get("labelList"));
			model.addObject("userAuth",labelTree.getModel().get("userAuth"));
			model.addObject("deptAuth",labelTree.getModel().get("deptAuth"));
			model.addObject("userId",userId);
			model.addObject("orgdoc",orgdoc);
			model.addObject("recipient",recipient);
			model.addObject("user", user);
			model.setViewName("egovframework/com/uss/ion/apv/EgovApprPass");
		}else{
			model.setViewName("egovframework/com/uat/uia/EgovLoginUsr");
		}
		return model;
	}
	
	@RequestMapping("/pass.do")
	public ModelAndView pass(HttpServletRequest request,HttpServletResponse response)throws Exception{
		ModelAndView model = new ModelAndView();
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
        if(isAuthentication(request, response, loginVO)) {
            UserManageVO user = userService.getUser(loginVO.getUniqId());
			String orgdocId = request.getParameter("orgdocId");
			String passdeptId = request.getParameter("passdeptId");
			
			Doc orgDoc = approvalDocService.getDoc(orgdocId);
			if(orgDoc == null) {
				throw new Exception("can not find the orginal doc info["+orgdocId+"]");
			}
			
			List<RecipientVO> recipientList = requestUtilService.getRecipientList4Pass(request);
			
			RecipientVO recipient = recipientService.getRecipient(orgdocId, passdeptId);
			if ((recipient != null) 
			        && (recipientList != null) 
			        && (recipientList.size() > 0)) {
			    recipientService.passDoc(orgDoc, recipient, recipientList, user);
			}
			
			model.addObject("orgDoc", orgDoc);
			model.addObject("recipientList",recipientList);
			model.addObject("user", user);
			model.setViewName("egovframework/com/uss/ion/apv/EgovApprView");
		}else{
			model.setViewName("egovframework/com/uat/uia/EgovLoginUsr");
		}
		return model;
	}
}