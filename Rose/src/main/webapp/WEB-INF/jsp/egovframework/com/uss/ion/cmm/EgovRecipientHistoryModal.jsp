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
function closeRecipientHistory(){
	$("#div_popup").hide();
}
</script>
</head>
<body>
<div class="layerPopup_rapper" style="width: 1000px">
	<p class="popup_title type_approval"><spring:message code="appvl.rcpthistory.title"/></p>
	<div class="ui_btn_rapper float_right mtb10">
		<a href="javascript:closeRecipientHistory()" class="btn_color2"><spring:message code="common.button.close"/></a>
	</div>
	<div class="rapper_table height_line10">
		<table summary="/No/Attochment/Title/Poster/Post Date/Expire date" class="board_type_height ">
			<caption class="blind"></caption>
			<colgroup>
				<col width="50px"/>
				<col width="60%"/>
				<col width="80px"/>
				<col width="150px"/>
				<col width="80px"/>		
				<col width="100px"/>
				<col width="110px"/>
				<col width="40%"/>
			</colgroup>
			<thead>
				<tr>
					<th scope="col" class="align_left"><span><spring:message code="appvl.rcpthistory.table.no"/></span></th>
					<th scope="col" class="align_left"><span><spring:message code="appvl.rcpthistory.table.department"/></span></th>
					<th scope="col" class="align_left"><span><spring:message code="appvl.rcpthistory.table.type"/></span></th>
					<th scope="col" class="align_left"><span><spring:message code="appvl.rcpthistory.table.method"/></span></th>
					<th scope="col" class="align_left"><span><spring:message code="appvl.rcpthistory.table.status"/></span></th>
					<th scope="col" class="align_left" colspan="2"><span><spring:message code="appvl.rcpthistory.table.date"/></span></th>
					<th scope="col" class="align_left"><span>Handler</span></th>
				</tr>
			</thead>					
			<tbody>
			<c:forEach var="recipient" items="${recipientList}" varStatus="loop">
				<tr>
					<td rowspan="4"><c:out value="${recipient.recpSeq}"/></td>
					<td rowspan="4"><c:out value="${recipient.recpDeptNm}"/></td>
					<td rowspan="4">
						<c:choose>
							<c:when test="${recipient.recpInnerFlag eq '1'}"><spring:message code="appvl.assignrecpt.button.internal"/></c:when>
							<c:when test="${recipient.recpInnerFlag eq '2'}"><spring:message code="appvl.assignrecpt.button.external"/></c:when>
						</c:choose>
					</td>
					<td rowspan="4">
						<c:choose>
							<c:when test="${recipient.recpSendType eq 'ST01'}"><spring:message code="appvl.recip.ST01"/></c:when>
							<c:when test="${recipient.recpSendType eq 'ST02'}"><spring:message code="appvl.recip.ST02"/></c:when>
							<c:when test="${recipient.recpSendType eq 'ST03'}"><spring:message code="appvl.recip.ST03"/></c:when>
							<c:when test="${recipient.recpSendType eq 'ST04'}"><spring:message code="appvl.recip.ST04"/></c:when>
							<c:when test="${recipient.recpSendType eq 'ST05'}"><spring:message code="appvl.recip.ST05"/></c:when>
							<c:otherwise><spring:message code="appvl.recip.ST01"/></c:otherwise>
						</c:choose>
					</td>
					<td rowspan="4">
						<c:choose>
							<c:when test="${not empty recipient.recpFinishDt}"><spring:message code="appvl.recip.label.status.finished"/></c:when>
							<c:when test="${not empty recipient.recpRecpDt and empty recipient.recpFinishDt}"><spring:message code="appvl.recip.label.status.received"/></c:when>
							<c:when test="${not empty recipient.recpArrivalDt and empty recipient.recpRecpDt and empty recipient.recpFinishDt}"><spring:message code="appvl.recip.label.status.arrived"/></c:when>
							<c:when test="${not empty recipient.recpSendDt and empty recipient.recpArrivalDt and empty recipient.recpRecpDt and empty recipient.recpFinishDt}"><spring:message code="appvl.recip.label.status.sent"/></c:when>
							<c:otherwise></c:otherwise>
						</c:choose>																	
					</td>
				</tr>
				<tr>
					<td><spring:message code="appvl.rcpthistory.table.sentdate"/></td>
					<td title="<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${recipient.recpSendDt}" />"><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${recipient.recpSendDt}" /></td>
					<td title="<c:out value="${recipient.recpSendUserNm}" />"><c:out value="${recipient.recpSendUserNm}" /></td>
				</tr>
				<c:choose>
				   <c:when test="${recipient.recpSendType eq 'ST01'}">
					<tr>
						<td><spring:message code="appvl.rcpthistory.table.receiveddate"/></td>
					
						<td title="<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${recipient.recpRecpDt}" />"><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${recipient.recpRecpDt}" /></td>
						<td title="<c:out value="${recipient.recpRecpUserNm}" />"><c:out value="${recipient.recpRecpUserNm}" /></td>
					</tr>
					<tr>
						<td><spring:message code="appvl.rcpthistory.table.finisheddate"/></td>
						<td title="<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${recipient.recpFinishDt}" />"><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${recipient.recpFinishDt}" /></td>
						<td title="<c:out value="${recipient.recpFinishUserNm}" />"><c:out value="${recipient.recpFinishUserNm}" /></td>
					</tr>
				   </c:when>

				   <c:otherwise>
				       <c:choose>
				           <c:when test="${recipient.recpSendDt eq null}">
				           		<tr>
									<td><spring:message code="appvl.rcpthistory.table.receiveddate"/></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td><spring:message code="appvl.rcpthistory.table.finisheddate"/></td>
									<td></td>
									<td></td>
								</tr>
				           </c:when>
				           
				           <c:otherwise>
								<tr>
									<td><spring:message code="appvl.rcpthistory.table.receiveddate"/></td>
									<td title="<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${recipient.recpRecpDt}" />"><spring:message code="appvl.rcpthistory.table.NA"/></td>
									<td title="<c:out value="${recipient.recpRecpUserNm}" />"><spring:message code="appvl.rcpthistory.table.NA"/></td>
								</tr>
								<tr>
									<td><spring:message code="appvl.rcpthistory.table.finisheddate"/></td>
									<td title="<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${recipient.recpFinishDt}" />"><spring:message code="appvl.rcpthistory.table.NA"/></td>
									<td title="<c:out value="${recipient.recpFinishUserNm}" />"><spring:message code="appvl.rcpthistory.table.NA"/></td>
								</tr>
				           </c:otherwise>
				       </c:choose>
				   </c:otherwise>
				</c:choose>
			</c:forEach>
			</tbody>
		</table>
	</div>
</div>
<div id="shadow_popup"></div>
</body>
<script language="javascript" type="text/javascript"> 
//<![CDATA[
function display_recipienthistory_load(){
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