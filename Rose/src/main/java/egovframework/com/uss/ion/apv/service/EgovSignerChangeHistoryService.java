package egovframework.com.uss.ion.apv.service;

import java.util.List;

public interface EgovSignerChangeHistoryService {

	public List<SignerChangeHistoryVO> getChangeHistory(String docId) throws Exception;
   
	public void insertChangeHistory(SignerChangeHistoryVO history) throws Exception;
}
