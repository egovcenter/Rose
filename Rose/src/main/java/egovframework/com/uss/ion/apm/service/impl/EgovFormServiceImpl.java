package egovframework.com.uss.ion.apm.service.impl;

import java.text.MessageFormat;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import egovframework.com.uss.ion.apm.service.EgovFormService;
import egovframework.com.uss.ion.apm.service.EgovLabelService;
import egovframework.com.uss.ion.apm.service.FormVO;
import egovframework.com.uss.ion.apm.service.LabelVO;
import egovframework.com.uss.ion.apu.ApprovalException;
import egovframework.com.uss.ion.apu.Pagination;
import egovframework.com.uss.omt.service.DeptVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

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
 */
@Service("EgovFormService")
public class EgovFormServiceImpl extends EgovAbstractServiceImpl implements EgovFormService {
	
	@Resource(name = "FormDAO")
	private FormDAO		formDAO;

	@Resource (name = "egovFormIdGnrService")
	private EgovIdGnrService idGnrService;
	
	public List<FormVO> getApprovalFormList(String deptId, String rootDeptID, String buttonType) throws Exception {

        DeptVO vo = new DeptVO();

        vo.setDeptId(deptId);
        vo.setBaseOrgnztId(rootDeptID);

        List<FormVO> formList = null;
        
        if (buttonType.equalsIgnoreCase("draft")) {
            formList = formDAO.selectDraftFormList(vo);
        } else if (buttonType.equalsIgnoreCase("register")) {
            formList = formDAO.selectRegisterFormList(vo);
        }

        return formList;
	}
	
	public List<FormVO> getFormList(String compID, Pagination pagination) throws Exception {
	    FormVO vo = new FormVO();
	    vo.setOrgId(compID);
	    vo.setOrderColumn(pagination.getOrderColumn());
	    vo.setOrderMethod(pagination.getOrderMethod());
	    
		return formDAO.selectForms(vo, pagination);
	}

	public FormVO getForm(String formID) throws Exception {
	    FormVO vo = new FormVO();
	    vo.setFormId(formID);
	    
		return formDAO.selectForm(vo);
	}
	
	public FormVO insertForm(FormVO form, int formType) throws Exception {
		form.setFormType(formType);
		
		if (form.getFormId() == null) {
            String formId = idGnrService.getNextStringId();
            form.setFormId(formId);
		}
		formDAO.insertForm(form);
		
		System.out.println("form id to insert[" + form.getFormId() +"]");
		return getForm(form.getFormId());
	}
	
	public FormVO updateForm(FormVO form, int formType) throws Exception {
		/* 20160325_SUJI.H */
		form.setFormType(formType);
		
		formDAO.updateForm(form);
		
		return getForm(form.getFormId());
	}

	public void deleteForm(String formID) throws Exception {
	    FormVO vo = new FormVO();
	    vo.setFormId(formID);
	    
//		int count = formDAO.selectFormCnt(vo);
//		if(count > 0){
//			throw new Exception("This form["+formID+"] is in use.");
//		}
		formDAO.deleteForm(vo);
	}
	
	public void deleteFormList(String[] formIdArray) throws Exception {
		for(int i=0; i<formIdArray.length; i++){
			String formID = formIdArray[i];

			if(StringUtils.isEmpty(formID)) continue;
			
			FormVO vo = new FormVO();
			vo.setFormId(formID);
//			int count = formDAO.selectFormCnt(vo);
//			if(count > 0){
//				throw new Exception("This form["+formID+"] is in use.");
//			}
			formDAO.deleteForm(vo);
		}
	}

	public int getTotalFormCount(String compID, Pagination pagination) throws Exception {
	    FormVO vo = new FormVO();
	    vo.setOrgId(compID);
	    
		return formDAO.selectListFormCnt(vo, pagination);
	}
	
	public String getNextFormId() throws Exception {
	    return idGnrService.getNextStringId();
	}
}
