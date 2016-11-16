package egovframework.com.uss.ion.apv.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import egovframework.com.uss.ion.apm.service.FormVO;
import egovframework.com.uss.ion.apv.ApprovalContext;
import egovframework.com.uss.ion.apv.Doc;
import egovframework.com.uss.umt.service.UserManageVO;

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
public interface EgovSignerService {
	public List<SignerVO> getApprovalSignerList(String docID) throws Exception;
    public List<SignerVO> getDefaultSignerList(FormVO form, UserManageVO drafter) throws Exception;
    public List<SignerVO> getDefaultReceiveSignerList(FormVO form, UserManageVO drafter) throws Exception;
    public List<SignerVO> getSignerListForRedraft(Doc doc) throws Exception;
    public String getNextSignerId() throws Exception;
    public String getDocForView(Doc doc) throws Exception;
    public String getWriteDocBody(Doc doc) throws Exception;
    public Doc registerIncoming(RegisterIncomingVO incoming, UserManageVO user) throws Exception;
    public Doc registerInternal(RegisterInternalVO internal, UserManageVO user) throws Exception;
    public List<String> getPreAttachedFileIdList(String preAttachedFileListJson) throws Exception;
    
    public File approveForDraft(UserManageVO drafter, ApprovalContext approvalContext, String docBody, Map parameterMap, String preAttachedFileListJson) throws Exception;
    public String createDraftDoc(FormVO form, UserManageVO drafter, List<SignerVO> signerList, List<RecipientVO> recipientList) throws Exception;
    public String mergeSignTable(Doc doc, String signTableId, List<SignerVO> signerList) throws Exception;
    public File approveForApprove(UserManageVO signer, ApprovalContext approvalContext, String docBody, Map parameterMap, String preAttachedFileListJson) throws Exception;
    public File approveForReject(UserManageVO signer, Doc doc, String opinion) throws Exception;
    public File approveForReceive(UserManageVO signer, ApprovalContext approvalContext, String docBody,Map parameterMap)throws Exception;
    public File approveForHold(UserManageVO signer, Doc doc, String opinion) throws Exception;
    
	public void insertApprovalSigner(SignerVO signer) throws Exception;
	public void updateApprovalSigner(SignerVO signer) throws Exception;
	public void deleteApprovalSigner(SignerVO signer) throws Exception;
}
