<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
<script type="text/javascript" src="<c:url value='/js/egovframework/com/cmm/fms/EgovMultiFile.js'/>" ></script>
<script type="text/javascript" src="<c:url value="/validator.do"/>"></script>
<script language="javascript" type="text/javascript">
var APPROVAL_CONTEXT = "${pageContext.servletContext.contextPath}";
var userId = "${user.uniqId}";
var deptID = "${user.orgnztId}";
var registerFileUpload = false;
var registerFileCountLimit = 10;
var registerFileLengthLimit = 1024 * 1024 * 100;
var registerAttachListTableId = "register_attach_list_table";
function closeRegisterIncoming(){
	history.back();
}
var appvl_draft_nolabel = "<spring:message code="appvl.draft.nolabel"/>";
var appvl_draft_notitle = "<spring:message code="appvl.draft.notitle"/>";
var appvl_draft_noattach = "<spring:message code="appvl.draft.noattach"/>";
var appvl_draft_noregdept = "<spring:message code="appvl.draft.noregdept"/>";
var appvl_draft_nodocnum = "<spring:message code="appvl.draft.nodocnum"/>";

function makeFileAttachment(){
	var maxFileNum =5;
	
	if(maxFileNum==null || maxFileNum==""){
		maxFileNum = 3;
	}
	var multi_selector = new MultiSelector( document.getElementById( 'egovComFileList' ), maxFileNum, '${pageContext.request.contextPath}');
	multi_selector.addElement( document.getElementById( 'egovComFileUploader' ) );
}
</script>
</head>
<body onLoad="makeFileAttachment();">
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
		<jsp:param value="draft" name="CommonButton"/>
		<jsp:param value="approval" name="CommonTitle"/>
	</jsp:include>
		<form:form commandName="formRegister" name="formRegister" method="post" id="formRegister" action="${pageContext.servletContext.contextPath}/registeredIncoming.do" enctype="multipart/form-data" >
		<input type="hidden" id="fileAtchPosblAt" name="fileAtchPosblAt" value="" />
		<input type="hidden" id="posblAtchFileNumber" name="posblAtchFileNumber" value="5" />
		<input type="hidden" id="posblAtchFileSize" name="posblAtchFileSize" value="" />
		<!-- Content Start -->
		<div class="Content"> 
			<!-- Content box Start -->
			<div class="content_box"> 
				<div class="h36"></div>
				<!-- Menu Title-->
				<div class="title_box">
					<div class="title_line">
						<h1><spring:message code="appvl.regincomingdoc.title"/></h1>						
					</div>					
					<div class="ui_btn_rapper float_right mtb10">
						<a href="javascript:registeredIncomming('formRegister')" class="btn_color1"><spring:message code="appvl.document.button.register"/></a>
						<a href="javascript:closeRegisterIncoming()" class="btn_color2"><spring:message code="common.button.cancel"/></a>
					</div>
					<div class="clear"></div>
				</div>		
				<div class="table_rapper">
					<table summary="" class="board_width_borderNone">
						<caption class="blind"></caption>
						<colgroup>
							<col width="140px"/>
							<col width="*"/>
							<col width="85px"/>
						</colgroup>
						<tbody>
							<tr>
								<th scope="row"><label for="register_draft_title"><spring:message code="appvl.documet.label.title"/></label></th>
								<td colspan="2"><div class="ui_input_text"><input type="text" value="<c:out value="${registerDoc.docTitle}"/>" name="register_draft_title" id="register_draft_title"/></div></td>
							</tr>
							<!--
							<tr>
								<th scope="row"><label for="register_selectlabelNm">Label</label></th>
								<td colspan="1"><div class="ui_input_text"><input type="text" value="" id="register_selectlabelNm"/><input type="hidden" value="<c:out value="${registerDoc.lbelID}"/>" id="register_selectlabelId"/></div></td>
								<td>
									<div class="ui_btn_rapper">
										<a href="#" class="btn_color3 selectLabel" onclick="javascript:openLabelPopup()">Select</a>
									</div>
								</td>
							</tr>
							-->
							<!-- 20160315_SUJI.H 
							<tr>
								<th scope="row"><spring:message code="appvl.documet.label.security"/></th>
								<td colspan="2">
									<span class="radio_rapper"><input type="radio" value="99" name="doc_slvl" checked/><label  for="label_3-1"><spring:message code="appvl.documet.label.security.open"/></label></span>
									<span class="radio_rapper"><input type="radio" value="1" name="doc_slvl"/><label  for="label_3-2"><spring:message code="appvl.documet.label.security.secret"/></label></span>
								</td>
							</tr>
								20160315_SUJI.H -->
							<tr>
								<th scope="row"><label for="register_recp_dept_nm"><spring:message code="appvl.regincomingdoc.label.sender.department"/></label></th>
								<td colspan="2"><div class="ui_input_text"><input type="text" value="" name="register_recp_s_dept_nm" id="register_recp_s_dept_nm"/></div></td>
							</tr>
							<tr>
								<th scope="row"><label for="register_recp_s_posi_nm"><spring:message code="appvl.regincomingdoc.label.sender.position"/></label></th>
								<td colspan="2"><div class="ui_input_text"><input type="text" value="" name="register_recp_s_posi_nm" id="register_recp_s_posi_nm"/></div></td>
							</tr>
							<tr>
								<th scope="row"><label for="register_recp_s_user_nm"><spring:message code="appvl.regincomingdoc.label.sender.name"/></label></th>
								<td colspan="2"><div class="ui_input_text"><input type="text" value="" name="register_recp_s_user_nm" id="register_recp_s_user_nm"/></div></td>
							</tr>
							<tr>
								<th scope="row"><label for="register_recp_s_docnum"><spring:message code="appvl.regincomingdoc.label.documnumber"/></label></th>
								<td colspan="2"><div class="ui_input_text"><input type="text" value="" name="register_recp_s_docnum" id="register_recp_s_docnum"/></div></td>
							</tr>
							<tr>
								<th scope="row"><label for="register_recp_dept_nm"><spring:message code="appvl.regincomingdoc.label.register.department"/></label></th>
								<td >
								<div class="ui_input_text">
									<input class="recipient_deptid"  name="recipient_deptid" id="recipient_deptid" type="hidden" value="">
									<input class="recipient_deptname"  name="recipient_deptname" id="recipient_deptname" type="text" value="" readonly>
								</div></td>
								<td>
									<div class="ui_btn_rapper"><a href="javascript:openOrgPopup('uni')" class="btn_color3"><spring:message code="appvl.document.button.assign"/></a></div>
								</td>
							</tr>
							<!--
							<tr>
								<th scope="row"><label for="register_recp_r_posi_nm">Register Position</label></th>
								<td colspan="2"><div class="ui_input_text"><input type="text" value="" name="register_recp_r_posi_nm" id="register_recp_r_posi_nm"/></div></td>
							</tr>
							<tr>
								<th scope="row"><label for="register_recp_r_user_nm">Register Name</label></th>
								<td colspan="2"><div class="ui_input_text"><input type="text" value="" name="register_recp_r_user_nm" id="register_recp_r_user_nm"/></div></td>
							</tr>
							-->				
							<tr>
								<th scope="row"><spring:message code="appvl.documet.label.attachment"/> </th>
								<td colspan="2">
									<table width="100%" cellspacing="0" cellpadding="0" border="0" align="center">
										<tr>
											<td><input name="file_1" id="egovComFileUploader" type="file" title="첨부파일입력"/></td>
										</tr>
										<tr>
											<td>
												<div id="egovComFileList"></div>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<th scope="row"></th>
								<td colspan="2">
									<c:if test="${not empty attachList}">
									<div class="float_right ui_btn_rapper">
										<a href="#" class="btn_color3"><spring:message code="common.button.select"/></a>
										<a href="#" class="btn_color2"><spring:message code="common.button.delete"/></a>
									</div>
									</c:if>
								</td>
							</tr>			
						</tbody>
					</table>
				</div>				
			</div>
			<!-- Content box End -->
		</div>
		<!-- Content End --> 
		</form:form>
	</div>
	<!-- Container End -->
	<div id="div_popup" style="display: none"></div>
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