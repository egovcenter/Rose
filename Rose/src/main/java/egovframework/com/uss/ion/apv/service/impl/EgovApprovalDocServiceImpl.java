package egovframework.com.uss.ion.apv.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import egovframework.com.uss.ion.apm.FormProcessor;
import egovframework.com.uss.ion.apm.service.EgovFormService;
import egovframework.com.uss.ion.apm.service.FormVO;
import egovframework.com.uss.ion.apu.Pagination;
import egovframework.com.uss.ion.apu.PathUtil;
import egovframework.com.uss.ion.apv.ApprovalConstants;
import egovframework.com.uss.ion.apv.Doc;
import egovframework.com.uss.ion.apv.DocImpl;
import egovframework.com.uss.ion.apv.SearchCriteria;
import egovframework.com.uss.ion.apv.service.ApprovedDocVO;
import egovframework.com.uss.ion.apv.service.DraftDocVO;
import egovframework.com.uss.ion.apv.service.EgovApprovalDocService;
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
import egovframework.com.uss.ion.apv.service.SignerVO;
import egovframework.com.uss.ion.apv.service.WaitingDocVO;
import egovframework.com.uss.omt.service.DeptVO;
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
@Service("EgovApprovalDocService")
public class EgovApprovalDocServiceImpl extends EgovAbstractServiceImpl implements EgovApprovalDocService {

	@Resource(name = "DocDAO")
	private DocDAO docDAO;
	
    @Resource(name = "EgovFormService")
    private EgovFormService formService;
    
    @Resource(name = "EgovUserManageService")
    private EgovUserManageService userService;
    
    @Resource(name = "EgovSignerService")
    private EgovSignerService signerService;
    
    @Resource (name = "egovDocIdGnrService")
    private EgovIdGnrService idGnrService;
    
    
    private FormProcessor formProcessor = new FormProcessor();
    
	@Override
	public int getWaitingDocCnt(String uniqId) throws Exception {
		SignerVO vo = new SignerVO();
		vo.setUniqID(uniqId);
		
		return docDAO.selectWaitingDocCnt(vo);
	}

	@Override
	public List<WaitingDocVO> getWaitingDocList(String uniqId) throws Exception {
		SearchCriteria vo = new SearchCriteria();
		vo.setUserId(uniqId);
		vo.setOrderColumn("2");
		vo.setOrderMethod("desc");
		
		return docDAO.selectWaitingDocs(vo);
	}

	@Override
	public List<WaitingDocVO> getWaitingDocList(String uniqId, Pagination pagination) throws Exception {
		SearchCriteria vo = new SearchCriteria();
		vo.setUserId(uniqId);
		vo.setOrderColumn(pagination.getOrderColumn());
		vo.setOrderMethod(pagination.getOrderMethod());
		
		return docDAO.selectWaitingDocs(vo, pagination);
	}

	@Override
	public int getOutgoingDocCnt(String deptId) throws Exception {
		RecipientVO vo = new RecipientVO();
		vo.setDeptId(deptId);
		
		return docDAO.selectOutgoingDocCnt(vo);
	}

	@Override
	public List<OutgoingDocVO> getOutgoingDocList(String deptId) throws Exception {
		SearchCriteria vo = new SearchCriteria();
		vo.setOrgnztId(deptId);
		vo.setOrderColumn("2");
		vo.setOrderMethod("desc");
		
		return docDAO.selectOutgoingDocs(vo);
	}

	@Override
	public List<OutgoingDocVO> getOutgoingDocList(String deptId, Pagination pagination) throws Exception {
		SearchCriteria vo = new SearchCriteria();
		vo.setOrgnztId(deptId);
		vo.setOrderColumn(pagination.getOrderColumn());
		vo.setOrderMethod(pagination.getOrderMethod());
		
		return docDAO.selectOutgoingDocs(vo, pagination);
	}

	@Override
	public int getIncomingDocCnt(String deptId) throws Exception {
		RecipientVO vo = new RecipientVO();
		vo.setRecpId(deptId);
		
		return docDAO.selectIncomingDocCnt(vo);
	}

	@Override
	public List<IncomingDocVO> getIncomingDocList(String deptId) throws Exception {
		SearchCriteria vo = new SearchCriteria();
		vo.setOrgnztId(deptId);
		vo.setOrderColumn("2");
		vo.setOrderMethod("desc");
		
		return docDAO.selectIncomingDocs(vo);
	}

	@Override
	public List<IncomingDocVO> getIncomingDocList(String deptId, Pagination pagination) throws Exception {
		SearchCriteria vo = new SearchCriteria();
		vo.setOrgnztId(deptId);
		vo.setOrderColumn(pagination.getOrderColumn());
		vo.setOrderMethod(pagination.getOrderMethod());
		
		return docDAO.selectIncomingDocs(vo, pagination);
	}

	@Override
	public int getOngoingDocCnt(String uniqId) throws Exception {
		SignerVO vo = new SignerVO();
		vo.setUniqID(uniqId);
		
		return docDAO.selectOngoingDocCnt(vo);
	}

	@Override
	public List<OngoingDocVO> getOngoingDocList(String uniqId) throws Exception {
		SearchCriteria vo = new SearchCriteria();
		vo.setUserId(uniqId);
		vo.setOrderColumn("2");
		vo.setOrderMethod("desc");
		
		return docDAO.selectOngoingDocs(vo);
	}

	@Override
	public List<OngoingDocVO> getOngoingDocList(String uniqId, Pagination pagination) throws Exception {
		SearchCriteria vo = new SearchCriteria();
		vo.setUserId(uniqId);
		vo.setOrderColumn(pagination.getOrderColumn());
		vo.setOrderMethod(pagination.getOrderMethod());
		
		return docDAO.selectOngoingDocs(vo, pagination);
	}

	@Override
	public int getDraftDocCnt(String uniqId) throws Exception {
		SignerVO vo = new SignerVO();
		vo.setUniqID(uniqId);

		return docDAO.selectDraftDocCnt(vo);
	}

	@Override
	public List<DraftDocVO> getDraftDocList(String uniqId) throws Exception {
		SearchCriteria vo = new SearchCriteria();
		vo.setUserId(uniqId);
		vo.setOrderColumn("2");
		vo.setOrderMethod("desc");
		
		return docDAO.selectDraftDocs(vo);
	}

	@Override
	public List<DraftDocVO> getDraftDocList(String uniqId, Pagination pagination) throws Exception {
		SearchCriteria vo = new SearchCriteria();
		vo.setUserId(uniqId);
		vo.setOrderColumn(pagination.getOrderColumn());
		vo.setOrderMethod(pagination.getOrderMethod());
		
		return docDAO.selectDraftDocs(vo, pagination);
	}

	@Override
	public int getApprovedDocCnt(String uniqId) throws Exception {
		SignerVO vo = new SignerVO();
		vo.setUniqID(uniqId);
		
		return docDAO.selectApprovedDocCnt(vo);
	}

	@Override
	public List<ApprovedDocVO> getApprovedDocList(String uniqId) throws Exception {
		SearchCriteria vo = new SearchCriteria();
		vo.setUserId(uniqId);
		vo.setOrderColumn("2");
		vo.setOrderMethod("desc");
		
		return docDAO.selectApprovedDocs(vo);
	}

	@Override
	public List<ApprovedDocVO> getApprovedDocList(String uniqId, Pagination pagination) throws Exception {
		SearchCriteria vo = new SearchCriteria();
		vo.setUserId(uniqId);
		vo.setOrderColumn(pagination.getOrderColumn());
		vo.setOrderMethod(pagination.getOrderMethod());
		
		return docDAO.selectApprovedDocs(vo, pagination);
	}

	@Override
	public int getInboxDocCnt(String deptId) throws Exception {
		DeptVO vo = new DeptVO();
		vo.setDeptId(deptId);
		
		return (Integer)docDAO.selectInboxDocCnt(vo);
	}

	@Override
	public List<InboxDocVO> getInboxDocList(String deptId) throws Exception {
		SearchCriteria vo = new SearchCriteria();
		vo.setOrgnztId(deptId);
		vo.setOrderColumn("2");
		vo.setOrderMethod("desc");
		
		return docDAO.selectInboxDocs(vo);
	}

	@Override
	public List<InboxDocVO> getInboxDocList(String deptId, Pagination pagination) throws Exception {
		SearchCriteria vo = new SearchCriteria();
		vo.setOrgnztId(deptId);
		vo.setOrderColumn(pagination.getOrderColumn());
		vo.setOrderMethod(pagination.getOrderMethod());
		
		return docDAO.selectInboxDocs(vo, pagination);
	}

	@Override
	public int getOutboxDocCnt(String deptId) throws Exception {
		DeptVO vo = new DeptVO();
		vo.setDeptId(deptId);
		
		return docDAO.selectOutboxDocCnt(vo);
	}

	@Override
	public List<OutboxDocVO> getOutboxDocList(String deptId) throws Exception {
		SearchCriteria vo = new SearchCriteria();
		vo.setOrgnztId(deptId);
		vo.setOrderColumn("2");
		vo.setOrderMethod("desc");
		
		return docDAO.selectOutboxDocs(vo);
	}

	@Override
	public List<OutboxDocVO> getOutboxDocList(String deptId, Pagination pagination) throws Exception {
		SearchCriteria vo = new SearchCriteria();
		vo.setOrgnztId(deptId);
		vo.setOrderColumn(pagination.getOrderColumn());
		vo.setOrderMethod(pagination.getOrderMethod());
		
		return docDAO.selectOutboxDocs(vo, pagination);
	}

	@Override
	public int getLabelDocCnt(String deptId, String labelID) throws Exception {
		LabelDocVO vo = new LabelDocVO();
		vo.setDraftDeptId(deptId);
		vo.setLbelId(labelID);
		
		return docDAO.selectLabelDocCnt(vo);
	}

	@Override
	public List<LabelDocVO> getLabelDocList(String deptId, String labelID) throws Exception {
		LabelDocVO vo = new LabelDocVO();
		vo.setDraftDeptId(deptId);
		vo.setLbelId(labelID);
		vo.setOrderColumn("2");
		vo.setOrderMethod("desc");
		
		return docDAO.selectLabelDocs(vo);
	}

	@Override
	public List<LabelDocVO> getLabelDocList(String deptId, String labelID, Pagination pagination) throws Exception {
		LabelDocVO vo = new LabelDocVO();
		vo.setDraftDeptId(deptId);
		vo.setLbelId(labelID);
		vo.setOrderColumn(pagination.getOrderColumn());
		vo.setOrderMethod(pagination.getOrderMethod());
		
		return docDAO.selectLabelDocs(vo, pagination);
	}

	@Override
	public String getDeptIdBySBoxUserId(String userID) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDeptIdByRBoxUserId(String userID) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDeptIdByUserId(String userID) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertDoc(Doc doc) throws Exception {
		docDAO.insertDoc(doc);
	}

	@Override
	public void updateDoc(Doc doc) throws Exception {
		docDAO.updateDoc(doc);
	}

	@Override
	public Doc getDoc(String docID) throws Exception {
		DraftDocVO vo = new DraftDocVO();
		vo.setDocID(docID);
		
		return docDAO.selectDoc(vo);
	}

	@Override
	public int getDocCntByLabelId(String labelID) {
		DraftDocVO vo = new DraftDocVO();
		vo.setLbelId(labelID);
		
		return docDAO.selectDocCntByLabelId(vo);
	}

	@Override
	public int getDocCntByFormId(String formID) {
		DraftDocVO vo = new DraftDocVO();
		vo.setFormId(formID);
		
		return docDAO.selectDocCntByFormId(vo);
	}

	@Override
	public int getPassboxDocCnt(String deptId) throws Exception {
		PassboxDocVO vo = new PassboxDocVO();
		vo.setDraftDeptId(deptId);
		return docDAO.selectPassboxDocCnt(vo);
	}

	@Override
	public List<PassboxDocVO> getPassboxDocList(String deptId) throws Exception {
		PassboxDocVO vo = new PassboxDocVO();
		vo.setDraftDeptId(deptId);
		vo.setOrderColumn("2");
		vo.setOrderMethod("desc");
		
		return docDAO.selectPassboxDocs(vo);
	}

	@Override
	public List<PassboxDocVO> getPassboxDocList(String deptId, Pagination pagination) throws Exception {
		PassboxDocVO vo = new PassboxDocVO();
		vo.setDraftDeptId(deptId);
		vo.setOrderColumn(pagination.getOrderColumn());
		vo.setOrderMethod(pagination.getOrderMethod());
		
		return docDAO.selectPassboxDocs(vo, pagination);
	}
    
    public int getNextDocNumber(String year, String tableName, String deptId, String formId) {
        return docDAO.selectNextDocNumber(year, tableName, deptId, formId);
    }
    
    public String getNextDocId() throws Exception {
        return idGnrService.getNextStringId();
    }
    
    public String createDocBody(Doc orgDoc)throws Exception{
        FormVO form = formService.getForm(orgDoc.getFormId());
        if(form == null){
            throw new Exception("can not find the form info["+orgDoc.getFormId()+"]");
        }
        
        List<SignerVO> orgSignerList = signerService.getApprovalSignerList(orgDoc.getDocID());
        SignerVO draftSigner = null;
        for(int i=0; i<orgSignerList.size(); i++){
            SignerVO tmp = orgSignerList.get(i);
            if(ApprovalConstants.SIGNER_KIND_DRAFT.equals(tmp.getSignKind())){
                draftSigner = tmp;
                break;
            }
        }
        
        UserManageVO drafter = new UserManageVO();
        drafter.setEmplyrNm(draftSigner.getSignerName());
//        drafter.setDeptNm(draftSigner.getSignerDeptName());
//        drafter.setPosiNm(draftSigner.getSignerPositionName());
        //List<Recipient> orgRecipientList = DAOFactory.getRecipientDAO().getRecipientList(orgDoc.getDocID());
        
        String formHTML = formProcessor.mergeDocForDraftView(form, drafter, null);
        File docFile = PathUtil.createDocFile(orgDoc, formHTML);
        Map parameterMap = new HashMap();
        parameterMap.put("draft_title", orgDoc.getDocTitle());
        if ((orgDoc.getDocCd() != null) 
            && !ApprovalConstants.NULL_DOC_NUM.equals(orgDoc.getDocCd()) 
            && ApprovalConstants.NULL_ID_STRING.equals(orgDoc.getDocCd())){
            parameterMap.put("draft_docnum", orgDoc.getDocTitle());
        }
        
        docFile = formProcessor.mergeDoc(orgDoc, docFile, draftSigner, null, parameterMap);
        
        String docBody = FileUtils.readFileToString(docFile, "utf-8");
        
        return docBody;
    }
    
    public String getDocBody(Doc doc) throws Exception{
        File file = PathUtil.getDocPath(doc);
        
        return FileUtils.readFileToString(file);
    }
}