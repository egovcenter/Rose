package egovframework.com.uss.ion.apv.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.com.uss.ion.apv.service.SignerChangeHistoryVO;
import egovframework.com.uss.ion.apv.service.SignerHistoryVO;

@Repository("SignerChangeHistoryDAO")
public class SignerChangeHistoryDAO extends EgovComAbstractDAO {

	public List<SignerChangeHistoryVO> selectHistory(SignerChangeHistoryVO vo) throws Exception {
		return (List<SignerChangeHistoryVO>) list("SignerChangeHistoryDAO.selectHistory", vo);
	}

	public void insertHistory(SignerChangeHistoryVO vo) throws Exception {
		insert("SignerChangeHistoryDAO.insertHistory", vo);
	}
	
}
