package egovframework.com.uss.ion.apv.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.com.uss.ion.apv.service.OpinionVO;
import egovframework.com.uss.ion.apv.service.SignerVO;

@Repository("OpinionDAO")
public class OpinionDAO extends EgovComAbstractDAO {

	public List<OpinionVO> selectOpinions(OpinionVO vo) throws Exception {
		return (List<OpinionVO>) list("OpinionDAO.selectOpinions", vo);
	}

	public void insertOpinion(OpinionVO vo) throws Exception {
		insert("OpinionDAO.insertOpinion", vo);
	}
	
	public int selectOpinionCnt(OpinionVO vo) throws Exception {
		return (int)select("OpinionDAO.selectOpinionCnt", vo);
	}
}
