package egovframework.com.uss.ion.apv.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.com.uss.ion.apv.Recipient;
import egovframework.com.uss.ion.apv.service.RecipientVO;

@Repository("RecipientDAO")
public class RecipientDAO extends EgovComAbstractDAO {

	public List<RecipientVO> selectRecipients(RecipientVO vo) throws Exception {
		return (List<RecipientVO>)list("RecipientDAO.selectRecipients", vo);
	}
	
	public RecipientVO selectRecipient(RecipientVO vo) throws Exception {
		return (RecipientVO)select("RecipientDAO.selectRecipient", vo);
	}
	
	public void insertRecipient(RecipientVO vo) throws Exception {
		insert("RecipientDAO.insertRecipient", vo);
	}
	
	public void updateRecipient(RecipientVO vo) throws Exception {
		update("RecipientDAO.updateRecipient", vo);
	}
	
    public void updateRecipient(List<RecipientVO> vo) throws Exception {
        Map<String, Object> map = new HashMap();
        map.put("recpList", vo);
        
        update("RecipientDAO.updateRecipientByIdList", map);
    }
	   
	public void deleteRecipient(RecipientVO vo) throws Exception {
		delete("RecipientDAO.deleteRecipient", vo);
	}
}
