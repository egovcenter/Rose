package egovframework.com.uss.omt;

import java.io.Serializable;

public class DeptTreeView implements Serializable {

	private static final long serialVersionUID = -8072984712494804526L;
	

    private String deptId;          //부서ID
    private String deptParId;       //상위부서ID
    private String deptNm;          //부서명
    
    private String depthAppliedName; // 계층깊이가적용된부서명
    private int depth;               // 계층깊이
    
    private boolean childNode;          // 자식노드여부


    
	public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptParId() {
        return deptParId;
    }

    public void setDeptParId(String deptParId) {
        this.deptParId = deptParId;
    }

    public String getDeptNm() {
        return deptNm;
    }

    public void setDeptNm(String deptNm) {
        this.deptNm = deptNm;
    }

    public String getDepthAppliedName() {
        return depthAppliedName;
    }

    public void setDepthAppliedName(String depthAppliedName) {
        this.depthAppliedName = depthAppliedName;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public boolean isChildNode() {
        return childNode;
    }

    public void setChildNode(boolean childNode) {
        this.childNode = childNode;
    }
    
}
