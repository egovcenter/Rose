package egovframework.com.uss.omt.service;

import java.io.Serializable;
import java.util.Date;


public class AuthenticationVO implements Serializable {

	private static final long serialVersionUID = 9078299293950872394L;


	private String userId;		//사용자ID
	private String deptId;		//회사ID
	private Date loginDt;		//로그인일시
	
	/* 2015-07-14  추가*/
	private String communityID;	// 소속기관의 DEPT_ID 
	
	private String userNm;		//사용자ID
	
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public Date getLoginDt() {
		return loginDt;
	}

	public void setLoginDt(Date loginDt) {
		this.loginDt = loginDt;
	}

	public String getCommunityID() {
		return communityID;
	}

	public void setCommunityID(String communityID) {
		this.communityID = communityID;
	}

	public String getUserNm() {
		return userNm;
	}

	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}