package egovframework.com.uss.ion.apv;

import java.util.Date;

public interface Doc {

	/**
	 * 문서ID
	 */
	public abstract String getDocID();

	/**
	 * 문서ID
	 */
	public abstract void setDocID(String docID);
	
	/**
	 * 문서Version
	 */
	public abstract int getDocVersion();
	
	/**
	 * 문서Version
	 */
	public abstract void setDocVersion(int docVersion);

	/**
	 * 분류태그ID
	 */
	public abstract String getLbelId();

	/**
	 * 분류태그ID
	 */
	public abstract void setLbelId(String lbelID);

	/**
	 * 제목
	 */
	public abstract String getDocTitle();

	/**
	 * 제목
	 */
	public abstract void setDocTitle(String docTitle);

	/**
	 * 문서번호
	 */
	public abstract String getDocCd();

	/**
	 * 문서번호
	 */
	public abstract void setDocCd(String docCd);

	/**
	 * 보존년한
	 */
	public abstract String getDocSyear();

	/**
	 * 보존년한
	 */
	public abstract void setDocSyear(String docSyear);

	/**
	 * 보안등급
	 */
	public abstract String getDocSlvl();

	/**
	 * 보안등급
	 */
	public abstract void setDocSlvl(String docSlvl);

	/**
	 * 긴급여부
	 */
	public abstract String getDocEmF();

	/**
	 * 긴급여부
	 */
	public abstract void setDocEmF(String docEmF);

	/**
	 * 종이문서여부
	 */
	public abstract String getDocPpF();

	/**
	 * 종이문서여부
	 */
	public abstract void setDocPpF(String docPpF);

	/**
	 * 첨부유무
	 */
	public abstract String getDocAttaF();

	/**
	 * 첨부유무
	 */
	public abstract void setDocAttaF(String docAttaF);

	/**
	 * 의견유무
	 */
	public abstract String getDocOpnF();

	/**
	 * 의견유무
	 */
	public abstract void setDocOpnF(String docOpnF);

	/**
	 * 진행상태
	 */
	public abstract String getDocPState();

	/**
	 * 진행상태
	 */
	public abstract void setDocPState(String docPState);

	/**
	 * 완료상태
	 */
	public abstract String getDocFState();

	/**
	 * 완료상태
	 */
	public abstract void setDocFState(String docFState);

	/**
	 * 서식ID
	 */
	public abstract String getFormId();

	/**
	 * 서식ID
	 */
	public abstract void setFormId(String formID);

	/**
	 * 원문서IDimport java.util.Date;

	 */
	public abstract String getDocOrgId();

	/**
	 * 원문서ID
	 */
	public abstract void setDocOrgId(String docOrgID);

	/**
	 * 문서유형
	 */
	public abstract String getDocType();

	/**
	 * 문서유형
	 */
	public abstract void setDocType(String docType);
	/**
	 * 기안일자
	 */
	public Date getDraftDateTime();
	/**
	 * 기안일자
	 */
	public void setDraftDateTime(Date draftDateTime);
	/**
	 * 도착일자
	 */
	public Date getApprovedDateTime();
	/**
	 * 도착일자
	 */
	public void setApprovedDateTime(Date approvedDateTime);
	/**
	 * 기안부서ID
	 */
	public String getDraftDeptId();
	/**
	 * 기안부서ID
	 */
	public void setDraftDeptId(String draftDeptId);
	/**
	 * 기안부서명
	 */
	public String getDraftDeptName();
	/**
	 * 기안부서명
	 */
	public void setDraftDeptName(String draftDeptName);
	/**
	 * 기안자ID
	 */
	public String getDrafterId();
	/**
	 * 기안자ID
	 */
	public void setDrafterId(String drafterId);
	/**
	 * 기안자명
	 */
	public String getDrafterName();
	/**
	 * 기안자명
	 */
	public void setDrafterName(String drafterName);

	public abstract String toString();

}
