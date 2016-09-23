package egovframework.com.uss.ion.apm.service;

import java.io.Serializable;

import egovframework.com.uss.ion.apm.Label;


public class LabelVO implements Label, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8735782643187169993L;
	
	protected String childLabel = "0";
	/**
	 * 분류태그ID
	 */
	protected String labelId;

	/**
	 * 분류태그명
	 */
	protected String labelNm;
	
	/**
	 * 부서ID
	 */
	protected String deptId;
	
	/* TBS 2016.02.18 start*/
	/**
	 * LFT
	 */
	protected int labelLft;
	
	/**
	 * RGT
	 */
	protected int labelRgt;
	
	/**
	 * TOP
	 */
	protected String labelTopF;
	/* TBS 2016.02.18 end*/
	
	/**
	 * 레벨
	 */
	protected String labelLevel;
	
	   /**
     * 상위분류태그ID
     */
    protected String labelParentId;

    /**
     * 순번
     */
    protected int labelSeq;
    
	
    public String getChildLabel() {
		return childLabel;
	}

	public void setChildLabel(String childLabel) {
		this.childLabel = childLabel;
	}

	public String getLabelParentId() {
        return labelParentId;
    }
    
    public void setLabelParentId(String id) {
        labelParentId = id;
    }
    
    public int getLabelSeq() {
        return labelSeq;
    }
    
    public void setLabelSeq(int seq) {
        labelSeq = seq;
    }
    
	public int getLabelLft() {
		return labelLft;
	}
	public void setLabelLft(int labelLft) {
		this.labelLft = labelLft;
	}
	public int getLabelRgt() {
		return labelRgt;
	}
	public void setLabelRgt(int labelRgt) {
		this.labelRgt = labelRgt;
	}
	public String getLabelId() {
		return labelId;
	}
	public void setLabelId(String labelID) {
		this.labelId = labelID;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getLabelNm() {
		return labelNm;
	}
	public void setLabelNm(String labelNm) {
		this.labelNm = labelNm;
	}
	public String getLabelTopF() {
		return labelTopF;
	}
	public void setLabelTopF(String labelTopF) {
		this.labelTopF = labelTopF;
	}
	public String getLabelLevel() {
		return labelLevel;
	}
	public void setLabelLevel(String labelLevel) {
		this.labelLevel = labelLevel;
	}	
}
