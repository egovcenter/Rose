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
function closeSignerline(){
	$("#div_popup").hide();
}
</script>
</head>
<body>
<div class="layerPopup_rapper" style="width:900px;">
	<p class="popup_title type_approval"><spring:message code="appvl.signerhistory.title"/></p>
	<div class="ui_btn_rapper float_right mtb10">
		<a href="javascript:closeSignerline()" class="btn_color2"><spring:message code="common.button.close"/></a>
	</div>
	<div class="rapper_table height_line10">
		<table summary="/No/Attochment/Title/Poster/Post Date/Expire date" class="board_type_height ">
			<caption class="blind"></caption>
			<colgroup>
				<col width="60px"/>
				<col width="80px"/>
				<col width=""/>
				<col width="10%"/>
				<col width="110px"/>
			</colgroup>
			<thead>
				<tr>
					<th scope="col" class="align_left"><span><spring:message code="appvl.signerhistory.table.order"/></span></span></th>
					<th scope="col" class="align_left"><span><spring:message code="appvl.signerhistory.table.type"/></span></th>
					<th scope="col" class="align_left"><span><spring:message code="appvl.signerhistory.table.name"/></span></th>
					<th scope="col" class="align_left"><span><spring:message code="appvl.signerhistory.table.status"/></span></th>
					<th scope="col" class="align_left"><span><spring:message code="appvl.signerhistory.table.processdate"/></span></th>
				</tr>
			</thead>					
			<tbody>
				<c:forEach var="signerHistory" items="${signerHistoryList}" varStatus="status">
				<tr <c:if test="${signerHistory.signerHistoryState == 'SS09' or signerHistory.signerHistoryKind == 'SK00'}">class="unsortable"</c:if>>
					<td rowspan="2">
						<div class="signer_seq"><c:out value="${status.count}"/></div>
					</td>
					<td>
						<c:choose>
							<c:when test="${signerHistory.signerHistoryKind == 'SK00'}"><spring:message code="appvl.signerKind.SK00"/></c:when>
							<c:when test="${signerHistory.signerHistoryKind == 'SK01'}"><spring:message code="appvl.signerKind.SK01"/></c:when>
							<c:when test="${signerHistory.signerHistoryKind == 'SK02'}"><spring:message code="appvl.signerKind.SK02"/></c:when>
							<c:when test="${signerHistory.signerHistoryKind == 'SK03'}"><spring:message code="appvl.signerKind.SK03"/></c:when>
							<c:when test="${signerHistory.signerHistoryKind == 'SK04'}"><spring:message code="appvl.signerKind.SK04"/></c:when>
						</c:choose>
					</td>
					<td><c:out value="${signerHistory.signerHistoryUserName}"/></td>
					<td>
						<c:choose>
							<c:when test="${signerHistory.signerHistoryState == 'SS00'}"><spring:message code="appvl.signerState.SS00"/></c:when>
							<c:when test="${signerHistory.signerHistoryState == 'SS01'}"><spring:message code="appvl.signerState.SS01"/></c:when>
							<c:when test="${signerHistory.signerHistoryState == 'SS02'}"><spring:message code="appvl.signerState.SS02"/></c:when>
							<c:when test="${signerHistory.signerHistoryState == 'SS03'}"><spring:message code="appvl.signerState.SS03"/></c:when>
							<c:when test="${signerHistory.signerHistoryState == 'SS09'}"><spring:message code="appvl.signerState.SS09"/></c:when>
						</c:choose>
					</td>
					<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${signerHistory.signerHistoryDate}" /></td>
				</tr>
				<tr>
					<td colspan="4">
					<c:choose>
						<c:when test="${not empty signerHistory.signerOpinion}"><div class="signer_opinion"><c:out value="${signerHistory.signerOpinion}"/></div></c:when>
						<c:otherwise><div class="signer_opinion"><spring:message code="appvl.signerhistory.table.no_opinion"/></div></c:otherwise>
					</c:choose>
					</td>			
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
<div id="shadow_popup"></div>
</body>
<script language="javascript" type="text/javascript"> 
//<![CDATA[
function display_signerhistory_load(){
	$('.board_type_height th').click(function() {
		if ($(this).hasClass('sort_down')){
			$(this).removeClass('sort_down');
			$(this).addClass('sort_up');
		} else {
			$(this).removeClass('sort_up');
			$(this).addClass('sort_down');
		  }
		});
	
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
			left:winW-450,
			top:"20%",
			marginTop:-parseInt($(".layerPopup_rapper").css("height"))/2+"px"
		}
	);
	$(window).resize(function(){
		var winW = $(window).width()/2;
		$(".layerPopup_rapper").css(
			{
				left:winW-450,
				top:"20%",
				marginTop:-parseInt($(".layerPopup_rapper").css("height"))/2+"px"
			}
		);
	});
}
//]]>
</script>
</html>