<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.*"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovCommon.jsp"%>
<%
 /**
  * @Class Name : EgovNoticeInqire.jsp
  * @Description : 게시물 조회 화면
  * @Modification Information
  * @
  * @  수정일      수정자            수정내용
  * @ -------        --------    ---------------------------
  * @ 2009.03.23   이삼섭          최초 생성
  * @ 2009.06.26   한성곤          2단계 기능 추가 (댓글관리, 만족도조사)
  *
  *  @author 공통서비스 개발팀 이삼섭
  *  @since 2009.03.23
  *  @version 1.0
  *  @see
  *
  */
%>
<html lang="en">
<head>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovHeader.jsp"%>
<link href="<c:url value='${brdMstrVO.tmplatCours}' />" rel="stylesheet" type="text/css">

<c:if test="${anonymous == 'true'}"><c:set var="prefix" value="/anonymous"/></c:if>
<script type="text/javascript">
function onloading() {
		if ("<c:out value='${msg}'/>" != "") {
			alert("<c:out value='${msg}'/>");
		}
	}

	function fn_egov_select_noticeList(pageNo) {
		//document.frm.pageIndex.value = pageNo;
		document.frm.action = "<c:url value='/cop/bbs${prefix}/selectBoardList.do'/>";
		document.frm.submit();
	}

	function fn_egov_moveUpdt_notice() {
		document.frm.action = "<c:url value='/cop/bbs${prefix}/forUpdateBoardArticle.do'/>";
		document.frm.submit();
	}

	function fn_egov_addReply() {
		document.frm.action = "<c:url value='/cop/bbs${prefix}/addReplyBoardArticle.do'/>";
		document.frm.submit();
	}
	
	function fn_egov_delete_notice() {
		if (confirm('<spring:message code="common.delete.msg" />')) {
			document.frm.action = "<c:url value='/cop/bbs${prefix}/deleteBoardArticle.do'/>";
			document.frm.submit();
		}
	}
	/* TBS 2016.02.11 start */
	function fn_egov_register() {
		document.frame.action = "<c:url value='/cop/bbs/addBoardArticle.do'/>";
		document.frame.submit();
	}
	/* TBS 2016.02.11 end */
</script>
<!-- 2009.06.29 : 2단계 기능 추가  -->
<c:if test="${useComment == 'true'}">
<c:import url="/cop/cmt/selectCommentList.do" charEncoding="utf-8">
	<c:param name="type" value="head" />
</c:import>
</c:if>
<c:if test="${useSatisfaction == 'true'}">
<c:import url="/cop/stf/selectSatisfactionList.do" charEncoding="utf-8">
	<c:param name="type" value="head" />
</c:import>
</c:if>
<c:if test="${useScrap == 'true'}">
<script type="text/javascript">
	function fn_egov_addScrap() {
		document.frm.action = "<c:url value='/cop/scp/addScrap.do'/>";
		document.frm.submit();
	}
</script>
</c:if>
<!-- 2009.06.29 : 2단계 기능 추가  -->
</head>
<body onload="onloading();">

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
			<%-- TBS 2016.02.11 start --%> 
			<form name="frame" action ="<c:url value='/cop/bbs/addBoardArticle.do'/>" method="post">
			<input type="hidden" name="bbsId" value="<c:out value='${result.bbsId}'/>" />
			<input type="hidden" name="bbsNm" value="<c:out value='${result.bbsNm}'/>" />
			<input type="hidden" name="nttId"  value="0" />
			<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>"/>
			<%-- TBS 2016.02.11 end --%> 
			<!-- lnb별 공통 버튼 -->
			<div class="lnb_butbox" id="lnb_butbox">
				<input type="submit" value='<spring:message code="appvl.sidemenu.button.posting"/>' title="<spring:message code="appvl.sidemenu.button.posting"/>" class="but_big" onClick="fn_egov_register();"/>
			</div>			
			<!-- 트리 삽입 영역 -->
			<div class="lnb_tree" style="background:#FFF;">
				<jsp:include page="./cmm/EgovBBSTree.jsp" flush="false" />
			</div>
			<%-- TBS 2016.02.11 start --%> 
			</form>
			<%-- TBS 2016.02.11 end --%> 
		</div>
</div>		
		<!-- Content start -->
		<!-- TBS 2016.02.04 start -->
		<form name="frm" method="post" action="">
		<input type="hidden" name="pageIndex" value="<c:out value='${searchVO.pageIndex}'/>">
		<input type="hidden" name="bbsId" value="<c:out value='${result.bbsId}'/>" >
		<input type="hidden" name="bbsNm" value="<c:out value='${result.bbsNm}'/>" >
		<input type="hidden" name="nttId" value="<c:out value='${result.nttId}'/>" >
		<input type="hidden" name="parnts" value="<c:out value='${result.parnts}'/>" >
		<input type="hidden" name="sortOrdr" value="<c:out value='${result.sortOrdr}'/>" >
		<input type="hidden" name="replyLc" value="<c:out value='${result.replyLc}'/>" >
		<input type="hidden" name="nttSj" value="<c:out value='${result.nttSj}'/>" >
		<div class="Content"> 
		<!-- Content box start -->
			<div class="content_box">
				<div class="h36"></div>
				<!-- 공통 타이틀 버튼 라인 -->
				<div class="title_box">
					<div class="title_line"><h1><c:out value='${result.bbsNm}'/> :: Inqire</h1></div>
					<div class="but_line">
						<c:if test="${result.frstRegisterId == sessionUniqId}">
							<input type="submit" value='Update' title="Update" class="but_navy mr05" onClick="javascript:fn_egov_moveUpdt_notice();return false;"/>
							<input type="submit" value='Delete' title="Delete" class="but_grayL" onClick="javascript:fn_egov_delete_notice();return false;"/>
						</c:if>
						<input type="submit" value='List' title="List" class="but_grayL" onClick="javascript:fn_egov_select_noticeList();return false;"/>
					</div>
					<div class="clear"></div>
				</div>
				<div class="tabContent_rapper" id="approval_head">
					<div class="display_table">
						<div class="display_tableCell">
							<div class="table_rapper">
								<table summary="" class="board_width_borderNone">
									<caption class="blind"></caption>
									<colgroup>
										<col width="100px"/>
										<col width="*"/>
										<col width="85px"/>
									</colgroup>
									<tbody>
										<tr>
											<th scope="row"><label for="label_1"><spring:message code="appvl.documet.label.title"/></label></th>
											<td colspan="2"><div class="ui_input_text" style="border-bottom:1px solid #e1e1e1;"><c:out value="${result.nttSj}"/></div></td>
										</tr>
										<tr>
											<th scope="row"><label for="label_2">Writer</label></th>
											<td colspan="2"><div class="ui_input_text" style="border-bottom:1px solid #e1e1e1;"><c:out value="${result.frstRegisterNm}"/></div></td>
											
										</tr>	
										<tr>
											<th scope="row"><label for="label_3">Content</label></th>
											<td>
												<div style="height: 500px; border:#e1e1e1 1px solid; ">
													<c:out value="${result.nttCn}" escapeXml="false" />
												</div>
											</td>
										</tr>									
										<tr>
											<th scope="row"><spring:message code="appvl.documet.label.attachment"/> </th>
											<td colspan="2">
												<c:if test="${not empty result.atchFileId}">
												<div>
													<c:import url="/cmm/fms/selectFileInfs.do" charEncoding="utf-8">
														<c:param name="param_atchFileId" value="${result.atchFileId}" />
													</c:import>
												</div>
												</c:if>
												<c:if test="${empty result.atchFileId}">
													<div><c:out value="No Files"/></div>
												</c:if>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>						
					</div>								
				</div>
				<!-- //tabContent_rapper head -->
			</div>
			<!-- Content box end --> 
		</div>
		<!-- Content end -->
	</div>
</form>
<div id="div_popup" style="display: none">
</div>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovCopyright.jsp" %>
</body>
<!-- TBS 2016.02.04 end -->
</html>
