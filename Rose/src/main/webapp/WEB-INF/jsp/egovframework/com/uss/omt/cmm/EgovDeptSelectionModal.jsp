<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovCommon.jsp"%>
<html lang="en">
<head>
<%@ include file="/WEB-INF/jsp/egovframework/com/cmm/EgovHeader.jsp"%>
<script src="<c:url value='/js/egovframework/com/uss/omt/orgCommon.js'/>"></script>
<script src="<c:url value='/js/egovframework/com/cmm/jquery-1.11.3.js'/>"></script>
<link rel="stylesheet" href="<c:url value='/css/egovframework/com/cmm/jquery.treeview.css'/>" />
<script src="<c:url value='/js/egovframework/com/cmm/jquery.treeview.js'/>" type="text/javascript"></script>
<script src="<c:url value='/js/egovframework/com/cmm/jquery.cookie.js'/>" type="text/javascript"></script>
<script>
function select_modal_load(){
	$(".shadow_org").css("display","block");
	$(".shadow_org").css(
		{
			width:$(document).width(),
			height:$(document).height(),
			opacity:0.5
		}
	);
	$(".layerPopup_rapper").css(
		{
			left:"50%",
			top:"15%",	
			marginLeft:-parseInt($(".layerPopup_rapper").css("width"))/2+"px",
			marginTop:-parseInt($(".layerPopup_rapper").css("height"))/2+"px"
		}
	);
}
function select_Appr_modal_load(){
	$("#shadow_popup").css("display","block");
	$("#shadow_popup").css(
		{
			width:$(document).width(),
			height:$(document).height(),
			opacity:0.5
		}
	);
	$(".layerPopup_rapper").css(
		{
			left:"50%",
			top:"15%",	
			marginLeft:-parseInt($(".layerPopup_rapper").css("width"))/2+"px",
			marginTop:-parseInt($(".layerPopup_rapper").css("height"))/2+"px"
		}
	);
}
function displayLoad(){
	$(".DEPT_ID").css("display","none");
}
$(".folder, .file").click(function(){
	if($(this).attr("nm") == "ROOT"){
		return false;
	}
    var caller = (opener) ? opener : parent;
    var retval = caller.setValue({
        name: $(this).attr("nm"),
        id: $(this).attr("id"),
        depth: $(this).attr("depth"),
        childNode: $(this).attr("childNode")
    }, true);
    close();
    return retval;
});
$(function() {
	$('input[type=checkbox]').each(function() {
		if($(this).val() != '1'){
			$(this).hide();
		}
	});
});
</script>
</head>

<body onload="displayLoad()">
<div class="layerPopup_rapper popup_type400">   
    <p class="popup_title type_admin">
   	<c:choose>
   		<c:when test="${user.userType eq 'S' }">
   			<spring:message code="org.dept.label.selectCompany"/>
   		</c:when>
   		<c:otherwise>
   			<spring:message code="org.dept.label.selectDept"/>
   		</c:otherwise>
   	</c:choose>
   	</p>
    <div class="ui_btn_rapper float_right mtb10">
        <a href="javascript:close();" id="button_close" class="btn_color2"><spring:message code="common.button.cancel"/></a>
    </div>
	<div style="clear: both; height:359px; background:#fff; overflow:auto;  border:1px solid #dcdbd5;">
	<ul id="tree">
		<!-- dept tree -->
		<%@ include file="EgovLnbTree.jsp" %>
	</ul>
	</div>
</div>	
<div id="shadow_popup" style="display: none"></div>
<div id="shadow_orgModal" class="shadow_org" style="display: none"></div>
</body>
<script language="javascript" type="text/javascript"> 
function close(){
	$("#div_popup").hide();
	$(".shadow_org").hide();
	$('input[type=checkbox]').each(function() {
		$(this).show();
	});
}
</script>
</html>