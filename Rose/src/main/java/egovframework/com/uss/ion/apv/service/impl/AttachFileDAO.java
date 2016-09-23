package egovframework.com.uss.ion.apv.service.impl;

import java.util.Iterator;
import java.util.List;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.com.uss.ion.apv.service.AttachFileVO;

import org.springframework.stereotype.Repository;

/**
 * @Class Name : AttachFileDAO.java
 * @Description : Data access class for managing attached file information
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
@Repository("AttachFileDAO")
public class AttachFileDAO extends EgovComAbstractDAO {

	/**
	 * Retrieve list of attached file information 
	 * @param attachFileInf 
	 * @return List<AttachFileVO>
	 * @throws Exception
	 */
	public List<AttachFileVO> selectAttachFileListByDocId(AttachFileVO attachFileInf) throws Exception {
		return (List<AttachFileVO>) list("FileAttachDAO.selectAttachFileListByDocId", attachFileInf);
	}
	
	/**
	 * Retrieve attached file information with given attach id
	 * @param fileInf
	 * @return AttachFileVO
	 * @throws Exception
	 */
	public AttachFileVO selectAttachFileByAttachId(AttachFileVO fileInf) throws Exception {
	    AttachFileVO vo = (AttachFileVO) select("FileAttachDAO.selectAttachFileByAttachId", fileInf);
		return vo;
	}

	/**
	 * Insert information for attached file
	 * @param fileInf
	 * @throws Exception
	 */
	public void insertAttachFile(AttachFileVO fileInf) throws Exception {
		insert("FileAttachDAO.insertAttachFile", fileInf);
	}
	
	/**
	 * Delete information for attached file
	 * @param fileInf
	 * @throws Exception
	 */
	public void deleteAttachFile(AttachFileVO fileInf) throws Exception {
		delete("FileAttatchDAO.deleteAttachFile", fileInf);
	}
	
}
