package egovframework.com.uss.omt.service;

import java.io.Serializable;

public class AbsenceVO  implements Serializable {

	private static final long serialVersionUID = -20160211134000L;
	
	private String absId;        //부재ID
    private String userId;       //사용자ID
    private String absSDt;       //시작일시
    private String absEDt;       //종료일시
    private String absRmrk;      //사유
    private String absSubuserId; //대리사용자ID

    /* 2015-06-17 추가 */
    private String absAtype;	 //결재처리옵션

    

    public String getAbsId() {
		return absId;
	}

	public void setAbsId(String absId) {
		this.absId = absId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAbsSDt() {
		return absSDt;
	}

	public void setAbsSDt(String absSDt) {
		this.absSDt = absSDt;
	}

	public String getAbsEDt() {
		return absEDt;
	}

	public void setAbsEDt(String absEDt) {
		this.absEDt = absEDt;
	}

	public String getAbsRmrk() {
		return absRmrk;
	}

	public void setAbsRmrk(String absRmrk) {
		this.absRmrk = absRmrk;
	}

	public String getAbsSubuserId() {
		return absSubuserId;
	}

	public void setAbsSubuserId(String absSubuserId) {
		this.absSubuserId = absSubuserId;
	}

	public String getAbsAtype() {
		return absAtype;
	}

	public void setAbsAtype(String absAtype) {
		this.absAtype = absAtype;
	}
}
