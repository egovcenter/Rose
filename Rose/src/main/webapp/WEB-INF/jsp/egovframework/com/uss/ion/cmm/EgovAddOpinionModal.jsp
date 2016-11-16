<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<meta http-equiv="Content-Style-Type" content="text/css">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=2, user-scalable=yes" />

<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/ion/jquery-1.7.1.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/ion/jquery-ui-1.11.4.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/omt/dynatree-1.2.4/jquery/jquery-ui.custom.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/omt/dynatree-1.2.4/jquery/jquery.cookie.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/omt/jquery.tbs.directory.js'/>"></script>
<script type="text/javascript">
var APPROVAL_CONTEXT = "${pageContext.servletContext.contextPath}";

var userId = "${user.uniqId}";
	function opinion_load(){
		opinion_load_ui();
	}
	
function closeOpinion(){
	$("body").css("overflow","");
	$("#div_popup").hide();
	submitted = false;
}
</script>
</head>
<body>
<div class="layerPopup_rapper opinionPopup" style="position:absolute;">
	<p class="popup_title type_approval opiniontitle">
		<c:if test="${type eq 'approve'}"><spring:message code="appvl.label.approval"/></c:if> 
		<c:if test="${type eq 'reject'}"><spring:message code="appvl.label.reject"/></c:if> 
		<c:if test="${type eq 'hold'}"><spring:message code="appvl.label.hold"/></c:if> 
		<c:if test="${type eq 'draft'}"><spring:message code="appvl.label.draft"/></c:if> 
		<c:if test="${type eq 'redraft' or type eq 'redraftForIncoming'}"><spring:message code="appvl.label.redraft"/></c:if> 
		<c:if test="${type eq 'receive'}"><spring:message code="appvl.label.register"/></c:if> 
	</p>
	<div class="float_right mtb10">
		<input type="button" id="apply_opinion_button" value='<spring:message code="common.button.apply"/>' class="but_navy" onClick="opinionDisabled('${type}');"/>
		<input type="button" id="cancel_opinion_button" value='<spring:message code="common.button.cancel"/>' class="but_gray" onClick="closeOpinion();"/>
	</div>
	<table summary="" class="board_width_borderNone">
		<caption class="blind"></caption>
		<colgroup>
			<col width="50px"/>
			<col width="*"/>
		</colgroup>
		<tbody>
			<tr>
			<td align="left">Opinion</td>
			<td colspan="2" style="padding-left: 10px;">
				<div class="display_tableCell_top">
					<textarea id="opinion" name="opinion" maxlength="1000" style="width:535px; height: 300px; overflow: auto;"></textarea>
				</div>			
			</td>
			</tr>
		</tbody>
	</table>
</div>
<div id="shadow_opinion"></div>
<input type="hidden" id="display" value="" />
</body>
<script language="javascript" type="text/javascript"> 
//<![CDATA[
function opinion_load_ui(){
	$( document ).ready(function() {
		$("#shadow_opinion").css(
			{
			width:$(document).width(),
			height:$(document).height()+48,
			opacity:0.5
		}
	);
	$(window).resize(function(){
		$("#shadow_opinion").css(
			{
				width:$(document).width(),
				height:$(window).height()+48,
				opacity:0.5
			}
		);
	});
	
		$(".layerPopup_rapper.opinionPopup").css(
			{
				left:"50%",
				top:"25%",
				marginLeft:-parseInt($(".layerPopup_rapper").css("width"))/2+"px",
				marginTop:-parseInt($(".layerPopup_rapper").css("height"))/2+"px"
			}
		);
	});
}
//]]>
</script>
</html>