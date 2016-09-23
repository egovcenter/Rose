package egovframework.com.uss.omt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.com.uss.omt.service.EgovPositionService;
import egovframework.com.uss.omt.service.PositionVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

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
@Service("EgovPositionService")
public class EgovPositionServiceImpl extends EgovAbstractServiceImpl implements EgovPositionService  {


    @Resource(name="PositionDAO")
    private PositionDAO positionDAO;

    @Resource(name="egovPositionIdGnrService")
    private EgovIdGnrService idGnrService;
    
	@Override
	public void insertPosition(PositionVO posi) throws Exception {
	    String nextPositionId = idGnrService.getNextStringId();
	    posi.setPosiId(nextPositionId);
	    
		positionDAO.insertPosition(posi);
	}

	@Override
	public void updatePosition(PositionVO posi) throws Exception {
		positionDAO.updatePosition(posi);		
	}

	@Override
	public void deletePosition(String posiId) throws Exception {
		PositionVO posiVO = new PositionVO();
		posiVO.setPosiId(posiId);
		
		positionDAO.deletePosition(posiVO);
	}

	@Override
	public PositionVO getPosition(String posiId) throws Exception {
		PositionVO posiVO = new PositionVO();
		posiVO.setPosiId(posiId);
		
		return positionDAO.selectPosition(posiVO);
	}

	@Override
	public List<PositionVO> getPositionListByDeptId(String deptId) throws Exception {
		PositionVO posiVO = new PositionVO();
		posiVO.setDeptId(deptId);
		
		List<PositionVO> list = positionDAO.selectPositionList(posiVO);
		if (list == null) {
		    return null;
		}
		
		return list;
	}

	@Override
	public int getPositionCnt(String deptId) throws Exception {
		PositionVO posiVO = new PositionVO();
		posiVO.setDeptId(deptId);
		
		return positionDAO.selectPositionCnt(posiVO);
	}
}
