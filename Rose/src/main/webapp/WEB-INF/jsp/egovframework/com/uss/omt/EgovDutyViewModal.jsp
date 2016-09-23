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
var env = {
		deletable: "true" == "${duty.deletable}"
};
$(function() {
   $('#button_delete').click(function() {
	   if (env.deletable) {
		   if (confirm("<spring:message code="common.msg.confim.delete"/>")) {
			   document.dutyForm.action = "<c:url value='/dutyDelete.do'/>";
			   document.dutyForm.submit();
		   	}
		} else {
			alert(["<spring:message code="org.duty.msg.002"/> "].join("\r\n"));
		}
	});
});
function close(){
	$("#div_popup").hide();
}
function dutyModify(id, name) {
	$("body").css("overflow","");
	$("#div_popup").load(CONTEXT+'/dutyForm.do', {DUTY_ID:id, DUTY_NM:name},
			function(){
				if (typeof(select_modal_load) != "undefined") {
					select_modal_load();
				}
				$("#div_popup").show();
			}
	);
}
</script>
</head>

<body>
<div class="layerPopup_rapper"> 
	<!-- Input Form start -->
	<form name="dutyForm" id="dutyForm" method="post" action="">
	    <input type="hidden" name="DEPT_ID" value="${duty.deptId}"/>
	    <input type="hidden" name="DUTY_ID" value="${duty.dutyId}"/>
	    <input type="hidden" name="DUTY_NM" value="${duty.dutyNm}"/>

	<p class="popup_title type_admin"><spring:message code="org.duty.label.view"/></p>
    <div class="overflow_hidden">
        <div class="ui_btn_rapper float_right mtb10">
            <a href="javascript:dutyModify('${duty.dutyId}','${duty.dutyNm}');" class="btn_color3" id="button_modify"><spring:message code="common.button.modify"/></a>
            <a href="#" class="btn_color2" id="button_delete"><spring:message code="common.button.delete"/></a>
            <a href="javascript:close();" class="btn_color2" id="button_close"><spring:message code="common.button.close"/></a>
        </div>
    </div>
	<!-- table_rapper start -->
	<div class="table_rapper display_table">
	<table summary="" class="board_width_borderNone">
		<caption class="blind"></caption>
		<colgroup>
			<col width="40px"/>
			<col width="*"/>
		</colgroup>
		<tbody>
			<tr>
				<th scope="row"><label for="label_1"><spring:message code="org.duty.label.name"/></label></th>
				<td colspan="2"><div class="ui_input_text"><input type="text" value="${duty.dutyNm}" id="DUTY_NM" readonly="readonly"/></div></td>
			</tr>										
		</tbody>
	</table>				    
	</div>
	<!-- table_rapper end -->
	</form>    
	<!-- Input Form end -->
    <div class="clear"></div>
</div>
<div id="shadow_org"></div>
</body>
</html>