package egovframework.com.uss.ion.apv.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.com.uss.ion.apv.service.EgovSignerHistoryService;
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
@Service("EgovSignerHistoryService")
public class EgovSignerHistoryServiceImpl extends EgovAbstractServiceImpl implements EgovSignerHistoryService {
	
	@Resource(name = "SignerHistoryDAO")
	private SignerHistoryDAO signerHistoryDAO;

    @Resource(name = "egovSignerHistoryIdGnrService")
    private EgovIdGnrService idgenService;
	   
	@Override
	public List<SignerHistoryVO> getSignerHistoryList(String docID) throws Exception {
		SignerHistoryVO vo = new SignerHistoryVO();
		vo.setDocID(docID);
		
		return signerHistoryDAO.selectSignerHistorys(vo);
	}

	@Override
	public void insertSignerHistory(SignerHistoryVO history) throws Exception {
		signerHistoryDAO.insertSignerHistory(history);
	}

	@Override
	public void deleteSignerHistory(SignerHistoryVO history) throws Exception {
		signerHistoryDAO.deleteSignerHistory(history);
	}

	
	@Override
    public void draft(SignerVO drafter) throws Exception {
        String signerHistoryID = idgenService.getNextStringId();
        
        SignerHistoryVO signerHistory = new SignerHistoryVO(signerHistoryID, drafter);
        
        insertSignerHistory(signerHistory);
        
    }

	@Override
    public void redraft(SignerVO redrafter) throws Exception {
        String signerHistoryID = idgenService.getNextStringId();
        
        SignerHistoryVO signerHistory = new SignerHistoryVO(signerHistoryID, redrafter);
        
        insertSignerHistory(signerHistory);
    }

	@Override
    public void approval(SignerVO approver) throws Exception {
        String signerHistoryID = idgenService.getNextStringId();
        
        SignerHistoryVO signerHistory = new SignerHistoryVO(signerHistoryID, approver);
        
        insertSignerHistory(signerHistory);
    }

	@Override
    public void reject(SignerVO rejector) throws Exception {
        String signerHistoryID = idgenService.getNextStringId();
        
        SignerHistoryVO signerHistory = new SignerHistoryVO(signerHistoryID, rejector);
        
        insertSignerHistory(signerHistory);
    }

    @Override
    public void hold(SignerVO holder) throws Exception {
        String signerHistoryID = idgenService.getNextStringId();

        SignerHistoryVO signerHistory = new SignerHistoryVO(signerHistoryID, holder);

        insertSignerHistory(signerHistory);
    }

	@Override
    public void receive(SignerVO receiver) throws Exception {
        String signerHistoryID = idgenService.getNextStringId();
        
        SignerHistoryVO signerHistory = new SignerHistoryVO(signerHistoryID, receiver);
        
        insertSignerHistory(signerHistory);
    }
}
