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
<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/ion/jquery.form.js"'/>"></script>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/ion/popup.js"'/>"></script>
<script>
var APPROVAL_CONTEXT = "${pageContext.servletContext.contextPath}";
var userId = "${user.uniqId}";
var userName = "${user.emplyrNm}";
var docId = "${doc.docID}";
var formId = "${formId}";
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
function doIncoming() {
	var obj = getPreAttachedList();
	$("#preAttachedFileList").val(JSON.stringify(obj));
	addOpinion('redraftForIncoming');
}
function getPreAttachedList() {
    var idList = new Array();
    $('input:checkbox[name=attachId]').each(function() {
        var attachId = $(this).val();
        if (attachId != undefined) {
            idList.push({"attachId" : attachId});
    	}
    });
    return idList;
}
function settingDocumentContent(){
 	var contentHtml = $("#editorContent").html();
 	document.getElementById("hiddenContent").value = contentHtml;
	if(contentHtml != null){
		$('div#editorContent').replaceWith('<iframe id="iframe_content" scrolling="yes"></iframe>');
		settingIframe(contentHtml);
	}else{
		settingIframe();
	}
}
function autoResize(id) {
	var iframeBodyHeight = document.getElementById(id).contentWindow.document.body.scrollHeight;
	var iframe = document.all(id);
	iframe.style.height = iframeBodyHeight;
}
function settingIframe(newContent) {
	if(newContent != null){
		var styleSheet = "<link rel='stylesheet' type='text/css' href='html/egovframework/com/cmm/utl/ckeditor/contents.css'>"
		var iframeHead = $("#iframe_content").contents().find("head");
		iframeHead.append(styleSheet);
		
		var iframeBody = $("#iframe_content").contents().find("body");
		iframeBody.append(newContent);
		autoResize("iframe_content");
	}
}
function replaceAll(str, searchStr, replaceStr) {
    return str.split(searchStr).join(replaceStr);
}
$(function(){
	settingDocumentContent();
});
var signerKind = {
		'SK00' : '<spring:message code="appvl.signerKind.SK00"/>',
		'SK01' : '<spring:message code="appvl.signerKind.SK01"/>',
		'SK02' : '<spring:message code="appvl.signerKind.SK02"/>',
		'SK03' : '<spring:message code="appvl.signerKind.SK03"/>',
		'SK04' : '<spring:message code="appvl.signerKind.SK04"/>'
}
var signerState = {
	'SS00' : '<spring:message code="appvl.signerState.SS00"/>',
	'SS01' : '<spring:message code="appvl.signerState.SS01"/>',
	'SS02' : '<spring:message code="appvl.signerState.SS02"/>',
	'SS03' : '<spring:message code="appvl.signerState.SS03"/>',
	'SS09' : '<spring:message code="appvl.signerState.SS09"/>'
}
var sendType = {
	'ST01' : '<spring:message code="appvl.recip.ST01"/>',
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
						<h1><spring:message code="appvl.document.title.label.redraft"/></h1>						
					</div>					
					<div class="but_line">
						<input type="button" id="redraft_button" value='<spring:message code="appvl.sidemenu.button.draft"/>' class="but_navy mr05" onClick="javascript:doIncoming();"/>
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
											<td colspan="2"><div class="ui_input_text" style="border-bottom:1px solid #e1e1e1;"><c:out value="${doc.docTitle}"/></div></td>
										</tr>
										<tr>
											<th scope="row"><label for="label_2"><spring:message code="appvl.documet.label.label"/></label></th>
											<td><div class="ui_input_text"><input type="text" value="<c:out value="${label.labelNm}"/>" id="selectlabelNm" readonly="readonly"/><input type="hidden" value="<c:out value="${doc.lbelId}"/>" id="selectlabelId"/></div></td>
											<td>
												<div class="ui_btn_rapper">
													<a href="#" class="btn_color3 selectLabel" onclick="javascript:openLabelPopup()"><spring:message code="common.button.select"/></a>
												</div>
											</td>
										</tr>
										<tr>
											<th scope="row"><spring:message code="appvl.documet.label.security"/></th>
											<td colspan="2">
												 <div class="ui_input_text" style="border-bottom:1px solid #e1e1e1;">
													<c:if test="${doc.docSlvl eq '99'}"><label  for="label_3-1"><spring:message code="appvl.documet.label.security.open"/></label></c:if>
													<c:if test="${doc.docSlvl eq '1'}"><label  for="label_3-2"><spring:message code="appvl.documet.label.security.secret"/></label></c:if>
												</div>
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
														<col width="200px"/>
														<col width="80px"/>
														<col width="90px"/>
														<col width="120px"/>
														<col width="60px"/>
														<col width="*"/>
													</colgroup>
													<thead>
														<tr>
															<th scope="col"><span><spring:message code="appvl.documet.label.signerline.table.order"/></span></th>
															<th scope="col"><span><spring:message code="appvl.documet.label.signerline.table.name"/></span></th>	
															<th scope="col"><span><spring:message code="appvl.documet.label.signerline.table.type"/></span></th>
															<th scope="col"><span><spring:message code="appvl.documet.label.signerline.table.status"/></span></th>
															<th scope="col"><span><spring:message code="appvl.documet.label.signerline.table.date"/></span></th>
															<th scope="col"><span><spring:message code="appvl.documet.label.signerline.table.version"/></span></th>
															<th scope="col"><span><spring:message code="appvl.documet.label.signerline.table.opinion"/></span></th>	
														</tr>
													</thead>					
													<tbody>
													<c:forEach var="signer" items="${signerList}" varStatus="status">
													<tr class="unsortable">
														<td>
															<div class="signer_seq"><c:out value="${signer.signSeq}"/></div>
															<div class="signer_id" style="display:none;"><c:out value="${signer.signerID}"/></div>
															<div class="signer_kind" style="display:none;"><c:out value="${signer.signKind}"/></div>
															<div class="signer_signstate" style="display:none;"><c:out value="SS00"/></div>
															<div class="signer_userid" style="display:none;"><c:out value="${signer.userID}"/></div>
															<div class="signer_deptid" style="display:none;"><c:out value="${signer.signerDeptID}"/></div>
															<div class="signer_deptname" style="display:none;"><c:out value="${signer.signerDeptName}"/></div>
															<div class="signer_positionname" style="display:none;"><c:out value="${signer.signerPositionName}"/></div>
															<div class="signer_dutyname" style="display:none;"><c:out value="${signer.signerDutyName}"/></div>
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
														<td>
															<c:choose>
																<c:when test="${signer.signState == 'SS00'}">
																	<div class="signer_state_"><spring:message code="appvl.signerState.SS00"/></div>
																</c:when>
																<c:when test="${signer.signState == 'SS01'}">
																	<div class="signer_state_"><spring:message code="appvl.signerState.SS01"/></div>
																</c:when>
																<c:when test="${signer.signState == 'SS02'}">
																	<div class="signer_state_"><spring:message code="appvl.signerState.SS02"/></div>
																</c:when>
																<c:when test="${signer.signState == 'SS03'}">
																	<div class="signer_state_"><spring:message code="appvl.signerState.SS03"/></div>
																</c:when>
																<c:when test="${signer.signState == 'SS09'}">
																	<div class="signer_state_"><spring:message code="appvl.signerState.SS09"/></div>
																</c:when>
															</c:choose>
														</td>
														<td>
															<div class="signer_signDate">
																<c:if test="${signer.signState ne 'SS03' and signer.signState ne 'SS00'}">
																	<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${signer.signDate}"/>
																</c:if>
															</div>
														</td>
														<td>
															<div class="signer_docVersion">
																<c:if test="${signer.docVersion ne '0'}">
																	<c:if test="${signer.signState ne 'SS03' and signer.signState ne 'SS00'}">
																		<a href="javascript:documentPopup('view','${signer.docVersion}')"><fmt:formatNumber pattern=".0" value="${signer.docVersion}"/></a>
																	</c:if>	
																</c:if>	
															</div>
														</td>														
														<td>
															<div class="signer_opinion">
															<c:if test="${signer.signState ne 'SS00' and signer.signState ne 'SS03'}">
																<c:if test="${not empty signer.opinion}"><a href="javascript:opinionView('${signer.opinion}')"><c:out value="${signer.opinion}"/></a></c:if> 
																<c:if test="${empty signer.opinion}"><spring:message code="appvl.approvalhistory.sign.table.no_opinion"/></c:if>
															</c:if> 					
															</div>								
														</td>
													</tr>
													</c:forEach>
													</tbody>
												</table>
											</td>
										</tr>											
										<tr>
											<th scope="row"><spring:message code="appvl.documet.label.attachment"/></th>
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
													<c:if test="${empty attachList}">
														<tr><td colspan="3"><spring:message code="appvl.documet.label.attachment.noattach"/></td></tr>
													</c:if>
													<c:if test="${not empty attachList}">
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
						<form id="draft_form" name="draft_form" action="<c:out value="${pageContext.servletContext.contextPath}"/>/redraftForIncoming.do" method="post">
						<input type="hidden" id="preAttachedFileList" name="preAttachedFileList" value="" />
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
		<input type="hidden" id="hiddenContent" name="hiddenContent" value="">
	</form>
	<form id="popupForm" name="popupForm" method="post" action="${pageContext.servletContext.contextPath}/documentEditor.do" target="">
		<input type="hidden" id="docId" name="docId" value="${doc.docID}" />
		<input type="hidden" id="action" name="action" value="" />
		<input type="hidden" id="docVersion" name="docVersion" value="" />
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