package egovframework.com.uss.ion.apv.service;

import java.util.List;

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
public interface EgovSignerHistoryService {

	public List<SignerHistoryVO> getSignerHistoryList(String docID) throws Exception;

	public void insertSignerHistory(SignerHistoryVO signerHistory) throws Exception;

	public void deleteSignerHistory(SignerHistoryVO signerHistory) throws Exception;
	
    public void draft(SignerVO drafter) throws Exception;
    
    public void redraft(SignerVO drafter) throws Exception;
    
    public void reject(SignerVO drafter) throws Exception;
    
    public void receive(SignerVO drafter) throws Exception;
    
    public void approval(SignerVO drafter) throws Exception;
    
    public void hold(SignerVO holder) throws Exception;
    
}
