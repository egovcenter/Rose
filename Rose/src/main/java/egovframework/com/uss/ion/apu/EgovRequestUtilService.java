package egovframework.com.uss.ion.apu;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ibm.icu.util.Calendar;

import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.EgovFileMngUtil;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.cmm.service.Globals;
import egovframework.com.cmm.util.DateHelper;
import egovframework.com.uss.ion.apm.service.EgovFormService;
import egovframework.com.uss.ion.apm.service.EgovLabelService;
import egovframework.com.uss.ion.apm.service.FormVO;
import egovframework.com.uss.ion.apm.service.LabelVO;
import egovframework.com.uss.ion.apv.ApprovalConstants;
import egovframework.com.uss.ion.apv.Doc;
import egovframework.com.uss.ion.apv.DocImpl;
import egovframework.com.uss.ion.apv.service.AttachFileVO;
import egovframework.com.uss.ion.apv.service.EgovApprovalDocService;
import egovframework.com.uss.ion.apv.service.EgovAttachFileService;
import egovframework.com.uss.ion.apv.service.EgovRecipientService;
import egovframework.com.uss.ion.apv.service.EgovSignerService;
import egovframework.com.uss.ion.apv.service.RecipientVO;
import egovframework.com.uss.ion.apv.service.RegisterIncomingVO;
import egovframework.com.uss.ion.apv.service.RegisterInternalVO;
import egovframework.com.uss.ion.apv.service.SignerVO;
import egovframework.com.uss.umt.service.UserManageVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("EgovRequestUtilService")
public class EgovRequestUtilService  extends EgovAbstractServiceImpl {
    
    @Resource (name = "EgovLabelService")
    private EgovLabelService labelService;
    
    @Resource (name = "EgovFormService")
    private EgovFormService formService;
        
    @Resource (name = "EgovApprovalDocService")
    private EgovApprovalDocService approvalDocService;
    
    @Resource (name = "EgovSignerService")
    private EgovSignerService signerService;
    
    @Resource (name = "EgovRecipientService")
    private EgovRecipientService recipientService;
    
    @Resource (name = "EgovAttachFileService")
    private EgovAttachFileService attachFileService;
    
    @Resource(name = "EgovFileMngUtil")
    private EgovFileMngUtil fileUtil;
    
    @Resource(name = "EgovFileMngService")
    private EgovFileMngService fileMngService;
    
    
	private static Logger logger = Logger.getLogger(EgovRequestUtilService.class);
	private static String tmpDir = EgovProperties.getProperty(Globals.APPROVAL_CONF_PATH, "approval.tmpdir");
	
	public static String BROWSER_MSIE = "MSIE";
	public static String BROWSER_MSIE11 = "Trident/";
	public static String BROWSER_MSIE11_rv = "rv:";
	public static String BROWSER_CHROME = "Chrome";
	public static String BROWSER_OPERA = "Opera";
	public static String BROWSER_FIREFOX = "Firefox";
	public static int DOC_VERSION = 1;
	
	public String getTmpDir() {
		return tmpDir;
	}

	public void setTmpDir(String tmpDir) {
		EgovRequestUtilService.tmpDir = tmpDir;
	}

	public Doc getDoc(HttpServletRequest request, String docId) throws Exception{
		if(docId==null){
			docId = request.getParameter("docId");
		}
		String formId = request.getParameter("formId");
		String labelId = request.getParameter("labelId");
		
		String docSyear = request.getParameter("doc_syear");
		String docSlvl = request.getParameter("doc_slvl");
		String docEmF = request.getParameter("doc_em_f");
		String docPpF = request.getParameter("doc_pp_f");
		String docOrgId = request.getParameter("doc_org_id");
		
		logger.debug("getDoc docId["+docId+"], formId["+formId+"], labelId["+labelId+"]");
		
		//TODO utility에서 ApprovalDocDAO를 접근할 수 있어야 한다
		Doc doc = approvalDocService.getDoc(docId);
		if (doc == null) {
			doc = new DocImpl();
			
			doc.setDocID(docId);
			doc.setDocVersion(DOC_VERSION);
			doc.setFormId(formId);
			doc.setDocSyear(ApprovalConstants.APPROVAL_DOC_SYEAR_FOREVER);
			doc.setDocSlvl(ApprovalConstants.APPROVAL_DOC_SLVL_OPEN);
			doc.setDocEmF(ApprovalConstants.APPROVAL_DOC_EM_F_NORMAL);
			doc.setDocPpF(ApprovalConstants.APPROVAL_DOC_PP_F_ELECTRONIC);
			doc.setDocAttaF(ApprovalConstants.APPROVAL_DOC_ATTA_F_NOTEXIST);
			doc.setDocOpnF(ApprovalConstants.APPROVAL_DOC_OPN_F_NOTEXIST);
			doc.setDocPState(ApprovalConstants.DOC_PROGRESS_STATE_ONGOING);
			doc.setDocFState(ApprovalConstants.DOC_FINISH_STATE_ONGOING);
			doc.setDocType(ApprovalConstants.DOC_TYPE_INTERNAL);
			
		}
		String title = null;
		if (request instanceof MultipartHttpServletRequest) {
	        title = URLDecoder.decode(request.getParameter("draft_title"), "UTF-8");
		} else {
		    title = request.getParameter("draft_title");
		}
		String docnum = request.getParameter("draft_docnum");
		
		if (title != null && title.length() > 0){
			doc.setDocTitle(title);
		}
		
		if (formId != null && formId.length() > 0){
			doc.setFormId(formId);
		}
		
		if (labelId != null && labelId.length() > 0){
			doc.setLbelId(labelId);
		}
		
		if (docnum != null && docnum.length() > 0){
			doc.setDocCd(docnum);
		}
		
		if (docSyear != null && docSyear.length() > 0){
			doc.setDocSyear(docSyear);
		}
		
		if (docSlvl != null && docSlvl.length() > 0){
			doc.setDocSlvl(docSlvl);
		}
		
		if (docEmF != null && docEmF.length() > 0){
			doc.setDocEmF(docEmF);
		}
		
		if (docPpF != null && docPpF.length() > 0){
			doc.setDocPpF(docPpF);
		}
		
		if (docPpF != null && docPpF.length() > 0){
			doc.setDocPpF(docPpF);
		}
		
		if (docOrgId != null && docOrgId.length() > 0){
			doc.setDocOrgId(docOrgId);
		}	
		//draft_title, draft_opentype
		logger.debug("getDoc doc["+doc+"]");
		
		return doc;
	}
	
	public Doc getDocForReceive(HttpServletRequest request, String docId, Doc orgDoc) throws Exception{
		if(docId != null && docId.equals("")){
			docId = null;
		}
		
		Doc doc = approvalDocService.getDoc(docId);
		if(doc != null){throw new Exception();}
		
		String formId = request.getParameter("formId");
		String labelId = request.getParameter("labelId");
		String docnum = request.getParameter("draft_docnum");
		
		doc = new DocImpl();
		doc.setDocID(docId);
		doc.setDocVersion(DOC_VERSION);
		doc.setDocOrgId(orgDoc.getDocID());
		doc.setDocTitle(orgDoc.getDocTitle());
		doc.setDocSyear(ApprovalConstants.APPROVAL_DOC_SYEAR_FOREVER);
		doc.setDocSlvl(orgDoc.getDocSlvl());
		doc.setDocEmF(ApprovalConstants.APPROVAL_DOC_EM_F_NORMAL);
		doc.setDocPpF(ApprovalConstants.APPROVAL_DOC_PP_F_ELECTRONIC);
		doc.setDocAttaF(ApprovalConstants.APPROVAL_DOC_ATTA_F_NOTEXIST);
		doc.setDocOpnF(ApprovalConstants.APPROVAL_DOC_OPN_F_NOTEXIST);
		doc.setDocPState(ApprovalConstants.DOC_PROGRESS_STATE_ONGOING);
		doc.setDocFState(ApprovalConstants.DOC_FINISH_STATE_ONGOING);
		doc.setDocType(ApprovalConstants.DOC_TYPE_INCOMING);
		
		if (formId != null && formId.length() > 0){
			doc.setFormId(formId);
		}
		if (labelId != null && labelId.length() > 0){
			doc.setLbelId(labelId);
		}
		if (docnum != null && docnum.length() > 0){
			doc.setDocCd(docnum);
		}
		logger.debug("getDocForReceive doc["+doc+"]");
		
		return doc;
	}
	
	public List<SignerVO> getSignerList(HttpServletRequest request, String docId, String userId, int docVersion) throws Exception{
		if(docId==null){
			docId = request.getParameter("docId");
		}
		String signerListJson = request.getParameter("signerList");;
		boolean isRedraft = "true".equals(request.getParameter("redraft"));
		
		List<SignerVO> signerList = new ArrayList<SignerVO>();
		List<SignerVO> orgSignerList = signerService.getApprovalSignerList(docId);

		try {
	        if (request instanceof MultipartHttpServletRequest) {
	            signerListJson = URLDecoder.decode(signerListJson, "UTF-8");
	        } 
		} catch (Exception e) {
		    logger.debug("Exceptionn on unescaping signerlist,"
		            + "Exception[" + e.toString() + "]/"
		            + "Msg[" + e.getMessage() + "]");
		}
		
		JSONArray jsonArray = new JSONArray(signerListJson);
					
		//signerList.push({"seq" : seq, "signerId": signerId, "signerKind": signerKind, "signerUserId" : signerUserId, "signerSignerName": signerSignerName, "signerDeptId": signerDeptId, "signerDeptName": signerDeptName, "signerPositionName": signerPositionName});
		for(int i=0; i<jsonArray.length(); i++){
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			logger.debug("getSignerList jsonArray["+i+"], jsonObject["+jsonObject+"]");
			
			int seq = jsonObject.getInt("seq");
			String signerId = null;
			if(jsonObject.has("signerId")){
				signerId = jsonObject.getString("signerId");
			}
			
			String signerKind = jsonObject.getString("signerKind");
			String signerUserId = jsonObject.getString("signerUserId");
			String signerSignerName = jsonObject.getString("signerSignerName");
			String signerDeptId = jsonObject.getString("signerDeptId");
			String signerDeptName = jsonObject.getString("signerDeptName");
			String signerPositionName = jsonObject.getString("signerPositionName");
			String signerDutyName = jsonObject.getString("signerDutyName");
			String signerSignState = jsonObject.getString("signerSignState");
			String signerSginDate = jsonObject.getString("signerSignDate");
			String signerOpinion = jsonObject.getString("signerOpinion");
			logger.debug("getSignerList jsonArray["+i+"], signerId["+signerId+"], signerKind["+signerKind+"], signerUserId["+signerUserId+"], signerSignState["+signerSignState+"]");

			SignerVO signer = null;
			if(signerId == null || signerId.length() < 1) {
				signerId = signerService.getNextSignerId();
				signer = new SignerVO();
				signer.setSignerID(signerId);
				signer.setSignSubUserFlag(ApprovalConstants.APPROVAL_SIGN_NON_SUBUSER);
				
				logger.debug("getSignerList jsonArray["+i+"], create default signerImpl, signerId["+signerId+"]");
			}else{
				for(int j=0; orgSignerList != null && j<orgSignerList.size(); j++){
				    SignerVO tmp = (SignerVO) orgSignerList.get(j);
					if(tmp.getSignerID().equals(signerId)){
						signer = tmp;
						break;
					}
				}
				logger.debug("getSignerList jsonArray["+i+"], find singer, signer["+signer+"]");
				if(signer == null){
					logger.debug("The signer's info["+signerId+"] does not exist.");
					
					signerId = signerService.getNextSignerId();
					signer = new SignerVO();
					signer.setSignerID(signerId);
					signer.setSignSubUserFlag(ApprovalConstants.APPROVAL_SIGN_NON_SUBUSER);
				}else if(isRedraft){
					signer.setSignDate(null);
					signer.setOpinion("");
					logger.debug("The signer's info["+signerId+"] is cleared. is redraft. signDate["+signer.getSignDate()+"]");
				}
			}
			
			if(userId != null && docVersion > 0){
				if(userId.equals(signerUserId)){
					signer.setDocVersion(docVersion);
				}
			}
			signer.setSignSeq(seq);
			signer.setDocID(docId);
			signer.setSignKind(signerKind);
			signer.setUserID(signerUserId);
			signer.setSignerName(signerSignerName);
			signer.setSignerDeptID(signerDeptId);
			signer.setSignerDeptName(signerDeptName);
			signer.setSignerPositionName(signerPositionName);
			signer.setSignerDutyName(signerDutyName);
			signer.setSignState(signerSignState);
			if (isRedraft == false) {
				signer.setSignDate(DateHelper.convertDate(signerSginDate, "yyyy-MM-dd HH:mm"));
				signer.setOpinion(signerOpinion);
			}
			
			logger.debug("getSignerList jsonArray["+i+"], signer["+signer+"] added in signerList");
			
			signerList.add(signer);
		}
		
		Collections.sort(signerList, new Comparator<SignerVO>() {
			public int compare(SignerVO o1, SignerVO o2) {
				return o1.getSignSeq() - o2.getSignSeq();
			}
		});
		
		return signerList;
	}
	
	public static String getDocBody(HttpServletRequest request){
		String content = request.getParameter("docBody");
		
		try {
            if (request instanceof MultipartHttpServletRequest) {
                try {
                    content = URLDecoder.decode(content, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    logger.error("Exceptionn on unescaping getDocBody,"
                            + "Exception[" + e.toString() + "]/"
                            + "Msg[" + e.getMessage() + "]");
                }
            }
        } catch (Exception e) {
            logger.error("Exceptionn on unescaping getDocBody,"
                    + "Exception[" + e.toString() + "]/"
                    + "Msg[" + e.getMessage() + "]");
        }
		
		logger.debug("getDocBody content["+content+"]");
		
		return content;
	}
	
	public List<RecipientVO> getRecipientList(HttpServletRequest request, String docId) throws Exception{
		if(docId==null){
			docId = request.getParameter("docId");
		}
		String recipientListJson = request.getParameter("recipientList");

		List<RecipientVO> recipientList = new ArrayList<RecipientVO>();
		
		if(recipientListJson == null || recipientListJson.length() < 1) {
		    return recipientList;
		}
		List<RecipientVO> orgRecipientList = recipientService.getRecipientList(docId);

        try {
            if (request instanceof MultipartHttpServletRequest) {
                recipientListJson = URLDecoder.decode(recipientListJson, "UTF-8");
            } 
        } catch (Exception e) {
            logger.error("Exceptionn on unescaping recipientList,"
                    + "Exception[" + e.toString() + "]/"
                    + "Msg[" + e.getMessage() + "]");
        }
		
		JSONArray jsonArray = new JSONArray(recipientListJson);
		
		// recipientList.push({/*"seq" : recipientRecSeq,*/ "recpId": recipientRecpId, "deptId": recipientDeptId, "recpDeptNm" : recipientDeptName});
		for(int i=0; i<jsonArray.length(); i++){
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			logger.debug("getRecipientList jsonArray["+i+"],jsonObject["+jsonObject+"]" );
			
			String recpSeq = jsonObject.getString("recpSeq");
			String recpId = jsonObject.getString("recpId");
			String recipientDeptId = jsonObject.getString("deptId");
			String recipientDeptName = jsonObject.getString("recpDeptNm");
			String recpSendType = jsonObject.getString("recpSendType");
			String recpInnerFlag = jsonObject.getString("recpInnerFlag");
			
			RecipientVO recipient = null;
			if(recpId == null || recpId.length() < 1){
				recpId = recipientService.getNextRecipientId();
				recipient = new RecipientVO();
				recipient.setRecpId(recpId);
			}else{
				logger.debug("getRecipientList recpId["+recpId+"]" );
				for(int j=0; orgRecipientList != null && j<orgRecipientList.size(); j++){
				    RecipientVO tmp = (RecipientVO)orgRecipientList.get(j);
					logger.debug("getRecipientList orgRecipientList["+i+"], Recipient["+tmp+"]" );
					if(tmp.getRecpId().equals(recpId)){
						recipient = tmp;
						break;
					}
				}
				logger.debug("getRecipientList recipient["+recipient+"]" );
				if(recipient == null){
					logger.debug("The recipient's info["+recpId+"] does not exist.");
					recpId = recipientService.getNextRecipientId();
					recipient = new RecipientVO();
					recipient.setRecpId(recpId);
				}
			}
			recipient.setRecpSeq( StringUtils.isEmpty(recpSeq) ?  (i+1) : Integer.parseInt(recpSeq));			
			recipient.setDeptId(recipientDeptId);
			recipient.setRecpDeptNm(recipientDeptName);
			recipient.setDocId(docId);
			recipient.setRecpSendType(StringUtils.isEmpty(recpSendType) ? ApprovalConstants.SEND_TYPE_AUTO : recpSendType);
			recipient.setRecpInnerFlag(StringUtils.isEmpty(recpInnerFlag) ? ApprovalConstants.FLAG_YES : recpInnerFlag);
			recipient.setRecpDocType(ApprovalConstants.RECP_DOC_TYPE_RECEIVE);
			recipient.setRecpMethod(ApprovalConstants.RECP_METHOD_SEND);
			
			recipientList.add(recipient);
			logger.debug("getRecipientList recipient["+recipient+"]" );
		}
		logger.debug("getRecipientList recipientList["+recipientList+"]" );
		return recipientList;
	}

	private String getAttachType(String fileName){
		if(fileName == null) return null;
		
		int idx = fileName.lastIndexOf(".");
		String ext = idx < 0 ? null : fileName.substring(idx+1);
//		占쏙옙占쏙옙占쏙옙占쏙옙(ATTA_TYPE)
//		  - 4001 : TXT
//		  - 4002 : DOC
//		  - 4003 : XLS
//		  - 4004 : PPT
//		  - 4005 : PDF
//		  - 4006 : IMG (占싱뱄옙占쏙옙)
//		  - 4007 : MOV (占쏙옙占쏙옙占쏙옙)
		if(",txt,".indexOf(","+ext+",") > -1){
			return ApprovalConstants.ATTACH_TYPE_TXT;
		}else if(",doc,docx,".indexOf(","+ext+",") > -1){
			return ApprovalConstants.ATTACH_TYPE_DOC;
		}else if(",xls,xlsx,".indexOf(","+ext+",") > -1){
			return ApprovalConstants.ATTACH_TYPE_XLS;
		}else if(",ppt,pptx,".indexOf(","+ext+",") > -1){
			return ApprovalConstants.ATTACH_TYPE_PPT;
		}else if(",pdf,,".indexOf(","+ext+",") > -1){
			return ApprovalConstants.ATTACH_TYPE_PDF;
		}else if(",mov,asf,mpeg,".indexOf(","+ext+",") > -1){
			return ApprovalConstants.ATTACH_TYPE_MOV;
		}else if(",bmp,gif,jpg,png,".indexOf(","+ext+",") > -1){
			return ApprovalConstants.ATTACH_TYPE_IMG;
		}
		return ApprovalConstants.ATTACH_TYPE_TXT;
	}
	
	public List<AttachFileVO> getAttachListFromOrgDoc(HttpServletRequest request, Doc doc) throws Exception{
		List<AttachFileVO> attachList = new ArrayList<AttachFileVO>();
		List<AttachFileVO> orgAttachList = attachFileService.getAttachFileListByDocId(doc.getDocOrgId());
		
		for(int i = 0; i < orgAttachList.size(); i++){
		    AttachFileVO attach = new AttachFileVO();
		    AttachFileVO orgin = (AttachFileVO) (orgAttachList.get(i));

			attach.setAttachID(attachFileService.getNextAttatchFileId());
			attach.setDocID(doc.getDocID());
			attach.setAttachSeq(orgin.getAttachSeq());
			attach.setAttachNm(orgin.getAttachNm());
			attach.setAttachSize(orgin.getAttachSize());
			attach.setAttachType(orgin.getAttachType());
			
			attachList.add(attach);
			logger.debug("getAttachListFromOrgDoc attach["+attach+"]");

			PathUtil.copyFile((AttachFileVO) (orgAttachList.get(i)), attach);
		}
		
		return attachList;
	}
	
	public List<AttachFileVO> getAttachList(HttpServletRequest request, Doc doc) throws Exception{
		List<AttachFileVO> attachList = new ArrayList<AttachFileVO>();
		String attachListJson = request.getParameter("attachList");
		
		if ((attachListJson == null) || (attachListJson.length() < 1)) {
		    return attachList;
		}
		
		JSONArray jsonArray = new JSONArray(attachListJson);
		
		List<AttachFileVO> orgAttachList = attachFileService.getAttachFileListByDocId(doc.getDocID());
		
		logger.debug("getAttachList orgAttachList["+orgAttachList+"]" );
		//attachList.push({"fileName": fileName, "sysFileName": sysFileName, "size": size});
		for(int i=0; i<jsonArray.length(); i++){
			JSONObject jsonObject = jsonArray.getJSONObject(i);
//			logger.debug("getAttachList jsonArray["+i+"], jsonObject["+jsonObject+"]" );
			String attachId = null;
			AttachFileVO attach = null;
			
			if(jsonObject.has("attachId")){
				attachId = jsonObject.getString("attachId");
				logger.debug("getAttachList attachId["+attachId+"]" );
				
				for(int j=0; j<orgAttachList.size(); j++){
					AttachFileVO tmp = (AttachFileVO)orgAttachList.get(j);
					if(attachId.equals(tmp.getAttachID())){
						attach = tmp;
						break;
					}
				}
				logger.debug("getAttachList attach["+attach+"]" );
			}else{
				String fileName = jsonObject.has("orgFileName") ? jsonObject.getString("orgFileName") : null;
				String sysFileName = jsonObject.has("sysFileName") ? jsonObject.getString("sysFileName") : null;;
				logger.debug("getAttachList fileName["+fileName+"], sysFileName["+sysFileName+"]");
				if(fileName == null || sysFileName == null){
					continue;
				}
				File srcFile = new File(tmpDir + sysFileName);
				
				attach = new AttachFileVO();
				attach.setAttachID(attachFileService.getNextAttatchFileId());
				attach.setAttachSeq(i);
				attach.setAttachNm(fileName);
				attach.setAttachSize((int)srcFile.length());
				attach.setDocID(doc.getDocID());
				attach.setAttachType(getAttachType(fileName));
				logger.debug("getAttachList attach["+attach+"]");
				if(srcFile.canWrite()){
					PathUtil.moveFile(attach, srcFile);
				}else{
					throw new ApprovalException("The file["+srcFile.getAbsolutePath()+" can not be written.");
				}
			}
			logger.debug("getAttachList attach["+attach+"]");
			if(attach != null){
				attachList.add(attach);
			}
		}
		logger.debug("attachList["+attachList+"]");
		return attachList;
	}

    public List<AttachFileVO> getAttachFileList(MultipartHttpServletRequest multiRequest, String docId) throws Exception {
        if (multiRequest == null) {
            return null;
        }
        
        List<AttachFileVO> attachList = new ArrayList<AttachFileVO>();
        final Map<String, MultipartFile> files = multiRequest.getFileMap();
        Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
        int fileSeq = 0;
        
        while (itr.hasNext()) {
            AttachFileVO attach = null;
            MultipartFile file;     
            Entry<String, MultipartFile> entry = itr.next();
    
            file = entry.getValue();
            String fileName = file.getOriginalFilename();
            logger.debug("######## getAttachList fileName["+fileName+"] - (" + (fileSeq+1) + " of " + files.size() + ")");
            
            if(fileName == null){
                continue;
            }
    
            attach = new AttachFileVO();
            attach.setAttachID(attachFileService.getNextAttatchFileId());
            attach.setAttachSeq(fileSeq);
            attach.setAttachNm(fileName);
            attach.setAttachSize((int)file.getSize());
            attach.setAttachType(getAttachType(fileName));
            attach.setDocID(docId);
            logger.debug("getAttachList attach["+attach+"]");
            
            if(file != null){
                PathUtil.saveAttachFile(attach, file);
                insertAttachFileInf(attach, file, fileSeq++);
            }else{
                throw new Exception("The attached file ["+file.getOriginalFilename() + "] is empty file.");
            }
    
            logger.debug("getAttachList attach["+attach+"]");
            if(attach != null){
                attachList.add(attach);
            }
        }
        
        return attachList;
    }
    
	private void insertAttachFileInf(AttachFileVO attachedFile, MultipartFile file, int seq) throws Exception {
	    try {
    	    String orgFileName = file.getOriginalFilename();
    	    
            int index = orgFileName.lastIndexOf(".");
            String fileExt = orgFileName.substring(index + 1);

            String folder = PathUtil.getDirectoryForAttachFile(attachedFile);
            
            FileVO fileInf = new FileVO();
            fileInf.setFileExtsn(fileExt);
            fileInf.setFileStreCours(folder);
            fileInf.setFileMg(Long.toString(attachedFile.getAttachSize()));
            fileInf.setOrignlFileNm(orgFileName);
            fileInf.setStreFileNm(attachedFile.getAttachNm());
            fileInf.setAtchFileId(attachedFile.getAttachID());
            fileInf.setFileSn(Integer.toString(seq));
            
            fileMngService.insertFileInf(fileInf);
	    } catch (Exception e) {
	        ;
	    }
    }

	public Map getParameterMap(HttpServletRequest request){
		Map requestMap = new HashMap();
		for(Enumeration e = request.getParameterNames(); e.hasMoreElements(); ){
			String name = (String)e.nextElement();
			Object value = request.getParameter(name);
			requestMap.put(name, value.toString());
		}
		return requestMap;
	}
	
	public String getBrowser(HttpServletRequest request) {
		String header =request.getHeader("User-Agent");
		if (header.contains(BROWSER_MSIE)) {
			return BROWSER_MSIE;
		} else if(header.contains(BROWSER_MSIE11) && header.contains(BROWSER_MSIE11_rv)) {
			return BROWSER_MSIE;
		} else if(header.contains(BROWSER_CHROME)) {
			return BROWSER_CHROME;
		} else if(header.contains(BROWSER_OPERA)) {
			return BROWSER_OPERA;
		}
		return BROWSER_FIREFOX;
	}
	
	public String getAttachmentFileName4Download(String orgFileName, HttpServletRequest request){
		String filename = null;
		try {
			String browser = getBrowser(request);
			if("MSIE".equals(browser)){
				filename = URLEncoder.encode(orgFileName,"UTF-8").replaceAll("₩₩+", "%20");
				String userAgent = request.getHeader("user-agent");
				int dotIndex = filename.lastIndexOf('.');
				// The IE 9 is crashed in the case that the length of the extension is more than 38 
				if(userAgent != null && userAgent.indexOf("MSIE 9.") > -1 && dotIndex > -1){
	        		String ext = filename.substring(dotIndex+1);
					if(ext != null && ext.indexOf('%') > -1) {
						filename = filename.substring(0, dotIndex) + "." + ext.replaceAll("%","");
					}
	        		else if(ext != null && ext.length() > 37){
	        			filename = filename.substring(0, dotIndex + 37);
	        		}
				}else if(userAgent != null && userAgent.indexOf("MSIE 8.") > -1 && dotIndex > -1){
					String ext = filename.substring(dotIndex+1);
					if(ext != null && ext.indexOf('%') > -1) {
						filename = filename.substring(0, dotIndex) + "." + ext.replaceAll("%","");
					}
	        		else if(ext != null && ext.length() > 37){
	        			filename = filename.substring(0, dotIndex + 37);
	        		}
					filename = filename.replace("%20", "_");
				}
			}else{
				filename = new String(orgFileName.getBytes("UTF-8"), "ISO-8859-1");
			}
		} catch (Exception e) {
			return orgFileName;
		}
		return filename;
	}
	
	/**
	 * Transfer file attach
	 * @param filePath
	 * @param fileName
	 * @param response
	 * @param contentType
	 */
	public void transfer(HttpServletRequest request, HttpServletResponse response, String fileName, File file, String contentType)throws Exception {
		FileInputStream input = null;
		try {

			String filename = getAttachmentFileName4Download(fileName, request);
			
			response.setContentType(contentType);

			response.setHeader("Content-Disposition", "attachment;filename=\""+ filename+"\"");
			

			// not in list file type can open automatically, files that are
			// download
			response.setContentLength((int) file.length());
			input = new FileInputStream(file);
			
			IOUtils.copy(input, response.getOutputStream());
		} catch (Exception e) {
			logger.error("fail to Transfer file attach", e);
			throw e;
		} finally {
			try {if (input != null)input.close();} catch (IOException ex) {}
		}
	}
	
	public RegisterIncomingVO getRegisterIncoming(HttpServletRequest request, UserManageVO register) throws Exception{
		String draftTitle = request.getParameter("register_draft_title");
		String senderDeptName = request.getParameter("register_recp_s_dept_nm");
		String senderPositionName = request.getParameter("register_recp_s_posi_nm");
		String senderUserName = request.getParameter("register_recp_s_user_nm");
		String recipientDeptID = request.getParameter("recipient_deptid");
		String recipientDeptName = request.getParameter("recipient_deptname");
		String registerDocNum = request.getParameter("register_recp_s_docnum");
		String securityLevel = request.getParameter("doc_slvl");
		
		String docId = approvalDocService.getNextDocId();
		RegisterIncomingVO registerIncoming = new RegisterIncomingVO();
		List<AttachFileVO> attachList = getAttachFileList((MultipartHttpServletRequest)request, docId);
	      
		registerIncoming.setDocID(docId);
		registerIncoming.setLabelID(ApprovalConstants.ROOT_LABEL_ID);
		registerIncoming.setTitle(draftTitle);
		registerIncoming.setSenderDeptName(senderDeptName);
		registerIncoming.setSenderPositionName(senderPositionName);
		registerIncoming.setSenderName(senderUserName);
		registerIncoming.setRegisterDeptID(recipientDeptID);
		registerIncoming.setRegisterDeptName(recipientDeptName);
		registerIncoming.setAttachList(attachList);
		registerIncoming.setRegisterDocNum(registerDocNum);
		
		if(StringUtils.isEmpty(securityLevel)){
			registerIncoming.setSecurityLevel(ApprovalConstants.APPROVAL_DOC_SLVL_OPEN);
		}else{
			registerIncoming.setSecurityLevel(securityLevel);
		}
		

		
		return registerIncoming;
	}
	
	public RegisterInternalVO getRegisterInternal(HttpServletRequest request, UserManageVO register) throws Exception{
		String draftTitle = request.getParameter("register_draft_title");
		String labelID = request.getParameter("register_selectlabelId");
		String securityLevel = request.getParameter("doc_slvl");
		String signerUserID = request.getParameter("selectedUserID");
		String signerUserNm = request.getParameter("selectedUserNm");
		String signerDeptID = request.getParameter("selectedDeptId");
		String signerDeptNm = request.getParameter("selectedDeptNm");
		String draftDocNum = request.getParameter("draft_docnum");
		/* 20160404_SUJI.H */
		String opinion = request.getParameter("opinion");
		
		String docID = approvalDocService.getNextDocId();
		
		List<AttachFileVO> attachList = getAttachFileList((MultipartHttpServletRequest)request, docID);
	
		RegisterInternalVO registerInternal = new RegisterInternalVO();
		registerInternal.setDocID(docID);
		registerInternal.setTitle(draftTitle);
		registerInternal.setLabelID(labelID);
		registerInternal.setDocNum(draftDocNum);
		registerInternal.setSignerUserID(signerUserID);
		registerInternal.setSignerUserNm(signerUserNm);
		registerInternal.setSignerDeptID(signerDeptID);
		registerInternal.setSignerDeptNm(signerDeptNm);
		registerInternal.setAttachList(attachList);
		registerInternal.setOpinion(opinion);
		
		if(StringUtils.isEmpty(securityLevel)) {
			registerInternal.setSecurityLevel(ApprovalConstants.APPROVAL_DOC_SLVL_OPEN);
		}else {
			registerInternal.setSecurityLevel(securityLevel);
		}
		
		return registerInternal;
	}
	
	public LabelVO getLabel(HttpServletRequest request) throws Exception{
		LabelVO label = new LabelVO();
		String labelID = request.getParameter("labelID");
		String deptId = request.getParameter("deptId");
		String labelParID = request.getParameter("labelParID");
		String labelSeq = request.getParameter("labelSeq");
		String labelNm = request.getParameter("labelNm");
		
		if(StringUtils.isNotEmpty(labelID)){
			label.setLabelId(labelID);
			LabelVO tmp = labelService.getLabel(labelID);
			
			if(tmp != null){
				label.setDeptId(tmp.getDeptId());
				label.setLabelParentId(tmp.getLabelParentId());
				label.setLabelSeq(tmp.getLabelSeq());
				label.setLabelNm(tmp.getLabelNm());
			}
		}
		
		if(StringUtils.isNotEmpty(deptId)){
			label.setDeptId(deptId);
		}
		if(StringUtils.isNotEmpty(labelParID)){
			label.setLabelParentId(labelParID);
		}
		if(StringUtils.isNotEmpty(labelSeq)){
			label.setLabelSeq(Integer.parseInt(labelSeq));
		}
		if(StringUtils.isNotEmpty(labelNm)){
			label.setLabelNm(labelNm);
		}
		
		return label;
	}
	
	public FormVO getForm(HttpServletRequest request) throws Exception{
		FormVO form = new FormVO();
		
		String formId = "";
		String deptId = "";
		String formNm = "";
		String formVer = "";
		String formRmrk = "";
		String formUseF = "";
		MultipartFile formFile = null;
		if(request instanceof MultipartHttpServletRequest){
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
			
            Iterator<String> file_iter = multipartRequest.getFileNames();
	        if ((file_iter != null) && (file_iter.hasNext())) {
	            String fileName = file_iter.next();
	            formFile = multipartRequest.getFile(fileName);
	        }
	                
			formId = multipartRequest.getParameter("formId");
			deptId = multipartRequest.getParameter("deptId");
			formNm = multipartRequest.getParameter("formNm");
			formVer = multipartRequest.getParameter("formVer");
			formRmrk = multipartRequest.getParameter("formRmrk");
			formUseF = multipartRequest.getParameter("formUseF");
			
			logger.debug("getForm request is MultipartHttpServletRequest ["+formId+","+deptId+","+formNm+","+formVer+","+formRmrk+","+formUseF+"]");
		}else{
			formId = request.getParameter("formId");
			deptId = request.getParameter("deptId");
			formNm = request.getParameter("formNm");
			formVer = request.getParameter("formVer");
			formRmrk = request.getParameter("formRmrk");
			formUseF = request.getParameter("formUseF");
			logger.debug("getForm request is HttpServletRequest ["+formId+","+deptId+","+formNm+","+formVer+","+formRmrk+","+formUseF+"]");
		}
		
		if(StringUtils.isNotEmpty(formId)){
			form.setFormId(formId);
			FormVO tmp = formService.getForm(formId);
			if(tmp != null){
				form.setOrgId(tmp.getOrgId());
				form.setFormName(tmp.getFormName());
				form.setFormRemark(form.getFormRemark());
				form.setFormVersion(tmp.getFormVersion());
				form.setFormUseF(tmp.isFormUseF());
			}
		}else{
			formId = formService.getNextFormId();
			form.setFormId(formId);
		}

		if(StringUtils.isNotEmpty(deptId)){
			deptId = deptId.replace("$", "");
			deptId = deptId.replace(";", "");
			form.setOrgId(deptId);
		}
		
		if(StringUtils.isNotEmpty(formNm)){
			form.setFormName(formNm);
		}
		
		if(StringUtils.isNotEmpty(formVer)){
			form.setFormVersion(formVer);
		}
		
		if(StringUtils.isNotEmpty(formRmrk)){
			form.setFormRemark(formRmrk);
		}
		
		if(StringUtils.isNotEmpty(formUseF)){
			form.setFormUseF(ApprovalConstants.FLAG_NO.equals(formUseF) ? false : true);
		}
		
		logger.debug("getForm formFile ["+formFile+"]");
		if(formFile != null && !formFile.isEmpty()){
			try {
			    if (form.getFormId() == null) {
			        throw new Exception("faile to save form file because FormId is null");
			    }
				File destFile = PathUtil.getFormPath(form);
				logger.debug("getForm destFile ["+destFile.getAbsolutePath()+"]");
				formFile.transferTo(destFile);
			} catch (Exception e) {
				logger.error("fail to save the form file.", e);
				throw new ApprovalException("fail to save the form file");
			}
		}
		logger.debug("getForm form is 1 ["+form+"]");
		
		return form;
	}
	
	public List<RecipientVO> getRecipientList4Send(HttpServletRequest request)throws Exception{
		List<RecipientVO> recipientList = new ArrayList<RecipientVO>();
		
		String docId = request.getParameter("docId");
		String recpSendUserID = request.getParameter("recpSendUserID");
		String recpSendUserNm = request.getParameter("recpSendUserNm");
		String recpIDList = request.getParameter("recpIDList");
		String datetimeformat = request.getParameter("datetimeformat");
		
		if (recpSendUserID == null) {
		    recpSendUserID = request.getParameter("selectedUserID");
		}
		
		if (recpSendUserNm == null) {
		    recpSendUserNm = request.getParameter("selectedUserNm");
		}
		
		logger.debug("getRecipientList4Send docId["+docId+"],recpSendUserID["+recpSendUserID+"], recpSendUserNm["+recpSendUserNm+"], recpIDList["+recpIDList+"], datetimeformat["+datetimeformat+"]" );
		
		List<RecipientVO> orgRecipientList = recipientService.getRecipientList(docId);
		logger.debug("getRecipientList4Send docId["+docId+"],orgRecipientList["+orgRecipientList+"]" );
		if(StringUtils.isEmpty(recpIDList)){
			logger.debug("getRecipientList4Send recpIDList is null["+recpIDList+"]" );
			return null;
		}
		
		if(StringUtils.isEmpty(datetimeformat)){  
			datetimeformat = "yyyy/MM/dd HH:mm";
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datetimeformat);
		
		String[] recpIDArray = recpIDList.split(",");
		for(int i=0; i<recpIDArray.length; i++){
			String recpId = recpIDArray[i];
			if(StringUtils.isEmpty(recpId)) {
			    continue;
			}
			
			RecipientVO recipient = null;
			for(int j=0; orgRecipientList != null && j<orgRecipientList.size(); j++){
			    RecipientVO tmp = (RecipientVO)orgRecipientList.get(j);
				logger.debug("getRecipientList4Send orgRecipientList["+i+"], Recipient["+tmp+"]" );
				if(tmp.getRecpId().equals(recpId)){
					recipient = tmp;
					break;
				}
			}
			
			String dateTimeString = request.getParameter("recpSendDt_"+recpId);
			if(recipient == null || StringUtils.isEmpty(dateTimeString)){
				logger.debug("getRecipientList4Send can not find the recipient["+recpId+"], recpSendDt_"+recpId+"["+dateTimeString+"]" );
				continue;
			}
			
			try {
				recipient.setRecpSendDt(simpleDateFormat.parse(dateTimeString));
			} catch (ParseException e) {
				logger.error(dateTimeString + " is invalid", e);
				throw new ApprovalException(dateTimeString + " is invalid");
			}
			
			recipient.setRecpSendUserId(recpSendUserID);
			recipient.setRecpSendUserNm(recpSendUserNm);
			recipientList.add(recipient);
		}

		logger.debug("getRecipientList recipientList["+recipientList+"]" );
		return recipientList;
	}
	
	public List<RecipientVO> getRecipientList4Pass(HttpServletRequest request) throws Exception{
		String orgdocId = request.getParameter("orgdocId");
		String passdeptId = request.getParameter("passdeptId");
		String recpDeptIDs = request.getParameter("recpDeptIDs");
		String recpDeptNms = request.getParameter("recpDeptNms");
		
		logger.debug("getRecipientList4Pass orgdocId["+orgdocId+"], passdeptId["+passdeptId+"], recpDeptIDs["+recpDeptIDs+"], recpDeptNms["+recpDeptNms+"]");
		
		RecipientVO recipient4pass = recipientService.getRecipient(orgdocId, passdeptId);
		logger.debug("getRecipientList4Pass passdeptId's recipient["+recipient4pass+"]");
		
		if(recipient4pass == null){
			throw new ApprovalException("The recipient["+orgdocId +","+passdeptId+"] is NULL.");
		}
		
		if(recpDeptIDs == null || recpDeptIDs.trim().length() < 1){
			throw new ApprovalException("The recpDeptIDs is NULL.");
		}
		
		StringTokenizer st = new StringTokenizer(recpDeptIDs, ",");
		StringTokenizer st2 = new StringTokenizer(recpDeptNms, ",");
		int idIndex = 0;
		List<RecipientVO> recipientList = new ArrayList<RecipientVO>();
		
		for(; st.hasMoreTokens(); ){
			String recpDeptID = st.nextToken();
			String recpDeptNm = st2.nextToken();
			
			if (StringUtils.isEmpty(recpDeptID)) {
			    continue;
			}
			String recpId = recipientService.getNextRecipientId();
			
			RecipientVO recipient = new RecipientVO();
			recipient.setRecpId(recpId);
			recipient.setRecpSeq(idIndex);			
			recipient.setDeptId(recpDeptID);
			recipient.setRecpDeptNm(recpDeptNm);
			recipient.setDocId(orgdocId);
			recipient.setRecpSendType(ApprovalConstants.SEND_TYPE_AUTO);
			recipient.setRecpInnerFlag(ApprovalConstants.FLAG_YES);
			recipient.setRecpDocType(ApprovalConstants.RECP_DOC_TYPE_PASS);
			recipient.setRecpMethod(ApprovalConstants.RECP_METHOD_SEND);
			recipient.setRecpPassDeptID(passdeptId);
			
			recipientList.add(recipient);
		}
	
		logger.debug("getRecipientList4Pass recipientList["+recipientList+"]" );
		return recipientList;
	}
}
