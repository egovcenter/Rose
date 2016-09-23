<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ page import="egovframework.com.cmm.service.EgovProperties" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovCommon.jsp"%>
<%
 /**
  * @Class Name : EgovNoticeList.jsp
  * @Description : 게시물 목록화면
  * @Modification Information
  * @
  * @  수정일      수정자            수정내용
  * @ -------        --------    ---------------------------
  * @ 2009.03.19   이삼섭          최초 생성
  * @ 2011.11.11   이기하          익명게시판 검색시 작성자 제거
  * @ 2015.05.08   조정국          표시정보 클릭시 이동링크 제한 : bbsId가 없는 요청은 처리제한.
  *
  *  @author 공통서비스 개발팀 이삼섭
  *  @since 2009.03.19
  *  @version 1.0
  *  @see
  *
  */
%>
<html lang="en">
<head>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovHeader.jsp"%>
<script type="text/javascript" src="/js/egovframework/com/uss/ion/approval.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<%-- <script type="text/javascript" src="<c:out value="${pageContext.servletContext.contextPath}"/>/js/egovframework/approval/approval.js"></script> --%>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/cop/bbs/EgovBBSMng.js' />" ></script>

<c:if test="${anonymous == 'true'}"><c:set var="prefix" value="/anonymous"/></c:if>

<c:choose>
	<c:when test="${preview == 'true'}">
		<script type="text/javascript">
		<!--
			function press(event) {
			}
		
			function fn_egov_addNotice() {
			}
		
			function fn_egov_select_noticeList(pageNo) {
			}
		
			function fn_egov_inqire_notice(nttId, bbsId) {
			}
		//-->
		</script>
	</c:when>
	<c:otherwise>
		<script type="text/javascript">
		<!--
		
			function fn_egov_addNotice() {
				document.frm.action = "<c:url value='/cop/bbs${prefix}/addBoardArticle.do'/>";
				document.frm.submit();
			}
		
		//-->
			function press(event) {
				if (event.keyCode==13) {
					fn_egov_select_noticeList('1');
				}
			}
			function fn_egov_select_noticeList(pageNo) {
				document.frm.pageIndex.value = pageNo;
				document.frm.action = "<c:url value='/cop/bbs${prefix}/selectBoardList.do'/>";
				document.frm.submit();
			}
		
			function fn_egov_inqire_notice() {
				if(bbsId == "") return false; //20150508
				document.subForm.nttId.value = nttId;
				document.subForm.bbsId.value = bbsId;
				document.subForm.action = "<c:url value='/cop/bbs${prefix}/selectBoardArticle.do'/>";
				document.subForm.submit();
			}
			function fn_egov_register() {
				document.butBig.action = "<c:url value='/cop/bbs/addBoardArticle.do'/>";
				document.butBig.submit();
			}
		</script>
	</c:otherwise>
</c:choose>
</head>

<body>
<div class="wrap">
	<!--  Top 메뉴영역 시작 --> 
	<jsp:include page="/WEB-INF/jsp/egovframework/com/cmm/EgovTopMenu.jsp" flush="false">
		<jsp:param value="BBS" name="selectedMenu"/>
	</jsp:include>	
	<!--  Top 메뉴영역 끝 --> 
	<div class="clear"></div>
	<div class="Container"> 
	<!-- Lnb -->
		<div class="Side"> 
		<!-- 메뉴별 lnb 타이틀 컬러 변경 / lnb_00 -->
			<div class="lnb_ap"><spring:message code="appvl.sidemenu.label.processing"/></div>
			<div class="h36"></div>
			<!-- lnb별 공통 버튼 -->
			<div class="lnb_butbox" id="lnb_butbox">
				<%-- TBS 2016.02.02 start --%> 
				<form name="butBig" action ="<c:url value='/cop/bbs${prefix}/addBoardArticle.do'/>" method="post">
					<input type="hidden" name="bbsId" value="<c:out value='${boardVO.bbsId}'/>" />
					<input type="hidden" name="bbsNm" value="<c:out value='${boardVO.bbsNm}'/>" />
					<input type="hidden" name="nttId"  value="0" />
					<input type="hidden" name="bbsTyCode" value="<c:out value='${brdMstrVO.bbsTyCode}'/>" />
					<input type="hidden" name="bbsAttrbCode" value="<c:out value='${brdMstrVO.bbsAttrbCode}'/>" />
					<input type="hidden" name="authFlag" value="<c:out value='${brdMstrVO.authFlag}'/>" />
					<input type="hidden" name="pageIndex" value="<c:out value='${searchVO.pageIndex}'/>"/>
				<%-- TBS 2016.02.02 end --%> 
					<input type="submit" value='<spring:message code="appvl.sidemenu.button.posting"/>' title="<spring:message code="appvl.sidemenu.button.posting"/>" class="but_big" onClick="fn_egov_register();" <c:if test="${BbsEempty eq true}">disabled="disabled"</c:if>/>
				</form>
			</div>
			<!-- 트리 삽입 영역 -->
			<c:if test="${BbsEempty ne true}">
			<div class="lnb_tree" style="background:#FFF;">
				<jsp:include page="./cmm/EgovBBSTree.jsp" flush="false" />
			</div>
			</c:if>
		</div>
		<!-- Content -->
		<div class="Content">
		<!-- Content box Start -->
			<div class="content_box"> 
				<!-- 탑 써치 -->
				 <div class="top_search">
					<!--  <input name="textfield" type="text" id="textfield" title="search" class="input260" onClick="this.style.backgroundImage='none'">
					<input type="button" value="Search" class="but_navy" onClick="location.href='http://www.naver.com'";/> -->
					<%-- TBS 2016.02.02 start --%>
					<%-- TBS 2016.02.02 start --%> 
					<form name="frm" action ="<c:url value='/cop/bbs${prefix}/selectBoardList.do'/>" method="post">
						<input type="hidden" name="bbsId" value="<c:out value='${boardVO.bbsId}'/>" />
						<input type="hidden" name="bbsNm" value="<c:out value='${boardVO.bbsNm}'/>" />
						<input type="hidden" name="nttId"  value="0" />
						<input type="hidden" name="bbsTyCode" value="<c:out value='${brdMstrVO.bbsTyCode}'/>" />
						<input type="hidden" name="bbsAttrbCode" value="<c:out value='${brdMstrVO.bbsAttrbCode}'/>" />
						<input type="hidden" name="authFlag" value="<c:out value='${brdMstrVO.authFlag}'/>" />
						<input type="hidden" name="pageIndex" value="<c:out value='${searchVO.pageIndex}'/>"/>
					<%-- TBS 2016.02.02 end --%> 
					<table class="table-search" border="0" >
						<tr align="left">
							<td width="3%">
								<select name="searchCnd" class="select" title="search">
					   			   <option value="0" <c:if test="${searchVO.searchCnd == '0'}">selected="selected"</c:if> >제목</option>
								   <option value="1" <c:if test="${searchVO.searchCnd == '1'}">selected="selected"</c:if> >내용</option>
								   <c:if test="${anonymous != 'true'}">
								   <option value="2" <c:if test="${searchVO.searchCnd == '2'}">selected="selected"</c:if> >작성자</option>
								   </c:if>
								</select>
							</td>
							<td width="30%">
    							<input name="searchWrd" type="text" size="130" value='<c:out value="${searchVO.searchWrd}"/>' maxlength="130" onkeypress="press(event);" title="검색어 입력">
    						</td>
    						<td width="36%"><input type="submit" value="search" onclick="fn_egov_select_noticeList('1'); return false;" style="height:20px;width:42px;padding:0px 0px 0px 0px;" ></td>
    					</tr>
    				</table>
					</form>
				</div>
				<div class="clear"></div>
				<div class="title_box">
					<div class="title_line">
						<c:choose>
							<c:when test="${BbsEempty eq true}">
								<h1><spring:message code="common.nodata.msg" /></h1>						
							</c:when>
							<c:otherwise>
								<h1>${boardVO.bbsNm}</h1>						
							</c:otherwise>
						</c:choose>
					</div>					
				</div>
			<%-- TBS 2016.02.02 end --%>
				<%-- TBS 2016.02.03 register button start --%>
				<div class="but_line" style="text-align:right; padding-top: 10px;">
<%-- 					<input type="button" value='<spring:message code="appvl.document.button.register"/>' class="but_navy mr05" onClick="fn_egov_register();"/> --%>
				</div>
				<%-- TBS 2016.02.03 register button end --%>

				<!-- 공통 타이틀 버튼 라인 -->
				
				<!-- TBS 2016.02.11 start-->
<%-- 				<jsp:include page="/WEB-INF/jsp/egovframework/com/cmm/EgovPagination.jsp" flush="false" />  --%>
				<!-- TBS 2016.02.11 end -->
				
				<div class="rapper_table">
					<table summary="/No/Attochment/Title/Poster/Post Date/Expire date" class="board_type_height">
						<caption class="blind"></caption>
						<colgroup>
							<col width="5%"/>
							<col width="*"/>
							<col width="10%"/>
							<col width="8%"/>
							<col width="10%"/>
						</colgroup>
						<thead>
							<tr>
								<th scope="col"><span>번호</span></th>
								<th scope="col" class=""><span>제목</span></th>			
								<c:if test="${brdMstrVO.bbsAttrbCode == 'BBSA01'}">
									<th scope="col"><span>게시시작일</span></th>
									<th scope="col"><span>게시종료일</span></th>
								</c:if>
								<c:if test="${anonymous != 'true'}">
									<th scope="col"><span>작성자</span></th>
								</c:if>
								<th scope="col"><span>작성일</span></th>		
								<th scope="col"><span>조회수</span></th>		
								<%--<th scope="col" class="align_center"><img src="./image/icon_file.png" alt="icon_check"></th>  --%>
							</tr>
						</thead>					
						<tbody>
							 <c:forEach var="result" items="${resultList}" varStatus="status">
								 <tr>
									<td>
										<c:choose>
											<c:when test="${not empty searchVO}">
												<c:out value="${(searchVO.pageIndex-1) * searchVO.pageSize + status.count}"/>
											</c:when>
											<c:otherwise>
												<c:out value="0"/>
											</c:otherwise>
										</c:choose>
									</td>
									<td class="">
								    	<c:choose>
								    		<c:when test="${result.isExpired=='Y'}">
										    	<c:if test="${result.replyLc!=0}">
										    		<c:forEach begin="0" end="${result.replyLc}" step="1">
										    			&nbsp;
										    		</c:forEach>
										    		<img src="<c:url value='/images/egovframework/com/cmm/icon/reply_arrow.gif'/>" alt="reply arrow">
										    	</c:if>
							    				<c:out value="${result.nttSj}" />
							    			</c:when>
							    			<c:otherwise>
			    				    		<form name="subForm" method="post" action="<c:url value='/cop/bbs${prefix}/selectBoardArticle.do'/>">
												<input type="hidden" name="bbsId" value="<c:out value='${result.bbsId}'/>" />
												<input type="hidden" name="nttId"  value="<c:out value="${result.nttId}"/>" />
												<input type="hidden" name="bbsTyCode" value="<c:out value='${brdMstrVO.bbsTyCode}'/>" />
												<input type="hidden" name="bbsAttrbCode" value="<c:out value='${brdMstrVO.bbsAttrbCode}'/>" />
												<input type="hidden" name="authFlag" value="<c:out value='${brdMstrVO.authFlag}'/>" />
												<input type="hidden" name="pageIndex" value="<c:out value='${searchVO.pageIndex}'/>"/>
										    	<c:if test="${result.replyLc!=0}">
										    		<c:forEach begin="0" end="${result.replyLc}" step="1">
										    			&nbsp;
										    		</c:forEach>
										    		<img src="<c:url value='/images/egovframework/com/cmm/icon/reply_arrow.gif'/>" alt="reply arrow">
											    	</c:if>
										    		<span class="link">
														<input type="submit" value="${result.nttSj}" onclick="fn_egov_inqire_notice();">
<%-- 										    			<a href="#"  onclick="fn_egov_inqire_notice();"><c:out value="${result.nttSj}"/></a> --%>
										    		</span>
										    	</form>
								    		</c:otherwise>
								    	</c:choose>
									</td>
									<c:if test="${brdMstrVO.bbsAttrbCode == 'BBSA01'}">
										<td><c:out value="${result.ntceBgnde}"/></td>
										<td><c:out value="${result.ntceEndde}"/></td>
									</c:if>
									<c:if test="${anonymous != 'true'}">
										<td><c:out value="${result.frstRegisterNm}"/></td>
									</c:if>
									<td><c:out value="${result.frstRegisterPnttm}"/></td>
									<td><c:out value="${result.inqireCo}"/></td>
									<!-- 첨부파일이 없을때 해당 a태그에 'hidden' class 추가 -->
<!-- 									<td class="align_center"> -->
<%-- 										<c:if test="${waiting.docAttaF =='1'}"> --%>
<!-- 										<a href="#" class="fileDown"> -->
<%-- 											<input type="hidden" value="${waiting.docID}" id="ongoingDocId" name="ongoingDocId"> --%>
<!-- 											<img src="./image/icon_file.png" alt="icon_file"> -->
<!-- 										</a> -->
<%-- 										</c:if> --%>
<!-- 									</td> -->
								</tr>
							 </c:forEach>
							 <c:if test="${fn:length(resultList) == 0}">
							  <tr>
						    	<c:choose>
						    		<c:when test="${brdMstrVO.bbsAttrbCode == 'BBSA01'}">
						    			<td colspan="5"><spring:message code="common.nodata.msg" /></td>
						    		</c:when>
						    		<c:otherwise>
						    			<c:choose>
						    				<c:when test="${anonymous == 'true'}">
								    			<td colspan="5"><spring:message code="common.nodata.msg" /></td>
								    		</c:when>
								    		<c:otherwise>
								    			<td colspan="5"><spring:message code="common.nodata.msg" /></td>
								    		</c:otherwise>
								    	</c:choose>
						    		</c:otherwise>
						    	</c:choose>
						    </tr>
						    </c:if>
						</tbody>
					</table>
				</div>
				
				<c:if test="${not empty paginationInfo}">
				<div align="center">
					<ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="fn_egov_select_noticeList" />
				</div>
				</c:if>
<!-- 				<div class="clear"></div> -->
<!-- 				<div class="toggle toggle01"  style="padding-top:150px; margin-top:20px;"></div> -->
<!-- 				<div class="statusSlide_rapper rapper_table" id="statusSlide"> -->
<!-- 					<div class="popup_close_rapper"><a href="#" class="btn_popup_close"><img src="./image/btn_popup_close.png" alt=""/></a></div> -->
<!-- 					<table summary="/No/Attochment/Title/Poster/Post Date/Expire date" class="board_type_height"> -->
<%-- 						<caption class="blind"></caption> --%>
<%-- 						<colgroup> --%>
<%-- 							<col width="60px"/> --%>
<%-- 							<col width="140px"/> --%>
<%-- 							<col width="30%"/> --%>
<%-- 							<col width="17%"/> --%>
<%-- 							<col width=""/> --%>
<%-- 							<col width="10%"/> --%>
<%-- 						</colgroup> --%>
<!-- 						<thead> -->
<!-- 							<tr> -->
<%-- 								<th scope="col" class="align_center"><span><spring:message code="appvl.documet.label.signerline.table.order"/></span></th> --%>
<%-- 								<th scope="col" class=""><span><spring:message code="appvl.documet.label.signerline.table.completed"/></span></th> --%>
<%-- 								<th scope="col"><span><spring:message code="appvl.documet.label.signerline.table.department"/></span></th> --%>
<%-- 								<th scope="col"><span><spring:message code="appvl.documet.label.signerline.table.position"/></span></th> --%>
<%-- 								<th scope="col"><span><spring:message code="appvl.documet.label.signerline.table.name"/></span></th> --%>
<%-- 								<th scope="col"><span><spring:message code="appvl.documet.label.signerline.table.status"/></span></th> --%>
<!-- 							</tr> -->
<!-- 						</thead>					 -->
<!-- 						<tbody class="listSignerBody"> -->
<!-- 						</tbody> -->
<!-- 					</table> -->
<!-- 				</div> -->
				
<!-- 				<div class="clear"></div> -->
<!-- 				<div class="toggle toggle02"  style="padding-top:150px; margin-top:20px;"></div> -->
<!-- 				<div class="statusSlide_rapper rapper_table" id="attachSlide"> -->
<!-- 					<div class="popup_close_rapper"><a href="#" class="btn_popup_close"><img src="./image/btn_popup_close.png" alt=""/></a></div> -->
<!-- 					<table summary="/No/Attochment/Title/Poster/Post Date/Expire date" class="board_type_height mb20" id="attach_list_table"> -->
<%-- 						<caption class="blind"></caption> --%>
<%-- 						<colgroup> --%>
<%-- 							<col width="*"/> --%>
<%-- 							<col width="100px"/> --%>
<%-- 						</colgroup> --%>
<!-- 						<thead> -->
<!-- 							<tr> -->
<%-- 								<th scope="col" class=""><span><spring:message code="appvl.documet.label.attachment.table.filename"/></span></th> --%>
<%-- 								<th scope="col" class=""><span><spring:message code="appvl.documet.label.attachment.table.filesize"/></span></th> --%>
<!-- 							</tr> -->
<!-- 						</thead>					 -->
<!-- 						<tbody class="listAttachBody"> -->
<!-- 						</tbody> -->
<!-- 					</table> -->
<!-- 				</div> -->
				
			</div>
		</div>
		<!-- Content box End --> 
	</div>
	<div class="clear"></div>
	<!-- Container End -->
</div>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovCopyright.jsp" %>
</body>
<script language="javascript" type="text/javascript"> 
//<![CDATA[
 
$('.board_type_height th').click(function() {
if ($(this).hasClass('sort_down')){
	$(this).removeClass('sort_down');
	$(this).addClass('sort_up');
} else {
	$(this).removeClass('sort_up');
	$(this).addClass('sort_down');
  }
});
 
$( ".Status" ).click(function() {
	$(".listSignerTr").remove();
	var docId = $(this).children("input").attr("value");
	var url = "<c:url value='/listOngoingApprovalSigner.do'/>";
	$.ajax({                        
        type: "POST",
        url: url,
        dataType: 'html',
        data:{docId:docId},
        success: function(data) {
        $(".listSignerBody").append(data); 
       }
    });
	$( ".toggle01" ).animate({'padding-top':'0'},480); 
	document.getElementById("statusSlide").style.display = "block";
});


$( ".btn_popup_close" ).click(function() {
	$( ".toggle" ).animate({'padding-top':'150px'},480); 
	$( ".statusSlide_rapper" ).slideUp( 480, function() {});
});

 function approvalDocPage(docType){
	 document.getElementById("docType").value = docType;
	 document.listForm.action = "<c:url value='/approvalDocPageList.do'/>";
	 document.listForm.submit();
 }

 $( ".fileDown" ).click(function() {
		$(".listAttachTr").remove();
		var docId = $(this).children("input").attr("value");
		var url = "<c:url value='/attachList.do'/>";
		$.ajax({                        
	        type: "POST",
	        url: url,
	        dataType: 'html',
	        data:{docId:docId},
	        success: function(data) {
	        $(".listAttachBody").append(data); 
	       }
	    });
		$( ".toggle01" ).css("padding-top",0);
		$( ".toggle02" ).animate({'padding-top':'0'},480); 
		document.getElementById("attachSlide").style.display = "block";
	});
 function approvalReceive4Paper(docID, userId){
		$("input[name=orgdocId]", "#listForm").val(docID);
		$("input[name=isincoming]", "#listForm").val("1");
		$("#formSelect").show();
		//ajax
		 var url = "<c:url value='/formSelectLabel.do'/>";
		$.ajax({                        
	        type: "POST",
	        url: url,
	        dataType: 'html',
	        data:{url:url},
	        success: function(data) {
	        	$(".fromLabel").empty();
	        	$(".fromLabel").append(data); 
	       }
	    });	 
	}

//]]>
</script>
</html>