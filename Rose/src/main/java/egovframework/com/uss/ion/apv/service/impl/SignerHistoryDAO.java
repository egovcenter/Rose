package egovframework.com.uss.ion.apv.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.com.uss.ion.apv.service.SignerHistoryVO;

@Repository("SignerHistoryDAO")
public class SignerHistoryDAO extends EgovComAbstractDAO {

	public List<SignerHistoryVO> selectSignerHistorys(SignerHistoryVO vo) throws Exception {
		return (List<SignerHistoryVO>) list("SignerHistoryDAO.selectSignerHistorys", vo);
	}

	public void insertSignerHistory(SignerHistoryVO vo) throws Exception {
		insert("SignerHistoryDAO.insertSignerHistory", vo);
	}
	
	public void deleteSignerHistory(SignerHistoryVO vo) throws Exception {
		delete("SignerHistoryDAO.deleteSignerHistory", vo);
	}
}
