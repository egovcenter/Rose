package egovframework.com.uss.ion.apm.service;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import egovframework.com.uss.umt.service.UserManageVO;

/**
 * @Class Name : EgovAttachFileService.java
 * @Description : Service for managing attached file information
 * @Modification Information
 *
 *    Date         Author	   Content
 *   -----------  -------     -------------------
 *    2016.02.04   paul        initial
 *
 * @since 2016.02.04.
 * @version
 * @see
 *
 */
public interface EgovLabelService {

	public List<LabelVO> getLabelTreeOfDeptID(String deptId) throws Exception;
	
	public LabelVO getLabel(String labelID) throws Exception;	
	
	public LabelVO getRootLabel(String deptId) throws Exception;
	
	public LabelVO insertRootLabel(String orgId) throws Exception;
	
	public LabelVO insertLabel(LabelVO label, String targetLabelID, String targetPosition,  UserManageVO user) throws Exception;
	
    public void updateLabel(LabelVO label, String targetLabelID, String targetPosition, UserManageVO user) throws Exception;

	public void deleteLabel(String labelID) throws Exception;
	
	public void shiftSeq(String parentID, int baseSeq) throws Exception;
	
	public int selectMaxSeq(String parentID) throws Exception;
	
	public ModelAndView getLabelTree(String userId, String deptId) throws Exception;
}
