package egovframework.com.uss.ion.apv.web;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.uss.ion.apv.service.EgovSignerService;
import egovframework.com.uss.ion.apv.service.SignerVO;

/**
 * 게시물 관리를 위한 컨트롤러 클래스
 * 
 * @author 공통서비스개발팀 이삼섭
 * @since 2009.06.01
 * @version 1.0
 * @see
 *
 * 		<pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------       --------    ---------------------------
 *   2009.3.19  이삼섭          최초 생성
 *   2009.06.29	한성곤			2단계 기능 추가 (댓글관리, 만족도조사)
 *   2011.07.01 안민정		 	댓글, 스크랩, 만족도 조사 기능의 종속성 제거
 *   2011.8.26	정진오			IncludedInfo annotation 추가
 *   2011.09.07 서준식           유효 게시판 게시일 지나도 게시물이 조회되던 오류 수정
 *      </pre>
 */

@Controller
public class EgovSignerController {

	@Resource(name = "EgovSignerService")
	private EgovSignerService signerService;

    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;
    
	@RequestMapping(value = "/listOngoingApprovalSigner.do", method = RequestMethod.POST)
	public void listOngoingApprovalSigner(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	List<SignerVO> listSigner = new ArrayList<SignerVO>();
		String selectOngoingDocId = request.getParameter("docId");
		
		StringBuffer sb = new StringBuffer();
		response.setContentType("text/xml;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		
		if(selectOngoingDocId != null){
			listSigner = signerService.getApprovalSignerList(selectOngoingDocId);
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		for(int i = 0 ; i < listSigner.size(); i++) {
			SignerVO sn = listSigner.get(i);
			
			sb.append("<tr class='listSignerTr'>");
			sb.append("<td>"+sn.getSignSeq()+"</td>");
			sb.append("<td class=''>"+sn.getSignerDeptName()+"</td>");
			sb.append("<td>"+sn.getSignerPositionName()+"</td>");
			sb.append("<td>"+sn.getSignerName()+"</td>");
			
			if (sn.getSignState().equals("SS00")) {
			    sb.append("<td>"+ egovMessageSource.getMessage("appvl.signerState.SS00")+" </td>");
			} else if (sn.getSignState().equals("SS01")) {
			    sb.append("<td>"+ egovMessageSource.getMessage("appvl.signerState.SS01")+" </td>");
			} else if (sn.getSignState().equals("SS02")) {
			    sb.append("<td>"+ egovMessageSource.getMessage("appvl.signerState.SS02")+" </td>");
			} else if(sn.getSignState().equals("SS03")) {
			    sb.append("<td>"+ egovMessageSource.getMessage("appvl.signerState.SS03")+" </td>");
			} else if (sn.getSignState().equals("SS09")) {
			    sb.append("<td>"+ egovMessageSource.getMessage("appvl.signerState.SS09")+" </td>");
			}
			
			if(sn.getSignDate() != null) {
			    sb.append("<td class=''>"+ dateFormat.format(sn.getSignDate())+"</td>");
			} else {
			    sb.append("<td class=''> </td>");
			}
			sb.append("</tr>");
		}
		
    	out.write(sb.toString());
		out.flush();

	}
	
    @RequestMapping(value="/listApprovalSigner.do" , method=RequestMethod.POST)
   	public void listApprovalSigner(HttpServletRequest request,HttpServletResponse response) throws Exception {
        
       	List<SignerVO> listSigner = new ArrayList<SignerVO>();
   		String docId = request.getParameter("docId");
   		StringBuffer sb = new StringBuffer();
   		
   		response.setContentType("text/xml;charset=UTF-8");
   		response.setHeader("Cache-Control", "no-cache");
   		PrintWriter out = response.getWriter();
   		
   		if(docId != null){
   			listSigner = signerService.getApprovalSignerList(docId);
   		}
   		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		for(int i = 0 ; i < listSigner.size(); i++){
			SignerVO sn = listSigner.get(i);
			
			sb.append("<tr class='listSignerTr'>");
			sb.append("<td>"+sn.getSignSeq()+"</td>");
			sb.append("<td class=''>"+sn.getSignerDeptName()+"</td>");
			sb.append("<td>"+sn.getSignerPositionName()+"</td>");
			sb.append("<td>"+sn.getSignerName()+"</td>");
			
			if (sn.getSignState().equals("SS00")) {
			    sb.append("<td>"+ egovMessageSource.getMessage("appvl.signerState.SS00")+" </td>");
			} else if (sn.getSignState().equals("SS01")) {
			    sb.append("<td>"+ egovMessageSource.getMessage("appvl.signerState.SS01")+" </td>");
			} else if (sn.getSignState().equals("SS02")) {
			    sb.append("<td>"+ egovMessageSource.getMessage("appvl.signerState.SS02")+" </td>");
			} else if (sn.getSignState().equals("SS03")) {
			    sb.append("<td>"+ egovMessageSource.getMessage("appvl.signerState.SS03")+" </td>");
			} else if (sn.getSignState().equals("SS09")) {
			    sb.append("<td>"+ egovMessageSource.getMessage("appvl.signerState.SS09")+" </td>");
			} 
			
			if (sn.getSignDate() != null) {
			    sb.append("<td class=''>"+ dateFormat.format(sn.getSignDate())+"</td>");
			} else {
			    sb.append("<td class=''> </td>");
			}
			
			sb.append("</tr>");
		}

       	out.write(sb.toString());
   		out.flush();
    }
}
