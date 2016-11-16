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

<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/ion/org.js'/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value='/js/egovframework/com/uss/omt/dynatree-1.2.4/src/skin/ui.dynatree.css'/>" />
<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/omt/dynatree-1.2.4/src/jquery.dynatree.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/omt/orgPopup.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/uss/omt/jquery.tbs.directory.js'/>"></script>
<script type="text/javascript">
var APPROVAL_CONTEXT = "${pageContext.servletContext.contextPath}";

function closeSignHistory(){
	$("body").css("overflow","");
	$("#div_popup").hide();
}
function displaySignHistory(tabObj, ID){
	$(".tab_sendtype a").each(function(index){
		$('.tab_sendtype').each(function(index){
			if($(this).attr('id') == ID){
				$(this).addClass("on");
			}else{
				$(this).removeClass("on");
			}
		});
	});
	$("#div_signerline").hide();
	$("#div_sign").show();
}
function displaySignerlineHistory(tabObj, ID){
	$(".tab_sendtype a").each(function(index){
		$('.tab_sendtype').each(function(index){
			if($(this).attr('id') == ID){
				$(this).addClass("on");
			}else{
				$(this).removeClass("on");
			}
		});
	});
	$("#div_sign").hide();
	$("#div_signerline").show();
}
</script>
</head>
<body>
<div class="layerPopup_rapper historyPopup" style="position:absolute; width: 1000px;">
	<p class="popup_title type_approval"><spring:message code="appvl.approvalhistory.title"/></p>
	<div class="ui_btn_rapper float_right mtb10">
		<a href="javascript:closeSignHistory()" class="btn_color2"><spring:message code="common.button.cancel"/></a>
	</div>
	<div class="tabBox_rapper">
		<!-- 탭메뉴 -->
		<div class="tab_box">
			<ul>
				<!-- 탭 활성화 on 추가 -->
				<li class="on tab_sendtype" id="li_SginHistory"><a href="javascript:displaySignHistory(this,'li_SginHistory')"><spring:message code="appvl.approvalhistory.button.Approval"/></a></li>
				<li class="tab_sendtype" id="li_SignerlineHistory"><a href="javascript:displaySignerlineHistory(this,'li_SignerlineHistory')"><spring:message code="appvl.approvalhistory.button.Signerline"/></a></li>				
			</ul>
		</div>
	</div>
	
	<div class="table_rapper rapper_table height_line10" id="div_sign">
		<table summary="" class="borader_width_borderNone board_type_height ">
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
					<th scope="col" class="align_left"><span><spring:message code="appvl.approvalhistory.sign.table.order"/></span></span></th>
					<th scope="col" class="align_left"><span><spring:message code="appvl.approvalhistory.sign.table.type"/></span></th>
					<th scope="col" class="align_left"><span><spring:message code="appvl.approvalhistory.sign.table.name"/></span></th>
					<th scope="col" class="align_left"><span><spring:message code="appvl.approvalhistory.sign.table.version"/></span></th>
					<th scope="col" class="align_left"><span><spring:message code="appvl.approvalhistory.sign.table.status"/></span></th>
					<th scope="col" class="align_left"><span><spring:message code="appvl.approvalhistory.sign.table.processdate"/></span></th>
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
					<td><a href="javascript:documentPopup('view','${signerHistory.docVersion}')"><fmt:formatNumber pattern=".0" value="${signerHistory.docVersion}"/></a></td>
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
					<td colspan="5">
					<c:choose>
						<c:when test="${not empty signerHistory.signerOpinion}"><div class="signer_opinion"><c:out value="${signerHistory.signerOpinion}"/></div></c:when>
						<c:otherwise><div class="signer_opinion"><spring:message code="appvl.approvalhistory.sign.table.no_opinion"/></div></c:otherwise>
					</c:choose>
					</td>			
				</tr>
				</c:forEach>
			</tbody>
		</table>	
	</div>

	<div class="table_rapper rapper_table height_line10" style="display: none" id="div_signerline">
		<table summary="" class="borader_width_borderNone board_type_height">
			<caption class="blind"></caption>
			<colgroup>
				<col width="60px"/>
				<col width="130px"/>
				<col width="160px"/>
				<col width="160px"/>
				<col width=""/>
			</colgroup>
			<thead>
				<tr>
					<th scope="col" class="align_left"><span><spring:message code="appvl.approvalhistory.signerline.table.order"/></span></span></th>
					<th scope="col" class="align_left"><span><spring:message code="appvl.approvalhistory.signerline.table.time"/></span></th>
					<th scope="col" class="align_left"><span><spring:message code="appvl.approvalhistory.signerline.table.initiator"/></span></th>
					<th scope="col" class="align_left"><span><spring:message code="appvl.approvalhistory.signerline.table.target"/></span></th>
					<th scope="col" class="align_left"><span><spring:message code="appvl.approvalhistory.signerline.table.activity"/></span></th>
				</tr>
			</thead>					
			<tbody>
			<c:if test="${empty signerChangeHistoryList}">
				<tr><td colspan="5" class="table_nodata"><span><spring:message code="common.msg.table.nodata"/></span></td></tr>
			</c:if>		
				<c:forEach var="changeHistory" items="${signerChangeHistoryList}" varStatus="status">
				<tr>
					<td><div class="signer_seq"><c:out value="${status.count}"/></div></td>
					<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${changeHistory.changeDate}" /></td>
					<td><div><c:out value="${changeHistory.initiatorNm}"></c:out></div></td>
					<td><div><c:out value="${changeHistory.targetNm}"></c:out></div></td>
					<td><div><c:out value="${changeHistory.activity}"></c:out></div></td>
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
function display_approvalHistory_load(){
	$('.board_type_height th').click(function() {
	if ($(this).hasClass('sort_down')){
		$(this).removeClass('sort_down');
		$(this).addClass('sort_up');
	} else {
		$(this).removeClass('sort_up');
		$(this).addClass('sort_down');
	  }
	});
	
	$( document ).ready(function() {
		$("#shadow_popup").css(
			{
			width:$(document).width(),
			height:$(document).height()+48,
			opacity:0.5
			}
		);
	});
		
	var winW = $(window).width()/2;
	$(".layerPopup_rapper.historyPopup").css(
		{
			left:winW-500,
			top:"20%",
			marginTop:-parseInt($(".layerPopup_rapper").css("height"))/2+"px"
		}
	);
}
//]]>
</script>
</html>