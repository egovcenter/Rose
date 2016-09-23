package egovframework.com.uss.omt;

public class RuleParameter {
	private String targetID;
	private String auth;
	private boolean isChild;
	
	/**
	 * <p>권한에 대한 검색 조건.</p>
	 * <p>the search condition for the authority.</p>
	 * 
	 * @param targetID 대상 ID(부서ID 또는 사용자ID).
	 * 				organization's id, if the isChild is false, the ID is the relID of the authority. 
	 * 				otherwise the ID is child of the relID.
	 * @param auth 권한 코드.
	 * 				authority code.
	 * @param isChild 하위 부서 여부. true 이면 대상 부서의 상위 부서(자신 포함)에서 해당 권한을 가지는 부서가 있는지 검색한다.
	 * 				if it is true, the search scope have the all children of the relId of the authority.
	 *  			if it is false, the search scope have only the relID of the authority.
	 */
	public RuleParameter(String targetID, String auth, boolean isChild) {
		super();
		this.targetID = targetID;
		this.auth = auth;
		this.isChild = isChild;
	}
	
	public String getTargetID() {
		return targetID;
	}
	
	public void setTargetID(String targetID) {
		this.targetID = targetID;
	}
	
	public boolean isChild() {
		return isChild;
	}
	
	public void setChild(boolean isChild) {
		this.isChild = isChild;
	}
	
	public String getAuth() {
		return auth;
	}
	
	public void setAuth(String auth) {
		this.auth = auth;
	}
	
}
