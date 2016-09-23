<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovCommon.jsp"%>
<html lang="en">
<head>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovHeader.jsp"%>
<script>
function select_modal_load(){
	$("#shadow_org").css(
		{
			width:$(document).width(),
			height:$(document).height(),
			opacity:0.5
		}
	);
	$(".layerPopup_rapper").css(
		{
			left:"50%",
			top:"25%",	
			marginLeft:-parseInt($(".layerPopup_rapper").css("width"))/2+"px",
		}
	);
}
var isAdd;
function checkOperation() {
	var dept = "<c:out value="${duty}" />";
	if (dept) {
		isAdd = false;
	} else {
		isAdd = true;
	}	
}
function formMapperOnload() {
	checkOperation();
    $('input:hidden[name=DEPT_ID]').val("<c:out value="${duty.deptId}" />");
    $('input:hidden[name=DUTY_ID]').val("<c:out value="${duty.dutyId}" />");
    $('#DUTY_NM').val("<c:out value="${duty.dutyNm}" />");
}
function formMapperSubmit() {
    $('input:hidden[name=DUTY_NM]').val($('#DUTY_NM').val());
}
function checkNullValue(id, msg) {
    if ($.trim($('#' + id).val()) == "") {
        $('#' + id).focus();
        alert(msg);
        return true;
    }
    return false;
}
function checkStrSize(id, min, max, msg) {
	var val = $('#' + id).val();
	if (val.length < min || val.length > max) {
		$('#' + id).focus();
        alert(msg);
        return true;
	}
}
function checkForm() {
    if (checkNullValue('DUTY_NM', "<spring:message code="org.duty.msg.001"/>")) return false;
    if (checkStrSize('DUTY_NM', 2, 30, "<spring:message code="org.duty.msg.001"/>")) return false;
    return true;
}
$(function() {
    formMapperOnload();
    $('#DUTY_NM').focus();
});
function close(){
	$("#div_popup").hide();
}
function dutySave(){
 	if (checkForm()){
 		formMapperSubmit();
 		document.dutyForm.action = "<c:url value='/dutySave.do'/>";
		document.dutyForm.submit();
 	}
}
</script>
</head>

<body>
<div class="layerPopup_rapper"> 
	<c:choose>
	<c:when test="${not empty duty.dutyId}">
		<!-- IF MODFY -->
		<c:set var="formType" value="Modify"></c:set>
		<c:set var="saveName" value="Apply"></c:set>
	</c:when>
	<c:otherwise>
		<!-- IF ADD -->
		<c:set var="formType" value="New"></c:set>
		<c:set var="saveName" value="Apply"></c:set>
	</c:otherwise>
	</c:choose>
		
	<form name="dutyForm" id="dutyForm" action="" method="post">
	    <input type="hidden" name="DEPT_ID"/>
	    <input type="hidden" name="DUTY_ID"/>
	    <input type="hidden" name="DUTY_NM"/>
	    
	<p class="popup_title type_admin"><c:out value="${formType}"/> <spring:message code="org.duty.label.duty"/></p>	
	<div class="overflow_hidden">
	  	<div class="ui_btn_rapper float_right mtb10">
			<a href="javascript:dutySave();" class="btn_color3" id="button_save"><c:out value="${saveName}"/></a>
			<a href="javascript:close();" class="btn_color2" id="button_close"><spring:message code="common.button.cancel"/></a>
		</div>
	</div>		
	<!-- table_rapper start -->
	<div class="table_rapper display_table">
	<!-- Input Form start -->
	<table summary="" class="board_width_borderNone">
		<caption class="blind"></caption>
		<colgroup>
			<col width="40px"/>
			<col width="*"/>
		</colgroup>
		<tbody>
			<tr>
				<th scope="row"><label for="label_1"><spring:message code="org.duty.label.name"/></label></th>
				<td colspan="2"><div class="ui_input_text"><input type="text" value="" id="DUTY_NM"/></div></td>
			</tr>										
		</tbody>
	</table>				    
	<!-- Input Form end -->
	</div>
	<!-- table_rapper end -->
	</form>    
    <div class="clear"></div>
</div>
<div id="shadow_org"></div>
</body>
</html>