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
	<p class="popup_title type_approval">Opinion</p>
	<div class="ui_btn_rapper float_right mtb10">
		<a href="javascript:closeOpinion()" class="btn_color2"><spring:message code="common.button.close"/></a>
	</div>
	
	<div class="table_rapper display_table">
		<div class="display_tableCell_top">
			<textarea id="opinion" name="opinion" style="width:595px; height: 300px; overflow: auto;" title="inputOpinion" readonly="readonly">${opinion}</textarea>
		</div>			
	</div>	
</div>
<div id="shadow_opinion"></div>
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