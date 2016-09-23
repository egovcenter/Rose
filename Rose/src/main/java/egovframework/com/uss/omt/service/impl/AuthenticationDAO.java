package egovframework.com.uss.omt.service.impl;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.com.uss.omt.service.AuthenticationVO;

@Repository("AuthenticationDAO")
public class AuthenticationDAO extends EgovComAbstractDAO {
	
	public void updateAuthenticationInf(AuthenticationVO authentication) throws Exception {
		int count = update("AuthenticationDAO.updateAuthenticationInf", authentication);

		if (count < 1){
			insertAuthenticationInf(authentication);
		}
	}
	
	public void insertAuthenticationInf(AuthenticationVO auth) throws Exception {
		insert("AuthenticationDAO.insertAuthenticationInf", auth);
	}
	
	public void deleteAuthenticationInfByUserId(AuthenticationVO auth) throws Exception {
		delete("AuthenticationDAO.deleteAuthenticationInfByUserId", auth);
	}
	
	public AuthenticationVO selectAuthenticationInfByUserId(AuthenticationVO auth) throws Exception {
		return (AuthenticationVO)select("AuthenticationDAO.selectAuthenticationInfByUserId", auth);
	}
}
