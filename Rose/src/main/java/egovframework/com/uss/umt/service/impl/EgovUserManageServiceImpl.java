package egovframework.com.uss.umt.service.impl;

import java.util.List;

import egovframework.com.uss.umt.service.EgovUserManageService;
import egovframework.com.uss.umt.service.UserDefaultVO;
import egovframework.com.uss.umt.service.UserManageVO;
import egovframework.com.utl.sim.service.EgovFileScrty;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

/**
 * 사용자관리에 관한 비지니스 클래스를 정의한다.
 * @author 공통서비스 개발팀 조재영
 * @since 2009.04.10
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.04.10  조재영          최초 생성
 *   2014.12.08	 이기하			암호화방식 변경(EgovFileScrty.encryptPassword)
 *
 * </pre>
 */
@Service("EgovUserManageService")
public class EgovUserManageServiceImpl extends EgovAbstractServiceImpl implements EgovUserManageService {

	/** userManageDAO */
	@Resource(name="userManageDAO")
	private UserManageDAO userManageDAO;

//	/** mberManageDAO */
//	@Resource(name="mberManageDAO")
//	private MberManageDAO mberManageDAO;
//
//	/** entrprsManageDAO */
//	@Resource(name="entrprsManageDAO")
//	private EntrprsManageDAO entrprsManageDAO;

	/** egovUsrCnfrmIdGnrService */
	@Resource(name="egovUsrCnfrmIdGnrService")
	private EgovIdGnrService idgenService;

	/**
	 * 입력한 사용자아이디의 중복여부를 체크하여 사용가능여부를 확인
	 * @param checkId 중복여부 확인대상 아이디
	 * @return 사용가능여부(아이디 사용회수 int)
	 * @throws Exception
	 */
	@Override
	public int checkIdDplct(String checkId) {
		return userManageDAO.checkIdDplct(checkId);
	}

	/**
	 * 화면에 조회된 사용자의 정보를 데이터베이스에서 삭제
	 * @param checkedIdForDel 삭제대상 업무사용자아이디
	 * @throws Exception
	 */
	@Override
	public void deleteUser(String uniqId) {
		userManageDAO.deleteUser(uniqId);
	}

    @Override
    public void deleteUserList(String idList) {
        String[] delId = idList.split(",");

        for (int i = 0; i < delId.length; i++) {
            userManageDAO.deleteUser(delId[i]);
        }
    }

	/**
	 * @param userManageVO 업무사용자 등록정보
	 * @return result 등록결과
	 * @throws Exception
	 */
	@Override
	public String insertUser(UserManageVO userManageVO) throws Exception {
		//고유아이디 셋팅
		String uniqId = idgenService.getNextStringId();
		userManageVO.setUniqId(uniqId);
		userManageDAO.insertUser(userManageVO);
		return uniqId;
	}

	/**
	 * 기 등록된 사용자 중 검색조건에 맞는 사용자의 정보를 데이터베이스에서 읽어와 화면에 출력
	 * @param uniqId 상세조회대상 업무사용자 아이디
	 * @return userManageVO 업무사용자 상세정보
	 * @throws Exception
	 */
	@Override
	public UserManageVO getUser(String uniqId) {
	    UserManageVO vo = new UserManageVO();
	    vo.setUniqId(uniqId);
	    
		UserManageVO userManageVO = getUser(vo);
		return userManageVO;
	}
	
	@Override
	public UserManageVO getUser(UserManageVO userSearchVO) {
		UserManageVO userManageVO = userManageDAO.selectUser(userSearchVO);
		return userManageVO;
	}
    
	/**
	 * 기 등록된 특정 사용자의 정보를 데이터베이스에서 읽어와 화면에 출력
	 * @param userSearchVO 검색조건
	 * @return List<UserManageVO> 업무사용자 목록정보
	 * @throws Exception
	 */
	@Override
	public List<?> getUserList(UserDefaultVO userSearchVO) {
		List<?> result = userManageDAO.selectUserList(userSearchVO);
		return result;
	}

	@Override
    public List<UserManageVO> getUserList(UserManageVO userSearchVO) throws Exception {
        List<UserManageVO> result = userManageDAO.selectUserInfList(userSearchVO);
        return result;
    }
	
	@Override
    public List<UserManageVO> getUserList(String emplyrId) throws Exception {
	    UserManageVO searchVO = new UserManageVO();
	    searchVO.setEmplyrId(emplyrId);
	    
        List<UserManageVO> result = userManageDAO.selectUserInfList(searchVO);
        return result;
    }
	/**
	 * 기 등록된 특정 사용자목록의 전체수를 확인
	 * @param userSearchVO 검색조건
	 * @return 총사용자갯수(int)
	 * @throws Exception
	 */
	@Override
	public int getUserCnt(UserDefaultVO userSearchVO) {
		return userManageDAO.selectUserListTotCnt(userSearchVO);
	}

	/**
	 * 화면에 조회된 사용자의 기본정보를 수정하여 항목의 정합성을 체크하고 수정된 데이터를 데이터베이스에 반영
	 * @param userManageVO 업무사용자 수정정보
	 * @throws Exception
	 */
	@Override
	public void updateUser(UserManageVO userManageVO) throws Exception {
		userManageDAO.updateUser(userManageVO);
	}

	/**
	 * 사용자정보 수정시 히스토리 정보를 추가
	 * @param userManageVO 업무사용자 수정정보
	 * @return result 등록결과
	 * @throws Exception
	 */
	@Override
	public String insertUserHistory(UserManageVO userManageVO) {
		return userManageDAO.insertUserHistory(userManageVO);
	}

	/**
	 * 업무사용자 암호 수정
	 * @param userManageVO 업무사용자 수정정보(비밀번호)
	 * @throws Exception
	 */
	@Override
	public void updatePassword(UserManageVO userManageVO) {
		userManageDAO.updatePassword(userManageVO);
	}

	/**
	 * 사용자가 비밀번호를 기억하지 못할 때 비밀번호를 찾을 수 있도록 함
	 * @param passVO 업무사용자 암호 조회조건정보
	 * @return userManageVO 업무사용자 암호정보
	 * @throws Exception
	 */
	@Override
	public UserManageVO getPassword(UserManageVO passVO) {
		UserManageVO userManageVO = userManageDAO.selectPassword(passVO);
		return userManageVO;
	}

    @Override
    public List<UserManageVO> getUserList(List<String> userIDList) {
        // TODO MIGRATION_DATABASE
        return null;
    }
    
    @Override
    public int getUserCntByPosition(String positionId) throws Exception {
        return userManageDAO.selectUserCntByPosition(positionId);
    }
    
    @Override
    public int getUserCntByDuty(String dutyId) throws Exception {
        return userManageDAO.selectUserCntByDuty(dutyId);
    }

}