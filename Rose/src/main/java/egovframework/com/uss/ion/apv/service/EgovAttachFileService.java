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
public interface EgovAttachFileService {

	/**
	 * Retrieve attached file information with given attach id
	 * @param fileInf
	 * @return AttachFileVO
	 * @throws Exception
	 */
	public List<AttachFileVO> getAttachFileListByDocId(String docID) throws Exception;
	
	/**
	 * Retrieve list of attached file information 
	 * @param attachFileInf 
	 * @return List<AttachFileVO>
	 * @throws Exception
	 */
	public AttachFileVO getAttachFileByAttachId(AttachFileVO fileInf) throws Exception;
	
	/**
	 * Insert information for attached file
	 * @param fileInf
	 * @throws Exception
	 */
	public void insertAttachFile(AttachFileVO fileInf) throws Exception;
	
	/**
	 * Delete information for attached file
	 * @param fileInf
	 * @throws Exception
	 */
	public void deleteAttachFile(AttachFileVO fileInf) throws Exception;

	public String getNextAttatchFileId() throws Exception;
}
