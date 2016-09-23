<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.*"%>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovCommon.jsp"%>
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
<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/ion/popup.js"'/>"></script>
<script>
var APPROVAL_CONTEXT = "${pageContext.servletContext.contextPath}";
var userId = "<c:out value="${user.uniqId}"/>";
var deptId = "<c:out value="${user.orgnztId}"/>";
var companyId = "<c:out value="${topDept.deptId}"/>";
var contentId = "content_box";
var popupLayerId = "popup_layer";
jQuery(function() {
	viewAddForm_onLoad();
});
function viewAddForm_onLoad(){
	$("input:radio[name='deptId']").click(function(){
		if($(this).next().text()=='Department Form'){
			$('#ScopeDeptInput').show();
			$('#ScopeDeptBtn').show();
		}else{
			$('#ScopeDeptInput').css('display','none');
			$('#ScopeDeptBtn').css('display','none');
		}
	});
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
        <div class="title_box">
          <div class="title_line">
            <h1><spring:message code="appvl.formmng.title.formmng"/></h1>
          </div>
          <div class="clear"></div>
        </div>
		<div class="overflow_hidden">
			<p class="tab_title float_left"><spring:message code="appvl.formmng.title.new"/></p>
			<div class="ui_btn_rapper float_right">
				<a href="javascript:addForm()" class="btn_color3"><spring:message code="common.button.apply"/></a>
				<a href="javascript:listForm()" class="btn_color2"><spring:message code="common.button.cancel"/></a>
			</div>
		</div>
		<!-- table_rapper start -->
		<div class="table_rapper display_table">
			<!-- display_tableCell start -->
			<div class="display_tableCell_top">
				<form id="form_form" name="form_form" action="<c:out value="${pageContext.servletContext.contextPath}"/>/addForm.do" method="post" enctype="multipart/form-data">
					<input type="hidden" name="formUseF" value="1">
					<table summary="" class="board_width_borderNone">
						<caption class="blind"></caption>
						<colgroup>
							<col width="70px"/>
							<col width="270px"/>
							<col width="*"/>
							<col width="85px"/>
						</colgroup>
						<tbody>
							<tr>
								<th scope="row"><label for="formNm"><spring:message code="appvl.formmng.label.name"/></label></th>
								<td colspan="3"><div class="ui_input_text"><input type="text" value="" name="formNm" id="formNm"/></div></td>
							</tr>						
							<tr>
								<th scope="row"><label  for="label_3"><spring:message code="appvl.formmng.label.scope"/></label></th>
								<td>
									<span class="radio_rapper"><input type="radio" value="${topDept.deptId}" checked name="deptId" id=label_2-1/><label  for="label_2-1"><spring:message code="appvl.formmng.label.scope.common"/></label></span>
									<span class="radio_rapper"><input type="radio" value="" name="deptId" id="label_2-2"/><label  for="label_2-2"><spring:message code="appvl.formmng.label.scope.department"/></label></span>
								</td>
								<td>
									<div class="ui_input_text" id="ScopeDeptInput" style="display: none;">
										<input type="text" value="" name="deptNm" id="deptNm"/>
									</div>
								</td>
								<td class="pr0">
									<div class="ui_btn_rapper align_right"  id="ScopeDeptBtn" style="display: none;">
										<a href="javascript:selectDept();" class="btn_color3"><spring:message code="common.button.select"/></a>
									</div>
								</td>
							</tr>
							<tr>
								<th scope="row"><label for="label_4"><spring:message code="appvl.formmng.label.version"/></label></th>
								<td colspan="3"><div class="ui_input_text"><input type="text" value="" name="formVer" id="formVer"/></div></td>
							</tr>
							<tr>
								<th scope="row"><label for="label_5"><spring:message code="appvl.formmng.label.remarks"/></label></th>
								<td colspan="3"><div class="ui_input_text"><input type="text" value="" name="formRmrk" id="formRmrk"/></div></td>
							</tr>
							<tr>
								<th scope="row"><label for="label_6"><spring:message code="appvl.formmng.label.use"/></label></th>
								<td colspan="3"><div class="ui_input_text"><input type="radio" value="1" checked name="formUseF"/><spring:message code="appvl.formmng.label.use.use"/> <input type="radio" value="2" name="formUseF"/><spring:message code="appvl.formmng.label.use.notuse"/></div></td>
							</tr>
							<tr>
								<th scope="row"><label for="label_7"><spring:message code="appvl.formmng.label.formtype"/></label></th>
								<td>
									<div class="ui_select_rapper" style="padding-left: 4px;">
										<select id="formType" name="formType">
											<option value="1" id="internal"><spring:message code="appvl.formmng.label.formtype.internal"/></option>
											<option value="2" id="outgoing"><spring:message code="appvl.formmng.label.formtype.outgoing"/></option>
											<option value="3" id="register"><spring:message code="appvl.formmng.label.formtype.register"/></option>
										</select>							
									</div>
								</td>
							</tr>
							<tr>
								<th scope="row"><label for="label_8"><spring:message code="appvl.formmng.label.formfile"/></label></th>
								<td colspan="2"><div class="ui_input_text"><input type="file" value="" name="formFile" id="formFile" size="50"/></div></td>
								<td class="pr0"><div class="ui_btn_rapper align_right"><!--<a href="#" class="btn_color3">Select</a>--></div></td>
							</tr>	
						</tbody>
					</table>
				</form>
			</div>	<!-- display_tableCell_top -->
		</div>	<!-- table_rapper display_table -->	
	</div>	<!-- Content -->		
  </div> <!-- Container -->	
  <div id="div_popup" style="display: none"></div>		
</div> <!-- wrap -->
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovCopyright.jsp" %>
</body>	
<script>
function validateAddForm(){
	var formNm = $('input[name=formNm]', '#form_form').val();
	if(formNm == ''){
		alert('<spring:message code="appvl.form.msg.001"/>');
		$('input[name=formNm]', '#form_form').focus();
		return false;
	}
	if(formNm.length > 60){
		alert('<spring:message code="appvl.form.msg.006"/>');
		$('input[name=formNm]', '#form_form').focus();
		return false;
	}
	if(!confirm('<spring:message code="common.msg.confim.add"/>')){
		return false;
	}
	return true;
}
function addForm(){
	if(!validateAddForm()){
		return;
	}
	var formURL = $('#form_form').attr("action");
	var departmentId = $($('input:radio[name=deptId]')[1]).val();
	if($('input:radio[name=deptId]')[1].checked == true){
		if(companyId == departmentId){
			$('input:radio[name=deptId]')[0].checked = true;
			$('#ScopeDeptInput').css('display','none');
			$('#ScopeDeptBtn').css('display','none');
		}else{
			$('input:radio[name=deptId]')[1].checked = true;
		}
	}
	document.form_form.submit();
}
function listForm() {
	location.href = "listForm.do";
}
function selectDept() {
	$("body").css("overflow","");
	$("#div_popup").load(APPROVAL_CONTEXT+'/deptSelectionModal.do', {},
			function(){
				if (typeof(select_Appr_modal_load) != "undefined") {
					select_Appr_modal_load();
				}
				$("#div_popup").show();
			}
	);
}
function setValue(obj, isDepartment) {
	$('input:radio[name=deptId]').val(obj.id);
	$('#deptNm').val(obj.name);
	$('input[type=checkbox]').each(function() {
		$(this).show();
	});
}
</script>
</html>