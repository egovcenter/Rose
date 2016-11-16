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
<script type="text/javascript">
function closeAttachmentHistory(){
	$("#div_popup").hide();
}
</script>
</head>
<body>
<div class="layerPopup_rapper" style="width: 1000px">
	<p class="popup_title type_approval"><spring:message code="appvl.attahistory.title"/></p>
	<div class="ui_btn_rapper float_right mtb10">
		<a href="javascript:closeAttachmentHistory()" class="btn_color2"><spring:message code="common.button.close"/></a>
	</div>
	<div class="rapper_table height_line10">
		<table summary="/No/Attochment/Title/Poster/Post Date/Expire date" class="board_type_height ">
			<caption class="blind"></caption>
			<colgroup>
				<col width="40px"/>
				<col width="40%"/>
				<col width="30%"/>
				<col width="100px"/>
				<col width="80px"/>		
			</colgroup>
			<thead>
				<tr>
					<th scope="col" class="align_left"><span><spring:message code="appvl.attahistory.table.no"/></span></th>
					<th scope="col" class="align_left"><span><spring:message code="appvl.attahistory.table.changer"/></span></th>
					<th scope="col" class="align_left"><span><spring:message code="appvl.attahistory.table.filename"/></span></th>
					<th scope="col" class="align_left"><span><spring:message code="appvl.attahistory.table.changedate"/></span></th>
					<th scope="col" class="align_left"><span><spring:message code="appvl.attahistory.table.action"/></span></th>
				</tr>
			</thead>					
			<tbody>
			<c:if test="${empty attachmentList}">
				<tr>
					<td colspan="5"><spring:message code="appvl.common.nohistory"/></td>
				</tr>
			</c:if>
			<c:if test="${not empty attachmentList}">
				<c:forEach var="attachment" items="${attachmentList}" varStatus="loop">
					<tr>
						<td><c:out value="${loop.count}"/></td>
						<td><c:out value="${attachment.attachSignerNm}"/></td>
						<td><c:out value="${attachment.attachNm}"/></td>
						<td title="<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${attachment.attachDateTime}" />"><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${attachment.attachDateTime}" /></td>
						<td><c:out value="${attachment.attachAction}"/></td>
					</tr>
				</c:forEach>
			</c:if>	
			</tbody>
		</table>
	</div>
</div>
<div id="shadow_popup"></div>
</body>
<script language="javascript" type="text/javascript"> 
//<![CDATA[
function display_attachmenthistory_load(){
	$("#shadow_popup").css(
			{
				width:$(document).width(),
				height:$(window).height()+48,
				opacity:0.5
			}
	);
	var winW = $(window).width()/2;
	$(".layerPopup_rapper").css(
		{
			left:winW-500,
			top:"20%",
			marginTop:-parseInt($(".layerPopup_rapper").css("height"))/2+"px"
		}
	);
	$(window).resize(function(){
		var winW = $(window).width()/2;
		$(".layerPopup_rapper").css(
			{
				left:winW-500,
				top:"20%",
				marginTop:-parseInt($(".layerPopup_rapper").css("height"))/2+"px"
			}
		);
	});
}
//]]>
</script>
</html>