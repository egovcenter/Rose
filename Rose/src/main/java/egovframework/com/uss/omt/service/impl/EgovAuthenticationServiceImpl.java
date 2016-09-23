package egovframework.com.uss.omt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.com.cmm.LoginVO;
import egovframework.com.uss.omt.service.AbsenceVO;
import egovframework.com.uss.omt.service.AuthenticationVO;
import egovframework.com.uss.omt.service.EgovAbsenceService;
import egovframework.com.uss.omt.service.EgovAuthenticationService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 일반 로그인, 인증서 로그인을 처리하는 비즈니스 인터페이스 클래스
 * @author 공통서비스 개발팀 박지욱
 * @since 2009.03.06
 * @version 1.0
 * @see
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2009.03.06  박지욱          최초 생성 
 *  2011.08.26  서준식          EsntlId를 이용한 로그인 추가
 *  </pre>
 */
@Service("AuthenticationService")
public class EgovAuthenticationServiceImpl extends EgovAbstractServiceImpl implements EgovAuthenticationService  {


    @Resource(name="AuthenticationDAO")
    private AuthenticationDAO authenticationDAO;

	@Override
	public void updateAuthenticationInf(AuthenticationVO auth) throws Exception {
		authenticationDAO.updateAuthenticationInf(auth);
	}

	@Override
	public void insertAuthenticationInf(AuthenticationVO auth) throws Exception {
		authenticationDAO.insertAuthenticationInf(auth);
		
	}

	@Override
	public void deleteAuthenticationInfByUserId(AuthenticationVO auth) throws Exception {
		authenticationDAO.deleteAuthenticationInfByUserId(auth);		
	}

	@Override
	public AuthenticationVO getAuthenticationInfByUserId(AuthenticationVO auth) throws Exception {
		AuthenticationVO authVO = authenticationDAO.selectAuthenticationInfByUserId(auth);
		
		return authVO;
	}

}
