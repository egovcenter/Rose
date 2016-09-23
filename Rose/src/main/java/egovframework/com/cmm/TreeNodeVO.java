package egovframework.com.cmm;

import java.io.Serializable;

public class TreeNodeVO implements Serializable {

	private static final long serialVersionUID = -20160202181700L;
	
	/**
	 * Node Id
	 */
	private String nodeId;
	
	/**
	 * Parent node id
	 */
	private String parentId;
	
	/**
	 * Node name
	 */
	private String nodeNm;
	
	/**
	 * Node Level
	 */
	private String level;
	
	/**
	 * Display sequence in same level
	 */
	private int seq;
	
	
	/**
	 * Return id of node
	 * @return nodeId
	 */
	public String getNodeId() {
		return nodeId;
	}
	
	/**
	 * Set id of node
	 * @param id
	 */
	public void setNodeId(String id) {
		nodeId = id;
	}
	
	
	/**
	 * Return id of parent node
	 * @return parentId
	 */
	public String getParentId() {
		return parentId;
	}
	
	/**
	 * Set parent id
	 * @param id
	 */
	public void setParentId(String id) {
		parentId = id;
	}
	
	/**
	 * Return node name
	 * @return nodeNm
	 */
	public String getNodeNm() {
		return nodeNm;
	}
	
	/**
	 * Set node name
	 * @param name
	 */
	public void setNodeNm(String name) {
		nodeNm = name;
	}
	
	/**
	 * Return level of node
	 * @return level
	 */	
	public String getLevel() {
		return level;
	}
	
	/**
	 * Set level of node
	 * @param level
	 */
	public void setLevel(String level) {
		this.level = level;
	}
	/**
	 * Return display sequence of node in the depth
	 * @return seq
	 */
	public int getSeq() {
		return seq;
	}
	
	/**
	 * Set display sequence of node
	 * @param seq
	 */
	public void setSeq(int seq) {
		this.seq = seq;
	}
}
