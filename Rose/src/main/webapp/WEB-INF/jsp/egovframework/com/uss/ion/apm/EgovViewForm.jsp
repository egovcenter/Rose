<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.util.*"%>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovCommon.jsp"%>
<!doctype html>
<html lang="en">
<head>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovHeader.jsp"%>
<script src="<c:url value='/js/egovframework/com/uss/omt/orgCommon.js'/>"></script>
<script src="<c:url value='/js/egovframework/com/cmm/jquery-1.11.3.js'/>"></script>
<link rel="stylesheet" href="<c:url value='/css/egovframework/com/cmm/jquery.treeview.css'/>" />
<script src="<c:url value='/js/egovframework/com/cmm/jquery.treeview.js'/>" type="text/javascript"></script>
<script src="<c:url value='/js/egovframework/com/cmm/jquery.cookie.js'/>" type="text/javascript"></script>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/omt/jquery.tbs.directory.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/ion/jquery.form.js"'/>"></script>
<script language="javascript">
var APPROVAL_CONTEXT = "${pageContext.servletContext.contextPath}";
var userID = "<c:out value="${user.uniqId}"/>";
var deptID = "<c:out value="${user.orgnztId}"/>";
var companyID = "<c:out value="${topDept.deptId}"/>";
var contentID = "content_box";
var popupLayerID = "popup_layer";
function validateDeleteForm(){
	if(!confirm('<spring:message code="common.msg.confim.delete"/>')){
		return false;
	}
	return true;
}
function deleteForm(formID){
	if(!validateDeleteForm()){
		return;
	}
	$.ajax({
		url : APPROVAL_CONTEXT+"/deleteForm.do",
		type: "POST",
		dataType: 'json',
		data : {formId: formID},
		success: function(data, textStatus, jqXHR) 
		{
			if(data.result && data.result == 'success'){
				listForm();
			}else if(data.error_msg){
				alert(data.error_msg);
			}
		},
		error: function(jqXHR, textStatus, errorThrown) 
		{
			alert("error!");
		}
	});
}
function listForm() {
	location.href = "listForm.do";
}
function viewModifyForm(formId) {
	 $('input:hidden[name=formId]').val(formId);
	 document.form_form.action = "<c:url value='/viewModifyForm.do'/>";
	 document.form_form.submit();
}
function downloadFormFile(url, formID){
	var formId = "downloadFileForm";
	var iframId = "downloadFileIFrame";
	if($("#"+iframId).length < 1){
		var iframe = $("<iframe id='"+iframId+"' name='"+iframId+"' width='0' height='0' style='display:none'></iframe>");
		$('body').append(iframe);
	}
	if($("#"+formId).length < 1){
		var form = $("<form id='"+formId+"' action='"+url+"' method='post' target='"+iframId+"'></form>");
		form.append("<input type=\"hidden\" name=\"formId\">");
		$('body').append(form);
	}
	$("#"+formId+" input[name='formId']").val(formID);
	$("#"+formId+"").submit();
}
</script>
</head>
<body>
	<div class="wrap"> 
		<!-- Top Menu Start-->
		<jsp:include page="/WEB-INF/jsp/egovframework/com/cmm/EgovTopMenu.jsp" flush="false">
			<jsp:param value="ADMIN" name="selectedMenu"/>
		</jsp:include>
		<!-- Top Menu End -->
		<div class="clear"></div>
		<!-- Container start -->
		<div class="Container"> 
		  <!-- Lnb -->
		<div class="Side"> 
			<!-- Side Menu -->
			<jsp:include page="/WEB-INF/jsp/egovframework/com/uss/omt/cmm/EgovLeftMenu.jsp" flush="false">
				<jsp:param value="FORM" name="selectedMenu"/>
			</jsp:include>
		</div>
		<!-- Content start -->
		<div class="Content"> 
		  <!-- Content box start -->
		<div class="content_box" id="org_content_box">
			<!-- 공통 타이틀 버튼 라인 -->
			<div style="overflow_hidden">
				<div class="title_box">
					<div class="title_line">
					  <h1><spring:message code="appvl.formmng.title.formmng"/></h1>
					</div>
				<div class="clear"></div>		  
				</div>
				<div>
					<p class="tab_title float_left"><spring:message code="appvl.formmng.title.view"/></p>
					<div class="ui_btn_rapper float_right">
						<a href="javascript:listForm()" class="btn_color3"><spring:message code="common.button.list"/></a>
						<a href="javascript:viewModifyForm('<c:out value="${form.formId}"/>')" class="btn_color3"><spring:message code="common.button.modify"/></a>
						<a href="javascript:deleteForm('<c:out value="${form.formId}"/>')" class="btn_color2"><spring:message code="common.button.delete"/></a>
					</div>
					<div class="table_rapper display_table">
					<form id="form_form" name="form_form" method="post">
						<input type="hidden" name="formId" id="formId" value="">
						<table summary="" class="board_width_borderNone">
							<caption class="blind"></caption>
							<colgroup>
								<col width="70px"/>
								<col width="275px"/>
								<col width="*"/>
							</colgroup>
							<tbody>
								<tr>
									<th scope="row"><label for="formNm"><spring:message code="appvl.formmng.label.name"/></label></th>
									<td colspan="3"><div class="ui_input_text cursorDefault"><input type="text" value="<c:out value="${form.formName}"/>" name="formNm" id="formNm" readonly/></div></td>
								</tr>						
								<tr>
									<th scope="row"><label  for="label_3"><spring:message code="appvl.formmng.label.scope"/></label></th>
									<td>
										<span class="radio_rapper cursorDefault"><input type="radio" value="<c:out value="${topDept.deptId}"/>" <c:if test="${form.orgId eq topDept.deptId}">checked</c:if> name="deptID" id="label_2-1" disabled="disabled"/><label  for="label_2-1"><spring:message code="appvl.formmng.label.scope.common"/></label></span>
										<span class="radio_rapper cursorDefault"><input type="radio" value="<c:out value="${form.orgId}"/>" <c:if test="${form.orgId ne topDept.deptId}">checked</c:if> name="deptID" id="label_2-2" disabled="disabled"/><label  for="label_2-2"><spring:message code="appvl.formmng.label.scope.department"/></label></span>
									</td>
									<td colspan="2"><div class="ui_input_text cursorDefault"><input type="text" value="<c:if test="${form.orgId ne topDept.deptId}"><c:out value="${form.orgNm}"/></c:if>" name="" id="label_3" readonly /></div></td>
								</tr>
								<tr>
									<th scope="row"><label for="label_4"><spring:message code="appvl.formmng.label.version"/></label></th>
									<td colspan="3"><div class="ui_input_text cursorDefault"><input type="text" value="<c:out value="${form.formVersion}"/>" name="formVer" id="formVer" readonly /></div></td>
								</tr>
								<tr>
									<th scope="row"><label for="label_5"><spring:message code="appvl.formmng.label.remarks"/></label></th>
									<td colspan="3"><div class="ui_input_text cursorDefault"><input type="text" value="<c:out value="${form.formRemark}"/>" name="formRmrk" id="formRmrk" readonly /></div></td>
								</tr>
								<tr>
									<th scope="row"><label for="label_6"><spring:message code="appvl.formmng.label.use"/></label></th>
									<td colspan="3"><div class="ui_input_text"><input type="radio" value="1" <c:if test="${form.formUseF}">checked</c:if> name="formUseF" disabled="disabled"/>Use <input type="radio" value="2" <c:if test="${not form.formUseF}">checked</c:if> name="formUseF" disabled="disabled"/>Not Use</div></td>
								</tr>
								<tr>
									<th scope="row"><label for="label_7"><spring:message code="appvl.formmng.label.formtype"/></label></th>
									<td colspan="3">
										<div class="ui_input_text cursorDefault">
											<c:if test="${form.formType eq '1'}"><input type="text" value='<spring:message code="appvl.formmng.label.formtype.internal"/>' name="formType" id="formType" readonly /></c:if>
											<c:if test="${form.formType eq '2'}"><input type="text" value='<spring:message code="appvl.formmng.label.formtype.outgoing"/>' name="formType" id="formType" readonly /></c:if>
											<c:if test="${form.formType eq '3'}"><input type="text" value='<spring:message code="appvl.formmng.label.formtype.register"/>' name="formType" id="formType" readonly /></c:if>
										</div>
									</td>
								</tr>
								<tr>
									<th scope="row"><label for="label_8"><spring:message code="appvl.formmng.label.formfile"/></label></th>
									<td colspan="2">
										<div class="ui_input_text cursorDefault"><input type="text" value="<c:out value="${formFilePath}"/>" name="formFile" id="formFile" readonly /></div>
									</td>
									<td class="pr0">
										<div class="ui_btn_rapper">
											<c:if test="${not empty formFilePath}">
												<a href="javascript:downloadFormFile('${pageContext.servletContext.contextPath}/downloadForm.do', '<c:out value="${form.formId}"/>')" class="btn_color3">Download</a>
											</c:if>
										</div>
									</td>
								</tr>
							</tbody>
						</table>
					</form>
					</div>			
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovCopyright.jsp" %>
</body>
</html>