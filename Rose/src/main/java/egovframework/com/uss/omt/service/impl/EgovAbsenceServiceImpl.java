package egovframework.com.uss.omt.service.impl;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cop.ems.service.EgovSndngMailRegistService;
import egovframework.com.cop.ems.service.SndngMailVO;
import egovframework.com.uat.uia.service.EgovLoginService;
import egovframework.com.uss.omt.service.AbsenceVO;
import egovframework.com.uss.omt.service.EgovAbsenceService;
import egovframework.com.utl.fcc.service.EgovNumberUtil;
import egovframework.com.utl.fcc.service.EgovStringUtil;
import egovframework.com.utl.sim.service.EgovFileScrty;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

/**
 * 일반 로그인, 인증서 로그인을 처리하는 비즈니스 구현 클래스
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
 *  2014.12.08	이기하			암호화방식 변경(EgovFileScrty.encryptPassword)
 *  </pre>
 */
@Service("AbsenceService")
public class EgovAbsenceServiceImpl extends EgovAbstractServiceImpl implements EgovAbsenceService {

    @Resource(name="AbsenceDAO")
    private AbsenceDAO absenceDAO;


	@Override
	public void insertAbsence(AbsenceVO absence) throws Exception {
		absenceDAO.insertAbsence(absence);
	}


	@Override
	public void updateAbsence(AbsenceVO absence) throws Exception {
		absenceDAO.updateAbsence(absence);
	}


	@Override
	public void deleteAbsence(AbsenceVO absence) throws Exception {
		absenceDAO.deleteAbsence(absence);
	}


	@Override
	public AbsenceVO getAbsence(AbsenceVO absence) throws Exception {
		AbsenceVO absenceVO = absenceDAO.getAbsence(absence);
		return absenceVO;
	}


	@Override
	public List<AbsenceVO> getAbsenceList() throws Exception {
		List<AbsenceVO> absenceList = absenceDAO.getAbsenceList();
		return absenceList;
	}
}
