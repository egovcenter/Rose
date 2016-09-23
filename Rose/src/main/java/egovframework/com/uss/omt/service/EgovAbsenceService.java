package egovframework.com.uss.omt.service;

import java.util.List;

import egovframework.com.cmm.LoginVO;

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
public interface EgovAbsenceService {

	public void insertAbsence(AbsenceVO absence) throws Exception;

	public void updateAbsence(AbsenceVO absence) throws Exception;

	public void deleteAbsence(AbsenceVO absence) throws Exception;

	public AbsenceVO getAbsence(AbsenceVO absence) throws Exception;
	
	public List<AbsenceVO> getAbsenceList() throws Exception;
}
