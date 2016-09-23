package egovframework.com.uss.omt.service;

import java.io.Serializable;
import java.util.Date;

public class DeptVO implements Serializable {

	private static final long serialVersionUID = 5878580717298229832L;
	private String deptId;
	private String deptNm;
	private String deptCd;
	private String deptRboxF;
	private String deptBoxF;
	private String deptTopF;
	private String deptRboxUserId;
	private String deptBoxUserId;
	private String deptSboxUserId;
	
	/* 2015-06-24 추가 */
    private String 	deptAdminUserId; //기관담당자
	private String 	deptStatus;
	private Date 	deptUpdateDt;
	private String 	deptDc;

	/* 2015-06-24 추가 */
	private String 	deptParId; 
	private int 	deptSeq;
	private int 	deptLevel;
	private String 	baseOrgnztId;
	private String 	funcType;
	
	/* TBS 2016-02-23 start */
	private String 	deptRboxUserNm;
	private String 	deptBoxUserNm;
	private String 	deptSboxUserNm;
	private String 	deptAdminUserNm;
	
	
	public String getDeptRboxUserNm() {
		return deptRboxUserNm;
	}
	public void setDeptRboxUserNm(String deptRboxUserNm) {
		this.deptRboxUserNm = deptRboxUserNm;
	}
	public String getDeptBoxUserNm() {
		return deptBoxUserNm;
	}
	public void setDeptBoxUserNm(String deptBoxUserNm) {
		this.deptBoxUserNm = deptBoxUserNm;
	}
	public String getDeptSboxUserNm() {
		return deptSboxUserNm;
	}
	public void setDeptSboxUserNm(String deptSboxUserNm) {
		this.deptSboxUserNm = deptSboxUserNm;
	}
	public String getDeptAdminUserNm() {
		return deptAdminUserNm;
	}
	public void setDeptAdminUserNm(String deptAdminUserNm) {
		this.deptAdminUserNm = deptAdminUserNm;
	}
	/* TBS 2016-02-23 end */
	
	public String getFuncType() {
		return funcType;
	}
	public void setFuncType(String funcType) {
		this.funcType = funcType;
	}
	public String getBaseOrgnztId() {
		return baseOrgnztId;
	}
	public void setBaseOrgnztId(String baseOrgnztId) {
		this.baseOrgnztId = baseOrgnztId;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getDeptNm() {
		return deptNm;
	}
	public void setDeptNm(String deptNm) {
		this.deptNm = deptNm;
	}
	public String getDeptCd() {
		return deptCd;
	}
	public void setDeptCd(String deptCd) {
		this.deptCd = deptCd;
	}
	public String getDeptRboxF() {
		return deptRboxF;
	}
	public void setDeptRboxF(String deptRboxF) {
		this.deptRboxF = deptRboxF;
	}
	public String getDeptBoxF() {
		return deptBoxF;
	}
	public void setDeptBoxF(String deptBoxF) {
		this.deptBoxF = deptBoxF;
	}
	public String getDeptTopF() {
		return deptTopF;
	}
	public void setDeptTopF(String deptTopF) {
		this.deptTopF = deptTopF;
	}
	public String getDeptRboxUserId() {
		return deptRboxUserId;
	}
	public void setDeptRboxUserId(String deptRboxUserId) {
		this.deptRboxUserId = deptRboxUserId;
	}
	public String getDeptBoxUserId() {
		return deptBoxUserId;
	}
	public void setDeptBoxUserId(String deptBoxUserId) {
		this.deptBoxUserId = deptBoxUserId;
	}
	public String getDeptSboxUserId() {
		return deptSboxUserId;
	}
	public void setDeptSboxUserId(String deptSboxUserId) {
		this.deptSboxUserId = deptSboxUserId;
	}
	public String getDeptAdminUserId() {
		return deptAdminUserId;
	}
	public void setDeptAdminUserId(String deptAdminUserId) {
		this.deptAdminUserId = deptAdminUserId;
	}
	public String getDeptStatus() {
		return deptStatus;
	}
	public void setDeptStatus(String deptStatus) {
		this.deptStatus = deptStatus;
	}
	public Date getDeptUpdateDt() {
		return deptUpdateDt;
	}
	public void setDeptUpdateDt(Date deptUpdateDt) {
		this.deptUpdateDt = deptUpdateDt;
	}
	public String getDeptDc() {
		return deptDc;
	}
	public void setDeptDc(String deptDc) {
		this.deptDc = deptDc;
	}
	public String getDeptParId() {
		return deptParId;
	}
	public void setDeptParId(String deptParId) {
		this.deptParId = deptParId;
	}
	public int getDeptSeq() {
		return deptSeq;
	}
	public void setDeptSeq(int deptSeq) {
		this.deptSeq = deptSeq;
	}
	public int getDeptLevel() {
		return deptLevel;
	}
	public void setDeptLevel(int deptLevel) {
		this.deptLevel = deptLevel;
	}
}
