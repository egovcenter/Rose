<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovCommon.jsp"%>
<html lang="en">
<!doctype html>
<head>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovHeader.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/egovframework/com/uss/ion/token-input.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/css/egovframework/com/uss/ion/token-input-facebook.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/css/egovframework/com/uss/ion/jquery-sortable.css'/>">

<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/ion/jquery-1.7.1.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/ion/jquery-ui-1.11.4.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/ion/jquery.tokeninput.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/ion/json2.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/ion/drafting.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/ion/approval.js'/>"></script>

<link rel="stylesheet" type="text/css" href="<c:url value='/js/egovframework/com/uss/ion/jquery.fileUpload/fileUpload.css'/>" >
<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/ion/jquery.hs.localization.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/ion/jquery.fileUpload/fileUpload.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/ion/jquery.form.js"'/>"></script>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/ion/popup.js"'/>"></script>
<script>
var APPROVAL_CONTEXT = "${pageContext.servletContext.contextPath}";
var userId = "${user.uniqId}";
var userName = "${user.emplyrNm}";
var docId = "${doc.docID}";
var formId = "${doc.formId}";
var orgdocId = "${orgDoc.docID}";
function toggle_content(obj, ID){
	$('.tab_box').each(function(index){
		if(ID == "approval_head"){
			$('.approval_head').addClass('on');
			$('.approval_document').removeClass('on');
		}else{
			$('.approval_head').removeClass('on');
			$('.approval_document').addClass('on');
		}
	});
	$('.tabContent_rapper').each(function(index){
		if($(this).attr('id') == ID){
			if(ID == 'approval_document'){
				applyIncomingSignTable();
			}
			$(this).show();
		}else{
			$(this).hide();
		}
	});
}
function approvalPass(){
	document.getElementById("orgdocId").value = orgdocId;
	document.pageParam.action = "<c:url value='/approvalPass.do'/>";
	document.pageParam.submit();
}
var signerKind = {'SK00' : '<spring:message code="appvl.signerKind.SK00"/>',
	'SK01' : '<spring:message code="appvl.signerKind.SK01"/>',
	'SK02' : '<spring:message code="appvl.signerKind.SK02"/>',
	'SK03' : '<spring:message code="appvl.signerKind.SK03"/>',
	'SK04' : '<spring:message code="appvl.signerKind.SK04"/>'
}
var signerState = {'SS00' : '<spring:message code="appvl.signerState.SS00"/>',
		'SS01' : '<spring:message code="appvl.signerState.SS01"/>',
		'SS02' : '<spring:message code="appvl.signerState.SS02"/>',
		'SS03' : '<spring:message code="appvl.signerState.SS03"/>',
		'SS09' : '<spring:message code="appvl.signerState.SS09"/>'
}
var sendType = {'ST01' : '<spring:message code="appvl.recip.ST01"/>',
				'ST02' : '<spring:message code="appvl.recip.ST02"/>',
				'ST03' : '<spring:message code="appvl.recip.ST03"/>',
				'ST04' : '<spring:message code="appvl.recip.ST04"/>',
				'ST05' : '<spring:message code="appvl.recip.ST05"/>'
};
var appvl_draft_nolabel = "<spring:message code="appvl.draft.nolabel"/>";
var appvl_invalid_signerList_noapprover= "<spring:message code="appvl.invalid.signerList.noapprover"/>";
</script>
</head>

<body>
<div class="wrap"> 
	<!--  Top Menu Start --> 
	<jsp:include page="/WEB-INF/jsp/egovframework/com/cmm/EgovTopMenu.jsp" flush="false">
		<jsp:param value="APPROVAL" name="selectedMenu"/>
	</jsp:include>
	<!--  Top Menu End --> 
	<div class="clear"></div>
	<!-- Container Start -->
	<div class="Container"> 
	<!-- Lnb -->
	<jsp:include page="/WEB-INF/jsp/egovframework/com/uss/ion/cmm/EgovLeftMenu.jsp" flush="false">
		<jsp:param value="register" name="CommonButton"/>
		<jsp:param value="approval" name="CommonTitle"/>
	</jsp:include>
		<!-- Content Start -->
		<div class="Content"> 
			<!-- Content box Start -->
			<div class="content_box"> 
				<div class="h36"></div>
				<!-- Menu Title-->
				<div class="title_box">
					<div class="title_line">
						<h1><spring:message code="appvl.document.title.label.register"/></h1>						
					</div>					
					<div class="but_line">
						<input type="button" value='<spring:message code="appvl.document.button.register"/>' class="but_navy mr05" onClick="javascript:addOpinion('receive');"/>
						<input type="button" value='<spring:message code="appvl.document.button.pass"/>' class="but_navy mr05" onClick="approvalPass();"/>
						<input type="button" value='<spring:message code="common.button.cancel"/>' class="but_grayL" onClick="history.back()"/>
					</div>
					<div class="clear"></div>
				</div>
				<!-- Tab Menu -->
				<div class="tab_box">
					<ul>
						<li class="on approval_head" onclick="javascript:toggle_content(this, 'approval_head')"><a href="#"><spring:message code="appvl.documet.label.head"/></a></li>
						<li class="approval_document" onclick="javascript:toggle_content(this, 'approval_document')"><a href="#"><spring:message code="appvl.documet.label.document"/></a></li>
					</ul>
				</div>			
				<!-- approval_head Start -->
				<div class="tabContent_rapper" id="approval_head">
					<div class="display_table">
						<div class="display_tableCell_top">
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
											<td colspan="2"><div class="ui_input_text"><input type="text" name="draft_title" value="<c:out value="${doc.docTitle}"/>" readonly="readonly"/></div></td>
										</tr>
										<tr>
											<th scope="row"><label for="label_2"><spring:message code="appvl.documet.label.label"/></label></th>
											<td><div class="ui_input_text"><input type="text" value="" id="selectlabelNm" readonly="readonly"/><input type="hidden" value="<c:out value="${doc.lbelId}"/>" id="selectlabelId"/></div></td>
											<td>
												<div class="ui_btn_rapper">
													<a href="#" class="btn_color3 selectLabel" onclick="javascript:openLabelPopup()"><spring:message code="common.button.select"/></a>
												</div>
											</td>
										</tr>
										<tr>
											<th scope="row"><spring:message code="appvl.documet.label.security"/></th>
											<td colspan="2">
												<span class="radio_rapper"><input type="radio" value="99" name="doc_slvl" <c:if test="${doc.docSlvl eq '99'}">checked</c:if> /><label  for="label_3-1"><spring:message code="appvl.documet.label.security.open"/></label></span>
												<span class="radio_rapper"><input type="radio" value="1" name="doc_slvl" <c:if test="${doc.docSlvl eq '1'}">checked</c:if> /><label  for="label_3-2"><spring:message code="appvl.documet.label.security.secret"/></label></span>
											</td>
										</tr>
										<tr>
											<th scope="row"><spring:message code="appvl.documet.label.signerline"/> </th>
											<td colspan="2">
												<div class="float_right ui_btn_rapper"><a href="javascript:assignSignerLine()" class="btn_color3"><spring:message code="appvl.document.button.assign"/></a></div>
											</td>
										</tr>
										<tr>
											<th scope="row"></th>
											<td colspan="2">
												<table summary="/No/Attochment/Title/Poster/Post Date/Expire date" class="board_type_height mb20" id="signer_list_table">
													<caption class="blind"></caption>
													<colgroup>
														<col width="50px"/>
														<col width=""/>
														<col width="200px"/>
														<col width="200px"/>
														<col width="80px"/>
													</colgroup>
													<thead>
														<tr>
															<th scope="col"><span><spring:message code="appvl.documet.label.signerline.table.order"/></span></th>
															<th scope="col"><span><spring:message code="appvl.documet.label.signerline.table.department"/></span></th>	
															<th scope="col"><span><spring:message code="appvl.documet.label.signerline.table.position"/></span></th>
															<th scope="col"><span><spring:message code="appvl.documet.label.signerline.table.name"/></span></th>	
															<th scope="col"><span><spring:message code="appvl.documet.label.signerline.table.type"/></span></th>
														</tr>
													</thead>					
													<tbody>
													<c:forEach var="signer" items="${signerList}" varStatus="status">
													<tr class="unsortable">
														<td>
															<div class="signer_seq"><c:out value="${signer.signSeq}"/></div>
															<div class="signer_id" style="display:none;"><c:out value="${signer.signerID}"/></div>
															<div class="signer_kind" style="display:none;"><c:out value="${signer.signKind}"/></div>
															<div class="signer_signstate" style="display:none;"><c:out value="${signer.signState}"/></div>
															<div class="signer_signdate" style="display:none;"><c:out value="${signer.signDate}"/></div>
															<div class="signer_userid" style="display:none;"><c:out value="${signer.userID}"/></div>
															<div class="signer_deptid" style="display:none;"><c:out value="${signer.signerDeptID}"/></div>
															<div class="signer_dutyname" style="display:none;"><c:out value="${signer.signerDutyName}"/></div>
															<div class="signer_opinion" style="display:none;"><c:out value="${signer.opinion}"/></div>
														</td>
														<td>
															<div class="signer_deptname"><c:out value="${signer.signerDeptName}"/></div>
														</td>
														<td>
															<div class="signer_positionname"><c:out value="${signer.signerPositionName}"/></div>
														</td>
														<td>
															<div class="signer_signername"><c:out value="${signer.signerName}"/></div>
														</td>
														<td>
															<c:choose>
																<c:when test="${signer.signKind == 'SK00'}">
																	<div class="signer_kind_text"><spring:message code="appvl.signerKind.SK00"/></div>
																</c:when>
																<c:when test="${signer.signKind == 'SK01'}">
																	<div class="signer_kind_text"><spring:message code="appvl.signerKind.SK01"/></div>
																</c:when>
																<c:when test="${signer.signKind == 'SK02'}">
																	<div class="signer_kind_text"><spring:message code="appvl.signerKind.SK02"/></div>
																</c:when>
																<c:when test="${signer.signKind == 'SK03'}">
																	<div class="signer_kind_text"><spring:message code="appvl.signerKind.SK03"/></div>
																</c:when>
																<c:when test="${signer.signKind == 'SK04'}">
																	<div class="signer_kind_text"><spring:message code="appvl.signerKind.SK04"/></div>
																</c:when>
															</c:choose>
														</td>
													</tr>
													</c:forEach>
													</tbody>
												</table>
											</td>
										</tr>
										<tr>
											<th scope="row"><spring:message code="appvl.documet.label.attachment"/> </th>
											<td colspan="2">
												<c:if test="${not empty attachList and not empty orgAttachList}">
												<div class="float_right ui_btn_rapper">
													<a href="#" class="btn_color3"><spring:message code="common.button.select"/></a>
													<a href="#" class="btn_color2"><spring:message code="common.button.delete"/></a>
												</div>
												</c:if>
											</td>
										</tr>
										<tr>
											<th scope="row"></th>
											<td colspan="2">
												<table summary="/No/Attochment/Title/Poster/Post Date/Expire date" class="board_type_height mb20" id="attach_list_table">
													<caption class="blind"></caption>
													<colgroup>
														<col width="40px"/>
														<col width="*"/>
														<col width="15%"/>
													</colgroup>
													<thead>
														<tr>
															<th scope="col" class="align_center"><img src="<c:url value='/images/egovframework/com/uss/cmm/icon_check.png'/>" alt="icon_check"></th>
															<th scope="col" class=""><span><spring:message code="appvl.documet.label.attachment.table.filename"/></span></th>
															<th scope="col" class=""><span><spring:message code="appvl.documet.label.attachment.table.filesize"/></span></th>
														</tr>
													</thead>					
													<tbody>
													<c:if test="${empty attachList and empty orgAttachList}">
														<tr><td colspan="3"><spring:message code="appvl.documet.label.attachment.noattach"/></td></tr>
													</c:if>
													<c:if test="${not empty attachList or not empty orgAttachList}">
													<c:forEach var="attach" items="${orgAttachList}">
														<tr>
															<td class="align_center"><input type="checkbox" value="<c:out value="${attach.attachID}"/>" name="attachId" checked="checked" disabled="disabled"/></td>
															<td><a href="javascript:downloadFile('<c:out value="${pageContext.servletContext.contextPath}"/>/downloadAttach.do', '<c:out value="${attach.docID}"/>','<c:out value="${attach.attachID}"/>','<c:out value="${user.uniqId}"/>')"><c:out value="${attach.attachNm}"/></a></td>
															<td>
																<c:choose>
																	<c:when test="${attach.attachSize / 1024 < 1}">1 KB</c:when>
																	<c:when test="${attach.attachSize / (1024*1024) < 1}"><fmt:formatNumber value="${attach.attachSize / 1024}" groupingUsed="true" maxFractionDigits="0"/> KB</c:when>
																	<c:otherwise><fmt:formatNumber value="${attach.attachSize /  (1024*1024)}" groupingUsed="true" maxFractionDigits="0"/> MB</c:otherwise>
																</c:choose>
															</td>
														</tr>
													</c:forEach>
													<c:forEach var="attach" items="${attachList}">
														<tr>
															<td class="align_center"><input type="checkbox" value="<c:out value="${attach.attachID}"/>" name="attachId" checked="checked" disabled="disabled"/></td>
															<td><a href="javascript:downloadFile('<c:out value="${pageContext.servletContext.contextPath}"/>/downloadAttach.do', '<c:out value="${attach.docID}"/>','<c:out value="${attach.attachID}"/>','<c:out value="${user.uniqId}"/>')"><c:out value="${attach.attachNm}"/></a></td>
															<td>
																<c:choose>
																	<c:when test="${attach.attachSize / 1024 < 1}">1 KB</c:when>
																	<c:when test="${attach.attachSize / (1024*1024) < 1}"><fmt:formatNumber value="${attach.attachSize / 1024}" groupingUsed="true" maxFractionDigits="0"/> KB</c:when>
																	<c:otherwise><fmt:formatNumber value="${attach.attachSize /  (1024*1024)}" groupingUsed="true" maxFractionDigits="0"/> MB</c:otherwise>
																</c:choose>
															</td>
														</tr>
													</c:forEach>
													</c:if>
													</tbody>
												</table>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>								
				</div>
				<!-- approval_head End -->
				<!-- approval_document Start-->
				<div class="tabContent_rapper" id="approval_document" style="display: none">
					<div class="table_rapper">
						<form id="draft_form" name="draft_form" action="<c:out value="${pageContext.servletContext.contextPath}"/>/received.do" method="post">
						<div class="print_rapper" id="draft_body">
							<c:out value="${docBody}" escapeXml="false"/>
						</div>
						</form>
					</div>			
				</div>
				<!-- approval_document End -->
			</div>
			<!-- Content box End -->
		</div>
		<!-- Content End --> 
	</div>
	<!-- Container End -->
	<div id="div_popup" style="display: none"></div>
	<form action="" id="pageParam" name="pageParam" method="post">
		<input type="hidden" id="docId" name="docId" value="">
		<input type="hidden" id="orgdocId" name="orgdocId" value="">
	</form>
</div>
<!-- wrap End -->
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
  $( ".statusSlide_rapper" ).slideDown( 500, function() {});
});
$( ".btn_popup_close" ).click(function() {
  $( ".statusSlide_rapper" ).slideUp( 500, function() {});
});
//]]>
</script>
</html>