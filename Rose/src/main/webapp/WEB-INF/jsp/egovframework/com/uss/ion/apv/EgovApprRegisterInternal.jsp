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
function closeRegisterInternal(){
	history.back();
}
function selectedLabel(obj){
	$(".labelSelect").css('background-color', '#fff');
	$(".relabelSelect").css('background-color', '#fff');
	var backColor = $(obj).css( "background-color" );
	if(backColor == "rgb(255, 255, 255)"){
		$(obj).css('background-color', '#f3f3f3');
		<c:choose>
			<c:when test="${not empty param.selectedLabelID and not empty param.selectedLabelNm}">
			$("#<c:out value="${param.selectedLabelID}"/>").val($(obj).attr("id"));
			$("#<c:out value="${param.selectedLabelNm}"/>").val($(obj).attr("nm"));
			</c:when>
			<c:otherwise>
			document.getElementById("selectedLabelID").value = $(obj).attr("id");
			document.getElementById("selectedLabelNm").value = $(obj).attr("nm");
			</c:otherwise>
		</c:choose>
	}else {
		$(obj).css('background-color', '#f3f3f3');
	}
}
function select_content(obj){
	$(".relabelSelect").css('background-color', '#fff');
	var child  =  $(obj).attr("child");
	if(child == '0'){
		$(".labelSelect").css('background-color', '#fff');
		var backColor = $(obj).css( "background-color" );
		if(backColor == "rgb(243, 243, 243)"){
			$(obj).css('background-color', '#fff');
		}else {
			$(obj).css('background-color', '#f3f3f3');
			document.getElementById("selectedLabelID").value = $(obj).attr("id");
			document.getElementById("selectedLabelNm").value = $(obj).attr("nm");
		}
	}
}
var appvl_draft_noattach = "<spring:message code="appvl.draft.noattach"/>";
var appvl_draft_notitle = "<spring:message code="appvl.draft.notitle"/>";

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
		<form:form commandName="formRegister" name="formRegister" method="post" id="formRegister" action="${pageContext.servletContext.contextPath}/registeredInternal.do" enctype="multipart/form-data" >
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
						<h1><spring:message code="appvl.reglabeldoc.title"/></h1>						
					</div>					
					<div class="ui_btn_rapper float_right mtb10">
						<a href="javascript:registeredInternal('formRegister')" class="btn_color1"><spring:message code="appvl.document.button.register"/></a>
						<a href="javascript:closeRegisterInternal()" class="btn_color2"><spring:message code="common.button.cancel"/></a>
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
								<td colspan="2"><div class="ui_input_text"><input type="text" value="" name="register_draft_title" id="register_draft_title"/></div></td>
							</tr>
							<tr>
								<th scope="row"><label for="label_2"><spring:message code="appvl.documet.label.label"/></label></th>
								<td colspan="1"><div class="ui_input_text">
													<input type="text" value="<c:out value="${labelNm}"/>" name="register_selectlabelNm" id="register_selectlabelNm" readonly/>
													<input type="hidden" value="<c:out value="${labelId}"/>" name="register_selectlabelId" id="register_selectlabelId"/>
												</div>
								</td>
								<td>
									<div class="ui_btn_rapper">
										<a href="javascript:openLabelPopup()" class="btn_color3 selectLabel"><spring:message code="common.button.select"/></a>
									</div>
								</td>
							</tr>
							<tr>
								<th scope="row"><spring:message code="appvl.documet.label.security"/></th>
								<td colspan="2">
									<span class="radio_rapper"><input type="radio" value="99" name="doc_slvl" checked/><label  for="label_3-1"><spring:message code="appvl.documet.label.security.open"/></label></span>
									<span class="radio_rapper"><input type="radio" value="1" name="doc_slvl"/><label  for="label_3-2"><spring:message code="appvl.documet.label.security.secret"/></label></span>
								</td>
							</tr>
							<tr>
								<th scope="row"><label for="sngr_user_nm"><spring:message code="appvl.reglabeldoc.label.registername"/></label></th>
								<td colspan="2"><div class="ui_input_text">
									<input type="text" value="<c:out value="${user.emplyrNm}"/>" name="selectedUserNm" id="selectedUserNm" readonly/>
									<input type="hidden" value="<c:out value="${user.uniqId}"/>" name="selectedUserID" id="selectedUserID"/>
								</div></td>
								<!-- 20160404_SUJI.H <td>
								<div class="ui_btn_rapper"><a href="javascript:selectUser('uni')" class="btn_color3"><spring:message code="appvl.document.button.assign"/></a></div>
								</td> -->
							</tr>
							<tr>
								<th scope="row"><label for="label_7"><spring:message code="appvl.reglabeldoc.label.registerdept"/></label></th>
								<td colspan="2"><div class="ui_input_text">
									<input type="hidden" value="<c:out value="${user.uniqId}"/>" name="selectedDeptId" id="selectedDeptId"/>
									<input type="text" value="<c:out value="${user.orgnztNm}"/>" name="selectedDeptNm" id="selectedDeptNm" readonly/>
								</div></td>
							</tr>
							<tr>
								<th scope="row"><label for="register_recp_s_docnum"><spring:message code="appvl.reglabeldoc.label.documnumber"/></label></th>
								<td colspan="2"><div class="ui_input_text"><input type="text" value="" name="draft_docnum" id="draft_docnum"/></div></td>
							</tr>
							<tr>
								<th scope="row"><spring:message code="appvl.documet.label.signerline.table.opinion"/></th>
								<td colspan="2" style="padding-left: 10px;">
									<textarea id="opinion" name="opinion" rows="5" maxlength="100" style="width: 100%;"></textarea>
								</td>									
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
function countAttachFile(){
	jQuery("#attechCnt").text(jQuery("#registerFileUpload .filename").size());
}
//]]>
</script>
</html>