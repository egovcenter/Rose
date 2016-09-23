package egovframework.com.uss.ion.apv.service;

import java.util.List;

import egovframework.com.uss.ion.apu.Pagination;
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
public interface EgovRecipientService {
	public List<RecipientVO> getRecipientList(String docID) throws Exception;
	
	public RecipientVO getRecipient(String docID, String deptId) throws Exception;
	   
    public String getNextRecipientId() throws Exception;
    
	public void insertRecipient(RecipientVO recipient) throws Exception;
	
	public void updateRecipient(RecipientVO recipient) throws Exception;
	
	public void updateRecipient(List<RecipientVO> recipient) throws Exception;
    
	public void deleteRecipient(RecipientVO recipient) throws Exception;
	
	public List<RecipientVO> createRecipient(RegisterIncomingVO incoming, UserManageVO user) throws Exception;
	
	public void passDoc(Doc orgDoc, RecipientVO recipient, List<RecipientVO> list, UserManageVO user) throws Exception;

}
