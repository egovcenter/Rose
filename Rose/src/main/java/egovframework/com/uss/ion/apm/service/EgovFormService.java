package egovframework.com.uss.ion.apm.service;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import egovframework.com.uss.ion.apu.Pagination;

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
public interface EgovFormService {

	public List<FormVO> getApprovalFormList(String deptId, String rootDeptID, String buttonType) throws Exception;
	
	public List<FormVO> getFormList(String compID, Pagination pagination) throws Exception;

	public FormVO getForm(String formID) throws Exception;	
	
	public FormVO insertForm(FormVO form, int formType) throws Exception ;
	
	public FormVO updateForm(FormVO form, int formType) throws Exception;

	public void deleteForm(String formID) throws Exception;
	
	public void deleteFormList(String[] formIdArray) throws Exception;

	public int getTotalFormCount(String compID, Pagination pagination) throws Exception;
	
    public String getNextFormId() throws Exception;
}
