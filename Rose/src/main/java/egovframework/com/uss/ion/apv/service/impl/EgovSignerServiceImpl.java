package egovframework.com.uss.ion.apv.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ibm.icu.util.Calendar;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.uss.ion.apm.FormProcessor;
import egovframework.com.uss.ion.apm.service.EgovFormService;
import egovframework.com.uss.ion.apm.service.EgovLabelService;
import egovframework.com.uss.ion.apm.service.FormVO;
import egovframework.com.uss.ion.apm.service.LabelVO;
import egovframework.com.uss.ion.apu.PathUtil;
import egovframework.com.uss.ion.apv.ApprovalConstants;
import egovframework.com.uss.ion.apv.ApprovalContext;
import egovframework.com.uss.ion.apv.Doc;
import egovframework.com.uss.ion.apv.DocImpl;
import egovframework.com.uss.ion.apv.service.AttachFileVO;
import egovframework.com.uss.ion.apv.service.EgovApprovalDocService;
import egovframework.com.uss.ion.apv.service.EgovAttachFileService;
import egovframework.com.uss.ion.apv.service.EgovRecipientService;
import egovframework.com.uss.ion.apv.service.EgovSignerHistoryService;
import egovframework.com.uss.ion.apv.service.EgovSignerService;
import egovframework.com.uss.ion.apv.service.RecipientVO;
import egovframework.com.uss.ion.apv.service.RegisterIncomingVO;
import egovframework.com.uss.ion.apv.service.RegisterInternalVO;
import egovframework.com.uss.ion.apv.service.SignerChangeHistoryVO;
import egovframework.com.uss.ion.apv.service.SignerVO;
import egovframework.com.uss.omt.service.DeptVO;
import egovframework.com.uss.omt.service.DutyVO;
import egovframework.com.uss.omt.service.EgovDeptService;
import egovframework.com.uss.omt.service.EgovDutyService;
import egovframework.com.uss.omt.service.EgovPositionService;
import egovframework.com.uss.omt.service.PositionVO;
import egovframework.com.uss.umt.service.EgovUserManageService;
import egovframework.com.uss.umt.service.UserManageVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

/**
 * @Class Name : EgovAttachFileService.java
 * @Description : Service for managing attached file information
 * @Modification Information
 *
 *    Date         Author	   Content
 *   -----------  -------     -------------------
 *    2016.02.04   paul        initial
 *
 * @since 2016.02.04.
 * @version
 * @see
 *
 */
@Service("EgovSignerService")
public class EgovSignerServiceImpl extends EgovAbstractServiceImpl implements EgovSignerService {
	
	@Resource(name = "SignerDAO")
	private SignerDAO signerDAO;

    @Resource(name="EgovDeptService")
    private EgovDeptService deptService;
    
    @Resource(name = "EgovPositionService")
    private EgovPositionService posiService;
    
    @Resource(name = "EgovDutyService")
    private EgovDutyService dutyService;
    
    @Resource(name = "EgovFormService")
    private EgovFormService formService;
    
    @Resource(name = "EgovUserManageService")
    private EgovUserManageService userService;
    
    @Resource(name = "EgovRecipientService")
    private EgovRecipientService recipientService;
    
    @Resource(name = "EgovSignerHistoryService")
    private EgovSignerHistoryService signerHistoryService;

	@Resource(name = "SignerChangeHistoryDAO")
	private SignerChangeHistoryDAO signerChangeHistoryDAO;
	
    @Resource(name = "EgovAttachFileService")
    private EgovAttachFileService attachFileService;
    
    @Resource(name = "EgovApprovalDocService")
    private EgovApprovalDocService docService;
    
    @Resource(name = "EgovLabelService")
    private EgovLabelService labelService;
    
    @Resource(name= "egovSignerIdGnrService")
    private EgovIdGnrService idGnrService;
    
    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;
    
	private FormProcessor formProcessor = new FormProcessor();
	
    private static final Logger LOGGER = LoggerFactory.getLogger(EgovSignerService.class);

	@Override
	public List<SignerVO> getApprovalSignerList(String docID) throws Exception {
		SignerVO vo = new SignerVO();
		vo.setDocID(docID);
		
		return (List<SignerVO>)signerDAO.selectApprovalSigners(vo);
	}

	@Override
	public void insertApprovalSigner(SignerVO signer) throws Exception {
		signerDAO.insertApprovalSigner(signer);
		return;
	}

	@Override
	public void updateApprovalSigner(SignerVO signer) throws Exception {
		signerDAO.updateApprovalSigner(signer);
		return;
	}

	@Override
	public void deleteApprovalSigner(SignerVO signer) throws Exception {
		signerDAO.deleteApprovalSigner(signer);
		return;
	}

	public String getNextSignerId() throws Exception {
	    return idGnrService.getNextStringId();
	}

    public FormProcessor getFormProcessor() {
        return formProcessor;
    }

    public void setFormProcessor(FormProcessor formProcessor) {
        this.formProcessor = formProcessor;
    }
//
//  public void afterPropertiesSet() throws Exception {
//      if(formProcessor == null){
//          throw new ApprovalException("ApprovalSinger.formProcessor is null. check the configuraion.");
//      }
//  }

    /**
     * 주어진 문서의 결재선 목록을 리턴한다.
     * 
     * @param userID
     * @param docType
     * @param labelID
     * @return
     */
    public List<SignerVO> getSigners(String docID){
        
        try{
            SignerVO vo = new SignerVO();
            vo.setDocID(docID);
            
            List<SignerVO> signerList = signerDAO.selectApprovalSigners(vo);
            for(SignerVO signer : signerList){
                System.out.println(signer.getSignerName());
            }
            return signerList;
                
        }catch(Exception e){
            e.printStackTrace();
            // TODO Exception processing..
            return (new ArrayList<SignerVO>());
        }
        

    }
    
    private Date createCurrentDate(){
        return new Date();
    }
    
    private SignerVO createSingerFromUser(UserManageVO user) throws Exception {
        SignerVO signer = new SignerVO();
        signer.setUserID(user.getUniqId());
        signer.setSignerName(user.getEmplyrNm());
        signer.setSignerDeptID(user.getOrgnztId());
        
        DeptVO dept = deptService.getDept(user.getOrgnztId());
        if (dept != null) {
            signer.setSignerDeptName(dept.getDeptNm());
        }
        
        PositionVO posi = posiService.getPosition(user.getPositionId());
        if (posi != null) {
            signer.setSignerPositionName(posi.getPosiNm());
        }
        
        DutyVO duty = dutyService.getDuty(user.getDutyId());
        if (duty != null) {
            signer.setSignerDutyName(duty.getDutyNm());
        }
        
        return signer;
    }
    
    public List<SignerVO> getDefaultSignerList(FormVO form, UserManageVO drafter) throws Exception{
        // TODO for the form-based auto singer list, the latest signer list for the form.
        List<SignerVO> signerList = new ArrayList<SignerVO>();
        SignerVO signerDrafter = createSingerFromUser(drafter);
        signerDrafter.setSignSeq(1);
        signerDrafter.setSignKind(ApprovalConstants.SIGNER_KIND_DRAFT);
        signerDrafter.setSignState(ApprovalConstants.SIGNER_STATE_WAIT);
        signerList.add(signerDrafter);
    
        return signerList;
    }
    
    public List<SignerVO> getDefaultReceiveSignerList(FormVO form, UserManageVO drafter) throws Exception{
        // TODO for the form-based auto singer list, the latest signer list for the form.
        List<SignerVO> signerList = new ArrayList<SignerVO>();
        SignerVO signerDrafter = createSingerFromUser(drafter);
        signerDrafter.setSignSeq(1);
        signerDrafter.setSignKind(ApprovalConstants.SIGNER_KIND_INCOMING);
        signerDrafter.setSignState(ApprovalConstants.SIGNER_STATE_WAIT);
        signerList.add(signerDrafter);
    
        return signerList;
    }
    
    private boolean isOngoingState(SignerVO signer){
        if(ApprovalConstants.SIGNER_STATE_HOLDING.equals(signer.getSignState())
                || ApprovalConstants.SIGNER_STATE_ONGOING.equals(signer.getSignState())){
            return true;
        }
        return false;
    }
    private boolean isWaitingState(SignerVO signer){
        if(ApprovalConstants.SIGNER_STATE_WAIT.equals(signer.getSignState())
                || ApprovalConstants.SIGNER_STATE_RETURN.equals(signer.getSignState())){
            return true;
        }
        return false;
    }
    private SignerVO getCurrentSigner(UserManageVO signer, List<SignerVO> signerList){
        for(int i=0; i<signerList.size(); i++){
            SignerVO tmp = signerList.get(i);
//            logger.debug("signerList["+i+"]=>["+tmp+"]");
//            if(isOngoingState(tmp) && tmp.getUserID().equals(signer.getUniqId())){
            if(tmp.getUserID().equals(signer.getUniqId())){
                return tmp;
            }
        }
//        logger.debug("can not find the current signer.");
        return null;
    }
    private SignerVO getNextSigner(List<SignerVO> signerList)throws Exception{
        // TODO for parallel agreement
        List<SignerVO> nextSignerList = new ArrayList();
        for(int i=0; i<signerList.size(); i++){
            SignerVO tmp = signerList.get(i);
            LOGGER.debug("signerList[{}]=>[{}]", i, tmp);
            if(isWaitingState(tmp)){
                nextSignerList.add(tmp);
            }
        }
        if(nextSignerList.size() <= 0){
            return null;
        }
//      if(nextSignerList.size() > 1){
//          throw new ApprovalException("the waiting signer is more than one.");
//      }
        return nextSignerList.get(0);
    }
    
    private void doneSigner(SignerVO signer){
        signer.setSignDate(createCurrentDate());
        signer.setSignState(ApprovalConstants.SIGNER_STATE_FINISHED);
    }
    private void rejectSigner(SignerVO signer, List<SignerVO> signerList, String opinion){
        signer.setSignDate(createCurrentDate());
        signer.setSignState(ApprovalConstants.SIGNER_STATE_RETURN);
        /* SUJI.H */
        signer.setOpinion(opinion);
        /* SUJI.H */

        SignerVO drafter = getDrafter(signerList);
        drafter.setSignState(ApprovalConstants.SIGNER_STATE_ONGOING);
    }
    private void holdSigner(SignerVO signer, List<SignerVO> signerList, String opinion){
        signer.setSignDate(createCurrentDate());
        signer.setSignState(ApprovalConstants.SIGNER_STATE_HOLDING);
        signer.setOpinion(opinion);
    }
    private void ongoingDoc(Doc doc, 
                            SignerVO draftSigner, 
                            SignerVO currentSigner, 
                            List<SignerVO> signerList, 
                            List<RecipientVO> recipientList, 
                            List<AttachFileVO> attachList,
                            String preAttachedFileListJson) throws Exception{
        
        LOGGER.debug("doneDoc doc.getDocType()[{}], currentSigner[{}], signerList[{}], recipientList[{}], attachList[{}]",
                doc.getDocType(), currentSigner, signerList, recipientList, attachList);
        
        if(ApprovalConstants.DOC_TYPE_INTERNAL.equals(doc.getDocType()) &&
                recipientList != null && recipientList.size() > 0){
            doc.setDocType(ApprovalConstants.DOC_TYPE_OUTGOING);
        }else if(ApprovalConstants.DOC_TYPE_INTERNAL.equals(doc.getDocType()) &&
                (recipientList == null || recipientList.size() < 1)){
            doc.setDocType(ApprovalConstants.DOC_TYPE_INTERNAL);
        }
        // process doc
        doc.setDocPState(ApprovalConstants.DOC_PROGRESS_STATE_ONGOING);
        doc.setDocFState(ApprovalConstants.DOC_FINISH_STATE_ONGOING);
        if(attachList != null && attachList.size() > 0){
            doc.setDocAttaF(ApprovalConstants.APPROVAL_DOC_ATTA_F_EXIST);
        }else{
        	if(preAttachedFileListJson != null && preAttachedFileListJson.length() > 2){
        		doc.setDocAttaF(ApprovalConstants.APPROVAL_DOC_ATTA_F_EXIST);
        	}else{
        		doc.setDocAttaF(ApprovalConstants.APPROVAL_DOC_ATTA_F_NOTEXIST);
        	}
        }
    }
    private void processRecipientInfo(Doc doc, SignerVO drafterSigner, SignerVO lastSigner)throws Exception{
//        logger.debug("processRecipientInfo doc["+doc+"], drafterSigner["+drafterSigner+"], lastSigner["+lastSigner+"]");
        if(doc == null || !ApprovalConstants.DOC_TYPE_INCOMING.equals(doc.getDocType())){
            return;
        }
        
        String orgdocId = doc.getDocOrgId();
        String deptId = drafterSigner.getSignerDeptID(); // is right? doc info doesn't have the dept info
//        logger.debug("processRecipientInfo orgdocId["+orgdocId+"], deptId["+deptId+"]");
        RecipientVO recipient = recipientService.getRecipient(orgdocId, deptId);
        if(recipient == null){
//            logger.debug("processRecipientInfo recipient is null");
            return;
        }
//        logger.debug("processRecipientInfo doc.getDocPState())["+doc.getDocPState()+"], recipient["+recipient+"]");
        if(ApprovalConstants.DOC_PROGRESS_STATE_ONGOING.equals(doc.getDocPState()) && drafterSigner != null){
            recipient.setRecpRecpDt(drafterSigner.getSignDate());
            recipient.setRecpRecpUserId(drafterSigner.getUniqID());
            recipient.setRecpRecpUserNm(drafterSigner.getSignerName());
            recipientService.updateRecipient(recipient);
//            logger.debug("processRecipientInfo recipient.getRecpRecpDt()["+recipient.getRecpRecpDt()+"], recipient.getRecpRecpUserID()["+recipient.getRecpRecpUserID()+"], recipient.getRecpRecpUserNm()["+recipient.getRecpRecpUserNm()+"]");
        }else if(ApprovalConstants.DOC_PROGRESS_STATE_FINISHED.equals(doc.getDocPState()) && lastSigner != null){
            recipient.setRecpFinishDt(lastSigner.getSignDate());
            recipient.setRecpFinishUserId(lastSigner.getUniqID());
            recipient.setRecpFinishUserNm(lastSigner.getSignerName());
            recipientService.updateRecipient(recipient);
//            logger.debug("processRecipientInfo recipient.getRecpFinishDt()["+recipient.getRecpFinishDt()+"], recipient.getRecpFinishUserID()["+recipient.getRecpFinishUserID()+"], recipient.getRecpFinishUserNm()["+recipient.getRecpFinishUserNm()+"]");
        }
    }
    
    private void doneDoc(Doc doc, 
                        SignerVO draftSigner, 
                        SignerVO lastSigner, 
                        List<SignerVO> signerList, 
                        List<RecipientVO> recipientList, 
                        List<AttachFileVO> attachList,
                        String preAttachedFileListJson) throws Exception{
//        logger.debug("doneDoc doc.getDocType()["+doc.getDocType()+"], signerList["+signerList+"], recipientList["+recipientList+"], attachList["+attachList+"]");
        if(ApprovalConstants.DOC_TYPE_INTERNAL.equals(doc.getDocType()) &&
                recipientList != null && recipientList.size() > 0){
            doc.setDocType(ApprovalConstants.DOC_TYPE_OUTGOING);
        }else if(ApprovalConstants.DOC_TYPE_INTERNAL.equals(doc.getDocType()) &&
                (recipientList == null || recipientList.size() < 1)){
            doc.setDocType(ApprovalConstants.DOC_TYPE_INTERNAL);
        }
        // process doc
        doc.setDocPState(ApprovalConstants.DOC_PROGRESS_STATE_FINISHED);
        doc.setDocFState(ApprovalConstants.DOC_FINISH_STATE_COMPLETED);
        if(attachList != null && attachList.size() > 0){
            doc.setDocAttaF(ApprovalConstants.APPROVAL_DOC_ATTA_F_EXIST);
        }else{
        	if(preAttachedFileListJson != null && preAttachedFileListJson.length() > 2){
        		doc.setDocAttaF(ApprovalConstants.APPROVAL_DOC_ATTA_F_EXIST);
        	}else{
        		doc.setDocAttaF(ApprovalConstants.APPROVAL_DOC_ATTA_F_NOTEXIST);
        	}
        }
        
        String docNum = generateDraftDocNum(doc, draftSigner, lastSigner);
        doc.setDocCd(docNum);
//        logger.debug("doneDoc doc.getDocCd()["+doc.getDocCd()+"]");
        for(int i=0; recipientList != null && i<recipientList.size(); i++){
            RecipientVO recipient = (RecipientVO)recipientList.get(i);
            if(ApprovalConstants.SEND_TYPE_AUTO.equals(recipient.getRecpSendType()) || StringUtils.isEmpty(recipient.getRecpSendType())){
                recipient.setRecpSendDt(new Date());
                recipient.setRecpSendUserId(draftSigner.getUniqID());
                recipient.setRecpSendUserNm(draftSigner.getSignerName());
                recipient.setRecpSendType(ApprovalConstants.SEND_TYPE_AUTO);
                recipient.setRecpArrivalDt(new Date());
            }else{
                // to external
            }
        }
//        logger.debug("doneDoc recipientList["+recipientList+"]");
    }
    private void rejectDoc(Doc doc, 
                            SignerVO currentSigner, 
                            List<SignerVO> signerList, 
                            List<RecipientVO> recipientList, 
                            List<AttachFileVO> attachList){
        // process doc
        doc.setDocPState(ApprovalConstants.DOC_PROGRESS_STATE_RETURN);
        doc.setDocFState(ApprovalConstants.DOC_FINISH_STATE_ONGOING);
        
    }

    private void holdDoc(Doc doc, 
                            SignerVO currentSigner, 
                            List<SignerVO> signerList, 
                            List<RecipientVO> recipientList,
                            List<AttachFileVO> attachList) {
        // process doc
        doc.setDocPState(ApprovalConstants.DOC_PROGRESS_STATE_HOLDING);
        doc.setDocFState(ApprovalConstants.DOC_FINISH_STATE_ONGOING);
    }

    
    private SignerVO getDrafter(List<SignerVO> signerList){
        for(int i=0; i<signerList.size(); i++){
            SignerVO tmp = (SignerVO)signerList.get(i);
            if(ApprovalConstants.SIGNER_KIND_DRAFT.equals(tmp.getSignKind())
                    || ApprovalConstants.SIGNER_KIND_RE_DRAFT.equals(tmp.getSignKind())
                    || ApprovalConstants.SIGNER_KIND_INCOMING.equals(tmp.getSignKind())){
                return tmp;
            }
        }
        return null;
    }
    
 
    
    private File doneDocFile(File docFile, 
                            Doc doc, 
                            SignerVO draftSigner, 
                            SignerVO lastSigner, 
                            List<SignerVO> signerList, 
                            Map parameterMap)throws Exception{
        if(docFile == null || !docFile.exists()){
            docFile = createDocFileFromForm(doc, signerList);
        }
        
//        logger.debug("Doc["+doc.getDocID()+"=>["+docFile.getAbsoluteFile()+"]");
        if(!docFile.exists()){
            throw new Exception("The doc file["+docFile.getAbsolutePath()+"] does not exist!");
        }

        formProcessor.mergeDoneDoc(doc, docFile, draftSigner, lastSigner, signerList, parameterMap);
        
        return docFile;
    }
    
    private File ongoingDocFile(File docFile, 
                                Doc doc, 
                                SignerVO draftSigner, 
                                List<SignerVO> signerList, 
                                Map parameterMap) throws Exception{
        
        if(docFile == null || !docFile.exists()){
            docFile = createDocFileFromForm(doc, signerList);
        }
        
//        logger.debug("Doc["+doc.getDocID()+"=>["+docFile.getAbsoluteFile()+"]");
        if(!docFile.exists()){
            throw new Exception("The doc file["+docFile.getAbsolutePath()+"] does not exist!");
        }

        formProcessor.mergeDoc(doc, docFile, draftSigner, signerList, parameterMap);
        
        return docFile;
    }
    
    private List<SignerVO> arrangeSignerList(List<SignerVO> signerList){
        int ongoingIndex = -1;
        for(int i=0; i<signerList.size(); i++){
            SignerVO signer = (SignerVO)signerList.get(i);
//            logger.debug("arrangeSignerList signerList before ["+i+"]["+signer+"]");
            if(!ApprovalConstants.SIGNER_STATE_FINISHED.equals(signer.getSignState())){
                if(ongoingIndex < 0){
                    ongoingIndex = i;
                }
                if(ongoingIndex == i){
                    signer.setSignState(ApprovalConstants.SIGNER_STATE_ONGOING);
                }else{
                    signer.setSignState(ApprovalConstants.SIGNER_STATE_WAIT);
                }
            }
//            logger.debug("arrangeSignerList signerList after ["+i+"]["+signer+"]");
        }
        
        return signerList;
    }
    
    public File approveForDraft(UserManageVO drafter, 
                                ApprovalContext approvalContext, 
                                String docBody, 
                                Map parameterMap,
                                String preAttachedFileListJson) throws Exception{
        
        Doc doc = approvalContext.getDoc();
        List<SignerVO> signerList = approvalContext.getSignerList();
        List<RecipientVO> recipientList = approvalContext.getRecipientList();
        List<AttachFileVO> attachList = approvalContext.getAttachList();
        
        File docFile = PathUtil.createDocFile(doc, docBody);
        
        signerList = arrangeSignerList(signerList);
        
        SignerVO currentSigner = getCurrentSigner(drafter, signerList);
//        logger.debug("currentSigner["+currentSigner+"]");
        if(currentSigner == null){
            throw new Exception("can not find the current signer by the signer state in the signerList.");
        }
        SignerVO draftSigner = getDrafter(signerList);
        if(draftSigner == null){
            throw new Exception("can not find the drafter info["+signerList+"]");
        }
        // done the singer
        doneSigner(currentSigner);

        SignerVO nextSigner = getNextSigner(signerList);
        if(nextSigner != null){
            LOGGER.debug("approveForDraft....next signer.....");
            
            ongoingDoc(doc, draftSigner, currentSigner, signerList, recipientList, attachList, preAttachedFileListJson);
            
            nextSigner.setSignState(ApprovalConstants.SIGNER_STATE_ONGOING);
            
            docFile = ongoingDocFile(docFile, doc, draftSigner, signerList, parameterMap);
            
            // apply the doc, signerList and so on to the db
            applyToDB(doc, signerList, recipientList, attachList, currentSigner, draftSigner, null, preAttachedFileListJson);
        }
        else{
            LOGGER.debug("approveForDraft....next signer is null.....");
            
            doneDoc(doc, draftSigner, currentSigner, signerList, recipientList, attachList, preAttachedFileListJson);
            
            docFile = doneDocFile(docFile, doc, draftSigner, currentSigner, signerList, parameterMap);
            
            // TODO send doc to recipients....
            
            // apply the doc, signerList and so on to the db
            applyToDB(doc, signerList, recipientList, attachList, currentSigner, draftSigner, currentSigner, preAttachedFileListJson);
        }
        
        signerHistoryService.draft(currentSigner);
            
        return docFile;
    }
    
    public File approveForApprove(UserManageVO signer, 
                                ApprovalContext approvalContext, 
                                String docBody, 
                                Map parameterMap,
                                String preAttachedFileListJson) throws Exception {
        
        Doc doc = approvalContext.getDoc();
        List<SignerVO> signerList = approvalContext.getSignerList();
        List<RecipientVO> recipientList = approvalContext.getRecipientList();
        List<AttachFileVO> attachList = approvalContext.getAttachList();
        
        File docFile = PathUtil.createDocFile(doc, docBody);
        
        signerList = arrangeSignerList(signerList);
        
        SignerVO currentSigner = getCurrentSigner(signer, signerList);
        LOGGER.debug("approveForApprove find currentSigner[{}]", currentSigner);
        if(currentSigner == null){
            throw new Exception("can not find the current signer by the signer state in the signerList.");
        }
        SignerVO draftSigner = getDrafter(signerList);
        LOGGER.debug("approveForApprove find draftSigner[{}]", draftSigner);
        if(draftSigner == null){
            throw new Exception("can not find the drafter info["+signerList+"]");
        }
        // done the singer
        doneSigner(currentSigner);
        SignerVO nextSigner = getNextSigner(signerList);
       
        if(nextSigner != null){
            // 검토 처리
            ongoingDoc(doc, draftSigner, currentSigner, signerList, recipientList, attachList, preAttachedFileListJson);
            LOGGER.debug("approveForApprove after ongoingDoc[{}]", doc);
            
            nextSigner.setSignState(ApprovalConstants.SIGNER_STATE_ONGOING);
            LOGGER.debug("approveForApprove after nextSigner[{}]", nextSigner);
            
            docFile = ongoingDocFile(docFile, doc, draftSigner, signerList, parameterMap);
            LOGGER.debug("approveForApprove ongoingDoc docFile[{}]", docFile.getAbsolutePath());
            // apply the doc, signerList and so on to the db
            applyToDB(doc, signerList, recipientList, attachList, currentSigner, draftSigner, null, preAttachedFileListJson);
        }else{
            // 결재 처리
            doneDoc(doc, draftSigner, currentSigner, signerList, recipientList, attachList, preAttachedFileListJson);
            LOGGER.debug("approveForApprove after doneDoc[{}]", doc);
            
            docFile = doneDocFile(docFile, doc, draftSigner, currentSigner, signerList, parameterMap);
            LOGGER.debug("approveForApprove doneDoc docFile[{}]", docFile.getAbsolutePath());
            
            // apply the doc, signerList and so on to the db
            applyToDB(doc, signerList, recipientList, attachList, currentSigner, draftSigner, currentSigner, preAttachedFileListJson);
        }
        
        signerHistoryService.approval(currentSigner);
        return docFile;
    }   
    
    public File approveForReject(UserManageVO signer, Doc doc, String opinion) throws Exception{
        SignerVO vo = new SignerVO();
        vo.setDocID(doc.getDocID());
        
        List<SignerVO> signerList = signerDAO.selectApprovalSigners(vo);
        
        List<RecipientVO> recipientList = recipientService.getRecipientList(doc.getDocID());
        List<AttachFileVO> attachList = attachFileService.getAttachFileListByDocId(doc.getDocID());
        
        File docFile = PathUtil.getDocPath(doc);
        
        SignerVO currentSigner = getCurrentSigner(signer, signerList);
        if(currentSigner == null){
            throw new Exception("can not find the current signer by the signer state in the signerList.");
        }
        currentSigner.setDocVersion(doc.getDocVersion());
        
        SignerVO draftSigner = getDrafter(signerList);
        if(draftSigner == null){
            throw new Exception("can not find the drafter info["+signerList+"]");
        }
        // done the singer
        rejectSigner(currentSigner, signerList, opinion);

        rejectDoc(doc, currentSigner, signerList, recipientList, attachList);
        
        applyToDB(doc, signerList, recipientList, attachList, currentSigner, draftSigner, null, null);
        
        signerHistoryService.reject(currentSigner);
        
        return docFile;
    }

    public File approveForHold(UserManageVO signer, Doc doc, String opinion) throws Exception{
        SignerVO vo = new SignerVO();
        vo.setDocID(doc.getDocID());
        
        List<SignerVO> signerList = signerDAO.selectApprovalSigners(vo);
        
        List<RecipientVO> recipientList = recipientService.getRecipientList(doc.getDocID());
        List<AttachFileVO> attachList = attachFileService.getAttachFileListByDocId(doc.getDocID());
        
        File docFile = PathUtil.getDocPath(doc);
        
        SignerVO currentSigner = getCurrentSigner(signer, signerList);
        if(currentSigner == null){
            throw new Exception("can not find the current signer by the signer state in the signerList.");
        }
        currentSigner.setDocVersion(doc.getDocVersion());
        
        SignerVO draftSigner = getDrafter(signerList);
        if(draftSigner == null){
            throw new Exception("can not find the drafter info["+signerList+"]");
        }
        // done the singer
        holdSigner(currentSigner, signerList, opinion);

        holdDoc(doc, currentSigner, signerList, recipientList, attachList);
        
        applyToDB(doc, signerList, recipientList, attachList, currentSigner, draftSigner, null, null);
        
        signerHistoryService.hold(currentSigner);
        
        return docFile;
    }
    
    public File approveForReceive(UserManageVO signer, 
                                    ApprovalContext approvalContext, 
                                    String docBody, 
                                    Map parameterMap)throws Exception{
        
        Doc doc = approvalContext.getDoc();
        List<SignerVO> signerList = approvalContext.getSignerList();
        List<RecipientVO> recipientList = approvalContext.getRecipientList();
        List<AttachFileVO> attachList = approvalContext.getAttachList();
        
        File docFile = PathUtil.createDocFile(doc, docBody);
        
        signerList = arrangeSignerList(signerList);
        
        SignerVO currentSigner = getCurrentSigner(signer, signerList);
//        logger.debug("currentSigner["+currentSigner+"]");
        if(currentSigner == null){
            throw new Exception("can not find the current signer by the signer state in the signerList.");
        }
        
        SignerVO draftSigner = getDrafter(signerList);
        if(draftSigner == null){
            throw new Exception("can not find the drafter info["+signerList+"]");
        }
        // done the singer
        doneSigner(currentSigner);
        
        
        SignerVO nextSigner = getNextSigner(signerList);
        if(nextSigner != null){
            ongoingDoc(doc, draftSigner, currentSigner, signerList, recipientList, attachList, null);
            
            nextSigner.setSignState(ApprovalConstants.SIGNER_STATE_ONGOING);
            
            docFile = ongoingDocFile(docFile, doc, draftSigner, signerList, parameterMap);
            
            // apply the doc, signerList and so on to the db
            applyToDB(doc, signerList, recipientList, attachList, currentSigner, draftSigner, null, null);
        }else{
            doneDoc(doc, draftSigner, currentSigner, signerList, recipientList, attachList, null);
            
            docFile = doneDocFile(docFile, doc, draftSigner, currentSigner, signerList, parameterMap);
            
            // TODO send doc to recipients....
            
            // apply the doc, signerList and so on to the db
            applyToDB(doc, signerList, recipientList, attachList, currentSigner, draftSigner, currentSigner, null);
        }
        
        signerHistoryService.receive(currentSigner);
        
        return docFile;
    }
    
    private void applySignerList(Doc doc, SignerVO currentSigner, List<SignerVO> signerList) throws Exception{
        if(doc == null || signerList == null) {
            return;
        }
        
        processSignerInfo(doc, currentSigner, signerList);
    }

    private int findIndex(SignerVO search, List<SignerVO> list) {
    	int idx = -1;
    	
    	int listSize = list.size();
    	SignerVO tmp = null;
    	
    	for (int i = 0; i < listSize; i++) {
    		tmp = list.get(i);
    		
    		if (tmp.getUserID().equalsIgnoreCase(search.getUserID())) {
    			return i;
    		}
    	}
    	return idx;
    }
    
	private void processSignerChangeInfo(Doc doc, SignerVO currentSigner, List<SignerVO> currentSignerList, List<SignerVO> changedSignerList) throws Exception {
		if (isDrafting(currentSignerList)) {
			return;
		}
		
		SignerVO vo = new SignerVO();
        vo.setDocID(doc.getDocID());
        
		List<SignerVO> oriSignerList = orderListByAsc(currentSignerList);
		List<SignerVO> changedItemList = new ArrayList<SignerVO>();
		SignerChangeHistoryVO changer = createChanger(doc, currentSigner);
		
		changedItemList = processChangedItem(oriSignerList, changedSignerList);
        writeChangedHistoryToDb(changer, changedSignerList, changedItemList, oriSignerList);
	}

	private boolean isDrafting(List<SignerVO> currentSignerList) {
		if ((currentSignerList == null) || (currentSignerList.size()==0)) {
			return true;
		}
		return false;
	}

	private List<SignerVO> processChangedItem(List<SignerVO> oriSignerList, List<SignerVO> changedSignerList) {
		List<SignerVO> changedItemList = makeChangedItemList(oriSignerList, changedSignerList);
		
		deleteSameItemsInBothList(oriSignerList, changedSignerList);
		
		return changedItemList;
	}

	private void deleteSameItemsInBothList(List<SignerVO> oriSignerList, List<SignerVO> changedSignerList) {
		int oriListSize = oriSignerList.size();
		
		for (int i = (oriListSize-1); i >= 0; i--) {
			SignerVO oriSigner = oriSignerList.get(i);
			int targetIdx = findIndex(oriSigner, changedSignerList);
			if (targetIdx >= 0) {
				changedSignerList.remove(targetIdx);
				oriSignerList.remove(i);
			}
		}
	}

	private List<SignerVO> makeChangedItemList(List<SignerVO> oriSignerList, List<SignerVO> changedSignerList) {
		List<SignerVO> changedItemList = new ArrayList<SignerVO>();
		
		SignerVO oriSigner = null;
		SignerVO changedSigner = null;
		
		int oriListSize = oriSignerList.size();
		int changedListSize = changedSignerList.size();
		
		for (int i = 0; i < oriListSize; i++) {
			oriSigner = oriSignerList.get(i);
			
			if (isOutOfRange(i, changedListSize)) {
				changedItemList.add(oriSigner);
			} else {
				changedSigner = changedSignerList.get(i);
				
				if (oriSigner.getUserID().equalsIgnoreCase(changedSigner.getUserID()) == false) {
					changedItemList.add(oriSigner);
				}
			}
		}
		return changedItemList;
	}

	private boolean isOutOfRange(int index, int listSize) {
		if (index >= listSize) {
			return true;
		}
		return false;
	}

	private SignerChangeHistoryVO createChanger(Doc doc, SignerVO currentSigner) {
		SignerChangeHistoryVO changeVO = new SignerChangeHistoryVO();
		
		changeVO.setDocId(doc.getDocID());
		changeVO.setChangeDate(Calendar.getInstance().getTime());
		changeVO.setInitiatorId(currentSigner.getUserID());
		return changeVO;
	}

	private List<SignerVO> orderListByAsc(List<SignerVO> prevSignerListByDesc) {
		int size = prevSignerListByDesc.size();
		
		List<SignerVO> newList = new ArrayList<SignerVO>();
		for (int i = (size-1); i >= 0; --i) {
			newList.add((size-1-i), prevSignerListByDesc.get(i));
		}
		return newList;
	}

	private void writeChangedHistoryToDb(SignerChangeHistoryVO changer, List<SignerVO> addedSignerList, List<SignerVO> changedSignerList,
			List<SignerVO> deletedSignerList) throws Exception {
		if (isValidList(addedSignerList)) {
			writeAddedHistory(changer, addedSignerList);
		}
		
		if (isValidList(changedSignerList)) {
			writeChangedHistory(changer, changedSignerList);
		}
		
		if (isValidList(deletedSignerList)) {
			writeDeletedHistory(changer, deletedSignerList);
		}
		
		addedSignerList = null;
		changedSignerList = null;
		deletedSignerList = null;
	}

	private boolean isValidList(List<SignerVO> list) {
		if ((list == null) || (list.size()==0)) {
			return false;
		}
		return true;
	}

	private void writeDeletedHistory(SignerChangeHistoryVO historyVO, List<SignerVO> deletedSignerList) throws Exception {
		for (SignerVO signer : deletedSignerList) {
			historyVO.setTargetId(signer.getUserID());
			historyVO.setActivity(makeHistoryForDeletion(signer.getSignerName()));
			
			signerChangeHistoryDAO.insertHistory(historyVO);
		}
	}

	private void writeChangedHistory(SignerChangeHistoryVO historyVO, List<SignerVO> changedSignerList) throws Exception {
		for (SignerVO signer : changedSignerList) {
			historyVO.setTargetId(signer.getUserID());
			historyVO.setActivity(makeHistoryForModification(signer.getSignerName()));
			
			signerChangeHistoryDAO.insertHistory(historyVO);
		}
	}

	private void writeAddedHistory(SignerChangeHistoryVO historyVO, List<SignerVO> addedSignerList) throws Exception {
		for (SignerVO signer : addedSignerList) {
			historyVO.setTargetId(signer.getUserID());
			historyVO.setActivity(makeHistoryForInsertion(signer.getSignerName()));
			
			signerChangeHistoryDAO.insertHistory(historyVO);
		}
	}

	private String makeHistoryForDeletion(String userName) {
		StringBuffer history = new StringBuffer();
		history.append("delete ");
		history.append(userName);
		
		return history.toString();
	}

	private String makeHistoryForModification(String userName) {
		StringBuffer history = new StringBuffer();
		history.append("change sequence of ");
		history.append(userName);
		
		return history.toString();
	}

	private String makeHistoryForInsertion(String userName) {
		StringBuffer history = new StringBuffer();
		history.append("add ");
		history.append(userName);
		
		return history.toString();
	}
	
	private void processSignerInfo(Doc doc, SignerVO currentSigner, List<SignerVO> signerList) throws Exception {
		SignerVO vo = new SignerVO();
        vo.setDocID(doc.getDocID());
        
        List<SignerVO> signerListFromDB = signerDAO.selectApprovalSigners(vo);
        List<SignerVO> oriSignerListFromDB = signerListFromDB;
        List<SignerVO> insertSignerList = new ArrayList<SignerVO>();
        List<SignerVO> updateSignerList = new ArrayList<SignerVO>();
        List<SignerVO> deleteSignerList = new ArrayList<SignerVO>();
        
        for(int i=0; i<signerList.size(); i++) {
            SignerVO signer = (SignerVO)signerList.get(i);
            SignerVO srcSigner = null;
            for(int j=0; signerListFromDB != null && j<signerListFromDB.size(); j++){
                SignerVO tmp = (SignerVO)signerListFromDB.get(j);
                if(tmp.getSignerID().equals(signer.getSignerID())){
                    signerListFromDB.remove(j);
                    srcSigner = tmp;
                    break;
                }
            }
            if(srcSigner != null){
                updateSignerList.add(signer);
            }else if(srcSigner == null){
                insertSignerList.add(signer);
            }
        }
        
        if (isInsertedWaitingSigner(currentSigner, signerList)) {
        	initSignState(currentSigner, updateSignerList);
        }

        processSignerChangeInfo(doc, currentSigner, oriSignerListFromDB, signerList);
        
        if(signerListFromDB.size() > 0){
            deleteSignerList.addAll(signerListFromDB);
        }
        for(int i=0; i<insertSignerList.size(); i++){
            insertApprovalSigner(insertSignerList.get(i));
        }   
        for(int i=0; i<updateSignerList.size(); i++){
            updateApprovalSigner(updateSignerList.get(i));
        }   
        for(int i=0; i<deleteSignerList.size(); i++){
            deleteApprovalSigner(deleteSignerList.get(i));
        }
	}
    
    private void initSignState(SignerVO currentSigner, List<SignerVO> signerList) {
    	SignerVO signer = null;
		int currentSignerIdx = findIndex(currentSigner, signerList);
		if (currentSignerIdx >= 0) {
			signer = signerList.get(currentSignerIdx);
			signer.setSignState(ApprovalConstants.SIGNER_STATE_ONGOING);
			signer.setOpinion(null);
		} else {
			signer = currentSigner;
			signer.setSignState(ApprovalConstants.SIGNER_STATE_ONGOING);
			signer.setOpinion(null);
			
			signerList.add(signer);
		}
	}

	private boolean isInsertedWaitingSigner(SignerVO currentSigner, List<SignerVO> signerList) {
		int currentSignerIdx = findIndex(currentSigner, signerList);
		
		if ((currentSignerIdx > 0)
				&& isOngoingSigner(signerList.get(currentSignerIdx-1))) {
			return true;
		}
		return false;
	}

	private boolean isOngoingSigner(SignerVO signer) {
		return signer.getSignState().equalsIgnoreCase(ApprovalConstants.SIGNER_STATE_ONGOING);
	}

	private void applyDoc(Doc doc)throws Exception{
        LOGGER.debug("applyDoc - enter");
        
        if(doc == null) {
            LOGGER.debug("applyDoc - doc is null");
            return;
        }
        
        Doc orgDoc = docService.getDoc(doc.getDocID());
        
        if(orgDoc == null){
            LOGGER.debug("applyDoc - insert doc.....");
            docService.insertDoc(doc);
        }else{
            LOGGER.debug("applyDoc - update doc.....");
            docService.updateDoc(doc);
        }
    }
    
    private void applyRecipient(Doc doc, List<RecipientVO> recipientList) throws Exception{
    	if(recipientList == null) {return;}
        
        List<RecipientVO> recipientListFromDB = recipientService.getRecipientList(doc.getDocID());
        List<RecipientVO> insertRecipientList = new ArrayList<RecipientVO>();
        List<RecipientVO> updateRecipientList = new ArrayList<RecipientVO>();
        List<RecipientVO> deleteRecipientList = new ArrayList<RecipientVO>();
        
        for(int i=0; i<recipientList.size(); i++){
            RecipientVO recipient = (RecipientVO)recipientList.get(i);
            RecipientVO srcRecipient = null;
            
            for(int j=0; (recipientListFromDB != null) && (j<recipientListFromDB.size()); j++){
                RecipientVO tmp = (RecipientVO)recipientListFromDB.get(j);
                
                if(tmp.getRecpId().equals(recipient.getRecpId())){
                    recipientListFromDB.remove(j);
                    srcRecipient = tmp;
                    break;
                }
            }
            
            if(srcRecipient != null){
                updateRecipientList.add(recipient);
            }else {
                insertRecipientList.add(recipient);
            }
        }
        
        if((recipientListFromDB != null) && recipientListFromDB.size() > 0){
            deleteRecipientList.addAll(recipientListFromDB);
        }
        
        for(int i=0; i<insertRecipientList.size(); i++){
            recipientService.insertRecipient(insertRecipientList.get(i));
        }   
        for(int i=0; i<updateRecipientList.size(); i++){
            recipientService.updateRecipient(updateRecipientList.get(i));
        }   
        for(int i=0; i<deleteRecipientList.size(); i++){
            recipientService.deleteRecipient(deleteRecipientList.get(i));
        }
    }
    
    private void applyAttachList(Doc doc, List<AttachFileVO> attachList, String preAttachedFileListJson) throws Exception {
        if(attachList == null) {
            return;
        }
        
        List<AttachFileVO> insertAttachList = new ArrayList<AttachFileVO>();
        List<AttachFileVO> deleteAttachList = new ArrayList<AttachFileVO>();
        List<AttachFileVO> attachListFromDB = attachFileService.getAttachFileListByDocId(doc.getDocID());
        List<String> preAttachedFileIdList = getPreAttachedFileIdList(preAttachedFileListJson);;
        
        for(int i=0; i<attachList.size(); i++){
            AttachFileVO attach = (AttachFileVO)attachList.get(i);
            AttachFileVO srcAttach = null;
            
            for(int j=0; j<attachListFromDB.size(); j++){
                AttachFileVO tmp = (AttachFileVO)attachListFromDB.get(j);
                if(attach.getAttachID().equals(tmp.getAttachID())){
                    srcAttach = tmp;
                    attachListFromDB.remove(tmp);
                    break;
                }       
            }
            
            if(srcAttach == null){
                insertAttachList.add(attach);
            }
        }
        
        for (int i = 0; i < attachListFromDB.size(); i++) {
            if ((preAttachedFileIdList != null) 
                    && preAttachedFileIdList.contains(attachListFromDB.get(i).getAttachID())) {
                continue;
            }
            deleteAttachList.add(attachListFromDB.get(i));
        }

        for(int i=0; i<insertAttachList.size(); i++){
            attachFileService.insertAttachFile(insertAttachList.get(i));
        }
        
        for(int i=0; i<deleteAttachList.size(); i++){
            attachFileService.deleteAttachFile(deleteAttachList.get(i));
        }
    }

    public List<String> getPreAttachedFileIdList(String preAttachedFileListJson) throws JSONException {
        List<String> preAttachedFileIdList = null;
        
        if ((preAttachedFileListJson != null) && (preAttachedFileListJson.isEmpty() == false)) {
            JSONArray jsonArray = new JSONArray(preAttachedFileListJson);
            
            if (jsonArray.length() > 0) {
                preAttachedFileIdList = new ArrayList<String>();
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                
                String attachId = obj.getString("attachId");
                if (attachId != null) {
                    preAttachedFileIdList.add(attachId);
                }
            }
        }
        return preAttachedFileIdList;
    }
    
    private void applyToDB(Doc doc, 
                            List<SignerVO> signerList,
                            List<RecipientVO> recipientList, 
                            List<AttachFileVO> attachList,
                            SignerVO currentSigner,
                            SignerVO draftSigner, 
                            SignerVO lastSigner,
                            String preAttachedFileListJson) throws Exception {
//        logger.debug("applyToDB doc["+doc+"]");
//        logger.debug("applyToDB signerList["+signerList+"]");
//        logger.debug("applyToDB recipientList["+recipientList+"]");
//        logger.debug("applyToDB attachList["+attachList+"]");
//        logger.debug("applyToDB draftSigner["+draftSigner+"], lastSigner["+lastSigner+"]");
    
        applyDoc(doc);
        applySignerList(doc, currentSigner, signerList);
        applyRecipient(doc, recipientList);
        applyAttachList(doc, attachList, preAttachedFileListJson);
        
        if(doc != null && ApprovalConstants.DOC_TYPE_INCOMING.equals(doc.getDocType())){
            processRecipientInfo(doc, draftSigner, lastSigner);
        }
    }

    public String mergeSignTable(Doc doc, String signTableId, List<SignerVO> signerList) throws Exception{
        File bodyFile = PathUtil.getDocPath(doc);
        
        if(doc.getDocID().equals("no_doc_id")){
            FormVO form = formService.getForm(doc.getFormId());
            
            bodyFile = PathUtil.getFormPath(form);
            if(bodyFile == null || !bodyFile.exists()){
                throw new Exception("can not find the body file or form file");
            }
        }
        
        return formProcessor.mergeSignTable(bodyFile, signTableId, signerList);
    }
    
    private String generateDraftDocNum(Doc doc, SignerVO draftSigner, SignerVO approveSigner) throws Exception{
        return generateDocNum(doc, ApprovalConstants.DOC_NUM_TABLE_NAME_DRAFT, draftSigner, approveSigner);
    }

    /**
     * 기관별은 조직도 지원후 고려 
     * @D : 기관(대표부서) 이름 - TODO reserved 조직도에서 아직 지원안함.
     * @d : 부서 이름
     * @n : 부서별 일련 번호
     * @N : 기관(대표부서)별 일련번호 - TODO reserved 조직도에서 아직 지원안함.
     * @Y : 4자리 년도
     * @y : 2자리년도"." 2자리월 "." 2자리 일 즉, yy.mm.dd
     * @s : 2자리 년도
     * @F : 기관(대표부서)의 양식별일련번호 - TODO reserved 조직도에서 아직 지원안함.
     * @f : 부서의 양식별일련번호
     * @L : 문서의 레이블
     * @param doc
     * @param drafter
     * @return
     */
    private String generateDocNum(Doc doc, 
                                    String tableName, 
                                    SignerVO draftSigner, 
                                    SignerVO approveSigner) throws Exception {
        DeptVO draftDept = deptService.getDept(draftSigner.getSignerDeptID());
        DeptVO repDept = null;
        // TODO 기관(대표부서)
//      List tmpDeptList = draftDept == null ? null : orgContext.getDeptList(draftDept.getCommunityID(), draftDept.getID(), OrgConstant.SCOPE_PATH, new FlagKey(OrgConstant.UF_REP_DEPT, true));
//      if(tmpDeptList != null && tmpDeptList.size() > 0){
//          repDept = (Dept)tmpDeptList.get(0);
//      }
        Date dStdYear = null;
        String stdYear = null;
        if(approveSigner != null && approveSigner.getSignDate() != null){
            dStdYear = approveSigner.getSignDate();
            stdYear = String.valueOf(dStdYear.getYear() + 1900);
        }
        
        String macroDocNum = doc.getDocCd();
        
        StringBuffer sbDocNum=new StringBuffer();

        for(int n=0; n<macroDocNum.length() ; n++){

            char c = macroDocNum.charAt(n);

            if( c != '@' ){
                sbDocNum.append((char)c);

                continue;
            }
            // @ + char중에서 char를 가져오기위해서이다.
            n++;
            c = macroDocNum.charAt(n);

            int nDocNum = -1;

            switch(c) {
                case 'D':
                    // 대표부서 이름 전체
                    if(repDept != null)
                        sbDocNum.append(repDept.getDeptNm());
                    else
                        sbDocNum.append("@D");
                    break;
                case 'd':
                    // 부서 이름 전체
                    if(draftDept != null)
                        sbDocNum.append(draftDept.getDeptNm());
                    else
                        sbDocNum.append("@d");
                    break;
                case 'Y':
                    // 년도, yyyy
                    sbDocNum.append(dStdYear.getYear()+1900);
                    break;
                case 'y':
                    // 일자, yy.mm.dd

                    int year = dStdYear.getYear();
                    if(year >= 100) year-=100; // 2000년이 넘으면 다시 100을 뺌.
                    int month = dStdYear.getMonth()+1;
                    String sMonth = String.valueOf(month);
                    if(month<10) sMonth = "0"+ sMonth;
                    int day = dStdYear.getDate();
                    String sDay = String.valueOf(day);
                    if(day<10) sDay = "0" + sDay;
                    sbDocNum.append(year + "." + sMonth + "." + sDay);
                    break;

                case 's':
                    // 짧은 년도, yy
                    int year3 = dStdYear.getYear();
                    if(year3 >= 100) year3 -= 100;
                    String sYear3 = String.valueOf(year3);
                    if(year3 == 0) sYear3 = "00";
                    else if(year3 < 10) sYear3 = "0" + year3;
                    sbDocNum.append(sYear3);
                    break;

                case 'N':// 대표 부서
                    if(repDept != null){
                        nDocNum = docService.getNextDocNumber(stdYear, tableName, repDept.getDeptId(), ApprovalConstants.DOC_NUM_FORM_ID_ALL);
                        sbDocNum.append(nDocNum);
                    }else{
                        sbDocNum.append("@N");
                    }
                    break;
                case 'n':// 부서
                    if(draftDept != null){
                        nDocNum = docService.getNextDocNumber(stdYear, tableName, draftDept.getDeptId(), ApprovalConstants.DOC_NUM_FORM_ID_ALL);
                        sbDocNum.append(nDocNum);
                    }else{
                        sbDocNum.append("@n");
                    }
                    break;
                case 'F':// 대표 부서
                    if(repDept != null){
                        nDocNum = docService.getNextDocNumber(stdYear, tableName, repDept.getDeptId(), doc.getFormId());
                        sbDocNum.append(nDocNum);
                    }else{
                        sbDocNum.append("@F");
                    }
                    break;
                case 'f':// 부서
                    if(draftDept != null){
                        nDocNum = docService.getNextDocNumber(stdYear, tableName, draftDept.getDeptId(), doc.getFormId());
                        sbDocNum.append(nDocNum);
                    }else{
                        sbDocNum.append("@f");
                    }
                    break;
                case 'L':// 레이블
                    if(doc.getLbelId() != null){
                        LabelVO label = labelService.getLabel(doc.getLbelId());
                        if(label == null){
                            sbDocNum.append(nDocNum);
                        }else{
                            sbDocNum.append("@L");
                        }
                    }else{
                        sbDocNum.append("@L");
                    }
                    break;
            }
        }

        return sbDocNum.toString();
    }
    
    public List<SignerVO> getSignerListForRedraft(Doc doc) throws Exception {
        List<SignerVO> signerList = getApprovalSignerList(doc.getDocID());
        
        for(int i=0; i<signerList.size(); i++){
            SignerVO signer = (SignerVO)signerList.get(i);
            
            if(signer.getSignKind().equals(ApprovalConstants.SIGNER_KIND_DRAFT)){
                signer.setSignKind(ApprovalConstants.SIGNER_KIND_RE_DRAFT);
                signer.setSignState(ApprovalConstants.SIGNER_STATE_ONGOING);
            }
        }
        return signerList;
    }
    
    public String getDocForView(Doc doc) throws Exception{
        return formProcessor.getDocForView(doc);
    }

    public String getWriteDocBody(Doc doc) throws Exception{
        String formHTML = formProcessor.getWriteDoc(doc);
        return formHTML;
    }
  
    /**
     * 외부 스캔문서 접수 등록(AP-M01-004-006)
     * - 발송정보(발송문서정보, 발송결재선, 발송문서첨부)
     * - 수신정보(수신문서정보, 수신결재선, 수신문서의견)
     * @param incoming 등록정보
     * @param register
     * @return
     */
    public Doc registerIncoming(RegisterIncomingVO incoming, UserManageVO user) throws Exception {
        Doc doc = createDoc(incoming, user);
        
        List<SignerVO> sendSignerList = createSignerList(incoming, user);
        List<RecipientVO> recipientList = recipientService.createRecipient(incoming, user);
        List<AttachFileVO> attachList = incoming.getAttachList();
        
        if((attachList != null) && (attachList.size() > 0)){
            doc.setDocAttaF(ApprovalConstants.APPROVAL_DOC_ATTA_F_EXIST);
        }
        
        SignerVO drafterSigner = ((sendSignerList != null) && (sendSignerList.size() > 0)) ? sendSignerList.get(0) : null;
        SignerVO lasterSigner = ((sendSignerList != null) && (sendSignerList.size() > 1)) ? sendSignerList.get(1) : null;
        
        applyToDB(doc, sendSignerList, recipientList, attachList, drafterSigner, drafterSigner, lasterSigner, null);
        
        return doc;
    }
    
    private List<SignerVO> createSignerList(RegisterIncomingVO incoming, UserManageVO user) throws Exception {
        List<SignerVO> sendSignerList = new ArrayList<SignerVO>();

        String signerId = getNextSignerId();
        SignerVO drafter = new SignerVO();
        
        drafter.setSignerID(signerId);
        drafter.setSignSubUserFlag(ApprovalConstants.APPROVAL_SIGN_NON_SUBUSER);
        drafter.setSignSeq(1);
        drafter.setDocID(incoming.getDocID());
        drafter.setSignKind(ApprovalConstants.SIGNER_KIND_DRAFT);
        // use register?
//      drafter.setUserID(user.getID());
//      drafter.setSignerName(user.getName());
//      drafter.setSignerDeptID(user.getDeptID());
//      drafter.setSignerDeptName(user.getDeptName());
//      drafter.setSignerPositionName(user.getPositionName());
//      drafter.setSignerDutyName(user.getDutyName());
        drafter.setUniqID(ApprovalConstants.NULL_ID_STRING);
        drafter.setUserID(ApprovalConstants.NULL_ID_STRING);
        drafter.setSignerName(incoming.getSenderName());
        drafter.setSignerDeptID(ApprovalConstants.NULL_ID_STRING);
        drafter.setSignerDeptName(incoming.getSenderDeptName());
        drafter.setSignerPositionName(incoming.getSenderPositionName());
        drafter.setSignerDutyName(ApprovalConstants.NULL_ID_STRING);
        drafter.setSignState(ApprovalConstants.SIGNER_STATE_FINISHED);
        drafter.setSignDate(new Date());
        
        sendSignerList.add(drafter);
        
        SignerVO approver = (SignerVO)((SignerVO)drafter).clone();
        approver.setSignerID(getNextSignerId());
        drafter.setSignSubUserFlag(ApprovalConstants.APPROVAL_SIGN_NON_SUBUSER);
        drafter.setSignSeq(2);
        drafter.setDocID(incoming.getDocID());
        approver.setSignKind(ApprovalConstants.SIGNER_KIND_APPROVAL);
        approver.setUserID(ApprovalConstants.NULL_ID_STRING);
        approver.setSignerName(incoming.getSenderName());
        approver.setSignerDeptID(ApprovalConstants.NULL_ID_STRING);
        approver.setSignerDeptName(incoming.getSenderDeptName());
        approver.setSignerPositionName(incoming.getSenderPositionName());
        approver.setSignerDutyName(ApprovalConstants.NULL_ID_STRING);
        drafter.setSignState(ApprovalConstants.SIGNER_STATE_FINISHED);
        drafter.setSignDate(new Date());
        sendSignerList.add(approver);       
        
        return sendSignerList;
    }

    public List<SignerVO> createSignerList(RegisterInternalVO internal, UserManageVO user)throws Exception {
        List<SignerVO> signerList = new ArrayList<SignerVO>();
        
        UserManageVO drafter = userService.getUser(internal.getSignerUserID());
        String posiNm = null;
        
        PositionVO posiInfo = null;
        DutyVO dutyInfo = null;

        if (drafter != null) {
            posiInfo = posiService.getPosition(drafter.getPositionId());
            dutyInfo = dutyService.getDuty(drafter.getPositionId());
        } 
        
        String signerId = getNextSignerId();
        SignerVO draftSigner = new SignerVO();
        draftSigner.setSignerID(signerId);
        draftSigner.setSignSubUserFlag(ApprovalConstants.APPROVAL_SIGN_NON_SUBUSER);
        draftSigner.setSignSeq(1);
        draftSigner.setDocID(internal.getDocID());
        draftSigner.setSignKind(ApprovalConstants.SIGNER_KIND_REGISTER);
        draftSigner.setUserID(internal.getSignerUserID());
        draftSigner.setSignerName(internal.getSignerUserNm());
        draftSigner.setSignerDeptID(internal.getSignerDeptID());
        draftSigner.setSignerDeptName(internal.getSignerDeptNm());
        
        if ((drafter != null) && (posiInfo != null)) {
            draftSigner.setSignerPositionName(posiInfo.getPosiNm());
        } else {
            draftSigner.setSignerPositionName("");
        }
        
        if ((drafter != null) && (dutyInfo != null)) {
            draftSigner.setSignerDutyName(dutyInfo.getDutyNm());
        } else {
            draftSigner.setSignerDutyName(" ");
        }
        
        draftSigner.setSignState(ApprovalConstants.SIGNER_STATE_FINISHED);
        draftSigner.setSignDate(new Date());
        draftSigner.setOpinion(internal.getOpinion());
        signerList.add(draftSigner);
        
        return signerList;
    }
    
    /**
     * 내부 스캔문서 등록(AP-M01-012-006)
     * - 문서정보(문서정보, 결재선, 문서첨부)
     * @param incoming 등록정보
     * @param register
     * @return
     */
    public Doc registerInternal(RegisterInternalVO internal, UserManageVO user) throws Exception {
        Doc doc = createDoc(internal, user);
        
        List<SignerVO> sendSignerList = createSignerList(internal, user);
        List<AttachFileVO> attachList = internal.getAttachList();
        
        if ((attachList != null) && (attachList.size() > 0)) {
            doc.setDocAttaF(ApprovalConstants.APPROVAL_DOC_ATTA_F_EXIST);
        }
        
        SignerVO drafterSigner = ((sendSignerList != null) && (sendSignerList.size() > 0)) ? sendSignerList.get(0) : null;
        SignerVO lasterSigner = ((sendSignerList != null) && (sendSignerList.size() > 1)) ? sendSignerList.get(1) : null;
        
        applyToDB(doc, sendSignerList, null, attachList, drafterSigner, drafterSigner, lasterSigner, null);
        
        return doc;
    }
    

    private Doc createDoc(RegisterIncomingVO incoming, UserManageVO user) throws Exception {
        Doc doc = new DocImpl();
        doc.setDocID(incoming.getDocID());
        doc.setFormId(ApprovalConstants.NULL_ID_STRING);
        doc.setDocSyear(ApprovalConstants.APPROVAL_DOC_SYEAR_FOREVER);
        doc.setDocSlvl("99");
        doc.setDocEmF(ApprovalConstants.APPROVAL_DOC_EM_F_NORMAL);
        doc.setDocPpF(ApprovalConstants.APPROVAL_DOC_PP_F_PAPER);
        doc.setDocAttaF(ApprovalConstants.APPROVAL_DOC_ATTA_F_NOTEXIST);
        doc.setDocOpnF(ApprovalConstants.APPROVAL_DOC_OPN_F_NOTEXIST);
        doc.setDocPState(ApprovalConstants.DOC_PROGRESS_STATE_FINISHED);
        doc.setDocFState(ApprovalConstants.DOC_FINISH_STATE_COMPLETED);
        doc.setDocType(ApprovalConstants.DOC_TYPE_OUTGOING);
        doc.setDocTitle(incoming.getTitle());
        doc.setLbelId(incoming.getLabelID());
        
        if(incoming.getRegisterDocNum() != null){
            doc.setDocCd(incoming.getRegisterDocNum());
        }else{
            doc.setDocCd(ApprovalConstants.NULL_DOC_NUM);
        }
        
        //draft_title, draft_opentype
//        LOGGER.debug("createDoc doc[{}]", doc);
        return doc;
    }
    
    private Doc createDoc(RegisterInternalVO internal, UserManageVO user) throws Exception {
        Doc doc = new DocImpl();
        doc.setDocID(internal.getDocID());
        doc.setFormId(ApprovalConstants.NULL_ID_STRING);
        doc.setDocSyear(ApprovalConstants.APPROVAL_DOC_SYEAR_FOREVER);
        doc.setDocSlvl(internal.getSecurityLevel());
        doc.setDocEmF(ApprovalConstants.APPROVAL_DOC_EM_F_NORMAL);
        doc.setDocPpF(ApprovalConstants.APPROVAL_DOC_PP_F_PAPER);
        doc.setDocAttaF(ApprovalConstants.APPROVAL_DOC_ATTA_F_NOTEXIST);
        doc.setDocOpnF(ApprovalConstants.APPROVAL_DOC_OPN_F_NOTEXIST);
        doc.setDocPState(ApprovalConstants.DOC_PROGRESS_STATE_FINISHED);
        doc.setDocFState(ApprovalConstants.DOC_FINISH_STATE_COMPLETED);
        doc.setDocType(internal.getDocType().equals(null)? ApprovalConstants.DOC_TYPE_OUTGOING : internal.getDocType());
        doc.setDocTitle(internal.getTitle());
        doc.setLbelId(internal.getLabelID());
        if(internal.getDocNum() != null){
            doc.setDocCd(internal.getDocNum());
        }else{
            doc.setDocCd(ApprovalConstants.NULL_DOC_NUM);
        }
        
        //draft_title, draft_opentype
//        logger.debug("createDoc doc["+doc+"]");
        return doc;
    }
    
    @Override
    public String createDraftDoc(FormVO form, 
            UserManageVO drafter, 
            List<SignerVO> signerList, 
            List<RecipientVO> recipientList) throws Exception {

        String formHTML = formProcessor.mergeDocForDraftView(form, drafter, signerList);
        
        return formHTML;
    }

    private File createDocFileFromForm(Doc doc, List<SignerVO> signerList) throws Exception{
        FormVO form = formService.getForm(doc.getFormId());
        
        if(form == null){
            throw new Exception("can not find the form info["+doc.getFormId()+"]");
        }
        
        SignerVO draftSigner = getDrafter(signerList);
        if(draftSigner == null){
            throw new Exception("can not find the drafter info["+signerList+"]");
        }
        UserManageVO drafter = userService.getUser(draftSigner.getUniqID());
        
        String formHTML = formProcessor.mergeDocForDraftView(form, drafter, signerList);
        
        return PathUtil.createDocFile(doc, formHTML);
    }
}
