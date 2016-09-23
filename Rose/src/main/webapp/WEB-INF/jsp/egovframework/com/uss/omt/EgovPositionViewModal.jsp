<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovCommon.jsp"%>
<html lang="en">
<head>
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
			marginTop:-parseInt($(".layerPopup_rapper").css("height"))/2+"px"
		}
	);
}
function formMapperOnload() {
    $('input:hidden[name=DEPT_ID]').val("<c:out value="${posi.deptId}" />");
    $('input:hidden[name=POSI_ID]').val("<c:out value="${posi.posiId}" />");
    $('#POSI_NM').val("<c:out value="${posi.posiNm}" />");
    $('#POSI_LV').val("<c:out value="${posi.posiLv}" />");
}
var env = {
	deletable: "true" == "${posi.deletable}"
};
$(function() {
    formMapperOnload();
    $('#button_delete').click(function() {
    	if (env.deletable) {
    		if (confirm("<spring:message code="common.msg.confim.delete"/>")) {	
		   		document.aForm.action = "<c:url value='/positionDelete.do'/>";
				document.aForm.submit();
    		}
        } else {
        	alert(["<spring:message code="org.position.msg.004"/> "].join("\r\n"));
        }
    });
});
function close(){
	$("#div_popup").hide();
}
function posiModify(id){
	$("body").css("overflow","");
	$("#div_popup").load(CONTEXT+'/positionForm.do', {POSI_ID:id},
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
	<!-- 입력양식 시작 -->
	<form name="aForm" method="post">
	    <input type="hidden" name="DEPT_ID"/>
	    <input type="hidden" name="POSI_ID"/>
	    <input type="hidden" name="POSI_NM"/>
	    <input type="hidden" name="POSI_LV"/>

	<p class="popup_title type_admin"><spring:message code="org.position.label.view"/></p>
	<div class="overflow_hidden">
	    <div class="ui_btn_rapper float_right mtb10">
	        <a href="javascript:posiModify('${posi.posiId}');" class="btn_color3" id="button_modify"><spring:message code="common.button.modify"/></a>
	        <a href="#" class="btn_color2" id="button_delete"><spring:message code="common.button.delete"/></a>
	        <a href="javascript:close();" class="btn_color2" id="button_close"><spring:message code="common.button.close"/></a>
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
	                <td colspan="2"><div class="ui_input_text cursorDefault"><input type="text" value="" id="POSI_NM" readonly /></div></td>
	            </tr>
	            <tr>
	                <th scope="row"><label for="label_2"><spring:message code="org.position.label.securitylevel"/></label></th>
	                <td colspan="2"><div class="ui_input_text cursorDefault"><input type="text" value="" id="POSI_LV" readonly /></div></td>
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