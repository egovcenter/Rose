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
function formMapperOnload() {
    $('input:hidden[name=DEPT_ID]').val("<c:out value="${posi.deptId}" />");
    $('input:hidden[name=POSI_ID]').val("<c:out value="${posi.posiId}" />");
    $('#POSI_NM').val("<c:out value="${posi.posiNm}" />");
    $('#POSI_LV').val("<c:out value="${posi.posiLv}" />");
}
function formMapperSubmit() {
    $('input:hidden[name=POSI_NM]').val($('#POSI_NM').val());
    $('input:hidden[name=POSI_LV]').val($('#POSI_LV').val());
}
function checkNullValue(id, msg) {
    if ($.trim($('#' + id).val()) == "") {
        $('#' + id).focus();
        alert(msg);
        return true;
    }
    return false;
}
function checkNumber(id, msg) {
	var val = $('#' + id).val(); 
	if (isNaN(val)) {
		$('#' + id).focus();
        alert(msg);
        return true;
    }
    return false;
}
function checkForm() {
    if (checkNullValue('POSI_NM', "<spring:message code="org.position.msg.001"/>")) return false;
    if (checkNullValue('POSI_LV', "<spring:message code="org.position.msg.002"/>")) return false;
    if (checkNumber('POSI_LV', "<spring:message code="org.position.msg.003"/> ")) return false;
    return true;
}
$(function() {
    formMapperOnload();
    $('#POSI_NM').focus();
    $('#button_save').click(function() {
    	if (checkForm()){
    		formMapperSubmit();
	   		document.aForm.action = "<c:url value='/positionSave.do'/>";
			document.aForm.submit();
    	}
    });
});
function close(){
	$("#div_popup").hide();
}
</script>
</head>
<body>
<div class="layerPopup_rapper"> 
	<c:choose>
	<c:when test="${not empty posi.posiId}">
		<!-- IF MODIFY -->
	    <c:set var="formType" value="Modify"></c:set>
	    <c:set var="saveName" value="Apply"></c:set>
	</c:when>
	<c:otherwise>
		<!-- IF ADD -->
	    <c:set var="formType" value="New"></c:set>
	    <c:set var="saveName" value="Apply"></c:set>
	</c:otherwise>
	</c:choose>
		
	<!-- 입력양식 시작 -->
	<form name="aForm" id="aForm" action="" method="post">
	    <input type="hidden" name="DEPT_ID"/>
	    <input type="hidden" name="POSI_ID"/>
	    <input type="hidden" name="POSI_NM"/>
	    <input type="hidden" name="POSI_LV"/>
		
	<p class="popup_title type_admin"><c:out value="${formType}"/> <spring:message code="org.position.label.position"/></p>
	<div class="overflow_hidden">
		  <div class="ui_btn_rapper float_right mtb10">
			<a href="#" class="btn_color3" id="button_save"><c:out value="${saveName}"/></a>
			<a href="javascript:close();" class="btn_color2" id="button_close"><spring:message code="common.button.cancel"/></a>
		</div>
	</div>
	<div class="table_rapper">
		<table summary="" class="board_width_borderNone">
			<caption class="blind"></caption>
			<colgroup>
				<col width="95px"/>
				<col width="*"/>
				<col width="62px"/>
			</colgroup>
			<tbody>
				<tr>
					<th scope="row"><label for="label_1"><spring:message code="org.position.label.name"/></label></th>
					<td colspan="2"><div class="ui_input_text"><input type="text" value="" id="POSI_NM"/></div></td>
				</tr>
				<tr>
					<th scope="row"><label for="label_2"><spring:message code="org.position.label.securitylevel"/></label></th>
					<td colspan="2">
						<div class="ui_select_rapper">
							<select id="POSI_LV" name="POSI_LV">
								<option value="1" id="1">1</option>
								<option value="2" id="2">2</option>
								<option value="3" id="3">3</option>
								<option value="4" id="4">4</option>
								<option value="5" id="5">5</option>
								<option value="6" id="6">6</option>
								<option value="7" id="7">7</option>
								<option value="8" id="8">8</option>
								<option value="9" id="9">9</option>
								<option value="10" id="10">10</option>
							</select>							
						</div>
					</td>
				</tr>					
			</tbody>
		</table>
	</div>
	</form>				
    <div class="clear"></div>
</div>
<div id="shadow_org"></div>
</body>
</html>