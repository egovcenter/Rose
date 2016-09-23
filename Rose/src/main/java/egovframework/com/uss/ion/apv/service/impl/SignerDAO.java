package egovframework.com.uss.ion.apv.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.com.uss.ion.apv.service.SignerVO;

@Repository("SignerDAO")
public class SignerDAO extends EgovComAbstractDAO {

	public List<SignerVO> selectApprovalSigners(SignerVO signer) throws Exception {
		return (List<SignerVO>) list("SignerDAO.selectApprovalSigners", signer);
	}

	public void insertApprovalSigner(SignerVO signer) throws Exception {
		insert("SignerDAO.insertApprovalSigner", signer);
	}
	
	public void updateApprovalSigner(SignerVO signer) throws Exception {
		update("SignerDAO.updateApprovalSigner", signer);
	}
	
	public void deleteApprovalSigner(SignerVO signer) throws Exception {
		delete("SignerDAO.deleteApprovalSigner", signer);
	}
	
	//TODO... make next document number
	public String selectNextDocNumber() {
	    return "";
	}
}
