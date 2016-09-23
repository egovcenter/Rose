package egovframework.com.uss.ion.apv.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.com.uss.ion.apv.ApprovalConstants;
import egovframework.com.uss.ion.apv.Doc;
import egovframework.com.uss.ion.apv.service.EgovRecipientService;
import egovframework.com.uss.ion.apv.service.RecipientVO;
import egovframework.com.uss.ion.apv.service.RegisterIncomingVO;
import egovframework.com.uss.umt.service.UserManageVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.cmmn.exception.FdlException;
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
@Service("EgovRecipientService")
public class EgovRecipientServiceImpl extends EgovAbstractServiceImpl implements EgovRecipientService {
	
	@Resource(name = "RecipientDAO")
	private RecipientDAO recipientDAO;

	@Resource(name = "egovRecpIdGnrService")
	private EgovIdGnrService idgenService;
	
	@Override
	public List<RecipientVO> getRecipientList(String docID) throws Exception {
		RecipientVO vo = new RecipientVO();
		vo.setDocId(docID);
		
		return recipientDAO.selectRecipients(vo);
	}

	@Override
	public RecipientVO getRecipient(String docID, String deptId) throws Exception {
		RecipientVO vo = new RecipientVO();
		vo.setDocId(docID);
		vo.setDeptId(deptId);
		
		return recipientDAO.selectRecipient(vo);
	}

	@Override
	public void insertRecipient(RecipientVO recipient) throws Exception {
		recipientDAO.insertRecipient(recipient);
	}

	@Override
	public void updateRecipient(RecipientVO recipient) throws Exception {
		recipientDAO.updateRecipient(recipient);
	}

	@Override
	public void updateRecipient(List<RecipientVO> recipientList) throws Exception {
	    recipientDAO.updateRecipient(recipientList);
	}
	
	@Override
	public void deleteRecipient(RecipientVO recipient) throws Exception {
		recipientDAO.deleteRecipient(recipient);
	}

	@Override
    public List<RecipientVO> createRecipient(RegisterIncomingVO incoming, UserManageVO user) throws Exception {
        List<RecipientVO> recipientList = new ArrayList<RecipientVO>();
        String recpId =  idgenService.getNextStringId();
        
        RecipientVO recipient = new RecipientVO();
        recipient.setRecpId(recpId);
        recipient.setRecpSeq(1);            
        recipient.setDeptId(incoming.getRegisterDeptID());
        recipient.setRecpDeptNm(incoming.getRegisterDeptName());
        recipient.setDocId(incoming.getDocID());
        recipient.setRecpSendType(ApprovalConstants.SEND_TYPE_AUTO);
        recipient.setRecpInnerFlag(ApprovalConstants.FLAG_YES);
        recipient.setRecpSendUserId(ApprovalConstants.NULL_ID_STRING);
        recipient.setRecpSendUserNm(incoming.getSenderName());
        recipient.setRecpSendDt(new Date());
        recipient.setRecpArrivalDt(new Date());
        recipient.setRecpDocType(ApprovalConstants.RECP_DOC_TYPE_PASS);
        
        recipientList.add(recipient);
//        LOGGER.debug("createRecipient recipient[{}]", recipient);
        
        return recipientList;
    }
	

    public void passDoc(Doc orgDoc, RecipientVO recipient, List<RecipientVO> list, UserManageVO user) throws Exception {
        recipient.setRecpMethod(ApprovalConstants.RECP_METHOD_PASS);
        recipient.setRecpRecpUserId(user.getUniqId());
        recipient.setRecpRecpUserNm(user.getEmplyrNm());
        recipient.setRecpRecpDt(new Date());
        recipient.setRecpFinishUserId(user.getUniqId());
        recipient.setRecpFinishUserNm(user.getEmplyrNm());
        recipient.setRecpFinishDt(new Date());
        
        for(int i=0; i<list.size() ;i++){
            RecipientVO tmp = (RecipientVO)list.get(i);
            tmp.setSentDate(new Date());
            tmp.setRecpSendDt(new Date());
            tmp.setRecpArrivalDt(new Date());
            tmp.setRecpSendUserId(user.getUniqId());
            tmp.setRecpSendUserNm(user.getEmplyrNm());
            tmp.setRecpDocType(ApprovalConstants.RECP_DOC_TYPE_PASS);
            tmp.setRecpMethod(ApprovalConstants.RECP_METHOD_PASS);
            tmp.setRecpPassDeptID(recipient.getDeptId());
        }
        
        recipientDAO.updateRecipient(recipient);
        
        for(int i=0; i<list.size(); i++){
            RecipientVO tmp = (RecipientVO)list.get(i);
            recipientDAO.insertRecipient(tmp);
        }
    }
    
    public String getNextRecipientId() throws Exception {
        return idgenService.getNextStringId();
    }
}
