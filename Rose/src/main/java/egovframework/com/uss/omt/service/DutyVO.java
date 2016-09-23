package egovframework.com.uss.omt.service;

import java.io.Serializable;

public class DutyVO implements Serializable {

	private static final long serialVersionUID = -1549280619907508769L;

	private String deptId;//부서ID
	private String dutyId;//직책ID
	private String dutyNm;//직책명
	
	private boolean deletable;	// 삭제가능여부
	
	
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDutyId() {
		return dutyId;
	}

	public void setDutyId(String dutyId) {
		this.dutyId = dutyId;
	}

	public String getDutyNm() {
		return dutyNm;
	}

	public void setDutyNm(String dutyNm) {
		this.dutyNm = dutyNm;
	}

	public boolean isDeletable() {
		return deletable;
	}

	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
	}


}
