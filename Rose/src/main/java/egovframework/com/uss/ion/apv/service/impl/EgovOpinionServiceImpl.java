package egovframework.com.uss.ion.apv.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.com.uss.ion.apu.Pagination;
import egovframework.com.uss.ion.apv.Doc;
import egovframework.com.uss.ion.apv.service.EgovAttachFileService;
import egovframework.com.uss.ion.apv.service.EgovOpinionService;
import egovframework.com.uss.ion.apv.service.EgovSignerService;
import egovframework.com.uss.ion.apv.service.OpinionVO;
import egovframework.com.uss.ion.apv.service.SignerVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

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
@Service("EgovOpionioService")
public class EgovOpinionServiceImpl extends EgovAbstractServiceImpl implements EgovOpinionService {
	
	@Resource(name = "OpinionDAO")
	private OpinionDAO opinionDAO;

	@Override
	public void insertOpinion(OpinionVO opinion) throws Exception {
		opinionDAO.insertOpinion(opinion);
	}

	@Override
	public int getOpinionCnt(String docID) throws Exception {
		OpinionVO vo = new OpinionVO();
		vo.setDocID(docID);
		
		return opinionDAO.selectOpinionCnt(vo);
	}

	@Override
	public List<OpinionVO> getOpinionList(String docID) throws Exception {
		OpinionVO vo = new OpinionVO();
		vo.setDocID(docID);
		
		return opinionDAO.selectOpinions(vo);
	}


}
