package egovframework.com.uss.ion.apv.service.impl;

import java.util.List;

import egovframework.com.uss.ion.apv.service.AttachFileVO;
import egovframework.com.uss.ion.apv.service.EgovAttachFileService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

/**
 * @Class Name : EgovFileMngServiceImpl.java
 * @Description : 파일정보의 관리를 위한 구현 클래스
 * @Modification Information
 *
 *    수정일       수정자         수정내용
 *    -------        -------     -------------------
 *    2009. 3. 25.     이삼섭    최초생성
 *
 * @author 공통 서비스 개발팀 이삼섭
 * @since 2009. 3. 25.
 * @version
 * @see
 *
 */
@Service("EgovAttachFileService")
public class EgovAttachFileServiceImpl extends EgovAbstractServiceImpl implements EgovAttachFileService {

	@Resource(name = "AttachFileDAO")
	private AttachFileDAO attachFileDAO;

	@Resource (name = "egovFileIdGnrService")
	private EgovIdGnrService idGnrService;
	
	
	public List<AttachFileVO> getAttachFileListByDocId(String docID) throws Exception {
		AttachFileVO fileInf = new AttachFileVO();
		fileInf.setDocID(docID);
		return attachFileDAO.selectAttachFileListByDocId(fileInf);
	}
	
	public AttachFileVO getAttachFileByAttachId(AttachFileVO fileInf) throws Exception {
		return attachFileDAO.selectAttachFileByAttachId(fileInf);
	}
	
	public void insertAttachFile(AttachFileVO fileInf) throws Exception {
		attachFileDAO.insertAttachFile(fileInf);
	}
	
	public void deleteAttachFile(AttachFileVO fileInf) throws Exception {
		attachFileDAO.deleteAttachFile(fileInf);
	}
	
	public String getNextAttatchFileId() throws Exception {
	    return idGnrService.getNextStringId();
	}
	
}
