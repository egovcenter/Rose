package egovframework.com.uss.ion.apm.service;

import egovframework.com.uss.ion.apm.Form;
import egovframework.com.uss.ion.apv.SearchCriteria;

public class FormVO extends SearchCriteria implements Form {
	private String formId; // form_id
	private String orgId; // dept_id
	private String formName; // form_nm
	private String formVersion; // form_ver
	private String formRemark; // form_rmrk
	private boolean formUseF; // form_use_f
	private String orgNm; // for view
	private int		formType;

	public String getFormId() {
		return formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	public String getFormVersion() {
		return formVersion;
	}
	public void setFormVersion(String formVersion) {
		this.formVersion = formVersion;
	}
	public String getFormRemark() {
	    if (formRemark == null) {
	        formRemark = "";
	    }
		return formRemark;
	}
	public void setFormRemark(String formRemark) {
		this.formRemark = formRemark;
	}
	public boolean isFormUseF() {
		return formUseF;
	}
	public void setFormUseF(boolean formUseF) {
		this.formUseF = formUseF;
	}
	public String getOrgNm() {
		return orgNm;
	}
	public void setOrgNm(String orgNm) {
		this.orgNm = orgNm;
	}
    public int getFormType(){
		return formType;
    }
	public void setFormType(int formType){
		this.formType = formType;
    }
	
}
