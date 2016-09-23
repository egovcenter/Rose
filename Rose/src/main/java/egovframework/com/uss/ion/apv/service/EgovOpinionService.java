package egovframework.com.uss.ion.apv.service;

import java.util.List;

/**
 * @Class Name : EgovAttachFileService.java
 * @Description : Service for managing attached file information
 * @Modification Information
 *
 *    Date         Author	   Content
 *   -----------  -------     -------------------
 *    2016.02.04   paul        initial
 *
 * @since 2016.02.04.
 * @version
 * @see
 *
 */
public interface EgovOpinionService {
	public void insertOpinion(OpinionVO opinion) throws Exception;
	
	public int getOpinionCnt(String docID) throws Exception;
	public List<OpinionVO> getOpinionList(String docID) throws Exception;
}
