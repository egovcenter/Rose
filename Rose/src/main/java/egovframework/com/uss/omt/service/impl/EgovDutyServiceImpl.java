package egovframework.com.uss.omt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.com.cmm.LoginVO;
import egovframework.com.uss.omt.service.AbsenceVO;
import egovframework.com.uss.omt.service.AuthenticationVO;
import egovframework.com.uss.omt.service.DutyVO;
import egovframework.com.uss.omt.service.EgovAbsenceService;
import egovframework.com.uss.omt.service.EgovAuthenticationService;
import egovframework.com.uss.omt.service.EgovDutyService;
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
@Service("EgovDutyService")
public class EgovDutyServiceImpl extends EgovAbstractServiceImpl implements EgovDutyService  {


    @Resource(name="DutyDAO")
    private DutyDAO dutyDAO;

	@Override
	public void insertDuty(DutyVO duty) throws Exception {
		dutyDAO.insertDuty(duty);
	}

	@Override
	public void updateDuty(DutyVO duty) throws Exception {
		dutyDAO.updateDuty(duty);
	}

	@Override
	public void deleteDuty(String id) throws Exception {
		DutyVO dutyVO = new DutyVO();
		dutyVO.setDutyId(id);
		
		dutyDAO.deleteDuty(dutyVO);
	}

	@Override
	public DutyVO getDuty(String dutyId) throws Exception {
		DutyVO vo = new DutyVO();
		vo.setDutyId(dutyId);
		
		DutyVO dutyVO = dutyDAO.selectDuty(vo);
		
		return dutyVO;
	}

	@Override
	public List<DutyVO> getDutyList(String deptId) throws Exception {
		List<DutyVO> dutyList = dutyDAO.selectDutyList(deptId);
		
		return dutyList;
	}

	@Override
	public int getDutyCnt(String deptId) throws Exception {
		int cnt = dutyDAO.selectDutyCnt(deptId);
		
		return cnt;
	}



}
