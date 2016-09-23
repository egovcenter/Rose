package egovframework.com.uss.omt.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.com.uss.omt.service.PositionVO;

@Repository("PositionDAO")
public class PositionDAO extends EgovComAbstractDAO {
	
	public void insertPosition(PositionVO posi) {
		insert("PositionDAO.insertPosition", posi);
	}
	
	public void updatePosition(PositionVO posi) {
		update("PositionDAO.updatePosition", posi);
	}
	
	public void deletePosition(PositionVO posi) {
		delete("PositionDAO.deletePosition", posi);
	}

	public PositionVO selectPosition(PositionVO posi) {
		return (PositionVO)select("PositionDAO.selectPosition", posi);
	}
	
	public List<PositionVO> selectPositionList(PositionVO posi) {
		return (List<PositionVO>) list("PositionDAO.selectPositionList", posi);
	}
	
	public int selectPositionCnt(PositionVO posi) {
		return (Integer)select("PositionDAO.selectPositionCnt", posi);
	}
}
