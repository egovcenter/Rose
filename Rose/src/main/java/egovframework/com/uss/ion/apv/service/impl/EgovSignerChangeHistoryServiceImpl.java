package egovframework.com.uss.ion.apv.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.com.uss.ion.apv.service.EgovSignerChangeHistoryService;
import egovframework.com.uss.ion.apv.service.EgovSignerHistoryService;
import egovframework.com.uss.ion.apv.service.SignerChangeHistoryVO;
import egovframework.com.uss.ion.apv.service.SignerHistoryVO;
import egovframework.com.uss.ion.apv.service.SignerVO;
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
@Service("EgovSignerChangeHistoryService")
public class EgovSignerChangeHistoryServiceImpl extends EgovAbstractServiceImpl implements EgovSignerChangeHistoryService {
	
	@Resource(name = "SignerChangeHistoryDAO")
	private SignerChangeHistoryDAO signerChangeHistoryDAO;
	   
	@Override
	public List<SignerChangeHistoryVO> getChangeHistory(String docID) throws Exception {
		SignerChangeHistoryVO vo = new SignerChangeHistoryVO();
		vo.setDocId(docID);
		
		return signerChangeHistoryDAO.selectHistory(vo);
	}

	@Override
	public void insertChangeHistory(SignerChangeHistoryVO history) throws Exception {
		signerChangeHistoryDAO.insertHistory(history);
	}
}
