package egovframework.com.uss.omt.service;

import java.io.Serializable;

public class PositionVO implements Serializable {

	private static final long serialVersionUID = -20160211154200L;

	private String deptId;	//부서ID
	private String posiId;	//직위ID
	private String posiNm;	//직위명
	private int posiLv;		//직위레벨
	
	private boolean deletable;	// 삭제가능여부
	
	
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getPosiId() {
		return posiId;
	}

	public void setPosiId(String posiId) {
		this.posiId = posiId;
	}

	public String getPosiNm() {
		return posiNm;
	}

	public void setPosiNm(String posiNm) {
		this.posiNm = posiNm;
	}

	public int getPosiLv() {
		return posiLv;
	}

	public void setPosiLv(int posiLv) {
		this.posiLv = posiLv;
	}

	public boolean isDeletable() {
		return deletable;
	}

	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
	}

	
}
